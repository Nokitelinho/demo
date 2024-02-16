/*
 * ULDPriceDepreciationWorker.java Created on Jul 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.worker;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDPriceDepreciationJobSchedulerVO;
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
public class ULDPriceDepreciationWorker extends RequestWorker {

	private Log log = LogFactory.getLogger("ULD");

	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
		log.log(Log.FINER,"---***inside ULDPriceDepreciationWorker***---");
	/*	DelayedMessageJobSchedulerVO delayedMessageJobSchedulerVO =
					(DelayedMessageJobSchedulerVO)context.getJobScheduleVO();
	    String companyCode = delayedMessageJobSchedulerVO.getCompanyCode();
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
		
		ULDPriceDepreciationJobSchedulerVO priceDepreciationJobSchedulerVO = 
			(ULDPriceDepreciationJobSchedulerVO)context.getJobScheduleVO();
		//Added by A-7359 for ICRD-233082 starts here
		log.log(Log.INFO, "%%%%%%%%%  priceDepreciationJobSchedulerVO : ",
				priceDepreciationJobSchedulerVO);
		String companyCode = priceDepreciationJobSchedulerVO.getCompanyCode();
		int period =  Integer.parseInt(priceDepreciationJobSchedulerVO.getPeriod());
		log.log(Log.FINER, "---going to call despatch request---", companyCode,period);
		//Added by A-7359 for ICRD-233082 ends here
		despatchRequest("updateULDPrice", companyCode,period);
	}

	/**
	 * @return
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ULDPriceDepreciationJobSchedulerVO();
	}
}
