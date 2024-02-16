/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.ULDAcquittalWorker.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Sep 2, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDAcquittalJobScheduleVO;
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.ULDAcquittalWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 2, 2016	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class ULDAcquittalWorker extends RequestWorker{
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			: A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws SystemException 
	 */
	@Override
	public void execute(WorkerContext context) throws SystemException {
		log.entering("ULDAcquittalWorker", "execute");
		ULDAcquittalJobScheduleVO jobscheduleVO =
			(ULDAcquittalJobScheduleVO)context.getJobScheduleVO();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		if(jobscheduleVO != null){
			operationalFlightVO.setCompanyCode(jobscheduleVO.getCompanyCode());
			operationalFlightVO.setPol(jobscheduleVO.getLegOrigin());
			operationalFlightVO.setPou(jobscheduleVO.getLegDestination());
			operationalFlightVO.setAirportCode(jobscheduleVO.getLegDestination());
			operationalFlightVO.setFlightNumber(jobscheduleVO.getFlightNumber());
			if(jobscheduleVO.getCarrierId() != null && jobscheduleVO.getCarrierId().trim().length() > 0) {
				operationalFlightVO.setCarrierId(Integer.parseInt(jobscheduleVO.getCarrierId()));
			}
			if(jobscheduleVO.getFlightSequenceNumber() != null && jobscheduleVO.getFlightSequenceNumber().trim().length() > 0) {
				operationalFlightVO.setFlightSequenceNumber(Long.parseLong(jobscheduleVO.getFlightSequenceNumber()));
			}
			if(jobscheduleVO.getFlightDate() != null && jobscheduleVO.getFlightDate().trim().length() > 0) {
				operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(jobscheduleVO.getFlightDate()));
			}
			if(jobscheduleVO.getLegSerialNumber() != null && jobscheduleVO.getLegSerialNumber().trim().length() > 0) {
				operationalFlightVO.setLegSerialNumber(Integer.parseInt(jobscheduleVO.getLegSerialNumber()));
			}
		}
		
		despatchRequest("initiateULDAcquittance", operationalFlightVO);
		log.exiting("ULDAcquittalWorker", "execute");
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 *	Added by 			: A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDAcquittalJobScheduleVO();
		}

}
