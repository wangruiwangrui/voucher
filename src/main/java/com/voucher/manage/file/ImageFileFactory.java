package com.voucher.manage.file;

import java.io.File;


public class ImageFileFactory extends AbstractFileUpload{
    private type fileType=type.IMAGE;
	
	@Override
	public String upload(File file) {
		// TODO Auto-generated method stub
        return uploadFile(file,fileType);

	}
	

}
