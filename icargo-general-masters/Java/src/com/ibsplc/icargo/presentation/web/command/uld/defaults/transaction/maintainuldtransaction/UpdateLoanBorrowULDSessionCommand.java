/*
 * UpdateLoanBorrowULDSessionCommand.java  Created on Jun 18, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class UpdateLoanBorrowULDSessionCommand extends BaseCommand {
	
	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";
	/**
	 * target String if success
	 */
	private static final String UPDATE_SUCCESS = "update_success";
    
    private static final String BLANK = "";
   
    //private static final String SHOW_DAMAGE = "showDamage";
       
    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("LoanBorrowUpdateSessionCommand","execute");   	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();		
    	MaintainULDTransactionForm maintainULDTransactionForm 
    								= (MaintainULDTransactionForm) invocationContext.screenModel;
        LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
									MODULE_NAME, SCREEN_ID);
    	ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		String companyCode = logonAttributes.getCompanyCode();
		updateSession(transactionVO, maintainULDTransactionForm);
		updateAccSession(transactionVO, maintainULDTransactionForm);
		// ULD Number Mandatory validation
		Collection<ULDTransactionDetailsVO> detailsVOs = transactionVO
				.getUldTransactionDetailsVOs();
		if (detailsVOs != null && detailsVOs.size() > 0) {
			for (ULDTransactionDetailsVO vo : detailsVOs) {
				if (vo.getUldNumber() == null
						|| vo.getUldNumber().trim().length() == 0) {
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.loanborrowuld.Uldnumberempty");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
			}
		}
		// Validate ULD Number Format
		Collection<String> uldNumbers = new ArrayList<String>();
		Collection<String> invalidUlds = null;

		if (detailsVOs != null && detailsVOs.size() > 0) {
			for (ULDTransactionDetailsVO vo : detailsVOs) {
				if (vo.getUldNumber() != null
						&& vo.getUldNumber().trim().length() > 0) {
					uldNumbers.add(vo.getUldNumber());
				}
			}
		}
		invalidUlds = validateUldNumberFormat(companyCode, uldNumbers);
		log.log(Log.INFO, "invalidUlds are ---->>>>>", invalidUlds);
		if (invalidUlds != null && invalidUlds.size() > 0) {
			int size = invalidUlds.size();
			for (int i = 0; i < size; i++) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.loanborrowULD.msg.err.invaliduldformat",
						new Object[] { ((ArrayList<String>) invalidUlds).get(i) });
				errors.add(error);
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = UPDATE_SUCCESS;			
			loanBorrowULDSession.setTransactionVO(transactionVO);
			maintainULDTransactionForm.setShowDamage(TransactionVO.FLAG_NO);		
			return;
		}
		//added by a-3045 for bug ULD551 starts
		Collection<ErrorVO> errs = checkDuplicateULDs(uldNumbers);
		errors.addAll(errs);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);	
			maintainULDTransactionForm.setTxnTypeDisable("Y");
				invocationContext.target = UPDATE_SUCCESS;			
				loanBorrowULDSession.setTransactionVO(transactionVO);
			return;
		}		
		//added by a-3045 for bug ULD551 ends
		// Check if ULD exists in system	
		ULDValidationVO uldValidationVO = new ULDValidationVO();

		for (String uldNumber : uldNumbers) {
			try {
				uldValidationVO = delegate.validateULD(companyCode, uldNumber);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE,
						"inside try...caught businessDelegateException");
				errors = handleDelegateException(businessDelegateException);
			}
			if (uldValidationVO == null) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.loanBorrowULD.msg.err.ulddoesnotexists",
						new Object[] { uldNumber });
				errors.add(error);
			}
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = UPDATE_SUCCESS;			
			loanBorrowULDSession.setTransactionVO(transactionVO);
			maintainULDTransactionForm.setShowDamage(TransactionVO.FLAG_NO);			
			return;
		}		
		log.log(Log.INFO,
				"maintainULDTransactionForm.getUldNumbersSelected() ",
				maintainULDTransactionForm.getUldNumbersSelected());
		maintainULDTransactionForm.setShowDamage(TransactionVO.FLAG_YES);
		invocationContext.target = UPDATE_SUCCESS;		
    }	
    
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
		if (form.getCrn() != null ) {			
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
		
		log.log(Log.FINE, "\n\n\n\nDATE AND TIME", txndat);
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
			if(form.getUldCondition()!=null){
			vo.setUldConditionCode(form.getUldCondition()[i]);
			}
			if (form.getUldCondition()!=null && ("DAM").equals(form.getUldCondition()[i])) {
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_YES);
			} else {
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_NO);
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
    
}
