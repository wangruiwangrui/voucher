package com.voucher.manage.daoModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;

public class RowMappers<T> implements RowMapper<T>{

	Class className;
	
	public RowMappers(Class className) {
		// TODO Auto-generated constructor stub
		this.className=className;
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException{
		// TODO Auto-generated method stub
		
		RoomInfo roomInfo=new RoomInfo();
        roomInfo.setGUID(rs.getString("GUID"));
        roomInfo.setNum(rs.getString("Num"));
        roomInfo.setOriginalNum(rs.getString("OriginalNum"));
        roomInfo.setAddress(rs.getString("Address"));
        roomInfo.setOriginalAddress(rs.getString("OriginalAddress"));
        
        Object object=null;
		try {
			object = className.newInstance();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}       
   	    String name = className.getName(); 
   	    Class<?> cl=null;
        try {
			cl = Class.forName(name);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        List<Map> columnDefs = new ArrayList<Map>();
 
        for(Field field : cl.getDeclaredFields())                  //获取声明的属性
        {
        	String columnName = null;           
            Annotation[] anns = field.getDeclaredAnnotations();//获取注解，一个属性可以有多个注解，所以是数组类型
            Map<String,Object> map=new HashMap();
            if(anns.length < 1)
            {
                continue;
            }else
            if(anns[0] instanceof SQLInteger)                //判断注解类型
            {
                SQLInteger sInt = (SQLInteger)anns[0];
                columnName = (sInt.name().length()<1)?field.getName():sInt.name();//获取列名称与获取表名一样
                map.put("field", field);//使用一个方法，自己写的getConstraints(Constraints constraints)获取列定义
                map.put("columnName", columnName);
                columnDefs.add(map);
            }else
            if(anns[0] instanceof SQLString)
            {
                SQLString sStr = (SQLString)anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                map.put("field", field);
                map.put("columnName", columnName);  
                columnDefs.add(map);
            }else
            if(anns[0] instanceof SQLFloat)
            {
                SQLFloat sStr = (SQLFloat) anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                map.put("field", field);
                map.put("columnName", columnName); 
                columnDefs.add(map);
            }else
            if(anns[0] instanceof SQLDateTime)
            {
                SQLDateTime sStr = (SQLDateTime) anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                map.put("field", field);
                map.put("columnName", columnName); 
                columnDefs.add(map);
            }
        }
        
        Iterator<Map> iterator=columnDefs.iterator();

        while (iterator.hasNext()) {
        	Map<String, Object> map=iterator.next();
        	Field field=(Field) map.get("field");
        	String columnName=(String) map.get("columnName");
        	setStringMethods(rs,object, className, field, columnName);

		}
        
        
        return (T) object;
	}

	public static void setStringMethods(ResultSet rs,Object object,Class className,Field field,String columnName){
        String filedName = field.getName();  
        //获取相应字段的getXXX()方法  
        String setMethodName = "set" + filedName.substring(0, 1).toUpperCase()  
                + filedName.substring(1);
        System.out.println("setMethodName="+setMethodName);
       try {
       	 Method setMethod =className.getDeclaredMethod(setMethodName,String.class);
       	 System.out.println("setmethod="+setMethod);
       	 String aa=rs.getString(columnName);
       	 System.out.println("aa="+aa);
		 setMethod.invoke(object,aa);
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
		}catch (SQLException e) {  // ResultSet的异常
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
	}
}
