/*
 * ViewCommand.java Created on Aug 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.listformone;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ViewCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");	
	private static final String DETAILS_SUCCESS = "details_success";	
	private static final String MODULE = "mra.airlinebilling";
	//private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";	
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
	private static final String BILLING_TYPE ="O";
	
	private static final String FROM_LISTFORMONE = "fromListFormOne";
	private static final String EMPTY = "";	
	private static final String CLASS_NAME = "ViewCommand";
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
					
		ListMailFormOneForm listFormOneForm = (ListMailFormOneForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ViewFormOneSession  viewFormOneSession=
			(ViewFormOneSession)getScreenSession(MODULE, SCREENID);
		
		String companyCode = logonAttributes.getCompanyCode();
		String clearancePeriod = listFormOneForm.getClearancePeriod().trim().toUpperCase();
		String airlineNumber = listFormOneForm.getAirlineNumber().trim().toUpperCase(); 
		String[] selectCheckBox = listFormOneForm.getSelectCheckBox();
		String select = listFormOneForm.getSelect();
		log.log(Log.FINE, "select", select);
		//String[] classType = listFormOneForm.getClassType();
		String[] airlineCode = listFormOneForm.getAirlineCode();
		
		InterlineFilterVO interlineFilterVo = new InterlineFilterVO();
		interlineFilterVo.setCompanyCode(companyCode);
		interlineFilterVo.setClearancePeriod(clearancePeriod);
		log.log(Log.FINE, "airlineCode", airlineCode, Integer.parseInt(select));
		interlineFilterVo.setAirlineCode(airlineCode[Integer.parseInt(select)].toUpperCase());	
		//interlineFilterVo.setInterlineBillingType(BILLING_TYPE);
		
				
		if(airlineNumber != null && !EMPTY.equals(airlineNumber)){
			interlineFilterVo.setAirlineNumber(airlineNumber);
		}
		log.log(Log.FINE, "interlinefiltervo...>", interlineFilterVo);
		viewFormOneSession.setCloseStatus(FROM_LISTFORMONE);
		viewFormOneSession.setClrperiod(interlineFilterVo.getClearancePeriod());
		viewFormOneSession.setAirlineCode((interlineFilterVo.getAirlineCode()));
		
		log.log(Log.FINE, "interlinefiltervo.from session..",
				viewFormOneSession.getInterlineFilterVO());
		log.log(Log.FINE, "interlinefiltervo.from session..",
				viewFormOneSession.getCloseStatus());
		log.exiting(CLASS_NAME,"execute");
    	invocationContext.target=DETAILS_SUCCESS;

	}

}
