package com.voucher.manage.dao;

import java.util.List;
import java.util.Map;

public interface FinanceDAO {

	public Map findMatureHire(Integer days,Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Map findGeneralChartInfo(Integer limit, Integer offset, String sort, String order,Integer isHistory,Map<String, String> search);
		
	public Map findOverdueHire(Integer limit, Integer offset, String sort, String order,Map<String, String> search);
	
	public Map findOverdueChartInfo(Integer limit, Integer offset, String sort, String order,Integer isHistory,Map<String, String> search);
		
	public Map findAllChartInfo(Integer limit, Integer offset, String sort, String order,Map<String, String> search);
	
	public Map<String, Object> getHireListByGUID(Integer limit, Integer offset, String sort, String order, Map search);
	
	public Integer updateHireSetHireList(List files);
	
}
