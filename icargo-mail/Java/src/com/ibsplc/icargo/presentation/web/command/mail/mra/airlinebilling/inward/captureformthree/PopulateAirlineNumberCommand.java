/*
 * PopulateAirlineNumberCommand.java Created on SEP-5-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureformthree;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureFormThreeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447 Command Class for Capture Invoice (Ajax implementation for
 *         getting airline no from airline code
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Sep 5, 2008 Muralee(a-3447) Initial draft
 * 
 */

public class PopulateAirlineNumberCommand extends BaseCommand {

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Log
	 */
	private Log log = LogFactory
			.getLogger("CaptureForm3 PopulateAirlineNumberCommand");

	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "PopulateAirlineNumberCommand";

	/**
	 * Module Name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureformthree";

	/**
	 * AIRLINE CODE INVALID
	 */
	private static final String ARLCOD_INVALID = "mailtracking.mra.airlinebilling.inward.captureinvoice.airlinecodeinvalid";

	/**
	 * Screen Sucess
	 */

	private static final String SCREEN_SUCCESS = "populate_success";

	/**
	 * 
	 * Failure
	 */
	private static final String SCREEN_FAILURE = "populate_failed";

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author a-3447 Execute method
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		CaptureMailFormThreeForm captureFormThreeForm = (CaptureMailFormThreeForm) invocationContext.screenModel;
		CaptureFormThreeSession captureFormThreeSession = (CaptureFormThreeSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();

		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		int counter = Integer.parseInt(captureFormThreeForm.getRowCounter());
		Collection<ErrorVO> arlerrors = new ArrayList<ErrorVO>();
		String[] airlineCode = captureFormThreeForm.getAirlineCode();
		
		
		if (captureFormThreeForm.getAirlineCode() != null
				&& captureFormThreeForm.getAirlineCode().length > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), airlineCode[counter]
								.toUpperCase());

			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (airlineValidationVO != null) {

				
				log.log(Log.FINE, "airlineValidationVO-->>",
						airlineValidationVO);
				String numericCode = airlineValidationVO.getNumericCode();
				log.log(Log.FINE, "<----NUMERIC CODE IS-->>", numericCode);
				captureFormThreeSession.setAirlineNumbericCode(numericCode);
				log.log(Log.FINE, "<----IN SESSION-->>",
						captureFormThreeSession.getAirlineNumbericCode());
				

			} else {
				log.log(Log.FINE, "Error in Airline no");
				ErrorVO err = new ErrorVO(ARLCOD_INVALID);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				arlerrors.add(err);
				invocationContext.addAllError(arlerrors);
				invocationContext.target = SCREEN_FAILURE;
				return;
			}
			invocationContext.target = SCREEN_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
		}

	}
}
