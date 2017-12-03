package com.voucher.manage.file;

import java.util.List;

public class XlsFileFactory extends AbstractFileUpload{
	private type fileType=type.XLS;

	@Override
	public Integer upload(String GUID, List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return uploadFile(GUID, names, files, fileType);
	}
	

}
