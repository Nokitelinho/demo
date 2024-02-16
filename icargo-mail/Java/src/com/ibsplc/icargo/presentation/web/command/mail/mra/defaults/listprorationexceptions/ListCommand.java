/*
 * ListCommand.java Created on Sep 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;




/**
 * @author A-3108
 *
 */
public class ListCommand extends BaseCommand {

		/**
		 * Logger and the file name
		 */
		private Log log = LogFactory.getLogger("MRA PRORATION");
		
		private static final String SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
		private static final String MODULE_NAME = "mailtracking.mra.defaults";
		private static final String CLASS_NAME = "ListCommand";
		private static final String LIST_SUCCESS = "list_success";
		private static final String LIST_FAILURE = "list_failure";		
		

		
		private static final String BLANK = "";
		
		private static final String KEY_EXCEPTION = "mra.proration.exceptions";
		private static final String KEY_STATUS = "mra.proration.exceptionstatus";
		private static final String KEY_TRIGGERPOINT = "mailtracking.mra.proration.triggerpoint";
		private static final String KEY_ASSIGNED_STATUS="mra.proration.assignedstatus";
		
		
		private static final String ERR_NORECORDS = "mra.proration.listexceptions.msg.err.norecords";
		private static final String ERR_TODATEMANDATORY = "mra.proration.listexceptions.msg.err.todatenomandatory";
		private static final String ERR_LISTMANDATORYFILTERS = "mra.proration.listexceptions.msg.err.listfiltersmandatory";
		
		private static final String ERR_FROMDATEMANDATORY = "mra.proration.listexceptions.msg.err.fromdatenomandatory";
		private static final String ERR_FROMDATEEARLY = "mra.proration.listexceptions.msg.err.fromdateearliertodate";
		
