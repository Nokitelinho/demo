/*
 * 
 * DeleteCommand.java Created on Sep 28, 2006
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.UPUCalendarForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2521
 *
 * Deletes selected rows from display table
 */
public class DeleteCommand extends BaseCommand {

	private static final String CLASS_NAME = "DeleteCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID =
    	"mailtracking.mra.airlinebilling.defaults.upucalendar";
	
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");
	
	private static final String BLANK =  "" ;
	
	private static final String STATUS_NEW =  "N" ;
	
	
	/* 

	 * For setting the Target action. If the system successfully saves the 

	 * data, then SAVE_SUCCESS target action is set to invocation context 

	 * */

	private static final String DELETEDETAILS_SUCCESS = "deletedetails_success";
	
	private static final String DELETEDETAILS_FAILURE = "deletedetails_failure";
	
	private static final String ERROR_KEY_NOROWSELECTED = "cra.airlinebilling.defaults.upucalendar.selectrow";
	
	/**
	 * Method  implementing deletion of clearance pereiods 
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		String toBeRemovedRow	= "R";
		ErrorVO error				= null;
		Collection<ErrorVO> errors	= new ArrayList<ErrorVO>();
		Collection<UPUCalendarVO> newUPUCalendarVOs 	= new ArrayList<UPUCalendarVO>();
		Collection<UPUCalendarVO> finalUPUCalendarVOs = new ArrayList<UPUCalendarVO>();
		Collection<UPUCalendarVO> upuCalendarVOs 	= null;
		UPUCalendarVO upuCalendarVO 				= null;
			
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		
		
		/** getting values from form */   	
    	UPUCalendarForm upuCalendarForm = (UPUCalendarForm)invocationContext.screenModel;
    	
    	
    	String[] billingPeriod  	= upuCalendarForm.getBillingPeriod();
		
		String[] fromDate 			= upuCalendarForm.getFromDate();
		
		String[] toDate 			= upuCalendarForm.getToDate();
		
		String[] submissionDate 	= upuCalendarForm.getSubmissionDate();
		
		String[] generateAfterToDate = upuCalendarForm.getGenerateAfterToDate();
		
		String[] opFlag = upuCalendarForm.getOperationalFlag();
		
		String[] rowCount = upuCalendarForm.getRowCount();
		
