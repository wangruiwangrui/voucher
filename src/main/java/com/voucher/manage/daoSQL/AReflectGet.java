package com.voucher.manage.daoSQL;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * 通过反射取得注解的方法名,调用get方法取得注解类型的值
 */

public class AReflectGet {
		
	public static Integer getIntMethods(Object object,Class className,Field field,String columnName){
        String filedName = field.getName();  
        //获取相应字段的getXXX()方法  
        String getMethodName = "get" + filedName.substring(0, 1).toUpperCase()  
                + filedName.substring(1);
        System.out.println(getMethodName);
        Integer i=0;
       try {
       	Method getMethod =className.getMethod(getMethodName);
			i=(Integer) getMethod.invoke(object);
			return i;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return i;
	}
	
	
	public static String getStringMethods(Object object,Class className,Field field,String columnName){
        String filedName = field.getName();  
        //获取相应字段的getXXX()方法  
        String getMethodName = "get" + filedName.substring(0, 1).toUpperCase()  
                + filedName.substring(1);
        System.out.println(getMethodName);
        String s="";
       try {
       	Method getMethod =className.getMethod(getMethodName);
			s=(String) getMethod.invoke(object);
			return s;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return s;
	}
	
	public static String[] getArrayMethods(Object object,Class className,Field field,String columnName){
        String filedName = field.getName();  
        //获取相应字段的getXXX()方法  
        String getMethodName = "get" + filedName.substring(0, 1).toUpperCase()  
                + filedName.substring(1);
        System.out.println(getMethodName);
        String[] s={};
       try {
       	Method getMethod =className.getMethod(getMethodName);
			s=(String[]) getMethod.invoke(object);
			return s;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return s;
	}
}
