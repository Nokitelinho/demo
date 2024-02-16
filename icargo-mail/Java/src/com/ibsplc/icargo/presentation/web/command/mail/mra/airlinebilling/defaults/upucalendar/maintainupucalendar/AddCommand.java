/*
 * 
 * AddCommand.java Created on Sep 28, 2006
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.UPUCalendarForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**

 * @author A-2521

 * Adds an row to table

 */



public class AddCommand  extends BaseCommand {

	private static final String CLASS_NAME = "AddCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID ="mailtracking.mra.airlinebilling.defaults.upucalendar";

	private static final String ADDDETAILS_SUCCESS = "adddetails_success";

	private Log log = LogFactory.getLogger("CRA_DEFAULTS");
	
	private static final String BLANK =  "" ;

	/**

	 * execute method

	 * @param invocationContext

	 * @throws CommandInvocationException

	 */

	public void execute(InvocationContext invocationContext)
										throws CommandInvocationException {

		int index = 0;
		Collection<UPUCalendarVO> upuCalendarVOs 	= null;
		UPUCalendarVO upuCalendarVO 				= null;
		Collection<UPUCalendarVO> newUPUCalendarVOs 	= new ArrayList<UPUCalendarVO>();
		log = LogFactory.getLogger("MRA AIRLINEBILLING");

		log.entering(CLASS_NAME, "execute");

		UPUCalendarForm upuCalendarForm = (UPUCalendarForm)invocationContext.screenModel;
		UPUCalendarSession upuCalendarSession = (UPUCalendarSession)getScreenSession(
														MODULE_NAME, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		
		/** getting values from form */
		String[] billingPeriod  	= upuCalendarForm.getBillingPeriod();

		String[] fromDate 			= upuCalendarForm.getFromDate();

		String[] toDate 			= upuCalendarForm.getToDate();

		String[] submissionDate 	= upuCalendarForm.getSubmissionDate();

		String[] generateAfterToDate = upuCalendarForm.getGenerateAfterToDate();
		
		upuCalendarVOs = upuCalendarSession.getUPUCalendarVOs();
		newUPUCalendarVOs.add( createEmptyVO( companyCode ) );
		
		/** if no collection of VO create new one and one empty VO to it*/
		if( upuCalendarVOs == null || upuCalendarVOs.size() < 1 ){
			upuCalendarSession.setUPUCalendarVOs( newUPUCalendarVOs );
			upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = ADDDETAILS_SUCCESS;
			return;
		}
		
				
		String[] opFlag = upuCalendarForm.getOperationalFlag();
		
		/** 
		 * if hasRowsToShow is set updates VOs with values captured from form  
		 *  and adds records to updated collection of VOs
		 */
		if( opFlag != null && opFlag.length > 0 ){

			
			for( String opFlagItr : opFlag ) {

				upuCalendarVO = new UPUCalendarVO();
				
				upuCalendarVO.setCompanyCode(companyCode);

				if(!billingPeriod[index].equals(BLANK)){
					upuCalendarVO.setBillingPeriod( billingPeriod[index]);
				}else {
					upuCalendarVO.setBillingPeriod( null );
				}

				if(! fromDate[index].equals(BLANK)){
					upuCalendarVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).
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

		}	
		
		/** 
		 * checks whether collection contains VOs other than to delete. if has set the flag true
		 *  Also adds records to be deleted to updated collection of VOs
		 */
		for( UPUCalendarVO upuCalendarVOItr : upuCalendarVOs ) {

			if( upuCalendarVOItr.getOperationalFlag() != null ){

				if( upuCalendarVOItr.getOperationalFlag().equalsIgnoreCase( UPUCalendarVO.OPERATION_FLAG_DELETE)){

					newUPUCalendarVOs.add( upuCalendarVOItr );

				}
			}
		}

		upuCalendarSession.setUPUCalendarVOs( newUPUCalendarVOs ); // sets updated Vos to session

		log.log(Log.INFO, "\n \n ----The upuCalendarVOs on existing  is--->>",
				newUPUCalendarVOs);
		upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = ADDDETAILS_SUCCESS; // sets target
		log.exiting(CLASS_NAME,"execute");

	}

	/**
	 *
	 * creates an empty Vo object
	 * @return UPUCalendarVO
	 */
	private UPUCalendarVO createEmptyVO( String companyCode ) {

		log.entering(CLASS_NAME, "createEmptyVO");
		UPUCalendarVO upuCalendarVO =  new UPUCalendarVO();
		upuCalendarVO.setOperationalFlag(UPUCalendarVO.OPERATION_FLAG_INSERT);
		upuCalendarVO.setCompanyCode(companyCode);
		upuCalendarVO.setClearingHouse(BLANK);
		upuCalendarVO.setToDate(null);
		upuCalendarVO.setFromDate(null);
		upuCalendarVO.setGenerateAfterToDate(0);

		log.exiting(CLASS_NAME,"createEmptyVO");

		return upuCalendarVO;
	}
}

