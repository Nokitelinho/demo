
package com.ibsplc.icargo.presentation.web.command.addons.mail.operations.listflight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.addons.mail.operations.AddonsMailDelegate;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.MailAwbBookingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class BookedFlightsPopupOkCommand extends AbstractCommand {
	
	private static final String CLASS = "BookedFlightsPopupOkCommand";
	private Log LOGGER = LogFactory.getLogger(CLASS);
	private static final String STATUS_SAME_ROUTE = "Please select only one flight for one segment";

	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOGGER.entering(CLASS, "execute");
		MailAwbBookingModel mailAwbBookingModel = (MailAwbBookingModel) actionContext.getScreenModel();
		AddonsMailDelegate delegate = new AddonsMailDelegate();
		List<ErrorVO> errors = new ArrayList<>();
		Collection<MailbagVO> selectedMailBagVO;
		selectedMailBagVO = mailAwbBookingModel.getSelectedMailBagVOs();
		Collection<MailBookingDetailVO> selectedMailBookingDetailVOs = findSelectedFlights(mailAwbBookingModel);
		String tmpOrigin = "";
		String tmpDestination = "";
		for (MailBookingDetailVO mailBookingDetailVOtoValidate : selectedMailBookingDetailVOs) {
			if ("".equals(tmpOrigin)) {
				tmpOrigin = mailBookingDetailVOtoValidate.getOrigin();
				tmpDestination = mailBookingDetailVOtoValidate.getDestination();
			} else {
				if (tmpOrigin.equals(mailBookingDetailVOtoValidate.getOrigin())
						|| tmpDestination.equals(mailBookingDetailVOtoValidate.getDestination())) {
					ErrorVO errorVO = new ErrorVO(STATUS_SAME_ROUTE);
					errors.add(errorVO);
					actionContext.addAllError(errors);
					return;

				} else {
					tmpOrigin = mailBookingDetailVOtoValidate.getOrigin();
					tmpDestination = mailBookingDetailVOtoValidate.getDestination();
				}
			}

		}
		try {
			delegate.saveMailBookingFlightDetails(selectedMailBagVO, selectedMailBookingDetailVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
		if (!errors.isEmpty()) {
			actionContext.addAllError(errors);
			return;
		} else {
			ResponseVO responseVO = new ResponseVO();
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
		}

		LOGGER.exiting(CLASS, "execute");

	}
	
	private Collection<MailBookingDetailVO> findSelectedFlights(MailAwbBookingModel mailAwbBookingModel){
		Collection<MailBookingDetailVO> mailBookingDetailVOs = mailAwbBookingModel.getMailBookingDetailVOs();
		Collection<MailBookingDetailVO> selectedMailBookingDetailVOs = new ArrayList<>();
		String[] selectedRows = mailAwbBookingModel.getBookingFlightNumber().split(",");
		int size = selectedRows.length;
		int row = 0;
		for (MailBookingDetailVO mailBookingDetailVOTemp : mailBookingDetailVOs) {
			for (int j = 0; j < size; j++) {
				if (row == Integer.parseInt(selectedRows[j])) {
					selectedMailBookingDetailVOs.add(mailBookingDetailVOTemp);
				}
			}
			row++;
		}
		return selectedMailBookingDetailVOs;
	}

}
