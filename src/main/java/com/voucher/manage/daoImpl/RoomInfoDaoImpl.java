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

public class RoomInfoDaoImpl extends JdbcDaoSupport implements RoomInfoDao{

	
	@Override
	public List<RoomInfo> findAllRoomInfo(Integer limit, Integer offset, String sort,
			String order,String search) {
		// TODO Auto-generated method stub
		
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
		
		return this.getJdbcTemplate().query(sql, new RoomInfoRowMapper());
	}

	@Override
	public Integer getRoomInfoCount(String search) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<>();
		String sql="SELECT count(*) FROM [TTT].[dbo].[RoomInfo]";
		
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
