package com.voucher.weixin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Check;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Neaten_Join;
import com.voucher.manage.model.Users;
import com.voucher.manage.service.UserService;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TestDistance;
import com.voucher.sqlserver.context.Connect;

@Controller
@RequestMapping("/mobile/hidden")
public class HiddenController {

	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	MobileDAO mobileDao=(MobileDAO) applicationContext.getBean("mobileDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/selectAllHidden")
	public @ResponseBody Map selectAllHidden(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		if(!search.equals("")){
			searchMap.put("Hidden.name like", "%"+search+"%");
		}
		
		Map map=hiddenDAO.selectAllHidden_Jion(limit, offset, sort, order, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.hiddenImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectHiddenByGuid")
	public @ResponseBody Map selectHiddenByGuid(@RequestParam String guid,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		System.out.println("guid="+guid);
	    searchMap.put("Hidden.GUID = ", guid);
		
		Map map=hiddenDAO.selectAllHidden_Jion(10, 0, null, null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Join hidden_Join=(Hidden_Join) list.get(0);
		
		List fileBytes=mobileDao.allHiddenImageByGUID(request, hidden_Join);
		
		result.put("hidden", hidden_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectAllCheck")
	public @ResponseBody Map selectAllCheck(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,String search2,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		Map map;
		
		/*
		if(!search.equals("")){
			searchMap.put("check_name like", "%"+search+"%");
		}
		*/
		
		if(search!=null&&!search.equals("")){
			
			int d=(int) TestDistance.get(search);
			
			System.out.println("d="+d);
			
			if(d>0){
				
				Calendar cal = Calendar.getInstance();  
				cal.set(cal.get(Calendar.YEAR), d-1, 0, 0, 0, 0);  
		        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
				
				String startTime = null;
				
				String endTime=null;
				
				startTime=sdf.format(cal.getTime());
				
				cal.set(cal.get(Calendar.YEAR), d, 0, 0, 0, 0);
		        
		        endTime=sdf.format(cal.getTime());
				
		        System.out.println("searchMap="+searchMap);
		        
				searchMap.put("convert(varchar(11),[Hidden_Check].date,120 ) >", startTime);
				searchMap.put("convert(varchar(11),[Hidden_Check].date,120 ) <", endTime);
				
				System.out.println("cal.getActualMinimum(Calendar.DAY_OF_MONTH="+startTime);
				
				System.out.println("searchMap="+searchMap);
				
				map=hiddenDAO.selectAllHiddenCheck(limit, offset, sort, order,null, searchMap);

				List list=(List) map.get("rows");
				
				int total=(int) map.get("total");
				
				Map fileBytes=mobileDao.checkImageQuery(request,list);
				
				Map result=new HashMap<>();
				
				result.put("hidden_Check", list);
				result.put("total", total);
				result.put("fileBytes", fileBytes);
				
				return result;
				
			}else{
				searchMap.put("check_name like", "%"+search+"%");
			}
	
		}
		
		if(search2!=null&&!search2.equals("")){
			
			Calendar cal = Calendar.getInstance();  
	        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
	        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
			
			String startTime = null;
			
			startTime=sdf.format(cal.getTime());
			
			searchMap.put("convert(varchar(11),[Hidden_Check].date,120 ) >", startTime);
			
			System.out.println("startTime="+startTime);
		}
		
		map=hiddenDAO.selectAllHiddenCheck(limit, offset, sort, order,search, searchMap);
		
		List list=(List) map.get("rows");
		
		int total=(int) map.get("total");
		
		Map fileBytes=mobileDao.checkImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden_Check", list);
		result.put("total", total);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectCheckByCheckId")
	public @ResponseBody Map selectCheckByCheckId(@RequestParam String check_id,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("[Hidden_Check].check_id = ", check_id);
		
		Map map=hiddenDAO.selectAllHiddenCheck(1, 0, null, null,null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Check_Join hidden_Check_Join=(Hidden_Check_Join) list.get(0);
		
		List fileBytes=mobileDao.allCheckImageByGUID(request, hidden_Check_Join);
		
		result.put("hidden_Check", hidden_Check_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	
	@RequestMapping("/selectAllNeaten")
	public @ResponseBody Map selectAllNeaten(@RequestParam Integer limit, @RequestParam Integer offset, 
			String sort, String order,
			@RequestParam String search,HttpServletRequest request) {
		Map searchMap=new HashMap<>();
		
		if(!search.equals("")){
			searchMap.put("[Hidden_Neaten].neaten_name like", "%"+search+"%");
		}
		
		Map map=hiddenDAO.selectAllHiddenNeaten(limit, offset, sort, order, searchMap);
		
		List list=(List) map.get("rows");
		
		Map fileBytes=mobileDao.neatenImageQuery(request,list);
		
		Map result=new HashMap<>();
		
		result.put("hidden_Neaten", list);
		result.put("fileBytes", fileBytes);
		
		return result;
	}
	
	@RequestMapping("/selectNeatenByNeatenId")
	public @ResponseBody Map selectNeatenByNeatenId(@RequestParam String neaten_id,HttpServletRequest request){
        Map searchMap=new HashMap<>();
		
	    searchMap.put("[Hidden_Neaten].neaten_id = ", neaten_id);
		
		Map map=hiddenDAO.selectAllHiddenNeaten(10, 0, null, null, searchMap);
		
		List list=(List) map.get("rows");
		
		Map result=new HashMap<>();
		
		Hidden_Neaten_Join hidden_Neaten_Join=(Hidden_Neaten_Join) list.get(0);
		
		List fileBytes=mobileDao.allNeatenImageByGUID(request, hidden_Neaten_Join);
		
		result.put("hidden_Neaten", hidden_Neaten_Join);
		result.put("fileBytes", fileBytes);
		MyTestUtil.print(fileBytes);
		
		return result;
	}
	
	
	@RequestMapping("/selectHiddenLevel")
	public @ResponseBody List selectHiddenLevel(){
		return hiddenDAO.setctAllHiddenLevel();
	}
	
	@RequestMapping("/updateHidden")
	public @ResponseBody Integer updateHidden(@RequestParam String guid,
			@RequestParam String name,@RequestParam String level,
			@RequestParam String manageRegion,
			@RequestParam String happenTime,@RequestParam String remark,
			@RequestParam String detail,@RequestParam Double lng,
			@RequestParam Double lat){
		
		Hidden hidden=new Hidden();

		String[] where={"[Hidden].GUID=",guid};
		hidden.setWhere(where);
		
		hidden.setName(name);
		if(level!=null)
		 hidden.setHidden_level(level);
		
		if(manageRegion!=null&&!manageRegion.equals("")){
			hidden.setManageRegion(manageRegion);
		}
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden.setHappen_time(date);
				System.out.println("thisdate="+date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hidden.setRemark(remark);
		hidden.setDetail(detail);
		
		Date date2=new Date();
		hidden.setUpdate_time(date2);
				
		int i=hiddenDAO.updateHidden(hidden);
		
		Position position=new Position();
		
	    position.setGUID(guid);
		position.setLat(lat);
		position.setLng(lng);
		
		int count=assetsDAO.countPositionByGUID(position);
		
		if(count==0){
			assetsDAO.updatePosition(position);
		}
		
		return i;
	}
	
	
	@RequestMapping("/insertHidden")
	public @ResponseBody Map insertHidden(
			@RequestParam String name,@RequestParam String level,
			@RequestParam String manageRegion,
			@RequestParam String happenTime,@RequestParam String remark,
			@RequestParam String detail,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden hidden=new Hidden();

        UUID uuid=UUID.randomUUID();
        
        String openId=( String ) request.getSession().getAttribute("openId");
        
        Users users=userService.getUserByOnlyOpenId(openId);
        
        String userName=users.getName();
        
        hidden.setUserName(userName);
        
        hidden.setCampusAdmin(openId);
        
        hidden.setGUID(uuid.toString());
		
		hidden.setName(name);
		if(level!=null)
		 hidden.setHidden_level(level);
		if(manageRegion!=null&&!manageRegion.equals("")){
			hidden.setManageRegion(manageRegion);
		}
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden.setHappen_time(date);
				System.out.println("thisdate="+date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hidden.setRemark(remark);
		hidden.setDetail(detail);
		
		Date date2=new Date();
		hidden.setUpdate_time(date2);
		hidden.setDate(date2);
		hidden.setTerminal("Wechat");
		int i=hiddenDAO.insertIntoHidden(hidden);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");		
		
		Position position=new Position();
		
		position.setGUID(uuid.toString());
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		
		position.setDate(date2);
		
		assetsDAO.updatePosition(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("guid", uuid.toString());
		
		return map;
		
	}
	
	
	@RequestMapping("/insertHiddenCheck")
	public @ResponseBody Map insertHiddenCheck(
			@RequestParam String guid,@RequestParam String check_name,
			@RequestParam String happenTime,@RequestParam String remark,
			@RequestParam String check_circs,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Check hidden_Check=new Hidden_Check();

        UUID uuid=UUID.randomUUID();
        
        String openId=( String ) request.getSession().getAttribute("openId");
        
        hidden_Check.setCampusAdmin(openId);

        hidden_Check.setCheck_id(uuid.toString());
        
        hidden_Check.setGUID(guid);
		
        hidden_Check.setCheck_name(check_name);
        
        hidden_Check.setRemark(remark);
        
        hidden_Check.setCheck_circs(check_circs);

		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Check.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Check.setUpdate_time(date);
		hidden_Check.setDate(date);
		hidden_Check.setTerminal("Wechat");
		
		int i=hiddenDAO.insertHiddenCheck(hidden_Check);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setCheck_id(uuid.toString());
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		position.setDate(date);
		
		assetsDAO.updatePosition(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("check_id", uuid.toString());
		
		position.setCheck_id(null);
		position.setGUID(guid);
		
		boolean isUpdate=false;   //如果有位置就不更新
		
		assetsDAO.updatePositionByRoomInfo(position,isUpdate); //更新资产位置
		
		//更新安全巡查时间
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setHidden_check_date(date);
		
		String[] where={Singleton.ROOMDATABASE+".[dbo].[RoomInfo].GUID = ",guid};
		
		roomInfo.setWhere(where);
		
		roomInfoDao.updateRoomInfo(roomInfo);
		
		return map;
		
	}
	
	
	@RequestMapping("/updateHiddenCheck")
	public @ResponseBody Map updateHiddenCheck(
			@RequestParam String check_id,@RequestParam String check_name,
			@RequestParam String happenTime,@RequestParam String remark,
			@RequestParam String check_circs,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Check hidden_Check=new Hidden_Check();
       		
        hidden_Check.setCheck_name(check_name);
        
        hidden_Check.setRemark(remark);
        
        hidden_Check.setCheck_circs(check_circs);

        String[] where={"[Hidden_Check].check_id=",check_id};
        
        hidden_Check.setWhere(where);
        
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Check.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Check.setUpdate_time(date);
		hidden_Check.setDate(date);
		
		int i=hiddenDAO.updateHiddenCheck(hidden_Check);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setCheck_id(check_id);
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		
		//assetsDAO.updatePosition(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("check_id", check_id);
		
		position.setCheck_id(null);
		
		//assetsDAO.updatePositionByRoomInfo(position); //更新资产位置
		
		return map;
		
	}
	
	@RequestMapping("/selectNameBycampusAdmin")
	public @ResponseBody Map selectNameBycampusAdmin(@RequestParam String campusAdmin){
		
		Map map=new HashMap<>();
		
		Users users=userService.getUserByOnlyOpenId(campusAdmin);
		
		map.put("name",users.getName());
		
		return map;
		
	}
	
	@RequestMapping("/insertHiddenNeaten")
	public @ResponseBody Map insertHiddenNeaten(
			@RequestParam String guid,
			@RequestParam String neaten_name,@RequestParam Double progress,
			@RequestParam String state,
			@RequestParam String happenTime,@RequestParam String principal,
			@RequestParam String remark,
			@RequestParam String neaten_instance,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Neaten hidden_Neaten=new Hidden_Neaten();

        UUID uuid=UUID.randomUUID();
        
        String openId=( String ) request.getSession().getAttribute("openId");
        
        hidden_Neaten.setGUID(guid);
        
        hidden_Neaten.setNeaten_id(uuid.toString());
        
        hidden_Neaten.setNeaten_name(neaten_name);
        
        hidden_Neaten.setPrincipal(principal);
        
        hidden_Neaten.setRemark(remark);
        
        hidden_Neaten.setNeaten_instance(neaten_instance);

        hidden_Neaten.setCampusAdmin(openId);
        
        Users users=userService.getUserByOnlyOpenId(openId);
        
        String userName=users.getName();
        
        hidden_Neaten.setUserName(userName);
        
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Neaten.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Neaten.setUpdate_time(date);
		hidden_Neaten.setDate(date);
		hidden_Neaten.setTerminal("Wechat");
		
		int i=hiddenDAO.insertHiddenNeaten(hidden_Neaten);
		
		Hidden hidden=new Hidden();
		
		hidden.setProgress(progress);
		
		hidden.setState(state);
		
		String[] where={"[Hidden].GUID=",guid};
		
		hidden.setWhere(where);
		
		i=hiddenDAO.updateHidden(hidden);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setNeaten_id(uuid.toString());
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		
		assetsDAO.updatePositionByNeaten(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("neaten_id", uuid.toString());
				
		return map;
		
	}
	
	
	@RequestMapping("/updateHiddenNeaten")
	public @ResponseBody Map updateHiddenNeaten(
			@RequestParam String guid,@RequestParam String neaten_id,
			@RequestParam String neaten_name,@RequestParam Double progress,
			@RequestParam String happenTime,@RequestParam String principal,
			@RequestParam String remark,
			@RequestParam String neaten_instance,@RequestParam String addComp,
			@RequestParam Double lng,@RequestParam Double lat,
			HttpServletRequest request){
		
		Hidden_Neaten hidden_Neaten=new Hidden_Neaten();
      
        String openId=( String ) request.getSession().getAttribute("openId");
        
        hidden_Neaten.setNeaten_id(neaten_id);
        
        hidden_Neaten.setNeaten_name(neaten_name);
        
        hidden_Neaten.setPrincipal(principal);
        
        hidden_Neaten.setRemark(remark);
        
        hidden_Neaten.setNeaten_instance(neaten_instance);

        hidden_Neaten.setCampusAdmin(openId);
        
		if(happenTime!=null&&!happenTime.equals("")){
			try {
				DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
				Date date;		
				date = fmt.parse(happenTime);	
				hidden_Neaten.setHappen_time(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Date date=new Date();
		hidden_Neaten.setUpdate_time(date);
		hidden_Neaten.setDate(date);
		hidden_Neaten.setTerminal("Wechat");
		
		String[] where={"[Hidden_Neaten].neaten_id=",neaten_id};
		
		hidden_Neaten.setWhere(where);
		
		int i=hiddenDAO.updateHiddenNeaten(hidden_Neaten);
		
		Hidden hidden=new Hidden();
		
		hidden.setProgress(progress);
		
		String[] where2={"[Hidden].GUID=",guid};
		
		hidden.setWhere(where2);
		
		i=hiddenDAO.updateHidden(hidden);
		
		JSONObject jsonObject=JSONObject.parseObject(addComp);
		
		String province=jsonObject.getString("province");		
		String city=jsonObject.getString("city");		
		String district=jsonObject.getString("district");		
		String street=jsonObject.getString("street");		
		String streetNumber=jsonObject.getString("streetNumber");	
		
		Position position=new Position();
		
		position.setNeaten_id(neaten_id);
		position.setLat(lat);
		position.setLng(lng);
		
		position.setProvince(province);
		position.setCity(city);
		position.setDistrict(district);
		position.setStreet(streetNumber);
		position.setStreet_number(streetNumber);
		
		assetsDAO.updatePositionByNeaten(position);
		
		Map map=new HashMap<>();
		
		map.put("status", i);
		map.put("neaten_id", neaten_id);
				
		return map;
		
	}
	
	@RequestMapping("/hiddenStat")
	public @ResponseBody Map hiddenStat(){
		
		int inHidden=assetsDAO.findInHidden();
		
		int notHidden=assetsDAO.findNotHidden();
		
		int successHidden=assetsDAO.findSuccessHidden();
		
		Map search=new HashMap<>();
		
		search.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State=","已出租");
		
		int alreadyHire=roomInfoDao.getRoomInfoCount(search);
		
		search=new HashMap<>();
		
		search.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State=","空置");
		
		int notHire=roomInfoDao.getRoomInfoCount(search);
		
		search=new HashMap<>();
		
		search.put(Singleton.ROOMDATABASE+".[dbo].[RoomInfo].State=","不可出租");
		
		int catnotHire=roomInfoDao.getRoomInfoCount(search);
		
		int allAsset=alreadyHire+notHire+catnotHire;
		
		int allAssetsHidden=assetsDAO.findAllAssetsHidden();
		
		Map map=new HashMap<>();
		
		map.put("inHidden", inHidden);
		
		map.put("notHidden", notHidden);
		
		map.put("successHidden", successHidden);
		
		map.put("allAsset", allAsset);
		
		map.put("allAssetsHidden", allAssetsHidden);
		
		return map;
		
	}
	
	@RequestMapping("/findHiddenByYear")
	public @ResponseBody List findHiddenByYear(){
		
		List list=assetsDAO.findHiddenByYear();
		
		return list;
		
	}
	
	@RequestMapping("/findHiddenByMonthOfYear")
	public @ResponseBody List findHiddenByMonthOfYear(@RequestParam String year){
		
		List list=assetsDAO.findHiddenByMonthOfYear(year);
		
		return list;
		
	}
	
	@RequestMapping("/getAllAssetByHidden_GUID")
	public @ResponseBody Integer getAllAssetByHidden_GUID(@RequestParam String guid){
		
		int count=hiddenDAO.getAllAssetByHidden_GUID(guid);
		
		return count;
		
	}
	
}
