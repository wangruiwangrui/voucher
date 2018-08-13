package com.voucher.manage.controller;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.activemq.transport.stomp.Stomp.Headers.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.rmi.server.AssetsImpl;
import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.dao.FinanceDAO;
import com.voucher.manage.dao.HiddenDAO;
import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoImpl.HiddenDAOImpl;
import com.voucher.manage.daoModelJoin.Finance.HireList_ChartInfo_Join;
import com.voucher.manage.redis.Orders;
import com.voucher.manage.redis.RedisDao;
import com.voucher.manage.service.AffairService;
import com.voucher.sqlserver.context.Connect;
import com.voucher.weixin.controller.WechatSendMessageController;


@Controller
@RequestMapping("/test")
public class testController {
	
	ApplicationContext applicationContext=new Connect().get();
	
	HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
	
	AssetsDAO assetsDAO=(AssetsDAO) applicationContext.getBean("assetsdao");
	
	FinanceDAO financeDAO=(FinanceDAO) applicationContext.getBean("financeDao");
	
	RoomInfoDao roomInfoDao=(RoomInfoDao) applicationContext.getBean("roomInfodao");
	
     private AffairService testService;
	
     
	/*
     private RedisDao orderDao;
 	
 	@Autowired
 	public void setOrderDao(RedisDao orderDao) {
 		this.orderDao = orderDao;
 	}
 	*/
   
    @RequestMapping("/roomPosition")
    public @ResponseBody Map<String, Object> roomPosition(@RequestParam Double lat,
    		@RequestParam Double lng){
    	return roomInfoDao.findRoomInfoPositionByLatLng(lat, lng);
    }
     
	@Transactional(rollbackFor = { Exception.class })
	@Autowired
	public void setTestService(AffairService testService) {
		this.testService=testService;
	}
	
	
	@RequestMapping("/finan")
	public @ResponseBody Map finan(@RequestParam Integer days,@RequestParam Integer limit,@RequestParam
			Integer offset,String search){
		
		Map searchMap=new HashMap<>();
		
		if(search!=null&&!search.equals("")){
			
			search="%"+search+"%";
		
			searchMap.put("Charter like ", search);
		
		}
		
		return financeDAO.findMatureHire(days, limit, offset, "HireDate", "asc", searchMap);
	}
	
	@RequestMapping("/chartInfo")
	public @ResponseBody Map charInfo(@RequestParam Integer limit,@RequestParam
			Integer offset){
		return financeDAO.findOverdueChartInfo(limit, offset, "", "", 0, null);
	}
	
	@RequestMapping("/findMatureHireClew")
	public @ResponseBody int findMatureHireClew(@RequestParam Integer days){
		return financeDAO.findMatureHireClew("oKAqL1ndJNnmviHZlFuNOcKfJPXk",days);
	}
	
	@RequestMapping("/findOverdueChartInfoClew")
	public @ResponseBody int findOverdueChartInfoClew(){
		return financeDAO.findOverdueChartInfoClew("oKAqL1ndJNnmviHZlFuNOcKfJPXk");
	}
	
	@RequestMapping("/send")
	public @ResponseBody Integer Send(){
		Map map=financeDAO.findMatureHire(15, 2, 0, "guid", "", null);
		List list=(List) map.get("rows");
		
		Iterator<HireList_ChartInfo_Join> iterator=list.iterator();
		
		String key2="";
		
		while (iterator.hasNext()) {
			HireList_ChartInfo_Join hireList_ChartInfo_Join=iterator.next();
			key2=key2+","+hireList_ChartInfo_Join.getRoomAddress();
		}
		
		Integer place=4;
		String Template_Id="yArnh7Tjzx07fXuuzz_AMd-gDa2Vo6eC3IH9dvqGjLA";
		String Send_Type="租金到期提醒";
		String first_data=" ";
		
		String allHire=(String) map.get("allHire");
		Integer count= (Integer) map.get("count");
		
		String keyword1_data="共"+allHire+","+count+"户";		
		
		key2=key2+"......";
		
		String keyword2_data=key2;
		
		String matureTime=(String) map.get("matureTime");
		
		String keyword3_data=matureTime;
		String keyword4_data=null;
		String url="http://lzxlzc.com/voucher/mobile/assetAdmin/assetFinance/15matureChartInfo.html";
		String remark_data="查看详情";
		
		try{
		 new WechatSendMessageController().sendMessage(place, Template_Id, Send_Type,url, first_data, keyword1_data, keyword2_data, keyword3_data, keyword4_data, remark_data);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}
	
	@RequestMapping("/aaa")
	public @ResponseBody
	String aaa() {
		
		HiddenDAO hiddenDAO=(HiddenDAO) applicationContext.getBean("hiddenDao");
		
		hiddenDAO.selectAllHidden(10, 0, null, "asc", new HashMap<>());

		return "aaa";
		 
	}
	
	
	
	@RequestMapping("affair1")
	public @ResponseBody
	Integer affair1() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 1);
		paramMap.put("val", "dddddd");
		
		int i=0;
		/*
		i=testService.insertAll(paramMap);	 
       */
		
		System.out.println(testService);
		
		i=testService.insert1(paramMap);
		
		
		return i;
	}

	
	@RequestMapping("affair2")
	public @ResponseBody
	Integer affair2() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 2);
		paramMap.put("val", "fffffffff");
		
		int i=0;
		
	    i=testService.insert2(paramMap);
    
		return i;
	}


	/*
	@RequestMapping("save")
	public @ResponseBody String save(@RequestParam String id,@RequestParam 
			String name){
		Orders order=new Orders();
		order.setId(id);
		order.setName(name);
		
		orderDao.save(order);
		
		return "1";
		
	}
	
	
	@RequestMapping("read")
	public @ResponseBody String read(@RequestParam String id){
		Orders order= (Orders) orderDao.read(id);
		
		return order.getName();
	}
	
	@RequestMapping("getAll")
	public @ResponseBody Map getAll(){
		Map<String, Object> map=new HashMap();
		Set set= orderDao.getAll();
		Iterator<String> iterator=set.iterator();
		while (iterator.hasNext()) {
			String key=iterator.next();
			Orders order=(Orders) orderDao.read(key);
			map.put(key, order);			
		}
		return map;
	}
	
	@RequestMapping("del")
	public @ResponseBody Integer del(@RequestParam String id){
		orderDao.delete(id);
		return 1;
	}
	
	@RequestMapping("delAll")
	public @ResponseBody Set delAll(){
		Set set= orderDao.getAll();
		orderDao.deleteAll();
		return set;
	}
	*/
	
	
	@RequestMapping("getAllCheck")
	public @ResponseBody Map getAllCheck(@RequestParam Integer limit,@RequestParam
			Integer offset){
		
		return hiddenDAO.selectAllHiddenCheck(limit, offset, null, null, null, new HashMap<>());
		
	}
	
	
	@RequestMapping("getTestOr")
	public @ResponseBody Map getTestOr(@RequestParam String address,@RequestParam
			String Num){
		
		Map search=new HashMap<>();
		
		search.put("[TTT].[dbo].[RoomInfo].Address like", "%"+address+"%");
		search.put("[TTT].[dbo].[RoomInfo].Num like", "%"+Num+"%");
		
		return assetsDAO.getTestOr(search);
		
	}
	
}
