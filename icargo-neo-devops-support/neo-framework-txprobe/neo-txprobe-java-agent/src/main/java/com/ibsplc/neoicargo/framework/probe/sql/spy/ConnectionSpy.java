/*
 * ConnectionSpy.java Created on 12-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql.spy;

import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SpyAgentFactory;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Wraps a JDBC Connection and reports method calls, returns and exceptions.
 */
/*
 *
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			12-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class ConnectionSpy implements Connection, Spy {

    protected final Connection realConnection;

    // Oracle specific methods
    private static Method pingDBNoArg;
    private static Method pingDBOneArg;

    /**
     * Get the real underlying Connection that this ConnectionSpy wraps.
     *
     * @return the real underlying Connection.
     */
    public Connection getRealConnection() {
        return realConnection;
    }

    private SqlPrincipalAgent log;

    private final int connectionNumber;
    private static AtomicInteger lastConnectionNumber = new AtomicInteger();

    /**
     * Create a new ConnectionSpy that wraps a given Connection.
     *
     * @param realConnection
     *            &quot;real&quot; Connection that this ConnectionSpy wraps.
     */
    public ConnectionSpy(Connection realConnection, SqlPrincipalAgent agent) {
        this(realConnection, RdbmsSpecifics.DEFAULT, agent);
    }

    /**
     * Create a new ConnectionSpy that wraps a given Connection.
     *
     * @param realConnection
     *            &quot;real&quot; Connection that this ConnectionSpy wraps.
     * @param rdbmsSpecifics
     *            the RdbmsSpecifics object for formatting logging appropriate
     *            for the Rdbms used.
     */
    public ConnectionSpy(Connection realConnection, RdbmsSpecifics rdbmsSpecifics, SqlPrincipalAgent agent) {
        if (rdbmsSpecifics == null) {
            rdbmsSpecifics = RdbmsSpecifics.DEFAULT;
        }
        setRdbmsSpecifics(rdbmsSpecifics);
        if (realConnection == null) {
            throw new IllegalArgumentException("Must pass in a non null real Connection");
        }
        this.realConnection = realConnection;
        if(agent == null)
            this.log = SpyAgentFactory._getSpyAgent();
        else
            this.log = agent;
        connectionNumber = lastConnectionNumber.incrementAndGet();
        log.connectionOpened(this);
        reportReturn("new Connection");
    }

    private RdbmsSpecifics rdbmsSpecifics;

    /**
     * Set the RdbmsSpecifics object for formatting logging appropriate for the
     * Rdbms used on this connection.
     *
     * @param rdbmsSpecifics
     *            the RdbmsSpecifics object for formatting logging appropriate
     *            for the Rdbms used.
     */
    void setRdbmsSpecifics(RdbmsSpecifics rdbmsSpecifics) {
        this.rdbmsSpecifics = rdbmsSpecifics;
    }

    /**
     * Get the RdbmsSpecifics object for formatting logging appropriate for the
     * Rdbms used on this connection.
     *
     * @return the RdbmsSpecifics object for formatting logging appropriate for
     *         the Rdbms used.
     */
    RdbmsSpecifics getRdbmsSpecifics() {
        return rdbmsSpecifics;
    }

    public Integer getConnectionNumber() {
        return connectionNumber;
    }

    public String getClassType() {
        return "Connection";
    }

    protected void reportException(String methodCall, SQLException exception, String sql) {
        log.exceptionOccured(this, methodCall, exception, sql, -1L);
    }

    protected void reportException(String methodCall, SQLException exception) {
        log.exceptionOccured(this, methodCall, exception, null, -1L);
    }

    protected void reportAllReturns(String methodCall, String returnValue) {
        log.methodReturned(this, methodCall, returnValue);
    }

    private void reportReturn(String methodCall) {
        reportAllReturns(methodCall, "");
    }

    // forwarding methods

    public boolean isClosed() throws SQLException {
        return realConnection.isClosed();
    }

    public SQLWarning getWarnings() throws SQLException {
        return realConnection.getWarnings();
    }

    public Savepoint setSavepoint() throws SQLException {
        return realConnection.setSavepoint();
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        realConnection.releaseSavepoint(savepoint);
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        String methodCall = "rollback(" + savepoint + ")";
        try {
            realConnection.rollback(savepoint);
        } catch (SQLException s) {
            reportException(methodCall, s);
            throw s;
        }
        reportReturn(methodCall);
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return realConnection.getMetaData();
    }

    public void clearWarnings() throws SQLException {
        realConnection.clearWarnings();
    }

    public Statement createStatement() throws SQLException {
        Statement statement = realConnection.createStatement();
        return new StatementSpy(this, this.log, statement);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        Statement statement = realConnection.createStatement(resultSetType, resultSetConcurrency);
        return new StatementSpy(this, this.log, statement);
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        Statement statement = realConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        return new StatementSpy(this, this.log, statement);
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        realConnection.setReadOnly(readOnly);
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement statement = realConnection.prepareStatement(sql);
        return new PreparedStatementSpy(sql, this, this.log, statement);
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        PreparedStatement statement = realConnection.prepareStatement(sql, autoGeneratedKeys);
        return new PreparedStatementSpy(sql, this, this.log, statement);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        PreparedStatement statement = realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        return new PreparedStatementSpy(sql, this, this.log, statement);
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        PreparedStatement statement = realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return new PreparedStatementSpy(sql, this, this.log, statement);
    }

    public PreparedStatement prepareStatement(String sql, int columnIndexes[]) throws SQLException {
        PreparedStatement statement = realConnection.prepareStatement(sql, columnIndexes);
        return new PreparedStatementSpy(sql, this, this.log, statement);
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        return realConnection.setSavepoint(name);
    }

    public PreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException {
        PreparedStatement statement = realConnection.prepareStatement(sql, columnNames);
        return new PreparedStatementSpy(sql, this, this.log, statement);
    }

    public Clob createClob() throws SQLException {
        return realConnection.createClob();
    }

    public Blob createBlob() throws SQLException {
        return realConnection.createBlob();
    }

    public NClob createNClob() throws SQLException {
        return realConnection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return realConnection.createSQLXML();
    }

    public boolean isValid(int timeout) throws SQLException {
        return realConnection.isValid(timeout);
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        realConnection.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        realConnection.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        return realConnection.getClientInfo(name);
    }

    public Properties getClientInfo() throws SQLException {
        return realConnection.getClientInfo();
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return realConnection.createArrayOf(typeName, elements);
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return realConnection.createStruct(typeName, attributes);
    }

    public boolean isReadOnly() throws SQLException {
        return realConnection.isReadOnly();
    }

    public void setHoldability(int holdability) throws SQLException {
        realConnection.setHoldability(holdability);
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        CallableStatement statement = realConnection.prepareCall(sql);
        return new CallableStatementSpy(sql, this, this.log,  statement);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        CallableStatement statement = realConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
        return new CallableStatementSpy(sql, this, this.log, statement);
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        CallableStatement statement = realConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return new CallableStatementSpy(sql, this, this.log, statement);
    }

    public void setCatalog(String catalog) throws SQLException {
        realConnection.setCatalog(catalog);
    }

    public String nativeSQL(String sql) throws SQLException {
        return realConnection.nativeSQL(sql);
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return realConnection.getTypeMap();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        realConnection.setAutoCommit(autoCommit);
    }

    public String getCatalog() throws SQLException {
        return realConnection.getCatalog();
    }

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        realConnection.setTypeMap(map);
    }

    public void setTransactionIsolation(int level) throws SQLException {
        realConnection.setTransactionIsolation(level);
    }

    public boolean getAutoCommit() throws SQLException {
        return realConnection.getAutoCommit();
    }

    public int getHoldability() throws SQLException {
        return realConnection.getHoldability();
    }

    public int getTransactionIsolation() throws SQLException {
        return realConnection.getTransactionIsolation();
    }

    public void commit() throws SQLException {
        String methodCall = "commit()";
        try {
            realConnection.commit();
        } catch (SQLException s) {
            reportException(methodCall, s);
            throw s;
        }
        reportReturn(methodCall);
    }

    public void rollback() throws SQLException {
        String methodCall = "rollback()";
        try {
            realConnection.rollback();
        } catch (SQLException s) {
            reportException(methodCall, s);
            throw s;
        }
        reportReturn(methodCall);
    }

    public void close() throws SQLException {
        String methodCall = "close()";
        try {
            realConnection.close();
        } catch (SQLException s) {
            reportException(methodCall, s);
            throw s;
        } finally {
            log.connectionClosed(this);
        }
        reportReturn(methodCall);
    }

    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return iface != null && (iface == Connection.class || iface == Spy.class) ? (T) this : realConnection.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface != null && (iface == Connection.class || iface == Spy.class)) || realConnection.isWrapperFor(iface);
    }

    public void setSchema(String schema) throws SQLException {
        realConnection.setSchema(schema);
    }

    public String getSchema() throws SQLException {
        return realConnection.getSchema();
    }

    public void abort(Executor executor) throws SQLException {
        realConnection.abort(executor);
    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        realConnection.setNetworkTimeout(executor, milliseconds);
    }

    public int getNetworkTimeout() throws SQLException {
        return realConnection.getNetworkTimeout();
    }

    public int pingDatabase() throws SQLException {
        if (pingDBNoArg == null) {
            try {
                pingDBNoArg = this.realConnection.getClass().getMethod("pingDatabase", new Class<?>[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Object answer = pingDBNoArg.invoke(this.realConnection, new Object[0]);
            return (Integer) answer;
        } catch (Exception e) {
            if (e instanceof SQLException)
                throw (SQLException)e;
            throw new RuntimeException(e);
        }
    }


    public int pingDatabase(int paramInt) throws SQLException {
        if (pingDBOneArg == null) {
            try {
                pingDBOneArg = this.realConnection.getClass().getMethod("pingDatabase", new Class<?>[]{Integer.TYPE});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Object answer = pingDBOneArg.invoke(this.realConnection, new Object[]{paramInt});
            return (Integer) answer;
        } catch (Exception e) {
            if (e instanceof SQLException)
                throw (SQLException)e;
            throw new RuntimeException(e);
        }
    }

}