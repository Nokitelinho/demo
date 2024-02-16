/*
 * SaveCommand.java Created on Sep 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.upucalendar.maintainupucalendar;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.UPUCalendarForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-2521
 *
 * Saves the collection of VOs.
 */
public class SaveCommand extends BaseCommand {
	
	private static final String CLASS_NAME = "SaveCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID =
		"mailtracking.mra.airlinebilling.defaults.upucalendar";
	
	private Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");
	
	private static final String STATUS_NEW =  "N" ;
	
	private static final String BLANK =  "" ;
	
	/* 
	 
	 * For setting the Target action. If the system successfully saves the 
	 
	 * data, then SAVE_SUCCESS target action is set to invocation context 
	 
	 * */
	
	private static final String SAVE_SUCCESS = "save_success";
	
	
	/* 
	 
	 * For setting the Target action. If the system cannot successfully saves 
	 
	 * the data, then SAVE_FAILURE target action is set to invocation context 
	 
	 * */
	
	private static final String SAVE_FAILURE = "save_failure";
	
	/*
	 *  For getting error messages from resources.
	 */
	private static final String ERROR_KEY_NOVOSFOUND = "mra.airlinebilling.upucalendar.norecordstosave";
	
	private static final String ERROR_KEY_PRIMARY_KEY_MISSING = "mra.airlinebilling.defaults.upucalendar.noprimarykey";
	
	private static final String ERROR_KEY_DUPLICATE_RECORDS = "mra.airlinebilling.defaults.upucalendar.duplicaterecords";
	
	private static final String ERROR_KEY_NO_INVALID_DATE = "mra.airlinebilling.defaults.upucalendar.notvaliddate";
	
	private static final String ERROR_KEY_NO_DATE = "mra.airlinebilling.defaults.upucalendar.nodatefields";
	/**
	 * Method  implementing saving of clearance period details
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		String opConditionItr = null; 
		String billingPeriodItr = null; 
		
		log = LogFactory.getLogger("CRA AirlineBilling");
		log.entering(CLASS_NAME, "execute");
		
		ErrorVO error = null;
		Collection<UPUCalendarVO> upuCalendarVOs = null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		Collection<UPUCalendarVO> newUPUCalendarVOs 	= new ArrayList<UPUCalendarVO>();
		
		UPUCalendarVO upuCalendarVO 		= null;
		UPUCalendarForm upuCalendarForm 	= (UPUCalendarForm)invocationContext.screenModel;
		
		/** getting values from form */
		String[] billingPeriod  	= upuCalendarForm.getBillingPeriod();
		
		String[] fromDate 			= upuCalendarForm.getFromDate();
		
		String[] toDate 			= upuCalendarForm.getToDate();
		
		String[] submissionDate 	= upuCalendarForm.getSubmissionDate();
		
		String[] generateAfterToDate = upuCalendarForm.getGenerateAfterToDate();
		
		String[] opFlag = upuCalendarForm.getOperationalFlag();
		
		UPUCalendarSession upuCalendarSession = (UPUCalendarSession)getScreenSession(
				MODULE_NAME, SCREENID );
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		
		upuCalendarVOs = upuCalendarSession.getUPUCalendarVOs();
		
		log.log(Log.FINE, "\n\n\n****************** upuCalendarVOs\n\n\n",
				upuCalendarVOs);
		/**
		 *  if VOs collection is present updates the values to each Vo from Form and saves 
		 *  else if no VO collection present it creates an error VO with appropriate message 
		 */
		if(upuCalendarVOs != null){
			
			int index = 0;		
			
			if(opFlag != null){
				
				/** iterates through rows in displayed table */
				for(String opFlagItr : opFlag) {
					
					upuCalendarVO = new UPUCalendarVO();
					
					upuCalendarVO.setCompanyCode(companyCode);
					upuCalendarVO.setClearingHouse( upuCalendarForm.getClrHsCodeLst());
					
					if(!BLANK.equals(billingPeriod[index])){
						upuCalendarVO.setBillingPeriod(billingPeriod[index]);
					} else {
						upuCalendarVO.setBillingPeriod( BLANK );
						error = new ErrorVO(ERROR_KEY_PRIMARY_KEY_MISSING);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					
					if(!BLANK.equals(fromDate[index])){
						upuCalendarVO.setFromDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,false).
								setDate(fromDate[index]));
					}
					else {
						upuCalendarVO.setFromDate(null);
					}
					
					if(!BLANK.equals(toDate[index])){
						upuCalendarVO.setToDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,false).
								setDate(toDate[index]));
					}
					else {
						upuCalendarVO.setToDate( null);
					}
					
					if( !BLANK.equals(submissionDate[index]) ){
						upuCalendarVO.setSubmissionDate(new LocalDate(LocalDate.NO_STATION, Location.NONE,false).
								setDate(submissionDate[index]));
					}
					else {
						upuCalendarVO.setSubmissionDate( null);
					}
					
