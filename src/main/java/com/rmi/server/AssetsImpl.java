package com.rmi.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Level;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Hidden_Type;
import com.voucher.manage.daoModel.Assets.Hidden_User;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
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
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
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
	public Integer uploadImageFile(Object object,String GUID,List<String> names, List<byte[]> files) {
	     return new ImageFileFactory().upload(object,GUID, names, files);
    }

	@Override
	public Integer uploadPdfFile(Object object,String GUID,List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return new PdfFileFactory().upload(object,GUID, names, files);
	}

	@Override
	public Integer uploadDocFile(Object object,String GUID,List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return new DocFileFactory().upload(object,GUID, names, files);
	}

	@Override
	public Integer uploadXlsFile(Object object,String GUID,List<String> names, List<byte[]> files) {
		// TODO Auto-generated method stub
		return new XlsFileFactory().upload(object,GUID, names, files);
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
		return hiddenDAO.insertIntoHidden(hidden);
	}

	@Override
	public Integer updateHidden(Hidden hidden) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.updateHidden(hidden);
	}

	@Override
	public Integer deleteHidden(Hidden hidden) {
		// TODO Auto-generated method stub
		
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

	@Override
	public Integer updatePosition(Position position) {
		// TODO Auto-generated method stub
		AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
		
		return assetsDAO.updatePosition(position);
	}


	@Override
	public List<Hidden_Level> setctAllHiddenLevel() {		
		// TODO Auto-generated method stub		
		return hiddenDAO.setctAllHiddenLevel();
	}


	@Override
	public Integer insertHiddenLevel(Hidden_Level hidden_level) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.insertHiddenLevel(hidden_level);
	}


	@Override
	public Integer deleteHiddenLevel(Hidden_Level hidden_level) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.deleteHiddenLevel(hidden_level);
	}


	@Override
	public Map<String, Object> selectAllHidden_Jion(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.selectAllHidden_Jion(limit, offset, sort, order, search);
	}


	@Override
	public Map<String, Object> selectAllHiddenDate(String GUID) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.selectAllHiddenDate(GUID);
	}


	@Override
	public Map<String, Object> selectAllHiddenCheckDate(String check_id) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenCheckDate(check_id);
	}


	@Override
	public Map<String, Object> selectAllHiddenNeatenDate(String neaten_id) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenNeatenDate(neaten_id);
	}
	
	@Override
	public List<Hidden_Type> selectAllHiddenType() {
		// TODO Auto-generated method stub
		
		return hiddenDAO.selectAllHiddenType();
	}


	@Override
	public Integer insertHiddenType(Hidden_Type hidden_Type) {
		// TODO Auto-generated method stub
		
		return hiddenDAO.insertHiddenType(hidden_Type);
	}


	@Override
	public Integer deleteHiddenType(Hidden_Type hidden_Type) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenType(hidden_Type);
	}


	@Override
	public List<Hidden_User> selectAllHiddenUser() {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenUser();
	}


	@Override
	public Integer insertHiddenUser(Hidden_User hidden_User) {
		// TODO Auto-generated method stub
		return hiddenDAO.insertHiddenUser(hidden_User);
	}


	@Override
	public Integer deleteHiddenUser(Hidden_User hidden_User) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenUser(hidden_User);
	}


	@Override
	public Map<String, Object> selectAllHiddenCheck(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenCheck(limit, offset, sort, order, search);
	}


	@Override
	public Integer insertHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return hiddenDAO.insertHiddenCheck(hidden_Check);
	}


	@Override
	public Integer deleteHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenCheck(hidden_Check);
	}


	@Override
	public Map<String, Object> selectAllHiddenNeaten(Integer limit, Integer offset, String sort, String order,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectAllHiddenNeaten(limit, offset, sort, order, search);
	}


	@Override
	public Integer insertHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		return hiddenDAO.insertHiddenNeaten(hidden_Neaten);
	}


	@Override
	public Integer deleteHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		return hiddenDAO.deleteHiddenNeaten(hidden_Neaten);
	}


	@Override
	public Integer updateHiddenCheck(Hidden_Check hidden_Check) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateHiddenCheck(hidden_Check);
	}


	@Override
	public Integer updateHiddenNeaten(Hidden_Neaten hidden_Neaten) {
		// TODO Auto-generated method stub
		return hiddenDAO.updateHiddenNeaten(hidden_Neaten);
	}


	@Override
	public List<Hidden_Join> selectHiddenOfMap(Map<String, String> search) {
		// TODO Auto-generated method stub
		return hiddenDAO.selectHiddenOfMap(search);
	}


	@Override
	public Integer insertIntoHidden_Assets(Hidden_Assets hidden_Assets) {
		// TODO Auto-generated method stub
		return  assetsDAO.insertIntoHidden_Assets(hidden_Assets);
	}


	@Override
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		return assetsDAO.findAssetByHideen(limit, offset, sort, order, search);
	}


	
}
