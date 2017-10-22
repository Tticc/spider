package com.spider.demo1;

import java.io.IOException;

public class UrlDataHanding implements Runnable {  
    /** 
     * 下载对应页面并分析出页面对应的URL放在未访问队列中。 
     *  
     * @param url 
     */  
    public void dataHanding(String url) {  
        try {
			HrefOfPage.getHrefOfContent(DownloadPage.getContentFormUrl(url),url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  
  
    public void run() {  
        while (!UrlQueue.isEmpty()) {  
        	try{
            dataHanding(UrlQueue.outElem()); 
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
        }  
    }  
}  
