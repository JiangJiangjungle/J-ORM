package com.jsj.orm.binding;

import com.jsj.orm.ExecuteType;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author jiangshenjie
 */
@Data
public class MethodDefinition {
    /**
     * sql操作类型
     */
    private ExecuteType executeType;
    /**
     * sql语句
     */
    private String sql;
    /**
     * 映射器id,UPDATE操作对应参数映射器，SELECT操作对应结果映射器
     */
    private String handlerId;
    /**
     * 实现接口名称
     */
    private String interfaceName;
    /**
     * 实现接口的具体方法
     */
    private String methodName;
    /**
     * 参数类型列表
     */
    private String[] parameterTypes = null;

    public Method getMethod() throws ClassNotFoundException, NoSuchMethodException {
        Class interfaceClass = Class.forName(interfaceName);
        Class[] paramTypes = new Class[parameterTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            paramTypes[i] = Class.forName(parameterTypes[i]);
        }
        return interfaceClass.getDeclaredMethod(getMethodName(), paramTypes);
    }
}
