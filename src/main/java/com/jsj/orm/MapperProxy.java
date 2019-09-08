package com.jsj.orm;

import com.jsj.orm.config.MapperInfo;
import com.jsj.orm.config.SqlInfo;
import com.jsj.orm.executor.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy<T> implements InvocationHandler {
    /**
     * 调用者定义的dao接口
     */
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;
    private Executor executor;

    public MapperProxy(Executor executor, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.executor = executor;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                throw throwable;
            }
        } else {
            MapperMethod mapperMethod = this.cachedMapperMethod(method);
            return mapperMethod.execute(this.executor, args);
        }
    }

    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = this.methodCache.get(method);
        if (mapperMethod == null) {
            MapperInfo mapperInfo = executor.getConfiguration().getMapperInfo(mapperInterface);
            SqlInfo sqlInfo = mapperInfo.getSqlInfo(method);
            mapperMethod = new MapperMethod(sqlInfo);
            this.methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
}
