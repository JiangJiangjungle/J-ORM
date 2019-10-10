package com.jsj.orm.config;

import com.jsj.orm.binding.MethodInfo;
import com.jsj.orm.map.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 配置项
 *
 * @author jiangshenjie
 */
@NoArgsConstructor
public class Configuration {
    private Map<Method, MethodInfo> methodInfos = new ConcurrentHashMap<>(64);

    private Map<String, ResultMapHandler> resultMapHandlers = new ConcurrentHashMap<>(64);

    private Map<String, ParamMapHandler> paramMapHandlers = new ConcurrentHashMap<>(64);

    /**
     * 注册方法
     *
     * @param method
     * @param methodInfo
     */
    public void registerMethod(@NonNull Method method, @NonNull MethodInfo methodInfo) {
        methodInfos.put(method, methodInfo);
    }

    /**
     * 注册结果映射器
     *
     * @param multiResultMap
     */
    public void registerResultMapHandler(@NonNull MultiResultMap multiResultMap) {
        String id = multiResultMap.getId();
        try {
            Class<?> clazz = Class.forName(multiResultMap.getClassName());
            ResultMapHandler resultMapHandler = new DefaultResultMapHandler(clazz, multiResultMap.getResultMaps());
            resultMapHandlers.put(id, resultMapHandler);
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    /**
     * 注册参数映射器
     *
     * @param multiParamMap
     */
    public void registerParamMapHandler(@NonNull MultiParamMap multiParamMap) {
        String id = multiParamMap.getId();
        try {
            Class<?> clazz = Class.forName(multiParamMap.getClassName());
            ParamMapHandler paramMapHandler = (object) -> {
                List<String> fieldNames = multiParamMap.getFieldNames();
                if (fieldNames == null || fieldNames.size() == 0) return new Object[0];
                Object[] params = new Object[fieldNames.size()];
                for (int i = 0; i < fieldNames.size(); i++) {
                    String fieldName = fieldNames.get(i);
                    try {
                        //todo 可能存在反射的性能问题
                        Field field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        params[i] = field.get(object);
                    } catch (NoSuchFieldException | IllegalAccessException n) {
                        n.printStackTrace();
                    }
                }
                return params;
            };
            paramMapHandlers.put(id, paramMapHandler);
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    public ParamMapHandler getParamMapHandler(@NonNull String id) {
        return paramMapHandlers.get(id);
    }

    public ResultMapHandler getResultMapHandler(@NonNull String id) {
        return resultMapHandlers.get(id);
    }

    public MethodInfo getMethodInfo(@NonNull Method method) {
        return methodInfos.get(method);
    }
}
