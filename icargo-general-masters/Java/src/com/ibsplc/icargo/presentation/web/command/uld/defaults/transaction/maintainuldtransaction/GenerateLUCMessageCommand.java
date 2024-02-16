/*
 * GenerateLUCMessageCommand.java  Created on Feb 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.config.format.vo.MessageRuleDefenitionVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCReceivingPartyDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCSupplementaryInfoVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCTransferringPartyDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCULDConditionDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCULDDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCULDIdentificationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.message.vo.MessageConfigConstants;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolSegmentExceptionsVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

/**
 *
 * @author A-2046
 *
 */
public class GenerateLUCMessageCommand extends BaseCommand {

	/**
	 * Logger for laon borrow uld
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD ");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	/**
	 * message standard to be set
	 */
	private static final String MESSAGE_STANDARD = "AHM";

	/**
	 * message type to be set
	 */
	private static final String MESSAGE_TYPE = "LUC";

	private static final String LUC_SUCCESS = "luc_success";

	private static final String LUC_FAILURE = "luc_failure";

	private static final String MSGMODULE_NAME = "msgbroker.message";
	
	private static final String MSGSCREEN_ID = "msgbroker.message.listmessages";
	
	private static final String BLANK = "";
	
	private static final String AIRLINE = "A";
	
	private static final String LOAN="L";
	
	private static final String LUC_NOT_SEND_TO_POOL="uld.defaults.luccannotbesendtopool";

	private static final String LUC_SEND_FOR_POOLAIRLINE = "uld.default.lucsendingforpoolairline";
	
	private static final String AIRLINE1_BASEDON = "uld.default.poolownerairline1basedon";
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ListMessageSession msgsession = 
			getScreenSession( MSGMODULE_NAME,MSGSCREEN_ID);
		// To be reviewed validate form
		//BaseMessageVO BaseMessageVO = new BaseMessageVO();

		Collection<MessageDespatchDetailsVO> messageDespatchDetails = new ArrayList<MessageDespatchDetailsVO>();
		messageDespatchDetails = msgsession.getDespatchDetails();

		LUCMessageVO messageVO = new LUCMessageVO();
		//added by nisha on 24JAN08
		//changed by nisha on 23Jun08 for bugfix
		if(AIRLINE.equals(maintainULDTransactionForm.getPartyType())){
			log.log(Log.INFO, "Transaction type is ---->>>>>",
					maintainULDTransactionForm.getTransactionType());
			if(LOAN.equals(maintainULDTransactionForm.getTransactionType())){
			messageVO.setCarrierCode(maintainULDTransactionForm
					.getToPartyCode().toUpperCase());
			}else{
				messageVO.setCarrierCode(maintainULDTransactionForm
						.getFromPartyCode().toUpperCase());
			}
		}else{
			messageVO.setCarrierCode(maintainULDTransactionForm
					.getFromPartyCode().toUpperCase());
		}
		messageVO.setAirlineCode(getApplicationSession().getLogonVO().getOwnAirlineCode());
		messageVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		messageVO.setMessageStandard(MESSAGE_STANDARD);
		messageVO.setMessageType(MESSAGE_TYPE);
		messageVO.setTransactionId(MessageConfigConstants.TXN_SCREEN);
		messageVO.setLastUpdateUser(getApplicationSession().getLogonVO().getUserId());
		
		
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		//added by a-3045 for CR QF1016 starts
		updateSession(transactionVO, maintainULDTransactionForm);
		updateAccSession(transactionVO, maintainULDTransactionForm);
		
		if(maintainULDTransactionForm.getTransactionRemarks()!=null && maintainULDTransactionForm.getTransactionRemarks().trim().length()>0){
			LUCSupplementaryInfoVO supplInfoVO = new LUCSupplementaryInfoVO();
			supplInfoVO.setSupplId("OSI");
			supplInfoVO.setRemarks(maintainULDTransactionForm.getTransactionRemarks());
			log.log(Log.INFO, "Remarks-----------", maintainULDTransactionForm.getTransactionRemarks());
			messageVO.setSupplInfoVO(supplInfoVO);
			log.log(Log.INFO, "supplInfoVO-----------", supplInfoVO);
		}
		
		Collection<ULDTransactionDetailsVO> transactionDetailsVOs = loanBorrowULDSession
				.getTransactionVO().getUldTransactionDetailsVOs();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.log(Log.FINE, "TRANSACTION DETAILS VOS--->", transactionDetailsVOs);
		//Validate ULD Number Format
		Collection<String> uLDNumbers = new ArrayList<String>();
		Collection<String> invalidUlds = null;
		String cmpCode = logonAttributes.getCompanyCode();
		Collection<ULDTransactionDetailsVO> detailsVos = transactionVO
				.getUldTransactionDetailsVOs();

