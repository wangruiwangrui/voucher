package com.voucher.manage.file;

public class XlsFileFactory extends AbstractFileUpload{
	private type fileType=type.XLS;
	
	@Override
	public String upload(String name, byte[] file) {
		// TODO Auto-generated method stub
		return uploadFile(name, file, fileType);
	}

}
