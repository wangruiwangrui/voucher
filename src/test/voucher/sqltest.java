package voucher;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoModel.RoomChartLog;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoRowMapper.RowMappers;
import com.voucher.manage.daoRowMapper.RowMappersJoin;
import com.voucher.manage.daoSQL.SelectSQL;
import com.voucher.manage.daoSQL.SelectSQLJoin;
import com.voucher.manage.daoSQL.annotations.DBTable;
import com.voucher.manage.daoSQL.annotations.QualifiLimit;
import com.voucher.manage.daoSQL.annotations.QualifiNotIn;
import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;
import com.voucher.manage.tools.MyTestUtil;
import com.voucher.manage.daoSQL.annotations.QualifiWhere;
import com.voucher.manage.daoModelJoin.*;


public class sqltest {

	public static void main(String[] args) throws ClassNotFoundException {
	   
		String a="ddddddddddddddd";
		String tableCreate;
		
      //  tableCreate = CreateSQL.get(User.class);
     //   System.out.println(tableCreate);                        //打印出来
        
		RoomInfo roomInfo=new RoomInfo();
		roomInfo.setLimit(30);
		roomInfo.setOffset(10);
		roomInfo.setNotIn("[GUID]");
		String[] where={"[TTT].[dbo].[RoomInfo].Region=","'江阳区'"};
		roomInfo.setWhere(where);
 
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		
		roomChangeHireLog.setLimit(30);
		roomChangeHireLog.setOffset(10);
		roomChangeHireLog.setNotIn("[GUID]");
		String[] where3={"[RoomManage].[dbo].[RoomChartLog].Region=","'江阳区'"};
		roomChangeHireLog.setWhere(where3);
		
		RoomChartLog roomChartLog=new RoomChartLog();
		roomChartLog.setLimit(30);
		roomChartLog.setOffset(10);
		roomChartLog.setNotIn("[GUID]");
		
		String[] where2={"[RoomManage].[dbo].[RoomChangeHireLog].Region=","'江阳区'"};
		roomChartLog.setWhere(where2);
		Object[] objects={roomChangeHireLog,roomInfo,roomChartLog};

			String sql=new SelectSQLJoin().get(objects,"[GUID]");
		
		//String sql=new SelectSQL().get(roomInfo);
		
		System.out.println(sql);
     
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.springframework.jdbc.core.JdbcTemplate");
		dataSource.setUrl("jdbc:jtds:sqlserver://127.0.0.1:1433/TTT");
		dataSource.setUsername("sa");
		dataSource.setPassword("123");
		
		JdbcTemplate getJdbcTemplate= new JdbcTemplate();
		
		getJdbcTemplate.setDataSource(dataSource);
		
		Class<?>[] classeNames={RoomChangeHireLog.class,RoomInfo.class, RoomChartLog.class};
		
    //	List list=getJdbcTemplate.query(sql,new RowMappers(RoomChangeHireLog.class));

		List list=getJdbcTemplate.query(sql, new RowMappersJoin(classeNames,RoomChangeHireLog_RoomChartLog.class));
		
		
		
		Iterator iterator=list.iterator();
		
		int j=0;
		while (iterator.hasNext()) {
   
				   MyTestUtil.print(iterator.next());

		   System.out.println("j="+j);
		   j++;
		}
		
		String sqlcount=SelectSQLJoin.getCount(objects, "[Charter]");
		System.out.println(sqlcount);
	}
}

