package com.zh.cmd.impl.entrance;

import com.zh.cmd.AccountStatus;
import com.zh.cmd.AccountType;
import com.zh.cmd.Subject;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.EntranceCommand;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;

import static com.zh.cmd.AccountStatus.*;

@CommandMeta(name="ZC",desc="注册",group="入口命令")
@EntranceCommand
public class RegisterCommand extends Abstract {

    public void execute(Subject subject) {
        System.out.println("欢迎注册！");
        System.out.println("请输入用户名： ");
        String username=scan.nextLine();
        System.out.println("请输入密码：");
        String password=scan.nextLine();
        System.out.println("再次输入密码： ");
        String password1=scan.nextLine();
        if(!password.equals(password1)){
            System.out.println("两次的密码输入不一致，请重新输入");
            return;
        }
        System.out.println("请输入姓名:");
        String name=scan.nextLine();
        System.out.println("请输入账户类型：1.管理员 2.用户 ");
        Integer accountTypeHao=scan.nextInt();
        AccountType accountType=AccountType.valueOf(accountTypeHao);
        System.out.println("请输入状态：1.启用 2.启停");
        Integer accountStatusN=scan.nextInt();
       AccountStatus accountStatus=AccountStatus.valueOf(accountStatusN);
      final   Account account=new Account();   //不可改变
        account.setUsername(username);
        account.setPassword(password);
      account.setAccountType(accountType);
        account.setName(name);
     account.setAccoutStatus(accountStatus);
        boolean flag=this.accountService.register(account);
        if(flag){
            System.out.println("注册成功");
        }else{
            System.out.println("注册失败");
        }

    }
}
