/*
 * @(#) ResultSetWrapper.java 1.0 Mar 30, 2005 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query.sql;

import com.ibsplc.xibase.server.framework.persistence.query.ResultSetFetchSizeExceededException;
import com.ibsplc.xibase.server.framework.persistence.query.ResultSetFilter;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
/**
 * 
 * A wrapper over a resultSet. This class wraps the operations of 
 * the underlying ResultSet. An instance of this class is passed to a
 * {@link Mapper}
 * implementation
 * 
 */
/*
 * Revision History
 * Revision         Date                Author          Description
 * 1.0              Mar 30, 2005        Binu K          First draft
 */

public class ResultSetWrapper implements ResultSet {

	private ResultSet resultSet;
    private final boolean lowerCaseColumns;
	private ArrayList<String> columns;

	private static final String LAST_UPDATED_TIME_COLUMN = "LSTUPTTIM";

	private static boolean ignoreCheck;
	private static boolean postgresCheck;
	private static final String RESULTSET_MAXSIZE_VALUE = "resultset.maxsize";
	private static final int RESULTSET_MAX_SIZE=Integer.parseInt(System.getProperty(RESULTSET_MAXSIZE_VALUE, "-1"));
	private int rsCount=0;
	
	static {
		ignoreCheck = false;
		postgresCheck = isPostgreEDB();

	}
	

	static boolean isPostgreEDB() {	
    	return PersistenceUtils.isPostgreEDB();
	}

