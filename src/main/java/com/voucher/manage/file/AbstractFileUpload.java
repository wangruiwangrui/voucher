package com.voucher.manage.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.voucher.manage.tools.FileTypeTest;
import com.voucher.manage.tools.Md5;

public abstract class AbstractFileUpload {
	public enum type{
		IMAGE,XLS,PDF,DOC;
	}
	
	private final static String imageFilePath="\\Desktop\\bb\\photo";
	private final static String xlsFilePath="\\Desktop\\bb\\xls";
	private final static String pdfFilePath="\\Desktop\\bb\\pdf";
	private final static String docFilePath="\\Desktop\\bb\\doc";
	
	public AbstractFileUpload() {
		// TODO Auto-generated constructor stub
		
	}
	
	public String uploadFile(String name, byte[] file,type fileType) {
        String pathRoot = System.getProperty("user.home");
        
        String filePath="";
        
        BufferedOutputStream os=null;
        
      //mime type 检测文件类型
        String mimeType="";
        Map<String,String> map=FileTypeTest.getFileType();
        Iterator<Map.Entry<String, String>> entryiterator = map.entrySet().iterator();
        
        if(mimeType.equals("")){
        	String s=name;
     		mimeType=s.substring(s.lastIndexOf('.')+1); //获取后缀名
         }
         
         if(fileType==type.IMAGE){
         	filePath=imageFilePath;
         }else if(fileType==type.XLS){
         	filePath=xlsFilePath;
         }else if(fileType==type.PDF){
         	filePath=pdfFilePath;
         }else if(fileType==type.DOC){
         	filePath=docFilePath;
         }else{
         	return "文件类型错误";
         }

         
         File savePath = new File(pathRoot+filePath);//创建新文件  
         System.out.println("filePath="+filePath);
         if (!savePath.exists()) {   
             savePath.mkdir();   
         }  
		
		try {  
             File newFile = new File(savePath+"//"+name);//创建新文件  
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
         
            File newFile2 = new File(savePath+"//"+fileName+"."+mimeType); 
            System.out.println("newFile="+newFile.getName());
            newFile.renameTo(newFile2);
            System.out.println("newFile2="+newFile2.getName());
            return savePath+"//"+fileName+"."+mimeType;
        }catch (Exception e) {
     		// TODO: handle exception
       	  e.printStackTrace();
       	    return e.toString();
   	    } 
	}
	
	public abstract String upload(String name, byte[] file);
}
