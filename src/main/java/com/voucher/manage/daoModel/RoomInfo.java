package com.voucher.manage.daoModel;

import java.io.Serializable;

import com.voucher.manage.daoSQL.annotations.DBTable;
import com.voucher.manage.daoSQL.annotations.QualifiLimit;
import com.voucher.manage.daoSQL.annotations.QualifiNotIn;
import com.voucher.manage.daoSQL.annotations.QualifiOffset;
import com.voucher.manage.daoSQL.annotations.QualifiOrder;
import com.voucher.manage.daoSQL.annotations.QualifiSort;
import com.voucher.manage.daoSQL.annotations.QualifiWhere;
import com.voucher.manage.daoSQL.annotations.QualifiWhereTerm;
import com.voucher.manage.daoSQL.annotations.SQLString;


@DBTable(name="[TTT].[dbo].[RoomInfo]")
public class RoomInfo implements Serializable{
   private static final long serialVersionUID = 1L; 	

   @SQLString(name="GUID")
   public String GUID;
   
   @SQLString(name="Num")
   private String Num;
   
   @SQLString(name="OriginalNum")
   private String OriginalNum;
   
   @SQLString(name="OriginalAddress")
   private String OriginalAddress;
   
   @SQLString(name="Address")
   private String Address;

   @SQLString(name="Region")
   private String Region;
   
   public String getGUID() {
	  return GUID;
   }

   public void setGUID(String gUID) {
	  GUID = gUID;
   }

   public String getNum() {
	  return Num;
   }

    public void setNum(String num) {
	  Num = num;
    }

    public String getOriginalNum() {
	  return OriginalNum;
    } 

public void setOriginalNum(String originalNum) {
	OriginalNum = originalNum;
}

public String getOriginalAddress() {
	return OriginalAddress;
}

public void setOriginalAddress(String originalAddress) {
	OriginalAddress = originalAddress;
}

public String getAddress() {
	return Address;
}

public void setAddress(String address) {
	Address = address;
}


/*
 * 数据库查询参数
 */
 
 @QualifiLimit(name="limit")
 private Integer limit;
 @QualifiOffset(name="offset")
 private Integer offset;
 @QualifiNotIn(name="notIn")
 private String notIn;
 @QualifiSort(name="sort")
 private String sort;
 @QualifiOrder(name="order")
 private String order;
 /*
  * 数据库查询where变量
  */
 @QualifiWhere(name="where")
 private String[] where;
 @QualifiWhereTerm(name="whereTerm")
 private String whereTerm;              //多个where的连接条件            
 
 
 public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String[] getWhere() {
		return where;
	}

	public void setWhere(String[] where) {
		this.where = where;
	}

	public String getNotIn() {
		return notIn;
	}

	public void setNotIn(String notIn) {
		this.notIn = notIn;
	}

	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	public String getWhereTerm() {
		return whereTerm;
	}

	public void setWhereTerm(String whereTerm) {
		this.whereTerm = whereTerm;
	}

}
