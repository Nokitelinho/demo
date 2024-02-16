/*
 * ValidateCommand.java Created on Aug 26,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class ValidateCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS Rate Audit Details");

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String COMPUTETOTAL_VALIDATE_SUCCESS = "computetotal_validate_success";
	private static final String SAVE_VALIDATE_SUCCESS = "save_validate_success";
	private static final String RTAUD_VALIDATE_SUCCESS = "rtaud_validate_success";
	private static final String VALIDATE_FAILURE = "validate_failure";

	private static final String BILLING_PAR_CHANGED = "P";
	private static final String GROSS_WGT_CHANGED = "W";
	private static final String AUD_WGT_CAHRGE_CHANGED = "C";
	private static final String YES = "Y";
	private static final String NO = "N";

	private static final String AIRLINE = "A";
	private static final String GPA = "G";
	private static final String RECIEVEABLE = "R";
	private static final String RETENSION = "T";


	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ValidateCommand","execute");
		RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		RateAuditDetailsSession rateAuditDetailsSession=getScreenSession(MODULE,SCREENID);
		String parChangeFlag ="";		
		RateAuditVO rateAuditVO = new RateAuditVO ();
		rateAuditVO = rateAuditDetailsSession.getRateAuditVO();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;

		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		int billflag=0;




		if(rateAuditDetailsForm.getUpdWt()==null||"".equals(rateAuditDetailsForm.getUpdWt().trim())){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.grosswgtempty");				
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}

		if(rateAuditDetailsForm.getAuditWgtCharge()==null||"".equals(rateAuditDetailsForm.getAuditWgtCharge().trim())){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.audwtchrgempty");				
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}



		/*******************************************************************
		 *  validating subclass	
		 ******************************************************************/
		if(rateAuditDetailsForm.getSubClass()==null||"".equals(rateAuditDetailsForm.getSubClass().trim())){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.subclassempty");				
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}else{
			try{		
				String validFlag=null;
				validFlag = delegate.validateMailSubClass(logonAttributes.getCompanyCode(),rateAuditDetailsForm.getSubClass());	
				if(validFlag==NO){
					errors = new ArrayList<ErrorVO>();
					Object[] obj = {rateAuditDetailsForm.getSubClass()};
					ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.invalidsubclass",obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = VALIDATE_FAILURE;    			
					return;
				}

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

		}




		/*******************************************************************
		 *  validate carriercode using carriercode code from the form	
		 ******************************************************************/

		if(rateAuditDetailsForm.getFlightCarCod()==null||"".equals(rateAuditDetailsForm.getFlightCarCod().trim())){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.carcodeempty");				
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}

		if(rateAuditDetailsForm.getFlightNo()==null||"".equals(rateAuditDetailsForm.getFlightNo().trim())){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.fltnumempty");				
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}


		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AirlineValidationVO airlineValidationVOForflight = null;
		String flightCarrierCode = rateAuditDetailsForm.getFlightCarCod().trim().toUpperCase();
		if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
			try {
				airlineValidationVOForflight = airlineDelegate.validateAlphaCode(logonAttributes.getCompanyCode(),flightCarrierCode);   			

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				errors = new ArrayList<ErrorVO>();
				Object[] obj = {flightCarrierCode};
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.invalidcarrier",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = VALIDATE_FAILURE;    			
				return;
			}

		}


		/*******************************************************************
		 *  validate BILL TO 	
		 ******************************************************************/



		if(rateAuditDetailsForm.getBillTo()==null||"".equals(rateAuditDetailsForm.getBillTo().trim())){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.billtoempty");				
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = VALIDATE_FAILURE;
			return;
		}

		AirlineValidationVO airlineValidationVOForBillTo = null;
		flightCarrierCode = rateAuditDetailsForm.getBillTo().trim().toUpperCase();
		if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {

			if(logonAttributes.getOwnAirlineCode().equals(flightCarrierCode)){
				errors = new ArrayList<ErrorVO>();
				Object[] obj = {flightCarrierCode};
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.billtoownairline");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = VALIDATE_FAILURE;    			
				return;

			}else{
				try {
					airlineValidationVOForBillTo = airlineDelegate.validateAlphaCode(logonAttributes.getCompanyCode(),flightCarrierCode);
					billflag = 1;


				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				if (errors != null && errors.size() > 0) {
					billflag = 0;
					errors = new ArrayList<ErrorVO>();
					try{
						PostalAdministrationVO postalAdministrationVO =delegate.findPostalAdminDetails(logonAttributes.getCompanyCode(),flightCarrierCode);
						if(postalAdministrationVO==null){
							billflag = 0;
							errors = new ArrayList<ErrorVO>();
							Object[] obj = {flightCarrierCode};
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.invalidbillto",obj);
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
							invocationContext.addAllError(errors);
							invocationContext.target = VALIDATE_FAILURE;    			
							return;
						}
						billflag = 2;    			
					}catch(BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				}		
			}
		}
		Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();

		rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();

		int trecCount = 0;
		for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){

			rateAuditDetailsVO.setRecVDate(rateAuditVO.getDsnDate()); 

			if(( rateAuditDetailsVO.getApplyAudt()== null && !("".equals(rateAuditDetailsForm.getApplyAudit())))){
				log.log(Log.FINE, "ApplyAudit Edited");	

				if("on".equalsIgnoreCase(rateAuditDetailsForm.getApplyAudit())){
					rateAuditVO.setApplyAutd(YES);
					rateAuditDetailsVO.setApplyAudt(YES);
				}else{
					rateAuditVO.setApplyAutd(rateAuditDetailsForm.getApplyAudit());
					rateAuditDetailsVO.setApplyAudt(rateAuditDetailsForm.getApplyAudit());
				}	

			}else if( rateAuditDetailsVO.getApplyAudt()!= null && (!(rateAuditDetailsVO.getApplyAudt().equals(rateAuditDetailsForm.getApplyAudit())))){
				log.log(Log.FINE, "ApplyAudit Edited");	
				if(rateAuditDetailsForm.getApplyAudit()==null){
					rateAuditVO.setApplyAutd(NO);
					rateAuditDetailsVO.setApplyAudt(NO);
				}else if("on".equalsIgnoreCase(rateAuditDetailsForm.getApplyAudit())){
					rateAuditVO.setApplyAutd(YES);
					rateAuditDetailsVO.setApplyAudt(YES);
				}
			}		

			if(RECIEVEABLE.equals(rateAuditDetailsVO.getPayFlag())){		
				//update the vo with new edited values	  

				if(( rateAuditDetailsVO.getCategory() == null && !("".equals(rateAuditDetailsForm.getCategory())))){
					log.log(Log.FINE, "Category Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setCategory(rateAuditDetailsForm.getCategory());
					rateAuditDetailsVO.setCategory(rateAuditDetailsForm.getCategory());
				}else if( rateAuditDetailsVO.getCategory() != null && (!(rateAuditDetailsVO.getCategory().equals(rateAuditDetailsForm.getCategory())))){
					log.log(Log.FINE, "Category Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setCategory(rateAuditDetailsForm.getCategory());
					rateAuditDetailsVO.setCategory(rateAuditDetailsForm.getCategory());
				}

				if(( rateAuditDetailsVO.getSubclass()== null && !("".equals(rateAuditDetailsForm.getSubClass())))){
					log.log(Log.FINE, "SubClass Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setSubClass(rateAuditDetailsForm.getSubClass());
					rateAuditDetailsVO.setSubclass(rateAuditDetailsForm.getSubClass());
				}else if( rateAuditDetailsVO.getSubclass()!= null && (!(rateAuditDetailsVO.getSubclass().equals(rateAuditDetailsForm.getSubClass())))){
					log.log(Log.FINE, "SubClass Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setSubClass(rateAuditDetailsForm.getSubClass());
					rateAuditDetailsVO.setSubclass(rateAuditDetailsForm.getSubClass());
				}

				if(( rateAuditDetailsVO.getUldno()== null && !("".equals(rateAuditDetailsForm.getULD())) )){
					log.log(Log.FINE, "Uld Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setUld(rateAuditDetailsForm.getULD());
					rateAuditDetailsVO.setUldno(rateAuditDetailsForm.getULD());

				}else if( rateAuditDetailsVO.getUldno()!= null && (!(rateAuditDetailsVO.getUldno().equals(rateAuditDetailsForm.getULD())))){
					log.log(Log.FINE, "Uld Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setUld(rateAuditDetailsForm.getULD());
					rateAuditDetailsVO.setUldno(rateAuditDetailsForm.getULD());
				}

				if(( rateAuditDetailsVO.getCarrierCode()== null && !("".equals(rateAuditDetailsForm.getFlightCarCod()))  )){
					log.log(Log.FINE, "FlightCarCod Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditDetailsVO.setCarrierid(airlineValidationVOForflight.getAirlineIdentifier());
					rateAuditVO.setFlightCarCod(rateAuditDetailsForm.getFlightCarCod());
					rateAuditDetailsVO.setCarrierCode(rateAuditDetailsForm.getFlightCarCod());
				}else if(rateAuditDetailsVO.getCarrierCode()!= null && (!(rateAuditDetailsVO.getCarrierCode().equals(rateAuditDetailsForm.getFlightCarCod())))){
					log.log(Log.FINE, "FlightCarCod Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditDetailsVO.setCarrierid(airlineValidationVOForflight.getAirlineIdentifier());
					rateAuditVO.setFlightCarCod(rateAuditDetailsForm.getFlightCarCod());
					rateAuditDetailsVO.setCarrierCode(rateAuditDetailsForm.getFlightCarCod());
				}

				if(( rateAuditDetailsVO.getFlightno()== null && !("".equals(rateAuditDetailsForm.getFlightNo())))){
					log.log(Log.FINE, "FlightNumber Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setFlightNumber(rateAuditDetailsForm.getFlightNo());
					rateAuditDetailsVO.setFlightno(rateAuditDetailsForm.getFlightNo());
				}else if(rateAuditDetailsVO.getFlightno()!= null && (!(rateAuditDetailsVO.getFlightno().equals(rateAuditDetailsForm.getFlightNo())))){
					log.log(Log.FINE, "FlightNumber Edited");
					parChangeFlag=BILLING_PAR_CHANGED;
					rateAuditVO.setFlightNumber(rateAuditDetailsForm.getFlightNo());
					rateAuditDetailsVO.setFlightno(rateAuditDetailsForm.getFlightNo());
				}


				if(rateAuditDetailsVO.getGrsWgt()!=Double.parseDouble((rateAuditDetailsForm.getUpdWt()))){
					log.log(Log.FINE, "UpdWt Edited");
					parChangeFlag=parChangeFlag+GROSS_WGT_CHANGED;
					rateAuditVO.setUpdWt(rateAuditDetailsForm.getUpdWt());						
					rateAuditDetailsVO.setGrsWgt(Double.parseDouble(rateAuditDetailsForm.getUpdWt()));				
				}


				if((rateAuditDetailsVO.getAudtdWgtCharge().getRoundedAmount())!= Double.parseDouble(rateAuditDetailsForm.getAuditWgtCharge())){
					log.log(Log.FINE, "AuditedWtCharge Edited");
					log
							.log(
									Log.FINE,
									"rateAuditDetailsVO.getAudtdWgtCharge().getAmount()=>",
									rateAuditDetailsVO.getAudtdWgtCharge().getAmount());
					log.log(Log.FINE,
							"rateAuditDetailsForm.getAuditWgtCharge()==>>",
							rateAuditDetailsForm.getAuditWgtCharge());
					parChangeFlag=parChangeFlag+AUD_WGT_CAHRGE_CHANGED;
					Money auditedWtCharge = null;
					try {											
						auditedWtCharge = CurrencyHelper.getMoney("NZD");
						auditedWtCharge.setAmount(Double.parseDouble(rateAuditDetailsForm.getAuditWgtCharge()));
						log.log(Log.FINE, "auditedWtCharge)==>>",
								auditedWtCharge);
						rateAuditVO.setAuditedWtCharge(auditedWtCharge);							
					} catch (CurrencyException e) {
						log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
						e.getErrorCode();
					}		
					rateAuditDetailsVO.getAudtdWgtCharge().setAmount(Double.parseDouble(rateAuditDetailsForm.getAuditWgtCharge()));
				}

				if(( rateAuditDetailsVO.getBillTO()== null && !("".equals(rateAuditDetailsForm.getBillTo())) )){
					log.log(Log.FINE, "BillTO Edited");
					parChangeFlag=parChangeFlag+"B";
					if(billflag==1){
						rateAuditDetailsVO.setGpaarlBillingFlag(AIRLINE);
						rateAuditDetailsVO.setUpdBillToIdr(airlineValidationVOForBillTo.getAirlineIdentifier());
					}else if(billflag==2){
						rateAuditDetailsVO.setGpaarlBillingFlag(GPA);
						rateAuditDetailsVO.setUpdBillToIdr(0);
					}
					rateAuditVO.setBillTo(rateAuditDetailsForm.getBillTo());
					rateAuditDetailsVO.setBillTO(rateAuditDetailsForm.getBillTo()); 
					rateAuditDetailsSession.setBillToChgFlag("Y");

				}else if( rateAuditDetailsVO.getBillTO()!= null && (!(rateAuditDetailsVO.getBillTO().equals(rateAuditDetailsForm.getBillTo())))){
					log.log(Log.FINE, "BillTO Edited");
					parChangeFlag=parChangeFlag+"B";
					if(billflag==1){
						rateAuditDetailsVO.setGpaarlBillingFlag(AIRLINE);
						rateAuditDetailsVO.setUpdBillToIdr(airlineValidationVOForBillTo.getAirlineIdentifier());
					}else if(billflag==2){
						rateAuditDetailsVO.setGpaarlBillingFlag(GPA);
						rateAuditDetailsVO.setUpdBillToIdr(0);
					}
					rateAuditVO.setBillTo(rateAuditDetailsForm.getBillTo());
					rateAuditDetailsVO.setBillTO(rateAuditDetailsForm.getBillTo());
					rateAuditDetailsSession.setBillToChgFlag("Y");
				}		

			}else if(RETENSION.equals(rateAuditDetailsVO.getPayFlag())){
				trecCount=trecCount+1;	    		
			}			
		}
		//if only one T record is there then no need to go for computeProrateFactors in controler
		rateAuditVO.setTRecordCount(trecCount);



		//change weight for all record as it is applicable for the despatch    	
		if(parChangeFlag.contains(GROSS_WGT_CHANGED)){
			rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();		
			for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
				rateAuditDetailsVO.setGrsWgt(Double.parseDouble(rateAuditDetailsForm.getUpdWt()));    	
			}
		}

		//change weight charge for all R and P records For T records it will be done in controller
		if(parChangeFlag.contains(AUD_WGT_CAHRGE_CHANGED)){
			rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();		
			for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
				if(!RETENSION.equals(rateAuditDetailsVO.getPayFlag())){
					rateAuditDetailsVO.getAudtdWgtCharge().setAmount(rateAuditVO.getAuditedWtCharge().getAmount()); 
				}else{
					//----if only one T record is there then no need to go for computeProrateFactors 
					if(rateAuditVO.getTRecordCount()==1){
						rateAuditDetailsVO.getAudtdWgtCharge().setAmount(rateAuditVO.getAuditedWtCharge().getAmount());
					}
				}
			}
		}

		//update session
		rateAuditDetailsSession.setRateAuditVO(rateAuditVO);
		if(rateAuditDetailsSession.getParChangeFlag().contains(NO)){
			rateAuditDetailsSession.setParChangeFlag(parChangeFlag);
		}
		//check from where validate is called
		if("CMPTOTBTN".equals(rateAuditDetailsForm.getValidateFrom())){			
			invocationContext.target = COMPUTETOTAL_VALIDATE_SUCCESS;
		}else if("SAVEBTN".equals(rateAuditDetailsForm.getValidateFrom())){			
			invocationContext.target = SAVE_VALIDATE_SUCCESS;
		}else if("RTAUDBTN".equals(rateAuditDetailsForm.getValidateFrom())){			
			invocationContext.target = RTAUD_VALIDATE_SUCCESS;
		}

		log.exiting("ValidateCommand","execute");

	}


}
		




