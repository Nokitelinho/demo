/* UpdateBillingDetailCommand.java Created on Aug-5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureinvoice;
/**
 * @author A-3447
 */
//import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;



/**
 * @author A-3447
 */
/**
 * Command class for Saving billing details
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 22-July-2008 Muralee(a-3447) For CRQ_172
 */
public class UpdateBillingDetailCommand extends BaseCommand {

	// ----------------------------------------------------------------------------------------------------------------------------------------------------

	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	/**
	 * SCREENID
	 * 
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * MODULE
	 * 
	 */
	private static final String MODULE = "mailtracking.mra.airlinebilling";

	/**
	 * CLASS NAME
	 * 
	 */
	private static final String CLASS_NAME = "mailtracking";

	/**
	 * target action
	 */
	private static final String SAVE_SUCCESS = "save_success";

	/**
	 * save failed
	 */

	private static final String SAVE_FAILED = "save_failure";    //Modified as part of bug ICRD-101066 by A-5526

	/**
	 * save success
	 * 
	 */

	private static final String SAVE_SUCCESSFULLY = "mailtracking.mra.airlinebilling.inward.msg.info.savesucessfully";

	/**
	 * invalid currency code
	 */

	private static final String INVALID_CURCODE = "mailtracking.mra.airlinebilling.inward.msg.error.invalidcurrencycode";

	/**
	 * null currency
	 */

	private static final String CURCODE_NULL = "mailtracking.mra.airlinebilling.inward.msg.error.null.currency";

	/**
	 * op flag
	 */

	private static final String INSERT = "I";

	/**
	 * Update Flag
	 */

	private static final String UPDATE = "U";

	/**
	 * Inter line Billinh Type
	 */
	private static final String INTERLINE_BILLING = "I";

	/**
	 * Save button Flag
	 */

	private static final String SAVE = "S";
	
	/**
	 * Blank valus
	 */
	private static final String BLANK = "";
	private static final String PRIME_BILLING = "P";

// --------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		CaptureMailInvoiceForm captureInvoiceForm = (CaptureMailInvoiceForm) invocationContext.screenModel;
		CaptureInvoiceSession captureInvoiceSession = (CaptureInvoiceSession) getScreenSession(
				MODULE, SCREENID);

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		AirlineCN51SummaryVO airlineCN51SummaryVO = new AirlineCN51SummaryVO();
		String invoiceDate = captureInvoiceForm.getInvoiceDate();
		
