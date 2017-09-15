package com.voucher.manage.daoSQL;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.voucher.manage.daoSQL.annotations.DBTable;
import com.voucher.manage.daoSQL.annotations.QualifiLimit;
import com.voucher.manage.daoSQL.annotations.QualifiNotIn;
import com.voucher.manage.daoSQL.annotations.QualifiOffset;
import com.voucher.manage.daoSQL.annotations.QualifiOrder;
import com.voucher.manage.daoSQL.annotations.QualifiSort;
import com.voucher.manage.daoSQL.annotations.QualifiWhere;
import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;

public class SelectSQLJoin {
	public static String get(Object[] objects,String joinParameter) throws ClassNotFoundException{
        Integer limit=10;
		Integer offset=0; 
		String notIn="";
    	String sort="";
		String order="";
        String tableName="";
        String tableName0="";
        String firstTableName="";
        String select="";
        List<String> columnDefs = new ArrayList<String>();
        String[] columnWhere=null;
        List wheres=new ArrayList<String[]>();
        boolean term=false;       //判断是否有where
        String leftJionTableName="";

      int i=0;
        
	  for(Object object:objects){
		Class className=object.getClass();
   	    String name = className.getName();                                    //从控制台输入一个类名，我们输入User即可
        Class<?> cl = Class.forName(name);                         //加载类，如果该类不在默认路径底下，会报 java.lang.ClassNotFoundException
        DBTable dbTable = cl.getAnnotation(DBTable.class);         //从User类中获取DBTable注解
        try{
         tableName = (dbTable.name().length()<1)?cl.getName():dbTable.name();//获取表的名字，如果没有在DBTable中定义，则获取类名作为Table的名字

         if(i==0){
          firstTableName=tableName;
          tableName0=firstTableName;
         }else{         
          leftJionTableName=leftJionTableName+" left join "+tableName+" on " +tableName0+"."+joinParameter
        		  +"="+tableName+"."+joinParameter;
          tableName0=tableName;
         }
        }catch (Exception e) {
			// TODO: handle exception
         tableName =name;
		 }
       
         
         int j=0;
        for(Field field : cl.getDeclaredFields())                  //获取声明的属性
        {
        	System.out.println("j="+j);
        	j++;
            String columnName = null;           
            Annotation[] anns = field.getDeclaredAnnotations();//获取注解，一个属性可以有多个注解，所以是数组类型
            if(anns.length < 1)
            {
                continue;
            }else
            if(anns[0] instanceof SQLInteger)                //判断注解类型
            {
                SQLInteger sInt = (SQLInteger)anns[0];
                columnName = tableName+"."+((sInt.name().length()<1)?field.getName():sInt.name());//获取列名称与获取表名一样
                columnDefs.add(columnName);//使用一个方法，自己写的getConstraints(Constraints constraints)获取列定义
            }else
            if(anns[0] instanceof SQLString)
            {
                SQLString sStr = (SQLString)anns[0];
                columnName = tableName+"."+((sStr.name().length()<1)?field.getName().toUpperCase():sStr.name());
                columnDefs.add(columnName);
            }else
            if(anns[0] instanceof SQLFloat)
            {
                SQLFloat sStr = (SQLFloat) anns[0];
                columnName = tableName+"."+((sStr.name().length()<1)?field.getName().toUpperCase():sStr.name());
                columnDefs.add(columnName);
            }else
            if(anns[0] instanceof SQLDateTime)
            {
                SQLDateTime sStr = (SQLDateTime) anns[0];
                columnName = tableName+"."+((sStr.name().length()<1)?field.getName().toUpperCase():sStr.name());
                columnDefs.add(columnName);
            }else
            if(anns[0] instanceof QualifiWhere)
            {            	
                QualifiWhere sStr = (QualifiWhere) anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                columnWhere = AReflectGet.getArrayMethods(object, className, field, columnName);
                System.out.println("columnWhere="+columnWhere);
                if(columnWhere!=null){
                	wheres.add(columnWhere);
                	term=true;
                }
            }else
            if(anns[0] instanceof QualifiLimit)
            {
            	 QualifiLimit sStr = (QualifiLimit) anns[0];
                 columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                 
                 String filedName = field.getName();  
                 limit=AReflectGet.getIntMethods(object, className, field,columnName);
                 System.out.println("limit="+limit);
             }else	
            if(anns[0] instanceof QualifiOffset)
            {
            	QualifiOffset sStr = (QualifiOffset) anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                offset=AReflectGet.getIntMethods(object, className, field,columnName);                
            }else
             if(anns[0] instanceof QualifiNotIn)
             {
            	QualifiNotIn sStr = (QualifiNotIn) anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                notIn=AReflectGet.getStringMethods(object, className, field,columnName);
             }else	
            if(anns[0] instanceof QualifiSort)
            {
            	QualifiSort sStr = (QualifiSort) anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                sort=columnName;
             }else
            if(anns[0] instanceof QualifiOrder)
            {
            	QualifiOrder sStr = (QualifiOrder) anns[0];
                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
                order=columnName;
             }
        }
        
        StringBuilder selectCommand = new StringBuilder("SELECT top "+limit);
                
        for(String columnDef :columnDefs){
            selectCommand.append("\n    "+columnDef+",");
        }
        
        select=selectCommand.substring(0,selectCommand.length()-1)+"\n FROM \n   "+firstTableName+leftJionTableName;
        
   
        i++;
   }
        
	  if(term){
          StringBuilder whereCommand = new StringBuilder();         
          Iterator<String[]> iterator=wheres.iterator();
          System.out.println("wheres="+wheres);
          while (iterator.hasNext()) {
        	  columnWhere=iterator.next();
        	  int k=1;
        	  for(String whereterm:columnWhere){
            	  if(k%2==0){
            		//  System.out.println("偶数");
            		  whereCommand.append(whereterm+"\n  AND ");
            	   }else{
            		//  System.out.println("奇数");
            		  whereCommand.append("\n   "+whereterm);
            	   }
               k++;
               System.out.println("whereCommand="+whereCommand);
              }
		  }
          
          select=select+   //sqlserver分页需要在top也加上where条件
            		 "\n  where "+firstTableName+"."+notIn+
                     " not in("+
                     " select top "+offset+" "+firstTableName+"."+notIn+" FROM "+firstTableName+" where "+
                      whereCommand.substring(0,whereCommand.length()-7)+")";
          System.out.println("select="+select);
          select=select+"\n  AND "+whereCommand.substring(0,whereCommand.length()-7);
          System.out.println("select="+select);
        }else{
        	select=select+
           		 "\n  where "+firstTableName+"."+firstTableName+"."+notIn+
                    " not in("+
                    " select top "+offset+" "+firstTableName+"."+notIn+" FROM "+firstTableName+")";
        }
	  
        return select;
   }
	
