/*
 * SaveUCMINOUTMessageCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;

import com.ibsplc.icargo.framework.session.ApplicationSession;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2046
 *
 */
public class SaveUCMINOUTMessageCommand extends AddULDDetailsCommand {
	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private static final String PAGEURL = "fromSendUCMInOut";
	private static final String SCREENID = "uld.defaults.ucmerrorlog";
	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_Messaging");

	//A-5116 for ICRD-50252
	private static final String ERR_DUP_FIRST = "E1";

	//private static final String ERR_DUP_SECOND = "E2";

	//private static final String ERR_MISMATCH = "E9";

	//private static final String ERR_INAGNSTOUT = "E13";
	
	//private static final String ERR_ULDNOTINSTOCK = "E7";

	//private static final String CONTENT = "uld.defaults.contentcodes";
	
	private static final String MESSAGE_SEND_FLAG_PENDING = "P";
	//Added by A-6991 for CR ICRD-177310 Starts
	 private static final String MSG_MODULE_NAME = "msgbroker.message";
	 private static final String MSG_SCREEN_ID = "msgbroker.message.listmessages";
	//Added by A-6991 for CR ICRD-177310 Ends
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("Save", "UCM IN OUT");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		UCMErrorLogSession ucmErrorLogSession = (UCMErrorLogSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSession appSession = getApplicationSession();
		ULDFlightMessageReconcileVO reconcileVO = session
				.getMessageReconcileVO();
		//Added by A-7359 fro ICRD-259943 
		form.setUcmOut(session.getMessageStatus());
		Collection<String> destinations = session.getOutDestinations();
		reconcileVO.setPous(destinations);
		ArrayList<ULDFlightMessageReconcileDetailsVO> reconcileDetails = (ArrayList<ULDFlightMessageReconcileDetailsVO>)reconcileVO
				.getReconcileDetailsVOs();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();

		log.log(Log.FINE, "session.getMessageStatus()--->", session.getMessageStatus());
		log.log(Log.FINE, "form.getOutConfirmStatus()--->", form.getOutConfirmStatus());
		log.log(Log.FINE, "form.getUcmVOStatus()--->", form.getUcmVOStatus());
		log.log(Log.FINE, "updateULDDetails--------------------->$$$$$$$$$$$$$$$$$$$$$");
			updateULDDetails(reconcileDetails, form, reconcileVO);
			//errors = validateULDDetails(reconcileDetails, form);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "inside errors--------------------->$$$$$$$$$$$$$$$$$$$$$");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				form.setIsUcmSent("N");
				return;

			}
			ArrayList<String> uldNumbersForValidation = new ArrayList<String>();
			ArrayList<String> stations = new ArrayList<String>();
			// Added on 9th April for bug num ULD 287--starts
			ArrayList<String> duplicateULDs=new ArrayList<String>();
			// To validte ULD number format
			if (reconcileVO.getReconcileDetailsVOs() != null
					&& reconcileVO.getReconcileDetailsVOs().size() > 0) {
											
				for (ULDFlightMessageReconcileDetailsVO uldDetailsVO : reconcileVO
						.getReconcileDetailsVOs()) {
					uldNumbersForValidation.add(uldDetailsVO.getUldNumber());
					stations.add(uldDetailsVO.getPou());
				}
				// To validate duplicate ULD s
				int len=uldNumbersForValidation.size();
				boolean dupUldFlag=false;
				for(int i=0 ;i<len ;i++){
					String firstULD=uldNumbersForValidation.get(i);
					for(int j=i+1 ;j < len ;j++){
						String secondULD=uldNumbersForValidation.get(j);
						if(firstULD==secondULD || firstULD.equals(secondULD)){
							log.log(Log.FINE, "firstULD and secondULD are equal");
							dupUldFlag=true;
							if(!duplicateULDs.contains(firstULD)){
								duplicateULDs.add(firstULD);
							}
						}
					}
					
				}
				if(dupUldFlag){
					if (duplicateULDs != null && duplicateULDs.size() > 0) {
						int size = duplicateULDs.size();
						for (int i = 0; i < size; i++) {
							ErrorVO error = new ErrorVO(
									"uld.defaults.ucminout.msg.err.duplicateUldNumber",
									new Object[] { ((ArrayList<String>) duplicateULDs)
											.get(i) });
							errors.add(error);
						}
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						log.log(Log.INFO, "reconcileVO after dup error---->>",
								reconcileVO);
						form.setIsUcmSent("N");
						//Added by A-7359 fro ICRD-259943 
						form.setUcmOut(session.getMessageStatus());
						return;
					}
				}
				// Validate invalid ULD numbers
				Collection<String> invalidUlds = null;
	
				log.log(Log.FINE, "before calling validateULDFormat",
						invalidUlds);
				invalidUlds = validateULDFormat(appSession, uldNumbersForValidation);
				log.log(Log.FINE, "invalid uldsss--------->", invalidUlds);
				ErrorVO error = null;
	
				if (invalidUlds != null && invalidUlds.size() > 0) {
					int size = invalidUlds.size();
					for (int i = 0; i < size; i++) {
						error = new ErrorVO(
								"uld.defaults.ucminout.msg.err.invaliduldformat",
								new Object[] { ((ArrayList<String>) invalidUlds)
										.get(i) });
						errors.add(error);
					}
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					form.setIsUcmSent("N");
					return;
				}
			}
			// Added on 9th April for bug num ULD 287--ends
			
