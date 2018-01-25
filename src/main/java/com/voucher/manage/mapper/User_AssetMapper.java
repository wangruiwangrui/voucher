package com.voucher.manage.mapper;

import com.voucher.manage.model.User_Asset;

public interface User_AssetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_Asset record);

    int insertSelective(User_Asset record);

    User_Asset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_Asset record);

    int updateByPrimaryKey(User_Asset record);
    
    int getCountUser_AssetByOpenId(String openId);
}