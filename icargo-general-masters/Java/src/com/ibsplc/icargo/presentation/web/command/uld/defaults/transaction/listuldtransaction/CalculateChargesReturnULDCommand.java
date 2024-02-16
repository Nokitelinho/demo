/*
 * CalculateChargesReturnULDCommand.java Created on Feb 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
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
 * 
 * @author a-2046
 * 
 * 
 */
public class CalculateChargesReturnULDCommand extends BaseCommand {

	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAMET = "uld.defaults";
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_IDT = "uld.defaults.loanborrowuld";

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
	private static final String CALCULATE_SUCCESS = "calculate_success";
	
	/**
	 * Target if failure
	 */
	private static final String CALCULATE_FAILURE = "calculate_failure";
	
	private static final String LOAN = "L";
	
	private static final String BORROW = "B";
	
	private static final String AIRLINE = "A";
	
	
	

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("CalculateChargesReturnULDCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;

		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAMET, SCREEN_IDT);
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		//added by a-3278 for QF1015 		
		ULDTransactionDetailsVO uLDTransactionDetailsVO = listULDTransactionSession
				.getULDTransactionDetailsVO();
		//This VO is used to retain the values in screen as previous in case any error occurs....
		ULDTransactionDetailsVO uldTxnVO = new ULDTransactionDetailsVO();
		if (uLDTransactionDetailsVO != null) {
			try {
				BeanHelper.copyProperties(uldTxnVO, uLDTransactionDetailsVO) ;
			} catch (SystemException e) {
				log.log(Log.INFO,"Bean Excepton");
			}
			log.log(Log.FINE,
					"listULDTransactionSession.getULDTransactionDetailsVO()",
					uLDTransactionDetailsVO);
			uLDTransactionDetailsVO
					.setTransactionStationCode(listULDTransactionForm
							.getTxnStation().toUpperCase());
		}
		if(listULDTransactionForm.getReturnStation().toUpperCase()!= null && 
				listULDTransactionForm.getReturnStation().toUpperCase().trim().length()>0){
		uLDTransactionDetailsVO.setReturnStationCode(listULDTransactionForm
				.getReturnStation().toUpperCase());
		}/*else{
			uLDTransactionDetailsVO.setReturnStationCode(logonAttributes.getAirportCode());
		}*/

		String txnDate = listULDTransactionForm.getModTxnDate();
		StringBuilder txnDateAndTime = (txnDate!=null) ? new StringBuilder(txnDate): null;
		String txnTime = listULDTransactionForm.getModTxnTime();
		if (txnTime != null && !"".equals(txnTime)) {
			if (!txnTime.contains(":")) {
				txnTime = txnTime.concat(":00");
			}

			txnDateAndTime.append(" ").append(txnTime).append(":00");
		} else {
			txnDateAndTime.append(" ").append("00:00").append(":00");
			// uLDTransactionDetailsVO.setStrRetDate("");
		}
		LocalDate ldate = new LocalDate(uLDTransactionDetailsVO
				.getTransactionStationCode(), Location.ARP, true);
		if (txnDate != null && !"".equals(txnDate)) {
			uLDTransactionDetailsVO.setTransactionDate(ldate
					.setDateAndTime(txnDateAndTime.toString()));
			uLDTransactionDetailsVO.setStrTxnDate(txnDate);
		} else {
			uLDTransactionDetailsVO.setStrTxnDate("");
		}
		
	
		String retDate = listULDTransactionForm.getModRtnDate();
		StringBuilder dateAndTime = (retDate!=null) ? new StringBuilder(retDate) : null;
		String retTime = listULDTransactionForm.getModRtnTime();
		boolean isReturnDateEmpty = false;
		if (retTime != null && !"".equals(retTime)) {
			if (!retTime.contains(":")) {
				retTime = retTime.concat(":00");
			}
			dateAndTime.append(" ").append(retTime).append(":00");
		} else {
			dateAndTime.append(" ").append("00:00").append(":00");
		}
		LocalDate ldte = null;
		if(uLDTransactionDetailsVO.getReturnStationCode() != null 
				&& uLDTransactionDetailsVO.getReturnStationCode().length() >0){
		 ldte = new LocalDate(uLDTransactionDetailsVO
				.getReturnStationCode(), Location.ARP, true);
		}else{
			ldte = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
		}
		if (retDate != null && !"".equals(retDate)) {
			log.log(Log.FINE, "******************", dateAndTime.toString());
			uLDTransactionDetailsVO.setReturnDate(ldte
					.setDateAndTime(dateAndTime.toString()));
			log
					.log(
							Log.FINE,
							"*******uLDTransactionDetailsVO.getReturnDate()***********",
							uLDTransactionDetailsVO.getReturnDate());
			uLDTransactionDetailsVO.setStrRetDate(retDate);
			 isReturnDateEmpty = false;
		} else {
			uLDTransactionDetailsVO.setStrRetDate(ldte.toDisplayDateOnlyFormat());
		  isReturnDateEmpty = true;
		  uLDTransactionDetailsVO.setReturnDate(ldte);
		}
		

