/*
 * MailMRAInterfaceWorker.java Created on May 9, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailBillingInterfaceFileJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.MRAInterfaceWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	09-05-2018	:	Draft
 */
@Module("mail")
@SubModule("mra")


public class MailBillingInterfaceWorker extends RequestWorker {
	
	private Log log = LogFactory.getLogger("MAIL MRA");
	private static final String CLASS = "MailBillingInterfaceWorker";
	private static final String MAL_BILLINGINTERFACE_JOB="MAL_BILLINGINTERFACE_JOB";
	private static final String METHOD_NAME="generateMailBillingInterfaceFile";
	private static final String FLAG_NO ="N";
	private static final String MAL_FLIGHTREVINTERFACE="MAL_FLIGHTREVINTERFACE";
	
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			:   A-7929 on 09-May-2018
	 * 	Used for 	        :   ICRD-245605
	 *	Parameters	        :	@param workercontext
	 *	Parameters	        :	@throws SystemException
	 */
	@Override
	public void execute(WorkerContext workercontext) throws SystemException {
		log.entering(CLASS,"execute");
		
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		LocalDate fromDate =  toDate.addDays(-60);;
		
		
		MailBillingInterfaceFileJobScheduleVO mailBillingInterfaceFileJobScheduleVO = (MailBillingInterfaceFileJobScheduleVO) workercontext
				.getJobScheduleVO(); 

		if(MAL_FLIGHTREVINTERFACE.equals(mailBillingInterfaceFileJobScheduleVO.getEvent())){
			despatchRequest("doInterfaceFlightRevenueDtls",mailBillingInterfaceFileJobScheduleVO.getCompanyCode(),0,false);
		}
		else if(MAL_BILLINGINTERFACE_JOB.equals(mailBillingInterfaceFileJobScheduleVO.getJobName())){
			despatchRequest(METHOD_NAME,mailBillingInterfaceFileJobScheduleVO.getCompanyCode(),FLAG_NO,"MALBLGINTJOB",fromDate,toDate);
		}
		
		log.exiting(CLASS, "execute");
	
		
		
	}
   /**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
     *	Added by 			:   A-7929 on 09-May-2018
     * 	Used for 	        :   ICRD-245605
     *	Parameters	        :	@return
   */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		
		log.entering(CLASS, "instantiateJobScheduleVO");
		return new MailBillingInterfaceFileJobScheduleVO();	
		
	}

}
