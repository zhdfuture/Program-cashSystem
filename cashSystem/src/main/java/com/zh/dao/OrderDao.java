package com.zh.dao;

import com.zh.cmd.AccountType;
import com.zh.cmd.OrderStatus;
import com.zh.entity.Account;
import com.zh.entity.Order;
import com.zh.entity.OrederItem;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao extends BaseDao {
    public boolean commitOrder(Order order){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        //设置手动提交事务
        try{
            connection = this.getConnection(false);
            String insertOrderSql = "insert into `order`" +
                    "(id, account_id, create_time, finish_time, " +
                    "actual_amount, total_money, order_status, " +
                    "account_name) values (?,?,now(),now(),?,?,?,?)";

            String insertOrderItemSql="insert into order_item(order_id, goods_id, goods_name,goods_introduce, goods_num, goods_unit, goods_price, goods_discount) values (?,?,?,?,?,?,?,?)";
       preparedStatement=connection.prepareStatement(insertOrderSql);
       preparedStatement.setString(1,order.getId());
       preparedStatement.setInt(2,order.getAccount_id());
       preparedStatement.setInt(3,order.getActual_amount());
       preparedStatement.setInt(4,order.getTotal_money());
       preparedStatement.setInt(5,order.getOrder_status().getFlag());
       preparedStatement.setString(6,order.getAccount_name());
      if(preparedStatement.executeUpdate()==0){
          throw new RuntimeException("插入订单失败");
      }
      //开始 插入订单项
      preparedStatement=connection.prepareStatement(insertOrderItemSql);
      for(OrederItem orederItemt:order.orederItemList){
          preparedStatement.setString(1,orederItemt.getOrderId());
         preparedStatement.setInt(2,orederItemt.getGoodsId());
          preparedStatement.setString(3,orederItemt.getGoodsName());
          preparedStatement.setString(4,orederItemt.getGoodsIntroduce());
          preparedStatement.setInt(5,orederItemt.getGoodsNum());
          preparedStatement.setString(6,orederItemt.getGoodsUnit());
          preparedStatement.setInt(7,orederItemt.getGoodsPrice());
          preparedStatement.setInt(8,orederItemt.getGoodsDiscount());

          //将每一项preparedStatement 缓存好
          preparedStatement.addBatch(); //批量插入，把所有的预编译的语句先全部放好，然后addBatch批量插入


      }
      //批量操作数据库
      int[] effects=preparedStatement.executeBatch();
      for(int i:effects){
          if(i==0){
              throw new RuntimeException("插入订单明细失败");
          }
      }
      //手动提交事务
            connection.commit();
        }catch(Exception e){
            e.printStackTrace();

            if(connection!=null){
                try{
                    //回滚
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return false;
        }finally {
            this.closeResource(null,preparedStatement,connection);
        }
        return true;
    }

    //查询我的所有的订单
    public List<Order> qureryAllOrders(Integer accountId) {
        List<Order> orderList=new ArrayList<>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try{
            connection=this.getConnection(false);
            String sql=this.getSql("@query_order_by_account");
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountId);
            resultSet=preparedStatement.executeQuery();
            Order order=null;
            while (resultSet.next()){
                //第一次进入，先生成一张订单
                if(order==null){
                    order=new Order();
                    this.extractOrder(order,resultSet);
                    orderList.add(order);
                }
                String orderId=resultSet.getString("order_id"); //拿到当前订单的id
                //不相同重新生成一个订单对象，添加到订单的List，只有当订单信息不同的时候，才会生成一个订单
                //订单对象，只有一个，因为其中包含了很多的订单信息
                //如果为每一个订单信息，都生成一个订单时是不合理的
                if(!orderId.equals(order.getId())){
                    order=new Order();
                    this.extractOrder(order,resultSet);
                    orderList.add(order);
                }
                //添加具体的订单项orderItem
                OrederItem orderItem=this.extractOrderItem(resultSet);
                order.getOrederItemList().add(orderItem);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            if (connection != null) { //connection则表明数据库都没有连接上
                try {
                    //回滚
                    connection.rollback();
                } catch (SQLException el){
                    el.printStackTrace();
                }
            }
        }
        return orderList;
    }
    private Order extractOrder(Order order,ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getString("order_id"));
        order.setAccount_id(resultSet.getInt("account_id"));
        order.setAccount_name(resultSet.getString("account_name"));
        order.setCreate_time(resultSet.getTimestamp("create_time").toLocalDateTime());
        Timestamp finishTime = resultSet.getTimestamp("finish_time");
        if (finishTime != null) {
            order.setFinish_time(finishTime.toLocalDateTime());
        }
        order.setActual_amount(resultSet.getInt("actual_amount"));
        order.setTotal_money(resultSet.getInt("total_money"));
        order.setOrder_status(OrderStatus.valueOf(resultSet.getInt("order_status")));
    return order;
    }
    public OrederItem extractOrderItem(ResultSet resultSet) throws SQLException{
        OrederItem orderItem = new OrederItem();
        orderItem.setId(resultSet.getInt("item_id"));
        orderItem.setGoodsId(resultSet.getInt("goods_id"));
        orderItem.setGoodsName(resultSet.getString("goods_name"));
        orderItem.setGoodsIntroduce(resultSet.getString("goods_introduce"));
        orderItem.setGoodsNum(resultSet.getInt("goods_num"));
        orderItem.setGoodsUnit(resultSet.getString("goods_unit"));
        orderItem.setGoodsPrice(resultSet.getInt("goods_price"));
        orderItem.setGoodsDiscount(resultSet.getInt("goods_discount"));
        return orderItem;

    }
}
