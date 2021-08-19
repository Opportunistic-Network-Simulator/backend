package com.project.interfaces.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MeetDTO {

	private PairDTO pair;
	private double instant; //meet instant in seconds
	
}
