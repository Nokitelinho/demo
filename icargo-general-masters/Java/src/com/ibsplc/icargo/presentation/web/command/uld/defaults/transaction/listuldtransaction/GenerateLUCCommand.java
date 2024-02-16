/*
 * GenerateLUCCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.msgbroker.config.format.vo.MessageRuleDefenitionVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCReceivingPartyDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCTransferringPartyDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCULDConditionDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCULDDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.LUCULDIdentificationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.message.vo.MessageConfigConstants;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1862
 * 
 */
public class GenerateLUCCommand extends BaseCommand {

	/**
	 * Logger for laon borrow uld
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD ");

	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

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
	
	private static final String TO_BE_INVOICED ="R";
	
	private static final String TO_BE_RETURNED ="T";
	
	private static final String LUC_SEND_FOR_POOLAIRLINE = "uld.default.lucsendingforpoolairline";
	
	private static final String AIRLINE1_BASEDON = "uld.default.poolownerairline1basedon";
	
	private static final String AIRLINE = "A";
	
	private static final String LUC_NOT_SEND_TO_POOL="uld.defaults.luccannotbesendtopool";
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
		
       ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    	
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ListMessageSession msgsession = 
			getScreenSession( MSGMODULE_NAME,MSGSCREEN_ID);
		
		Collection<MessageDespatchDetailsVO> messageDespatchDetails = new ArrayList<MessageDespatchDetailsVO>();
		messageDespatchDetails = msgsession.getDespatchDetails();
		
	    Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
	
		TransactionListVO   transactionListVO = listULDTransactionSession.getTransactionListVO();
    	Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
    	Page<ULDTransactionDetailsVO>  uldTransactionDetailsVOs  
													= transactionListVO.getTransactionDetailsPage();
		String[] primaryKey = listULDTransactionForm.getUldDetails();

		if (LUCMessageVO.FLAG_YES.equals(listULDTransactionForm.getFromPopup())) {
			primaryKey = listULDTransactionForm.getUldNumbersSelected() != null ? listULDTransactionForm.getUldNumbersSelected().split(",") : null;
		}
		
		StringBuilder uldDetails = new StringBuilder();
		
		int count = 0;
		if (primaryKey != null) {
			for(String key : primaryKey) {
				if (key != null && key.trim().length() > 0) {
					if (count == 0) {
						uldDetails.append(key);
						count++;
					} else {
						uldDetails.append(",");
						uldDetails.append(key);
					}				
				}
			}
		}
		
		listULDTransactionForm.setUldNumbersSelected(uldDetails.toString());
		
		if (primaryKey != null && primaryKey.length > 0) {
			int cnt=0;
			int index = 0;
			int primaryKeyLen = primaryKey.length;
			if (uldTransactionDetailsVOs != null && uldTransactionDetailsVOs.size() != 0) {
				for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {
					index++;
					String primaryKeyFromVO =new StringBuilder(uldTransactionDetailsVO.getUldNumber())
																		.append(uldTransactionDetailsVO.getTransactionRefNumber())
																		.append(index).toString();
						if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
							equalsIgnoreCase(primaryKey[cnt].trim())) {						           
        		    		         uldTxnDetailsVOs.add(uldTransactionDetailsVO);
        		    	          	 cnt ++;
					   }
				  }
			}
		}
		
		
		LUCMessageVO messageVO = new LUCMessageVO();
		messageVO.setCarrierCode(getApplicationSession().getLogonVO().getOwnAirlineCode());
		messageVO.setAirlineCode(getApplicationSession().getLogonVO().getOwnAirlineCode());
		messageVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		messageVO.setMessageStandard(MESSAGE_STANDARD);
		messageVO.setMessageType(MESSAGE_TYPE);
		messageVO.setTransactionId(MessageConfigConstants.TXN_SCREEN);
		messageVO.setLastUpdateUser(getApplicationSession().getLogonVO().getUserId());		
		
		log.log(Log.FINE, "TRANSACTION DETAILS VOS--->", uldTxnDetailsVOs);
		if (uldTxnDetailsVOs == null || uldTxnDetailsVOs.size() == 0) {
			ErrorVO error = new ErrorVO(
					"uld.defaults.loanborrowuld.msg.err.cannotgenerateluc");
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = LUC_FAILURE;
			return;
		}
		LocalDate dateOfTransfer = new LocalDate(logonAttributes.getAirportCode(),Location.ARP, true);
		Collection<LUCULDDetailsVO> uldDetailsVOs = new ArrayList<LUCULDDetailsVO>();
		for (ULDTransactionDetailsVO detailsVO : uldTxnDetailsVOs) {
			LUCULDDetailsVO uldDetailsVO = new LUCULDDetailsVO();
			//added by a-3045 for bug 36242 on 09Feb09 starts
			if(TO_BE_RETURNED.equals(detailsVO.getTransactionStatus()) || 
					"L".equals(detailsVO.getTransactionType())){
				dateOfTransfer = detailsVO.getTransactionDate();
			}else{
				dateOfTransfer = detailsVO.getReturnDate();
			}
			log.log(Log.FINE, "date of transfer--++---++++++++++-->",
					dateOfTransfer);
			StringBuilder dateoFTransfer = new StringBuilder();
			dateoFTransfer.append(dateOfTransfer.toDisplayDateOnlyFormat().substring(0, 2));
			dateoFTransfer.append(dateOfTransfer.toDisplayDateOnlyFormat().substring(3, 6)
					.toUpperCase());
			dateoFTransfer.append(dateOfTransfer.toDisplayDateOnlyFormat().substring(7, 11));
			log.log(Log.FINE,
					"date of transfer after formatting--------------->",
					dateoFTransfer);
			StringBuilder time = new StringBuilder();
			String times[]=dateOfTransfer.toDisplayTimeOnlyFormat().split(":");
			time.append(times[0]);
			time.append(times[1]);
			log.log(Log.FINE, "time of transfer---------------->", time);
			//added by a-3045 for bug 36242 on 09Feb09 ends
			//if-else check added by a-3278 for bug 33991 on 19Jan09 
			if(detailsVO.getReturnCRN() != null && detailsVO.getReturnCRN().trim().length() > 0){
				uldDetailsVO.setUldReceiptNumber(detailsVO.getReturnCRN());
			}else{
				uldDetailsVO.setUldReceiptNumber(detailsVO.getControlReceiptNumber());
			}
			uldDetailsVO.setDateofTransfer(dateoFTransfer.toString());
			
			if("T".equals(detailsVO.getTransactionStatus()) || "L".equals(detailsVO.getTransactionType())){
				log.log(Log.INFO, "%%%%DESTINATION OF TRANSFER%%", detailsVO.getTxStationCode());
				log.log(Log.INFO, "%%%%LOCATION OF TRANSFER%%", detailsVO.getTransactionStationCode());
				uldDetailsVO.setDestinationLocation(Objects.nonNull(detailsVO.getTxStationCode()) ?
						detailsVO.getTxStationCode() : detailsVO.getTransactionStationCode());
				uldDetailsVO.setLocationOftransfer(detailsVO.getTransactionStationCode());
			}else{
				uldDetailsVO.setDestinationLocation(Objects.nonNull(detailsVO.getReturnStationCode()) ?
						detailsVO.getReturnStationCode() : detailsVO.getTransactionStationCode());
				uldDetailsVO.setLocationOftransfer(Objects.nonNull(detailsVO.getReturnStationCode()) ?
						detailsVO.getReturnStationCode() : detailsVO.getTransactionStationCode());
				log.log(Log.INFO, "%%%%LOCATION OF TRANSFER%%", detailsVO.getReturnStationCode());
				
				
			}
			
			uldDetailsVO.setTimeofTransfer(Integer.parseInt(time.toString()));

			LUCULDIdentificationVO identificationVO = new LUCULDIdentificationVO();
			String uldNumber = detailsVO.getUldNumber();
			identificationVO.setUldType(uldNumber.substring(0, 3));
			identificationVO.setUldOwnerCode(uldNumber.substring(uldNumber
					.length() - 2));
			identificationVO.setUldSerialNumber(uldNumber.substring(3,
					uldNumber.length() - 2));
			uldDetailsVO.setUldIdentificationVO(identificationVO);
			LUCReceivingPartyDetailsVO recevingPartyDetailsVO = new LUCReceivingPartyDetailsVO();
			LUCTransferringPartyDetailsVO transferringPartyDetailsVO = new LUCTransferringPartyDetailsVO();
			if("T".equals(detailsVO.getTransactionStatus())){
				if("A".equals(detailsVO.getPartyType())){
					recevingPartyDetailsVO.setCarrierCode(detailsVO.getToPartyCode());
				}else{
					recevingPartyDetailsVO.setCarrierCode("YY");
					recevingPartyDetailsVO.setNonCarrierId(detailsVO.getToPartyCode());
				}
				transferringPartyDetailsVO.setCarrierCode(detailsVO.getFromPartyCode());
			}else{
				if("A".equals(detailsVO.getPartyType())){
					transferringPartyDetailsVO.setCarrierCode(detailsVO.getToPartyCode());
				}else{
					transferringPartyDetailsVO.setCarrierCode("YY");
					transferringPartyDetailsVO.setNonCarrierId(detailsVO.getToPartyCode());
				}
				recevingPartyDetailsVO.setCarrierCode(detailsVO.getFromPartyCode());
			}
			
			
			
			uldDetailsVO.setReceivingPartyDetailsVO(recevingPartyDetailsVO);
			
			log.log(Log.INFO, "transaction type in generateLUC", detailsVO.getTransactionType());
			uldDetailsVO
					.setTransferringPartyDetailsVO(transferringPartyDetailsVO);
			LUCULDConditionDetailsVO conditionDetailsVO = new LUCULDConditionDetailsVO();
			/*if ("Y".equals(detailsVO.getDamageStatus())) {
				conditionDetailsVO.setDamageCode("DAM");
			} else {
				conditionDetailsVO.setDamageCode("SER");
			}*/
			 conditionDetailsVO.setServiceableCode(Objects.nonNull(detailsVO.getUldConditionCode()) ? 
					detailsVO.getUldConditionCode() : "SER");
			conditionDetailsVO.setDamageCode(detailsVO.getUldConditionCode());
			uldDetailsVO.setUldConditionDetailsVO(conditionDetailsVO);
			uldDetailsVOs.add(uldDetailsVO);
//			added by nisha on 24jan08
			//changed by nisha on 23Jun08 for BugFix
			if("A".equals(detailsVO.getPartyType())){
				if("L".equals(detailsVO.getTransactionType())){
					messageVO.setCarrierCode(detailsVO.getToPartyCode());
				}else{
					messageVO.setCarrierCode(detailsVO.getFromPartyCode());
				}
			}else{
				messageVO.setCarrierCode(detailsVO.getFromPartyCode());
			}
		}
		log.log(Log.FINE, "uld details vos------------------->", uldDetailsVOs);
		messageVO.setUldDetails(uldDetailsVOs);
		messageVO.setStationCode(getApplicationSession().getLogonVO().getAirportCode());
		
		
		log.log(Log.FINE, "MESSAGE VO TO SERVER===============", messageVO);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Map<String , String> data=null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		Collection<MessageVO> messageVOs=null;
		
		
		boolean isPoolOwnerPair = false;
		try {
			isPoolOwnerPair = isPoolOwnersPresent(uldTxnDetailsVOs);
		} catch (SystemException e) {
			log.log(Log.SEVERE, "Error",
					e.getMessage());
		}
		log.log(Log.INFO, "Pool Owners---->", isPoolOwnerPair);
		
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
		
		//Added by A-7131 for ICRD-220974 starts
		
		if(!LUCMessageVO.FLAG_YES.equals(listULDTransactionForm.getFromPopup())){
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
			listULDTransactionForm.setMsgFlag("TRUE");
		}else {
			//Added by A-7131 for ICRD-220974 ends
			try {
				if (messageDespatchDetails != null) {
					messageVO.setDespatchDetails(messageDespatchDetails);
				}
			messageVOs=delegate.generateLUCMessage(messageVO);
			log.log(Log.INFO, "message VOs**********", messageVOs);
			if(messageVOs!=null && messageVOs.size()>0){
				ArrayList<MessageVO> msgVOs =new ArrayList<MessageVO>(messageVOs);
				log.log(Log.INFO, "encode VO from server-->", msgVOs.size());
				log.log(Log.INFO, "encode VO from server-->", msgVOs.get(0));
				msgsession.setMessage(msgVOs.get(0));
				msgsession.removeDespatchDetails();
				msgsession.removeMessageVO();
					listULDTransactionForm.setMsgFlag("FALSE");
					listULDTransactionForm.setFromPopup("N");
				// to do....set the raw msg in form
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
		}
		}
		
		
		 if(error!=null && error.size()>0){
	    	   log.log(Log.ALL, "generateLUCMessage errors-----", error.size());
			invocationContext.addAllError(error);
	   		   invocationContext.target = LUC_FAILURE;
	   		   for(ErrorVO vo:error){
	   			   log.log(Log.ALL, "generateLUCMessage error code is", vo.getErrorCode());	   			   
	   		   }	   		   
	   		return;	    	       
	    }
		/*maintainULDTransactionForm.setSaveStatus("");
		
		maintainULDTransactionForm.setTxnTypeDisable("");	   
	    maintainULDTransactionForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    ArrayList<String> uldNos=new ArrayList<String>();	   
	    
	    if(data!=null){
	    for(String uldno:data.keySet()){	    
		    //to show LUC reciept numbers
		    String uld=uldno;
		    String reciept=data.get(uldno);
		    log.log(Log.FINE, "uld===============" + uld);
		    log.log(Log.FINE, "reciept===============" + reciept);
		    String receiptDetails=uld+"&"+reciept;
		    uldNos.add(receiptDetails);
		   
		    //Object[] obj1 = { uld,reciept };	    
		    //ErrorVO errorVO = new ErrorVO("uld.defaults.uldtransaction.lucgenerated",obj1);
		    //errorVO.setErrorDisplayType(ERROR);
		    //errors.add(errorVO);	    
	    }
	    maintainULDTransactionForm.setLucPopup("open");	    
	    loanBorrowULDSession.setULDReceipt(uldNos);
	   
	    }*/
	   // if(errors!=null && errors.size()>0){
	   // invocationContext.addAllError(errors);
	   // }
		invocationContext.target = LUC_SUCCESS;
	}
	
	
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
	public boolean isPoolOwnersPresent(Collection<ULDTransactionDetailsVO> transactionDetailsVOs) throws SystemException{
		boolean isPoolOwnersPresent = false;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		for(ULDTransactionDetailsVO uldTransactionDetailsVO: transactionDetailsVOs){
			if(AIRLINE.equals(uldTransactionDetailsVO.getPartyType())){
				HashMap<String,String> odpairs = new HashMap<String,String>();
				odpairs.put(uldTransactionDetailsVO.getTransactionStationCode(), uldTransactionDetailsVO.getTxStationCode());
				
				AirlineValidationVO airlineValidationVO = null;
			   	 try{
					airlineValidationVO = new AirlineDelegate().validateAlphaCode(logonAttributes.getCompanyCode(), 
							uldTransactionDetailsVO.getToPartyCode());
				 }catch (BusinessDelegateException ex) {
					 log.log(Log.SEVERE, " Airline Exception" );
				 }
				 int toAirlineIdr = airlineValidationVO.getAirlineIdentifier(); 
				 
				 
				boolean isPoolOwnerPair = false;
				ULDPoolOwnerFilterVO uldPoolOwnerFilterVO = new ULDPoolOwnerFilterVO();
				uldPoolOwnerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				
				int frmPtyIdr = getAirline1(uldTransactionDetailsVO);
				uldPoolOwnerFilterVO.setAirlineIdentifierOne(frmPtyIdr);
				uldPoolOwnerFilterVO.setAirlineIdentifierTwo(toAirlineIdr);			
				uldPoolOwnerFilterVO.setOdpairs(odpairs);
				uldPoolOwnerFilterVO.setAirport(uldTransactionDetailsVO.getTransactionStationCode());
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
