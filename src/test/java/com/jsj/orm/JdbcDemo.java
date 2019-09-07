package com.jsj.orm;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 使用示例
 *
 * @author jiangshenjie
 */
public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("driverClassName", "com.mysql.cj.jdbc.Driver");
        properties.put("url", "jdbc:mysql://119.23.204.78:3306/sec_kill?useUnicode=true&characterEncoding=utf8&useSSL=false");
        properties.put("username", "root");
        properties.put("password", "123456");
        DataSource druidDataSource = DruidDataSourceFactory.createDataSource(properties);
        UserMapper userMapper = new UserMapper(druidDataSource, false);
        String userName = userMapper.selectName(1L);
        System.out.println(userName);
        userMapper.update("jsj", 1L);
        UserDO userDO = userMapper.selectOne(1L);
        if (userDO != null) {
            System.out.println(userDO);
        }
        userMapper.commit();
    }
}
