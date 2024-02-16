/*
 * StockRangeUtilisationDepleteWorker.java Created on May 31,2011
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.worker;


import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Module("stockcontrol")
@SubModule("defaults")
public class UpdateStockHolderStockUtilisationWorker extends RequestWorker{

	 private Log log = LogFactory.getLogger( "STOCK RANGE UTILISATION DEPLETE WORKER" );
		/**
		 * @param context
		 * @throws SystemException
		 */
	public void execute(WorkerContext context) throws SystemException {
		log.log(log.INFO,"----->UpdateStockHolderStockUtilisationWorker");
		despatchRequest("updateStockUtilization","JOB");
		}
	public JobScheduleVO instantiateJobScheduleVO() {
		return null;
	}
}
