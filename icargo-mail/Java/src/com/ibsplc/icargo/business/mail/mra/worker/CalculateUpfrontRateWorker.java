package com.ibsplc.icargo.business.mail.mra.worker;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProvisionalRateJobScheduleVO;
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
public class CalculateUpfrontRateWorker extends RequestWorker {
	private static final Log LOGGER = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS ="CalculateUpfrontRateWorker";
	@Override
	public void execute(WorkerContext workerContext) throws SystemException {
		LOGGER.entering(CLASS, "execute");
		ProvisionalRateJobScheduleVO provisionalJobScheduleVO = (ProvisionalRateJobScheduleVO)workerContext.getJobScheduleVO();	
		
		Long noOfRecords = provisionalJobScheduleVO.getnoOfRecords();
		despatchRequest("calculateProvisionalRate", noOfRecords);
		LOGGER.exiting(CLASS, "execute");
	}

	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new ProvisionalRateJobScheduleVO();
	}

}
