package com.zh.cmd;

import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.CustomerCommand;
import com.zh.cmd.annotion.EntranceCommand;
import com.zh.cmd.impl.Command;
import com.zh.cmd.impl.account.AccountBrowseCommand;
import com.zh.cmd.impl.account.AccountPasswordResetCommand;
import com.zh.cmd.impl.account.AccountStatusSetCommane;
import com.zh.cmd.impl.common.AboutCommand;
import com.zh.cmd.impl.common.HelpCommand;
import com.zh.cmd.impl.common.QuitCommand;
import com.zh.cmd.impl.entrance.LoginCommand;
import com.zh.cmd.impl.entrance.RegisterCommand;
import com.zh.cmd.impl.goods.GoodsBrowseCommand;
import com.zh.cmd.impl.goods.GoodsPutAwayCommand;
import com.zh.cmd.impl.goods.GoodsSoldOutCommand;
import com.zh.cmd.impl.goods.GoodsUpdateCommand;
import com.zh.cmd.impl.order.OrderBrowseCommand;
import com.zh.cmd.impl.order.OrederPayCommand;

import java.util.*;

public class Commands {
    public static Map<String,Command> ADMIN_COMMANDS=new HashMap<>();  //map表示共有的
    public static Map<String,Command> CUSTOMER_COMMANDS=new HashMap<>();
    public static Map<String,Command> ENTRANCE_COMMANDS=new HashMap<>();
    //存放所有的命令的集合
    private static final Set<Command> COMMANDS=new HashSet<>();
   private static final Command CACHED_HELP_COMMANDS; //类型是Command,是一个接口，向下继承的关系
    static {  //静态代码块（类加载时进行初始化）
        Collections.addAll(COMMANDS,  //将所有命令放到COMMANDS集合中
                new AccountBrowseCommand(),
                new AccountPasswordResetCommand(),
                new AccountStatusSetCommane(),
                new AboutCommand(),
                CACHED_HELP_COMMANDS= new HelpCommand(),//将HelpCommand进行缓存
                new QuitCommand(),
                new LoginCommand(),
                new RegisterCommand(),
                new GoodsBrowseCommand(),
                new GoodsPutAwayCommand(),
                new GoodsSoldOutCommand(),
                new GoodsUpdateCommand(),
                new OrderBrowseCommand(),
                new OrederPayCommand());
        for(Command command:COMMANDS){
            //利用反射，将命令进行分类到不同的map
            Class<?> cls=command.getClass();
            AdminCommand adminCommand=cls.getDeclaredAnnotation(AdminCommand.class);//得到字节码文件中的注解
            CustomerCommand customerCommand=cls.getDeclaredAnnotation(CustomerCommand.class);
            EntranceCommand entranceCommand=cls.getDeclaredAnnotation(EntranceCommand.class);
            CommandMeta commandMeta=cls.getDeclaredAnnotation(CommandMeta.class);
           if(commandMeta==null){  //为空，继续找
               continue;
           }
           String commandKey=commandMeta.name();    //拿到 key值（因为所有的登录信息都放在commandMeta中）
            if(adminCommand!=null){
                ADMIN_COMMANDS.put(commandKey,command);  //将这个命令放进去
            }
            if(customerCommand!=null){
                CUSTOMER_COMMANDS.put(commandKey,command);
            }
            if(entranceCommand!=null){
                ENTRANCE_COMMANDS.put(commandKey,command);
            }
        }
    }


    //在map中得到对应的对象

    //得到缓存的命令
    public static Command getCachedHelpCommands() {
        return CACHED_HELP_COMMANDS;
    }
    public static Command getAdminCommand(String commandKey){
        return getCommand(commandKey,ADMIN_COMMANDS);
    }
    public static Command getCustomerCommand(String commandKey){
        return getCommand(commandKey,CUSTOMER_COMMANDS);
    }
    public static Command getEntranceCommand(String commandKey){
        return getCommand(commandKey,ENTRANCE_COMMANDS);
    }
   //因为每一个map都有自己对应的参数，所以给一个参数Map<String,Command>commandMap
   //给一个通用的方法
    public static Command getCommand(String commandKey,Map<String,Command>commandMap){
        //遍历相应的map，根据commandKey，得到相应的value值
        return commandMap.getOrDefault(commandKey,CACHED_HELP_COMMANDS);
        //找不到返回帮助（缓存好的命令）

    }
}
