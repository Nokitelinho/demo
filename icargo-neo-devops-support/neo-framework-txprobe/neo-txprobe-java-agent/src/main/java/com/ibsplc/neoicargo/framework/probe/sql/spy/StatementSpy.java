/*
 * StatementSpy.java Created on 20-Sep-2013
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.sql.spy;

import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SpyAgentFactory;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SpyContext;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Wraps a Statement and reports method calls, returns and exceptions.
 * <p>
 * jdbc 4 version
 */
public class StatementSpy implements Statement, Spy {

    protected final SqlPrincipalAgent log;

    /**
     * The Connection that created this Statement.
     */
    protected ConnectionSpy connectionSpy;

    /**
     * The real statement that this StatementSpy wraps.
     */
    protected Statement realStatement;

    /**
     * Get the real Statement that this StatementSpy wraps.
     *
     * @return the real Statement that this StatementSpy wraps.
     */
    public Statement getRealStatement() {
        return realStatement;
    }

    /**
     * Create a StatementSpy that wraps another Statement for the purpose of
     * logging all method calls, sql, exceptions and return values.
     *
     * @param connectionSpy Connection that created this Statement.
     * @param realStatement real underlying Statement that this StatementSpy wraps.
     */
    public StatementSpy(ConnectionSpy connectionSpy, SqlPrincipalAgent agent, Statement realStatement) {
        if (realStatement == null) {
            throw new IllegalArgumentException("Must pass in a non null real Statement");
        }
        if (connectionSpy == null) {
            throw new IllegalArgumentException("Must pass in a non null ConnectionSpy");
        }
        this.realStatement = realStatement;
        this.connectionSpy = connectionSpy;
        this.log = agent;
    }

    public String getClassType() {
        return "Statement";
    }

    public Integer getConnectionNumber() {
        return connectionSpy.getConnectionNumber();
    }


