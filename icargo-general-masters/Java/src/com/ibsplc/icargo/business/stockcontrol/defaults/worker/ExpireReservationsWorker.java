/*
 * ExpireReservationsWorker.java Created on Jul 25, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * 
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.worker;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.ExpireReservationJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1954
 *
 */
@Module("stockcontrol")
@SubModule("defaults")
public class ExpireReservationsWorker extends RequestWorker {

	 private Log log = LogFactory.getLogger( "STKCTRL_SCHEDULE_WORKER" );
	 
	/**
	 * @param workerContext
	 * @throws SystemException
	 */
	public void execute(WorkerContext workerContext) throws SystemException {

		log.log(Log.FINE,"---inside worker class");
		LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		ExpireReservationJobScheduleVO expResScheduleVO = 
			(ExpireReservationJobScheduleVO)workerContext.getJobScheduleVO();
		ReservationFilterVO filterVO = new ReservationFilterVO();
		filterVO.setCompanyCode(expResScheduleVO.getCompanyCode());
		filterVO.setAirportCode(expResScheduleVO.getAirportCode());
		filterVO.setCurrentDate(new LocalDate(
				logonAttributes.getStationCode(),Location.ARP,true));
		
		log.log(Log.FINER,"---going to call despatch request---");
		despatchRequest("expireReservations", filterVO);
	}

	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return null;
	}

}
