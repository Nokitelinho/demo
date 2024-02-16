/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.AttachPAWBToConsignmentWorkerTest.java
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
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AttachPAWBToConsignmentWorkerTest extends AbstractFeatureTest {

    private AttachPAWBToConsignmentWorker worker;
    private WorkerContext workerContext;
    private AttachPAWBToConsignmentJobScheduleVO jobSchedulerVO;

    @Override
    public void setup() throws Exception {
        worker = spy(AttachPAWBToConsignmentWorker.class);
        workerContext = new WorkerContext();
        mockDespatchRequest(AttachPAWBToConsignmentWorker.class);
    }

    @Test
    public void validAttachPAWBToConsignmentWorkerDetails() throws SystemException {
        jobSchedulerVO = (AttachPAWBToConsignmentJobScheduleVO) worker.instantiateJobScheduleVO();
        jobSchedulerVO.setNoOfDays(15);
        workerContext.setJobScheduleVO(jobSchedulerVO);
        worker.execute(workerContext);
        verify(worker, times(1)).setExecutionSuccess(true);
    }
}