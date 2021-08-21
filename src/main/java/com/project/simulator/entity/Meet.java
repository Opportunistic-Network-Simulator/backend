package com.project.simulator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Meet implements Comparable<Meet> {
	
	private Pair pair;
	private double instant; //meet instant in seconds

	@Override
	public int compareTo(Meet otherMeet) {
		if(this.instant > otherMeet.instant) return 1; //increasing way
		if(this.instant < otherMeet.instant) return -1;
		return 0;
	}
	
}
