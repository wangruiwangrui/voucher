package com.voucher.manage.daoImpl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Users;
import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Hidden_Data;
import com.voucher.manage.daoModel.Assets.Hidden_Level;
import com.voucher.manage.daoModel.Assets.Hidden_Type;
import com.voucher.manage.daoModel.Assets.Hidden_User;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Assets_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Data_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Hidden_Join;
import com.voucher.manage.daoRowMapper.RowMappersJoin;
import com.voucher.manage.daoSQL.DeleteExe;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe;
import com.voucher.manage.daoSQL.SelectSqlJoinExe;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.file.AbstractFileUpload;
import com.voucher.manage.tools.FileConvect;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.tools.TransMapToString;

public class AssetsDAOImpl extends JdbcDaoSupport implements AssetsDAO{

	@Override
	public Map findAllHidden(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Hidden hidden=new Hidden();
		
		hidden.setLimit(limit);
		hidden.setOffset(offset);
		hidden.setSort(sort);
		hidden.setOrder(order);
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    hidden.setWhere(where);
		}
		
		
		return null;
	}

	@Override
	public Integer updatePosition(Position position) {
		// TODO Auto-generated method stub
		int i;
		String[] where={"[Position].GUID=",position.getGUID()};
		position.setWhere(where);
		int count=(int) SelectExe.getCount(this.getJdbcTemplate(), position).get("");
		if(count==1){
			i=UpdateExe.get(this.getJdbcTemplate(), position);
		}else{
			i=InsertExe.get(this.getJdbcTemplate(), position);
		}
		return i;
	}

	public Integer deletePosition(Position position){
		return DeleteExe.get(this.getJdbcTemplate(), position);
	}
	
	@Override
	public Map findAllPosition(String manageRegion) {
		// TODO Auto-generated method stub
		int limit=1000;
		int offset=0;
		
		Position_Hidden_Join position_Hidden_Join=new Position_Hidden_Join();
		
		Position position=new Position();		
		position.setOffset(offset);
		position.setLimit(limit);
		position.setNotIn("id");
		
		Hidden hidden=new Hidden();
		hidden.setOffset(offset);
		hidden.setLimit(limit);
		hidden.setNotIn("id");
		
		if(!manageRegion.equals("")){
			String[] where = {"Hidden.ManageRegion = ", manageRegion};
			position.setWhere(where);
			hidden.setWhere(where);
		}
		
		Object[] objects={position,hidden};
		
		String[] join={"GUID","GUID"};
		
		List list=SelectJoinExe.get(this.getJdbcTemplate(), objects,position_Hidden_Join,join);
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		return map;
	}

	
	@Override
	public List findPosition(Position position) {
		// TODO Auto-generated method stub
		return SelectExe.get(this.getJdbcTemplate(), position);
	}
	
	@Override
	public Map findHiddenByDistance(Double lng, Double lat) {
		// TODO Auto-generated method stub
		
		String sql="SELECT TOP 5"+
		            "[Position].GUID,"+
					"[Position].province,"+
					"[Position].city,"+
					"[Position].district,"+
					"[Position].street,"+
					"[Position].street_number,"+
					"[Position].lng,"+
					"[Position].lat,"+
					"[Position].date,"+
					"[Hidden].name,"+
					"[Hidden].hidden_level,"+
					"[Hidden].detail,"+
					"[Hidden].progress,"+
					"[Hidden].happen_time,"+
					"[Hidden].principal,"+
					"[Hidden].type,"+
					"[Hidden].state,"+
					"[Hidden].remark,"+
					"[Hidden].update_time,"+
					"[Hidden].date "+
					"FROM [Position] left join [Hidden] "+
					"on [Position].GUID = [Hidden].GUID "+
					"WHERE "+  
					"[Position].id not in( select top 6 [Position].id from [Position] left join [Hidden] "+
					"on [Position].GUID = [Hidden].GUID "+ 
					"ORDER BY   SQRT((105.4955-lng)*(105.4955-lng)+(28.91866-lat)*(28.91866-lat))) "+
					"AND "+
					"geography::STGeomFromText('POINT(' + cast([lng] as varchar(20)) + ' '"+  
					"+ cast([lat] as varchar(20)) +')', 4326).STDistance(  "+
					"geography::STGeomFromText('POINT(105.4955 28.91866)', 4326))<10000 "+
					"ORDER BY   "+
					"SQRT((105.4955-lng)*(105.4955-lng)+(28.91866-lat)*(28.91866-lat))  ";
		
		Position_Hidden_Join position_Hidden_Join=new Position_Hidden_Join();
		
		Position position=new Position();		
		
		Hidden hidden=new Hidden();
		
		Object[] objects={position,hidden};
		
		List list=SelectSqlJoinExe.get(this.getJdbcTemplate(), sql, objects,position_Hidden_Join);
		
		Map map=new HashMap<>();
		
		map.put("row", list);
		
		return map;
	}
	
	
	@Override
	public Integer insertIntoHidden_Assets(Hidden_Assets hidden_Assets) {
		// TODO Auto-generated method stub

		return InsertExe.get(this.getJdbcTemplate(), hidden_Assets);
	}

	@Override
	public Map findAssetByHideen(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Hidden_Assets hidden_Assets=new Hidden_Assets();
		
		hidden_Assets.setLimit(limit);
		hidden_Assets.setOffset(offset);
		hidden_Assets.setSort(sort);
		hidden_Assets.setOffset(offset);
		hidden_Assets.setNotIn("[GUID]");
		
		RoomInfo roomInfo=new RoomInfo();
		
		roomInfo.setLimit(limit);
		roomInfo.setOffset(offset);
		roomInfo.setSort(sort);
		roomInfo.setOrder(order);
		roomInfo.setNotIn("[GUID]");
			
		Object[] objects={hidden_Assets,roomInfo};
		
		Map map=new HashMap<String, Object>();
		
		Hidden_Assets_Join hidden_Assets_Join=new Hidden_Assets_Join();
		

  String sql="SELECT top "+limit+
    "[Hidden_Assets].id,"+
    "[Hidden_Assets].asset_GUID,"+
    "[Hidden_Assets].hidden_GUID,"+
    "[TTT].[dbo].[RoomInfo].GUID,"+
    "[TTT].[dbo].[RoomInfo].Num,"+
    "[TTT].[dbo].[RoomInfo].OriginalNum,"+
    "[TTT].[dbo].[RoomInfo].Address,"+
    "[TTT].[dbo].[RoomInfo].OriginalAddress,"+
    "[TTT].[dbo].[RoomInfo].Region,"+
    "[TTT].[dbo].[RoomInfo].Segment,"+
    "[TTT].[dbo].[RoomInfo].ManageRegion,"+
    "[TTT].[dbo].[RoomInfo].RoomProperty,"+
    "[TTT].[dbo].[RoomInfo].Useful,"+
    "[TTT].[dbo].[RoomInfo].Floor,"+
    "[TTT].[dbo].[RoomInfo].State,"+
    "[TTT].[dbo].[RoomInfo].Structure,"+
    "[TTT].[dbo].[RoomInfo].BuildArea,"+
    "[TTT].[dbo].[RoomInfo].RoomType,"+
    "[TTT].[dbo].[RoomInfo].IsCity,"+
    "[TTT].[dbo].[RoomInfo].Manager,"+
    "[TTT].[dbo].[RoomInfo].ManagerPhone,"+
    "[TTT].[dbo].[RoomInfo].IsStreet,"+
    "[TTT].[dbo].[RoomInfo].FitMent,"+
    "[TTT].[dbo].[RoomInfo].BeFrom,"+
    "[TTT].[dbo].[RoomInfo].InDate,"+
    "[TTT].[dbo].[RoomInfo].PropertyRightNo,"+
    "[TTT].[dbo].[RoomInfo].PropertyRightArea,"+
    "[TTT].[dbo].[RoomInfo].DesignUseful,"+
    "[TTT].[dbo].[RoomInfo].BuildYear,"+
    "[TTT].[dbo].[RoomInfo].PropertyRightUnit,"+
    "[TTT].[dbo].[RoomInfo].RealPropertyRightUnit,"+
    "[TTT].[dbo].[RoomInfo].PropertyCardUnit,"+
    "[TTT].[dbo].[RoomInfo].GlebeCardUnit,"+
    "[TTT].[dbo].[RoomInfo].TransferUnit,"+
    "[TTT].[dbo].[RoomInfo].GlebeCardNo,"+
    "[TTT].[dbo].[RoomInfo].GlebeUseType,"+
    "[TTT].[dbo].[RoomInfo].GlebeEndDate,"+
    "[TTT].[dbo].[RoomInfo].GlebeTypeUseful,"+
    "[TTT].[dbo].[RoomInfo].PlanUseful,"+
    "[TTT].[dbo].[RoomInfo].BefromFile,"+
    "[TTT].[dbo].[RoomInfo].NoPRNResion,"+
    "[TTT].[dbo].[RoomInfo].NoGCResion,"+
    "[TTT].[dbo].[RoomInfo].RealEstateNo,"+
    "[TTT].[dbo].[RoomInfo].PropertyMemo,"+
    "[TTT].[dbo].[RoomInfo].OriginalAmount,"+
    "[TTT].[dbo].[RoomInfo].AllDepreciation,"+
    "[TTT].[dbo].[RoomInfo].DepreciationYear,"+
    "[TTT].[dbo].[RoomInfo].NetWorth,"+
    "[TTT].[dbo].[RoomInfo].EvaluationPrice,"+
    "[TTT].[dbo].[RoomInfo].EvaluationSinglePrice,"+
    "[TTT].[dbo].[RoomInfo].EvaluationPlace,"+
    "[TTT].[dbo].[RoomInfo].BefromAmount,"+
    "[TTT].[dbo].[RoomInfo].MarketHire,"+
    "[TTT].[dbo].[RoomInfo].EvaluationUnit,"+
    "[TTT].[dbo].[RoomInfo].EvaluationNo,"+
    "[TTT].[dbo].[RoomInfo].IsPawn,"+
    "[TTT].[dbo].[RoomInfo].PawnUnit,"+
    "[TTT].[dbo].[RoomInfo].OriginalUnit,"+
    "[TTT].[dbo].[RoomInfo].FinanceMemo,"+
    "[TTT].[dbo].[RoomInfo].Utility,"+
    "[TTT].[dbo].[RoomInfo].ChartGUID,"+
    "[TTT].[dbo].[RoomInfo].AddressCode,"+
    "[TTT].[dbo].[RoomInfo].OriginalAddressCode,"+
    "[TTT].[dbo].[RoomInfo].SecurityClassification,"+
    "[TTT].[dbo].[RoomInfo].DangerClassification,"+
    "[TTT].[dbo].[RoomInfo].HiddenDanger,"+
    "[TTT].[dbo].[RoomInfo].ResponsiblePerson,"+
    "[TTT].[dbo].[RoomInfo].sMemo,"+
    "[TTT].[dbo].[RoomInfo].BelongUnit,"+
    "[TTT].[dbo].[RoomInfo].FileIndex,"+
    "[TTT].[dbo].[RoomInfo].SecurityRegion,"+
    "[TTT].[dbo].[RoomInfo].RoomCount,"+
    "[TTT].[dbo].[RoomInfo].LandArea,"+
    "[TTT].[dbo].[RoomInfo].UseYears "+
    "FROM "+
    "[Hidden_Assets] left join [TTT].[dbo].[RoomInfo] on [Hidden_Assets].[asset_GUID]=[TTT].[dbo].[RoomInfo].[GUID]"+
    ""; 
  
  //  String sqlWhere="AND [Hidden_Assets].[asset_GUID] not in( select top "+offset+" [Hidden_Assets].[asset_GUID] FROM [Hidden_Assets] left join [TTT].[dbo].[RoomInfo] on [Hidden_Assets].[asset_GUID]=[TTT].[dbo].[RoomInfo].[GUID] ORDER BY [Hidden_Assets].[asset_GUID]) ORDER BY [Hidden_Assets].[asset_GUID]";
	
  		StringBuilder whereCommand = new StringBuilder(); 
       
  		Class<?>[] classeNames=new Class<?>[2];
  
  		classeNames[0]=Hidden_Assets.class;
  		classeNames[1]=RoomInfo.class;
  		
    	List params=new ArrayList<>();
        List paramsCount=new ArrayList<>();
    	
    	if(!search.isEmpty()){
			String[] where=TransMapToString.get(search);	
			
			List wheres=new ArrayList<String[]>();
			
			String[] columnWhere;
			
			wheres.add(where);
             			        
	          Iterator<String[]> iterator=wheres.iterator();
	          System.out.println("wheres="+wheres);
	          
	          while (iterator.hasNext()) {
	        	  columnWhere=iterator.next();
	        	  int k=1;
	        	  for(String whereterm:columnWhere){
	            	  if(k%2==0){
	            		//  System.out.println("偶数");
	            		//  whereCommand.append(whereterm+"\n  AND ");
	            	   }else{
	            		//  System.out.println("奇数");
	            		//  whereCommand.append("\n   "+whereterm);
	            		   whereCommand.append(whereterm+"? \n  AND ");
	            	   }
	               k++;
	               System.out.println("whereCommand="+whereCommand);
	              }
	            }
	          
	          iterator=wheres.iterator();
	          while (iterator.hasNext()) {
	        	  columnWhere=iterator.next();
	        	  int k=1;
	        	  for(String whereterm:columnWhere){
	            	  if(k%2==0){
	            		//  System.out.println("偶数");
	            		  params.add(whereterm);
	            	   }else{
	            		//  System.out.println("奇数");
	            		//  whereCommand.append("\n   "+whereterm);
	            	   }
	               k++;
	              }
			  }
	          
	          iterator=wheres.iterator();
		       while (iterator.hasNext()) {
		        	  columnWhere=iterator.next();
		        	  int k=1;
		        	  for(String whereterm:columnWhere){
		            	  if(k%2==0){
		            		//  System.out.println("偶数");
		            		  params.add(whereterm);
		            		  paramsCount.add(whereterm);
		            	   }else{
		            		//  System.out.println("奇数");
		            		//  whereCommand.append("\n   "+whereterm);
		            	   }
		               k++;
		              }
				  }
	          
		}

    	System.out.println("params="+params);
    	
    	if(!search.isEmpty()){
    		   sql=sql+   //sqlserver分页需要在top也加上where条件
    	         		 "\n  where "+whereCommand+
    	                  " [Hidden_Assets].asset_GUID not in("+
    	                  " select top "+offset+" [Hidden_Assets].asset_GUID  FROM [Hidden_Assets] where "+
    	                   whereCommand.substring(0,whereCommand.length()-7)+")";
    	}
    	
    	List list=this.getJdbcTemplate().query(sql,params.toArray(), new RowMappersJoin(classeNames,hidden_Assets_Join.getClass()));
       
    	map.put("rows", list);
		MyTestUtil.print(list);
		
		String countSql="select count(*) FROM "+
			    "[Hidden_Assets] left join [TTT].[dbo].[RoomInfo] on [Hidden_Assets].[asset_GUID]=[TTT].[dbo].[RoomInfo].[GUID]";
		
		if(!search.isEmpty()){
			countSql=countSql+   //sqlserver分页需要在top也加上where条件
	          		 "\n  where "+
	                    whereCommand.substring(0,whereCommand.length()-7);
    	}
		
    	Map amount=this.getJdbcTemplate().queryForMap(countSql,paramsCount.toArray());
    	
		map.put("total", amount.get(""));
		
		return map;

	}

	@Override
	public Integer deleteHidden_Assets(Hidden_Assets hidden_Assets) {
		// TODO Auto-generated method stub
		return DeleteExe.get(this.getJdbcTemplate(), hidden_Assets);
	}

	@Override
	public Integer findNotHidden() {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		
		String[] where={"[Hidden].progress =","0"};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}

	@Override
	public Integer findInHidden() {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		
		String[] where={"[Hidden].progress >","0"
				," [Hidden].progress <","1"};
		hidden.setWhere(where);
		
		int i=(int) SelectExe.getCount(this.getJdbcTemplate(), hidden).get("");
		
		return i;
	}

	@Override
	public String findLastHidden() {
		// TODO Auto-generated method stub
		
		Hidden hidden=new Hidden();
		
		String sql="SELECT MAX([date]) FROM [Hidden_Check]";
		
		List list=this.getJdbcTemplate().query(sql,new hiddenRowMapper());
		
		hidden=(Hidden) list.get(0);
		
		String s=String.valueOf(hidden.getHappen_time());
		
		return s;
	}

	class hiddenRowMapper implements RowMapper<Hidden> {
        //rs为返回结果集，以每行为单位封装着
        public Hidden mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden hidden=new Hidden();
        	Date date=rs.getDate("");
        	hidden.setHappen_time(date);
            return hidden;
        }
    }
	
	@Override
	public String findIgnoreHidden() {
		// TODO Auto-generated method stub
		Hidden hidden=new Hidden();
		
		String sql="SELECT MAX([happen_time]) FROM [Hidden]";
		
		List list=this.getJdbcTemplate().query(sql,new hiddenRowMapper());
		
		hidden=(Hidden) list.get(0);
		
		String s=String.valueOf(hidden.getHappen_time());
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		
		Date begin=new Date();
		
		int between=0;
		
		try {
			Date endDate=df.parse(s);
			between=(int) ((begin.getTime()-endDate.getTime())/(1000*3600*24));
			System.out.println("time=  "+begin.getTime()+"          "+endDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(between<0){
			between=0;
		}
		
		return String.valueOf(between);
	}

	@Override
	public Map<String, Object> hiddenQuery(Integer hiddenLevel) {
		// TODO Auto-generated method stub
		
		/*
		String sql="SELECT top 5 "+    
					"[Hidden_Data].GUID, "+
				    "[Hidden_Data].URI, "+
					"[Hidden_Data].date, "+
				    "[Hidden].name, "+
					"[Hidden].detail, "+
				    "[Hidden].hidden_level, "+
					"[Hidden].progress "+
					"FROM "+
					"[Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+  
					"where  [Hidden].hidden_level = "+hiddenLevel+" "+
					"AND ([Hidden_Data].TYPE ='png ' "+
					"OR [Hidden_Data].TYPE ='jpg ' "+
					"OR [Hidden_Data].TYPE ='jpeg ' "+
					"OR [Hidden_Data].TYPE ='gif ' )"+
					"group by [Hidden_Data].GUID,[Hidden_Data].URI,[Hidden_Data].date, "+
					"[Hidden].name,[Hidden].detail,[Hidden].hidden_level,[Hidden].progress "+
					"order by [Hidden_Data].date desc ";
		*/
		
		String sql1="SELECT top 5 [Hidden_Data].GUID "+
				 	"FROM [Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+
				 	"where  [Hidden].hidden_level = "+hiddenLevel+" "+
				 	"AND ([Hidden_Data].TYPE ='png ' OR [Hidden_Data].TYPE ='jpg ' OR [Hidden_Data].TYPE ='jpeg ' OR [Hidden_Data].TYPE ='gif ' ) "+
				 	"group by [Hidden_Data].GUID ";
		
		List hidden_Data_Joins=this.getJdbcTemplate().query(sql1,new hiddenQueryRowMapper1());
		
        String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+AbstractFileUpload.filePath;
		
		List<String> GUIDs=new ArrayList<String>();
		List<String> names=new ArrayList<String>();
		List<String> details=new ArrayList<String>();
		List<Double> progress=new ArrayList<Double>();
		List<Date> dates=new ArrayList<Date>();
		List<byte[]> fileBytes=new ArrayList<byte[]>();
				
		Iterator<Hidden_Data_Join> iterator=hidden_Data_Joins.iterator();
		
		while(iterator.hasNext()){			
			
			Hidden_Data_Join hidden_Data_Join1=iterator.next();
			
			String GUID=hidden_Data_Join1.getGUID();
			
			String sql2="SELECT top 1 "+    
					"[Hidden_Data].GUID, "+
				    "[Hidden_Data].URI, "+
					"[Hidden_Data].date, "+
				    "[Hidden].name, "+
					"[Hidden].detail, "+
				    "[Hidden].hidden_level, "+
					"[Hidden].progress "+
					"FROM "+
					"[Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+  
					"where [Hidden_Data].GUID='"+GUID+"'  "+
					"AND ([Hidden_Data].TYPE ='png ' OR [Hidden_Data].TYPE ='jpg ' OR [Hidden_Data].TYPE ='jpeg ' OR [Hidden_Data].TYPE ='gif ' ) "+
					"order by [Hidden_Data].date desc ";
			
			List hidden_Data_Joins2=this.getJdbcTemplate().query(sql2,new hiddenQueryRowMapper2());
			
			try{
			Hidden_Data_Join hidden_Data_Join2=(Hidden_Data_Join) hidden_Data_Joins2.get(0);
			
			File file=new File(filePath+"\\"+hidden_Data_Join2.getURI());
			
			byte[] fileByte=FileConvect.fileToByte(file);
			
			GUIDs.add(hidden_Data_Join2.getGUID());
			names.add(hidden_Data_Join2.getName());
			details.add(hidden_Data_Join2.getDetail());
			dates.add(hidden_Data_Join2.getDate());
			progress.add(hidden_Data_Join2.getProgress());
			fileBytes.add(fileByte);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		Map<String, Object> map=new HashMap<>();
		
		map.put("GUIDs", GUIDs);
		map.put("names", names);
		map.put("details", details);
		map.put("dates", dates);
		map.put("progress", progress);
		map.put("fileBytes", fileBytes);
		MyTestUtil.print(map);
		return map;
	}

	class hiddenQueryRowMapper1 implements RowMapper<Hidden_Data_Join> {
        //rs为返回结果集，以每行为单位封装着
        public Hidden_Data_Join mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Data_Join hidden_Data_Join=new Hidden_Data_Join();
        	hidden_Data_Join.setGUID(rs.getString("GUID"));
            return hidden_Data_Join;
        }
    }
	
	class hiddenQueryRowMapper2 implements RowMapper<Hidden_Data_Join> {
        //rs为返回结果集，以每行为单位封装着
        public Hidden_Data_Join mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Data_Join hidden_Data_Join=new Hidden_Data_Join();
        	hidden_Data_Join.setGUID(rs.getString("GUID"));
        	hidden_Data_Join.setURI(rs.getString("URI"));
        	hidden_Data_Join.setName(rs.getString("name"));
        	hidden_Data_Join.setDetail(rs.getString("detail"));
        	hidden_Data_Join.setHidden_level(rs.getInt("hidden_level"));
        	hidden_Data_Join.setProgress(rs.getDouble("progress"));
        	hidden_Data_Join.setDate(rs.getDate("date"));
            return hidden_Data_Join;
        }
    }

	@Override
	public List selectManageRegion() {
		// TODO Auto-generated method stub
		
		String sql="SELECT [ManageRegion] "+
                    "FROM [TTT].[dbo].[RoomInfo] group by [ManageRegion]";
		
		List list=this.getJdbcTemplate().query(sql, new roomInfoRowMapper());
		
		return list;
	}

	class roomInfoRowMapper implements RowMapper<RoomInfo> {
        //rs为返回结果集，以每行为单位封装着
        public RoomInfo mapRow(ResultSet rs, int rowNum) throws SQLException {    
        	RoomInfo roomInfo=new RoomInfo();
        	roomInfo.setManageRegion(rs.getString("ManageRegion"));        	
            return roomInfo;
        }
    }
	
}
