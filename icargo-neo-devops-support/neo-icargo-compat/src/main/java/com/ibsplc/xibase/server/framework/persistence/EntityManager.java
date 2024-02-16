/*
 * @(#) EntityManager.java 1.0 Aug 2, 2005 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.
 *
 */
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;
import org.hibernate.Session;

/**
 *  Interface used to interact with the persistence context.
 *  Clients of the presistence framework will work with a concrete
 *  implementation of this interface. Concrete implementations of
 *  the EntityManger are obtained from the <i>PersistenceController</i>
 *  which will return an EntityManger instance that works with the
 *  configured persistence API like <i>Hibernate<i>.
 *  
 *  @see PersistenceController
 */
/*
 * Revision History
 * Revision 	Date      	  	   Author			Description
 * 0.1			Jun 1, 2005	  	   Binu K			First draft
 * 0.2			Aug 2, 2005		   Binu K			Added specific exceptions
 */
public interface EntityManager {
	/**
	 * Make an instance managed and persistent.
	 * 
	 * @param entity
	 * @throws CreateException
	 */
	public void persist(Object entity) throws CreateException;

	/**
	 * Return a managed entity with the given identifier. The managed 
	 * entity is lazily initialized.  
	 * 
	 * @param entityName - the fully qualified name of the entity class
	 * @param primaryKey - the primary key of the entity
	 * @return - a managed entity
	 * @throws FinderException
	 */
	public Object find(String entityName, Object primaryKey)
			throws FinderException;

	/**
	 * Return a managed entity with the given identifier. The managed 
	 * entity is lazily initialized. 
	 * 
	 * @param entityClazz - class of the entity
	 * @param primaryKey - the primary key of the entity
	 * @return - a manged entity
	 * @throws FinderException
	 */
	public <T> T find(Class<T> entityClazz, Object primaryKey)
			throws FinderException;

	/**
	 * Update the state of a detached entity. If the entity is 
	 * managed the operation fails.
	 * 
	 * @param persistentObject - the detached instance
	 * @throws PersistenceException
	 * @throws OptimisticConcurrencyException
	 */
	public void modify(Object persistentObject) throws PersistenceException,
			OptimisticConcurrencyException;

	/**
	 * Remove a managed entity.
	 * 
	 * @param persistentObject
	 * @throws RemoveException
	 * @throws OptimisticConcurrencyException
	 */
	public void remove(Object persistentObject) throws RemoveException,
			OptimisticConcurrencyException;

	/**
	 * Force all state changes to managed entities within the context of the
	 * entity manager to be applied to the database.
	 * 
	 * @throws PersistenceException
	 */
	public void flush() throws PersistenceException;

	/**
	 * Merge the state of the detached entity with the managed entity within
	 * the context of the entity manager.
	 * 
	 * @param entity - a detached entity
	 * @return - a managed entity
	 * @throws PersistenceException
	 * @throws OptimisticConcurrencyException
	 */
	public <T> T merge(T entity) throws PersistenceException,
			OptimisticConcurrencyException;

	/**
	 * Create an instance of Query for executing an
	 * EJBQL query.
	 * @param ejbqlString - the EJB QL String
	 * @return the new Query Instance
	 */
	public Query createQuery(String ejbqlString) throws PersistenceException;

	/**
	 * Create an instance of Query for executing a
	 * named query (in EJBQL or native SQL).
	 * 
	 * @param name - the name of the named query
	 * @return the new Query Instance
	 */
	public Query createNamedQuery(String name) throws PersistenceException;

	/**
	 * Create an instance of Query for executing
	 * a native SQL query.
	 * 
	 * @param sqlString - the native query String
	 * @return - the new Query Instance
	 */
	public Query createNativeQuery(String sqlString);

	/**
	 * Create an instance of Query for executing
	 * a native SQL query.
	 * 
	 * @param sqlString - the native query String
	 * @param resultClass -  the class of the resulting instances
	 * @return the new Query Instance
	 */
	public Query createNativeQuery(String sqlString, Class resultClass);

	/**
	 * Create an instance of Query for executing
	 * a native SQL query.
	 * 
	 * @param sqlString - the native query String
	 * @param resultSetMapping -  the name of the result set mapping
	 * @return the new Query Instance
	 */
	public Query createNativeQuery(String sqlString, String resultSetMapping);

	/**
	 * Refresh the state of the instance from the
	 * database. The operation fails if the entity is not managed
	 * 
	 * @param entity - the managed entity
	 * @throws PersistenceException
	 */
	public void refresh(Object entity) throws PersistenceException;

	/**
	 * Check if the instance belongs to the current context of
	 * the EntityManager
	 * 
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	public boolean contains(Object entity) throws PersistenceException;

	/**
	 * Create an instance of QueryDAO for the given module name 
	 * 
	 * @param name - the module name
	 * @return - a new QueryDAO instance
	 * @throws PersistenceException
	 */
	public QueryDAO getQueryDAO(String name)
			throws PersistenceException;
	
	/**
	 * Create an instance of QueryDAO for the given module name 
	 * 
	 * @param klass - the query dao class name
	 * @return - a new QueryDAO instance
	 * @throws PersistenceException
	 */
	public QueryDAO getEQueryDAO(Class<? extends QueryDAO> daoKlass, String name)
			throws PersistenceException;

	/**
	 * Create an instance of ObjectQueryDAO for the given module name 
	 * 
	 * @param name - the module name
	 * @return - a new ObjectQueryDAO instance
	 * @throws PersistenceException
	 */

	<T extends QueryDAO> T getObjectQueryDAO(String moduleName)
			throws PersistenceException;
	
	/**
	 * Causes the underlying hibernate session to clear
	 * @throws PersistenceException
	 *
	 */
	public void clear() throws PersistenceException ;

	Session currentSession();

}