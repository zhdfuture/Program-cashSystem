package com.zh.dao;

import com.zh.entity.Goods;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDao extends BaseDao {

    public List<Goods> qureryAllGoods() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Goods> list = new ArrayList<>();
        //拿到连接
        try {
            connection = this.getConnection(true);
            String sql = "select * from goods";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            //返回结果集到resultSet
            while (resultSet.next()) {
                //解析resultset,拿到对应的account
                Goods goods = this.extractGoods(resultSet);
                if (goods != null) {
                    list.add(goods);
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return list;
    }

    private Goods extractGoods(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getInt("id"));
        goods.setName(resultSet.getString("name"));
        goods.setIntroduce(resultSet.getString("introduce"));
        goods.setDiscount(resultSet.getInt("discount"));
        goods.setStock(resultSet.getInt("stock"));
        goods.setPrice(resultSet.getInt("price"));
        goods.setUnit(resultSet.getString("unit"));
        return goods;

    }

    public boolean put(Goods goods) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = this.getConnection(true);
            String sql = "insert into goods(name,introduce,stock,unit,price,discount) values(?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, goods.getName());
            preparedStatement.setString(2, goods.getIntroduce());
            preparedStatement.setInt(3, goods.getStock());
            preparedStatement.setString(4, goods.getUnit());
            preparedStatement.setInt(5, goods.getPrice());
            preparedStatement.setInt(6, goods.getDiscount());
            flag = (preparedStatement.executeUpdate() == 1);
            resultSet = preparedStatement.getGeneratedKeys();
            //返回结果集到resultSet
            //解析resultset,拿到对应的account
            if (flag) {
                Integer id = resultSet.getInt(1);
                goods.setId(id);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return flag;
    }

    public Goods getGoods(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Goods goods = null;
        try {
            connection = this.getConnection(true);
            String sql = "select * from goods where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();//返回结果集到resultSet
            //解析resultset,拿到对应的account
            if (resultSet.next()) {
                goods = this.extractGoods(resultSet);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return goods;
    }

    public boolean modify(Goods goods) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean flag = false;
        try {
            //拿到连接
            connection = this.getConnection(true);
            String sql = "update goods set name=?,introduce=?,stock=?,unit=?,price=?,discount=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, goods.getName());
            preparedStatement.setString(2, goods.getIntroduce());
            preparedStatement.setInt(3, goods.getStock());
            preparedStatement.setString(4, goods.getUnit());
            preparedStatement.setInt(5, goods.getPrice());
            preparedStatement.setInt(6, goods.getDiscount());
            preparedStatement.setInt(7, goods.getId());
            flag = (preparedStatement.executeUpdate() == 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean soldOut(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try {
            connection = this.getConnection(true);
            String sql = "delete from goods where id=?";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);
            flag = (preparedStatement.executeUpdate() == 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean updateAfterPay(Goods goods,int goodsNum){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean flag=false;
        try{
            connection = this.getConnection(true);
            String sql="update goods set stock=? where id=?";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,goods.getStock()-goodsNum);
            preparedStatement.setInt(2,goods.getId());
            if(preparedStatement.executeUpdate()==1){
                flag=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return flag;
    }
}




