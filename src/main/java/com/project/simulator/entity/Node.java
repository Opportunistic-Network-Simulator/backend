package com.project.simulator.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Node {
	
	private long id;
	private List<Message> messages;

}
