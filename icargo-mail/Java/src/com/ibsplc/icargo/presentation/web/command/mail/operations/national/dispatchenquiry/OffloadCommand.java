/**
 * OffloadCommand.java Created on February 23, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.dispatchenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.OffloadDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.DispatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class OffloadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String SCREEN_ID_OFFLOAD = "mailtracking.defaults.national.offload";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String CONST_OFFLOADDISPATCH = "OFFLOAD";
	private static final String OUTBOUND = "O";
	private static final String FLIGHT_NOTCLOSED = "mailtracking.defaults.national.offload.error.flightnotclosed";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		log.entering("OffloadCommand", "execute");
		DispatchEnquiryForm dispatchEnquiryForm = (DispatchEnquiryForm) invocationcontext.screenModel;
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
		OffloadDispatchSession offloadSession  = getScreenSession(MODULE_NAME, SCREEN_ID_OFFLOAD);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<DespatchDetailsVO> despatchDetailsVOs = dispatchEnquirySession.getDespatchDetailsVO();
		Collection<DespatchDetailsVO> despatchVOSToOffload = new ArrayList<DespatchDetailsVO>();
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		String [] selectedRows = dispatchEnquiryForm.getRowId();		
		int row = 0;
		/*
		 *  VALIDATE WHETHER SELECTED DESPATCH IS INBOUND - 
		 *  Inbound despatches cannot be offloaded
		 */
		if(("I").equals(dispatchEnquiryForm.getOperationType())){
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.national.reassign.error.cannotOffloadInboundDsn");
			errorVOs = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errorVOs.add(errorVO);
			invocationcontext.addAllError(errorVOs);			
			invocationcontext.target = SCREENLOAD_FAILURE;
			return;
		}
		for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
			for (int j = 0; j < selectedRows.length ; j++) {
				if (row == Integer.parseInt(selectedRows[j])) {   
					despatchVOSToOffload.add(despatchDetailsVO);
				}
			}
			row++;
		}
		//Added to validate already offloaded bags by A-4810 for icrd-116957
		for(DespatchDetailsVO despatchDetailsVO:despatchVOSToOffload){
			if("Y".equals(despatchDetailsVO.getOffloadFlag())){
				errorVOs.add(new ErrorVO("mailtracking.defaults.national.reassign.error.cannotOffloadAlreadyOffloadedBags"));
				  break;
				  
				  
			}
		}
		
		if(errorVOs != null && errorVOs.size() >0){
			invocationcontext.addAllError(errorVOs);			
			invocationcontext.target = SCREENLOAD_FAILURE;
			return;
			
		}
		


		// 		Validating Flight Closure
		OperationalFlightVO operationalFlightVO =  new OperationalFlightVO();
		for(DespatchDetailsVO despatchDetailsVO : despatchVOSToOffload){
			operationalFlightVO.setLegSerialNumber(despatchDetailsVO.getLegSerialNumber());
			operationalFlightVO.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
			operationalFlightVO.setCarrierId(despatchDetailsVO.getCarrierId());		
			operationalFlightVO.setAirportCode(logonAttributes.getAirportCode());
			operationalFlightVO.setPol(logonAttributes.getAirportCode());
			operationalFlightVO.setCompanyCode(despatchDetailsVO.getCompanyCode());
			operationalFlightVO.setFlightNumber(despatchDetailsVO.getFlightNumber());			
			operationalFlightVO.setPou(despatchDetailsVO.getPou().toUpperCase());
			operationalFlightVO.setCarrierCode(despatchDetailsVO.getCarrierCode());
			operationalFlightVO.setFlightDate(despatchDetailsVO.getFlightDate());
			operationalFlightVO.setDirection(OUTBOUND);
		}

		boolean isFlightClosed = false;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		try {	    		
			isFlightClosed = 
				new MailTrackingDefaultsDelegate().isFlightClosedForMailOperations(operationalFlightVO);   		        		

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationcontext.addAllError(errors);
			invocationcontext.target = SCREENLOAD_FAILURE;
			return;
		}
		if(!isFlightClosed){
			ErrorVO errorVO = new ErrorVO(FLIGHT_NOTCLOSED);
			Object [] obj = {operationalFlightVO.getCarrierCode(),operationalFlightVO.getFlightNumber(),operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat()};
			errorVO.setErrorData(obj);
			errors.add(errorVO);
			invocationcontext.addAllError(errors);		
			invocationcontext.target = SCREENLOAD_SUCCESS;
			return;

		}

		offloadSession.setDespatchDetailsVOs(despatchVOSToOffload);
		dispatchEnquiryForm.setPopupFlag(CONST_OFFLOADDISPATCH);
		invocationcontext.target = SCREENLOAD_SUCCESS ;

	}

}
