package com.ibsplc.icargo.business.mail.operations.worker;

import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInboundAutoAttachAWBJobScheduleVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Module("mail")
@SubModule("operations")
public class MailInboundAutoAttachAWBWorker extends RequestWorker {
	private static final String AUTO_ATTACH_AWB_DETAILS = "autoAttachAWBDetails";
	private static final String POPULATE_MAIL_ARRIVAL_VO_FOR_INBOUND = "populateMailArrivalVOForInbound";
	private static final String FIND_FLIGHTS_FOR_MAIL_INBOUND_AUTO_ATTACH_AWB = "findFlightsForMailInboundAutoAttachAWB";
	private static final String MODULE_SUBMODULE = "MAIL OPERATIONS";
	private static final Log LOGGER = LogFactory.getLogger(MODULE_SUBMODULE);

	@Override
	public void execute(WorkerContext workerContext) throws SystemException {
		LOGGER.entering(getClass().getSimpleName(), "execute");
		MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO = workerContext.getJobScheduleVO();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		mailInboundAutoAttachAWBJobScheduleVO.setCompanyCode(logonAttributes.getCompanyCode());
		Collection<OperationalFlightVO> operationalFlightVOs = findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
		MailArrivalVO mailArrivalVO = null;
		if (Objects.nonNull(operationalFlightVOs) && !operationalFlightVOs.isEmpty()) {
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
				mailArrivalVO = getMailArrivalVOs(operationalFlightVO);
				if (Objects.nonNull(mailArrivalVO) && Objects.nonNull(mailArrivalVO.getContainerDetails())
						&& !mailArrivalVO.getContainerDetails().isEmpty()) {
					autoAttachAWBDetails(mailArrivalVO, operationalFlightVO);
				}
			}
		}
		LOGGER.exiting(getClass().getSimpleName(), "execute");
	}

	public void autoAttachAWBDetails(MailArrivalVO mailArrivalVO, OperationalFlightVO operationalFlightVO) {
		despatchRequest(AUTO_ATTACH_AWB_DETAILS, mailArrivalVO.getContainerDetails(), operationalFlightVO);
	}

	public MailArrivalVO getMailArrivalVOs(OperationalFlightVO operationalFlightVO) {
		return despatchRequest(POPULATE_MAIL_ARRIVAL_VO_FOR_INBOUND, operationalFlightVO);
	}

	public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO) {
		return despatchRequest(FIND_FLIGHTS_FOR_MAIL_INBOUND_AUTO_ATTACH_AWB, mailInboundAutoAttachAWBJobScheduleVO);
	}

	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		return new MailInboundAutoAttachAWBJobScheduleVO();
	}

}
