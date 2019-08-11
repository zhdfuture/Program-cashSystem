package com.zh.cmd;

import com.zh.entity.Account;

public class Subject {  //观察的是一个账户
   private  Account account;
    public void setAccount(Account account){
        this.account=account;

    }
    public Account getAccount(){
        return this.account;
    }
}
