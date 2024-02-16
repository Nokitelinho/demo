/*
 * ListSegmentsCommand.java Created on Feb 13, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.viewflownmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.ViewFlownMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.ViewFlownMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2449
 *
 */
public class ListSegmentsCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MRA flown");
	private static final String ACTION_SUCCESS= "action_success";
	private static final String ACTION_FAILURE= "action_failure";
	private static final String LIST_SUCCESS = "list_success";
	private static final String CLASS_NAME = "ListSegmentsCommand";
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREENID = "mra.flown.viewflownmail";
	private static final String SCREENID_DUPLICATE_FLIGHTS = "flight.operation.duplicateflight";
	
	private static final String NO_RESULTS_FOUND = "mra.flown.msg.err.noresultsfound";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		ViewFlownMailSession session = 
			(ViewFlownMailSession)getScreenSession(MODULE_NAME, SCREENID);
		ViewFlownMailForm form =
			(ViewFlownMailForm)invocationContext.screenModel;
		
		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size() > 0){
			log.log(Log.INFO, "errors-------->", invocationContext.getErrors());
			invocationContext.target=ACTION_FAILURE;
			return;
		}
	
		else if(("Y").equals(form.getDuplicateFlightFlag())){
			log.log(Log.INFO, "duplicateFlightFlag---->", form.getDuplicateFlightFlag());
			log.log(1,"duplicateflights exists......");
			invocationContext.target=ACTION_FAILURE;
			return;
		}
		else{
			log.log(Log.INFO, "duplicateFlightFlag----->", form.getDuplicateFlightFlag());
			log.log(log.INFO,"Entering Listing for Segments...");
			
			FlightValidationVO flightValidationVO = session.getFlightDetails();
			log.log(Log.INFO,
					"flightValidationVO in ListSegment command class---->",
					flightValidationVO);
			ErrorVO error=null;  
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			Collection<FlownMailSegmentVO> flownMailSegmentVOs = 
				new ArrayList<FlownMailSegmentVO>();
			
			FlownMailFilterVO filterVO = new FlownMailFilterVO();
			log.log(Log.INFO, "Flight Number from form------>", form.getFlightNumber());
			log.log(Log.INFO, "Flight Date from form------->", form.getFlightDate());
			filterVO.setFlightCarrierCode(form.getCarrierCode());
			filterVO.setFlightNumber(form.getFlightNumber());
			
			LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			flightDate.setDate(form.getFlightDate());
			filterVO.setFlightDate(flightDate);
			
			filterVO.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
			filterVO.setFlightSequenceNumber((int)flightValidationVO.getFlightSequenceNumber());
			filterVO.setCompanyCode(flightValidationVO.getCompanyCode());
			
			
			try{
				flownMailSegmentVOs = new MailTrackingMRADelegate().findFlightDetails(filterVO);
			}catch (BusinessDelegateException businessDelegateException) {
				errors=handleDelegateException(businessDelegateException);
				
			}
			
			session.setSegmentDetails(flownMailSegmentVOs);
			log.log(Log.INFO, "FlownMailSegmentVOs from session-------->",
					session.getSegmentDetails());
			if(flownMailSegmentVOs == null){
				error = new ErrorVO(NO_RESULTS_FOUND);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			
			if(errors != null && errors.size() > 0){
				log.log(Log.FINE,"!!!inside errors!= null");
				invocationContext.addAllError(errors);
				
				invocationContext.target=ACTION_FAILURE;
				return;
			}	
			if(flownMailSegmentVOs.size() == 1){
				invocationContext.target = LIST_SUCCESS;
				return;
			}
		}
		form.setListSegmentsFlag("Y");
		invocationContext.target=ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	
}
