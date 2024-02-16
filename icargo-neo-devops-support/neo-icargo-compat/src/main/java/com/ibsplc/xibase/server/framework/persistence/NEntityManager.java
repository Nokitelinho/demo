/*
 * NEntityManager.java Created on 24/05/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.persistence;

import com.ibsplc.neoicargo.framework.core.context.tenant.support.TenantApplicationContext;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jpa.config.DefaultJpaConstants;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.QueryManager;
import com.ibsplc.xibase.server.framework.persistence.query.object.AbstractObjectQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.object.ObjectQueryManager;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.function.Supplier;

import static com.ibsplc.neoicargo.framework.core.util.BeanUtil.*;

/**
 * @author jens
 */
public class NEntityManager implements EntityManager{

    static final Logger logger = LoggerFactory.getLogger(NEntityManager.class);

    private final ContextUtil contextUtil;
    private final Supplier<javax.persistence.EntityManager> entityManagerSupplier;

    /**
     *
     * @param contextUtil
     */
    public NEntityManager(ContextUtil contextUtil) {
        this.contextUtil = contextUtil;
        this.entityManagerSupplier = lazyInit(() -> (javax.persistence.EntityManager) contextUtil.getBean(javax.persistence.EntityManager.class));
    }

    @Override
    public void persist(Object entity) throws CreateException{
        logger.info("Persisting {}", entity);
        entityManagerSupplier.get().persist(entity);
    }

    @Override
    @SneakyThrows
    public Object find(String entityName, Object primaryKey) throws FinderException {
        Class<?> klass = this.getClass().getClassLoader().loadClass(entityName);
        return find(klass, primaryKey);
    }

    @Override
    public <T> T find(Class<T> entityClazz, Object primaryKey) throws FinderException {
        T result = entityManagerSupplier.get().find(entityClazz, primaryKey);
        if(Objects.isNull(result))
            throw new FinderException("No persisted object found for " + primaryKey);
        return result;
    }

    @Override
    public void modify(Object persistentObject) throws PersistenceException, OptimisticConcurrencyException {
        throw new UnsupportedOperationException("modify is not implemented.");
    }

    @Override
    public void remove(Object persistentObject) throws RemoveException, OptimisticConcurrencyException {
        entityManagerSupplier.get().remove(persistentObject);
    }

    @Override
    public void flush() throws PersistenceException {
        entityManagerSupplier.get().flush();
    }

    @Override
    public <T> T merge(T entity) throws PersistenceException, OptimisticConcurrencyException {
        return entityManagerSupplier.get().merge(entity);
    }

    @Override
    public Query createQuery(String ejbqlString) throws PersistenceException {
        throw new UnsupportedOperationException("createQuery is not implemented.");
    }

    @Override
    public Query createNamedQuery(String name) throws PersistenceException {
        throw new UnsupportedOperationException("createQuery is not implemented.");
    }

    @Override
    public Query createNativeQuery(String sqlString) {
        throw new UnsupportedOperationException("createQuery is not implemented.");
    }

    @Override
    public Query createNativeQuery(String sqlString, Class resultClass) {
        throw new UnsupportedOperationException("createQuery is not implemented.");
    }

    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        throw new UnsupportedOperationException("createQuery is not implemented.");
    }

    @Override
    public void refresh(Object entity) throws PersistenceException {
        entityManagerSupplier.get().refresh(entity);
    }

    @Override
    public boolean contains(Object entity) throws PersistenceException {
        return entityManagerSupplier.get().contains(entity);
    }

    @Override
    @SneakyThrows
    public QueryDAO getQueryDAO(String name) throws PersistenceException {
        QueryRepository.QueryConfiguration config = QueryRepository.resolveQueryConfiguration(name, (TenantApplicationContext) ContextUtil.getTenantContext());
        QueryDAO dao = config.sqlDaoKlass.getDeclaredConstructor().newInstance();
        if(dao instanceof AbstractQueryDAO) {
            AbstractQueryDAO abstractQueryDAO = (AbstractQueryDAO) dao;
            abstractQueryDAO.setQueryManager(new QueryManager(name, dao, config.qryMappings, config.procMappings, this.entityManagerSupplier.get()));
        }
        return dao;
    }

    @Override
    public QueryDAO getEQueryDAO(Class<? extends QueryDAO> daoKlass, String name) throws PersistenceException {
        throw new UnsupportedOperationException();
    }

    @Override
    @SneakyThrows
    public <T extends QueryDAO> T getObjectQueryDAO(String name) throws PersistenceException {
        QueryRepository.QueryConfiguration config = QueryRepository.resolveQueryConfiguration(name, (TenantApplicationContext) ContextUtil.getTenantContext());
        T dao = (T) config.objectDaoKlass.getDeclaredConstructor().newInstance();
        if(dao instanceof AbstractObjectQueryDAO) {
            AbstractObjectQueryDAO abstractQueryDAO = (AbstractObjectQueryDAO) dao;
            abstractQueryDAO.setQueryManager(new ObjectQueryManager(name, dao, config.qryMappings, config.procMappings, this.entityManagerSupplier.get()));
        }
        return dao;
    }

    @Override
    public void clear() throws PersistenceException {
        entityManagerSupplier.get().clear();
    }

    @Override
    public Session currentSession() {
        return this.entityManagerSupplier.get().unwrap(Session.class);
    }
}
