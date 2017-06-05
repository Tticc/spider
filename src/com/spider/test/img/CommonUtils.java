package com.spider.test.img;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;

public class CommonUtils {

	
	/**
	 * get completed img url list by html content.
	 * @param content
	 * @param oriUrl
	 * @return final img url list. type:LinkedList<String>
	 */
	public static LinkedList<String> getImgUrlFromContent(String content,String oriUrl){
		LinkedList<String> finalUrlList = new LinkedList<String>();
		String title = getTitle(content);
		//check the img url is surrounding by " or ' .
		boolean isSingleQuoteMark = false;
        String[] contents = content.split("<img"); 
        for(int i = 0; i < contents.length; i++){
			int length = contents[i].indexOf(">");
			contents[i] = contents[i].substring(0,length);
		}
        for(String url : contents){
        	//System.out.println(url);
        	int start = url.indexOf("src");
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
        	String finalUrl = title + "?" + getFinalURL(url.substring(0,end),oriUrl);
        	
        	finalUrlList.add(finalUrl);
        }
        return finalUrlList;
        
        //how to use regexp to get img url?
		/*String IMGURL_REG = "<img(.*src=.*)/>";
		Matcher matcher=Pattern.compile(IMGURL_REG).matcher(content);
		List<String> listimgurl=new ArrayList<String>();
		while (matcher.find()){
			listimgurl.add(matcher.group());
		}
		System.out.println(listimgurl.size());
		for(String url : listimgurl){
			System.out.println(url);
		}*/
	}
	/**
	 * 
	 * get final url. the param "href" is the href need to be modified, and the param "requestUrl" is the request url which get content.
	 * 
	 * @param href
	 * @param requestUrl
	 * @return final url. type:String
	 */
	public static String getFinalURL(String href, String requestUrl) {  
		
        /* 内外部链接最终转化为完整的链接格式 */
        String finalHref = "";
  
        /* 判断是否为外部链接 */
        if (href.startsWith("http://") || href.startsWith("https://")) {
        	finalHref = href;
        } else {
            /* 如果是内部链接,则补充完整的链接地址,其他的格式忽略不处理,如：a href="#" */  
			
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
				finalHref = domain + href;
            }else{
				int splittoken = tempURL.lastIndexOf("/");
				if(splittoken<0){
					domain = requestUrl;
				}else{
					domain = requestUrl.substring(0, splittoken + 1 + protocolLength);
				}
				finalHref = domain + href;  
            }
        }
        return finalHref;
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
		sb.append(folderName);
		File dir = new File(sb.toString());
		if(dir.exists()){
			//System.out.println("dir exists. --" + sb.toString());
		}else{
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
	 * use the completed url(imgUrl) to download the img, and save them into "path"
	 * @param path
	 * @param imgUrl
	 */
	public static void downloadImg(String path, String imgUrl){
		try{
			System.out.println("downloadImg start!");
			String imgName = imgUrl.substring(imgUrl.lastIndexOf("/")+1, imgUrl.length());
			URL uri = new URL(imgUrl);
			InputStream in = uri.openStream();  
		
			/*String path = createDir(imgUrl);
			if(path == null){
				System.out.println("error could not create target diratory!!");
				throw new Exception();
			}*/
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
	 * read a txt file into String.
	 * @param file
	 * @return a String. type:String
	 */
	public static String txt2String(File file){
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
	/**
	 * get the title of the page
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
