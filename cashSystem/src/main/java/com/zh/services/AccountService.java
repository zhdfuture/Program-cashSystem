package com.zh.services;

import com.zh.dao.AccountDao;
import com.zh.entity.Account;

public class AccountService {  //操作DAO层
    private AccountDao accountDao;
    public AccountService(){

        this.accountDao=new AccountDao();
    }
    public Account query(String username,int accountId){
        return this.accountDao.query(username,accountId);
    }
    public Account resetPassword(String username,int accountId,String password){
        return this.accountDao.resetPassword(username,accountId,password);
    }
    public Account setStatus(String username,int id){
        return this.accountDao.setStatus(username,id);
    }
    public Account login(String username,String password){

        return this.accountDao.login(username,password);   //登录成功，返回这个用户
    }
    public boolean register(Account account){
        return this.accountDao.regist(account);
    }
}
