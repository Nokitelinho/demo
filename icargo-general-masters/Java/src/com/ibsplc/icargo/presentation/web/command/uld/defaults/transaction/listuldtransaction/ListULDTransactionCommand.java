/*
 * ListULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.STATUS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;

import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 * 
 */
public class ListULDTransactionCommand extends BaseCommand {

	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";

	/**
	 * Target if success
	 */
	private static final String LIST_SUCCESS = "list_success";

	private static final String AIRLINE = "Ae";

	private static final String AGENT = "G";
	
	private static final String UPDATED = "U";
	
	private static final String RESENT = "R";
	
	private static final String SENT = "S";
	
	private static final String MUCREQUIRED = "Q";
	
	private static final String NOT_TO_BE_REPORTED = "N";
	
	private static final String STR_MUCREQUIRED = "MUC Required";
	
	private static final String STR_NOT_TO_BE_REPORTED = "Not to be Reported";
	
	private static final String STR_GENERATED = "MUC Generated";
	//added by a-3045 for bug18209 starts
	private static final String FROM_LOANBORROWENQUIRY = "ListLoanBorrowEnq";
	private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";
	private static final String TXNNATURE_ONETIME = "uld.defaults.TxnNature";
	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
	private static final String ACCESSCODE_ONETIME = "uld.defaults.accessoryCode";
	private static final String TXNSTATUS_ONETIME = "uld.defaults.transactionStatus";	    
	private static final String MUCSTATUS_ONETIME = "uld.defaults.mucstatus";
	private static final String LOAN_TRANSACTION = "L";
	private static final String YES = "Y";
	//added by a-3045 for bug18209 ends
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListULDTransactionCommand", "execute");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String prtyCode = "";
		AirlineValidationVO ownerVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();
		AgentVO agentVO = null;		

