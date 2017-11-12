package com.voucher.manage.file;

import java.io.File;


public class ImageFileFactory extends AbstractFileUpload{

	@Override
	public String upload(File file,type fileType) {
		// TODO Auto-generated method stub
		String path="\\Desktop\\bb\\photo";  
        return uploadFile(file,fileType);

	}
	

}
