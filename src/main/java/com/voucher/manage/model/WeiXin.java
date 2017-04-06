package com.voucher.manage.model;

public class WeiXin {

	private Integer campusId;

	private String campusName;

	private Integer cityId;
	
	private String customService;
	
	private String homePage;
	
	private String appId;
	
	private String appSecret;
	
	private String accessToken;
	
	public Integer getCampusId() {
		return campusId;
	}

	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}

	public String getCampusName() {
		return campusName;
	}

	public void setCampusName(String campusName) {
		this.campusName = campusName == null ? null : campusName.trim();
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	
	public void setCustomService(String customService) {
		this.customService=customService;
	}
	
	public String getCustomService() {
		return customService;
	}
	
	public void setAppId(String appId) {
		this.appId=appId;
	}
	
	public String getAppId() {
		return appId;
	}
	
	
	public void setAppSecret(String appSecret) {
		this.appSecret=appSecret;
	}
	
	public String getAppSecret() {
		return appSecret;
	}
	
	
	public void setAccessToken(String accessToken) {
		this.accessToken=accessToken;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public String getHomePage() {
		return "<a href='"+homePage+"'> http://"+homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	
}
