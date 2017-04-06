package com.voucher.manage.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voucher.manage.mapper.WeiXinMapper;
import com.voucher.manage.model.WeiXin;
import com.voucher.manage.service.WeiXinService;


@Service("weixinService")
public class WeiXinServiceImpl implements WeiXinService{

	private WeiXinMapper weixinMapper;
	
	@Autowired
	public void setWeiXinMapper(WeiXinMapper weixinMapper) {
		this.weixinMapper=weixinMapper;
	}
	
	public WeiXinMapper getAccessTokenMapper() {
		return weixinMapper;
	}
	
	@Override
	public WeiXin getAccessTokenById(Integer campusId) {
		// TODO Auto-generated method stub
		return weixinMapper.getAccessToken(campusId);
	}

	@Override
	public List<WeiXin> getAllCampusById(Integer cityId) {
		// TODO Auto-generated method stub
		return weixinMapper.getAllCampus(cityId);
	}

	@Override
	public Integer insertIntoCampus(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return weixinMapper.insertCampus(paramMap);
	}

	@Override
	public WeiXin getByCampusIds(Integer campusId) {
		// TODO Auto-generated method stub
		return weixinMapper.getByCampusId(campusId);
	}

	@Override
	public Integer updateCampusById(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return weixinMapper.updateCampus(paramMap);
	}

	@Override
	public WeiXin getCampusById(Integer campusId) {
		// TODO Auto-generated method stub
		return weixinMapper.getCampus(campusId);
	}

	@Override
	public Integer updateHomePageByCampusId(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return weixinMapper.updateHomePageByCampusId(paramMap);
	}

}
