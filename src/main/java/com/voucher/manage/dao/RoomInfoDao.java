package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.voucher.manage.daoModel.RoomInfo;

public interface RoomInfoDao {

	public List<RoomInfo> findAllRoomInfo(Integer limit, Integer offset, String sort,
			String order,String search);
	
	Integer getRoomInfoCount(@Param(value="search")String search);
	
	
	public Map<String, Object> findAllChangehire_CharLog(Integer limit, Integer offset, String sort,
			String order,String search);
}
