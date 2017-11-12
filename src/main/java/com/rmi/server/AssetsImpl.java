package com.rmi.server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.rmi.server.entity.ImageUploadResult;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.file.AbstractFileUpload.type;
import com.voucher.manage.file.ImageFileFactory;
import com.voucher.manage.model.Photo;
import com.voucher.manage.tools.FileTypeTest;
import com.voucher.sqlserver.context.Connect;

public class AssetsImpl implements Assets{
	
//	ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-sqlservers.xml");
	
	/*
	 * 连接池
	 */
	ApplicationContext applicationContext=new Connect().get();
	
	@Override
	public Map<String, Object> getRoomInfo(Integer limit,Integer offset,String sort,String order,
			String search) {
		Map<String, Object> map = new HashMap<String, Object>();

		
	
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";
		}		
		


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
	public String uploadFile(File imageFile,type fileType) {
	     return new ImageFileFactory().upload(imageFile,fileType);
    }

	
	
}
