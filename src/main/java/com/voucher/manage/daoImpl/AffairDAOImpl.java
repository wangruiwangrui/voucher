package com.voucher.manage.daoImpl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.voucher.manage.dao.AffairDAO;
import com.voucher.manage.daoModel.RoomInfoRowMapper;
import com.voucher.manage.model.Affair;

public class AffairDAOImpl extends JdbcDaoSupport implements AffairDAO{
	private static final Logger logger = LoggerFactory.getLogger("AffairDAO");
	
	@Override
	public int insertTest(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		String sql=" insert into  [TTT].[dbo].[test]  values(?,?)";
		
		return this.getJdbcTemplate().update(sql, 1,"qq");
	}

	@Override
	public int insertTest2(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		String sql="insert into  [TTT].[dbo].[test2]  values(?,?)";
		return this.getJdbcTemplate().update(sql, 1,"ww");
	}

	@Override
	public int  insertTest3(Map<String, Object> paramMap3) {
		// TODO Auto-generated method stub
		String sql=" insert into  test3  values('1','ww')";
		return this.getJdbcTemplate().queryForInt(sql);
	}

	public int  insertAll(Map<String, Object> paramMap) {
		 Map<String, Object> paramMap3 = new HashMap<String, Object>();
			
			Affair a=new Affair();
		    a=selectTest3();
		    int i;
		    
		    int j=a.getAmount();
		if(j>0){    
		    i=insertTest(paramMap);
		    i=insertTest2(paramMap);
		    
			paramMap3.put("amount", j+1);
		    
	        i=insertTest3(paramMap3);
	        if(i==0){                 //��������0��ȡ�����׻ع����ύ����
	        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        }
	        logger.info("i=-===="+i);
		}else{
			i=0;
		}
		 return i;
	}

	public Affair selectTest3() {
		// TODO Auto-generated method stub
		String sql=" select amount from test3 where id=1";
		int i=this.getJdbcTemplate().queryForInt(sql);
		Affair affair=new Affair();
		affair.setAmount(i);
		return affair;
	}

	@Override
	public int insert1(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		
		Affair a=new Affair();
	    a=selectTest3();
	    int i;
	    
	    int j=a.getAmount();
	if(j>0){ 
	    i=insertTest(paramMap);
	    
		paramMap3.put("amount", j+1);
	    
        i=insertTest3(paramMap3);
        if(i==0){                 //��������0��ȡ�����׻ع����ύ����
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        logger.info("i=-===="+i);
	}else{
		i=0;
	}
	
	
	 return i;
	}

	@Override
	public int insert2(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		
		Affair a=new Affair();
	    a=selectTest3();
	    int i;
	    
	    int j=a.getAmount();
	if(j>0){    
	    i=insertTest2(paramMap);
	    
		paramMap3.put("amount", j+1);
	    
        i=insertTest3(paramMap3);
        if(i==0){                 //��������0��ȡ�����׻ع����ύ����
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        logger.info("i=-===="+i);
	}else{
		i=0;
	}
	 return i;
	}
}
