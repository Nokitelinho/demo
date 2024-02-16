
package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.GenerateGPAInvoiceWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	09-Feb-2021	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class GenerateGPAInvoiceWorker extends RequestWorker{
	private Log logger = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS ="GenerateGPAInvoiceWorker";

	@Override
	public void execute(WorkerContext workerContext) throws SystemException {
		logger.entering(CLASS, "execute");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode = logonAttributes.getCompanyCode();
		despatchRequest("generateInvoiceJobScheduler", companyCode);
		logger.exiting(CLASS, "execute");
	}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
 *	Added by 			: A-8061 on 09-Feb-2021
 * 	Used for 	:
 *	Parameters	:	@return
 */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return null;
	}

}
