package com.project.thread;

import java.util.ArrayList;
import java.util.List;

public class RunThreads {

	public static void main (String args[]) throws InterruptedException {
		
//		ThreadTest test = new ThreadTest();
//		test.start();
		
		createThread();
		
	}
	
	public static int createThread() throws InterruptedException {
		List<Integer> ids = new ArrayList<Integer>();
		int n = 10;
		for(int i=0; i<n; i++) {
			new ThreadTest(i, ids).start();
		}
		new CheckEnd(ids, n).start();
		
		return 0;
	}
	
	private static String printIds(List<Integer> ids) {
		String listString = "[";
		for(int i=0; i<ids.size(); i++) {
			listString+=" " + ids.get(i) + " ";
		}
		listString+="]";
		return listString;
	}
	
	private static int callThreads() {
		ThreadTest2 test2 = new ThreadTest2("thread 1");
		test2.start();

		ThreadTest2 test3 = new ThreadTest2("thread 2");
		test3.start();
		
		return 0;
	}
	
}
