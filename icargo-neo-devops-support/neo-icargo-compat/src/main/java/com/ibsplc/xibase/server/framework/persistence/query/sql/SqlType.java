/*
* @(#) SqlType.java 1.0 Apr 13, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query.sql;

import java.sql.Types;

/**
 * An enumeration of supported types that can be registered as OUT
 * prameters for a Native Prcodeure call.
 * 
 */
/*
 * Revision History
 * Revision         Date                Author          Description
 * 1.0              Apr 13, 2005        Binu K          First draft
 * 2.0              Apr 30, 2015        Jens J P        Added enum methods
 */

public enum SqlType {

	STRING {
		@Override
		public String getJavaType() {
			return toJavaType(this);
		}

		@Override
		public int getSqlType() {
			return toSqlType(this);
		}
	}, LONG {
		@Override
		public String getJavaType() {
			return toJavaType(this);
		}

		@Override
		public int getSqlType() {
			return toSqlType(this);
		}
	}, INTEGER {
		@Override
		public String getJavaType() {
			return toJavaType(this);
		}

		@Override
		public int getSqlType() {
			return toSqlType(this);
		}
	}, FLOAT {
		@Override
		public String getJavaType() {
			return toJavaType(this);
		}

		@Override
		public int getSqlType() {
			return toSqlType(this);
		}
	}, DOUBLE {
		@Override
		public String getJavaType() {
			return toJavaType(this);
		}

		@Override
		public int getSqlType() {
			return toSqlType(this);
		}
	}, DATE {
		@Override
		public String getJavaType() {
			return toJavaType(this);
		}

		@Override
		public int getSqlType() {
			return toSqlType(this);
		}
	}, TIMESTAMP {
		@Override
		public String getJavaType() {
			return toJavaType(this);
		}

		@Override
		public int getSqlType() {
			return toSqlType(this);
		}
	};

	/**
	 * Get the Java type of the instance of this enum
	 * 
	 * @return - the Java type
	 */
	public abstract String getJavaType();
	
	/**
	 * Get the SQL type of the instance of this enum
	 * 
	 * @return - thr SQL type
	 */
	public abstract int getSqlType();
	
	/**
	 * @author A-2394
	 * @param type
	 * @return the Java Type representation
	 */
	private static final String toJavaType(SqlType type){
		switch(type){
			case DATE:
					return "Date";
			case DOUBLE:
					return "Double";
			case FLOAT:
					return "Float";
			case INTEGER:
					return "Int";
			case LONG:
				    return "Long";
			case STRING:
				   	return "String";
			case TIMESTAMP:
					return "Timestamp";
			default:
					throw new IllegalArgumentException("INVALID SQLTYPE : \"" + type + "\"");
		}
	}
	
	/**
	 * @author A-2394
	 * @param type
	 * @return the Sql type mapping
	 */
	private static final int toSqlType(SqlType type){
		switch(type){
			case DATE:
					return Types.DATE;
			case DOUBLE:
					return Types.DOUBLE;
			case FLOAT:
					return Types.FLOAT;
			case INTEGER:
					return Types.INTEGER;
			case LONG:
					return Types.BIGINT;
			case STRING:
					return Types.VARCHAR;
			case TIMESTAMP:
					return Types.TIMESTAMP;
			default:
				throw new IllegalArgumentException("INVALID SQLTYPE : \"" + type + "\"");
		}
	}

}
