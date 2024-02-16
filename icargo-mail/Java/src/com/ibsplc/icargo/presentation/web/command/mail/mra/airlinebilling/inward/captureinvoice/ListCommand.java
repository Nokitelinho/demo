/* ListCommand.java Created on July 30,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 *
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.captureinvoice;
/**
 * @author A-3447
 */
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
//import com.ibsplc.icargo.business.shared.airline.vo.AirlineAttributeVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.CaptureInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author a-3447
 * 
 */
public class ListCommand extends BaseCommand {

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private Log log = LogFactory.getLogger("MRA --AIRLINEBILLING");

	/**
	 * SCREENID
	 * 
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.inward.captureinvoice";

	/**
	 * MODULE
	 * 
	 */
	private static final String MODULE = "mailtracking";

	/**
	 * CLASS NAME
	 * 
	 */
	private static final String CLASS_NAME = "mailtracking";

	/**
	 * Success Target
	 */
	private static final String LIST_SUCCESS = "list_success";

	/**
	 * Failure path
	 */

	private static final String LIST_FAILURE = "list_failure";

	/**
	 * Check for form one is enabled or not
	 */
	private static final String IS_FORMONE_ENABLED = "mra.airlinebilling.inward.captureinvoice.isformoneenabled";

	/**
	 * Mandatory Condtions
	 */

	private static final String CLEARENCE_PRD_MANDATORY = "mra.airlinebilling.inward.captureinvoice.msg.err.clearanceperiod.mandatory";

	/**
	 * Mandatory Condtions
	 */

	private static final String ARLCODE_MANDATORY = "mra.airlinebilling.inward.captureinvoice.msg.err.airlinecode.mandatory";

	/**
	 * Mandatory Condtions
	 */

	private static final String INVOICENO_MANDATORY = "mra.airlinebilling.inward.captureinvoice.msg.err.invoiceno.mandatory";

	/**
	 * Mandatory Condtions
	 */

	private static final String INVOICEDATE_MANDATORY = "mra.airlinebilling.inward.captureinvoice.msg.err.invoicedate.mandatory";

	/**
	 * INVOICE_STATUS one time
	 */

	private static final String INVOICE_STATUS = "mra.airlinebilling.inward.captureinvoice.invoicestatus";

	/**
	 * INVOICE_FORM1_STATUS one time
	 */
	private static final String INVOICE_FORM1_STATUS = "mra.airlinebilling.inward.captureinvoice.invoiceformonestatus";

	/**
	 * For invalid Clearence period
	 * 
	 */

	private static final String CLRPRD_INVALID = "mra.airlinebilling.inward.captureinvoice.invalidclearenceperiod";

	/**
	 * AIRLINE CODE INVALID
	 */
	private static final String ARLCOD_INVALID = "mailtracking.mra.airlinebilling.inward.captureinvoice.airlinecodeinvalid";

	/**
	 * AIRLINE NO INVALID
	 */
	private static final String ARLNO_INVALID = "mailtracking.mra.airlinebilling.inward.captureinvoice.airlinenoinvalid";

	/**
	 * No data Found
	 */
	private static final String NO_DATA_FOUND = "mailtracking.mra.airlinebilling.inward.captureinvoice.nodatafound";

	private static final String NO_DATA_FOUND_FORICH = "mailtracking.mra.airlinebilling.inward.captureinvoice.nodatafound.ich";

	/**
	 * ich/non ich
	 */
	private static final String NON_ICH = "N";

	/**
	 * ich
	 */
	private static final String ICH = "I";

	// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author a-3447 execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		String companyCode = null;
		String clearancePeriod = null;
		String airlineCode = null;
		String airlineNumber = null;
		String invoiceNumber = null;
		String invoiceDate = null;
		AirlineVO airlineVO = null;
		CaptureMailInvoiceForm captureInvoiceForm = (CaptureMailInvoiceForm) invocationContext.screenModel;
		CaptureInvoiceSession captureInvoiceSession = (CaptureInvoiceSession) getScreenSession(
				MODULE, SCREENID);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		AirlineForBillingVO airlineForBillingVO=new AirlineForBillingVO();


