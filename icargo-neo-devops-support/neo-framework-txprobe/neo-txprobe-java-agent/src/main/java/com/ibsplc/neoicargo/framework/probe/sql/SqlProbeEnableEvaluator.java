/*
 * SqlProbeEnableEvaluator.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;


import com.ibsplc.neoicargo.framework.probe.*;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class SqlProbeEnableEvaluator implements TxProbeEnabledEvaluator {

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeEnabledEvaluator#isProbeEnabled(com.ibsplc.icargo.framework.probe.TxProbeConfig,
     * com.ibsplc.icargo.framework.probe.ProbePayload, java.lang.Object[])
     */
    @Override
    public boolean isProbeEnabled(TxProbeConfig config, ProbePayload probePayload, Object... probeData) {
        String method = (String) probeData[1];
        Exception error = (Exception) probeData[2];
        if (!isMethodEnabled(method, config.getSqlProbeConfig()))
            return false;
        if (error != null && !config.getSqlProbeConfig().isLogErrors())
            return false;
        String correlationId = TxProbeContext.threadContext(TxProbeUtils.TXPROBE_CORRELATION_HEADER);
        if (correlationId == null)
            return false;
        probePayload.setCorrelationId(correlationId);
        return true;
    }

    protected boolean isMethodEnabled(String method, SqlProbeConfigMXBean config) {
        if ("execute".equals(method))
            return config.isLogExecute();
        if ("executeUpdate".equals(method))
            return config.isLogExecuteUpdate();
        if ("executeBatch".equals(method))
            return config.isLogExecuteBatch();
        if ("executeQuery".equals(method))
            return config.isLogExecuteQuery();
        return false;
    }

}