    // implementation of interface methods
    public SQLWarning getWarnings() throws SQLException {
        return realStatement.getWarnings();
    }

    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        StringBuilder sbul = new StringBuilder(sql);
        sbul.append("\n columnNames : \n").append(Arrays.toString(columnNames));
        SpyContext context = log.onSqlExecutionStart(this, "executeUpdate", sbul.toString());
        int rowCount = 0;
        SQLException err = null;
        try {
            rowCount = realStatement.executeUpdate(sql, columnNames);
            return rowCount;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.ROW_COUNT, rowCount);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public boolean execute(String sql, String[] columnNames) throws SQLException {
        StringBuilder sbul = new StringBuilder(sql);
        sbul.append("\n columnNames : \n").append(Arrays.toString(columnNames));
        SpyContext context = log.onSqlExecutionStart(this, "execute", sbul.toString());
        boolean answer = false;
        SQLException err = null;
        try {
            answer = realStatement.execute(sql, columnNames);
            return answer;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.EXECUTE_RESPONSE, answer);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public void setMaxRows(int max) throws SQLException {
        realStatement.setMaxRows(max);
    }

    public boolean getMoreResults() throws SQLException {
        return realStatement.getMoreResults();
    }

    public void clearWarnings() throws SQLException {
        realStatement.clearWarnings();
    }

    /**
     * Tracking of current batch (see addBatch, clearBatch and executeBatch)
     */
    protected List<String> currentBatch = new ArrayList<String>(5);

    public void addBatch(String sql) throws SQLException {
        currentBatch.add(sql);
        realStatement.addBatch(sql);
    }

    public int getResultSetType() throws SQLException {
        return realStatement.getResultSetType();
    }

    public void clearBatch() throws SQLException {
        realStatement.clearBatch();
        currentBatch.clear();
    }

    public void setFetchDirection(int direction) throws SQLException {
        realStatement.setFetchDirection(direction);
    }

    public int[] executeBatch() throws SQLException {
        StringBuilder batchReport = new StringBuilder(250);
        for (int i = 0; i < currentBatch.size(); i++) {
            if (batchReport.length() > 0)
                batchReport.append('\n');
            batchReport.append(i).append(" :  ").append(currentBatch.get(i));
        }
        SpyContext context = log.onSqlExecutionStart(this, "executeBatch", batchReport.toString());
        int rowCount = 0;
        int[] rowsCount = {};
        SQLException err = null;
        try {
            rowsCount = realStatement.executeBatch();
            return rowsCount;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else {
                    for (int x = 0; x < rowsCount.length; x++)
                        rowCount += rowsCount[x];
                    context.set(SpyContext.ROW_COUNT, rowCount);
                }
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public void setFetchSize(int rows) throws SQLException {
        realStatement.setFetchSize(rows);
    }

    public int getQueryTimeout() throws SQLException {
        return realStatement.getQueryTimeout();
    }

    public Connection getConnection() throws SQLException {
        return connectionSpy;
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return realStatement.getGeneratedKeys();
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        realStatement.setEscapeProcessing(enable);
    }

    public int getFetchDirection() throws SQLException {
        return realStatement.getFetchDirection();
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        realStatement.setQueryTimeout(seconds);
    }

    public boolean getMoreResults(int current) throws SQLException {
        return realStatement.getMoreResults(current);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        SpyContext context = log.onSqlExecutionStart(this, "executeQuery", sql);
        SQLException err = null;
        boolean failed = true;
        try {
            ResultSet rs = realStatement.executeQuery(sql);
            failed = false;
            if (context != null) {
                ResultSetSpy spy = new ResultSetSpy(rs);
                spy.invokeEndOnClose(this.log, context);
                return spy;
            }
            return rs;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (failed && context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                this.log.onSqlExecutionEnd(context);
            }
        }
    }

    public int getMaxFieldSize() throws SQLException {
        return realStatement.getMaxFieldSize();
    }

    public int executeUpdate(String sql) throws SQLException {
        SpyContext context = log.onSqlExecutionStart(this, "executeUpdate", sql);
        int rowCount = 0;
        SQLException err = null;
        try {
            rowCount = realStatement.executeUpdate(sql);
            return rowCount;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.ROW_COUNT, rowCount);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public void cancel() throws SQLException {
        realStatement.cancel();
    }

    public void setCursorName(String name) throws SQLException {
        realStatement.setCursorName(name);
    }

    public int getFetchSize() throws SQLException {
        return realStatement.getFetchSize();
    }

    public int getResultSetConcurrency() throws SQLException {
        return realStatement.getResultSetConcurrency();
    }

    public int getResultSetHoldability() throws SQLException {
        return realStatement.getResultSetHoldability();
    }

    public boolean isClosed() throws SQLException {
        return realStatement.isClosed();
    }

    public void setPoolable(boolean poolable) throws SQLException {
        realStatement.setPoolable(poolable);
    }

    public boolean isPoolable() throws SQLException {
        return realStatement.isPoolable();
    }

    public void setMaxFieldSize(int max) throws SQLException {
        realStatement.setMaxFieldSize(max);
    }

    public boolean execute(String sql) throws SQLException {
        SpyContext context = log.onSqlExecutionStart(this, "execute", sql);
        boolean answer = false;
        SQLException err = null;
        try {
            answer = realStatement.execute(sql);
            return answer;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.EXECUTE_RESPONSE, answer);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        SpyContext context = log.onSqlExecutionStart(this, "executeUpdate", sql);
        int rowCount = 0;
        SQLException err = null;
        try {
            rowCount = realStatement.executeUpdate(sql, autoGeneratedKeys);
            return rowCount;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.ROW_COUNT, rowCount);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        SpyContext context = log.onSqlExecutionStart(this, "execute", sql);
        boolean answer = false;
        SQLException err = null;
        try {
            answer = realStatement.execute(sql, autoGeneratedKeys);
            return answer;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.EXECUTE_RESPONSE, answer);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        StringBuilder sbul = new StringBuilder(sql);
        sbul.append("\n columnIndexes : \n").append(Arrays.toString(columnIndexes));
        SpyContext context = log.onSqlExecutionStart(this, "executeUpdate", sbul.toString());
        int rowCount = 0;
        SQLException err = null;
        try {
            rowCount = realStatement.executeUpdate(sql, columnIndexes);
            return rowCount;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.ROW_COUNT, rowCount);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        StringBuilder sbul = new StringBuilder(sql);
        sbul.append("\n columnIndexes : \n").append(Arrays.toString(columnIndexes));
        SpyContext context = log.onSqlExecutionStart(this, "execute", sbul.toString());
        boolean answer = false;
        SQLException err = null;
        try {
            answer = realStatement.execute(sql, columnIndexes);
            return answer;
        } catch (SQLException e) {
            err = e;
            throw e;
        } finally {
            if (context != null) {
                if (err != null)
                    context.set(SpyContext.ERROR, err);
                else
                    context.set(SpyContext.EXECUTE_RESPONSE, answer);
                log.onSqlExecutionEnd(context);
            }
        }
    }

    public ResultSet getResultSet() throws SQLException {
        return realStatement.getResultSet();
    }

    public int getMaxRows() throws SQLException {
        return realStatement.getMaxRows();
    }

    public void close() throws SQLException {
        realStatement.close();
    }

    public int getUpdateCount() throws SQLException {
        return realStatement.getUpdateCount();
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return (iface != null && (iface == Connection.class || iface == Spy.class)) ? (T) this : realStatement.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface != null && (iface == Statement.class || iface == Spy.class)) || realStatement.isWrapperFor(iface);
    }

    public void closeOnCompletion() throws SQLException {
        realStatement.closeOnCompletion();
    }

    public boolean isCloseOnCompletion() throws SQLException {
        return realStatement.isCloseOnCompletion();
    }

}
