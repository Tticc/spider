package com.spider.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestReadConfig {

	public static void main(String[] args) throws IOException{
		String contextPath = "";
		//InputStream inStream = new FileInputStream(new File("filePath"));
		//InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/log4j.properties");//("common.properties");//
		InputStream stream = ClassLoader.getSystemResourceAsStream("config/my.properties");
		Properties properties=new Properties();  
		
       /* StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = stream.read(b)) != -1;) {
             out.append(new String(b, 0, n));
        }
        System.out.println(out.toString());*/
        try {
			properties.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        contextPath = properties.getProperty("about");  
        System.out.println(contextPath);
        System.out.println(properties.getProperty("path"));
        System.out.println(System.getProperty("user.dir"));
        System.out.println(TestReadConfig.class.getClassLoader().getResource("config/my.properties").toString());
	}
}