		log.log(Log.FINE, "ButtonFlag==", captureInvoiceForm.getButtonFlag());
		if ("S".equals(captureInvoiceForm.getButtonFlag())) {
			
			if (captureInvoiceSession.getAirlineCN51SummaryVO() != null) {
				airlineCN51SummaryVO = captureInvoiceSession
						.getAirlineCN51SummaryVO();
				
				airlineCN51SummaryVO.setOperationFlag(UPDATE);
				airlineCN51SummaryVO.setButtonFlag(SAVE);
				log.log(Log.FINE, "not NULL", airlineCN51SummaryVO.getButtonFlag());
				log.log(Log.FINE, "not NULL", airlineCN51SummaryVO);
				if(invoiceDate != null && invoiceDate.trim().length() > 0){
		    		if(DateUtilities.isValidDate(invoiceDate,"dd-MMM-yyyy")) {
							LocalDate invDate = new LocalDate(NO_STATION,NONE,false);
							invDate.setDate(invoiceDate);
							airlineCN51SummaryVO.setBilleddate(invDate);
							log.log(Log.FINE, "date-- update ",
									airlineCN51SummaryVO.getBilleddate());
		    		}
		    	}

			}

			else {
				airlineCN51SummaryVO.setButtonFlag(SAVE);

				log.log(Log.FINE, "NULL---Creating new ");
				airlineCN51SummaryVO.setOperationFlag(INSERT);
				String clearancePeriod = captureInvoiceForm
						.getClearancePeriod().trim().toUpperCase();
				String airlineCode = captureInvoiceForm.getAirlineCode().trim()
						.toUpperCase();
				String invoiceNumber = captureInvoiceForm.getInvoiceRefNo()
						.trim().toUpperCase();
				
				String companyCode = getApplicationSession().getLogonVO()
						.getCompanyCode();
				airlineCN51SummaryVO.setCompanycode(companyCode);
				airlineCN51SummaryVO.setAirlinecode(airlineCode);
				airlineCN51SummaryVO.setClearanceperiod(clearancePeriod);
				airlineCN51SummaryVO.setInvoicenumber(invoiceNumber);
				
				log.log(Log.FINE, "billed date  ---->>", airlineCN51SummaryVO.getBilleddate());
				if(invoiceDate != null && invoiceDate.trim().length() > 0){
		    		if(DateUtilities.isValidDate(invoiceDate,"dd-MMM-yyyy")) {
							LocalDate invDate = new LocalDate(NO_STATION,NONE,false);
							invDate.setDate(invoiceDate);
							airlineCN51SummaryVO.setBilleddate(invDate);
							log.log(Log.FINE, "date---->>>>",
									airlineCN51SummaryVO.getBilleddate());
		    		}
		    	}
				
				/*if (invoiceDate != null && invoiceDate.length() > 0) {
					log.log(Log.FINE, "Date ---->>" + invoiceDate);
					StringBuffer toDate = new StringBuffer(invoiceDate);
					log.log(Log.FINE, "todate---->>" + toDate.toString());
					LocalDate toDateAndTime = new LocalDate(
							getApplicationSession().getLogonVO()
									.getStationCode(), Location.STN, true);
					toDateAndTime.setDate(toDate.toString());
					airlineCN51SummaryVO.setBilleddate(toDateAndTime);
				
				}*/
				if(captureInvoiceSession.getFilterVo()!=null){

				airlineCN51SummaryVO.setAirlineidr(captureInvoiceSession
						.getFilterVo().getAirlineIdentifier());
				}
				airlineCN51SummaryVO.setInterlinebillingtype(INTERLINE_BILLING);
				airlineCN51SummaryVO.setBillingType(PRIME_BILLING); //Added as part of ICRD-265471

			}
			if (captureInvoiceForm.getListingCurrency() == null
					|| captureInvoiceForm.getListingCurrency().trim().length() == 0) {
				log.log(1, "getCurrency not present-....");
				ErrorVO err = new ErrorVO(CURCODE_NULL);
				errors.add(err);
			} else {
				log.log(Log.INFO, "getCurrency  present....",
						captureInvoiceForm.getListingCurrency());
				try {
					new CurrencyDelegate().validateCurrency(
							airlineCN51SummaryVO.getCompanycode(),
							captureInvoiceForm.getListingCurrency().trim()
									.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					log.log(Log.INFO, "\n -------- Vaidation Failed---------");
					captureInvoiceForm.setListingCurrency("");
					ErrorVO error = new ErrorVO(INVALID_CURCODE);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);
					invocationContext.target = SAVE_SUCCESS;
					captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
					//captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
					return;

				}
			}

			if (errors == null || errors.size() == 0) {
				airlineCN51SummaryVO.setListingCurrency(captureInvoiceForm
						.getListingCurrency().toUpperCase());
				airlineCN51SummaryVO.setClearanceperiod(captureInvoiceForm
						.getClearancePeriod());
				airlineCN51SummaryVO.setAirlinecode(captureInvoiceForm
						.getAirlineCode());
				if (captureInvoiceForm.getAirlineNo() != null
						&& captureInvoiceForm.getAirlineNo().trim().length() > 0) {
					log.log(Log.FINE, "captureInvoiceForm.getAirlineNo()",
							captureInvoiceForm.getAirlineNo());

				}
				airlineCN51SummaryVO.setInvoicenumber(captureInvoiceForm
						.getInvoiceRefNo());
			/*	if (captureInvoiceForm.getInvoiceDate() != null
						&& captureInvoiceForm.getInvoiceDate().trim().length() > 0) {
					log.log(Log.FINE, "Date----->>"
							+ captureInvoiceForm.getInvoiceDate());
					StringBuffer toDate = new StringBuffer(captureInvoiceForm
							.getInvoiceDate());
					log.log(Log.FINE, "todate---->>" + toDate.toString());
					LocalDate toDateAndTime = new LocalDate(
							getApplicationSession().getLogonVO()
									.getStationCode(), Location.STN, true);
					toDateAndTime.setDate(toDate.toString());
					airlineCN51SummaryVO.setBilleddate(toDateAndTime);

				}*/
								
				if(invoiceDate != null && invoiceDate.trim().length() > 0){
		    		if(DateUtilities.isValidDate(invoiceDate,"dd-MMM-yyyy")) {
							LocalDate invDate = new LocalDate(NO_STATION,NONE,false);
							invDate.setDate(invoiceDate);
							airlineCN51SummaryVO.setBilleddate(invDate);
							log.log(Log.FINE, "date----222",
									airlineCN51SummaryVO.getBilleddate());
		    		}
		    	}

				try {

					Money amtinUsd = CurrencyHelper.getMoney(captureInvoiceForm
							.getListingCurrency());

					if (captureInvoiceForm.getAmountInusd() != null
							&& captureInvoiceForm.getAmountInusd().trim()
									.length() > 0) {
						amtinUsd.setAmount(Double
								.parseDouble(captureInvoiceForm
										.getAmountInusd()));

					}
					log.log(Log.FINE, "captureInvoiceForm.getAmountInUSD())",
							captureInvoiceForm.getAmountInusd());
					airlineCN51SummaryVO.setAmountInusd(amtinUsd);

					Money exchangeRate = CurrencyHelper
							.getMoney(captureInvoiceForm.getListingCurrency());
					if (captureInvoiceForm.getExgRate()!= null
							&& captureInvoiceForm.getExgRate().trim()
									.length() > 0) {
						exchangeRate.setAmount(Double
								.parseDouble(captureInvoiceForm
										.getExgRate()));
						log.log(Log.INFO, "Exch Rate", exchangeRate);
					}
					airlineCN51SummaryVO.setExchangeRate(exchangeRate);

					Money netAmount = CurrencyHelper
							.getMoney(captureInvoiceForm.getListingCurrency());
					if (captureInvoiceForm.getNetAmount() != null
							&& captureInvoiceForm.getNetAmount().trim()
									.length() > 0) {
						netAmount
								.setAmount(Double
										.parseDouble(captureInvoiceForm
												.getNetAmount()));
					}
					airlineCN51SummaryVO.setNetAmount(netAmount);

				} catch (CurrencyException currencyException) {
					log.log(Log.FINE, "currency Exception--");

				}

				if (captureInvoiceForm.getInvoiceReceiptDate() != null
						&& captureInvoiceForm.getInvoiceReceiptDate().trim()
								.length() > 0) {
					log.log(Log.FINE, "InvoiceReceipt ---->>",
							captureInvoiceForm.getInvoiceDate());
					StringBuffer toDate = new StringBuffer(captureInvoiceForm
							.getInvoiceReceiptDate());
					log.log(Log.FINE, "todate---->>", toDate.toString());
					LocalDate toDateAndTime = new LocalDate(
							getApplicationSession().getLogonVO()
									.getStationCode(), Location.STN, true);
					toDateAndTime.setDate(toDate.toString());
					airlineCN51SummaryVO.setInvRcvdate(toDateAndTime);

				}
				if (captureInvoiceForm.getTotalWeight() != null
						&& captureInvoiceForm.getTotalWeight().trim().length() > 0) {

					airlineCN51SummaryVO.setTotWt(Double
							.parseDouble(captureInvoiceForm.getTotalWeight()));

				}
				if (airlineCN51SummaryVO.getInvoiceSrcFlag() != null) {

					airlineCN51SummaryVO.setInvoiceSrcFlag(airlineCN51SummaryVO
							.getInvoiceSrcFlag());
				}

				if (airlineCN51SummaryVO.getLastUpdatedUser() != null) {

					airlineCN51SummaryVO
							.setLastUpdatedUser(airlineCN51SummaryVO
									.getLastUpdatedUser());
				} else {
					airlineCN51SummaryVO
							.setLastUpdatedUser(getApplicationSession()
									.getLogonVO().getUserId());

				}
			}

			else {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILED;
				return;
			}
		}

