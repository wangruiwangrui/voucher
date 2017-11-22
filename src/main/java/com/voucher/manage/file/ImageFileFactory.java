package com.voucher.manage.file;


public class ImageFileFactory extends AbstractFileUpload{
    private type fileType=type.IMAGE;
	
	@Override
	public String upload(String name, byte[] file) {
		// TODO Auto-generated method stub
        return uploadFile(name, file,fileType);

	}
	

}
