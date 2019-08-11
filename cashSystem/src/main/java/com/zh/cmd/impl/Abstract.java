package com.zh.cmd.impl;

import com.zh.services.AccountService;
import com.zh.services.GoodsService;
import com.zh.services.OrderService;

public abstract class Abstract implements Command  {//实现command接口
    //启动所有的服务
public AccountService accountService;
public GoodsService goodsService;
public OrderService orderService;
public  Abstract(){
    this.accountService=new AccountService();
    this.goodsService=new GoodsService();
    this.orderService=new OrderService();
}
public void printInfo(String info){
    System.out.println(info);
}
}
