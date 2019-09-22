# J-ORM
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

### 示例

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
    public void updateOne() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setUserName("jsj");
        userDO.setPhone("12312412");
        userDO.setBalance(new BigDecimal(666.6));
        userMapper.update(userDO);
        System.out.println("---------select-----------");
        selectOne();
    }

    @Test
    public void updateName() throws Exception {
        DataSource dataSource = getDataSource();
        UserMapper userMapper = new UserMapperImpl(dataSource, true);
        userMapper.updateName("tom_" + System.currentTimeMillis(), 1L);
        System.out.println("---------select-----------");
        selectOne();
    }



