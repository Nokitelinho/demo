/*
 * DetailsCommand.java Created on July 15, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.viewformone;


import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;

/**
 * @author A-3456
 * 
 */
public class DetailsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
	/**
	 *SCREENID
	 * 
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
	/**
	 *MODULE
	 * 
	 */
	private static final String MODULE = "mra.airlinebilling";

	private static final String INVALID_AIRLINE_CODE=
		"mra.airlinebilling.outward.viewformone.msg.err.invalidairlinecode";

	private static final String INVALID_NUMERIC_CODE=
		"mra.airlinebilling.outward.viewformone.msg.err.invalidnumericcode";
	private static final String ERROR_KEY_NORESUTLS_FOUND=
		"mra.airlinebilling.outward.viewformone.msg.err.norecordfound";

	private static final String CLEARANCE_MANDATORY="mra.airlinebilling.outward.viewformone.msg.err.clrprdmandtry";
	private static final String AIRLINECODE_MANDATORY="mra.airlinebilling.outward.viewformone.msg.err.arlcodmandtry";
	private static final String CLASS_NAME = "DetailsCommand";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String FROM_LISTFORMONE="fromListFormOne";

	/**
	 * For invalid Clearence period
	 * 
	 */

	private static final String CLRPRD_INVALID = "mra.airlinebilling.outward.viewformone.invalidclearenceperiod";



	/**
	 * execute method
	 * 
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

		ViewMailFormOneForm viewFormOneForm = (ViewMailFormOneForm) invocationContext.screenModel;
		ViewFormOneSession session = (ViewFormOneSession) getScreenSession(
				MODULE, SCREENID);
		Collection<InvoiceInFormOneVO> invoiceFormOneDetailsVOs = null;
		Collection<ErrorVO> clearencePeriodError = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		InterlineFilterVO interlineFilterVo = new InterlineFilterVO();
		FormOneVO formOneVo = null;
		AirlineValidationVO airlineValidationVO = null;
		companyCode = getApplicationSession().getLogonVO().getCompanyCode();

		clearancePeriod = viewFormOneForm.getClearancePeriod().trim()
		.toUpperCase();
		airlineCode = viewFormOneForm.getAirlineCode().trim().toUpperCase();
		airlineNumber = viewFormOneForm.getAirlineNumber().trim()
		.toUpperCase();
		//	From ListFormOne
		if(FROM_LISTFORMONE.equals(session.getCloseStatus())){
			interlineFilterVo.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			log.log(Log.FINE, "CloseStatus----->>", session.getCloseStatus());
			interlineFilterVo.setClearancePeriod(session.getClrperiod());
			interlineFilterVo.setAirlineCode(session.getAirlineCode());			
			log.log(Log.FINE, "interlineFilterVo----->>", session.getClrperiod());
			log.log(Log.FINE, "interlineFilterVo----->>", session.getAirlineCode());
			viewFormOneForm.setClearancePeriod(interlineFilterVo.getClearancePeriod());
			viewFormOneForm.setAirlineCode(interlineFilterVo.getAirlineCode());
			try {
				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
						getApplicationSession().getLogonVO().getCompanyCode(), session.getAirlineCode());
			}catch (BusinessDelegateException e) {
				log.log(Log.FINE,  "BusinessDelegateException");
			}
			if(airlineValidationVO!=null)	{
				interlineFilterVo.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());

			}

		}

		else{
			//	added for mandatory fields start
			IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
			iatacalendarfiltervo.setCompanyCode(companyCode);
			iatacalendarfiltervo.setClearancePeriod(clearancePeriod);
			if("".equals(clearancePeriod) && clearancePeriod.trim().length()==0){

				error = new ErrorVO(CLEARANCE_MANDATORY);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);

			}
			if("".equals(airlineCode) && airlineCode.trim().length()==0){

				error = new ErrorVO(AIRLINECODE_MANDATORY);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;		
				return;	
			}
			if (viewFormOneForm.getClearancePeriod() != null
					&& viewFormOneForm.getClearancePeriod().trim().length() > 0) {
				clearencePeriodError = validateClearencePeriod(iatacalendarfiltervo);

			}
			if (clearencePeriodError != null && clearencePeriodError.size() > 0) {
				invocationContext.addAllError(clearencePeriodError);
				invocationContext.target = LIST_FAILURE;		
				return;	
			}


			interlineFilterVo.setCompanyCode(companyCode);

			interlineFilterVo.setClearancePeriod(clearancePeriod);

			interlineFilterVo.setAirlineCode(airlineCode);

			interlineFilterVo.setAirlineNumber(airlineNumber);
			if(airlineCode != null && airlineCode.trim().length()>0){
				try {
					airlineValidationVO = new AirlineDelegate().validateAlphaCode(
							companyCode, airlineCode);
				}catch (BusinessDelegateException e) {
					if(airlineValidationVO == null ){
						error = new ErrorVO(INVALID_AIRLINE_CODE);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
					else{
						log.log(Log.FINE,"airlineValidationVO NOT NULL");
						interlineFilterVo.setAirlineNumber(airlineValidationVO.getNumericCode());	
						interlineFilterVo.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
					}
				}	

			}

			if (airlineNumber != null && airlineNumber.trim().length()>0) {
				//listFormOneFilterVo.setAirlineNumber(airlineNumber);	
				airlineValidationVO 
				= validateNumericCode(companyCode,airlineNumber);
				if(airlineValidationVO == null ){
					error = new ErrorVO(INVALID_NUMERIC_CODE);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}else{
					interlineFilterVo.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());				

				}
			}	
			log
					.log(Log.FINE, "airlineValidationVO----->>",
							airlineValidationVO);
			if(airlineValidationVO!=null){
				interlineFilterVo.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());		

			}
			log.log(Log.FINE, "filter to server----->>", interlineFilterVo);
		}
		try {

			formOneVo = new MailTrackingMRADelegate()
			.findFormOneDetails(interlineFilterVo);
		} catch (BusinessDelegateException businessDelegateException) {
			errors=	handleDelegateException(businessDelegateException);
		}
		if(formOneVo == null  ){
			log.log(Log.FINE,"No Records Found :---> invoiceFormOneDetailsVOs = ");
			error = new ErrorVO(ERROR_KEY_NORESUTLS_FOUND);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			viewFormOneForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);		
			errors.add(error);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;		
			return;	
		}	
		invoiceFormOneDetailsVOs =formOneVo.getInvoiceInFormOneVOs();
		//setting the read only fields in form
		viewFormOneForm.setExchangeRateInBillingCurrency(String.valueOf(formOneVo.getExchangeRateBillingCurrency()));
		viewFormOneForm.setExchangeRateInListingCurrency(String.valueOf(formOneVo.getExchangeRateListingCurrency()));
		viewFormOneForm.setBillingCurrency(formOneVo.getBillingCurrency());
		viewFormOneForm.setListingCurrency(formOneVo.getListingCurrency());
		viewFormOneForm.setTableClass(formOneVo.getClassType());
		if(invoiceFormOneDetailsVOs!=null &&invoiceFormOneDetailsVOs.size()>0){
			session.setInvoiceFormOneDetailsVOs(invoiceFormOneDetailsVOs);
			log.log(Log.FINE, "-from session----------->", session.getInvoiceFormOneDetailsVOs());
		}
		session.setFormOneVO(formOneVo);
		viewFormOneForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);		
		invocationContext.target = LIST_SUCCESS;
	}


	/**
	 * @author a-3456
	 * @param companyCode
	 * @param airlineNumber
	 * @return
	 */
	private AirlineValidationVO validateNumericCode(String companyCode,String airlineNumber){
		AirlineDelegate airlineDelegate = new AirlineDelegate(); 
		AirlineValidationVO validationVO = null;
		try{
			validationVO 
			= airlineDelegate.validateNumericCode(companyCode,airlineNumber);
		}
		catch(BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE," invalid Airline Exception from server ");
		}
		return validationVO;
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
			log.log(Log.INFO, "iatacalendarvos not null ", iatacalendarvo);

		} else {
			log.log(log.INFO, "iatacalendarvo null--->");
			ErrorVO err = new ErrorVO(CLRPRD_INVALID);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
		}

		return errors;

	}


}	
		
