package com.project.interfaces.web.parser;

import java.util.ArrayList;
import java.util.List;

import com.project.exception.InvalidParametersException;
import com.project.interfaces.commandLine.dto.CLIPairDTO;
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
import com.project.simulator.entity.distribution.Exponential;
import com.project.simulator.entity.distribution.HyperExponential;
import com.project.simulator.entity.distribution.Pareto;
import com.project.simulator.entity.distribution.VariableExponential;

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
		String distributionType = meetingTraceConfigurationDTO.getType();
		return new MeetingTraceConfiguration(
					MeetingTraceConfigurationType.valueOf(distributionType),
					meetingTraceConfigurationDTO.getTotalSimulationTime(),
					parsePairs(meetingTraceConfigurationDTO.getPairList(), distributionType)
				);
	}
	
	private List<Pair> parsePairs(List<PairDTO> pairs, String distributionType) {
		List<Pair> pairsList = new ArrayList<>();

		switch (distributionType) {
			case "EXPONENTIAL":
				for(PairDTO pair : pairs) {
					pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new Exponential(pair.getLambda())));
				}
				break;
			case "PARETO":
				for(PairDTO pair : pairs) {
					pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new Pareto(pair.getMu(), pair.getSigma(), pair.getGamma(), pair.getAlpha())));
				}
				break;
			case "HYPER_EXPONENTIAL":
				for(PairDTO pair : pairs) {
					pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new HyperExponential(pair.getLambdas(), pair.getPs())));
				}
				break;
			case "VARIABLE_EXPONENTIAL":
				for(PairDTO pair : pairs) {
					pairsList.add(new Pair(pair.getNode1(), pair.getNode2(), new VariableExponential(pair.getLambda(), pair.getVariability())));
				}
				break;
			default:
				throw new InvalidParametersException("Invalid distribution type. Distribution type: " + distributionType);
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
