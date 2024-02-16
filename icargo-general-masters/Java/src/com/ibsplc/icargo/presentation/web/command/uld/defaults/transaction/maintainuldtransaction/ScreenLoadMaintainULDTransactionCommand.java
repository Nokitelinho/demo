/*
 * ScreenLoadMaintainULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 * 
 */
public class ScreenLoadMaintainULDTransactionCommand extends BaseCommand {

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
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";

	private static final String TXNNATURE_ONETIME = "uld.defaults.TxnNature";

	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
	
	private static final String ULDNATURE_ONETIME = "uld.defaults.uldnature";

	private static final String ACCESSCODE_ONETIME = "uld.defaults.accessoryCode";

	private static final String PAGE_URL = "fromScmUldReconcile";
	
	private static final String SAVE_SUCCESS = "save_success";
	//Added by A-2412 
	//private static final String CONDITIONCODE_ONETIME = "uld.defaults.conditioncode";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainULDTransactionForm maintainULDTransactionForm = (MaintainULDTransactionForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = "";
		String stationCode = "";
		String userId = "";
		companyCode = logonAttributes.getCompanyCode();
		stationCode = logonAttributes.getAirportCode();
		userId = logonAttributes.getUserId();

		log.log(Log.FINE, "\n\n\n\n ****SCREENLOAD***");
		
		TransactionVO transactionVO = new TransactionVO();
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		if(!"cleared".equals(maintainULDTransactionForm.getSaveStatus())){
		if("Y".equals(maintainULDTransactionForm.getPrintUCR()) && !("print_failure".equals(loanBorrowULDSession.getLoanUcrPrint()))){
			loanBorrowULDSession.setLoanUcrPrint("");
			maintainULDTransactionForm.setPrintUCR("");
			ErrorVO error= new ErrorVO("uld.defaults.loanBorrowULD.loansaved");
			invocationContext.addError(error);
			invocationContext.target =SAVE_SUCCESS;  
		}else if("Y".equals(maintainULDTransactionForm.getPrintUCR()) &&("print_failure".equals(loanBorrowULDSession.getLoanUcrPrint()))){
			loanBorrowULDSession.setLoanUcrPrint("");
			maintainULDTransactionForm.setPrintUCR("");
			invocationContext.target = SCREENLOAD_SUCCESS;
			return;
			}
		}
		loanBorrowULDSession.setCtrlRcptNo(null);
		loanBorrowULDSession.setSelectedULDColl(null);
		if (("nolucmessage").equals(maintainULDTransactionForm.getSaveStatus())) {
			TransactionVO newTransactionVO = new TransactionVO();
			newTransactionVO.setTransactionType("L");
			newTransactionVO.setTransactionNature("T");
			newTransactionVO.setStrTransactionDate("");
			newTransactionVO.setTransactionTime("");
			loanBorrowULDSession.setTransactionVO(newTransactionVO);
			maintainULDTransactionForm.setSaveStatus("");
		}
		maintainULDTransactionForm.setFromPartyCode("");
		maintainULDTransactionForm.setToPartyCode("");
		// QF1506
		maintainULDTransactionForm.setStationCode(stationCode);
//		added by nisha for defaulting partytype as airline on screenload
		maintainULDTransactionForm.setPartyType("A");
		
		loanBorrowULDSession.setCompanyCode(companyCode);
		loanBorrowULDSession.setStationCode(stationCode);
		loanBorrowULDSession.setUserId(userId);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//removed by nisha on 30Apr08
		
    	log.log(Log.FINE, "logonAttributes.getCompanyCode()------->",
				logonAttributes.getCompanyCode());
		log.log(Log.FINE, "logonAttributes.getUserId()     ------->",
				logonAttributes.getUserId());
			if (logonAttributes.isAirlineUser()) {
			transactionVO.setFromPartyCode(logonAttributes.getOwnAirlineCode());
			AirlineLovFilterVO airlineLovFilterVO=new  AirlineLovFilterVO();
			airlineLovFilterVO.setCompanyCode(companyCode);
			airlineLovFilterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
			airlineLovFilterVO.setDisplayPage(1);	
			Page<AirlineLovVO> page=null;
			try{
			page= new AirlineDelegate().findAirlineLov(airlineLovFilterVO,1);
			
			} catch (BusinessDelegateException businessDelegateException) {	
				 handleDelegateException(businessDelegateException);
			}
		    transactionVO.setFromPartyName(page.get(0).getAirlineName());
			maintainULDTransactionForm.setDisableStatus("airline");
			maintainULDTransactionForm.setAirlineCode(logonAttributes.getOwnAirlineCode());
			maintainULDTransactionForm.setAirlineName(page.get(0).getAirlineName());
			log.log(Log.FINE, "AirlineCode----->", maintainULDTransactionForm.getAirlineCode());
			log.log(Log.FINE, "AirlineName----->", maintainULDTransactionForm.getAirlineName());
			
			
			
		} else {
			maintainULDTransactionForm.setDisableStatus("GHA");
		}
		
