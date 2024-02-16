/*
 * DisplayCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.WARNING;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureRejectionMemoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for listing 
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Feb 21, 2007  Ruby Abraham	 		Initial draft
 */
public class DisplayCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	
	private static final String CLASS_NAME = "DisplayCommand";
	
    private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	private static final String DISPLAY_SUCCESS = "display_success";
	
	private static final String DISPLAY_FAILURE = "display_failure";
	
	//For setting the raing basis type in currencyconvertorvo
	
	//private static final String RATE = "30D";
	
	//private static final String FORMONE_BALANCED = "B";
	
	//private static final String FORMONE_REJECTED = "R";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		CaptureRejectionMemoSession  captureRejectionMemoSession = 
			(CaptureRejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		CaptureRejectionMemoForm captureRejectionMemoForm=
			(CaptureRejectionMemoForm)invocationContext.screenModel;
		
		
		if(invocationContext.getErrors() != null &&
				invocationContext.getErrors().size()>0){
			captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			captureRejectionMemoSession.removeMemoInInvoiceVOs();
			invocationContext.target = DISPLAY_FAILURE;
			
		}
		else{							
							
				MemoFilterVO  memoFilterVO = captureRejectionMemoSession.getMemoFilterVO();				
				log.log(Log.FINE, "MemoFilterVO", memoFilterVO);
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		        
		        Collection<MemoInInvoiceVO> memoInInvoiceVOs = null;
		        
				if(errors != null && errors.size() > 0){
					log.log(Log.FINE,"!!!!!!!inside errors!= null");
				}
				else{
					log.log(Log.FINE,"!!!inside errors== null");
					
					ApplicationSessionImpl applicationSession = getApplicationSession();
			        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
			        String companyCode=logonAttributes.getCompanyCode().toUpperCase();	        
			             
					 
					 MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();	 
					 
					 UPUCalendarVO upuCalendarVO = new UPUCalendarVO();
					 
					 if(memoFilterVO.getClearancePeriod() != null  &&
							 memoFilterVO.getClearancePeriod().trim().length()>0){
					 
					 try{
						 upuCalendarVO =  mailTrackingMRADelegate.validateIataClearancePeriod(companyCode,
								 								memoFilterVO.getClearancePeriod());
					 }catch(BusinessDelegateException businessDelegateException){
					    	log.log(Log.FINE,"inside updateAirlinecaught busDelegateExc");
					    	businessDelegateException.getMessage();
					    	errors=handleDelegateException(businessDelegateException);
					 }
						 
					 if(errors != null && errors.size() >0){
							invocationContext.addAllError(errors);
							captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
							invocationContext.target=DISPLAY_FAILURE;
							return;
					 }
					 else{
						 log.log(Log.FINE, "UPUCALENDARVO", upuCalendarVO);
					 }
					 }
			        int airlineIdentifier=0;				        
			        AirlineValidationVO airlineValidationVO = null;
				    AirlineDelegate airlineDelegate = new AirlineDelegate();
				    if(memoFilterVO.getAirlineCodeFilter() != null &&
				    		memoFilterVO.getAirlineCodeFilter().trim().length() > 0){ 
					  try{
					    	airlineValidationVO = airlineDelegate.validateAlphaCode(companyCode,
					    								memoFilterVO.getAirlineCodeFilter());
					    	  airlineIdentifier = airlineValidationVO.getAirlineIdentifier();
					    }catch(BusinessDelegateException businessDelegateException){
					    	log.log(Log.FINE,"inside updateAirlinecaught busDelegateExc");
					    	handleDelegateException(businessDelegateException);			
					    }
					    
					    if(airlineValidationVO == null){
					    	ErrorVO error = new ErrorVO("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.invalidairline");
							error.setErrorDisplayType(ERROR);
							errors.add(error);
					    }
					    else{	
					    	memoFilterVO.setAirlineIdentifier(airlineIdentifier);
					    }
				    }			       
			    if(errors != null && errors.size() > 0){
					log.log(Log.FINE,"!!!inside errors!= null");
					invocationContext.addAllError(errors);
					captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
					invocationContext.target = DISPLAY_FAILURE;
					return;
				}else{
					try{
						memoInInvoiceVOs = mailTrackingMRADelegate.findMemoDetails(memoFilterVO);
						log.log(Log.FINE,
								"MEMOININVOICEVOs fromServer is----->",
								memoInInvoiceVOs);
									
						}catch(BusinessDelegateException businessDelegateException){
				    		log.log(Log.FINE,"inside try...caught businessDelegateException");
				        	businessDelegateException.getMessage();
				        	handleDelegateException(businessDelegateException);
						}  		
							if(memoInInvoiceVOs == null ||memoInInvoiceVOs.size() == 0){
									log.log(Log.FINE,"!!!inside resultList== null");
									captureRejectionMemoSession.removeMemoInInvoiceVOs();
									if(("O").equals(memoFilterVO.getInterlineBillingType())){
										ErrorVO errorVO = new ErrorVO(
										"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.nomemoininvoicevos");
										errorVO.setErrorDisplayType(ERROR);
										errors.add(errorVO);
									}else{
										captureRejectionMemoForm.setLinkStatusFlag("enable");
										captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
										ErrorVO errorVO = new ErrorVO(
												"mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.nomemodetails");
										errorVO.setErrorDisplayType(WARNING);
										errors.add(errorVO);
										//invocationContext.addAllError(errors);
									}
								}
						}
				 }
				if(errors != null && errors.size() > 0){
					log.log(Log.FINE,"!!!inside errors!= null");
					invocationContext.addAllError(errors);					
					invocationContext.target = DISPLAY_FAILURE;
				}else{
					log.log(Log.FINE,"!!!inside resultList!= null");
					
					captureRejectionMemoSession.setMemoInInvoiceVOs((ArrayList<MemoInInvoiceVO>)memoInInvoiceVOs);
					
					if(!("O").equals(memoFilterVO.getInterlineBillingType())){
						captureRejectionMemoForm.setLinkStatusFlag("enable");
						captureRejectionMemoForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
					}
					
					//captureFormOneForm.setStatusFlag("list");
					/*if((FORMONE_BALANCED).equals(formOneVO.getStatus())||
					   (FORMONE_REJECTED).equals(formOneVO.getStatus())){
						captureFormOneForm.setEditFormOneFlag("N");
					}*/
					invocationContext.target = DISPLAY_SUCCESS;			
		       
		    
		    }
			}
		log.exiting(CLASS_NAME, "execute");
	}
}
