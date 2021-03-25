package com.project.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CFOPTransaction {
	
	public Integer cfop;
	public Double valor;
}
