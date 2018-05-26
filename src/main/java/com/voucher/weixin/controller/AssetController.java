package com.voucher.weixin.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Assets_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.model.Access;
import com.voucher.manage.model.Users;
import com.voucher.manage.service.UserService;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;

import common.HttpClient;

@Controller
@RequestMapping("/mobile/asset")
public class AssetController {

	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	ApplicationContext applicationContext=new Connect().get();
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");

	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	@RequestMapping("/getAll")
	public @ResponseBody Map<String, Object> RoomInfo(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,String search2,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
			
		if(sort!=null&&sort.equals("buildArea")){
			sort="BuildArea";
		}
		
		if(sort!=null&&sort.equals("address")){
			sort="Address";
		}
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map where=new HashMap<>();
		
		where.put("[RoomInfo].State !=", "已划拨");
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("Address like ", search);
		}		

		if(search2!=null&&!search2.trim().equals("")){ 
			where.put("[RoomInfo].State= ", search2);
		}
		
		/*
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(limit,offset,sort,
				order,where);
		System.out.println("roominfocontroller sort="+sort+"   order="+order);
		*/
		
		Map roomInfo_Positions=roomInfoDao.findAllRoomInfo_Position(limit, offset, sort, order, where);
		
		List roominfos=(List) roomInfo_Positions.get("rows");
		
		map.put("rows", roominfos);
			
