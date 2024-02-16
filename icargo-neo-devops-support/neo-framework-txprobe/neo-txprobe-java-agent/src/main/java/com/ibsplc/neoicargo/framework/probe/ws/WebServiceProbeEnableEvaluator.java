/*
 * WebServiceProbeEnableEvaluator.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.ws;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.TxProbeConfig;
import com.ibsplc.neoicargo.framework.probe.TxProbeEnabledEvaluator;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class WebServiceProbeEnableEvaluator implements TxProbeEnabledEvaluator {

    private ContextUtil contextUtil;

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.TxProbeEnabledEvaluator#isProbeEnabled(com.ibsplc.icargo.framework.probe.TxProbeConfig,
     * 	com.ibsplc.icargo.framework.probe.ProbePayload, java.lang.Object[])
     */
    @Override
    public boolean isProbeEnabled(TxProbeConfig config, ProbePayload probePayload, Object... probeData) {
        if(this.contextUtil == null)
            this.contextUtil = config.getApplicationContext().getBean(ContextUtil.class);
        LoginProfile profile = null;
        try {
            profile = contextUtil.callerLoginProfile();
        }catch (Exception e){
            //ignored invoked in a non auth context
        }
        String userId = "ANONYMOUS";
        if (profile != null)
            userId = profile.getUserId();
        probePayload.setUser(userId);
        if (probePayload.getUser() == null) {
            probePayload.setUser("ANONYMOUS");
        } else
            return TxProbeUtils.isIncluded(probePayload.getUser(), config.getEnabledUsers(), config.getDisabledUsers());
        return true;
    }

}
