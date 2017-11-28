package com.voucher.manage.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.daoModel.Hidden;
import com.voucher.manage.daoModel.Position;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.tools.TransMapToString;

public class AssetsDAOImpl extends JdbcDaoSupport implements AssetsDAO{

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

	@Override
	public Integer updatePosition(Position position) {
		// TODO Auto-generated method stub
		return InsertExe.get(this.getJdbcTemplate(), position);
	}

}
