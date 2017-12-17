package com.voucher.manage.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.AssetsDAO;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.Assets.Hidden;
import com.voucher.manage.daoModel.Assets.Hidden_Assets;
import com.voucher.manage.daoModel.Assets.Position;
import com.voucher.manage.daoModelJoin.RoomInfo_Position;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Assets_Join;
import com.voucher.manage.daoRowMapper.RowMappersJoin;
import com.voucher.manage.daoSQL.InsertExe;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe;
import com.voucher.manage.daoSQL.UpdateExe;
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
		return InsertExe.get(this.getJdbcTemplate(), position);
	}

	@Override
	public Map findAllPosition() {
		// TODO Auto-generated method stub
		Position position=new Position();
		
		position.setOffset(0);
		position.setLimit(100);
		
		List list=SelectExe.get(this.getJdbcTemplate(), position);
		
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
    "[Assets].[dbo].[Hidden_Assets].id,"+
    "[Assets].[dbo].[Hidden_Assets].asset_GUID,"+
    "[Assets].[dbo].[Hidden_Assets].hidden_GUID,"+
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
    "[Assets].[dbo].[Hidden_Assets] left join [TTT].[dbo].[RoomInfo] on [Assets].[dbo].[Hidden_Assets].[asset_GUID]=[TTT].[dbo].[RoomInfo].[GUID]"+
    ""; 
  
  //  String sqlWhere="AND [Assets].[dbo].[Hidden_Assets].[asset_GUID] not in( select top "+offset+" [Assets].[dbo].[Hidden_Assets].[asset_GUID] FROM [Assets].[dbo].[Hidden_Assets] left join [TTT].[dbo].[RoomInfo] on [Assets].[dbo].[Hidden_Assets].[asset_GUID]=[TTT].[dbo].[RoomInfo].[GUID] ORDER BY [Assets].[dbo].[Hidden_Assets].[asset_GUID]) ORDER BY [Assets].[dbo].[Hidden_Assets].[asset_GUID]";
	
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
    	                  " [Assets].[dbo].[Hidden_Assets].asset_GUID not in("+
    	                  " select top "+offset+" [Assets].[dbo].[Hidden_Assets].asset_GUID  FROM [Assets].[dbo].[Hidden_Assets] where "+
    	                   whereCommand.substring(0,whereCommand.length()-7)+")";
    	}
    	
    	List list=this.getJdbcTemplate().query(sql,params.toArray(), new RowMappersJoin(classeNames,hidden_Assets_Join.getClass()));
       
    	map.put("rows", list);
		MyTestUtil.print(list);
		
		String countSql="select count(*) FROM "+
			    "[Assets].[dbo].[Hidden_Assets] left join [TTT].[dbo].[RoomInfo] on [Assets].[dbo].[Hidden_Assets].[asset_GUID]=[TTT].[dbo].[RoomInfo].[GUID]";
		
		if(!search.isEmpty()){
			countSql=countSql+   //sqlserver分页需要在top也加上where条件
	          		 "\n  where "+
	                    whereCommand.substring(0,whereCommand.length()-7);
    	}
		
    	Map amount=this.getJdbcTemplate().queryForMap(countSql,paramsCount.toArray());
    	
		map.put("total", amount.get(""));
		
		return map;

	}

}