			if (reconcileDetails != null && reconcileDetails.size() > 0) {
			log.log(Log.FINE, "Special Information--------------------->", form.getUcmIn());
			if (form.getUcmIn() != null && form.getUcmIn().trim().length() > 0) {
				reconcileVO
						.setSpecialInformation(form.getUcmIn().toUpperCase());
			}
			
			// Commented for ULD 287
			// It was never entering into this condition
			/*

			Collection<String> uldNumbersForValidation = new ArrayList<String>();
			for (ULDFlightMessageReconcileDetailsVO uldDetailsVO : reconcileDetails) {
				uldNumbersForValidation.add(uldDetailsVO.getUldNumber());
			}
			Collection<String> invalidUlds = null;

			invalidUlds = validateULDFormat(appSession, uldNumbersForValidation);
			log.log(Log.FINE, "invalid uldsss--------->" + invalidUlds);
			ErrorVO error = null;

			if (invalidUlds != null && invalidUlds.size() > 0) {
				int size=invalidUlds.size();
				for (int i = 0; i < size; i++) {
					error = new ErrorVO(
							"uld.defaults.ucminout.msg.err.invaliduldformat",
							new Object[] { ((ArrayList<String>) invalidUlds)
									.get(i) });
					errors.add(error);
				}

				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}*/

		}
		Collection<ULDFlightMessageReconcileVO> reconcileVOs = new ArrayList<ULDFlightMessageReconcileVO>();
		FlightValidationVO flightValidationVO = session
				.getFlightValidationVOSession();
		for(int i=0 ;i<stations.size() ;i++){
			log.log(Log.FINE, "stations", stations.get(i));
		}
		try {
			log.log(Log.FINE, "trueee");
			if(delegate.validateFlightArrival(flightValidationVO,stations)){
				ErrorVO error = new ErrorVO("uld.defaults.ucminout.msg.err.flightalreadyarrived");
				errors.add(error);
				log.log(Log.FINE, "trueee");
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		} catch (BusinessDelegateException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		}
		
		/*
		 * for construction of separate vo for in and out of ucm messaging
		 */
		if ("IN".equals(session.getMessageStatus())
				&& "Y".equals(form.getOutConfirmStatus())) {
			Collection<ULDFlightMessageReconcileDetailsVO> newDetails = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
			newDetails = constructReconcileVOsForSave(reconcileVO);
			if (newDetails != null && newDetails.size() > 0) {
				reconcileVO.getReconcileDetailsVOs().removeAll(newDetails);
				ULDFlightMessageReconcileVO newReconcileVO = new ULDFlightMessageReconcileVO();
				newReconcileVO
						.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
				newReconcileVO.setReconcileDetailsVOs(newDetails);
				populateMessageVO(flightValidationVO, newReconcileVO, form,
						session);
				//added by a-3459 as part of bug 29943 starts
				if(flightValidationVO.getAtd() != null){
					newReconcileVO.setActualDate(flightValidationVO.getAtd());
				}else if(flightValidationVO.getEtd() != null){
					newReconcileVO.setActualDate(flightValidationVO.getEtd());
				}else{
					newReconcileVO.setActualDate(flightValidationVO.getStd());
				}	
				//added by a-3459 as part of bug 29943 ends
				newReconcileVO.setMessageType("OUT");
				reconcileVOs.add(newReconcileVO);
			}
		}
		populateMessageVO(flightValidationVO, reconcileVO, form, session);
		

		//Added by A-6991 for CR ICRD-177310 Starts
			
				//user entered despatch details
		Collection<MessageDespatchDetailsVO> detailsVOs = null;
		ListMessageSession listMessageSession = (ListMessageSession) getScreenSession(
				MSG_MODULE_NAME, MSG_SCREEN_ID);
		detailsVOs = listMessageSession.getDespatchDetails();

		if (listMessageSession.getAutoForwardDetails() != null) {
			if (detailsVOs == null) {
				detailsVOs = new ArrayList<MessageDespatchDetailsVO>();
			}
			detailsVOs
					.addAll(populateMessageDespatchDetailsVOs(listMessageSession
							.getAutoForwardDetails()));
		}

		reconcileVO.setMsgDsptcDetailsVOs(detailsVOs);
						
		//Added by A-6991 for CR ICRD-177310 Ends
		reconcileVOs.add(reconcileVO);
		log.log(Log.FINE, "VOS to server----------------->", reconcileVOs);
		ULDFlightMessageReconcileVO reconcileVOFromServer = null;
		Collection<ULDFlightMessageReconcileVO> reconcileVOsFromServer = new ArrayList<ULDFlightMessageReconcileVO>();
		if ("N".equals(form.getUcmVOStatus())) {
			log.log(Log.FINE, "\n\n\nSAVE UCM MESSAGE");
			log
					.log(
							Log.FINE,
							"reconcileVOs to delegate----------saveULDFlightMessageReconcile------->>>",
							reconcileVOs);
			try {
				reconcileVOsFromServer = delegate
						.saveULDFlightMessageReconcile(reconcileVOs);
			} catch (BusinessDelegateException exception) {
				errors = handleDelegateException(exception);
				exception.getMessage();
			}

			// Added by A-6991 for CR ICRD-177310
			for (ULDFlightMessageReconcileVO msgSndChk : reconcileVOsFromServer) {

				if (msgSndChk.getErrorCode() != null) {
					msgSndChk.setMessageSendFlag("P");
				}

			}

			// Added by A-6991 for CR ICRD-177310
	
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_SUCCESS;
				//Added by A-7359 fro ICRD-259943 
				form.setIsUcmSent("N");
				return;				
			}
			log.log(Log.FINE, "Collection from server----------------->",
					reconcileVOsFromServer);
			reconcileVOFromServer = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer)
					.get(0);
			//Added by A-5116 for ICRD-50252 start
			 if (ERR_DUP_FIRST.equals(reconcileVOFromServer.getErrorCode())) {
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e1");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					form.setIsUcmSent("N");
					invocationContext.addAllError(errors);
			 }
			//end
			Collection<ULDFlightMessageReconcileDetailsVO> uldVosToRemove = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
			if (reconcileVOFromServer.getReconcileDetailsVOs() != null
					&& reconcileVOFromServer.getReconcileDetailsVOs().size() > 0) {
				for (ULDFlightMessageReconcileDetailsVO uldDetailsVo : reconcileVOFromServer
						.getReconcileDetailsVOs()) {
					if (ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_DELETE
							.equals(uldDetailsVo.getOperationFlag())) {
						uldVosToRemove.add(uldDetailsVo);
					} else {
						uldDetailsVo.setOperationFlag(null);
					}
				}
			}

