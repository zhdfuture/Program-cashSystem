package com.zh.cmd.impl.goods;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Goods;

@CommandMeta(name="GXSP",desc="更新商品",group="商品信息")
@AdminCommand
public class GoodsUpdateCommand extends Abstract{
    public void execute(Subject subject) {
        System.out.println("****更新商品***");
        printInfo("请输入更新商品的编号： ");
        int id=scan.nextInt();
        Goods goods= this.goodsService.getGoods(id);
        if(goods==null){
            printInfo("没有此编号的货物");
            return;
        }
        else{
            printInfo("商品信息如下：");
            System.out.println(goods);
        }
        printInfo("请输入需要更新的商品名称： ");
        String name=scan.next();
        printInfo("请输入需要更新的商品简介：");
        String introduce=scan.next();
        printInfo("请输入商品库存：");
        int stock=Integer.parseInt(scan.next());
        System.out.println("请输入单位： ");
        String unit=scan.next();
        System.out.println("请输入商品价格: 单位（元）,保留小数点两位");
        int price=new Double(100*scan.nextDouble()).intValue();
        System.out.println("请输入商品折扣：");
        int discount=Integer.parseInt(scan.next());


        printInfo("请确认是否更新：Y/N");
        String flag=scan.next();
        if("Y".equalsIgnoreCase(flag)){
            goods.setName(name);
            goods.setIntroduce(introduce);
            goods.setStock(stock);
            goods.setUnit(unit);
            goods.setPrice(price);
            goods.setDiscount(discount);
            //更新数据库表
            boolean ef=  this.goodsService.modify(goods);
            if(ef){
                System.out.println("更新成功");
            }else{
                System.out.println("更新失败");
            }

        }else{
            System.out.println("不执行更新");
        }
    }
}
