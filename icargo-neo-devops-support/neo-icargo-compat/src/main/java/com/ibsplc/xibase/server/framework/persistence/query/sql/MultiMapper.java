/*
 * MultiMapper.java Created on Jul 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence.query.sql;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Binu K
 * 
 * The interface for all MultiMappers. A multimapper is different from the
 * ordinary mapper in that it is called only once on executing of a query with
 * it. i.e the iteration logic should be written with it. <p/>
 * A multi-mapper will be needed in instances where control is required
 * over iteration of the resultset and the level of control provided
 * by the mapper is not sufficient.<p/> 
 * e.g is a query having a join b/w a parent and child table which
 * may result in repetition of rows retrieved and the resultset
 * may have to iterated upon fully to arrive at the final results 
 */


public interface MultiMapper<T extends Serializable> {

	/**
	 * Maps the resultset to a Collection of VOs
	 * 
	 * @param rs - the ResultSet
	 * @return - an collection of VOs of type T with the 
	 * values set.
	 * @throws SQLException
	 */
	  List<T> map(ResultSet rs) throws SQLException;
}
