package com.project.thread;

import java.util.List;

public class CheckEnd extends Thread {
	
	private List<Integer> ids;
	private int n;

	public CheckEnd(List<Integer> ids, int n) {
		this.ids = ids;
		this.n = n;
	}

	public void run() {
		if(ids.size() == n)
			System.out.println(printIds());
	}
	
	private String printIds() {
		String listString = "[";
		for(int l=0; l<ids.size(); l++) {
			listString+=" " + ids.get(l) + " ";
		}
		listString+="]";
		return listString;
	}
	
}
