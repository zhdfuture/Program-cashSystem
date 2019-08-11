package com.zh.entity;

import com.zh.cmd.AccountType;
import com.zh.cmd.AccountStatus;
import lombok.Data;
@Data
public class Account {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private AccountType accountType;
    private AccountStatus accoutStatus;
}
