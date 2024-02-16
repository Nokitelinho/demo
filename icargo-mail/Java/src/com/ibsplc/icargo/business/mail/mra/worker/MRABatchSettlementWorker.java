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

@Module("mail")
@SubModule("mra")
public class MRABatchSettlementWorker extends RequestWorker{
	
private static final Log LOG = LogFactory.getLogger("MAIL MRA");
	
	@Override
	public void execute(WorkerContext arg0) throws SystemException {
		LOG.entering("MRABatchSettlementWorker","execute");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode = logonAttributes.getCompanyCode();
		despatchRequest("mraBatchSettlementWorkerJob", companyCode);
		LOG.exiting("MRABatchSettlementWorker", "execute");
	}

	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return null;
	}

}