		UPUCalendarSession upuCalendarSession = (UPUCalendarSession)getScreenSession(
    																		MODULE_NAME, SCREENID);
    	upuCalendarVOs 	= upuCalendarSession.getUPUCalendarVOs();
		
		
		/** 
		 * if no rows in table is elected creates an error VO with suitable message 
		 * if has rows selected , updates VOs, Changes VO's operational flag of  selected rows appropriately
		 * and adds it to VO collection 
		 */
		if( rowCount == null || upuCalendarVOs == null ) {
			
			log.log(Log.FINE, "No row selected");
			error = new ErrorVO(ERROR_KEY_NOROWSELECTED);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);		
			invocationContext.addAllError(errors);
			invocationContext.target = DELETEDETAILS_FAILURE;
			return;
		
		} else {
			
			int index = 0;
			
			for( String opFlagItr : opFlag ) {
				
				upuCalendarVO = new UPUCalendarVO();
				
				upuCalendarVO.setCompanyCode( companyCode );
				
				upuCalendarVO.setClearingHouse( upuCalendarForm.getClrHsCodeLst() );
				
				if(!billingPeriod[index].equals(BLANK)){
					upuCalendarVO.setBillingPeriod( billingPeriod[index]);
				}else {
					upuCalendarVO.setBillingPeriod( null );
				}
				
				if(! fromDate[index].equals(BLANK)){
					upuCalendarVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).
									setDate(fromDate[index]));
				}
				else {
					upuCalendarVO.setFromDate( null);
				}
				
				if(! toDate[index].equals(BLANK)){
					upuCalendarVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).
									setDate(toDate[index]));
				}
				else {
					upuCalendarVO.setToDate( null);
				}
				
				if( !submissionDate[index].equals(BLANK) ){
					upuCalendarVO.setSubmissionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).
									setDate(submissionDate[index]));
				}
				else {
					upuCalendarVO.setSubmissionDate( null);
				}
				
				if( !generateAfterToDate[index].equals(BLANK) ){
					upuCalendarVO.setGenerateAfterToDate(Integer.parseInt( generateAfterToDate[index] ));
				}
				else {
					upuCalendarVO.setGenerateAfterToDate( 0 );
				}
				upuCalendarVO.setOperationalFlag( opFlagItr );
				newUPUCalendarVOs.add( upuCalendarVO );
				++index;
			}
			
			/** Changing flag values for selected rows */ 
			for(String rowID : rowCount ) {
				
				index = 0;
				
				for( UPUCalendarVO upuCalendarVOItr : newUPUCalendarVOs ){
					
					if(index ==  Integer.parseInt(rowID) ) {
						
						
						/** if current falg is insert discards those VO */
						if( upuCalendarVOItr.getOperationalFlag().equalsIgnoreCase(UPUCalendarVO.OPERATION_FLAG_INSERT )){
							upuCalendarVOItr.setOperationalFlag( toBeRemovedRow );
							++index;
							break;
						}
							
						upuCalendarVOItr.setOperationalFlag( UPUCalendarVO.OPERATION_FLAG_DELETE );
						
						break;
					}
					++index;
				}
			}
			
			/** adds VOs that are to shown to final VO collection */
			for( UPUCalendarVO upuCalendarVOItr : newUPUCalendarVOs ){
				
				if( upuCalendarVOItr.getOperationalFlag() != null && 
					(UPUCalendarVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(upuCalendarVOItr.getOperationalFlag()) || 
					 UPUCalendarVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(upuCalendarVOItr.getOperationalFlag()) ||
					 STATUS_NEW.equalsIgnoreCase(upuCalendarVOItr.getOperationalFlag()))
					){
					
					finalUPUCalendarVOs.add( upuCalendarVOItr );
				}
				
			}
			
			/** adds VOs that are to shown to final VO collection */
			for( UPUCalendarVO upuCalendarVOItr : newUPUCalendarVOs ){
				
				if( upuCalendarVOItr.getOperationalFlag() != null && 
						upuCalendarVOItr.getOperationalFlag().equalsIgnoreCase( UPUCalendarVO.OPERATION_FLAG_DELETE)){
					
					finalUPUCalendarVOs.add( upuCalendarVOItr );
				}
				
			}
			
			/** adds Vos to be deleted from old collection to new collection */
			for( UPUCalendarVO upuCalendarVOItr : upuCalendarVOs ){
				
				if( upuCalendarVOItr.getOperationalFlag() != null && 
						upuCalendarVOItr.getOperationalFlag().equalsIgnoreCase( UPUCalendarVO.OPERATION_FLAG_DELETE)){
					
					finalUPUCalendarVOs.add( upuCalendarVOItr );
				}
				
			}
			
//			for( UPUCalendarVO upuCalendarVOItr : newUPUCalendarVOs ){
//				
//				if( upuCalendarVOItr.getOperationalFlag() != null &&
//						upuCalendarVOItr.getOperationalFlag().equalsIgnoreCase( toBeRemovedRow )){
//					
//					continue;
//				
//				}else{
//					
//					finalUPUCalendarVOs.add( upuCalendarVOItr );
//				}
//			}
			
			log.log(Log.FINE, "VO in finalUPUCalendarVOs is: ",
					finalUPUCalendarVOs);
			upuCalendarSession.setUPUCalendarVOs( finalUPUCalendarVOs );
			upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = DELETEDETAILS_SUCCESS;
		}
				
		log.exiting(CLASS_NAME, "execute");
		
	}
}
