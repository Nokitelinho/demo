/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.PublishMailMasterDataWorker.java
 * <p>
 * Created by	:	204082
 * Created on	:	26-Sep-2022
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.vo.MailMasterDataFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailMasterDataJobScheduleVO;
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
public class PublishMailMasterDataWorker extends RequestWorker {

    private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
     * Added by 			: 204082 on Sep 26, 2022
     * Used for 	:
     * Parameters	:	@param workerContext
     * Parameters	:	@throws SystemException
     */
    @Override
    public void execute(WorkerContext workerContext) throws SystemException {
        LOGGER.entering("PublishMailMasterDataWorker", "execute");
        MailMasterDataJobScheduleVO mailMasterDataJobScheduleVO = (MailMasterDataJobScheduleVO) workerContext.getJobScheduleVO();
        MailMasterDataFilterVO mailMasterDataFilterVO = new MailMasterDataFilterVO();
        mailMasterDataFilterVO.setCompanyCode(mailMasterDataJobScheduleVO.getCompanyCode());
        mailMasterDataFilterVO.setMasterType(mailMasterDataJobScheduleVO.getMasterType());
        mailMasterDataFilterVO.setNoOfDaysToConsider(mailMasterDataJobScheduleVO.getNoOfDaysToConsider() != null ? mailMasterDataJobScheduleVO.getNoOfDaysToConsider() : 0);
        mailMasterDataFilterVO.setLastScanTime(mailMasterDataJobScheduleVO.getLastScanTime() != null ? mailMasterDataJobScheduleVO.getLastScanTime() : 0);
        mailMasterDataFilterVO.setRecordSize(mailMasterDataJobScheduleVO.getRecordSize() != null ? mailMasterDataJobScheduleVO.getRecordSize() : 2000);
        despatchRequest("publishMasterDataForMail", mailMasterDataFilterVO);
        LOGGER.exiting("PublishMailMasterDataWorker", "execute");
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
     * Added by 			: 204082 on Sep 26, 2022
     * Used for 	:
     * Parameters	:	@return
     */
    @Override
    public JobScheduleVO instantiateJobScheduleVO() {
        return new MailMasterDataJobScheduleVO();
    }
}
