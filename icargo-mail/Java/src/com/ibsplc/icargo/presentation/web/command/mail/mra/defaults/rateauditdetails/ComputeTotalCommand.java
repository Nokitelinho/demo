/*
 * ComputeTotalCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3251
 *
 */
public class ComputeTotalCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RATE AUDITDETAILS ComputeTotalCommand");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String COMPUTETOTAL_SUCCESS="computetotal_success";
	private static final String COMPUTETOTAL_FAILURE="computetotal_failure";
	private static final String TRIGG_FRM_RATEAUDIT = "RA";
	private static final String BILLING_PAR_CHANGED = "P";
	private static final String GROSS_WGT_CHANGED = "W";
	private static final String AUD_WGT_CAHRGE_CHANGED = "C";
	private static final String YES = "Y";
	private static final String NO = "N";
	private static final String RECIEVEABLE = "R";
	private static final String CURRENCY_CODE = "NZD";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("ComputeTotalCommand", "execute");
		 ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		 RateAuditDetailsSession rateAuditDetailsSession=getScreenSession(MODULE,SCREENID);
		 RateAuditVO rateAuditVO = null;
		 RateAuditVO rateAuditVOAfterProc = null;
		 Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();	
		 MailTrackingMRADelegate delegate=new MailTrackingMRADelegate(); 
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();			
		 rateAuditVO = rateAuditDetailsSession.getRateAuditVO();
		
		 
		 rateAuditVO.setParChangeFlag(rateAuditDetailsSession.getParChangeFlag());
		 //the procedure needs a trigger point from which screen RA -- for rate audit and CCA for maintaincca
		 rateAuditVO.setCompTotTrigPt(TRIGG_FRM_RATEAUDIT);
		 
		 log.log(Log.FINE, "\nRateAuditVO Before alteration----->>>\n\n\n",
				rateAuditVO);
		log.log(Log.FINE, "ParChangeFlag", rateAuditVO.getParChangeFlag());
		if(rateAuditDetailsSession.getParChangeFlag()!=null&&rateAuditDetailsSession.getParChangeFlag().contains(BILLING_PAR_CHANGED)||rateAuditDetailsSession.getParChangeFlag().contains(GROSS_WGT_CHANGED)||rateAuditDetailsSession.getParChangeFlag().contains(AUD_WGT_CAHRGE_CHANGED)){
		 
		       String populateTempFlag=NO;
		 	//populate the temp tables with initial data for procedure only  if billing parameters changed		 		
				 if(rateAuditVO.getParChangeFlag().contains(BILLING_PAR_CHANGED)){					
					 try {	
						 delegate.populateInitialDataInTempTables(rateAuditVO);	
						 populateTempFlag=YES;
						} catch (BusinessDelegateException e) {
							log.log(Log.SEVERE, "Cannot populate temp tables !!!!");
							e.getMessage();
						}
				 }
		 
		    //all other calls are made in controller
					try {	
						rateAuditVOAfterProc = delegate.computeTotalForRateAuditDetails(rateAuditVO);									
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);		
					
					}	
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = COMPUTETOTAL_FAILURE;
						return;
					}

		   //Clean Temp Tables
					 if(YES.equalsIgnoreCase(populateTempFlag)){
						try {	
							delegate.removeRateAuditDetailsFromTemp(rateAuditVO);									
						} catch (BusinessDelegateException e) {
							log.log(Log.SEVERE, "remove from temp tables error !!!!");
							e.getMessage();		
						
						}
					 }
					if (errors != null && errors.size() > 0) {
					for(ErrorVO errVO : errors ){
						if("mailtracking.mra.defaults.prorationnotfound".equals(errVO.getErrorCode())){
							errors = new ArrayList<ErrorVO>();		    			
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.prorationnotfound");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
			    			invocationContext.addAllError(errors);
			    			invocationContext.target = COMPUTETOTAL_FAILURE;    			
			    			return;							
						}
						if("mailtracking.mra.defaults.computetotprocfailed".equals(errVO.getErrorCode())){
							errors = new ArrayList<ErrorVO>();		    			
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.computetotprocfailed");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
			    			invocationContext.addAllError(errors);
			    			invocationContext.target = COMPUTETOTAL_FAILURE;    			
			    			return;							
						}else{
							errors = new ArrayList<ErrorVO>();		    			
							ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.computetotprocfailed");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
			    			invocationContext.addAllError(errors);
			    			invocationContext.target = COMPUTETOTAL_FAILURE;    			
			    			return;		
						}
					}
					}
					
			 	
				
		 //To display in page
				
				if(rateAuditVOAfterProc!=null){						
					
					
					rateAuditVOAfterProc.setSaveToHistoryFlg(YES);
					
					rateAuditDetailsVOs = rateAuditVOAfterProc.getRateAuditDetails();
					
					for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
						//CHANGE DUE AIRLINE 
						rateAuditDetailsVO.setDueAirline(rateAuditDetailsVO.getAudtdWgtCharge().getAmount());						
						
						if(RECIEVEABLE.equals(rateAuditDetailsVO.getPayFlag())){								
							rateAuditDetailsForm.setUpdWt(String.valueOf(rateAuditDetailsVO.getGrsWgt()));
							rateAuditDetailsForm.setCategory(rateAuditDetailsVO.getCategory());
							rateAuditDetailsForm.setSubClass(rateAuditDetailsVO.getSubclass());
							rateAuditDetailsForm.setULD(rateAuditDetailsVO.getUldno());
							rateAuditDetailsForm.setFlightCarCod(rateAuditDetailsVO.getCarrierCode());
							rateAuditDetailsForm.setFlightNo(rateAuditDetailsVO.getFlightno());
							rateAuditDetailsForm.setAuditWgtCharge(String.valueOf(rateAuditDetailsVO.getAudtdWgtCharge()));	
							if(YES.equals(rateAuditDetailsVO.getApplyAudt())){
							rateAuditDetailsForm.setApplyAudit(rateAuditDetailsVO.getApplyAudt());
							}
							rateAuditDetailsForm.setBillTo(rateAuditDetailsVO.getBillTO());	
							
								Double disp=0.0;
								disp = rateAuditVOAfterProc.getAuditedWtCharge().getAmount()- rateAuditVOAfterProc.getPresentWtCharge().getAmount();
								
								   if(disp==0){
									   rateAuditVOAfterProc.setDiscrepancyNo(YES);											
									}else{
										rateAuditVOAfterProc.setDiscrepancyNo(NO);
										
										Money discrp = null;
										try {											
											discrp = CurrencyHelper.getMoney(CURRENCY_CODE);											
											discrp.setAmount(disp);							
											rateAuditVOAfterProc.setDiscrepancyYes(discrp);
											
										} catch (CurrencyException e) {
											log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
											e.getMessage();
										}									
								}						
					      }							
				      }
					rateAuditDetailsSession.setRateAuditVO(rateAuditVOAfterProc);
				  }else{
					if(rateAuditVO!=null){
					
						Double disp=0.0;
						disp =Double.parseDouble(rateAuditDetailsForm.getAuditWgtCharge())- rateAuditVOAfterProc.getPresentWtCharge().getAmount();
						   if(disp==0){							   
							   rateAuditVO.setDiscrepancyNo(YES);									
							}else{
								
								rateAuditVO.setDiscrepancyNo(NO);
								
								Money discrp = null;
								try {											
									discrp = CurrencyHelper.getMoney(CURRENCY_CODE);											
									discrp.setAmount(disp);							
									rateAuditVO.setDiscrepancyYes(discrp);
									
								} catch (CurrencyException e) {
									log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
									e.getErrorCode();
								}										
								}						
				}
			 }		 
	 }
		 rateAuditDetailsSession.setParChangeFlag(NO);
		 rateAuditDetailsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);		
		 invocationContext.target = COMPUTETOTAL_SUCCESS;
		 log.exiting("ComputeTotalCommand", "execute");

	 }
}
