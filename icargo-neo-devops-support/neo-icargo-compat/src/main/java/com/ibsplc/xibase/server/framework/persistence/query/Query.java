/*
* @(#) Query.java 1.0 Apr 12, 2005
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query;

import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

import java.io.Serializable;
import java.util.List;
/**
 * The interface for all executable Queries.
 *
 */
/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Apr 12, 2005        Binu K          First draft
*/

public interface Query {

	/**
	 * Execute query to get a result list of VOs. This method accepts a mapper
	 * implementation that maps a ResultSet to a VO.
	 *
	 * @param map
	 *            A implemntation of mapper to map from the reultset to a T
	 * @return List<T> a list of VOs as a result of executing the query
	 */
	<T extends Serializable> List<T> getResultList(Mapper<T> map);

	/**
	 * Execute query to get a result list of VOs. This method accepts a mapper
	 * implementation that maps a ResultSet to a Collection of VOs.
	 *
	 * @param map
	 *            A implemntation of mapper to map from the reultset to a T
	 * @return List<T> a list of VOs as a result of executing the query
	 */
	<T extends Serializable> List<T> getResultList(MultiMapper<T> map);

	/**
	 * Set a paramater for the query at the named position
	 *
	 * @param name - the named parameter
	 * @param value - the value to be set
	 * @return the instance of query with parameter set
	 */
	Query setParameter(String name, Object value);

	/**
	 * Set a parameter to the query at the specified position
	 *
	 * @param pos - the postion at which to set
	 * @param value - the value to set
	 * @return the instance of query with parameter set
	 */
	Query setParameter(int pos, Object value);

	/**
	 * Execute query to get a single result This method accepts a mapper
	 * implementaion
	 *
	 * @param mapper
	 *            A implemntation of mapper to map from the reultset to a T
	 * @return T a result of type T obtained by executing the query
	 */
	<T extends Serializable>  T getSingleResult(Mapper<T> map);

	/**
	 * Execute an SQL INSERT,UPDATE or DELETE Query
	 *
	 * @return - the number of rows inserted/deleted/updated
	 */
	int executeUpdate();

	/**
	 * Append a condition to the query string
	 *
	 * @param value
	 * @return - the instance of query with the condition appended
	 */
	Query append(String condition);

	/**
	 * Add a hint for the Query
	 *
	 * @param hint -the hint
	 * @return - the instance of query with the hint added
	 */
	Query addHint(String hint);

	/**
	 * Set the maximum results to be returned by the query
	 * @param maxResult
	 * @return - the instance of query with the value set
	 */

	Query setMaxResults(int maxResult);

	/**
	 * Set the position for the first row to be retrieved
	 *
	 * @param startPosition
	 * @return - the instance of query with the value set
	 */
    Query setFirstResult(int startPosition);

    /**
     * Make the query sensitive to previous updates made within
     * the transaction.
     *
     * @param isSensitiveToUpdates
     * @return- the instance of query with the value set
     */
    Query setSensitivity(boolean isSensitiveToUpdates);

    /**
     * Indicates if  the query is senstitive
     * @return
     */
    boolean isSensitive();

        /**
	     * Method to combine two queries .
	     *
	     * @param appendQuery
	     * @return
	     */
	    Query combine( Query appendQuery );

	    /**
	     * A method to read the parameter set into the Query
	     * @param pos
	     * @return
	     */
    Object getParameter ( int pos );

    /**
     * Method to set the dynamic search string
     * 
     * @param dynamicQueryString
     */
    void setDynamicQuery(String dynamicQueryString);
    /**
     * Method to set the filter query key. This is for integrating addon query with base. 
     * @param filterKey
     */
    void setFilterKey(String filterKey);
}