			if (uldVosToRemove != null && uldVosToRemove.size() > 0) {
				reconcileVOFromServer.getReconcileDetailsVOs().removeAll(
						uldVosToRemove);
			}
			reconcileVOFromServer
					.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_UPDATE);
			log.log(Log.FINE, "Collection from server-----------------...>",
					reconcileVOsFromServer);
			session.setMessageReconcileVO(reconcileVOFromServer);
			//Added by A-7359 for ICRD -192413 starts here
			if (reconcileVOFromServer.getErrorCode() != null 
					&& reconcileVOFromServer.getErrorCode().trim().length() > 0) {
				populateUCMError(invocationContext, ucmErrorLogSession, errors,
						reconcileVOFromServer);
				form.setIsUcmSent("N");
				return;
			}
			for(ULDFlightMessageReconcileDetailsVO uLDFlightMessageReconcileDetailsVO : reconcileVOFromServer.getReconcileDetailsVOs()){ 
					if(uLDFlightMessageReconcileDetailsVO.getErrorCode() != null){  
						populateUCMError(invocationContext, ucmErrorLogSession, errors,
								reconcileVOFromServer);
						form.setIsUcmSent("N");
						return;
					}
				}
			
			//Added by A-7359 for ICRD -192413 ends here
			/* commented by A-5142 for ICRD-3814,If any errors exist, show a common message, instead of specific ones
			 * since specific errors can be checked in UCM error logs screen
			 
			if (reconcileVOFromServer.getErrorCode() != null
					&& reconcileVOFromServer.getErrorCode().trim().length() > 0) {
				if (ERR_DUP_FIRST.equals(reconcileVOFromServer.getErrorCode())) {
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e1");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);

				}else if(ERR_DUP_SECOND.equals(reconcileVOFromServer.getErrorCode())){
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e2");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
				}else if(ERR_MISMATCH.equals(reconcileVOFromServer.getErrorCode())){
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e9");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
				}else if(ERR_INAGNSTOUT.equals(reconcileVOFromServer.getErrorCode())){
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e13");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
				}else if (ERR_ULDNOTINSTOCK.equals(reconcileVOFromServer.getErrorCode())){
					log.log(Log.FINE, "inside errors--------------------->$$$$$$$$$$$$$$$$$$$$$");
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e7");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
				}
				}*/

			// if MessageSendFlag is P, message not send due to errors in processing, added by A-5142 for ICRD-3814
			//Modified by A-5116 for ICRD-50252
			if(reconcileVOFromServer.getMessageSendFlag() != null && reconcileVOFromServer.getMessageSendFlag().equalsIgnoreCase(MESSAGE_SEND_FLAG_PENDING)){
					form.setIsUcmSent("N");
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.generror");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
			}else{
				//Added by A-5116
				if (!ERR_DUP_FIRST.equals(reconcileVOFromServer.getErrorCode())) {
				form.setIsUcmSent("Y");
				}
			}
			clearForm(form);
			form.setOrigin(getApplicationSession().getLogonVO()
					.getAirportCode().toUpperCase());
			form.setOutConfirmStatus(BLANK);
			invocationContext.target = SAVE_SUCCESS;
			
			return;
		} else if ("Y".equals(form.getUcmVOStatus())) {
			log.log(Log.FINE, "\n\n\nSAVE IN OUT UCM MESSAGE");

		    ULDFlightMessageReconcileVO reconcileINVOFromServer = null;
		    ULDFlightMessageReconcileVO reconcileOUTVOFromServer = null;
			//Collection<ULDFlightMessageReconcileVO> reconcileVOsFromServer = new ArrayList<ULDFlightMessageReconcileVO>();

log.log(Log.FINE,
		"reconcileVOs to delegate----------saveINOUTMessage------->>>",
		reconcileVOs);
			try {
				reconcileVOsFromServer=delegate.saveINOUTMessage(reconcileVOs);
			} catch (BusinessDelegateException ex) {
				errors = handleDelegateException(ex);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_SUCCESS;
				form.setIsUcmSent("Y");
				return;

			}


			log.log(Log.FINE, "Collection from server----------------->>>",
					reconcileVOsFromServer);
			if(reconcileVOsFromServer.size() > 1){
				if(("IN").equals(
						(((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer).get(0)).getMessageType())){
					reconcileINVOFromServer = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer)
						.get(0);
					reconcileOUTVOFromServer = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer)
					.get(1);
				}else{
					reconcileINVOFromServer = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer)
					.get(1);
					reconcileOUTVOFromServer = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer)
					.get(0);
				}
			}else{
				if(("IN").equals(
						(((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer).get(0)).getMessageType())){
					reconcileINVOFromServer = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer)
										.get(0);
				}else{
					reconcileOUTVOFromServer = ((ArrayList<ULDFlightMessageReconcileVO>) reconcileVOsFromServer)
					.get(0);
				}
			}

			log.log(Log.FINE, "IN VO from server----------------->",
					reconcileINVOFromServer);
			log.log(Log.FINE, "OUT VO from server----------------->",
					reconcileOUTVOFromServer);
			if(reconcileVOsFromServer.size() > 1){
				if(reconcileINVOFromServer.getMessageSendFlag().equalsIgnoreCase(MESSAGE_SEND_FLAG_PENDING)){
					form.setIsUcmSent("N");
					ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.generror");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
				}
				/* commented by A-5142 for ICRD-3814
			
				if(reconcileINVOFromServer.getErrorCode()!= null
						&& reconcileINVOFromServer.getErrorCode().trim().length() > 0) {
					if(reconcileOUTVOFromServer.getErrorCode()!= null
						&& reconcileOUTVOFromServer.getErrorCode().trim().length() > 0) {
						if(ERR_DUP_SECOND.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e1e2");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}else if(ERR_MISMATCH.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e1e9");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}else if(ERR_INAGNSTOUT.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e1e13");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}
						else if (ERR_ULDNOTINSTOCK.equals(reconcileVOFromServer.getErrorCode())){
							log.log(Log.FINE, "inside errors--------------------->$$$$$$$$$$$$$$$$$$$$$");
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e7");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}
					}else{
						if(ERR_DUP_SECOND.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e2");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}else if(ERR_MISMATCH.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e9");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}else if(ERR_INAGNSTOUT.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e13");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}
						else if (ERR_ULDNOTINSTOCK.equals(reconcileVOFromServer.getErrorCode())){
							log.log(Log.FINE, "inside errors--------------------->$$$$$$$$$$$$$$$$$$$$$");
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e7");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}
					}
				}else{
					if (ERR_DUP_FIRST.equals(reconcileOUTVOFromServer.getErrorCode())) {
						ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e1");
						error.setErrorDisplayType(ErrorDisplayType.INFO);
						errors.add(error);
						invocationContext.addAllError(errors);
					}
				}
			*/}else{
				if(reconcileINVOFromServer != null){
					
					if(reconcileINVOFromServer.getMessageSendFlag().equalsIgnoreCase(MESSAGE_SEND_FLAG_PENDING)){
						form.setIsUcmSent("N");
						ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.generror");
						error.setErrorDisplayType(ErrorDisplayType.INFO);
						errors.add(error);
						invocationContext.addAllError(errors);
					}
					/* commented by A-5142 for ICRD-3814
					if(reconcileINVOFromServer.getErrorCode()!= null
						&& reconcileINVOFromServer.getErrorCode().trim().length() > 0){
						if(ERR_DUP_SECOND.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e2");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}else if(ERR_MISMATCH.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e9");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}else if(ERR_INAGNSTOUT.equals(reconcileINVOFromServer.getErrorCode())){
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e13");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}else if (ERR_ULDNOTINSTOCK.equals(reconcileVOFromServer.getErrorCode())){
							log.log(Log.FINE, "inside errors--------------------->$$$$$$$$$$$$$$$$$$$$$");
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e7");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}						
					}
				*/}else{
					if(reconcileOUTVOFromServer != null){
						
						if(reconcileOUTVOFromServer.getMessageSendFlag().equalsIgnoreCase(MESSAGE_SEND_FLAG_PENDING)){
							form.setIsUcmSent("N");
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.generror");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}
						/*commented by A-5142 for ICRD-3814
						 * if (ERR_DUP_FIRST.equals(reconcileOUTVOFromServer.getErrorCode())) {
							ErrorVO error=new ErrorVO("uld.defaults.ucminout.ucmerror.e1");
							error.setErrorDisplayType(ErrorDisplayType.INFO);
							errors.add(error);
							invocationContext.addAllError(errors);
						}*/
					}
				}
			}
			Collection<ULDFlightMessageReconcileDetailsVO> uldVos =
				new ArrayList<ULDFlightMessageReconcileDetailsVO>();
			if(reconcileINVOFromServer != null){
				uldVos.addAll(reconcileINVOFromServer.getReconcileDetailsVOs());
			}
			if(reconcileOUTVOFromServer != null){
				uldVos.addAll(reconcileOUTVOFromServer.getReconcileDetailsVOs());
			}
			reconcileVOFromServer =new ULDFlightMessageReconcileVO();
			try{
			BeanHelper.copyProperties(reconcileVOFromServer,
					reconcileINVOFromServer);
			}catch (SystemException systemException) {
				systemException.getMessage();
			}
			reconcileVOFromServer.setMessageType("IN");
			reconcileVOFromServer.setReconcileDetailsVOs(uldVos);


			Collection<ULDFlightMessageReconcileDetailsVO> uldVosToRemove =
				new ArrayList<ULDFlightMessageReconcileDetailsVO>();
			if (reconcileVOFromServer.getReconcileDetailsVOs() != null
					&& reconcileVOFromServer.getReconcileDetailsVOs().size() > 0) {
				for (ULDFlightMessageReconcileDetailsVO uldDetailsVo : reconcileVOFromServer
						.getReconcileDetailsVOs()) {
					if (ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_DELETE
							.equals(uldDetailsVo.getOperationFlag())) {
						uldVosToRemove.add(uldDetailsVo);
					} else {
						uldDetailsVo.setOperationFlag(null);
					}
				}
			}

			if (uldVosToRemove != null && uldVosToRemove.size() > 0) {
				reconcileVOFromServer.getReconcileDetailsVOs().removeAll(
						uldVosToRemove);
			}
			reconcileVOFromServer
					.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_UPDATE);



			log.log(Log.FINE,
					"After Setting fields vo on screen----------------->",
					reconcileVOsFromServer);
			session.setMessageReconcileVO(reconcileVOFromServer);

			/*form.setOrigin(getApplicationSession().getLogonVO()
					.getAirportCode().toUpperCase());
			form.setCarrierCode(BLANK);
			form.setFlightDate(BLANK);
			form.setFlightNo(BLANK);
			form.setOrigin(getApplicationSession().getLogonVO()
					.getAirportCode());
			form.setDestination(BLANK);
			form.setUcmIn(BLANK);
			form.setUcmOut("OUT");
			form.setArrivalTime(BLANK);
			form.setDepartureTime(BLANK);
			form.setDuplicateFlightStatus(BLANK);
			form.setRoute(BLANK);
			form.setViewUldStatus(BLANK);
			form.setLinkStatus(BLANK);
			form.setMessageTypeStatus(BLANK);
			form.setOutConfirmStatus(BLANK);
			form.setUcmVOStatus(BLANK);
			session.setMessageStatus(BLANK);
			session.setOutDestinations(null);
			form.setPouStatus(BLANK);
			form.setOrgDestStatus(BLANK);
			session.setFlightValidationVOSession(null);

			ULDFlightMessageReconcileVO newReconcileVO = new ULDFlightMessageReconcileVO();
			newReconcileVO
					.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
			newReconcileVO.setCompanyCode(getApplicationSession().getLogonVO()
					.getCompanyCode());
			session.setMessageReconcileVO(newReconcileVO);
			*/
			clearForm(form);
			invocationContext.target = SAVE_SUCCESS;
			form.setIsUcmSent("Y");
			return;
		}
		form.setIsUcmSent("Y");
		invocationContext.target = SAVE_SUCCESS;
	}

	private void populateUCMError(InvocationContext invocationContext,
			UCMErrorLogSession ucmErrorLogSession, Collection<ErrorVO> errors,
			ULDFlightMessageReconcileVO reconcileVOFromServer) {
		ErrorVO error = new ErrorVO("uld.defaults.ucminout.ucmerror.e10",
				new Object[] { reconcileVOFromServer.getSequenceNumber().toUpperCase() });
		error.setErrorDisplayType(ErrorDisplayType.WARNING);
		errors.add(error);
		invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
		FlightFilterMessageVO flightFilterMessageVO = new FlightFilterMessageVO();
		flightFilterMessageVO.setAirportCode(reconcileVOFromServer.getAirportCode());
		flightFilterMessageVO.setFlightNumber(reconcileVOFromServer.getFlightNumber());
		flightFilterMessageVO.setCarrierCode(reconcileVOFromServer.getCarrierCode());
		flightFilterMessageVO.setFlightCarrierId(reconcileVOFromServer.getFlightCarrierIdentifier());
		flightFilterMessageVO.setFlightSequenceNumber(reconcileVOFromServer.getFlightSequenceNumber());
		flightFilterMessageVO.setFlightDate(reconcileVOFromServer.getFlightDate());
		//Added by A-7359 for ICRD-225848
		flightFilterMessageVO.setMessageType(reconcileVOFromServer.getMessageType());
		flightFilterMessageVO.setCompanyCode(reconcileVOFromServer.getCompanyCode());
		log.log(Log.FINE, "Flight filter message vo to UCMerrorLogScreen-------------->",
				flightFilterMessageVO);
		ucmErrorLogSession.setPageURL(PAGEURL);
		ucmErrorLogSession.setFlightFilterMessageVOSession(flightFilterMessageVO);
		
	}

