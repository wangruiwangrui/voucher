package com.voucher.manage.file;

import java.io.File;

public class PdfFileFactory extends AbstractFileUpload{
	private type fileType=type.PDF;
	
	@Override
	public String upload(File file) {
		// TODO Auto-generated method stub
		return uploadFile(file, fileType);
	}

}
