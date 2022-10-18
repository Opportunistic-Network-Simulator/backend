package com.project.interfaces.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolConfigurationDTO {

	String type;
	double p;
    double q;
    int l;
	
}
