package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.voucher.manage.daoModel.RoomInfo;

public interface RoomInfoDao {

	public List<RoomInfo> findAllRoomInfo(Integer limit, Integer offset, String sort,
			String order,Map search);
	
	Integer getRoomInfoCount(Map search);
	
	public Integer updateRoomInfo(RoomInfo roomInfo);
	
	public Integer deleteRoomInfo(RoomInfo roomInfo);
	
	public Map<String, Object> findAllRoomInfo_Position(Integer limit, Integer offset, String sort,
			String order,Map search);
	
	public Map<String, Object> findAllChangehire_CharLog(Integer limit, Integer offset, String sort,
			String order,String search);
	
	public Map<String, Object> findAllFloor(Integer limit, Integer offset, String sort,
			String order,String search);
	
	public Map<String, Object> getChartInfoByGUID(Integer limit, Integer offset, String sort,
			String order,Map search);
	
	public Map<String, Object> getHireListByGUID(Integer limit, Integer offset, String sort,
			String order,Map search);
	
}
