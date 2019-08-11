package com.zh.cmd.impl.account;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;


import java.util.List;

@CommandMeta(name="CKZH",desc="查看账户",group="账号信息")
@AdminCommand   //加此注解，通过注解分类  管理员浏览账户
public class AccountBrowseCommand extends Abstract {

    public void execute(Subject subject) {
        System.out.println("查看账户");
        System.out.println("请输入你需要查看的账户名： ");
        String username = scan.nextLine();
        System.out.println("请输入您要查看的账户的ID： ");
        int  id=scan.nextInt();
        Account account = this.accountService.query(username,id);
        if (account != null) {
            System.out.println("账户");
            System.out.println("账户id: " + account.getId());
            System.out.println("账户名：" + account.getUsername());
            System.out.println("账户密码：" + account.getPassword());
            System.out.println("账户姓名：" + account.getName());
            System.out.println("账户类型：" + account.getAccountType());
            System.out.println("账户状态：" + account.getAccoutStatus());

        } else {
            System.out.println("此用户不存在！！");

        }
    }
}

