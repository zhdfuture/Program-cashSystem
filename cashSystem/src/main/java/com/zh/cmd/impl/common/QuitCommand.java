package com.zh.cmd.impl.common;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.CustomerCommand;
import com.zh.cmd.annotion.EntranceCommand;
import com.zh.cmd.impl.Abstract;
@CommandMeta(name="TCXT",desc="退出系统",group="公共命令")

@AdminCommand
@CustomerCommand
@EntranceCommand
public class QuitCommand extends Abstract{
    public void execute(Subject subject) {
        System.out.println("退出系统");
        scan.close();
        System.exit(0);  //正常退出
    }
}
