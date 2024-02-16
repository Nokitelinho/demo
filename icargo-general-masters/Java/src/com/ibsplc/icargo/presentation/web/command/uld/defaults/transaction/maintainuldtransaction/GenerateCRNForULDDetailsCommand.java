/*
 * GenerateCRNForULDDetailsCommand.java  Created on Jun 18, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class GenerateCRNForULDDetailsCommand extends BaseCommand {

	/**
	 * Logger for Release ULD
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

	/**
	 * target String if success
	 */
	private static final String CRN_SUCCESS = "crn_success";

	private static final String CRN_FAILURE = "crn_failure";

	private static final String BLANK = "";

	private static final String AGENT = "G";
	
	private static final String ULD_COUNT_PER_UCR = "shared.airline.uldCountPerUCR";
	//Added by A-5116
	private static final String INVALID_ULD_TRANSACTION = "uld.defaults.transaction.msg.err.invalidtransaction";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.INFO, "INSIDE GenerateCRNForULDDetailsCommand");
		//ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		MaintainULDTransactionForm maintainULDTransactionForm
				= (MaintainULDTransactionForm) invocationContext.screenModel;
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
					MODULE_NAME, SCREEN_ID);
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		//String companyCode = logonAttributes.getCompanyCode();
		String fromPrtyCode = maintainULDTransactionForm.getFromPartyCode().toUpperCase();
		//Added by A-5116 for ICRD-48135 starts
		Collection<ErrorVO> errors = null;
	/*	 Modified by A-3415 for ICRD-114538
	 * if("B".equals(maintainULDTransactionForm.getTransactionType()) &&
				"G".equals(maintainULDTransactionForm.getPartyType())){
			 errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
				error = new ErrorVO(INVALID_ULD_TRANSACTION);
				errors.add(error);
		}*/
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = CRN_FAILURE;
			return;
		}
		//Added by A-5116 for ICRD-48135 ends
		if("R".equals(maintainULDTransactionForm.getTransactionType()) &&
				!"A".equals(maintainULDTransactionForm.getPartyType())){
			// Modified by A-3268 for Bug 101907
			fromPrtyCode = maintainULDTransactionForm.getToPartyCode().toUpperCase();
		}
		log.log(Log.INFO, "FROM PARTY CODE IS --->>", fromPrtyCode);
		log.log(Log.INFO, "generateConditionCodes --->>>>>");

		generateConditionCodes(maintainULDTransactionForm, loanBorrowULDSession);


		// Update session with Form values
		if (transactionVO != null) {
			updateSession(transactionVO, maintainULDTransactionForm);
		}
		/*ULDAgreementVO uldAgreementVO = loanBorrowULDSession.getAgreementVO();
		Collection<String> agreementNumbers = new ArrayList<String>();
		// Will enter this condition only for first time
		if (uldAgreementVO == null) {
			// Search for Agreement
			agreementNumbers = searchForAgreement(transactionVO);
			log.log(Log.INFO, "AGREEMNT NUMBERS RETURNED ARE --->>"
					+ agreementNumbers);
			if (agreementNumbers != null) {
				maintainULDTransactionForm.setAgreementFound(TransactionVO.FLAG_YES);
			} else {
				maintainULDTransactionForm.setAgreementFound(TransactionVO.FLAG_NO);
			}
		}
		// IF no agreement exists --> return
		if (TransactionVO.FLAG_NO.equals(maintainULDTransactionForm.getAgreementFound())) {
			invocationContext.target = CRN_FAILURE;
			return;
		}*/
		ULDTransactionDetailsVO uLDTransactionDetailsVO = new ULDTransactionDetailsVO();
		uLDTransactionDetailsVO.setUldNumber("");
		errors = new ArrayList<ErrorVO>();
		String airlineID = null;
		//int airlineId = 0;
		// QF1504
		//Modified as opart of ICRD-2726 by A-3767 on 29Jun11
		
		String controlRecieptNumber = null;
		controlRecieptNumber = getLastCRNNumber(maintainULDTransactionForm);
		//Commented as opart of ICRD-2726 by A-3767 on 29Jun11
		/*if(AGENT.equals(maintainULDTransactionForm.getPartyType())){
			countPerUCR = 3;
		}else{*/
		//Commented and modified by A-4072 as part of CR ICRD-192300
		int countPerUCR = getUldCountperUCR(logonAttributes.getCompanyCode(),
				logonAttributes.getOwnAirlineIdentifier())-1;
		/*		try{
					AirlineDelegate airlineDelegate = new AirlineDelegate();
					AirlineValidationVO airlineValidationVO = null;
					airlineValidationVO = airlineDelegate.validateAlphaCode(logonAttributes.getCompanyCode(),
							fromPrtyCode);
					if(airlineValidationVO != null){
						airlineId = airlineValidationVO.getAirlineIdentifier();
					}
					String ULD_COUNT_PER_UCR = "shared.airline.uldCountPerUCR";
					Collection<String> parameterCodes = new ArrayList<String>();
					Map<String,String> airlineParameterMap = null;
					parameterCodes.add(ULD_COUNT_PER_UCR);
					airlineParameterMap = airlineDelegate.findAirlineParametersByCode(logonAttributes
							.getCompanyCode(), airlineId, parameterCodes);
					if(airlineParameterMap != null && !airlineParameterMap.isEmpty()){

						String value = airlineParameterMap.get(ULD_COUNT_PER_UCR);
						if(value != null && value.trim().length() > 0){
							countPerUCR = Integer.parseInt(value)-1;
						}
					}
				}catch(BusinessDelegateException businessDelegateException){
					errors = handleDelegateException(businessDelegateException);
		        	
				}
		//}
		//Added as opart of ICRD-2726 by A-3767 on 29Jun11
		if(countPerUCR==0)
		 {
			countPerUCR=2;//Iata Standard is 3
		}*/

		log.log(Log.INFO, "Count Per UCR ---->", countPerUCR);
		log.log(Log.INFO, "LAST CRN IS ---->", controlRecieptNumber);
		if (controlRecieptNumber == null) {
			log.log(Log.ALL, "crn number is null");
			try {
				String crn = new ULDDefaultsDelegate()
						.findCRNForULDTransaction(logonAttributes
								.getCompanyCode(), fromPrtyCode);
				if (crn != null && crn.length() > 0) {
					//Modified by A-4803 for ICRD-6983
					int count=0;
					String number = crn.substring(crn.length() - 7, crn.length());  
							  
					airlineID = new StringBuilder(crn.substring(0, 4)).append(count)
																			.toString();
					log.log(Log.ALL, "airlineID is===", airlineID);
					uLDTransactionDetailsVO.setControlReceiptNumberPrefix(airlineID);
					uLDTransactionDetailsVO.setCrnToDisplay(new StringBuilder(number)
																		.toString());
				}
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		} else {
			String crnNumberPart = controlRecieptNumber.substring(4,
					controlRecieptNumber.length());

			int count = Integer.parseInt(crnNumberPart.substring(0, 1));
			String number = crnNumberPart.substring(1, crnNumberPart
					.length());
			if (count < countPerUCR) {
				count++;
				airlineID = new StringBuilder(controlRecieptNumber
						.substring(0, 4)).toString();
			} else {
				try {
					String crn = new ULDDefaultsDelegate()
							.findCRNForULDTransaction(logonAttributes
									.getCompanyCode(), fromPrtyCode);
					log.log(Log.INFO, "control ref no", controlRecieptNumber);
					if (crn != null && crn.length() > 0) {
						airlineID = new StringBuilder(crn.substring(0, 4))
								.toString();
						number = crn.substring(crn.length() - 7, crn
								.length());
						//Modified as opart of ICRD-2726 by A-3767 on 29Jun11
						count = 0;
					}
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			}
			uLDTransactionDetailsVO.setControlReceiptNumberPrefix(airlineID
					+ count);
			uLDTransactionDetailsVO.setCrnToDisplay(number);
		}
		//added by a-3045 for bug ULD558 starts
		uLDTransactionDetailsVO.setUldConditionCode("SER");
		//added by a-3045 for bug ULD558 ends
		uLDTransactionDetailsVO.setUldNature("GEN");
		if (transactionVO != null) {
			if (transactionVO.getUldTransactionDetailsVOs() != null
					&& transactionVO.getUldTransactionDetailsVOs().size() > 0) {
				transactionVO.getUldTransactionDetailsVOs().add(
						uLDTransactionDetailsVO);
			} else {
				Collection<ULDTransactionDetailsVO> uldTxnVos = new ArrayList<ULDTransactionDetailsVO>();
				uldTxnVos.add(uLDTransactionDetailsVO);
				transactionVO.setUldTransactionDetailsVOs(uldTxnVos);
			}
			loanBorrowULDSession.setTransactionVO(transactionVO);
		} else {
			TransactionVO vo = new TransactionVO();
			Collection<ULDTransactionDetailsVO> uldTxnVos = new ArrayList<ULDTransactionDetailsVO>();
			uldTxnVos.add(uLDTransactionDetailsVO);
			vo.setUldTransactionDetailsVOs(uldTxnVos);
			loanBorrowULDSession.setTransactionVO(vo);
		}
		invocationContext.target = CRN_SUCCESS;
	}

	/**
	 * @param form
	 * @return crnNum
	 */
	private String getLastCRNNumber(MaintainULDTransactionForm form) {
		if (form.getCrn() != null && form.getCrn().length > 0) {
			int len = form.getCrn().length;
			return new StringBuilder(form.getCrnPrefix()[len - 1]).append(form.getCrn()[len - 1]).toString();
		} else {
			return null;
		}

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
		if ( txndat.length()>0){
			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			transactionVO.setStrTransactionDate(strTxnDate);
			transactionVO.setTransactionTime(txnTime);
			}else {
				transactionVO.setStrTransactionDate(BLANK);
				transactionVO.setTransactionTime(BLANK);
			}
		transactionVO.setTransactionRemark(form.getTransactionRemarks());
		if(form.getPartyType() != null
				&& form.getPartyType().trim().length() > 0){
			transactionVO.setPartyType(form.getPartyType().trim());
		}
		if(form.getFromPartyCode() != null
				&& form.getFromPartyCode().trim().length() > 0){
			transactionVO.setFromPartyCode(form.getFromPartyCode().trim().toUpperCase());
		}
		if(form.getToPartyCode() != null
				&& form.getToPartyCode().trim().length() > 0){
			transactionVO.setToPartyCode(form.getToPartyCode().trim().toUpperCase());
		}
		if(form.getFromPartyName() != null
				&& form.getFromPartyName().trim().length() > 0){
			transactionVO.setFromPartyName(form.getFromPartyName().trim());
		}
		if(form.getToPartyName() != null
				&& form.getToPartyName().trim().length() > 0){
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
			vo.setUldType(BLANK);
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
			//added by a-3045 for bug ULD558 starts
			vo.setUldConditionCode(form.getUldCondition()[i]);
			//added by a-3045 for bug ULD558 ends
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
			transactionVO.setOriginatorName(form.getOriginatorName().toUpperCase());
			//Added by A-4072 for CR ICRD-192300 end
			/*if (form.getDamageCheck()[i] != null
					&& form.getDamageCheck()[i].trim().length() > 0
					&& ULDTransactionDetailsVO.FLAG_YES.equals(form
							.getDamageCheck()[i])) {
				vo.setDamageStatus(ULDTransactionDetailsVO.FLAG_YES);
			} else {
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
	 * @return agreementNumbers
	 */
	private Collection<String> searchForAgreement(TransactionVO transactionVO) {
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		Collection<String> agreementNumbers = new ArrayList<String>();
		try {
			agreementNumbers =  uldDefaultsDelegate.findAgreementNumberForTransaction(transactionVO.getUldTransactionDetailsVOs());
			 }catch (BusinessDelegateException businessDelegateException) {
			  businessDelegateException.getMessage();
	          errors = handleDelegateException(businessDelegateException);
		     }
		return agreementNumbers;

	}
	/**
	 * @param session
	 * @param form
	 * @return
	 */
	private void generateConditionCodes(MaintainULDTransactionForm form,
			LoanBorrowULDSession session) {
		log.log(Log.INFO, "INSIDE generateConditionCodes !!!!!!!!");
		Collection<ULDServiceabilityVO> uldServiceabilityVOs = new ArrayList<ULDServiceabilityVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> exceptions = new ArrayList<ErrorVO>();
		String partyType = form.getPartyType().toUpperCase();
		log.log(Log.FINE, "partyType--------->>>>>>>>>>>>>>", partyType);
		try {
			uldServiceabilityVOs = delegate.listULDServiceability(
					logonAttributes.getCompanyCode(), partyType);
			log
					.log(
							Log.FINE,
							"uldServiceabilityVOs getting from delegate--------->>>>>>>>>>>>>>",
							uldServiceabilityVOs);
			session.setULDServiceabilityVOs(uldServiceabilityVOs);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exceptions = handleDelegateException(e);
		}

	}
	
	/**
	 * Added by A-4072 for getting number of ULDs to to print in UCR report.
	 * and this count is used for generating crn number.
	 * Pick ULD count configured in airline parameter or else it should be 3 by default
	 * @param cmpCode
	 * @param ownAirlineIdr
	 * @return
	 */
	private int getUldCountperUCR(String cmpCode,int ownAirlineIdr){
		int uldCountPerUCR =3;//As per IATA default is 3
		try{
			Collection<String> parameterCodes = new ArrayList<String>();
			Map<String,String> airlineParameterMap = null;
			parameterCodes.add(ULD_COUNT_PER_UCR);
			airlineParameterMap = new AirlineDelegate().findAirlineParametersByCode(cmpCode, ownAirlineIdr, parameterCodes);
			if(airlineParameterMap != null && !airlineParameterMap.isEmpty()){
				String value = airlineParameterMap.get(ULD_COUNT_PER_UCR);
				if(value != null && value.trim().length() > 0){
					uldCountPerUCR = Integer.parseInt(value);
				}
			}
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE, "*****in the exception in findAirlineParameter"+businessDelegateException.getMessage());
		}		
		return uldCountPerUCR;
	}

}
