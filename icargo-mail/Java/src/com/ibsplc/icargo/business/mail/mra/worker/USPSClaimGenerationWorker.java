/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.USPSClaimGenerationWorker.java
 *
 *	Created by	:	A-4809
 *	Created on	:	May 31, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.USPSClaimGenerationWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	May 31, 2019	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class USPSClaimGenerationWorker extends RequestWorker{
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS ="USPSClaimGenerationWorker";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			: A-4809 on May 31, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public void execute(WorkerContext arg0) throws SystemException {
		log.entering(CLASS, "execute");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode = logonAttributes.getCompanyCode();
		despatchRequest("generateClaimAndResditsFromJob", companyCode);
		log.exiting(CLASS, "execute");
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 *	Added by 			: A-4809 on May 31, 2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		// TODO Auto-generated method stub
		return null;
	}

}
