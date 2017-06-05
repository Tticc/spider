package com.spider.test;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;



class WriteURLToFile implements Runnable{

	@Override
	public void run() {
		while (!Tools.isEmpty()) {
			try {
				writeFile();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	//dataHanding(Tools.outElem());
			try {
				Thread.sleep(1000*60*30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
        }
	}
	public void writeFile() throws Exception{
		//String str = new String(); //原有txt内容  
        StringBuilder s1 = new StringBuilder();//内容更新  
		String filePath = "D:" + File.separator +"spider" + File.separator + "URL.txt";
		File f = new File(filePath);  
        if (f.exists()) {  
            System.out.println("文件存在--WriteURLToFile");  
        } else {  
            System.out.println("文件不存在--WriteURLToFile");  
            try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 不存在则创建  
        }
        
        //read original text - start
        /*BufferedReader input = new BufferedReader(new FileReader(f));  
        while ((str = input.readLine()) != null) {  
            s1 += str + "\n";  
        }  
        System.out.println(s1);  
        input.close();  */
        //read original text - end
        //s1.append("total size : " + (Tools.urlQueue.size() + Tools.visitedUrlQueue.size())).append("\r\n");
        s1.append("total size : " + (Tools.visitedUrlQueue.size())).append("\r\n");
        for(int i = 0; i < TestDemo1.ths.size(); i++){
        	TestDemo1.ths.get(i).setInterruptIt(true);
        }
        Thread.sleep(1000*60);
        /*if(Tools.urlQueue.size() > 0){
	        
	        for(String str : Tools.urlQueue){
	    		s1.append(str).append("\r\n");
	    	}
        }*/
	    if(Tools.visitedUrlQueue.size()>0){
	    	//Iterator<String> it = Tools.visitedUrlQueue.iterator();
	    	for(String str : Tools.visitedUrlQueue){
	    		s1.append(str).append("\r\n");
	    	}
	    }

        for(int i = 0; i < TestDemo1.ths.size(); i++){
        	TestDemo1.ths.get(i).setInterruptIt(false);
        	TestDemo1.ths.get(i).notifyThread();
        }
        BufferedWriter output = new BufferedWriter(new FileWriter(f,false));  
        try{
	        output.write(s1.toString());  
	        
        }catch(Exception ex){
        	ex.printStackTrace();
        }finally{
        	output.close();
        }
	}

}


class UrlDataHanding extends Thread {  
	boolean interruptIt = false;
	public void setInterruptIt(boolean interruptIt){
		this.interruptIt = interruptIt;
	}
	public void pauseThread(){
		synchronized(this){
			if(interruptIt){
				try{
					System.out.println(Thread.currentThread().getId()+"--before sleep!");
					wait();
					System.out.println(Thread.currentThread().getId()+"--after sleep!");
				}catch(InterruptedException e){
					System.out.println("---------------------exception in wait--------------");
					e.printStackTrace();
				}
			}
		}
	}
	public void notifyThread(){
		synchronized(this){
			try{
				System.out.println(Thread.currentThread().getId()+"--before wakeup!");
				notify();
				System.out.println(Thread.currentThread().getId()+"--after wakeup!");
			}catch(Exception ex){
				System.out.println("---------------------exception in notify--------------");
				ex.printStackTrace();
			}
		}
	}
    /** 
     * 下载对应页面并分析出页面对应的URL放在未访问队列中。 12,15,16,21,23,24,25,26,30,31,37,39,42,44,45,46,47,49
     *  
     * @param url 
     */  
    public void dataHanding(String url) {  
    	try{
			String pageContent = Tools.getContentFormUrl(url);
			Tools.getHrefOfContent(pageContent, url);
			pauseThread();
		}catch(java.lang.IllegalArgumentException ex){
			ex.printStackTrace();
			System.out.println("IllegalArgumentException");
		}catch(Exception ex2){
			ex2.printStackTrace();
			System.out.println("done!-exception at dataHanding()");				
		} 
    }  
  
    public void run() {  
    	System.out.println("Thread ID--"+Thread.currentThread().getId());
        while (!Tools.isEmpty()) {  
        	try{
        		dataHanding(Tools.outElem());
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
        	
        }  
    }  
}  

public class TestDemo1 { 
	public static ArrayList<UrlDataHanding> ths = new ArrayList<UrlDataHanding>();
	public static void main(String[] args) throws InterruptedException {  
		String url = "https://www.hao123.com/";//"http://13.76.185.51:8080/Medical/jsp/frontPage/index.jsp";//"http://13.76.191.189:8080/gd";//"https://www.baidu.com";

		Tools.addElem(url);

		//write url into the txt file
		UrlDataHanding[] url_Handings = new UrlDataHanding[40];
		
		for (int i = 0; i < url_Handings.length; i++) { 
            url_Handings[i] = new UrlDataHanding();  
            
			ths.add(url_Handings[i]);
            //new Thread(url_Handings[i]).start();  
            ths.get(i).start();
			Thread.sleep(4000);
        }
		//check if all threads alive
		new Thread(new WriteURLToFile()).start();
		new CheckIsThreadAlive().start();
		Thread.sleep(1000*60*60);
		for(int i = 0; i < url_Handings.length; i++) { 
			ths.get(i).interrupt();
		}
		
	}
  
}
class Tools{
	//url list section
		public static LinkedList<String> urlQueue = new LinkedList<String>();

		
		
		//urlQueue = Collections.synchronizedCollection(urlQueue);
		//urlQueue = 
		public synchronized static void addElem(String url){
			
			urlQueue.add(url);
		}
		public synchronized static String outElem(){
			return urlQueue.removeFirst();
		}
		public synchronized static boolean isEmpty() {
			return urlQueue.isEmpty();
		}
		public static int size() {
			return urlQueue.size();
		}
		public static boolean isContains(String url) {
			return urlQueue.contains(url);
		}
		
		public static HashSet<String> visitedUrlQueue = new HashSet<String>();
		public synchronized static void addElemToVisited(String url) {
			visitedUrlQueue.add(url);
		}
		public synchronized static boolean isContainsInVisited(String url) {
			return visitedUrlQueue.contains(url);
		}
		public synchronized static int visitedSize() {
			return visitedUrlQueue.size();
		}
		
		
		//get page content
		public static String getContentFormUrl(String url) {  
	        /* 实例化一个HttpClient客户端 */  
	        HttpClient client = new DefaultHttpClient();  
	        HttpGet getHttp = new HttpGet(url);  
	  
	        //HttpPost pset = new HttpPost(url);
	        
	        String content = null;  
	  
	        HttpResponse response;  
	        try {  
	            /* 获得信息载体 */  
	            response = client.execute(getHttp);  
	            HttpEntity entity = response.getEntity();  
	  
	            addElemToVisited(url);  
	          //control the number of the urlQueue
    			/*if(Tools.visitedSize()>300000){
    				Tools.visitedUrlQueue.re
    			}*/
	            if (entity != null) {  
	                /* 转化为文本信息 */  
	                content = EntityUtils.toString(entity);  
	  
	                /* 判断是否符合下载网页源代码到本地的条件 */  
	                //if (FunctionUtils.isCreateFile(url) && FunctionUtils.isHasGoalContent(content) != -1) 
	                if (true) 
	                {  
	                    //FunctionUtils.createFile(FunctionUtils.getGoalContent(content), url);  
	                	//FunctionUtils.createFile(content, url);  
	                }  
	            }  
	  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            client.getConnectionManager().shutdown();  
	        }  
	  
	        return content;  
	    }  
		//get href in content
		public static void getHrefOfContent(String content, String oriUrl) {  
	        System.out.println("开始");  
	        String[] contents = content.split("<a href=\"");  
	        String[] allHref = new String[contents.length];
	        
	        for (int i = 1; i < contents.length; i++) {  
	        	allHref[i] = "";
	            int endHref = contents[i].indexOf("\"");  
	  
	            String aHref = getHrefOfInOut(contents[i].substring(0, endHref),oriUrl);
	            
	            allHref[i] = aHref;
	            if (aHref != null) {
	                String href = getHrefOfInOut(aHref,oriUrl);
	  
	                //if (!UrlQueue.isContains(href) && href.indexOf("/code/explore") != -1 && !VisitedUrlQueue.isContains(href)) {
	                if (!isContains(href) && !isContainsInVisited(href)) {
	                    addElem(href);

	                    //control the number of the urlQueue
	        			if(Tools.size()>100000){
	        				//Tools.urlQueue.removeFirst();
	        				Tools.outElem();
	        			}
	                }
	            }
	        }
	  
	        System.out.println(size() + "--抓取到的连接数");  
	        System.out.println(visitedSize() + "--已处理的页面数");  
	        System.out.println("Thread ID--"+Thread.currentThread().getId());
	  
	    }  
		public static String getHrefOfInOut(String href, String oriUrl) {  
			
	        /* 内外部链接最终转化为完整的链接格式 */  
	        String resultHref = null;  
	  
	        /* 判断是否为外部链接 */  
	        if (href.startsWith("http://") || href.startsWith("https://")) {  
	            resultHref = href;  
	        } else {  
	            /* 如果是内部链接,则补充完整的链接地址,其他的格式忽略不处理,如：a href="#" */  
				
				String tempURL = "";
				String domain = "";
				int protocolLength = 0;
				if(oriUrl.startsWith("http://")){
					tempURL = oriUrl.replace("http://","");
					protocolLength = 7;
				}
				if(oriUrl.startsWith("https://")){
					tempURL = oriUrl.replace("https://","");
					protocolLength = 8;
				}
				
	            if (href.startsWith("/")) {  
	                //resultHref = "http://13.76.191.189:8080" + href;  
	            	/*if(href.startsWith("/Medical")){
	            		resultHref = "http://13.76.185.51:8080" + href;  
	            	}else{
	            		resultHref = "http://13.76.185.51:8080/Medical/jsp/frontPage" + href;
	            	}*/
					
					
					int splittoken = tempURL.indexOf("/");
					if(splittoken<0){
						domain = oriUrl;
					}else{
						domain = oriUrl.substring(0, splittoken + protocolLength);
					}
	            	resultHref = domain + href;
	            }else{
					int splittoken = tempURL.lastIndexOf("/");
					if(splittoken<0){
						domain = oriUrl;
					}else{
						domain = oriUrl.substring(0, splittoken + 1 + protocolLength);
					}
	            	resultHref = domain + href;  
	            }
	        }
	  
	        return resultHref;  
			
			
			
	        /* 内外部链接最终转化为完整的链接格式   
	        String resultHref = null;  
	  
	         判断是否为外部链接   
	        if (href.startsWith("http://") || href.startsWith("https://")) {  
	            resultHref = href;  
	        } else {  
	             如果是内部链接,则补充完整的链接地址,其他的格式忽略不处理,如：a href="#"   
	            if (href.startsWith("/")) {  
	                //resultHref = "http://13.76.191.189:8080" + href;  
	            	if(href.startsWith("/Medical")){
	            		resultHref = "http://13.76.185.51:8080" + href;  
	            	}else{
	            		resultHref = "http://13.76.185.51:8080/Medical/jsp/frontPage" + href;
	            	}
	            	resultHref = "http://13.76.185.51:8080/Medical/jsp/frontPage" + href;
	            }else{
	            	resultHref = "http://13.76.185.51:8080/Medical/jsp/frontPage/" + href;  
	            }
	        }  
	  
	        return resultHref;  */
	    }
		
		
		//create file
		public static void createFile(String content, String urlPath) {  
			BufferedWriter writer = null;
			
	        /* 分割url */  
	        String[] elems = urlPath.split("/");;  
	        StringBuffer path = new StringBuffer();  
	  
	        File file = null;  
	        for (int i = 1; i < elems.length; i++) {  
	        	//create folders
	            if (i != elems.length - 1) {  
	            	elems[i] = elems[i].replace(":", "_");
	                path.append(elems[i]);  
	                path.append(File.separator);  
	                file = new File("D:" + File.separator +"spider" + File.separator + path.toString());  
	  
	            }  
	            //create files
	            if (i == elems.length - 1) {  
	                //Pattern pattern = Pattern.compile("\\w+");  
	                Pattern pattern = Pattern.compile(".*");
	                Matcher matcher = pattern.matcher(elems[i]);  
	                if ((matcher.matches())) {  
	                //if(true){
	                    if (!file.exists()) {  
	                        file.mkdirs();  
	                    }
	                    String[] fileName = elems[i].split("\\.");  
	                    file = new File("D:" + File.separator +"spider" + File.separator + path.toString()
	                            + File.separator + fileName[0] + ".html");
	                    try {
	                        file.createNewFile();
	                        writer = new BufferedWriter(new OutputStreamWriter(
	                                new FileOutputStream(file)));
	                        writer.write(content);
	                        writer.flush();
	                        writer.close();
	                        System.out.println("创建文件成功");
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }
}


class CheckIsThreadAlive extends Thread{
	@Override
	public void run() {
		while(true){
			try{
				writeFile();
				try {
					Thread.sleep(1000*60*30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	public void writeFile() throws IOException{
        StringBuilder s1 = new StringBuilder();
		String filePath = "D:" + File.separator +"spider" + File.separator + "threadStatus.txt";
		File f = new File(filePath);  
        if (f.exists()) {  
            System.out.println("文件存在--CheckIsThreadAlive");  
        } else {  
            System.out.println("文件不存在--CheckIsThreadAlive");  
            try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        /*s1.append(txt2String(f));
        s1.append("\r\n\r\n\r\n");*/
        for(int i = 0; i < TestDemo1.ths.size(); i++){
        	s1.append("Thread "+TestDemo1.ths.get(i).getId()+" is alive? "+TestDemo1.ths.get(i).isAlive()+" and isInterrupted? "+TestDemo1.ths.get(i).isInterrupted()+"\r\n");
        	
        }
        /*System.out.println("--------------------------------------------------");  
        System.out.println(s1.toString());  
        System.out.println("--------------------------------------------------");  */
        BufferedWriter output = new BufferedWriter(new FileWriter(f,false));  
        try{
	        output.write(s1.toString());
        }catch(Exception ex){
        	ex.printStackTrace();
        }finally{
        	output.close();
        }
	}
	public String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(s+"\r\n");
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
