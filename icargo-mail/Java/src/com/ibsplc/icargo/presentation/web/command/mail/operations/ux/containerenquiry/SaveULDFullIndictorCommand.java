package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveULDFullIndictorCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("MAIL");

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering("SaveULDFullIndictorCommand", "execute");
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
		Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();
		LogonAttributes logonAttributes = getLogonAttribute();
		
		ResponseVO responseVO = new ResponseVO();
		ArrayList<ListContainerModel> results = new ArrayList<>();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ContainerVO containerVO = new ContainerVO();

		for (ContainerDetails containerDetails : selectedContainerData) {

			containerVO = MailOperationsModelConverter.constructContainerVO(containerDetails, logonAttributes);
			containerVO.setAssignedUser(containerDetails.getAssignedUser());
			containerVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
			containerVO.setTransactionCode(containerDetails.getTransactionCode());
		}

		try {
			mailTrackingDefaultsDelegate.markUnmarkUldIndicator(containerVO);

		} catch (BusinessDelegateException businessDelegateException) {
			 handleDelegateException(businessDelegateException);
		} 

		results.add(listContainerModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);

	}
}
