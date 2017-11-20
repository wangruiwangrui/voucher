package com.voucher.manage.daoModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class HiddenRowMapper implements RowMapper<Hidden>{

	@Override
	public Hidden mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		hidden.setChangeSpeed(rs.getString("ChangeSpeed"));
		hidden.setHiddenInstance(rs.getString("HiddenInstance"));
		hidden.setDoubletest(rs.getDouble("doubletest"));
		System.out.println("time="+rs.getString("time"));
		hidden.setTime(rs.getDate("time"));
		return hidden;
	}


}
