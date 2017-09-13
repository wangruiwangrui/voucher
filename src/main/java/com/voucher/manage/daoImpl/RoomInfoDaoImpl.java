package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.RoomInfoDao;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoModel.RoomInfoRowMapper;
import com.voucher.manage.daoModel.RowMappers;
import com.voucher.manage.daoSQL.SelectSQL;

public class RoomInfoDaoImpl extends JdbcDaoSupport implements RoomInfoDao{

	
	@Override
	public List<RoomInfo> findAllRoomInfo(Integer limit, Integer offset, String sort,
			String order,String search) {
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
		if(search!=null&&!search.trim().equals("")){
		  String[] where={"Address like ",search};
		  roomInfo.setWhere(where);
		}
        String sql="";
		try {
			sql = SelectSQL.get(roomInfo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return this.getJdbcTemplate().query(sql, new RoomInfoRowMapper());
		return this.getJdbcTemplate().query(sql, new RowMappers(RoomInfo.class));
	}

	@Override
	public Integer getRoomInfoCount(String search) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<>();
		//String sql="SELECT count(*) FROM [TTT].[dbo].[RoomInfo]";
		
		RoomInfo roomInfo=new RoomInfo();
		if(search!=null&&!search.trim().equals("")){
			  String[] where={"Address like ",search};
			  roomInfo.setWhere(where);
			}
        String sql="";
		try {
			sql = SelectSQL.getCount(roomInfo);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		map=this.getJdbcTemplate().queryForMap(sql);
	
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

}
