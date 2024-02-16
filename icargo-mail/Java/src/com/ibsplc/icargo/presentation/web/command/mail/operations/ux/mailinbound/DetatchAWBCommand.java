package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class DetatchAWBCommand extends AbstractCommand {


	private static final String CANNOT_BE_DETACHED = "mailtracking.defaults.detachawb.msg.err.cannotbedetached";
	private static final Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS ScreenLoadAttachAwbCommand");
	private static final String CLASS_NAME = "DetatchAWBCommand";
	

	public void execute(ActionContext actionContext) throws BusinessDelegateException {
	LOGGER.entering(CLASS_NAME, "detatchAWBCommand");

		LogonAttributes logonAttributes = getLogonAttribute();
		
		MailinboundModel inboundModel = (MailinboundModel) actionContext.getScreenModel();
		Collection<ContainerDetailsVO> containers = new ArrayList<>();
		ContainerDetails containerDetails = null;
		ContainerDetailsVO containerDetailsVO = null;
		List<ErrorVO> errors = null;
		ArrayList<ContainerDetails> containerDetailsCollection = inboundModel.getContainerDetailsCollection();
		List<DespatchDetails> dsnList = inboundModel.getDespatchDetailsList();
		if (containerDetailsCollection != null) {
			containerDetails = containerDetailsCollection.get(0);
			containerDetailsVO = MailInboundModelConverter.constructContainerDetailsVO(containerDetails,
					logonAttributes);
			containers.add(containerDetailsVO);

			Collection<DSNVO> dsnVOs = MailInboundModelConverter.constructDSNVOs(dsnList);
			containerDetailsVO.setDsnVOs(dsnVOs);
			if (!(containerDetailsVO.getDsnVOs().isEmpty())) {
				containerDetailsVO.setFromDetachAWB("N");
			}
			else {
				containerDetailsVO.setFromDetachAWB("Y");
			}
			containerDetailsVO.setFromScreen("Inbound");
		
			
		
			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
			try {
				mailTrackingDefaultsDelegate.detachAWBDetails(containerDetailsVO);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
			}
		}
	}
}
