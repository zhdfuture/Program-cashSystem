package com.zh.cmd.impl.goods;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.annotion.CustomerCommand;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Goods;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@CommandMeta(name="LLSP",desc="浏览商品",group="商品信息")
@AdminCommand
@CustomerCommand
public class GoodsBrowseCommand extends Abstract {

    public void execute(Subject subject) {
        System.out.println("*****浏览商品******");
        //1.查询所有商品
        List<Goods> list = this.goodsService.qureryAllGoods();
        if (list.isEmpty()) {
            System.out.println("货物为空");
        }
        else {
            for(Goods goods:list){
                System.out.println(goods);
            }
        }
    }
}

