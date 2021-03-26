package com.project.api.dto;

import java.util.List;

import com.project.model.entity.Pair;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PairsDTO {

	private List<Pair> pairsList;
	
}