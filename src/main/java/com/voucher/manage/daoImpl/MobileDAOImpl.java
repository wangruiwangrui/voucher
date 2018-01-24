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
import com.voucher.manage.daoModel.Assets.Hidden_Check_Date;
import com.voucher.manage.daoModel.Assets.Hidden_Neaten_Date;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Check_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Data_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Join;
import com.voucher.manage.daoModelJoin.Assets.Hidden_Neaten_Join;
import com.voucher.manage.daoModelJoin.Assets.Position_Hidden_Join;
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
				"[Hidden_Data].GUID, "+
			    "[Hidden_Data].URI, "+
				"[Hidden_Data].date, "+
			    "[Hidden_Data].NAME "+
				"FROM "+
				"[Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+  
				"where [Hidden_Data].GUID='"+GUID+"'  "+
				"AND ([Hidden_Data].TYPE ='png ' OR [Hidden_Data].TYPE ='jpg ' OR [Hidden_Data].TYPE ='jpeg ' OR [Hidden_Data].TYPE ='gif ' ) "+
				"order by [Hidden_Data].date desc ";
		
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

	
	@Override
	public Map<String, Object> positionHiddenImageQuery(HttpServletRequest request,List guidLits) {
		// TODO Auto-generated method stub
	
		String pathRoot = System.getProperty("user.home");
	
		String filePath=pathRoot+AbstractFileUpload.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(AbstractFileUpload.filePath);
		
		//List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		Map fileBytes=new HashMap<>();
		
		Iterator<Position_Hidden_Join> iterator=guidLits.iterator();
	
		while(iterator.hasNext()){			
		
			Position_Hidden_Join position_Hidden_Join = iterator.next();
		
			String GUID=position_Hidden_Join.getGUID();
		
			String sql="SELECT top 1 "+    
				"[Hidden_Data].GUID, "+
			    "[Hidden_Data].URI, "+
				"[Hidden_Data].date, "+
			    "[Hidden_Data].NAME "+
				"FROM "+
				"[Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+  
				"where [Hidden_Data].GUID='"+GUID+"'  "+
				"AND ([Hidden_Data].TYPE ='png ' OR [Hidden_Data].TYPE ='jpg ' OR [Hidden_Data].TYPE ='jpeg ' OR [Hidden_Data].TYPE ='gif ' ) "+
				"order by [Hidden_Data].date desc ";
		
			List hidden_Data_Joins=this.getJdbcTemplate().query(sql,new hiddenImageQueryRowMapper());
		
			try{
				Hidden_Data_Join hidden_Data_Join=(Hidden_Data_Join) hidden_Data_Joins.get(0);
							
				//String fileByte=Base64Test.getImageStr(filePath+"\\"+hidden_Data_Join.getURI());
				
				String oldFile=filePath+"\\"+hidden_Data_Join.getURI();
				
				CopyFile.set(imgPath, oldFile, hidden_Data_Join.getURI());
				
				fileBytes.put(GUID, AbstractFileUpload.filePath+"\\"+hidden_Data_Join.getURI());
		
			}catch (Exception e) {
			// TODO: handle exception
			//	e.printStackTrace();
			}
		
		}
	

		MyTestUtil.print(fileBytes);
		
		return fileBytes;
	}
	
	
	@Override
	public List allHiddenImageByGUID(HttpServletRequest request,Hidden_Join hidden_Join) {
		// TODO Auto-generated method stub
	
		String pathRoot = System.getProperty("user.home");
	
		String filePath=pathRoot+AbstractFileUpload.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(AbstractFileUpload.filePath);
		
		String GUID=hidden_Join.getGUID();
		
		String sql="SELECT "+    
				"[Hidden_Data].GUID, "+
			    "[Hidden_Data].URI, "+
				"[Hidden_Data].date, "+
			    "[Hidden_Data].NAME "+
				"FROM "+
				"[Hidden_Data] left join [Hidden] on [Hidden_Data].GUID=[Hidden].GUID "+  
				"where [Hidden_Data].GUID='"+GUID+"'  "+
				"AND ([Hidden_Data].TYPE ='png ' OR [Hidden_Data].TYPE ='jpg ' OR [Hidden_Data].TYPE ='jpeg ' OR [Hidden_Data].TYPE ='gif ' ) "+
				"order by [Hidden_Data].date desc ";
		
		List hidden_Data_Joins=this.getJdbcTemplate().query(sql,new hiddenImageQueryRowMapper());
		
		List fileBytes=new ArrayList<>();
		
		Iterator<Hidden_Data_Join> iterator=hidden_Data_Joins.iterator();
		
		while (iterator.hasNext()) {
			
			Hidden_Data_Join hidden_Data_Join=iterator.next();
			
			try{			
				String oldFile=filePath+"\\"+hidden_Data_Join.getURI();
			
				CopyFile.set(imgPath, oldFile, hidden_Data_Join.getURI());
			
				Map<String,String> map=new HashMap<>();
				
				map.put("name", hidden_Data_Join.getNAME());
				map.put("uri", AbstractFileUpload.filePath+"\\"+hidden_Data_Join.getURI());
				map.put("date", hidden_Data_Join.getDate().toString());
				MyTestUtil.print(map);
				fileBytes.add(map);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
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
	
	
	@Override
	public Map<String, Object> checkImageQuery(HttpServletRequest request,List guidLits) {
		// TODO Auto-generated method stub
	
		String pathRoot = System.getProperty("user.home");
	
		String filePath=pathRoot+AbstractFileUpload.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(AbstractFileUpload.filePath);
		
		//List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		Map fileBytes=new HashMap<>();
		
		Iterator<Hidden_Check_Join> iterator=guidLits.iterator();
	
		while(iterator.hasNext()){			
		
			Hidden_Check_Join hidden_Check_Join = iterator.next();
		
			String Check_id=hidden_Check_Join.getCheck_id();
		
			String sql="SELECT top 1 "+    
				"[Hidden_Check_Date].Check_id, "+
			    "[Hidden_Check_Date].URI, "+
				"[Hidden_Check_Date].date, "+
			    "[Hidden_Check_Date].NAME "+
				"FROM "+
				"[Hidden_Check_Date] left join [Hidden_Check] on [Hidden_Check_Date].Check_id=[Hidden_Check].Check_id "+  
				"where [Hidden_Check_Date].Check_id='"+Check_id+"'  "+
				"AND ([Hidden_Check_Date].TYPE ='png ' OR [Hidden_Check_Date].TYPE ='jpg ' OR [Hidden_Check_Date].TYPE ='jpeg ' OR [Hidden_Check_Date].TYPE ='gif ' ) "+
				"order by [Hidden_Check_Date].date desc ";
		
			List hidden_Data_Joins=this.getJdbcTemplate().query(sql,new checkImageQueryRowMapper());
		
			try{
				Hidden_Check_Date hidden_Check_Date=(Hidden_Check_Date) hidden_Data_Joins.get(0);
							
				//String fileByte=Base64Test.getImageStr(filePath+"\\"+hidden_Data_Join.getURI());
				
				String oldFile=filePath+"\\"+hidden_Check_Date.getURI();
				
				CopyFile.set(imgPath, oldFile, hidden_Check_Date.getURI());
				
				fileBytes.put(Check_id, AbstractFileUpload.filePath+"\\"+hidden_Check_Date.getURI());
		
			}catch (Exception e) {
			// TODO: handle exception
				e.printStackTrace();
			}
		
		}
	

		MyTestUtil.print(fileBytes);
		
		return fileBytes;
	}

	
	@Override
	public List allCheckImageByGUID(HttpServletRequest request,Hidden_Check_Join hidden_Check_Join) {
		// TODO Auto-generated method stub
	
		String pathRoot = System.getProperty("user.home");
	
		String filePath=pathRoot+AbstractFileUpload.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(AbstractFileUpload.filePath);
		
		String check_id=hidden_Check_Join.getCheck_id();
		
		String sql="SELECT "+    
				"[Hidden_Check_Date].check_id, "+
			    "[Hidden_Check_Date].URI, "+
				"[Hidden_Check_Date].date, "+
			    "[Hidden_Check_Date].NAME "+
				"FROM "+
				"[Hidden_Check_Date] left join [Hidden_Check] on [Hidden_Check_Date].check_id=[Hidden_Check].check_id "+  
				"where [Hidden_Check_Date].check_id='"+check_id+"'  "+
				"AND ([Hidden_Check_Date].TYPE ='png ' OR [Hidden_Check_Date].TYPE ='jpg ' OR [Hidden_Check_Date].TYPE ='jpeg ' OR [Hidden_Check_Date].TYPE ='gif ' ) "+
				"order by [Hidden_Check_Date].date desc ";
		
		List hidden_Data_Joins=this.getJdbcTemplate().query(sql,new checkImageQueryRowMapper());
		
		List fileBytes=new ArrayList<>();
		
		Iterator<Hidden_Check_Date> iterator=hidden_Data_Joins.iterator();
		
		while (iterator.hasNext()) {
			
			Hidden_Check_Date hidden_Check_Date=iterator.next();
			
			try{			
				String oldFile=filePath+"\\"+hidden_Check_Date.getURI();
			
				CopyFile.set(imgPath, oldFile, hidden_Check_Date.getURI());
			
				Map<String,String> map=new HashMap<>();
				
				map.put("name", hidden_Check_Date.getNAME());
				map.put("uri", AbstractFileUpload.filePath+"\\"+hidden_Check_Date.getURI());
				map.put("date", hidden_Check_Date.getDate().toString());
				
				fileBytes.add(map);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return fileBytes;
	
	}
	
	
	class checkImageQueryRowMapper implements RowMapper<Hidden_Check_Date> {
        //rs为返回结果集，以每行为单位封装着
        public Hidden_Check_Date mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Check_Date hidden_Check_Date=new Hidden_Check_Date();
        	hidden_Check_Date.setCheck_id(rs.getString("check_id"));
        	hidden_Check_Date.setURI(rs.getString("URI"));
        	hidden_Check_Date.setNAME(rs.getString("NAME"));
        	hidden_Check_Date.setDate(rs.getDate("date"));
            return hidden_Check_Date;
        }
    }
	
	
	@Override
	public Map<String, Object> neatenImageQuery(HttpServletRequest request,List guidLits) {
		// TODO Auto-generated method stub
	
		String pathRoot = System.getProperty("user.home");
	
		String filePath=pathRoot+AbstractFileUpload.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(AbstractFileUpload.filePath);
		
		//List<byte[]> fileBytes=new ArrayList<byte[]>();
		
		Map fileBytes=new HashMap<>();
		
		Iterator<Hidden_Neaten_Join> iterator=guidLits.iterator();
	
		while(iterator.hasNext()){			
		
			Hidden_Neaten_Join hidden_Neaten_Join = iterator.next();
		
			String neaten_id=hidden_Neaten_Join.getNeaten_id();
		
			String sql="SELECT top 1 "+    
				"[Hidden_Neaten_Date].neaten_id, "+
			    "[Hidden_Neaten_Date].URI, "+
				"[Hidden_Neaten_Date].date, "+
			    "[Hidden_Neaten_Date].NAME "+
				"FROM "+
				"[Hidden_Neaten_Date] left join [Hidden_Neaten] on [Hidden_Neaten_Date].neaten_id=[Hidden_Neaten].neaten_id "+  
				"where [Hidden_Neaten_Date].neaten_id='"+neaten_id+"'  "+
				"AND ([Hidden_Neaten_Date].TYPE ='png ' OR [Hidden_Neaten_Date].TYPE ='jpg ' OR [Hidden_Neaten_Date].TYPE ='jpeg ' OR [Hidden_Neaten_Date].TYPE ='gif ' ) "+
				"order by [Hidden_Neaten_Date].date desc ";
		
			List hidden_Data_Joins=this.getJdbcTemplate().query(sql,new neatenImageQueryRowMapper());
		
			try{
				Hidden_Neaten_Date hidden_Neaten_Date= (Hidden_Neaten_Date) hidden_Data_Joins.get(0);
							
				//String fileByte=Base64Test.getImageStr(filePath+"\\"+hidden_Data_Join.getURI());
				
				String oldFile=filePath+"\\"+hidden_Neaten_Date.getURI();
				
				CopyFile.set(imgPath, oldFile, hidden_Neaten_Date.getURI());
				
				fileBytes.put(neaten_id, AbstractFileUpload.filePath+"\\"+hidden_Neaten_Date.getURI());
		
			}catch (Exception e) {
			// TODO: handle exception
				e.printStackTrace();
			}
		
		}
	

		MyTestUtil.print(fileBytes);
		
		return fileBytes;
	}

	
	@Override
	public List allNeatenImageByGUID(HttpServletRequest request,Hidden_Neaten_Join hidden_Neaten_Join) {
		// TODO Auto-generated method stub
	
		String pathRoot = System.getProperty("user.home");
	
		String filePath=pathRoot+AbstractFileUpload.filePath;
        
		String imgPath=request.getSession().getServletContext().getRealPath(AbstractFileUpload.filePath);
		
		String neaten_id=hidden_Neaten_Join.getNeaten_id();
		
		String sql="SELECT "+    
			"[Hidden_Neaten_Date].neaten_id, "+
		    "[Hidden_Neaten_Date].URI, "+
			"[Hidden_Neaten_Date].date, "+
		    "[Hidden_Neaten_Date].NAME "+
			"FROM "+
			"[Hidden_Neaten_Date] left join [Hidden_Neaten] on [Hidden_Neaten_Date].neaten_id=[Hidden_Neaten].neaten_id "+  
			"where [Hidden_Neaten_Date].neaten_id='"+neaten_id+"'  "+
			"AND ([Hidden_Neaten_Date].TYPE ='png ' OR [Hidden_Neaten_Date].TYPE ='jpg ' OR [Hidden_Neaten_Date].TYPE ='jpeg ' OR [Hidden_Neaten_Date].TYPE ='gif ' ) "+
			"order by [Hidden_Neaten_Date].date desc ";
		
		List hidden_Data_Joins=this.getJdbcTemplate().query(sql,new neatenImageQueryRowMapper());
		
		List fileBytes=new ArrayList<>();
		
		Iterator<Hidden_Neaten_Date> iterator=hidden_Data_Joins.iterator();
		
		while (iterator.hasNext()) {
			
			Hidden_Neaten_Date hidden_Neaten_Date=iterator.next();
			
			try{			
				String oldFile=filePath+"\\"+hidden_Neaten_Date.getURI();
			
				CopyFile.set(imgPath, oldFile, hidden_Neaten_Date.getURI());
			
				Map<String,String> map=new HashMap<>();
				
				map.put("name", hidden_Neaten_Date.getNAME());
				map.put("uri", AbstractFileUpload.filePath+"\\"+hidden_Neaten_Date.getURI());
				map.put("date", hidden_Neaten_Date.getDate().toString());
				
				fileBytes.add(map);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return fileBytes;
	
	}
	
	
	class neatenImageQueryRowMapper implements RowMapper<Hidden_Neaten_Date> {
        //rs为返回结果集，以每行为单位封装着
        public Hidden_Neaten_Date mapRow(ResultSet rs, int rowNum) throws SQLException {    
            Hidden_Neaten_Date hidden_Neaten_Date=new Hidden_Neaten_Date();
        	hidden_Neaten_Date.setNeaten_id(rs.getString("neaten_id"));
        	hidden_Neaten_Date.setURI(rs.getString("URI"));
        	hidden_Neaten_Date.setNAME(rs.getString("NAME"));
        	hidden_Neaten_Date.setDate(rs.getDate("date"));
            return hidden_Neaten_Date;
        }
    }
	
}
