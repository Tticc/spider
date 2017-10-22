package com.spider.test;


import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface UseCase {//定义注解类
	public int id() default -1;
	//在使用@xx注解时，若没有如：
	//@xx(description = "description") 给出description，
	//则description为默认值。
	public String description() default "default description";
	//在使用@xx注解时，若没有如：@xx("value") 给出value，则value为默认值。
	public String value() default "default value";
}
class PasswordUtils{
	@UseCase(id=47,description="the id is 47")
	public boolean validatePassword(){
		return false;
	}
	@UseCase(id=48)
	public boolean validatePassword1(){
		return false;
	}
	@UseCase(id=49,description="the id is 49")
	public boolean validatePassword2(){
		return false;
	}
}
public class TestAnnotation {
	//定义解析注解方法
	//判定是否存在特定注解
	//isAnnotationPresent(Class<? extends Annotation> annotationClass) 
	public static void trackUseCase(List<Integer> useCases,Class<?> cl){
		for(Method m : cl.getDeclaredMethods()){
			UseCase uc = m.getAnnotation(UseCase.class);
			if(uc!=null){
				System.out.println("Found Use Case: " + uc.id() + " description is : " + uc.description());
				useCases.remove(new Integer(uc.id()));//;uc.id()
			}
		}
		for(int i : useCases){
			System.out.println("Warning:Missing use case-"+i);
		}
	}
	public static void main(String[] args) {
		/*if(args.length<1){
			System.out.println("arguments:annotated classes");
			System.exit(0);
		}*/
		System.out.println(args.toString());
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 47,48,49,50);//测试数据
		trackUseCase(useCases,PasswordUtils.class);		
	}
}

