package com.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.model.entity.Meet;
import com.project.model.entity.Pair;

	

@Service
public class SimulationService {

	private List<Meet> fel; //future event list
	private double totalSimulationTime; //seconds
	
	public SimulationService() { //constructor. It will be executed when server start
		this.fel = new ArrayList<Meet>(); //to initialize fel. 
		this.totalSimulationTime = 100; //it must be defined by another way
	}
	
	public List<Meet> generateMeetingTrace(List<Pair> pairsList) {
		
		this.fel.clear();
		for(Pair pair : pairsList) {
			if(pair.getRate() != 0) this.generatePairMeetings(pair);
		}
		Collections.sort(this.fel);
		return this.fel;
	}	

	public void generatePairMeetings(Pair pair) {
		double rate = pair.getRate(); //just for first meet
		double minRate = pair.isVariableRate() ? rate*(1 - pair.getVariabilityDegree()) : 0;
		double maxRate = pair.isVariableRate() ? rate*(1 + pair.getVariabilityDegree()) : 0;
		double dt = 1.0/rate;
		double lastMeetInstant = 0;
		double nextMeetInstant;
		boolean first = true;
		
		while(true) {
			if(pair.isVariableRate() && !first) {
				rate = Math.random() * (maxRate - minRate) + minRate;
				dt = 1.0/rate;
			}
			first = false;
			//else, dt was already setted before while
			nextMeetInstant = lastMeetInstant + dt;
			lastMeetInstant = nextMeetInstant;
			if(nextMeetInstant > this.totalSimulationTime) break;
			Meet meet =  Meet.builder().pair(pair).instant(nextMeetInstant).build();
			this.fel.add(meet);
		}
	}
	
}
