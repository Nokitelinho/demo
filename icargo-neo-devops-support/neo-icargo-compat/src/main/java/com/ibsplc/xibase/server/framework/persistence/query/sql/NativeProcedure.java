/*
 * @(#) NativeProcedure.java 1.0 Apr 12, 2005
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
import com.ibsplc.xibase.server.framework.persistence.query.Procedure;
import com.ibsplc.xibase.server.framework.persistence.query.QueryException;
import com.ibsplc.xibase.util.reflect.ReflectionUtils;
import com.ibsplc.xibase.util.time.XCalendar;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import static com.ibsplc.xibase.server.framework.persistence.PersistenceConstants.GET_PREFIX;
import static com.ibsplc.xibase.server.framework.persistence.PersistenceConstants.SET_PREFIX;

/**
 * A native implementation of @link com.ibsplc.xibase.server.framework.persistence.query.Procedure.
 * Meant to wrap the <b>"call"</b> statement of the procedure.
 * @author A-1456
 *
 */
/*
 * Revision History
 * Revision         Date                Author          Description
 * 1.0              Apr 12, 2005        Binu K          First draft
 */

public abstract class NativeProcedure implements Procedure {

	protected CallableStatement callableStatement;
	protected final Session session;
	protected String call;
	protected HashMap<Integer, SqlType> posMap;
	protected HashMap<Integer, Object> valueMap;

	private static HashMap<String, String> conversions = new HashMap<String, String>(4);

	protected boolean isClosed;
	private boolean isSensitiveToUpdates;
	
	private static final String TIMESTAMP = Timestamp.class.getSimpleName();
	private static final String DATE = Date.class.getSimpleName();
	private static final String LONG = Long.class.getSimpleName();
	private static final String FLOAT = Float.class.getSimpleName();
	private static final String BOOLEAN = Boolean.class.getSimpleName();
	private static final String DOUBLE = Double.class.getSimpleName();
	private static final String INT = "Int";
	private static final String NULL="Null";

	protected void prepareCall(Connection conn) {
		try {
			if (this.callableStatement == null) {
				this.call = getNativeCall().toUpperCase();
				this.call = new StringBuilder(this.call.length() + 4).append("{ ").append(this.call).append(" }").toString();
				this.callableStatement = conn.prepareCall(this.call);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QueryException(e);
		}
	}

	static {
		conversions.put("int", "Int");
		conversions.put("long", "Long");
		conversions.put("float", "Float");
		conversions.put("double", "Double");
	}

	/**
	 *
	 */
	public NativeProcedure(Session session) {
		super();
		posMap = new HashMap<>(4);
		this.session = session;
		session.doWork(connection -> prepareCall(connection));
	}

	public boolean isSensitive() {
		return isSensitiveToUpdates;
	}

	public Procedure setSensitivity(boolean isSensitiveToUpdates) {
		this.isSensitiveToUpdates = isSensitiveToUpdates;
		if (isSensitiveToUpdates) {
			try {
				EntityManager em = PersistenceController.getEntityManager();
				em.flush();
			}  catch (PersistenceException e) {
				throw new SystemException("CON005", "Persistence Error", e);
			}  catch (OptimisticConcurrencyException e) {
				throw new SystemException(e.getErrorCode(), e.getEntityName(), e);
			}
		}
		return this;
	}

	private void populateOutParams() {
		if (valueMap == null) {
			valueMap = new HashMap<Integer, Object>();
			Set<Integer> keys = posMap.keySet();
			for (Integer pos : keys) {
				valueMap.put(pos, getOutParameter(pos));
			}
		}
	}

	public Object getParameter(int pos) {
		return valueMap.get(pos);
	}

	private Object getOutParameter(int pos) {
		String name = new StringBuilder().append(GET_PREFIX).append(posMap.get(pos).getJavaType()).toString();
		Object ret = null;
		try {
			ret = ReflectionUtils.invokeMethod(callableStatement, name, new Object[] { pos });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibsplc.icargo.framework.persistence.query.Procedure#setParameter(int,
	 *      java.lang.Object)
	 */
	public Procedure setParameter(int pos, Object value) {
		if (value != null) {
			invokeMethod(pos, value);
			return this;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void invokeMethod(int pos, Object obj) {
		try {
			Method method = getMethodToInvoke(obj);
			if (obj instanceof XCalendar) {
				XCalendar cal = (XCalendar) obj;
				if (cal.isTimePresent()) {
					Calendar jvmcal = getInJVMTimezone(cal);
					Timestamp timeStamp = new Timestamp(jvmcal
							.getTimeInMillis());
					method.invoke(callableStatement, new Object[] { pos, timeStamp, jvmcal });
				} else {
					Calendar jvmcal = getInJVMTimezone(cal);
					Date date = new Date(jvmcal.getTimeInMillis());
					method.invoke(callableStatement, new Object[] { pos, date, cal });
				}
			} else if (obj instanceof Calendar) {
				Calendar cal = (Calendar) obj;
				Calendar jvmcal = getInJVMTimezone(cal);
				Timestamp timeStamp = new Timestamp(jvmcal.getTimeInMillis());
				method.invoke(callableStatement, new Object[] { pos, timeStamp, jvmcal });
			} else {
				method.invoke(callableStatement, new Object[] { pos, obj });
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}

	private Method getMethodToInvoke(Object obj) {
		String name = null;
		Method method = null;
		try {
			if (obj instanceof XCalendar) {
				XCalendar cal = (XCalendar) obj;
				if (cal.isTimePresent()) {
					name = SET_PREFIX + TIMESTAMP;
					method = callableStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, Timestamp.class, Calendar.class });
				} else {
					name = SET_PREFIX + DATE;
					method = callableStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, Date.class, Calendar.class });
				}
			} else if (obj instanceof Calendar) {
				name = SET_PREFIX + TIMESTAMP;
				method = callableStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, Timestamp.class, Calendar.class });
			} else {
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
				method = callableStatement.getClass().getMethod(name, new Class[] { Integer.TYPE, clazz });
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
		return (Number.class.isAssignableFrom(clazz) || (Boolean.class
				.isAssignableFrom(clazz)));
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
	 * (non-Javadoc)
	 *
	 * @see com.ibsplc.icargo.framework.persistence.query.Procedure#setOutParameter(int,
	 *      int)
	 */
	public Procedure setOutParameter(int pos, SqlType sqlType) {
		try {
			callableStatement.registerOutParameter(pos, sqlType.getSqlType());
			posMap.put(pos, sqlType);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QueryException(e);
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibsplc.icargo.framework.persistence.query.Procedure#execute()
	 */
	public void execute() {
		try {
			callableStatement.execute();
			populateOutParams();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} finally {
			close();
		}
	}

	protected void close() {
		try {
			if (!isClosed) {
				if (callableStatement != null) {
					callableStatement.close();
				}
				isClosed = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QueryException(e);
		}
	}

	public abstract String getNativeCall();

	public Procedure setNullParameter(int pos, int type){
		Method method = null;
		try {
			String name = SET_PREFIX + NULL;
			method = callableStatement.getClass().getMethod(name,new Class[] { Integer.TYPE,Integer.TYPE });
			method.setAccessible(true);
			try {
				method.invoke(callableStatement, new Object[] { pos, type });//Types.TIMESTAMP_WITH_TIMEZONE
				return this;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}