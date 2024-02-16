/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.PublishMailOperationDataWorker.java
 * <p>
 * Created by	:	204082
 * Created on	:	26-Jul-2023
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.vo.MailOperationDataFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailOperationDataJobScheduleVO;
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
public class PublishMailOperationDataWorker extends RequestWorker { 

    private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");

    @Override
    public void execute(WorkerContext workerContext) throws SystemException {
        LOGGER.entering("PublishMailOperationDataWorker", "execute");
        MailOperationDataJobScheduleVO mailOperationDataJobScheduleVO = (MailOperationDataJobScheduleVO) workerContext.getJobScheduleVO();
        MailOperationDataFilterVO mailOperationDataFilterVO = new MailOperationDataFilterVO();
        mailOperationDataFilterVO.setCarrierCode(mailOperationDataJobScheduleVO.getCarrierCode());
        mailOperationDataFilterVO.setMailbagOrigin(mailOperationDataJobScheduleVO.getMailbagOrigin());
        mailOperationDataFilterVO.setNoOfDaysToConsider(mailOperationDataJobScheduleVO.getNoOfDaysToConsider());
        mailOperationDataFilterVO.setPostalAuthorityCode(mailOperationDataJobScheduleVO.getPostalAuthorityCode());
        mailOperationDataFilterVO.setTriggerPoints(mailOperationDataJobScheduleVO.getTriggerPoints());
        mailOperationDataFilterVO.setTolerance(mailOperationDataJobScheduleVO.getTolerance());
        despatchRequest("publishMailOperationDataForRapidSystem", mailOperationDataJobScheduleVO);
        LOGGER.exiting("PublishMailOperationDataWorker", "execute");
    }


    @Override
    public JobScheduleVO instantiateJobScheduleVO() {
        return new MailOperationDataJobScheduleVO();
    }

}
