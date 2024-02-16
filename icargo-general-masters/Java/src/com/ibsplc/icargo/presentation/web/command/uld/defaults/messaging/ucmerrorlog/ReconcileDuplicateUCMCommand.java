/*
 * ReconcileDuplicateUCMCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2046
 *
 */
public class ReconcileDuplicateUCMCommand extends BaseCommand {
	/**
	 * Logger for UCM Error Log
	 */
	private Log log = LogFactory.getLogger("UCM Error Log");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID = "uld.defaults.ucmerrorlog";

	private static final String SAVE_SUCCESS = "save_success";
	private static final String MSG_MODULE_NAME = "msgbroker.message";
	private static final String MSG_SCREEN_ID = "msgbroker.message.listmessages";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String compCode = logonAttributes.getCompanyCode();
		UCMErrorLogSession ucmErrorLogSession = (UCMErrorLogSession) getScreenSession(
				MODULE, SCREENID);

		UCMErrorLogForm ucmErrorLogForm = (UCMErrorLogForm) invocationContext.screenModel;
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ULDFlightMessageReconcileVO uldFltMsgRecVOFirst = ucmErrorLogSession
				.getUCM1ReconcileVO();
		log.log(Log.FINE, "\n\n\n\nReconcile vo 1 ------------------>",
				uldFltMsgRecVOFirst);
		ULDFlightMessageReconcileVO uldFltMsgRecVOSecond = ucmErrorLogSession
				.getUCM2ReconcileVO();
		ULDFlightMessageReconcileVO reconcileVOForSave = populateReconcileVO(ucmErrorLogSession);
		/*
		 * reconcile vo containing unselected uld numbers
		 */
		ULDFlightMessageReconcileVO reconcileVOForULDStatusChange = populateReconcileVO(ucmErrorLogSession);

		reconcileVOForULDStatusChange.setOperationFlag("Q");

		// new collection for saving
		Collection<ULDFlightMessageReconcileVO> reconcileVOsForSave = new ArrayList<ULDFlightMessageReconcileVO>();
		// new child vo collection
		Collection<ULDFlightMessageReconcileDetailsVO> detailsVOsForSave = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		/*
		 * collection for storing uld numbers which are not selected
		 */
		Collection<ULDFlightMessageReconcileDetailsVO> deselectedUlds = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		String[] selectedUldsFirst = ucmErrorLogForm.getSelectedUcmsFirst();
		String[] selectedUldsSecond = ucmErrorLogForm.getSelectedUcmsSecond();

		Collection<ULDFlightMessageReconcileDetailsVO> detailsFirst = uldFltMsgRecVOFirst
				.getReconcileDetailsVOs();
		Collection<ULDFlightMessageReconcileDetailsVO> detailsSecond = uldFltMsgRecVOSecond
				.getReconcileDetailsVOs();
		int index = 0;

		log.log(Log.FINE, "details1---------------->", detailsFirst);
		if (selectedUldsFirst != null) {
			for (ULDFlightMessageReconcileDetailsVO detailsVO1 : detailsFirst) {
				detailsVO1.setFromReconcileScreen(true);
				boolean hasAdded = false;
				for (int i = 0; i < selectedUldsFirst.length; i++) {
					if (index == Integer.parseInt(selectedUldsFirst[i])) {
						detailsVOsForSave.add(detailsVO1);
						hasAdded = true;

					}

				}
				if (!hasAdded) {
					deselectedUlds.add(detailsVO1);

				}
				index++;
			}

		}
		log.log(Log.FINE, "Details Selected 1----------->", detailsVOsForSave);
		log.log(Log.FINE, "\n\n\n\n\nDeselectd ulds------------------->",
				deselectedUlds);
		int ind = 0;
		if (selectedUldsSecond != null) {
			for (ULDFlightMessageReconcileDetailsVO detailsVO1 : detailsSecond) {
				detailsVO1.setFromReconcileScreen(true);
				boolean hasAdded = false;
				for (int i = 0; i < selectedUldsSecond.length; i++) {
					if (ind == Integer.parseInt(selectedUldsSecond[i])) {
						detailsVOsForSave.add(detailsVO1);
						hasAdded = true;
					}
				}
				if (!hasAdded) {
					deselectedUlds.add(detailsVO1);

				}
				ind++;
			}

		}
		detailsVOsForSave = checkForDuplicateULDNumbers(detailsVOsForSave);
		log.log(Log.FINE,
				"\n\n\nDetails After Duplicate Removal---------------->",
				detailsVOsForSave);
		deselectedUlds = checkForDuplicateULDNumbers(deselectedUlds);
		log.log(Log.FINE, "Details Selected Final ----------->",
				detailsVOsForSave);
		log.log(Log.FINE,
				"\n\n\n\n\nDeselectd ulds-22222222222------------------>",
				deselectedUlds);
		reconcileVOForSave.setReconcileDetailsVOs(detailsVOsForSave);

