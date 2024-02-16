/*
 * MarkULDMovementsOnSTAWorker.java Created on Oct 14, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.business.uld.defaults.worker;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDMovementJobSchedulerVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-8368
 * 
 *         The job shall updated the current airport for the ULDs if it
 *         satisfies the following criteria. 1) The Flight is in finalized
 *         status 2) ULDs that are in manifested status. 3) ATA is not available
 *         for the flight
 * 
 */
@Module("uld")
@SubModule("defaults")
public class ULDMovementJobScheduleWorker extends RequestWorker{
	
	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.log(Log.FINER,"---***inside***---");
		ULDMovementJobSchedulerVO uldMovementJobSchedulerVO = (ULDMovementJobSchedulerVO)context.getJobScheduleVO();
		
		log.log(Log.INFO,"%%%%%%%%%  ULDMovementJobSchedulerVO  "+uldMovementJobSchedulerVO);
		String companyCode =  uldMovementJobSchedulerVO.getCompanyCode();
		int rowCount =  Integer.parseInt(uldMovementJobSchedulerVO.getPeriod());
		
		log.log(Log.FINER,"---going to call despatch request---");
		despatchRequest("findUldsForMarkMovement",companyCode,rowCount);
	}
	
	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDMovementJobSchedulerVO();
	}
}
