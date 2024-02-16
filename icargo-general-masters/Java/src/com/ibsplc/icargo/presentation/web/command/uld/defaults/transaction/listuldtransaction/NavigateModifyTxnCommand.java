/*
 * NavigateModifyTxnCommand.java Created on Oct 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * 
 */
public class NavigateModifyTxnCommand extends BaseCommand {

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
	 * The Module Name
	 */
	private static final String MODULE_NAMET = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_IDT = "uld.defaults.loanborrowuld";

	/**
	 * Target if success
	 */
	private static final String UPDATE_SUCCESS = "update_success";

	private static final String UPDATE_FAILURE = "update_failure";

	private static final String BLANK = "";
	
	private static final String TO_BE_INVOICED ="R";
	
	private static final String TO_BE_RETURNED ="T";
	
	private static final String PTYTYP_AGT ="G";
	
	private static final String PTYTYP_ARL ="A";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("UpdateListULDTransactionCommand", "execute");
		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAMET, SCREEN_IDT);

		// Added for updation starts
		int displayPage = Integer.parseInt(listULDTransactionForm
				.getModDisplayPage());
		log.log(Log.INFO, "Display Page in nanigate", listULDTransactionForm.getModDisplayPage());
		int currentPage = Integer.parseInt(listULDTransactionForm
				.getModCurrentPage());
		log.log(Log.INFO, "listULDTransactionForm.getModCurrentPage()",
				listULDTransactionForm.getModCurrentPage());
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ArrayList<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>(
				loanBorrowULDSession.getTransactionVO()
						.getUldTransactionDetailsVOs());
		// this is to obtain the VO which was displayed....
		//to be upadted with the form values
		ULDTransactionDetailsVO uLDTxnDetailsVO = uldTxnDetailsVOs
				.get(currentPage - 1);
	// Updating the VO in session with the form
		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getModDuration())
				&& listULDTransactionForm.getModULDNo() != null) {
			uLDTxnDetailsVO.setTransationPeriod(listULDTransactionForm
					.getModDuration());
		} else {
			uLDTxnDetailsVO.setTransationPeriod("0");
		}
		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getTxnStation())
				&& listULDTransactionForm.getTxnStation() != null) {
			if (validateAirportCode(listULDTransactionForm,
					getApplicationSession().getLogonVO().getCompanyCode(),
					listULDTransactionForm.getTxnStation()) == null) {
				uLDTxnDetailsVO
						.setTransactionStationCode(listULDTransactionForm
								.getTxnStation().toUpperCase());
			} else {
				uLDTxnDetailsVO
						.setTransactionStationCode(listULDTransactionForm
								.getTxnStation().toUpperCase());
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.modify.txnstation.invalid");
				errorVO.setErrorDisplayType(ERROR);
				listULDTransactionForm.setModDisplayPage(listULDTransactionForm
						.getModCurrentPage());
				invocationContext.target = UPDATE_FAILURE;
				invocationContext.addError(errorVO);
				return;
			}

		}
		LocalDate ldte = new LocalDate(uLDTxnDetailsVO
				.getTransactionStationCode(), Location.ARP, true);

		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getModTxnDate())
				&& listULDTransactionForm.getModTxnDate() != null) {

			if (!(BLANK).equalsIgnoreCase(listULDTransactionForm
					.getModTxnTime())
					&& listULDTransactionForm.getModTxnTime() != null) {
				StringBuilder txndat = new StringBuilder();
				txndat.append(listULDTransactionForm.getModTxnDate()).append(
						" ").append(listULDTransactionForm.getModTxnTime())
						.append(":00");
				uLDTxnDetailsVO.setTransactionDate(ldte.setDateAndTime(txndat
						.toString()));
			} else {
				uLDTxnDetailsVO.setTransactionDate(ldte
						.setDate(listULDTransactionForm.getModTxnDate()));
			}

			uLDTxnDetailsVO.setStrTxnDate(listULDTransactionForm
					.getModTxnDate());
			uLDTxnDetailsVO.setStrTxnTime(listULDTransactionForm
					.getModTxnTime());
		} else {
			uLDTxnDetailsVO.setStrTxnDate(BLANK);
		}

		if (!(BLANK)
				.equalsIgnoreCase(listULDTransactionForm.getModTxnRemarks())
				&& listULDTransactionForm.getModTxnRemarks() != null) {
			uLDTxnDetailsVO.setTransactionRemark(listULDTransactionForm
					.getModTxnRemarks());
		}

		boolean isCrnChanged = false;
		if (listULDTransactionForm.getModCRN() == null
				|| listULDTransactionForm.getModCRN().length() == 0) {

			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.modify.crn.mandatory");
			errorVO.setErrorDisplayType(ERROR);
			invocationContext.target = UPDATE_FAILURE;
			listULDTransactionForm.setModDisplayPage(listULDTransactionForm
					.getModCurrentPage());
			listULDTransactionSession
					.setCtrlRcptNoPrefix(listULDTransactionForm
							.getModCrnPrefix());
			listULDTransactionSession.setCtrlRcptNo(listULDTransactionForm
					.getModCRN());
			invocationContext.addError(errorVO);
			return;
		}

		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getModCRN())
				&& listULDTransactionForm.getModCRN() != null) {
			String crnPrefix = listULDTransactionForm.getModCrnPrefix();
			String crn = listULDTransactionForm.getModCRN();
			String crnToSet = new StringBuffer(crnPrefix).append(crn)
					.toString();
			if (crn.equals(listULDTransactionSession.getCtrlRcptNo())) {
				isCrnChanged = false;
			} else {
				isCrnChanged = true;
				uLDTxnDetailsVO.setControlReceiptNumber(crnToSet);
			}

		}

		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm
				.getModUldCondition())
				&& listULDTransactionForm.getModUldCondition() != null) {
			uLDTxnDetailsVO.setUldConditionCode((listULDTransactionForm
					.getModUldCondition()));
		}
		if (listULDTransactionForm.getModAwbNumber() != null
				&& listULDTransactionForm.getModAwbNumber().trim().length() > 0) {
			log.log(Log.INFO, "listULDTransactionForm.getModAwbNumber()-->",
					listULDTransactionForm.getModAwbNumber());
			uLDTxnDetailsVO.setAwbNumber(listULDTransactionForm
					.getModAwbNumber().trim());
		}
		if ("Y".equals(listULDTransactionForm.getModLoaded())) {
			uLDTxnDetailsVO.setEmptyStatus("N");
		} else {
			uLDTxnDetailsVO.setEmptyStatus("Y");
		}

		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getDesStation())
				&& listULDTransactionForm.getDesStation() != null) {
			if (validateAirportCode(listULDTransactionForm,
					getApplicationSession().getLogonVO().getCompanyCode(),
					listULDTransactionForm.getDesStation()) == null) {
				uLDTxnDetailsVO.setTxStationCode(listULDTransactionForm
						.getDesStation().toUpperCase());
			} else {
				uLDTxnDetailsVO.setTxStationCode(listULDTransactionForm
						.getDesStation().toUpperCase());
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.modify.desstation.invalid");
				errorVO.setErrorDisplayType(ERROR);
				listULDTransactionForm.setModDisplayPage(listULDTransactionForm
						.getModCurrentPage());
				invocationContext.target = UPDATE_FAILURE;
				invocationContext.addError(errorVO);
				return;
			}
		}
		if (!(BLANK)
				.equalsIgnoreCase(listULDTransactionForm.getReturnStation())
				&& listULDTransactionForm.getReturnStation() != null) {
			if (validateAirportCode(listULDTransactionForm,
					getApplicationSession().getLogonVO().getCompanyCode(),
					listULDTransactionForm.getReturnStation()) == null) {
				uLDTxnDetailsVO.setReturnStationCode(listULDTransactionForm
						.getReturnStation().toUpperCase());
			} else {
				uLDTxnDetailsVO.setReturnStationCode(listULDTransactionForm
						.getReturnStation().toUpperCase());
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.transaction.modify.rtnstation.invalid");
				listULDTransactionForm.setModDisplayPage(listULDTransactionForm
						.getModCurrentPage());
				errorVO.setErrorDisplayType(ERROR);
				invocationContext.target = UPDATE_FAILURE;
				invocationContext.addError(errorVO);
				return;
			}

		}
		LocalDate ldate = null;
		if(uLDTxnDetailsVO.getReturnStationCode() != null 
				&& uLDTxnDetailsVO.getReturnStationCode().length() >0){
			ldate = new LocalDate(uLDTxnDetailsVO.getReturnStationCode(),
					Location.ARP, true);
		}else{
			ldate = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
		}
		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getModRtnDate())
				&& listULDTransactionForm.getModRtnDate() != null) {
			if (!(BLANK).equalsIgnoreCase(listULDTransactionForm
					.getModRtnTime())
					&& listULDTransactionForm.getModRtnTime() != null) {
				StringBuilder retdat = new StringBuilder();
				retdat.append(listULDTransactionForm.getModRtnDate()).append(
						" ").append(listULDTransactionForm.getModRtnTime())
						.append(":00");
				uLDTxnDetailsVO.setReturnDate(ldate.setDateAndTime(retdat
						.toString()));
			} else {
				uLDTxnDetailsVO.setReturnDate(ldate
						.setDate(listULDTransactionForm.getModRtnDate()));
			}
			uLDTxnDetailsVO.setStrRetDate(listULDTransactionForm
					.getModRtnDate());
			uLDTxnDetailsVO.setStrRetTime(listULDTransactionForm
					.getModRtnTime());
		} else {
			uLDTxnDetailsVO.setStrRetDate("");
			uLDTxnDetailsVO.setReturnDate(null);
		}

		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getRtnRemarks())
				&& listULDTransactionForm.getRtnRemarks() != null) {
			uLDTxnDetailsVO.setReturnRemark(listULDTransactionForm
					.getRtnRemarks());
		}
		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getRtndemurrage())
				&& listULDTransactionForm.getRtndemurrage() != null) {
			uLDTxnDetailsVO.setDemurrageAmount(Double
					.parseDouble(listULDTransactionForm.getRtndemurrage()));
		}
		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getRtntaxes())
				&& listULDTransactionForm.getRtntaxes() != null) {
			uLDTxnDetailsVO.setTaxes(Double.parseDouble(listULDTransactionForm
					.getRtntaxes()));
		}
		if (!(BLANK).equalsIgnoreCase(listULDTransactionForm.getRtnwaived())
				&& listULDTransactionForm.getRtnwaived() != null) {
			uLDTxnDetailsVO.setWaived(Double.parseDouble(listULDTransactionForm
					.getRtnwaived()));
		}
		log.log(Log.FINE, "uLDTxnDetailsVO***final", uLDTxnDetailsVO);
		TransactionVO transactionVO = loanBorrowULDSession.getTransactionVO();
		int errorFlag = 0;
		LocalDate currentdate = new LocalDate(uLDTxnDetailsVO
				.getTransactionStationCode(), Location.ARP, true);

		if (uLDTxnDetailsVO.getTransactionDate().isGreaterThan(currentdate)) {
			errorFlag = 1;
		}
		if (uLDTxnDetailsVO.getReturnDate() != null) {
			if (uLDTxnDetailsVO.getTransactionDate().isGreaterThan(
					uLDTxnDetailsVO.getReturnDate())) {
				errorFlag = 2;
			}
		}

		if (TO_BE_INVOICED.equals(uLDTxnDetailsVO.getTransactionStatus())
				&& uLDTxnDetailsVO.getReturnDate() == null) {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.transaction.modify.rtndate.mandatory");
			errorVO.setErrorDisplayType(ERROR);
			invocationContext.target = UPDATE_FAILURE;
			uLDTxnDetailsVO.setStrRetDate(listULDTransactionForm
					.getModRtnDate());
			log.log(Log.INFO, "current page no:", listULDTransactionForm.getModCurrentPage());
			listULDTransactionForm.setModDisplayPage(listULDTransactionForm
					.getModCurrentPage());
			invocationContext.addError(errorVO);
			return;
		}

		if (errorFlag == 1) {
			ErrorVO error = new ErrorVO(
					"uld.defaults.loanborrowmodify.txndategreaterthancurrentdate");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);

			invocationContext.addError(error);
			listULDTransactionForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			listULDTransactionForm.setModDisplayPage(listULDTransactionForm
					.getModCurrentPage());
			invocationContext.target = UPDATE_FAILURE;
			return;
		}
		if (errorFlag == 2) {
			ErrorVO error = new ErrorVO(
					"uld.defaults.loanborrowreturn.txndategreaterthanreturndate");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);

			invocationContext.addError(error);
			listULDTransactionForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			log.log(Log.INFO, "Current Page No", listULDTransactionForm.getModCurrentPage());
			listULDTransactionForm.setModDisplayPage(listULDTransactionForm
					.getModCurrentPage());
			invocationContext.target = UPDATE_FAILURE;
			return;
		}

		transactionVO.setOperationalFlag("U");
		/*
		 * for checking if duplicate CRN number exists for the current vo shown
		 */

		TransactionVO transactionVOCRN = new TransactionVO();
		try {
			BeanHelper.copyProperties(transactionVOCRN, transactionVO);
		} catch (SystemException e) {
			log.log(Log.INFO, "Bean Excepton");
		}
		ArrayList<ULDTransactionDetailsVO> crntxns = new ArrayList<ULDTransactionDetailsVO>();
		crntxns.add(uLDTxnDetailsVO);
		transactionVOCRN.setUldTransactionDetailsVOs(crntxns);
		if (isCrnChanged) {
			Collection<String> crnNums = null;
			try {
				String companyCode = logonAttributes.getCompanyCode();
				crnNums = new ULDDefaultsDelegate().checkForDuplicateCRN(
						companyCode, transactionVOCRN);
				log.log(Log.FINE, "crnNums returned------------", crnNums);
			} catch (BusinessDelegateException ex) {
				handleDelegateException(ex);
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
				invocationContext.addError(error);
				listULDTransactionForm.setModDisplayPage(listULDTransactionForm
						.getModCurrentPage());
				listULDTransactionSession
						.setCtrlRcptNoPrefix(listULDTransactionForm
								.getModCrnPrefix());
				listULDTransactionSession.setCtrlRcptNo(listULDTransactionForm
						.getModCRN());
				invocationContext.target = UPDATE_FAILURE;
				return;
			}
		}
		boolean prefixFlag = false;
		for (ULDTransactionDetailsVO transactionDetailsVO : transactionVOCRN
				.getUldTransactionDetailsVOs()) {
			/*String crnNum = transactionDetailsVO.getControlReceiptNumber();
			String numPart = crnNum.substring(4, 5);
			log.log(Log.INFO, "NUMBER PART IS ----------->>" + numPart);
			try {
				int num = Integer.parseInt(String.valueOf(numPart));
				if (num > 2) {
					prefixFlag = true;
				}
			} catch (NumberFormatException e) {
				prefixFlag = true;
				log.log(Log.INFO, "NumberFormatexception caught");
				break;
			}*/
			
			//Added for bug 104125 bt A-2521. Implemented as LoanBorrow Entry screen
			 prefixFlag = validateCRNFormat (transactionDetailsVO);
			 
			 if(prefixFlag) {
				break;
			}
		}
		if (prefixFlag) {
			ErrorVO error = new ErrorVO(
					"uld.defaults.loanBorrowULD.msg.err.incorrectCRNprefix");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			listULDTransactionSession
					.setCtrlRcptNoPrefix(listULDTransactionForm
							.getModCrnPrefix());
			listULDTransactionSession.setCtrlRcptNo(listULDTransactionForm
					.getModCRN());
			listULDTransactionForm.setModDisplayPage(listULDTransactionForm
					.getModCurrentPage());
			invocationContext.target = UPDATE_FAILURE;
			return;
		}

		boolean returnFlag = false;
		if (TO_BE_RETURNED.equals(uLDTxnDetailsVO.getTransactionStatus())
				&& uLDTxnDetailsVO.getReturnDate() == null
				|| TO_BE_INVOICED.equals(uLDTxnDetailsVO.getTransactionStatus())
				&& uLDTxnDetailsVO.getReturnDate() != null) {
			if (TO_BE_RETURNED.equals(uLDTxnDetailsVO.getTransactionStatus())) {

				uLDTxnDetailsVO.setDemurrageAmount(0.0);
				uLDTxnDetailsVO.setDemurrageRate(0.0);
				uLDTxnDetailsVO.setWaived(0.0);
				uLDTxnDetailsVO.setTaxes(0.0);
				uLDTxnDetailsVO.setTotal(0.0);

			} else {
				// for(ULDTransactionDetailsVO vo:uldTxnDtlsVOs){
				uLDTxnDetailsVO.setTotal((uLDTxnDetailsVO.getDemurrageAmount()
						+ uLDTxnDetailsVO.getTaxes() + uLDTxnDetailsVO
						.getOtherCharges())
						- uLDTxnDetailsVO.getWaived());
				// }
			}
			// VO set for updations...
			returnFlag = false;
			uLDTxnDetailsVO.setReturn(returnFlag);

		}

		if (TO_BE_RETURNED.equals(uLDTxnDetailsVO.getTransactionStatus())
				&& uLDTxnDetailsVO.getReturnDate() != null) {
			uLDTxnDetailsVO
					.setTransactionStatus(ULDTransactionDetailsVO.TO_BE_INVOICED);
			uLDTxnDetailsVO.setTotal((uLDTxnDetailsVO.getDemurrageAmount()
					+ uLDTxnDetailsVO.getTaxes() + uLDTxnDetailsVO
					.getOtherCharges())
					- uLDTxnDetailsVO.getWaived());
			uLDTxnDetailsVO.setReturnedBy(uLDTxnDetailsVO.getToPartyCode());
			uLDTxnDetailsVO.setCurrOwnerCode(uLDTxnDetailsVO
					.getFromPartyIdentifier());
			returnFlag = true;
			uLDTxnDetailsVO.setReturn(returnFlag);

		}

		log.log(Log.INFO, "listULDTransactionSession.getTransactionVO",
				loanBorrowULDSession.getTransactionVO());
		//Updating the previous VO in session ends here
		//Fetching the next record form session and setting in session using displap page
		ULDTransactionDetailsVO modTxnVO = new ULDTransactionDetailsVO();
		if (loanBorrowULDSession.getTransactionVO() != null
				&& loanBorrowULDSession.getTransactionVO()
						.getUldTransactionDetailsVOs() != null) {

			ArrayList<ULDTransactionDetailsVO> uldTxnDtls = new ArrayList<ULDTransactionDetailsVO>(
					loanBorrowULDSession.getTransactionVO()
							.getUldTransactionDetailsVOs());
			log.log(Log.INFO, "Display Page---->", displayPage);
			try {
				BeanHelper.copyProperties(modTxnVO, uldTxnDtls
						.get(displayPage - 1));
			} catch (SystemException e) {
				log.log(Log.INFO, "Bean Excepton");
			}
		}
		listULDTransactionSession.setULDTransactionDetailsVO(modTxnVO);
		log.log(Log.INFO, "Display Page---->", displayPage);
		listULDTransactionForm.setModCurrentPage(Integer.toString(displayPage));
		log.log(Log.INFO, "Cureent Page no", listULDTransactionForm.getModCurrentPage());
		listULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = UPDATE_SUCCESS;

		log.exiting("UpdateListULDTransactionCommand", "execute");

	}

	/**
	 * @param form
	 * @param companyCode
	 * @param airport
	 * @return
	 */
	private Collection<ErrorVO> validateAirportCode(
			ListULDTransactionForm form, String companyCode, String airport) {
		log.log(Log.INFO, "Airport inside airport validation", airport);
		Collection<ErrorVO> errors = null;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AreaDelegate delegate = new AreaDelegate();
		AirportValidationVO airportValidationVO = null;

		try {
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), airport.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			log.log(Log.INFO, "Errors", errors);
		}
		return errors;

	}
	
	/**
	 * Validates the CRN number pefix. 
	 * for agent it can be 0 - 9 and for airline it can as configured 
	 * in system parameter .
	 * @param ULDTransactionDetailsVO
	 * @return boolean
	 */
	private boolean validateCRNFormat(
			ULDTransactionDetailsVO transactionDetailsVO) {

		log.entering("validateCRNFormat", "validateCRNFormat");
		boolean prefixFlag = false;
		int countPerUCR = 3;
		String fromPrtyCode = transactionDetailsVO.getFromPartyCode();
		String crnNum = transactionDetailsVO.getControlReceiptNumber();
		String uldCountPerUCR = "shared.airline.uldCountPerUCR";
		
		if (crnNum == null)
		 {
			return prefixFlag;
		//Commented as part of ICRD-3493 by A-3767 on 04Aug11
		/*if (PTYTYP_AGT.equals(transactionDetailsVO.getPartyType())) {
			countPerUCR = 9;

		} else {*/
		}
			
			try {
				AirlineDelegate airlineDelegate = new AirlineDelegate();
				AirlineValidationVO airlineValidationVO = null;
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						transactionDetailsVO.getCompanyCode(), fromPrtyCode);
				//Commented as part of ICRD-3493 by A-3767 on 04Aug11
				/*Collection<String> parameterCodes = new ArrayList<String>();
				Map<String, String> airlineParameterMap = null;
				parameterCodes.add(uldCountPerUCR);
				airlineParameterMap = airlineDelegate
						.findAirlineParametersByCode(
								transactionDetailsVO.getCompanyCode(), 
								airlineValidationVO.getAirlineIdentifier(), parameterCodes);
				
				if (airlineParameterMap != null
						&& !airlineParameterMap.isEmpty()) {

					String value = airlineParameterMap.get(uldCountPerUCR);
					if (value != null && value.trim().length() > 0) {
						countPerUCR = Integer.parseInt(value) - 1;
					}
				}*/
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
//		}
		
		log.log(Log.INFO, "crnNum ----------->>>", crnNum);
		String numPart = crnNum.substring(4, 5);
		log.log(Log.INFO, "NUMBER PART IS ----------->>", numPart);
		try {
			int num = Integer.parseInt(String.valueOf(numPart));
			if (num > countPerUCR) {
				prefixFlag = true;
			}
		} catch (NumberFormatException e) {
			prefixFlag = true;
			log.log(Log.INFO, "NumberFormatexception caught");
		}

		log.exiting("validateCRNFormat", "validateCRNFormat");
		return prefixFlag;
	}
}
