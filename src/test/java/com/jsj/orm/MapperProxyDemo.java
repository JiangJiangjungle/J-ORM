package com.jsj.orm;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jsj.orm.binding.MapperProxyFactory;
import com.jsj.orm.binding.MethodDefinition;
import com.jsj.orm.config.Configuration;
import com.jsj.orm.dao.UserMapper;
import com.jsj.orm.map.MultiParamMap;
import com.jsj.orm.map.MultiResultMap;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * 基于动态代理方式获取mapper对象
 *
 * @author jiangshenjie
 */
public class MapperProxyDemo {
    private DataSource getDataSource() throws Exception {
        Properties properties = new Properties();
        properties.put("driverClassName", "com.mysql.cj.jdbc.Driver");
        properties.put("url", "jdbc:mysql://119.23.204.78:3306/test2?useUnicode=true&characterEncoding=utf8&useSSL=false");
        properties.put("username", "root");
        properties.put("password", "123456");
        return DruidDataSourceFactory.createDataSource(properties);
    }

    private Configuration newConfiguration() throws Exception {
        Configuration configuration = new Configuration();
        //注册结果映射器
        String id = "0";
        String className = "com.jsj.orm.UserDO";
        MultiResultMap multiResultMap = new MultiResultMap();
        multiResultMap.setId(id);
        multiResultMap.setClassName(className);
        multiResultMap.addResultMap("id", "id");
        multiResultMap.addResultMap("user_name", "userName");
        multiResultMap.addResultMap("phone", "phone");
        multiResultMap.addResultMap("balance", "balance");
        multiResultMap.addResultMap("create_time", "createTime");
        configuration.registerResultMapHandler(multiResultMap);

        //注册参数映射器
        MultiParamMap multiParamMap = new MultiParamMap();
        multiParamMap.setId(id);
        multiParamMap.setClassName(className);
        multiParamMap.addParam(0, "userName");
        multiParamMap.addParam(1, "phone");
        multiParamMap.addParam(2, "balance");
        multiParamMap.addParam(3, "id");
        configuration.registerParamMapHandler(multiParamMap);

        //注册selectOne方法
        MethodDefinition methodDefinition = new MethodDefinition();
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setExecuteType(ExecuteType.SELECT_ONE);
        methodDefinition.setHandlerId(id);
        methodDefinition.setSql("select * from `tb_user` where id =?");
        methodDefinition.setMethodName("selectOne");
        methodDefinition.setParameterTypes(new String[]{"java.lang.Long"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        //注册selectName方法
        methodDefinition = new MethodDefinition();
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setExecuteType(ExecuteType.SELECT_ONE);
        methodDefinition.setHandlerId("String");
        methodDefinition.setSql("select user_name from `tb_user` where id =?");
        methodDefinition.setMethodName("selectName");
        methodDefinition.setParameterTypes(new String[]{"java.lang.Long"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        //注册updateName方法
        methodDefinition = new MethodDefinition();
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setExecuteType(ExecuteType.UPDATE);
        methodDefinition.setSql("update `tb_user` set user_name=? where id =?");
        methodDefinition.setMethodName("updateName");
        methodDefinition.setParameterTypes(new String[]{"java.lang.String", "java.lang.Long"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        //注册update方法
        methodDefinition = new MethodDefinition();
        methodDefinition.setExecuteType(ExecuteType.UPDATE);
        methodDefinition.setHandlerId(id);
        methodDefinition.setSql("update `tb_user` set user_name=? , phone=? , balance=? where id =?");
        methodDefinition.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodDefinition.setMethodName("update");
        methodDefinition.setParameterTypes(new String[]{"com.jsj.orm.UserDO"});
        configuration.registerMethod(methodDefinition.getMethod(), methodDefinition);

        return configuration;
    }

    private MapperProxyFactory getMapperProxyFactory(Configuration configuration, DataSource dataSource) {
        return new MapperProxyFactory(configuration, dataSource);
    }

    private UserMapper getUserMapper() throws Exception {
        DataSource dataSource = getDataSource();
        Configuration configuration = newConfiguration();
        MapperProxyFactory mapperProxyFactory = getMapperProxyFactory(configuration, dataSource);
        return mapperProxyFactory.getMapper(UserMapper.class);
    }

    @Test
    public void update() throws Exception {
        UserMapper userMapper = getUserMapper();
        UserDO userDO = userMapper.selectOne(1L);
        userDO.setBalance(new BigDecimal(66778899.22));
        userMapper.update(userDO);
        userDO = userMapper.selectOne(1L);
        System.out.println(userDO);
    }

    @Test
    public void selectName() throws Exception {
        UserMapper userMapper = getUserMapper();
        String userName = userMapper.selectName(1L);
        System.out.println(userName);
    }

    @Test
    public void selectOne() throws Exception {
        UserMapper userMapper = getUserMapper();
        UserDO userDO = userMapper.selectOne(1L);
        System.out.println(userDO);
    }

    @Test
    public void updateOne() throws Exception {
        UserMapper userMapper = getUserMapper();
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
        UserMapper userMapper = getUserMapper();
        userMapper.updateName("tom_" + System.currentTimeMillis(), 1L);
        System.out.println("---------select-----------");
        selectOne();
    }
}
