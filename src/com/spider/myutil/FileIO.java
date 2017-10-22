package com.spider.myutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

	public static void main(String[] args){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "spetial.txt";
		File f = new File(filePath);
		String content = readFile(f);
		System.out.println(content);
		content += "new append";
		//appendWriteFileWithString(f,content);
		//newWriteFileWithString(f,content);
	}
	/**
	 * read file and return a String, if return null, file not found
	 * @param file(File)
	 * @return String
	 */
	public static String readFile(File file){
		String str = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	    try {
			while ((str = input.readLine()) != null) {
				sb.append(str).append("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return sb.toString();
	}
	/**
	 * read file to String List
	 * @param file
	 * @return
	 */
	public static List<String> readFileToList(File file){
		String str = null;
		List<String> list = new ArrayList<String>();
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	    try {
			while ((str = input.readLine()) != null) {
				list.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	//param File, String
	/**
	 * append content into the specific file
	 * @param file
	 * @param content
	 */
	public static void appendWriteFileWithString(File file, String content){
		//StringBuilder s1 = new StringBuilder();
		//String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "log.txt";
		//File f = new File(filePath);
        
        if (!file.exists()) {
            System.out.println("file not exist");
            try {
            	System.out.println("creating file...");
            	file.createNewFile();
				System.out.println("file created. path:" + file.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file,true));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
        try{
	        output.write(content);
	        
        }catch(Exception ex){
        	ex.printStackTrace();
        }finally{
        	try{
				output.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
        }
	}
	/**
	 * replace original content with content in the specific file
	 * @param file
	 * @param content
	 */
	public static void newWriteFileWithString(File file, String content){
		//StringBuilder s1 = new StringBuilder();
				//String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "log.txt";
				//File f = new File(filePath);
		        
		        if (!file.exists()) {
		            System.out.println("file not exist");
		            try {
		            	System.out.println("creating file...");
		            	file.createNewFile();
						System.out.println("file created. path:" + file.getPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		        BufferedWriter output;
				try {
					output = new BufferedWriter(new FileWriter(file,false));
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
		        try{
			        output.write(content);
			        
		        }catch(Exception ex){
		        	ex.printStackTrace();
		        }finally{
		        	try{
						output.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
		        }
	}
}
