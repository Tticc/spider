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
	public void donothing() throws InterruptedException{
		System.out.println(Son.IKS);
		System.out.println(Son.SG);
		/*Parent son1 = new Son();
		Thread.sleep(2000);
		Parent son2 = new Son();*/
		/*son1.stP();
		System.out.println(son1.greet);
		son1.print();*/
		
	}
	@Test
	public void changeToString(){
		//binary to String
				//String origin = "10011110 10111001 01110101 01111110 11011000 00101111 10101111 01111101 01000001 11010111 01110111";
				String origin = "6f 72 67 2f 66 65 6e 69 78 73 6f 66 74 2f";
				String[] cols = origin.split(" ");  
				StringBuilder strb = new StringBuilder();  
				for(String col : cols) {
				  char ch =(char) Integer.parseInt(col,16);  
				  strb.append(ch);  
				}  
				System.out.println(strb.toString()); 
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

class Parent{
	public String greet = "i'm father";
	public static String SG = "i'm father";
	public void print(){
		System.out.println("i'm the printer for father");
	}
	public static void stP(){
		System.out.println("i'm the stP for father");
	}
	static{
		System.out.println("init for father");
	}
}
class Son extends Parent{
	public static final String IKS = "this is a constant";
	public String greet = "i'm son";

	public void print(){
		System.out.println("i'm the printer for son");
	}
	public static void stP(){
		System.out.println("i'm the stP for son");
	}
	static{
		System.out.println("init for son");
	}
}





