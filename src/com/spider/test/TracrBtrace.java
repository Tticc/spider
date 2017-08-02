package com.spider.test;
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class TracrBtrace{

	@OnMethod(
			clazz = "com.spider.test.TestJVM2", 
			method = "add", 
			location = @Location(Kind.RETURN)
			)
	public static void func(@ProbeClassName String name,@ProbeMethodName String method,int methodParam1,int methodParam2, @Return int result){
		println("--------------------------------------------\n");
		//jstack();
		println("ClassName :" + str(name));
		println("MethodName :" + str(method));
		println("param a :" + str(methodParam1));
		println("param b :" + str(methodParam2));
		println("param result :" + str(result));

	}
}
