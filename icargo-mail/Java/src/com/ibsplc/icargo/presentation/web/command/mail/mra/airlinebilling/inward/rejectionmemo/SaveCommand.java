/*
 * SaveCommand.java Created on May 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class SaveCommand extends BaseCommand {
	
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");
	private static final String CLASS_NAME = "SaveCommand";
	
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";
	
	
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE="failure_save";
	private static final String BLANK="";
	private static final String ON="on";
	private static final String TRUE="T";
	private static final String FALSE="F";
	
	private static final String NO_DATA="mailtracking.mra.airlinebilling.rejectionmemo.err.nodataforsave";
	
	private static final String SAVE_SUCCESSFULL="mailtracking.mra.airlinebilling.rejectionmemo.err.datasaved";
	private static final String DATA_SAVE_SUCCESSFULL="mailtracking.mra.airlinebilling.rejectionmemo.err.datasaved.withmemono";
	
	private static final String INVEXP_SCREEN="invoiceexp_sucess";
	
	private static final String CAPTURE_INVOICE="captureinvoice";
	private static final String CAPTURE_FORM_ONE="captureformone";
	private static final String  LISTEXCEPTION_DETAILS="airlineexceptions";
	private static final String  LISTEXCEPTION_DTLS="listexception";
	private static final String BLGCURCOD = "USD";
	
	/**
	 *
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		RejectionMemoForm rejectionMemoForm = 
			(RejectionMemoForm)invocationContext.screenModel;
		
		RejectionMemoSession session = 
			(RejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		ErrorVO errorVO=null;
		String clraccpamt=rejectionMemoForm.getChargeNotConvertedToContractIndicator();
		String exgrate=rejectionMemoForm.getIncorrectExchangeRateIndicator();
		log.log(Log.INFO, "check box value---->clraccamt", clraccpamt);
		log.log(Log.INFO, "check box value---->exgrate", exgrate);
		RejectionMemoVO vo=new RejectionMemoVO();
		String screen = rejectionMemoForm.getInvokingScreen();		
		log.log(Log.INFO, "invoking Screen", screen);
		String memo=null;
		double acceptedAmt=0.0;
		double billedAmt=0.0;
		double rejectedAmt=0.0;
		Money moneyAcceptedAmt=null;
		Money moneyRejectedAmt=null;
		
		if(session.getRejectionMemoVO()!=null){
					
			vo=session.getRejectionMemoVO();
			log.log(Log.INFO, "vo for save from session ", vo);
			if (CAPTURE_INVOICE.equals(screen)||CAPTURE_FORM_ONE.equals(screen)||LISTEXCEPTION_DETAILS.equals(screen)){
				/**
				 * @author a-3447 for bug  starts --->> 24257
				 */
				if(rejectionMemoForm.getBillCurBilledAmount()!=null && rejectionMemoForm.getBillCurBilledAmount().trim().length()>0){
					billedAmt=Double.parseDouble(rejectionMemoForm.getBillCurBilledAmount());
				}
				
				if(rejectionMemoForm.getBilCuracceptedAmount()!=null && rejectionMemoForm.getBilCuracceptedAmount().trim().length()>0){
					acceptedAmt=Double.parseDouble(rejectionMemoForm.getBilCuracceptedAmount());
				
				}
				
				if(rejectionMemoForm.getBilCurrejectedAmount()!=null && rejectionMemoForm.getBilCurrejectedAmount().trim().length()>0){
					rejectedAmt=Double.parseDouble(rejectionMemoForm.getBilCurrejectedAmount());
				}
				/**
				 * @author a-3447 for bug ends  --->> 24257
				 */
				if(acceptedAmt>billedAmt){
					//rejectionMemoForm.setAcceptedAmount(String.valueOf(acceptedAmt));
					 errorVO = new ErrorVO("mailtracking.mra.airlinebilling.rejectionmemo.err.acceptedamountgreaterthanbilledamount");
					errors.add(errorVO);
				}
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
				}
				vo=session.getRejectionMemoVO();
				/**@author a-3447
				 * 
				 * Added Null checks for Bug 24257 Starts 
				 */
				try{
					moneyAcceptedAmt=CurrencyHelper.getMoney(BLGCURCOD);
					moneyAcceptedAmt.setAmount(acceptedAmt);
					moneyRejectedAmt=CurrencyHelper.getMoney(BLGCURCOD);
					moneyRejectedAmt.setAmount(rejectedAmt);
					if(rejectionMemoForm.getAcceptedAmount()!=null && rejectionMemoForm.getAcceptedAmount().trim().length()>0){
						vo.setContractAcceptedAmount(Double.parseDouble(rejectionMemoForm.getAcceptedAmount()));
						}
						if(rejectionMemoForm.getRejectedAmount()!=null && rejectionMemoForm.getRejectedAmount().trim().length()>0){
						vo.setContractRejectedAmount(Double.parseDouble(rejectionMemoForm.getRejectedAmount()));
						}
						if(rejectionMemoForm.getBilCuracceptedAmount()!=null && rejectionMemoForm.getBilCuracceptedAmount().trim().length()>0){
						vo.setBillingAcceptedAmount(moneyAcceptedAmt);
						}
						if(rejectionMemoForm.getBilCurrejectedAmount()!=null && rejectionMemoForm.getBilCuracceptedAmount().trim().length()>0){
						vo.setBillingRejectedAmount(moneyRejectedAmt);
						vo.setContractBilledAmount(billedAmt);
						vo.setClearanceBilledAmount(0.0);
						}
						LocalDate rejectedDate = new LocalDate(getApplicationSession().getLogonVO()
								.getAirportCode(), Location.ARP, true);
						vo.setRejectedDate(rejectedDate);
						if(screen!=null){
						vo.setScreenFlag(screen);
						}
				}catch(CurrencyException currencyException){
					log.log(Log.INFO,"CurrencyException found");
				}
				/**@author a-3447
				 * 
				 * Added Null checks for Bug 24257 Ends
				 */
								
			}
			/*if(ON.equals(rejectionMemoForm.getChargeNotConvertedToContractIndicator())){
				vo.setChargeNotConvertedToContractIndicator(TRUE);
			}else{
				vo.setChargeNotConvertedToContractIndicator(FALSE);
			}
			if(ON.equals(rejectionMemoForm.getChargeNotCoveredByContractIndicator())){
				vo.setChargeNotCoveredByContractIndicator(TRUE);
			}else{
				vo.setChargeNotCoveredByContractIndicator(FALSE);
			}
			if(ON.equals(rejectionMemoForm.getIncorrectExchangeRateIndicator())){
				vo.setIncorrectExchangeRateIndicator(TRUE);
			}else{
				vo.setIncorrectExchangeRateIndicator(FALSE);
			}
			if(ON.equals(rejectionMemoForm.getDuplicateBillingIndicator())){
				vo.setDuplicateBillingIndicator(TRUE);
				vo.setDuplicateBillingInvoiceNumber(rejectionMemoForm.getDuplicateBillingInvoiceNumber());
				if(!BLANK.equals(rejectionMemoForm.getDuplicateBillingInvoiceDate())){
					
					vo.setDuplicateBillingInvoiceDate(new LocalDate
							(LocalDate.NO_STATION,Location.NONE,false).setDate( 
									rejectionMemoForm.getDuplicateBillingInvoiceDate() ));
				}
			}else{
				vo.setDuplicateBillingIndicator(FALSE); 
				vo.setDuplicateBillingInvoiceNumber(null);
				vo.setDuplicateBillingInvoiceDate(null);
			}
			if(ON.equals(rejectionMemoForm.getNoApprovalIndicator())){
				vo.setNoApprovalIndicator(TRUE);
			}else{
				vo.setNoApprovalIndicator(FALSE);
			}
			if(ON.equals(rejectionMemoForm.getNoReceiptIndicator())){
				vo.setNoReceiptIndicator(TRUE);
			}else{
				vo.setNoReceiptIndicator(FALSE);
			}
			if(ON.equals(rejectionMemoForm.getRequestAuthorisationIndicator())){
				vo.setRequestAuthorisationIndicator(TRUE);
				vo.setRequestAuthorisationReference(rejectionMemoForm.getRequestAuthorisationReference());
				if(!BLANK.equals(rejectionMemoForm.getRequestAuthorisationDate())){
					vo.setRequestAuthorisationDate(new LocalDate
							(LocalDate.NO_STATION,Location.NONE,false).setDate( 
									rejectionMemoForm.getRequestAuthorisationDate()));
				}
				
			}else{
				vo.setRequestAuthorisationIndicator(FALSE);
				vo.setRequestAuthorisationReference(null);
				vo.setRequestAuthorisationDate(null);
			}
			if(ON.equals(rejectionMemoForm.getOutTimeLimitsForBillingIndicator())){
				vo.setOutTimeLimitsForBillingIndicator(TRUE);
			}else{
				vo.setOutTimeLimitsForBillingIndicator(FALSE);
			}*/
			/*if(ON.equals(rejectionMemoForm.getOtherIndicator())){
				vo.setOtherIndicator(TRUE);*/
				vo.setRemarks(rejectionMemoForm.getRemarks());
			/*}else{
				vo.setOtherIndicator(FALSE);
				vo.setRemarks(BLANK);
			}*/
			
			
			
		}else{
			errorVO=new ErrorVO(NO_DATA);
			errors.add(errorVO);
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		log.log(Log.INFO, "vo for save ", vo);
		if(vo.getScreenFlag() !=null){
		if(vo.getScreenFlag().equals(CAPTURE_INVOICE)||vo.getScreenFlag().equals(CAPTURE_FORM_ONE)){
			try{
				memo=new MailTrackingMRADelegate().saveRejectionMemos(vo);
			}
			catch(BusinessDelegateException businessDelegateException){
				errors=handleDelegateException(businessDelegateException);
			}	
			
		}else if(vo.getScreenFlag().equals(LISTEXCEPTION_DETAILS)){
			try{
				memo=new MailTrackingMRADelegate().saveRejectionMemoForDsn(vo);
			}
			catch(BusinessDelegateException businessDelegateException){
				errors=handleDelegateException(businessDelegateException);
			}	
		}}
		else{
		try{
			new MailTrackingMRADelegate().updateRejectionMemo(vo);
		}
		catch(BusinessDelegateException businessDelegateException){
			errors=handleDelegateException(businessDelegateException);
		}
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}else{
			rejectionMemoForm.setMemoCode(BLANK);
			rejectionMemoForm.setInvoiceNumber(BLANK);
			rejectionMemoForm.setAirlineCode(BLANK);
			rejectionMemoForm.setYourInvoiceNumber(BLANK);
			rejectionMemoForm.setYourInvoiceDate(BLANK);
			rejectionMemoForm.setYourReferenceNumber(BLANK);
			rejectionMemoForm.setMonthOfClearance(BLANK);
			rejectionMemoForm.setMonthOfTransaction(BLANK);
			rejectionMemoForm.setDsn(BLANK);
			/*rejectionMemoForm.setChargeNotConvertedToContractIndicator(BLANK);
			rejectionMemoForm.setChargeNotCoveredByContractIndicator(BLANK);
			rejectionMemoForm.setIncorrectExchangeRateIndicator(BLANK);
			rejectionMemoForm.setDuplicateBillingIndicator(BLANK);
			rejectionMemoForm.setNoApprovalIndicator(BLANK);
			rejectionMemoForm.setNoReceiptIndicator(BLANK);*/
			rejectionMemoForm.setOtherIndicator(BLANK);
			/*rejectionMemoForm.setRequestAuthorisationIndicator(BLANK);
			rejectionMemoForm.setOutTimeLimitsForBillingIndicator(BLANK);*/
			rejectionMemoForm.setRemarks(BLANK);
			rejectionMemoForm.setDuplicateBillingInvoiceDate(BLANK);
			rejectionMemoForm.setDuplicateBillingInvoiceNumber(BLANK);
			rejectionMemoForm.setRequestAuthorisationReference(BLANK);
			rejectionMemoForm.setRequestAuthorisationDate(BLANK);
			rejectionMemoForm.setFromScreenFlag(BLANK);
			rejectionMemoForm.setInvokingScreen(screen); 
			session.removeRejectionMemoVO();
			if("invexpscreen".equals(rejectionMemoForm.getFromScreenFlag())){
				invocationContext.target = INVEXP_SCREEN;
			}else{
				if(CAPTURE_INVOICE.equals(vo.getScreenFlag())||CAPTURE_FORM_ONE.equals(vo.getScreenFlag())||LISTEXCEPTION_DETAILS.equals(vo.getScreenFlag())){
					log.log(Log.INFO, "Memo Code--->", memo);
					/*	Object[] obj = {memo};
					errorVO=new ErrorVO(DATA_SAVE_SUCCESSFULL,obj); 
					log.log(Log.INFO, "errorVO" +errorVO);
					log.log(Log.INFO, "obj" +obj);
					*/
					if(memo!=null&& memo.trim().length()>0){
						errorVO = new ErrorVO(  DATA_SAVE_SUCCESSFULL,
			                     new String[]{memo});
					}else{
						errorVO=new ErrorVO(SAVE_SUCCESSFULL); 
					}
					
				}
				else{
					errorVO=new ErrorVO(SAVE_SUCCESSFULL); 
					
				}
				
				
			}
		}
		//to do delegate call for save
		
		/*rejectionMemoForm.setChargeNotConvertedToContractIndicator(BLANK);
		rejectionMemoForm.setChargeNotCoveredByContractIndicator(BLANK);
		rejectionMemoForm.setIncorrectExchangeRateIndicator(BLANK);
		rejectionMemoForm.setDuplicateBillingIndicator(BLANK);
		rejectionMemoForm.setNoApprovalIndicator(BLANK);
		rejectionMemoForm.setNoReceiptIndicator(BLANK);
		rejectionMemoForm.setOtherIndicator(BLANK);
		rejectionMemoForm.setRequestAuthorisationIndicator(BLANK);
		rejectionMemoForm.setOutTimeLimitsForBillingIndicator(BLANK); */
		
		
		
		invocationContext.addError(errorVO);
		invocationContext.target = SAVE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
}

