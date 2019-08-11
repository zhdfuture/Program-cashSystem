package com.zh.cmd.impl.account;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;

@CommandMeta(name="CZMM",desc="重置密码",group="")
@AdminCommand
public class AccountPasswordResetCommand extends Abstract {


    public void execute(Subject subject) {

        System.out.println("重置密码");
        System.out.println("***************");
        System.out.println("请输入你需要重置密码的账户名： ");
        String username=scan.nextLine();
        System.out.println("请输入该账户名的编号：");
       int accountId=scan.nextInt();
        Account account=this.accountService.query(username,accountId);
        if(account==null){
            System.out.println("你输入的账户不存在，请重新输入！！！");
            execute(subject);
        }
        System.out.println("请输入你需要重置的密码： ");
        String password=scan.next();
        System.out.println("请再次输入密码： ");
        String password1=scan.next();
        if(!password.equals(password1)){
            System.out.println("您两次输入的密码不一致,请重新输入");
            execute(subject);
        }else{
           this.accountService.resetPassword(username,accountId,password);

        }
    }
}
