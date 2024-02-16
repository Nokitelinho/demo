/*
 * ImportFlownMailWorker.java Created on Apr 1, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.ImportFlownMailJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
* @author A-2246 
* Revision History
* -------------------------------------------------------------------------
* Revision 		Date 					Author 		Description
* ------------------------------------------------------------------------- 
* 0.1     		  Apr 1, 2008			  	 A-2246		Created
*/
@Module("mail")
@SubModule("mra")
/**
 * @author a-2246
 *
 */
public class ImportFlownMailWorker extends RequestWorker{
	
	private Log log = LogFactory.getLogger("MAIL MRA");
	
	
	// used to import flown mails to MailRevenueAccounting,added by Prathibha
	
	
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 */
	@Override
	
	/**
	 * @param context
	 * @return 
	 * @exception SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.entering("ImportFlownMailWorker", "execute");
		ImportFlownMailJobScheduleVO jobscheduleVO =
			(ImportFlownMailJobScheduleVO)context.getJobScheduleVO();
		despatchRequest("processFlight", jobscheduleVO.getCompanyCode());
		log.exiting("ImportFlownMailWorker", "execute");
	}

	
	
	
	@Override
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 */
	/**
	 * @param 
	 * @return JobScheduleVO
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ImportFlownMailJobScheduleVO();
	}
}
