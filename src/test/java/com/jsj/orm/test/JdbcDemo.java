package com.jsj.orm.test;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jsj.orm.MapperRegistry;
import com.jsj.orm.UserDO;
import com.jsj.orm.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * JDBC的DEMO
 *
 * @author jsj
 * @date 2019-01-22
 */
public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("driverClassName", "com.mysql.cj.jdbc.Driver");
        properties.put("url", "jdbc:mysql://119.23.204.78:3306/sec_kill?useSSL=false");
        properties.put("username", "root");
        properties.put("password", "123456");
        DataSource druidDataSource = DruidDataSourceFactory.createDataSource(properties);
        MapperRegistry.init();
        MapperRegistry.getInstance().register(new UserMapper("tb_user"));
        UserMapper userDAO = MapperRegistry.getInstance().getDAO(UserMapper.class);
        try (Connection connection = druidDataSource.getConnection()) {
            List<UserDO> userDOS = userDAO.selectAll(connection, 1L);
            for (UserDO userDO : userDOS) {
                System.out.println(userDO);
            }
        }
    }
}
