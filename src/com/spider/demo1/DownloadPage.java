package com.spider.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.util.EntityUtils;

import com.spider.temp.HttpsClient;  
  


public class DownloadPage {  
  
    /** 
     * 根据URL抓取网页内容 
     *  
     * @param url 
     * @return 
     * @throws IOException 
     */  
    @SuppressWarnings({ "resource"})
	public static String getContentFormUrl(String url) throws IOException {  
        /* 实例化一个HttpClient客户端 */  
        HttpClient client = new DefaultHttpClient();  
        HttpGet getHttp = new HttpGet(url);  
        if(url.startsWith("https")){
        	client = HttpsClient.getNewHttpsClient(client);
        }else{/*
            	URL uri = new URL(url);
    			//start
    			URLConnection urlcon = uri.openConnection();			
    			//将爬虫连接伪装成浏览器连接
    			urlcon.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
    			urlcon.connect();
    			InputStream in = urlcon.getInputStream();
    			Reader reader = new InputStreamReader(in,"utf-8");
    			char[] buf = new char[1024];
    			StringBuffer sb = new StringBuffer();
    			int length = 0;
    			while ((length = reader.read(buf,0,buf.length)) != -1) {
                	sb.append(new String(buf,0,length));
                }
    			//new String(new byte[10],1,10,Charset.forName("utf-8"));
    			System.out.println(sb.toString());
    			System.out.println("-----------------------end------------------");
    			*/
        }
        String content = null;  
  
        HttpResponse response = null;  
        VisitedUrlQueue.addElem(url);  
        try {  
            /* 获得信息载体 */  
        	try{
        		response = client.execute(getHttp); 
        	}catch(java.net.ConnectException ce){
        		ce.printStackTrace();
        		URL uri = new URL(url);
    			//start
    			URLConnection urlcon = uri.openConnection();			
    			//将爬虫连接伪装成浏览器连接
    			urlcon.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
    			urlcon.connect();
    			InputStream in = urlcon.getInputStream();
    			Reader reader = new InputStreamReader(in,"utf-8");
    			char[] buf = new char[1024];
    			StringBuffer sb = new StringBuffer();
    			int length = 0;
    			while ((length = reader.read(buf,0,buf.length)) != -1) {
                	sb.append(new String(buf,0,length));
                }
    			return sb.toString();
        	}
            HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                /* 转化为文本信息 */  
                content = EntityUtils.toString(entity);  
                //System.out.println(content);
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
  
}  
