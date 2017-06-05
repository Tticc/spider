package com.spider.demo1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;  


public class Main {  
    public static void main(String[] args) throws SQLException {  
        String url = "http://www.oschina.net/code/explore/achartengine/client/AndroidManifest.xml";  
        String url1 = "http://www.oschina.net/code/explore";  
        String url2 = "http://13.76.191.189:8080/yk/sources/pages/home.jsp";//"http://www.oschina.net/code/explore/achartengine";  
        String url3 = "http://www.cnblogs.com/shizongger/p/6385052.html";//"http://www.oschina.net/code/explore/achartengine/client";  
        String urll = "https://www.hao123.com/";
        //UrlQueue.addElem(url);  
        //UrlQueue.addElem(url1);  
        //UrlQueue.addElem(url2);  
        //UrlQueue.addElem(url3);  
        UrlQueue.addElem(urll);  
        UrlQueue.addElem(urll);  
        UrlQueue.addElem(urll);  
        
        UrlDataHanding[] url_Handings = new UrlDataHanding[10];  
  
        for (int i = 0; i < 10; i++) {  
            url_Handings[i] = new UrlDataHanding();  
            new Thread(url_Handings[i]).start();  
        }  
        /*String as = DownloadPage.getContentFormUrl("http://www.baidu.com");
        //System.out.println(as);
        
        String ass = DownloadPage.getContentFormUrl("http://13.76.191.189:8080/yk/sources/pages/home.jsp");
        System.out.println(ass);
        List<String> urlList = new ArrayList<String>();
        getHrefOfContent(ass,urlList);
        for(String urls : urlList){
        	System.out.println(urls);
        }*/
    }  
    public static void getHrefOfContent(String content, List<String> urlList) {  
        
        String[] contents = content.split("<a href=\"");  
        for (int i = 1; i < contents.length; i++) {  
            int endHref = contents[i].indexOf("\"");  
  
            String aHref = FunctionUtils.getHrefOfInOut(contents[i].substring(  
                    0, endHref));  
            if(aHref != null){
            	urlList.add(aHref);
            }
            
            
        }  
  
        
    }  
}  