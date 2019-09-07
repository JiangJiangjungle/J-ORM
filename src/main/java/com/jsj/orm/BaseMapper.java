package com.jsj.orm;

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
 * @author jiangshenjie
 */
public abstract class BaseMapper implements Mapper {
    private DefaultTransactionFactory transactionFactory = new DefaultTransactionFactory();
    private DataSource dataSource;
    private boolean autoCommit;
    private Executor executor = null;
    private boolean closed = false;

    public BaseMapper(DataSource dataSource, boolean autoCommit) {
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
        if (executor == null) return;
        try {
            executor.commit();
        } catch (SQLException s) {
        } finally {
            closed = true;
        }
    }

    @Override
    public void rollback() {
        if (executor == null) return;
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
            executor = new BaseExecutor(transaction);
        }
    }
}
