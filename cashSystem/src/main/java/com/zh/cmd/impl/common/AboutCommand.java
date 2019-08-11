package com.zh.cmd.impl.common;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.CustomerCommand;
import com.zh.cmd.annotion.EntranceCommand;
import com.zh.cmd.impl.Abstract;
@CommandMeta(name="GYXT",desc="关于系统",group="公共命令")
@AdminCommand
@CustomerCommand
@EntranceCommand
public class AboutCommand extends Abstract {
    public void execute(Subject subject) {
        System.out.println("******基于字符界面的收银台系统*****");
        System.out.println("*********作者：张豪****************");
        System.out.println("*********时间：20180804***********");
        System.out.println("**********************************");
    }
}
