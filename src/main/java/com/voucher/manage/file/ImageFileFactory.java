package com.voucher.manage.file;

import java.util.List;

public class ImageFileFactory extends AbstractFileUpload{
    private type fileType=type.IMAGE;

	@Override
	public Integer upload(String GUID, List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return uploadFile(GUID, names, files, fileType);
	}
	

}