	private void populateColumns(ResultSet resultSet) throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int count = rsmd.getColumnCount();
		columns = new ArrayList<String>(count);
		for (int i = 1; i <= count; i++) {
			columns.add(rsmd.getColumnLabel(i));
		}
	}

	private boolean checkIfColumnExists(String column) {
		return columns.contains(column);
	}

	/**
	 * @param resultSet
	 */
	public ResultSetWrapper(ResultSet resultSet) throws SQLException {
		this(resultSet, false);
	}
	
	public ResultSetWrapper(ResultSet resultSet, boolean lowerCaseColumns) throws SQLException {
		this.resultSet = resultSet;
		populateColumns(resultSet);
		this.lowerCaseColumns = lowerCaseColumns;
	}

	
	public boolean next() throws SQLException {
		++rsCount;
        if(RESULTSET_MAX_SIZE > 0 && rsCount > RESULTSET_MAX_SIZE){
            throw new ResultSetFetchSizeExceededException("QUERY RETURNED RESULT EXCEEDING CONFIGURED SIZE OF " + RESULTSET_MAX_SIZE);
        }
		return resultSet.next();
	}

	public void close() throws SQLException {
		throw new UnsupportedOperationException();

	}

	public boolean wasNull() throws SQLException {
		return resultSet.wasNull();
	}

	public String getString(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public boolean getBoolean(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public byte getByte(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public short getShort(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public int getInt(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public long getLong(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public float getFloat(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public double getDouble(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public byte[] getBytes(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Date getDate(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Time getTime(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Timestamp getTimestamp(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public InputStream getAsciiStream(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public InputStream getUnicodeStream(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public InputStream getBinaryStream(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}


	private String resolveColumn(String col){
	   return this.lowerCaseColumns ? col.toLowerCase() : col;
	}

	public String getString(String arg0) throws SQLException {
        arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			if(postgresCheck) {
				if(resultSet.getString(arg0)!= null && !"".equals(resultSet.getString(arg0))) {
					return resultSet.getString(arg0);
				}
				return null;
			}
			return resultSet.getString(arg0);
		} else {
			return null;
		}
	}

	public boolean getBoolean(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getBoolean(arg0);
		} else {
			return false;
		}
	}

	public byte getByte(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getByte(arg0);
		} else {
			return 0;
		}
	}

	public short getShort(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getShort(arg0);
		} else {
			return 0;
		}
	}

	public int getInt(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getInt(arg0);
		} else {
			return 0;
		}
	}

	public long getLong(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getLong(arg0);
		} else {
			return 0;
		}
	}

	public float getFloat(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getFloat(arg0);
		} else {
			return 0;
		}
	}

	public double getDouble(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getDouble(arg0);
		} else {
			return 0;
		}
	}

	/**
	 * @deprecated
	 * 
	 */
	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
	   arg0 = resolveColumn(arg0);	
       return resultSet.getBigDecimal(arg0, arg1);

	}

	public byte[] getBytes(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getBytes(arg0);
		} else {
			return null;
		}
	}

	public Date getDate(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			if (!ignoreCheck) {
				final String LAST_UPDATED_TIME_COLUMN_RESOLVED = resolveColumn(LAST_UPDATED_TIME_COLUMN);
				if (LAST_UPDATED_TIME_COLUMN_RESOLVED.equals(arg0)) {
					throw new IllegalArgumentException(LAST_UPDATED_TIME_COLUMN + " SHOULD BE USED ALONG WITH rs.getTimestamp(..)");
				}
			}
			return resultSet.getDate(arg0);
		} else {
			return null;
		}
	}

	public Time getTime(String arg0) throws SQLException {
		throw new UnsupportedOperationException("getTime is Unsupported");
		/*
		 * if (checkIfColumnExists(arg0)) { Calendar cal = getUserTimeZone();
		 * return resultSet.getTime(arg0, cal); } else { return null; }
		 */
	}

	public Timestamp getTimestamp(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getTimestamp(arg0);
		} else {
			return null;
		}

	}



	public InputStream getAsciiStream(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getAsciiStream(arg0);
		} else {
			return null;
		}
	}

	/**
	 * @deprecated
	 * 
	 */
	public InputStream getUnicodeStream(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getUnicodeStream(arg0);
		} else {
			return null;
		}
	}

	public InputStream getBinaryStream(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getBinaryStream(arg0);
		} else {
			return null;
		}
	}

	public SQLWarning getWarnings() throws SQLException {
		return resultSet.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		resultSet.clearWarnings();

	}

	public String getCursorName() throws SQLException {
		return resultSet.getCursorName();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return resultSet.getMetaData();
	}

	public Object getObject(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Object getObject(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getObject(arg0);
		} else {
			return null;
		}
	}

	public int findColumn(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.findColumn(arg0);
	}

	public Reader getCharacterStream(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Reader getCharacterStream(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getCharacterStream(arg0);
		} else {
			return null;
		}
	}

	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		if (checkIfColumnExists(arg0)) {
			return resultSet.getBigDecimal(arg0);
		} else {
			return null;
		}
	}

	public boolean isBeforeFirst() throws SQLException {
		return resultSet.isBeforeFirst();
	}

	public boolean isAfterLast() throws SQLException {
		return resultSet.isAfterLast();
	}

	public boolean isFirst() throws SQLException {
		return resultSet.isFirst();
	}

	public boolean isLast() throws SQLException {
		return resultSet.isLast();
	}

	public void beforeFirst() throws SQLException {
		resultSet.beforeFirst();

	}

	public void afterLast() throws SQLException {
		resultSet.afterLast();

	}

	public boolean first() throws SQLException {
		return resultSet.first();
	}

	public boolean last() throws SQLException {
		return resultSet.last();
	}

	public int getRow() throws SQLException {
		return resultSet.getRow();
	}

	public boolean absolute(int arg0) throws SQLException {
		return resultSet.absolute(arg0);
	}

	public boolean relative(int arg0) throws SQLException {
		return resultSet.relative(arg0);
	}

	public boolean previous() throws SQLException {
		return resultSet.previous();
	}

	public void setFetchDirection(int arg0) throws SQLException {
		resultSet.setFetchDirection(arg0);

	}

	public int getFetchDirection() throws SQLException {
		return resultSet.getFetchDirection();
	}

	public void setFetchSize(int arg0) throws SQLException {
		resultSet.setFetchSize(arg0);

	}

	public int getFetchSize() throws SQLException {
		return resultSet.getFetchSize();
	}

	public int getType() throws SQLException {
		return resultSet.getType();
	}

	public int getConcurrency() throws SQLException {
		return resultSet.getConcurrency();
	}

	public boolean rowUpdated() throws SQLException {
		return resultSet.rowUpdated();
	}

	public boolean rowInserted() throws SQLException {
		return resultSet.rowInserted();
	}

	public boolean rowDeleted() throws SQLException {
		return resultSet.rowDeleted();
	}

	public void updateNull(int arg0) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateByte(int arg0, byte arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateShort(int arg0, short arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateInt(int arg0, int arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateLong(int arg0, long arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateFloat(int arg0, float arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateDouble(int arg0, double arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateString(int arg0, String arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateDate(int arg0, Date arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateTime(int arg0, Time arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateObject(int arg0, Object arg1, int arg2)
			throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateObject(int arg0, Object arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateNull(String arg0) throws SQLException {
		resultSet.updateNull(arg0);

	}

	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		resultSet.updateBoolean(arg0, arg1);

	}

	public void updateByte(String arg0, byte arg1) throws SQLException {
		resultSet.updateByte(arg0, arg1);

	}

	public void updateShort(String arg0, short arg1) throws SQLException {
		resultSet.updateShort(arg0, arg1);

	}

	public void updateInt(String arg0, int arg1) throws SQLException {
		resultSet.updateInt(arg0, arg1);

	}

	public void updateLong(String arg0, long arg1) throws SQLException {
		resultSet.updateLong(arg0, arg1);

	}

	public void updateFloat(String arg0, float arg1) throws SQLException {
		resultSet.updateFloat(arg0, arg1);

	}

	public void updateDouble(String arg0, double arg1) throws SQLException {
		resultSet.updateDouble(arg0, arg1);

	}

	public void updateBigDecimal(String arg0, BigDecimal arg1)
			throws SQLException {
		resultSet.updateBigDecimal(arg0, arg1);

	}

	public void updateString(String arg0, String arg1) throws SQLException {
		resultSet.updateString(arg0, arg1);
	}

	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		resultSet.updateBytes(arg0, arg1);
	}

	public void updateDate(String arg0, Date arg1) throws SQLException {
		resultSet.updateDate(arg0, arg1);

	}

	public void updateTime(String arg0, Time arg1) throws SQLException {
		resultSet.updateTime(arg0, arg1);

	}

	public void updateTimestamp(String arg0, Timestamp arg1)
			throws SQLException {
		resultSet.updateTimestamp(arg0, arg1);

	}

	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		resultSet.updateAsciiStream(arg0, arg1, arg2);

	}

	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		resultSet.updateBinaryStream(arg0, arg1, arg2);

	}

	public void updateCharacterStream(String arg0, Reader arg1, int arg2)
			throws SQLException {
		resultSet.updateCharacterStream(arg0, arg1, arg2);

	}

	public void updateObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		resultSet.updateObject(arg0, arg1, arg2);

	}

	public void updateObject(String arg0, Object arg1) throws SQLException {
		resultSet.updateObject(arg0, arg1);

	}

	public void insertRow() throws SQLException {
		resultSet.insertRow();

	}

	public void updateRow() throws SQLException {
		resultSet.updateRow();

	}

	public void deleteRow() throws SQLException {
		resultSet.deleteRow();

	}

	public void refreshRow() throws SQLException {
		resultSet.refreshRow();

	}

	public void cancelRowUpdates() throws SQLException {
		resultSet.cancelRowUpdates();

	}

	public void moveToInsertRow() throws SQLException {
		resultSet.moveToInsertRow();

	}

	public void moveToCurrentRow() throws SQLException {
		resultSet.moveToCurrentRow();

	}

	public Statement getStatement() throws SQLException {
		return resultSet.getStatement();
	}

	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		return resultSet.getObject(arg0, arg1);
	}

	public Ref getRef(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Blob getBlob(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Clob getClob(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Array getArray(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getObject(arg0, arg1);
	}

	public Ref getRef(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getRef(arg0);
	}

	public Blob getBlob(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getBlob(arg0);
	}

	public Clob getClob(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getClob(arg0);
	}

	public Array getArray(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getArray(arg0);
	}

	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getDate(arg0, arg1);
	}

	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getTime(arg0, arg1);
	}

	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public Timestamp getTimestamp(String arg0, Calendar arg1)
			throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getTimestamp(arg0, arg1);
	}

	public URL getURL(int arg0) throws SQLException {
		throw new UnsupportedOperationException();
	}

	public URL getURL(String arg0) throws SQLException {
		arg0 = resolveColumn(arg0);
		return resultSet.getURL(arg0);
	}

	public void updateRef(int arg0, Ref arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateRef(String arg0, Ref arg1) throws SQLException {
		arg0 = resolveColumn(arg0);
		resultSet.updateRef(arg0, arg1);
	}

	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		arg0 = resolveColumn(arg0);
		resultSet.updateBlob(arg0, arg1);
	}

	public void updateClob(int arg0, Clob arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateClob(String arg0, Clob arg1) throws SQLException {
		arg0 = resolveColumn(arg0);
		resultSet.updateClob(arg0, arg1);
	}

	public void updateArray(int arg0, Array arg1) throws SQLException {
		throw new UnsupportedOperationException();

	}

	public void updateArray(String arg0, Array arg1) throws SQLException {
		arg0 = resolveColumn(arg0);
		resultSet.updateArray(arg0, arg1);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return resultSet.unwrap(iface);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return resultSet.isWrapperFor(iface);
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		return resultSet.getRowId(columnIndex);
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		return resultSet.getRowId(columnLabel);
	}

	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		resultSet.updateRowId(columnIndex, x);
	}

	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		resultSet.updateRowId(columnLabel, x);
	}

	public int getHoldability() throws SQLException {
		return resultSet.getHoldability();
	}

	public boolean isClosed() throws SQLException {
		return resultSet.isClosed();
	}

	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		resultSet.updateNString(columnIndex, nString);
	}

	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateNString(columnLabel, nString);
	}

	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		resultSet.updateNClob(columnIndex, nClob);
	}

	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateNClob(columnLabel, nClob);
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		return resultSet.getNClob(columnIndex);
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		return resultSet.getNClob(columnLabel);
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		return resultSet.getSQLXML(columnIndex);
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		return resultSet.getSQLXML(columnLabel);
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		resultSet.updateSQLXML(columnIndex, xmlObject);
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateSQLXML(columnLabel, xmlObject);		
	}

	public String getNString(int columnIndex) throws SQLException {
		return resultSet.getNString(columnIndex);
	}

	public String getNString(String columnLabel) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		return resultSet.getNString(columnLabel);
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		return resultSet.getCharacterStream(columnIndex);
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		return resultSet.getCharacterStream(columnLabel);
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		resultSet.updateNCharacterStream(columnIndex, x, length);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateNCharacterStream(columnLabel, reader, length);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		resultSet.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		resultSet.updateBinaryStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		resultSet.updateCharacterStream(columnIndex, x, length);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateAsciiStream(columnLabel, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateBinaryStream(columnLabel, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateCharacterStream(columnLabel, reader, length);
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		resultSet.updateBlob(columnIndex, inputStream, length);
	}

	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateBlob(columnLabel, inputStream, length);
	}

	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		resultSet.updateClob(columnIndex, reader, length);
	}

	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateClob(columnLabel, reader, length);
	}

	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		resultSet.updateNClob(columnIndex, reader, length);
	}

	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateNClob(columnLabel, reader, length);
	}

	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		resultSet.updateNCharacterStream(columnIndex, x);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateCharacterStream(columnLabel, reader);
	}

	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		resultSet.updateAsciiStream(columnIndex, x);
	}

	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		resultSet.updateBinaryStream(columnIndex, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		resultSet.updateCharacterStream(columnIndex, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateAsciiStream(columnLabel, x);
	}

	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateBinaryStream(columnLabel, x);
	}

	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateCharacterStream(columnLabel, reader);
	}

	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		resultSet.updateBlob(columnIndex, inputStream);
	}

	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateBlob(columnLabel, inputStream);
	}

	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		resultSet.updateClob(columnIndex, reader);
	}

	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateClob(columnLabel, reader);
	}

	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		resultSet.updateNClob(columnIndex, reader);
	}
		
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		resultSet.updateNClob(columnLabel, reader);
	}
	
	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getObject(int, java.lang.Class)
	 */
	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		return resultSet.getObject(columnIndex, type);
	}

	/* (non-Javadoc)
	 * @see java.sql.ResultSet#getObject(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		columnLabel = resolveColumn(columnLabel);
		return resultSet.getObject(columnLabel, type);
	}

	/**
	 * 
	 * @author A-2394
	 */
	public static class CountAwareResultSetWrapper extends ResultSetWrapper{

		boolean isFirstInvocation = true;
		private int totalRecordCount;
		
		public CountAwareResultSetWrapper(ResultSet resultSet, boolean lowerCaseLabels) throws SQLException {
			super(resultSet, lowerCaseLabels);
		}
		
		@Override
		public boolean next() throws SQLException {
			boolean hasNext = super.resultSet.next();
			if(hasNext && isFirstInvocation){
				isFirstInvocation = false;
				totalRecordCount = this.getInt(NativeQuery.RECCNT_ALIAS);
			}
			return hasNext;
		}

		/**
		 * @return the totalRecordCount
		 */
		public int getTotalRecordCount() {
			return totalRecordCount;
		}

	}
	
	public static class RankKeyFilterAwareResultSetWrapper extends ResultSetWrapper{
		private List<ResultSetFilter> resultSetFilters = null; 
		private boolean checkResultSetSize;
		@SuppressWarnings("unchecked")
		public RankKeyFilterAwareResultSetWrapper(ResultSet resultSet,String rankFilterKey, boolean checkResultSetSize,boolean lowerCaseColumns) throws SQLException {
			super(resultSet,lowerCaseColumns);
				List<ResultSetFilter> resultSetFiltersInContext = null; //(List<ResultSetFilter>) ContextUtils.getTxBusinessParameter(ResultSetFilter.CONTEXT_KEY);
				if(Objects.nonNull(resultSetFiltersInContext)){
					this.resultSetFilters = resultSetFiltersInContext.stream().filter(resultSetFilter -> resultSetFilter.getFilterKey().equals(rankFilterKey)).collect(Collectors.toList());
				}
			this.checkResultSetSize = checkResultSetSize;
		}
		
		public RankKeyFilterAwareResultSetWrapper(ResultSet resultSet,String rankFilterKey,boolean lowerCaseColumns) throws SQLException {
			this(resultSet,rankFilterKey,false,lowerCaseColumns);
		}
		


		@Override
		public boolean next() throws SQLException {
			boolean hasNext = checkResultSetSize ? super.next() : super.resultSet.next();
			if(Objects.nonNull(resultSetFilters)){
				boolean isRankKeyDead = true;
				while(isRankKeyDead && hasNext){
					String rankKey = super.resultSet.getString("XIBASE_RECKEY");
					for(ResultSetFilter resultSetFilter : resultSetFilters){
						boolean rankKeyPresent = resultSetFilter.getFilterDataKeys().contains(rankKey);
						isRankKeyDead = resultSetFilter.getFilterType().equals(ResultSetFilter.FILTER_TYPE.INCLUDE) ? !rankKeyPresent : rankKeyPresent;
						if(isRankKeyDead){
							hasNext = super.resultSet.next();
							break;
						}
					}
				}	
			}
			return hasNext;
		}

		
	}

}