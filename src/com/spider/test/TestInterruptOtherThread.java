package com.spider.test;

import java.util.Calendar;

public class TestInterruptOtherThread {

	public static void main(String[] args) throws InterruptedException{
		InterruptedThread it = new InterruptedThread();
		it.start();
		Thread.sleep(5000);
		it.setInterruptIt(true);
		Thread.sleep(9000);
		it.setInterruptIt(false);
		it.notifyThread();
	}
	
}

class InterruptedThread extends Thread{
	boolean interruptIt = false;
	public void setInterruptIt(boolean interruptIt){
		this.interruptIt = interruptIt;
	}
	public void pauseThread(){
		synchronized(this){
			if(interruptIt){
				try{
					wait();
				}catch(InterruptedException e){
					System.out.println("---------------------exception in wait--------------");
					e.printStackTrace();
				}
			}
		}
	}
	public void notifyThread(){
		synchronized(this){
			try{
				notify();
			}catch(Exception ex){
				System.out.println("---------------------exception in notify--------------");
				ex.printStackTrace();
			}
		}
	}
	@Override
	public void run(){
		while(true){
			
			Calendar ca = Calendar.getInstance();
			System.out.println("print it:"+ca.get(Calendar.HOUR)+":"+ca.get(Calendar.MINUTE)+":"+ca.get(Calendar.SECOND));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println("---------------------exception in print it--------------");
				e.printStackTrace();
			}
			pauseThread();
		}
	}
	
}
