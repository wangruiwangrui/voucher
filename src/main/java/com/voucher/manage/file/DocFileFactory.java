package com.voucher.manage.file;

public class DocFileFactory extends AbstractFileUpload{
    private type fileType=type.DOC;
	@Override
	public String upload(String name, byte[] file) {
		// TODO Auto-generated method stub
		return uploadFile(name, file, fileType);
	}

}
