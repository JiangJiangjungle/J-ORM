package com.jsj.orm.binding;

import com.jsj.orm.Mapper;
import com.jsj.orm.config.Configuration;
import org.apache.ibatis.reflection.ExceptionUtil;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * 根据接口和配置，生成Mapper的代理对象
 *
 * @param <T>
 * @author jiangshenjie
 */
public class MapperProxy<T> implements InvocationHandler {
    private Mapper mapper;
    private Class<T> mapperInterface;
    private Configuration configuration;
    private Map<Method, MapperMethod> methodCache;


    public MapperProxy(Mapper mapper, Class<T> mapperInterface, Configuration configuration, Map<Method, MapperMethod> methodCache) {
        this.mapper = mapper;
        this.mapperInterface = mapperInterface;
        this.configuration = configuration;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            //Object方法不作任何处理
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
            //执行接口默认方法
            else if (isDefaultMethod(method)) {
                return invokeDefaultMethod(proxy, method, args);
            }
        } catch (Throwable t) {
            throw ExceptionUtil.unwrapThrowable(t);
        }
        //对接口的方法进行代理
        final MapperMethod mapperMethod = cachedMapperMethod(method);
        return mapperMethod.execute(mapper, args);
    }

    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            mapperMethod = new MapperMethod(configuration, method);
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }

    /**
     * 执行接口的默认方法
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args)
            throws Throwable {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        return constructor
                .newInstance(declaringClass,
                        MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
                                | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC)
                .unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
    }

    /**
     * 判断方法是否接口的默认方法
     *
     * @param method
     * @return
     */
    private boolean isDefaultMethod(Method method) {
        return (method.getModifiers()
                & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC
                && method.getDeclaringClass().isInterface();
    }
}
