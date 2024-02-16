/*
 * ScreenLoadListUldDiscrepancyCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 */
public class ScreenLoadListUldDiscrepancyCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENID = "uld.defaults.listulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private static final String DISCREPANCY_STATUS = "uld.defaults.discrepancyCode";
	private Log log = LogFactory 
			.getLogger("ScreenLoadListUldDiscrepancyCommand");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadListUldDiscrepancyCommand---------------->>>>",
				"Entering");
		ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		ListULDDiscrepanciesForm actionForm = 
			(ListULDDiscrepanciesForm) invocationContext.screenModel;
		ListUldDiscrepancySession session = 
			getScreenSession(MODULE, SCREENID);		
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		session.setOneTimeValues(oneTimeValues);
		session.removeULDDiscrepancyFilterVODetails();
		session.removeULDDiscrepancyVODetails();
		actionForm.setUldNo("");
		actionForm.setFlag("");
		//actionForm.setAirlineCode("");
		actionForm.setReportingStation("");
		actionForm.setOwnerStation("");
		
		
//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	log.log(Log.FINE, "logonAttributes.getCompanyCode()------->",
				logonAttributes.getCompanyCode());
		log.log(Log.FINE, "logonAttributes.getUserId()     ------->",
				logonAttributes.getUserId());
		//removed by nisha on 29Apr08
	   	//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471) ends
		ULDDiscrepancyFilterVO filterVO = new ULDDiscrepancyFilterVO();
		
			if(logonAttributes.isAirlineUser()){
	    		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    		actionForm.setDiscDisableStat("airline");
	    		actionForm.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    	}
	    	else{
	    		filterVO.setReportingStation(logonAttributes.getAirportCode());
	    		actionForm.setDiscDisableStat("GHA");
	    	}
    	
		//Added by Tarun for BUG_4647_03Dec08 ends
		
    	filterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	session.setULDDiscrepancyFilterVODetails(filterVO);
    	
		log.exiting("ScreenLoadListUldDiscrepancyCommand---------------->>>>",
				"Exiting");
		
		invocationContext.addAllError(error);
		invocationContext.target = SCREENLOAD_SUCCESS;
	}
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ListUldDiscrepancyCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */ 
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ListUldDiscrepancyCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	 private Collection<String> getOneTimeParameterTypes() {
	    	ArrayList<String> parameterTypes = new ArrayList<String>();    	
	      //DISCREPANCY_STATUS
	    	parameterTypes.add(DISCREPANCY_STATUS);  
	    	return parameterTypes;
	}

}
