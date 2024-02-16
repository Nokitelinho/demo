/*
 * CreateCommand.java Created on Dec 19, 2005
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
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainUldDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class CreateCommand extends BaseCommand {

	private static final String SCREENID = "uld.defaults.maintainulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private Log log = LogFactory.getLogger("CreateCommand");

	private static final String CREATE_SUCCESS = "create_success";

	private static final String PAGE_URL = "fromScmUldReconcile";

	private static final String DISCREPANCYCODE = "uld.defaults.discrepancyCode";

	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";

	private static final String BLANK = "";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("CreateCommand", "ENTRY");
		log.log(Log.FINE, "ULDDiscrepancyScreenloadadadadasd Command");
		MaintainUldDiscrepanciesForm form = (MaintainUldDiscrepanciesForm) invocationContext.screenModel;
		MaintainUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		log.log(Log.FINE, "form.getflag()", form.getFlag());
		log.log(Log.FINE, "form.getCloseFlag()", session.getCloseFlag());
		if(BLANK.equals(form.getFlag()) && BLANK.equals(session.getCloseFlag())){
			session.setULDDiscrepancyFilterVODetails(null);
		}
		//Commented by Sreekumar S as a part of AirNZ CR434
		/*Collection<OneTimeVO> discrepancyCode = findScreenLoadDetails(logonAttributes
				.getCompanyCode());*/
		//New oneTime values -  Added by Sreekumar S as a part of AirNZ CR434
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		session.setOneTimeValues(oneTimeValues);
		form.setSaveOpFlag("I");
		if (session.getPageURL() != null
				&& PAGE_URL.equals(session.getPageURL())) {
			log.log(Log.FINE, "\n\n\nFrom scm reconcile screen");
			ULDSCMReconcileDetailsVO scmDetailsVO = session
					.getSCMULDReconcileDetailsVO();

			form.setUldNoChild(scmDetailsVO.getUldNumber());
			//form.setFlag("createmode");
			form.setPageURL(session.getPageURL());

		}
//		Commented by Sreekumar S as a part of AirNZ CR434
/*		if (discrepancyCode != null) {
			session.setDiscrepancyCode(discrepancyCode);
		}*/
		form.setRemarks("");
		form.setSaveStatusFlag("Y");
		form.setSaveStatusPopup("completeSave");
		log.log(Log.FINE, "form.getflag()", form.getFlag());
		if (form.getUldNo() != null && form.getUldNo().trim().length() > 0) {
			form.setUldNoChild(form.getUldNo());
			form.setFilterStatus("uldno");
		}
		if (form.getReportingStation() != null
				&& form.getReportingStation().trim().length() > 0) {
			form.setReportingStationChild(form.getReportingStation());
			form.setFilterStatus("reportingstation");
		}
		if (form.getUldNo() != null && form.getUldNo().trim().length() > 0
				&& form.getReportingStation() != null
				&& form.getReportingStation().trim().length() > 0) {
			form.setUldNoChild(form.getUldNo());
			form.setReportingStationChild(form.getReportingStation());
			form.setFilterStatus("uldnoreportingstation");
		}

        ULDDiscrepancyFilterVO filterVO = new ULDDiscrepancyFilterVO();
        filterVO.setCompanyCode(logonAttributes.getCompanyCode());

        //Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
        // Commented by Manaf for INT UlD510
		//Collection<ErrorVO> error = new ArrayList<ErrorVO>();

    	//removed by nisha on 29Apr08
			if(logonAttributes.isAirlineUser()){
	    		form.setDiscDisableStat("airline");
	    		form.setAirlineCode(logonAttributes.getOwnAirlineCode());
	    	}
	    	else{
	    		//filterVO.setReportingStation(logonAttributes.getAirportCode());
	    		form.setDiscDisableStat("GHA");
	    		log.log(Log.INFO,
						"session.getULDDiscrepancyFilterVODetails()---->>",
						session.getULDDiscrepancyFilterVODetails());
				if (session.getULDDiscrepancyFilterVODetails() != null) {
					session.getULDDiscrepancyFilterVODetails()
							.setReportingStation(
									logonAttributes.getAirportCode());
				} else {
					log.log(Log.INFO,"session filter vo is null");
					ULDDiscrepancyFilterVO vo = new ULDDiscrepancyFilterVO();
					vo.setReportingStation(logonAttributes.getAirportCode());
					session.setULDDiscrepancyFilterVODetails(vo);
					log.log(Log.INFO,
							"session.getULDDiscrepancyFilterVODetails", session.getULDDiscrepancyFilterVODetails());
				}
		    	//Added by Preet for ULD 339--ends
	    	}

		//Added by Tarun for BUG_4647_03Dec08 ends

		log.exiting("CreateCommand", "EXIT");
		invocationContext.target = CREATE_SUCCESS;

	}

	/**
	 * Function to get one time values
	 *
	 * @param companyCode
	 * @return
	 *//*
	private Collection<OneTimeVO> findScreenLoadDetails(){
		Collection<String> oneTimeList = new ArrayList<String>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		try {
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			oneTimeList.add(DISCREPANCYCODE);
			oneTimeList.add(FACILITY_TYPE);
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException businessDelegateException) {
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
