package voucher;

import java.sql.Date;

import com.voucher.manage.daoModel.RoomChangeHireLog;
import com.voucher.manage.daoModel.RoomChartLog;
import com.voucher.manage.daoModel.RoomInfo;
import com.voucher.manage.daoSQL.SelectSQL;
import com.voucher.manage.daoSQL.SelectSQLJion;
import com.voucher.manage.daoSQL.annotations.DBTable;
import com.voucher.manage.daoSQL.annotations.QualifiLimit;
import com.voucher.manage.daoSQL.annotations.QualifiNotIn;
import com.voucher.manage.daoSQL.annotations.SQLDateTime;
import com.voucher.manage.daoSQL.annotations.SQLFloat;
import com.voucher.manage.daoSQL.annotations.SQLInteger;
import com.voucher.manage.daoSQL.annotations.SQLString;
import com.voucher.manage.daoSQL.annotations.QualifiWhere;



public class sqltest {

	public static void main(String[] args) throws ClassNotFoundException {
	   
		String a="ddddddddddddddd";
		String tableCreate;
		
      //  tableCreate = CreateSQL.get(User.class);
     //   System.out.println(tableCreate);                        //打印出来
        
		RoomInfo roomInfo=new RoomInfo();
		roomInfo.setLimit(30);
		roomInfo.setOffset(10);
		String[] where={"Region=","'江阳区'"};
		roomInfo.setWhere(where);
 
		RoomChangeHireLog roomChangeHireLog=new RoomChangeHireLog();
		
		roomChangeHireLog.setLimit(30);
		roomChangeHireLog.setOffset(10);
		roomChangeHireLog.setNotIn("[GUID]");
		
		
		RoomChartLog roomChartLog=new RoomChartLog();
		roomChartLog.setLimit(30);
		roomChartLog.setOffset(10);
		roomChartLog.setNotIn("[GUID]");
		Object[] objects={roomChangeHireLog,roomChartLog};
		
		String sql=new SelectSQLJion().get(objects,"[GUID]");
		
		System.out.println(sql);
     

	}
}

@DBTable(name="ggg")
class User {
    @SQLInteger(name="id")
    public Integer id;
    @SQLString(name="30")
    public String name;
    @SQLString(name="passwd")
    public String password;
    @SQLString(name="sss")
    public String ssss;
    @SQLString(name="www")
    public String www;
    
    @SQLFloat(name="bbb")
    public Float bbb;
    @SQLDateTime(name="rrrrrr")
    public Date time;
    
    @QualifiWhere(name="ss")
    public String where;
    
    @QualifiLimit(name="20")
    public String limit;
    
    @QualifiNotIn(name="[GUID]")
    public String NotIn;
}


@DBTable(name="[TTT].[dbo].[RoomInfo]")
class RoomInfo2{
	@SQLString(name="count(*)")
	public String count;
	
	@QualifiWhere(name="ww")
    public String where;
}