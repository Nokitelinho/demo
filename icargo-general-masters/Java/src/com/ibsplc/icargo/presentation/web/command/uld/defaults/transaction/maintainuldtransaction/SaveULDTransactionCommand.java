/*
 * SaveULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.WARNING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import org.apache.commons.lang.StringUtils;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.AccessoryTransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.lock.ULDLockVO;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class SaveULDTransactionCommand extends BaseCommand {

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
	private static final String SCREEN_ID_ONE = "uld.defaults.loanborrowuld";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID_TWO = "uld.defaults.misc.recorduldmovement";

	/**
	 * target String if success
	 */
	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVEBORROW_SUCCESS = "saveborrow_success";

	private static final String PRINT_LOAN_UCR = "print_loan_ucr";
	
	/**
	 * target String if success
	 */
	private static final String SAVE_ERROR = "save_error";

	private static final String AIRLINE = "A";

	private static final String AGENT = "G";

	private static final String ULD_TRANSACTION_REF_NUMBER = "uld.transaction.refnumber";

	// added for scm reconcillation

	private static final String PAGE_URL = "fromScmUldReconcile";
	
	
	
	private static final String BLANK = "";

	private static final String ULD_SEPARATOR=",";
	private static final String TRANSACTION_TYPE_LOAN="L";
	//private static final String TRANSACTION_TYPE_BARROW="B";
	private static final String SCREEN_STATUS="formloanborrow";
	private static final String SHOW_WARNING_MSG="Y";
	private static final String YES="Y";
	private static final String NO="N";
	private static final String ULD_STK_UPDATE_FOR_OWN = "uld.defaults.cancreateuld";
	private static final String ULD_STK_UPDATE_FOR_OAL = "uld.defaults.autouldstockupdateforOAL";
	private static final String ULD_COUNT_PER_UCR = "shared.airline.uldCountPerUCR";
	private final static String ULD_TRANSACTION_CREATION_BASED_ON = "uld.default.transactioncreationbasedon";
	private static final  String ULD_OWNER_AIRLINE_CODE = "OWNAIRCOD";
	private static final String ULD_OWNER_CODE = "ULDCARCOD";
	private static final String INVALID_LEASE_END_DATE = "uld.defaults.loanborrow.invalidleaseenddate";
	
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainULDTransactionForm maintainULDTransactionForm =
			(MaintainULDTransactionForm) invocationContext.screenModel;
		maintainULDTransactionForm.setSaveStatus("");
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID_ONE);
		RecordUldMovementSession recordUldMovementSession =
			(RecordUldMovementSession) getScreenSession(
				MODULE_NAME, SCREEN_ID_TWO);
		log.log(Log.FINE, "\n\n ****INSIDE SAVE******", loanBorrowULDSession.getPageURL());
		if (("fromDamageReport").equals(loanBorrowULDSession.getPageURL())
				|| ("fromulderrorlogforborrowDamage")
						.equals(loanBorrowULDSession.getPageURL())
				|| ("fromulderrorlogforloanDamage").equals(loanBorrowULDSession
						.getPageURL())
				|| ("fromScmUldReconcileDamage").equals(loanBorrowULDSession
						.getPageURL())
				||("fromScmReconcileBorrowDamage").equals(loanBorrowULDSession.
						getPageURL())
				|| ("LoanBorrow").equals(loanBorrowULDSession.getPageURL())) {
			maintainULDTransactionForm.setPageurl("");
			if (("fromulderrorlogforborrowDamage").equals(loanBorrowULDSession
					.getPageURL())) {
				maintainULDTransactionForm
						.setPageurl("fromulderrorlogforborrow");
			}
			if (("fromulderrorlogforloanDamage").equals(loanBorrowULDSession
					.getPageURL())) {
				maintainULDTransactionForm.setPageurl("fromulderrorlogforloan");
			}

			if (("fromScmUldReconcileDamage").equals(loanBorrowULDSession
					.getPageURL())) {
				//FOR ERROR RECONCILATION WHEN RETURN FROM DAMAGE OR DUMMY MOVEMENT
				Collection<ULDSCMReconcileDetailsVO> reconcileVOs =
					new ArrayList<ULDSCMReconcileDetailsVO>();
				reconcileVOs.add(loanBorrowULDSession
						.getULDSCMReconcileDetailsVO());
				try {
					new ULDDefaultsDelegate()
							.removeErrorCodeForULDsInSCM(reconcileVOs);
				} catch (BusinessDelegateException ex) {
					Collection<ErrorVO> errors = handleDelegateException(ex);
		        	if(errors != null && errors.size() > 0){
		        		invocationContext.addAllError(errors);
		        		invocationContext.target =SAVE_ERROR;
		        		return;
		        	}
				}

				maintainULDTransactionForm.setPageurl(PAGE_URL);
			}

			if (("fromScmReconcileBorrowDamage").equals(loanBorrowULDSession
					.getPageURL())) {
				Collection<ULDSCMReconcileDetailsVO> reconcileVOs =
					new ArrayList<ULDSCMReconcileDetailsVO>();
				reconcileVOs.add(loanBorrowULDSession
						.getULDSCMReconcileDetailsVO());
				try {
					new ULDDefaultsDelegate()
							.removeErrorCodeForULDsInSCM(reconcileVOs);
				} catch (BusinessDelegateException ex) {
					Collection<ErrorVO> errors = handleDelegateException(ex);
		        	if(errors != null && errors.size() > 0){
		        		invocationContext.addAllError(errors);
		        		invocationContext.target =SAVE_ERROR;
		        		return;
		        	}
				}


				maintainULDTransactionForm.setPageurl("fromScmReconcileBorrow");
			}
			loanBorrowULDSession.setPageURL("");
			maintainULDTransactionForm.setSaveStatus("saved");
			maintainULDTransactionForm.setTxnTypeDisable("");
			log.log(Log.FINE, "\n\n ****FROM DAMAGE REPORT******");
			log.log(Log.FINE,
					"\n\n ***loanBorrowULDSession.getTransactionVO()******",
					loanBorrowULDSession.getTransactionVO());
			maintainULDTransactionForm
					.setScreenStatusFlag(
							ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			/* Commented by A-3415 for ICRD-114538
			 * if (loanBorrowULDSession.getTransactionVO() != null
					&& ("B").equals(loanBorrowULDSession.getTransactionVO()
							.getTransactionType())) {

				TransactionVO newTransactionVO = new TransactionVO();
				newTransactionVO.setTransactionType("L");
				newTransactionVO.setTransactionNature("T");
				newTransactionVO.setStrTransactionDate("");
				newTransactionVO.setTransactionTime("");
				loanBorrowULDSession.setTransactionVO(newTransactionVO);
				maintainULDTransactionForm.setSaveStatus("borrowsaved");

				invocationContext.target = SAVEBORROW_SUCCESS;
				return;
			}*/
			/* added by Nisha for Client UCR printing starts*/ 
			if(maintainULDTransactionForm.getPrintUCR()!=null ){
				 if("Y".equals(maintainULDTransactionForm.getPrintUCR())){
						loanBorrowULDSession.setLoanUcrPrint(maintainULDTransactionForm.getPrintUCR());
						//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
						maintainULDTransactionForm.setSaveStatus("savedandprintucr");
						ErrorVO error = populateSuccessMessage(maintainULDTransactionForm);
						error.setErrorDisplayType(ErrorDisplayType.INFO);
						invocationContext.addError(error);
						invocationContext.target = SAVE_SUCCESS;
				 }else{
					 invocationContext.target = SAVE_SUCCESS;
				 }				
			}else{
				invocationContext.target = SAVE_SUCCESS; 
			}
			/* added by Nisha for Client UCR printing ends*/ 
			//invocationContext.target = SAVE_SUCCESS;
			return;
		}
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		log.log(Log.FINE, "transactionVO get set go---->>>>>>>", transactionVO);
		String uldDmg = "";
		int flag = 0;
		
		
		
		
		String cmpCode = logonAttributes.getCompanyCode();
		updateSession(transactionVO, maintainULDTransactionForm,errors);
		updateAccSession(transactionVO, maintainULDTransactionForm);
		//Added by A-4072  for CR ICRD-192300 Starts
		int uldCountPerUCR = getUldCountperUCR(logonAttributes.getCompanyCode(),
				logonAttributes.getOwnAirlineIdentifier())-1;
		//Added by A-4072  for CR ICRD-192300 end
//		 Validate ULD Number Format
		Collection<String> uLDNumbers = new ArrayList<String>();
		Collection<String> invalidUlds = null;
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
		
    	
		invalidUlds = validateUldNumberFormat(cmpCode, uLDNumbers,errors);
		if(errors != null && errors.size() > 0){
    		invocationContext.addAllError(errors);
    		invocationContext.target =SAVE_ERROR;
    		return;
    	}
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
		//added by a-3045 for bug ULD549 starts
		if(maintainULDTransactionForm.getTransactionStation() != null 
				&& maintainULDTransactionForm.getTransactionStation().trim().length()>0){
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				new AreaDelegate().validateAirportCode(cmpCode, maintainULDTransactionForm.getTransactionStation());
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.INFO, "validateAirportCode ---->>>>>",
						maintainULDTransactionForm.getTransactionStation());
			}
			if(error != null && error.size() > 0){
				ErrorVO err = new ErrorVO(
						"uld.defaults.loanborrowULD.msg.err.invalidtxnairport");
				errors.add(err);
			}			
		}		
		//added by a-3045 for bug ULD549 ends
		// Validate Destination Airport Codes
		Collection<String> airports = new ArrayList<String>();
		if (detailsVos != null && detailsVos.size() > 0) {
			for (ULDTransactionDetailsVO vo : detailsVos) {
				if (vo.getTransactionStationCode() != null
						&& vo.getTransactionStationCode().trim().length() > 0) {
					airports.add(vo.getTransactionStationCode());
				}
			}
		}	
		log.log(Log.INFO, "validateAirportCodes are ---->>>>>", airports);
		Collection<ErrorVO> errs = validateAirportCodes(cmpCode, airports);
		errors.addAll(errs);
		if (errors != null && errors.size() > 0) {
			log.log(Log.INFO, "validateAirportCodes are ---->>>>>");
			invocationContext.addAllError(errors);	
			maintainULDTransactionForm.setTxnTypeDisable("Y");
				invocationContext.target = SAVE_ERROR;			
				loanBorrowULDSession.setTransactionVO(transactionVO);
			return;
		}

		// Validate Duplicate ULD Number
		errs = checkDuplicateULDs(uLDNumbers);
		errors.addAll(errs);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);	
			maintainULDTransactionForm.setTxnTypeDisable("Y");
				invocationContext.target = SAVE_ERROR;			
				loanBorrowULDSession.setTransactionVO(transactionVO);
			return;
		}
		//Validating In Use ULDs
		if(uLDNumbers != null && uLDNumbers.size() > 0){
			errs = checkULDInUse(cmpCode, uLDNumbers);
			errors.addAll(errs);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);	
				maintainULDTransactionForm.setTxnTypeDisable("Y");
					invocationContext.target = SAVE_ERROR;			
					loanBorrowULDSession.setTransactionVO(transactionVO);
				return;
			}
		}		
		/*transactionVO.setCompanyCode(loanBorrowULDSession.getCompanyCode());
		transactionVO.setTransactionType(maintainULDTransactionForm
				.getTransactionType());
		transactionVO.setTransactionNature(maintainULDTransactionForm
				.getTransactionNature());
		transactionVO.setTransactionStation(maintainULDTransactionForm
				.getTransactionStation().toUpperCase());
		String txnDate = maintainULDTransactionForm.getTransactionDate();
		String strTxnDate = maintainULDTransactionForm.getTransactionDate();
		String txnTime = maintainULDTransactionForm.getTransactionTime();
		LocalDate ldte = new LocalDate(
				logonAttributes.getAirportCode(),Location.ARP,true);
		if(!txnTime.contains(":")){
			txnTime=txnTime.concat(":00");
		}
		StringBuilder txndat = new StringBuilder();
		txndat.append(txnDate).append(" ").append(txnTime).append(":00");


		log.log(Log.FINE, "\n\n\n\nDATE AND TIME" + txndat);
		if (txndat != null && !"".equals(txndat)){
			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			transactionVO.setStrTransactionDate(strTxnDate);
			transactionVO.setTransactionTime(txnTime);
			}else {
				transactionVO.setStrTransactionDate("");
				transactionVO.setTransactionTime("");
			}

		transactionVO.setTransactionRemark(maintainULDTransactionForm
				.getTransactionRemarks());
		transactionVO.setPartyType(maintainULDTransactionForm.getPartyType());
		transactionVO.setFromPartyCode(maintainULDTransactionForm
				.getFromPartyCode().toUpperCase());
		transactionVO.setToPartyCode(maintainULDTransactionForm
				.getToPartyCode().toUpperCase());
		transactionVO.setFromPartyName(maintainULDTransactionForm
				.getFromPartyName());
		transactionVO.setToPartyName(maintainULDTransactionForm
				.getToPartyName());
*/
		errors = validateForm(maintainULDTransactionForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);		
				invocationContext.target = SAVE_ERROR;			
				loanBorrowULDSession.setTransactionVO(transactionVO);
			return;
		}
		log.log(Log.FINE, "\n\n\n\n before check&&&&&&&&&&&&&&&&&&&&",
				transactionVO);
		if(transactionVO.getUldTransactionDetailsVOs() != null && transactionVO.getUldTransactionDetailsVOs().size() > 0){
			Collection<String> agreementNumbers = null;
		
			/************ Setting Return Date   for bug 102920 starts ***************************/
			String retDate = maintainULDTransactionForm.getTransactionDate();
			String retTime = maintainULDTransactionForm.getTransactionTime();
			LocalDate currentdate= new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			if(!retTime.contains(":")){
				retTime =retTime .concat(":00");
			}
			StringBuilder retdat = new StringBuilder();
			retdat.append( retDate).append(" ").append(retTime).append(":00");						
									
			/***************** Setting Return Date for bug 102920 ends  ************/
			
			errors = new ArrayList<ErrorVO>();
			agreementNumbers = searchForAgreement(transactionVO,currentdate.setDateAndTime(retdat.toString()),errors);
			
			log.log(Log.FINE, "\n\n\n\n after check");
			if(agreementNumbers.isEmpty()){
				log.log(Log.FINE, "\n\n\n\n agreementNumbers == null");
				maintainULDTransactionForm.setTxnTypeDisable("Y");
				ErrorVO error = new ErrorVO(
				"uld.defaults.loanborrow.msg.err.agreementdoesnotexists");
				errors.add(error);
				invocationContext.addAllError(errors);	
				if( maintainULDTransactionForm.getPrintUCR() !=null && ("Y").equals(maintainULDTransactionForm.getPrintUCR())
						&& ("").equals(maintainULDTransactionForm.getPageurl())){
					//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
					maintainULDTransactionForm.setSaveStatus("saveandprintfailure");
					invocationContext.target = SAVE_ERROR;
				}else{
					invocationContext.target = SAVE_ERROR;
				}											
				loanBorrowULDSession.setTransactionVO(transactionVO);
				return;
			}
		}	
		//Changed by a-3045 for Bug20362 starts
		//added by a-3045 for ULD735 ends
		/**
		 * setting of transaction status "To be returned"
		 */
		transactionVO.setTransactionStatus(transactionVO.TO_BE_RETURNED);

		/**
		 * to get Airline identifier for party code
		 */
		String fromPrtyCode = maintainULDTransactionForm.getFromPartyCode().trim()
				.toUpperCase();
		String toPrtyCode = maintainULDTransactionForm.getToPartyCode().trim()
				.toUpperCase();
		AirlineValidationVO fromOwnerVO = null;
		AirlineValidationVO toOwnerVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;

		if (("L").equals(maintainULDTransactionForm.getTransactionType())) {
			if (fromPrtyCode != null && !("".equals(fromPrtyCode))) {

				try {
					fromOwnerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							maintainULDTransactionForm.getFromPartyCode()
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
				}
			}
		}
		/* Commented by A-3415 for ICRD-114538
		 * if (("B").equals(maintainULDTransactionForm.getTransactionType())) {
			if (toPrtyCode != null && !("".equals(toPrtyCode))) {

				try {
					toOwnerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							maintainULDTransactionForm.getToPartyCode()
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
				}
			}
		}*/
		//aysh
		log.log(Log.FINE, "***** ENTER ING for toOwnerVO");
		if (("R").equals(maintainULDTransactionForm.getTransactionType())) {
			if (toPrtyCode != null && !("".equals(toPrtyCode))) {

				try {
					toOwnerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							maintainULDTransactionForm.getToPartyCode()
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
				}
			}
		}
		log.log(Log.FINE, "***** toOwnerVO------->>", toOwnerVO);
		if (AIRLINE.equals(maintainULDTransactionForm.getPartyType())) {

			if (("L").equals(maintainULDTransactionForm.getTransactionType()) || ("R").equals(maintainULDTransactionForm.getTransactionType()) ) {
				if (toPrtyCode != null && !("".equals(toPrtyCode))) {

					try {
						toOwnerVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(),
								maintainULDTransactionForm.getToPartyCode()
										.toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {

						errors = handleDelegateException(businessDelegateException);
					}
				}
			}
			/* Commented by A-3415 for ICRD-114538
			 * if (("B").equals(maintainULDTransactionForm.getTransactionType())) {
				if (fromPrtyCode != null && !("".equals(fromPrtyCode))) {

					try {
						fromOwnerVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(),
								maintainULDTransactionForm.getFromPartyCode()
										.toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {

						errors = handleDelegateException(businessDelegateException);
					}
				}
			}*/
			
			
			if(errors == null || errors.size() == 0) {
				if(fromPrtyCode.trim().equals(toPrtyCode.trim())) {
					log.log(Log.FINE, "***** fromPrtyCode topartyCode  exception");
					ErrorVO errorVO = new ErrorVO("uld.defaults.loanborrowULD.frompartysameastoparty");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
			}
		}
		if (AGENT.equals(maintainULDTransactionForm.getPartyType())) {
			/* Commented by A-3415 for ICRD-114538
			 * if (("B").equals(maintainULDTransactionForm.getTransactionType())) {
				if (fromPrtyCode != null && !("".equals(fromPrtyCode))) {
					Collection<ErrorVO> error = new ArrayList<ErrorVO>();
					try {
						agentVO = agentDelegate.findAgentDetails(
								logonAttributes.getCompanyCode(),
								maintainULDTransactionForm.getFromPartyCode()
										.toUpperCase());
					} catch (BusinessDelegateException exception) {
						log.log(Log.FINE, "*****in the exception");
						exception.getMessage();
						error = handleDelegateException(exception);
					}
				}
			}*/
			if (("L").equals(maintainULDTransactionForm.getTransactionType())) {
				if (toPrtyCode != null && !("".equals(toPrtyCode))) {
					Collection<ErrorVO> error = new ArrayList<ErrorVO>();
					try {
						agentVO = agentDelegate.findAgentDetails(
								logonAttributes.getCompanyCode(),
								maintainULDTransactionForm.getToPartyCode().trim()
										.toUpperCase());
					} catch (BusinessDelegateException exception) {
						log.log(Log.FINE, "*****in the exception");
						exception.getMessage();
						error = handleDelegateException(exception);
					}
				}
			}
			if (("R").equals(maintainULDTransactionForm.getTransactionType())) {
				if (fromPrtyCode != null && !("".equals(fromPrtyCode))) {
					Collection<ErrorVO> error = new ArrayList<ErrorVO>();
					try {
						agentVO = agentDelegate.findAgentDetails(
								logonAttributes.getCompanyCode(),fromPrtyCode
										.toUpperCase());
					} catch (BusinessDelegateException exception) {
						log.log(Log.FINE, "*****in the exception");
						exception.getMessage();
						error = handleDelegateException(exception);
					}
				}
			}
			if (agentVO == null) {
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.loanborrowULD.invalidagentcode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}

		}

		if ("L".equalsIgnoreCase(transactionVO.getTransactionType())|| "R".equalsIgnoreCase(transactionVO.getTransactionType())) {
			if (toOwnerVO != null) {
				transactionVO.setOperationalAirlineIdentifier(toOwnerVO
						.getAirlineIdentifier());
			}
		}
		/* Commented by A-3415 for ICRD-114538
		 * if ("B".equalsIgnoreCase(transactionVO.getTransactionType())) {
			if (fromOwnerVO != null) {
				transactionVO.setCurrOwnerCode(fromOwnerVO
						.getAirlineIdentifier());
			}
		}*/
		if ("R".equalsIgnoreCase(transactionVO.getTransactionType())) {
			if (toOwnerVO != null) {
				transactionVO.setCurrOwnerCode(toOwnerVO
						.getAirlineIdentifier());
			}
		}

		/**
		 * to get Airline identifier for Login station
		 */
		AirlineValidationVO loanCurOwnerVO = null;
		AirlineValidationVO borrowCurOwnerVO = null;
		if (("L").equals(maintainULDTransactionForm.getTransactionType())) {
			try {
				loanCurOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						maintainULDTransactionForm.getFromPartyCode().toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		/* Commented by A-3415 for ICRD-114538
		 * if (("B").equals(maintainULDTransactionForm.getTransactionType())) {
			try {
				borrowCurOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						maintainULDTransactionForm.getToPartyCode().toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}*/
		//aysh
		if ((("R").equals(maintainULDTransactionForm.getTransactionType())) &&
				(AGENT.equals(maintainULDTransactionForm.getPartyType()))) {
			try {
				borrowCurOwnerVO = new AirlineDelegate().validateAlphaCode(
						logonAttributes.getCompanyCode(),
						maintainULDTransactionForm.getToPartyCode().toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		log.log(Log.FINE, "borrowCurOwnerVO------%%--->", borrowCurOwnerVO);
		//aysh
		if ("L".equalsIgnoreCase(transactionVO.getTransactionType())) {
			if (loanCurOwnerVO != null) {
				transactionVO.setCurrOwnerCode(loanCurOwnerVO
						.getAirlineIdentifier());
			}
		}
		/* Commented by A-3415 for ICRD-114538
		 * if ("B".equalsIgnoreCase(transactionVO.getTransactionType())) {
			if (borrowCurOwnerVO != null) {
				transactionVO.setOperationalAirlineIdentifier(borrowCurOwnerVO
						.getAirlineIdentifier());
			}
		}*/
		//aysh
		if ((("R").equals(maintainULDTransactionForm.getTransactionType())) &&
				(AGENT.equals(maintainULDTransactionForm.getPartyType()))) {
			log.log(Log.FINE, "borrowCurOwnerVO------&&&&--->>",
					borrowCurOwnerVO);
			if (borrowCurOwnerVO != null) {
				transactionVO.setOperationalAirlineIdentifier(borrowCurOwnerVO
						.getAirlineIdentifier());
			}
		}
		log.log(Log.FINE, "transactionVO--------->>", transactionVO);
		Collection<AccessoryTransactionVO> accTransactionVOs = transactionVO
				.getAccessoryTransactionVOs();
		Collection<AccessoryTransactionVO> accessoryTransactionVOs =
			new ArrayList<AccessoryTransactionVO>();
		Collection<ULDTransactionDetailsVO> uldDetailsVOs = transactionVO
				.getUldTransactionDetailsVOs();
		log.log(Log.FINE, "uldDetailsVOs", uldDetailsVOs);
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs =
			new ArrayList<ULDTransactionDetailsVO>();

		/*
		 * To Update accTransactionVOs from form
		 */
		try {
			if (accTransactionVOs != null && accTransactionVOs.size() > 0) {
				for (AccessoryTransactionVO accessoryVO : accTransactionVOs) {
					AccessoryTransactionVO newAccessoryVO = new AccessoryTransactionVO();
					BeanHelper.copyProperties(newAccessoryVO, accessoryVO);
					newAccessoryVO.setCompanyCode(transactionVO
							.getCompanyCode());
					newAccessoryVO.setTransactionType(transactionVO
							.getTransactionType());
					newAccessoryVO.setTransactionNature(transactionVO
							.getTransactionNature());
					newAccessoryVO.setTransactionStationCode(transactionVO
							.getTransactionStation());
					newAccessoryVO.setTransactionDate(transactionVO
							.getTransactionDate());
					newAccessoryVO.setTransactionRemark(transactionVO
							.getTransactionRemark());
					newAccessoryVO.setPartyType(transactionVO.getPartyType());
					newAccessoryVO.setFromPartyCode(transactionVO
							.getFromPartyCode());
					newAccessoryVO.setFromPartyName(transactionVO
							.getFromPartyName());
					newAccessoryVO.setToPartyCode(transactionVO
							.getToPartyCode());
					newAccessoryVO.setToPartyName(transactionVO
							.getToPartyName());
					/*
					 * if("B".equalsIgnoreCase(transactionVO.getTransactionType())) {
					 * newAccessoryVO.setCurrOwnerCode
					 * (transactionVO.getOperationalAirlineIdentifier());
					 * newAccessoryVO.setOperationalAirlineIdentifier
					 * (transactionVO.getCurrOwnerCode());
					 * }else {
					 * newAccessoryVO.setCurrOwnerCode
					 * (transactionVO.getCurrOwnerCode());
					 * newAccessoryVO.setOperationalAirlineIdentifier
					 * (transactionVO.getOperationalAirlineIdentifier()); }
					 */

					newAccessoryVO.setCurrOwnerCode(transactionVO
							.getCurrOwnerCode());
					newAccessoryVO
							.setOperationalAirlineIdentifier(transactionVO
									.getOperationalAirlineIdentifier());

					LocalDate ldate = new LocalDate(logonAttributes
							.getAirportCode(), Location.ARP, true);
					newAccessoryVO.setLastUpdateTime(ldate);
					newAccessoryVO.setLastUpdateUser(logonAttributes
							.getUserId());

					accessoryTransactionVOs.add(newAccessoryVO);

				}
				log.log(Log.FINE, "accessoryTransactionVOs",
						accessoryTransactionVOs);
			}

		//	log.log(Log.FINE,"uldDetailsVOs.size()"+ uldDetailsVOs.size());
			if (uldDetailsVOs != null && uldDetailsVOs.size() > 0) {
				for (ULDTransactionDetailsVO uldVO : uldDetailsVOs) {
					ULDTransactionDetailsVO newUldVO =
						new ULDTransactionDetailsVO();
					BeanHelper.copyProperties(newUldVO, uldVO);
					log.log(Log.FINE, "uldVO", uldVO);
					log.log(Log.FINE, "newUldVO", newUldVO);
					newUldVO.setCompanyCode(transactionVO.getCompanyCode());
					newUldVO.setTransactionType(transactionVO
							.getTransactionType());
					newUldVO.setTransactionNature(transactionVO
							.getTransactionNature());
					//added by a-3045 for CRQ_QF1016_Nishanth_19Jun08 starts
					if(uldVO.getTxStationCode() == null || ("".equals(uldVO.getTxStationCode()))){
						newUldVO.setTxStationCode(transactionVO
								.getTransactionStation());
					}else{
						newUldVO.setTxStationCode(uldVO
								.getTxStationCode());					
					}
					//added by a-3045 for CRQ_QF1016_Nishanth_19Jun08 ends
					/************ Setting Returnm Date   for bug 102920 starts ***************************/
					if("R".equals(transactionVO.getTransactionType())){						
						String retDate = maintainULDTransactionForm.getTransactionDate();
						String retTime = maintainULDTransactionForm.getTransactionTime();
						LocalDate currentdate= new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						if(!retTime.contains(":")){
							retTime =retTime .concat(":00");
						}
						StringBuilder retdat = new StringBuilder();
						retdat.append( retDate).append(" ").append(retTime).append(":00");						
						newUldVO.setReturnDate(currentdate.setDateAndTime(retdat .toString()));					
						//Added by A-7131 for ICRD-154607
						newUldVO.setTransactionDate(transactionVO.getTransactionDate());
						}else{
					newUldVO.setTransactionDate(transactionVO
							.getTransactionDate());
						}
					/************ Setting Returnm Date   for bug 102920 ends ***************************/
					newUldVO.setTransactionStatus(transactionVO
							.getTransactionStatus());
					newUldVO.setTransactionRemark(transactionVO
							.getTransactionRemark());
					newUldVO.setAwbNumber(transactionVO.getAwbNumber());
					newUldVO.setOriginatorName(transactionVO.getOriginatorName());
					newUldVO.setEmptyStatus(transactionVO.getEmptyStatus());
					//newUldVO.setDamageStatus(uldVO.getDamageStatus());
					//Added by Preet
					if(uldVO.getUldConditionCode()!=null && uldVO.getUldConditionCode().length()>0){
						if("DAM".equals(uldVO.getUldConditionCode())){
							newUldVO.setDamageStatus("Y");
						}					
					}
					else{
						log.log(Log.FINE,"DamageStatus++++++++++++++++++++++++++++");
						newUldVO.setDamageStatus("N");
					}
					// ends
					if (("Y").equals(newUldVO.getDamageStatus())) {
						if (flag == 0) {
							uldDmg = newUldVO.getUldNumber();
							flag = 1;
						} else {
							uldDmg = new StringBuilder(uldDmg).append(",")
									.append(newUldVO.getUldNumber()).toString();
						}
					}
					newUldVO.setUldType(uldVO.getUldNumber().substring(0, 3));
					newUldVO.setFromPartyCode(transactionVO.getFromPartyCode());
					newUldVO.setToPartyCode(transactionVO.getToPartyCode());
					newUldVO.setPartyType(transactionVO.getPartyType());
					newUldVO.setFromPartyName(transactionVO.getFromPartyName());
					newUldVO.setToPartyName(transactionVO.getToPartyName());
					log.log(Log.INFO, "fromOwnerVO+++ ---", fromOwnerVO);
					log.log(Log.INFO, "toOwnerVO+ ---", toOwnerVO);
					if (fromOwnerVO != null) {
						newUldVO.setFromPartyIdentifier(fromOwnerVO
								.getAirlineIdentifier());
					}
					if (toOwnerVO != null) {
						newUldVO.setToPartyIdentifier(toOwnerVO
								.getAirlineIdentifier());
					}
					
					newUldVO.setCurrOwnerCode(transactionVO.getCurrOwnerCode());
					if(transactionVO
							.getOperationalAirlineIdentifier() != 0){
						newUldVO.setOperationalAirlineIdentifier(transactionVO
								.getOperationalAirlineIdentifier());
					}else{
						newUldVO.setOperationalAirlineIdentifier(transactionVO
								.getCurrOwnerCode());
					}
					log.log(log.FINE,"latest changeeee >$$$>>-");
					
                    
					//LocalDate ldate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
					//newUldVO.setLastUpdateTime(ldate);
					//newUldVO.setLastUpdateUser(logonAttributes.getUserId());
					
					log.log(Log.INFO,
							"Last Updated Time in SaveCommand --iss >$$$>>-",
							loanBorrowULDSession.getUldLastUpdateTime());
					if(loanBorrowULDSession.getUldLastUpdateTime() != null){
						newUldVO.setLastUpdateTime(loanBorrowULDSession.getUldLastUpdateTime());
					}else{
						LocalDate ldate = new LocalDate(uldVO.getTransactionStationCode(), Location.ARP, true);
						newUldVO.setLastUpdateTime(ldate);
					}
					
					
					if ("L".equalsIgnoreCase(transactionVO.getTransactionType())) {
						if (("fromulderrorlogforloan").equals(maintainULDTransactionForm.getPageurl())) {
							newUldVO.setLastUpdateUser(logonAttributes.getUserId());
						}else if(PAGE_URL.equals(maintainULDTransactionForm.getPageurl())){
							newUldVO.setLastUpdateUser(logonAttributes.getUserId());							
						}else{
							log.log(Log.INFO,
									"Last Updated user in SaveCommand+++ ---",
									logonAttributes.getUserId());
							newUldVO.setLastUpdateUser(logonAttributes.getUserId());
						}
					}/*else if ("B".equalsIgnoreCase(transactionVO.getTransactionType())) {
						newUldVO.setLastUpdateUser(logonAttributes.getUserId());
					}*/
					
					log.log(Log.INFO, "newUldVO+++ --->>>", newUldVO);
					uldTxnDetailsVOs.add(newUldVO);

				}
			}

		} catch (SystemException e) {
			e.getMessage();
		}
		log.log(Log.FINE, "uldDmg", uldDmg);
		maintainULDTransactionForm.setDamageULD(uldDmg);
		transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);
		transactionVO.setAccessoryTransactionVOs(accessoryTransactionVOs);
		loanBorrowULDSession.setTransactionVO(transactionVO);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			/*maintainULDTransactionForm
					.setScreenStatusFlag(
							ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);*/
			// maintainULDTransactionForm.setTxnTypeDisable("");
			if (maintainULDTransactionForm.getPageurl() != null) {
				if (("fromulderrorlogforborrow")
						.equals(maintainULDTransactionForm.getPageurl())) {
					loanBorrowULDSession.setPageURL("fromulderrorlogforborrow");
				} else if (("fromulderrorlogforloan")
						.equals(maintainULDTransactionForm.getPageurl())) {
					loanBorrowULDSession.setPageURL("fromulderrorlogforloan");
				} else if (PAGE_URL.equals(maintainULDTransactionForm
						.getPageurl())) {
					loanBorrowULDSession.setPageURL(PAGE_URL);
				}else if("fromScmReconcileBorrow".equals(maintainULDTransactionForm.getPageurl())){
					loanBorrowULDSession.setPageURL("fromScmReconcileBorrow");
				}
			}
			log.log(Log.FINE, "\n\n\n\n ****SAVE 1 with******");
//			 added by nisha starts for UCR 
			if( maintainULDTransactionForm.getPrintUCR() !=null && ("Y").equals(maintainULDTransactionForm.getPrintUCR())
					&& ("").equals(maintainULDTransactionForm.getPageurl())){
				//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
				maintainULDTransactionForm.setSaveStatus("saveandprintfailure");
				invocationContext.target = SAVE_ERROR;
			}else{
			invocationContext.target = SAVE_ERROR;
			}
			return;
		}

		transactionVO.setOperationalFlag("I");

		Collection<ErrorVO> errorsaftersave = new ArrayList<ErrorVO>();

		log.log(Log.FINE, "\n\n\n\n ****transactionVO before save******",
				transactionVO);
		/*
		 * Construct lock vos for implicit locking
		 */
		Collection<LockVO> locks = prepareLocksForSave(transactionVO);
		log.log(Log.FINE, "LockVO for implicit check", locks);
		/* Added by nisha UCR printing */
		if( maintainULDTransactionForm.getPrintUCR() !=null && ("Y").equals(maintainULDTransactionForm.getPrintUCR())){
			transactionVO.setToBePrinted(true);
		}
		else{
			transactionVO.setToBePrinted(false);
		}
		/* Addition  by nisha for UCR printing ends*/
		/* Added by A-2412 on 18 th Oct for Editable CRN CR*/
		ArrayList<ULDTransactionDetailsVO> uldTxnVOs=new ArrayList<ULDTransactionDetailsVO>();
		uldTxnVOs=(ArrayList<ULDTransactionDetailsVO>)transactionVO.getUldTransactionDetailsVOs();
		log.log(Log.FINE, "uldTxnVOs------------", uldTxnVOs);
		if(uldTxnVOs !=null && uldTxnVOs.size()>0){
			String crnFormatValidator="";
			for(ULDTransactionDetailsVO vo:uldTxnVOs){
				if(vo.getControlReceiptNumber()==null || vo.getControlReceiptNumber().length()==0){
					ErrorVO errorVO=new ErrorVO("uld.defaults.transaction.modify.crn.mandatory");
					errorVO.setErrorDisplayType(ERROR);
					maintainULDTransactionForm.setBorrowPopupClose(FLAG_NO);
					maintainULDTransactionForm.setTxnTypeDisable("Y");
//					 added by nisha starts for UCR 
					if( maintainULDTransactionForm.getPrintUCR() !=null && ("Y").equals(maintainULDTransactionForm.getPrintUCR())
							&& ("").equals(maintainULDTransactionForm.getPageurl())){
						//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
						maintainULDTransactionForm.setSaveStatus("saveandprintfailure");
						invocationContext.target = SAVE_ERROR;
					}else{
					invocationContext.target = SAVE_ERROR;
					}
					
					invocationContext.addError(errorVO);
					return;
				}else{
					String[] splitCRN = vo.getControlReceiptNumber().split("-");
					if ((vo.getOperationalFlag().equals(
							ULDTransactionDetailsVO.OPERATION_FLAG_INSERT) || vo
							.getOperationalFlag()
							.equals(ULDTransactionDetailsVO.OPERATION_FLAG_UPDATE))
							&& splitCRN[1] != null && splitCRN[1].length() > 0) {
						int crn = Integer.parseInt(splitCRN[1].substring(0,1));
						if((splitCRN[1].length()!=8) || (crn>uldCountPerUCR)){
							crnFormatValidator="invalidCRN";
							break;
						} else {
							crnFormatValidator="crnIsValid";
						}
					}
				}
			}
			if(("invalidCRN").equals(crnFormatValidator)){
				ErrorVO error = new ErrorVO("uld.defaults.loanBorrowULD.msg.err.incorrectCRNformat");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				maintainULDTransactionForm.setTxnTypeDisable("Y");
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_ERROR;
				return;
			}
		}
		boolean dupCrnFlag=false;
		if(uldTxnVOs !=null && uldTxnVOs.size()>0){		
			int uldsiz=uldTxnVOs.size();
			for(int i=0;i<uldsiz;i++){
				String firstCRN=uldTxnVOs.get(i).getControlReceiptNumber();
				log.log(Log.FINE, "firstCRN------------", firstCRN);
				int uldtxnsize=uldTxnVOs.size();
				for(int j=i+1;j<uldtxnsize;j++){
					String secondCRN=uldTxnVOs.get(j).getControlReceiptNumber();
					log.log(Log.FINE, "secondCRN------------", secondCRN);
					if(firstCRN.equals(secondCRN)){
						log.log(Log.FINE, "firstCRN and secondCRN are equal");
						dupCrnFlag=true;
						break;
					}
				}
				
			}
		
		log.log(Log.FINE, "dupCrnFlag------------", dupCrnFlag);
		if(dupCrnFlag){
			ErrorVO error = new ErrorVO("uld.defaults.loanBorrowULD.msg.err.duplicateCRN");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			maintainULDTransactionForm.setTxnTypeDisable("Y");
			errors.add(error);
			invocationContext.addAllError(errors);
//			 added by nisha starts for UCR 
			if( maintainULDTransactionForm.getPrintUCR() !=null && ("Y").equals(maintainULDTransactionForm.getPrintUCR())
					&& ("").equals(maintainULDTransactionForm.getPageurl())){
				//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
				maintainULDTransactionForm.setSaveStatus("saveandprintfailure");
				invocationContext.target = SAVE_ERROR;
			}else{
			invocationContext.target = SAVE_ERROR;
			}
			return;
		}
		Collection<String> crnNums=null;
		try {
			String companyCode=logonAttributes.getCompanyCode();
			crnNums=uldDefaultsDelegate.checkForDuplicateCRN(companyCode,transactionVO);
			log.log(Log.FINE, "crnNums returned------------", crnNums);
		} catch (BusinessDelegateException ex) {
			errors = handleDelegateException(ex);
        	if(errors != null && errors.size() > 0){
        		invocationContext.addAllError(errors);
        		invocationContext.target =SAVE_ERROR;
        		return;
        	}
		}
		
		if (crnNums != null && crnNums.size() > 0) {
			StringBuffer duplicates = new StringBuffer("");
			for (String duplicate : crnNums) {
				if (("").equals(duplicates.toString())) {
					duplicates.append(duplicate);
				} else {
					duplicates.append(" , ");
					duplicates.append(duplicate);
				}
			}
			ErrorVO error = new ErrorVO(
					"uld.defaults.loanBorrowULD.msg.err.duplicateCRNexists",
					new Object[] { duplicates.toString() });
			errors.add(error);
			maintainULDTransactionForm.setTxnTypeDisable("Y");
			invocationContext.addAllError(errors);
//			 added by nisha starts for UCR 
			if( maintainULDTransactionForm.getPrintUCR() !=null && ("Y").equals(maintainULDTransactionForm.getPrintUCR())
					&& ("").equals(maintainULDTransactionForm.getPageurl())){
				//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
				maintainULDTransactionForm.setSaveStatus("saveandprintfailure");
				invocationContext.target = SAVE_ERROR;
			}else{
			invocationContext.target = SAVE_ERROR;
			}
			return;
		}
		
		boolean similarCrnFlag = true;	
		HashSet<String> uniqueCRN = new HashSet<String>();
		
		double length = uldTxnVOs.size();
		
		log.log(Log.INFO, "size --->>", length);
		double count = 0;
		//BUG_101488_SowmyaK_11Oct10 starts 
		//For agent loan/return-loan, 9 ulds can have the similar CRN number with only the change in CRNprefix		
			
		//Changed for bug_105541 by A_4138
		for (ULDTransactionDetailsVO transactionDetailsVO : uldTxnVOs) {
			String crnNum = transactionDetailsVO.getControlReceiptNumber().substring(5,transactionDetailsVO.getControlReceiptNumber().length());
			
			
			
			log.log(Log.INFO, "crnNum is---------->>", crnNum);
			uniqueCRN.add(crnNum);
		}
		/*if(uniqueCRN.size()==1){
		if(AGENT.equals(maintainULDTransactionForm.getPartyType())){
				count = Math.ceil(length/9);
		}else{
			count = Math.ceil(length/3);
		} 
		} 
		else{*/
			count=uniqueCRN.size();
		log.log(Log.INFO, "Math.ceil(length/3 --->>", Math.ceil(length/3));
		log.log(Log.INFO, "Count --->>", count);
		double crnLen = uniqueCRN.size();
		log.log(Log.FINE, "uniqueCRN -----------", uniqueCRN);
		log.log(Log.FINE, "crnLen -----------", crnLen);
		log.log(Log.FINE, "count -----------", count);
		if(crnLen == count){
			similarCrnFlag = false;
		}
		log.log(Log.FINE, "similarCrnFlag -----------", similarCrnFlag);
		if(similarCrnFlag){
			ErrorVO error = new ErrorVO(
			"uld.defaults.loanBorrowULD.msg.err.similarCRN");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_ERROR;
			return;
		}
		
		
		 
		 
		}
//		Preet--This is to retain values in session for UCR printing starts
		Collection<String> uldNumbersForNavigation = new ArrayList<String>();
		Collection<Integer> txnNumbersForNavigation = new ArrayList<Integer>();
		Collection<TransactionFilterVO> filterVOs = new ArrayList<TransactionFilterVO>();
		if(transactionVO.getUldTransactionDetailsVOs()!=null && transactionVO.getUldTransactionDetailsVOs().size()>0){
			for(ULDTransactionDetailsVO vo:transactionVO.getUldTransactionDetailsVOs()){
				log.log(Log.FINE, "****insdie for loop ", vo.getUldNumber());
				uldNumbersForNavigation.add(vo.getUldNumber());	
				txnNumbersForNavigation.add(vo.getTransactionRefNumber());
				vo.setSource("SCREEN");
				TransactionFilterVO filterVO = new TransactionFilterVO();
				filterVO.setCompanyCode(vo.getCompanyCode());
				filterVO.setUldNumber(vo.getUldNumber());	
				filterVO.setFromPartyCode(vo.getFromPartyCode());
				filterVO.setToPartyCode(vo.getToPartyCode());
				filterVOs.add(filterVO);
			}
			log.log(Log.FINE, "****uldNumbersForNavigation-- ",
					uldNumbersForNavigation);
			loanBorrowULDSession.setUldNumbersSelected(uldNumbersForNavigation);			
			loanBorrowULDSession.setTxnNumbersSelected(txnNumbersForNavigation);	 		
			log.log(Log.ALL, "setUldNumbersSelected--", loanBorrowULDSession.getUldNumbersSelected());
		}
		//Preet--This is to retain values in session for UCR printing ends
		
		// if No Error , Warnings ,if no already fired // a-5125 for ICRD-26465 
		ULDListVO uldListVO = null;
		Boolean isinvalidUldsExistForLoan = false;
		Boolean isinvalidUldsExistForBarrow = false;
		StringBuilder uldsNotInStock = new StringBuilder();
		// Boolean isUldValidFormat=false;
		if (maintainULDTransactionForm.getUldNum() != null
				&& maintainULDTransactionForm.getUldNum().length > 0) {
			//Modified by A-3415 for ICRD-114538
			Collection<String> systemparameterCodes = new ArrayList<String>();
			systemparameterCodes.add(ULD_STK_UPDATE_FOR_OWN);
			systemparameterCodes.add(ULD_STK_UPDATE_FOR_OAL);
			Map parameterMap = new HashMap();
			try {
				parameterMap = new SharedDefaultsDelegate()
						.findSystemParameterByCodes(systemparameterCodes);
			} catch (BusinessDelegateException delegateException) {
				log.log(Log.INFO, "Proxy Exception when ERROR TYPE is picked up",
						delegateException.getMessage());				
			}
			for (String UldNumber : maintainULDTransactionForm.getUldNum()) {
		try {
					/*
					 * isUldValidFormat=validateUldFormat(logonAttributes
					 * .getCompanyCode(),UldNumber);
					 */
					uldListVO = new ULDDefaultsDelegate()
							.fetchULDDetailsForTransaction(
									logonAttributes.getCompanyCode(), UldNumber);
				} catch (BusinessDelegateException e) {
					e.getMessage();
				}
				if (uldListVO == null) {
					int length = UldNumber.length();
					String twoalphacode = UldNumber.substring(length - 2);
					String threealphacode = UldNumber.substring(length - 3);
					String ownCarrierCode = logonAttributes.getOwnAirlineCode();
					boolean isOwnULD = false;
					if(ownCarrierCode!=null && (ownCarrierCode.equals(twoalphacode)||ownCarrierCode.equals(threealphacode))){
						isOwnULD = true;
					}
					//Added by A-4072 
					/*
					 * System parameter uld.defaults.cancreateuld values has been changed as part of CR
					 */
					boolean isOALUldStockUpdateEnabled = parameterMap.get(ULD_STK_UPDATE_FOR_OAL)!=null && 
							!FLAG_NO.equals(parameterMap.get(ULD_STK_UPDATE_FOR_OAL));					
					if((isOwnULD && !FLAG_YES.equals(parameterMap.get(ULD_STK_UPDATE_FOR_OWN))) ||
							(!isOwnULD && !isOALUldStockUpdateEnabled)){
					if (isinvalidUldsExistForLoan) {
						uldsNotInStock.append(ULD_SEPARATOR);
					}
					isinvalidUldsExistForLoan = true;
					uldsNotInStock.append(UldNumber);
				}
					
				}
				/* Commented by A-3415 for ICRD-114538
				 * if (uldListVO != null
						&& TRANSACTION_TYPE_BARROW
								.equalsIgnoreCase(maintainULDTransactionForm
										.getTransactionType())) {
					if (isinvalidUldsExistForBarrow) {
						uldsNotInStock.append(ULD_SEPARATOR);
					}
					isinvalidUldsExistForBarrow = true;
					uldsNotInStock.append(UldNumber);
				}*/
			}
		}
		String invalidUldsPresent = maintainULDTransactionForm.getIsInvalidUldsPresent();
		if (isinvalidUldsExistForBarrow || isinvalidUldsExistForLoan) {
			ErrorVO error = null;
			maintainULDTransactionForm.setIsInvalidUldsPresent(YES);
			loanBorrowULDSession.setTransactionVO(transactionVO);
			if (isinvalidUldsExistForLoan) {
				error = new ErrorVO("uld.defaults.transaction.err.InvalidUlds",
						new Object[] { uldsNotInStock });
			} else if (isinvalidUldsExistForBarrow) {
				error = new ErrorVO(
						"uld.defaults.transaction.err.InvalidUldsForBarrow",
						new Object[] { uldsNotInStock });
			}
			invocationContext.addError(error);
			maintainULDTransactionForm.setTxnTypeDisable(YES);
			invocationContext.target = SAVE_ERROR;
			return;
		} else {
			if (SCREEN_STATUS
					.equals(maintainULDTransactionForm.getScreenInfo())
					&& SHOW_WARNING_MSG.equals(maintainULDTransactionForm
							.getWarningMsgStatus())) {
				/*ErrorVO error = null;
				error = new ErrorVO(
						"uld.defaults.transaction.wrn.saveTransaction");
				error.setErrorDisplayType(WARNING);
				invocationContext.addError(error);
				invocationContext.target = SAVE_ERROR;*/
				maintainULDTransactionForm.setPrintUCR(YES);
				maintainULDTransactionForm.setWarningMsgStatus(NO);
				maintainULDTransactionForm.setIsInvalidUldsPresent(NO);
				//return;
				// Error window need to raise ..
			}
		}
		//Added by A-3415 for ICRD-114538
		if(filterVOs!=null && filterVOs.size()>0 && !"IGNORE_OPEN_TXN".equals(invalidUldsPresent)){
			Collection<String> openULDs = new ArrayList<String>();
		try {
				openULDs = uldDefaultsDelegate.checkIfOpenTransactionExists(filterVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				log.log(Log.FINE, "\n\n\n\n ****caught errorscheckIfOpenTransactionExists******");
				errorsaftersave = handleDelegateException(businessDelegateException);
			}
			if(openULDs!=null && openULDs.size()>0){
				String errorULDs = StringUtils.join(openULDs, ",");
				ErrorVO error = null;				
				maintainULDTransactionForm.setIsInvalidUldsPresent("OPEN_TXN_WARNING");
				loanBorrowULDSession.setTransactionVO(transactionVO);
				error = new ErrorVO("uld.defaults.transaction.err.openUldTransaction",
							new Object[] { errorULDs });	
				error.setErrorDisplayType(WARNING);
				invocationContext.addError(error);
				maintainULDTransactionForm.setTxnTypeDisable(YES);
				invocationContext.target = SAVE_ERROR;
				return;
			}
		}
		try {
			log.log(Log.INFO, "BEFORE SAVE -------------------->>",
					transactionVO);
			errorsaftersave = uldDefaultsDelegate
					.saveULDTransaction(transactionVO,locks);
			log.log(Log.FINE, "\n\n\n\n ****return errorsaftersave******");
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			log.log(Log.FINE, "\n\n\n\n ****caught errorsaftersave******");
			errorsaftersave = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "\n\n\n\n ****errors******", errorsaftersave);
		maintainULDTransactionForm.setForDamage(FLAG_NO);
		if (errorsaftersave != null && errorsaftersave.size() > 0) {
			log.log(Log.FINE, "\n\n\n\n ****ERROR PRESENT******");
			ArrayList<String> txnRefNo = new ArrayList<String>();
			Collection<ErrorVO> errorsnew = new ArrayList<ErrorVO>();
			for (ErrorVO error : errorsaftersave) {
				if (ULD_TRANSACTION_REF_NUMBER.equals(error.getErrorCode())) {
					Object[] object = error.getErrorData();
					String uld = (String) object[0];
					log.log(Log.FINE, "\n\n\n\n ****uld******", uld);
					txnRefNo.add(uld);
				}
				if (!ULD_TRANSACTION_REF_NUMBER.equals(error.getErrorCode())) {
					errorsnew.add(error);
				}
			}
			log.log(Log.FINE, "\n\n\n\n ****txnRefNo******", txnRefNo);
			loanBorrowULDSession.setTxnRefNo(txnRefNo);
			errorsaftersave = errorsnew;
		}
		if (errorsaftersave != null && errorsaftersave.size() > 0) {
			log.log(Log.FINE, "\n\n\n\n ****errors******", errorsaftersave);
			Collection<String> uldNumbers = new ArrayList<String>();
			Collection<ULDMovementVO> uldMovementVOs =
				new ArrayList<ULDMovementVO>();
			String uldN = "";
			String stn = "";
			for (ErrorVO error : errorsaftersave) {
				if ("uld.defaults.uldcurrentstation.different".equals(error
						.getErrorCode())) {
					if(uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() > 0){
						maintainULDTransactionForm.setSaveStatus("dummysaved");
					}else{
						maintainULDTransactionForm.setSaveStatus("accessoriessaved");
					}
					Object[] obj = error.getErrorData();
					uldN = (String) obj[0];
					String[] uldArr = uldN.split(",");
					if (uldArr.length != 0) {
						for (int i = 0; i < uldArr.length; i++) {
							uldNumbers.add(uldArr[i]);
						}
					}
					recordUldMovementSession.setULDNumbers(uldNumbers);

					stn = (String) obj[1];
					String[] pol = stn.split(",");
					if (pol.length != 0) {
						for (int i = 0; i < pol.length; i++) {
							ULDMovementVO uldMovementVO = new ULDMovementVO();
							uldMovementVO.setPointOfLading(pol[i]);
							uldMovementVO.setPointOfUnLading(transactionVO
									.getTransactionStation());
							uldMovementVO.setCurrentStation(transactionVO
									.getTransactionStation());
							LocalDate mvtdate = new LocalDate(uldMovementVO
									.getPointOfUnLading(), Location.ARP, true);
							uldMovementVO.setLastUpdatedTime(mvtdate);
							uldMovementVO.setCompanyCode(logonAttributes
									.getCompanyCode());
							uldMovementVO.setLastUpdatedUser(logonAttributes
									.getUserId());
							uldMovementVO.setUpdateCurrentStation(true);
							uldMovementVO.setDummyMovement(true);
							uldMovementVOs.add(uldMovementVO);
						}
					}
					recordUldMovementSession.setULDMovementVOs(uldMovementVOs);
					log.log(Log.FINE, "\n\n\n\n ERROR");
					/*
					 * TransactionVO newTransactionVO = new TransactionVO();
					 * newTransactionVO.setTransactionType("L");
					 * newTransactionVO.setTransactionNature("T");
					 * newTransactionVO.setStrTransactionDate("");
					 * loanBorrowULDSession.setTransactionVO(newTransactionVO);
					 */
				}
			}
			invocationContext.addAllError(errorsaftersave);

		} else {

			log.log(Log.FINE, "\n\n\n\n NO ERROR");
			if (!"".equals(uldDmg)) {
				if(uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() > 0){
					maintainULDTransactionForm.setSaveStatus("saved");
				}else{
					maintainULDTransactionForm.setSaveStatus("accessoriessaved");
				}
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.uldintransaction.damaged");
				errorVO.setErrorDisplayType(WARNING);
				errorsaftersave.add(errorVO);
				invocationContext.addAllError(errorsaftersave);
				maintainULDTransactionForm.setTxnTypeDisable("Y");
				maintainULDTransactionForm.setForDamage(FLAG_YES);
			} else {
				// confirmation msg not required
				// ErrorVO errorVO = new
				// ErrorVO("uld.defaults.transaction.transactionsaved");
				// errorVO.setErrorDisplayType(INFO);
				// errors.add(errorVO);
				// invocationContext.addAllError(errors);
			}

			/*
			 * if("Y".equals(maintainULDTransactionForm.getLucEnable())){
			 * invocationContext.target = SAVE_MESSAGE; return; }
			 */
			/*
			 * TransactionVO newTransactionVO = new TransactionVO();
			 * newTransactionVO.setTransactionType("L");
			 * newTransactionVO.setTransactionNature("T");
			 * newTransactionVO.setStrTransactionDate("");
			 * loanBorrowULDSession.setTransactionVO(newTransactionVO);
			 */
		}
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {

			log.log(Log.FINE, "\n\n\n\n ****SAVE 2 with error******");
			if (maintainULDTransactionForm.getPageurl() != null) {
				if (("fromulderrorlogforborrow").equals(maintainULDTransactionForm.getPageurl())) {
					loanBorrowULDSession.setPageURL("fromulderrorlogforborrow");
				} else if (("fromulderrorlogforloan").equals(maintainULDTransactionForm.getPageurl())) {
					loanBorrowULDSession.setPageURL("fromulderrorlogforloan");
				} else if (PAGE_URL.equals(maintainULDTransactionForm
						.getPageurl())) {
					loanBorrowULDSession.setPageURL(PAGE_URL);
				}else if("fromScmReconcileBorrow".equals(maintainULDTransactionForm.getPageurl())){
					loanBorrowULDSession.setPageURL("fromScmReconcileBorrow");
				}
			}
			maintainULDTransactionForm.setTxnTypeDisable("Y");
			/*maintainULDTransactionForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);*/
//			 added by nisha starts for UCR 
			if( maintainULDTransactionForm.getPrintUCR() !=null && ("Y").equals(maintainULDTransactionForm.getPrintUCR())
					&& ("").equals(maintainULDTransactionForm.getPageurl())){
				//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
				maintainULDTransactionForm.setSaveStatus("saveandprintfailure");
				invocationContext.target = SAVE_ERROR;
			}else{
			invocationContext.target = SAVE_ERROR;
			}
			//added by T-1927 for the BUG ICRD-42615
			maintainULDTransactionForm.setPrintUCR(NO);
			return;

		} else {
			if(uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() > 0){
				maintainULDTransactionForm.setSaveStatus("saved");
			}else{
				maintainULDTransactionForm.setSaveStatus("accessoriessaved");
			}
			maintainULDTransactionForm.setTxnTypeDisable("");
			log.log(Log.FINE, "\n\n\n\n ****SAVE 2 without error******");
			maintainULDTransactionForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			if (maintainULDTransactionForm.getPageurl() != null
					&& PAGE_URL.equals(maintainULDTransactionForm.getPageurl())) {
				log.log(Log.FINE, "Inside SCM Reconcile------------------>",
						loanBorrowULDSession.getPageURL());
				Collection<ULDSCMReconcileDetailsVO> reconcileVOs =
					new ArrayList<ULDSCMReconcileDetailsVO>();
				reconcileVOs.add(loanBorrowULDSession
						.getULDSCMReconcileDetailsVO());
				try {
					new ULDDefaultsDelegate()
							.removeErrorCodeForULDsInSCM(reconcileVOs);
				} catch (BusinessDelegateException ex) {
					errors = handleDelegateException(ex);
		        	if(errors != null && errors.size() > 0){
		        		invocationContext.addAllError(errors);
		        		invocationContext.target =SAVE_ERROR;
		        		return;
		        	}
				}
				TransactionVO newTransactionVO = new TransactionVO();
				newTransactionVO.setTransactionType("L");
				newTransactionVO.setTransactionNature("T");
				newTransactionVO.setStrTransactionDate("");
				newTransactionVO.setTransactionTime("");
				loanBorrowULDSession.setTransactionVO(newTransactionVO);
//				 Modified by Nisha for ULD 164 starts
				// To display save message after saving transaction 
				//maintainULDTransactionForm.setSaveStatus("borrowsaved");
				//invocationContext.target = SAVEBORROW_SUCCESS;
				maintainULDTransactionForm.setSaveStatus("saved");				
				invocationContext.target = SAVE_SUCCESS;
				// Modified by Nisha for ULD 164 ends

				return;

			}if(maintainULDTransactionForm.getPageurl()!=null && "fromScmReconcileBorrow"
					.equals(maintainULDTransactionForm.getPageurl())){
				Collection<ULDSCMReconcileDetailsVO> reconcileVOs =
					new ArrayList<ULDSCMReconcileDetailsVO>();
				reconcileVOs.add(loanBorrowULDSession
						.getULDSCMReconcileDetailsVO());
				try {
					new ULDDefaultsDelegate()
							.removeErrorCodeForULDsInSCM(reconcileVOs);
				} catch (BusinessDelegateException ex) {
					errors = handleDelegateException(ex);
		        	if(errors != null && errors.size() > 0){
		        		invocationContext.addAllError(errors);
		        		invocationContext.target =SAVE_ERROR;
		        		return;
		        	}
				}
				TransactionVO newTransactionVO = new TransactionVO();
				newTransactionVO.setTransactionType("L");
				newTransactionVO.setTransactionNature("T");
				newTransactionVO.setStrTransactionDate("");
				newTransactionVO.setTransactionTime("");
				loanBorrowULDSession.setTransactionVO(newTransactionVO);
				maintainULDTransactionForm.setSaveStatus("borrowsaved");

				invocationContext.target = SAVEBORROW_SUCCESS;
				return;


			}

			if (maintainULDTransactionForm.getPageurl() != null
					&& (("fromulderrorlogforborrow").equals(maintainULDTransactionForm.getPageurl()))) {

				log
						.log(
								Log.FINE,
								"\n \n loanBorrowULDSession.getULDFlightMessageReconcileDetailsVO()",
								loanBorrowULDSession
										.getULDFlightMessageReconcileDetailsVO());
				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				try {
					log.log(Log.FINE, "\n reconcile  delegate ");
					new ULDDefaultsDelegate()
							.reconcileUCMULDError(loanBorrowULDSession
									.getULDFlightMessageReconcileDetailsVO());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
		        	if(errors != null && errors.size() > 0){
		        		invocationContext.addAllError(errors);
		        		invocationContext.target =SAVE_ERROR;
		        		return;
		        	}
				}

				log.log(Log.FINE, "\n\n\n\n ****SAVE 2 without error******");

				/* Commented by A-3415 for ICRD-114538
				 * if (("B").equals(maintainULDTransactionForm
						.getTransactionType())) {
					TransactionVO newTransactionVO = new TransactionVO();
					newTransactionVO.setTransactionType("L");
					newTransactionVO.setTransactionNature("T");
					newTransactionVO.setStrTransactionDate("");
					newTransactionVO.setTransactionTime("");
					loanBorrowULDSession.setTransactionVO(newTransactionVO);
					maintainULDTransactionForm.setSaveStatus("borrowsaved");

					invocationContext.target = SAVEBORROW_SUCCESS;
					return;

				}*/
			} /* Commented by A-3415 for ICRD-114538
			else {
				if (("B").equals(maintainULDTransactionForm
						.getTransactionType())) {
					TransactionVO newTransactionVO = new TransactionVO();
					newTransactionVO.setTransactionType("L");
					newTransactionVO.setTransactionNature("T");
					newTransactionVO.setStrTransactionDate("");
					newTransactionVO.setTransactionTime("");
					loanBorrowULDSession.setTransactionVO(newTransactionVO);
					maintainULDTransactionForm.setSaveStatus("borrowsaved");
					log.log(Log.FINE, "\n\n\n\n ****borrowtransaction saved******");
					ErrorVO error = new ErrorVO("uld.defaults.loanBorrowULD.msg.err.borrowtransactionsaved");
								error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVEBORROW_SUCCESS;
					return;
				}
			}*/
			/* added by Preet on 12 th Dec for Client UCR printing starts*/ 
			if(maintainULDTransactionForm.getPrintUCR()!=null && "Y".equals(maintainULDTransactionForm.getPrintUCR())){
				//added by nisha for bug fix---UCR was not getting printed when we click yes after a loan transaction
				loanBorrowULDSession.setLoanUcrPrint(maintainULDTransactionForm.getPrintUCR());
				//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
				maintainULDTransactionForm.setSaveStatus("savedandprintucr");
				invocationContext.target = SAVE_SUCCESS;
			}else{
				/*if(maintainULDTransactionForm.getPrintCheck().trim().length()<1
						&& "".equals(maintainULDTransactionForm.getPrintCheck())){
					maintainULDTransactionForm.setPrintCheck(YES);
					maintainULDTransactionForm.setIsInvalidUldsPresent(NO);
				}*/				
				invocationContext.target = SAVE_SUCCESS;
			}
			//added by T-1927 for the BUG ICRD-42615
			
			if("savedandcontinue".equals(maintainULDTransactionForm.getSaveStatus())){
				maintainULDTransactionForm.setSaveStatus("savedandprintucr");
			}else{
				maintainULDTransactionForm.setSaveStatus("savedandcontinue");
			}
			/* added by Preet on 12 th Dec for Client UCR printing ends*/ 
			//invocationContext.target = SAVE_SUCCESS;
			return;
		}

	}

	/**
	 * Method to perform validations.
	 *
	 * @param maintainULDTransactionForm
	 *            MaintainULDTransactionForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			MaintainULDTransactionForm maintainULDTransactionForm) {
		log.entering("SaveULDTransactionCommand", "validateForm");
		String txnStation = maintainULDTransactionForm.getTransactionStation();
		String txnDate = maintainULDTransactionForm.getTransactionDate();
		String txnTime = maintainULDTransactionForm.getTransactionTime();
		String partyType = maintainULDTransactionForm.getPartyType();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (txnStation == null || ("".equals(txnStation))) {
			error = new ErrorVO("uld.defaults.loanborrowuld.txnstationempty");
			error.setErrorDisplayType(ERROR);
			maintainULDTransactionForm.setTxnTypeDisable("Y");
			errors.add(error);
		}
		if (txnDate == null || ("".equals(txnDate))) {
			error = new ErrorVO("uld.defaults.loanborrowuld.txndateempty");
			error.setErrorDisplayType(ERROR);
			maintainULDTransactionForm.setTxnTypeDisable("Y");
			errors.add(error);
		}
		if (txnTime == null || ("".equals(txnTime))) {
			error = new ErrorVO("uld.defaults.loanborrowuld.txndateempty");
			error.setErrorDisplayType(ERROR);
			maintainULDTransactionForm.setTxnTypeDisable("Y");
			errors.add(error);
		}
		if (partyType == null || ("".equals(partyType))) {
			error = new ErrorVO("uld.defaults.loanborrowuld.partytypeempty");
			error.setErrorDisplayType(ERROR);
			maintainULDTransactionForm.setTxnTypeDisable("Y");
			errors.add(error);
		}
		if(maintainULDTransactionForm.getCrnPrefix() !=null){
		int i=0;
		int len = maintainULDTransactionForm.getCrnPrefix().length;
		for(i=0;i<len;i++){
			if("".equals(maintainULDTransactionForm.getUldNum()[i]) ){
				log.log(Log.FINE,"\n checking------->>");
				error = new ErrorVO("uld.defaults.loanborrowuld.Uldnumberempty");
				error.setErrorDisplayType(ERROR);
				maintainULDTransactionForm.setTxnTypeDisable("Y");
				errors.add(error);
			}
			if("".equals(maintainULDTransactionForm.getCrn()[i]) ){
				log.log(Log.FINE,"\n checking1`2------->>");
				error = new ErrorVO("uld.defaults.loanborrowuld.crnempty");
				error.setErrorDisplayType(ERROR);
				maintainULDTransactionForm.setTxnTypeDisable("Y");
				// FOR icrd-ICRD-30534 author A-5125
				maintainULDTransactionForm.setPrintUCR("N");
				errors.add(error);
			}	
		}	
		}
		log.exiting("SaveULDTransactionCommand", "validateForm");
		return errors;
	}

	/*
	 * Added by Ayswarya
	 */
	private Collection<LockVO> prepareLocksForSave(
			TransactionVO transactionVO) {
		log.log(Log.FINE, "\n prepareLocksForSave------->>", transactionVO);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<LockVO>();
		Collection<ULDTransactionDetailsVO> uldDetailsVOs = transactionVO
							.getUldTransactionDetailsVOs();
		if (uldDetailsVOs != null && uldDetailsVOs.size() > 0) {
			for (ULDTransactionDetailsVO uldVO : uldDetailsVOs) {
				ULDLockVO lock = new ULDLockVO();
				lock.setAction(LockConstants.ACTION_LOANBORROWULD);
				lock.setClientType(ClientType.WEB);
				lock.setCompanyCode(logonAttributes.getCompanyCode());
				lock.setScreenId(SCREEN_ID_ONE);
				lock.setStationCode(logonAttributes.getStationCode());
				lock.setUldNumber(uldVO.getUldNumber());
				lock.setDescription(uldVO.getUldNumber());
				lock.setRemarks(uldVO.getUldNumber());
				log.log(Log.FINE, "\n lock------->>", lock);
				locks.add(lock);
			}
		}

		return locks;
	}

	
	private void updateSession(TransactionVO transactionVO,
			MaintainULDTransactionForm form,Collection<ErrorVO> errors) {
		log.log(Log.INFO, "INSIDE UPDATE SESSION");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String uldTransactionBasedOn = null;
		Map<String,String> map = new HashMap<String,String>();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(ULD_TRANSACTION_CREATION_BASED_ON);
		try {
			map = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException exception) {
			errors.addAll(handleDelegateException(exception));
		}
		uldTransactionBasedOn  = map.get(ULD_TRANSACTION_CREATION_BASED_ON);
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
		String strLeaseEndDate = form.getLeaseEndDate();
		String txnTime = form.getTransactionTime();
		LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		if(!txnTime.contains(":")){
			txnTime=txnTime.concat(":00");
		}
		StringBuilder txndat = new StringBuilder();
		txndat.append(txnDate).append(" ").append(txnTime).append(":00");
		//txnDate = txnDate.concat(" "+txnTime+(":00"));		
		/************check   for bug 102920 starts ***************************/
		if(!"R".equals(form.getTransactionType())){
		if (txndat.length()>0){
			transactionVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			transactionVO.setStrTransactionDate(strTxnDate);
			transactionVO.setTransactionTime(txnTime);
		}else {
			transactionVO.setStrTransactionDate("");
			transactionVO.setTransactionTime("");
		}
		transactionVO.setStrLeaseEndDate(Objects.nonNull(strLeaseEndDate) ? strLeaseEndDate : "");
		}
		/************check   for bug 102920 ends ***************************/
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
		transactionVO.setOriginatorName(form.getOriginatorName().toUpperCase());
		if("Y".equals(form.getLoaded())){
			transactionVO.setEmptyStatus("N");
		}else{
			transactionVO.setEmptyStatus("Y");
		}
		for (int i = 0; i < len; i++) {
			log.log(Log.FINE, "len", len);
			ULDTransactionDetailsVO vo = new ULDTransactionDetailsVO();
			if(form.getCrnPrefix()!=null){
			vo.setControlReceiptNumberPrefix(form.getCrnPrefix()[i]);
			}
			if(form.getCrn()!=null){
			vo.setCrnToDisplay(form.getCrn()[i]);
			}
		
			String crn = new StringBuilder(form.getCrnPrefix()[i]).append(
					form.getCrn()[i]).toString();
			vo.setControlReceiptNumber(crn);
			
			if(form.getFromPartyCode()!= null && form.getFromPartyCode().trim().length()>0){
				vo.setFromPartyCode(form.getFromPartyCode().toUpperCase());
			}
			vo.setFromPartyName(form.getFromPartyName());
			vo.setOperationalFlag(ULDTransactionDetailsVO.OPERATION_FLAG_INSERT);
			if(form.getToPartyCode()!= null && form.getToPartyCode().trim().length()>0){
				vo.setToPartyCode(form.getToPartyCode().toUpperCase());
			}
			vo.setToPartyName(form.getToPartyName());
			if(form.getUldNature() !=null ){
			vo.setUldNature(form.getUldNature()[i]);
			}
			//Added by A-2052 for the bug 102217 starts
			if(form.getUldNum() != null){
			vo.setUldNumber(form.getUldNum()[i].toUpperCase());
			vo.setUldType(vo.getUldNumber().substring(0,3));
			}
			//Added by A-2052 for the bug 102217 ends
			vo.setCompanyCode(logonAttributes.getCompanyCode());						
			vo.setTransactionType(form.getTransactionType());
			vo.setPartyType(form.getPartyType());
			//vo.setUldNumber(form.getUldNum()[i].toUpperCase());
			//Modified by A-3415 for ICRD-114538 starts			
			boolean isError = false;
			boolean isError2 = false;
			boolean isError3 = false;
			boolean isError4 = false;
			String configurableOwner;
			if(form.getUldNum() != null){
				String uldNumber  = form.getUldNum()[i].trim().toUpperCase();
				//boolean isThirdParty = checkThirdPartyTransaction(vo, logonAttributes);
				if(uldNumber!=null && uldNumber.trim().length() > 0){


					//Added for ICRD-349648
					if(ULD_OWNER_CODE.equals(uldTransactionBasedOn)) {
						configurableOwner = uldNumber;
					} else if(ULD_OWNER_AIRLINE_CODE.equals(uldTransactionBasedOn)) {
						ULDValidationVO uldValidationVO = null;
						try {
							uldValidationVO = new ULDDefaultsDelegate().validateULD(logonAttributes.getCompanyCode(),uldNumber);
						} catch (BusinessDelegateException e) {
							errors.addAll(handleDelegateException(e));
							configurableOwner = uldNumber;
						}
						if(uldValidationVO != null) {
							configurableOwner = uldValidationVO.getOwnerAirlineCode();
						} else {
							configurableOwner = uldNumber;
						}
					} else {
						configurableOwner = uldNumber;
					}

					if("L".equalsIgnoreCase(form.getTransactionType()) //&& !isThirdParty
							//&& AIRLINE.equals(form.getPartyType())
			    			&& form.getToPartyCode()!=null && form.getToPartyCode().trim().length()>0
			    			&& form.getFromPartyCode()!=null && form.getFromPartyCode().trim().length()>0){
						String toPartyCode = form.getToPartyCode().trim().toUpperCase();
						String fromPartyCode = form.getFromPartyCode().trim().toUpperCase();
			    		if(configurableOwner.endsWith(toPartyCode)){
			    			if(errors!=null && errors.size()>0){
			    				for(ErrorVO errVO : errors){
			    					if("uld.defaults.loanborrow.loan.topartycannotbeowner".equals(errVO.getErrorCode())){
			    						isError = true;
			    					}
			    				}
			    	         }
			    			if(!isError){
			    			ErrorVO errorVO = new ErrorVO("uld.defaults.loanborrow.loan.topartycannotbeowner", new Object[]{uldNumber});
			    			errors.add(errorVO);
			    		}
		                  }
			    		isError = false;
			    		if(!configurableOwner.endsWith(fromPartyCode)){
			    			if(errors!=null && errors.size()>0){
			    				for(ErrorVO errVO : errors){
			    					if("uld.defaults.loanborrow.loan.owneronlycanloanuld".equals(errVO.getErrorCode())
			    							|| "uld.defaults.loanborrow.loan.topartycannotbeowner".equals(errVO.getErrorCode())){
			    						isError2 = true;
			    						}
			    				}
			    	         }
			    			if(!isError2 ){
			    			ErrorVO errorVO = new ErrorVO("uld.defaults.loanborrow.loan.owneronlycanloanuld", new Object[]{uldNumber});
			    			errors.add(errorVO);
			    		}
			    	}
			    		isError2 = false;
			    	}
					if("R".equalsIgnoreCase(form.getTransactionType()) 
							//&& AIRLINE.equals(form.getPartyType())
			    			&& form.getToPartyCode()!=null && form.getToPartyCode().trim().length()>0
			    			&& form.getFromPartyCode()!=null && form.getFromPartyCode().trim().length()>0){
						String toPartyCode = form.getToPartyCode().trim().toUpperCase();
						String fromPartyCode = form.getFromPartyCode().trim().toUpperCase();
			    		if(!configurableOwner.endsWith(toPartyCode)){
			    			if(errors!=null && errors.size()>0){
			    				for(ErrorVO errVO : errors){
			    					if("uld.defaults.loanborrow.canreturnonlytoowner".equals(errVO.getErrorCode())){
			    						isError3 = true;
			    					}
			    				}
			    	         }
			    			if(!isError3){
			    			ErrorVO errorVO = new ErrorVO("uld.defaults.loanborrow.canreturnonlytoowner", new Object[]{uldNumber});
			    			errors.add(errorVO);
			    		}
			    		}
			    		isError3 = false;
			    		if(configurableOwner.endsWith(fromPartyCode)){
			    		 if(errors!=null && errors.size()>0){
                        	for(ErrorVO errVO : errors){
		    					if("uld.defaults.loanborrow.canreturnonlytoowner".equals(errVO.getErrorCode())){
		    						isError4 = true;
		    					}
		    				 }
			    		  }
                        	if(!isError4){
			    			ErrorVO errorVO = new ErrorVO("uld.defaults.loanborrow.ownercanloanuldonly", new Object[]{uldNumber});
			    			errors.add(errorVO);
			    		}
			    	   }
			    		isError4 = false;
			    	}
				}
			}
			if(!"R".equals(form.getTransactionType())){
			if (txndat.length()>0) {
				vo.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
			}
			vo.setLeaseEndDate((Objects.nonNull(strLeaseEndDate) && !strLeaseEndDate.isEmpty()
					&& Objects.nonNull(form.getDestnAirport()[i]) && !form.getDestnAirport()[i].isEmpty()) 
					? new LocalDate(form.getDestnAirport()[i], Location.ARP,true).setDate(strLeaseEndDate) : null);
			}
			if(Objects.nonNull(vo.getLeaseEndDate()) 
					&& !vo.getLeaseEndDate().isGreaterThan(new LocalDate(form.getDestnAirport()[i], Location.ARP,false))){
				ErrorVO errorVO = new ErrorVO(INVALID_LEASE_END_DATE, new Object[]{form.getUldNum()[i].trim().toUpperCase()});
				errors.add(errorVO);
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
			if(form.getUldCondition()!=null && form.getUldCondition().length>0){
			vo.setUldConditionCode(form.getUldCondition()[i]);
			}
			if (form.getUldCondition() !=null && 
					("DAM").equals(form.getUldCondition()[i])) {
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
			log.log(Log.FINE, "getUldCondition", form.getUldCondition());
			log.log(Log.FINE, "getUldCondition", vo.getDamageStatus());
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
	
	 private void updateAccSession(TransactionVO transactionVO,
	    		MaintainULDTransactionForm form) {
			
			Collection<AccessoryTransactionVO> accTxnVos = new ArrayList<AccessoryTransactionVO>();			
			String[] flags = form.getAccOperationFlag();			
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			//added by a-3045 for bug 20362 starts
			String txnDate = form.getTransactionDate();
			String txnTime = form.getTransactionTime();
			LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			if(!txnTime.contains(":")){
				txnTime=txnTime.concat(":00");
			}
			StringBuilder txndat = new StringBuilder();
			txndat.append(txnDate).append(" ").append(txnTime).append(":00");
			//added by a-3045 for bug 20362 ends
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
						//added by a-3045 for bug 20362 starts
						if(!"R".equals(form.getTransactionType())){
						if (txndat.length()>0) {
							vo.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
						}
						}
						//added by a-3045 for bug 20362 ends
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
	
	private Collection<String> validateUldNumberFormat(String companyCode,
			Collection<String> uldNumbers,Collection<ErrorVO> errors) {
		Collection<String> invalidUlds = null;
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		try {
			invalidUlds = delegate.validateMultipleULDFormats(companyCode,
					uldNumbers);
		} catch (BusinessDelegateException ex) {
			log.log(Log.FINE, "\n\n\ninside handle delegatwe exception");
		
			if(errors != null){				
				errors.addAll(handleDelegateException(ex));
			}
		}

		return invalidUlds;
	}
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

	private Collection<ErrorVO> validateAirportCodes(String companyCode,
			Collection<String> airports) {
		log.log(Log.FINE, "validateAirportCodes__________________");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			new AreaDelegate().validateAirportCodes(companyCode, airports);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);

		}
		return errors;
	}

	private Collection<ErrorVO> checkULDInUse(String companyCode,
			Collection<String> uldNumbers) {
		log.log(Log.FINE, "checkULDInUse---------");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<String> invalidUlds = null;
		StringBuilder uldNums = null;
		String inUseUldNumbers = null;
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		try {
			invalidUlds = delegate.checkULDInUse(companyCode,
					uldNumbers);
		} catch (BusinessDelegateException ex) {
			log.log(Log.FINE, "\n\n\ninside handle delegatwe exception");
			return handleDelegateException(ex);
		}
		log.log(Log.FINE, "\n\n\n invalidUlds", invalidUlds);
		if(invalidUlds != null && invalidUlds.size() > 0){			
			for(String ulds : invalidUlds){
				if (uldNums == null) {
					uldNums = new StringBuilder(ulds).append(",");
				} else {
					uldNums.append(ulds)
							.append(",");
				}				
			}
			if (uldNums != null && uldNums.length() > 0) {
				uldNums = uldNums.deleteCharAt(uldNums.length() - 1);
				inUseUldNumbers = uldNums.toString();
			}
			ErrorVO error = new ErrorVO(
			"uld.defaults.loanborrow.msg.err.uldsinuse",new Object[] {inUseUldNumbers});
			errors.add(error);			
		}
		return errors;
	}
	//added by a-3045 for ULD735 starts
	private Collection<String> searchForAgreement(TransactionVO transactionVO,
			LocalDate returnDate , Collection<ErrorVO> errors) {				
	ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
	Collection<String> agreementNumbers = new ArrayList<String>();
	Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionVO.getUldTransactionDetailsVOs();
	Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
	if(uldTransactionDetailsVOs != null && uldTransactionDetailsVOs.size() > 0){
		for(ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs){
			if("R".equals(uldTransactionDetailsVO.getTransactionType())){				
				uldTransactionDetailsVO.setReturnDate(returnDate);
			}
			uldTransactionDetailsVO.setUldType(BLANK);
			uldTxnDetailsVOs.add(uldTransactionDetailsVO);
		}
		transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);
		try {
			agreementNumbers =  uldDefaultsDelegate.findAgreementNumberForTransaction(transactionVO.getUldTransactionDetailsVOs());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			/*if(errors != null){				
				errors.addAll(handleDelegateException(businessDelegateException));
			}*/
		}
	}
	log.log(Log.FINE, "\n\n\n agreementNumbers-----------------",
			agreementNumbers);
	return agreementNumbers;
	}
	//added by a-3045 for ULD735 ends
	/**
	 * Added by A-4072 as part of CR ICRD-192300 for getting number of ULDs to to print in UCR report.
	 * and this count is used for generating crn number.
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
	/**
	 * 	Method		:	SaveULDTransactionCommand.populateSuccessMessage
	 *	Added on 	:	13-Jan-2022
	 * 	Used for 	:
	 *	Parameters	:	@param form
	 *	Parameters	:	@return 
	 *	Return type	: 	ErrorVO
	 */
	private ErrorVO populateSuccessMessage(MaintainULDTransactionForm form){
		ErrorVO error = new ErrorVO("uld.defaults.loanBorrowULD.loansaved");
		if(Objects.equals("R", form.getTransactionType())){
			error = new ErrorVO("uld.defaults.transaction.msg.info.returnsaved");
		}
		return error;
	}
}
