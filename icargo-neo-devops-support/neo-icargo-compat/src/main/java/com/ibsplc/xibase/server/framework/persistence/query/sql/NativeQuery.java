/*
 * @(#) NativeQuery.java 1.0 Apr 27, 2005
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence.query.sql;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.QueryException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.ResultSetWrapper.CountAwareResultSetWrapper;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.server.framework.util.error.ErrorUtils;
import com.ibsplc.xibase.util.time.XCalendar;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.*;

import static com.ibsplc.xibase.server.framework.persistence.PersistenceConstants.SET_PREFIX;
import static com.ibsplc.xibase.server.framework.persistence.PersistenceConstants.SPACE;

/**
 * The Abstract base of all Native Queries. Concrete implementations of this
 * class will be used to execute native queries. Concrete implementations can be
 * obtained from the QueryManager for named queries.Unnamed queries should
 * provide an implementation of this class.
 * <p>
 * The query once executed caches the results internally.<p>
 * <b>IMPORTANT : Using the same instance of the query
 * with different parameters set and/or conditions
 * appended WILL NOT return different results.</b>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			Apr 27, 2005 	   Binu K			First draft
 *  0.2 		Aug 07, 2006	  Dilip				Changed executeUpdate
 */
public abstract class NativeQuery implements Query {

	static final Logger logger = LoggerFactory.getLogger(NativeQuery.class);

	protected static final String SELECT_CLAUSE = "SELECT";

	protected PreparedStatement preparedStatement;

	protected final Session session;

	protected List resultList;

	protected Object singleResult;

	protected String finalisedQuery;

	protected boolean isPageable;

	protected HashMap<Integer, Object> parameters;

	protected int maxResults;

	protected int firstResult;

	protected boolean isClosed;

	protected StringBuilder appendedQuery;

	protected String hint;

	protected boolean isSensitiveToUpdates;

	protected String dynamicQueryString;
	
	protected boolean lowerCaseColumns;
	
	protected static final String TIMESTAMP = Timestamp.class.getSimpleName();

	protected static final String DATE = Date.class.getSimpleName();

	protected static final String LONG = Long.class.getSimpleName();

	protected static final String FLOAT = Float.class.getSimpleName();

	protected static final String BOOLEAN = Boolean.class.getSimpleName();

	protected static final String DOUBLE = Double.class.getSimpleName();

	protected static final String INT = "Int";
	
	protected static final String RECCNT_ALIAS = "XIBASE_RECCNT";

	protected String filterKey;
	/*
	private static final String RESULTSET_MAXSIZE_VALUE = "resultset.maxsize";
	
	protected static int RESULTSET_MAX_SIZE = -1;
	
	static{		
		RESULTSET_MAX_SIZE = Integer.parseInt(System.getProperty(RESULTSET_MAXSIZE_VALUE, "-1"));		 
	}
	*/
	/**
	 * @see Query#append(String)
	 */
	public Query append(String value) {
		if (appendedQuery == null) {
			appendedQuery = new StringBuilder();
		}
		appendedQuery.append(SPACE).append(value).append(SPACE);
		return this;
	}

	/**
	 * @see Query#addHint(String)
	 */
	public Query addHint(String hint) {
		this.hint = hint;
		return this;
	}

	/**
	 * Construct the NativeQuery
	 */
	public NativeQuery(Session session) {
		/*
		 * Changed by A-2397 for fixing the connection not released issue
		 * while IllegalArgumentException is thrown in PageableQuery.getPageAbsolute.
		 * getConnection is moved from constructor of NativeQuery to individual methods 
		 * which used the connection in the class.
		 */ 
		this.session = session;
		this.lowerCaseColumns = true;
	}

	/**
	 * @see Query#isSensitive()
	 */
	public boolean isSensitive() {
		return isSensitiveToUpdates;
	}

	/**
	 * @see Query#setSensitivity(boolean)
	 */
	public Query setSensitivity(boolean isSensitiveToUpdates) {
		this.isSensitiveToUpdates = isSensitiveToUpdates;
		if (isSensitiveToUpdates) {
			try {
				EntityManager em = PersistenceController.getEntityManager();
				em.flush();
			} catch (PersistenceException e) {
				throw new SystemException("CON005", e.getMessage(), e);
			} catch (OptimisticConcurrencyException e) {
				throw new SystemException(e.getErrorCode(), e.getEntityName(), e);
			}
		}
		return this;
	}

