package com.zh.services;

import com.zh.dao.OrderDao;
import com.zh.entity.Account;
import com.zh.entity.Order;

import java.util.List;

public class OrderService {
    private OrderDao orderDao;
    public OrderService(){

        this.orderDao=new OrderDao();
    }
    public boolean commitOrder(Order order){
        return this.orderDao.commitOrder(order);
    }
    public List<Order> queryAllOrder(Integer id)
    {
        return this.orderDao.qureryAllOrders(id);
    }
}
