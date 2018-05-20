package com.voucher.manage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.sqlserver.context.Connect;

import voucher.Mybatis;

@Controller
@RequestMapping("/baiduMap")
public class BaiduMapController {

	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	@RequestMapping("/get")
	public @ResponseBody List test(String manageRegion) {		
		
		Map map;
		if(manageRegion!=null){
		   map=assetsDAO.findAllPosition(manageRegion);
		}else{
		   map=assetsDAO.findAllPosition("");
		}
		
		//MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		return list;
	}
	
	@RequestMapping("/getPosition")
	public @ResponseBody JSONObject getPosition() {
		JSONObject jsonObject=new JSONObject();
		Position position=new Position();
		position.setLimit(10);
		position.setOffset(0);		
		position.setSort("date");
		position.setOrder("desc");
		position.setNotIn("id");
        		
		try{
			position=(Position) assetsDAO.findPosition(position).get(0);
			jsonObject.put("lat", position.getLat());
			jsonObject.put("lng", position.getLng());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}
	
	@RequestMapping("/location")
	public @ResponseBody JSONObject baiduSwitch(HttpServletRequest request){
		JSONObject jsonObject=null;
		String requestUrl = "http://api.map.baidu.com/location/ip?ak=pQFgFpS0VnMXwCRN6cTc1jDOcBVi3XoD&coor=bd09ll";
		
		HttpGet g = new HttpGet(requestUrl);
    	RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
    	CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    	CloseableHttpResponse r;
    	String content = null;
		try {
			r = httpClient.execute(g);
			content = EntityUtils.toString(r.getEntity());
	        r.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		jsonObject=JSONObject.parseObject(content);
  		
		return jsonObject;
		
	}
	
	@RequestMapping("/getByDistance")
	public @ResponseBody List getByDistance(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){
		
		Map map=assetsDAO.findHiddenByDistance(limit, offset, lng, lat, "");
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		return list;
	}
	
	@RequestMapping("/getByPoint")
	public @ResponseBody List getByPoint(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){
		
		Map map=assetsDAO.findHiddenByPoint(lng, lat, distance, "");
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		return list;
	}
	
	@RequestMapping("/getByDistanceImg")
	public @ResponseBody Map getByDistanceImg(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,String search,HttpServletRequest request){
		System.out.println("search="+search);
		if(search==null)
			search="";
		
		Map map=assetsDAO.findHiddenByDistance(limit, offset, lng, lat, search);
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		Map fileBytes=mobileDao.positionHiddenImageQuery(request, list);
		
		Map result=new HashMap<>();
		
		result.put("hidden", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/getAssetsByDistance")
	public @ResponseBody Map getAssetsByDistance(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){

		Map map=assetsDAO.findAssetByDistance(limit, offset, lng, lat,"");
	
		List list=(List) map.get("row");

		Map result=new HashMap<>();
		
		result.put("rows", list);
		
		return result;
	}
	
	@RequestMapping("/getAssetsByPoint")
	public @ResponseBody Map getAssetsByPoint(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,HttpServletRequest request){

		Map map=assetsDAO.findAssetByPoint(lng, lat, distance, "");
	
		List list=(List) map.get("row");

		Map result=new HashMap<>();
		
		result.put("rows", list);
		
		return result;
	}
	
	@RequestMapping("/getAssetsByDistanceImg")
	public @ResponseBody Map getAssetsByDistanceImg(Integer limit,Integer offset,Double lng,Double lat,
			Double distance,String search,HttpServletRequest request){
		System.out.println("search="+search);
		if(search==null)
			search="";
		
		Map map=assetsDAO.findAssetByDistance(limit, offset, lng, lat, search);
		
		MyTestUtil.print(map);
		
		List list=(List) map.get("row");
		
		Map fileBytes=mobileDao.roomInfo_PositionImageQuery(request, list);
		
		Map result=new HashMap<>();
		
		result.put("rows", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("getManageRegion")
	public @ResponseBody List getManageRegion(){
		
		return assetsDAO.selectManageRegion();
		
	}
	
	
	@RequestMapping("getGUIDByPosition")
	public @ResponseBody Map getGUIDByPosition(@RequestParam Double lng,
			@RequestParam Double lat){
		  
		Map searchMap=new HashMap<>();
        
        searchMap.put("[Position].lng=", String.valueOf(lng));
        searchMap.put("[Position].lat=", String.valueOf(lat));
        
        List list=hiddenDAO.selectHiddenOfMap(searchMap);
        
        Hidden_Join hidden_Join=(Hidden_Join) list.get(0);
		
        String GUID=hidden_Join.getGUID();
        
        Map map=new HashMap<>();
        
        map.put("guid", GUID);
        
        return map;
        
	}
	
	@RequestMapping("getAssetGUIDByPosition")
	public @ResponseBody Map getAssetGUIDByPosition(@RequestParam Double lng,
			@RequestParam Double lat){
		  
		Map searchMap=new HashMap<>();
        
        searchMap.put("[Position].lng=", String.valueOf(lng));
        searchMap.put("[Position].lat=", String.valueOf(lat));

        Map map=roomInfoDao.findAllRoomInfo_Position(2, 0, null, null, searchMap); 
        
        List list=(List) map.get("rows"); 
        
        RoomInfo_Position roomInfo_Position=(RoomInfo_Position) list.get(0);
        
        Map map2=new HashMap<>();
        
        map2.put("guid", roomInfo_Position.getGUID());
        
        return map2;
        
	}
	
	@RequestMapping("/getAllAsset")
	public @ResponseBody List getAllAsset(String manageRegion){
	
		Map where = new HashMap<>();
	
		if(manageRegion!=null&&!manageRegion.equals("")){
			where.put("[Position].GUID !=","''");
			where.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].ManageRegion = ", manageRegion);
		}else{
			where.put("[Position].GUID !=","''");
		}
		
		Map map=roomInfoDao.findAllRoomInfo_Position(1000, 0, null, null, where);
	
		List list=(List) map.get("rows");
		
		return list;
		
	}
	
	
	@RequestMapping("/getAllCheckByOpenId")
	public @ResponseBody Map getAllCheckByOpenId(@RequestParam String openId,
			String datepicker, String datepicker2){
				
		Calendar calendar; 
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
		String startTime = null;
		String endTime = null;
		
		Date sTime = null;
		Date eTime = null;
		
		try {
			if(datepicker!=null&&!datepicker.equals("")){
				sTime=sdf.parse(datepicker);
				startTime=sdf.format(sTime);
				System.out.println("startTime="+startTime);
			}
			if(datepicker2!=null&&!datepicker2.equals("")){
				eTime=sdf.parse(datepicker2);			
				endTime=sdf.format(eTime);	
				System.out.println("endTime="+endTime);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(startTime==null||startTime.equals("")){
			
			calendar=Calendar.getInstance();
			
			calendar.set(Calendar.HOUR_OF_DAY, 0);  
		    calendar.set(Calendar.SECOND, 0);  
		    calendar.set(Calendar.MINUTE, 0);  
		    calendar.set(Calendar.MILLISECOND, 0); 
			
			calendar.add(Calendar.DATE, -1);
			
			Date start=calendar.getTime();
			
			startTime=sdf.format(start);			
		}
		
		
		if(endTime==null||endTime.equals("")){
			
			calendar=Calendar.getInstance();
			
			calendar.set(Calendar.HOUR_OF_DAY, 0);  
		    calendar.set(Calendar.SECOND, 0);  
		    calendar.set(Calendar.MINUTE, 0);  
		    calendar.set(Calendar.MILLISECOND, 0); 
			
			Date end=calendar.getTime();
			
			endTime=sdf.format(end);	
			
		}
		
		Map searchMap=new HashMap<>();
		
		searchMap.put("[Hidden_Check].campusAdmin=", openId);	
		searchMap.put("[Position].check_id !=", "");
		searchMap.put("convert(varchar(11),[Hidden_Check].date,120)>",startTime);
		searchMap.put("convert(varchar(11),[Hidden_Check].date,120)<=",endTime);
		
		Map map=hiddenDAO.selectAllHiddenCheck(1000, 0, "date", "asc","", searchMap);
		
		/*
		System.out.println("datepicker="+datepicker+" "+datepicker2);
		
		System.out.println("openid="+openId);
		System.out.println("time="+startTime+"   "+endTime);
		
		MyTestUtil.print(map);
		*/
		
		return map;
		
	}
	
}
