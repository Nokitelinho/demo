package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.containerenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
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
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;


public class CloseFlightCommand extends AbstractCommand {

	
	private static final String FLIGHT_CLOSED_ERR = "mailtracking.defaults.err.flightclosederr";
	private static final String COMMAND_NAME="MAIL OPERATIONS CloseFlightCommand";
	private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);
	
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		ListContainerModel listContainerModel = (ListContainerModel) actionContext.getScreenModel();

		 LOGGER.info("entering into" + COMMAND_NAME + "class");   
		List<ErrorVO> errorVOs = null;
		ResponseVO responseVO = new ResponseVO();
		List<ListContainerModel> results = new ArrayList<>();
		Collection<ContainerDetails> selectedContainerData = listContainerModel.getSelectedContainerData();
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();

		for (ContainerDetails containerData : selectedContainerData) {
			OperationalFlightVO operationalFlightVO = MailOperationsModelConverter
					.constructOperationalFlightVO(containerData);

			boolean isFlightClosed = false;
			try {
				isFlightClosed = delegate.isFlightClosedForMailOperations(operationalFlightVO).booleanValue();
			} catch (BusinessDelegateException businessDelegateException) {
				errorVOs = handleDelegateException(businessDelegateException);
			}
			if ((errorVOs != null) && (!errorVOs.isEmpty())) {
				return;
			}
			if (isFlightClosed) {

				Object[] obj = { operationalFlightVO.getCarrierCode() + " " + operationalFlightVO.getFlightNumber(),
						(operationalFlightVO.getFlightDate() != null
								? operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat() : "") };
				actionContext.addError(new ErrorVO(FLIGHT_CLOSED_ERR, obj));

				return;
			}

			results.add(listContainerModel);
			responseVO.setResults(results);
			actionContext.setResponseVO(responseVO);
		}
	}
}
