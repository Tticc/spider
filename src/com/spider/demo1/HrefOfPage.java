package com.spider.demo1;


public class HrefOfPage {  
    /** 
     * 获得页面源代码中超链接 
     */  
    public static void getHrefOfContent(String content,String oriUrl) {  
        System.out.println("开始");  
        String[] contents = content.split("<a href=\"");  
        String[] allHref = new String[contents.length];
        
        for (int i = 1; i < contents.length; i++) {  
        	allHref[i] = "";
            int endHref = contents[i].indexOf("\"");  
  
            String aHref = FunctionUtils.getHrefOfInOut(contents[i].substring(0, endHref),oriUrl);
            allHref[i] = aHref;
            if (aHref != null) {
                String href = FunctionUtils.getHrefOfInOut(aHref,oriUrl);  
  
                //if (!UrlQueue.isContains(href) && href.indexOf("/code/explore") != -1 && !VisitedUrlQueue.isContains(href)) {  
                if (!UrlQueue.isContains(href) && !VisitedUrlQueue.isContains(href)) {  
                    UrlQueue.addElem(href);  
                }  
            }  
        }  
  
        System.out.println(UrlQueue.size() + "--抓取到的连接数");  
        System.out.println(VisitedUrlQueue.size() + "--已处理的页面数");  
        System.out.println("Thread ID--"+Thread.currentThread().getId());
  
    }  
  
}  
