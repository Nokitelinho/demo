/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.USPSInvoicProcessingWorker.java
 *
 *	Created by	:	A-3429
 *	Created on	:	Nov 28, 2022
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.USPSInvoicProcessingWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-3429	:	Nov 28, 2022	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class USPSInvoicProcessingWorker extends RequestWorker{ 
	private static final Log LOGGER = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS ="USPSInvoicProcessingWorker";
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
 *	Added by 			: A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@param arg0
 *	Parameters	:	@throws SystemException
 */
	@Override
	public void execute(WorkerContext workerContext) throws SystemException {
		LOGGER.entering(CLASS, "execute");	
		InvoicJobScheduleVO invoicJobScheduleVO = (InvoicJobScheduleVO)workerContext.getJobScheduleVO();	
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setCompanyCode(invoicJobScheduleVO.getCompanyCode());	
		invoicVO.setStartBatchnum(invoicJobScheduleVO.getStartBatchnum());	
		invoicVO.setEndBatchnum(invoicJobScheduleVO.getEndBatchnum());	
		invoicVO.setFileName(invoicJobScheduleVO.getFileName());	
		invoicVO.setPoaCode(invoicJobScheduleVO.getPoacod());	
		invoicVO.setTxnCode(invoicJobScheduleVO.getTxnCode());	
		invoicVO.setTxnSerialNum(invoicJobScheduleVO.getTxnSerialNum());	
		invoicVO.setActionCode(invoicJobScheduleVO.getActionCode());
		invoicVO.setJobIdx(invoicJobScheduleVO.getJobidx());
		despatchRequest("processInvoicFileFromJob", invoicVO);
		LOGGER.exiting(CLASS, "execute");
	}
/**
 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
 *	Added by 			: A-4809 on Nov 28, 2018
 * 	Used for 	:
 *	Parameters	:	@return
 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
	
		return new InvoicJobScheduleVO();
	}

}
