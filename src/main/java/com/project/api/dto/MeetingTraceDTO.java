package com.project.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.Meet;

import lombok.Getter;

@Getter
public class MeetingTraceDTO {
	
	private List<MeetDTO> meetingTrace;
	
	public MeetingTraceDTO() {
		this.meetingTrace = new ArrayList<MeetDTO>();
	}
	
	public void addMeet(MeetDTO meet) {
		this.meetingTrace.add(meet);
	}

}
