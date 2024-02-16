/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.AttachPAWBToConsignmentWorker.java
 * <p>
 * Created by	:	A-9998
 * 
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.vo.AttachPAWBToConsignmentJobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Module("mail")
@SubModule("operations")
public class AttachPAWBToConsignmentWorker extends RequestWorker {

    private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
     * Added by 			: A-9998
     * Used for 	:
     * Parameters	:	@param workerContext
     * Parameters	:	@throws SystemException
     */
    @Override
    public void execute(WorkerContext workerContext) throws SystemException {
        LOGGER.entering("AttachPAWBToConsignmentWorker", "execute");
        AttachPAWBToConsignmentJobScheduleVO attachPAWBToConsignmentJobScheduleVO = workerContext.getJobScheduleVO();
        int noOfDays = attachPAWBToConsignmentJobScheduleVO.getNoOfDays();
        despatchRequest("createPAWBForConsignment",noOfDays);
        LOGGER.exiting("AttachPAWBToConsignmentWorker", "execute");
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
     * Added by 			: A-9998
     * Used for 	:
     * Parameters	:	@return
     */
    @Override
    public JobScheduleVO instantiateJobScheduleVO() {
        return new AttachPAWBToConsignmentJobScheduleVO();
    }
}
