package com.zh.cmd.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {

    String name();  //DL  启停账号的英文字母
    String desc();  //登陆
    String group(); //入口命令
}