	public static String getCount(Object[] objects,String joinParameter) throws ClassNotFoundException{
		List<String> columnDefs = new ArrayList<String>();
        String[] columnWhere=null;
        List wheres=new ArrayList<String[]>();
        String tableName="";
        String tableName0="";
        String firstTableName="";
        String select="";
        boolean term=false;       //判断是否有where
        String leftJionTableName="";

        int i=0;
        
		for(Object object:objects){
			Class className=object.getClass();
	   	    String name = className.getName();                                    //从控制台输入一个类名，我们输入User即可
	        Class<?> cl = Class.forName(name);                         //加载类，如果该类不在默认路径底下，会报 java.lang.ClassNotFoundException
	        DBTable dbTable = cl.getAnnotation(DBTable.class);         //从User类中获取DBTable注解
	        try{
	         tableName = (dbTable.name().length()<1)?cl.getName():dbTable.name();//获取表的名字，如果没有在DBTable中定义，则获取类名作为Table的名字

	         if(i==0){
	          firstTableName=tableName;
	          tableName0=firstTableName;
	         }else{         
	          leftJionTableName=leftJionTableName+" left join "+tableName+" on " +tableName0+"."+joinParameter
	        		  +"="+tableName+"."+joinParameter;
	          tableName0=tableName;
	         }
	        }catch (Exception e) {
				// TODO: handle exception
	         tableName =name;
			 }
	       
	         
	         int j=0;
	        for(Field field : cl.getDeclaredFields())                  //获取声明的属性
	        {
	        	System.out.println("j="+j);
	        	j++;
	            String columnName = null;           
	            Annotation[] anns = field.getDeclaredAnnotations();//获取注解，一个属性可以有多个注解，所以是数组类型
	            if(anns.length < 1)
	            {
	                continue;
	            }else	            
	            if(anns[0] instanceof QualifiWhere)
	            {            	
	                QualifiWhere sStr = (QualifiWhere) anns[0];
	                columnName = (sStr.name().length()<1)?field.getName().toUpperCase():sStr.name();
	                columnWhere = AReflectGet.getArrayMethods(object, className, field, columnName);
	                System.out.println("columnWhere="+columnWhere);
	                if(columnWhere!=null){
	                	wheres.add(columnWhere);
	                	term=true;
	                }
	            }
	        }
	        
	        StringBuilder selectCommand = new StringBuilder("SELECT count(*)");
	                
	        for(String columnDef :columnDefs){
	            selectCommand.append("\n    "+columnDef+",");
	        }
	        
	        select=selectCommand+"\n FROM \n   "+firstTableName+leftJionTableName;
	        
	   
	        i++;
	   }
	     
		if(term){
	          StringBuilder whereCommand = new StringBuilder();         
	          Iterator<String[]> iterator=wheres.iterator();
	          System.out.println("wheres="+wheres);
	          while (iterator.hasNext()) {
	        	  columnWhere=iterator.next();
	        	  int k=1;
	        	  for(String whereterm:columnWhere){
	            	  if(k%2==0){
	            		//  System.out.println("偶数");
	            		  whereCommand.append(whereterm+"\n  AND ");
	            	   }else{
	            		//  System.out.println("奇数");
	            		  whereCommand.append("\n   "+whereterm);
	            	   }
	               k++;
	              // System.out.println("whereCommand="+whereCommand);
	              }
			  }
	          
	       //   System.out.println("select="+select);
	          select=select+"\n  WHERE "+whereCommand.substring(0,whereCommand.length()-7);
	       //   System.out.println("select="+select);
	        }else{
	        	select=select+
	           		 "\n  where "+firstTableName+"."+firstTableName;
	        }
		
	        return select;
	   }
	
}