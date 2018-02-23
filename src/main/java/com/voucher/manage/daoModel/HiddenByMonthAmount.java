package com.voucher.manage.daoModel;

import java.io.Serializable;

public class HiddenByMonthAmount implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String year;
	
	private int amount;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
