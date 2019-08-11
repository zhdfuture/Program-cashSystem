package com.zh.cmd.impl.common;

import com.zh.cmd.Commands;
import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.CustomerCommand;
import com.zh.cmd.annotion.EntranceCommand;
import com.zh.cmd.impl.Abstract;
import com.zh.cmd.impl.Command;
import com.zh.entity.Account;

import java.util.*;

@CommandMeta(name="BZXX",desc="帮助信息",group="公共命令")

@AdminCommand
@CustomerCommand
@EntranceCommand
public class HelpCommand extends Abstract {
    public void execute(Subject subject) {
        System.out.println("HelpCommand");
        Account account=subject.getAccount();
        if(account==null){   //第一次进来account为空
         entranceHelp();
        }else{
            switch(account.getAccountType()){
                case CUSTOM:
                    customerHelp();
                    break;
                case ADMIN:
                    adminHelp();
                    break;
                    default:
            }
        }
    }
    //Map.values()返回所有value的集合
    public void entranceHelp(){
        print("欢迎", Commands.ENTRANCE_COMMANDS.values());
    }
    public void customerHelp(){
        print("客户端", Commands.CUSTOMER_COMMANDS.values());
    }
    public void adminHelp(){
        print("管理端", Commands.ADMIN_COMMANDS.values());
    }
    //通用的打印命令
    public void print(String title, Collection<Command> collection){   //用collection接收
        System.out.println("********"+title+"*********");
        //再建一个新的map
        Map<String, List<String>> helpInfo=new HashMap<>();
        for(Command command:collection){
            CommandMeta commandMeta=command.getClass().getDeclaredAnnotation(CommandMeta.class);//拿到自己的注解
            String group=commandMeta.group();  //拿到新map的key

            List<String> func=helpInfo.get(group);
            //开始没有group，为空
            if(func==null){
                func=new ArrayList<>();  //生成
                helpInfo.put(group,func);  //将生成的放入
            }
            func.add(commandMeta.desc()+"("+commandMeta.name()+")"); //若不为空，直接添加进来
        }
        //entrySet取出键值对的集合

        /*
        Map中采用Entry内部类来表示一个映射项，映射项包含key和value
        entrySet 键-值对的集合
         */
        int i=0;
        for(Map.Entry<String,List<String>> entry:helpInfo.entrySet()){

            i++;
            System.out.println(i+"."+entry.getKey());
            int j=0;
            for(String item:entry.getValue()){  //每一个group都有一个list
                j++;
                System.out.println("\t"+(i)+"."+(j)+" "+item);
            }
        }
        System.out.println("输入菜单括号后面的编号(忽略大小写），进行下一步操作");
        System.out.println("********************************");
    }
}
