package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.RoomChangeHireLogDAO;
import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoModel.RowMappers;
import com.voucher.manage.daoSQL.SelectSQL;


public class RoomChangeHireLogImpl extends JdbcDaoSupport implements RoomChangeHireLogDAO{

	@Override
	public List<RoomChangeHireLog> findAllRoomInfo(Integer limit, Integer offset, String sort, String order,
			String search) {
		// TODO Auto-generated method stub
		
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		
		roomChangeHireLog.setLimit(limit);
		roomChangeHireLog.setOffset(offset);
		roomChangeHireLog.setNotIn("[GUID]");
		if(search!=null&&!search.trim().equals("")){
			  String[] where={"RoomAddress like ",search};
		      roomChangeHireLog.setWhere(where);
		}
		
		String sql="";
		try {
			sql = SelectSQL.get(roomChangeHireLog);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.getJdbcTemplate().query(sql, new RowMappers(RoomChangeHireLog.class));
	}

	@Override
	public Integer getRoomChangeHireLogCount(String search) {
		// TODO Auto-generated method stub
		
		Map<String,Object> map=new HashMap<>();
		
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		
		if(search!=null&&!search.trim().equals("")){
			  String[] where={"RoomAddress like ",search};
		      roomChangeHireLog.setWhere(where);
		}
		
		String sql="";
		try {
			sql = SelectSQL.getCount(roomChangeHireLog);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		map=this.getJdbcTemplate().queryForMap(sql);
		
		return (Integer) map.get("");
	}

}
