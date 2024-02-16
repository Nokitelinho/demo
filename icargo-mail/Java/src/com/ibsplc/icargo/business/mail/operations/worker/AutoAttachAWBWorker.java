package com.ibsplc.icargo.business.mail.operations.worker;

import com.ibsplc.icargo.business.mail.operations.proxy.MailtrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AutoAttachAWBJobScheduleVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.proxy.ProxyException;

import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.worker.AutoAttachAWBWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	U-1467	:	23-Sep-2020	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class AutoAttachAWBWorker extends RequestWorker {
    private Log log = LogFactory.getLogger("MAIL OPERATIONS");
    private static final String AUTO_ATTACH_AWB_DETAILS = "autoAttachAWBDetails";

    @Override
    public void execute(WorkerContext workerContext) throws SystemException {
        log.entering("AutoAttachAWBWorker", "execute");
        AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO = (AutoAttachAWBJobScheduleVO) workerContext.getJobScheduleVO();
        MailManifestVO mailManifestVO = null;
        OperationalFlightVO operationalFlightVO = constructOperationFlightVO(autoAttachAWBJobScheduleVO);
        try {
            mailManifestVO = new MailtrackingDefaultsProxy().findContainersInFlightForManifest(operationalFlightVO);
        } catch (ProxyException e) {
            log.log(Log.FINE, "ProxyException");
        }
        if (mailManifestVO != null) {
            despatchRequest(AUTO_ATTACH_AWB_DETAILS, mailManifestVO.getContainerDetails(), operationalFlightVO);
        }
        log.exiting("AutoAttachAWBWorker", "execute");
    }


    @Override
    public JobScheduleVO instantiateJobScheduleVO() {
        return new AutoAttachAWBJobScheduleVO();
    }
    private OperationalFlightVO constructOperationFlightVO(AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO) {
        OperationalFlightVO operationalFlightVO=new OperationalFlightVO() ;
        operationalFlightVO.setCompanyCode(autoAttachAWBJobScheduleVO.getCompanyCode());
        operationalFlightVO.setCarrierId(autoAttachAWBJobScheduleVO.getCarrierId());
        operationalFlightVO.setCarrierCode(autoAttachAWBJobScheduleVO.getCarrierCode());
        operationalFlightVO.setFlightNumber(autoAttachAWBJobScheduleVO.getFlightNumber());
        operationalFlightVO.setFlightSequenceNumber(autoAttachAWBJobScheduleVO.getFlightSequenceNumber());
        operationalFlightVO.setPol(autoAttachAWBJobScheduleVO.getPol());
        operationalFlightVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND); 
        return operationalFlightVO;
    }
}
