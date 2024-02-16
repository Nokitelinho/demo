/**
 * ReturnCommand.java Created on February 22, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.dispatchenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.DispatchEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReturnDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.DispatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ReturnCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.dispatchEnquiry";
	private static final String SCREEN_ID_RETURN = "mailtracking.defaults.national.return";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";	
	private static final String SCREENLOAD_FAILURE = "screenload_failure";	
	private static final String CONST_RETURNDISPATCH = "RETURN";
	private static final String OUTBOUND = "O";
	private static final String FLIGHT_CLOSED = "mailtracking.defaults.national.return.error.flightclosed";
	/**
	 * @param invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {

		DispatchEnquiryForm dispatchEnquiryForm = (DispatchEnquiryForm) invocationcontext.screenModel;
		DispatchEnquirySession dispatchEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
		ReturnDispatchSession returnSession = getScreenSession( MODULE_NAME, SCREEN_ID_RETURN);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Page<DespatchDetailsVO> despatchDetailsVOs = dispatchEnquirySession.getDespatchDetailsVO();
		Collection<DSNVO> dsnvosToReturn = new ArrayList<DSNVO>();
		Collection<DespatchDetailsVO> despatchDetailsVOsToReturn = new ArrayList<DespatchDetailsVO>();		
		FlightValidationVO flightValidationVO = null;
		String [] selectedRows = dispatchEnquiryForm.getRowId();		
		int row = 0;	
		if(selectedRows != null && selectedRows.length >0){
			DespatchDetailsVO despatchDetailsVO = null;
			DSNVO dsnvo = null;			
			for(String selectedRow : selectedRows){
				despatchDetailsVO = despatchDetailsVOs.get(Integer.parseInt(selectedRow));
				dsnvo = new DSNVO();
				dsnvo.setFlightNumber(despatchDetailsVO.getFlightNumber());
				dsnvo.setCarrierId(despatchDetailsVO.getCarrierId());
				dsnvo.setCarrierCode(despatchDetailsVO.getCarrierCode());
				dsnvo.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
				dsnvo.setSegmentSerialNumber(despatchDetailsVO.getSegmentSerialNumber());
				dsnvo.setPou(despatchDetailsVO.getPou());
				dsnvo.setPol(logonAttributes.getAirportCode());
				dsnvo.setAcceptedDate(despatchDetailsVO.getAcceptedDate());
				dsnvo.setBags(despatchDetailsVO.getAcceptedBags());
				dsnvo.setCompanyCode(logonAttributes.getCompanyCode());
				dsnvo.setConsignmentDate(despatchDetailsVO.getConsignmentDate());
				dsnvo.setCsgDocNum(despatchDetailsVO.getConsignmentNumber());
				dsnvo.setContainerNumber("BULK");
				dsnvo.setContainerType("B");
				dsnvo.setCsgSeqNum(despatchDetailsVO.getConsignmentSequenceNumber());
				dsnvo.setDestination(despatchDetailsVO.getDestination());
				dsnvo.setDestinationExchangeOffice(despatchDetailsVO.getDestinationOfficeOfExchange());
				dsnvo.setDsn(despatchDetailsVO.getDsn());
				dsnvo.setYear(despatchDetailsVO.getYear());
				dsnvo.setMailCategoryCode(despatchDetailsVO.getMailCategoryCode());
				dsnvo.setMailClass(despatchDetailsVO.getMailClass());
				dsnvo.setMailSubclass(despatchDetailsVO.getMailSubclass());
				dsnvo.setOrigin(despatchDetailsVO.getOriginOfficeOfExchange());
				dsnvo.setOriginExchangeOffice(despatchDetailsVO.getOriginOfficeOfExchange());
				dsnvo.setPaCode(despatchDetailsVO.getPaCode());
				dsnvo.setStatedBags(despatchDetailsVO.getStatedBags());
				dsnvo.setStatedWeight(despatchDetailsVO.getStatedWeight());
				dsnvo.setWeight(despatchDetailsVO.getAcceptedWeight());					
				dsnvo.setPrevStatedBags(despatchDetailsVO.getPrevStatedBags());
				dsnvo.setPrevStatedWeight(despatchDetailsVO.getPrevStatedWeight());
				dsnvo.setRemarks(despatchDetailsVO.getRemarks());
				//Added by a-4810 for icrd-19343 for returning from offloaded despathes
				if(despatchDetailsVO.getFlightNumber() != null){
				if("-1".equals(despatchDetailsVO.getFlightNumber() )){
					dsnvo.setCarrierCode(logonAttributes.getOwnAirlineCode());
					dsnvo.setContainerNumber(despatchDetailsVO.getContainerNumber());
				}
				}
				if(despatchDetailsVO.getFlightNumber() != null &&
						despatchDetailsVO.getFlightSequenceNumber() >0 
						&& despatchDetailsVO.getFlightDate() != null){
					dispatchEnquirySession.setFlightValidationVO(populateFlightValidationVO(despatchDetailsVO));
				}
				dsnvosToReturn.add(dsnvo);
				despatchDetailsVOsToReturn.add(despatchDetailsVO);

			}

		}
		// 		Validating Flight Closure
		OperationalFlightVO operationalFlightVO =  new OperationalFlightVO();
		for(DespatchDetailsVO despatchDetailsVO : despatchDetailsVOsToReturn){
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
		if(isFlightClosed){
			ErrorVO errorVO = new ErrorVO(FLIGHT_CLOSED);
			Object [] obj = {operationalFlightVO.getCarrierCode(),operationalFlightVO.getFlightNumber(),operationalFlightVO.getFlightDate().toDisplayDateOnlyFormat()};
			errorVO.setErrorData(obj);
			errors.add(errorVO);
			invocationcontext.addAllError(errors);		
			invocationcontext.target = SCREENLOAD_SUCCESS;
			return;

		}
		returnSession.setSelectedDSNVO(dsnvosToReturn);
		dispatchEnquiryForm.setPopupFlag(CONST_RETURNDISPATCH);
		invocationcontext.target = SCREENLOAD_SUCCESS;
	}
	
	/**
	 * 
	 * @param despatchDetailsVO
	 * @return
	 */
	private FlightValidationVO populateFlightValidationVO(DespatchDetailsVO despatchDetailsVO){
		FlightValidationVO flightValidationVO  = null;
		
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(despatchDetailsVO.getCompanyCode());
		flightFilterVO.setFlightDate(despatchDetailsVO.getFlightDate());
		flightFilterVO.setCarrierCode(despatchDetailsVO.getCarrierCode());
		flightFilterVO.setFlightCarrierId(despatchDetailsVO.getCarrierId());
		flightFilterVO.setFlightNumber(despatchDetailsVO.getFlightNumber());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setAirportCode(despatchDetailsVO.getAirportCode());
		
		Collection<FlightValidationVO> flightValidationVOs =  null;
		try{
		flightValidationVOs = new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		if(flightValidationVOs != null && flightValidationVOs.size() >0){
			flightValidationVO = flightValidationVOs.iterator().next();

		}
		return flightValidationVO;
		
	}

}
