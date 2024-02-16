/*
 * StockRangeUtilisationDepleteWorker.java Created on Apr 25, 2006
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.worker;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1958
 *
 */
@Module("stockcontrol")
@SubModule("defaults")
public class StockRangeUtilisationDepleteWorker extends RequestWorker {
	 private Log log = LogFactory.getLogger( "STOCK RANGE UTILISATION DEPLETE WORKER" );
	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
			log.entering("StockRangeUtilisationDepleteWorker","execute");
			StockDepleteJobScheduleVO stockDepleteJobScheduleVO = (StockDepleteJobScheduleVO)context.getJobScheduleVO();
			StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
			stockDepleteFilterVO.setCompanyCode(stockDepleteJobScheduleVO.getCompanyCode());
			stockDepleteFilterVO.setAirlineId(stockDepleteJobScheduleVO.getAirlineId());
			// Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
			stockDepleteFilterVO.setLogClearingThreshold(stockDepleteJobScheduleVO.getLogClearingThreshold());
			despatchRequest("autoStockDeplete", stockDepleteFilterVO);
	}

	/**
	 *
	 * @return JobScheduleVO
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new StockDepleteJobScheduleVO();
	}

}
