package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;

public interface MobileDAO {
	
	public Map<String, Object> hiddenImageQuery(HttpServletRequest request,List guidLits);
	
	public List allHiddenImageByGUID(HttpServletRequest request,Hidden_Join hidden_Join);

}
