/*
 * PopulateAirlineNoCommand.java Created on SEP-5-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformone;

/**
 * @author a-3447
 */

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447 
 * Command Class for Capture Invoice (Ajax implementation for
 *   getting airline no from airline code
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Sep 5, 2008 Muralee(a-3447) Initial draft
 * 
 */

public class PopulateAirlineNoCommand extends BaseCommand {
	
//-------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Log
	 */
	private Log log = LogFactory.getLogger("MRA airlinebilling CLEARCOMMAND");

	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "PopulateAirlineNoCommand";

	/**
	 * Module
	 */

	private static final String MODULE = "mailtracking.mra.airlinebilling";

	/**
	 * SubModule
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureformone";

	/**
	 * Screen Sucess
	 */

	private static final String SCREEN_SUCCESS = "populate_success";

	/**
	 * 
	 * Failure
	 */
	private static final String SCREEN_FAILURE = "populate_failed";

	/**
	 * 
	 * Invalid Airline
	 * 
	 */

	private static final String ARLCOD_INVALID = "mailtracking.mra.airlinebilling.inward.captureformone.airlinecodeinvalid";

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		CaptureMailFormOneForm captureFormOneForm = (CaptureMailFormOneForm) invocationContext.screenModel;
		CaptureFormOneSession captureFormOneSession = getScreenSession(MODULE,
				SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();

		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		AirlineValidationVO airlineValidationVO = null;
		Collection<ErrorVO> arlerrors = new ArrayList<ErrorVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (captureFormOneForm.getAirlineCode() != null
				&& captureFormOneForm.getAirlineCode().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), captureFormOneForm
								.getAirlineCode().toUpperCase());

			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (airlineValidationVO != null) {
				log.log(Log.FINE, "airlineValidationVO-->>",
						airlineValidationVO);
				captureFormOneForm.setAirlineNo(airlineValidationVO
						.getNumericCode());
				log.log(Log.FINE, "captureInvoiceForm.getArlNo--->",
						captureFormOneForm.getAirlineNo());
				invocationContext.target = SCREEN_SUCCESS;

			} else {
				log.log(Log.FINE, "Error in Airline no");
				ErrorVO err = new ErrorVO(ARLCOD_INVALID);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				arlerrors.add(err);
				invocationContext.addAllError(arlerrors);
				invocationContext.target = SCREEN_FAILURE;
				return;
			}

			log.exiting(CLASS_NAME, "execute");
		}

	}
}
