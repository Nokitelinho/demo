/*
 * WorkflowDefaultsProxy.java Created on May 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd.
 * All Rights Reserved. This software is the
 * proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;

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
 * 
 * @author a-2518
 * 
 */
@Module("workflow")
@SubModule("defaults")
public class WorkflowDefaultsProxy extends ProductProxy {

	private static final String MODULE_NAME = "mailtracking.defaults";

	private static final String CLASS_NAME = "WorkflowDefaultsProxy";

	private Log log = LogFactory.getLogger(MODULE_NAME);

	/**
	 * @author a-2518
	 * @param workflowVOs
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public void startWorkflow(Collection<WorkflowVO> workflowVOs)
			throws ProxyException, SystemException {
		log.entering(CLASS_NAME, "startWorkflow");
		log.log(Log.INFO, "<<::STARTING WORKFLOW::>> \n", workflowVOs);
		log.exiting(CLASS_NAME, "startWorkflow");
		despatchRequest("startWorkflow", workflowVOs);
		// request will be mapped in workflow request-mapping.xml
	}
}
