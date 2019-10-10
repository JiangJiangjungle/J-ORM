package com.jsj.orm;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jsj.orm.binding.MapperProxyFactory;
import com.jsj.orm.binding.MethodInfo;
import com.jsj.orm.config.Configuration;
import com.jsj.orm.dao.UserMapper;
import com.jsj.orm.map.MultiParamMap;
import com.jsj.orm.map.MultiResultMap;
import org.junit.Test;

import javax.sql.DataSource;
import java.lang.reflect.Method;
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
        //注册结果映射器0
        String id = "0";
        String className = "com.jsj.orm.UserDO";
        MultiResultMap multiResultMap = new MultiResultMap();
        multiResultMap.setId(id);
        multiResultMap.setClassName(className);
        multiResultMap.addResultMap("id", "id");
        multiResultMap.addResultMap("user_name", "userName");
        multiResultMap.addResultMap("phone", "phone");
        multiResultMap.addResultMap("phone", "phone");
        multiResultMap.addResultMap("balance", "balance");
        multiResultMap.addResultMap("create_time", "createTime");
        configuration.registerResultMapHandler(multiResultMap);

        //注册参数映射器1
        MultiParamMap multiParamMap = new MultiParamMap();
        multiParamMap.setId(id);
        multiParamMap.setClassName(className);
        multiParamMap.addParam(0, "userName");
        multiParamMap.addParam(1, "phone");
        multiParamMap.addParam(2, "balance");
        multiParamMap.addParam(3, "id");
        configuration.registerParamMapHandler(multiParamMap);


        //注册selectOne方法
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setExecuteType(ExecuteType.SELECT_ONE);
        methodInfo.setHandlerId(id);
        methodInfo.setSql("select * from `tb_user` where id =?");
        methodInfo.setMethodName("selectOne");
        String[] paramTypeNames = new String[]{"java.lang.Long"};
        methodInfo.setParameterTypes(paramTypeNames);
        Class[] paramTypes = new Class[paramTypeNames.length];
        for (int i = 0; i < paramTypeNames.length; i++) {
            paramTypes[i] = Class.forName(paramTypeNames[i]);
        }
        Method method = UserMapper.class.getDeclaredMethod(methodInfo.getMethodName(), paramTypes);
        configuration.registerMethod(method, methodInfo);

        //注册update方法
        methodInfo = new MethodInfo();
        methodInfo.setExecuteType(ExecuteType.UPDATE);
        methodInfo.setHandlerId(id);
        methodInfo.setSql("update `tb_user` set user_name=? , phone=? , balance=? where id =?");
        methodInfo.setInterfaceName("com.jsj.orm.dao.UserMapper");
        methodInfo.setMethodName("update");
        paramTypeNames = new String[]{"com.jsj.orm.UserDO"};
        methodInfo.setParameterTypes(paramTypeNames);
        Class<?> mapperClass = Class.forName(methodInfo.getInterfaceName());
        paramTypes = new Class[paramTypeNames.length];
        for (int i = 0; i < paramTypeNames.length; i++) {
            paramTypes[i] = Class.forName(paramTypeNames[i]);
        }
        method = mapperClass.getDeclaredMethod(methodInfo.getMethodName(), paramTypes);
        configuration.registerMethod(method, methodInfo);

        return configuration;
    }

    private MapperProxyFactory getMapperProxyFactory(Configuration configuration, DataSource dataSource) {
        return new MapperProxyFactory(configuration, dataSource);
    }

    @Test
    public void testUpdate() throws Exception {
        DataSource dataSource = getDataSource();
        Configuration configuration = newConfiguration();
        MapperProxyFactory mapperProxyFactory = getMapperProxyFactory(configuration, dataSource);
        UserMapper userMapper = mapperProxyFactory.getMapper(UserMapper.class);
        UserDO userDO = userMapper.selectOne(1L);
        userDO.setBalance(new BigDecimal(6667788.22));
        userMapper.update(userDO);
        userDO = userMapper.selectOne(1L);
        System.out.println(userDO);
    }
}
