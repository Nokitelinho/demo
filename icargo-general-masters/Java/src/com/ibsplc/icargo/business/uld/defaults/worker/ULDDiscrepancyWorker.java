/*
 * ULDDiscrepancyWorker.java Created on Apr 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.worker;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDDiscrepancyJobSchedulerVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1950
 *
 */
@Module("uld")
@SubModule("defaults")
public class ULDDiscrepancyWorker extends RequestWorker {

	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.log(Log.FINER,"---***inside***---");
		//int numberOfMonths = 0;
		//String companyCode = "AV";
		ULDDiscrepancyJobSchedulerVO discrepancyJobSchedulerVO = (ULDDiscrepancyJobSchedulerVO)context.getJobScheduleVO();

		log.log(Log.INFO, "%%%%%%%%%  discrepancyJobSchedulerVO  ",
				discrepancyJobSchedulerVO);
		String companyCode =  discrepancyJobSchedulerVO.getCompanyCode();
		int period =  Integer.parseInt(discrepancyJobSchedulerVO.getPeriod());


		log.log(Log.FINER,"---going to call despatch request---");
		despatchRequest("sendAlertForDiscrepancy",companyCode,period);
	}

	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDDiscrepancyJobSchedulerVO();
	}
}
