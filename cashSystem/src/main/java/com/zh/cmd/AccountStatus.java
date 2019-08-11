package com.zh.cmd;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum AccountStatus {
    UNLOCK(1,"启用"),LOCK(2,"启停");
    private int flag;
    private String desc;
     AccountStatus(int flag,String desc){
        this.flag=flag;
        this.desc=desc;
    }
    //用户输入对应 的flag找是启用还是启停
    public static AccountStatus valueOf(int flag){   //用户输入flag
         for(AccountStatus accountStatus:values()){   //枚举的values方法，把所有的values放在一个数组，遍历数组
             if(accountStatus.flag==flag){
                 return accountStatus;      //返回当前的状态
             }
         }
         throw new RuntimeException("accountStatus flag"+flag+"not found!");
    }
}//状态：要么返回UNLOCK，要么返回LOCK
