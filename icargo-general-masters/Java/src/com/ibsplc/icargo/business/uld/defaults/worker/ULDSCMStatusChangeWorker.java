/*
 * ULDSCMStatusChangeWorker.java Created on Jun 25, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.worker;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDStatusChangeJobSchedulerVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
@Module("uld")
@SubModule("defaults")
public class ULDSCMStatusChangeWorker extends RequestWorker {

	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.log(Log.FINER,"---***ULDSCMStatusChangeWorker***---");
		ULDStatusChangeJobSchedulerVO statusChangeJobSchedulerVO = (ULDStatusChangeJobSchedulerVO)context.getJobScheduleVO();
		
		log.log(Log.INFO, "%%%%%%%%%  statusChangeJobSchedulerVO  ",
				statusChangeJobSchedulerVO);
		String companyCode =  statusChangeJobSchedulerVO.getCompanyCode();
		int period =  Integer.parseInt(statusChangeJobSchedulerVO.getPeriod());
		
		
		log.log(Log.FINER,"---going to call despatch request---");
		despatchRequest("updateSCMStatusForULD",companyCode,period);
	}

	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDStatusChangeJobSchedulerVO();
	}
}
