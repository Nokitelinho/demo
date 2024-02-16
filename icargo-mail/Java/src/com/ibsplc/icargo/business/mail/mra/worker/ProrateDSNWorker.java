/*
 * ProrateDSNWorker.java Created on Mar 06, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5166 
 * New job to force trigger proration of DSNs having YY sectors
 */
@Module("mail")
@SubModule("mra")
public class ProrateDSNWorker extends RequestWorker{
	private Log log = LogFactory.getLogger("MAIL MRA");

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 */
	@Override
	public void execute(WorkerContext arg0) throws SystemException {
		log.entering("ProrateDSNWorker","execute");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		String companyCode = logonAttributes.getCompanyCode();
		despatchRequest("initiateProration", companyCode);
		log.exiting("ProrateDSNWorker", "execute");
	}

	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return null;
	}

}
