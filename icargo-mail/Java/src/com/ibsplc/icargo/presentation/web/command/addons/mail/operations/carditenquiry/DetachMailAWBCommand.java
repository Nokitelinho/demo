package com.ibsplc.icargo.presentation.web.command.addons.mail.operations.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.addons.mail.operations.AddonsMailDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.AddonMailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;

public class DetachMailAWBCommand extends AbstractCommand {

	private static final String STATUS_DETACH = "AWB is either executed or accepted";
	private static final String STATUS_ACCEPTED = "Y";
	private static final String COMMAND_NAME = "DettachMailAWBCommand";
	private static final String MAIL_COUNT_LIMIT_SYSPAR = "mail.operations.mailcountlimitforawbhandling";
	

	private Log log = LogFactory.getLogger(COMMAND_NAME);
    private static final String STATUS_DETACH_AWBNUM = "Selected mail bag is not attached to any AWB";

	/**
	 * Overriding Method : @see
	 * com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * Added by : A-7531 on 08-Sep-2017 Used for : Parameters : @param arg0
	 * Parameters : @throws CommandInvocationException
	 */
	public void execute(ActionContext actionContext) throws CommandInvocationException {

		LogonAttributes logonAttributes = getLogonAttribute();
		CarditEnquiryModel carditEnquiryModel = (CarditEnquiryModel) actionContext.getScreenModel();
		AddonsMailDelegate delegate = new AddonsMailDelegate();
		Collection<ErrorVO> errors = new ArrayList<>();

		Collection<MailbagVO> selectedMailBagVO = new ArrayList<>();
		ResponseVO responseVO = new ResponseVO();

		Collection<Mailbag> selectedMailIds = carditEnquiryModel.getSelectedMailbags();
		CarditFilter carditFilter = carditEnquiryModel.getCarditFilter();
		if (selectedMailIds != null) {

			for (Mailbag selectedIds : selectedMailIds) {
				MailbagVO mailbagVO = new MailbagVO();
				mailbagVO.setAccepted(selectedIds.getAccepted());
				mailbagVO.setCompanyCode(selectedIds.getCompanyCode());
				mailbagVO.setMailbagId(selectedIds.getMailbagId());
	            mailbagVO.setShipmentPrefix(selectedIds.getShipmentPrefix());
				mailbagVO.setDocumentNumber(selectedIds.getMasterDocumentNumber());

				selectedMailBagVO.add(mailbagVO);
			}

		}

		if (!selectedMailBagVO.isEmpty()) {
			for (MailbagVO mailbagVO : selectedMailBagVO) {
				if (STATUS_ACCEPTED.equals(mailbagVO.getAccepted())) {
					ErrorVO error = new ErrorVO(STATUS_DETACH);
					errors.add(error);

					actionContext.addAllError((List<ErrorVO>) errors);

					log.exiting(COMMAND_NAME, "isacceptedcondition");
					return;
				}
	            if (mailbagVO.getShipmentPrefix()==null && mailbagVO.getDocumentNumber()==null ) {
					ErrorVO error = new ErrorVO(STATUS_DETACH_AWBNUM);
					errors.add(error);

					actionContext.addAllError((List<ErrorVO>) errors);

					log.exiting(COMMAND_NAME, "Empty AWB Number condition");
					return;
				}
			}
		}

		CarditEnquiryFilterVO carditEnquiryFilterVO = null;
         findMailCount(carditEnquiryModel);

		AddonMailOperationsModelConverter modelConverter = new AddonMailOperationsModelConverter();
		carditEnquiryFilterVO = modelConverter.constructCarditFilterVO(carditFilter, logonAttributes);

		dettachMailBookingDetails(actionContext, carditEnquiryModel, delegate, errors, selectedMailBagVO, responseVO,
				carditEnquiryFilterVO);

	}
	private void findMailCount(CarditEnquiryModel carditEnquiryModel) { 
		Collection<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(MAIL_COUNT_LIMIT_SYSPAR);
		Map<String, String> result = null;
		String mailCount = "0";
		try {
			result = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		if (result != null && !result.isEmpty()) {
			mailCount = result.get(MAIL_COUNT_LIMIT_SYSPAR);
		}
		carditEnquiryModel.getCarditFilter().setMailCount(mailCount);
        }

	private void dettachMailBookingDetails(ActionContext actionContext, CarditEnquiryModel carditEnquiryModel,
			AddonsMailDelegate delegate, Collection<ErrorVO> errors, Collection<MailbagVO> selectedMailBagVO,
			ResponseVO responseVO, CarditEnquiryFilterVO carditEnquiryFilterVO) {
		try {
			delegate.dettachMailBookingDetails(selectedMailBagVO, carditEnquiryFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}

		if (errors != null) {
			for (ErrorVO err : errors) {
				if ("mailtracking.defaults.searchconsignment.selectedmailcountexceeded"
						.equalsIgnoreCase(err.getErrorCode())) {
					Object[] obj = { carditEnquiryFilterVO.getMailCount() };
					ErrorVO error = new ErrorVO(
							"addons.mail.operations.searchconsignment.detach.selectedmailcountexceeded", obj);
					errors.add(error);
					actionContext.addError(error);
					return;
				} else {
					actionContext.addError(err);
					return;
				}
			}

			log.exiting(COMMAND_NAME, "execute");
		}

		ArrayList<CarditEnquiryModel> result = new ArrayList<>();
		result.add(carditEnquiryModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
	}

}
