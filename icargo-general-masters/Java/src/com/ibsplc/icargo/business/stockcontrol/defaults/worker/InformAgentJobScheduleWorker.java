/*
 * InformAgentJobScheduleWorker.java Created on Apr 25, 2006
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.worker;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.InformAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.InformAgentJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1958
 *
 */
@Module("stockcontrol")
@SubModule("defaults")
public class InformAgentJobScheduleWorker extends RequestWorker {
	 private Log log = LogFactory.getLogger( "INFORMAGENT_SCHEDULE_WORKER" );
	/**
	 * @param context
	 * @throws SystemException
	 */
	public void execute(WorkerContext context) throws SystemException {
			log.entering("FlightJobScheduleWorker","execute");
			InformAgentJobScheduleVO flightJobScheduleVO = (InformAgentJobScheduleVO)context.getJobScheduleVO();
			InformAgentFilterVO informAgentFilterVO = new InformAgentFilterVO();
			informAgentFilterVO.setCompanyCode(flightJobScheduleVO.getCompanyCode());
			despatchRequest("sendReorderMessages", informAgentFilterVO);
	}

	/**
	 *
	 * @return JobScheduleVO
	 */
	public JobScheduleVO instantiateJobScheduleVO() {
		return new InformAgentJobScheduleVO();
	}

}