		private static final String INVOKING_SCREEN = "viewflightsectorrevenue";
		private static final String LISTRATEAUDIT = "listrateaudit";
		private static final String PRORATE_FACTORS = "maintainprorate";
		private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
		private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
		/**
		 * Execute method
		 *
		 * @param invocationContext
		 *            InvocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;
		ListProrationExceptionsSession listExceptionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		
		Collection<ErrorVO> errors = null;
		ProrationExceptionsFilterVO proExpFilterVO = new ProrationExceptionsFilterVO();
		if(listExceptionSession.getSelectedVoidMailbags() != null && !listExceptionSession.getSelectedVoidMailbags().isEmpty()){
			listExceptionSession.setSelectedRows(null);
			listExceptionSession.setSelectedVoidMailbags(null);
   	 	}
		if(listExceptionForm.getInvokingScreen()!=null 
				&& INVOKING_SCREEN.equals(listExceptionForm.getInvokingScreen())){
			log.log(Log.FINE, "FilterVo fro flightSectorrevenue",
					listExceptionSession.getProrationExceptionFilterVO());
			proExpFilterVO=listExceptionSession.getProrationExceptionFilterVO();
			listExceptionForm.setDispatchNo(proExpFilterVO.getDispatchNo());
			listExceptionForm.setToDate(proExpFilterVO.getToDate().toDisplayDateOnlyFormat());
			getOneTimeValues(listExceptionSession,logonAttributes);		
			listExceptionForm.setCloseFlag(INVOKING_SCREEN);
			listExceptionForm.setInvokingScreen(BLANK);
			listExceptionForm.setDisplayPage("1");
			listExceptionForm.setLastPageNum("0");
			//added by A-5223 for ICRD-21098 starts
			proExpFilterVO.setTotalRecords(-1);
			//added by A-5223 for ICRD-21098 ends
			proExpFilterVO.setDisplayPage(Integer.parseInt(listExceptionForm.getDisplayPage()));
		}else if(listExceptionForm.getInvokingScreen()!=null 
				&& LISTRATEAUDIT.equals(listExceptionForm.getInvokingScreen())){
			log.log(Log.FINE, "FilterVo fro flightSectorrevenue",
					listExceptionSession.getProrationExceptionFilterVO());
			proExpFilterVO=listExceptionSession.getProrationExceptionFilterVO();
			listExceptionForm.setDispatchNo(proExpFilterVO.getDispatchNo());
			listExceptionForm.setToDate(proExpFilterVO.getToDate().toDisplayDateOnlyFormat());
			getOneTimeValues(listExceptionSession,logonAttributes);		
			listExceptionForm.setInvokingScreen(BLANK);
			listExceptionForm.setDisplayPage("1");
			listExceptionForm.setLastPageNum("0");
			//added by A-5223 for ICRD-21098 starts
			proExpFilterVO.setTotalRecords(-1);
			//added by A-5223 for ICRD-21098 ends
			proExpFilterVO.setDisplayPage(Integer.parseInt(listExceptionForm.getDisplayPage()));
		}else{
		 String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
	
		if(invocationContext.getErrors()!=null && invocationContext.getErrors().size()>0){	
			
			invocationContext.target=LIST_FAILURE;
			return;
		}
		getOneTimeValues(listExceptionSession,logonAttributes);		
		
		/*Added for Navigation */
		if(listExceptionForm.getInvokingScreen()!=null 
				&& PRORATE_FACTORS.equals(listExceptionForm.getInvokingScreen())){
			proExpFilterVO =listExceptionSession.getProrationExceptionFilterVO();
			log.log(Log.FINE, "proExpFilterVO....", proExpFilterVO);
			populateForm(proExpFilterVO,listExceptionForm);
			listExceptionForm.setInvokingScreen("");
			
		}
		log.log(Log.FINE, "FromScreenFlag....", listExceptionSession.getFromScreenFlag());
	if(listExceptionSession.getFromScreenFlag()!=null && ("Y").equals(listExceptionSession.getFromScreenFlag())){ 
		
			 proExpFilterVO =listExceptionSession.getProrationExceptionFilterVO();
			 
			 log.log(Log.FINE, "proExpFilterVO....", proExpFilterVO);
			populateForm(proExpFilterVO,listExceptionForm);
			
			listExceptionSession.setFromScreenFlag(BLANK);
		}
	/*Validate form*/
	errors= validateForm(listExceptionForm);
	if(errors!=null && errors.size()>0){	
		log.log(Log.FINE,"Entered first validation :::returned errors");

		invocationContext.addAllError(errors);
		listExceptionSession.removeProrationExceptionVOs();
		invocationContext.target=LIST_FAILURE;
		return;
	}
	
	
	if(errors != null && errors.size() > 0){
    	invocationContext.addAllError(errors);
    	log.log(Log.FINE,"entered loop :in case of errors");
    	listExceptionSession.removeProrationExceptionVOs();
    	invocationContext.target = LIST_FAILURE;	
    	return;
	}else{
		proExpFilterVO.setCompanyCode(companyCode);
		
		
		/* 
		 * Getting local date 
		 */

				if (listExceptionForm.getFlightDate().length() != 0
						&& listExceptionForm.getFlightDate() != null
						&& !BLANK.equals(listExceptionForm.getFlightDate())) {
					proExpFilterVO.setFlightDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, false)
							.setDate(listExceptionForm.getFlightDate().trim()));
					
				}

				if (listExceptionForm.getFromDate().length() != 0
						&& listExceptionForm.getFromDate() != null
						&& !BLANK.equals(listExceptionForm.getFromDate())) {
					proExpFilterVO.setFromDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, false)
							.setDate(listExceptionForm.getFromDate().trim()));
				}

				if (listExceptionForm.getToDate().length() != 0
						&& listExceptionForm.getToDate() != null
						&& !BLANK.equals(listExceptionForm.getToDate())) {
					LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
							Location.NONE, false);
					toDate.setDate(listExceptionForm.getToDate());
					proExpFilterVO.setToDate(toDate);
		}
		
		/*
		 * Getting Attributes
		 */
		
				
				if (listExceptionForm.getOriginOfficeOfExchange() != null
						&& !BLANK.equals(listExceptionForm
								.getOriginOfficeOfExchange()))
						 {
					proExpFilterVO.setOriginOfficeOfExchange(listExceptionForm
							.getOriginOfficeOfExchange());
					}
				//Added as Part of CR ID:ICRD-241594
				if (listExceptionForm.getGpaCode() != null
						&& !BLANK.equals(listExceptionForm.getGpaCode())) {
					proExpFilterVO.setGpaCode(listExceptionForm.getGpaCode());
				}
				//End Here 
				
				
				if (listExceptionForm.getDestinationOfficeOfExchange() != null
						&& !BLANK.equals(listExceptionForm
								.getDestinationOfficeOfExchange()))
						 {
					proExpFilterVO.setDestinationOfficeOfExchange(listExceptionForm
							.getDestinationOfficeOfExchange());
					}
				
				if (listExceptionForm.getMailCategory() != null
						&& !BLANK.equals(listExceptionForm
								.getMailCategory()))
						 {
					proExpFilterVO.setMailCategory(listExceptionForm
							.getMailCategory());
					}
				if (listExceptionForm.getSubClass() != null
						&& !BLANK.equals(listExceptionForm
								.getSubClass()))
						 {
					proExpFilterVO.setSubClass(listExceptionForm
							.getSubClass());
					}
				if (listExceptionForm.getYear() != null
						&& !BLANK.equals(listExceptionForm
								.getYear()))
						 {
					proExpFilterVO.setYear(listExceptionForm
							.getYear());
					}
				if (listExceptionForm.getDispatchNo() != null
						&& !BLANK.equals(listExceptionForm
								.getDispatchNo()))
						 {
					proExpFilterVO.setDispatchNo(listExceptionForm
							.getDispatchNo());
					}
				//Added as part of ICRD-205027 starts
				if (listExceptionForm.getMailbagID() != null
						&& !BLANK.equals(listExceptionForm
								.getMailbagID()))
						 {
					proExpFilterVO.setMailbagId(listExceptionForm
							.getMailbagID());
					}
				//Added as part of ICRD-205027 ends
				if (listExceptionForm.getReceptacleSerialNumber() != null
						&& !BLANK.equals(listExceptionForm
								.getReceptacleSerialNumber()))
						 {
					proExpFilterVO.setReceptacleSerialNumber(listExceptionForm
							.getReceptacleSerialNumber());
					}
				
				//8331
				if (listExceptionForm.getCsgDocNum() != null
						&& !BLANK.equals(listExceptionForm
								.getCsgDocNum()))
						 {
					proExpFilterVO.setCsgdocnum(listExceptionForm
							.getCsgDocNum());
					}
				if (listExceptionForm.getHighestNumberIndicator() != null
						&& !BLANK.equals(listExceptionForm
								.getHighestNumberIndicator()))
						 {
					proExpFilterVO.setHighestNumberIndicator(listExceptionForm
							.getHighestNumberIndicator());
					}
				if (listExceptionForm.getRegisteredIndicator()!= null
						&& !BLANK.equals(listExceptionForm
								.getRegisteredIndicator()))
						 {
					proExpFilterVO.setRegisteredIndicator(listExceptionForm
							.getRegisteredIndicator());
					}
				if (listExceptionForm.getExceptionCode() != null
						&& !BLANK.equals(listExceptionForm.getExceptionCode())) {
					proExpFilterVO.setExceptionCode(listExceptionForm
							.getExceptionCode());
				}

				if (listExceptionForm.getFlightNumber() != null
						&& !BLANK.equals(listExceptionForm.getFlightNumber())) {
					proExpFilterVO.setFlightNumber(listExceptionForm
							.getFlightNumber());
				}

				if (listExceptionForm.getCarrierCode() != null
						&& !BLANK.equals(listExceptionForm.getCarrierCode())) {
					proExpFilterVO.setCarrierCode(listExceptionForm
							.getCarrierCode());
				}

				if (listExceptionForm.getOrigin() != null
						&& !BLANK.equals(listExceptionForm.getOrigin())) {
					proExpFilterVO.setSegmentOrigin(listExceptionForm
							.getOrigin());
				}

				if (listExceptionForm.getDestination() != null
						&& !BLANK.equals(listExceptionForm.getDestination())) {
					proExpFilterVO.setSegmentDestination(listExceptionForm
							.getDestination());
				}

				if (listExceptionForm.getStatus() != null
						&& !BLANK.equals(listExceptionForm.getStatus())) {
			proExpFilterVO.setStatus(listExceptionForm.getStatus());
		}
				if (listExceptionForm.getAssignedStatus() != null
						&& !BLANK.equals(listExceptionForm.getAssignedStatus())) {
			proExpFilterVO.setAssignedStatus(listExceptionForm.getAssignedStatus());
				}
				if (listExceptionForm.getAsignee() != null
						&& !BLANK.equals(listExceptionForm.getAsignee())) {
					proExpFilterVO.setAsignee(listExceptionForm.getAsignee());
				}
		String toDisplayPage = listExceptionForm.getDisplayPage();
		log.log(Log.INFO, "toDisplayPage----------->>", toDisplayPage);
		proExpFilterVO.setDisplayPage(Integer.parseInt(toDisplayPage));
			}
		
		}	
		//added by A-5223 for ICRD-21098 starts
		if(ListMailProrationExceptionsForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(listExceptionForm.getPaginationMode()) ) {

			log.log(Log.FINE, " <: Page navigating after listing :> ");
			proExpFilterVO.setTotalRecords(listExceptionSession.getTotalRecords());
			proExpFilterVO.setDisplayPage(Integer.parseInt(listExceptionForm.getDisplayPage()));

		} else {
			log.log(Log.FINE, " <: LISTING FROM FILTER :> ");
			log
					.log(
							Log.FINE,
							"VALUE SETTED THROUGH JSP}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}} ",
							listExceptionForm.getPaginationMode());
			//listExceptionForm.setDisplayPage("1");
			proExpFilterVO.setDisplayPage(Integer.parseInt(listExceptionForm.getDisplayPage()));
			proExpFilterVO.setTotalRecords(-1);
		}
		//added by A-5223 for ICRD-21098 ends
		
		
	Page<ProrationExceptionVO> resultListExceptionsDetailsVOs=null;		
	log.log(Log.INFO, "proExpFilterVO from list command-------->>",
			proExpFilterVO);
		listExceptionSession.setProrationExceptionFilterVO(proExpFilterVO);
		/*
		 * 
		 * Delegate Call to list Exceptions
		 */
		try{
			resultListExceptionsDetailsVOs = new MailTrackingMRADelegate()
					.findProrationExceptions(proExpFilterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
		log.log(Log.INFO, "resultListSPADetailsVOs-------->>",
				resultListExceptionsDetailsVOs);
		if (resultListExceptionsDetailsVOs != null
				&& resultListExceptionsDetailsVOs.size() > 0) {
			Collection<OneTimeVO> vos = listExceptionSession.getOneTimeValues().get(KEY_STATUS);
			if(vos != null && !vos.isEmpty()){
				for(ProrationExceptionVO proVO : resultListExceptionsDetailsVOs){
					for(OneTimeVO vo : vos){
						if(vo.getFieldValue().equals(proVO.getStatus())){
							proVO.setStatus(vo.getFieldDescription());
							break;
						}
					}
				}
			}
			log.log(Log.INFO,
					"resultListExceptionsDetailsVOs.size()-------->>",
					resultListExceptionsDetailsVOs.size());
			log.log(Log.FINE, " the total records in the    list:>",
					resultListExceptionsDetailsVOs.getTotalRecordCount());
			log.log(Log.FINE, " caching in screen session ");
   			listExceptionSession.setTotalRecords(resultListExceptionsDetailsVOs.getTotalRecordCount()); 
   		//added by A-5223 for ICRD-21098 ends
			/*for(ProrationExceptionVO prorationExceptionVO:resultListExceptionsDetailsVOs){
				try{
				String route=new MailTrackingMRADelegate()
				.findDSNRoute(prorationExceptionVO);
				prorationExceptionVO.setRoute(route);
				}catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
//printStackTrrace()();
				}
			}*/
			listExceptionSession
					.setProrationExceptionVOs(resultListExceptionsDetailsVOs);
			log.log(Log.INFO, "resultListSPADetailsVOs-------->>",
					resultListExceptionsDetailsVOs);
			listExceptionForm
					.setCalendarFormat(logonAttributes.getDateFormat());
			log.log(Log.INFO, "logonAttributes.getDateAndTimeFormat()>>>",
					logonAttributes.getDateFormat());
		log.exiting(CLASS_NAME, "execute");
		listExceptionForm.setOperationFlag("U");
		invocationContext.target =  LIST_SUCCESS;
		} else {
		
			ErrorVO errorVO = new ErrorVO(ERR_NORECORDS);
			errors = new ArrayList<ErrorVO>();
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			listExceptionSession.removeProrationExceptionVOs();
			invocationContext.target = LIST_FAILURE;
			
		}
	}
		
		/*
		 * Method to validate form fields
		 */
		
		 private Collection<ErrorVO> validateForm (ListMailProrationExceptionsForm listExceptionForm){
			 
			 boolean isLocal=false;
			 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			 ErrorVO error = null; 
			 log.log(Log.FINE,"Validate");
			 
			 
			// Added by A-5526 as part of ICRD-63164
			  if((listExceptionForm.getFlightNumber()==null || listExceptionForm.getFlightNumber().trim().length() == 0 || listExceptionForm==null)||
							(listExceptionForm.getCarrierCode()==null || listExceptionForm.getCarrierCode().trim().length() ==0 || listExceptionForm==null) || 
							( listExceptionForm.getFlightDate()==null || listExceptionForm.getFlightDate().trim().length()==0 || listExceptionForm==null) )
					{
				  if((listExceptionForm.getToDate()== null || listExceptionForm.getToDate().trim().length() == 0 || listExceptionForm == null) || 
							( listExceptionForm.getFromDate()==null || listExceptionForm.getFromDate().trim().length() == 0 || listExceptionForm == null ))
						  {
						 error =  new ErrorVO(ERR_LISTMANDATORYFILTERS);
				 error.setErrorDisplayType(ERROR);
				 errors.add(error);
						
						
						  }
						
					}
			  
			  
			  
			  else if((listExceptionForm.getToDate()== null || listExceptionForm.getToDate().trim().length() == 0 || listExceptionForm == null) || 
				( listExceptionForm.getFromDate()==null || listExceptionForm.getFromDate().trim().length() == 0 || listExceptionForm == null ))
			  {
				  if((listExceptionForm.getFlightNumber()==null || listExceptionForm.getFlightNumber().trim().length() == 0 || listExceptionForm==null)||
							(listExceptionForm.getCarrierCode()==null || listExceptionForm.getCarrierCode().trim().length() ==0 || listExceptionForm==null) || 
							( listExceptionForm.getFlightDate()==null || listExceptionForm.getFlightDate().trim().length()==0 || listExceptionForm==null) )
					{
					 error =  new ErrorVO(ERR_LISTMANDATORYFILTERS);
					 error.setErrorDisplayType(ERROR);
					 errors.add(error);
					  
			  }
			  }
			  
			 // Ends the changes added by A-5526 as part of ICRD-63164 
				 
		 else {
			if (((!("").equals(listExceptionForm.getFromDate())) && listExceptionForm
					.getFromDate() != null)) {
				if (new LocalDate(LocalDate.NO_STATION, Location.NONE, isLocal)
						.setDate(listExceptionForm.getFromDate().trim())
						.isGreaterThan(
								new LocalDate(LocalDate.NO_STATION,
										Location.NONE, isLocal)
										.setDate(listExceptionForm.getToDate()
												.trim()))) {
						 ErrorVO errorVO = new ErrorVO(ERR_FROMDATEEARLY);
						 errorVO.setErrorDisplayType(ERROR);
						 errors.add(errorVO);
					 }
				 }
			 }
			 log.log(Log.FINE, "Errors", errors);
			return errors;
		 }
		 
		 /**
		 * @param listExceptionSession
		 * @param logonAttributes
		 */
		void getOneTimeValues(ListProrationExceptionsSession listExceptionSession, LogonAttributes logonAttributes){
		    	log.entering(CLASS_NAME,"getOneTimeValues");
		    	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			    Map<String,Collection<OneTimeVO>> hashMap = null;
			    Collection<String> oneTimeList = new ArrayList<String>();

				oneTimeList.add(KEY_TRIGGERPOINT);
				oneTimeList.add(KEY_EXCEPTION);
				oneTimeList.add(KEY_STATUS);
				oneTimeList.add(KEY_ASSIGNED_STATUS);
				oneTimeList.add(KEY_BILLING_TYPE_ONETIME);
				oneTimeList.add(KEY_CATEGORY_ONETIME);
				try {
					log.log(Log.FINEST,"***********************************inside try");
					hashMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
					log.log(Log.FINEST,
							"hash map*****************************", hashMap);

				}catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
					log.log(Log.SEVERE, "status fetch exception");
				}
				if(hashMap!=null){
					Collection<OneTimeVO> vos = hashMap.get(KEY_STATUS);
					if(vos != null && !vos.isEmpty()){
						Iterator<OneTimeVO> iterator = vos.iterator();
						while(iterator.hasNext()){
							OneTimeVO vo = iterator.next();
							if("R".equals(vo.getFieldValue())){
								iterator.remove();
							}
						}
						
					}
					listExceptionSession.setOneTimeValues((HashMap<String,Collection<OneTimeVO>>)hashMap);
				}
				log.exiting(CLASS_NAME,"getOneTimeValues");
		    }

	/**
	 * 
	 * Created by a-3140 May 9, 2008
	 * 
	 * @param proExpFilterVO
	 * @param listExceptionForm
	 */
	void populateForm(ProrationExceptionsFilterVO proExpFilterVO,
			ListMailProrationExceptionsForm listExceptionForm) {
		log.log(Log.INFO, "populateForm entering");
		if (proExpFilterVO.getDispatchNo() != null) {
			listExceptionForm.setDispatchNo(proExpFilterVO.getDispatchNo());
			
			
		} else {
			listExceptionForm.setDispatchNo(BLANK);		
		}
		if (proExpFilterVO.getCarrierCode()!= null) {
			listExceptionForm.setCarrierCode(proExpFilterVO.getCarrierCode());
		} else {
			listExceptionForm.setCarrierCode(BLANK);
		}
		if (proExpFilterVO.getFlightNumber() != null) {
			listExceptionForm.setFlightNumber(proExpFilterVO.getFlightNumber());
		} else {
			listExceptionForm.setFlightNumber(BLANK);
		}
		if (proExpFilterVO.getFlightDate() != null) {
			listExceptionForm.setFlightDate(proExpFilterVO.getFlightDate()
					.toDisplayDateOnlyFormat());
		} else {
			listExceptionForm.setFlightDate(BLANK);
		}
		if (proExpFilterVO.getToDate() != null) {
			listExceptionForm.setToDate(proExpFilterVO.getToDate()
					.toDisplayDateOnlyFormat());
		} else {
			listExceptionForm.setToDate(BLANK);
		}
		if (proExpFilterVO.getExceptionCode() != null) {
			listExceptionForm.setExceptionCode(proExpFilterVO
					.getExceptionCode());
		} else {
			listExceptionForm.setExceptionCode(BLANK);
		}
		if (proExpFilterVO.getSegmentOrigin() != null) {
			listExceptionForm.setOrigin(proExpFilterVO.getSegmentOrigin());
		} else {
			listExceptionForm.setOrigin(BLANK);
		}
		if (proExpFilterVO.getSegmentDestination() != null) {
			listExceptionForm.setDestination(proExpFilterVO
					.getSegmentDestination());
		} else {
			listExceptionForm.setDestination(BLANK);
		}
		if (proExpFilterVO.getStatus() != null) {
			listExceptionForm.setStatus(proExpFilterVO.getStatus());
		}
		else
		{
			listExceptionForm.setStatus(BLANK);
		}
		if( proExpFilterVO.getMailbagId()!= null)
		{
			listExceptionForm.setMailbagID(proExpFilterVO.getMailbagId());
		}
		else
		{
			listExceptionForm.setMailbagID(BLANK);	
		}
		if( proExpFilterVO.getOriginOfficeOfExchange()!= null)
		{
			listExceptionForm.setOriginOfficeOfExchange(proExpFilterVO.getOriginOfficeOfExchange());
		}
		else
		{
			listExceptionForm.setOriginOfficeOfExchange(BLANK);	
		}
		if( proExpFilterVO.getDestinationOfficeOfExchange()!= null)
		{
			listExceptionForm.setDestinationOfficeOfExchange(proExpFilterVO.getDestinationOfficeOfExchange());
		}
		else
		{
			listExceptionForm.setDestinationOfficeOfExchange(BLANK);	
		}
		if( proExpFilterVO.getMailCategory()!= null)
		{
			listExceptionForm.setMailCategory(proExpFilterVO.getMailCategory());
		}
		else
		{
			listExceptionForm.setMailCategory(BLANK);	
		}
		if( proExpFilterVO.getSubClass()!= null)
		{
			listExceptionForm.setSubClass(proExpFilterVO.getSubClass());
		}
		else
		{
			listExceptionForm.setSubClass(BLANK);	
		}
		if( proExpFilterVO.getYear()!= null)
		{
			listExceptionForm.setYear(proExpFilterVO.getYear());
		}
		else
		{
			listExceptionForm.setYear(BLANK);	
		}
		if( proExpFilterVO.getReceptacleSerialNumber()!= null)
		{
			listExceptionForm.setReceptacleSerialNumber(proExpFilterVO.getReceptacleSerialNumber());
		}
		else
		{
			listExceptionForm.setReceptacleSerialNumber(BLANK);	
		}
		if( proExpFilterVO.getRegisteredIndicator()!= null)
		{
			listExceptionForm.setRegisteredIndicator(proExpFilterVO.getRegisteredIndicator());
		}
		else
		{
			listExceptionForm.setRegisteredIndicator(BLANK);	
		}
		if( proExpFilterVO.getHighestNumberIndicator()!= null)
		{
			listExceptionForm.setHighestNumberIndicator(proExpFilterVO.getHighestNumberIndicator());
		}
		else
		{
			listExceptionForm.setHighestNumberIndicator(BLANK);	
		}
		if( proExpFilterVO.getAsignee()!= null)
		{
			listExceptionForm.setAsignee(proExpFilterVO.getAsignee());
		}
		else
		{
			listExceptionForm.setAsignee(BLANK);	
		}
		if (proExpFilterVO.getDisplayPage() != 0) {
			listExceptionForm.setDisplayPage(String.valueOf(proExpFilterVO.getDisplayPage()));
		}
		else
		{
			listExceptionForm.setDisplayPage("1");
		}
		if (proExpFilterVO.getFromDate() != null) {
			listExceptionForm.setFromDate(proExpFilterVO.getFromDate()
					.toDisplayDateOnlyFormat());
		}
		else{
			listExceptionForm.setFromDate(BLANK);
		}
		//8331	
		if( proExpFilterVO.getCsgdocnum()!= null)
		{
			listExceptionForm.setCsgDocNum(proExpFilterVO.getCsgdocnum());
		}
		else
		{
			listExceptionForm.setCsgDocNum(BLANK);	
		}
		
		
		
		log.log(Log.INFO, "populateForm exiting");
	}
}