		transactionVO.setTransactionType("L");
		transactionVO.setTransactionNature("T");
		LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, true);
		transactionVO.setStrTransactionDate(ldte
				.toDisplayFormat(CALENDAR_DATE_FORMAT));	
		
		transactionVO.setTransactionTime(ldte.toDisplayTimeOnlyFormat(true));
		
		transactionVO.setTransactionStation(loanBorrowULDSession
				.getStationCode());
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		if (("fromulderrorlogforborrow").equals(loanBorrowULDSession.getPageURL())) {
			log.log(Log.INFO,"inside page url fromulderrorlogforborrow");
			//added by nisha on 29JAN starts for bugfix
			transactionVO.setFromPartyName("");
			maintainULDTransactionForm.setAirlineName("");
			//added by nisha on 29JAN ends for bugfix
			maintainULDTransactionForm.setPageurl("fromulderrorlogforborrow");
			String uldnum = loanBorrowULDSession
					.getULDFlightMessageReconcileDetailsVO().getUldNumber();

			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				log
						.log(
								Log.FINE,
								"logonAttributes.getOwnAirlineIdentifier()--------------->",
								logonAttributes.getOwnAirlineIdentifier());
				airlineValidationVO = airlineDelegate.findAirline(companyCode,
						logonAttributes.getOwnAirlineIdentifier());

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}

			String airlineCode = airlineValidationVO.getAlphaCode();
			int airlineCodeSize = airlineCode.length();
			String uldAirlineCode = uldnum.substring(uldnum.length()
					- airlineCodeSize, uldnum.length());
			log.log(Log.FINE, "uldAirlineCode-------------------->",
					uldAirlineCode);
			transactionVO.setFromPartyCode(uldAirlineCode);
			transactionVO.setToPartyCode(loanBorrowULDSession
					.getULDFlightMessageReconcileDetailsVO().getCarrierCode());

			// populating the transaction for borrow

			ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
			//added by a-3045 for bug ULD575 starts
			//added for setting CRN while coming for SCM Reconcile
			String airlineID = null;
			if (uldAirlineCode != null) {
				log.log(Log.ALL, "crn number is null");
				try {
					String crn = new ULDDefaultsDelegate()
							.findCRNForULDTransaction(logonAttributes
									.getCompanyCode(), uldAirlineCode);
					if (crn != null && crn.length() > 0) {
						//Modified by A-4803 for ICRD-6983  
						int count=0;      
						String number = crn.substring(crn.length() - 7, crn.length());  
						airlineID = new StringBuilder(crn.substring(0, 4)).append(count).toString();  
						log.log(Log.ALL, "airlineID is===", airlineID);
						uldTransactionDetailsVO.setControlReceiptNumberPrefix(airlineID);  
						uldTransactionDetailsVO.setCrnToDisplay(new StringBuilder(number).toString());  
					}  
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			}
			//added by a-3045 for bug ULD575 ends
			if (maintainULDTransactionForm.getBorrowUldNum() != null) {
				uldTransactionDetailsVO.setUldNumber(maintainULDTransactionForm
						.getBorrowUldNum().toUpperCase());
			}
			String txnNum = maintainULDTransactionForm.getBorrowTxnNum();
			if (!("").equalsIgnoreCase(txnNum)) {
				//uldTransactionDetailsVO.setCapturedRefNumber(txnNum);
				uldTransactionDetailsVO.setControlReceiptNumber(txnNum);
			}
			uldTransactionDetailsVO.setOperationalFlag("I");
			if ("on".equalsIgnoreCase(maintainULDTransactionForm
					.getBorrowDamage())) {
				uldTransactionDetailsVO.setDamageStatus("Y");
			} else {
				uldTransactionDetailsVO.setDamageStatus("N");
			}
			if (transactionVO.getFromPartyCode() != null) {
				uldTransactionDetailsVO.setReturnPartyCode(transactionVO
						.getFromPartyCode().toUpperCase());
			}
			if (maintainULDTransactionForm.getBorrowTransactionStation() != null
					&& maintainULDTransactionForm.getBorrowTransactionStation()
							.trim().length() > 0) {

				uldTransactionDetailsVO
						.setTxStationCode(maintainULDTransactionForm
								.getBorrowTransactionStation().toUpperCase());

			} else {
				uldTransactionDetailsVO
						.setTxStationCode(getApplicationSession().getLogonVO()
								.getAirportCode());

			}
			uldTransactionDetailsVO.setUldNumber(loanBorrowULDSession
					.getULDFlightMessageReconcileDetailsVO().getUldNumber());
			Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
			uldTxnDetailsVOs.add(uldTransactionDetailsVO);
			transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);

			// finished populating for borrow

			loanBorrowULDSession.setPageURL(null);

		} else if ((("fromulderrorlogforloan").equals(loanBorrowULDSession.getPageURL()) || 
						("fromulderrorlogforloanforflight").equals(loanBorrowULDSession.getPageURL()))) {
//			added by nisha on 29JAN starts for bugfix
			transactionVO.setFromPartyName("");
			maintainULDTransactionForm.setAirlineName("");
			//added by nisha on 29JAN ends for bugfix
			String uldnum = loanBorrowULDSession
					.getULDFlightMessageReconcileDetailsVO().getUldNumber();

			Integer oprAirline = 0;
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				oprAirline = uldDefaultsDelegate.findOperationalAirlineForULD(
						companyCode, uldnum);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "oprAirline-------------------->", oprAirline);
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			Collection<ErrorVO> err = new ArrayList<ErrorVO>();
			try {
				log
						.log(
								Log.FINE,
								"logonAttributes.getOwnAirlineIdentifier()--------------->",
								logonAttributes.getOwnAirlineIdentifier());
				airlineValidationVO = airlineDelegate.findAirline(companyCode,
						oprAirline);

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				err = handleDelegateException(businessDelegateException);
			}
			String oprAirlineCode = airlineValidationVO.getAlphaCode();
			if (("fromulderrorlogforloanforflight").equals(loanBorrowULDSession.getPageURL())) {
				transactionVO.setFromPartyCode(oprAirlineCode);
				transactionVO.setToPartyCode(loanBorrowULDSession
						.getULDFlightMessageReconcileDetailsVO()
						.getCarrierCode());

			}

			// populate details VO

			ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
			//added by a-3045 for bug ULD575 starts
			//added for setting CRN while coming for SCM Reconcile
			String airlineID = null;
			if (oprAirlineCode != null) {
				log.log(Log.ALL, "crn number is null");
				try {
					String crn = new ULDDefaultsDelegate()
							.findCRNForULDTransaction(logonAttributes
									.getCompanyCode(), oprAirlineCode);
					if (crn != null && crn.length() > 0) {

						String number = crn.substring(crn.length() - 7, crn
								.length());
						airlineID = new StringBuilder(crn.substring(0, 5))
								.toString();
						log.log(Log.ALL, "airlineID is===", airlineID);
						uldTransactionDetailsVO
								.setControlReceiptNumberPrefix(airlineID);
						uldTransactionDetailsVO
								.setCrnToDisplay(new StringBuilder(number)
										.toString());
					}
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			}
			//added by a-3045 for bug ULD575 ends
			if (maintainULDTransactionForm.getLoanUldNum() != null) {
				uldTransactionDetailsVO.setUldNumber(maintainULDTransactionForm
						.getLoanUldNum().toUpperCase());
			}
			String txnNum = maintainULDTransactionForm.getLoanTxnNum();
			if (!("").equalsIgnoreCase(txnNum)) {
				//uldTransactionDetailsVO.setCapturedRefNumber(txnNum);
				uldTransactionDetailsVO.setControlReceiptNumber(txnNum);
			}
			uldTransactionDetailsVO.setOperationalFlag("I");
			if ("true".equalsIgnoreCase(maintainULDTransactionForm
					.getLoanDamage())) {
				uldTransactionDetailsVO.setDamageStatus("Y");
			} else {
				uldTransactionDetailsVO.setDamageStatus("N");
			}
			if (transactionVO.getFromPartyCode() != null) {
				uldTransactionDetailsVO.setReturnPartyCode(transactionVO
						.getFromPartyCode().toUpperCase());
			}
			if (maintainULDTransactionForm.getLoanTransactionStation() != null
					&& maintainULDTransactionForm.getLoanTransactionStation()
							.trim().length() > 0) {

				uldTransactionDetailsVO
						.setTxStationCode(maintainULDTransactionForm
								.getLoanTransactionStation().toUpperCase());

			} else {
				uldTransactionDetailsVO
						.setTxStationCode(getApplicationSession().getLogonVO()
								.getAirportCode());

			}
			uldTransactionDetailsVO.setUldNumber(loanBorrowULDSession
					.getULDFlightMessageReconcileDetailsVO().getUldNumber());

			Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
			uldTxnDetailsVOs.add(uldTransactionDetailsVO);
			transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);

			// finished populating

			loanBorrowULDSession.setTransactionVO(transactionVO);

			maintainULDTransactionForm.setPageurl("fromulderrorlogforloan");
			loanBorrowULDSession.setPageURL(null);

		} else if (loanBorrowULDSession.getPageURL() != null
				&& PAGE_URL.equals(loanBorrowULDSession.getPageURL())) {
//			added by nisha on 29JAN starts for bugfix
			transactionVO.setFromPartyName("");
			maintainULDTransactionForm.setAirlineName("");
			//added by nisha on 29JAN ends for bugfix
			String uldnum = loanBorrowULDSession.getULDSCMReconcileDetailsVO()
					.getUldNumber();

			int oprAirline = 0;
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				oprAirline = uldDefaultsDelegate.findOperationalAirlineForULD(
						companyCode, uldnum);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "oprAirline-------------------->", oprAirline);
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;
			Collection<ErrorVO> err = new ArrayList<ErrorVO>();
			try {
				log
						.log(
								Log.FINE,
								"logonAttributes.getOwnAirlineIdentifier()--------------->",
								logonAttributes.getOwnAirlineIdentifier());
				airlineValidationVO = airlineDelegate.findAirline(companyCode,
						oprAirline);

			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				err = handleDelegateException(businessDelegateException);
			}
			String oprAirlineCode = airlineValidationVO.getAlphaCode();
			transactionVO.setFromPartyCode(oprAirlineCode);
			transactionVO.setToPartyCode(loanBorrowULDSession
					.getULDSCMReconcileDetailsVO().getFlightCarrierCode());
			// populate transaction details VO
			ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();

			uldTransactionDetailsVO
					.setOperationalFlag(ULDTransactionDetailsVO.OPERATION_FLAG_INSERT);
			uldTransactionDetailsVO.setUldNumber(uldnum);
			uldTransactionDetailsVO.setCompanyCode(logonAttributes
					.getCompanyCode());
			//added by a-3045 for bug ULD575 starts
			//added for setting CRN while coming for SCM Reconcile
			String airlineID = null;
			if (oprAirlineCode != null) {
				log.log(Log.ALL, "crn number is null");
				try {
					String crn = new ULDDefaultsDelegate()
							.findCRNForULDTransaction(logonAttributes
									.getCompanyCode(), oprAirlineCode);
					if (crn != null && crn.length() > 0) {

						String number = crn.substring(crn.length() - 7, crn
								.length());
						airlineID = new StringBuilder(crn.substring(0, 5))
								.toString();
						log.log(Log.ALL, "airlineID is===", airlineID);
						uldTransactionDetailsVO
								.setControlReceiptNumberPrefix(airlineID);
						uldTransactionDetailsVO
								.setCrnToDisplay(new StringBuilder(number)
										.toString());
					}
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			}
			//added by a-3045 for bug ULD575 ends
			if (transactionVO.getFromPartyCode() != null) {
				uldTransactionDetailsVO.setReturnPartyCode(transactionVO
						.getFromPartyCode().toUpperCase());
			}
			uldTransactionDetailsVO.setTxStationCode(getApplicationSession()
					.getLogonVO().getAirportCode());
			Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
			uldTxnDetailsVOs.add(uldTransactionDetailsVO);
			transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);
			maintainULDTransactionForm.setPageurl(PAGE_URL);
			loanBorrowULDSession.setPageURL(null);
		}else if(loanBorrowULDSession.getPageURL() != null &&
				"fromScmReconcileBorrow".equals(loanBorrowULDSession.getPageURL())){
			log.log(Log.FINE,"Inside SCM Borrow Reconcile");
//			added by nisha on 29JAN starts for bugfix
			transactionVO.setFromPartyName("");
			maintainULDTransactionForm.setAirlineName("");
			//added by nisha on 29JAN ends for bugfix
			/*
			 *SCM Borrow Transaction
			 */
				
				String uldNumber=loanBorrowULDSession.
				getULDSCMReconcileDetailsVO().getUldNumber();
				String airlineCode = uldNumber.
					substring(uldNumber.length()-3,uldNumber.length());
				
				if(Character.isDigit(airlineCode.charAt(0))){
					airlineCode=airlineCode.substring(1);
				}
				transactionVO.setFromPartyCode(airlineCode);
				
				transactionVO.setToPartyCode(loanBorrowULDSession.
						getULDSCMReconcileDetailsVO().getFlightCarrierCode());
				String uldNum=loanBorrowULDSession.getULDSCMReconcileDetailsVO()
									.getUldNumber();
				
				//populate transaction details VO
				ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();

				uldTransactionDetailsVO
						.setOperationalFlag(ULDTransactionDetailsVO.OPERATION_FLAG_INSERT);
				uldTransactionDetailsVO.setUldNumber(uldNum);
				uldTransactionDetailsVO.setCompanyCode(logonAttributes
						.getCompanyCode());
				//added by a-3045 for bug ULD575 starts
				//added for setting CRN while coming for SCM Reconcile
				String airlineID = null;
				if (airlineCode != null) {
					log.log(Log.ALL, "crn number is null");
					try {
						String crn = new ULDDefaultsDelegate()
								.findCRNForULDTransaction(logonAttributes
										.getCompanyCode(), airlineCode);
						if (crn != null && crn.length() > 0) {
							//Modified by A-4803 for ICRD-6983
							int count=0;
							String number = crn.substring(crn.length() - 7, crn.length());
							airlineID = new StringBuilder(crn.substring(0, 4)).append(count).toString();
							log.log(Log.ALL, "airlineID is===", airlineID);
							uldTransactionDetailsVO.setControlReceiptNumberPrefix(airlineID);
							uldTransactionDetailsVO.setCrnToDisplay(new StringBuilder(number).toString());
						}
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				}
				//added by a-3045 for bug ULD575 ends
				
				if (transactionVO.getFromPartyCode() != null) {
					uldTransactionDetailsVO.setReturnPartyCode(transactionVO
							.getFromPartyCode().toUpperCase());
				}
				uldTransactionDetailsVO.setTxStationCode(getApplicationSession()
						.getLogonVO().getAirportCode());
				Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = 
						new ArrayList<ULDTransactionDetailsVO>();
				uldTxnDetailsVOs.add(uldTransactionDetailsVO);
				transactionVO.setUldTransactionDetailsVOs(uldTxnDetailsVOs);
				maintainULDTransactionForm.setPageurl("fromScmReconcileBorrow");
				loanBorrowULDSession.setPageURL(null);

				
				
		}
		else {
			loanBorrowULDSession.setULDFlightMessageReconcileDetailsVO(null);
			loanBorrowULDSession.setPageURL(null);
		}

		HashMap<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues(companyCode);

		Collection<OneTimeVO> txnTypes = oneTimeValues.get(TXNTYPE_ONETIME);
		if (Objects.nonNull(txnTypes)
				&& !(txnTypes.stream().anyMatch(txnType -> Objects.equals(TXNTYPE_ONETIME, txnType.getFieldType())
						&& Objects.equals("R", txnType.getFieldValue())))) {
			OneTimeVO oneTimeVO = new OneTimeVO();
			oneTimeVO.setCompanyCode(companyCode);
			oneTimeVO.setFieldDescription("Return");
			oneTimeVO.setFieldType(TXNTYPE_ONETIME);
			oneTimeVO.setFieldValue("R");
			txnTypes.add(oneTimeVO);
		}
		log.log(Log.FINE, "****txnTypes  OneTime******", txnTypes);
		loanBorrowULDSession.setTxnTypes(txnTypes);

		Collection<OneTimeVO> txnNatures = oneTimeValues.get(TXNNATURE_ONETIME);
		log.log(Log.FINE, "****txnNatures  OneTime******", txnNatures);
		loanBorrowULDSession.setTxnNatures(txnNatures);

		Collection<OneTimeVO> partyTypes = oneTimeValues.get(PARTYTYPE_ONETIME);
		log.log(Log.FINE, "****partyTypes  OneTime******", partyTypes);
		loanBorrowULDSession.setPartyTypes(partyTypes);

		Collection<OneTimeVO> accessCodes = oneTimeValues
				.get(ACCESSCODE_ONETIME);
		log.log(Log.FINE, "****partyTypes  OneTime******", accessCodes);
		loanBorrowULDSession.setAccessoryCodes(accessCodes);
		
		//Added by A-2412
	//	Collection<OneTimeVO> conditionCodes = oneTimeValues.get(CONDITIONCODE_ONETIME);
		//log.log(Log.FINE, "****conditionCodes  OneTime******" + conditionCodes);
		//loanBorrowULDSession.setConditionCodes(conditionCodes);
        //Addition ends 
		//added by a-3045 for CR QF1016 starts
		Collection<OneTimeVO> uldNature = oneTimeValues.get(ULDNATURE_ONETIME);
		log.log(Log.FINE, "****uldNature  OneTime******", partyTypes);
		loanBorrowULDSession.setUldNature(uldNature);
		//added by a-3045 for CR QF1016 ends
		
		loanBorrowULDSession.setOneTimeValues(oneTimeValues);

		log.log(Log.FINE,
				"****maintainULDTransactionForm.getSaveStatus()******",
				maintainULDTransactionForm.getSaveStatus());
		log.log(Log.FINE,
				"****maintainULDTransactionForm.getFromPartNamme()******",
				maintainULDTransactionForm.getFromPartyName());
		if (("cleared").equals(maintainULDTransactionForm.getSaveStatus())) {
			transactionVO.setUldTransactionDetailsVOs(null);
		}
		//Added by A-7978 for ICRD-248049 starts
		Collection<ULDServiceabilityVO> uldServiceabilityVOs = new ArrayList<ULDServiceabilityVO>();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		//Added by A-7978 for ICRD-248049 ends
		loanBorrowULDSession.setTransactionVO(transactionVO);
		maintainULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		// maintainULDForm.setStatusFlag("screenload");
		maintainULDTransactionForm.setTxnTypeDisable("");
		maintainULDTransactionForm.setComboFlag("agentlov");
		//Default value as Airline already set 
		maintainULDTransactionForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		log.log(Log.FINE, "\n\n\n\n ****SCREENLOAD******");
		//Added by A-7978 for ICRD-248049 starts here
		try {
			uldServiceabilityVOs = delegate.listULDServiceability(
					logonAttributes.getCompanyCode(), maintainULDTransactionForm.getPartyType());
			log.log(log.FINE,"uldServiceabilityVOs getting from delegate--------->>>>>>>>>>>>>>",uldServiceabilityVOs);
			loanBorrowULDSession.setULDServiceabilityVOs(uldServiceabilityVOs);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			errors = handleDelegateException(e);
		}
		//Added by A-7978 for ICRD-248049 ends here
		invocationContext.target = SCREENLOAD_SUCCESS;

	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(
			String companyCode) {
		log.entering("ScreenLoadCommand", "getOneTimeValues");

		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand", "getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
	}

	/**
	 * Method to populate the collection of onetime parameters to be obtained
	 * 
	 * @return parameterTypes
	 */
	private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(TXNTYPE_ONETIME);
		parameterTypes.add(TXNNATURE_ONETIME);
		parameterTypes.add(PARTYTYPE_ONETIME);
		parameterTypes.add(ACCESSCODE_ONETIME);
		parameterTypes.add(ULDNATURE_ONETIME);

		// Added by A-2412
	//	parameterTypes.add(CONDITIONCODE_ONETIME);
		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}

}
