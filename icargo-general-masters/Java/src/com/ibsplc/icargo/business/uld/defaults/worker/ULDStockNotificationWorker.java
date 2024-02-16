/*
 * ULDStockNotificationWorker.java Created on Jun 01, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.worker;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDStockNotificationJobSchedulerVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3791
 *
 */
@Module("uld")
@SubModule("defaults")
public class ULDStockNotificationWorker extends RequestWorker {

	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.log(Log.FINER,"---***Scheduler Worker***---");
		ULDStockNotificationJobSchedulerVO uldStockNotificationJobSchedulerVO = (ULDStockNotificationJobSchedulerVO)context.getJobScheduleVO();
		EstimatedULDStockFilterVO estimatedULDStockFilterVO = new EstimatedULDStockFilterVO();
		String companyCode =  uldStockNotificationJobSchedulerVO.getCompanyCode();
		int airlineId = 0;
		if(uldStockNotificationJobSchedulerVO.getAirlineId()!=null){
			airlineId = Integer.parseInt(uldStockNotificationJobSchedulerVO.getAirlineId());
		}
		estimatedULDStockFilterVO.setCompanyCode(companyCode);
		estimatedULDStockFilterVO.setAirlineIdentifier(airlineId);
		
		despatchRequest("sendULDStockNotifications",estimatedULDStockFilterVO);
	}

	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDStockNotificationJobSchedulerVO();
	}
}
