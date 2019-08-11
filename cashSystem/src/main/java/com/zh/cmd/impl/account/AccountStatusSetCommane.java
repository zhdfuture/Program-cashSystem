package com.zh.cmd.impl.account;

import com.zh.cmd.AccountStatus;
import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;

@CommandMeta(name="QTZH",desc="启停账户",group="账号信息")
@AdminCommand
public class AccountStatusSetCommane extends Abstract{

    public void execute(Subject subject) {
        System.out.println("启停账户");
        System.out.println("请输入要重置状态的用户名：");
        String username=scan.nextLine();
        System.out.println("请输入您要启停的账户的ID");
        int id=scan.nextInt();
        Account account=this.accountService.query(username,id);
        if(account==null){
            System.out.println("你输入的用户不存在，请重新输入！！！");
            execute(subject);
        }
        if(account.getAccoutStatus()== AccountStatus.LOCK){
            System.out.println("该账户状态设置成功，处于启停状态");
        }
        this.accountService.setStatus(username,id);
    }
}
