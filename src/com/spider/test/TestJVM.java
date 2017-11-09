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
import java.util.WeakHashMap;

import org.junit.*;

public class TestJVM {
	
	public static void main(String[] arg) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
		busyThread();
		br.readLine();
		Object obj = new Object();
		waitThread(obj);
		br.readLine();
		obj.notify();
		br.readLine();
	}
	public void pup(){
		System.out.println("i'm the public method");
	}
	protected void prp(){
		System.out.println("i'm the protected method");
	}
	private void prip(){
		System.out.println("i'm the private method");
	}
	public void beOverride(){
		System.out.println("i'm the beOverried method");
	}
		@Test
		public void testAdd() {
			//fail("Not yet implemented");
			
			//2个参数的含义，（期望值，实际值）
			System.out.println(Integer.toBinaryString(255)+" " + Integer.toBinaryString(13));
		}
		
		@Test  
		public void strongReference() {   
		Object referent = new Object();   
		   
		/**  
		 * 通过赋值创建 StrongReference   
		 */  
		Object strongReference = referent;   
		   
		assertSame(referent, strongReference);   
		   
		referent = null;   
		System.gc();   
		   
		/**  
		 * StrongReference 在 GC 后不会被回收  
		 */  
		assertNull(strongReference);   
		}   
		@Test  
		public void weakReference() {   
		Object referent = new Object();   
		WeakReference<Object> weakRerference = new WeakReference<Object>(referent);   
		Object str = weakRerference.get();
		System.out.println(weakRerference.get());   
		   
		referent = null;   
		System.gc();   
		   
		/**  
		 * 一旦没有指向 referent 的强引用, weak reference 在 GC 后会被自动回收  
		 */  
		System.out.println(weakRerference.get());   
		
		
		
		}
		@Test
		public void weakHashMap() throws InterruptedException{
			WeakHashMap<Object,Object> whm = new WeakHashMap<Object,Object>();
			Object key = new Object();
			Object value = new Object();
			whm.put(key, value);
			System.out.println(whm.containsKey(key));
			System.out.println("size is " + whm.size());
			key = null;
			System.gc();
			Thread.sleep(1000);
			//System.out.println(whm.containsKey(key));
			System.out.println("size is " + whm.size());
		}
		@Test
		public void softReference(){
			Object referent = new Object();
			SoftReference<Object> srf = new SoftReference<Object>(referent);
			System.out.println(srf.get());
			referent = null;
			System.gc();
			System.out.println(srf.get());
			
		}
		@Test  
		public void referenceQueue() throws InterruptedException {   
		Object referent = new Object();  
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();   
		WeakReference<Object> weakReference = new WeakReference<Object>(referent, referenceQueue);   
		   
		assertFalse(weakReference.isEnqueued());   
		Reference<? extends Object> polled = referenceQueue.poll();   
		assertNull(polled);   
		   
		referent = null;   
		System.gc();   
		 
		assertTrue(weakReference.isEnqueued());   
		Reference<? extends Object> removed = referenceQueue.remove();   
		assertNotNull(removed);  
		System.out.println(removed);
		}  

		@Test
		public void fillHeap()throws InterruptedException{
			List<OOMObject>list=new ArrayList<OOMObject>();
			for(int i=0;i<1000;i++){
			//稍作延时，令监视曲线的变化更加明显
			Thread.sleep(50);
			list.add(new OOMObject());
			}
			System.gc();
		}
		@Test
		public void monitorThread() throws IOException{
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			br.readLine();
			busyThread();
			br.readLine();
			Object obj = new Object();
			waitThread(obj);
			br.readLine();
			synchronized(obj){
				obj.notify();
			}
			br.readLine();
		}
		public static void busyThread(){
			Thread thread=new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						int i = 0;
					}
				}
			},"testBusyThread");
			thread.start();
		}
		public static void waitThread(final Object lock){
			Thread thread = new Thread(new Runnable(){

				@Override
				public void run() {
					synchronized(lock){
						try{
							lock.wait();
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
				}				
			},"testWaitThread");
			thread.start();
		}
}
class OOMObject{
	public byte[]placeholder=new byte[640*1024];
	}
class TestClass{
	private int hashCode = super.hashCode();
	public int hashCode(){
		return hashCode;
	}
	public void setHashCode(int code){
		hashCode = code;
		
	}
}