		ErrorVO error = null;
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		AirlineCN51FilterVO airlineCN51FilterVO = new AirlineCN51FilterVO();
		AirlineCN51SummaryVO airlineCN51SummaryVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> arlerrors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> clearencePeriodError = new ArrayList<ErrorVO>();
		Collection<ErrorVO> nodataErrors = new ArrayList<ErrorVO>();
		companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
		iatacalendarfiltervo.setCompanyCode(companyCode);
		iatacalendarfiltervo.setClearancePeriod(captureInvoiceForm
				.getClearancePeriod());
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(INVOICE_STATUS);
		parameterTypes.add(INVOICE_FORM1_STATUS);
		captureInvoiceForm.setIchFlag("nonich");
		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					companyCode, parameterTypes);
			log.log(Log.FINE, "One Time Values%%---", oneTimeValues);

		} catch (BusinessDelegateException e) {
			handleDelegateException(e);

		}

		if (oneTimeValues != null) {
			captureInvoiceSession
			.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		}
		if (captureInvoiceSession.getFilterVo() == null) {
			errors = validateForm(captureInvoiceForm);
			
            //Added as part of ICRD 21165
			IATACalendarVO iatacalendarvo = null;
			if (captureInvoiceForm.getClearancePeriod() != null
					&& captureInvoiceForm.getClearancePeriod().trim().length() > 0) {
					
				try {
					iatacalendarvo = new MailTrackingMRADelegate()
					.validateClearancePeriod(iatacalendarfiltervo);
					log
							.log(Log.INFO, "iatacalendarvo obtained",
									iatacalendarvo);
				}catch(BusinessDelegateException businessDelegateException){
					clearencePeriodError = handleDelegateException(businessDelegateException);
				}
				
				if (iatacalendarvo != null ) {
					log.log(Log.INFO, "iatacalendarvo not null ",
							iatacalendarvo);

				} else {
					log.log(log.INFO, "iatacalendarvo null--->");
					ErrorVO err = new ErrorVO(CLRPRD_INVALID);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					clearencePeriodError.add(err);
				}

			}
			
			AirlineDelegate airlineDelegate = new AirlineDelegate();
			AirlineValidationVO airlineValidationVO = null;

			if (captureInvoiceForm.getAirlineCode() != null
					&& captureInvoiceForm.getAirlineCode().trim().length() > 0) {
				try {
					//Added as part of ICRD 21165
                    LocalDate applicableDate = new LocalDate(NO_STATION,NONE,false);
					if(iatacalendarvo!=null && iatacalendarvo.getToDate()!=null){
						applicableDate = iatacalendarvo.getToDate();
					}
					airlineValidationVO = airlineDelegate.validateAlphaCodeWithDate(
							getApplicationSession().getLogonVO()
							.getCompanyCode(), captureInvoiceForm
							.getAirlineCode().toUpperCase(),applicableDate.toGMTDate());

				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
				if (airlineValidationVO != null) {
					if (captureInvoiceForm.getAirlineNo() != null) {
						if (("").equals(captureInvoiceForm.getAirlineNo())
								&& (captureInvoiceForm.getAirlineNo().trim()
										.length() == 0)) {
							captureInvoiceForm.setAirlineNo(airlineValidationVO
									.getNumericCode());
							log.log(Log.FINE, "airlineValidationVO---",
									airlineValidationVO
									.getAirlineIdentifier());
							int identifier = airlineValidationVO
							.getAirlineIdentifier();

							airlineCN51FilterVO
							.setAirlineIdentifier(identifier);

							airlineCN51FilterVO	.setAirlineNum(airlineValidationVO.getNumericCode());

						} /*
						 * else { if (captureInvoiceForm.getAirlineNo() !=
						 * airlineValidationVO .getNumericCode()) { ErrorVO
						 * err = new ErrorVO(ARLNO_INVALID);
						 * err.setErrorDisplayType(ErrorDisplayType.ERROR);
						 * arlerrors.add(err); } }
						 */
					}
				} else {
					ErrorVO err = new ErrorVO(ARLCOD_INVALID);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					arlerrors.add(err);
				}
			}

			if (captureInvoiceForm.getClearancePeriod() != null
					&& captureInvoiceForm.getClearancePeriod().trim().length() > 0) {
				clearencePeriodError = validateClearencePeriod(iatacalendarfiltervo);

			}

			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				captureInvoiceForm
				.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);

			}
			if (arlerrors != null && arlerrors.size() > 0) {
				invocationContext.addAllError(arlerrors);
				captureInvoiceForm
				.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);

			}

			if (clearencePeriodError != null && clearencePeriodError.size() > 0) {
				log.log(Log.FINE, "errors--", clearencePeriodError.size());
				captureInvoiceForm
				.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				invocationContext.addAllError(clearencePeriodError);

			}
			if (((clearencePeriodError != null && clearencePeriodError.size() > 0)
					|| (errors != null && errors.size() > 0) || (arlerrors != null && arlerrors
							.size() > 0))) {

				captureInvoiceForm
				.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				log.log(Log.FINE, "Error Checks--");
				invocationContext.target = LIST_FAILURE;
				return;

			} else {

				companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
				captureInvoiceSession
				.setAirlineCN51SummaryVO(airlineCN51SummaryVO);

				clearancePeriod = captureInvoiceForm.getClearancePeriod()
				.trim().toUpperCase();
				airlineCode = captureInvoiceForm.getAirlineCode().trim()
				.toUpperCase();
				airlineNumber = captureInvoiceForm.getAirlineNo().trim()
				.toUpperCase();
				invoiceNumber = captureInvoiceForm.getInvoiceRefNo().trim()
				.toUpperCase();
				invoiceDate = captureInvoiceForm.getInvoiceDate();

				airlineCN51FilterVO.setCompanyCode(companyCode);
				airlineCN51FilterVO.setAirlineCode(airlineCode);
				// airlineCN51FilterVO.setAirlineNumber(Integer.parseInt(airlineNumber));
				airlineCN51FilterVO.setIataClearancePeriod(clearancePeriod);
				airlineCN51FilterVO.setInvoiceReferenceNumber(invoiceNumber);
				airlineCN51FilterVO.setAirlineIdentifier(airlineValidationVO
						.getAirlineIdentifier());
				airlineCN51FilterVO	.setAirlineNum(airlineValidationVO.getNumericCode());
				/**
				 * @author a-3447
				 */
				/*if (captureInvoiceForm.getInvoiceDate() != null
						&& captureInvoiceForm.getInvoiceDate().trim().length() > 0) {
					log.log(Log.FINE, "Date ---->>"
							+ captureInvoiceForm.getInvoiceDate());
					StringBuffer toDate = new StringBuffer(captureInvoiceForm
							.getInvoiceDate());
					log.log(Log.FINE, "todate---->>" + toDate.toString());
					LocalDate toDateAndTime = new LocalDate(
							getApplicationSession().getLogonVO()
									.getStationCode(),  Location.STN, true);
					toDateAndTime.setDate(toDate.toString());
					airlineCN51FilterVO.setInvDate(toDateAndTime);

				}*/

				if(invoiceDate != null && invoiceDate.trim().length() > 0){
					if(DateUtilities.isValidDate(invoiceDate,"dd-MMM-yyyy")) {
						LocalDate invDate = new LocalDate(NO_STATION,NONE,false);
						invDate.setDate(invoiceDate);
						airlineCN51FilterVO.setInvDate(invDate);
						log.log(Log.FINE, "date---->>", airlineCN51FilterVO.getInvDate());
					}
				}
				try {
					airlineVO = airlineDelegate.findAirlineDetails(companyCode,
							airlineValidationVO.getAirlineIdentifier());

				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
				//Cpmmented for Build Issue  on 14Sep2009
				/*Collection<AirlineAttributeVO> validationDetails = airlineVO
				.getBillingInfomationsVOs();

				if (validationDetails != null && validationDetails.size() > 0) {
					int flag = 0;
					boolean isNonIch = false;
					for (AirlineAttributeVO AirlineAttributeVO : validationDetails) {
						if (airlineCN51FilterVO.getInvDate() != null
								&& AirlineAttributeVO.getValidTooDate() != null
								&& AirlineAttributeVO.getValidFromDate() != null) {
							if ((!airlineCN51FilterVO.getInvDate()
									.isGreaterThan(
											AirlineAttributeVO
											.getValidTooDate()))
											&& (!airlineCN51FilterVO
													.getInvDate()
													.isLesserThan(
															AirlineAttributeVO
															.getValidFromDate()))) {
								log.log(Log.FINE, "Invoice Date"
										+ airlineCN51FilterVO.getInvDate());
								log
								.log(Log.FINE, "Airline From Date"

										+ AirlineAttributeVO
										.getValidFromDate());
								log.log(Log.FINE,
										"AirlineAttributeVO.getAttributeType()"
										+ AirlineAttributeVO
										.getAttributeType());
								flag = 1;

								if (NON_ICH.equalsIgnoreCase(AirlineAttributeVO
										.getAttributeType())) {
									isNonIch = true;

								}
								if (ICH.equalsIgnoreCase(AirlineAttributeVO
										.getAttributeType())) {
									captureInvoiceForm.setIchFlag("ich");
									break;
								}
							}
						}
					}

				}*/

			}
			if(("ich").equals(captureInvoiceForm.getIchFlag())){
				log.log(Log.FINE, "inside ich filters to sever ",
						airlineCN51FilterVO);
				try {

					airlineForBillingVO=new MailTrackingMRADelegate().findAllInvoiceFlags(airlineCN51FilterVO);
					log.log(Log.FINE, "airlineCn51SummaryVO----->>>",
							airlineForBillingVO);

				} catch (BusinessDelegateException e) {
					e.getMessage();


				}
			}

		}




		else {
			captureInvoiceForm.setFromScreenFlg("FORMONE");
			airlineCN51FilterVO = captureInvoiceSession.getFilterVo();
			log.log(Log.INFO, "airlineCN51FilterVO from session ---> ",
					airlineCN51FilterVO);
			captureInvoiceForm.setInvoiceDate(airlineCN51FilterVO.getInvDate()
					.toDisplayDateOnlyFormat());
			captureInvoiceForm.setInvoiceRefNo(airlineCN51FilterVO
					.getInvoiceReferenceNumber());

		}
		/**
		 * @author a-3447
		 */



		log.log(Log.INFO, "airlineCN51FilterVO from session ---> ",
				airlineCN51FilterVO);
		try {
			airlineCN51SummaryVO = new MailTrackingMRADelegate()
			.findCaptureInvoiceDetails(airlineCN51FilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		if (airlineCN51SummaryVO != null) {
			oneTimeMap = captureInvoiceSession.getOneTimeMap();
			Collection<OneTimeVO> invoiceStatus = oneTimeMap
			.get(INVOICE_STATUS);
			Collection<OneTimeVO> invform1Status = oneTimeMap
			.get(INVOICE_FORM1_STATUS);
			log.log(Log.FINE, "INVOICE_FORM1_STATUS", invform1Status);
			log.log(Log.FINE, "INVOICE_STATUS", invoiceStatus);
			/**
			 * Iterating one time
			 */
			if (invoiceStatus != null || invform1Status != null) {
				if (airlineCN51SummaryVO.getInvStatus() != null	|| airlineCN51SummaryVO.getInvFormstatus() != null) {
					for (OneTimeVO status : invoiceStatus) {
						if (airlineCN51SummaryVO.getInvStatus() != null) {
							if (airlineCN51SummaryVO.getInvStatus().equals(
									status.getFieldValue())) {
								captureInvoiceForm.setInvoiceStatus(status
										.getFieldDescription());
								captureInvoiceForm.setInvSatusCheckFlag(status
										.getFieldValue());

							}
						}
					}
					for (OneTimeVO form1InvStatus : invform1Status) {
						if (airlineCN51SummaryVO.getInvFormstatus() != null 
								&& ((("ich").equals(captureInvoiceForm.getIchFlag()))||("FORMONE".equals(captureInvoiceForm.getFromScreenFlg())))){
							if (airlineCN51SummaryVO.getInvFormstatus().equals(
									form1InvStatus.getFieldValue())) {
								captureInvoiceForm
								.setInvoiceFormOneStatus(form1InvStatus
										.getFieldDescription());
								captureInvoiceForm
								.setInvForm1SatusCheckFlag(form1InvStatus
										.getFieldValue());

							}
						}
					}

				}
			}
			//airlineCN51SummaryVO.setExchangeRate(airlineCN51SummaryVO.getExchangeRate())
			captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			if(airlineCN51SummaryVO.getExchangeRate().getAmount()!=0.0){
				airlineCN51SummaryVO.setExgRate(airlineCN51SummaryVO.getExchangeRate().getAmount());

			}
			captureInvoiceSession.setAirlineCN51SummaryVO(airlineCN51SummaryVO);
			captureInvoiceSession.setFilterVo(airlineCN51FilterVO);
			captureInvoiceForm.setClearancePeriod(airlineCN51FilterVO
					.getIataClearancePeriod());
			captureInvoiceForm
			.setAirlineNo(airlineCN51FilterVO.getAirlineNum());
			captureInvoiceForm.setAirlineCode(airlineCN51FilterVO
					.getAirlineCode());
			captureInvoiceForm.setInvoiceDate(airlineCN51FilterVO.getInvDate()
					.toDisplayDateOnlyFormat());
			captureInvoiceForm.setInvoiceRefNo(airlineCN51FilterVO
					.getInvoiceReferenceNumber());
			log.log(Log.INFO, "captureInvoiceForm ", captureInvoiceForm.getAirlineCode());
			log.log(Log.INFO, "airlineCN51FilterVO ", airlineCN51FilterVO);
			invocationContext.target = LIST_SUCCESS;

		} else {
			captureInvoiceSession.setFilterVo(airlineCN51FilterVO);
			log.log(Log.INFO, "airlineCN51FilterVO ", airlineForBillingVO);
			if(("ich").equals(captureInvoiceForm.getIchFlag())) {
				log.log(Log.INFO, "inside non ich ", airlineForBillingVO);
				if(airlineForBillingVO.isCapturedFormOneFlag())

				{	log.log(Log.INFO, "inside non ich ", airlineCN51FilterVO);
				captureInvoiceForm.setIchFlag("formoneenableich");
				ErrorVO nodataError = new ErrorVO(NO_DATA_FOUND);
				nodataErrors.add(nodataError);
				nodataError.setErrorDisplayType(ERROR);
				captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
				invocationContext.addAllError(nodataErrors);
				invocationContext.target = LIST_FAILURE;	


				}
				else{


					log.log(Log.INFO, "airlineCn51SummaryVO-->>",
							airlineForBillingVO);
					ErrorVO nodataError = new ErrorVO(NO_DATA_FOUND_FORICH);
					captureInvoiceForm.setNoFormOneCaptured("false");
					nodataErrors.add(nodataError);
					nodataError.setErrorDisplayType(ERROR);
					captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
					invocationContext.addAllError(nodataErrors);
					invocationContext.target = LIST_FAILURE;
				}
			}
			else if (("nonich").equals(captureInvoiceForm.getIchFlag())){
				log.log(Log.INFO, "inside non ich ", airlineCN51FilterVO);
				ErrorVO nodataError = new ErrorVO(NO_DATA_FOUND);
				nodataErrors.add(nodataError);
				nodataError.setErrorDisplayType(ERROR);
				captureInvoiceForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
				invocationContext.addAllError(nodataErrors);
				invocationContext.target = LIST_FAILURE;
			}
		}
	}

	/**
	 * Method for validating Clearence period
	 * 
	 * @author a-3447
	 * @param iatacalendarfiltervo
	 * @return Collection<ErrorVO>
	 */

	private Collection<ErrorVO> validateClearencePeriod(
			IATACalendarFilterVO iatacalendarfiltervo) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		IATACalendarVO iatacalendarvo = null;
		try {

			iatacalendarvo = new MailTrackingMRADelegate()
			.validateClearancePeriod(iatacalendarfiltervo);
			log.log(Log.INFO, "iatacalendarvo obtained", iatacalendarvo);
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		
		if (iatacalendarvo != null ) {
			log.log(Log.INFO, "iatacalendarvo not null ", iatacalendarvo);

		} else {
			log.log(log.INFO, "iatacalendarvo null--->");
			ErrorVO err = new ErrorVO(CLRPRD_INVALID);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
		}

		return errors;

	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String, String> getSystemParameterValues() {
		log.entering("ScreenLoadCommand", "getSystemParameterValues");
		/**
		 * 
		 * the shared defaults delegate
		 */

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, String> parameterValues = null;

		try {
			parameterValues = sharedDefaultsDelegate
			.findSystemParameterByCodes(getSystemParameters());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "in the exception");
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", parameterValues);
		log.exiting("ScreenLoadCommand", "getSystemParameterValues");
		return parameterValues;
	}

	/**
	 * @author a-3447
	 * @return Collection<String>
	 * 
	 */
	private Collection<String> getSystemParameters() {
		log.entering("ScreenLoadCommand", "getSystemParameters");

		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(IS_FORMONE_ENABLED);
		log.exiting("ScreenLoadCommand", "getSystemParameters");
		return fieldTypes;
	}

	/**
	 * Method for validationg form
	 * 
	 * @author a-3447
	 * @param captureInvoiceForm
	 * @return Collection<ErrorVO>
	 */

	private Collection<ErrorVO> validateForm(
			CaptureMailInvoiceForm captureInvoiceForm) {

		String clearancePeriod = null;
		String airlineCode = null;
		String invoiceNumber = null;
		String invoiceDate = null;

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("MandatoryChecksCommand", "validateForm");
		clearancePeriod = captureInvoiceForm.getClearancePeriod().trim()
		.toUpperCase();
		airlineCode = captureInvoiceForm.getAirlineCode().trim().toUpperCase();
		invoiceNumber = captureInvoiceForm.getInvoiceRefNo().trim()
		.toUpperCase();
		invoiceDate = captureInvoiceForm.getInvoiceDate();

		if (clearancePeriod == null || clearancePeriod.trim().length() == 0) {
			ErrorVO error = new ErrorVO(CLEARENCE_PRD_MANDATORY);
			errors.add(error);
			error.setErrorDisplayType(ERROR);

		}

		if (airlineCode == null || airlineCode.trim().length() == 0) {
			ErrorVO error = new ErrorVO(ARLCODE_MANDATORY);
			errors.add(error);
			error.setErrorDisplayType(ERROR);

		}

		if (invoiceNumber == null || invoiceNumber.trim().length() == 0) {
			ErrorVO error = new ErrorVO(INVOICENO_MANDATORY);
			errors.add(error);
			error.setErrorDisplayType(ERROR);

		}
		if (invoiceDate == null || invoiceDate.trim().length() == 0) {
			ErrorVO error = new ErrorVO(INVOICEDATE_MANDATORY);
			errors.add(error);
			error.setErrorDisplayType(ERROR);

		}

		log.exiting("MandatoryChecksCommand-->", "validateForm-->");
		return errors;
	}

}
