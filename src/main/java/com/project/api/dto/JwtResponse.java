package com.project.api.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtResponse implements Serializable {

private static final long serialVersionUID = -8091879091924046844L;
	
//	private ResponseUserDTO user;
//	private final String token;
	private SessionInfoDTO sessionInfo;


}