/*
 * ScreenLoadFlownReportsCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.report.FlownReportsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.report.FlownReportsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2449
 *
 */
public class ScreenLoadFlownReportsCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA FLOWN");
	/**
	 * 
	 * class name
	 */
	private static final String CLASS_NAME = "ScreenloadFlownReportsCommand";
	/**
	 * 
	 * module name
	 */	
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	/**
	 * 
	 * screen id
	 */
	private static final String SCREENID = "mra.flown.flownreports";
	/**
	 * 
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	private static final String KEY_FLIGHTSTATUS = "flight.operation.flightstatus";
	
	
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		FlownReportsSession session =( FlownReportsSession)getScreenSession(
				MODULE_NAME, SCREENID);
		FlownReportsForm flownReportForm = (FlownReportsForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Map<String, Collection<OneTimeVO>>	oneTimeValues = null;
		Collection<ErrorVO> errors = null;

	    Collection<String> oneTimeList = new ArrayList<String>();
	    oneTimeList.add(KEY_FLIGHTSTATUS);
	   	    	   
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues( 
					companyCode, oneTimeList );
		}catch ( BusinessDelegateException businessDelegateException ) {
			handleDelegateException( businessDelegateException );
		}
		if ( oneTimeValues != null ) {
			session.setFlightStatus( 
				(ArrayList<OneTimeVO>) oneTimeValues.get(KEY_FLIGHTSTATUS));
		}
		if(flownReportForm.getComboFlag() == null ){
		flownReportForm.setReportId("ListOfFlightsWithFlightStatus");
		flownReportForm.setComboFlag("ListOfFlights");
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}


}
