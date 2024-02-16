package com.ibsplc.icargo.presentation.web.command.addons.mail.operations.listflight;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.addons.mail.operations.AddonsMailDelegate;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.MailAwbBookingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListFlightScreenloadCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("ADDONSMAIL");

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("ListFlightPopupScreenloadCommand", "execute");
		MailAwbBookingModel mailAwbBookingModel = (MailAwbBookingModel) actionContext.getScreenModel();
		AddonsMailDelegate delegate = new AddonsMailDelegate();
		String companyCode = null;
		String shipmentPrefix = null;
		String masterDocumentNumber = null;
		Collection<MailbagVO> mailbagVOs = mailAwbBookingModel.getSelectedMailBagVOs();
		Collection<MailBookingDetailVO> mailBookingDetailVOs = null;
		Collection<MailbagVO> selectedMailBagVOs = new ArrayList<>();

		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				companyCode = mailbagVO.getCompanyCode();
				shipmentPrefix = mailbagVO.getShipmentPrefix();
				masterDocumentNumber = mailbagVO.getDocumentNumber();
				mailAwbBookingModel.setShipmentPrefix(shipmentPrefix);
				mailAwbBookingModel.setMasterDocumentNumber(masterDocumentNumber);
				selectedMailBagVOs.add(mailbagVO); 
			}
		}

		try {
			if ("LoadPlanView".equals(mailAwbBookingModel.getFligtTab())) {
				mailBookingDetailVOs = delegate.findFlightDetailsforAWB(companyCode, shipmentPrefix,
						masterDocumentNumber);  
			} else {
				mailBookingDetailVOs = delegate.fetchBookedFlightDetails(companyCode, shipmentPrefix,
						masterDocumentNumber);
			}

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}

		ResponseVO responseVO = new ResponseVO();

		ArrayList<MailAwbBookingModel> result = new ArrayList<>();
		mailAwbBookingModel.setSelectedMailBagVOs(selectedMailBagVOs);
		mailAwbBookingModel.setMailBookingDetailVOs(mailBookingDetailVOs);
		result.add(mailAwbBookingModel);
		responseVO.setResults(result);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		this.log.exiting("ListFlightPopupScreenloadCommand", "execute");
 
	}

}
