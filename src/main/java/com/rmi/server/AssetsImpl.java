package com.rmi.server;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.Hidden;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.file.DocFileFactory;
import com.voucher.manage.file.ImageFileFactory;
import com.voucher.manage.file.PdfFileFactory;
import com.voucher.manage.file.XlsFileFactory;
import com.voucher.sqlserver.context.Connect;

public class AssetsImpl implements Assets{
	
//	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	/*
	 * 连接池
	 */
	ApplicationContext applicationContext=new Connect().get();
	
	@Override
	public Map<String, Object> getRoomInfo(Integer limit,Integer offset,String sort,String order,
			Map search) {
		Map<String, Object> map = new HashMap<String, Object>();

		
	
		/*
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
		}		
		*/


		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(limit,offset,sort,
				order,search);
		
		map.put("rows", roomInfos);
		
		Integer total=roomInfoDao.getRoomInfoCount(search);
		
        map.put("total", total);
		
		return map;
	}

	@Override
	public Map<String, Object> findAllChangehire_CharLog(Integer limit, Integer offset, String sort, String order,
			String search) {
		// TODO Auto-generated method stub
		
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");

		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
	
		
		

		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";              
		}	
		
		
		Map map2=roomInfoDao.findAllChangehire_CharLog(limit, offset, sort, order, search);
		

		return map2;
		
	}

	@Override
	public String uploadImageFile(String name, byte[] file) {
	     return new ImageFileFactory().upload(name,file);
    }

	@Override
	public String uploadPdfFile(String name, byte[] file) {
		// TODO Auto-generated method stub
		return new PdfFileFactory().upload(name,file);
	}

	@Override
	public String uploadDocFile(String name, byte[] file) {
		// TODO Auto-generated method stub
		return new DocFileFactory().upload(name,file);
	}

	@Override
	public String uploadXlsFile(String name, byte[] file) {
		// TODO Auto-generated method stub
		return new XlsFileFactory().upload(name,file);
	}

	@Override
	public Map<String, Object> selectAllHidden(Integer limit, Integer offset, String sort, String order, Map search) {
		// TODO Auto-generated method stub
		
		HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
		
		Map map=hiddenDAO.selectAllHidden(limit, offset, sort, order, search);
		
		return map;
	}

	@Override
	public Integer insertIntoHidden(Hidden hidden) {
		// TODO Auto-generated method stub
		
		HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
		
		return hiddenDAO.insertIntoHidden(hidden);
	}

	@Override
	public Integer updateHidden(Hidden hidden) {
		// TODO Auto-generated method stub
		
		HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
		
		return hiddenDAO.updateHidden(hidden);
	}

	@Override
	public Integer deleteHidden(Hidden hidden) {
		// TODO Auto-generated method stub
		HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
		
		return hiddenDAO.deleteHidden(hidden);
	}

	@Override
	public Integer updateRoomInfo(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		return roomInfoDao.updateRoomInfo(roomInfo);
	}

	@Override
	public Integer deleteRoomInfo(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		return roomInfoDao.deleteRoomInfo(roomInfo);
	}

	@Override
	public Map<String, Object> findAllRoomInfo_Position(Integer limit, Integer offset, String sort, String order,
			Map search) {
		// TODO Auto-generated method stub
		RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
		
		Map map=roomInfoDao.findAllRoomInfo_Position(limit, offset, sort, order, search);
		
		return map;
	}

	
	
}
