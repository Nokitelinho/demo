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
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry.SaveContentIDCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7929	:	04-Feb-2019	:	Draft
 */
public class SaveContentIDCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("Mail Operations");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";
	//private static final String SAVE_SUCCESS_MESSAGE = "mail.operations.searchContainer.saved";

	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		log.entering("SaveContentIDCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		ResponseVO responseVO = new ResponseVO();
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();

		if (listContainerModel != null && listContainerModel.getSelectedContainerData() != null) {

			Collection<ContainerDetails> containerActionData = listContainerModel.getSelectedContainerData();
			for (ContainerDetails containerVO : containerActionData) {
				containerVOs.add(MailOperationsModelConverter.constructContainerVO(containerVO, logonAttributes));
			}
			mailTrackingDefaultsDelegate.saveContentID(containerVOs);

		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}
		responseVO.setStatus("success");
		/*ErrorVO error = new ErrorVO(SAVE_SUCCESS_MESSAGE);
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		actionContext.addError(error);*/
		actionContext.setResponseVO(responseVO);

		log.exiting("SaveContentIDCommand", "execute");

	}

}
