package com.voucher.manage.file;

import java.io.File;

public class XlsFileFactory extends AbstractFileUpload{
	private type fileType=type.XLS;
	
	@Override
	public String upload(File file) {
		// TODO Auto-generated method stub
		return uploadFile(file, fileType);
	}

}
