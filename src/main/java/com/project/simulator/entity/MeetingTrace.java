package com.project.simulator.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class MeetingTrace {
	private List<Meet> meetingTrace;
	
	public MeetingTrace() {
		this.meetingTrace = new ArrayList<Meet>();
	}
	
	public void addMeet(Meet meet) {
		this.meetingTrace.add(meet);
	}
	
	public void orderTrace() {
		Collections.sort(this.meetingTrace);
	}
}
