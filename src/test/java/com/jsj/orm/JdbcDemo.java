package com.jsj.orm;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jsj.orm.dao.UserMapper;
import com.jsj.orm.dao.UserMapperImpl;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * 使用示例
 *
 * @author jiangshenjie
 */
public class JdbcDemo {
    private DataSource getDataSource() throws Exception {
        Properties properties = new Properties();
        properties.put("driverClassName", "com.mysql.cj.jdbc.Driver");
        properties.put("url", "jdbc:mysql://119.23.204.78:3306/test2?useUnicode=true&characterEncoding=utf8&useSSL=false");
        properties.put("username", "root");
        properties.put("password", "123456");
        return DruidDataSourceFactory.createDataSource(properties);
    }

    @Test
    public void createTable() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        userMapper.createTableIfNotExists();
    }

    @Test
    public void selectName() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        String userName = userMapper.selectName(1L);
        System.out.println(userName);
    }

    @Test
    public void selectOne() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        UserDO userDO = userMapper.selectOne(1L);
        System.out.println(userDO);
    }

    @Test
    public void updateOne() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setUserName("jsj");
        userDO.setPhone("12312412");
        userDO.setBalance(new BigDecimal(666.6));
        userMapper.update(userDO);
        System.out.println("---------select-----------");
        selectOne();
    }

    @Test
    public void updateName() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        userMapper.updateName("tom_" + System.currentTimeMillis(), 1L);
        System.out.println("---------select-----------");
        selectOne();
    }
}
