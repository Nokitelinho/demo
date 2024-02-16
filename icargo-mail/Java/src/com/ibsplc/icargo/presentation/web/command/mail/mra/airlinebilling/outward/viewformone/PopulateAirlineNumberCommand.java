/*
 * PopulateAirlineNumberCommand.java Created on SEP-22-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.viewformone;

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
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm;




/**@author A-3447
 * 
 * Command Class for ViewFormOne Screen (Ajax implementation for getting airline no
 * from airline code)
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Sep 22, 2008 Muralee(a-3447) Initial draft
 * 
 */

public class PopulateAirlineNumberCommand extends BaseCommand {


// -------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING---OUTWARD");
	/**
	 * 
	 * Class name
	 */
	private static final String CLASS_NAME = "PopulateAirlineNumberCommand";

	
	/**
	 *SCREENID
	 * 
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
	/**
	 *MODULE
	 * 
	 */
	private static final String MODULE = "mra.airlinebilling";

	
	/**
	 * AIRLINE CODE INVALID
	 */

	private static final String INVALID_AIRLINE_CODE="mra.airlinebilling.outward.viewformone.msg.err.invalidairlinecode";
	
	
	
	/**
	 * Screen Sucess
	 */

	private static final String SCREEN_SUCCESS = "populate_success";
	
	/**
	 * 
	 * Failure 
	 */
	private static final String  SCREEN_FAILURE="populate_failed";
	
// ----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * @author a-3447 
	 * Execute method for populating airline no
	 * @param invocationContext
	 *     InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ViewMailFormOneForm viewMailFormOneForm = (ViewMailFormOneForm) invocationContext.screenModel;
		ViewFormOneSession session = (ViewFormOneSession) getScreenSession(
				MODULE, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();

		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<ErrorVO> arlerrors = new ArrayList<ErrorVO>();
		if (viewMailFormOneForm.getAirlineCode() != null
				&& viewMailFormOneForm.getAirlineCode().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), viewMailFormOneForm
								.getAirlineCode().toUpperCase());
				
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (airlineValidationVO != null) {
				log.log(Log.FINE, "airlineValidationVO-->>",
						airlineValidationVO);
				viewMailFormOneForm.setAirlineNumber(airlineValidationVO.getNumericCode());	
				log.log(Log.FINE, "AirlineNumber---->", viewMailFormOneForm.getAirlineNumber());
				invocationContext.target =SCREEN_SUCCESS;	
				
			}
			else {
				log.log(Log.FINE, "Error in Airline no");
				ErrorVO err = new ErrorVO(INVALID_AIRLINE_CODE);
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
			
