package com.voucher.manage.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.MapMaker;
import com.voucher.manage.dao.FinanceDAO;
import com.voucher.manage.daoImpl.AssetsDAOImpl.allHire;
import com.voucher.manage.daoModel.TTT.ChartInfo;
import com.voucher.manage.daoModel.TTT.HireList;
import com.voucher.manage.daoModelJoin.Finance.HireList_ChartInfo_Join;
import com.voucher.manage.daoRowMapper.RowMappers;
import com.voucher.manage.daoSQL.SelectExe;
import com.voucher.manage.daoSQL.SelectJoinExe2;
import com.voucher.manage.daoSQL.SelectSQL;
import com.voucher.manage.daoSQL.UpdateExe;
import com.voucher.manage.singleton.Singleton;
import com.voucher.manage.tools.TransMapToString;

public class FinanceDAOImpl extends JdbcDaoSupport implements FinanceDAO{

	@Override
	public Map findMatureHire(Integer days,Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.DAY_OF_MONTH, days);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM");
		
        String matureTime = null;
		
        matureTime=sdf.format(cal.getTime());
        
        System.out.println("matureTime="+matureTime);
        
        String[] where={"convert(varchar(11),[HireList].HireDate ,120 )=",matureTime,
        		"State=","未交"};
        
		HireList hireList=new HireList();
		hireList.setLimit(limit);
		hireList.setOffset(offset);
		hireList.setNotIn("GUID");
		hireList.setWhere(where);
		
		ChartInfo chartInfo=new ChartInfo();
		chartInfo.setLimit(limit);
		chartInfo.setOffset(offset);
		chartInfo.setNotIn("GUID");
		chartInfo.setWhere(where);
		
		HireList_ChartInfo_Join hireList_ChartInfo_Join=new HireList_ChartInfo_Join();
		
		Object[] objects={hireList,chartInfo};
		
		String[][] join={{"ChartGUID","GUID"}};
		
		List list=SelectJoinExe2.get(this.getJdbcTemplate(), objects, hireList_ChartInfo_Join, join);
		
		int total=(int) SelectJoinExe2.getCount(this.getJdbcTemplate(), objects, join).get("");
		
		String sql2="SELECT sum([Hire]) as allHire,count(*) as count from HireList where "
				+ "convert(varchar(11),[HireList].HireDate ,120 )='"+matureTime+"' and "
				+ "State='未交'";
		
		List list2=this.getJdbcTemplate().query(sql2,new allHire());
		
		Map HireMap=(Map) list2.get(0);
		
		Double allHire=(Double) HireMap.get("allHire")/10000;	
		int  count=(int) HireMap.get("count");
		java.text.DecimalFormat   df=new   java.text.DecimalFormat("######0.00");   			
				
		Map map=new HashMap<>();
		
		map.put("rows", list);
		
		map.put("total", total);
		
		map.put("count", count);
		
		map.put("allHire", df.format(allHire)+"万元");
		
		map.put("matureTime", matureTime);
		
