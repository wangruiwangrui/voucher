package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.Floor;
import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoModel.RoomChartLog;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.RoomInfoRowMapper;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.HireList;
import com.voucher.manage.daoModelJoin.RoomChangeHireLog_RoomChartLog;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoRowMapper.RowMappers;
import com.voucher.manage.daoRowMapper.RowMappersJoin;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe;
import com.voucher.manage.daoSQL.SelectSQL;
import com.voucher.manage.daoSQL.SelectSQLJoin;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TransMapToString;

import voucher.UpdateSql;

public class RoomInfoDaoImpl extends JdbcDaoSupport implements RoomInfoDao{

	
	@Override
	public List<RoomInfo> findAllRoomInfo(Integer limit, Integer offset, String sort,
			String order,Map search) {
		// TODO Auto-generated method stub
		/*
		String sql="SELECT top "+ limit+" [GUID],[Num],[OriginalNum],[Address],[OriginalAddress],[Region],[Segment],[ManageRegion]"+
                    ",[RoomProperty],[Useful],[Floor],[State],[Structure],[BuildArea],[RoomType],[IsCity],[Manager],[ManagerPhone]"+
                    ",[IsStreet],[FitMent],[BeFrom],[InDate],[PropertyRightNo],[PropertyRightArea],[DesignUseful],[BuildYear],[PropertyRightUnit]"+
                    ",[RealPropertyRightUnit],[PropertyCardUnit],[GlebeCardUnit],[TransferUnit],[GlebeCardNo],[GlebeUseType],[GlebeEndDate]"+
                    ",[GlebeTypeUseful],[PlanUseful],[BefromFile],[NoPRNResion],[NoGCResion],[RealEstateNo],[PropertyMemo]"+
                    ",[OriginalAmount],[AllDepreciation],[DepreciationYear],[NetWorth],[EvaluationPrice],[EvaluationSinglePrice]"+
                    ",[EvaluationPlace],[BefromAmount],[MarketHire],[EvaluationUnit],[EvaluationNo],[IsPawn],[PawnUnit],[OriginalUnit]"+
                    ",[FinanceMemo],[Utility],[ChartGUID],[AddressCode],[OriginalAddressCode],[SecurityClassification],[DangerClassification]"+
                    ",[HiddenDanger],[ResponsiblePerson],[sMemo],[BelongUnit],[FileIndex],[SecurityRegion],[RoomCount],[LandArea],[UseYears]"+
                    "FROM [TTT].[dbo].[RoomInfo]"+
                    "where [GUID]"+
                    "not in("+
                    "select top "+offset+"[GUID] FROM [TTT].[dbo].[RoomInfo])";
		*/
		
		
		RoomInfo roomInfo=new RoomInfo();
		roomInfo.setLimit(limit);
		roomInfo.setOffset(offset);
		roomInfo.setNotIn("[GUID]");
		/*
		if(search!=null&&!search.trim().equals("")){
		  String[] where={"Address like ",search};
		  roomInfo.setWhereTerm("OR");
		  roomInfo.setWhere(where);
		}
		*/
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    roomInfo.setWhereTerm("OR");
		    roomInfo.setWhere(where);
		}
		
		System.out.println("impl sort="+sort+ "   order="+order);

		if(sort!=null)
			roomInfo.setSort(sort);
		if(order!=null)
			roomInfo.setOrder(order);
		
       /*
        String sql="";
        Map<String,Object> map=new HashMap<>();
		try {
			map = SelectSQL.get(roomInfo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sql=(String) map.get("sql");
		List params=(List) map.get("params");
		
		//return this.getJdbcTemplate().query(sql, new RoomInfoRowMapper());
		List list= this.getJdbcTemplate().query(sql,params.toArray(),new RowMappers(RoomInfo.class));
		*/
		
		List list=SelectExe.get(this.getJdbcTemplate(), roomInfo);
	
		MyTestUtil.print(list);
	
