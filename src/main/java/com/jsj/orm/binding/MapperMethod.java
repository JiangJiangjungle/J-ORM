package com.jsj.orm.binding;

import com.jsj.orm.ExecuteType;
import com.jsj.orm.Mapper;
import com.jsj.orm.config.Configuration;
import com.jsj.orm.exception.MethodInfoNotFoundException;
import com.jsj.orm.map.ParamMapHandler;
import com.jsj.orm.map.ResultMapHandler;

import java.lang.reflect.Method;

/**
 * 代理对象根据注册的MethodInfo执行实际的方法调用
 *
 * @author jiangshenjie
 */
public class MapperMethod {
    private MethodInfo methodInfo;
    private Method method;
    private Configuration configuration;

    public MapperMethod(Configuration configuration, Method method) {
        this.configuration = configuration;
        this.method = method;
        this.methodInfo = configuration.getMethodInfo(method);
    }

    /**
     * @param mapper
     * @param args
     * @return
     */
    public Object execute(Mapper mapper, Object[] args) {
        if (methodInfo == null) {
            throw new MethodInfoNotFoundException("MethodInfo: " + method.getName() + " not found!");
        }
        ExecuteType executeType = methodInfo.getExecuteType();
        String sql = methodInfo.getSql();
        String handlerId = methodInfo.getHandlerId();
        Object result = null;
        if (ExecuteType.SELECT_ONE == executeType || ExecuteType.SELECT_LIST == executeType) {
            ResultMapHandler resultMapHandler = handlerId == null ? null : configuration.getResultMapHandler(handlerId);
            if (ExecuteType.SELECT_ONE == executeType) {
                result = mapper.selectOne(sql, resultMapHandler, args);
            } else {
                result = mapper.selectList(sql, resultMapHandler, args);
            }
        } else if (ExecuteType.UPDATE == executeType) {
            ParamMapHandler paramMapHandler = handlerId == null ? null : configuration.getParamMapHandler(handlerId);
            if (paramMapHandler != null) {
                result = mapper.update(sql, paramMapHandler.mapper(args[0]));
            } else {
                result = mapper.update(sql, args);
            }
        }
        return result;
    }
}
