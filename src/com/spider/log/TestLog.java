package com.spider.log;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import com.spider.myutil.Util;

public class TestLog {

	public static void main(String[] args){
		
		Logger logger = WriteLog.GetLog("TestLog");
		logger.error(Util.getFormatDateAll() + " - error message");
	}
}


class WriteLog{
	private WriteLog(){}
	
	private static Logger logger = null;
	public static Logger GetLog(String className){
		if(logger == null){
			logger = Logger.getLogger(className);
			setting();
		}
		return logger;
	}
	
	private static void setting(){
		FileAppender appender = null;
		SimpleLayout layout = new SimpleLayout();
	      try {

	          appender = new FileAppender(layout,"D:\\spider\\log\\log_"+Util.getFormatDate()+".txt",false);

	       } catch(Exception e) {}
	      logger.addAppender(appender);
	}
}