/*
 * DummySqlAgent.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql.spy.agents;

import com.ibsplc.neoicargo.framework.probe.sql.spy.Spy;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class DummySqlAgent implements SqlPrincipalAgent {

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#isAgentActive()
     */
    @Override
    public boolean isAgentActive() {
        // NOOP
        return false;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#exceptionOccured(com.ibsplc.neoicargo.framework.probe.sql.spy.Spy, java.lang.String, java.lang.Exception, java.lang.String, long)
     */
    @Override
    public void exceptionOccured(Spy spy, String methodCall, Exception e, String sql, long execTime) {
        // NOOP

    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#methodReturned(com.ibsplc.neoicargo.framework.probe.sql.spy.Spy, java.lang.String, java.lang.String)
     */
    @Override
    public void methodReturned(Spy spy, String methodCall, String returnMsg) {
        // NOOP

    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#constructorReturned(com.ibsplc.neoicargo.framework.probe.sql.spy.Spy, java.lang.String)
     */
    @Override
    public void constructorReturned(Spy spy, String constructionInfo) {
        // NOOP

    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#sqlOccured(com.ibsplc.neoicargo.framework.probe.sql.spy.Spy, java.lang.String, java.lang.String)
     */
    @Override
    public SpyContext onSqlExecutionStart(Spy spy, String methodCall, String sql) {
        return null;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#sqlTimingOccured(Object context)
     */
    @Override
    public void onSqlExecutionEnd(SpyContext conext) {
        // NOOP

    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#connectionOpened(com.ibsplc.neoicargo.framework.probe.sql.spy.Spy)
     */
    @Override
    public void connectionOpened(Spy spy) {
        // NOOP

    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#connectionClosed(com.ibsplc.neoicargo.framework.probe.sql.spy.Spy)
     */
    @Override
    public void connectionClosed(Spy spy) {
        // NOOP

    }

    /* (non-Javadoc)
     * @see com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent#debug(java.lang.String)
     */
    @Override
    public void debug(String msg) {
        // NOOP

    }

}