		String demurrage = listULDTransactionForm.getRtndemurrage();
		String waived = listULDTransactionForm.getRtnwaived();
		String taxes = listULDTransactionForm.getRtntaxes();
		String rtnRemarks = listULDTransactionForm.getRtnRemarks();
		String txnRemarks = listULDTransactionForm.getModTxnRemarks();

		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, true);
		LocalDate currdat = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, true);
		currdat.setDate(currentdate.toDisplayDateOnlyFormat());

		int errorFlagTxn = 0;
		int errorFlagCur = 0;
		if (uLDTransactionDetailsVO != null) {
			LocalDate retdat = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);

			retdat.setDate(uLDTransactionDetailsVO.getReturnDate()
					.toDisplayDateOnlyFormat());
			if (retdat.isGreaterThan(currdat)) {
				log.log(Log.FINE, "\n\n\n\n currdat  ", currdat);
				log.log(Log.FINE, "\n\n\n\n retdat  ", retdat);
				errorFlagCur = 1;
			}
			LocalDate txndat = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
			txndat.setDate(uLDTransactionDetailsVO.getTransactionDate()
					.toDisplayDateOnlyFormat());
			if (txndat.isGreaterThan(retdat)) {
				log.log(Log.FINE, "\n\n\n\n txndat  ", txndat);
				log.log(Log.FINE, "\n\n\n\n retdat  ", retdat);
				errorFlagTxn = 1;
			}
			if (errorFlagCur == 0 && errorFlagTxn == 0) {
				uLDTransactionDetailsVO
						.setTransationPeriod(calculateLoanPeriod(
								uLDTransactionDetailsVO,
								listULDTransactionSession));
				uLDTransactionDetailsVO
						.setNumberMonths(calculateMonthDifference(
								uLDTransactionDetailsVO,
								listULDTransactionSession));
			}
			if (waived == null || waived.length() == 0) {
				waived = "0.0";
			}
			if (taxes == null || taxes.length() == 0) {
				taxes = "0.0";
			}
			// Added by a-3278 for CR QF1015 0n 08July08
			if (demurrage == null || demurrage.length() == 0) {
				demurrage = "0.0";
			}
			uLDTransactionDetailsVO.setDemurrageAmount(Double
					.parseDouble(demurrage));
			// a-3278 ends

			// Addition ends
			uLDTransactionDetailsVO.setUldType(uLDTransactionDetailsVO
					.getUldNumber().substring(0, 3));
			uLDTransactionDetailsVO.setWaived(Double.parseDouble(waived));
			uLDTransactionDetailsVO.setTaxes(Double.parseDouble(taxes));

		}
		uLDTransactionDetailsVO.setTransactionRemark(txnRemarks);
		uLDTransactionDetailsVO.setReturnRemark(rtnRemarks);
		/**
		 * to validate txn station
		 */
		int stationFlag = 0;
		if(uLDTransactionDetailsVO.getReturnStationCode() != null && uLDTransactionDetailsVO.getReturnStationCode().length() >0){
		String retStation = uLDTransactionDetailsVO.getReturnStationCode();		
		try {
			new AreaDelegate().validateAirportCode(logonAttributes
					.getCompanyCode(), retStation);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			stationFlag = 1;
		}
		}
		if (errorFlagCur != 0 || errorFlagTxn != 0 || stationFlag == 1) {
			if (errorFlagCur == 1) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.loanborrowreturn.returndategreaterthancurrentdate");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				uLDTransactionDetailsVO.setReturnDate(uldTxnVO.getReturnDate());
				uLDTransactionDetailsVO.setStrRetDate(uldTxnVO.getStrRetDate());
				errors.add(error);
			}
			if (errorFlagTxn == 1) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.loanborrowreturn.txndategreaterthanreturndate");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				uLDTransactionDetailsVO.setTransactionDate(uldTxnVO.getTransactionDate());
				uLDTransactionDetailsVO.setStrTxnDate(uldTxnVO.getStrTxnDate());
				errors.add(error);
			}
			invocationContext.addAllError(errors);
			listULDTransactionForm.setCloseFlag("");
			invocationContext.target = CALCULATE_FAILURE;
			return;
		}
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		uldTxnDetailsVOs.add(uLDTransactionDetailsVO);
		Collection<ULDTransactionDetailsVO> newULDTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
		log.log(Log.FINE, " \n uldTxnDetailsVOs ", uldTxnDetailsVOs);
		try {
			newULDTxnDetailsVOs = uldDefaultsDelegate
						.calculateULDDemmurage(uldTxnDetailsVOs);
			/*newULDTxnDetailsVOs = uldDefaultsDelegate
					.calculateReturnULDCharges(uldTxnDetailsVOs);*/
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			log.log(Log.FINE, " \n errors occured ", uldTxnVO);
			listULDTransactionSession
					.setULDTransactionDetailsVO(uldTxnVO);
			invocationContext.target = CALCULATE_FAILURE;
			return;
		} else {
			for (ULDTransactionDetailsVO uldTxnDetailsVO : newULDTxnDetailsVOs) {
				if(isReturnDateEmpty){
					//added by a-3278 for bug 26527 on 21Nov08 
					if("Y".equals(listULDTransactionForm.getDummy())){
						/*LocalDate sysDate = new LocalDate(logonAttributes.getAirportCode(),
								Location.ARP, true);*/
					uldTxnDetailsVO.setReturnDate(ldte);
					uldTxnDetailsVO.setStrRetDate(ldte
							.toDisplayFormat(CALENDAR_DATE_FORMAT));
					uldTxnDetailsVO.setStrRetTime("00:01");
					listULDTransactionForm.setDummy("Y");
					//a-3278 ends
					}else{
					uldTxnDetailsVO.setReturnDate(null);
					uldTxnDetailsVO.setStrRetDate("");
					listULDTransactionForm.setDummy("");
					}					
				}
				listULDTransactionSession
						.setULDTransactionDetailsVO(uldTxnDetailsVO);
			}
		}
		
		/* Added by Preet for CRN Number genaration
		 In case of Loan -- the prefix will be generated , rest of the number remains the same 
		 in borrow case full number will be auto genarated */		
		
		uLDTransactionDetailsVO = listULDTransactionSession.getULDTransactionDetailsVO();
		String crn = uLDTransactionDetailsVO.getControlReceiptNumber();		
		
		AirlineValidationVO toOwnerVO = null;		
		String toPrtyCode = uLDTransactionDetailsVO.getToPartyCode();
		String airlineID = null;
		
		//if-else check for modFlag added by a-3278 for bug ULD823 on 15Dec08
		if("changeRtnDate".equals(listULDTransactionForm.getModFlag()) && "T".equals(uLDTransactionDetailsVO.getTransactionStatus())){	
		if ( (LOAN.equals(uLDTransactionDetailsVO.getTransactionType()) && (AIRLINE.equals(uLDTransactionDetailsVO.getPartyType())))){	
			log.log(Log.INFO, "LOANED TO AIRLINE CASE");
			if (toPrtyCode != null && !("".equals(toPrtyCode))) {
				try {
					toOwnerVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(), toPrtyCode
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
			}
			if (toOwnerVO != null) {
				airlineID = new StringBuilder(toOwnerVO.getNumericCode()).append("-0").toString();							
					if (crn != null && crn.length() > 5) {						
						String controlRecieptNumber = new StringBuilder(airlineID).append(crn.substring(5, crn.length())).toString();
						/*
						 * modified by a-3278 for 28897 on 06Jan09
						 * a new CRN is maintained to save the latest and the old CRN seperately
						 * in case of return the crn is updated in the returnCRN field
						 */
						//uLDTransactionDetailsVO.setControlReceiptNumber(controlRecieptNumber);
						uLDTransactionDetailsVO.setReturnCRN(controlRecieptNumber);
						log
								.log(
										Log.INFO,
										"LOANED TO AIRLINE CASE controlRecieptNumber------ ",
										controlRecieptNumber);
					} else{						
						String controlRecieptNumber = new StringBuilder(airlineID).toString();
						/*
						 * modified by a-3278 for 28897 on 06Jan09
						 * a new CRN is maintained to save the latest and the old CRN seperately
						 * in case of return the crn is updated in the returnCRN field
						 */
						//uLDTransactionDetailsVO.setControlReceiptNumber(controlRecieptNumber);
						uLDTransactionDetailsVO.setReturnCRN(controlRecieptNumber);
					}
				}
				
					
		}else if(BORROW.equals(uLDTransactionDetailsVO.getTransactionType())){	
			String controlRecieptNumber = null;		
			try {
				controlRecieptNumber = new ULDDefaultsDelegate()
						.findCRNForULDTransaction(logonAttributes
								.getCompanyCode(),logonAttributes.getOwnAirlineCode());
				if (controlRecieptNumber != null
						&& controlRecieptNumber.length() > 0) {

					String number = controlRecieptNumber.substring(
							controlRecieptNumber.length() - 7,
							controlRecieptNumber.length());
					String airlineIdentifier = new StringBuilder(controlRecieptNumber.substring(0,4)).append("0").toString();
					/*
					 * modified by a-3278 for 28897 on 06Jan09
					 * a new CRN is maintained to save the latest and the old CRN seperately
					 * in case of return the crn is updated in the returnCRN field
					 */
					//uLDTransactionDetailsVO.setControlReceiptNumber(controlRecieptNumber);
					uLDTransactionDetailsVO.setReturnCRN(new StringBuilder(airlineIdentifier).append(number).toString());
				}

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
		}
		else if (ULDTransactionDetailsVO.AGENT.equals(uLDTransactionDetailsVO.getPartyType()) || ULDTransactionDetailsVO.OTHERS.equals(uLDTransactionDetailsVO.getPartyType())){
							
				//String controlRecieptNumber = new StringBuilder(crn.substring(0, 4)).append(crn.substring(4, crn.length())).toString();
				/*
				 * modified by a-3278 for 28897 on 06Jan09
				 * a new CRN is maintained to save the latest and the old CRN seperately
				 * in case of return the crn is updated in the returnCRN field
				 */
				//uLDTransactionDetailsVO.setControlReceiptNumber(crn);	
				uLDTransactionDetailsVO.setReturnCRN(crn);			
			 
		}		
		listULDTransactionForm.setModFlag("");
		}else{			
			listULDTransactionForm.setModFlag("");
		}
		
		listULDTransactionSession
		.setULDTransactionDetailsVO(uLDTransactionDetailsVO);
		
		 /*Added by a-3278 for ULD823 on 15Dec08
		  * Modified CRN is set to the listULDTransation Session but For saving it is taking frm loanBorrowULdsession
		  * Hence the value is set frm listULDtransactionSession to loanborrowULDSession
		  * */
		int currentPage = Integer.parseInt(listULDTransactionForm
				.getModCurrentPage());

		ArrayList<ULDTransactionDetailsVO> uldTxnDetailsVOsToSet = new ArrayList<ULDTransactionDetailsVO>(
				loanBorrowULDSession.getTransactionVO()
						.getUldTransactionDetailsVOs());
		/*
		 * modified by a-3278 for 28897 on 06Jan09
		 * a new CRN is maintained to save the latest and the old CRN seperately
		 * in case of return the crn is updated in the returnCRN field
		 */
		/*uldTxnDetailsVOsToSet.get(currentPage - 1).setControlReceiptNumber(
				uLDTransactionDetailsVO.getControlReceiptNumber());*/
		uldTxnDetailsVOsToSet.get(currentPage - 1).setReturnCRN(
				uLDTransactionDetailsVO.getReturnCRN());		
		loanBorrowULDSession.getTransactionVO().setUldTransactionDetailsVOs(
				uldTxnDetailsVOsToSet);
		//Added by a-3278 for ULD823 on 15Dec08 ends
		
		
		log
				.log(
						Log.FINE,
						" \n listULDTransactionSession.getULDTransactionDetailsVO() -----------",
						listULDTransactionSession
								.getULDTransactionDetailsVO());
		listULDTransactionForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = CALCULATE_SUCCESS;

		log.exiting("CalculateChargesReturnULDCommand", "execute");

	}
	//added by a-3278 for QF1015 ends	

	/**
	 * 
	 * @param listVO
	 * @param detailsvo
	 * @param listULDTransactionSession
	 * @return
	 */
	private String calculateLoanPeriod(ULDTransactionDetailsVO detailsvo,
			ListULDTransactionSession listULDTransactionSession) {

		final int seconds = 3600;
		final int hours = 24;
		final int millis = 1000 * seconds * hours;
		final int days = 365;
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();		
		LocalDate transdate = new LocalDate(detailsvo
				.getTransactionStationCode(), Location.ARP, false);
		transdate.setDate(detailsvo.getStrTxnDate());
		LocalDate returnDate = null;
		if(detailsvo.getReturnStationCode() != null && detailsvo.getReturnStationCode().length() >0){
		 returnDate = new LocalDate(detailsvo.getReturnStationCode(),
				Location.ARP, false);
		}else{
			returnDate = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, false);
		}
		returnDate.setDate(detailsvo.getStrRetDate());

		/** Find Out Day Difference Start * */
		long tDate = transdate.getTimeInMillis();
		long rDate = returnDate.getTimeInMillis();
		long diff = rDate - tDate;
		long dayDiff = (diff / millis) + 1;
		/** Find Out Day Difference End * */
		String loanPrd = String.valueOf(dayDiff);
		/** Find Out Month Difference Start * */
		int monthDiff = (int) (dayDiff / days) * 12;
		LocalDate temp = transdate.addMonths(monthDiff);
		long tDiff = 0;
		while (temp.isLesserThan(returnDate)) {
			temp.addMonths(1);
			tDiff += 1;
		}
		if (transdate.get(Calendar.DAY_OF_MONTH) >= returnDate
				.get(Calendar.DAY_OF_MONTH)) {
			tDiff += 1;
		}
		monthDiff += tDiff;
		/** Find Out Month Difference End * */

		return loanPrd;
	}

	/**
	 * 
	 * @param listVO
	 * @param detailsvo
	 * @param listULDTransactionSession
	 * @return
	 */
	private int calculateMonthDifference(ULDTransactionDetailsVO detailsvo,
			ListULDTransactionSession listULDTransactionSession) {

		final int seconds = 3600;
		final int hours = 24;
		final int millis = 1000 * seconds * hours;
		final int days = 365;
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();	
		LocalDate transdate = new LocalDate(detailsvo
				.getTransactionStationCode(), Location.ARP, false);
		transdate.setDate(detailsvo.getStrTxnDate());
		LocalDate returnDate = null;
		if(detailsvo.getReturnStationCode() != null && detailsvo.getReturnStationCode().length() >0){
			 returnDate = new LocalDate(detailsvo.getReturnStationCode(),
					Location.ARP, false);
			}else{
				returnDate = new LocalDate(logonAttributes.getAirportCode(),
						Location.ARP, false);
			}
		returnDate.setDate(detailsvo.getStrRetDate());

		/** Find Out Day Difference Start * */
		long tDate = transdate.getTimeInMillis();
		long rDate = returnDate.getTimeInMillis();
		long diff = rDate - tDate;
		long dayDiff = (diff / millis);

		/** Find Out Day Difference End * */

		/** Find Out Month Difference Start * */
		int monthDiff = (int) (dayDiff / days) * 12;
		LocalDate temp = transdate.addMonths(monthDiff);
		long tDiff = 0;
		while (temp.isLesserThan(returnDate)) {
			temp.addMonths(1);
			tDiff += 1;
		}
		if (transdate.get(Calendar.DAY_OF_MONTH) >= returnDate
				.get(Calendar.DAY_OF_MONTH)) {
			tDiff += 1;
		}
		monthDiff += tDiff;

		/** Find Out Month Difference End * */

		return monthDiff;
	}

}
