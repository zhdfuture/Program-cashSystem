package com.zh.cmd.impl.order;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.CustomerCommand;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;
import com.zh.entity.Order;
import com.zh.entity.OrederItem;

import java.util.List;

@CommandMeta(name="LLDD",desc="浏览订单",group="订单信息")
@CustomerCommand
public class OrderBrowseCommand extends Abstract {
    public void execute(Subject subject) {
        System.out.println("我的订单列表");
        System.out.println("*******************");
        Account account = subject.getAccount();
        List<Order> list = this.orderService.queryAllOrder(account.getId());
        if (list.isEmpty()) {
            System.out.println("没有订单");
        } else {
            for (Order order : list) {
                System.out.println("**************************");
                System.out.println(order);
                System.out.println("**************************");
            }
        }
    }
}