		else {
			airlineCN51SummaryVO = captureInvoiceSession.getAirlineCN51SummaryVO();
			airlineCN51SummaryVO.setOperationFlag(captureInvoiceForm
					.getButtonFlag());
			log.log(Log.FINE, "Delation--->");
		}
		log.log(Log.FINE, "---DelgateCalled---->>", airlineCN51SummaryVO);
		try {

			new MailTrackingMRADelegate()
					.updateBillingDetailCommand(airlineCN51SummaryVO);

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);

		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILED;
			captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			return;
		} else {
			
			captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			captureInvoiceForm.setInvSatusCheckFlag("S");
			errors.add(new ErrorVO(SAVE_SUCCESSFULLY));
			captureInvoiceSession.removeAirlineCN51SummaryVO();
			captureInvoiceSession.removeOneTimeMap();
			captureInvoiceSession.removeFilterVo();
			captureInvoiceForm.setAirlineCode(BLANK);
			captureInvoiceForm. setAirlineNo(BLANK);
			captureInvoiceForm.setInvoiceRefNo(BLANK);
			captureInvoiceForm.setClearancePeriod(BLANK);
			captureInvoiceForm.setInvoiceDate(BLANK);
			captureInvoiceForm.setListingCurrency(BLANK)	;
			captureInvoiceForm.setNetAmount(BLANK);
			captureInvoiceForm.setExgRate(BLANK);
			captureInvoiceForm.setAmountInusd(BLANK);
			captureInvoiceForm.setInvoiceReceiptDate(BLANK);
			captureInvoiceForm.setTotalWeight(BLANK);
			captureInvoiceForm.setInvoiceFormOneStatus(BLANK);
			captureInvoiceForm.setInvoiceStatus(BLANK);
			
			invocationContext.addAllError(errors);
		}
		//captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
		captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SAVE_SUCCESS;

	}

}
