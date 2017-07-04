package com.spider.myutil.destructive;

import java.util.ArrayList;
import java.util.List;


public class DestructiveTest {

	public static void main(String[] args) {
		//stackOverflow();
		
	}

	public static void outOfMemory(){
		List<DestructiveTest> list = new ArrayList<DestructiveTest>();
		while(true){
			list.add(new DestructiveTest());
		}
	}
	public static void stackOverflow(){
		while(true){
			stackOverflow();
		}
	}
	
	public static void breakTheOSDown(){
		while(true){
			new Thread(new Runnable(){
				@Override
				public void run(){
					while(true){}
				}
			}).start();
		}
	}
	public static void breakTheOSDownTest(){
		List<TooMuchThread> list = new ArrayList<TooMuchThread>();
		int i = 0;
		try{
			while(true){
				list.add(new TooMuchThread());
				list.get(i).start();
				++i;
			}
		}catch(Throwable e){
			System.out.println(list.size());
		}
	}
}


class TooMuchThread extends Thread{
	private void dontStop(){
		while(true){}
	}
	@Override
	public void run(){
		dontStop();
	}
}