/**
 *
 * @param flightValidationVO
 * @param reconcileVO
 * @param form
 * @param session
 */
	private void populateMessageVO(FlightValidationVO flightValidationVO,
			ULDFlightMessageReconcileVO reconcileVO, UCMINOUTForm form,
			UCMINOUTSession session) {
		reconcileVO.setFlightCarrierIdentifier(flightValidationVO
				.getFlightCarrierId());
		reconcileVO.setAirportCode(form.getOrigin().toUpperCase());
		reconcileVO.setCarrierCode(flightValidationVO.getCarrierCode());
		reconcileVO.setFlightDate(flightValidationVO
				.getApplicableDateAtRequestedAirport());
		reconcileVO.setFlightNumber(flightValidationVO.getFlightNumber());
		reconcileVO.setFlightSequenceNumber((int) flightValidationVO
				.getFlightSequenceNumber());
		reconcileVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		reconcileVO.setTailNumber(flightValidationVO.getTailNumber());  
		reconcileVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		reconcileVO.setMessageType(session.getMessageStatus());
		//Added by A-9111 as a part of IASCB-33977
		reconcileVO.setFlightType(flightValidationVO.getFlightType());
		reconcileVO.setMessageSource("MAN"); 
		//Added by A-8445 as a part of IASCB-22297
		reconcileVO.setFlightType(flightValidationVO.getFlightType());
		//Added by a-3459 as part of bug 29943 starts
		if("OUT".equalsIgnoreCase(session.getMessageStatus())){
			if(flightValidationVO.getAtd()!= null){
				reconcileVO.setActualDate(flightValidationVO.getAtd());
			}else if(flightValidationVO.getEtd()!=null){
				reconcileVO.setActualDate(flightValidationVO.getEtd());
			}else{
				reconcileVO.setActualDate(flightValidationVO.getStd());
			}
		}else{
			if(flightValidationVO.getAta()!= null){
				reconcileVO.setActualDate(flightValidationVO.getAta());
			}else if(flightValidationVO.getEtd()!=null){
				reconcileVO.setActualDate(flightValidationVO.getEta());
			}else{
				reconcileVO.setActualDate(flightValidationVO.getSta());
			}
		}
		//Added by a-3459 as part of bug 29943 ends
		Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs = reconcileVO
				.getReconcileDetailsVOs();
		log.log(Log.FINE, "message type------------>", session.getMessageStatus());
		if (detailsVOs != null && detailsVOs.size() > 0) {
			for (ULDFlightMessageReconcileDetailsVO detailsVO : detailsVOs) {
				if (detailsVO.getMessageType() == null
						|| detailsVO.getMessageType().trim().length() == 0) {
					detailsVO.setMessageType(session.getMessageStatus()
							.toUpperCase());
				}
				detailsVO.setFlightCarrierIdentifier(flightValidationVO
						.getFlightCarrierId());
				detailsVO.setCarrierCode(flightValidationVO.getCarrierCode());
				detailsVO.setFlightDate(flightValidationVO
						.getApplicableDateAtRequestedAirport());
				detailsVO.setFlightNumber(flightValidationVO.getFlightNumber());
				detailsVO.setAirportCode(form.getOrigin().toUpperCase());
				detailsVO.setFlightSequenceNumber((int) flightValidationVO
						.getFlightSequenceNumber());
				detailsVO.setLegSerialNumber(flightValidationVO
						.getLegSerialNumber());
				detailsVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
                if(detailsVO.getUldSource()==null||detailsVO.getUldSource().trim().length()==0){
                	detailsVO.setUldSource("MAN");  
                }
                //Added by A-7359 ICRD-238782
                if(detailsVO.getUldStatus()==null||detailsVO.getUldStatus().trim().length()==0){
                	detailsVO.setUldStatus("N");  
                }
			}
		}

	}

	/**
	 *
	 * @param reconcileVO
	 * @return
	 */
	private Collection<ULDFlightMessageReconcileDetailsVO> constructReconcileVOsForSave(
			ULDFlightMessageReconcileVO reconcileVO) {
		Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs = reconcileVO
				.getReconcileDetailsVOs();
		Collection<ULDFlightMessageReconcileDetailsVO> newULDDetails = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		if (detailsVOs != null && detailsVOs.size() > 0) {
			for (ULDFlightMessageReconcileDetailsVO detailsVO : detailsVOs) {
				if ("OUT".equals(detailsVO.getMessageType())) {
					newULDDetails.add(detailsVO);
				}
			}
		}
		return newULDDetails;
	}
	private void clearForm(UCMINOUTForm form){
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		form.setCarrierCode(BLANK);
		form.setFlightDate(BLANK);
		form.setFlightNo(BLANK);
		form.setOrigin(BLANK);
		form.setDestination(BLANK);
		form.setUcmIn(BLANK);
		//Added by A-7359 fro ICRD-259943 
		form.setUcmOut(session.getMessageStatus());
		form.setArrivalTime(BLANK);
		form.setDepartureTime(BLANK);
		form.setDuplicateFlightStatus(BLANK);
		form.setRoute(BLANK);
		form.setViewUldStatus(BLANK);
		form.setLinkStatus(BLANK);
		form.setOutConfirmStatus(BLANK);
		form.setUcmVOStatus(BLANK);
		form.setMessageTypeStatus(BLANK);
		session.setMessageStatus(BLANK);
		session.setOutDestinations(null);
		form.setPouStatus(BLANK);
		form.setOrgDestStatus(BLANK);
		form.setUcmBlockStatus(BLANK);
		session.setFlightValidationVOSession(null);
		ULDFlightMessageReconcileVO reconcileVO = new ULDFlightMessageReconcileVO();
		reconcileVO
				.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
		reconcileVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		session.setMessageReconcileVO(reconcileVO);
		form.setOrigin(getApplicationSession().getLogonVO().getAirportCode()
				.toUpperCase());
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	}
	
	
//Added by A-6991 for CR ICRD-177310 Starts
/**
 * 
 * 	Method		:	SaveUCMINOUTMessageCommand.populateMessageDespatchDetailsVOs
 *	Added by 	:	A-6991 on 25-Jan-2017
 * 	Used for 	:
 *	Parameters	:	@param autoForwardDetailsVOs
 *	Parameters	:	@return 
 *	Return type	: 	Collection<MessageDespatchDetailsVO>
 */
	private Collection<MessageDespatchDetailsVO> populateMessageDespatchDetailsVOs(Collection<AutoForwardDetailsVO> autoForwardDetailsVOs){
		Collection<MessageDespatchDetailsVO> messageDespatchDetailsVOs = new ArrayList<MessageDespatchDetailsVO>();
		Collection<String> airports;
		MessageDespatchDetailsVO messageDespatchDetailsVO = null;
		if(autoForwardDetailsVOs != null && autoForwardDetailsVOs.size()>0){
			for(AutoForwardDetailsVO autoFwdDetls:autoForwardDetailsVOs){
				airports = new ArrayList<String>();
				messageDespatchDetailsVO = new MessageDespatchDetailsVO();
				messageDespatchDetailsVO.setParty(autoFwdDetls.getParticipantName());
				messageDespatchDetailsVO.setPartyType(autoFwdDetls.getParticipantType());
				messageDespatchDetailsVO.setInterfaceSystem(autoFwdDetls.getInterfaceSystem());
				messageDespatchDetailsVO.setCountry(autoFwdDetls.getCountryCode());
				messageDespatchDetailsVO.setExcludeMsgCfg(autoFwdDetls.isExcludeMsgCfg());
				if(autoFwdDetls.getAirportCode().trim().length()>0){
					airports.add(autoFwdDetls.getAirportCode());
					messageDespatchDetailsVO.setPous(airports);
				}
				messageDespatchDetailsVOs.add(messageDespatchDetailsVO);
			}
		}
		return messageDespatchDetailsVOs;
	}	
	
//Added by A-6991 for CR ICRD-177310 Ends	
	
}
