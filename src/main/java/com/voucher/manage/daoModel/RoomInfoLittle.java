package com.voucher.manage.daoModel;

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
public class RoomInfoLittle {
	
	private static final long serialVersionUID = 1L;
	
	@SQLString(name="Address")
	private String Address;
	
	@SQLString(name="ManageRegion")
	private String ManageRegion;

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getManageRegion() {
		return ManageRegion;
	}

	public void setManageRegion(String manageRegion) {
		ManageRegion = manageRegion;
	}
	
	/*
	*数据库查询参数
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
	    @QualifiWhere(name="where")
	    private String[] where;
	    @QualifiWhereTerm(name="whereTerm")
	    private String whereTerm;


		public void setLimit(Integer limit){
			this.limit = limit;
		}

		public Integer getLimit(){
			return limit;
		}

		public void setOffset(Integer offset){
			this.offset = offset;
		}

		public Integer getOffset(){
			return offset;
		}

		public void setNotIn(String notIn){
			this.notIn = notIn;
		}

		public String getNotIn(){
			return notIn;
		}

		public void setSort(String sort){
			this.sort = sort;
		}

		public String getSort(){
			return sort;
		}

		public void setOrder(String order){
			this.order = order;
		}

		public String getOrder(){
			return order;
		}

		public void setWhere(String[] where){
			this.where = where;
		}

		public String[] getWhere(){
			return where;
		}

		public void setWhereTerm(String whereTerm){
			this.whereTerm = whereTerm;
		}

		public String getWhereTerm(){
			return whereTerm;
		}

}
