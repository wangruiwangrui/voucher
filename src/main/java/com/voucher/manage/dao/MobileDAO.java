package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Neaten_Join;

public interface MobileDAO {
	
	public Map<String, Object> hiddenImageQuery(HttpServletRequest request,List guidLits);
	
	public Map<String, Object> positionHiddenImageQuery(HttpServletRequest request,List guidLits);
	
	public List allHiddenImageByGUID(HttpServletRequest request,Hidden_Join hidden_Join);

	public Map<String, Object> checkImageQuery(HttpServletRequest request,List guidLits);
	
	public List allCheckImageByGUID(HttpServletRequest request,Hidden_Check_Join hidden_Check_Join);
	
	public Map<String, Object> neatenImageQuery(HttpServletRequest request,List guidLits);
	
	public List allNeatenImageByGUID(HttpServletRequest request,Hidden_Neaten_Join hidden_Neaten_Join);
	
	
}