	/**
	 * Set a parmater at the specified position in the query
	 *
	 * @param pos -
	 *            the position in the query to be set
	 * @param value -
	 *            the value to be set to
	 * @return Query
	 */
	public Query setParameter(int pos, Object value) {
		if (value != null) {
			if (parameters == null) {
				parameters = new HashMap<>();
			}
			parameters.put(pos, value);
			return this;
		} else {
			throw new IllegalArgumentException("PARAMETER AT POSITION " + pos + " IS NULL");
		}
	}

	/**
	 * This operation is not Supported
	 *
	 * @throws UnsupportedOperationException
	 */
	public Query setParameter(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Execute query to get a list of result VOs. This method accepts a mapper
	 * implementation
	 *
	 * @param mapper
	 *            A implemntation of mapper to map from the reultset to a T
	 * @return List<T> a list of VOs as a result of executing the query
	 */
	public <T extends Serializable> List<T> getResultList(Mapper<T> mapper) {
		return session.doReturningWork(connection -> {
			if (resultList == null) {
				prepareQuery(connection);
				if (isPageable) {
					populatePageableQueryResult(mapper);
				} else {
					populateWithQueryResult(mapper);
				}
			}
			return resultList;
		});
	}

	/**
	 * Execute query to get a list of result VOs. This method accepts a mapper
	 * implementation
	 *
	 * @param multiMapper
	 *            A implementation of Multimapper to map from the reultset to a
	 *            List<T>
	 * @return List<T> a list of VOs as a result of executing the query
	 */
	public <T extends Serializable> List<T> getResultList(MultiMapper<T> multiMapper) {
		return session.doReturningWork(connection -> {
			if (resultList == null) {
				prepareQuery(connection);
				ResultSet resultSet = null;
				try {
					resultSet = preparedStatement.executeQuery();
					if (isPageable) {
						if (multiMapper instanceof PageAwareMultiMapper) {
							PageAwareMultiMapper pam = (PageAwareMultiMapper) multiMapper;
							pam.setMaxResults(this.maxResults);
							CountAwareResultSetWrapper rsw = new CountAwareResultSetWrapper(resultSet, this.lowerCaseColumns);
							pam.setResultSet(resultSet);
							int justBefore = firstResult - 1;
							if (justBefore == 0) {
								rsw.beforeFirst();
							} else {
								rsw.absolute(justBefore);
							}
							pam.setAbsoluteIndex(justBefore);
							resultList = pam.map(rsw);
							this.setRecordCount(rsw.getTotalRecordCount());
						} else {
							throw new IllegalStateException("MAPPER PROVIDED SHOULD BE PAGE AWARE.");
						}

					} else {
						final ResultSet rs = resultSet;
						ResultSetWrapper rsw = isFilteredResult() ? new ResultSetWrapper.RankKeyFilterAwareResultSetWrapper(rs,filterKey,true,this.lowerCaseColumns) : new ResultSetWrapper(rs, this.lowerCaseColumns);
						resultList = multiMapper.map(rsw);
					}

				} catch (SQLException e) {
					throw new QueryException(e);
				} finally {
					if (resultSet != null) {
						try {
							resultSet.close();
						} catch (SQLException e) {
							//Ignored
						}
					}
					close();
				}
			}
			return resultList;
		});
	}

	/**
	 * Execute query to get a single result This method accepts a mapper
	 * implementaion
	 *
	 * @param mapper
	 *            A implemntation of mapper to map from the reultset to a T
	 * @return T a result of type T obtained by executing the query
	 */
	public <T extends Serializable> T getSingleResult(Mapper<T> mapper) {
		return session.doReturningWork(connection -> {
			ResultSet rs = null;
			ResultSetWrapper rsw = null;
			prepareQuery(connection);
			if (singleResult == null) {
				try {
					rs = preparedStatement.executeQuery();
					if (rs.next()) {
						rsw = new ResultSetWrapper(rs, this.lowerCaseColumns);
						singleResult = mapper.map(rsw);
					}
				} catch (SQLException e) {
					throw new QueryException(e);
				} finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (SQLException e) {
							//Ignored
						}
					}
					close();
				}
			}
			return (T) singleResult;
		});
	}

	/**
	 * Execute query to get a single result
	 *
	 * @return T a result of type T obtained by executing the query
	 */
	public int executeUpdate() {
		return session.doReturningWork(connection -> {
			try {
				prepareQuery(connection);
				int ret = preparedStatement.executeUpdate();
				commit();
				return ret;
			} catch (SQLException e) {
				rollback();
				throw new QueryException(e);
			} finally {
				close();
			}
		});
	}

	/**
	 * @see Query#setMaxResults(int)
	 */
	public Query setMaxResults(int maxResult) {
		maxResults = maxResult;
		isPageable = true;
		return this;
	}

	/**
	 * @see Query#setFirstResult(int)
	 */
	public Query setFirstResult(int startPosition) {
		firstResult = startPosition;
		return this;
	}

	/**
	 * Utility method. To be used only in queries which return only a single
	 * long value, and where providing a <i>Mapper</i> implementation is
	 * tedious.
	 *
	 * @param name
	 * @return
	 */
	public long getLong(String name) {
		return session.doReturningWork(connection -> {
			ResultSet rs = null;
			prepareQuery(connection);
			long l = 0;
			String col = this.lowerCaseColumns ? name.toLowerCase() : name;
			try {
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					l = rs.getLong(col);
				}
			} catch (SQLException e) {
				throw new QueryException(e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						//Ignored
					}
				}
				close();
			}
			return l;
		});
	}

	/**
	 * Utility method. To be used only in queries which return only a single int
	 * value, and where providing a <i>Mapper</i> implementation is tedious.
	 *
	 * @param name
	 * @return
	 */
	public int getInt(String name) {
		return this.session.doReturningWork(connection -> {
			prepareQuery(connection);
			int i = 0;
			ResultSet rs = null;
			try {
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					i = rs.getInt(name);
				}
			} catch (SQLException e) {
				throw new QueryException(e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						//Ignored
					}
				}
				close();
			}
			return i;
		});
	}

	/**
	 * Utility method. To be used only in queries which return only a single
	 * String value, and where providing a <i>Mapper</i> implementation is
	 * tedious.
	 *
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		return session.doReturningWork(connection -> {
			ResultSet rs = null;
			prepareQuery(connection);
			String ret = null;
			try {
				rs = preparedStatement.executeQuery();
				if (rs.next()) {
					ret = rs.getString(name);
				}
			} catch (SQLException e) {
				throw new QueryException(e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						//Ignored
					}
				}
				close();
			}
			return ret;
		});
	}

	@Override
	public String toString() {
		return getFinalQuery(false);
	}

	/**
	 * Return the native query string that is associated with this
	 * instance of the query. Concrete implementations should
	 * provide an implementaion of this method
	 * @return
	 */
	public abstract String getNativeQuery();

	/**
	 * Set all the parameters that to have to be set to the
	 * PreparedStatement
	 */
	protected void setAllParameters() {
		if (parameters != null) {
			Set<Integer> keys = parameters.keySet();
			for (int i : keys) {
				invokeMethod(i, parameters.get(i));
			}
		}
	}

	/**
	 * Perpare a preparedStatement of the query
	 *
	 */
	protected void prepareStatement(Connection conn) {
		try {
			if (preparedStatement == null) {
				if (isPageable) {
					preparedStatement = conn.prepareStatement(
							getFinalQuery(true),
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
				} else {
					preparedStatement = conn.prepareStatement(getFinalQuery(true));
				}
			}
		} catch (SQLException e) {
			throw new QueryException(e);
		}
	}

	/**
	 * Prepare the query for execution
	 */
	protected void prepareQuery(Connection connection) {
		if (preparedStatement == null) {
			prepareStatement(connection);
			setAllParameters();
		}
	}

	/**
	 * Populate a non-pageable query
	 * @param mapper - a mapper implementation
	 */
	protected <T extends Serializable> void populateWithQueryResult(Mapper<T> mapper) {
		ResultSet rs = null;
		ResultSetWrapper rsw = null;		
		try {
			rs = preparedStatement.executeQuery();
			rsw = isFilteredResult() ? new ResultSetWrapper.RankKeyFilterAwareResultSetWrapper(rs,filterKey,true,this.lowerCaseColumns) : new ResultSetWrapper(rs, this.lowerCaseColumns);
			resultList = new ArrayList<T>();
			while (rs.next()) {
				resultList.add(mapper.map(rsw));
			}
		} catch (SQLException e) {
			throw new QueryException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					//Ignored
				}
			}
			close();
		}
	}

	
	/**
	 * Populate a pageable query
	 * @param mapper - the mapper implementation
	 */
	protected <T extends Serializable> void populatePageableQueryResult(Mapper<T> mapper) {
		ResultSet rs = null;
		CountAwareResultSetWrapper rsw = null;		
		try {
			preparedStatement.setFetchSize(this.maxResults - this.firstResult+ 1);
			rs = preparedStatement.executeQuery();
			rsw = new CountAwareResultSetWrapper(rs, this.lowerCaseColumns);
			int justBefore = firstResult - 1;
			if (justBefore == 0) {
				rsw.beforeFirst();
			} else {
				rsw.absolute(justBefore);
			}
			int endCount = maxResults;
			resultList = new ArrayList<T>();
			for (int i = firstResult; i <= endCount; i++) {
				if (!rs.next()) {
					break;
				}
				resultList.add(mapper.map(rsw));
			}
			this.setRecordCount(rsw.getTotalRecordCount());
		} catch (SQLException e) {
			throw new QueryException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					//Ignored
				}
			}
			close();
		}
	}

	protected String getFinalQuery(boolean isFinal) {
		if (finalisedQuery == null) {
			String finalQuery = getNativeQuery();
			if (appendedQuery != null) {
				finalQuery = new StringBuilder().append(finalQuery).append(appendedQuery).toString();
			}
			if (hint != null) {
				hint = new StringBuilder().append(SELECT_CLAUSE).append(SPACE)
						.append(hint).append(SPACE).toString();
				finalQuery = finalQuery.replaceFirst(SELECT_CLAUSE, hint);
			}
			if (isFinal) {
				finalQuery = PersistenceUtils.appendDynamicParams(finalQuery, dynamicQueryString);
				finalisedQuery = finalQuery;
			} else {
				return finalQuery;
			}
		}
		return finalisedQuery;
	}

	/**
	 * Close underlying Connection anf PreparedStatement Objects
	 *
	 */
	protected void close() {
		try {
			if (!isClosed) {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				isClosed = true;
			}
		} catch (SQLException e) {
			throw new SystemException("CON005", "Persistence Error", e);
		}
	}

	/**
	 * Find actual method on PreparedStatment to invoke
	 *
	 * @param obj - any object
	 * @return - the Method
	 */
	private Method getMethodToInvoke(Object obj) {
		String name = null;
		Method method = null;
		try {
			if (obj instanceof XCalendar) {
				XCalendar cal = (XCalendar) obj;
				if (cal.isTimePresent()) {
					name = SET_PREFIX + TIMESTAMP;
					method = preparedStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, Timestamp.class, Calendar.class });
				} else {
					name = SET_PREFIX + DATE;
					method = preparedStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, Date.class, Calendar.class });
				}
			} else if (obj instanceof Calendar) {
				name = SET_PREFIX + TIMESTAMP;
				method = preparedStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, Timestamp.class, Calendar.class });
			} 
			 else if (obj instanceof LocalDateTime) {
					name = SET_PREFIX + TIMESTAMP;
					method = preparedStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, Timestamp.class });
				} 
			
			else {
				Class clazz = obj.getClass();
				if (isPrimitive(clazz)) {
					Object[] typeAndName = findPrimitiveType(clazz);
					//Get The primitive class TYPE  - the original clazz
					//will be the boxed type
					clazz = (Class) typeAndName[0];
					name = (String) typeAndName[1];
				} else {
					clazz = obj.getClass();
					name = clazz.getSimpleName();
				}
				name = SET_PREFIX + name;
				method = preparedStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, clazz });
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			throw new QueryException("UNSUPPORTED PARAMETER TYPE " + name);
		}
		method.setAccessible(true);
		return method;
	}

	private boolean isPrimitive(Class clazz) {
		return (Number.class.isAssignableFrom(clazz) || (Boolean.class.isAssignableFrom(clazz)));
	}

	private Object[] findPrimitiveType(Class clazz) {
		Object[] typeAndName = null;
		if (Number.class.isAssignableFrom(clazz)) {
			if (Integer.class.isAssignableFrom(clazz)) {
				typeAndName = new Object[] { Integer.TYPE, INT };
			} else if (Long.class.isAssignableFrom(clazz)) {
				typeAndName = new Object[] { Long.TYPE, LONG };
			} else if (Double.class.isAssignableFrom(clazz)) {
				typeAndName = new Object[] { Double.TYPE, DOUBLE };
			} else {
				typeAndName = new Object[] { Float.TYPE, FLOAT };
			}
		} else {
			//Boolean
			typeAndName = new Object[] { Boolean.TYPE, BOOLEAN };
		}
		return typeAndName;
	}

	/**
	 * Invoke the actual method on the PreparedStatement
	 *
	 * @param pos - the postion
	 * @param obj - the object with which to set
	 */
	private void invokeMethod(int pos, Object obj) {
		try {
			Method method = getMethodToInvoke(obj);
			if (obj instanceof XCalendar) {
				XCalendar cal = (XCalendar) obj;
				if (cal.isTimePresent()) {
					Calendar jvmcal = getInJVMTimezone(cal);
					Timestamp timeStamp = new Timestamp(jvmcal.getTimeInMillis());
					method.invoke(preparedStatement, new Object[] { pos, timeStamp, jvmcal });
				} else {
					Calendar jvmcal = getInJVMTimezone(cal);
					Date date = new Date(jvmcal.getTimeInMillis());
					method.invoke(preparedStatement, new Object[] { pos, date, jvmcal });
				}
			} else if (obj instanceof Calendar) {
				Calendar cal = (Calendar) obj;
				Calendar jvmcal = getInJVMTimezone(cal);
				Timestamp timeStamp = new Timestamp(jvmcal.getTimeInMillis());
				method.invoke(preparedStatement, new Object[] { pos, timeStamp, jvmcal });
			} 
			else if (obj instanceof LocalDateTime) {
				LocalDateTime dateTime=(LocalDateTime) obj;
				Timestamp timeStamp = Timestamp.valueOf(dateTime);
				method.invoke(preparedStatement, new Object[] { pos, timeStamp });
			}
			
			else if ((obj instanceof Integer)) {
		        int val = ((Integer)obj).intValue();
		        method.invoke(this.preparedStatement, new Object[] { Integer.valueOf(pos), Integer.valueOf(val) });
			} else {
				method.invoke(preparedStatement, new Object[] { pos, obj });
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			ErrorUtils.getCallStackToLog();			
			throw new QueryException(e);
		}

	}

	private Calendar getInJVMTimezone(Calendar cal) {
		Calendar jvmCal = Calendar.getInstance();
		jvmCal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		jvmCal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		jvmCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		jvmCal.set(Calendar.HOUR, cal.get(Calendar.HOUR));
		jvmCal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
		jvmCal.set(Calendar.SECOND, cal.get(Calendar.SECOND));
		jvmCal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));
		jvmCal.set(Calendar.AM_PM, cal.get(Calendar.AM_PM));
		return jvmCal;

	}

	/*
	 * Private method to commit the transaction. Used only if the
	 * transactions are self-managed
	 */
	private void commit() {
	}

	/*
	 * Private method to rollback the transaction. Used only if the
	 * transactions are self-managed
	 */
	private void rollback() {

	}

	public Query combine(Query appendQuery) {
		if (appendQuery != null) {
			this.append(appendQuery.toString());
			int lastPosition = 0;
			this.getFinalQuery(false);
			if (parameters != null) {
				List<Integer> sortedIndexes = new ArrayList<Integer>(parameters.keySet());
				lastPosition = Collections.max(sortedIndexes);
			}
			int index = 0;
			while (appendQuery.getParameter(++index) != null) {
				this.setParameter(++lastPosition, appendQuery.getParameter(index));
			}
			return this;
		}
		return null;
	}


	public Object getParameter(int pos) {
		if (parameters != null) {
			return parameters.get(pos);
		}
		return null;
	}

	/**
	 * method to be overridden by the subclasses to get the record count set.
	 * @param recordCount
	 */
	protected void setRecordCount(int recordCount){
		// not supported by native query
	}
	/**
	 * Implementation of the method in Query
	 */
	public void setDynamicQuery(String dynamicQueryString){
		this.dynamicQueryString=dynamicQueryString;
	}

	/**
	 * @return the filterKey
	 */
	public String getFilterKey() {
		return filterKey;
	}

	/**
	 * @param filterKey the filterKey to set
	 */
	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}
	

	protected boolean isFilteredResult(){
		return Objects.nonNull(filterKey) && !filterKey.isEmpty();
	}
}