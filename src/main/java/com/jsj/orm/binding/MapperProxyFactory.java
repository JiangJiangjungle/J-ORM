package com.jsj.orm.binding;

import com.jsj.orm.BaseMapper;
import com.jsj.orm.Mapper;
import com.jsj.orm.config.Configuration;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理对象工厂
 * @author jiangshenjie
 */
public class MapperProxyFactory {
    private Configuration configuration;
    private DataSource dataSource;
    private Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<>();

    public MapperProxyFactory(Configuration configuration, DataSource dataSource) {
        this.configuration = configuration;
        this.dataSource = dataSource;
    }

    public <T> T getMapper(Class<T> mapperInterface) {
        Mapper mapper = new BaseMapper(dataSource, true);
        MapperProxy<T> mapperProxy = new MapperProxy<>(mapper, mapperInterface, configuration, methodCache);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