		if (detailsVos != null && detailsVos.size() > 0) {
			for (ULDTransactionDetailsVO vo : detailsVos) {
				if (vo.getUldNumber() != null
						&& vo.getUldNumber().trim().length() > 0) {
					uLDNumbers.add(vo.getUldNumber().toUpperCase());
				}
			}
		}
		invalidUlds = validateUldNumberFormat(cmpCode, uLDNumbers);
		log.log(Log.INFO, "invalidUlds are ---->>>>>", invalidUlds);
		if (invalidUlds != null && invalidUlds.size() > 0) {
			int size = invalidUlds.size();
			for (int i = 0; i < size; i++) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.loanborrowULD.msg.err.invaliduldformat",
						new Object[] { ((ArrayList<String>) invalidUlds).get(i) });
				errors.add(error);
				maintainULDTransactionForm.setTxnTypeDisable("Y");
				invocationContext.addAllError(errors);
				invocationContext.target = LUC_FAILURE;
				return;
			}
		}
		//added by a-3045 for bug ULD551 starts
		Collection<ErrorVO> errs = checkDuplicateULDs(uLDNumbers);
		errors.addAll(errs);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);	
			maintainULDTransactionForm.setTxnTypeDisable("Y");
				invocationContext.target = LUC_FAILURE;			
				loanBorrowULDSession.setTransactionVO(transactionVO);
			return;
		}		
		log
				.log(Log.INFO, "printflag", maintainULDTransactionForm.getPrintUCR());
		log.log(Log.INFO, "ULD Transaction details in generateLUC",
				transactionDetailsVOs);
		//		 added by nisha starts for UCR ends
		if (transactionDetailsVOs == null || transactionDetailsVOs.size() == 0) {

			// commented by nisha for CR-15
			/*log.log(Log.FINE, "\n\n\n\n   RECONCILE ERROR IN LUC MESSAGE");
			log.log(Log.FINE, "maintainULDTransactionForm.getPageurl()--->"
					+ maintainULDTransactionForm.getPageurl());
			log.log(Log.FINE, "loanBorrowULDSession.getULDFlightMessageReconcileDetailsVO()--->"
					+ loanBorrowULDSession.getULDFlightMessageReconcileDetailsVO());
			if(maintainULDTransactionForm.getPageurl()!=null && (maintainULDTransactionForm.getPageurl().equals("fromulderrorlogforborrow") ||
	   			 maintainULDTransactionForm.getPageurl().equals("fromulderrorlogforloan"))) {

				log.log(Log.FINE, "\n \n loanBorrowULDSession.getULDFlightMessageReconcileDetailsVO()" +loanBorrowULDSession.getULDFlightMessageReconcileDetailsVO());
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				try {
					log.log(Log.FINE, "\n reconcile  delegate " );
					new ULDDefaultsDelegate().reconcileUCMULDError(loanBorrowULDSession.getULDFlightMessageReconcileDetailsVO());
				}
				catch(BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
					error = handleDelegateException(businessDelegateException);
	   			}
	 	}*/
			ErrorVO error = new ErrorVO(
					"uld.defaults.loanborrowuld.msg.err.cannotgenerateluc");
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = LUC_FAILURE;
			return;
		}
		//added by nisha for CR QF1018 on 15Jul08 starts
		
		//Collection<ULDPoolOwnerVO> poolOwners =null;
		//Collection<ULDPoolOwnerVO> listPoolOwners =null;
		HashMap<String,String> odpairs = new HashMap<String,String>();
		for (ULDTransactionDetailsVO vo : transactionDetailsVOs) {
			odpairs.put(vo.getTransactionStationCode(), vo.getTxStationCode());
		}
		boolean isPoolOwnerPair = false;
		if(AIRLINE.equals(maintainULDTransactionForm.getPartyType())){
				ArrayList<String> arlCode = new ArrayList<String>();
				arlCode.add(maintainULDTransactionForm
						.getFromPartyCode().toUpperCase());
				arlCode.add(maintainULDTransactionForm
						.getToPartyCode().toUpperCase());
				
				Map<String,AirlineValidationVO>  arlines= null;
				try{
					arlines = new AirlineDelegate().validateAlphaCodes(logonAttributes.getCompanyCode(), arlCode);
				}catch (BusinessDelegateException ex) {
					errors = handleDelegateException(ex);
				}
				if(errors!=null && errors.size()>0){
					
					invocationContext.addAllError(errors);
					invocationContext.target = LUC_FAILURE;
					return;
				}
				
				try {
					isPoolOwnerPair = isPoolOwnersPresent(transactionDetailsVOs,
							maintainULDTransactionForm.getTransactionStation(),maintainULDTransactionForm
							.getToPartyCode().toUpperCase());
				} catch (SystemException e) {
					log.log(Log.SEVERE, "Error",
							e.getMessage());
				}
				/*ULDPoolOwnerFilterVO uldPoolOwnerFilterVO = new ULDPoolOwnerFilterVO();
				uldPoolOwnerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				if(arlines!=null){
					
					int frmPtyIdr = arlines.get(maintainULDTransactionForm
						.getFromPartyCode().toUpperCase()).getAirlineIdentifier();
					int toPtyIdr = arlines.get(maintainULDTransactionForm
							.getToPartyCode().toUpperCase()).getAirlineIdentifier();
					uldPoolOwnerFilterVO.setAirlineIdentifierOne(frmPtyIdr);
					uldPoolOwnerFilterVO.setAirlineIdentifierTwo(toPtyIdr);			
				}
				uldPoolOwnerFilterVO.setOdpairs(odpairs);
				uldPoolOwnerFilterVO.setAirport(maintainULDTransactionForm.getTransactionStation());
				log.log(Log.INFO, "Pool Owner Filter VO", uldPoolOwnerFilterVO);
			try{
				//poolOwners = new ULDDefaultsDelegate().listULDPoolOwner(uldPoolOwnerFilterVO);
				isPoolOwnerPair = new ULDDefaultsDelegate().checkforPoolOwner(uldPoolOwnerFilterVO);
			} catch (BusinessDelegateException ex) {
				handleDelegateException(ex);
			}*/
			log.log(Log.INFO, "Pool Owners---->", isPoolOwnerPair);
			log.log(Log.INFO, "Airlines---->", arlines);
			
		}
	
	//ends
		LocalDate dateOfTransfer = null;
		Collection<LUCULDDetailsVO> uldDetailsVOs = new ArrayList<LUCULDDetailsVO>();
		StringBuilder dateoFTransfer = new StringBuilder();
		StringBuilder time = new StringBuilder();
		log.log(Log.INFO, "Transaction VO--->", transactionVO);
		if(transactionVO.getTransactionDate()!=null && transactionVO.getTransactionTime()!=null){
			log.log(Log.INFO,"Not NUll");
			dateoFTransfer.append(transactionVO.getTransactionDate().toDisplayDateOnlyFormat().substring(0, 2));
			dateoFTransfer.append(transactionVO.getTransactionDate().toDisplayDateOnlyFormat().substring(3, 6)
					.toUpperCase());
			dateoFTransfer.append(transactionVO.getTransactionDate().toDisplayDateOnlyFormat().substring(9, 11));
			String times[]=transactionVO.getTransactionTime().split(":");
			time.append(times[0]);
			time.append(times[1]);
		}else{
		 dateOfTransfer = new LocalDate(logonAttributes.getAirportCode(),Location.ARP, true);
		 dateoFTransfer.append(dateOfTransfer.toDisplayDateOnlyFormat().substring(0, 2));
			dateoFTransfer.append(dateOfTransfer.toDisplayDateOnlyFormat().substring(3, 6)
					.toUpperCase());
			dateoFTransfer.append(dateOfTransfer.toDisplayDateOnlyFormat().substring(9, 11));
			log.log(Log.FINE,
					"date of transfer after formatting--------------->",
					dateoFTransfer);
			String times[]=dateOfTransfer.toDisplayTimeOnlyFormat().split(":");
			time.append(times[0]);
			time.append(times[1]);
		}
		
		log.log(Log.FINE, " time of dateOfTransfer---------------->",
				dateOfTransfer);
		log.log(Log.FINE, "time of transfer---------------->", time);
		ArrayList<String> txnrefnums = loanBorrowULDSession.getTxnRefNo();
		//boolean poolOwner = false;

		for (ULDTransactionDetailsVO detailsVO : transactionDetailsVOs) {
			LUCULDDetailsVO uldDetailsVO = new LUCULDDetailsVO();
			uldDetailsVO.setUldReceiptNumber(detailsVO.getControlReceiptNumber());
			uldDetailsVO.setDateofTransfer(dateoFTransfer.toString());
			//added by a-3045 for bug ULD550 starts
			//if(detailsVO.getTransactionStationCode() == null || ("".equals(detailsVO.getTransactionStationCode()))){
		
			uldDetailsVO.setDestinationLocation(detailsVO.getTxStationCode());	
			uldDetailsVO.setLocationOftransfer(maintainULDTransactionForm.getTransactionStation());	
			
			
			//uldDetailsVO.setDestinationLocation(detailsVO.getTransactionStationCode());
			//added by a-3045 for bug ULD550 starts
			uldDetailsVO.setTimeofTransfer(Integer.parseInt(time.toString()));
			LUCULDIdentificationVO identificationVO = new LUCULDIdentificationVO();
			String uldNumber = detailsVO.getUldNumber();
			
			//commented by nisha  starts 
			/*for(String str:txnrefnums){
				String[] uldnum=str.split("~");
				if(uldNumber.equals(uldnum[0])){
					identificationVO.setTransactionRefNumber((int)Integer.parseInt(uldnum[1]));
				}
			}*/
			//commented by nisha ends
			identificationVO.setUldType(uldNumber.substring(0, 3));
			identificationVO.setUldOwnerCode(uldNumber.substring(uldNumber
					.length() - 2));
			identificationVO.setUldSerialNumber(uldNumber.substring(3,
					uldNumber.length() - 2));
			uldDetailsVO.setUldIdentificationVO(identificationVO);
			LUCReceivingPartyDetailsVO recevingPartyDetailsVO = new LUCReceivingPartyDetailsVO();
			LUCTransferringPartyDetailsVO transferringPartyDetailsVO = new LUCTransferringPartyDetailsVO();
			if(LOAN.equals(maintainULDTransactionForm.getTransactionType())){
			if("A".equals(maintainULDTransactionForm.getPartyType())){
				recevingPartyDetailsVO.setCarrierCode(maintainULDTransactionForm
						.getToPartyCode().toUpperCase());
			}else{
				recevingPartyDetailsVO.setCarrierCode("YY");
				recevingPartyDetailsVO.setNonCarrierId(maintainULDTransactionForm
						.getToPartyCode().toUpperCase());
			}	
			transferringPartyDetailsVO.setCarrierCode(maintainULDTransactionForm
					.getFromPartyCode().toUpperCase());
			}else{
				if("A".equals(maintainULDTransactionForm.getPartyType())){
					transferringPartyDetailsVO.setCarrierCode(maintainULDTransactionForm
							.getFromPartyCode().toUpperCase());
				}else{
					transferringPartyDetailsVO.setCarrierCode("YY");  
					transferringPartyDetailsVO.setNonCarrierId(maintainULDTransactionForm.getFromPartyCode().toUpperCase());
				}
				recevingPartyDetailsVO.setCarrierCode(maintainULDTransactionForm.getToPartyCode().toUpperCase());  
			}
			uldDetailsVO.setReceivingPartyDetailsVO(recevingPartyDetailsVO);
			//changed by nisha
			//Commented the code for ICRD-272345 starts here
			/*Modified by A-4072 for LUC message encoding failed for party type as Others
			if("A".equals(maintainULDTransactionForm.getPartyType())){
			transferringPartyDetailsVO.setCarrierCode(maintainULDTransactionForm
					.getFromPartyCode().toUpperCase());
			}else{
				transferringPartyDetailsVO.setCarrierCode("YY");
				transferringPartyDetailsVO.setNonCarrierId(maintainULDTransactionForm
						.getFromPartyCode().toUpperCase());
			} */
			//Commented the code for ICRD-272345 ends here
			
		
			
			
			uldDetailsVO
					.setTransferringPartyDetailsVO(transferringPartyDetailsVO);
			LUCULDConditionDetailsVO conditionDetailsVO = new LUCULDConditionDetailsVO();
			//Preet starts
			conditionDetailsVO.setDamageCode(detailsVO.getUldConditionCode());
			/*if ("Y".equals(detailsVO.getDamageStatus())) {
				conditionDetailsVO.setDamageCode("DAM");
			} else {
				conditionDetailsVO.setDamageCode("SER");
			}
			*/
			//Preet ends
			uldDetailsVO.setUldConditionDetailsVO(conditionDetailsVO);
			//added by nisha for QF1018 on 15Jul08 starts
				
			Collection <ULDPoolSegmentExceptionsVO> poolSegmentsExceptionsVOs = null;
			/*if(poolOwners!= null && poolOwners.size() > 0){
				poolOwner = false;
				for(ULDPoolOwnerVO poolVO:poolOwners){
					if(poolVO.getAirport().equals(transactionVO.getTransactionStation()) || "NA".equals(poolVO.getAirport())){
						poolOwner = true;
						poolSegmentsExceptionsVOs = poolVO.getPoolSegmentsExceptionsVOs();
						log.log(Log.INFO,"poolSegmentsExceptionsVOs present------->"+poolVO.getPoolSegmentsExceptionsVOs());
						if(poolSegmentsExceptionsVOs != null && poolSegmentsExceptionsVOs.size() > 0){
							for(ULDPoolSegmentExceptionsVO expVO : poolSegmentsExceptionsVOs){
								log.log(Log.INFO,"poolSegmentsExceptions origin------->"+expVO.getOrigin());
								log.log(Log.INFO,"transactionVO origin------->"+transactionVO.getTransactionStation());
								log.log(Log.INFO,"poolSegmentsExceptions Destination------->"+expVO.getDestination());
								log.log(Log.INFO,"transactionVO Destination------->"+detailsVO.getTxStationCode());
								if(expVO.getOrigin().equals(transactionVO.getTransactionStation()) 
										&& expVO.getDestination().equals(detailsVO.getTxStationCode())){
									log.log(Log.INFO,"pool exceptions present");
									poolOwner = false;
									break;
								}
							}
						}
						break;
					}
				}
				if(poolOwner){
					break;
				}
			}*/
			//ends
			uldDetailsVOs.add(uldDetailsVO);
		}
		
		boolean shouldSendLUCForPoolOwners = false;
		try {
			shouldSendLUCForPoolOwners = shouldSendLUCForPoolOwners();
		} catch (SystemException e) {
			log.log(Log.ALL, "exception caught", e.getMessage());
		}
		
		if(isPoolOwnerPair && !shouldSendLUCForPoolOwners){
			log.log(Log.INFO,"pool exceptions present---@@@@@@@@---->error thrown");
			errors.add(new ErrorVO(
					LUC_NOT_SEND_TO_POOL));
			invocationContext.addAllError(errors);
			invocationContext.target = LUC_FAILURE;
			return;
		}
		messageVO.setUldDetails(uldDetailsVOs);
		messageVO.setStationCode(getApplicationSession().getLogonVO().getAirportCode());
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		// Added by Preet for the Server side validations for bug 29466 starts
		Collection<ErrorVO> errsFromServer = new ArrayList<ErrorVO>();
		try {
			log.log(Log.INFO,"GOING FOR validateULDsForTransaction -----------");
			errsFromServer = delegate.validateULDsForTransaction(transactionVO);
			log.log(Log.INFO, "errorsFromServer------%%%%%^^^^^^^^-------",
					errsFromServer);
		} catch (BusinessDelegateException e) {			
			e.getMessage();
		}
		if(errsFromServer != null && errsFromServer.size()>0){
			log.log(Log.INFO,"----------server side erorrs are present-------");			
			invocationContext.addAllError(errsFromServer);
			invocationContext.target = LUC_FAILURE;
			return;
		}
		// Added by Preet for the Server side validations for bug 29466 ends
	
		//Map<String , String> data=null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		//change by nisha starts
		Collection<MessageVO> messageVOs=null;
		//Added by A-6841 as part of ICRD-188218
		if(!LUCMessageVO.FLAG_YES.equals(maintainULDTransactionForm.getFromPopup())){
			Collection<MessageRuleDefenitionVO> messageRuleDefVOs = null;
			MessageRuleDefenitionVO messageRuleDefenitionVO = null;
			try {
				messageRuleDefVOs= new ULDDefaultsDelegate().
						findMessageTypeAndVersion(logonAttributes.getCompanyCode(),LUCMessageVO.MESSAGE_TYPE);
			} catch (BusinessDelegateException e) {
				e.getMessage();
				error = handleDelegateException(e);
				log.log(Log.ALL, "exception caught", error.size());
			}
			if (messageRuleDefVOs != null
					&& messageRuleDefVOs.size() > 0) {
				messageRuleDefenitionVO = messageRuleDefVOs.iterator()
						.next();
			}
			if (messageRuleDefenitionVO != null) { 
				MessageVO message = new MessageVO();
				message.setMessageType(LUCMessageVO.MESSAGE_TYPE);
				message.setMessageVersion(messageRuleDefenitionVO.getMessageVersion());
				msgsession.setMessage(message);
			}
			maintainULDTransactionForm.setMsgFlag("TRUE");
		}else {
			try{
				log.log(Log.INFO, "message vo before encoding***********", messageVO);
				if(messageDespatchDetails!=null){
					messageVO.setDespatchDetails(messageDespatchDetails);
				}
				messageVOs=delegate.generateLUCMessage(messageVO);
				log.log(Log.INFO, "messageVOs from delegate", messageVOs);
				//log.log(log.INFO,"messageVOs size"+messageVOs.size());
				if(messageVOs!=null && messageVOs.size()>0){
					//ArrayList<MessageVO> msgVOs =new ArrayList<MessageVO>(messageVOs);
					//log.log(Log.INFO, "encode VO from server-->", msgVOs.size());
					//log.log(Log.INFO, "encode VO from server-->", msgVOs.get(0));
					//msgsession.setMessage(msgVOs.get(0));
					msgsession.removeDespatchDetails();
					msgsession.removeMessageVO(); 
					maintainULDTransactionForm.setMsgFlag("FALSE");
					maintainULDTransactionForm.setFromPopup("N");
					// to do....set the raw msg in form
					if (errors == null || errors.size() == 0) {
						log.log(Log.INFO, "LUC SAVED SUCCESFULLY");
						errors = new ArrayList<ErrorVO>();
						ErrorVO err = new ErrorVO("uld.defaults.loanborrowuld.info.msg.sendLUCsuccess");
						err.setErrorDisplayType(ErrorDisplayType.STATUS);
						errors.add(err);
						invocationContext.addAllError(errors);
					}
				}else{
					errors.add(new ErrorVO(
					"uld.defaults.loanborrowuld.msg.err.cannotgenerateluc"));
					invocationContext.addAllError(errors);
					invocationContext.target = LUC_FAILURE;
					return;
						 
				}
			} catch (BusinessDelegateException ex) {
				ex.getMessage();
				error = handleDelegateException(ex);
				log.log(Log.ALL, "exception caught", error.size());
			}
		}
		//maintainULDTransactionForm.setSaveStatus("");
		//maintainULDTransactionForm.setTxnTypeDisable("");
	    //maintainULDTransactionForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		if(error!=null && error.size()>0){
	    	   //maintainULDTransactionForm.setLucPopup("open-error");
	    	   invocationContext.addAllError(error);
	    	   log.log(Log.ALL, "errors-----", error.size());
	    	   invocationContext.target = LUC_FAILURE;
	   		   for(ErrorVO vo:error){
	   			   log.log(Log.ALL, "error code is", vo.getErrorCode());
	   		   }
	   		return;
	    }/*else{
	    	maintainULDTransactionForm.setLucPopup("open");
	    	ErrorVO er = new ErrorVO("uld.defaults.loanborrowuld.msg.err.generatedluc");
	        er.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(er);
			invocationContext.addAllError(errors);

		}*/
	  	/*if(maintainULDTransactionForm.getPageurl()!=null && (maintainULDTransactionForm.getPageurl().equals("fromulderrorlogforborrow") ||
   			 maintainULDTransactionForm.getPageurl().equals("fromulderrorlogforloan"))) {
  			Collection<ErrorVO> err = new ArrayList<ErrorVO>();
			try {
				log.log(Log.FINE, "\n reconcile  delegate " );
				new ULDDefaultsDelegate().reconcileUCMULDError(loanBorrowULDSession.getULDFlightMessageReconcileDetailsVO());
			}
			catch(BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
				err = handleDelegateException(businessDelegateException);
   			}
		}*/
