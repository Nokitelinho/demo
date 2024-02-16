/*
 * WorkflowDefaultsProxy.java Created on Jan 03, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;

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
 * @author A-1873
 *
 */
@Module("workflow")
@SubModule("defaults")
public class WorkflowDefaultsProxy extends ProductProxy {

	//	 this is the log variable. call this for logging.
	private Log log = LogFactory.getLogger("ULD");

	/**
	 *
	 * @param workflowVos
	 * @throws SystemException
	 * @throws ProxyException
	 */
    public void startWorkflow(Collection<WorkflowVO> workflowVos)
    	throws SystemException, ProxyException {
		log.log(Log.FINE,"Inside WorkflowDefaultsProxy, startWorkFlow");
		despatchRequest("startWorkflow", workflowVos);
    }
  }
