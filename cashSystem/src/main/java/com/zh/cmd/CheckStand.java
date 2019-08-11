package com.zh.cmd;

import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;

public class CheckStand extends Abstract{  //继承抽象类，一运行就会有Scanner，并且启动服务
    public static void main(String[] args) {
        Subject subject=new Subject();
        new CheckStand().execute(subject);  //通过对象调用自己的方法，观察的是subject（看是管理员还是用户）
    }

    @Override
    public void execute(Subject subject) {

        Commands.getCachedHelpCommands().execute(subject);
        while(true){
            System.out.println(">>>");
            String str=scan.nextLine();
            String commandCode=str.trim().toUpperCase();
            Account account=subject.getAccount();
            if(account==null){
                Commands.getEntranceCommand(commandCode).execute(subject);
            }else{
                switch(account.getAccountType()){
                    case ADMIN:
                        Commands.getAdminCommand(commandCode).execute(subject);
                        break;
                    case CUSTOM:
                        Commands.getCustomerCommand(commandCode).execute(subject);
                        break;
                        default:
                }
            }
        }
    }
}
