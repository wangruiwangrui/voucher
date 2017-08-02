package com.voucher.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.voucher.manage.daoModel.RoomInfo;

public interface RoomInfoDao {

	public List<RoomInfo> findAllRoomInfo(Integer campusId,Integer limit, Integer offset, String sort,
			String order,String search);
	
	Integer getRoomInfoCount(@Param(value="campusId")Integer campusId,@Param(value="search")String search);
	
}
