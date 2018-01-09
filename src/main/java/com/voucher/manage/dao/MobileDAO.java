package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface MobileDAO {
	public Map<String, Object> hiddenImageQuery(HttpServletRequest request,List guidLits);

}
