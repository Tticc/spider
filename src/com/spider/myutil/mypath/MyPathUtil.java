package com.spider.myutil.mypath;

import java.net.URL;

public class MyPathUtil {

	public static String getCurrentProjectPath(){
		return System.getProperty("user.dir");
	}
	public static String getFilePath(){
		return MyPathUtil.class.getClassLoader().getResource("config/my.properties").toString();
	}
}
