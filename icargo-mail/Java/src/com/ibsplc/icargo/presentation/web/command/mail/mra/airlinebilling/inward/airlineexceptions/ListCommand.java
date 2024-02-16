/*
 * ListCommand.java Created on Feb 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * Command class for Listing of airlineexceptions screen.
 *
 * Revision History
 *
 * Version      Date           Author        Description
 *
 *  0.1 	 Feb 19, 2007	 Shivjith    	Initial draft		
 *
 */
public class ListCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	
	private static final String CLASS_NAME = "ListCommand";
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.airlineexceptions";
	
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String ERROR_KEY_NO_RESULTS = 
		"mra.airlinebilling.defaults.airlineexceptions.noresultsfound";
	
	private static final String ERROR_KEY_DATE = 
		"mra.airlinebilling.defaults.airlineexceptions.nodatefields";
	
	private static final String ERROR_KEY_AIRLINE = 
		"mra.airlinebilling.defaults.airlineexceptions.noairline";
	
	private static final String EXCEPTION_CODES = 
		"mailtracking.mra.gpareporting.exceptioncodes";
	
	private static final String ERROR_KEY_NO_INVALID_DATE = 
		"mra.airlinebilling.defaults.airlineexceptions.notvaliddate";
	
	private static final String BLANK = "";
	
	private static final String FROM_SCREEN_CN66 = "cn66screen";
	
	private static final String FROM_SCREEN_INVEXP = "invoiceexceptions";
	
	private static final String FROM_REJECTIONMEMO = "RejectionMemo";
	
	private static final String AFTER_ACCEPT = "afteraccept";
	//Added for bug ICRD-100936 by A-5526 starts
	/**
	 * TO_INVOICEEXCEPTION is for identifying the screen flow.
	 */
	private static final String TO_INVOICEEXCEPTION = "toInvoiceException";
	
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
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		
		AirlineExceptionsForm airlineExceptionsForm = 
			(AirlineExceptionsForm)invocationContext.screenModel;
		
		AirlineExceptionsSession airLineExceptionsSession = 
			(AirlineExceptionsSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		airLineExceptionsSession.removeAirlineExceptionsVOs();
		
		Page<AirlineExceptionsVO> exceptionsVOs = null;
		
		Map oneTimeHashMap 							= null;
		Collection<String> oneTimeActiveStatusList 	= new ArrayList<String>();
		
		String cmpCod = getApplicationSession().getLogonVO().getCompanyCode();
		if(FROM_SCREEN_CN66.equals(airlineExceptionsForm.getFromScreenFlag())
			|| FROM_SCREEN_INVEXP.equals(airlineExceptionsForm.getFromScreenFlag())){
			
			oneTimeActiveStatusList.add(EXCEPTION_CODES);
	    	
	    	try {
				
	    		oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
	    										cmpCod, oneTimeActiveStatusList);
			} catch (BusinessDelegateException e) {
	    		e.getMessage();
				errors=handleDelegateException( e );
			}
			
			airLineExceptionsSession.setExceptionsOneTime(
					(HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);
		}
		AirlineExceptionsFilterVO filterVO = null;
		
		if(FROM_REJECTIONMEMO.equals(airlineExceptionsForm.getFromScreenFlag())||AFTER_ACCEPT.equals(airlineExceptionsForm.getFromScreenFlag())){
			filterVO=airLineExceptionsSession.getAirlineExceptionsFilterVO();
			
			
			airlineExceptionsForm.setFromDate(filterVO.getFromDate().toDisplayDateOnlyFormat());
			airlineExceptionsForm.setToDate(filterVO.getToDate().toDisplayDateOnlyFormat());
			airlineExceptionsForm.setAirlineCode(filterVO.getAirlineCode());
			if(filterVO.getDespatchSerNo()==null||filterVO.getDespatchSerNo().trim().length()<=0){
				airlineExceptionsForm.setDsn("");
			}
		}
		
		errors = validateForm(airlineExceptionsForm, errors);
		
		if(errors != null && errors.size() > 0){
			airlineExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		if(!FROM_REJECTIONMEMO.equals(airlineExceptionsForm.getFromScreenFlag())&&!AFTER_ACCEPT.equals(airlineExceptionsForm.getFromScreenFlag())){
		 filterVO = populateFilter(airlineExceptionsForm);
		 //airlineExceptionsForm.setFromScreenFlag("");  //Modified as part of bug ICRD-100936 by A-5526     
		}
		else //Added as part of bug ICRD-100936 by A-5526 starts
		{		
			airlineExceptionsForm.setCloseStatusFlag(TO_INVOICEEXCEPTION);    
		}//Added as part of bug ICRD-100936 by A-5526 ends
//		if(airLineExceptionsSession.getAirlineExceptionsFilterVO()!=null)
//		{
//			filterVO=airLineExceptionsSession.getAirlineExceptionsFilterVO();        
//		}
		log.log(Log.FINE, "filterVO", filterVO);
		airLineExceptionsSession.setAirlineExceptionsFilterVO(filterVO);

		filterVO.setPageNumber(Integer.parseInt(airlineExceptionsForm.getDisplayPage()));

		
		filterVO.setCompanyCode(cmpCod);
		
		try {
			exceptionsVOs = new MailTrackingMRADelegate().displayAirlineExceptions(filterVO);
		
		} catch (BusinessDelegateException e) {
			
			errors=handleDelegateException( e );
			invocationContext.target = LIST_FAILURE;
		}
			
		if(exceptionsVOs == null || exceptionsVOs.size() <= 0){
			
			errors.add(new ErrorVO(ERROR_KEY_NO_RESULTS));
			airLineExceptionsSession.setTotalRecords(0);
			airLineExceptionsSession.setAirlineExceptionsVOs(null);
			airLineExceptionsSession.setAirlineExceptionsFilterVO(null);
			airlineExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
		}else{
			//Modified as part of Bug ICRD-100249 by A-5526
			airLineExceptionsSession.setAirlineExceptionsFilterVO(filterVO);    
			airlineExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;
		}
		log.log(Log.FINE, "CLASS_NAME", exceptionsVOs);
		log.log(Log.INFO, "airline Exceptions======>>>>", exceptionsVOs);
		airLineExceptionsSession.setAirlineExceptionsVOs(exceptionsVOs);
		invocationContext.addAllError(errors);
		log.exiting(CLASS_NAME, "execute");
	}
	
	/**
	 * populates the filterVO from form fields
	 * 
	 * @param form
	 * @return
	 */
	private AirlineExceptionsFilterVO populateFilter(AirlineExceptionsForm form){
		
		log.entering(CLASS_NAME, "populateFilter");
		AirlineExceptionsFilterVO filterVO = new AirlineExceptionsFilterVO();
		
		filterVO.setFrmDate(form.getFromDate());
		filterVO.setToDat(form.getToDate());
		filterVO.setAirlineCode(form.getAirlineCode());
		filterVO.setFromDate(convertToDate(form.getFromDate()));
		filterVO.setToDate(convertToDate(form.getToDate()));   
		filterVO.setOriginOfficeOfExchange(form.getOriginOfficeOfExchange());
		filterVO.setDestinationOfficeOfExchange(form.getDestinationOfficeOfExchange());
		filterVO.setMailCategory(form.getMailCategory());
		filterVO.setSubClass(form.getSubClass());
		filterVO.setYear(form.getYear());
		filterVO.setReceptacleSerialNumber(form.getReceptacleSerialNumber());
		filterVO.setHighestNumberIndicator(form.getHighestNumberIndicator());
		filterVO.setRegisteredIndicator(form.getRegisteredIndicator());
		String expCod = form.getExceptionCode();
		if( !BLANK.equals(expCod)){
			
			filterVO.setExceptionCode(expCod);
		}
		
		String dsn = form.getDsn();
		if( !BLANK.equals(dsn)){
			
			filterVO.setDespatchSerNo(dsn);
		}
		
		String invNo = form.getInvoiceRefNo();
		if( !BLANK.equals(invNo)){
			
			filterVO.setInvoiceRefNumber(invNo);
		}
		log.exiting(CLASS_NAME, "populateFilter");
		return filterVO;
	}
	
	
	/**
	 * Converts date format string to LocalDate
	 * 
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !date.equals(BLANK)){

			return new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date );
		}
		return null;
	}
	
	/**
	 * 
	 * @param form
	 * @param errors
	 * @return
	 */
	private  Collection<ErrorVO> validateForm(AirlineExceptionsForm form,
											Collection<ErrorVO> errors){
		
		
		if(BLANK.equals(form.getFromDate()) || BLANK.equals(form.getToDate())){
			
			errors.add(new ErrorVO(ERROR_KEY_DATE));
		
		}else if(!validateDate(form.getFromDate(), form.getToDate())){
			
			errors.add(new ErrorVO(ERROR_KEY_NO_INVALID_DATE));
		}
		
		if(BLANK.equals(form.getAirlineCode())){
			
			errors.add(new ErrorVO(ERROR_KEY_AIRLINE));
		
		}
		
		return errors;
	}
	
	/**
	 * validating fromdate and todate 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private boolean validateDate( String fromDate, String toDate ){
		
		/*if( ((toDate != null)&&(toDate.trim().length()>0)) && 
				((fromDate != null) &&(fromDate.trim().length()>0))) {
			
			return DateUtilities.isLessThan( fromDate, toDate, LocalDate.CALENDAR_DATE_FORMAT );
			
		}else{
			
			return true;
		}*/
		if( ((toDate != null)&&(toDate.trim().length()>0)) && 
				((fromDate != null) &&(fromDate.trim().length()>0))) {
			  LocalDate frmDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			  LocalDate toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			  frmDat.setDate(fromDate);
			  toDat.setDate(toDate);
			  if(frmDat.isGreaterThan(toDat)){
				  log.log(Log.INFO,"todate lesser");
				  return false;
			  } else {
				return true;
			}
			
		}else{
			
			return false;
		}
		
		
	}
		
	
}
