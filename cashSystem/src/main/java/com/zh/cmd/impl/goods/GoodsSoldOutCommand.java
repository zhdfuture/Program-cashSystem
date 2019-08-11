package com.zh.cmd.impl.goods;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Goods;

@CommandMeta(name="XJSP",desc="下架商品",group="商品信息")
@AdminCommand
public class GoodsSoldOutCommand extends Abstract{
    public void execute(Subject subject) {
        System.out.println("下架商品");

        printInfo("请输入下架商品的编号： ");
        int id=scan.nextInt();
        Goods goods=this.goodsService.getGoods(id);
        if(goods!=null){
            printInfo("商品信息如下");
            printInfo("商品编号："+goods.getId());
            printInfo("商品名称： "+goods.getName());
        printInfo("商品简介："+goods.getIntroduce());
        printInfo("商品库存："+goods.getStock());
      printInfo("商品单位："+goods.getUnit());
      printInfo("商品价格: "+goods.getPrice());
      printInfo("商品折扣: "+goods.getDiscount());
        printInfo("请确认是否下架：Y/N");
        String flag=scan.next();
        if("Y".equalsIgnoreCase(flag)){
            boolean ef=  this.goodsService.soldOut(id);
            if(ef){
                System.out.println("下架成功");
            }else{
                System.out.println("下架失败");
            }

        }else{
            System.out.println("不下架");
        }
    }
    }
}
