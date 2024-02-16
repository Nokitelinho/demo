/*
 * AutoRateCommand.java Created on Aug 26,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class AutoRateCommand extends BaseCommand {


	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	private static final String SAVE_SUCCESS = "autorate_success";
	private static final String SAVE_FAILURE = "autorate_failure";	

	private static final String BILLING_PAR_CHANGED = "P";
	private static final String GROSS_WGT_CHANGED = "W";
	private static final String AUD_WGT_CAHRGE_CHANGED = "C";	
	private static final String RECIEVEABLE = "R";
	private static final String PAYABLE = "P";
	private static final String CURRENCY_CODE = "NZD";
	
	private static final String AIRLINE = "ARL";
	private static final String GPA = "GPA";
	
	private static final String TRIGG_FRM_CCA = "CCA";
	private static final String DUE_AIRLINE = "AC";
	private static final String DUE_GPA = "GC";
	private static final String RETENSION = "T";
	private static final String TO_BE_FINALIZED = "T";
	private static final String FINALIZED = "F";
	
	
	
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("AutoRateCommand", "execute");
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;		
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();				
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		CCAdetailsVO ccaDetailsVO = maintainCCASession.getCCAdetailsVO();

		ccaDetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
		ccaDetailsVO.setRevGpaName(maintainCCAForm.getRevGpaName());
		//ADDED FOR BUG 48711
		if(ccaDetailsVO.getCcaType() != maintainCCAForm.getCcaType() && "A".equals(maintainCCAForm.getCcaType())){
			maintainCCAForm.setDisableFlag("N");
		}
		ccaDetailsVO.setCcaType(maintainCCAForm.getCcaType());
		/*
		 * Validations -->>
		 */
		//validating the revised destination
		String destination=maintainCCAForm.getRevDStCode();				
		if (destination == null || "".equals(destination.trim())) {
			updateForm(maintainCCAForm,maintainCCASession);
			errors = new ArrayList<ErrorVO>();			
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.msg.err.destblank");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;    			
			return;	
		}				
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
					PostalAdministrationVO postalAdministrationVO =mailTrackingMRADelegate.findPostalAdminDetails(logonAttributes.getCompanyCode(),gpaCode);
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
					}else{
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

		if(ccaDetailsVO!=null){		
			String parChgFlag = "";
			log.log(Log.FINE, "RevDueArl in vo!!", ccaDetailsVO.getRevDueArl().getRoundedAmount());
			log.log(Log.FINE, "RevDueArl in form!!", maintainCCAForm.getRevDueArl());
			/*
			 * Second condition is added By A-3434 for bug 43986
			 * If user edits DueArl and save,AutorateFlag will be N,while doing Autorate original and rev values will be different.
			 * If user directly autorates without save,AutorateFlag will be null,while doing Autorate rev values in vo and form will be different.
			 */	
			if(AIRLINE.equalsIgnoreCase(maintainCCAForm.getIssueParty())){

				if((ccaDetailsVO.getDueArl().getRoundedAmount()!= Double.parseDouble(maintainCCAForm.getRevDueArl())
						&& "N".equals(ccaDetailsVO.getAutorateFlag())) || (ccaDetailsVO.getRevDueArl().getRoundedAmount()!= 
							Double.parseDouble(maintainCCAForm.getRevDueArl())
							&& (ccaDetailsVO.getAutorateFlag()== null))){	
					parChgFlag = parChgFlag+DUE_AIRLINE;
				}

			}else if(GPA.equals(maintainCCAForm.getIssueParty())){	


				if((ccaDetailsVO.getDuePostDbtDisp().getRoundedAmount()!= Double.parseDouble(maintainCCAForm.getRevDuePostDbt())
						&& "N".equals(ccaDetailsVO.getAutorateFlag())) || (ccaDetailsVO.getRevDuePostDbtDisp().getRoundedAmount()!= 
							Double.parseDouble(maintainCCAForm.getRevDuePostDbt())
							&&(ccaDetailsVO.getAutorateFlag()== null))){	
					parChgFlag = parChgFlag+DUE_GPA;
				}							
			}	

			if(parChgFlag.contains(DUE_AIRLINE)){
				ccaDetailsVO.getRevDueArl().setAmount(Double.parseDouble(maintainCCAForm.getRevDueArl()));
				ccaDetailsVO.getRevDuePostDbtDisp().setAmount(-(ccaDetailsVO.getRevDueArl().getAmount()));
				maintainCCAForm.setRevChgGrossWeight(String.valueOf(ccaDetailsVO.getRevDueArl().getAmount()));
			}else if(parChgFlag.contains(DUE_GPA)){
				ccaDetailsVO.getRevDuePostDbtDisp().setAmount(Double.parseDouble(maintainCCAForm.getRevDuePostDbt()));	
				ccaDetailsVO.getRevDueArl().setAmount(-Double.parseDouble(maintainCCAForm.getRevDuePostDbt()));							
				maintainCCAForm.setRevChgGrossWeight(String.valueOf(ccaDetailsVO.getRevDueArl().getAmount()));
			}

			String excludeRec = "";
			if(RECIEVEABLE.equals(ccaDetailsVO.getPayFlag())){
				excludeRec = PAYABLE;
			}else if(PAYABLE.equals(ccaDetailsVO.getPayFlag())){
				excludeRec = RECIEVEABLE;
			}					

			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			RateAuditVO rateAuditVO=null;
			RateAuditVO rateAuditVOAfterProc = new RateAuditVO();
			try {				
				RateAuditFilterVO rateAuditFilterVO = new RateAuditFilterVO();					
				rateAuditFilterVO.setCompanyCode(ccaDetailsVO.getCompanyCode());
				rateAuditFilterVO.setBillingBasis(ccaDetailsVO.getBillingBasis());				
				rateAuditFilterVO.setCsgDocNum(ccaDetailsVO.getCsgDocumentNumber());
				rateAuditFilterVO.setCsgSeqNum(ccaDetailsVO.getCsgSequenceNumber());
				rateAuditFilterVO.setGpaCode(ccaDetailsVO.getPoaCode());
				rateAuditFilterVO.setDsnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(ccaDetailsVO.getDsnDate()));

				log.log(Log.FINE, "rateAuditFilterVO----->>>",
						rateAuditFilterVO);
				//Fetch R,P1,P2,T1,T2.... from rate audit tables
				rateAuditVO=delegate.findListRateAuditDetails(rateAuditFilterVO);					
				if(rateAuditVO!=null){
					Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();						
					rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();
					int trecCount = 0;

					if(TO_BE_FINALIZED.equals(rateAuditVO.getDsnStatus())){
						maintainCCAForm.setRateAuditedFlag("N");
					}else if(FINALIZED.equals(rateAuditVO.getDsnStatus())){
						maintainCCAForm.setRateAuditedFlag("Y");
					}


					for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){	  		

						/*
						 * common change for all records
						 */

						//1: Destination cahnge
						if(!(ccaDetailsVO.getDestnCode().equals(maintainCCAForm.getRevDStCode()))){
							rateAuditVO.setDestination(maintainCCAForm.getRevDStCode());							
							parChgFlag= parChgFlag+BILLING_PAR_CHANGED;
						}
						//2: Weight change
						if(ccaDetailsVO.getGrossWeight()!= Double.parseDouble(maintainCCAForm.getRevGrossWeight())){

							rateAuditVO.setUpdWt(maintainCCAForm.getRevGrossWeight());
							rateAuditDetailsVO.setGrsWgt(Double.parseDouble(maintainCCAForm.getRevGrossWeight()));
							parChgFlag= parChgFlag+GROSS_WGT_CHANGED;						
						}										

						/*
						 * specific change 
						 * if R record is listed in maintaincca screen change only R and T records need to change
						 * if T record is listed in maintaincca screen change only P and T records need to change
						 */	


						/*Weight charge changed	
						 * Second condition is added By A-3434 for bug 43986
						 * If user edits weight charge and save,AutorateFlag will be N,while doing Autorate original and rev weight will be different.
						 * If user directly autorates without save,AutorateFlag will be null,while doing Autorate rev weight in vo and form will be different.
						 */			
						if(!(excludeRec.equals(rateAuditDetailsVO.getPayFlag())||RETENSION.equals(rateAuditDetailsVO.getPayFlag()))){					
							if((ccaDetailsVO.getChgGrossWeight().getRoundedAmount()!= Double.parseDouble(maintainCCAForm.getRevChgGrossWeight())
									&& "N".equals(ccaDetailsVO.getAutorateFlag())) || (ccaDetailsVO.getRevChgGrossWeight().getRoundedAmount()!= 
										Double.parseDouble(maintainCCAForm.getRevChgGrossWeight())
										&& (ccaDetailsVO.getAutorateFlag()== null))){	
								Money auditedWtCharge = null;
								try {											
									auditedWtCharge = CurrencyHelper.getMoney(CURRENCY_CODE);											
									if(parChgFlag.contains(DUE_AIRLINE)){																														
										auditedWtCharge.setAmount(Double.parseDouble(maintainCCAForm.getRevDueArl()));
									}else if(parChgFlag.contains(DUE_GPA)){																			
										auditedWtCharge.setAmount(-(Double.parseDouble(maintainCCAForm.getRevDuePostDbt())));
									}else{
										auditedWtCharge.setAmount(Double.parseDouble(maintainCCAForm.getRevChgGrossWeight()));
									}							
									rateAuditVO.setAuditedWtCharge(auditedWtCharge);
									rateAuditDetailsVO.setAudtdWgtCharge(auditedWtCharge);
								} catch (CurrencyException e) {
									log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
									e.getErrorCode();
								}										
								parChgFlag= parChgFlag+AUD_WGT_CAHRGE_CHANGED;										
							}					
						}else if(RETENSION.equals(rateAuditDetailsVO.getPayFlag())){
							trecCount=trecCount+1;	    		
						}

						rateAuditDetailsVO.setRecVDate(rateAuditVO.getDsnDate());
						rateAuditVO.setRate(rateAuditDetailsVO.getRate());
					}
					rateAuditVO.setParChangeFlag(parChgFlag);
					//if only one T record is there then no need to go for computeProrateFactors in controler
					rateAuditVO.setTRecordCount(trecCount);						    	
					//change weight charge For T records it will be done in controller
					if(parChgFlag.contains(AUD_WGT_CAHRGE_CHANGED)){
						rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();		
						for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
							if(RETENSION.equals(rateAuditDetailsVO.getPayFlag())){						        	 
								//----if only one T record is there then no need to go for computeProrateFactors 
								if(rateAuditVO.getTRecordCount()==1){
									rateAuditDetailsVO.getAudtdWgtCharge().setAmount(rateAuditVO.getAuditedWtCharge().getAmount());
								}
							}
						}
					}
				}

			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}				
			if(!"".equals(parChgFlag)){		    		
				String populateTempFlag="N";
				//populate the temp tables with initial data for procedure only  if billing parameters changed		 		
				if(parChgFlag.contains(BILLING_PAR_CHANGED)){
					try {	
						delegate.populateInitialDataInTempTables(rateAuditVO);	
						populateTempFlag="Y";
					} catch (BusinessDelegateException e) {
						// TODO Auto-generated catch block
						e.getMessage();
					}
				}							
				try {
					//the procedure needs a trigger point from which screen RA -- for rate audit and CCA for maintaincca
					rateAuditVO.setCompTotTrigPt(TRIGG_FRM_CCA);						
					rateAuditVOAfterProc = delegate.computeTotalForRateAuditDetails(rateAuditVO);									
				} catch (BusinessDelegateException e) {
					errors=handleDelegateException(e);
				}		

				//Clean Temp Tables
				if("Y".equalsIgnoreCase(populateTempFlag)){
					try {	
						delegate.removeRateAuditDetailsFromTemp(rateAuditVO);									
					} catch (BusinessDelegateException e) {
						log.log(Log.SEVERE, "remove from temp tables error !!!!");
						e.getMessage();		

					}
				}					 

				if (errors != null && errors.size() > 0) {
					updateForm(maintainCCAForm,maintainCCASession);
					for(ErrorVO errVO : errors ){								
						if("mailtracking.mra.defaults.prorationnotfound".equals(errVO.getErrorCode())){
							errors = new ArrayList<ErrorVO>();		    			
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.prorationnotfound");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;    			
							return;							
						}
						if("mailtracking.mra.defaults.computetotprocfailed".equals(errVO.getErrorCode())){
							errors = new ArrayList<ErrorVO>();		    			
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.autoratefailed");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;    			
							return;							
						}else{
							errors = new ArrayList<ErrorVO>();		    			
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.autoratefailed");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;    			
							return;		
						}
					}
				}

				if(rateAuditVOAfterProc!=null){	

					rateAuditVOAfterProc.setSaveToHistoryFlg("Y");
					rateAuditVOAfterProc.setCompTotTrigPt("CA");				
					ccaDetailsVO.getRevChgGrossWeight().setAmount(rateAuditVOAfterProc.getAuditedWtCharge().getAmount());
					ccaDetailsVO.setRevGrossWeight(Double.parseDouble(rateAuditVOAfterProc.getUpdWt()));
					//checking if revdue airline or postdetor is not changed manualy, then override with autorated value
					if(!(parChgFlag.contains(DUE_AIRLINE)||parChgFlag.contains(DUE_GPA))){
						ccaDetailsVO.getRevDueArl().setAmount(rateAuditVOAfterProc.getAuditedWtCharge().getAmount());
						ccaDetailsVO.getRevDuePostDbtDisp().setAmount(-(ccaDetailsVO.getRevDueArl().getAmount()));
					}
					Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();						
					rateAuditDetailsVOs = rateAuditVOAfterProc.getRateAuditDetails();						
					//update the net due airline in blgdtl table with new due airline
					for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
						if(!(excludeRec.equals(rateAuditDetailsVO.getPayFlag())||RETENSION.equals(rateAuditDetailsVO.getPayFlag()))){				    		
							rateAuditDetailsVO.setDueAirline(ccaDetailsVO.getRevDueArl().getAmount());
						}
					}				    		



					//put updateted rateauditvo r,p1,p2,t1,t2... in session
					maintainCCASession.setRateAuditVO(rateAuditVOAfterProc);
				}					
			}			
		}

		maintainCCAForm.setAutoratedFlag("Y");

		//maintainCCASession.setAutoRateFlg("Y");
		invocationContext.target = SAVE_SUCCESS;	
	}
	public void updateForm(MRAMaintainCCAForm maintainCCAForm,MaintainCCASession maintainCCASession){
		CCAdetailsVO ccaDetailsVO = maintainCCASession.getCCAdetailsVO();
		ccaDetailsVO.setCcaType(maintainCCAForm.getCcaType());
		ccaDetailsVO.setRevGrossWeight(Double.parseDouble(maintainCCAForm.getRevGrossWeight()));
		ccaDetailsVO.getRevChgGrossWeight().setAmount(Double.parseDouble(maintainCCAForm.getRevChgGrossWeight()));
		ccaDetailsVO.getRevDueArl().setAmount(Double.parseDouble(maintainCCAForm.getRevDueArl()));
		ccaDetailsVO.getRevDuePostDbtDisp().setAmount(Double.parseDouble(maintainCCAForm.getRevDuePostDbt()));
		ccaDetailsVO.setRevDStCode(maintainCCAForm.getRevDStCode());
		ccaDetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
		ccaDetailsVO.setRevGpaName(maintainCCAForm.getRevGpaName());	
		ccaDetailsVO.setCcaRemark(maintainCCAForm.getRemarks());
	}
}
