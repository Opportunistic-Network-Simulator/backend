package com.project.test.interfaces.commandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.project.interfaces.commandLine.dto.CLIPairDTO;
import com.project.interfaces.commandLine.dto.CLIPairsDTO;
import com.project.simulator.entity.Pair;

public class PairsJsonParser {
	
	public static void pairsToJsonFile(List<Pair> pairs) {
		List<CLIPairDTO> pairsDTO = new ArrayList<CLIPairDTO>();
		
		for(Pair pair : pairs) {
			pairsDTO.add(pairToCLIPair(pair));
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(
					new File("D:\\Gabriel\\Documentos\\Matérias\\PFC\\Protocolos\\Direct Delivery\\DirectDeliveryPairsExample.json")
					, new CLIPairsDTO(pairsDTO));
		} catch (Exception e) {
		}
	}
	
	private static CLIPairDTO pairToCLIPair(Pair pair) {
		CLIPairDTO pairDTO = new CLIPairDTO(pair.getNode1(), pair.getNode2(), pair.getMaxRate(), false, 0);
		return pairDTO;
	}

}
