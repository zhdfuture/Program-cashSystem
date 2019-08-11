
package com.zh.cmd;

import lombok.Getter;
import lombok.ToString;

//类型：管理员， 用户
@Getter
@ToString
public enum AccountType {
    ADMIN(1,"管理员"),CUSTOM(2,"客户");
    private int flag;
    private String desc;
    AccountType(int flag,String desc){
        this.flag=flag;
        this.desc=desc;
    }
    public static AccountType valueOf(int flag){
        for(AccountType accountType:values()){
            if(accountType.flag==flag){
                return accountType;
            }
        }
        throw new RuntimeException("accountStatus flag"+flag+"not find!");
    }
}


