/*
 * ScreenLoadULDDiscrepancyCommand.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

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
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the ScreenLoadULDDiscrepancyCommand popup screen
 * 
 * @author A-2052
 */
public class ScreenLoadULDDiscrepancyCommand extends BaseCommand {
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("ScreenLoadULDDiscrepancyCommand");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREEN_ID = "uld.defaults.listulddiscrepancies";
	private static final String DISCREPANCYCODE =
		"uld.defaults.discrepancyCode";
	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";
    
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ScreenLoadULDDiscrepancyCommand","execute");
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	// Commented by Manaf for INT ULD510

		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
     	ListUldDiscrepancySession session = getScreenSession(MODULE, SCREEN_ID);
     	ULDDiscrepancyFilterVO uldDiscrepancyVO = new ULDDiscrepancyFilterVO();
/*     	Collection<OneTimeVO> discrepancyCode = 
			findScreenLoadDetails(logonAttributes
				.getCompanyCode());*/
/*		if (discrepancyCode != null) { 
			session.setDiscrepancyCode(discrepancyCode);
		}*/
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		session.setOneTimeValues(oneTimeValues);
		String uldNumber = null;
    	if(listULDForm.getUldNumber()!=null && listULDForm.getUldNumber().trim().length()>0){
    		uldNumber = listULDForm.getUldNumber().toUpperCase();
    	}

    	uldDiscrepancyVO.setUldNumber(uldNumber);
    	//added by a-3045 for bug 13872 starts
    	uldDiscrepancyVO.setCompanyCode(logonAttributes.getCompanyCode());
    	uldDiscrepancyVO.setPageNumber(1);
    	//added by a-3045 for bug 13872 ends
    	session.setULDDiscrepancyFilterVODetails(uldDiscrepancyVO);
    	session.setPageURL("fromlistuldscreen");
    	session.setCloseFlag("fromlistuldscreen");
    	log.log(Log.FINE,
				"session.setULDDiscrepancyFilterVODetails--------->>>", session.getULDDiscrepancyFilterVODetails());
		log.exiting("ScreenLoadULDDiscrepancyCommand", "EXIT");
		invocationContext.target = SCREENLOAD_SUCCESS;
	}


/*	*//**
	 * Function to get one time values
	 * 
	 * @param companyCode
	 * @return
	 *//*
	private Collection<OneTimeVO> findScreenLoadDetails(String companyCode) {
		Collection<String> oneTimeList = new ArrayList<String>();
		Map<String, Collection<OneTimeVO>> hashMap = 
			new HashMap<String, Collection<OneTimeVO>>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			SharedDefaultsDelegate sharedDefaultsDelegate =
				new SharedDefaultsDelegate();
			oneTimeList.add(DISCREPANCYCODE);
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
		}
		return hashMap.get(DISCREPANCYCODE);
	}*/
	/**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("CreateCommand","getOneTimeValues");
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
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("CreateCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("CreateCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(DISCREPANCYCODE);
    	parameterTypes.add(FACILITY_TYPE);
    	log.exiting("CreateCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}
