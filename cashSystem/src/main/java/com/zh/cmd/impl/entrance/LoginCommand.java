package com.zh.cmd.impl.entrance;

import com.zh.cmd.AccountStatus;
import com.zh.cmd.Subject;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.EntranceCommand;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;

@CommandMeta(name="DL",desc="登录",group="入口命令")
@EntranceCommand
public class LoginCommand extends Abstract {
    public void execute(Subject subject) {
        Account account=subject.getAccount();
        if(account!=null){
            System.out.println("您已经登录过了");
        }
        System.out.println("输入用户名: ");
        String username=scan.nextLine();
        System.out.println("输入密码");
        String password=scan.nextLine();

     account=this.accountService.login(username,password);

     if(account!=null&&account.getAccoutStatus()== AccountStatus.UNLOCK){   //UNLOCK启用
         System.out.println(account.getAccountType()+"登录成功");//看是管理员登录成功，还是用户登录成功
         subject.setAccount(account);
     }else{
         System.out.println("密码或者用户名错误");
     }
    }
}