		log.log(Log.FINE,
				"<<--------listULDTransactionForm.getTxnType()------->>>",
				listULDTransactionForm.getTxnType());
		//added by a-3045 for bug18209 starts
		if(FROM_LOANBORROWENQUIRY.equals(listULDTransactionForm.getPageURL())){			
			HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues(logonAttributes.getCompanyCode());			
			Collection<OneTimeVO> txnTypes = oneTimeValues.get(TXNTYPE_ONETIME);
			log.log(Log.FINE, "****txnTypes  OneTime******", txnTypes);
			listULDTransactionSession.setTxnTypes(txnTypes);			
			Collection<OneTimeVO> txnNatures = oneTimeValues.get(TXNNATURE_ONETIME);
			log.log(Log.FINE, "****txnNatures  OneTime******", txnNatures);
			listULDTransactionSession.setTxnNatures(txnNatures);			
			Collection<OneTimeVO> partyTypes = oneTimeValues.get(PARTYTYPE_ONETIME);
			log.log(Log.FINE, "****partyTypes  OneTime******", partyTypes);
			listULDTransactionSession.setPartyTypes(partyTypes);			
			Collection<OneTimeVO> accessCodes = oneTimeValues.get(ACCESSCODE_ONETIME);
			log.log(Log.FINE, "****accessCodes  OneTime******", accessCodes);
			listULDTransactionSession.setAccessoryCodes(accessCodes);			
			Collection<OneTimeVO> txnStatus = oneTimeValues.get(TXNSTATUS_ONETIME);
			log.log(Log.FINE, "****txnStatus  OneTime******", txnStatus);
			listULDTransactionSession.setTxnStatus(txnStatus);			
			Collection<OneTimeVO> mucStatus = oneTimeValues.get(MUCSTATUS_ONETIME);
			log.log(Log.FINE, "****txnStatus  OneTime******", mucStatus);
			listULDTransactionSession.setMUCStatus(mucStatus);			
			listULDTransactionSession.setPageURL(listULDTransactionForm.getPageURL());
		}
		//added by a-3045 for bug18209 ends
		//commented by a-3278 for bug 17954 on 06Sep08
		/*if (("L").equals(listULDTransactionForm.getTxnType())) {
			prtyCode = listULDTransactionForm.getToPartyCode().toUpperCase();

			if (listULDTransactionForm.getToPartyCode() != null
					&& !("".equals(listULDTransactionForm.getToPartyCode()))) {
				log
						.log(Log.FINE,
								"<<----------------AirlineDelegate---------------------->>>");
				try {
					ownerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							listULDTransactionForm.getToPartyCode()
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
				}
			}

		}
		if (("B").equals(listULDTransactionForm.getTxnType())) {
			prtyCode = listULDTransactionForm.getFromPartyCode().toUpperCase();

			if (listULDTransactionForm.getFromPartyCode() != null
					&& !("".equals(listULDTransactionForm.getFromPartyCode()))) {
				log
						.log(Log.FINE,
								"<<----------------AirlineDelegate---------------------->>>");
				try {
					ownerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),
							listULDTransactionForm.getFromPartyCode()
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {

					errors = handleDelegateException(businessDelegateException);
				}
			}
		}*/
		if (("L").equals(listULDTransactionForm.getTxnType())
				//|| ("B").equals(listULDTransactionForm.getTxnType())
				) {
			log.log(Log.FINE,
					"listULDTransactionForm.getComboFlag()-------->>>",
					listULDTransactionForm.getComboFlag());
			if (AIRLINE.equals(listULDTransactionForm.getPartyType())) {
				if (prtyCode != null && !("".equals(prtyCode))) {
					log
							.log(Log.FINE,
									"<<----------------AirlineDelegate---------------------->>>");
					try {
						ownerVO = new AirlineDelegate().validateAlphaCode(
								logonAttributes.getCompanyCode(), prtyCode
										.toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {

						errors = handleDelegateException(businessDelegateException);
					}
				}
			}
			if (AGENT.equals(listULDTransactionForm.getPartyType())) {
				if (prtyCode != null && !("".equals(prtyCode))) {
					log
							.log(Log.FINE,
									"<<----------------agentDelegate---------------------->>>");
					Collection<ErrorVO> error = new ArrayList<ErrorVO>();
					try {
						agentVO = agentDelegate.findAgentDetails(
								logonAttributes.getCompanyCode(), prtyCode
										.toUpperCase());
					} catch (BusinessDelegateException exception) {
						log.log(Log.FINE, "*****in the exception");
						exception.getMessage();
						error = handleDelegateException(exception);
					}
					if (agentVO == null) {
						ErrorVO errorVO = new ErrorVO(
								"uld.defaults.generateinvoice.invalidinvoicedtocode");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(errorVO);
					}

				}
			}
		}
		Collection<String> airportCodes = new ArrayList<String>();
		/*
		 * if (errors != null && errors.size() > 0) {
		 * invocationContext.addAllError(errors); invocationContext.target
		 * =LIST_SUCCESS; return; }
		 */
		//added by nisha for BugFix on 10Jun08 starts
		if (listULDTransactionForm.getTxnStation() != null
				&& listULDTransactionForm.getTxnStation().trim().length() > 0) {
			airportCodes.add(listULDTransactionForm.getTxnStation().trim()
					.toUpperCase());
		}
		if (listULDTransactionForm.getReturnStation() != null
				&& listULDTransactionForm.getReturnStation().trim().length() > 0) {
			airportCodes.add(listULDTransactionForm.getReturnStation().trim()
					.toUpperCase());
		}
		log.log(Log.INFO, "airport codes", airportCodes);
		//		added by nisha for BugFix on 10Jun08 ends
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();

		// errors = validateForm(listULDTransactionForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			return;
		}
		transactionFilterVO = makeFilter(listULDTransactionForm,
				listULDTransactionSession, logonAttributes);
		if(listULDTransactionForm.getMucReferenceNumber()!=null 
				&& listULDTransactionForm.getMucReferenceNumber().trim().length()>0	){
			transactionFilterVO.setMucReferenceNumber(listULDTransactionForm.getMucReferenceNumber().trim());
		}
		//added by a-3045 for bug18209 starts
		if(transactionFilterVO.getCompanyCode() == null){
			transactionFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		}
		if(FROM_LOANBORROWENQUIRY.equals(listULDTransactionForm.getPageURL())){
			transactionFilterVO.setTransactionType(transactionFilterVO.TRANSACTION_TYPE_ALL);
		}
		//added by a-3045 for bug18209 ends
		if(("FROMLISTBUTTON").equals(listULDTransactionForm.getListMode()))
		{
			transactionFilterVO.setPageNumber(1);
			listULDTransactionSession.setTransactionFilterVO(null);
			listULDTransactionSession.setTransactionListVO(null);	
			listULDTransactionForm.setListMode("");
		}
		log.log(Log.FINE,
				"ListULDTransactionCommand ~~~~~~transactionFilterVO~~~",
				transactionFilterVO);
		if("Y".equals(listULDTransactionForm.getTotalCountFlag()) &&
				listULDTransactionSession.getTotalRecords() != 0){
			transactionFilterVO.setTotalRecord(listULDTransactionSession.getTotalRecords());
		}else{
			transactionFilterVO.setTotalRecord(-1);
		}
		/**as part of IAG query optimization controllerd list loan transaction based on system param. If System param Y, 
	     * List query will consider loan demurage details with ULDTXNMST saved data else list with active agreements  **/
		ArrayList<String> parameterCodes = new ArrayList<>();
		HashMap<String, String> systemParameterCodes = new HashMap<>();
		parameterCodes.add("uld.defaults.demurrageoncurrentloanagreement");
		try {
			systemParameterCodes = (HashMap<String, String>) (new SharedDefaultsDelegate().findSystemParameterByCodes(parameterCodes));
			if(Objects.nonNull(systemParameterCodes)){
				transactionFilterVO.setIsAgreementListingRequired(systemParameterCodes.get("uld.defaults.demurrageoncurrentloanagreement"));
			}
		} catch (BusinessDelegateException businessException) {
			errors=handleDelegateException(businessException);
		}
		TransactionListVO transactionListVO = new TransactionListVO();
		
		try {
			transactionListVO = uldDefaultsDelegate
					.listULDTransactionDetails(transactionFilterVO);
			
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		
		log.log(Log.FINE, "TransactionListVO ~~~~~~~~~~~~~~~~~",
				transactionListVO);
		//CRQ_QF1186_Tarun_22Dec08
		Page<ULDTransactionDetailsVO>	pagesize = transactionListVO.getTransactionDetailsPage();
		//null check added by a-3278 on 02Dec08
		if(pagesize != null && pagesize.size() > 0){
			if(pagesize.hasNextPage()){				
		listULDTransactionSession.setTotalRecords(pagesize.getTotalRecordCount());
			}		
		}
		//commented for ULD959(error message displaying thrice)
		/*else{
		ErrorVO errorVO = new ErrorVO(
			"uld.defaults.transaction.noenquiriesfound");
		errorVO.setErrorDisplayType(STATUS);
		errors.add(errorVO);
		invocationContext.addAllError(errors);
		}*/
		//a-3278 ends
	
		//added by a-3045
		Collection<ULDTransactionDetailsVO>	uldTransactionDetailsVO = transactionListVO.getTransactionDetailsPage();
		
		
		if((uldTransactionDetailsVO != null && uldTransactionDetailsVO.size() > 0)){
			for(ULDTransactionDetailsVO vo: uldTransactionDetailsVO){
				if(vo.getMucIataStatus().equals(UPDATED) 
						|| vo.getMucIataStatus().equals(RESENT) 
						|| vo.getMucIataStatus().equals(SENT)){
					
					vo.setMucIataStatus(STR_GENERATED);
					log.log(Log.FINE,
							"vo.getMucIataStatus() ~~~~~~~~~~~~~~~~~", vo.getMucIataStatus());
				}else if(vo.getMucIataStatus().equals(MUCREQUIRED)){
					vo.setMucIataStatus(STR_MUCREQUIRED);
				}else if(vo.getMucIataStatus().equals(NOT_TO_BE_REPORTED)){
					vo.setMucIataStatus(STR_NOT_TO_BE_REPORTED);
					log.log(Log.FINE,
							"vo.getMucIataStatus() ~~~~~~~~~~~~~~~~~", vo.getMucIataStatus());
				}
			}
			
		}
		/*
		 * if (errors != null && errors.size() > 0) {
		 * invocationContext.addAllError(errors); }
		 */
		//added by a-2883
		//for enabling/disabling buttons
		listULDTransactionForm.setListStatus("");
		//changed by a-3045 for bug 20362 starts
		if((transactionListVO.getTransactionDetailsPage()!=null && transactionListVO.getTransactionDetailsPage().size()>0)
				|| (transactionListVO.getAccessoryTransactions()!=null && transactionListVO.getAccessoryTransactions().size()>0)){
			listULDTransactionForm.setListStatus("N");
		}
		//changed by a-3045 for bug 20362 ends
		//a-2883 ends
		
		if ((transactionListVO.getTransactionDetailsPage()== null || transactionListVO
				.getTransactionDetailsPage().size() == 0)
				&& (transactionListVO.getAccessoryTransactions() == null || transactionListVO
						.getAccessoryTransactions().size() == 0)) {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.noenquiriesfound");
			errorVO.setErrorDisplayType(STATUS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
		}
		listULDTransactionSession.setTransactionListVO(transactionListVO);
		listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		//added by a-3278 for Bug 15715 on 11Aug08
		listULDTransactionSession.setTotalDemmurage(0.0);
		listULDTransactionSession.setBaseCurrency("");		
		//a-3278 ends
		listULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		log.log(Log.ALL, "listULDTransactionForm.getMucRefNum()",
				listULDTransactionForm.getMucRefNum());
		if(!("").equalsIgnoreCase(listULDTransactionForm.getMucRefNum()) 
				&& listULDTransactionForm.getMucRefNum() != null){			
			Object[] number = new Object[] { listULDTransactionForm.getMucRefNum() };
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.mucreferencenumber", number);
			errorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			listULDTransactionForm.setMucRefNum("");
		}
		//added by a-3045 for CRQF1142 ends
		invocationContext.target = LIST_SUCCESS;

		log.exiting("ListULDTransactionCommand", "execute");

	}

	/**
	 * 
	 * @param listULDTransactionForm
	 * @param listULDTransactionSession
	 * @param logonAttributes
	 * @return
	 */
	private TransactionFilterVO makeFilter(
			ListULDTransactionForm listULDTransactionForm,
			ListULDTransactionSession listULDTransactionSession,
			LogonAttributes logonAttributes) {
		log.entering("ListULDTransactionCommand", "makeFilter");

		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();

		LocalDate txntodt = null;
		LocalDate txnfromdt = null;
		LocalDate retodt = null;
		LocalDate refromdt = null;
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation())
				
				&& listULDTransactionForm.getTxnStation() != null) {
			txntodt = new LocalDate(listULDTransactionForm.getTxnStation()
					.toUpperCase(), Location.ARP, true);
			txnfromdt = new LocalDate(listULDTransactionForm.getTxnStation()
					.toUpperCase(), Location.ARP, true);
		} else {
			txntodt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
			txnfromdt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation())
				&& listULDTransactionForm.getReturnStation() != null) {
			retodt = new LocalDate(listULDTransactionForm.getReturnStation()
					.toUpperCase(), Location.ARP, true);
			refromdt = new LocalDate(listULDTransactionForm.getReturnStation()
					.toUpperCase(), Location.ARP, true);
		} else {
			retodt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
			refromdt = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
		}

