/*
 * WorkflowDefaultsProxy.java Created on Sep 20, 2013
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.proxy;

import java.util.Collection;
import com.ibsplc.icargo.business.workflow.defaults.vo.WorkflowVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * The Class WorkflowDefaultsProxy.
 *
 * @author A-5160
 */
@Module( "workflow" )
@SubModule( "defaults" )
public class WorkflowDefaultsProxy extends ProductProxy {
	
	/** The log. */
	private Log log = LogFactory.getLogger("SHARED_EMBARGO");

	
    /**
     * Generate notification.
     *
     * @param workflowVos the workflow vos
     * @throws SystemException the system exception
     * @throws ProxyException the proxy exception
     */
    public void generateNotification( Collection<WorkflowVO> workflowVos )
    	throws SystemException, ProxyException {
		log.entering( "WorkflowDefaultsProxy", "generateInternalMessage" );
		despatchRequest( "startWorkflow", workflowVos );
    }
  }
