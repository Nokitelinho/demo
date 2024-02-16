/*
 * ULDStockDeviationWorker.java Created on Apr 05 , 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.worker;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDStockDeviationJobSchedulerVO;
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
public class ULDStockDeviationWorker extends RequestWorker {

	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.entering("ULDStockDeviationWorker","execute");
		ULDStockDeviationJobSchedulerVO stockDeviationJobSchedulerVO =
			(ULDStockDeviationJobSchedulerVO)context.getJobScheduleVO();
		String companyCode = stockDeviationJobSchedulerVO.getCompanyCode();
		log.log(Log.FINER, "---going to call despatch request---", companyCode);
		despatchRequest("sendAlertForULDStockDeviation", companyCode);
	}

	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDStockDeviationJobSchedulerVO();
	}
}
