package com.jsj.orm;

import com.jsj.orm.config.SqlInfo;
import com.jsj.orm.constant.SqlCommandType;
import com.jsj.orm.executor.Executor;
import com.jsj.orm.map.BasicResultMapHandler;
import com.jsj.orm.map.DefaultResultMapHandler;
import com.jsj.orm.map.ResultMap;
import com.jsj.orm.map.ResultMapHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * todo
 *
 * @author jiangshenjie
 */
public class MapperMethod<T> {
    private SqlInfo sqlInfo;
    private ResultMapHandler<T> resultMapHandler;

    public MapperMethod(SqlInfo sqlInfo) {
        this.sqlInfo = sqlInfo;
    }

    public Object execute(Executor executor, Object[] args) {
        Object result = null;
        SqlCommandType commandType = sqlInfo.getCommandType();
        checkResultMapHandler();
        String sql = sqlInfo.getSql();
        boolean returnsSingle = sqlInfo.returnsSingle();
        try {
            if (SqlCommandType.UPDATE == commandType) {
                result = executor.update(sql, args);
            } else if (SqlCommandType.SELECT == commandType) {
                List<T> list = executor.query(sql, resultMapHandler, args);
                if (returnsSingle) {
                    result = list == null || list.size() == 0 ? null : list.get(0);
                } else {
                    result = list;
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return result;
    }

    private void checkResultMapHandler() {
        if (resultMapHandler == null) {
            try {
                Class<T> clazz = (Class<T>) Class.forName(sqlInfo.getResultType());
                List<ResultMap> resultMaps = sqlInfo.getResultMaps();
                resultMapHandler = resultMaps == null ? new BasicResultMapHandler<>(clazz) :
                        new DefaultResultMapHandler<>(clazz, resultMaps);
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
            }
        }
    }
}
