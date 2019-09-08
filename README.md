# JORM
简洁版ORM框架：提供自定义映射：）

### 主要接口
    public interface Mapper {
        <E> E selectOne(String sql, ResultMapHandler<E> resultMapHandler, Object... params);
    
        <E> List<E> selectList(String sql, ResultMapHandler<E> resultMapHandler, Object... params);
    
        boolean update(String sql, Object... params);
    
        void commit();
    
        void rollback();
    }
    
    public interface ResultMapHandler<E> {
        E mapper(Map<String, Object> results);
    }

### 使用方法

* 继承BaseMapper（参考示例中的UserMapper）
* 直接调用BaseMapper方法

###示例

    @Test
    public void selectName() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        String userName = userMapper.selectName(1L);
        System.out.println(userName);
    }

    @Test
    public void selectOne() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        UserDO userDO = userMapper.selectOne(1L);
        System.out.println(userDO);
    }

    @Test
    public void update() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        userMapper.update("tom_" + System.currentTimeMillis(), 1L);
        System.out.println("---------select-----------");
        selectOne();
    }



