package com.spider.temp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.spider.myutil.FileIO;

public class FindLink {
	public static final String rootPath = "https://www.csdn.net/";//
	public static final String target = "";
	//download page in the specific path, it depend what url will put into "urlQueue"
	public static final String specificPath = "https://www.csdn.net/";//
	public static final String specificPath2 = "https://www.csdn.net/";
	public static final String charset = "utf-8";
	
	public static ArrayList<UrlDataHandingFind> ths = new ArrayList<UrlDataHandingFind>();
	public static void main(String[] args) throws InterruptedException {
		String url = rootPath;//"http://www.baidu.com/";////"http://13.76.185.51:8080/Medical/jsp/frontPage/index.jsp";
		ToolsFind.addElem(url);
		int UrlDataHandingThreadSize = 10;
		for (int i = 0; i < UrlDataHandingThreadSize; i++) {             
			ths.add(new UrlDataHandingFind());
            ths.get(i).start();
			Thread.sleep(4000);
        }
		Thread.sleep(1000*60*1);
		StringBuilder sb = new StringBuilder();
		for(String keys : ToolsFind.targetLinkAndTitle.keySet()){
			sb.append(keys+"\t");
			sb.append(ToolsFind.targetLinkAndTitle.get(keys)+"\r\n");
		}

		StringBuilder sbv = new StringBuilder();
		for(String link : ToolsFind.visitedUrlQueue){
			//System.out.println(link);
			sbv.append(link+"\r\n");
		}
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "targetUrlMap.txt";
		File f = new File(filePath);
		String filePathv = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "visitedUrl.txt";
		File fv = new File(filePathv);
		FileIO.newWriteFileWithString(f,sb.toString());
		FileIO.newWriteFileWithString(fv,sbv.toString());
	}

}
class UrlDataHandingFind extends Thread {  
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
	 * get page content, all page url into list, add img url into list.
	 * @param url
	 */
    public void dataHanding(String url) {  
    	try{
			String pageContent = ToolsFind.getContentFromUrl(url);
			String title = ToolsFind.getTitle(pageContent);
			if(!"".equals(title) && title.indexOf(FindLink.target) > -1){
				//ToolsFind.addElemToTarget(url);
				ToolsFind.addTLT(url,title);
			}
			//ToolsFind.addALinkedList(ToolsFind.getImgUrlFromContent(pageContent, url));
			ToolsFind.getHrefOfContent(pageContent, url);
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
        while (!ToolsFind.isEmpty()) {  
        	try{
        		dataHanding(ToolsFind.outElem());
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
        	
        }  
    }  
}  

/**
 * ToolsFind class
 * @author wenc
 *
 */
class ToolsFind{
		//download page in the specific path, it depend what url will put into "urlQueue"
		public static final String specificPath = FindLink.specificPath;//"http://www.baidu.com/";//
		//unvisited page url
		public static LinkedList<String> urlQueue = new LinkedList<String>();
		//unvisited imp url
		public static LinkedList<String> imgUrl = new LinkedList<String>();
		//visited page url
		public static HashSet<String> visitedUrlQueue = new HashSet<String>();

		//target URL
		public static ArrayList<String> targetLink = new ArrayList<String>();
		
		//target url&title
		public static HashMap<String,String> targetLinkAndTitle = new HashMap<String,String>();
		public synchronized static void addTLT(String url, String title){
			if(!targetLinkAndTitle.containsKey(url)){
				targetLinkAndTitle.put(url, title);
			}
			
		}
		
		
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
		
		
		public synchronized static void addElemToImgUrl(String url){
			imgUrl.add(url);
		}
		public synchronized static String outElemFromImgUrl(){
			return imgUrl.removeFirst();
		}
		public synchronized static boolean isImgUrlEmpty() {
			return imgUrl.isEmpty();
		}
		public synchronized static void addALinkedList(LinkedList<String> list){
			imgUrl.addAll(list);
		}
		public synchronized static int imgUrlSize(){
			return imgUrl.size();
		}
		
		
		public synchronized static void addElemToVisited(String url) {
			visitedUrlQueue.add(url);
		}
		public synchronized static boolean isContainsInVisited(String url) {
			return visitedUrlQueue.contains(url);
		}
		public synchronized static int visitedSize() {
			return visitedUrlQueue.size();
		}
		
		public synchronized static void addElemToTarget(String url) {
			targetLink.add(url);
		}
		public synchronized static boolean isContainsInTarget(String url) {
			return targetLink.contains(url);
		}
		public synchronized static int targetSize() {
			return targetLink.size();
		}
		
		
		/**
		 * get page content by completed url
		 * @param url
		 * @return page content. type:String
		 * @throws IOException
		 */
		public static String getContentFromUrl(String url) throws IOException {
		        StringBuilder result = new StringBuilder();
		        BufferedReader in = null ;
		        try {
		        	addElemToVisited(url);
		        	InputStream inS; 
		        	if(url.startsWith("https://")){
						//new
						HttpClient httpClient = new DefaultHttpClient();
						httpClient = HttpsClient.getNewHttpsClient(httpClient);
						HttpGet httpGet = new HttpGet(url);
						HttpResponse response = httpClient.execute(httpGet);
						HttpEntity entity = response.getEntity();
						inS = entity.getContent();
					}else{
						URL uri = new URL(url);
						//start
						URLConnection urlcon = uri.openConnection();
						
						//将爬虫连接伪装成浏览器连接
						urlcon.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
						//HttpURLConnection httpc = (HttpURLConnection)urlcon;
						urlcon.connect();
						
						inS = urlcon.getInputStream();
						
						//end
						
						//in = uri.openStream();
					}
		            //URL realUrl = new URL(url);
		            //URLConnection connection = realUrl.openConnection();
		            //将爬虫连接伪装成浏览器连接
		            
		            
		            //InputStream urlStream = connection.getInputStream();  
		            in = new BufferedReader(new InputStreamReader(inS,FindLink.charset));  
		            String line = "";  
		            while ((line = in.readLine()) != null) {
		                result.append(line);
		            }
		 
		        } catch (MalformedURLException e) {
		            System.out.print("发送GET请求出现异常！" + e);
		            e.printStackTrace();
		        } finally {
		            try {
		                if (in != null) {
		                    in.close();
		                }
		            } catch (Exception e2) {
		                e2.printStackTrace();
		            }
		        }
		 
		        return result.toString();
	    }  
		/**
		 * get url from content and supplement them. and oriUrl into visitedUrlQueue and add new url into urlQueue
		 * @param content
		 * @param oriUrl
		 */
		public static void getHrefOfContent(String content, String oriUrl) {
	        System.out.println("开始");
	        String[] contents = content.split("<a href=\"");
	        
	        for (int i = 1; i < contents.length; i++) {
	            int endHref = contents[i].indexOf("\"");
	            
	            String finalUrl = getPageFinalUrl(contents[i].substring(0, endHref),oriUrl);	            
	            if (finalUrl != null) {
	                if (!isContains(finalUrl) && !isContainsInVisited(finalUrl)) {
	                    addElem(finalUrl);
	        			if(ToolsFind.size()>100000){
	        				ToolsFind.outElem();
	        			}
	                }
	            }
	        }
	        urlQueue.size();
	        System.out.println(size() + "--抓取到的连接数");  
	        System.out.println(visitedSize() + "--已处理的页面数");  
	        System.out.println("Thread ID--"+Thread.currentThread().getId());
	  
	    }
		/**
		 * get page final url by url from page content and oriUrl. And check if outside url match specificPath. 
		 * @param href
		 * @param oriUrl
		 * @return final url. type:String
		 */
		public static String getPageFinalUrl(String href, String oriUrl) {
	        	String finalUrl = null;
	        	//check if outside url and match specificPath.
		        if (href.startsWith("http://") || href.startsWith("https://")) {
		        	if(href.startsWith(specificPath)){
		        		finalUrl = href;
		        	}
		        } else {
		        	//supplement url according to oriUrl
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
						int splittoken = tempURL.indexOf("/");
						if(splittoken<0){
							domain = oriUrl;
						}else{
							domain = oriUrl.substring(0, splittoken + protocolLength);
						}
						finalUrl = domain + href;
		            }else{
						int splittoken = tempURL.lastIndexOf("/");
						if(splittoken<0){
							domain = oriUrl;
						}else{
							domain = oriUrl.substring(0, splittoken + 1 + protocolLength);
						}
						finalUrl = domain + href;  
		            }
		            if(!finalUrl.startsWith(specificPath)){
		            	finalUrl = null;
		            }
		        }
	        return finalUrl;
	    }

		
		/**
		 * use the completed url(imgUrl) to download the img, and save them into "path"
		 * @param path
		 * @param imgUrl
		 */
		public static void downloadImg(String path, String imgUrl,int count){
			int splitPosition = imgUrl.indexOf("?http");			
			if(splitPosition >= 0 ){
				imgUrl = imgUrl.substring(splitPosition+1, imgUrl.length());
			}
			try{
					System.out.println("downloadImg start!");
					String imgName = imgUrl.substring(imgUrl.lastIndexOf("/")+1, imgUrl.length());
					imgName = count+".jpg";
					InputStream in = null;
					//some image url are wrong
					//imgUrl = imgUrl.replace("http://", "https://");
					if(imgUrl.startsWith("https://")){
						//new
						HttpClient httpClient = new DefaultHttpClient();
						httpClient = HttpsClient.getNewHttpsClient(httpClient);
						HttpGet httpGet = new HttpGet(imgUrl);
						HttpResponse response = httpClient.execute(httpGet);
						HttpEntity entity = response.getEntity();
						in = entity.getContent();
					}
					else{
						URL uri = new URL(imgUrl);
						//start
						URLConnection urlcon = uri.openConnection();
						
						//将爬虫连接伪装成浏览器连接
						urlcon.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
						//HttpURLConnection httpc = (HttpURLConnection)urlcon;
						urlcon.connect();
						
						in = urlcon.getInputStream();
						
						//end
						
						//in = uri.openStream();
					}
					FileOutputStream fo = new FileOutputStream(new File(path + File.separator + imgName));
		            byte[] buf = new byte[1024];  
		            int length = 0;  
		            while ((length = in.read(buf, 0, buf.length)) != -1) {
		            	fo.write(buf, 0, length);
		            }  
		            in.close();
		            fo.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				System.out.println("downloadImg end!");
			}
		}
		
		
		/**
		 * According to the url, Creating all needed folders. Attention! the url pattent is : "xxx?http.....", so need to remove "xxx?"
		 * @param url
		 * @return the folder path.type:String
		 */
		public static String createDir(String url){
			int splitPosition = url.indexOf("?http");
			String folderName = "";
			if(splitPosition >= 0 ){
				folderName = url.substring(0,splitPosition);
				url = url.substring(splitPosition+1, url.length());
			}
			if(url.startsWith("https://")){
				url = url.replace("https://", "");				
			}else if(url.startsWith("http://")){
				url = url.replace("http://", "");	
			}
			url = url.replace(":", "_");
			String[] section = url.split("/");
			StringBuilder sb = new StringBuilder();
			sb.append("D:" + File.separator +"spider" + File.separator + "img");
			for(int i = 0; i < section.length-1; i++){
				sb.append(File.separator);
				sb.append(section[i]);				
			}
			//sb.append("\\new1");
			sb.append(folderName);
			File dir = new File(sb.toString());
			if(!dir.exists()){
				if(dir.mkdirs()){
					System.out.println("dir create success. --" + sb.toString());
				}else{
					System.out.println("dir create failed! --" + sb.toString());
					return null;
				}
			}
			return sb.toString();
		}
		/**
		 * get completed img url list by html content.
		 * @param content
		 * @param oriUrl
		 * @return final img url list. type:LinkedList<String>
		 */
		public static LinkedList<String> getImgUrlFromContent(String content,String oriUrl){
			LinkedList<String> finalUrlList = new LinkedList<String>();
			String title = getTitle(content);
			//check the img url is surrounding by """ or "'" .
			boolean isSingleQuoteMark = false;
	        String[] contents = content.split("<img"); 
	        for(int i = 0; i < contents.length; i++){
				int length = contents[i].indexOf(">");
				contents[i] = contents[i].substring(0,length);
			}
	        for(String url : contents){
	        	int start = url.indexOf("src=");
	        	if(start<0){
	        		start = url.indexOf("src =");
	        		if(start < 0 ){
	        			continue;
	        		}
	        	}
	        	if(start<0){
	        		continue;
	        	}
	        	url = url.substring(start, url.length());
	        	int startOfDoubleQuote = url.indexOf("\"");
	        	int startOfSingleQuote = url.indexOf("'");
	        	if((startOfDoubleQuote==startOfSingleQuote)&&startOfSingleQuote==-1){
	        		continue;
	        	}
	        	if(startOfDoubleQuote<0){
	        		isSingleQuoteMark = true;
	        		start = startOfSingleQuote;
	        	}else if(startOfSingleQuote<0){
	        		isSingleQuoteMark = false;
	        		start = startOfDoubleQuote;
	        	}else if(startOfDoubleQuote > startOfSingleQuote){
	        		isSingleQuoteMark = true;
	        		start = startOfSingleQuote;
	        	}else{
	        		isSingleQuoteMark = false;
	        		start = startOfDoubleQuote;
	        	}
	        	url = url.substring(start+1, url.length());
	        	int end = isSingleQuoteMark ? url.indexOf("'") : url.indexOf("\"");
	        	String finalUrl = title + "?" + getImgFinalURL(url.substring(0,end),oriUrl);
	        	
	        	finalUrlList.add(finalUrl);
	        }
	        return finalUrlList;
		}
		/**
		 * 
		 * get img final url. the param "href" is the href need to be modified, and the param "requestUrl" is the request url which get content.
		 * 
		 * @param href
		 * @param requestUrl
		 * @return final url. type:String
		 */
		public static String getImgFinalURL(String href, String requestUrl) {  
			
	        /* 内外部链接最终转化为完整的链接格式 */
	        String finalUrl = "";
	  
	        //check if outside url and match specificPath.
	        if (href.startsWith("http://") || href.startsWith("https://")) {
	        	finalUrl = href;
	        } else {
	        	//supplement url according to oriUrl
				String tempURL = "";
				String domain = "";
				int protocolLength = 0;
				if(requestUrl.startsWith("http://")){
					tempURL = requestUrl.replace("http://","");
					protocolLength = 7;
				}
				if(requestUrl.startsWith("https://")){
					tempURL = requestUrl.replace("https://","");
					protocolLength = 8;
				}
				
	            if (href.startsWith("/")) {
					int splittoken = tempURL.indexOf("/");
					if(splittoken<0){
						domain = requestUrl;
					}else{
						domain = requestUrl.substring(0, splittoken + protocolLength);
					}
					finalUrl = domain + href;
	            }else{
					int splittoken = tempURL.lastIndexOf("/");
					if(splittoken<0){
						domain = requestUrl;
					}else{
						domain = requestUrl.substring(0, splittoken + 1 + protocolLength);
					}
					finalUrl = domain + href;  
	            }
	        }
	        return finalUrl;
	    }
		
		/**
		 * get the title of the page according page content
		 * @param content
		 * @return page title
		 */
		public static String getTitle(String content){
			int start = content.indexOf("<title");
			if(start < 0){
				return "ntitle";
			}
			content = content.substring(start, content.length());
			content = content.replace("<title", "");
			start = content.indexOf(">");
			content = content.substring(start+1, content.length());
			start = content.indexOf("<");
			content = content.substring(0,start);
			return content;
		}

}
