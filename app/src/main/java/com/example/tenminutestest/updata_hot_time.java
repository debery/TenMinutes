package com.example.tenminutestest;

import android.os.Looper;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class updata_hot_time {
    public static void updata_hot(String hot){

    Connection connection = null;
    try {
        String url = "jdbc:mysql://120.24.191.82:3306/timeapp?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
        //连接数据库使用的用户名
        String userName = "timeapp";
        //连接的数据库时使用的密码
        String password = "user12345";

        //1、加载驱动
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        System.out.println("驱动加载成功！！！");
        //2、获取与数据库的连接
        connection = DriverManager.getConnection(url, userName, password);
        System.out.println("连接数据库成功！！！");
        getSubUtil(hot,connection);
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (
    SQLException throwables) {
        throwables.printStackTrace();
    }}
    public static void getSubUtil(String str,Connection connection) throws SQLException{

        Pattern p=Pattern.compile("\\#(.*?)\\#");
        Matcher m=p.matcher(str);

        String sql="select * from hot_page where pname=?";

        while (m.find()) {
            String paramStr=m.group(1);
            System.out.println(paramStr);

            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,paramStr);
            ResultSet result=preparedStatement.executeQuery();


            result.last();
            int count=result.getRow();

            if(count==0){
                preparedStatement=connection.prepareStatement("INSERT INTO hot_page (pname,time) VALUES (?,?)");
                preparedStatement.setString(1,paramStr);
                preparedStatement.setInt(2,1);
                preparedStatement.executeUpdate();

            }
            else {
                int hot_time=result.getInt(2);
                preparedStatement=connection.prepareStatement("UPDATE hot_page SET time=? WHERE pname=?");
                preparedStatement.setInt(1, hot_time+1);
                preparedStatement.setString(2,paramStr);
                preparedStatement.executeUpdate();
            }
        }

    };
}
