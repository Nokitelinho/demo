/*
 * PopulateAirlineNoCommand.java Created on SEP-5-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureinvoice;
/**
 * @author a-3447
 */



import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.text.DecimalFormat;



/**@author A-3447
 * Command Class for Capture Invoice (Ajax implementation for getting airline no
 * from airline code and populating amount in usd frm net amt and exchange rate
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Sep 5, 2008 Muralee(a-3447) Initial draft
 * 
 */

public class PopulateAirlineNoCommand extends BaseCommand {


//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	/**
	 * 
	 * Class name
	 */
	private static final String CLASS_NAME = "PopulateAirlineNoCommand";

	/**
	 * Module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * Screen Id
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * AIRLINE CODE INVALID
	 */
	private static final String ARLCOD_INVALID = "mailtracking.mra.airlinebilling.inward.captureinvoice.airlinecodeinvalid";


	/**
	 * Screen Sucess
	 */

	private static final String SCREEN_SUCCESS = "populate_success";

	/**
	 * 
	 * Failure 
	 */
	private static final String  SCREEN_FAILURE="populate_failed";
	/**
	 * invalid currency code
	 */

	private static final String INVALID_CURCODE = "mailtracking.mra.airlinebilling.inward.msg.error.invalidcurrencycode";

	/**
	 * null currency
	 */

	private static final String CURCODE_NULL = "mailtracking.mra.airlinebilling.inward.msg.error.null.currency";

	private static final String INVALID_EXCHANGE_RATE = "mailtracking.mra.airlinebilling.inward.captureinvoice.invalid.excrate";


//	----------------------------------------------------------------------------------------------------------------------------------------------------------


