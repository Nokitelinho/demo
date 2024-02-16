/*
 * TxProbeAutoWiringPostProcessor.java Created on 24-Apr-2018
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;

import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.sql.Connection;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			24-Apr-2018       		Jens J P 			First Draft
 */

/**
 * @author Jens J P
 */
public class TxProbeAutoWiringSqlPostProcessor implements BeanPostProcessor {

    static final Logger logger = LoggerFactory.getLogger(TxProbeAutoWiringSqlPostProcessor.class);

    private SqlPrincipalAgent sqlPrincipalAgent;

    public TxProbeAutoWiringSqlPostProcessor(SqlPrincipalAgent agent) {
        this.sqlPrincipalAgent = agent;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected HikariDataSource primeDataSource(HikariDataSource ds) {
        Enhancer dsEnhancer = new Enhancer();
        dsEnhancer.setSuperclass(ds.getClass());
        dsEnhancer.setCallback(new GetConnectionInterceptor(ds, this.sqlPrincipalAgent));
        logger.info("SQL Probe Support configured for pool {}", ds.getPoolName());
        return (HikariDataSource) dsEnhancer.create();
    }

    static class GetConnectionInterceptor implements MethodInterceptor {

        final SqlPrincipalAgent agent;
        final HikariDataSource proxied;

        public GetConnectionInterceptor(HikariDataSource proxied, SqlPrincipalAgent agent) {
            this.agent = agent;
            this.proxied = proxied;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            if ("getConnection".equals(method.getName()) && objects.length == 0) {
                Connection conn = this.proxied.getConnection();
                return ConnectionUtil.wrap(conn, this.agent);
            }
            if ("getConnection".equals(method.getName()) && objects.length == 2) {
                Connection conn = this.proxied.getConnection((String)objects[0], (String)objects[1]);
                return ConnectionUtil.wrap(conn, this.agent);
            }
            return methodProxy.invoke(this.proxied, objects);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HikariDataSource)
            bean = primeDataSource((HikariDataSource) bean);
        return bean;
    }


}
