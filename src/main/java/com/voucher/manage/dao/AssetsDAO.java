package com.voucher.manage.dao;

import java.util.Map;

public interface AssetsDAO {

	public Map findAllHidden(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
}
