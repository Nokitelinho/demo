/*
 * PopulateAirlineNoCommand.java Created on SEP-5-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.listformone;
/**
 * @author a-3434
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ListFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;




/**@author A-3434
 * Command Class for ListFormOne (Ajax implementation for getting airline no
 * from airline code
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 
 * 
 */

public class PopulateAirlineNoCommand extends BaseCommand {


// -------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING OUTWARD");
	/**
	 * 
	 * Class name
	 */
	private static final String CLASS_NAME = "PopulateAirlineNoCommand";

	/**
	 * Module name
	 */

	private static final String MODULE_NAME = "mra.airlinebilling";

	/**
	 * Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.outward.listform1";
	
	/**
	 * AIRLINE CODE INVALID
	 */
	private static final String ARLCOD_INVALID = "mra.airlinebilling.outward.listformone.msg.err.invalidairlinecode";
	
	
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
	 * @author a-3434 Execute method
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		ListMailFormOneForm listMailFormOneForm = (ListMailFormOneForm) invocationContext.screenModel;
		ListFormOneSession listFormOneSession=(ListFormOneSession)getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();

		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<ErrorVO> arlerrors = new ArrayList<ErrorVO>();
		
		if (listMailFormOneForm.getAirlineCodeFilterField()!= null
				&& listMailFormOneForm.getAirlineCodeFilterField().trim().length() > 0) {
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), listMailFormOneForm
								.getAirlineCodeFilterField().toUpperCase());
				
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (airlineValidationVO != null) {
				log.log(Log.FINE, "airlineValidationVO-->>",
						airlineValidationVO);
				listMailFormOneForm.setAirlineNumber(airlineValidationVO.getNumericCode());	
				log.log(Log.FINE, "AirlineNumber--->", listMailFormOneForm.getAirlineNumber());
					
			}
			else {
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
		invocationContext.target =SCREEN_SUCCESS;
		
	}
}
						
