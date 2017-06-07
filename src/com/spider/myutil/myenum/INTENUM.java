package com.spider.myutil.myenum;


/**
 * keep the int constants.
 * @author wenc
 *
 * @how_to_use 
 * 		INTENUM.ONE.value();<br/>
 * 		INTENUM.valueOf(int);
 */
public enum INTENUM{
	ONE(1), TWO(2);
	
	private int intValue = 0;
	public static INTENUM valueOf(int intValue) {
        switch (intValue) {
        case 1:
            return ONE;
        case 2:
            return TWO;
        default:
            return null;
        }
    }
	private INTENUM(int intValue) {
        this.intValue = intValue;
    }
	public int value() {
        return this.intValue;
    }
}