//	  change by nisha ends
		invocationContext.target = LUC_SUCCESS;
	}
	
	//added by a-3045 for CR QF1016 starts 
	/**
	 * @param transactionVO
	 * @param form
	 * @return
	 */
	private void updateSession(TransactionVO transactionVO,
			MaintainULDTransactionForm form) {
		log.log(Log.INFO, "INSIDE UPDATE SESSION");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		int len = 0;
		log.log(Log.INFO, "INSIDE form.getCrn().length)");
		if (form.getCrn() != null ) {
			log.log(Log.INFO, "INSIDE form.getCrn().length)",
					form.getCrn().length);
			len = form.getCrn().length;
		}
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		transactionVO.setCompanyCode(logonAttributes.getCompanyCode());
	    transactionVO.setTransactionType(form.getTransactionType());
		transactionVO.setTransactionNature(form.getTransactionNature());
		transactionVO.setTransactionStation(form.getTransactionStation());
		String txnDate = form.getTransactionDate();
		String strTxnDate = form.getTransactionDate();
		String txnTime = form.getTransactionTime();
		LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		if(!txnTime.contains(":")){
			txnTime=txnTime.concat(":00");
		}
		StringBuilder txndat = new StringBuilder();
		txndat.append(txnDate).append(" ").append(txnTime).append(":00");
		//txnDate = txnDate.concat(" "+txnTime+(":00"));
		
		
		if (txndat.length()>0){
			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			transactionVO.setStrTransactionDate(strTxnDate);
			transactionVO.setTransactionTime(txnTime);
			}else {
				transactionVO.setStrTransactionDate(BLANK);
				transactionVO.setTransactionTime(BLANK);
			}		
		transactionVO.setTransactionRemark(form.getTransactionRemarks());
		if(form.getPartyType() != null && 
				form.getPartyType().trim().length() > 0){
			transactionVO.setPartyType(form.getPartyType().trim());
		}
		if(form.getFromPartyCode() != null && 
				form.getFromPartyCode().trim().length() > 0){
			transactionVO.setFromPartyCode(form.getFromPartyCode().trim().toUpperCase());
		}
		if(form.getToPartyCode() != null && 
				form.getToPartyCode().trim().length() > 0){
			transactionVO.setToPartyCode(form.getToPartyCode().trim().toUpperCase());
		}
		if(form.getFromPartyName() != null && 
				form.getFromPartyName().trim().length() > 0){
			transactionVO.setFromPartyName(form.getFromPartyName().trim());
		}
		if(form.getToPartyName() != null && 
				form.getToPartyName().trim().length() > 0){
			transactionVO.setToPartyName(form.getToPartyName().trim());
		}
		
			transactionVO.setAwbNumber(form.getAwbNumber());
		
		if("Y".equals(form.getLoaded())){
			transactionVO.setEmptyStatus("N");
		}else{
			transactionVO.setEmptyStatus("Y");
		}
		log.log(Log.FINE, "#########");
		for (int i = 0; i < len; i++) {
			log.log(Log.FINE, "len", len);
			ULDTransactionDetailsVO vo = new ULDTransactionDetailsVO();
			vo.setControlReceiptNumberPrefix(form.getCrnPrefix()[i]);
			vo.setCrnToDisplay(form.getCrn()[i]);
			String crn = new StringBuilder(form.getCrnPrefix()[i]).append(
					form.getCrn()[i]).toString();
			vo.setControlReceiptNumber(crn);	
			vo.setFromPartyName(form.getFromPartyName());
			vo.setOperationalFlag(ULDTransactionDetailsVO.OPERATION_FLAG_INSERT);
			vo.setToPartyCode(form.getToPartyCode());
			vo.setToPartyName(form.getToPartyName());
			vo.setUldNature(form.getUldNature()[i]);			
			vo.setUldNumber(form.getUldNum()[i]);		
			vo.setCompanyCode(logonAttributes.getCompanyCode());					
			vo.setTransactionType(form.getTransactionType());
			vo.setPartyType(form.getPartyType());
			if (txndat.length()>0) {
				vo.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			}
			//changed and added for bug ULD565 by a-3045 starts
			vo.setTransactionStationCode(form.getTransactionStation());
			if(form.getDestnAirport()[i] != null && !BLANK.equals(form.getDestnAirport()[i])){
				vo.setTxStationCode(form.getDestnAirport()[i]);
			}else{
				vo.setTxStationCode(form.getTransactionStation());
			}
			//changed and added for bug ULD565 by a-3045 ends		
			vo.setLastUpdateUser(logonAttributes.getUserId());
			vo.setTransactionStatus(TransactionVO.TO_BE_RETURNED);    
			if(form.getUldCondition()!= null && form.getUldCondition().length >0) {  
				vo.setUldConditionCode(form.getUldCondition()[i]);
				if (("DAM").equals(form.getUldCondition()[i])) {
					vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_YES);
				} else {
					vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_NO);
				}
			}
			//Added by A-4072 for CR ICRD-192300 starts
			/*
			 * As part of new UCR report few fields are added in ULD010
			 * they are only required to display in Report
			 * Below details are only expected from screen ULD010.
			 */
			if (form.getDamageRemark()[i] != null &&
					!form.getDamageRemark()[i].isEmpty()) {
				vo.setDamageRemark(form.getDamageRemark()[i]);
			}    
			if (form.getOdlnCode()[i] != null &&
					!form.getOdlnCode()[i].isEmpty()) {
				vo.setOdlnCode(form.getOdlnCode()[i].toUpperCase());
			}
			boolean isDamagedSelected= false;
			if(form.getDamagedFlag()!=null){
				for( int dmgindx = 0; dmgindx < form.getDamagedFlag().length; dmgindx++)
				{					
					if(form.getDamagedFlag()[dmgindx]!=null &&
							i==Integer.parseInt(form.getDamagedFlag()[dmgindx])){
						isDamagedSelected = true;
						break;
					}					
				}
			}
			if (isDamagedSelected){
				vo.setDamageFlagFromScreen(ULDTransactionDetailsVO.FLAG_YES);
			}else{ 
				vo.setDamageFlagFromScreen(ULDTransactionDetailsVO.FLAG_NO);  
			}      
			vo.setOriginatorName(form.getOriginatorName().toUpperCase());     
			//Added by A-4072 for CR ICRD-192300 end
			/*if (form.getDamageCheck()[i] != null
					&& form.getDamageCheck()[i].trim().length() > 0
					&& ULDTransactionDetailsVO.FLAG_YES.equals(form
							.getDamageCheck()[i])) {
				log.log(Log.FINE, "in if" + 8888);
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_YES);
			} else {
				log.log(Log.FINE, "in else" + 8888);
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_NO);
			}*/
			uldTransactionDetailsVOs.add(vo);
		}
		Collection<ULDTransactionDetailsVO> uldVos = transactionVO
				.getUldTransactionDetailsVOs();
		if (uldVos != null && uldVos.size() > 0) {
			transactionVO.getUldTransactionDetailsVOs().removeAll(uldVos);
			transactionVO.getUldTransactionDetailsVOs().addAll(
					uldTransactionDetailsVOs);
		}			
	}
	
	/**
	 * @param transactionVO
	 * @param form
	 * @return
	 */
	 private void updateAccSession(TransactionVO transactionVO,
	    		MaintainULDTransactionForm form) {
			
			Collection<AccessoryTransactionVO> accTxnVos = new ArrayList<AccessoryTransactionVO>();			
			String[] flags = form.getAccOperationFlag();			
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			if(flags != null && flags.length>0){
				for (int i = 0; i < flags.length; i++) {
					if (AccessoryTransactionVO.OPERATION_FLAG_INSERT.equals(flags[i])
							|| AccessoryTransactionVO.OPERATION_FLAG_UPDATE
									.equals(flags[i])) {
						log.log(Log.INFO, "OPFLAG", flags, i);
						AccessoryTransactionVO vo = new AccessoryTransactionVO();
						vo.setAccessoryCode(form.getAcessoryCode()[i]);
						vo.setOperationalFlag(AccessoryTransactionVO.OPERATION_FLAG_INSERT);
						vo.setTransactionType(form.getTransactionType());
						if (form.getAccessoryQuantity()[i] != null
								&& form.getAccessoryQuantity()[i].trim().length() > 0) {
							vo.setQuantity(Integer
									.parseInt(form.getAccessoryQuantity()[i]));
						}
						vo.setCurrOwnerCode(transactionVO.getCurrOwnerCode());
						vo.setCompanyCode(logonAttributes.getCompanyCode());
						vo.setTransactionStationCode(transactionVO
								.getTransactionStation());
						vo.setTransactionDate(transactionVO.getTransactionDate());
						vo.setLastUpdateUser(logonAttributes.getUserId());
						vo.setTransactionNature(transactionVO.getTransactionNature());
						vo.setPartyType(transactionVO.getPartyType());
						vo.setToPartyCode(transactionVO.getToPartyCode());
						accTxnVos.add(vo);
					}
				}
			}
			/*AccessoryTransactionVO accVo = new AccessoryTransactionVO();
			accVo.setAccessoryCode("B");
			accVo.setQuantity(0);
			accTxnVos.add(accVo);*/
			transactionVO.setAccessoryTransactionVOs(accTxnVos);
		}
	 
	 	/**
		 * @param companyCode
		 * @param uldNumbers
		 * @return invalidUlds
		 */
		private Collection<String> validateUldNumberFormat(String companyCode,
				Collection<String> uldNumbers) {
			Collection<String> invalidUlds = null;
			ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
			try {
				invalidUlds = delegate.validateMultipleULDFormats(companyCode,
						uldNumbers);
			} catch (BusinessDelegateException ex) {
				log.log(Log.FINE, "\n\n\ninside handle delegatwe exception");
				handleDelegateException(ex);
			}

			return invalidUlds;
		}
		//added by a-3045 for CR QF1016 ends
		//added by a-3045 for bug ULD551 starts
		/**
		 * 
		 * @param uldNumbers
		 * @return
		 */
		private Collection<ErrorVO> checkDuplicateULDs(Collection<String> uldNumbers) {
			log.log(Log.FINE, "checkDuplicateULDs", uldNumbers);
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			for (String uld1 : uldNumbers) {
				int noOfOccurance = 0;
				String uldNumber = uld1;
				for (String uld2 : uldNumbers) {
					if (uld2.equals(uldNumber)) {
						noOfOccurance++;
					}
					if (noOfOccurance > 1) {
						ErrorVO error = new ErrorVO(
								"uld.defaults.loanborrow.msg.err.duplicateuldsexist");
						errors.add(error);
						return errors;
					}
				}
			}
			return errors;
		}
		//added by a-3045 for bug ULD551 ends
		
		
		
		/**
		 * added by A-5799 for IASCB-27997
		 * @param transactionDetailsVo
		 * @return
		 * @throws SystemException
		 */
		private boolean shouldSendLUCForPoolOwners() throws SystemException{
			boolean shouldSendLUC = false;

	        	String lucSendingForPoolAirline = "N";
	        	Map<String,String> map = new HashMap<String,String>();
	            Collection<String> systemParameterCodes = new ArrayList<String>();
	            systemParameterCodes.add(LUC_SEND_FOR_POOLAIRLINE);
	            
	            
	              try {
					map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
				} catch (BusinessDelegateException e) {
					log.log(Log.ALL, "exception caught", e.getMessage());
				}
	            
	            lucSendingForPoolAirline  = map.get(LUC_SEND_FOR_POOLAIRLINE);
	            
	        	if("Y".equalsIgnoreCase(lucSendingForPoolAirline)){
	        		shouldSendLUC = true;
	        	}
	        
			return shouldSendLUC;
			
		}
		
		/**
		 * added by A-5799 for IASCB-27997
		 * @param transactionDetailsVo
		 * @return
		 * @throws SystemException
		 */
		public boolean isPoolOwnersPresent(Collection<ULDTransactionDetailsVO> transactionDetailsVOs,
				 String transactionStation, String toParty) throws SystemException{
			boolean isPoolOwnersPresent = false;
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			AirlineValidationVO airlineValidationVO = null;
		   	 try{
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(logonAttributes.getCompanyCode(), toParty);
			 }catch (BusinessDelegateException ex) {
				 log.log(Log.SEVERE, " Airline Exception" );
			 }
			 int toAirlineIdr = airlineValidationVO.getAirlineIdentifier(); 
			 
			 
			for(ULDTransactionDetailsVO uldTransactionDetailsVO: transactionDetailsVOs){
				HashMap<String,String> odpairs = new HashMap<String,String>();
				odpairs.put(uldTransactionDetailsVO.getTransactionStationCode(), uldTransactionDetailsVO.getTxStationCode());
				boolean isPoolOwnerPair = false;
				ULDPoolOwnerFilterVO uldPoolOwnerFilterVO = new ULDPoolOwnerFilterVO();
				uldPoolOwnerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				
				int frmPtyIdr = getAirline1(uldTransactionDetailsVO);
				uldPoolOwnerFilterVO.setAirlineIdentifierOne(frmPtyIdr);
				uldPoolOwnerFilterVO.setAirlineIdentifierTwo(toAirlineIdr);			

				uldPoolOwnerFilterVO.setOdpairs(odpairs);
				uldPoolOwnerFilterVO.setAirport(transactionStation);
				log.log(Log.INFO, "Pool Owner Filter VO", uldPoolOwnerFilterVO);
				try{
					isPoolOwnerPair = new ULDDefaultsDelegate().checkforPoolOwner(uldPoolOwnerFilterVO);
				} catch (BusinessDelegateException ex) {
					handleDelegateException(ex);
				}
				if(isPoolOwnerPair){
					isPoolOwnersPresent = true;
				}
			}
			
			return isPoolOwnersPresent;
		}
		
		 /**
		 * added by A-5799 for IASCB-27997
		 * @param transactionDetailsVo
		 * @return
		 * @throws SystemException
		 */
		public int getAirline1(ULDTransactionDetailsVO transactionDetailsVo) throws SystemException{
			int airlineIdr = transactionDetailsVo.getFromPartyIdentifier();
			String airline1 = null;
		    String poolownerairline1basedon = null;
		    ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();

			
	        Map<String,String> map = new HashMap<String,String>();
	        Collection<String> systemParameterCodes = new ArrayList<String>();
	        systemParameterCodes.add(AIRLINE1_BASEDON);
	        
	        try {
				map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
			} catch (BusinessDelegateException e1) {
				log.log(Log.ALL, "exception caught", e1.getMessage());
			}
	        poolownerairline1basedon  = map.get(AIRLINE1_BASEDON);
	        
	        if(poolownerairline1basedon!=null && "OWNCARCODE".equalsIgnoreCase(poolownerairline1basedon)){
	        	ULDVO uldvo = null;
	        	try {
	        		uldvo = new ULDDefaultsDelegate().findULDDetails(transactionDetailsVo.getCompanyCode(),
							transactionDetailsVo.getUldNumber());
				} catch (BusinessDelegateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if(uldvo!=null){
	        		airlineIdr = uldvo.getOperationalAirlineIdentifier();
	        	}   
	        }else{
	        	 int length = transactionDetailsVo.getUldNumber().length();
	        	 airline1 = transactionDetailsVo.getUldNumber().substring(length - 2);  
	        	 AirlineValidationVO airlineValidationVO = null;
			   	 try{
					airlineValidationVO = new AirlineDelegate().validateAlphaCode(logonAttributes.getCompanyCode(), airline1);
				 }catch (BusinessDelegateException ex) {
					 log.log(Log.SEVERE, " Airline Exception" );
				 }
				 airlineIdr = airlineValidationVO.getAirlineIdentifier(); 
	        }
	        
			
			return airlineIdr;
		}
}
