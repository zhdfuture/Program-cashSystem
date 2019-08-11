package com.zh.dao;

import com.zh.cmd.AccountStatus;
import com.zh.cmd.AccountType;
import com.zh.entity.Account;
import lombok.val;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

public class AccountDao extends BaseDao{

    //操作数据库
    public Account query(String username,int accountId){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        Account account=null;
try{
    connection=this.getConnection(true);
    String sql="select * from account where username=? and id=?";
    preparedStatement=connection.prepareStatement(sql);
    preparedStatement.setString(1,username);
    preparedStatement.setInt(2,accountId);
    resultSet=preparedStatement.executeQuery();
    //返回结果集到resultSet
    if(resultSet.next()){
        //解析resultset,拿到对应的account
        account= this.extractAccount(resultSet);

    }

}catch(SQLException e){
    e.printStackTrace();
}
        return account;
    }
public Account resetPassword(String username,int accountId,String password){
    Connection connection=null;
    PreparedStatement preparedStatement=null;

    Account account=null;
    int flg = 0;
    try{
        //拿到连接
        connection=this.getConnection(true);
        String sql=" update account set password=? where id=? and username=?";
       preparedStatement =connection.prepareStatement(sql);
      preparedStatement.setString(1,DigestUtils.md5Hex(password));
        preparedStatement.setInt(2,accountId);
        preparedStatement.setString(3,username);
        flg = preparedStatement.executeUpdate();
        if(flg!=0){
            account = this.login(username,password);
            System.out.println("重置密码成功！");
        }else{
            System.out.println("重置密码失败！");
        }

    }catch(SQLException e){
        e.printStackTrace();
    }
    return account;
}
public Account setStatus(String username,int id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Account account = null;
     int flag=0;
    try {
        //拿到连接
        connection = this.getConnection(true);
        String sql = "update account set account_status=2 where username=? and id=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2,id);
        flag= preparedStatement.executeUpdate();
        //返回结果集到resultSet
        if (flag!=0) {
            System.out.println("启停成功");
        }else{
            System.out.println("启停失败");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return account;
}
    public Account login(String username,String password){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        Account account=null;

        try{
            //拿到连接
            connection=this.getConnection(true);
            String sql="select id,username,password,name,account_type,account_status from account where username=? and password=?";
            preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, DigestUtils.md5Hex(password));

            resultSet=preparedStatement.executeQuery();
            //返回结果集到resultSet
            if(resultSet.next()){
                //解析resultset,拿到对应的account
              account= this.extractAccount(resultSet);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return account;
    }
    private Account extractAccount(ResultSet resultSet) throws SQLException {
        Account account=new Account();
        account.setId(resultSet.getInt("id"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setName(resultSet.getString("name"));
        account.setAccountType(AccountType.valueOf(resultSet.getInt("account_type")));
        account.setAccoutStatus(AccountStatus.valueOf(resultSet.getInt("account_status")));

        return account;

    }
    //注册无结果集
    public boolean regist(Account account){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        boolean flag=false;
        try{
            //拿到连接
            connection=this.getConnection(true);
            String sql="insert into account(username,password,name,account_type,account_status)values(?,?,?,?,?)";
            preparedStatement =connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2, DigestUtils.md5Hex(account.getPassword()));
           preparedStatement.setString(3,account.getName());
             preparedStatement.setInt (4, account.getAccountType().getFlag());
         preparedStatement.setInt(5,account.getAccoutStatus().getFlag());
                 flag=(preparedStatement.executeUpdate()==1);
                 //获取自增的主键
                 resultSet=preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int id=resultSet.getInt(1);
                    account.setId(id);
            }
    } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeResource(resultSet,preparedStatement,connection);
        }
        return flag;
    }
}