		transactionFilterVO.setCompanyCode(listULDTransactionSession
				.getCompanyCode());
		if (!("").equalsIgnoreCase(listULDTransactionForm.getUldNum())
				&& listULDTransactionForm.getUldNum() != null) {
			transactionFilterVO.setUldNumber(listULDTransactionForm.getUldNum().trim()
					.toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getUldTypeCode())
				&& listULDTransactionForm.getUldTypeCode() != null) {
			transactionFilterVO.setUldTypeCode(listULDTransactionForm
					.getUldTypeCode().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getAccessoryCode())
				&& listULDTransactionForm.getAccessoryCode() != null) {
			transactionFilterVO.setAccessoryCode(listULDTransactionForm
					.getAccessoryCode().trim());
		}
		/* Commented by A-3415 for ICRD-114538
        *if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnType())
				&& listULDTransactionForm.getTxnType() != null) {
			transactionFilterVO.setTransactionType(listULDTransactionForm
					.getTxnType().trim());
		}*/
        transactionFilterVO.setTransactionType(LOAN_TRANSACTION);
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnStatus())
				&& listULDTransactionForm.getTxnStatus() != null) {
			transactionFilterVO.setTransactionStatus(listULDTransactionForm
					.getTxnStatus().trim());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getPartyType())
				&& listULDTransactionForm.getPartyType() != null) {
			transactionFilterVO.setPartyType(listULDTransactionForm
					.getPartyType().trim());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getFromPartyCode())
				&& listULDTransactionForm.getFromPartyCode() != null) {
			transactionFilterVO.setFromPartyCode(listULDTransactionForm
					.getFromPartyCode().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getToPartyCode())
				&& listULDTransactionForm.getToPartyCode() != null) {
			transactionFilterVO.setToPartyCode(listULDTransactionForm
					.getToPartyCode().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnStation())
				&& listULDTransactionForm.getTxnStation() != null) {
			transactionFilterVO
					.setTransactionStationCode(listULDTransactionForm
							.getTxnStation().trim().toUpperCase());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnStation())
				&& listULDTransactionForm.getReturnStation() != null) {
			transactionFilterVO.setReturnedStationCode(listULDTransactionForm
					.getReturnStation().trim().toUpperCase());
		}
		if (Objects.nonNull(listULDTransactionForm.getDesStation()) 
				&& !listULDTransactionForm.getDesStation().isEmpty()) {
			transactionFilterVO
					.setDesStation((listULDTransactionForm
							.getDesStation().trim().toUpperCase()));
		}

		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnFromDate())
				&& listULDTransactionForm.getTxnFromDate() != null) {

			String txnFrmDate = listULDTransactionForm.getTxnFromDate();
			StringBuilder txnFrmDateAndTime = new StringBuilder(txnFrmDate);
			String txnFrmTime = listULDTransactionForm.getTxnFrmTime();
			if (!txnFrmTime.contains(":")) {
				txnFrmTime = txnFrmTime.concat(":00");
			}
			transactionFilterVO.setStrTxnFrmTime(txnFrmTime);
			 txnFrmDateAndTime.append(" ").append(txnFrmTime).append(":00");
					

			transactionFilterVO.setTxnFromDate(txntodt
					.setDateAndTime(txnFrmDateAndTime.toString()));
			
			// transactionFilterVO.setStrTxnFromDate(listULDTransactionForm
			// .getTxnFromDate());
			transactionFilterVO.setStrTxnFromDate(listULDTransactionForm
					.getTxnFromDate());

		}

		if (!("").equalsIgnoreCase(listULDTransactionForm.getTxnToDate())
				&& listULDTransactionForm.getTxnToDate() != null) {

			String txnToDate = listULDTransactionForm.getTxnToDate();
			StringBuilder txnToDateAndTime = new StringBuilder(txnToDate);
			String txnToTime = listULDTransactionForm.getTxnToTime();
			if (!txnToTime.contains(":")) {
				txnToTime = txnToTime.concat(":00");
			}
			transactionFilterVO.setStrTxnToTime(txnToTime);

			txnToDateAndTime.append(" ").append(txnToTime ).append(":00");

			transactionFilterVO.setTxnToDate(txnfromdt
					.setDateAndTime(txnToDateAndTime.toString()));
			transactionFilterVO.setStrTxnToDate(listULDTransactionForm
					.getTxnToDate());
		}
		transactionFilterVO.setLeaseOrReturn(listULDTransactionForm.getLeaseOrReturnFlg()); 
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnFromDate())
				&& listULDTransactionForm.getReturnFromDate() != null) {

			String returnFrmDate = listULDTransactionForm.getReturnFromDate();
			StringBuilder returnFrmDateAndTime = new StringBuilder(returnFrmDate);
			String returnFrmTime = listULDTransactionForm.getReturnFrmTime();
			if (!returnFrmTime.contains(":")) {
				returnFrmTime = returnFrmTime.concat(":00");
			}
			transactionFilterVO.setStrRetFrmTime(returnFrmTime);
			returnFrmDateAndTime.append(" ").append(returnFrmTime).append(":00");
					  

			transactionFilterVO.setReturnFromDate(retodt
					.setDateAndTime(returnFrmDateAndTime.toString()));
			transactionFilterVO.setStrReturnFromDate(listULDTransactionForm
					.getReturnFromDate());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getReturnToDate())
				&& listULDTransactionForm.getReturnToDate() != null) {

			String returnToDate = listULDTransactionForm.getReturnToDate();
			StringBuilder returnToDateAndTime = new StringBuilder(returnToDate);
			String returnToTime = listULDTransactionForm.getReturnToTime();
			if (!returnToTime.contains(":")) {
				returnToTime = returnToTime.concat(":00");
			}
			transactionFilterVO.setStrRetToTime(returnToTime);
			returnToDateAndTime.append(" ").append(returnToTime).append(":00");
					

			transactionFilterVO.setReturnToDate(refromdt
					.setDateAndTime(returnToDateAndTime.toString()));
			transactionFilterVO.setStrReturnToDate(listULDTransactionForm
					.getReturnToDate());
		}
		//added by a-3045 for CR QF1142 starts
		if (!("").equalsIgnoreCase(listULDTransactionForm.getMucStatus())
				&& listULDTransactionForm.getMucStatus() != null) {
			transactionFilterVO
					.setMucStatus(listULDTransactionForm
							.getMucStatus().toUpperCase());
		}
		//added by a-3045 for CR QF1142 ends
		//added by a-3045 for bug 26528 starts,28658		
		if (!("").equalsIgnoreCase(listULDTransactionForm.getControlReceiptNoPrefix())
				&& listULDTransactionForm.getControlReceiptNoPrefix() != null) {
			transactionFilterVO.setPrefixControlReceiptNo(listULDTransactionForm.getControlReceiptNoPrefix());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getControlReceiptNoMid())
				&& listULDTransactionForm.getControlReceiptNoMid() != null) {
			transactionFilterVO.setMidControlReceiptNo(listULDTransactionForm.getControlReceiptNoMid());
		}
		if (!("").equalsIgnoreCase(listULDTransactionForm.getControlReceiptNo())
				&& listULDTransactionForm.getControlReceiptNo() != null) {
			transactionFilterVO.setControlReceiptNo(listULDTransactionForm.getControlReceiptNo());
		}		
		//added by a-3045 for bug 26528 ends
		String toDisplayPage = listULDTransactionForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);	
		transactionFilterVO.setPageNumber(displayPage);
		log.exiting("ListULDTransactionCommand", "makeFilter");
		return transactionFilterVO;
	}

	/*
	 * private Collection<ErrorVO> validateForm( ListULDTransactionForm
	 * listULDTransactionForm) { Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	 * ErrorVO error = null; if (listULDTransactionForm.getTxnFromDate() != null &&
	 * listULDTransactionForm.getTxnFromDate().trim().length() > 0) { if
	 * (listULDTransactionForm.getTxnFrmTime() == null ||
	 * listULDTransactionForm.getTxnFrmTime().trim().length() == 0) { error =
	 * new ErrorVO("uld.defaults.listuldtxn.entertxnfrmtime");
	 * errors.add(error); } }
	 * 
	 * if (listULDTransactionForm.getTxnToDate() != null &&
	 * listULDTransactionForm.getTxnToDate().trim().length() > 0) { if
	 * (listULDTransactionForm.getTxnToTime() == null ||
	 * listULDTransactionForm.getTxnToTime().trim().length() == 0) { error = new
	 * ErrorVO("uld.defaults.listuldtxn.entertxntotime"); errors.add(error); } }
	 * 
	 * if (listULDTransactionForm.getReturnFromDate() != null &&
	 * listULDTransactionForm.getReturnFromDate().trim().length() > 0) { if
	 * (listULDTransactionForm.getReturnFrmTime() == null ||
	 * listULDTransactionForm.getReturnFrmTime().trim() .length() == 0) { error =
	 * new ErrorVO( "uld.defaults.listuldtxn.enterreturnfrmtime");
	 * errors.add(error); } }
	 * 
	 * if (listULDTransactionForm.getReturnToDate() != null &&
	 * listULDTransactionForm.getReturnToDate().trim().length() > 0) { if
	 * (listULDTransactionForm.getReturnToTime() == null ||
	 * listULDTransactionForm.getReturnToTime().trim().length() == 0) { error =
	 * new ErrorVO("uld.defaults.listuldtxn.enterreturntotime");
	 * errors.add(error); } } return errors; }
	 */

    /**
     * 
     * @param companyCode
     * @return
     */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(String companyCode){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * 
	 * @return
	 */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(TXNTYPE_ONETIME);
    	parameterTypes.add(TXNNATURE_ONETIME);
    	parameterTypes.add(PARTYTYPE_ONETIME);
    	parameterTypes.add(ACCESSCODE_ONETIME);
    	parameterTypes.add(TXNSTATUS_ONETIME);
    	parameterTypes.add(MUCSTATUS_ONETIME);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
	
}
