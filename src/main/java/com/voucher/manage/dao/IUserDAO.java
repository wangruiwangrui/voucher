package com.voucher.manage.dao;

import java.util.List;

import com.voucher.manage.daoModel.Users;


public interface IUserDAO {

    public void addUser(Users user);

    public void deleteUser(int id);

    public void updateUser(Users user);

    public String searchUserName(int id);
    
    public Users searchUser(int id);
    
    public List<Users> findAll();

}
