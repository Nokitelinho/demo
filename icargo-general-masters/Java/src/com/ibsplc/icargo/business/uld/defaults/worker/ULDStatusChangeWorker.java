/*
 * ULDStatusChangeWorker.java Created on Jul 18, 2006
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
 * @author A-1954
 *
 */
@Module("uld")
@SubModule("defaults")
public class ULDStatusChangeWorker extends RequestWorker {

	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.log(Log.FINER,"---***inside***---");
		//int numberOfMonths = 0;
		//String companyCode = "AV";
		ULDStatusChangeJobSchedulerVO statusChangeJobSchedulerVO = (ULDStatusChangeJobSchedulerVO)context.getJobScheduleVO();
		
		log.log(Log.INFO, "%%%%%%%%%  statusChangeJobSchedulerVO  ",
				statusChangeJobSchedulerVO);
		String companyCode =  statusChangeJobSchedulerVO.getCompanyCode();
		int period =  Integer.parseInt(statusChangeJobSchedulerVO.getPeriod());
		
		/*
		
		DelayedMessageJobSchedulerVO delayedMessageJobSchedulerVO =
					(DelayedMessageJobSchedulerVO)context.getJobScheduleVO();
		String period = delayedMessageJobSchedulerVO.getCompanyCode();
		if(period != null){
			numberOfMonths = 
		}
		/*
		String workflowName = delayedMessageJobSchedulerVO.getWorkflowName();
		long sequenceNumber = Long.parseLong(delayedMessageJobSchedulerVO.getSequenceNumber());
		String messageType = delayedMessageJobSchedulerVO.getMessageType();
		String messageId = delayedMessageJobSchedulerVO.getMessageId();
		int instanceId = Integer.parseInt(delayedMessageJobSchedulerVO.getInstanceId());
				LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		if(companyCode == null){
			companyCode = logonAttributes.getCompanyCode();
		}
	*/
		log.log(Log.FINER,"---going to call despatch request---");
		despatchRequest("updateULDStatusAsLost",companyCode,period);
	}

	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDStatusChangeJobSchedulerVO();
	}
}
