/*
 * ValidateCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignDSNSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * Command class : ValidateCommand
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         Jul 1 2016         A-5991         Coding
 */
public class ValidateCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "validation_success";
   private static final String TARGET_FAILURE = "validation_failure";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";
   private static final String SCREEN_ID_REASSIGN_DSN = "mailtracking.defaults.reassigndsn";

   private static final String CONST_VIEW = "VIEW";
   private static final String CONST_REASSIGNMAILBAG = "reassignMailbag";
   private static final String CONST_REASSIGNDSN = "reassignDSN";
   private static final String CONST_SHOWDSN="showReassignDSNScreen";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ValidateCommand","execute");

    	MailExportListForm mailExportListForm =  (MailExportListForm)invocationContext.screenModel;
    	MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,SCREEN_ID);
    	ReassignDSNSession reassignDSNSession=getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN_DSN);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		MailAcceptanceVO mailAcceptanceVO = mailExportListSession.getMailAcceptanceVO();
		List<ContainerDetailsVO> containerDetailsVOs=(ArrayList<ContainerDetailsVO>)mailAcceptanceVO.getContainerDetails();
    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		Collection<DespatchDetailsVO> despatchDetailsVOs=null;
		ContainerDetailsVO containerDetailsVO=null;
		String selCont=mailExportListForm.getReCON();
        String arr[] =selCont.split(logonAttributes.getCompanyCode());        
		String status = mailExportListForm.getStatus();

		//Collection<DSNVO> dsnVOs = mailExportListSession.getDSNVOs();
		ArrayList<DSNVO> selectedvos = new ArrayList<DSNVO>();
		//String selectedDSNs = mailExportListForm.getSelDSN();

		if((containerDetailsVOs!=null && containerDetailsVOs.size()>0)
				&&(selCont !=null && selCont.length()>0)){
			containerDetailsVO=containerDetailsVOs.get(Integer.parseInt(arr[1]));
		}

		if(!CONST_VIEW.equals(status)){
			//	Validating Flight Closure
	    	boolean isFlightClosed = false;
	    	OperationalFlightVO operationalFlightVO = mailExportListSession.getOperationalFlightVO();
	    	if(operationalFlightVO!=null){
		    	try {
		    		isFlightClosed =
		    				mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);

				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				log
						.log(
								Log.FINE,
								"MailbagEnquiry-->OffloadMailCommand--->isFlightClosed----->",
								isFlightClosed);
				if(isFlightClosed){
					Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
							mailAcceptanceVO.getFlightNumber(),
							mailAcceptanceVO.getFlightDate().toString().substring(0,11)};
					invocationContext.addError(new ErrorVO("mailtracking.defaults.exportlist.msg.err.flightclosed",obj));
					mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		 	   		invocationContext.target =TARGET_SUCCESS;
		 	   		return;
				}
	    	}
		}
		//added by Sreejith For Reassign Mailbag
		DSNVO selectedvo = ((ArrayList<DSNVO>)containerDetailsVO.getDsnVOs())
		.get(Integer.parseInt(mailExportListForm.getReDSN()));
		if (CONST_REASSIGNMAILBAG.equals(status)) {

			/*
			 * VALIDATE WHETHER SELECTED DSN IS PLT ENABLED -
			 * only PLT enabled mailbags can be Reassigned
			 */

    		log.log(Log.FINE, "selected DSN to reassignMailbag --------->>",
					selectedvo);
			if (!(MailConstantsVO.FLAG_YES.equals(selectedvo.getPltEnableFlag()))) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.pltNotEnabled");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			int errorArrived = 0;
			String contArrived = "";
			if("Y".equals(containerDetailsVO.getArrivedStatus())){
				errorArrived = 1;
				if("".equals(contArrived)){
					contArrived = containerDetailsVO.getContainerNumber();
				}else{
					contArrived = new StringBuilder(contArrived)
					.append(",")
					.append(containerDetailsVO.getContainerNumber())
					.toString();
				}
			}

			if(errorArrived == 1){
				log.log(Log.INFO, ">>>>>>>>>>>>Container Already Arrived At Some Port<<<<<<<<<<");
				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailexportlist.mailbagalreadyarrived",new Object[]{contArrived}));
				mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
				return;
			}

			mailExportListForm.setStatus("showReassignMailbag");
			mailbagEnquiryFilterVO.setCarrierCode(containerDetailsVO.getCarrierCode());
			if(!("-1".equals(containerDetailsVO.getFlightNumber()))){
				mailbagEnquiryFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			}else{
				mailbagEnquiryFilterVO.setFlightNumber(null);
			}
			mailbagEnquiryFilterVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
			mailbagEnquiryFilterVO.setCarrierId(mailAcceptanceVO.getCarrierId());
			mailbagEnquiryFilterVO.setFlightDate(mailAcceptanceVO.getFlightDate());
			mailbagEnquiryFilterVO.setOoe(selectedvo.getOriginExchangeOffice());
			mailbagEnquiryFilterVO.setDoe(selectedvo.getDestinationExchangeOffice());
			mailbagEnquiryFilterVO.setCurrentStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			mailbagEnquiryFilterVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			mailbagEnquiryFilterVO.setDespatchSerialNumber(selectedvo.getDsn());
			mailbagEnquiryFilterVO.setYear(String.valueOf(selectedvo.getYear()));
			mailbagEnquiryFilterVO.setMailCategoryCode(selectedvo.getMailCategoryCode());
			mailbagEnquiryFilterVO.setMailSubclass(selectedvo.getMailSubclass());
			mailbagEnquiryFilterVO.setScanPort(logonAttributes.getAirportCode());
			mailbagEnquiryFilterVO.setFromExportList("exportlist");                

		}else if (CONST_VIEW.equals(status)) {

			/*
			 * VALIDATE WHETHER SELECTED DSN IS PLT ENABLED -
			 * only PLT enabled mailbags can be viewed
			 */
			if (!(MailConstantsVO.FLAG_YES.equals(selectedvo.getPltEnableFlag()))) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.pltNotEnabled");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			mailExportListForm.setStatus("showViewMailEnquiry");
			mailbagEnquiryFilterVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
			mailbagEnquiryFilterVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
			mailbagEnquiryFilterVO.setFlightDate(mailAcceptanceVO.getFlightDate());
			mailbagEnquiryFilterVO.setOoe(selectedvo.getOriginExchangeOffice());
			mailbagEnquiryFilterVO.setDoe(selectedvo.getDestinationExchangeOffice());
			mailbagEnquiryFilterVO.setCurrentStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			mailbagEnquiryFilterVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			mailbagEnquiryFilterVO.setDespatchSerialNumber(selectedvo.getDsn());
			mailbagEnquiryFilterVO.setYear(String.valueOf(selectedvo.getYear()));
			mailbagEnquiryFilterVO.setMailCategoryCode(selectedvo.getMailCategoryCode());
			mailbagEnquiryFilterVO.setMailSubclass(selectedvo.getMailSubclass());
			mailbagEnquiryFilterVO.setScanPort(logonAttributes.getAirportCode());
			mailbagEnquiryFilterVO.setFromExportList(MailConstantsVO.FLAG_YES);


		}else if(CONST_REASSIGNDSN.equals(status)){
			/*
			 * VALIDATE WHETHER SELECTED DSN IS PLT ENABLED -
			 * only PLT enabled mailbags can be viewed
			 */
			String mode=null;
			mode=mailExportListForm.getAssignToFlight();
			if (MailConstantsVO.FLAG_YES.equals(selectedvo.getPltEnableFlag())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.mailexportlist.msg.err.notdespatch");
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			mailExportListForm.setStatus(CONST_SHOWDSN);
			selectedvo.setContainerType(containerDetailsVO.getContainerType());
			log.log(Log.FINE, "$$$$$containerDetailsVO.getContainerNumber()",
					containerDetailsVO.getContainerNumber());
			log.log(Log.FINE, "containerDetailsVO.getContainerNumber()");
			selectedvo.setContainerNumber(containerDetailsVO.getContainerNumber());
			try {
				despatchDetailsVOs = new MailTrackingDefaultsDelegate()
															.findDespatchesOnDSN(selectedvo,mode);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			for(DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs){
				despatchDetailsVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				despatchDetailsVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				despatchDetailsVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				despatchDetailsVO.setFlightDate(mailAcceptanceVO.getFlightDate());
				despatchDetailsVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
				despatchDetailsVO.setPou(containerDetailsVO.getPou());
				despatchDetailsVO.setAcceptedPcsToDisplay(despatchDetailsVO.getAcceptedBags());
				despatchDetailsVO.setAcceptedWgtToDisplay(despatchDetailsVO.getAcceptedWeight());
				despatchDetailsVO.setAcceptedBags(0);
				//despatchDetailsVO.setAcceptedWeight(0);
				despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.WEIGHT,0.0));
			}
			reassignDSNSession.setDespatchDetailsVOs(despatchDetailsVOs);
		}
		mailExportListSession.setMailbagEnquiryFilterVO(mailbagEnquiryFilterVO);
		mailExportListSession.setDespatchDetailsVOs(despatchDetailsVOs);
		mailExportListSession.setSelectedDSNVOs(selectedvos);
		mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET_SUCCESS;

    	log.exiting("ValidateCommand","execute");

    }

}
