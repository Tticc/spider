package com.spider.test;

import java.util.ArrayList;
import java.util.List;

import com.spider.myutil.myenum.*;

public class CommonTest {
	static class OOMObject{}
	
	public static void main(String[] args) {
		List<TooMuchThread> list = new ArrayList<TooMuchThread>();
		int i = 0;
		try{
			while(true){
				TooMuchThread nel = new TooMuchThread();
				list.add(new TooMuchThread());
				list.get(i).start();
				++i;
				//throw new Exception();
			}
		}catch(Throwable e){
			System.out.println(list.size());
		}
		
	}
	

}
class TooMuchThread extends Thread{
private void dontstop(){
	while(true){}
}
public void run(){
	dontstop();
}
}

class THashCode{
	int count = 0;
	void testLeak(){
		++count;
		testLeak();
	}
	int getCount(){
		return count;
	}
}