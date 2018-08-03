package com.voucher.manage.dao;

import java.util.Map;

public interface FinanceDAO {

	public Map findMatureHire(Integer days,Integer limit, Integer offset, String sort, String order,
			Map<String, String> search);
	
	public Map findOverdueHire(Integer limit, Integer offset, String sort, String order,Map<String, String> search);
	
	public Map findOverdueChartInfo(Integer limit, Integer offset, String sort, String order,Map<String, String> search);
		
	public Map findAllChartInfo(Integer limit, Integer offset, String sort, String order,Map<String, String> search);
	
}
