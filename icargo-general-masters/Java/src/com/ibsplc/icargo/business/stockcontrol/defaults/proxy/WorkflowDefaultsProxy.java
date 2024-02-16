/*
 * WorkflowDefaultsProxy.java Created on Jul 6, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.proxy;

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
 * @author A-1883
 *
 */
@Module("workflow")
@SubModule("defaults")
public class WorkflowDefaultsProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("STOCK CONTROL PROXY");
	/**
	 * @author A-1883
	 * @param workflowVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void startWorkflow(Collection<WorkflowVO> workflowVOs)
		throws SystemException,ProxyException {
    	log.entering("WorkflowDefaultsProxy","startWorkflow");
    	despatchRequest("startWorkflow",workflowVOs);
    	log.exiting("WorkflowDefaultsProxy","startWorkflow");
	}
	
}
