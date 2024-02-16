/* SaveCCACommand.java Created on July-22, 2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 * @author A-3447
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.text.NumberFormat;
import java.text.ParseException;
/**
 * @author A-3447
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.currency.vo.ExchangeRateFilterVO;
import com.ibsplc.icargo.business.shared.currency.vo.ExchangeRateParameterMasterVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;

/**
 * @author A-3447
 * Command class for listing.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 22-July-2008 Muralee(a-3447) For CRQ_172
 */
public class SaveCCACommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "SaveCCADetailsCommand";

	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * target action
	 */
	private static final String SAVE_SUCCESS = "save_success";

	/**
	 * Save _FAILURE
	 */
	private static final String SAVE_FAILURE = "save_failure";

	/**
	 * validation
	 */
	private static final String ERROR_MANDATORY = "mailtracking.mra.defaults.maintaincca.dsnnomandatory";
	private static final String SAVE_SUCCESS_INFO = "mailtracking.mra.defaults.rateauditdetails.saveinfo";
	private static final String NO = "N";
	private static final String YES = "Y";
	private static final String UPDATE = "U";
	private static final String INSERT = "I";
	private static final String DELETE = "D";
	private static final String AIRLINE = "A";
	private static final String GPA = "G";
	private static final String BASED_ON_RULES = "R";//Added for IASCB-2373
	
	private static final String ACTUAL = "A";
	private static final String BILLABLE = "BB";
	private static final String BILLED = "BD";
	private static final String OUTWARD_BILLABLE = "OB";
	private static final String RECEIVEABLE = "R";
	private static final String PAYABLE = "P";
	private static final String INWARD_UNUTILISED = "IU";
	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String APPROVED = "A";
	private static final String DELETED = "D";
	private static final String REJECTED = "R";
	private static final String INVALID_CURCODE = "mailtracking.mra.defaults.maintaincca.invalidcurcode";
	private static final String INVALID_CCATYPE="mailtracking.mra.defaults.maintaincca.invalidccatype";
	private static final String INVALID_INTERNALMCA="mailtracking.mra.defaults.maintaincca.invalidinternalmca";//Added as part of ICRD-115138
	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	private static final String MRA_SUB_SYSTEM = "M";
	private static final String FLOWN_FUNCTION_POINT = "FM";
	private static final String MAIL_FLOWN_ACTUALMCA_FUNCTION_POINT = "FMA";
	private static final String INTERNAL_FUNCTION_POINT = "IA";
	private static final String NO_EXCHANGE_RATE_PAR="mailtracking.mra.maintaincca.exchangeratenotavailable";
	private static final String ERROR_ONHOLD = "mailtracking.mra.defaults.maintaincca.onholdmailbags";
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";//Added by A-6991 for ICRD-213422
	private static final String CURR_CHG_FOR_BILLED = "mailtracking.mra.defaults.maintaincca.currencychange";
	private static final String CONVERSION_RATE_NOTFOUND = "mailtracking.mra.defaults.maintaincca.conversionratenotfound";
	

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;

		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		PostalAdministrationVO postalAdministrationVO = null;

		Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs=null;
		Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOForAutoMca=maintainCCASession.getSurchargeCCAdetailsVOs();
		Collection<DocumentBillingDetailsVO> documentBillingDetailsVO = maintainCCASession.getDocumentBillingDetailsVOs(); //Added by A-7929 as part icrd-132548
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = maintainCCASession.getGPABillingEntriesFilterVO();//Added by A-7540
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		CCAdetailsVO ccaDetailsVO = maintainCCASession.getCCAdetailsVO();
		ccaDetailsVO.setCcaRemark(maintainCCAForm.getRemarks());
		//ccaDetailsVO.setDocumentBillingDetailsVO(documentBillingDetailsVO); //Added by A-7929 as part icrd-132548		
		ccaDetailsVO.setOverrideRounding(maintainCCASession.getSystemparametres().get(SYS_PAR_OVERRIDE_ROUNDING));//Added by A-6991 for ICRD-213422
		maintainCCASession.getCCAdetailsVOs();
		ccaDetailsVO.setSurchargeCCAdetailsVOs(surchargeCCAdetailsVOForAutoMca);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String conversionErrorToCurrCode=null;
		
		//added for ICRD-346925 begin
		if (maintainCCAForm.getRevCurCode()!=null && maintainCCAForm.getRevCurCode().trim().length() > 0){
			try {
				conversionErrorToCurrCode = mailTrackingMRADelegate.validateCurrConversion(maintainCCAForm.getRevCurCode());
			} catch (BusinessDelegateException e) {
				e.printStackTrace();
			}
			if(conversionErrorToCurrCode!=null && conversionErrorToCurrCode.length()>0){
				Object[] obj = {maintainCCAForm.getRevCurCode(),conversionErrorToCurrCode};
				errors.add(new ErrorVO(CONVERSION_RATE_NOTFOUND,obj));
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE; 
				return;
				
			}
		}
		//ICRD-346925 end
		
		//Added by A-7929 as part of ICRD-132548 starts......
		
			if("listbillingentries".equals(maintainCCAForm.getFromScreen())||"listbillingentriesuxopenPopUp".equals(maintainCCAForm.getFromScreen())){
				if("BB".equals(ccaDetailsVO.getBillingStatus())||"BILLABLE".equals(ccaDetailsVO.getBillingStatus())){
					if (maintainCCAForm.getRevCurCode()!=null && maintainCCAForm.getRevCurCode().trim().length() > 0){
					 Collection<ErrorVO> errs = validateRevCurCodForAutoMCA(maintainCCAForm,logonAttributes.getCompanyCode());
					if (errs != null && errs.size() > 0) {
						errors.add(new ErrorVO(INVALID_CURCODE));
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE; 
						return;
					}
				}
				}
				
				
				int changesDoneForAutoMCA = new SaveCCACommand().multiModificationRestrictionForAutoMCA(maintainCCAForm,ccaDetailsVO,documentBillingDetailsVO);
				if(changesDoneForAutoMCA > 1){
					updateFormForAutoMCA(maintainCCAForm,maintainCCASession);
					ErrorVO displayErrorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.multichangerestrict");
					displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(displayErrorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
					
				}
				
				String gpaCode =maintainCCAForm.getRevGpaCode();
				errors = new ArrayList<ErrorVO>();
				if (gpaCode != null && !"".equals(gpaCode.trim())) {
					try{
						postalAdministrationVO =mailTrackingMRADelegate.findPostalAdminDetails(logonAttributes.getCompanyCode(),gpaCode);
						if(postalAdministrationVO==null){
							updateFormForAutoMCA(maintainCCAForm,maintainCCASession);
							errors = new ArrayList<ErrorVO>();
							Object[] obj = {gpaCode};
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.invalidgpacode",obj);
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;    			
							return;
						}	else{
							if(postalAdministrationVO.getStatus()!=null&&!"".equals(postalAdministrationVO.getStatus())&& "ACTIVE".equalsIgnoreCase(postalAdministrationVO.getStatus())){
								log.log(Log.FINE,"ACTIVE GPA!!");
							}else{
								updateFormForAutoMCA(maintainCCAForm,maintainCCASession);
								errors = new ArrayList<ErrorVO>();
								Object[] obj = {gpaCode};
								ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.gpacodenotactive",obj);
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
								invocationContext.addAllError(errors);
								invocationContext.target = SAVE_FAILURE;    			
								return;					
								
							}
						}

					}catch(BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				}
				//populateChangeIndicatorForAutoMCA(ccaDetailsVO,maintainCCAForm);
				try {
					if(!checkWorkFlowEnabled()){
						ccaDetailsVO.setCcaStatus(APPROVED);
					}
				} catch (SystemException e) {
					
					log.log(Log.FINE,  "Sys.Excptn ");
				}
				Collection <CCAdetailsVO> cCAdetailsVOs = populateAttributesForAutoMCA(documentBillingDetailsVO,ccaDetailsVO,maintainCCAForm);
				String ccaRefNo = null;
				
				try {
					//ccaRefNo = 
							mailTrackingMRADelegate.saveAutoMCAdetails(cCAdetailsVOs,gpaBillingEntriesFilterVO);
					//maintainCCASession.setRefNoToDisp(ccaRefNo);			

				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);

				}			
				/*maintainCCAForm.setAfterSave("NAVIGATIONSAVE");
				invocationContext.target = SAVE_SUCCESS;*/
				
				if ("listbillingentries".equals(maintainCCAForm.getFromScreen())||"listbilling".equals(maintainCCAForm.getFromScreen())){
					maintainCCAForm.setAfterSave("NAVIGATIONSAVE");
				}
				else if ("listbillingentriesuxopenPopUp".equals(maintainCCAForm.getFromScreen()))
						{
					maintainCCAForm.setAfterSave("NAVIGATIONSAVESUCCESS");
						}
					invocationContext.target = SAVE_SUCCESS;
					
					}
				
				
				
				
				//Added by A-7929 as part of ICRD-132548 ends......
		
		
		
		
	else{
		Collection<ErrorVO> errs = validateRevCurCod(maintainCCAForm,logonAttributes.getCompanyCode());
		if (errs != null && errs.size() > 0) {
			errors.add(new ErrorVO(INVALID_CURCODE));
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		//Added by A-7794 as part of ICRD-290058
		if(MRAConstantsVO.BILLED.equals(ccaDetailsVO.getBillingStatus()) && !ccaDetailsVO.getContCurCode().equals(maintainCCAForm.getRevCurCode())){
			errors.add(new ErrorVO(CURR_CHG_FOR_BILLED));
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		if(maintainCCAForm.getCcaType()==null||maintainCCAForm.getCcaType().trim().length()==0){
			errors.add(new ErrorVO(INVALID_CCATYPE));
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;	
		}	
		if(MRAConstantsVO.ONHOLD.equals(ccaDetailsVO.getBillingStatus())){
			errors.add(new ErrorVO(ERROR_ONHOLD));
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;	
		}	
		int changesDone = new SaveCCACommand().multiModificationRestriction(maintainCCAForm,ccaDetailsVO);
		if(changesDone > 1){
			updateForm(maintainCCAForm,maintainCCASession);
			ErrorVO displayErrorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.multichangerestrict");
			displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(displayErrorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		//Added by A-5219 for ICRD-67967 start
		boolean isExchangeParExist = isExchangeRateParExist(logonAttributes.getCompanyCode(), maintainCCAForm.getCcaType());
		if(!isExchangeParExist){
			updateForm(maintainCCAForm,maintainCCASession);
			ErrorVO displayErrorVO = new ErrorVO(NO_EXCHANGE_RATE_PAR);
			displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(displayErrorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		//Added by A-5219 for ICRD-67967 end
		ErrorVO displayErrorVO = null;
		if (maintainCCAForm.getDsnNumber() == null) {
			updateForm(maintainCCAForm,maintainCCASession);
			displayErrorVO = new ErrorVO(ERROR_MANDATORY);
			displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(displayErrorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}




		//validating the revised destination
		String destination=maintainCCAForm.getRevDStCode();

		/*if (destination == null || "".equals(destination.trim())) {
			updateForm(maintainCCAForm,maintainCCASession);
			errors = new ArrayList<ErrorVO>();			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.destblank");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;	

		}*/




		AreaDelegate areaDelegate = new AreaDelegate();		
		AirportValidationVO airportValidationVO = null;
		if (destination != null && !"".equals(destination.trim())) {
			try {
				airportValidationVO = areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(),destination.toUpperCase());
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				updateForm(maintainCCAForm,maintainCCASession);
				errors = new ArrayList<ErrorVO>();
				Object[] obj = {destination};
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.invalidairportcode",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;    			
				return;
			}
		}

	



		if(ccaDetailsVO!=null){		
			if(ccaDetailsVO.getUpdBillToIdr()==0){
				//validating the revised GPA Code 	
				String gpaCode =maintainCCAForm.getRevGpaCode();

				if (gpaCode == null || "".equals(gpaCode.trim())) {
					updateForm(maintainCCAForm,maintainCCASession);
					errors = new ArrayList<ErrorVO>();			
					ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.gpacodeblank");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;    			
					return;	

				}


				errors = new ArrayList<ErrorVO>();
				if (gpaCode != null && !"".equals(gpaCode.trim())) {
					try{
						postalAdministrationVO =mailTrackingMRADelegate.findPostalAdminDetails(logonAttributes.getCompanyCode(),gpaCode);
						if(postalAdministrationVO==null){
							updateForm(maintainCCAForm,maintainCCASession);
							errors = new ArrayList<ErrorVO>();
							Object[] obj = {gpaCode};
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.invalidgpacode",obj);
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;    			
							return;
						}	else{
							if(postalAdministrationVO.getStatus()!=null&&!"".equals(postalAdministrationVO.getStatus())&& "ACTIVE".equalsIgnoreCase(postalAdministrationVO.getStatus())){
								log.log(Log.FINE,"ACTIVE GPA!!");
							}else{
								updateForm(maintainCCAForm,maintainCCASession);
								errors = new ArrayList<ErrorVO>();
								Object[] obj = {gpaCode};
								ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.gpacodenotactive",obj);
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(errorVO);
								invocationContext.addAllError(errors);
								invocationContext.target = SAVE_FAILURE;    			
								return;					
							}

						}



					}catch(BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
				}

			}

			if(ccaDetailsVO.getCcaRefNumber()==null||"".equals(ccaDetailsVO.getCcaRefNumber().trim())){
				ccaDetailsVO.setOperationFlag(INSERT);				

			}else{
				ccaDetailsVO.setOperationFlag(UPDATE);						
			}

			if(YES.equals(maintainCCASession.getUsrCCANumFlg())){
				ccaDetailsVO.setUsrccanum(maintainCCAForm.getCcaNum());				
			}

			if(ccaDetailsVO.getUpdBillToIdr()== 0){
				//ccaDetailsVO.setRevDuePostDbt((Double.parseDouble(maintainCCAForm.getRevDuePostDbt())));	
				ccaDetailsVO.setGpaArlIndicator(GPA);							
			}
			else {
				if(maintainCCAForm.getRevDueArl()!=null){					
					ccaDetailsVO.getRevDueArl().setAmount(Double.parseDouble(maintainCCAForm.getRevDueArl()));
					ccaDetailsVO.setGpaArlIndicator(AIRLINE);
				}
									
			}

			ccaDetailsVO.setIssuingParty(maintainCCAForm.getIssueParty());
			if(maintainCCAForm.getAirlineCode()!=null && maintainCCAForm.getAirlineCode().trim().length()>0){
				ccaDetailsVO.setAirlineCode(maintainCCAForm.getAirlineCode());	
			}
			ccaDetailsVO.setLocation(maintainCCAForm.getAirlineLoc());

			if(!APPROVED.equals(ccaDetailsVO.getCcaStatus())&&!DELETED.equals(ccaDetailsVO.getCcaStatus())
					&&!REJECTED.equals(ccaDetailsVO.getCcaStatus())){
				ccaDetailsVO.setCcaStatus(maintainCCAForm.getCcaStatus());
			}
			/*else if(!REJECTED.equals(ccaDetailsVO.getCcaStatus())&& !ACTUAL.equals(ccaDetailsVO.getCcaStatus())){
				ccaDetailsVO.setCcaType(maintainCCAForm.getCcaType());	
			}	*/
			ccaDetailsVO.setCcaType(maintainCCAForm.getCcaType());
			//Stamping cca status for actual cca 
			if(ccaDetailsVO.getCcaStatus()!=null && ACTUAL.equals(ccaDetailsVO.getCcaStatus())){

				if(ccaDetailsVO.getGpaArlIndicator()!=null){
					if(GPA.equals(ccaDetailsVO.getGpaArlIndicator())){
						//gpa is being billed
						ccaDetailsVO.setCcaStatus(BILLABLE);						
					}else if(AIRLINE.equals(ccaDetailsVO.getGpaArlIndicator())){
						if(logonAttributes.getOwnAirlineCode().equalsIgnoreCase(ccaDetailsVO.getAirlineCode())){
							//own airline is billing to --->>
							if(ccaDetailsVO.getUpdBillToIdr()==0){
								//billed to a GPA
								ccaDetailsVO.setCcaStatus(BILLABLE);								  
							}else{
								//billed to an airline
								ccaDetailsVO.setCcaStatus(OUTWARD_BILLABLE);
							}							   
						}else{
							//other airline is issuing cca
							if(RECEIVEABLE.equals(ccaDetailsVO.getPayFlag())){
								//other airline is being billed
								ccaDetailsVO.setCcaStatus(OUTWARD_BILLABLE);
							}else if(PAYABLE.equals(ccaDetailsVO.getPayFlag())){
								//Own airline is being billed by other airline
								ccaDetailsVO.setCcaStatus(INWARD_UNUTILISED);								  
							}							  
						}						
					}				
				}				
			}


			ccaDetailsVO.setAutorateFlag(ccaDetailsVO.FLAG_NO);
			if (maintainCCAForm
					.getRevGrossWeight()!=null)
			{
			ccaDetailsVO.setRevGrossWeight(Double.parseDouble(maintainCCAForm
					.getRevGrossWeight()));
			}
			if(ccaDetailsVO.getRevChgGrossWeight()!=null)
			{
			ccaDetailsVO.getRevChgGrossWeight().setAmount(Double
					.parseDouble(maintainCCAForm.getRevChgGrossWeight()));
			}
			//Modified by A-8331 as part of ICRD-298948
	      try {
				ccaDetailsVO.getOtherChgGrossWgt().setAmount(NumberFormat.getInstance().parse(maintainCCAForm.getOtherChgGrossWgt()).doubleValue());
			} catch (ParseException e1) {
				log.log(Log.FINE,"PARSE EXCEPTION!!");
				
			}
			if(maintainCCAForm.getRevisedRate()!=null && maintainCCAForm.getRevisedRate().trim().length() > 0){
			ccaDetailsVO.setRevisedRate((Double
					.parseDouble(maintainCCAForm.getRevisedRate())));//Added by A-7929 as part of ICRD-132548
			}
			if(!"".equals(maintainCCAForm.getOtherRevChgGrossWgt())){
				Money revChgGrossWeight = null;
				try {
					revChgGrossWeight = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
				} catch (CurrencyException e) {
					log.log(Log.FINE,"CURRENCY EXCEPTION!!");
				}
				revChgGrossWeight.setAmount(Double.parseDouble(maintainCCAForm.getOtherRevChgGrossWgt()));					
				ccaDetailsVO.setOtherRevChgGrossWgt(revChgGrossWeight);	
			}
			ccaDetailsVO.setRevOrgCode(maintainCCAForm.getRevOrgCode());
			ccaDetailsVO.setRevDStCode(maintainCCAForm.getRevDStCode());			

			ccaDetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
			ccaDetailsVO.setRevGpaName(maintainCCAForm.getRevGpaName());
			//Added for CRQ-7352

			ccaDetailsVO.setRevContCurCode(maintainCCAForm.getRevCurCode());
			
			ccaDetailsVO.setRevTax(Double.parseDouble(maintainCCAForm.getRevTax()));

			ccaDetailsVO.setCcaRemark(maintainCCAForm.getRemarks());
			Collection<CRAParameterVO> cRAParameterVOs = maintainCCASession.getCRAParameterVOs();
			StringBuilder str=new StringBuilder();
				
					if(maintainCCAForm.getReasonCheck() != null && maintainCCAForm.getReasonCheck().length > 0){
						String[] reasonCheck = maintainCCAForm.getReasonCheck();
						for(int i=0 ; i < maintainCCAForm.getReasonCheck().length ; ++i){
							if(!("").equals(reasonCheck[i])){
								if(str.length()>1){
									str.append(",");
								}
							str=str.append(reasonCheck[i]);
							}
							}
						ccaDetailsVO.setMcaReasonCodes(str.toString());	
					}

			//Added by A-7929 as part of ICRD-277509
			if(maintainCCAForm.getIsAutoMca()!=null && maintainCCAForm.getIsAutoMca().trim().length() >0){
				maintainCCAForm.setIsAutoMca(YES);
			}
			else
			maintainCCAForm.setIsAutoMca(NO);
			
			ccaDetailsVO.setAutoMca(maintainCCAForm.getIsAutoMca());
			

			double netAmount = 0.0;
			Money netAmt;
			try { 
				netAmt = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
				netAmount=ccaDetailsVO.getRevChgGrossWeight().getAmount()+ccaDetailsVO.getOtherRevChgGrossWgt().getAmount()+ ccaDetailsVO.getRevTax() - ccaDetailsVO.getRevTds();
				netAmt.setAmount(netAmount);
				ccaDetailsVO.setRevNetAmount(netAmt);
				//Modified by A-7794 as part of ICRD-299050
				ccaDetailsVO.setRevDueArl(netAmt);
			} catch (CurrencyException excep) {
				log.log(Log.FINE,"Inside CurrencyException.. ");
			}
			if(ccaDetailsVO.getIssueDate()==null){
				ccaDetailsVO.setIssueDate( new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toDisplayDateOnlyFormat());
			}
			log.log(Log.SEVERE, "\n\nCCA IssueDate !!!!\n\n", ccaDetailsVO.getIssueDate());
			/**
			 * populating SurchargeCCAdetailsVOs from session
			 * Modified as part of ICRD-153858
			 * Populating surcharge details for all mca cases,For all mca's entry into MTKMRACCACHGDTL
			 */
			//Added for ICRD-153858 starts
			Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs = null;
			surchargeCCAdetailsVOs=maintainCCASession.getSurchargeCCAdetailsVOs();
			if(surchargeCCAdetailsVOs==null || surchargeCCAdetailsVOs.size()==0){
				Collection<String> codes = new ArrayList<String>();
				codes.add(BASE_CURRENCY);
				Map<String, String> results = new HashMap<String, String>();
				try {
					results = new SharedDefaultsDelegate()
							.findSystemParameterByCodes(codes);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				String baseCurrency = results.get(BASE_CURRENCY);
				MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
				maintainCCAFilterVO = maintainCCASession.getMaintainCCAFilterVO();
				if (maintainCCAFilterVO != null) {
					maintainCCAFilterVO.setBaseCurrency(baseCurrency);
					if (maintainCCAFilterVO.getCcaReferenceNumber() != null) {
						try {
							surchargeCCAdetailsVOs = mailTrackingMRADelegate
									.getSurchargeCCADetails(maintainCCAFilterVO);
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
					} else {
						try {
							surchargeProrationDetailsVOs = mailTrackingMRADelegate
									.viewSurchargeProrationDetailsForMCA(populateProrationFilterVO(maintainCCAFilterVO));
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						surchargeCCAdetailsVOs = convertToSurchargeCCAdetailsVOs(
								surchargeProrationDetailsVOs, maintainCCAForm);
						if((surchargeProrationDetailsVOs==null||surchargeProrationDetailsVOs.size()==0)&&maintainCCAFilterVO.isApprovedMCAExists()){
							ArrayList<String> allCCARefNumbers=(ArrayList<String>)maintainCCASession.getCCARefNumbers();
							if(allCCARefNumbers!=null&&allCCARefNumbers.size()>0){
							maintainCCAFilterVO.setCcaReferenceNumber(allCCARefNumbers.get(allCCARefNumbers.size()-1));
								try{
								surchargeCCAdetailsVOs=mailTrackingMRADelegate.
										getSurchargeCCADetails(maintainCCAFilterVO);
								}catch (BusinessDelegateException businessDelegateException) {
									errors = handleDelegateException(businessDelegateException);
								}
							}
							maintainCCAFilterVO.setCcaReferenceNumber(null);
						}
					}
					maintainCCASession
							.setSurchargeCCAdetailsVOs((ArrayList<SurchargeCCAdetailsVO>) surchargeCCAdetailsVOs);
				}
				}
			//Added for ICRD-153858 ends
			if(surchargeCCAdetailsVOs!=null && surchargeCCAdetailsVOs.size()>0){
				ccaDetailsVO.setSurchargeCCAdetailsVOs(surchargeCCAdetailsVOs);
			}
			//Modified for ICRD-145267
			populateChangeIndicator(ccaDetailsVO);
			//Added as part of ICRD-115138
			if("I".equals(ccaDetailsVO.getCcaType())){
				int change = validateFormForInternalMCA(ccaDetailsVO);
				if(change>0){
					errors.add(new ErrorVO(INVALID_INTERNALMCA));
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
				}
			}
			/*if(!YES.equals(maintainCCAForm.getReason1())&&!YES.equals(ccaDetailsVO.getCurrChangeInd())){
				ccaDetailsVO.setCurrChangeInd(NO);

			}else{
				ccaDetailsVO.setCurrChangeInd(YES);	
			}

			if(!YES.equals(maintainCCAForm.getReason2())&&!YES.equals(ccaDetailsVO.getGpaChangeInd())){
				ccaDetailsVO.setGpaChangeInd(NO);

			}else{
				ccaDetailsVO.setGpaChangeInd(YES);	
			}

			if(!YES.equals(maintainCCAForm.getReason3())&&!YES.equals(ccaDetailsVO.getGrossWeightChangeInd())){
				ccaDetailsVO.setGrossWeightChangeInd(NO);

			}else{
				ccaDetailsVO.setGrossWeightChangeInd(YES);	
			}
			if(!YES.equals(maintainCCAForm.getReason4())&&!YES.equals(ccaDetailsVO.getWeightChargeChangeInd())){
				ccaDetailsVO.setWeightChargeChangeInd(NO);

			}else{
				ccaDetailsVO.setWeightChargeChangeInd(YES);	
			}*/




			log.log(Log.FINE, "CcaRefNumber before delegate Call--->",
					ccaDetailsVO.getCcaRefNumber());
			log.log(Log.FINE, "Usrccanum before delegate Call--->",
					ccaDetailsVO.getUsrccanum());
			log.log(Log.FINE, "Vos before delegate Call--->", ccaDetailsVO);
			if(DELETE.equals(ccaDetailsVO.getOperationFlag())){
				maintainCCASession.setStatusinfo("DELETE");					
			}else if(INSERT.equals(ccaDetailsVO.getOperationFlag())){
				maintainCCASession.setStatusinfo("INSERT");					
			}else if(UPDATE.equals(ccaDetailsVO.getOperationFlag())){
				maintainCCASession.setStatusinfo("UPDATE");				
			}
			ccaDetailsVO.setBillingStatus(BILLABLE);
			String ccaRefNo = null;
			try {
				if(!checkWorkFlowEnabled()){
					ccaDetailsVO.setCcaStatus(APPROVED);
				}
			} catch (SystemException e) {
				
				log.log(Log.FINE,  "Sys.Excptn ");
			}
			try {
				ccaRefNo = mailTrackingMRADelegate.saveMCAdetails(ccaDetailsVO);
				maintainCCASession.setRefNoToDisp(ccaRefNo);			

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);

			}			
			maintainCCAForm.setAfterSave(YES);
			invocationContext.target = SAVE_SUCCESS;

		}
		else{
			ccaDetailsVO=populateCCADetailsVO(maintainCCAForm,ccaDetailsVO);
			ccaDetailsVO.setBillingStatus(BILLABLE);
			String ccaRefNo = null;
			try {
				if(!checkWorkFlowEnabled()){
					ccaDetailsVO.setCcaStatus(APPROVED);
				}
			} catch (SystemException e) {
				
				log.log(Log.FINE,  "Sys.Excptn");
			}
			try {
				ccaRefNo = mailTrackingMRADelegate.saveMCAdetails(ccaDetailsVO);
				maintainCCASession.setRefNoToDisp(ccaRefNo);			

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);

			}			
			maintainCCAForm.setAfterSave(YES);
			invocationContext.target = SAVE_SUCCESS;
		}
	}
	}
	/**
	 * 
	 * 	Method		:	SaveCCACommand.populateAttributesForAutoMCA
	 *	Added by 	:	A-7929 on 26-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param documentBillingDetailsVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CCAdetailsVO>
	 * @param ccaDetailsVO 
	 * @param maintainCCAForm 
	 */
	private Collection<CCAdetailsVO> populateAttributesForAutoMCA(Collection<DocumentBillingDetailsVO> documentBillingDetailsVO, CCAdetailsVO ccaDetailsVO, MRAMaintainCCAForm maintainCCAForm) {
		
		Collection<CCAdetailsVO> detailsVOs = new ArrayList<CCAdetailsVO>(); 
		
		for(DocumentBillingDetailsVO documentBillingDetailsVOs : documentBillingDetailsVO){
		
			if(!"VD".equals(documentBillingDetailsVOs.getBillingStatus())){//Added this check as part of IASCB-20935 for A-5219
			CCAdetailsVO detailsVO = new CCAdetailsVO() ;			  
			detailsVO.setCompanyCode(documentBillingDetailsVOs.getCompanyCode());
			detailsVO.setBillingBasis(documentBillingDetailsVOs.getBillingBasis());
			detailsVO.setMailSequenceNumber(documentBillingDetailsVOs.getMailSequenceNumber());
			//detailsVO.setCsgSequenceNumber(documentBillingDetailsVOs.getCsgSequenceNumber());
			//detailsVO.setCsgDocumentNumber(documentBillingDetailsVOs.getCsgDocumentNumber());
			//detailsVO.setPoaCode(	documentBillingDetailsVOs.getPoaCode())	;
			detailsVO.setOperationFlag(INSERT);
			if((documentBillingDetailsVOs.getCcaRefNumber()!=null) && (documentBillingDetailsVOs.getCcaRefNumber().trim().length() > 0)){
				detailsVO.setOperationFlag(UPDATE);
			}
			detailsVO.setSurchargeCCAdetailsVOs(ccaDetailsVO.getSurchargeCCAdetailsVOs());
			detailsVO.setCcaRemark(ccaDetailsVO.getCcaRemark());
			detailsVOs.add(detailsVO);
			}
		}
		String ccaStatus =null;
		Double revisedRate = Double.parseDouble((maintainCCAForm.getRevisedRate()));           
		String revgpaCode = maintainCCAForm.getRevGpaCode();
		String revCurr = maintainCCAForm.getRevCurCode();
		if(ccaDetailsVO.getCcaStatus() != APPROVED){
		 ccaStatus = maintainCCAForm.getCcaStatus();
		}  
		else 
		ccaStatus = ccaDetailsVO.getCcaStatus();	
			  
		
		
		for(CCAdetailsVO ccadetailsVOs: detailsVOs){
			//ccadetailsVOs.setRateChangeInd("N");
			//ccadetailsVOs.setGpaChangeInd("N");
			//ccadetailsVOs.setCurrChangeInd("N");
			
			if(revisedRate != 0  ){
			ccadetailsVOs.setRevisedRate(revisedRate);
			//ccadetailsVOs.setRateChangeInd("Y"); 
			}
			if((revgpaCode != null) && (revgpaCode.trim().length() > 0) ){
			ccadetailsVOs.setRevGpaCode(revgpaCode);
			//ccadetailsVOs.setGpaChangeInd("Y");
			}
			if((revCurr != null) && (revCurr.trim().length() > 0) ){
			ccadetailsVOs.setRevContCurCode(revCurr);
			//ccadetailsVOs.setCurrChangeInd("Y");
			}
			if((ccaStatus != null) && (ccaStatus.trim().length() > 0) ){
				ccadetailsVOs.setCcaStatus(ccaStatus);
				}
		}
		return detailsVOs;
	}
	/**
	 * 
	 * 	Method		:	SaveCCACommand.validateRevCurCodForAutoMCA
	 *	Added by 	:	A-7929 on 25-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param maintainCCAForm
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateRevCurCodForAutoMCA(MRAMaintainCCAForm maintainCCAForm, String companyCode) {
		try {
			new CurrencyDelegate().validateCurrency(companyCode, maintainCCAForm
					.getRevCurCode().trim().toUpperCase());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "getRevCurCode not valid....");
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}
	/**
	 * 
	 * 	Method		:	SaveCCACommand.populateChangeIndicatorForMCA
	 *	Added by 	:	A-7929 on 25-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param ccaDetailsVO 
	 *	Return type	: 	void
	 * @param maintainCCAForm 
	 */
	/*private void populateChangeIndicatorForAutoMCA(CCAdetailsVO ccaDetailsVO, MRAMaintainCCAForm maintainCCAForm) {
		
		if((maintainCCAForm.getRevisedRate() !=null) && (maintainCCAForm.getRevisedRate().trim().length() > 0)){
			ccaDetailsVO.setRateChangeInd(NO);
			if(ccaDetailsVO.getRevisedRate() != (ccaDetailsVO.getRate())){
				ccaDetailsVO.setRateChangeInd(YES);
			}
		}
		if((maintainCCAForm.getRevCurCode()!=null) && (maintainCCAForm.getRevCurCode().trim().length() > 0)){
			ccaDetailsVO.setCurrChangeInd(NO);
			if(!ccaDetailsVO.getRevContCurCode().equals(ccaDetailsVO.getContCurCode())){
				ccaDetailsVO.setCurrChangeInd(YES);		
			}
		}
		if((maintainCCAForm.getRevGpaCode()!=null) && (maintainCCAForm.getRevGpaCode().trim().length() > 0)){
			ccaDetailsVO.setGpaChangeInd(NO);
			if(!ccaDetailsVO.getRevGpaCode().equals(ccaDetailsVO.getGpaCode())){
				ccaDetailsVO.setGpaChangeInd(YES);
			}
		}
	}*/
	/**
	 * 
	 * 	Method		:	SaveCCACommand.updateFormForAutoMCA
	 *	Added by 	:	A-7929 on 24-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param maintainCCAForm
	 *	Parameters	:	@param maintainCCASession 
	 *	Return type	: 	void
	 */
	private void updateFormForAutoMCA(MRAMaintainCCAForm maintainCCAForm, MaintainCCASession maintainCCASession) {
		CCAdetailsVO ccaDetailsVO = maintainCCASession.getCCAdetailsVO();
		//Collection<DocumentBillingDetailsVO> documentBillingDetailsVO = maintainCCASession.getDocumentBillingDetailsVOs();
		if((maintainCCAForm.getRevisedRate()!=null) && (maintainCCAForm.getRevisedRate().trim().length() > 0)){
			ccaDetailsVO.setRevisedRate(Double.parseDouble(maintainCCAForm.getRevisedRate()));
		}
		if((maintainCCAForm.getRevGpaCode()!=null) && (maintainCCAForm.getRevGpaCode().trim().length() > 0)){
			ccaDetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
		}
		if((maintainCCAForm.getRevCurCode()!=null) && (maintainCCAForm.getRevCurCode().trim().length() > 0)){
			ccaDetailsVO.setRevContCurCode(maintainCCAForm.getRevCurCode());
		}
			//ccaDetailsVO.setCurrChangeInd(maintainCCAForm.getCurChgInd());
		ccaDetailsVO.setAutoMca(maintainCCAForm.getIsAutoMca());//ADDED BY A-7540
		
		
	}
	/**
	 * 
	 * 	Method		:	SaveCCACommand.multiModificationRestrictionForAutoMCA
	 *	Added by 	:	A-7929 on 24-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param maintainCCAForm
	 *	Parameters	:	@param ccaDetailsVO
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 */
	private int multiModificationRestrictionForAutoMCA(MRAMaintainCCAForm maintainCCAForm, CCAdetailsVO ccaDetailsVO,Collection<DocumentBillingDetailsVO> documentBillingDetailsVO) {
		int changes = 0;
		double rate  =0; 
		String gpaCode = "";
		//String curCode = "";
		if((maintainCCAForm.getRevCurCode()!=null) && (maintainCCAForm.getRevCurCode().trim().length() > 0)){
		 for(DocumentBillingDetailsVO documentBillingDetailsVOs :documentBillingDetailsVO){
			if(!ccaDetailsVO.getContCurCode().equals(maintainCCAForm.getRevCurCode())){
				changes++;
				break;
			}
		 }
		}
		if(ccaDetailsVO != null){
			//curCode = ccaDetailsVO.getContCurCode();
			gpaCode = ccaDetailsVO.getGpaCode();
			rate = ccaDetailsVO.getRate(); 	
			
		if((maintainCCAForm.getRevisedRate()!=null) && (maintainCCAForm.getRevisedRate().trim().length() > 0) && (!maintainCCAForm.getRevisedRate().equals("0.0"))){	
			if(rate != 0 && rate != Double.parseDouble(maintainCCAForm.getRevisedRate())){
				changes++;
			}
		}
		if((maintainCCAForm.getRevGpaCode()!=null) && (maintainCCAForm.getRevGpaCode().trim().length() > 0)){
			if(gpaCode!=null && !gpaCode.equals(maintainCCAForm.getRevGpaCode())){
				changes++;
			}
		}
			/*if(maintainCCAForm.getRevisedRate()!=null && maintainCCAForm.getRevisedRate().trim().length() >0) {
				changes++;
			}*/
		}
		return changes;
	}

	/**
	 * @author a-4823
	 * to construct CCADetailsVO if despatch not participated in billing
	 * @param maintainCCAForm
	 * @param ccaDetailsVOInSession 
	 * @return
	 */
	private CCAdetailsVO populateCCADetailsVO(MRAMaintainCCAForm maintainCCAForm, CCAdetailsVO ccaDetailsVOInSession) {
		CCAdetailsVO ccAdetailsVO=new CCAdetailsVO();
		ccAdetailsVO.setCcaType(maintainCCAForm.getCcaType());
		ccAdetailsVO.setCategoryCode(ccaDetailsVOInSession.getCategoryCode());
		ccAdetailsVO.setSubClass(ccaDetailsVOInSession.getSubClass());
		ccAdetailsVO.setOrigin(ccaDetailsVOInSession.getOrigin());
		ccAdetailsVO.setDestination(ccaDetailsVOInSession.getDestination());
		ccAdetailsVO.setContCurCode(maintainCCAForm.getRevCurCode());
		ccAdetailsVO.setRevContCurCode(maintainCCAForm.getRevCurCode());
		ccAdetailsVO.setGpaCode(maintainCCAForm.getRevGpaCode());
		ccAdetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
		ccAdetailsVO.setCcaStatus(maintainCCAForm.getCcaStatus());
		ccAdetailsVO.setOperationFlag(INSERT);
		ccAdetailsVO.setCsgDocumentNumber(maintainCCAForm.getConDocNo());
		ccAdetailsVO.setCsgSequenceNumber(ccaDetailsVOInSession.getCsgSequenceNumber());
		ccAdetailsVO.setPoaCode(maintainCCAForm.getRevGpaCode());
		ccAdetailsVO.setDsnNo(maintainCCAForm.getDsnNumber());		
		if(maintainCCAForm.getRevGrossWeight()!=null){
			ccAdetailsVO.setGrossWeight(Double.parseDouble(maintainCCAForm.getRevGrossWeight()));
			ccAdetailsVO.setRevGrossWeight(Double.parseDouble(maintainCCAForm.getRevGrossWeight()));
		}
		ccAdetailsVO.setAutoMca(maintainCCAForm.getIsAutoMca());//Added by A-7540
		ccAdetailsVO.setRate(Double.parseDouble(maintainCCAForm.getRevisedRate()));//Added by A-7929 as part of ICRD-132548
		ccAdetailsVO.setRevisedRate(Double.parseDouble(maintainCCAForm.getRevisedRate())); //Added by A-7929 as part of ICRD-132548
		
		if(maintainCCAForm.getRevCurCode()!=null){
			if(maintainCCAForm.getRevChgGrossWeight()!=null){
				Money amount=null;
				try {
					amount = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
					amount.setAmount(Double.parseDouble(maintainCCAForm.getRevChgGrossWeight()));
					ccAdetailsVO.setRevChgGrossWeight(amount);
					ccAdetailsVO.setChgGrossWeight(amount);
					
				} catch (CurrencyException e) {
					// TODO Auto-generated catch block
					log.log(Log.FINE,"Inside CurrencyException.. ");
				}
			}
			
			double netAmount = 0.0;
			Money netAmt;
			try {
				netAmt = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
				netAmount=ccAdetailsVO.getRevChgGrossWeight().getAmount()+ccAdetailsVO.getOtherRevChgGrossWgt().getAmount()+ ccAdetailsVO.getRevTax() - ccAdetailsVO.getRevTds();
				netAmt.setAmount(netAmount);
				ccAdetailsVO.setRevNetAmount(netAmt);
			} catch (CurrencyException excep) {
				log.log(Log.FINE,"Inside CurrencyException.. ");
			}
			
		}
		return ccAdetailsVO;
	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private boolean checkWorkFlowEnabled() throws SystemException {
		Boolean workFlowEnabled=true;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
		Map<String, String> systemParameters = null;		
		try {
			systemParameters = sharedDefaultsDelegate
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			if(!(systemParameters.containsValue(YES) || systemParameters.containsValue(BASED_ON_RULES))){//Modified For IASCB-2373
				workFlowEnabled=false;
			}
		}
		return workFlowEnabled;
	}
	/**
	 * 
	 * @param ccaDetailsVO
	 */
	private void populateChangeIndicator(CCAdetailsVO ccaDetailsVO) {
		if(ccaDetailsVO.getRevContCurCode()!=null){
			ccaDetailsVO.setCurrChangeInd(NO);
			if(!ccaDetailsVO.getRevContCurCode().equals(ccaDetailsVO.getContCurCode())){
				ccaDetailsVO.setCurrChangeInd(YES);		
			}
		}
		if(ccaDetailsVO.getRevGpaCode()!=null){
			ccaDetailsVO.setGpaChangeInd(NO);
			if(!ccaDetailsVO.getRevGpaCode().equals(ccaDetailsVO.getGpaCode())){
				ccaDetailsVO.setGpaChangeInd(YES);
			}
		}
		if(ccaDetailsVO.getRevChgGrossWeight()!=null){
			ccaDetailsVO.setWeightChargeChangeInd(NO);	
			if(!(ccaDetailsVO.getRevChgGrossWeight().getAmount()==(ccaDetailsVO.getChgGrossWeight().getAmount()))){
				ccaDetailsVO.setWeightChargeChangeInd(YES);	
			}
		}
		//Added by A-7929 as part of ICRD-132548
			if(ccaDetailsVO.getRevisedRate()!= 0){
				ccaDetailsVO.setRateChangeInd(NO);
				if(ccaDetailsVO.getRevisedRate() != (ccaDetailsVO.getRate())){
					ccaDetailsVO.setRateChangeInd(YES);
				}
			}
		
		if(ccaDetailsVO.getSurchargeCCAdetailsVOs()!=null&&ccaDetailsVO.getSurchargeCCAdetailsVOs().size()>0){
		for(SurchargeCCAdetailsVO surchargeCCAdetailsVO:ccaDetailsVO.getSurchargeCCAdetailsVOs()){
			if(!(surchargeCCAdetailsVO.getRevSurCharge().getAmount()==surchargeCCAdetailsVO.getOrgSurCharge().getAmount())){
				ccaDetailsVO.setWeightChargeChangeInd(YES);	
			}
			//Added by A-7540
			if(!(surchargeCCAdetailsVO.getSurchargeRevRate()==surchargeCCAdetailsVO.getSurchareOrgRate())){
				ccaDetailsVO.setRateChangeInd(YES);
			}
		}
		}
		//Added for ICRD-145267 ends
		ccaDetailsVO.setGrossWeightChangeInd(NO);
		if(ccaDetailsVO.getRevGrossWeight()!=ccaDetailsVO.getGrossWeight()){
			ccaDetailsVO.setGrossWeightChangeInd(YES);	
		}	



	}
	public void updateForm(MRAMaintainCCAForm maintainCCAForm,MaintainCCASession maintainCCASession){
		CCAdetailsVO ccaDetailsVO = maintainCCASession.getCCAdetailsVO();
		ccaDetailsVO.setCcaType(maintainCCAForm.getCcaType());
		ccaDetailsVO.setRevGrossWeight(Double.parseDouble(maintainCCAForm.getRevGrossWeight()));
		ccaDetailsVO.getRevChgGrossWeight().setAmount(Double.parseDouble(maintainCCAForm.getRevChgGrossWeight()));
		if(maintainCCAForm.getRevDueArl()!=null)     
		{
		ccaDetailsVO.getRevDueArl().setAmount(Double.parseDouble(maintainCCAForm.getRevDueArl()));
		}
		//ccaDetailsVO.getRevDuePostDbtDisp().setAmount(Double.parseDouble(maintainCCAForm.getRevDuePostDbt()));
		//ccaDetailsVO.setRevDStCode(maintainCCAForm.getRevDStCode());
		ccaDetailsVO.setCategoryCode(maintainCCAForm.getCategory());
		//ccaDetailsVO.setSubClass(maintainCCAForm.getSubclass());
		ccaDetailsVO.setOrigin(maintainCCAForm.getOrigin());
		ccaDetailsVO.setDestination(maintainCCAForm.getDestination());
		ccaDetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
		ccaDetailsVO.setRevGpaName(maintainCCAForm.getRevGpaName());
		ccaDetailsVO.setCcaRemark(maintainCCAForm.getRemarks());
		//Added for CRQ-7352
		ccaDetailsVO.setCcaStatus(maintainCCAForm.getCcaStatus());
		ccaDetailsVO.setRevContCurCode(maintainCCAForm.getRevCurCode());
		ccaDetailsVO.setCurrChangeInd(maintainCCAForm.getCurChgInd());
		ccaDetailsVO.setRevTax(Double.parseDouble(maintainCCAForm.getRevTax()));
		ccaDetailsVO.setGpaChangeInd(maintainCCAForm.getReason2());
		ccaDetailsVO.setCurrChangeInd(maintainCCAForm.getReason1());
		ccaDetailsVO.setGrossWeightChangeInd(maintainCCAForm.getReason3());
		ccaDetailsVO.setWeightChargeChangeInd(maintainCCAForm.getReason4());
		ccaDetailsVO.setRate(Double.parseDouble(maintainCCAForm.getRate())); //Added by A-7929 as part of icrd-132548 
		ccaDetailsVO.setAutoMca(maintainCCAForm.getIsAutoMca());//ADDED BY A-7540
		if(!"".equals(maintainCCAForm.getOtherRevChgGrossWgt())){
			Money revChgGrossWeight = null;
			try {
				revChgGrossWeight = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
			} catch (CurrencyException e) {
				log.log(Log.FINE,"Inside CurrencyException.. ");
			}
			revChgGrossWeight.setAmount(Double.parseDouble(maintainCCAForm.getOtherRevChgGrossWgt()));					
			ccaDetailsVO.setOtherRevChgGrossWgt(revChgGrossWeight);	
		}
		
		double netAmount = 0.0;
		Money netAmt;
		try {
			netAmt = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
			netAmount=ccaDetailsVO.getRevChgGrossWeight().getAmount()+ccaDetailsVO.getOtherRevChgGrossWgt().getAmount()+ ccaDetailsVO.getRevTax() - ccaDetailsVO.getRevTds();
			netAmt.setAmount(netAmount);
			ccaDetailsVO.setRevNetAmount(netAmt);
		} catch (CurrencyException excep) {
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}

	}
	private Collection<ErrorVO> validateRevCurCod(MRAMaintainCCAForm maintainCCAForm,String companyCode) {
		try {
			new CurrencyDelegate().validateCurrency(companyCode, maintainCCAForm
					.getRevCurCode().trim().toUpperCase());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "getRevCurCode not valid....");
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}
	
	public int multiModificationRestriction(MRAMaintainCCAForm maintainCCAForm,CCAdetailsVO ccaDetailsVO) {
		int changes = 0;
		String gpaCode="";
		String curCode="";
		double rate = 0 ; 	//Added by A-7929 as part of icrd-132548 
		double ccaamount=0.0;
		double surChrgAmount=0.0;
		double wgt=0.0;
		if(ccaDetailsVO!=null){
			curCode=ccaDetailsVO.getContCurCode();
			gpaCode=ccaDetailsVO.getGpaCode();
			rate = ccaDetailsVO.getRate(); 	//Added by A-7929 as part of icrd-132548 
			if (ccaDetailsVO.getChgGrossWeight()!=null)
			{
			ccaamount=ccaDetailsVO.getChgGrossWeight().getAmount();
			}
			
			if(ccaDetailsVO.getOtherChgGrossWgt()!=null){
			surChrgAmount=ccaDetailsVO.getOtherChgGrossWgt().getAmount();
			}
			wgt=ccaDetailsVO.getGrossWeight();
			if(curCode!=null && !curCode.equals(maintainCCAForm.getRevCurCode())){
				changes++;
			}
			if(gpaCode!=null && !gpaCode.equals(maintainCCAForm.getRevGpaCode())){
				changes++;
			}
			//Added by A-7929 as part of icrd-132548 
			

			
			
               Money amountt=null;
				try {
                            amountt = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
                            amountt.setAmount(Double.parseDouble(maintainCCAForm.getRevChgGrossWeight()));
				} catch (CurrencyException e) {
					log.log(Log.FINE, "Currency Exception");
                     }
                            
                     Money ccaamountt=null;                   
                     try {
                            ccaamountt = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
                            ccaamountt.setAmount(ccaamount);
                     } catch (CurrencyException e) {
                            log.log(Log.FINE, "Currency Exception");
                     }
                     //Added for ICRD-154124 starts
                     Money surchgAmt=null;
     				try {
     					surchgAmt = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
     					surchgAmt.setAmount(Double.parseDouble(maintainCCAForm.getOtherRevChgGrossWgt()));
     				} catch (CurrencyException e) {
     					log.log(Log.FINE, "Currency Exception");
                          }
                     Money ccaSurChgAmt=null;                   
                     try {
                    	 ccaSurChgAmt = CurrencyHelper.getMoney(maintainCCAForm.getRevCurCode());
                    	 ccaSurChgAmt.setAmount(surChrgAmount);
                     } catch (CurrencyException e) {
                            log.log(Log.FINE, "Currency Exception");
                     }
                     //Added for ICRD-154124 ends
                	log.log(Log.FINE, "amountt.getRoundedAmount()--->",
                			amountt.getRoundedAmount());
                	log.log(Log.FINE, "ccaamountt.getRoundedAmount()--->",
                			ccaamountt.getRoundedAmount());
                	
                    
               double amount = Double.valueOf(maintainCCAForm.getRevChgGrossWeight());
			double weight=Double.valueOf(maintainCCAForm.getRevGrossWeight());	
               if(ccaamountt.getRoundedAmount()!= amountt.getRoundedAmount() || wgt!=weight||ccaSurChgAmt.getRoundedAmount()!=surchgAmt.getRoundedAmount()){
				changes++;
			}
               
           		if(BILLED.equals(ccaDetailsVO.getBillingStatus())){
   			if(rate != 0 && rate != (Double.parseDouble(maintainCCAForm.getRevisedRate())) && !"0.0".equals(maintainCCAForm.getRevisedRate()) &&  wgt!=weight ){
   				changes++;
   			}
   			}

	               if(maintainCCAForm.getAfterSave()==null){
	            	   
	            	   maintainCCAForm.setAfterSave(CCAdetailsVO.FLAG_YES);
	               }
               
               
			}
		return changes;
	}
	//Added as part of ICRD-115138
	private int validateFormForInternalMCA(CCAdetailsVO ccaDetailsVO) {
		int changes=0;
		if((YES.equals(ccaDetailsVO.getCurrChangeInd()))||(YES.equals(ccaDetailsVO.getGpaChangeInd()))){
			changes++;
		}
	return changes;
	}
	
	/**
	 * 
	 * @author A-5255
	 * @param maintainCCAFilterVO
	 * @return
	 */
	private ProrationFilterVO populateProrationFilterVO(
			MaintainCCAFilterVO maintainCCAFilterVO) {
		ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
		prorationFilterVO.setCompanyCode(maintainCCAFilterVO.getCompanyCode());
		prorationFilterVO.setConsigneeDocumentNumber(maintainCCAFilterVO
				.getConsignmentDocNum());
		prorationFilterVO.setConsigneeSequenceNumber(maintainCCAFilterVO
				.getConsignmentSeqNum());
		prorationFilterVO.setPoaCode(maintainCCAFilterVO.getPOACode());
		prorationFilterVO
				.setBillingBasis(maintainCCAFilterVO.getBillingBasis());
		prorationFilterVO
				.setSerialNumber(maintainCCAFilterVO.getBlgDtlSeqNum());
		prorationFilterVO.setBaseCurrency(maintainCCAFilterVO.getBaseCurrency());
		return prorationFilterVO;
	}
	/**
	 * 
	 * @author A-5255
	 * @param surchargeProrationDetailsVOs
	 * @param maintainCCAForm
	 */
	private Collection<SurchargeCCAdetailsVO> convertToSurchargeCCAdetailsVOs(
			Collection<SurchargeProrationDetailsVO> surchargeProrationDetailsVOs,
			MRAMaintainCCAForm maintainCCAForm) {
		Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs = new ArrayList<SurchargeCCAdetailsVO>();
		if(surchargeProrationDetailsVOs!=null && surchargeProrationDetailsVOs.size()>0){
		SurchargeCCAdetailsVO surchargeCCAdetailsVO = null;
		Money revSurCharge = null;
		for (SurchargeProrationDetailsVO surchargeProrationDetailsVO : surchargeProrationDetailsVOs) {
			surchargeCCAdetailsVO = new SurchargeCCAdetailsVO();
			surchargeCCAdetailsVO.setCompanyCode(surchargeProrationDetailsVO
					.getCompanyCode());
			surchargeCCAdetailsVO.setBillingBasis(surchargeProrationDetailsVO
					.getBillingBasis());
			surchargeCCAdetailsVO
					.setCsgSequenceNumber(surchargeProrationDetailsVO
							.getCsgSeqNumber());
			surchargeCCAdetailsVO
					.setCsgDocumentNumber(surchargeProrationDetailsVO
							.getCsgDocumentNumber());
			surchargeCCAdetailsVO.setPoaCode(surchargeProrationDetailsVO
					.getPoaCode());
			surchargeCCAdetailsVO.setChargeHeadName(surchargeProrationDetailsVO
					.getChargeHead());
			try {
				revSurCharge = CurrencyHelper.getMoney(maintainCCAForm
						.getRevCurCode());
			} catch (CurrencyException e) {
				log.log(Log.FINE,"Inside CurrencyException.. ");
			}
			revSurCharge.setAmount(surchargeProrationDetailsVO
					.getProrationValue().getAmount());
			surchargeCCAdetailsVO.setRevSurCharge(revSurCharge);
			surchargeCCAdetailsVO.setOrgSurCharge(surchargeProrationDetailsVO
					.getProrationValue());
			
			//Added by A-7540
			if(maintainCCAForm.getSurchargeRevRate() != null){
			surchargeCCAdetailsVO.setSurchargeRevRate(Double.parseDouble(maintainCCAForm.getSurchargeRevRate().toString()));
			}
			if(maintainCCAForm.getSurchareOrgRate() != null){
			surchargeCCAdetailsVO.setSurchareOrgRate(Double
					.parseDouble(maintainCCAForm.getSurchareOrgRate().toString()));
			}
			surchargeCCAdetailsVOs.add(surchargeCCAdetailsVO);
		}
		}
		return surchargeCCAdetailsVOs;
	}
	/**
	 * Added by A-5219 for ICRD-67967
	 * @param companyCode
	 * @param mcaType
	 * @return
	 */
	private boolean isExchangeRateParExist(String companyCode, String mcaType){
		boolean parExist = false;
		ExchangeRateFilterVO exchangeRateFilterVO =new ExchangeRateFilterVO();
		exchangeRateFilterVO.setCompanyCode(companyCode);
		if(ACTUAL.equals(mcaType)){
			exchangeRateFilterVO.setFunctionPoint(MAIL_FLOWN_ACTUALMCA_FUNCTION_POINT);//ICRD-301738
		}else{
			exchangeRateFilterVO.setFunctionPoint(INTERNAL_FUNCTION_POINT);
		}
		exchangeRateFilterVO.setSubSystem(MRA_SUB_SYSTEM);
		Collection<ExchangeRateParameterMasterVO> parVOs = null;
		try {
			 parVOs = new CurrencyDelegate().findExchangeRateParameters(exchangeRateFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "Exchange rate paramter not configured");
		}
		if(parVOs!= null && !parVOs.isEmpty())
			{
			parExist = true;
			}
		return parExist;
	}
}
