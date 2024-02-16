/*
 * ScreenLoadCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateline;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

    private static final String SCREENID ="mailtracking.mra.defaults.viewupurate";

	private static final String SCREENLOADETAILS_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String STATUS_ONETIME = "mra.gpabilling.ratestatus";
	
	private static final String STATUS_LEVEL = "mail.mra.ratecar.orgdstlevel";

	private static final String ON_SCREENLOAD = "OSL";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException{

		Log log = LogFactory.getLogger("MRA_DEFAULTS");
		log.entering(CLASS_NAME, "execute");

		ListUPURateLineForm   listUPURateLineForm = (ListUPURateLineForm)invocationContext.screenModel;

		ListUPURateLineSession listUPURateLineSession  =
			                (ListUPURateLineSession)getScreenSession(MODULE_NAME, SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		log.log(Log.FINE, companyCode);
		Map<String, Collection<OneTimeVO>>oneTimeValues = fetchOneTimeDetails(companyCode);
		listUPURateLineSession = updateOneTimeInSession(listUPURateLineSession,oneTimeValues);
		//log.log(log.FINE,"session.getStatusOneTime()"+ listUPURateLineSession.getStatusOneTime());
		log.log(Log.INFO, "Over");
		listUPURateLineForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		listUPURateLineSession.setRateLineVOs(null);
		
		populateForm(listUPURateLineForm,listUPURateLineSession.getRateLineFilterVO());
		
		//listUPURateLineForm.setStatus("N");
		listUPURateLineForm.setNewStatus(ON_SCREENLOAD);
		invocationContext.target = SCREENLOADETAILS_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

    /**
	 * Helper method to get the one time data.
	 * @param companyCode String
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {

		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =
					new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = buildOneTimeList();
			try {
					SharedDefaultsDelegate sharedDefaultsDelegate =
												new SharedDefaultsDelegate();
					hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
			    handleDelegateException(businessDelegateException);
			}

			log.exiting(CLASS_NAME, "fetchOneTimeDetails");
			return hashMap;
	}
	/**
	 * Helper method to build the list of required one time values.
	 *
	 * @return Collection<String>
	 */
	private Collection<String> buildOneTimeList() {

		log.entering(CLASS_NAME, "buildOneTimeList");
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(STATUS_ONETIME);
		//oneTimeList.add(STATUS_LEVEL);
		log.exiting(CLASS_NAME, "buildOneTimeList");
		return oneTimeList;
	}

	/**
	 * Method to update the one time values in session.
	 *
	 * @param session
	 * @param hashMap
	 * @return MaintainFlightScheduleSessionInterface
	 */

	private ListUPURateLineSession updateOneTimeInSession(
			ListUPURateLineSession session,
					Map<String, Collection<OneTimeVO>> hashMap) {

			log.entering(CLASS_NAME, "updateOneTimeInSession");
			session.setStatusOneTime((ArrayList<OneTimeVO>) hashMap.get(STATUS_ONETIME));
			session.setOneTimeVOs(
					(HashMap<String, Collection<OneTimeVO>>) hashMap );

			log.exiting(CLASS_NAME, "updateOneTimeInSession");
			return session;
	}
	
	private  void populateForm (ListUPURateLineForm form,RateLineFilterVO filterVo){
		log.entering(CLASS_NAME, "populateForm");
		if(filterVo!=null){
			if(form.getFromDate()!=null){
			form.setFromDate(checkValue(filterVo.getStartDate()));
			}
			if(form.getToDate()!=null){
			form.setToDate(checkValue(filterVo.getEndDate()));
			}
			if(form.getStatus()!=null){
			form.setStatus(filterVo.getRatelineStatus());
			}
			if (form.getOrigin()!=null){
			form.setOrigin(filterVo.getOrigin());
			}
			if(form.getDestination()!=null){
			form.setDestination(filterVo.getDestination());
			}
			if(form.getRateCardID()!=null){
			form.setRateCardID(filterVo.getRateCardID());
			}
			}
		log.exiting(CLASS_NAME, "populateForm");
		
		
	}
	
	/**
	 * Checks obj, and converts it to suitable string for display in form
	 * @param obj
	 * @return
	 */
	private String checkValue(LocalDate obj){

		log.entering(CLASS_NAME, "checkValue");

		String returnStr = "";

		if(obj != null){
			returnStr = obj.toDisplayFormat();
		}

		log.log(Log.FINE, "returnStr", returnStr);
		return returnStr;
	}






}

