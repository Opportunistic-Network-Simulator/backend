package com.project.interfaces.web.parser;

import java.util.ArrayList;
import java.util.List;

import com.project.interfaces.web.dto.MeetingTraceConfigurationDTO;
import com.project.interfaces.web.dto.MessageGenerationConfigurationDTO;
import com.project.interfaces.web.dto.PairDTO;
import com.project.interfaces.web.dto.ProtocolConfigurationDTO;
import com.project.interfaces.web.dto.SimulationConfigurationDTO;
import com.project.simulator.configuration.MeetingTraceConfiguration;
import com.project.simulator.configuration.MeetingTraceConfigurationType;
import com.project.simulator.configuration.MessageGenerationConfiguration;
import com.project.simulator.configuration.MessageGenerationType;
import com.project.simulator.configuration.ProtocolConfiguration;
import com.project.simulator.configuration.ProtocolType;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.Pair;

public class WebParser {
	
	public SimulationConfiguration parser (SimulationConfigurationDTO simulationConfigurationDTO) {
		
		SimulationConfiguration config = new SimulationConfiguration();
		config.setNumberOfRounds(simulationConfigurationDTO.getNumberOfRounds());
		config.setProtocolConfiguration(parseProtocolConfiguration(simulationConfigurationDTO.getProConfigurationDTO()));
		config.setMeetingTraceConfiguration(parserMeetingTraceConfiguration(simulationConfigurationDTO.getMeetingTraceConfigurationDTO()));
		config.setMessageGenerationConfiguration(parseMessageGenerationConfiguration(simulationConfigurationDTO.getMessageConfigurationDTO()));
		return config;
		
	}
	
	private ProtocolConfiguration parseProtocolConfiguration(ProtocolConfigurationDTO protocolConfigurationDTO) {
		ProtocolConfiguration protocolConfiguration = new ProtocolConfiguration();

        ProtocolType type = ProtocolType.valueOf(protocolConfigurationDTO.getType());
        protocolConfiguration.setType(type);
        
        switch (type) {
		
        case EPIDEMIC_P_Q:
			protocolConfiguration.setP(protocolConfigurationDTO.getP());
    		protocolConfiguration.setQ(protocolConfigurationDTO.getQ());
			break;

        case SPRAY_AND_WAIT:
        case BINARY_SPRAY_AND_WAIT:
        	protocolConfiguration.setL(protocolConfigurationDTO.getL());
        	break;
			
		default:
			break;
		}
        
        return protocolConfiguration;

	}
	
	private MeetingTraceConfiguration parserMeetingTraceConfiguration(MeetingTraceConfigurationDTO meetingTraceConfigurationDTO) {
		MeetingTraceConfiguration meetingTraceConfiguration = new MeetingTraceConfiguration();
		meetingTraceConfiguration.setType(MeetingTraceConfigurationType.valueOf(meetingTraceConfigurationDTO.getType()));
		meetingTraceConfiguration.setPairs(parsePairs(meetingTraceConfigurationDTO.getPairList()));
		meetingTraceConfiguration.setTotalSimulationTime(meetingTraceConfigurationDTO.getTotalSimulationTime());
		
		return meetingTraceConfiguration;
	}
	
	private List<Pair> parsePairs(List<PairDTO> pairs) {
		List<Pair> pairsList = new ArrayList<Pair>();
		for(PairDTO pair : pairs) {
			pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), pair.getRate(), pair.getVariabilityDegree()));
		}
		return pairsList;
	}
	
	private MessageGenerationConfiguration parseMessageGenerationConfiguration(MessageGenerationConfigurationDTO messageGenerationConfigurationDTO) {
		MessageGenerationConfiguration messageGenerationConfiguration = new MessageGenerationConfiguration();
		MessageGenerationType type = MessageGenerationType.valueOf(messageGenerationConfigurationDTO.getType());
		switch (type) {
		case FIXED_NODES:
			messageGenerationConfiguration.setSourceNodeId(messageGenerationConfigurationDTO.getSourceNodeId());
			messageGenerationConfiguration.setDestinationNodeId(messageGenerationConfigurationDTO.getDestinationNodeId());
			break;

		default:
			break;
		}
		messageGenerationConfiguration.setGenerationInstant(messageGenerationConfigurationDTO.getGenerationInstant());
		messageGenerationConfiguration.setAmountNodes(messageGenerationConfigurationDTO.getAmountNodes());
		return messageGenerationConfiguration;
		
	}

}
