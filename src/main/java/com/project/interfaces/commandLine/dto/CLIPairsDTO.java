package com.project.interfaces.commandLine.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CLIPairsDTO {
	private List<CLIPairDTO> pairsList;

}