		return map;
	}

	
	class allHire implements RowMapper<Map> {

		@Override
		public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			
			Double allHire=rs.getDouble("allHire");
			
			Integer count=rs.getInt("count");
			
			Map map=new HashMap<>();
			
			map.put("allHire", allHire);
			
			map.put("count", count);
			
			return map;
		}
		
	}

	
	@Override
	public Map findGeneralChartInfo(Integer limit, Integer offset, String sort, String order, Integer isHistory,
			Map<String, String> search) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM");
		
        String matureTime = null;
		
        matureTime=sdf.format(cal.getTime());
		
        System.out.println("matureTime="+matureTime);
        
        String sql = null;
        
        String sql2 = null;
        
        if(!search.isEmpty()){
        	
        	StringBuilder sb = new StringBuilder();
        	
        	String[] where=TransMapToString.get(search);
        	
        	int i=0;
			
        	for(String str : where){
			    
			    if(i%2==0){
			    	sb.append(str);
			    }else{
			    	sb.append("'"+str+"'");
			    	sb.append(" or ");
			    }
			    i++;
			    
			   System.out.println("sb="+sb);
			}
        	
        	String s = sb.toString();
			
			String serach=s.substring(0,s.length()-4);
			
			System.out.println("serach="+serach);
        	
			sql=" select top "+limit+" * from "+
					"(select ROW_NUMBER() OVER (ORDER BY "+sort+" "+order+") AS rows ,* from "+
					"ChartInfo left join (	"+
					 "select ChartGUID from [HireList] "+
        			"where [HireList].ChartGUID not in(	"+        			
        			 "select ChartGUID from [HireList] "+
        			"where [HireList].State='未交'  and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+
        			"[HireList].ChartGUID )group by  chartGuid "+
        			") as t2 on ChartInfo.GUID=t2.ChartGUID	"+
        			"where ChartGUID is not null "+
        			")as w1 "+
        			"where rows>"+offset+" and "+
        			"IsHistory="+isHistory+" "+
        			"order by "+sort+" "+order;
		
        	sql2="select count(*) from "+
					"ChartInfo left join (	"+
					 "select ChartGUID from [HireList] "+
					 "where [HireList].ChartGUID not in(	"+        			
					 "select ChartGUID from [HireList] "+
					 "where [HireList].State='未交'  and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+
					 "[HireList].ChartGUID )group by  chartGuid "+
					 ") as t2 on ChartInfo.GUID=t2.ChartGUID	"+
					 "where ChartGUID is not null  and "+
					 "IsHistory="+isHistory;
			
        }else{
        
        	sql=" select top "+limit+" * from "+
					"(select ROW_NUMBER() OVER (ORDER BY "+sort+" "+order+") AS rows ,* from "+
					"ChartInfo left join (	"+
					 "select ChartGUID from [HireList] "+
        			"where [HireList].ChartGUID not in(	"+        			
        			 "select ChartGUID from [HireList] "+
        			"where [HireList].State='未交'  and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+
        			"[HireList].ChartGUID )group by  chartGuid "+
        			") as t2 on ChartInfo.GUID=t2.ChartGUID	"+
        			"where ChartGUID is not null "+
        			")as w1 "+
        			"where rows>"+offset+" and "+
        			"IsHistory="+isHistory+" "+
        			"order by "+sort+" "+order;
		
        	sql2="select count(*) from "+
					"ChartInfo left join (	"+
					 "select ChartGUID from [HireList] "+
					 "where [HireList].ChartGUID not in(	"+        			
					 "select ChartGUID from [HireList] "+
					 "where [HireList].State='未交'  and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+
					 "[HireList].ChartGUID )group by  chartGuid "+
					 ") as t2 on ChartInfo.GUID=t2.ChartGUID	"+
					 "where ChartGUID is not null and "+
					 "IsHistory="+isHistory;
        	
        }
        
        System.out.println("sql="+sql);
        
		ChartInfo chartInfo=new ChartInfo();
		
		List list=this.getJdbcTemplate().query(sql, new RowMappers(chartInfo.getClass()));
		
		int total=(int) this.getJdbcTemplate().queryForMap(sql2).get("");
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		
		map.put("total", total);
		
		return map;
	}
	

	@Override
	public Map findOverdueHire(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map findOverdueChartInfo(Integer limit, Integer offset, String sort, String order,
			Integer isHistory,Map<String, String> search) {
		// TODO Auto-generated method stub
		
		Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM");
		
        String matureTime = null;
		
        matureTime=sdf.format(cal.getTime());
		
        System.out.println("matureTime="+matureTime);
        
        String sql = null;
        
        String sql2 = null;
        
        if(!search.isEmpty()){
        	
        	StringBuilder sb = new StringBuilder();
        	
        	String[] where=TransMapToString.get(search);
        	
        	int i=0;
			
        	for(String str : where){
			    
			    if(i%2==0){
			    	sb.append(str);
			    }else{
			    	sb.append("'"+str+"'");
			    	sb.append(" or ");
			    }
			    i++;
			    
			   System.out.println("sb="+sb);
			}
        	
        	String s = sb.toString();
			
			String serach=s.substring(0,s.length()-4);
			
			System.out.println("serach="+serach);
        	
			sql="select top "+limit+" * from "+
					"(select ROW_NUMBER() OVER (ORDER BY "+sort+" "+order+") AS rows ,* from "+
					"ChartInfo left join ( "+
					"select ChartGUID,COUNT(*)as c from [HireList] "+
					"where [HireList].State='未交' and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+ 
					"[HireList].ChartGUID ) t2 on ChartInfo.GUID=t2.ChartGUID where "+
					"ChartGUID is not null "+
					") as w1 "+
					"where rows>"+offset+" and ("+serach+") and "+
					"IsHistory="+isHistory+" and "+
					"ChartGUID is not null order by "+sort+" "+order;
		
        	sql2="select COUNT(*) from "+
        			"ChartInfo left join ( "+
        			"select ChartGUID,COUNT(*)as c from [HireList] "+
        			"where [HireList].State='未交' and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+
        			"[HireList].ChartGUID ) t2 on ChartInfo.GUID=t2.ChartGUID where "+
        			"("+serach+") and "+
        			"IsHistory="+isHistory+" and "+
        			"ChartGUID is not null ";
			
        }else{
        
        	sql="select top "+limit+" * from "+
					"(select ROW_NUMBER() OVER (ORDER BY "+sort+" "+order+") AS rows ,* from "+
					"ChartInfo left join ( "+
					"select ChartGUID,COUNT(*)as c from [HireList] "+
					"where [HireList].State='未交' and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+ 
					"[HireList].ChartGUID ) t2 on ChartInfo.GUID=t2.ChartGUID where "+
					"ChartGUID is not null "+
					") as w1 "+
					"where rows>"+offset+" and "+
					"IsHistory="+isHistory+" and "+
					"ChartGUID is not null order by "+sort+" "+order;
		
        	sql2="select COUNT(*) from "+
        			"ChartInfo left join ( "+
        			"select ChartGUID,COUNT(*)as c from [HireList] "+
        			"where [HireList].State='未交' and convert(varchar(11),[HireList].HireDate ,120 )<'"+matureTime+"' group by "+
        			"[HireList].ChartGUID ) t2 on ChartInfo.GUID=t2.ChartGUID where "+
        			"IsHistory="+isHistory+" and "+
        			"ChartGUID is not null ";
        }
        
		ChartInfo chartInfo=new ChartInfo();
		
		List list=this.getJdbcTemplate().query(sql, new RowMappers(chartInfo.getClass()));
		
		int total=(int) this.getJdbcTemplate().queryForMap(sql2).get("");
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		
		map.put("total", total);
		
		return map;
	}


	@Override
	public Map findAllChartInfo(Integer limit, Integer offset, String sort, String order, Map<String, String> search) {
		// TODO Auto-generated method stub
		
		ChartInfo chartInfo=new ChartInfo();
		
		chartInfo.setLimit(limit);
		chartInfo.setOffset(offset);
		chartInfo.setSort(sort);
		chartInfo.setOrder(order);
		chartInfo.setNotIn("GUID");
		
		if(!search.isEmpty()){
		    String[] where=TransMapToString.get(search);
		    chartInfo.setWhereTerm("OR");
		    chartInfo.setWhere(where);
		}
		
		List list=SelectExe.get(this.getJdbcTemplate(), chartInfo);
		
		int total=(int) SelectExe.getCount(this.getJdbcTemplate(), chartInfo).get("");
		
		Map map=new HashMap<>();
		
		map.put("rows", list);
		
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
		hireList.setOrder(order);
		hireList.setOffset(offset);
		hireList.setNotIn("GUID");
				
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


	@Override
	public Integer updateHireSetHireList(List files) {
		// TODO Auto-generated method stub
		if(!files.isEmpty()){
            try {
            	int u=0;
            	Iterator<String> iterator=files.iterator();
            	
                while (iterator.hasNext()){
                        String guid=iterator.next();

                    	String[] where={"GUID=",guid};
                    	
                    	Date date=new Date();
                    	
                    	HireList hireList=new HireList();
                    	hireList.setState("已交");
                    	hireList.setOptDate(date);
                    	hireList.setWhere(where);                    	
                    	
                    	u=UpdateExe.get(this.getJdbcTemplate(), hireList);
                    	
                    	if(u==0){
                    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    		return 0;
                    	}

                }

                return u;
               
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }else {
        	
        	return 2;
        	
        }
		
	}
			
	
}
