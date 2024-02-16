/*
 * ProxySqlAgent.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql.spy.agents;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

import com.ibsplc.neoicargo.framework.probe.sql.spy.Spy;

/**
 * @author A-2394
 */
public class ProxySqlAgent implements SqlPrincipalAgent {

    private volatile SqlPrincipalAgent actual;

    /**
     * Default Constructor
     *
     * @param actual
     */
    public ProxySqlAgent(SqlPrincipalAgent actual) {
        super();
        this.actual = actual;
    }

    /**
     * @return the actual
     */
    public SqlPrincipalAgent getActual() {
        return actual;
    }

    /**
     * @param actual the actual to set
     */
    public void setActual(SqlPrincipalAgent actual) {
        this.actual = actual;
    }


    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#isAgentActive()
     */
    @Override
    public boolean isAgentActive() {
        return this.actual.isAgentActive();
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#exceptionOccured(com.ibsplc.jdbc.spy.Spy, java.lang.String, java.lang.Exception, java.lang.String, long)
     */
    @Override
    public void exceptionOccured(Spy spy, String methodCall, Exception e, String sql, long execTime) {
        this.actual.exceptionOccured(null, methodCall, e, sql, execTime);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#methodReturned(com.ibsplc.jdbc.spy.Spy, java.lang.String, java.lang.String)
     */
    @Override
    public void methodReturned(Spy spy, String methodCall, String returnMsg) {
        this.actual.methodReturned(spy, methodCall, returnMsg);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#constructorReturned(com.ibsplc.jdbc.spy.Spy, java.lang.String)
     */
    @Override
    public void constructorReturned(Spy spy, String constructionInfo) {
        this.actual.constructorReturned(spy, constructionInfo);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#sqlOccured(com.ibsplc.jdbc.spy.Spy, java.lang.String, java.lang.String)
     */
    @Override
    public SpyContext onSqlExecutionStart(Spy spy, String methodCall, String sql) {
        return this.actual.onSqlExecutionStart(spy, methodCall, sql);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#sqlTimingOccured(com.ibsplc.jdbc.spy.Spy, long, java.lang.String, java.lang.String)
     */
    @Override
    public void onSqlExecutionEnd(SpyContext context) {
        this.actual.onSqlExecutionEnd(context);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#connectionOpened(com.ibsplc.jdbc.spy.Spy)
     */
    @Override
    public void connectionOpened(Spy spy) {
        this.actual.connectionOpened(spy);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#connectionClosed(com.ibsplc.jdbc.spy.Spy)
     */
    @Override
    public void connectionClosed(Spy spy) {
        this.actual.connectionClosed(spy);
    }

    /* (non-Javadoc)
     * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#debug(java.lang.String)
     */
    @Override
    public void debug(String msg) {
        this.actual.debug(msg);
    }


}
