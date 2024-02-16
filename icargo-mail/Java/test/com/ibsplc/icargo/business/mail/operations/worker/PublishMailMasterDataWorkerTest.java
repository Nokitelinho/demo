/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.PublishMailMasterDataWorkerTest.java
 * <p>
 * Created by	:	204082
 * Created on	:	04-Oct-2022
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.vo.MailMasterDataJobScheduleVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PublishMailMasterDataWorkerTest extends AbstractFeatureTest {

    private PublishMailMasterDataWorker worker;
    private WorkerContext workerContext;
    private MailMasterDataJobScheduleVO jobSchedulerVO;

    @Override
    public void setup() throws Exception {
        worker = spy(PublishMailMasterDataWorker.class);
        workerContext = new WorkerContext();
        mockDespatchRequest(PublishMailMasterDataWorker.class);
    }

    @Test
    public void validPublishMailMasterDataWorkerForGPADetails() throws SystemException {
        jobSchedulerVO = (MailMasterDataJobScheduleVO) worker.instantiateJobScheduleVO();
        jobSchedulerVO.setCompanyCode("AV");
        jobSchedulerVO.setMasterType("PACOD,SUBCLS,EXGOFC");
        workerContext.setJobScheduleVO(jobSchedulerVO);
        worker.execute(workerContext);
        verify(worker, times(1)).setExecutionSuccess(true);
    }

    @Test
    public void validPublishMailMasterDataWorkerForMailbagDetails() throws SystemException {
        jobSchedulerVO = (MailMasterDataJobScheduleVO) worker.instantiateJobScheduleVO();
        jobSchedulerVO.setCompanyCode("AV");
        jobSchedulerVO.setMasterType("MALBAGINF");
        jobSchedulerVO.setNoOfDaysToConsider(60);
        jobSchedulerVO.setNoOfDaysToConsider(72);
        jobSchedulerVO.setRecordSize(2000);
        workerContext.setJobScheduleVO(jobSchedulerVO);
        worker.execute(workerContext);
        verify(worker, times(1)).setExecutionSuccess(true);
    }
}