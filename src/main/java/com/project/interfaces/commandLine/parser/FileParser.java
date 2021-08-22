package com.project.interfaces.commandLine.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moandjiezana.toml.Toml;
import com.project.exception.SimulatorException;
import com.project.interfaces.commandLine.dto.CLIPairDTO;
import com.project.interfaces.commandLine.dto.CLIPairsDTO;
import com.project.simulator.configuration.*;
import com.project.simulator.entity.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileParser {
    public static SimulationConfiguration parseConfig(FileNamesParser fileNamesParser) throws FileNotFoundException, IOException {
        Toml parser = new Toml().read(fileNamesParser.getInputFile().get());

        SimulationConfiguration config = new SimulationConfiguration();

        config.setMessageGenerationConfiguration(parseMessageGenerationConfig(parser.getTable("messageGeneration")));
        config.setProtocolConfiguration(parseProtocolConfig(parser.getTable("protocol")));
        config.setMeetingTraceConfiguration(
        		parseMeetingTraceConfig(parser.getTable("meetingTrace"), fileNamesParser));

        Long numberOfRounds = parser.getLong("numberOfRounds");

        if(numberOfRounds != null) {
            config.setNumberOfRounds(numberOfRounds.intValue());
        }

        return config;
    }

    private static MessageGenerationConfiguration parseMessageGenerationConfig(Toml parser) {
        MessageGenerationConfiguration config = new MessageGenerationConfiguration();

        MessageGenerationType type = MessageGenerationType.valueOf(parser.getString("type"));
        config.setType(type);

        String filename = parser.getString("filename");
        if (filename != null) {
            config.setFileName(filename);
        }

        Double generationInstant = parser.getDouble("generationInstant");
        if (generationInstant != null) {
            config.setGenerationInstant(generationInstant);
        } else {
            config.setGenerationInstant(0);
        }

        Long amountNodes = parser.getLong("amountNodes");
        if (amountNodes != null) {
            config.setAmountNodes(amountNodes.intValue());
        }

        Long sourceNodeId = parser.getLong("sourceNodeId");
        if (sourceNodeId != null) {
            config.setSourceNodeId(sourceNodeId.intValue());
        }

        Long destinationNodeId = parser.getLong("destinationNodeId");
        if (destinationNodeId != null) {
            config.setDestinationNodeId(destinationNodeId.intValue());
        }

        return config;
    }

    private static MeetingTraceConfiguration parseMeetingTraceConfig(Toml parser, FileNamesParser fileNamesParser) 
    		throws FileNotFoundException, IOException {
    	
    	String pairDefinitionFile = parser.getString("pairDefinitionFile");

    	
		return new MeetingTraceConfiguration(
		    MeetingTraceConfigurationType.valueOf(parser.getString("type")),
		    parser.getDouble("totalSimulationTime"),
		    parsePairDefinitionFile(fileNamesParser, pairDefinitionFile)
		);
    }
    
    private static List<Pair> parsePairDefinitionFile(FileNamesParser fileNamesParser, String pairDefinitionFile) throws FileNotFoundException, IOException {
    	JSONParser parser = new JSONParser();
        JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) parser
					.parse(new FileReader(fileNamesParser.toAbsoluteInputPairDefinitionFile(pairDefinitionFile)));
		} catch (ParseException e) {
			throw new SimulatorException("pairDefinitionFile format error");
		}
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //to ignore missing json fields
        CLIPairsDTO pairsDto = mapper.readValue(jsonObject.toJSONString(), CLIPairsDTO.class);
        List<CLIPairDTO> pairDtoList = pairsDto.getPairsList();
        List<Pair> pairList = convertDtoToPair(pairDtoList);
        
		return pairList;
    	
    }
    
    private static List<Pair> convertDtoToPair(List<CLIPairDTO> pairs) {
		List<Pair> pairsList = new ArrayList<Pair>();
		for(CLIPairDTO pair : pairs) {
			pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), pair.getRate(), pair.getVariabilityDegree()));
		}
		return pairsList;
	}

    private static ProtocolConfiguration parseProtocolConfig(Toml parser) {
        ProtocolConfiguration config = new ProtocolConfiguration();

        ProtocolType type = ProtocolType.valueOf(parser.getString("type"));
        config.setType(type);

        Double p = parser.getDouble("p");
        if (p != null) {
            config.setP(p);
        }

        Double q = parser.getDouble("q");
        if (q != null) {
            config.setQ(q);
        }

        Long l = parser.getLong("l");
        if (l != null) {
            config.setL(l.intValue());
        }

        return config;
    }
}
