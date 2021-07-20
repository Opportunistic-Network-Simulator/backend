package com.project.thread;

public class ThreadTest2 extends Thread {
	
	public ThreadTest2(String name) {
		this.setName(name);
	}

	public void run(){
		System.out.println("Começou!");
		while(true) {
			System.out.println(Thread.currentThread().getName() + ": priority " + Thread.currentThread().getPriority());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	    }

}
