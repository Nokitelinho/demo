/**
 * ReturnCommand.java Created on January 18, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.offload;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.OffloadDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.OffloadDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class OffloadDispatchCommand  extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.offload";
	private static final String SCREEN_ID_ASSIGN = "mailtracking.defaults.national.assignmailbag";
	private static final String OUTBOUND = "O";	
	private static final String OFFLOAD_REASONCODE = "mailtracking.defaults.offload.reasoncode";
	private static final String ASG_MAIL_BAG = "ASG_MAIL_BAG";
	private static final String DSN_ENQUIRY = "DSN_ENQUIRY";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("OffloadCommand","execute");
		AssignMailBagSession assignMailBagSession = getScreenSession(MODULE_NAME, SCREEN_ID_ASSIGN);
		OffloadDispatchSession offloadSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		OffloadDispatchForm offloadForm = (OffloadDispatchForm)invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = null;		
		String fromScreen = offloadForm.getFromScreen();

		Boolean status = true;
		errors = validateForm(offloadForm);

		if (errors != null && errors.size() > 0) { 
			invocationContext.addAllError(errors);
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}

		Collection<ErrorVO> offloadErrors = new ArrayList<ErrorVO>();
		Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();		
		OffloadVO offloadVO = new OffloadVO();
		Page<DespatchDetailsVO> newDespatchDetailsVOs = new Page<DespatchDetailsVO>(new ArrayList<DespatchDetailsVO>(),0,0,0,0,0,false);
		if(ASG_MAIL_BAG.equals(fromScreen)){
			Collection<DSNVO> dsnvos = offloadSession.getSelectedDSNVO();
			FlightValidationVO flightValidationVO =assignMailBagSession.getFlightValidationVO();
			newDespatchDetailsVOs = populateDespatchDetailsVOs(dsnvos,offloadForm, flightValidationVO );
			offloadErrors = validateOffload(offloadForm,newDespatchDetailsVOs);		
			if(offloadErrors != null  && offloadErrors.size() > 0){
				invocationContext.addAllError(offloadErrors);	
				offloadForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
			for(DespatchDetailsVO despatchDetailsVO : newDespatchDetailsVOs){				
				despatchDetailsVO.setAcceptedBags(Integer.parseInt(offloadForm.getPieces()));
				//despatchDetailsVO.setAcceptedWeight(Double.valueOf(offloadForm.getWeight()));
				despatchDetailsVO.setAcceptedWeight(offloadForm.getWeightMeasure());//added by A-7371
				despatchDetailsVO.setStatedBags(Integer.parseInt(offloadForm.getPieces()));
				//despatchDetailsVO.setStatedWeight(Double.valueOf(offloadForm.getWeight()));
				despatchDetailsVO.setStatedWeight(offloadForm.getWeightMeasure());//added by A-7371
				// modified as part of fix icrd-16977 by a-4810
				despatchDetailsVO.setOffloadedDescription(handleReasonCodes(
						offloadForm.getReasonCode(),
						offloadSession));
				despatchDetailsVO.setOffloadedReason(offloadForm.getReasonCode());				
				despatchDetailsVO.setOffloadedRemarks(offloadForm.getOffloadRemarks());
				if(offloadForm.getOffloadRemarks()!=null) {
				 despatchDetailsVO.setRemarks(new StringBuilder().append(offloadForm.getOffloadRemarks()).append(",").append(despatchDetailsVO.getOffloadedDescription()).toString());
				}
			}
			



		}
		else if(DSN_ENQUIRY.equals(fromScreen)){
			newDespatchDetailsVOs = new Page<DespatchDetailsVO>(new ArrayList<DespatchDetailsVO>(offloadSession.getDespatchDetailsVOs()),0,0,0,0,0,false); 
			offloadErrors = validateOffload(offloadForm,newDespatchDetailsVOs);		
			if(offloadErrors != null  && offloadErrors.size() > 0){
				invocationContext.addAllError(offloadErrors);	
				offloadForm.setScreenStatusFlag(ComponentAttributeConstants.
						SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}

			for(DespatchDetailsVO despatchDetailsVO : newDespatchDetailsVOs){
				despatchDetailsVO.setAcceptedBags(Integer.parseInt(offloadForm.getPieces()));
				//despatchDetailsVO.setAcceptedWeight(Double.valueOf(offloadForm.getWeight()));
				despatchDetailsVO.setAcceptedWeight(offloadForm.getWeightMeasure());//added by A-7371
				despatchDetailsVO.setStatedBags(Integer.parseInt(offloadForm.getPieces()));
				//despatchDetailsVO.setStatedWeight(Double.valueOf(offloadForm.getWeight()));	
				despatchDetailsVO.setStatedWeight(offloadForm.getWeightMeasure());	//added by A-7371
				despatchDetailsVO.setOffloadedDescription(handleReasonCodes(
						offloadForm.getReasonCode(),
						offloadSession));
				despatchDetailsVO.setOffloadedReason(offloadForm.getReasonCode());				
				despatchDetailsVO.setOffloadedRemarks(offloadForm.getOffloadRemarks());
				if(offloadForm.getOffloadRemarks()!=null) {
				 despatchDetailsVO.setRemarks(new StringBuilder().append(offloadForm.getOffloadRemarks()).append(",").append(despatchDetailsVO.getOffloadedDescription()).toString());
				}
				despatchDetailsVO.setOffload(true);

			}
		}



		offloadVO = populateOffloadVO(newDespatchDetailsVOs );
		try {
			containerDetailsVOs = 
				new MailTrackingDefaultsDelegate().offload(offloadVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			status = false;
		}
		if(errors != null  && errors.size() > 0){
			invocationContext.addAllError(errors);	
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		if(status){
			offloadForm.setCloseFlag("Y");
		}
		offloadForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SCREENLOAD_SUCCESS;

	}
	/**
	 * 
	 * @param offloadForm
	 * @param newDespatchDetailsVOs
	 * @return offloadErrors
	 */
	private Collection<ErrorVO> validateOffload(
			OffloadDispatchForm offloadForm, Page<DespatchDetailsVO> newDespatchDetailsVOs) {
		Collection<ErrorVO> offloadErrors = new ArrayList<ErrorVO>();
		for(DespatchDetailsVO despatchDetailsVO : newDespatchDetailsVOs){
			//if(Integer.parseInt(offloadForm.getPieces()) > despatchDetailsVO.getAcceptedBags() || Double.valueOf(offloadForm.getWeight()) > despatchDetailsVO.getAcceptedWeight()){
			try {
				if(Integer.parseInt(offloadForm.getPieces()) > despatchDetailsVO.getAcceptedBags()
						|| Measure.compareTo(offloadForm.getWeightMeasure(), despatchDetailsVO.getAcceptedWeight())>0){
					offloadErrors.add(new ErrorVO("mailtracking.defaults.national.offload.error.acceptedweightlessthanoffloaded"));
				}
			} catch (NumberFormatException | UnitException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE,"",e.getMessage());
			}
		}
		return offloadErrors;
	}
	/**
	 * 
	 * @param dsnvos
	 * @param offloadForm
	 * @param flightValidationVO
	 * @return
	 */
	private Page<DespatchDetailsVO> populateDespatchDetailsVOs(
			Collection<DSNVO> dsnvos, OffloadDispatchForm offloadForm,
			FlightValidationVO flightValidationVO) {
		DespatchDetailsVO despatchDetailsVO = null;
		Page<DespatchDetailsVO> newDespatchDetailsVOs = new Page<DespatchDetailsVO>(new ArrayList<DespatchDetailsVO>(),0,0,0,0,0,false);
		Collection<DespatchDetailsVO> selectedDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		OffloadDispatchSession offloadSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		for(DSNVO dsnvo : dsnvos){
			
			despatchDetailsVO = new DespatchDetailsVO();			
			despatchDetailsVO.setCarrierCode(flightValidationVO.getCarrierCode());				
			despatchDetailsVO.setFlightDate(flightValidationVO.getStd());
			despatchDetailsVO.setAirportCode(logonAttributes.getAirportCode());		
			despatchDetailsVO.setOperationalFlag("I");
			despatchDetailsVO.setContainerNumber("BULK");
			despatchDetailsVO.setUldNumber("BULK");
			despatchDetailsVO.setContainerType("B");			
			despatchDetailsVO.setPrevStatedBags(dsnvo.getBags());
			despatchDetailsVO.setPrevStatedWeight(dsnvo.getWeight());
			despatchDetailsVO.setCompanyCode(dsnvo.getCompanyCode());
			despatchDetailsVO.setConsignmentNumber(dsnvo.getCsgDocNum());
			despatchDetailsVO.setConsignmentSequenceNumber(dsnvo.getCsgSeqNum());
			despatchDetailsVO.setConsignmentDate(dsnvo.getConsignmentDate());
			despatchDetailsVO.setPaCode(dsnvo.getPaCode());
			despatchDetailsVO.setOperationalFlag(dsnvo.getOperationFlag());
			despatchDetailsVO.setOwnAirlineCode(logonAttributes.getAirportCode());				
			despatchDetailsVO.setAcceptedDate(dsnvo.getAcceptedDate());
			despatchDetailsVO.setStatedBags(Integer.parseInt(offloadForm.getPieces()));
			//despatchDetailsVO.setStatedWeight(Double.valueOf(offloadForm.getWeight()));
			despatchDetailsVO.setStatedWeight(offloadForm.getWeightMeasure());//added by A-7371
			despatchDetailsVO.setAcceptedBags(dsnvo.getBags());
			despatchDetailsVO.setAcceptedWeight(dsnvo.getWeight());
			despatchDetailsVO.setAcceptedUser(logonAttributes.getUserId());
			despatchDetailsVO.setOriginOfficeOfExchange(dsnvo.getOriginExchangeOffice());
			despatchDetailsVO.setDestinationOfficeOfExchange(dsnvo.getDestinationExchangeOffice());
			despatchDetailsVO.setYear(dsnvo.getYear());
			despatchDetailsVO.setDsn(dsnvo.getDsn());
			despatchDetailsVO.setMailCategoryCode(dsnvo.getMailCategoryCode());
			despatchDetailsVO.setMailClass(dsnvo.getMailClass());
			despatchDetailsVO.setMailSubclass(dsnvo.getMailSubclass());			
			despatchDetailsVO.setCarrierId(dsnvo.getCarrierId());
			despatchDetailsVO.setFlightNumber(dsnvo.getFlightNumber());
			despatchDetailsVO.setFlightSequenceNumber(dsnvo.getFlightSequenceNumber());
			despatchDetailsVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			despatchDetailsVO.setSegmentSerialNumber(dsnvo.getSegmentSerialNumber());
			despatchDetailsVO.setPou(dsnvo.getPou());
			despatchDetailsVO.setOffloadedReason(offloadForm.getReasonCode());
			despatchDetailsVO.setOffloadedRemarks(offloadForm.getOffloadRemarks());
			despatchDetailsVO.setOffload(true);			
			despatchDetailsVO.setOffloadedDescription(handleReasonCodes(
					offloadForm.getReasonCode(),
					offloadSession));
			selectedDespatchDetailsVOs.add(despatchDetailsVO);
			newDespatchDetailsVOs.add(despatchDetailsVO);
		}

		return newDespatchDetailsVOs;
	}


	/**
	 * 
	 * @param reasonCode
	 * @param offloadSession
	 * @return
	 */
	private String handleReasonCodes(String reasonCode,
			OffloadDispatchSession offloadSession) {
		String reasonDesc = "";

		Collection<OneTimeVO> reasonCodes = offloadSession.getOneTimeVOs().get(OFFLOAD_REASONCODE);
		if(reasonCodes != null){
			for (OneTimeVO vo : reasonCodes) {
				if (vo.getFieldValue().equals(reasonCode)) {
					reasonDesc = vo.getFieldDescription();
					break;
				}
			}


		}
		return reasonDesc;
	}

	/**
	 * 
	 * @param mailManifestVO2 
	 * @param flightValidationVO2 
	 * @param newDespatchDetailsVOs 
	 * @param selectedDespatchDetailsVOs
	 * @param flightValidationVO
	 * @param offloadForm, MailManifestVO mailManifestVO 
	 * @return
	 */
	private OffloadVO populateOffloadVO(Page<DespatchDetailsVO> newDespatchDetailsVOs) {
		OffloadVO offloadVO = new OffloadVO();	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		for(DespatchDetailsVO despatchDetailsVO : newDespatchDetailsVOs){


			offloadVO.setCompanyCode(logonAttributes.getCompanyCode());
			offloadVO.setCarrierCode(despatchDetailsVO.getCarrierCode());
			offloadVO.setCarrierId(despatchDetailsVO.getCarrierId());
			offloadVO.setFlightDate(despatchDetailsVO.getFlightDate());
			offloadVO.setFlightNumber(despatchDetailsVO.getFlightNumber());
			offloadVO.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
			offloadVO.setLegSerialNumber(despatchDetailsVO.getLegSerialNumber());
			offloadVO.setPol(logonAttributes.getAirportCode());
			offloadVO.setOffloadType("D");
			offloadVO.setOffloadDSNs(newDespatchDetailsVOs);
			offloadVO.setUserCode(logonAttributes.getUserId());
			offloadVO.setDepartureOverride(true);
		}

		return offloadVO;
	}
	/**
	 * 
	 * @param offloadForm
	 * @return
	 */
	private Collection<ErrorVO> validateForm(OffloadDispatchForm offloadForm) {
		String pieces = offloadForm.getPieces();
		String weight = offloadForm.getWeight();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(pieces == null || pieces.trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.offload.error.piecesmandatory"));
		}
		if(weight == null || weight.trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.offload.error.weightmandatory"));
		}
		if(offloadForm.getReasonCode() == null || offloadForm.getReasonCode().trim().length() == 0){
			errors.add(new ErrorVO("mailtracking.defaults.national.offload.error.reasoncodemandatory"));
		}

		return errors;
	}

}
