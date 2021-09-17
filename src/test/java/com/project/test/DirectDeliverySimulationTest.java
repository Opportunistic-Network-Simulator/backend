package com.project.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.MeetingTraceConfiguration;
import com.project.simulator.configuration.MeetingTraceConfigurationType;
import com.project.simulator.configuration.MessageGenerationConfiguration;
import com.project.simulator.configuration.MessageGenerationType;
import com.project.simulator.configuration.ProtocolConfiguration;
import com.project.simulator.configuration.ProtocolType;
import com.project.simulator.configuration.SimulationConfiguration;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.SimulationReport;
import com.project.test.interfaces.commandLine.PairsJsonParser;

public class DirectDeliverySimulationTest {
	
//	private double[] testSpeeds = { 2, 8, 12, 18 };
	private double[] testSpeeds = {2};

	
	//@Test
	public void testSuite() {
		for(double speed : testSpeeds) {
            testCaseSpeed(speed);
        }
	}
	private void testCaseSpeed(double speed) {System.out.println("SPEED: " + speed);
    SimulationConfiguration config = prepareConfig(speed, 10);
    SimulationProcessor processor = new SimulationProcessor(config);
    SimulationReport report = processor.runSimulation();
    System.out.println("Delivery ratio: " + report.getDeliveryRatio());
    System.out.println("Average delay: " + report.getAverageDelay());
    System.out.println();
    }
	
	private SimulationConfiguration prepareConfig(double speed, int numberOfRounds) {
		 return new SimulationConfiguration(
     			numberOfRounds,
     			prepareProtocolConfig(),
     			prepareMeetingTraceConfig(speed),
     			prepareMessageConfig()
     		);
    }
	
	private MessageGenerationConfiguration prepareMessageConfig() {
        return new MessageGenerationConfiguration(
                MessageGenerationType.ALL_PAIRS,
                0,
                50
        );
    }
	
	private MeetingTraceConfiguration prepareMeetingTraceConfig(double speed) {
        return new MeetingTraceConfiguration(MeetingTraceConfigurationType.EXPONENTIAL, 1800, generatePairs(speed));
    }
	
	private List<Pair> generatePairs(double speed) {
        List<Pair> pairs = new ArrayList<Pair>();
        double lambda = this.calculateLambda(speed);
        for(int i = 0; i < 50; i++) {
            for(int j = i + 1; j < 50; j++) {
                pairs.add(new Pair(i, j, lambda));
            }
        }
        PairsJsonParser.pairsToJsonFile(pairs);
        return pairs;
    }
	
	private double calculateLambda(double speed) {
        double omega = 1.3683;
        double radius = 50;
        double pi = Math.PI;
        double side = 2000;
        double lambda = (8*omega*radius*speed)/(pi*side*side);
        return lambda;
    }
	
	private ProtocolConfiguration prepareProtocolConfig() {
        return new ProtocolConfiguration(ProtocolType.DIRECT_DELIVERY);
    }

}
