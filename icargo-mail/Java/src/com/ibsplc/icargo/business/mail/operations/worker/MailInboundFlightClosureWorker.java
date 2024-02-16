/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.MailInboundFlightClosureWorker.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Sep 2, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.vo.MailFlightClosureJobScheduleVO;
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.MailInboundFlightClosureWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 2, 2016	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class MailInboundFlightClosureWorker extends RequestWorker{
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			: A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public void execute(WorkerContext workercontext) throws SystemException {
		// TODO Auto-generated method stub
	
		log.entering("MailInboundFlightClosureWorker", "execute");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		MailFlightClosureJobScheduleVO mailFlightClosureJobScheduleVO = (MailFlightClosureJobScheduleVO)workercontext.getJobScheduleVO();
		String companyCode = logonAttributes.getCompanyCode();
		//int time = mailFlightClosureJobScheduleVO.getTime();
		//String airportCode = mailFlightClosureJobScheduleVO.getAirportCode();
		despatchRequest("closeInboundFlightForMailOperation", companyCode);
		log.exiting("MailInboundFlightClosureWorker", "execute");
		
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 *	Added by 			: A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new MailFlightClosureJobScheduleVO();
		}

}
