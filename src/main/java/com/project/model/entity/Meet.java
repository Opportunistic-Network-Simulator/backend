package com.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class Meet implements Comparable<Meet>{
	
	Pair pair;
	double instant; //meet instant in seconds
	
	@Override
	public int compareTo(Meet otherMeet) {
		if(this.instant > otherMeet.instant) return 1; //increasing way
		return -1;
	}
	
}