	/**
	 * @author a-3447 Execute method
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		CaptureMailInvoiceForm captureInvoiceForm = (CaptureMailInvoiceForm) invocationContext.screenModel;
		CaptureInvoiceSession captureInvoiceSession=(CaptureInvoiceSession)getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		Collection<ErrorVO> arlerrors = new ArrayList<ErrorVO>();

//		for populating Ariline no automatically from airline code

		if(!("amtinusd".equals(captureInvoiceForm.getButtonFlag()))){
			//Added as part of ICRD 21165
			IATACalendarVO iatacalendarvo = null;
			
			IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
			iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
			iatacalendarfiltervo.setClearancePeriod(captureInvoiceForm.getClearancePeriod());
			
			if (captureInvoiceForm.getClearancePeriod() != null
					&& captureInvoiceForm.getClearancePeriod().trim().length() > 0) {
					
				try {
					iatacalendarvo = new MailTrackingMRADelegate().validateClearancePeriod(iatacalendarfiltervo);
					log
							.log(Log.INFO, "iatacalendarvo obtained",
									iatacalendarvo);
				}catch(BusinessDelegateException businessDelegateException){
					handleDelegateException(businessDelegateException);
				}				
				

			}
			if (captureInvoiceForm.getAirlineCode() != null
					&& captureInvoiceForm.getAirlineCode().trim().length() > 0) {
				try {
					//Added as part of ICRD 21165 
					LocalDate applicableDate = new LocalDate(NO_STATION,NONE,false);
					if(iatacalendarvo!=null && iatacalendarvo.getToDate()!=null){
						applicableDate = iatacalendarvo.getToDate();
					}
					airlineValidationVO = airlineDelegate.validateAlphaCodeWithDate(
							logonAttributes.getCompanyCode(), captureInvoiceForm
							.getAirlineCode().toUpperCase(),applicableDate.toGMTDate());

				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
				if (airlineValidationVO != null) {
					log.log(Log.FINE, "airlineValidationVO-->>",
							airlineValidationVO);
					captureInvoiceForm.setAirlineNo(airlineValidationVO.getNumericCode());	
					log.log(Log.FINE, "captureInvoiceForm.getArlNo--->",
							captureInvoiceForm.getAirlineNo());
					invocationContext.target =SCREEN_SUCCESS;	

				}
				else {
					log.log(Log.FINE, "Error in Airline no");
					ErrorVO err = new ErrorVO(ARLCOD_INVALID);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					arlerrors.add(err);
					invocationContext.addAllError(arlerrors);
					invocationContext.target = SCREEN_FAILURE;
					return;
				}
			}
		}else{


//			for populating Amount in usd AutoMatically from Exchange Rate and net Amount
			
			
			
			if (captureInvoiceForm.getListingCurrency() == null
					|| captureInvoiceForm.getListingCurrency().trim().length() == 0) {
				log.log(1, "getCurrency not present-....");
				ErrorVO err = new ErrorVO(CURCODE_NULL);
				arlerrors.add(err);

			} else {
				log.log(Log.INFO, "getCurrency  present....",
						captureInvoiceForm.getListingCurrency());
				try {
					new CurrencyDelegate().validateCurrency(
							getApplicationSession().getLogonVO().getCompanyCode(),
							captureInvoiceForm.getListingCurrency().trim()
							.toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					log.log(Log.INFO, "\n -------- Vaidation Failed---------");
					captureInvoiceForm.setListingCurrency("");
					ErrorVO error = new ErrorVO(INVALID_CURCODE);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);
					invocationContext.target = SCREEN_FAILURE;
					return;

				}
			}

			if (arlerrors == null || arlerrors.size() == 0) {
				if ((captureInvoiceForm.getExchangeRate() != null||  captureInvoiceForm.getExchangeRate().trim().length()>0) &&(captureInvoiceForm.getNetAmount()!=null ||captureInvoiceForm.getNetAmount().trim().length()>0)){
					log.log(Log.FINE, "Inside Money Conversion---");
					//double exgRate=Double.parseDouble(captureInvoiceForm.getExchangeRate());
					double amtInUsd=0.0;
					String  usdAmt=null;
					Money netAmount;
					Money exchangeRate;
					Money amt;
					//double netAmts=0.0;
					try {
						netAmount = CurrencyHelper.getMoney(captureInvoiceForm.getListingCurrency().toUpperCase());
						exchangeRate = CurrencyHelper.getMoney(captureInvoiceForm.getListingCurrency().toUpperCase());
						if (captureInvoiceForm.getNetAmount() != null
								&& captureInvoiceForm.getNetAmount().trim()
								.length() > 0) {
							log.log(Log.FINE, "Inside Money Conversion");
							netAmount.setAmount(Double.parseDouble(captureInvoiceForm.getNetAmount()));
							exchangeRate.setAmount(Double.parseDouble(captureInvoiceForm.getExchangeRate()));
							if(exchangeRate.getAmount()!=0){
							amtInUsd=netAmount.getAmount()/exchangeRate.getAmount();
							DecimalFormat fmt = new DecimalFormat("0.00");
							String decimalString = fmt.format(amtInUsd);						
							 usdAmt=(decimalString);
						}else{
							log.log(Log.FINE, "Error in Exchange Rate");
							ErrorVO err = new ErrorVO(INVALID_EXCHANGE_RATE);
							err.setErrorDisplayType(ErrorDisplayType.ERROR);
							arlerrors.add(err);
							invocationContext.addAllError(arlerrors);
							invocationContext.target = SCREEN_FAILURE;
							return;
							
							
						}
						}
					} catch (CurrencyException currencyException) {
						log.log(Log.FINE, "currency Exception--");


					}


					if(usdAmt!=null){						
						log.log(Log.FINE, "usdAmt---", usdAmt);
						captureInvoiceForm.setAmountInusd(usdAmt);						
							//Money amtinUsd = CurrencyHelper.getMoney(captureInvoiceForm
									//.getListingCurrency().toUpperCase());						
							//amtinUsd.setAmount(amtInUsd);							
							//airlineCN51SummaryVO.setAmountInusd(amtinUsd);
							//captureInvoiceSession.setAirlineCN51SummaryVO(airlineCN51SummaryVO);
						invocationContext.target =SCREEN_SUCCESS;											
						log.exiting(CLASS_NAME, "execute");
					}

				}
			}else {
				invocationContext.addAllError(arlerrors);
				invocationContext.target = SCREEN_FAILURE;
				return;
			}
		
		}

	}
}
