package com.spider.test;

import java.io.IOException;
import java.io.InputStream;

public class TestJVM3 {
	public static void main(String[] args)throws Exception{

		ClassLoader myLoader = new ClassLoader(){
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException{
				try{
					System.out.println("name: "+name);
					String fileName=name.substring(name.lastIndexOf(".")+1)+".class";
					System.out.println(fileName);
					Class c = findLoadedClass(fileName);
					InputStream is = getClass().getResourceAsStream(fileName);
					if(is == null){
						return super.loadClass(name);
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name,b,0,b.length);
				}catch(IOException e){
					throw new ClassNotFoundException(name);
				}
			}
			
			@Override
			protected Class<?> findClass(String name) throws ClassNotFoundException {
				return null;
			}
		};
		Object obj = myLoader.loadClass("com.spider.test.TestJVM3").newInstance();
		System.out.println();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof com.spider.test.TestJVM3);
	}
	
	
}
