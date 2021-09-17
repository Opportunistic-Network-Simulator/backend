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
		
		return new SimulationConfiguration(
					simulationConfigurationDTO.getNumberOfRounds(),
					parseProtocolConfiguration(simulationConfigurationDTO.getProtocolConfiguration()),
					parserMeetingTraceConfiguration(simulationConfigurationDTO.getMeetingTraceConfiguration()),
					parseMessageGenerationConfiguration(simulationConfigurationDTO.getMessageConfiguration())
				);
	}
	
	private ProtocolConfiguration parseProtocolConfiguration(ProtocolConfigurationDTO protocolConfigurationDTO) {

        ProtocolType type = ProtocolType.valueOf(protocolConfigurationDTO.getType());
        
        switch (type) {
		
        case EPIDEMIC_P_Q:
        	
        	return new ProtocolConfiguration(type, protocolConfigurationDTO.getP(), protocolConfigurationDTO.getQ());
		
        case SPRAY_AND_WAIT:
        case BINARY_SPRAY_AND_WAIT:
        	return new ProtocolConfiguration(type, protocolConfigurationDTO.getL());
			
		default:
			return new ProtocolConfiguration(type);
		}
                
	}
	
	private MeetingTraceConfiguration parserMeetingTraceConfiguration(MeetingTraceConfigurationDTO meetingTraceConfigurationDTO) {	
		
		return new MeetingTraceConfiguration(
					MeetingTraceConfigurationType.valueOf(meetingTraceConfigurationDTO.getType()),
					meetingTraceConfigurationDTO.getTotalSimulationTime(),
					parsePairs(meetingTraceConfigurationDTO.getPairList())
				);
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
			return new MessageGenerationConfiguration(
					type,
					messageGenerationConfigurationDTO.getSourceNodeId(),
					messageGenerationConfigurationDTO.getDestinationNodeId(),
					messageGenerationConfigurationDTO.getGenerationInstant(),
					messageGenerationConfigurationDTO.getAmountNodes()
				);

		default:
			return new MessageGenerationConfiguration(
						type,
						messageGenerationConfigurationDTO.getGenerationInstant(),
						messageGenerationConfigurationDTO.getAmountNodes()
					);
		}		
	}

}
