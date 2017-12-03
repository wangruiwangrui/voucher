package com.voucher.manage.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.ApplicationContext;

import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.tools.FileTypeTest;
import com.voucher.manage.tools.Md5;
import com.voucher.sqlserver.context.Connect;

public abstract class AbstractFileUpload {
	public enum type{
		IMAGE,XLS,PDF,DOC;
	}
	
	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	public final static String filePath="\\Desktop\\bb\\photo";
	
	public AbstractFileUpload() {
		// TODO Auto-generated constructor stub
		
	}
	
	public Integer uploadFile(String GUID,List<String> names, List<byte[]> files,type fileType) {
        String pathRoot = System.getProperty("user.home");
               
        BufferedOutputStream os=null;
        
      //mime type 检测文件类型
        String mimeType="";
        Map<String,String> map=FileTypeTest.getFileType();
        Iterator<Map.Entry<String, String>> entryiterator = map.entrySet().iterator();
       
         File savePath = new File(pathRoot+filePath);//创建新文件  
         System.out.println("filePath="+filePath);
         if (!savePath.exists()) {   
             savePath.mkdir();   
         }  
		
       Iterator<byte[]> iterator=files.iterator();

       int i=0;
       try {  
         while(iterator.hasNext()){
        	 String name=names.get(i);
        	 byte[] file=iterator.next();
             File newFile = new File(savePath+"\\"+name);//创建新文件  
             if(newFile!=null && !newFile.exists()){  
                 newFile.createNewFile();  
             }  
             
             os = new BufferedOutputStream(new FileOutputStream(newFile));
             os.write(file);
             os.flush();  
             os.close();  
                     
             String filetypeHex = String.valueOf(FileTypeTest.getFileHexString(newFile));
             while (entryiterator.hasNext()) {
                 Map.Entry<String,String> entry =  entryiterator.next();
                 String fileTypeHexValue = entry.getValue();
                 if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                     mimeType=entry.getKey();
                     break;
                 }
              }
          
     		UUID uuid=UUID.randomUUID();		
     		Date date=new Date();
     		
     		String fileName=Md5.GetMD5Code(uuid.toString())+date.getTime();
         
            File newFile2 = new File(savePath+"\\"+fileName+"."+mimeType); 
            System.out.println("newFile="+newFile.getName());
            newFile.renameTo(newFile2);
            System.out.println("newFile2="+newFile2.getName());
            String uri=fileName+"."+mimeType;
            System.out.println("uri="+savePath+"\\"+fileName+"."+mimeType);
            hiddenDAO.InsertIntoHiddenData(GUID,name,fileType.toString(), uri);
            i++;
          }  
          return 1;
        }catch (Exception e) {
     		// TODO: handle exception
       	  e.printStackTrace();
       	  return 0;
   	    } 

	}
	
	protected abstract Integer upload(String GUID,List<String> names, List<byte[]> files);
}
