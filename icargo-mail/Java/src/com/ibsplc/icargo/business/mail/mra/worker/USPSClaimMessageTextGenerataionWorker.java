package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSClaimMessageJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
@Module("mail")
@SubModule("mra")
public class USPSClaimMessageTextGenerataionWorker extends RequestWorker {
	
	private Log log = LogFactory.getLogger("MAIL MRA");
	private static final String CLASS = "USPSClaimMessageTextGenetaionWorker";
	private static final String METHOD_NAME="generateClaimMessageText";

	@Override
	public void execute(WorkerContext context) throws SystemException {
		USPSClaimMessageJobScheduleVO uSPSClaimMessageJobScheduleVO  =context.getJobScheduleVO();
		despatchRequest(METHOD_NAME,uSPSClaimMessageJobScheduleVO.getCompanyCode()); 
	}

	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		log.entering(CLASS, "instantiateJobScheduleVO");
		return new USPSClaimMessageJobScheduleVO();   
	}

}
