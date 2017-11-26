package com.voucher.manage.daoImpl;

import java.util.List;
import java.util.Map;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.daoModel.Hidden;
import com.voucher.manage.tools.TransMapToString;

public class AssetsDAOImpl implements AssetsDAO{

	@Override
	public Map findAllHidden(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Hidden hidden=new Hidden();
		
		hidden.setLimit(limit);
		hidden.setOffset(offset);
		hidden.setSort(sort);
		hidden.setOrder(order);
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    hidden.setWhere(where);
		}
		
		
		return null;
	}

}
