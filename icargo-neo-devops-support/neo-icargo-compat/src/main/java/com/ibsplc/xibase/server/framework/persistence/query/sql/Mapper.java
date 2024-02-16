/*
* @(#) Mapper.java 1.0 Apr 7, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query.sql;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The interface for all Mapper Implementations. An implementation
 * of this class will map the ResultSet to a VO and has to be passed
 * for executing an instance of a Query.
 * 
 * @see com.ibsplc.xibase.server.framework.persistence.query.Query
 */
/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Apr 7, 2005         Binu K          First draft
*/
public interface Mapper<T extends Serializable> {

	/**
	 * Map the resultset to a VO
	 * 
	 * @param rs - the ResultSet
	 * @return - an instance of VO of type T with the 
	 * values set.
	 * @throws SQLException
	 */
	  T map(ResultSet rs) throws SQLException;
	
}
