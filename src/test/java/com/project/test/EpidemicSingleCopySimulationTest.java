package com.project.test;

import com.project.simulator.SimulationProcessor;
import com.project.simulator.configuration.*;
import com.project.simulator.entity.Pair;
import com.project.simulator.entity.SimulationReport;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EpidemicSingleCopySimulationTest {
	
//	@Test
    public void test() {
		Timestamp begin = Timestamp.valueOf(LocalDateTime.now());
        SimulationConfiguration config = prepareConfig(100, 10000);
        SimulationProcessor processor = new SimulationProcessor(config);
        SimulationReport report = processor.runSimulation();
        System.out.println("Average delay: " + report.getAverageDelay());
        System.out.println("Delivery ratio: " + report.getDeliveryRatio());
        System.out.println();
        Timestamp end = Timestamp.valueOf(LocalDateTime.now());
        System.out.println("tempo de duração (s): " + (end.getTime() - begin.getTime())/1000);

    }

    private SimulationConfiguration prepareConfig(int numberOfRounds, double totalSimulationTime) {
    	
    	 return new SimulationConfiguration(
     			numberOfRounds,
     			prepareProtocolConfig(),
     			prepareMeetingTraceConfig(totalSimulationTime),
     			prepareMessageConfig()
     		);
    }
    
    private MessageGenerationConfiguration prepareMessageConfig() {
        return new MessageGenerationConfiguration(
                MessageGenerationType.ALL_PAIRS,
                0,
                15
        );
    }

    private MeetingTraceConfiguration prepareMeetingTraceConfig(double totalSimulationTime) {
        return new MeetingTraceConfiguration(MeetingTraceConfigurationType.EXPONENTIAL, totalSimulationTime, generatePairs());
    }

    private List<Pair> generatePairs() {
		List<Pair> pairs = new ArrayList<Pair>();
		
		for(int i = 0; i < 3; i++)
			pairs.addAll(generateComunity(i));
		
		pairs.addAll(generateTraveling());
		
		return pairs;
	}
    
    private List<Pair> generateComunity(int comunityNumber) {
		List<Pair> pairs = new ArrayList<Pair>();
		for (int i = comunityNumber * 5; i < comunityNumber * 5 + 5; i++) {
			for (int j = i + 1; j < comunityNumber * 5 + 5; j++) {
				if (i == 0 || i == 1 )
					pairs.add(new Pair(i, j, 1.25));
				else
					pairs.add(new Pair(i, j, 2.5));
			}
		}
		return pairs;
	}
    
    private List<Pair> generateTraveling() {
		List<Pair> pairs = new ArrayList<Pair>();
		for (int i = 0; i < 2; i++) {
			for (int j = 5 * (i + 1); j < 5 * (i + 2); j++) {
				pairs.add(new Pair(i, j, 1.25));
			}
		}
		return pairs;
	}

    private ProtocolConfiguration prepareProtocolConfig() {
        return new ProtocolConfiguration(ProtocolType.SINGLE_COPY_EPIDEMIC);
    }

}

