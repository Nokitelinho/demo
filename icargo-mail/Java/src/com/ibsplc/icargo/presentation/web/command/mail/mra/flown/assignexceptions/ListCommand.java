/*
 * ListCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-2401
 *
 */
public class ListCommand extends BaseCommand {

	/**
	 * 
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAIL TRACKING");
	private static final String CLASS_NAME = "ListCommand";
/**
 *module name
 * 
 */
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	/**
	 * screen id
	 * 
	 */
	private static final String SCREEN_ID = "mailtracking.mra.flown.assignexceptions";
	private static final String DATEFORMAT = "dd-MMM-yyyy";
	//private static final String FROMDATEERR ="mailtracking.mra.flown.assignexceptions.msg.err.invalidfromDate";
	private static final String DATERANGEERR ="mailtracking.mra.flown.assignexceptions.msg.err.invalidDateRange";
	private static final String FROMDATMANDATORY ="mailtracking.mra.flown.assignexceptions.msg.err.mandatoryfromdate";
//	private static final String EMPTY ="";
	private static final String STATUS = "toSave";
	/*private static final String OPFLAG_N = "N";
	private static final String OPFLAG_D = "*";//for disabled rows*/
	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";
	private static final String NO_RESULTS = "mailtracking.mra.flown.assignexceptios.msg.err.noresults";
	
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		String companyCode=logonAttributes.getCompanyCode();
		AssignExceptionsForm assignExceptionsForm = (
				AssignExceptionsForm)invocationContext.screenModel;
		AssignExceptionSession assignExceptionSession = (AssignExceptionSession)
		getScreenSession(MODULE_NAME, SCREEN_ID);
		assignExceptionSession.setExceptions(null);
		Collection<ErrorVO> errorvos = null;
		errorvos = validateFlightNumberandDates(
				 assignExceptionsForm,companyCode,assignExceptionSession);
		
		if (errorvos != null && errorvos.size() > 0) {
			log.log(Log.FINE, "**********errors", errorvos.size());
		invocationContext.addAllError(errorvos);
       	 assignExceptionSession.setExceptions(null);
       	 invocationContext.target = LIST_FAILURE;
       }
		else{
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			ArrayList<FlownMailExceptionVO> flownMailExceptionVOs = new ArrayList<FlownMailExceptionVO>();
			log.log(Log.INFO, "FilterVO to Server", assignExceptionSession.getFilterDetails());
			try {
				flownMailExceptionVOs =new ArrayList<FlownMailExceptionVO>( mailTrackingMRADelegate.findFlownMailExceptions(
						assignExceptionSession.getFilterDetails()));
			}catch(BusinessDelegateException businessDelegateException) {
				errorvos = handleDelegateException(businessDelegateException);
			}
			if(flownMailExceptionVOs == null || flownMailExceptionVOs.size() == 0) {
				ErrorVO errorVO = new ErrorVO(
						NO_RESULTS);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorvos = new ArrayList<ErrorVO>();
				errorvos.add(errorVO);
				invocationContext.addAllError(errorvos);
				
			}
			else{
				log.log(Log.INFO, "VOs from Server", flownMailExceptionVOs);
				assignExceptionsForm.setStatusFlag(STATUS);
			/*	for(FlownMailExceptionVO flownMailExceptionVO:flownMailExceptionVOs) {
					if(flownMailExceptionVO.getResolvedDate() == null) {
						flownMailExceptionVO.setOperationFlag(OPFLAG_N);
					}else {
						flownMailExceptionVO.setOperationFlag(OPFLAG_D);
					}
					
				}*/
				assignExceptionSession.setExceptions(flownMailExceptionVOs);
				
			}
			//assignExceptionsForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;
		}
		log.exiting(CLASS_NAME, "execute");
	
	
}
	
	
	/**
	 * 
	 * @param assignExceptionsForm
	 * @param companyCode
	 * @param assignExceptionSession
	 * @return
	 * 
	 */
	 public Collection<ErrorVO> validateFlightNumberandDates(
			 AssignExceptionsForm assignExceptionsForm,
     		String companyCode,AssignExceptionSession assignExceptionSession){
     	Collection<ErrorVO> errorsadd = new ArrayList<ErrorVO>();
     	FlownMailFilterVO flownMailFilterVO = 
     		assignExceptionSession.getFilterDetails();
     	AirlineValidationVO airlineVO = null;
 		if(assignExceptionsForm.getFlightCarrierCode() != null
 				&& assignExceptionsForm.getFlightCarrierCode().trim().length()
 				> 0) {
 					log.log(Log.FINE, "!!!!!!!!!!!!!!!!!!!!!!! INSIDE IF.",
							assignExceptionsForm.getFlightCarrierCode());
					Collection<ErrorVO> errorsAirline = null;
 					try {
 						airlineVO = new AirlineDelegate().validateAlphaCode(
 								companyCode,(assignExceptionsForm.
 										getFlightCarrierCode()).toUpperCase());
 					}
 					catch(BusinessDelegateException businessDelegateException){
 						errorsAirline = handleDelegateException(
 								businessDelegateException);
 	       			}
 					if(errorsAirline != null &&
 							errorsAirline.size() > 0) {
 							errorsadd.addAll(errorsAirline);
 					}
 		}

 		if(airlineVO != null) {

 			int airlineIdentifier=airlineVO.getAirlineIdentifier();
 			log.log(Log.INFO, "!!!!!!!!!!!!!!!!!airlineIdentifier!!!!!!!!!!",
					airlineIdentifier);
			flownMailFilterVO.setFlightCarrierId(airlineIdentifier);
 			log.log(Log.INFO, "!!!!!!!!!!!!!!!!airlineIdentifier!!!!!!!!!!!!",
					flownMailFilterVO.getFlightCarrierId());
			assignExceptionSession.setFilterDetails(flownMailFilterVO);
 		}

 		/*if(flownMailFilterVO.getFromDate()!= null){
 			log.log(Log.INFO,"+++++++++++++++++++++from date is not null");
		 if (DateUtilities.isGreaterThan(
				 assignExceptionsForm.getFromDate(),
					DateUtilities.getCurrentDate(DATEFORMAT), 
					DATEFORMAT)) {
			 log.log(Log.INFO,"+++++++++++++++++++++error From date earlier than current date");
				ErrorVO errorVO = new ErrorVO(
						FROMDATEERR);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorsadd.add(errorVO);
			}
		}*/
 		if(		flownMailFilterVO.getFromDate()== null ||
 				flownMailFilterVO.getToDate()== null){ 			
 			log.log(Log.INFO,"+++++++++++++++++++++error from date  is mandatory");
				ErrorVO errorVO = new ErrorVO(
						FROMDATMANDATORY);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorsadd.add(errorVO);
 			
 		}
 			
		if ((assignExceptionsForm.getFromDate() != null) 
				&& (assignExceptionsForm.getToDate() != null)
				&& (assignExceptionsForm.getFromDate().trim().length()>0)
				&&(assignExceptionsForm.getToDate().trim().length()>0)){
				if (!assignExceptionsForm.getFromDate().equals(
						assignExceptionsForm.getToDate())) {
					if (DateUtilities.isGreaterThan(
							assignExceptionsForm.getFromDate(),
							assignExceptionsForm.getToDate(), 
							DATEFORMAT)) {
						log.log(Log.INFO,"+++++++++++++++++++++from date geater than todate");
						ErrorVO errorVO = new ErrorVO(
								DATERANGEERR);
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errorsadd.add(errorVO);
					}
				}			
		}
		
     	return errorsadd;
     }
}