		Map fileBytes=mobileDao.roomInfo_PositionImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);
		
		MyTestUtil.print(fileBytes);
		
		return map;
	}
	
	@RequestMapping("/getNotPlace")
	public @ResponseBody Map<String, Object> notPlaceRoomInfo(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
			
		if(sort!=null&&sort.equals("buildArea")){
			sort="BuildArea";
		}
		
		if(sort!=null&&sort.equals("address")){
			sort="Address";
		}
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		Map where=new HashMap<>();
		
		where.put("[RoomInfo].State !=", "已划拨");
		
		if(search!=null&&!search.trim().equals("")){
			search="%"+search+"%";  
			where.put("Address like ", search);
		}		

		
		/*
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(limit,offset,sort,
				order,where);
		System.out.println("roominfocontroller sort="+sort+"   order="+order);
		*/
		
		Map roomInfo_Positions=roomInfoDao.notPlaceRoomInfo(limit, offset, search);
		
		List roominfos=(List) roomInfo_Positions.get("rows");

		
		map.put("rows", roominfos);
			
		Map fileBytes=mobileDao.roomInfo_PositionImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);
		
		int total=(int) roomInfo_Positions.get("total");		
		map.put("total", total);
		
		//MyTestUtil.print(fileBytes);
		
		return map;
	}
	
	@RequestMapping("/getRoomInfoByGUID")
	public @ResponseBody Map<String, Object> getRoomInfoByGUID(@RequestParam String guid
			,HttpServletRequest request){
		Map searchMap=new HashMap<>();
		searchMap.put("[RoomInfo].GUID = ", guid);
		
		List<RoomInfo_Position> roomInfo_Positions=(List<RoomInfo_Position>) roomInfoDao.findAllRoomInfo_Position(2,0,null,null,searchMap).get("rows");
		
		RoomInfo_Position roomInfo_Position=roomInfo_Positions.get(0);
		
		Map map=new HashMap<>();
		
		map.put("roomInfo", roomInfo_Position);
		
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setGUID(guid);
		
		List fileBytes=mobileDao.allRoomInfoImageByGUID(request, roomInfo);
		
		map.put("fileBytes", fileBytes);
		
		return map;
		
	}
	
	@RequestMapping("/getRoomInfoForGUID")
	public @ResponseBody Map<String, Object> getRoomInfoForGUID(@RequestParam String guid
			,HttpServletRequest request){
		Map searchMap=new HashMap<>();
		searchMap.put("[RoomInfo].GUID = ", guid);
		
		List<RoomInfo> roomInfos=roomInfoDao.findAllRoomInfo(2,0,null,null,searchMap);
		
		RoomInfo roomInfo=roomInfos.get(0);
		
		Map map=new HashMap<>();
		
		map.put("roomInfo", roomInfo);		
		
		return map;
		
	}
	
	@RequestMapping("/getChartInfoByGUID")
	public @ResponseBody Map getChartInfoByGUID(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			String search,HttpServletRequest request){
		
		String openId=( String ) request.getSession().getAttribute("openId");
		
		Users users=userService.getUserByOnlyOpenId(openId);
		
		String Charter=users.getCharter();
    	String IDNo=users.getIDNo();
		
    	Map searchMap=new HashMap<>();
    	
    	searchMap.put("[ChartInfo].IDNo=", IDNo);
    	   	    	
    	Map map=roomInfoDao.getChartInfoByGUID(limit, offset, sort, order, searchMap);
    	
    	return map;
    	
	}
	
	@RequestMapping("/getHireListByGUID")
	public @ResponseBody Map getHireListByGUID(@RequestParam Integer limit,@RequestParam Integer offset,
			@RequestParam String hireGUID,String sort,String order,
			String search,HttpServletRequest request){
		if(sort==""||sort.equals("")){
			sort="HireDate";
		}
		
		if(order==""||order.equals("")){
			order="asc";
		}
		
		Map searchMap=new HashMap<>();
    	
    	searchMap.put("[HireList].ChartGUID=", hireGUID);
    	   	    	
    	Map map=roomInfoDao.getHireListByGUID(limit, offset, sort, order, searchMap);
    	
    	return map;
    	
	}
	
	
	@RequestMapping("/getAllHire")
	public @ResponseBody Map getAllHire(){
		
		Double totalHire=roomInfoDao.getAllTotalHire();
		
		Double alreadyHire=roomInfoDao.getAlreadyHire();
		
		Double notHire=roomInfoDao.getNotHire();
		
		Map map=new HashMap<>();
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		map.put("totalHire", df.format(totalHire/10000));
		
		map.put("alreadyHire", df.format(alreadyHire/10000));
		
		map.put("notHire", df.format(notHire/10000));
		
		return map;
		
	}
	
	
	@RequestMapping("/findChartInfoByYear")
	public @ResponseBody List findChartInfoByYear(){
		
		return roomInfoDao.findChartInfoByYear();
		
	}
	
	
	@RequestMapping("/findHireListByYear")
	public @ResponseBody List findHireListByYear(){
		
		return roomInfoDao.findHireListByYear();
		
	}
	
	
	@RequestMapping("/getChartInfoByMonthOfYear")
	public @ResponseBody List getChartInfoByMonthOfYear(@RequestParam String year){
		
		List list=roomInfoDao.findChartInfoByMonthOfYear(year);
		
		Iterator iterator=list.iterator();
		
		List chartByMonth=new ArrayList<>();
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		while (iterator.hasNext()) {
			String month=(String) iterator.next();
			
			Double monthHire=roomInfoDao.getTotalHireByMonth(month);
			
			Map map=new HashMap<>();
			
			map.put("month", month);
			
			map.put("monthHire", df.format(monthHire/10000));
			
			chartByMonth.add(map);
		}
		
		return chartByMonth;
	}
	
	@RequestMapping("/getHireListByMonthOfYear")
	public @ResponseBody List getHireListByMonthOfYear(@RequestParam String year){
		System.out.println("year="+year);
		List list=roomInfoDao.findHireListByMonthOfYear(year);
		
		Iterator iterator=list.iterator();
		
		List hireByMonth=new ArrayList<>();
		
		DecimalFormat df = new DecimalFormat("0.00");
		
		while (iterator.hasNext()) {
			String month=(String) iterator.next();
			
			Double nothireMonth=roomInfoDao.getNotHireByMonth(month);
			
			Double alreadyhireMonth=roomInfoDao.getAlreadyHireByMonth(month);
			
			Map map=new HashMap<>();
			
			map.put("month", month);
			
			map.put("nothireMonth", df.format(nothireMonth/10000));
			
			map.put("alreadyhireMonth", df.format(alreadyhireMonth/10000));
			
			hireByMonth.add(map);
		}
		
		System.out.println("hireByMonth");
		MyTestUtil.print(hireByMonth);
		
		return hireByMonth;
	}
	
	@RequestMapping("/getAssetByHidden")
	public @ResponseBody Map getAssetByHidden(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			@RequestParam String hiddenGuid,HttpServletRequest request){
		
		Map searchMap=new HashMap<>();
		
		searchMap.put("[Hidden_Assets].hidden_GUID=", hiddenGuid);
		
		Map map=new HashMap<>();
		
		Map hidden_Assets_JoinMap=assetsDAO.findAssetByHideen(limit, offset, sort, order, searchMap);
		
		List hidden_Assets_Joins=(List) hidden_Assets_JoinMap.get("rows");
		
		map.put("rows", hidden_Assets_Joins);
		
		Iterator<Hidden_Assets_Join> iterator=hidden_Assets_Joins.iterator();
		
		List roominfos=new ArrayList<>();
		
		while (iterator.hasNext()) {
			Hidden_Assets_Join hidden_Assets_Join=iterator.next();
			RoomInfo roomInfo=new RoomInfo();
			
			roomInfo.setGUID(hidden_Assets_Join.getAsset_GUID());
			
			roominfos.add(roomInfo);
		}
		
		Map fileBytes=mobileDao.roomInfoImageQuery(request, roominfos);
		map.put("fileBytes", fileBytes);
		
		return map;
		
	}
	
	@RequestMapping("/insertHiddenAssets")
	public @ResponseBody Integer insertHiddenAssets(@RequestParam String guid,
			@RequestParam String hiddenGuid,HttpServletRequest request){
		
		Hidden_Assets hidden_Assets=new Hidden_Assets();
		
		hidden_Assets.setAsset_GUID(guid);
		hidden_Assets.setHidden_GUID(hiddenGuid);
		
		WechatSendMessageController wechatSendMessageController=new WechatSendMessageController();
		
		wechatSendMessageController.send(guid, hiddenGuid);
		
		return assetsDAO.insertIntoHidden_Assets(hidden_Assets);
		
	}
	
	@RequestMapping("/delHiddenAssets")
	public @ResponseBody Integer delHiddenAssets(@RequestParam String guid,
			@RequestParam String hiddenGuid,HttpServletRequest request){
		
		Hidden_Assets hidden_Assets=new Hidden_Assets();
		
		String[] where={"asset_GUID=",guid,"hidden_GUID=",hiddenGuid};
		
		hidden_Assets.setWhere(where);
		
		return assetsDAO.deleteHidden_Assets(hidden_Assets);
		
	}
	
	@RequestMapping("/getAllAssetByHidden_GUID")
	public @ResponseBody Integer getAllAssetByHidden_GUID(@RequestParam String guid){
		
		int count=assetsDAO.getAllAssetByHidden_GUID(guid);
		
		return count;
		
	}
	
	
	@RequestMapping("/getHiddenByAsset")
	public @ResponseBody Map getHiddenByAsset(@RequestParam Integer limit,@RequestParam Integer offset,String sort,String order,
			@RequestParam String assetGuid,HttpServletRequest request){
		
		Map searchMap=new HashMap<>();
		
		searchMap.put("[Hidden_Assets].asset_GUID=", assetGuid);
		
		Map map=new HashMap<>();
		
		Map hidden_Assets_JoinMap=assetsDAO.findHideenByAsset(limit, offset, sort, order, searchMap);
		
		List hidden_Joins=(List) hidden_Assets_JoinMap.get("rows");
		
		map.put("rows", hidden_Joins);
		

		Map fileBytes=mobileDao.hiddenImageQuery(request, hidden_Joins);
		map.put("fileBytes", fileBytes);
		
		return map;
		
	}
	
	@RequestMapping("/getChartInfoByChartGUID")
	public @ResponseBody Map<String, Object> getChartInfoByChartGUID(@RequestParam String chartGUID
			,HttpServletRequest request){
		
		Map map=new HashMap<>();
		
		ChartInfo chartInfo=assetsDAO.getChartInfoByChartGUID(chartGUID);
		
		map.put("chartInfo", chartInfo);
		
		return map;
		
	}
	
	@RequestMapping("/selectManageRegion")
	public @ResponseBody List selectHiddenLevel(){
		return assetsDAO.selectManageRegion();
	}
	
	@RequestMapping("/getWetchatAllUsers")
	public @ResponseBody List getWetchatAllUsers(){
		return userService.getWetchatAllUsers(1, 1, null, null, null, null);
	}
	
	@RequestMapping("/updatePositionByRoomInfo")
	public @ResponseBody Integer updatePositionByRoomInfo(
			HttpServletRequest request,ServletResponse response, 
			@RequestParam String imagename,@RequestParam String serverId,  
    		@RequestParam Integer campusId,@RequestParam String id,
    		@RequestParam String classType,
    		@RequestParam String GUID,
			@RequestParam Double lng,@RequestParam Double lat){
		
		int upload=0;
		
		String url="http://"+request.getServerName()+"/voucher/mobile/file/upload.do";
		
		System.out.println("url="+url);
		
		List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		
		reqParam.add(new BasicNameValuePair("imagename", imagename));
		reqParam.add(new BasicNameValuePair("serverId", serverId));
		reqParam.add(new BasicNameValuePair("campusId", String.valueOf(campusId)));
		reqParam.add(new BasicNameValuePair("id", id));
		reqParam.add(new BasicNameValuePair("classType", classType));
		
		HttpClient httpClient = new HttpClient();
		
		upload=Integer.parseInt(httpClient.doGet(url, reqParam));
		
		if(upload==1){
			
			boolean isUpdate=false;   //如果有位置就不更新
			
			Position position=new Position();		
			position.setGUID(GUID);
			position.setLat(lat);
			position.setLng(lng);

			Date date=new Date();
		    
		    position.setDate(date);
		    
			assetsDAO.updatePositionByRoomInfo(position,isUpdate);
		}
		
		return upload;
		
	}
	
	@RequestMapping("/insertAccess")
	public @ResponseBody Integer insertAccess(@RequestParam String page,
			@RequestParam Integer campusId,String guid,HttpServletRequest hrequest){
		Access access=new Access();
		
		String openId=( String ) hrequest.getSession().getAttribute("openId");
		
		access.setCampusId(campusId);
		access.setOpenId(openId);
		access.setPage(page);
		
		if(guid!=null&&!guid.equals("")){
			access.setGuid(guid);
		}
		
		Date date=new Date();
		
		access.setAccessTime(date);
		
		return userService.insertAccess(access);
	}
	
	@RequestMapping("/selectAllAccess")
	public @ResponseBody Map selectAllAccess(@RequestParam Integer limit,@RequestParam Integer offset,
			String sort,String order,
			String search,String page,HttpServletRequest request){
		
		Integer campusId=1;
		
		return userService.selectAllAccess(campusId, limit, offset, sort, order, search,page);
		
	}
	
}
