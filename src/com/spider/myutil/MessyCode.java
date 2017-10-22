package com.spider.myutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessyCode {
	public static void main(String[] args){
		String str = "��瀹跺琛ㄩ〉";
		System.out.println(isMessyCode(str));
	}
	public static boolean isMessyCode(String strName) {  
        Pattern p = Pattern.compile("\\d*\\s*\\t*\\r*\\n*\\f*\\t*\\v*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{Punct}", "");
        char[] ch = temp.trim().toCharArray();
        int chLength = ch.length;
        int messyCodeCount = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                	++messyCodeCount;
                }
            }
        }
        //System.out.println("chLength: "+chLength);
        //System.out.println("count: "+messyCodeCount);
        if (messyCodeCount != 0) {
            return true;
        }
        return false;
	}
	public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
