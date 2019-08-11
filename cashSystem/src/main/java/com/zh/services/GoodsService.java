package com.zh.services;

import com.zh.dao.GoodsDao;
import com.zh.entity.Goods;

import java.util.List;

public class GoodsService {
    private GoodsDao goodsDao;
    public GoodsService(){
        this.goodsDao=new GoodsDao();
    }
    public List<Goods> qureryAllGoods(){
        return this.goodsDao.qureryAllGoods();   //登录成功，返回这个用户
    }
    public boolean put(Goods goods){
        return this.goodsDao.put(goods);
    }
    public  Goods getGoods(int id){
        return this.goodsDao.getGoods(id);     //通过id拿到货物
    }
    public boolean modify(Goods goods){
        return this.goodsDao.modify(goods);
    }
    public boolean soldOut(int id){
        return this.goodsDao.soldOut(id);
    }
    public boolean updateAfterPay(Goods goods,int goodsNum){
        return this.goodsDao.updateAfterPay(goods,goodsNum);
    }
    }

