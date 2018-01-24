package com.voucher.manage.mapper;

import com.voucher.manage.model.User_Asset;

public interface User_AssetMapper {
    int insert(User_Asset record);

    int insertSelective(User_Asset record);
}