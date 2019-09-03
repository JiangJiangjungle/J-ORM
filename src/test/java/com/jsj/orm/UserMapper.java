package com.jsj.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserMapper extends BaseMapper<UserDO> {
    private final String createTable = "CREATE TABLE `tb_user` (\n" +
            "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',\n" +
            "  `user_name` varchar(50) NOT NULL COMMENT '用户名',\n" +
            "  `phone` varchar(20) NOT NULL COMMENT '手机号码',\n" +
            "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;";

    private final String selectAll = "select * from " + tableName + " where id =?";

    public UserMapper(String tableName) {
        super(tableName);
    }

    public List<UserDO> selectAll(Connection connection, Long id) {
        try {
            return selectList(connection, selectAll, id);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return null;
    }


    @Override
    public void createTableIfNotExists(Connection connection) throws SQLException {
        execute(connection, createTable);
    }

    @Override
    protected UserDO mapper(Map<String, Object> values) {
        UserDO userDO = new UserDO();
        Object value = values.get("id");
        if (value != null) {
            userDO.setId((Long) value);
        }
        value = values.get("user_name");
        if (value != null) {
            userDO.setUserName((String) value);
        }
        value = values.get("phone");
        if (value != null) {
            userDO.setPhone((String) value);
        }
        value = values.get("create_time");
        if (value != null) {
            userDO.setCreateTime((Date) value);
        }
        return userDO;
    }
}
