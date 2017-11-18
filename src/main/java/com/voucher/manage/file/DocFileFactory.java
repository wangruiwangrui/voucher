package com.voucher.manage.file;

import java.io.File;

public class DocFileFactory extends AbstractFileUpload{
    private type fileType=type.DOC;
	@Override
	public String upload(File file) {
		// TODO Auto-generated method stub
		return uploadFile(file, fileType);
	}

}
