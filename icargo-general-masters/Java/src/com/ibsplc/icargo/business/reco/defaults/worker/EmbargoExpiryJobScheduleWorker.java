/*
 * EmbargoExpiryJobScheduleWorker.java Created on Sep 20, 2013
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.worker;


import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoJobSchedulerVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * The Class EmbargoExpiryJobScheduleWorker.
 * @author a-5160
 */
@Module("reco")
@SubModule("defaults")
public class EmbargoExpiryJobScheduleWorker extends RequestWorker {
	 
 
 	/** The log. */
	 private Log log = LogFactory.getLogger( "SHARED_EMBARGO_WORKER" );
	
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 */
	public void execute(WorkerContext context) throws SystemException {
			log.entering("EmbargoExpiryJobScheduleWorker","execute");
			EmbargoJobSchedulerVO embargoJobSchedulerVO = (EmbargoJobSchedulerVO)context.getJobScheduleVO(); 
			despatchRequest("alertEmbargoExpiry", embargoJobSchedulerVO);
	}


	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new EmbargoJobSchedulerVO();
	}

}
