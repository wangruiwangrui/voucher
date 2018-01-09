package com.voucher.manage.daoImpl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.voucher.manage.dao.MobileDAO;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Data_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.file.AbstractFileUpload;
import com.voucher.manage.tools.Base64Test;
import com.voucher.manage.tools.CopyFile;
import com.voucher.manage.tools.MyTestUtil;

public class MobileDAOImpl extends JdbcDaoSupport implements MobileDAO{

	@Override
	public Map<String, Object> hiddenImageQuery(HttpServletRequest request,List guidLits) {
		// TODO Auto-generated method stub
	
		String pathRoot = System.getProperty("user.home");
	
		String filePath=pathRoot+AbstractFileUpload.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(AbstractFileUpload.filePath);
		
		//List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		Map fileBytes=new HashMap<>();
		
		Iterator<Hidden_Join> iterator=guidLits.iterator();
	
		while(iterator.hasNext()){			
		
			Hidden_Join hidden_Jion = iterator.next();
		
			String GUID=hidden_Jion.getGUID();
		
			String sql="SELECT top 1 "+    
				"[Assets].[dbo].[Hidden_Data].GUID, "+
			    "[Assets].[dbo].[Hidden_Data].URI, "+
				"[Assets].[dbo].[Hidden_Data].date, "+
			    "[Assets].[dbo].[Hidden_Data].NAME "+
				"FROM "+
				"[Assets].[dbo].[Hidden_Data] left join [Assets].[dbo].[Hidden] on [Assets].[dbo].[Hidden_Data].GUID=[Assets].[dbo].[Hidden].GUID "+  
				"where [Assets].[dbo].[Hidden_Data].GUID='"+GUID+"'  "+
				"AND ([Assets].[dbo].[Hidden_Data].TYPE ='png ' OR [Assets].[dbo].[Hidden_Data].TYPE ='jpg ' OR [Assets].[dbo].[Hidden_Data].TYPE ='jpeg ' OR [Assets].[dbo].[Hidden_Data].TYPE ='gif ' ) "+
				"order by [Assets].[dbo].[Hidden_Data].date desc ";
		
			List hidden_Data_Joins=this.getJdbcTemplate().query(sql,new hiddenImageQueryRowMapper());
		
			try{
				Hidden_Data_Join hidden_Data_Join=(Hidden_Data_Join) hidden_Data_Joins.get(0);
							
				//String fileByte=Base64Test.getImageStr(filePath+"\\"+hidden_Data_Join.getURI());
				
				String oldFile=filePath+"\\"+hidden_Data_Join.getURI();
				
				CopyFile.set(imgPath, oldFile, hidden_Data_Join.getURI());
				
				fileBytes.put(GUID, AbstractFileUpload.filePath+"\\"+hidden_Data_Join.getURI());
		
			}catch (Exception e) {
			// TODO: handle exception
				e.printStackTrace();
			}
		
		}
	

		MyTestUtil.print(fileBytes);
		
		return fileBytes;
	}

	
	class hiddenImageQueryRowMapper implements RowMapper<Hidden_Data_Join> {
        //rs为返回结果集，以每行为单位封装着
        public Hidden_Data_Join mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Data_Join hidden_Data_Join=new Hidden_Data_Join();
        	hidden_Data_Join.setGUID(rs.getString("GUID"));
        	hidden_Data_Join.setURI(rs.getString("URI"));
        	hidden_Data_Join.setNAME(rs.getString("NAME"));
        	hidden_Data_Join.setDate(rs.getDate("date"));
            return hidden_Data_Join;
        }
    }
	
}
