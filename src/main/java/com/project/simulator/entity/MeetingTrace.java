package com.project.simulator.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.simulator.entity.event.MeetEvent;

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
	
	public List<MeetEvent> generateMeetEventQueue() {
		List<MeetEvent> meetEventQueue = new ArrayList<MeetEvent>();
		for(Meet meet : meetingTrace) {
			meetEventQueue.add(new MeetEvent(
									meet.getInstant(), 
									meet.getPair().getNode1(), 
									meet.getPair().getNode2()));
		}
		return meetEventQueue;
	}
}
