/*
 * ListCommand.java Created on Jan 22, 2007
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
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author a-2270
 *
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";

	private static final String LISTDETAILS_SUCCESS = "screenload_success";                      

	private static final String LISTDETAILS_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.defaults.viewupurate.noresultsfound";

	private static final String STATUS_ONETIME = "mra.gpabilling.ratestatus";

	private static final String INVK_SCRN = "listupuratecard";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("MRA_GPABILLING");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ListUPURateLineForm form = (ListUPURateLineForm)invocationContext.screenModel;

		ListUPURateLineSession session = (ListUPURateLineSession)getScreenSession(MODULE, SCREENID);

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Page<RateLineVO> rateLineVos = null;
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		RateLineFilterVO filterVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		log.log(Log.INFO, "form.getInvokingScreen()-->", form.getInvokingScreen());
		log.log(Log.INFO, "session.getRateLineFilterVO()-->", session.getRateLineFilterVO());
		if(INVK_SCRN.equals(form.getInvokingScreen())){

			session.removeRateLineVOs();
			session.removeSelectedRateLineVOs();
			filterVO = session.getRateLineFilterVO();
			populateFormFields(form, filterVO);

			Map<String, Collection<OneTimeVO>> oneTimes =
				fetchOneTimeDetails(companyCode) == null ? null : fetchOneTimeDetails(companyCode);
			session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimes);
			log.log(Log.FINE, "oneTimes--> ", oneTimes);

		}else{
			session.removeRateLineVOs();
			//session.removeAllAttributes();
			filterVO = populateFilterVO(form);
			filterVO.setCompanyCode(companyCode);
		}


		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		log.log(Log.INFO, "FilterVo to Delegate-->", filterVO);
		/*
		 * calling MailTrackingMRADelegate
		 */
		try {
			log.log(Log.FINE,
			"Inside try : Calling findRateLineDetails");
			rateLineVos = delegate.findRateLineDetails(filterVO);
			log.log(Log.FINE, "rateLineVos from Server:--> ", rateLineVos);

//			log.log(Log.FINE, "rateid:--> "
//					+ rateLineVos.iterator().next().getRateCardID());
//

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught");
		}

		// for setting the invocation context in case of success
		if (rateLineVos !=null&&rateLineVos.size()>0
				|| rateLineVos !=null && rateLineVos.size()>0) {
			//rateLineVos.setTotalRecordCount(rateLineVos.size());
			LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			for(RateLineVO ratelineVO:rateLineVos){
				if(ratelineVO.getValidityEndDate().before(currentDate)){
					ratelineVO.setRatelineStatus("E");  
				}
			}
			session.setRateLineVOs(rateLineVos);

			invocationContext.target = LISTDETAILS_SUCCESS;
		}
		// for setting the invouaction context in case of failure
		if(rateLineVos ==null || rateLineVos.size()==0
				&& rateLineVos ==null || rateLineVos.size()==0){
			log.log(Log.FINE, "results from Server is ::null::");
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			invocationContext.target = LISTDETAILS_FAILURE;
		}
		else{
			//form.setSaveBtnStatus("X");
		}
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		form.setChangeStatusFlag("");
		form.setNewStatus("");
		//log.log(Log.FINE, "form.setSaveBtnStatus"+form.getSaveBtnStatus());
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 *
	 * @param form
	 * @return
	 */
	private RateLineFilterVO populateFilterVO(ListUPURateLineForm form){

		log.entering(CLASS_NAME, "PopulateFilterVO");
		RateLineFilterVO filterVO = new RateLineFilterVO();

		if(form.getOrigin()!=null&&
				form.getOrigin().trim().length()>0){
			filterVO.setOrigin(form.getOrigin().toUpperCase());
		}
		if(form.getDestination()!=null&&
				form.getDestination().trim().length()>0){
			filterVO.setDestination(form.getDestination().toUpperCase());
		}
		if(form.getFromDate()!=null && form.getFromDate().trim().length()>0){
			LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			LocalDate fromDate=localDate.setDate(form.getFromDate().trim());
			filterVO.setStartDate(fromDate);
		}
		if(form.getToDate()!=null && form.getToDate().trim().length()>0){
			LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			LocalDate toDate=localDate.setDate(form.getToDate().trim());
			filterVO.setEndDate(toDate);
		}
		if(form.getStatus()!=null&&
				form.getStatus().trim().length()>0){
			filterVO.setRatelineStatus(form.getStatus().toUpperCase());
		}
		if(form.getRateCardID()!=null && form.getRateCardID().trim().length()>0){
			filterVO.setRateCardID(form.getRateCardID().toUpperCase());
		}
		if(form.getOrgDstLevel() != null && form.getOrgDstLevel().trim().length() >0){
			filterVO.setOrgDstLevel(form.getOrgDstLevel());
		}
		log.exiting(CLASS_NAME, "PopulateFilterVO");
		return filterVO;

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
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(STATUS_ONETIME);

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
	 *
	 * @param filterVO
	 * @param form
	 * @return
	 */
	private ListUPURateLineForm populateFormFields(
			ListUPURateLineForm form, RateLineFilterVO filterVO){

		log.entering(CLASS_NAME, "PopulateFormFields");

		if(filterVO != null && form != null){

			form.setRateCardID(filterVO.getRateCardID());
			form.setFromDate(checkValue(filterVO.getStartDate()));
			form.setToDate(checkValue(filterVO.getEndDate()));
			form.setOrigin(filterVO.getOrigin());
			form.setDestination(filterVO.getDestination());
			form.setStatus(filterVO.getRatelineStatus());
		}

		log.exiting(CLASS_NAME, "populateFormFields");

		return form;
	}

	/**
	 * Checks obj, and converts it to suitable string for display in form
	 * @param obj
	 * @return
	 */
	private String checkValue(LocalDate obj){

		log.entering(CLASS_NAME, "checkValue");

		String returnStr = BLANK;

		if(obj != null){
			returnStr = obj.toDisplayFormat();
		}

		log.log(Log.FINE, "returnStr", returnStr);
		return returnStr;
	}
}


