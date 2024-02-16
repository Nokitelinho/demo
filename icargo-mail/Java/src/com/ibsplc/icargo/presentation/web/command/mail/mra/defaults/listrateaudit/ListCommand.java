/*
 * ListCommand.java created on July 15,2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateaudit;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListRateAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class ListCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listrateaudit";

	private static final String LIST_SUCCESS = "list_success";
	private static final String LIST_FAILURE = "list_failure";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.defaults.listrateaudit.nodatafound";
	private static final String FROMDATE_LESSTHAN_TODATE="mailtracking.mra.defaults.listrateaudit.fromdateshouldbelesserthantodate";
	private static final String RATEAUDIT_SUCCESSMSG="mailtracking.mra.defaults.listrateaudit.msg.rateauditsuccess";
	private static final String BLANK="";
	private static final String YES = "Y";
	private static final String NO = "N";
	private static final String CURRENCY_CODE = "NZD";
	private static final String FROM_SCREEN="fromListRateAudit";
	private static final String FROM_PRORATION="fromProrationException";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		ListRateAuditForm form=(ListRateAuditForm)invocationContext.screenModel;

		ListRateAuditSession listRateAuditSession = getScreenSession(
				MODULE_NAME, SCREENID);
		//Collection<RateAuditVO> rateAuditVOs=null;
		Page<RateAuditVO> rateAuditVOs = null;
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();

		//Collection<ErrorVO> errors = null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		RateAuditFilterVO rateAuditFilterVO=listRateAuditSession.getRateAuditFilterVO();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		log
				.log(Log.FINE, "FROM_SCREEN..", listRateAuditSession.getFromScreen());
		/* From ListRateAudit,we can navigate to RateAuditDetails or ListProrationException
		 * After clicking Close from RateAuditDetails or ListProrationException
		 * we have to retain the filters
		 * The if loop given below is for that purpose.
		 */
		if((rateAuditFilterVO!=null && FROM_SCREEN.equals(listRateAuditSession.getFromScreen()))||
				 FROM_PRORATION.equals(listRateAuditSession.getFromScreen())) {

			log
					.log(Log.FINE, "rateAuditFilterVO not null..",
							rateAuditFilterVO);
			if(rateAuditFilterVO.getDsn()!=null){
			form.setDsn(rateAuditFilterVO.getDsn());
			}
			else{
			form.setDsn(BLANK);
			}
			if(rateAuditFilterVO.getDsnDate()!=null){

			form.setDsnDate(rateAuditFilterVO.getDsnDate().toDisplayDateOnlyFormat());
			}
			else{
			form.setDsnDate(BLANK);
			}
			if(rateAuditFilterVO.getCarrierId()>0){
				
			String carrierCode = validateCarrierId(rateAuditFilterVO, invocationContext,
					logonAttributes);
			form.setCarrierCode(carrierCode);
			}
			else{
			form.setCarrierCode(BLANK);
			}
			if(rateAuditFilterVO.getFlightNumber()!=null){
				form.setFlightNo(rateAuditFilterVO.getFlightNumber());
			}
			else{
			form.setFlightNo(BLANK);
			}
			if(rateAuditFilterVO.getGpaCode()!=null){
				form.setGpaCode(rateAuditFilterVO.getGpaCode());
			}
			else{
			form.setGpaCode(BLANK);
			}
			if(rateAuditFilterVO.getSubClass()!=null){
			form.setSubClass(rateAuditFilterVO.getSubClass());
			}
			else{
			form.setSubClass(BLANK);
			}
			if(rateAuditFilterVO.getDsnStatus()!=null){
			form.setDsnStatus(rateAuditFilterVO.getDsnStatus());
			}
			else{
			form.setDsnStatus(BLANK);
			}
			if(rateAuditFilterVO.getFromDate()!=null){
			form.setFromDate(rateAuditFilterVO.getFromDate().toDisplayDateOnlyFormat());
			}
			else{
			form.setFromDate(BLANK);
			}
			if(rateAuditFilterVO.getToDate()!=null){
			form.setToDate(rateAuditFilterVO.getToDate().toDisplayDateOnlyFormat());
			}
			else{
			form.setToDate(BLANK);
			}
			listRateAuditSession.removeRateAuditFilterVO();
			listRateAuditSession.setFromScreen(BLANK);
			//rateAuditFilterVO=new RateAuditFilterVO();
		}
		else{

			 rateAuditFilterVO=new RateAuditFilterVO();

		}
		
		rateAuditFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		rateAuditFilterVO.setPageNumber(Integer.parseInt(form
				.getDisplayPageNum()));
		if(form.getDsn()!=null &&(form.getDsn()).trim().length()>0){

			rateAuditFilterVO.setDsn(form.getDsn());
		}
		if(form.getDsnDate()!=null &&(form.getDsnDate()).trim().length()>0){

			rateAuditFilterVO.setDsnDate(new LocalDate
			(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getDsnDate()));
		}
		if(form.getDsnStatus()!=null &&(form.getDsnStatus()).trim().length()>0){

			rateAuditFilterVO.setDsnStatus(form.getDsnStatus());

		}
		if(form.getCarrierCode()!=null &&(form.getCarrierCode()).trim().length()>0){
		/*
			* Validate the carrier code and obtain the carrierId
		*/
		int carrierId = validateCarrierCode(form, invocationContext,
					logonAttributes);
		log.log(Log.FINE, "carrierId", carrierId);
		if(carrierId>0){
			rateAuditFilterVO.setCarrierId(carrierId);
		}
		else{
			invocationContext.target = LIST_FAILURE;
			return;
		}

		}
		if(form.getGpaCode()!=null &&(form.getGpaCode()).trim().length()>0){
			/*
				* Validate the GpaCode  and obtain the GpaCode
			*/
			errors=new ArrayList<ErrorVO>();
			 errors = validateGpaCode(form,
						logonAttributes);
			 log.log(Log.FINE, "errors", errors);
			if(errors!=null&& errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;

			}
			else{

				rateAuditFilterVO.setGpaCode(form.getGpaCode());

			}

		}
		if(form.getSubClass()!=null &&(form.getSubClass()).trim().length()>0){
			/*
				* Validate the GpaCode  and obtain the GpaCode
			*/
			errors=new ArrayList<ErrorVO>();
			 errors = validateSubClass(form,
						logonAttributes);
			 log.log(Log.FINE, "errors", errors);
			if(errors!=null&& errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;

			}
			else{

				rateAuditFilterVO.setSubClass(form.getSubClass());

			}

		}


		if(form.getFlightNo()!=null &&(form.getFlightNo()).trim().length()>0){
			rateAuditFilterVO.setFlightNumber(form.getFlightNo());
		}
		if(form.getFlightDate()!=null &&(form.getFlightDate()).length()>0){
			rateAuditFilterVO.setFlightDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getFlightDate())));
		}
		if(form.getFromDate()!=null &&(form.getFromDate()).trim().length()>0){
			rateAuditFilterVO.setFromDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getFromDate())));
		}
		if(form.getToDate()!=null &&(form.getToDate()).trim().length()>0){
			rateAuditFilterVO.setToDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(form.getToDate())));
		}
		if(rateAuditFilterVO.getFromDate()!=null && rateAuditFilterVO.getToDate()!=null){
			errors=new ArrayList<ErrorVO>();
			if(rateAuditFilterVO.getToDate().isLesserThan(rateAuditFilterVO.getFromDate())){
				errors.add(new ErrorVO(FROMDATE_LESSTHAN_TODATE));
				invocationContext.addAllError(errors);
				listRateAuditSession.removeRateAuditVOs();
				invocationContext.target = LIST_FAILURE;
				return;
			}
		}
		/*if(form.getGpaCode()!=null &&(form.getGpaCode()).trim().length()>0){
			rateAuditFilterVO.setGpaCode(form.getGpaCode());
		}*/
		/*if(form.getSubClass()!=null &&(form.getSubClass()).trim().length()>0){
			rateAuditFilterVO.setSubClass(form.getSubClass());
		}
		*/

		log.log(Log.FINE, "rateAuditFilterVO", rateAuditFilterVO);
		listRateAuditSession.setRateAuditFilterVO(rateAuditFilterVO);



		try {
			rateAuditVOs=mailTrackingMRADelegate.findRateAuditDetails(rateAuditFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught Exception");
		}

		if(rateAuditVOs==null ||rateAuditVOs.size()==0){
			errors=new ArrayList<ErrorVO>();
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			listRateAuditSession.removeRateAuditVOs();
			invocationContext.target = LIST_FAILURE;
			return;
		}
		/*
		 * ADDED FOR BUG 27408
		 * START
		 */
		else if(rateAuditVOs.size()>0){
			for(RateAuditVO rateAuditVO : rateAuditVOs){
				if(rateAuditVO!=null){
					if(rateAuditVO.getAuditedWtCharge() != null && rateAuditVO.getPresentWtCharge() != null) {
						Double diffAmt = 0.0;
						log
								.log(
										Log.FINE,
										"rateAuditVO.getAuditedWtCharge().getAmount()----->",
										rateAuditVO.getAuditedWtCharge().getAmount());
						log
								.log(
										Log.FINE,
										"rateAuditVO.getPresentWtCharge().getAmount()----->",
										rateAuditVO.getPresentWtCharge().getAmount());
						diffAmt = rateAuditVO.getAuditedWtCharge().getRoundedAmount() - rateAuditVO.getPresentWtCharge().getRoundedAmount();
						if(diffAmt == 0.0){
							rateAuditVO.setDiscrepancyNo(YES);
						}else{
							rateAuditVO.setDiscrepancyNo(NO);
							Money discrp = null;
							try {
								discrp = CurrencyHelper.getMoney(CURRENCY_CODE);
								discrp.setAmount(diffAmt);
								rateAuditVO.setDiscrepancyYes(discrp);

							} catch (CurrencyException e) {
								log.log(Log.SEVERE,"\n\n******* CurrencyException Caught********!\n\n");
								e.getErrorCode();
							}
						}
					}
				}
			}
		}
		// END BUG 27408

		listRateAuditSession.setRateAuditVOs(rateAuditVOs);


		if("Y".equals(form.getRateauditFlag())){
			form.setRateauditFlag("");
			errors.add(new ErrorVO(RATEAUDIT_SUCCESSMSG));
	   		invocationContext.addAllError(errors);
		}
		invocationContext.target=LIST_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}


    /**
	 *
	 * @param form
	 * @param invocationContext
	 * @param logonAttributes
	 * @return
	 */
	private int validateCarrierCode(ListRateAuditForm form,
			InvocationContext invocationContext,
			LogonAttributes logonAttributes) {
		log.entering("ListCommand", "validateCarrierCode");
		int carrierId = 0;
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();

		try {
			airlineValidationVO =
				airlineDelegate.validateAlphaCode(
						logonAttributes.getCompanyCode(), form.getCarrierCode().toUpperCase());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "airlineValidationVO ---> ", airlineValidationVO);
		if(airlineValidationVO == null) {
			invocationContext.addError(
					new ErrorVO("shared.airline.invalidcarriercode",
					new String[]{form.getCarrierCode().toUpperCase()}));
		} else {
			carrierId = airlineValidationVO.getAirlineIdentifier();
		}
		log.log(Log.FINE, "carrierId ---> ", carrierId);
		log.exiting("ListCommand", "validateCarrierCode");
		return carrierId;
	}

	/**
	 * Method to validate form.
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateGpaCode(ListRateAuditForm form,
			LogonAttributes logonAttributes) {

		String gpaCode=form.getGpaCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(gpaCode != null || ("".equals(gpaCode.trim()))){

//    	validate PA code
	  	log.log(Log.FINE, "Going To validate GPA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
									logonAttributes.getCompanyCode(),gpaCode.toUpperCase());
					log.log(Log.FINE, "postalAdministrationVO",
							postalAdministrationVO);
					if(postalAdministrationVO == null) {
		  				Object[] obj = {gpaCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.mra.defaults.listrateaudit.gpacode.invalid",obj));
		  			}

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}
	/**
	 * Method to validate form.
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateSubClass(ListRateAuditForm form,
			LogonAttributes logonAttributes) {

		String subClass=form.getSubClass();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(subClass != null || ("".equals(subClass.trim()))){

//    	validate SubClass
	  	log.log(Log.FINE, "Going To validateSubClass ...in command");
	  	try{
			String validFlag=null;
			validFlag =  new MailTrackingMRADelegate().validateMailSubClass(logonAttributes.getCompanyCode(),subClass);
			log.log(Log.FINE, "validFlag", validFlag);
			if("N".equals(validFlag)){
				errors = new ArrayList<ErrorVO>();
    			Object[] obj = {form.getSubClass()};
    			errors.add(new ErrorVO("mailtracking.mra.defaults.listrateaudit.subclass.invalid",obj));

			}

		}catch (BusinessDelegateException businessDelegateException) {
     			errors = handleDelegateException(businessDelegateException);
     		}
		}
		return errors;
	}
	
	 /**
	 *
	 * @param form
	 * @param invocationContext
	 * @param logonAttributes
	 * @return
	 */
	private String validateCarrierId(RateAuditFilterVO rateAuditFilterVO,
			InvocationContext invocationContext,
			LogonAttributes logonAttributes) {
		log.entering("ListCommand", "validateCarrierCode");
		String carrierCode = null;
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();

		try {
			airlineValidationVO = airlineDelegate.findAirline(logonAttributes
					.getCompanyCode(), rateAuditFilterVO.getCarrierId());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "airlineValidationVO ---> ", airlineValidationVO);
		if(airlineValidationVO != null) {
			carrierCode = airlineValidationVO.getAlphaCode();
		} 
		log.log(Log.FINE, "carrierCode ---> ", carrierCode);
		log.exiting("ListCommand", "validateCarrierCode");
		return carrierCode;
	}

}
