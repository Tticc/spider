package com.spider.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.WeakHashMap;

import org.junit.*;

public class TestJVM2 {
	
	public static void main(String[] arg) throws IOException, InterruptedException{
		Random random = new Random();
		while(true){
			System.out.println(add(random.nextInt(100),random.nextInt(100)));
			
			Thread.sleep(2000);
		}
	}
	@Test
	public void donothing(){
		//BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//br.readLine();
	}
	@Test
	public void testDeadLock() throws InterruptedException, IOException{
		TestJVM2 j1 = new TestJVM2();
		TestJVM2 j2 = new TestJVM2();
		//new Thread(new DeadLockTest(j1,j2),"DeadLockTest1").start();
		//new Thread(new DeadLockTest(j2,j1),"DeadLockTest2").start();

		Thread.sleep(5*60*0000);
	}
	
	@Test
	public void testBtrace() throws InterruptedException{
		Random random = new Random();
		while(true){
			System.out.println(add(random.nextInt(100),random.nextInt(100)));
			
			Thread.sleep(2000);
		}
	}
	public static int add(int a,int b){
		return a+b;
	}
	
}


class DeadLockTest implements Runnable{
	private Object a;
	private Object b;
	DeadLockTest(Object a, Object b){
		this.a = a;
		this.b = b;
	}
	@Override
	public void run() {
		synchronized(a){
			try {
				Thread.sleep(1150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(b){
				System.out.println(a);
			}
		}
		
	}
	
}