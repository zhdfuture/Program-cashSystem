package com.zh.cmd.impl.order;

import com.zh.cmd.OrderStatus;
import com.zh.cmd.Subject;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.CustomerCommand;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Account;
import com.zh.entity.Goods;
import com.zh.entity.Order;
import com.zh.entity.OrederItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CommandMeta(name="ZFDD",desc="支付订单",group="订单信息")
@CustomerCommand
public class OrederPayCommand extends Abstract {

    public void execute(Subject subject) {
        System.out.println("请输入您要购买的货物id以及数量多个货物之间使用，号隔开：格式：1-8，3-5");
        String string = scan.next();
        //[0]="1-8"
        String[] strings = string.split(",");
        //把所有需要购买的商品存放到goodsList中
        List<Goods> goodsList = new ArrayList<>();
        for (String goodsS : strings) {  //遍历
            //[0]="1",[1]="8"
            String[] str = goodsS.split("-");
            Goods goods = this.goodsService.getGoods(Integer.parseInt(str[0]));
            goods.setBuyGoodsNum(Integer.parseInt(str[1]));
            goodsList.add(goods);
        }
        Order order = new Order();
        order.setId(String.valueOf(System.currentTimeMillis()));  //获取当前的时间,避免重复
        order.setAccount_id(subject.getAccount().getId());
        order.setAccount_name(subject.getAccount().getName());
        order.setCreate_time(LocalDateTime.now());  //创建的时间，当前的时间

        int totalMoney = 0;
        int actualMoney = 0;
        //遍历集合计算总金额
        for (Goods goods : goodsList) {
            OrederItem orederItem = new OrederItem(); //订单项
            orederItem.setOrderId(order.getId());  //一设置，一得到
            orederItem.setGoodsId(goods.getId());
            orederItem.setGoodsName(goods.getName());
            orederItem.setGoodsIntroduce(goods.getIntroduce());
            orederItem.setGoodsNum(goods.getBuyGoodsNum());
            orederItem.setGoodsUnit(goods.getUnit());
            orederItem.setGoodsPrice(goods.getPrice());
            orederItem.setGoodsDiscount(goods.getDiscount());
            order.orederItemList.add(orederItem);    //每一次都把订单项存放在了orederItemList中
            int currentMoney = goods.getBuyGoodsNum() * goods.getPrice();
            totalMoney += currentMoney;  //不包含折扣
            actualMoney += currentMoney * goods.getDiscount() / 100;
        }
        order.setTotal_money(totalMoney);
        order.setActual_amount(actualMoney);
        order.setOrder_status(OrderStatus.PLAYING);

        //先进行展示当前订单的明细
        System.out.println(order);
        System.out.println("请输入是否支付订单：确认输入支付：zf");
        String confirm = scan.next();

        if ("zf".equalsIgnoreCase(confirm)) {
            order.setFinish_time(LocalDateTime.now());
            order.setOrder_status(OrderStatus.OK);   //支付完成
            boolean flag = this.orderService.commitOrder(order);
            if (flag) {//插入成功
                for (Goods goods : goodsList) {
                    boolean isUpdate = this.goodsService.updateAfterPay(goods, goods.getBuyGoodsNum());

                    if (isUpdate) {
                        System.out.println("库存更新成功");
                    } else {
                        System.out.println("库存更新失败");
                    }

                }
            } else {
                System.out.println("订单未支付成功，请重新下单！！！");
            }
        }
    }
}