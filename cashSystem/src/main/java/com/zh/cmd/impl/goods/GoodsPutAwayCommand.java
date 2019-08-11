package com.zh.cmd.impl.goods;

import com.zh.cmd.Subject;
import com.zh.cmd.annotion.AdminCommand;
import com.zh.cmd.annotion.CommandMeta;
import com.zh.cmd.impl.Abstract;
import com.zh.entity.Goods;

@CommandMeta(name="SJSP",desc="上架商品",group="商品信息")
@AdminCommand
public class GoodsPutAwayCommand extends Abstract{
    public void execute(Subject subject) {
        System.out.println("***上架商品****");
        System.out.println("请输入商品的名称： ");
        String name=scan.next();
        System.out.println("商品简介：");
        String introduce=scan.next();
        System.out.println("商品库存：");
        int stock=Integer.parseInt(scan.next());
        System.out.println("请输入单位： ");
        String unit=scan.next();
        System.out.println("请输入商品价格: 单位（元）,保留小数点两位");
        int price=new Double(100*scan.nextDouble()).intValue();
        System.out.println("请输入商品折扣：");
        int discount=Integer.parseInt(scan.next());

        Goods goods=new Goods();
        goods.setName(name);
        goods.setIntroduce(introduce);
        goods.setStock(stock);
        goods.setUnit(unit);
        goods.setPrice(price);
        goods.setDiscount(discount);
boolean flag=this.goodsService.put(goods);
if(flag){
    System.out.println("上架成功");
}
else{
    System.out.println("上架失败");
}
    }

}