		uldFltMsgRecVOFirst
				.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_DELETE);
		uldFltMsgRecVOFirst.setMessageSource("MAN");
		log.log(Log.FINE, "Mismatch status---------------------->",
				ucmErrorLogForm.getMismatchStatus());
		/*Added for ICRD-200282 startssa*/
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
		/*Added for ICRD-200282 ends*/
		if (!"Y".equals(ucmErrorLogForm.getMismatchStatus())) {
			uldFltMsgRecVOSecond
					.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_DELETE);
			uldFltMsgRecVOSecond.setMessageSource("MAN");
			uldFltMsgRecVOSecond.setMsgDsptcDetailsVOs(detailsVOs);
			reconcileVOsForSave.add(uldFltMsgRecVOSecond);
		}
		uldFltMsgRecVOFirst.setMsgDsptcDetailsVOs(detailsVOs);
		reconcileVOsForSave.add(uldFltMsgRecVOFirst);
		
		// reconcileVOForSave
		reconcileVOForULDStatusChange.setReconcileDetailsVOs(deselectedUlds);

		log.log(Log.FINE, "VOS FOR Reconciling---------------->",
				reconcileVOsForSave);
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			delegate.saveULDFlightMessageReconcile(reconcileVOsForSave);
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			error = handleDelegateException(exception);
		}
		reconcileVOsForSave = new ArrayList<ULDFlightMessageReconcileVO>();

		if (reconcileVOForSave.getReconcileDetailsVOs() != null
				&& reconcileVOForSave.getReconcileDetailsVOs().size() > 0) {
			for (ULDFlightMessageReconcileDetailsVO dtlsVo : reconcileVOForSave
					.getReconcileDetailsVOs()) {
				dtlsVo.setMessageType(reconcileVOForSave.getMessageType());
				if ("Y".equals(ucmErrorLogForm.getMismatchStatus())) {
					dtlsVo.setAirportCode(reconcileVOForSave.getAirportCode());
				}
			}
		}
		if ("Y".equals(ucmErrorLogForm.getMismatchStatus())) {
			reconcileVOForSave.setToBeAvoidedFromValidationCheck(true);
			log.log(Log.FINE,
					"\n\n\n\nVALIDATION CHECK----------------------->",
					reconcileVOForSave);
		}
		reconcileVOForULDStatusChange.setMsgDsptcDetailsVOs(detailsVOs);
		reconcileVOsForSave.add(reconcileVOForULDStatusChange);
		reconcileVOForSave.setMsgDsptcDetailsVOs(detailsVOs);
		reconcileVOsForSave.add(reconcileVOForSave);
		log.log(Log.FINE, "VOS After Reconciling---------------->",
				reconcileVOsForSave);
		Collection<ErrorVO> err = new ArrayList<ErrorVO>();
		try {
			delegate.saveULDFlightMessageReconcile(reconcileVOsForSave);
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			err = handleDelegateException(ex);
		}

		ucmErrorLogForm.setCanClose("close");
		ucmErrorLogForm.setMismatchStatus("");
		invocationContext.target = SAVE_SUCCESS;
	}

	/**
	 *
	 * @param ucmErrorLogSession
	 * @return
	 */
	private ULDFlightMessageReconcileVO populateReconcileVO(
			UCMErrorLogSession ucmErrorLogSession) {
		ULDFlightMessageReconcileVO reconcileVO = new ULDFlightMessageReconcileVO();
		ULDFlightMessageReconcileVO uldFltMsgReconcileVOFirst = ucmErrorLogSession
				.getUCM1ReconcileVO();
		reconcileVO
				.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
		reconcileVO.setCarrierCode(uldFltMsgReconcileVOFirst
				.getCarrierCode());
		reconcileVO.setCompanyCode(uldFltMsgReconcileVOFirst
				.getCompanyCode());
		reconcileVO.setFlightCarrierIdentifier(uldFltMsgReconcileVOFirst
				.getFlightCarrierIdentifier());
		reconcileVO.setFlightDate(uldFltMsgReconcileVOFirst.getFlightDate());
		reconcileVO.setFlightNumber(uldFltMsgReconcileVOFirst
				.getFlightNumber());
		reconcileVO.setFlightSequenceNumber(uldFltMsgReconcileVOFirst
				.getFlightSequenceNumber());
		reconcileVO.setAirportCode(uldFltMsgReconcileVOFirst
				.getAirportCode());
		reconcileVO.setMessageType(uldFltMsgReconcileVOFirst
				.getMessageType());
		log.log(Log.FINE, "VOS After Reconciling---------------->",
				uldFltMsgReconcileVOFirst.getActualDate());
		reconcileVO.setActualDate(uldFltMsgReconcileVOFirst.getActualDate());
        reconcileVO.setMessageSource("MAN");
		return reconcileVO;
	}

	/**
	 *
	 * @param uldDetails
	 */
	private Collection<ULDFlightMessageReconcileDetailsVO> checkForDuplicateULDNumbers(
			Collection<ULDFlightMessageReconcileDetailsVO> uldDetails) {

		HashMap<String, ULDFlightMessageReconcileDetailsVO> map = new HashMap<String, ULDFlightMessageReconcileDetailsVO>();
		if (uldDetails != null && uldDetails.size() > 0) {
			for (ULDFlightMessageReconcileDetailsVO uldVO1 : uldDetails) {
				ULDFlightMessageReconcileDetailsVO currentVO = map.get(uldVO1
						.getUldNumber());
				if (currentVO == null) {
					map.put(uldVO1.getUldNumber(), uldVO1);
				} else if (!map.containsKey(uldVO1.getUldNumber())) {
					map.put(uldVO1.getUldNumber(), uldVO1);
				}

			}

			uldDetails = new ArrayList<ULDFlightMessageReconcileDetailsVO>(map
					.values());


		}

		return uldDetails;

	}
	
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

}
