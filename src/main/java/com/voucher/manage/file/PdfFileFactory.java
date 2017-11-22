package com.voucher.manage.file;

public class PdfFileFactory extends AbstractFileUpload{
	private type fileType=type.PDF;
	
	@Override
	public String upload(String name, byte[] file) {
		// TODO Auto-generated method stub
		return uploadFile(name, file, fileType);
	}

}
