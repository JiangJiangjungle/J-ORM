package com.jsj.orm;

import com.jsj.orm.config.Configuration;
import com.jsj.orm.exception.MapperClosedException;
import com.jsj.orm.executor.BaseExecutor;
import com.jsj.orm.executor.Executor;
import com.jsj.orm.map.ResultMapHandler;
import com.jsj.orm.transaction.DefaultTransactionFactory;
import com.jsj.orm.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 可以直接使用，也可以通过继承BaseMapper进行使用
 *
 * @author jiangshenjie
 */
public class BaseMapper implements Mapper {
    private DefaultTransactionFactory transactionFactory = new DefaultTransactionFactory();
    private DataSource dataSource;
    private Configuration configuration;
    private boolean autoCommit;
    private Executor executor = null;
    private boolean closed = false;

    public BaseMapper(DataSource dataSource, boolean autoCommit) {
        this(new Configuration(), dataSource, autoCommit);
    }

    public BaseMapper(Configuration configuration, DataSource dataSource, boolean autoCommit) {
        this.configuration = configuration;
        this.dataSource = dataSource;
        this.autoCommit = autoCommit;
    }

    @Override
    public <E> List<E> selectList(String sql, ResultMapHandler<E> resultMapHandler, Object... params) {
        checkExecutor();
        try {
            return executor.query(sql, resultMapHandler, params);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return new ArrayList<>(0);
    }

    @Override
    public <E> E selectOne(String sql, ResultMapHandler<E> resultMapHandler, Object... params) {
        List<E> result = selectList(sql, resultMapHandler, params);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public boolean update(String sql, Object... params) {
        checkExecutor();
        boolean updated = false;
        try {
            updated = executor.update(sql, params);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return updated;
    }

    @Override
    public void commit() {
        if (executor == null) {
            return;
        }
        try {
            executor.commit();
        } catch (SQLException s) {
        } finally {
            closed = true;
        }
    }

    @Override
    public void rollback() {
        if (executor == null) {
            return;
        }
        try {
            executor.rollback();
        } catch (SQLException s) {
        } finally {
            closed = true;
        }
    }

    private void checkExecutor() {
        if (closed) {
            throw new MapperClosedException("This Mapper has been closed!");
        }
        if (executor == null) {
            Transaction transaction = transactionFactory.newTransaction(dataSource, autoCommit);
            executor = new BaseExecutor(this.configuration, transaction);
        }
    }
}
