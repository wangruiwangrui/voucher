package com.voucher.manage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	
	public String uploadFile(File file,type fileType) {
        String pathRoot = System.getProperty("user.home");
        System.out.println("file size is:"+file.length());//打印文件长度
        
        String filePath="";
        
      //mime type 检测文件类型
        String mimeType="";
        Map<String,String> map=FileTypeTest.getFileType();
        Iterator<Map.Entry<String, String>> entryiterator = map.entrySet().iterator();
        String filetypeHex = String.valueOf(FileTypeTest.getFileHexString(file));
        while (entryiterator.hasNext()) {
            Map.Entry<String,String> entry =  entryiterator.next();
            String fileTypeHexValue = entry.getValue();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                mimeType=entry.getKey();
                break;
            }
         }
        
        if(mimeType.equals("")){
       	String s=file.getName();
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
        
		UUID uuid=UUID.randomUUID();		
		Date date=new Date();
		
		String fileName=Md5.GetMD5Code(uuid.toString())+date.getTime();
		
		try {  
             File newFile = new File(savePath+"//"+fileName);//创建新文件  
             if(newFile!=null && !newFile.exists()){  
                 newFile.createNewFile();  
             }  
             OutputStream oputstream = new FileOutputStream(newFile); 
             
             InputStream iputstream = new FileInputStream(file);  
             
             byte[] buffer = new byte[4*1024];  
             int byteRead = -1;     
             while((byteRead=(iputstream.read(buffer)))!= -1){  
                 oputstream.write(buffer, 0, byteRead);  
             }  
             oputstream.flush();    
             iputstream.close();  
             oputstream.close();  
         
           //  newFile = new File(savePath+"//"+fileName); 
             
         
         
         File newFile2 = new File(savePath+"//"+fileName+"."+mimeType); 
         newFile.renameTo(newFile2);
         
          return savePath+"//"+fileName+"."+mimeType;
        }catch (Exception e) {
     		// TODO: handle exception
       	  e.printStackTrace();
       	  return e.toString();
   	  }   
	}
	
	public abstract String upload(File file);
}
