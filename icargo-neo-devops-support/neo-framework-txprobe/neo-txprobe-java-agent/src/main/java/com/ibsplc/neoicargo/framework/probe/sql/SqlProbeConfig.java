/*
 * SqlProbeConfig.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class SqlProbeConfig implements SqlProbeConfigMXBean {

	private boolean logExecute = true;
	private boolean logExecuteUpdate = true;
	private boolean logExecuteBatch = true;
	private boolean logExecuteQuery = true;
	private boolean logErrors = true;
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#isLogErrors()
	 */
	@Override
	public boolean isLogErrors() {
		return logErrors;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#setLogErrors(boolean)
	 */
	@Override
	public void setLogErrors(boolean logErrors) {
		this.logErrors = logErrors;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#isLogExecute()
	 */
	@Override
	public boolean isLogExecute() {
		return logExecute;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#setLogExecute(boolean)
	 */
	@Override
	public void setLogExecute(boolean logExecute) {
		this.logExecute = logExecute;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#isLogExecuteUpdate()
	 */
	@Override
	public boolean isLogExecuteUpdate() {
		return logExecuteUpdate;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#setLogExecuteUpdate(boolean)
	 */
	@Override
	public void setLogExecuteUpdate(boolean logExecuteUpdate) {
		this.logExecuteUpdate = logExecuteUpdate;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#isLogExecuteBatch()
	 */
	@Override
	public boolean isLogExecuteBatch() {
		return logExecuteBatch;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#setLogExecuteBatch(boolean)
	 */
	@Override
	public void setLogExecuteBatch(boolean logExecuteBatch) {
		this.logExecuteBatch = logExecuteBatch;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#isLogExecuteQuery()
	 */
	@Override
	public boolean isLogExecuteQuery() {
		return logExecuteQuery;
	}

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.probe.sql.SqlProbeConfigMXBean#setLogExecuteQuery(boolean)
	 */
	@Override
	public void setLogExecuteQuery(boolean logExecuteQuery) {
		this.logExecuteQuery = logExecuteQuery;
	}
	
	
}
