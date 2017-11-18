package com.voucher.manage.dao;

import java.util.Map;

public interface HiddenDAO {

	public Map<String, Object> findAllHidden(Integer limit, Integer offset, String sort,
			String order,Map<String, String> search);
}
