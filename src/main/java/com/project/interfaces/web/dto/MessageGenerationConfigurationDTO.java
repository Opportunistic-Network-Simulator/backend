package com.project.interfaces.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageGenerationConfigurationDTO {
	
	private String type;
    private int sourceNodeId;
    private int destinationNodeId;
    private double generationInstant;
    private int amountNodes;

}