		return list;
	}

	@Override
	public Integer getRoomInfoCount(Map search) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<>();
		//String sql="SELECT count(*) FROM [TTT].[dbo].[RoomInfo]";
		
		RoomInfo roomInfo=new RoomInfo();
		/*
		if(search!=null&&!search.trim().equals("")){
			String[] where={"Address like ",search};
			  roomInfo.setWhereTerm("OR");
			  roomInfo.setWhere(where);
			}
			*/
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    roomInfo.setWhereTerm("OR");
		    roomInfo.setWhere(where);
		}
		
		 String sql="";
	        Map<String,Object> map2=new HashMap<>();
		try {
			map2 = SelectSQL.getCount(roomInfo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql=(String) map2.get("sql");
		System.out.println(sql);
		List params=(List) map2.get("params");
		map=this.getJdbcTemplate().queryForMap(sql,params.toArray());
	
		/*
		Set  set=map.entrySet();       	    
		Iterator iterator=set.iterator();       		    
		while (iterator.hasNext()){       		    
		    Map.Entry  mapentry = (Map.Entry) iterator.next();       		    
		    System.out.println(mapentry.getKey()+"/"+ mapentry.getValue());       		    
		}   
		*/
		
		return (Integer) map.get("");
	}

	@Override
	public Map<String, Object> findAllChangehire_CharLog(Integer limit, Integer offset, String sort, String order,
			String search) {
		// TODO Auto-generated method stub
		
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		RoomChartLog roomChartLog=new RoomChartLog();
		
		roomChangeHireLog.setLimit(limit);
		roomChangeHireLog.setOffset(offset);
		roomChangeHireLog.setSort(sort);
		roomChangeHireLog.setOrder(order);
		roomChangeHireLog.setNotIn("[GUID]");
		roomChangeHireLog.setWhereTerm("OR");
		
		roomChartLog.setLimit(limit);
		roomChartLog.setOffset(offset);
		roomChartLog.setSort(sort);
		roomChartLog.setOrder(order);
		roomChartLog.setNotIn("[Charter]");
		roomChartLog.setWhereTerm("OR");
		
		System.out.println("srot="+sort);
		
		if(search!=null&&!search.trim().equals("")){
		 String[] where={"[RoomManage].[dbo].[RoomChangeHireLog].Charter LIKE",search,
				"[RoomManage].[dbo].[RoomChangeHireLog].Region LIKE",search};
		 roomChangeHireLog.setWhere(where);
		}
		
		
		
		Object[] objects={roomChangeHireLog,roomChartLog};
		/*
		Class<?>[] classes={roomChangeHireLog.getClass(),roomChartLog.getClass()};
		Map map2=null;
		try {
			map2=new SelectSQLJoin().get(objects,"[Charter]");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List params=(List) map2.get("params");
		String sql=(String) map2.get("sql");
		
		System.out.println("params="+params);
		
		List list=this.getJdbcTemplate().query(sql,params.toArray(), new RowMappersJoin(classes, RoomChangeHireLog_RoomChartLog.class));
		
		Map map=new HashMap<String, Object>();
		
		map.put("value", list);
		
		try {
			map2=new SelectSQLJoin().getCount(objects, "[Charter]")	;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		params=(List) map2.get("params");
		sql=(String) map2.get("sql");
		
		Map map3=this.getJdbcTemplate().queryForMap(sql,params.toArray());
		*/
		
		Map map=new HashMap<String, Object>();
		
		RoomChangeHireLog_RoomChartLog roomChangeHireLog_RoomChartLog=new RoomChangeHireLog_RoomChartLog();
		
		String[] join={"[Charter]"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects, roomChangeHireLog_RoomChartLog, join);
		map.put("value", list);
		
		Map map3=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
		map.put("rows", map3.get(""));
		
		return map;
	}

	@Override
	public Map<String, Object> findAllFloor(Integer limit, Integer offset, String sort, String order, String search) {
		// TODO Auto-generated method stub
		Floor floor=new Floor();
		floor.setLimit(limit);
		floor.setOffset(offset);
		floor.setSort(sort);
		floor.setOrder(order);
		
		Map<String,Object> map=new HashMap<>();
	        Map<String,Object> map2=new HashMap<>();
			try {
				map2 = SelectSQL.get(floor);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			List params=(List) map2.get("params");
			String sql=(String) map2.get("sql");
			
			List list=this.getJdbcTemplate().query(sql,params.toArray(), new RowMappers(Floor.class));
			
			map.put("value", list);
			
			try {
				map2=new SelectSQL().getCount(floor);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			params=(List) map2.get("params");
			sql=(String) map2.get("sql");
			
			Map map3=this.getJdbcTemplate().queryForMap(sql,params.toArray());
			
			map.put("rows", map3.get(""));
			
		return map;
	}

	@Override
	public Integer updateRoomInfo(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		int i=UpdateExe.get(this.getJdbcTemplate(), roomInfo);
		return i;
	}

	@Override
	public Integer deleteRoomInfo(RoomInfo roomInfo) {
		// TODO Auto-generated method stub
		int i=DeleteExe.get(this.getJdbcTemplate(), roomInfo);
		return i;
	}

	@Override
	public Map<String, Object> findAllRoomInfo_Position(Integer limit, Integer offset, String sort, String order,
			Map search) {
		// TODO Auto-generated method stub
		RoomInfo roomInfo=new RoomInfo();
		Position position=new Position();
		
		roomInfo.setLimit(limit);
		roomInfo.setOffset(offset);
		roomInfo.setSort(sort);
		roomInfo.setOrder(order);
		roomInfo.setNotIn("[GUID]");
		
		position.setLimit(limit);
		position.setOffset(offset);
		position.setSort(sort);
		position.setOrder(order);
		position.setNotIn("[GUID]");
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    roomInfo.setWhere(where);
		    position.setWhere(where);
		}
		
		Object[] objects={roomInfo,position};
		
		Map map=new HashMap<String, Object>();
		
		RoomInfo_Position roomInfo_Position=new RoomInfo_Position();
		
		String[] join={"[GUID]"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects, roomInfo_Position, join);		
        map.put("rows", list);
		MyTestUtil.print(list);
		Map map3=SelectJoinExe.getCount(this.getJdbcTemplate(), objects, join);
		map.put("total", map3.get(""));
		
		return map;
	}

	
	@Override
	public Map<String, Object> getChartInfoByGUID(Integer limit, Integer offset, String sort, String order,
			Map search) {
		// TODO Auto-generated method stub
		ChartInfo chartInfo=new ChartInfo();
		
		if(sort==null){
			sort="ConcludeDate";
		}
		
		if(order==null){
			order="desc";
		}
		
		chartInfo.setLimit(limit);
		chartInfo.setOffset(offset);
		chartInfo.setSort(sort);
		chartInfo.setOffset(offset);
		chartInfo.setNotIn("GUID");
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    chartInfo.setWhere(where);
		}
		
		Map map=new HashMap<String, Object>();
		
		List list=SelectExe.get(this.getJdbcTemplate(), chartInfo);
		map.put("rows", list);
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(), chartInfo).get("");
		map.put("total", total);
		
		return map;
	}
	
	
	@Override
	public Map<String, Object> getHireListByGUID(Integer limit, Integer offset, String sort, String order, Map search) {
		// TODO Auto-generated method stub
		HireList hireList=new HireList();
		
		hireList.setLimit(limit);
		hireList.setOffset(offset);
		hireList.setSort(sort);
		hireList.setOffset(offset);
		hireList.setNotIn("GUID");
		
		if(sort==null){
			sort="State";
		}
		
		if(order==null){
			order="desc";
		}
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    hireList.setWhere(where);
		}
		
		Map map=new HashMap<String, Object>();
		
		List list=SelectExe.get(this.getJdbcTemplate(), hireList);
		map.put("rows", list);
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(), hireList).get("");
		map.put("total", total);
		
		return map;
	}

	

	
}
