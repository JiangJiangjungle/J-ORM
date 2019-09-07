package com.jsj.orm;

import com.jsj.orm.mapper.BaseMapper;

import javax.sql.DataSource;
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

    private final String selectAll = "select * from `tb_user` where id =?";

    private final String updateUserDO = "update `tb_user` set user_name=? where id =?";

    public UserMapper(DataSource dataSource) {
        this(dataSource, true);
    }

    public UserMapper(DataSource dataSource, boolean autoCommit) {
        super(dataSource, autoCommit);
    }

    @Override
    public UserDO mapper(Map<String, Object> results) {
        UserDO userDO = new UserDO();
        Object value = results.get("id");
        if (value != null) {
            userDO.setId((Long) value);
        }
        value = results.get("user_name");
        if (value != null) {
            userDO.setUserName((String) value);
        }
        value = results.get("phone");
        if (value != null) {
            userDO.setPhone((String) value);
        }
        value = results.get("create_time");
        if (value != null) {
            userDO.setCreateTime((Date) value);
        }
        return userDO;
    }

    @Override
    public void createTableIfNotExists() {
        update(createTable);
    }

    public List<UserDO> selectAll(Long id) {
        return selectList(selectAll, id);
    }

    public boolean update(String userName, Long id) {
        return update(updateUserDO, userName, id);
    }
}
