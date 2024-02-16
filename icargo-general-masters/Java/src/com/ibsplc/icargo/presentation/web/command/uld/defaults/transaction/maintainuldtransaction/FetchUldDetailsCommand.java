/*
 * FetchUldDetailsCommand.java  Created on Sep 25, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2412
 * 
 */
public class FetchUldDetailsCommand extends BaseCommand {
	/**
	 * Logger for Fetch ULd Details
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of Release ULD screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	private static final String BLANK = "";

	/**
	 * target String if success
	 */
	private static final String LIST_SUCCESS = "list_success";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ULD ", "FetchUldDetailsCommand");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		// Update session with Form values
		updateSession(transactionVO, maintainULDTransactionForm);
		String uldNum = "";
		int rowIndex = 0;
		try {
			rowIndex = Integer.parseInt(maintainULDTransactionForm
					.getRowIndex());
			uldNum = maintainULDTransactionForm.getUldNum()[rowIndex]
					.toUpperCase();
		} catch (NumberFormatException e) {
			log.log(Log.INFO, "Number format caught");
		}
		log.log(Log.INFO, "uldNum is ---------->>", uldNum);
		boolean isFormatValid = false;
		if (uldNum != null && uldNum.trim().length() > 0) {
			try {
				isFormatValid = new ULDDefaultsDelegate().validateULDFormat(
						loanBorrowULDSession.getCompanyCode(), uldNum);

			} catch (BusinessDelegateException businessDelegateException) {
				//errors = handleDelegateException(businessDelegateException);
				//invocationContext.addAllError(errors);
				invocationContext.target = LIST_SUCCESS;
				return;
			}
		}

		ULDListVO uldListVO = null;
		try {
			uldListVO = new ULDDefaultsDelegate()
					.fetchULDDetailsForTransaction(logonAttributes
							.getCompanyCode(), uldNum);
		} catch (BusinessDelegateException e) {
			e.getMessage();
		}
		log.log(Log.INFO, "uldListVO returned is ---------90000->>", uldListVO);
		if (uldListVO != null) {
			ArrayList<ULDTransactionDetailsVO> uldTxnDetails = new ArrayList<ULDTransactionDetailsVO>(
					transactionVO.getUldTransactionDetailsVOs());
			ULDTransactionDetailsVO uldDetailVo = uldTxnDetails.get(rowIndex);
			uldDetailVo.setUldNature(uldListVO.getUldNature());
			if ("D".equals(uldListVO.getDamageStatus())) {
				uldDetailVo.setUldConditionCode("DAM");
			}
		}
		loanBorrowULDSession.setTransactionVO(transactionVO);
		// invocationContext.addAllError(errors);
		invocationContext.target = LIST_SUCCESS;

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
		if (form.getCrn() != null) {
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
		LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, true);
		if (!txnTime.contains(":")) {
			txnTime = txnTime.concat(":00");
		}
		StringBuilder txndat = new StringBuilder();
		txndat.append(txnDate).append(" ").append(txnTime).append(":00");
		// txnDate = txnDate.concat(" "+txnTime+(":00"));
		if (txndat.length()>0) {
			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat
					.toString()));
			transactionVO.setStrTransactionDate(strTxnDate);
			transactionVO.setTransactionTime(txnTime);
		} else {
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
			transactionVO.setToPartyCode(form.getToPartyCode().toUpperCase().trim());
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

		if ("Y".equals(form.getLoaded())) {
			transactionVO.setEmptyStatus("N");
		} else {
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
			vo
					.setOperationalFlag(ULDTransactionDetailsVO.OPERATION_FLAG_INSERT);
			vo.setToPartyCode(form.getToPartyCode());
			vo.setToPartyName(form.getToPartyName());
			vo.setUldNature(form.getUldNature()[i]);
			vo.setUldNumber(form.getUldNum()[i]);
			vo.setUldType(BLANK);
			vo.setCompanyCode(logonAttributes.getCompanyCode());
			vo.setTransactionType(form.getTransactionType());
			vo.setPartyType(form.getPartyType());
			if (txndat.length()>0) {
				vo.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			}
			// changed and added for bug ULD565 by a-3045 starts
			vo.setTransactionStationCode(form.getTransactionStation());
			if (form.getDestnAirport()[i] != null
					&& !BLANK.equals(form.getDestnAirport()[i])) {
				vo.setTxStationCode(form.getDestnAirport()[i]);
			} else {
				vo.setTxStationCode(form.getTransactionStation());
			}
			// changed and added for bug ULD565 by a-3045 ends
			vo.setLastUpdateUser(logonAttributes.getUserId());
			vo.setTransactionStatus(TransactionVO.TO_BE_RETURNED);
			// added by a-3045 for bug ULD558 starts
			vo.setUldConditionCode(form.getUldCondition()[i]);
			// added by a-3045 for bug ULD558 ends
			if (("DAM").equals(form.getUldCondition()[i])) {
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
			/*
			 * if (form.getDamageCheck()[i] != null &&
			 * form.getDamageCheck()[i].trim().length() > 0 &&
			 * ULDTransactionDetailsVO.FLAG_YES.equals(form
			 * .getDamageCheck()[i])) {
			 * vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_YES); } else {
			 * vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_NO); }
			 */
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

}