					if( !BLANK.equals(generateAfterToDate[index]) ){
						upuCalendarVO.setGenerateAfterToDate(Integer.parseInt( generateAfterToDate[index] ));
					}
					else {
						upuCalendarVO.setGenerateAfterToDate( 0 );
					}
					
					if(BLANK.equals(fromDate[index]) || BLANK.equals(toDate[index])){
						error = new ErrorVO( ERROR_KEY_NO_DATE );
					}else if( !(validateDate( fromDate[index], toDate[index] )) ){
						error = new ErrorVO( ERROR_KEY_NO_INVALID_DATE );
					}
					
					/** 
					 * if operational flag is insert it checks whether it is previously deleted row in Collection
					 * if so makes it flag update 
					 */
					if( opFlagItr.equalsIgnoreCase( UPUCalendarVO.OPERATION_FLAG_INSERT )){
						
						for( UPUCalendarVO upuCalendarVOItr : upuCalendarVOs ){
							
							opConditionItr = upuCalendarVOItr.getOperationalFlag() == null? 
									BLANK : upuCalendarVOItr.getOperationalFlag();
							billingPeriodItr = upuCalendarVOItr.getBillingPeriod()== null? 
									BLANK : upuCalendarVOItr.getBillingPeriod();
														
							/** 
							 * if operational flag is insert it checks whether it is previously deleted row in Collection
							 * if so it changes its flag to update
							 * if already exists in table with update status creates an error for duplicate records .
							 */
							
							if( opConditionItr != null && 
									opConditionItr.equalsIgnoreCase( UPUCalendarVO.OPERATION_FLAG_DELETE)){
								
								if( billingPeriodItr.equalsIgnoreCase(upuCalendarVO.getBillingPeriod())){
									opFlagItr = UPUCalendarVO.OPERATION_FLAG_UPDATE;
									upuCalendarVOItr.setOperationalFlag(BLANK);
								}
							}else if( opConditionItr != null && 
									( UPUCalendarVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(opConditionItr) ||
											STATUS_NEW.equalsIgnoreCase(opConditionItr))){
								
								if( billingPeriodItr.equalsIgnoreCase(upuCalendarVO.getBillingPeriod())){
									error = new ErrorVO( ERROR_KEY_DUPLICATE_RECORDS );
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
								}
							}
						}
					}
					upuCalendarVO.setOperationalFlag( opFlagItr );
					newUPUCalendarVOs.add(upuCalendarVO);		
					++index;
				}
			}
			
			
			
			/** adding all VOs with delete status to VO collection */
			for( UPUCalendarVO upuCalendarVOItr : upuCalendarVOs ){
				
				if( upuCalendarVOItr.getOperationalFlag() != null && 
						upuCalendarVOItr.getOperationalFlag().equalsIgnoreCase( UPUCalendarVO.OPERATION_FLAG_DELETE)){
					
					newUPUCalendarVOs.add( upuCalendarVOItr );
				}
				
			}
			
			if(error != null){
				errors.add(error);
			}
			
			upuCalendarSession.setUPUCalendarVOs( newUPUCalendarVOs );
			
		}else{
			
			error = new ErrorVO(ERROR_KEY_NOVOSFOUND);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);		
		}
		
		
		for(ErrorVO error1: errors){
			
			log
					.log(Log.FINE, "\n\n\n*****1111****error1*****\n", errors.size());
			log.log(Log.FINE, "\n\n\n*****1111****error1*****\n", error1);
		}
		
		try{
			/** 
			 * if errors are present adds all errors to invocation context and sets target to SAVE_FAILURE and return
			 * else saves the VO collection and sets target to SAVE_SUCCESS
			 */ 
			
			log.log(Log.FINE,
					"\n\n\n****************** newUPUCalendarVOs\n\n\n",
					newUPUCalendarVOs);
			if( errors != null && errors.size() > 0){
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				log.exiting(CLASS_NAME, "execute");
				return;
				
			}else{
				new MailTrackingMRADelegate().saveUPUCalendarDetails( newUPUCalendarVOs );
				upuCalendarSession.setUPUCalendarVOs(null);
				upuCalendarForm.setClrHsCodeLst(BLANK);
				upuCalendarForm.setFromDateLst(BLANK);
				upuCalendarForm.setToDateLst(BLANK);
				upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SAVE_SUCCESS;
			}
			
			
		}catch(BusinessDelegateException e){
			e.getMessage();
			errors=handleDelegateException( e );
		}
		
		for(ErrorVO error1: errors){
			
			log.log(Log.FINE, "\n\n\n*****222****error1*****\n", error1.getErrorCode());
		}
		invocationContext.addAllError(errors);
		log.exiting(CLASS_NAME, "execute");
	}
	
	/**
	 * validating fromdate and todate 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private boolean validateDate( String fromDate, String toDate ){
		
		if( ((toDate != null)&&(toDate.trim().length()>0)) && 
				((fromDate != null) &&(fromDate.trim().length()>0))) {
			
			return DateUtilities.isLessThan( fromDate, toDate, LocalDate.CALENDAR_DATE_FORMAT );
			
		}else{
			
			return true;
		}
	}
}


