/*
 * SqlTxProbingAgent.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbedState;
import com.ibsplc.neoicargo.framework.probe.TxProbeFacade;
import com.ibsplc.neoicargo.framework.probe.TxProbeFacadeAware;
import com.ibsplc.neoicargo.framework.probe.sql.spy.Spy;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SpyContext;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent;

import java.util.concurrent.atomic.AtomicLong;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 *
 */
public class SqlTxProbingAgent implements SqlPrincipalAgent, TxProbeFacadeAware {

	private static final AtomicLong counter = new AtomicLong(0L);

	private TxProbeFacade facade;

	@Override
	public void setTxProbeFacade(TxProbeFacade facade) {
		this.facade = facade;
	}

	public SqlTxProbingAgent(TxProbeFacade facade){
		this.facade = facade;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#isAgentActive()
	 */
	@Override
	public boolean isAgentActive() {
		return facade.isProbeEnabled(Probe.SQL);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#exceptionOccured(com.ibsplc.jdbc.spy.Spy, java.lang.String, java.lang.Exception, java.lang.String, long)
	 */
	@Override
	public void exceptionOccured(Spy spy, String methodCall, Exception e, String sql, long execTime) {
		SqlProbePayload payload = new SqlProbePayload();
		payload.setInvocationId(counter.incrementAndGet());
		payload.setProbe(Probe.SQL);
		payload.setProbeState(ProbedState.ON);
		payload.setBody(sql);
		facade.doProbe(payload, spy, methodCall, e);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#methodReturned(com.ibsplc.jdbc.spy.Spy, java.lang.String, java.lang.String)
	 */
	@Override
	public void methodReturned(Spy spy, String methodCall, String returnMsg) {
		// NOOP not required
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#constructorReturned(com.ibsplc.jdbc.spy.Spy, java.lang.String)
	 */
	@Override
	public void constructorReturned(Spy spy, String constructionInfo) {
		// NOOP not required
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#sqlOccured(com.ibsplc.jdbc.spy.Spy, java.lang.String, java.lang.String)
	 */
	@Override
	public SpyContext onSqlExecutionStart(Spy spy, String methodCall, String sql) {
		SqlProbePayload payload = new SqlProbePayload();
		payload.setInvocationId(counter.incrementAndGet());
		payload.setProbe(Probe.SQL);
		payload.setProbeState(ProbedState.BEFORE);
		payload.setBody(sql);
		boolean probed = facade.doProbe(payload, spy, methodCall, null);
		if(probed)
			return payload;
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#sqlTimingOccured(com.ibsplc.jdbc.spy.Spy, long, java.lang.String, java.lang.String)
	 */
	@Override
	public void onSqlExecutionEnd(SpyContext context) {
		SqlProbePayload before = (SqlProbePayload) context;
		SqlProbePayload after = new SqlProbePayload();
		after.setProbe(Probe.SQL);
		after.setCorrelationId(before.getCorrelationId());
		after.setUser(before.getUser());
		after.setInvocationId(before.getInvocationId());
		after.setProbeState(ProbedState.AFTER);
		// copy the context
		after.setExecuteResponse(before.getExecuteResponse());
		after.setError(before.getError());
		after.setRowCount(before.getRowCount());
		facade.doProbeForced(after, null, null, null);
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#connectionOpened(com.ibsplc.jdbc.spy.Spy)
	 */
	@Override
	public void connectionOpened(Spy spy) {
		// not required
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#connectionClosed(com.ibsplc.jdbc.spy.Spy)
	 */
	@Override
	public void connectionClosed(Spy spy) {
		// not required
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.jdbc.spy.agents.SqlPrincipalAgent#debug(java.lang.String)
	 */
	@Override
	public void debug(String msg) {
		// not required
	}
	
}
