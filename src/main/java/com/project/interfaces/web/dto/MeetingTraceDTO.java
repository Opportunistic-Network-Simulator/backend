package com.project.interfaces.web.dto;

import java.util.ArrayList;
import java.util.List;

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
