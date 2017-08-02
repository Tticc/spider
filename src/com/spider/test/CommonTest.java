package com.spider.test;

import org.junit.*;

public class CommonTest {
	@BeforeClass//在所有方法开始之前 static
	public static void beforeClass(){}	
	@AfterClass//在所有方法结束之后 static
	public static void afterClass(){}	
	@Before//在每一个方法开始之前 
	public void notSetUp(){//setUp
	}	
	@After//在每一个方法结束之后
	public void after(){}//tearDown
	@Test
	public void testAdd() {
		//fail("Not yet implemented");
		
		//连个参数的含义，（期望值，实际值）
		
	}
	@Test(expected = ArithmeticException.class)
	//@Ignore
	public void testDevide(){
		
		//以double为参数的重载方法里，第三个double参数的意思是：误差值，两个double参数的重载方法已被废弃。
		//Assert.assertEquals(1.0,dou,0.1);
	}
	@Test(timeout=500)
	public void testTimeOut() {
		
	}

}


