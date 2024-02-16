/*
 * ProxiedDataSource.java Created on 16-May-2017
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			16-May-2017       		Jens J P 			First Draft
 */

/**
 * @author E-1279
 *
 */
public class ProxiedDataSource implements DataSource{

	private final DataSource proxied;

	/**
	 * Default Constructor
	 * @param proxied
	 */
	public ProxiedDataSource(DataSource proxied) {
		super();
		this.proxied = proxied;
	}

	/**
	 * @return
	 * @throws SQLException
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return proxied.getLogWriter();
	}

	/**
	 * @param iface
	 * @return
	 * @throws SQLException
	 * @see java.sql.Wrapper#unwrap(Class)
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return proxied.unwrap(iface);
	}

	/**
	 * @param out
	 * @throws SQLException
	 * @see javax.sql.CommonDataSource#setLogWriter(PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		proxied.setLogWriter(out);
	}

	/**
	 * @param iface
	 * @return
	 * @throws SQLException
	 * @see java.sql.Wrapper#isWrapperFor(Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return proxied.isWrapperFor(iface);
	}

	/**
	 * @return
	 * @throws SQLException
	 * @see DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = proxied.getConnection();
		Connection answer = ConnectionUtil.wrap(conn, null);
		return answer;
	}

	/**
	 * @param seconds
	 * @throws SQLException
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		proxied.setLoginTimeout(seconds);
	}

	/**
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @see DataSource#getConnection(String, String)
	 */
	public Connection getConnection(String username, String password) throws SQLException {
		Connection conn = proxied.getConnection(username, password);
		Connection answer = ConnectionUtil.wrap(conn, null);
		return answer;
	}

	/**
	 * @return
	 * @throws SQLException
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		return proxied.getLoginTimeout();
	}

	/**
	 * @return
	 * @throws SQLFeatureNotSupportedException
	 * @see javax.sql.CommonDataSource#getParentLogger()
	 */
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return proxied.getParentLogger();
	}
	
}
