/*
 * SaveCommand.java Created on Feb 21, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 * 
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory
			.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";
	private static final String  ZERO="0.00";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form = (CaptureCN51Form) invocationContext.screenModel;
		if (invocationContext.getErrors() == null
				|| invocationContext.getErrors().size() == 0) {
			Collection<ErrorVO> errors = null;
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			AirlineCN51SummaryVO airlineCN51SummaryVO = captureCN51Session
					.getCn51Details();
			if (airlineCN51SummaryVO.getCn51DetailsPageVOs()== null
					|| airlineCN51SummaryVO.getCn51DetailsPageVOs().size() == 0) {
				airlineCN51SummaryVO
						.setOperationFlag(AirlineCN51SummaryVO.OPERATION_FLAG_DELETE);
			}
			log.log(Log.INFO,
					"airlineCN51SummaryVO to server for saving--->>>",
					airlineCN51SummaryVO);
			try {
				mailTrackingMRADelegate.saveCN51(airlineCN51SummaryVO);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if(errors == null || errors.size() == 0) {
				captureCN51Form.setInvoiceRefNo("");
				captureCN51Form.setClearancePeriod("");
				captureCN51Form.setBillingType("");
				captureCN51Form.setAirlineCode("");
				captureCN51Form.setCategory("");
				captureCN51Form.setCn51Period("");
				captureCN51Form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				captureCN51Form.setLinkStatusFlag("disable");
				captureCN51Form.setCarriageFrom("");
				captureCN51Form.setCarriageTo("");
				/**
				 * 
				 * @author a-3447 for Bug 29672
				 */
				captureCN51Form.setNetCharge(ZERO);						
				captureCN51Form.setNetWeight(0.00);
				captureCN51Form.setNetCP(0.00);
				captureCN51Form.setNetLC(0.00);
				captureCN51Form.setNetUld(0.00);
				captureCN51Form.setNetSal(0.00);
				captureCN51Form.setNetSV(0.00);
				captureCN51Form.setNetEMS(0.00);
				/**
				 * 
				 * @author a-3447 for  Bug-29672
				 */
				captureCN51Session.setFilterDetails(null);
				captureCN51Session.setCn51Details(null);
				captureCN51Form.setScreenFlg("");
				errors = new ArrayList<ErrorVO>();
				ErrorVO error = new ErrorVO("mra.airlinebilling.defaults.capturecn51.msg.err.savedsuccessfully");
				errors.add(error);
				invocationContext.addAllError(errors);
			}else {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
			}
			invocationContext.target = SAVE_SUCCESS;
		} else {
			invocationContext.target = SAVE_FAILURE;
		}
		log.exiting(CLASS_NAME, "execute");

	}

}
