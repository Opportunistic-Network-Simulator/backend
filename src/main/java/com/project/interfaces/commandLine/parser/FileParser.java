package com.project.interfaces.commandLine.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moandjiezana.toml.Toml;
import com.project.exception.InvalidParametersException;
import com.project.exception.SimulatorException;
import com.project.interfaces.commandLine.dto.CLIPairDTO;
import com.project.interfaces.commandLine.dto.CLIPairsDTO;
import com.project.simulator.configuration.*;
import com.project.simulator.entity.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.distribution.Exponential;
import com.project.simulator.entity.distribution.HyperExponential;
import com.project.simulator.entity.distribution.Pareto;
import com.project.simulator.entity.distribution.VariableExponential;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileParser {
    public static SimulationConfiguration parseConfig(FileNamesParser fileNamesParser) throws IOException {
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

        String fileName = parser.getString("fileName");
        if (fileName != null) {
            config.setFileName(fileName);
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

        Long hopLimit = parser.getLong("hopLimit");
        if (hopLimit != null && hopLimit != 0) {
            config.setHopLimit(hopLimit);
        } else {
            config.setHopLimit(Long.MAX_VALUE);
        }

        return config;
    }

    private static MeetingTraceConfiguration parseMeetingTraceConfig(Toml parser, FileNamesParser fileNamesParser) 
    		throws IOException {
    	
    	String pairDefinitionFile = parser.getString("pairDefinitionFile");
        String distributionType = parser.getString("type");
    	
		return new MeetingTraceConfiguration(
		    MeetingTraceConfigurationType.valueOf(distributionType),
		    parser.getDouble("totalSimulationTime"),
		    parsePairDefinitionFile(fileNamesParser, pairDefinitionFile, distributionType)
		);
    }
    
    private static List<Pair> parsePairDefinitionFile(FileNamesParser fileNamesParser, String pairDefinitionFile, String distributionType) throws IOException {
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
        
		return convertDtoToPair(pairDtoList, distributionType);
    	
    }
    
    private static List<Pair> convertDtoToPair(List<CLIPairDTO> pairs, String distributionType) {
		List<Pair> pairsList = new ArrayList<>();

        switch (distributionType) {
            case "EXPONENTIAL":
                for(CLIPairDTO pair : pairs) {
                    pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new Exponential(pair.getLambda())));
                }
                break;
            case "PARETO":
                for(CLIPairDTO pair : pairs) {
                    pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new Pareto(pair.getMu(), pair.getSigma(), pair.getGamma(), pair.getAlpha())));
                }
                break;
            case "HYPER_EXPONENTIAL":
                for(CLIPairDTO pair : pairs) {
                    pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new HyperExponential(pair.getLambdas(), pair.getPs())));
                }
                break;
            case "VARIABLE_EXPONENTIAL":
                for(CLIPairDTO pair : pairs) {
                    pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new VariableExponential(pair.getLambda(), pair.getVariability())));
                }
                break;
            default:
                throw new InvalidParametersException("Invalid distribution type. Distribution type: " + distributionType);
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

        Double pInit = parser.getDouble("pInit");
        if (pInit != null) {
            config.setPInit(pInit);
        }

        Double gamma = parser.getDouble("gamma");
        if (gamma != null) {
            config.setGamma(gamma);
        }

        Double beta = parser.getDouble("beta");
        if (beta != null) {
            config.setBeta(beta);
        }

        return config;
    }
}
