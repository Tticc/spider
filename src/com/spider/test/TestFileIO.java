package com.spider.test;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.spider.myutil.*;

public class TestFileIO {
	public static Set<String> finalSet = new HashSet<String>();
	public static Set<String> midSet = new HashSet<String>();
	public static Map<String,Integer> finalMap = new HashMap<String,Integer>();
	public static void main(String[] args){
		TestFileIO ins = new TestFileIO();
		String[] beSearched = ins.getBeSearched();
		int[] beSearchedNum = ins.getBeSearchedNum();
		String[] jobTitle = ins.getJobTitle();
		String[] jobCodes = ins.getJobCodes();
		String[] jobCostLevel = ins.getJobCostLevel();
		String[] jobHP = ins.getJobHP();
		String[] special = ins.getSpetial();
		String[] verySpecial = ins.getVerySpecial();
		ins.getSpecial(beSearched,beSearchedNum,special);
		//System.out.println(finalSet.size());
		/*for(Map.Entry<String, Integer> me : finalMap.entrySet()){
			System.out.println("K: "+me.getKey()+",V: "+me.getValue());
    	}*/
		//System.out.println("**********************************************");
		ins.getVerySpecial(beSearched,beSearchedNum,verySpecial);
		/*for(Map.Entry<String, Integer> me : finalMap.entrySet()){
			System.out.println("K: "+me.getKey()+",V: "+me.getValue());
    	}*/
		ins.getNormal(beSearched);
		ins.getFinalAll2Sub(beSearched, beSearchedNum, jobTitle);
		//System.out.println(finalMap.size());
		/*for(Map.Entry<String, Integer> me : finalMap.entrySet()){
			System.out.println(me.getKey()+"\t"+me.getValue());
    	}*/
		ins.getFinalAll2(jobTitle, jobCodes, jobCostLevel, jobHP);
		//System.out.println(finalSet.size());
		//ins.getNormal(beSearched);
		//ins.getFinalAll(jobTitle, jobCodes, jobCostLevel, jobHP);
		//System.out.println(finalSet.size());
		
		
		//System.out.println(jjj);
		//for(int i = 0; i < jobTitle.length; i++){
			//System.out.println(jobTitle[i]);
		//}
		/*System.out.println(jobTitle[800]);
		System.out.println(jobTitle[801]);
		System.out.println(jobTitle[802]);
		System.out.println(jobTitle[803]);
		System.out.println(jobTitle[804]);
		System.out.println(jobTitle[805]);*/
		//System.out.println(beSearched.length);
		//System.out.println(beSearchedNum.length);
//		System.out.println(jobTitle.length);
//		System.out.println(jobCodes.length);
//		System.out.println(jobCostLevel.length);
//		System.out.println(jobHP.length);
//		System.out.println(special.length);
//		System.out.println(verySpecial.length);
	}
	public void getNormal(String[] beSearched){
		for(int i = 0; i < beSearched.length; i++){
			int end = beSearched[i].indexOf(" - ") == -1 ? beSearched[i].length() : beSearched[i].indexOf(" - ");
			beSearched[i] = beSearched[i].substring(0, end);
			int start = beSearched[i].lastIndexOf(")") == -1 ? 0 : beSearched[i].lastIndexOf(")",beSearched[i].length()-1);
			/*if(start == beSearched[i].length()){
				beSearched[i].l
			}*/
			beSearched[i] = beSearched[i].substring(start+1);
			finalSet.add(beSearched[i]);
		}
	}
	public void getFinalAll(String[] jobTitle,String[] jobCodes,String[] jobCostLevel,String[] jobHP){
		System.out.println("ESMasterJobTitles\tESMasterJobCodes\tESMasterJobCostLevels\tMasterJobHP TextLevels");
		for(Iterator<String> it = finalSet.iterator();it.hasNext();){
			
			String iis = it.next();
			
			for(int i = 0; i < jobTitle.length; i++){
				if(iis.equals(jobTitle[i])){
					System.out.println(jobTitle[i] + "\t" + jobCodes[i] + "\t" + jobCostLevel[i]+ "\t" + jobHP[i]);
				}
				if(iis.equals(" "+jobTitle[i])){
					System.out.println(jobTitle[i] + "\t" + jobCodes[i] + "\t" + jobCostLevel[i]+ "\t" + jobHP[i]);
				}
			}
		}
	}
	public void getFinalAll2(String[] jobTitle,String[] jobCodes,String[] jobCostLevel,String[] jobHP){
		System.out.println("ESMasterJobTitles\tESMasterJobCodes\tESMasterJobCostLevels\tMasterJobHP TextLevels\tCount");
		for(Map.Entry<String, Integer> me : finalMap.entrySet()){
			//System.out.println(me.getKey()+"\t"+me.getValue());
			for(int i = 0; i < jobTitle.length; i++){
				if(me.getKey().equals(jobTitle[i])){
					System.out.println(jobTitle[i] + "\t" + jobCodes[i] + "\t" + jobCostLevel[i]+ "\t" + jobHP[i]+"\t"+me.getValue());
					//break;
				}
			}
    	}
		
	}
	public void getFinalAll2Sub(String[] beSearched,int[] beSearchedNum, String[] jobTitle){
		
		for(int k = 0 ; k < beSearched.length ; k++){
			
			String iis = beSearched[k];
			
			for(int i = 0; i < jobTitle.length; i++){
				if(iis.equals(jobTitle[i]) || iis.equals(" "+jobTitle[i])|| 
						iis.equals(" "+jobTitle[i]+" ")|| iis.equals(jobTitle[i]+" ")){
					//System.out.println(jobTitle[i] + "\t" + jobCodes[i] + "\t" + jobCostLevel[i]+ "\t" + jobHP[i]);
					if(finalMap.containsKey(jobTitle[i])){
						finalMap.put(jobTitle[i], finalMap.get(jobTitle[i])+beSearchedNum[k]);
					}
					else{
						finalMap.put(jobTitle[i], beSearchedNum[k]);
					}
					beSearchedNum[k] = 0;
					beSearched[k] = "()000000000000000000";
				}
			}
		}
	}
	public void getSpecial(String[] beSearched,int[] beSearchedNum, String[] special){
		for(int i = 0; i < special.length; i++){
			for(int j = 0; j < beSearched.length; j++){
				if(beSearched[j].indexOf(special[i]) != -1){
//					System.out.println(special[i]);
//					System.out.println(beSearched[j]);
//					System.out.println(i+"--------------------------------------------");
					finalSet.add(special[i]);
					if(finalMap.containsKey(special[i])){
						finalMap.put(special[i], finalMap.get(special[i])+beSearchedNum[j]);
					}
					else{
						finalMap.put(special[i], beSearchedNum[j]);
					}
					beSearchedNum[j] = 0;
					beSearched[j] = "()000000000000000000";
					
				}
			}
		}
	}
	public void getVerySpecial(String[] beSearched, int[] beSearchedNum, String[] verySpecial){
		for(int i = 0; i < verySpecial.length; i++){
			for(int j = 0; j < beSearched.length; j++){
				if(beSearched[j].indexOf(verySpecial[i]) != -1){
					//System.out.println(verySpecial[i]);
					//System.out.println(beSearched[j]);
					//System.out.println(i+"--------------------------------------------");
					finalSet.add(verySpecial[i]);
					if(finalMap.containsKey(verySpecial[i])){
						finalMap.put(verySpecial[i], finalMap.get(verySpecial[i])+beSearchedNum[j]);
					}
					else{
						finalMap.put(verySpecial[i], beSearchedNum[j]);
					}
					beSearchedNum[j] = 0;
					beSearched[j] = "()000000000000000000";
					
				}
			}
		}
	}
	
	
	public String[] getBeSearched(){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "BeSearched.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		String[] arr = new String[list.size()];
		String[] beSearched = list.toArray(arr);
		return beSearched;
	}
	public int[] getBeSearchedNum(){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "BeSearchedNum.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		
		int[] beSearched = new int[list.size()];
		for(int i = 0; i < beSearched.length; i++){
			beSearched[i] = Integer.parseInt(list.get(i));
		}
		//Integer[] arr = new Integer[list.size()];
		//Integer[] beSearched = list.toArray(arr);
		return beSearched;
	}
	public String[] getJobTitle(){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "JobTitle.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		String[] arr = new String[list.size()];
		String[] jobTitle = list.toArray(arr);
		return jobTitle;
	}
	public String[] getJobCodes(){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "JobCodes.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		String[] arr = new String[list.size()];
		String[] jobCodes = list.toArray(arr);
		return jobCodes;
	}
	public String[] getJobCostLevel(){

		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "JobCostLevel.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		String[] arr = new String[list.size()];
		String[] jobCostLevel = list.toArray(arr);
		return jobCostLevel;
	}
	public String[] getJobHP(){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "JobHP.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		String[] arr = new String[list.size()];
		String[] jobHP = list.toArray(arr);
		return jobHP;
	}
	public String[] getSpetial(){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "spetial.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		String[] arr = new String[list.size()];
		String[] spetial = list.toArray(arr);
		return spetial;
	}
	public String[] getVerySpecial(){
		String filePath = "D:" + File.separator +"spider" + File.separator +"log"+ File.separator  + "verySpecial.txt";
		File f = new File(filePath);
		List<String> list = FileIO.readFileToList(f);
		String[] arr = new String[list.size()];
		String[] verySpecial = list.toArray(arr);
		return verySpecial;
	}
}
