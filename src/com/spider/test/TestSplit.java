package com.spider.test;

public class TestSplit {

	public static void main(String[] args){
		String url = "https://www.baidu.com/e3t/srml/index.jsp";

		System.out.println("the url is : " + url);

		System.out.println("url.indexOf(\"/\") before replace : " + url.indexOf("/"));
		String tempUrl = url.replace("https://", "");
		int mis = 8;
		System.out.println("tempURL is : " + tempUrl);
		int first = tempUrl.indexOf("/");
		System.out.println("tempUrl.indexOf(\"/\") after replace(first) : " + first);
		int last = tempUrl.lastIndexOf("/");
		System.out.println("tempUrl.indexOf(\"/\") after replace(last) : " + last);
		
		System.out.println("substring(0-first) : " + url.substring(0, first+mis));
		System.out.println("substring(0-last) : " + url.substring(0, last+1+mis));
	}
}
