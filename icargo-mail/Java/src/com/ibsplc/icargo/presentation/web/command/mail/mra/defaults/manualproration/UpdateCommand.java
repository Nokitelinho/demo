/*
	 * UpdateCommand.java Created on Aug 10, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.manualproration;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ManualProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class UpdateCommand  extends BaseCommand {
	private  Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS "); 
	
	private static final String UPDATE_SUCCESS_TO_SAVE = "update_success_to_save";	
	private static final String UPDATE_SUCCESS_TO_PRORATE = "update_success_to_prorate";		
	private static final String UPDATE_FAILURE = "update_failure";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";
	private static final String SCREENID_DSNPOPUP = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String PAYFLAG_ONETIME = "mailtracking.mra.defaults.payflag";
	private static final String SECTORSTATUS_ONETIME = "mailtracking.mra.proration.sectorstatus";
    /**
     * @param invocationContext
     * @throws CommandInvocationException
     */
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	

			log.entering("UpdateCommand Manual Proration Details","execute");
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	ManualProrationForm  manualProrationForm = (ManualProrationForm)invocationContext.screenModel;
    	ManualProrationSession manualProrationSession = getScreenSession(MODULE_NAME,SCREEN_ID); 
    	DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREENID_DSNPOPUP);
    	Collection<ProrationDetailsVO> prorationDetailsVOs = null;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		    	
    	//From popup 
		DSNPopUpVO popUpVO=popUpSession.getSelectedDespatchDetails();
		log.log(Log.INFO, "SESSION-----------------------", popUpVO);
		if(popUpVO!=null){
			manualProrationForm.setCompanyCode(popUpVO.getCompanyCode());
			manualProrationForm.setBillingBasis(popUpVO.getBlgBasis());
			manualProrationForm.setConsigneeDocumentNumber(popUpVO.getCsgdocnum());
			manualProrationForm.setConsigneeSequenceNumber(String.valueOf(popUpVO.getCsgseqnum()));
			manualProrationForm.setPoaCode(popUpVO.getGpaCode());
		}
		prorationDetailsVOs = manualProrationSession.getProrationDetailVOs();
		log.log(Log.INFO, "prorationDetailsVOs before SESSION UPDATION",
				prorationDetailsVOs);
		int err=0;
		if(manualProrationForm.getValidateFrom()!=null && "PRORATEBTN".equals(manualProrationForm.getValidateFrom())){	
		 err=	updateManualProrationSession(manualProrationSession,manualProrationForm,manualProrationForm.getValidateFrom());
		 prorationDetailsVOs = manualProrationSession.getProrationDetailVOs();
			log.log(Log.INFO,
					"prorationDetailsVOs AFTER UPDATION IN PRORATION",
					prorationDetailsVOs);
		}
		if(err==1 && ("PRORATEBTN").equals(manualProrationForm.getValidateFrom())){
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.nowgtchg");				
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target = UPDATE_FAILURE;
			return;	
		}  
		
		if(manualProrationForm.getValidateFrom()!=null && "SAVEBTN".equals(manualProrationForm.getValidateFrom())){
			updateManualProrationSession(manualProrationSession,manualProrationForm); 
			prorationDetailsVOs = manualProrationSession.getProrationDetailVOs();
			log.log(Log.INFO, "prorationDetailsVOs after updation frm SAVE",
					prorationDetailsVOs);
		}
		
		if(manualProrationForm.getValidateFrom()!=null && "SAVEBTN".equals(manualProrationForm.getValidateFrom())){			
		 if(manualProrationForm.getProrated()!=null && "N".equals(manualProrationForm.getProrated())){	    	
	    		errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.manualproration.msg.err.mustprorate");				
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = UPDATE_FAILURE;
				return;	
	    	}
		 
		}
		
    	errors = new ArrayList<ErrorVO>();
    	
    	Map<String, AirlineValidationVO> validealphaCodes = new HashMap<String, AirlineValidationVO>();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		 
	 	String[] carrierCodeForSec=manualProrationForm.getCarrierCodeForSec();
	 	String[] carrierCodeForPri=manualProrationForm.getCarrierCodeForPri();
	 	String[] hiddenopFlgPri = manualProrationForm.getHiddenOpFlagForPri();
	 	String[] hiddenopFlgSec = manualProrationForm.getHiddenOpFlagForSec();
		try {
			
				Collection<String> carrierCode=new ArrayList<String>();
				
				 if(hiddenopFlgPri!=null && hiddenopFlgPri.length>0 ){
					int count=0;
				   	for(String opflag :hiddenopFlgPri){
						if(carrierCodeForPri!=null){						
								if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){
									if(!(carrierCode.contains(carrierCodeForPri[count]))){
									carrierCode.add(carrierCodeForPri[count]);	
									}
							  }							
						   }
						count= count+1;	
						}					
					}					
				
				 if(hiddenopFlgSec!=null && hiddenopFlgSec.length>0 ){
						int count=0;
					   	for(String opflag :hiddenopFlgSec){
							if(carrierCodeForSec!=null){						
									if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){	
										if(!(carrierCode.contains(carrierCodeForSec[count]))){
										carrierCode.add(carrierCodeForSec[count]);	
										}
								  }							
							   }
							count= count+1;	
							}					
						}
				
				
				validealphaCodes = airlineDelegate.validateAlphaCodes(
						logonAttributes.getCompanyCode(), carrierCode);
				
		
			
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "airlineValidationVO********************",
				validealphaCodes);
		if(errors!=null && errors.size()>0){
    		invocationContext.addAllError(errors);
    		//manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
    		invocationContext.target = UPDATE_FAILURE;
    		return;	
    	}
    	
    	
    	errors = new ArrayList<ErrorVO>();
    	Map<String, AirportValidationVO> validCodes = new HashMap<String, AirportValidationVO>();
		AreaDelegate areaDelegate = new AreaDelegate();	
		
	 	String[] sectorFromForPri=manualProrationForm.getSectorFromForPri();
	 	String[] sectorToForPri=manualProrationForm.getSectorToForPri();
	 	String[] sectorFromForSec=manualProrationForm.getSectorFromForSec();
	 	String[] sectorToForSec=manualProrationForm.getSectorToForSec();
	
		try {
			
				Collection<String> sectorCode=new ArrayList<String>();
				
				 if(hiddenopFlgPri!=null && hiddenopFlgPri.length>0 ){
					int cnt=0;
				   	for(String opflag :hiddenopFlgPri){
						if(sectorFromForPri!=null){						
								if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){								
									sectorCode.add(sectorFromForPri[cnt]);						
							  }							
						   }
						if(sectorToForPri!=null){						
							if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){								
								sectorCode.add(sectorToForPri[cnt]);						
						  }							
					     }							
						cnt= cnt+1;	
						}					
					}					
				
				 if(hiddenopFlgSec!=null && hiddenopFlgSec.length>0 ){
						int cnt=0;
					   	for(String opflag :hiddenopFlgSec){
							if(sectorFromForSec!=null){						
									if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){								
										sectorCode.add(sectorFromForSec[cnt]);						
								  }											
							   }
							if(sectorToForSec!=null){						
								if(!"NOOP".equals(opflag)&&!"D".equals(opflag)){								
									sectorCode.add(sectorToForSec[cnt]);						
							  }											
						   }								
							cnt= cnt+1;	
							}					
					}
				
				
				 validCodes = areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(), sectorCode);			
			
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "Sector********************", validCodes);
		if(errors!=null && errors.size()>0){
    		invocationContext.addAllError(errors);
    		//manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
    		invocationContext.target = UPDATE_FAILURE;
    		return;	
    	}
		
		if(manualProrationForm.getValidateFrom()!=null && "SAVEBTN".equals(manualProrationForm.getValidateFrom())){			
			invocationContext.target = UPDATE_SUCCESS_TO_SAVE; 
		}else if(manualProrationForm.getValidateFrom()!=null && "PRORATEBTN".equals(manualProrationForm.getValidateFrom())){
			invocationContext.target = UPDATE_SUCCESS_TO_PRORATE; 
		}
		
		
		
	}

	/*
	 * updateManualProrationSession A-3251
	 */
	private int updateManualProrationSession(ManualProrationSession manualProrationSession,ManualProrationForm manualProrationForm,String frmButton){

		log.log(Log.INFO, "BEGINING OF  updateManualProrationSession",
				manualProrationSession.getProrationDetailVOs());
		ArrayList<ProrationDetailsVO> prorationDetailsVOs= (ArrayList<ProrationDetailsVO>)manualProrationSession.getProrationDetailVOs();

		ArrayList<ProrationDetailsVO> primaryProrationVOs = (ArrayList<ProrationDetailsVO>)manualProrationSession.getPrimaryProrationVOs();
		
		ArrayList<ProrationDetailsVO> toRemoveprimaryProrationVOs = new ArrayList<ProrationDetailsVO>();
		
		int serialNumber =1;
		String csgseqnum="";
		String csgdocnum="";
		String blgbas="";
		String cmpcod="";
		String poacod="";
		
		int wtChgFlg=0;
		int newFlg=0;
	
		if(prorationDetailsVOs!=null){
			    for(ProrationDetailsVO proVO : prorationDetailsVOs ) {
			    	
							 csgseqnum =   proVO.getConsigneeSequenceNumber();
							 csgdocnum =   proVO.getConsigneeDocumentNumber();
							 blgbas = proVO.getBillingBasis();
							 cmpcod = proVO.getCompanyCode();
							 poacod = proVO.getPostalAuthorityCode();
							 serialNumber=serialNumber+1;
		    }
		}
	   
		
    	
		if (primaryProrationVOs == null) {
			primaryProrationVOs = new ArrayList<ProrationDetailsVO>();
		}
		
		String[] hiddenOpFlagForPri=manualProrationForm.getHiddenOpFlagForPri();    	
    	
    	String[] carrierCodeForPri=manualProrationForm.getCarrierCodeForPri();
    	String[] sectorFromForPri=manualProrationForm.getSectorFromForPri();
    	String[] sectorToForPri=manualProrationForm.getSectorToForPri();
    	String[] numberOfPiecesForPri=manualProrationForm.getNumberOfPiecesForPri();
    	String[] weightForPri=manualProrationForm.getWeightForPri();
    	String[] payableFlagForPri=manualProrationForm.getPayableFlagForPri();
    	String[] prorationTypeForPri=manualProrationForm.getProrationTypeForPri();
    	String[] prorationPercentForPri=manualProrationForm.getProrationPercentageForPri();
    	String[] prorationFactorForPri=manualProrationForm.getProrationFactorForPri();
    	String[] amtInUsdForPri=manualProrationForm.getProrationAmtInUsdForPri();
    	String[] amtInSdrForPri=manualProrationForm.getProrationAmtInSdrForPri();
        String[] amtInBasForPri=manualProrationForm.getProrationAmtInBaseCurrForPri();    
        String[] amtInCurForPri=manualProrationForm.getProratedAmtInCtrCurForPri();
        String[] sectorStatusForPri=manualProrationForm.getSectorStatusForPri();
        
    	Map<String, Collection<OneTimeVO>> oneTimeValues = (HashMap<String,Collection<OneTimeVO>>) manualProrationSession.getOneTimeVOs();
    	 
    	ArrayList<OneTimeVO> billstat = (ArrayList<OneTimeVO>) oneTimeValues.get(PAYFLAG_ONETIME);
    	ArrayList<OneTimeVO> secStat = (ArrayList<OneTimeVO>) oneTimeValues.get(SECTORSTATUS_ONETIME);
    
    
    		for(ProrationDetailsVO primaryProrationVO :primaryProrationVOs){
    			if("I".equals(primaryProrationVO.getOperationFlag())){
    			toRemoveprimaryProrationVOs.add(primaryProrationVO);	
    			}
    		}
    			
    	
    	  if(toRemoveprimaryProrationVOs.size()>0){
	        	primaryProrationVOs.removeAll(toRemoveprimaryProrationVOs);
	        	prorationDetailsVOs.removeAll(toRemoveprimaryProrationVOs);
	        }
	        
    ArrayList<ProrationDetailsVO> priTrecsToremove =new ArrayList<ProrationDetailsVO>();
    	
        if(hiddenOpFlagForPri!=null && hiddenOpFlagForPri.length>0 ){
        	int count=0;
        	for(String opflag :hiddenOpFlagForPri){
        		if(!"NOOP".equals(opflag)){
        		ProrationDetailsVO primaryProrationVO = null;
			        if("U".equals(opflag)){
			        	primaryProrationVO = primaryProrationVOs.get(count);
			        	primaryProrationVO.setOperationFlag("U");
			        	if(primaryProrationVO.getWeight()!=Double.parseDouble(weightForPri[count])){
			        		wtChgFlg++;
			        	}
			        }else if("D".equals(opflag)){
			        	primaryProrationVO = primaryProrationVOs.get(count);
			        	primaryProrationVO.setOperationFlag("D");
			        }else if("N".equals(opflag)){
			        	primaryProrationVO = primaryProrationVOs.get(count);
			        	primaryProrationVO.setOperationFlag("N");
			        	priTrecsToremove.add(primaryProrationVO);
			        }else if("I".equals(opflag)){
			        	newFlg++;
			            primaryProrationVO = new ProrationDetailsVO();
			            primaryProrationVO.setOperationFlag("I");       
			            
			            primaryProrationVO.setConsigneeSequenceNumber(csgseqnum);
					    primaryProrationVO.setConsigneeDocumentNumber(csgdocnum);
					    primaryProrationVO.setBillingBasis(blgbas);
					    primaryProrationVO.setCompanyCode(cmpcod);
						primaryProrationVO.setPostalAuthorityCode(poacod);
						primaryProrationVO.setSerialNumber(serialNumber);
						 serialNumber=serialNumber+1;	
						 //temprly set as business in not clear
						 primaryProrationVO.setGpaarlBillingFlag("A");
						 
	        		} 				        	
		        			Money amountincc=null;
		        	    	Money amountinbas=null;
		        	    	Money amountinusd=null;
		        	    	Money amountinsdr=null;
		        			//changed BY INDU for avoiding updating values without weight change
		        	    	if(wtChgFlg>0 || newFlg>0){
		        			primaryProrationVO.setCarrierCode(carrierCodeForPri[count]);
		        			primaryProrationVO.setSectorFrom(sectorFromForPri[count]);
		        			primaryProrationVO.setSectorTo(sectorToForPri[count]);
		        			primaryProrationVO.setNumberOfPieces(Integer.parseInt(numberOfPiecesForPri[count]));
		        			primaryProrationVO.setWeight(Double.parseDouble(weightForPri[count]));
		        			primaryProrationVO.setProrationType(prorationTypeForPri[count]);
				
		        			primaryProrationVO.setProrationPercentage(Double.parseDouble(prorationPercentForPri[count]));
		        	    	}
		        			if(primaryProrationVO.getOperationFlag()!=null && (!"I".equals(primaryProrationVO.getOperationFlag()))){
//		        				added
		        				if(wtChgFlg>0){
		        				if(billstat!=null){
		        					for(OneTimeVO oneTimeVO : billstat){
		        						if(payableFlagForPri[count].equals(oneTimeVO.getFieldDescription())){
		        							primaryProrationVO.setPayableFlag(oneTimeVO.getFieldValue());	
		        						}			        				
		        					}
		        				}
		        				}
		        			}else if("I".equals(primaryProrationVO.getOperationFlag())){
		        				primaryProrationVO.setPayableFlag(payableFlagForPri[count]);	
		        			}
		        			//added
		        			if(wtChgFlg>0 || newFlg>0){
		        			primaryProrationVO.setProrationFactor(Integer.parseInt(prorationFactorForPri[count]));
		        			
		        			if(secStat!=null){
	        					for(OneTimeVO secOneTime : secStat){
	        						if(sectorStatusForPri[count].equals(secOneTime.getFieldDescription())){
	        							primaryProrationVO.setSectorStatus(secOneTime.getFieldValue());	        						
	        						}			        				
	        					}
	        				}
	
				    		
				    		//Contract currency code
		        			primaryProrationVO.setCtrCurrencyCode(manualProrationSession.getBaseCurrency());
				    		
				    		try {
				    		amountincc=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
							amountincc.setAmount(Double.parseDouble(amtInCurForPri[count]));
							primaryProrationVO.setProratedAmtInCtrCur(amountincc);								
			    	
							amountinbas=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
							amountinbas.setAmount(Double.parseDouble(amtInBasForPri[count]));
							primaryProrationVO.setProrationAmtInBaseCurr(amountinbas);				    		
			    	
							amountinusd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
				    		amountinusd.setAmount(Double.parseDouble(amtInUsdForPri[count]));
				    		primaryProrationVO.setProrationAmtInUsd(amountinusd);
	
							amountinsdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
							amountinsdr.setAmount(Double.parseDouble(amtInSdrForPri[count]));
							primaryProrationVO.setProrationAmtInSdr(amountinsdr);
				    		}
				    		catch(CurrencyException currencyException){
				    			log.log(Log.SEVERE,"CurrencyException!!!!!!!");
			    	        }
				    		}
				    		if("I".equals(primaryProrationVO.getOperationFlag())){
				    			primaryProrationVOs.add(primaryProrationVO);
				    		}
        	}
        		count++;
        		
        	}
        }
                
        
        log.log(Log.INFO, "AFTER FIRST HALF updateManualProrationSession",
				manualProrationSession.getProrationDetailVOs());
		ArrayList<ProrationDetailsVO> secondaryProrationVOs =(ArrayList<ProrationDetailsVO>) manualProrationSession.getSecondaryProrationVOs();
        
        if (secondaryProrationVOs == null) {
        	secondaryProrationVOs = new ArrayList<ProrationDetailsVO>();
		}
        
        
      ArrayList<ProrationDetailsVO> toRemoveSecProrationVOs = new ArrayList<ProrationDetailsVO>();
        
    	for(ProrationDetailsVO secProrationVO :secondaryProrationVOs){
			if("I".equals(secProrationVO.getOperationFlag())){
				toRemoveSecProrationVOs.add(secProrationVO);	
			}
		}
			
	
	  if(toRemoveSecProrationVOs.size()>0){
		  secondaryProrationVOs.removeAll(toRemoveSecProrationVOs);
		  prorationDetailsVOs.removeAll(toRemoveprimaryProrationVOs);
        }
        
        
        String[] hiddenOpFlagForSec=manualProrationForm.getHiddenOpFlagForSec();
        
    	String[] carrierCodeForSec=manualProrationForm.getCarrierCodeForSec();
    	String[] sectorFromForSec=manualProrationForm.getSectorFromForSec();
    	String[] sectorToForSec=manualProrationForm.getSectorToForSec();
    	String[] numberOfPiecesForSec=manualProrationForm.getNumberOfPiecesForSec();
    	String[] weightForSec=manualProrationForm.getWeightForSec();
    	String[] payableFlagForSec=manualProrationForm.getPayableFlagForSec();
    	String[] prorationTypeForSec=manualProrationForm.getProrationTypeForSec();
    	String[] prorationPercentForSec=manualProrationForm.getProrationPercentageForSec();
    	String[] amtInUsdForSec=manualProrationForm.getProrationAmtInUsdForSec();
    	String[] amtInSdrForSec=manualProrationForm.getProrationAmtInSdrForSec();
        String[] amtInBasForSec=manualProrationForm.getProrationAmtInBaseCurrForSec();    
        String[] amtInCurForSec=manualProrationForm.getProratedAmtInCtrCurForSec();
        String[] sectorStatusForSec=manualProrationForm.getSectorStatusForSec();
        
        
        if(hiddenOpFlagForSec!=null && hiddenOpFlagForSec.length>0 ){
        	int count=0;
        	for(String opflag :hiddenOpFlagForSec){	
        		if(!"NOOP".equals(opflag)){
        		ProrationDetailsVO secondaryProrationVO = null;
			        if("U".equals(opflag)){
			        	secondaryProrationVO = secondaryProrationVOs.get(count);
			        	secondaryProrationVO.setOperationFlag("U");
			        	if(secondaryProrationVO.getWeight()!=Double.parseDouble(weightForSec[count])){
			        		wtChgFlg++;
			        	}
			        }else if("D".equals(opflag)){
			        	secondaryProrationVO = secondaryProrationVOs.get(count);
			        	secondaryProrationVO.setOperationFlag("D");
			        }else if("I".equals(opflag)){
			        	newFlg++;
			        	secondaryProrationVO = new ProrationDetailsVO();
			        	secondaryProrationVO.setOperationFlag("I");
			        	secondaryProrationVO.setConsigneeSequenceNumber(csgseqnum);
			        	secondaryProrationVO.setConsigneeDocumentNumber(csgdocnum);
			        	secondaryProrationVO.setBillingBasis(blgbas);
			        	secondaryProrationVO.setCompanyCode(cmpcod);
			        	secondaryProrationVO.setPostalAuthorityCode(poacod);
			        	secondaryProrationVO.setSerialNumber(serialNumber);
						serialNumber=serialNumber+1;
						//temprly set as business in not clear
						secondaryProrationVO.setGpaarlBillingFlag("A");
	        		} 				        	
		        			Money amountincc=null;
		        	    	Money amountinbas=null;
		        	    	Money amountinusd=null;
		        	    	Money amountinsdr=null;
//		        	    	changed BY INDU for avoiding updating values without weight change
		        	    	if(wtChgFlg>0 || newFlg>0){
		        	    	secondaryProrationVO.setCarrierCode(carrierCodeForSec[count]);
		        	    	secondaryProrationVO.setSectorFrom(sectorFromForSec[count]);
		        	    	secondaryProrationVO.setSectorTo(sectorToForSec[count]);
		        	    	secondaryProrationVO.setNumberOfPieces(Integer.parseInt(numberOfPiecesForSec[count]));
		        	    	secondaryProrationVO.setWeight(Double.parseDouble(weightForSec[count]));
		        	    	secondaryProrationVO.setProrationType(prorationTypeForSec[count]);
		        	    	
		        	    	secondaryProrationVO.setProrationPercentage(Double.parseDouble(prorationPercentForSec[count]));
		        	    	}
		        	    	if(secondaryProrationVO.getOperationFlag()!=null && (!"I".equals(secondaryProrationVO.getOperationFlag()))){
		        	    		if(wtChgFlg>0){
		        			if(billstat!=null){
			        			for(OneTimeVO oneTimeVO : billstat){
			        				if(payableFlagForSec[count].equals(oneTimeVO.getFieldDescription())){
			        					secondaryProrationVO.setPayableFlag(oneTimeVO.getFieldValue());
			        				}			        				
			        			}
		        			}
			        		}
		        	    	}else if("I".equals(secondaryProrationVO.getOperationFlag())){
		        	    		secondaryProrationVO.setPayableFlag(payableFlagForSec[count]);
		        	    	}
//		        	    	added
		        			if(wtChgFlg>0 || newFlg>0){
		        	    	if(secStat!=null){
	        					for(OneTimeVO secOneTime : secStat){
	        						if(sectorStatusForSec[count].equals(secOneTime.getFieldDescription())){
	        							secondaryProrationVO.setSectorStatus(secOneTime.getFieldValue());	        						
	        						}			        				
	        					}
	        				}

							//Contract currency code
		        	    	secondaryProrationVO.setCtrCurrencyCode(manualProrationSession.getBaseCurrency());
							try {
								amountincc=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
								amountincc.setAmount(Double.parseDouble(amtInCurForSec[count]));
								secondaryProrationVO.setProratedAmtInCtrCur(amountincc);


								amountinbas=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
								amountinbas.setAmount(Double.parseDouble(amtInBasForSec[count]));
								secondaryProrationVO.setProrationAmtInBaseCurr(amountinbas);


								amountinusd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
								amountinusd.setAmount(Double.parseDouble(amtInUsdForSec[count]));
								secondaryProrationVO.setProrationAmtInUsd(amountinusd);

								amountinsdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
								amountinsdr.setAmount(Double.parseDouble(amtInSdrForSec[count]));
								secondaryProrationVO.setProrationAmtInSdr(amountinsdr);
							}catch(CurrencyException currencyException){
								log.log(Log.SEVERE,"CurrencyException!!!!!!!");
							}
		        			}
				    		if("I".equals(secondaryProrationVO.getOperationFlag())){
				    			secondaryProrationVOs.add(secondaryProrationVO);
				    		}
				    		
        		}
        		count++;
        	}
        	
        	
        }     
        
        log.log(Log.INFO, "AFTER SECOND HALF updateManualProrationSession",
				manualProrationSession.getProrationDetailVOs());
		ArrayList<ProrationDetailsVO> priTrecs =new ArrayList<ProrationDetailsVO>();
       
        ArrayList<ProrationDetailsVO> newProrationVOs =new ArrayList<ProrationDetailsVO>();
    
		Money totalTrecInUsd = null;
		Money totalTrecInBase = null;
		Money totalTrecInSdr = null;
		Money totalTrecInCtr = null;
		
		try {
			totalTrecInUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			totalTrecInUsd.setAmount(0.0);
			totalTrecInBase = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
			totalTrecInBase.setAmount(0.0);
			totalTrecInSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
			totalTrecInSdr.setAmount(0.0);
			totalTrecInCtr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
			totalTrecInCtr.setAmount(0.0);

		} catch (CurrencyException e) {
			e.getErrorCode();
		}
		
        for(ProrationDetailsVO priProrationVO : secondaryProrationVOs){
        	if("T".equals(priProrationVO.getPayableFlag())){
        		priTrecs.add(priProrationVO);	
        		
        	}
        }
               
     
		String tOrgin ="";
		String tDestn ="";
		int profactor=0;
		int siz=0;
		if(priTrecs!=null && priTrecs.size() >1){
			tOrgin = priTrecs.get(0).getSectorFrom();
			tDestn = priTrecs.get(0).getSectorTo();
			profactor= priTrecs.get(0).getProrationFactor();
			newProrationVOs.add( priTrecs.get(0));
			siz=priTrecs.size()-1;
			for(int i =0;i<siz;i++){
				String secto =  priTrecs.get(i).getSectorTo();
				String nxtsecfrm = priTrecs.get(i+1).getSectorFrom();						
				if(secto.equals(nxtsecfrm)){
					tDestn = priTrecs.get(i+1).getSectorTo();
					profactor=profactor+priTrecs.get(i+1).getProrationFactor();		
				
					totalTrecInUsd.setAmount((priTrecs.get(i).getProrationAmtInUsd().getAmount())+(priTrecs.get(i+1).getProrationAmtInUsd().getAmount()));
					totalTrecInBase.setAmount((priTrecs.get(i).getProrationAmtInBaseCurr().getAmount())+(priTrecs.get(i+1).getProrationAmtInBaseCurr().getAmount()));
					totalTrecInSdr.setAmount((priTrecs.get(i).getProrationAmtInSdr().getAmount())+(priTrecs.get(i+1).getProrationAmtInSdr().getAmount()));
					totalTrecInCtr.setAmount((priTrecs.get(i).getProratedAmtInCtrCur().getAmount())+(priTrecs.get(i+1).getProratedAmtInCtrCur().getAmount()));
				}	
			}
		}
	
		ProrationDetailsVO priProrationVO = null;
		
		if(priTrecsToremove!=null && priTrecsToremove.size()>0){
			
			primaryProrationVOs.removeAll(priTrecsToremove);
			
			for(ProrationDetailsVO newProrationVO :newProrationVOs){
				
				priProrationVO =  new ProrationDetailsVO();						
				
				priProrationVO.setOperationFlag("N");						
				priProrationVO.setSectorFrom(tOrgin);
				priProrationVO.setSectorTo(tDestn);
				priProrationVO.setProrationFactor(profactor);
				priProrationVO.setPayableFlag("T");
				priProrationVO.setProrationPercentage(100);
				priProrationVO.setNumberOfPieces(newProrationVO.getNumberOfPieces());
				priProrationVO.setWeight(newProrationVO.getWeight());
				priProrationVO.setProrationType(newProrationVO.getProrationType());
				priProrationVO.setCarrierCode(newProrationVO.getCarrierCode());
				priProrationVO.setProrationAmtInUsd(totalTrecInUsd);
				priProrationVO.setProrationAmtInBaseCurr(totalTrecInBase);
				priProrationVO.setProrationAmtInSdr(totalTrecInSdr);
				priProrationVO.setProratedAmtInCtrCur(totalTrecInCtr);
				priProrationVO.setSectorStatus(newProrationVO.getSectorStatus());
				priProrationVO.setGpaarlBillingFlag("A");
				primaryProrationVOs.add(priProrationVO);
			}					
		}
		log.log(Log.INFO, "OUTSIDE  SESSION SET ", manualProrationSession.getProrationDetailVOs());
		if(wtChgFlg>0 || newFlg>0){
		log.log(log.INFO,"INSIDE SESSION SET");
        manualProrationSession.setPrimaryProrationVOs(primaryProrationVOs);
        manualProrationSession.setSecondaryProrationVOs(secondaryProrationVOs);
        
        
        Collection<ProrationDetailsVO> oldreceiveableProrations = new ArrayList<ProrationDetailsVO>();
        
        for(ProrationDetailsVO prorationDataVO : prorationDetailsVOs){
        	  if("G".equals(prorationDataVO.getGpaarlBillingFlag())){	
  				if("R".equals(prorationDataVO.getPayableFlag())){
  					oldreceiveableProrations.add(prorationDataVO);
  				}	  				
  			}
        }
              
        prorationDetailsVOs.clear();
        
        if(oldreceiveableProrations.size()>0){
        	prorationDetailsVOs.addAll(oldreceiveableProrations);
        }
        
        for(ProrationDetailsVO primaryProrationVO : primaryProrationVOs){
        	if(primaryProrationVO.getPayableFlag()!=null && !("T".equals(primaryProrationVO.getPayableFlag()))){
        		prorationDetailsVOs.add(primaryProrationVO);
        	}
        }
        for(ProrationDetailsVO secondaryProrationVO : secondaryProrationVOs){	        	
        		prorationDetailsVOs.add(secondaryProrationVO);	        	
        }
        
        manualProrationSession.setProrationDetailVOs(prorationDetailsVOs);
       return 0;
	} else {
			return 1;
		}
	}
	
	/*
	 * updateManualProrationSession A-3251
	 */
	private void updateManualProrationSession(ManualProrationSession manualProrationSession,ManualProrationForm manualProrationForm){

		
		ArrayList<ProrationDetailsVO> prorationDetailsVOs= (ArrayList<ProrationDetailsVO>)manualProrationSession.getProrationDetailVOs();

		ArrayList<ProrationDetailsVO> primaryProrationVOs = (ArrayList<ProrationDetailsVO>)manualProrationSession.getPrimaryProrationVOs();
		
		ArrayList<ProrationDetailsVO> toRemoveprimaryProrationVOs = new ArrayList<ProrationDetailsVO>();
		
		int serialNumber =1;
		String csgseqnum="";
		String csgdocnum="";
		String blgbas="";
		String cmpcod="";
		String poacod="";
		
	

	
		if(prorationDetailsVOs!=null){
			    for(ProrationDetailsVO proVO : prorationDetailsVOs ) {   	

							 csgseqnum =   proVO.getConsigneeSequenceNumber();
							 csgdocnum =   proVO.getConsigneeDocumentNumber();
							 blgbas = proVO.getBillingBasis();
							 cmpcod = proVO.getCompanyCode();
							 poacod = proVO.getPostalAuthorityCode();
							 serialNumber=serialNumber+1;
		    }
		}
	   
		
    	
		if (primaryProrationVOs == null) {
			primaryProrationVOs = new ArrayList<ProrationDetailsVO>();
		}
		
		String[] hiddenOpFlagForPri=manualProrationForm.getHiddenOpFlagForPri();    	
    	
    	String[] carrierCodeForPri=manualProrationForm.getCarrierCodeForPri();
    	String[] sectorFromForPri=manualProrationForm.getSectorFromForPri();
    	String[] sectorToForPri=manualProrationForm.getSectorToForPri();
    	String[] numberOfPiecesForPri=manualProrationForm.getNumberOfPiecesForPri();
    	String[] weightForPri=manualProrationForm.getWeightForPri();
    	String[] payableFlagForPri=manualProrationForm.getPayableFlagForPri();
    	String[] prorationTypeForPri=manualProrationForm.getProrationTypeForPri();
    	String[] prorationPercentForPri=manualProrationForm.getProrationPercentageForPri();
    	String[] prorationFactorForPri=manualProrationForm.getProrationFactorForPri();
    	String[] amtInUsdForPri=manualProrationForm.getProrationAmtInUsdForPri();
    	String[] amtInSdrForPri=manualProrationForm.getProrationAmtInSdrForPri();
        String[] amtInBasForPri=manualProrationForm.getProrationAmtInBaseCurrForPri();    
        String[] amtInCurForPri=manualProrationForm.getProratedAmtInCtrCurForPri();
        String[] sectorStatusForPri=manualProrationForm.getSectorStatusForPri();
        
    	Map<String, Collection<OneTimeVO>> oneTimeValues = (HashMap<String,Collection<OneTimeVO>>) manualProrationSession.getOneTimeVOs();
    	 
    	ArrayList<OneTimeVO> billstat = (ArrayList<OneTimeVO>) oneTimeValues.get(PAYFLAG_ONETIME);
    	ArrayList<OneTimeVO> secStat = (ArrayList<OneTimeVO>) oneTimeValues.get(SECTORSTATUS_ONETIME);
    
    
    		for(ProrationDetailsVO primaryProrationVO :primaryProrationVOs){
    			if("I".equals(primaryProrationVO.getOperationFlag())){
    			toRemoveprimaryProrationVOs.add(primaryProrationVO);	
    			}
    		}
    			
    	
    	  if(toRemoveprimaryProrationVOs.size()>0){
	        	primaryProrationVOs.removeAll(toRemoveprimaryProrationVOs);
	        	prorationDetailsVOs.removeAll(toRemoveprimaryProrationVOs);
	        }
	        
    ArrayList<ProrationDetailsVO> priTrecsToremove =new ArrayList<ProrationDetailsVO>();
    	
        if(hiddenOpFlagForPri!=null && hiddenOpFlagForPri.length>0 ){
        	int count=0;
        	for(String opflag :hiddenOpFlagForPri){
        		if(!"NOOP".equals(opflag)){
        		ProrationDetailsVO primaryProrationVO = null;
			        if("U".equals(opflag)){
			        	primaryProrationVO = primaryProrationVOs.get(count);
			        	primaryProrationVO.setOperationFlag("U");



			        }else if("D".equals(opflag)){
			        	primaryProrationVO = primaryProrationVOs.get(count);
			        	primaryProrationVO.setOperationFlag("D");
			        }else if("N".equals(opflag)){
			        	primaryProrationVO = primaryProrationVOs.get(count);
			        	primaryProrationVO.setOperationFlag("N");
			        	priTrecsToremove.add(primaryProrationVO);
			        }else if("I".equals(opflag)){

			            primaryProrationVO = new ProrationDetailsVO();
			            primaryProrationVO.setOperationFlag("I");       
			            
			            primaryProrationVO.setConsigneeSequenceNumber(csgseqnum);
					    primaryProrationVO.setConsigneeDocumentNumber(csgdocnum);
					    primaryProrationVO.setBillingBasis(blgbas);
					    primaryProrationVO.setCompanyCode(cmpcod);
						primaryProrationVO.setPostalAuthorityCode(poacod);
						primaryProrationVO.setSerialNumber(serialNumber);
						 serialNumber=serialNumber+1;	
						 //temprly set as business in not clear
						 primaryProrationVO.setGpaarlBillingFlag("A");
						 
	        		} 				        	
		        			Money amountincc=null;
		        	    	Money amountinbas=null;
		        	    	Money amountinusd=null;
		        	    	Money amountinsdr=null;
		        			
		        			primaryProrationVO.setCarrierCode(carrierCodeForPri[count]);
		        			primaryProrationVO.setSectorFrom(sectorFromForPri[count]);
		        			primaryProrationVO.setSectorTo(sectorToForPri[count]);
		        			primaryProrationVO.setNumberOfPieces(Integer.parseInt(numberOfPiecesForPri[count]));
		        			primaryProrationVO.setWeight(Double.parseDouble(weightForPri[count]));
		        			primaryProrationVO.setProrationType(prorationTypeForPri[count]);
				
		        			primaryProrationVO.setProrationPercentage(Double.parseDouble(prorationPercentForPri[count]));
		        			
		        			if(primaryProrationVO.getOperationFlag()!=null && (!"I".equals(primaryProrationVO.getOperationFlag()))){
		        				if(billstat!=null){
		        					for(OneTimeVO oneTimeVO : billstat){
		        						if(payableFlagForPri[count].equals(oneTimeVO.getFieldDescription())){
		        							primaryProrationVO.setPayableFlag(oneTimeVO.getFieldValue());	
		        						}			        				
		        					}
		        				}
		        			}else if("I".equals(primaryProrationVO.getOperationFlag())){
		        				primaryProrationVO.setPayableFlag(payableFlagForPri[count]);	
		        			}
		        			
		        			primaryProrationVO.setProrationFactor(Integer.parseInt(prorationFactorForPri[count]));
		        			
		        			if(secStat!=null){
	        					for(OneTimeVO secOneTime : secStat){
	        						if(sectorStatusForPri[count].equals(secOneTime.getFieldDescription())){
	        							primaryProrationVO.setSectorStatus(secOneTime.getFieldValue());	        						
	        						}			        				
	        					}
	        				}
	
				    		
				    		//Contract currency code
		        			primaryProrationVO.setCtrCurrencyCode(manualProrationSession.getBaseCurrency());
				    		
				    		try {
				    		amountincc=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
							amountincc.setAmount(Double.parseDouble(amtInCurForPri[count]));
							primaryProrationVO.setProratedAmtInCtrCur(amountincc);								
			    	
							amountinbas=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
							amountinbas.setAmount(Double.parseDouble(amtInBasForPri[count]));
							primaryProrationVO.setProrationAmtInBaseCurr(amountinbas);				    		
			    	
							amountinusd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
				    		amountinusd.setAmount(Double.parseDouble(amtInUsdForPri[count]));
				    		primaryProrationVO.setProrationAmtInUsd(amountinusd);
	
							amountinsdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
							amountinsdr.setAmount(Double.parseDouble(amtInSdrForPri[count]));
							primaryProrationVO.setProrationAmtInSdr(amountinsdr);
							
				    		}catch(CurrencyException currencyException){
				    			log.log(Log.SEVERE,"CurrencyException!!!!!!!");
			    	        }
				    		if("I".equals(primaryProrationVO.getOperationFlag())){
				    			primaryProrationVOs.add(primaryProrationVO);
				    		}
        	}
        		count++;
        		
        	}
        }
                
        
        
        
        ArrayList<ProrationDetailsVO> secondaryProrationVOs =(ArrayList<ProrationDetailsVO>) manualProrationSession.getSecondaryProrationVOs();
        
        if (secondaryProrationVOs == null) {
        	secondaryProrationVOs = new ArrayList<ProrationDetailsVO>();
		}
        
        
      ArrayList<ProrationDetailsVO> toRemoveSecProrationVOs = new ArrayList<ProrationDetailsVO>();
        
    	for(ProrationDetailsVO secProrationVO :secondaryProrationVOs){
			if("I".equals(secProrationVO.getOperationFlag())){
				toRemoveSecProrationVOs.add(secProrationVO);	
			}
		}
			
	
	  if(toRemoveSecProrationVOs.size()>0){
		  secondaryProrationVOs.removeAll(toRemoveSecProrationVOs);
		  prorationDetailsVOs.removeAll(toRemoveprimaryProrationVOs);
        }
        
        
        String[] hiddenOpFlagForSec=manualProrationForm.getHiddenOpFlagForSec();
        
    	String[] carrierCodeForSec=manualProrationForm.getCarrierCodeForSec();
    	String[] sectorFromForSec=manualProrationForm.getSectorFromForSec();
    	String[] sectorToForSec=manualProrationForm.getSectorToForSec();
    	String[] numberOfPiecesForSec=manualProrationForm.getNumberOfPiecesForSec();
    	String[] weightForSec=manualProrationForm.getWeightForSec();
    	String[] payableFlagForSec=manualProrationForm.getPayableFlagForSec();
    	String[] prorationTypeForSec=manualProrationForm.getProrationTypeForSec();
    	String[] prorationPercentForSec=manualProrationForm.getProrationPercentageForSec();
    	String[] amtInUsdForSec=manualProrationForm.getProrationAmtInUsdForSec();
    	String[] amtInSdrForSec=manualProrationForm.getProrationAmtInSdrForSec();
        String[] amtInBasForSec=manualProrationForm.getProrationAmtInBaseCurrForSec();    
        String[] amtInCurForSec=manualProrationForm.getProratedAmtInCtrCurForSec();
        String[] sectorStatusForSec=manualProrationForm.getSectorStatusForSec();
        
        
        if(hiddenOpFlagForSec!=null && hiddenOpFlagForSec.length>0 ){
        	int count=0;
        	for(String opflag :hiddenOpFlagForSec){	
        		if(!"NOOP".equals(opflag)){
        		ProrationDetailsVO secondaryProrationVO = null;
			        if("U".equals(opflag)){
			        	secondaryProrationVO = secondaryProrationVOs.get(count);
			        	secondaryProrationVO.setOperationFlag("U");



			        }else if("D".equals(opflag)){
			        	secondaryProrationVO = secondaryProrationVOs.get(count);
			        	secondaryProrationVO.setOperationFlag("D");
			        }else if("I".equals(opflag)){

			        	secondaryProrationVO = new ProrationDetailsVO();
			        	secondaryProrationVO.setOperationFlag("I");
			        	secondaryProrationVO.setConsigneeSequenceNumber(csgseqnum);
			        	secondaryProrationVO.setConsigneeDocumentNumber(csgdocnum);
			        	secondaryProrationVO.setBillingBasis(blgbas);
			        	secondaryProrationVO.setCompanyCode(cmpcod);
			        	secondaryProrationVO.setPostalAuthorityCode(poacod);
			        	secondaryProrationVO.setSerialNumber(serialNumber);
						serialNumber=serialNumber+1;
						//temprly set as business in not clear
						secondaryProrationVO.setGpaarlBillingFlag("A");
	        		} 				        	
		        			Money amountincc=null;
		        	    	Money amountinbas=null;
		        	    	Money amountinusd=null;
		        	    	Money amountinsdr=null;
		        			
		        	    	secondaryProrationVO.setCarrierCode(carrierCodeForSec[count]);
		        	    	secondaryProrationVO.setSectorFrom(sectorFromForSec[count]);
		        	    	secondaryProrationVO.setSectorTo(sectorToForSec[count]);
		        	    	secondaryProrationVO.setNumberOfPieces(Integer.parseInt(numberOfPiecesForSec[count]));
		        	    	secondaryProrationVO.setWeight(Double.parseDouble(weightForSec[count]));
		        	    	secondaryProrationVO.setProrationType(prorationTypeForSec[count]);

		        	    	secondaryProrationVO.setProrationPercentage(Double.parseDouble(prorationPercentForSec[count]));
		        	    	if(secondaryProrationVO.getOperationFlag()!=null && (!"I".equals(secondaryProrationVO.getOperationFlag()))){
		        			if(billstat!=null){
			        			for(OneTimeVO oneTimeVO : billstat){
			        				if(payableFlagForSec[count].equals(oneTimeVO.getFieldDescription())){
			        					secondaryProrationVO.setPayableFlag(oneTimeVO.getFieldValue());
			        				}			        				
			        			}
			        		}
		        	    	}else if("I".equals(secondaryProrationVO.getOperationFlag())){
		        	    		secondaryProrationVO.setPayableFlag(payableFlagForSec[count]);
		        	    	}
							
		        	    	if(secStat!=null){
	        					for(OneTimeVO secOneTime : secStat){
	        						if(sectorStatusForSec[count].equals(secOneTime.getFieldDescription())){
	        							secondaryProrationVO.setSectorStatus(secOneTime.getFieldValue());	        						
	        						}			        				
	        					}
	        				}

							//Contract currency code
		        	    	secondaryProrationVO.setCtrCurrencyCode(manualProrationSession.getBaseCurrency());
							try {
								amountincc=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
								amountincc.setAmount(Double.parseDouble(amtInCurForSec[count]));
								secondaryProrationVO.setProratedAmtInCtrCur(amountincc);


								amountinbas=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
								amountinbas.setAmount(Double.parseDouble(amtInBasForSec[count]));
								secondaryProrationVO.setProrationAmtInBaseCurr(amountinbas);


								amountinusd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
								amountinusd.setAmount(Double.parseDouble(amtInUsdForSec[count]));
								secondaryProrationVO.setProrationAmtInUsd(amountinusd);

								amountinsdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
								amountinsdr.setAmount(Double.parseDouble(amtInSdrForSec[count]));
								secondaryProrationVO.setProrationAmtInSdr(amountinsdr);
							}catch(CurrencyException currencyException){
								log.log(Log.SEVERE,"CurrencyException!!!!!!!");
							}
				    		if("I".equals(secondaryProrationVO.getOperationFlag())){
				    			secondaryProrationVOs.add(secondaryProrationVO);
				    		}
				    		
        		}
        		count++;
        	}
        	
        	
        }     
        
        
        
		//------------- To group  primary retention records for multi sector flight
		
        
        ArrayList<ProrationDetailsVO> priTrecs =new ArrayList<ProrationDetailsVO>();
       
        ArrayList<ProrationDetailsVO> newProrationVOs =new ArrayList<ProrationDetailsVO>();
    
		Money totalTrecInUsd = null;
		Money totalTrecInBase = null;
		Money totalTrecInSdr = null;
		Money totalTrecInCtr = null;
		
		try {
			totalTrecInUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			totalTrecInUsd.setAmount(0.0);
			totalTrecInBase = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
			totalTrecInBase.setAmount(0.0);
			totalTrecInSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
			totalTrecInSdr.setAmount(0.0);
			totalTrecInCtr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
			totalTrecInCtr.setAmount(0.0);

		} catch (CurrencyException e) {
			e.getErrorCode();
		}
		
        for(ProrationDetailsVO priProrationVO : secondaryProrationVOs){
        	if("T".equals(priProrationVO.getPayableFlag())){
        		priTrecs.add(priProrationVO);	
        		
        	}
        }
               
     
		String tOrgin ="";
		String tDestn ="";
		int profactor=0;
		int siz=0;
		if(priTrecs!=null && priTrecs.size() >1){
			tOrgin = priTrecs.get(0).getSectorFrom();
			tDestn = priTrecs.get(0).getSectorTo();
			profactor= priTrecs.get(0).getProrationFactor();
			newProrationVOs.add( priTrecs.get(0));
			siz=priTrecs.size()-1;
			for(int i =0;i<siz;i++){
				String secto =  priTrecs.get(i).getSectorTo();
				String nxtsecfrm = priTrecs.get(i+1).getSectorFrom();						
				if(secto.equals(nxtsecfrm)){
					tDestn = priTrecs.get(i+1).getSectorTo();
					profactor=profactor+priTrecs.get(i+1).getProrationFactor();		
				
					totalTrecInUsd.setAmount((priTrecs.get(i).getProrationAmtInUsd().getAmount())+(priTrecs.get(i+1).getProrationAmtInUsd().getAmount()));
					totalTrecInBase.setAmount((priTrecs.get(i).getProrationAmtInBaseCurr().getAmount())+(priTrecs.get(i+1).getProrationAmtInBaseCurr().getAmount()));
					totalTrecInSdr.setAmount((priTrecs.get(i).getProrationAmtInSdr().getAmount())+(priTrecs.get(i+1).getProrationAmtInSdr().getAmount()));
					totalTrecInCtr.setAmount((priTrecs.get(i).getProratedAmtInCtrCur().getAmount())+(priTrecs.get(i+1).getProratedAmtInCtrCur().getAmount()));
				}	
			}
		}
	
		ProrationDetailsVO priProrationVO = null;
		
		if(priTrecsToremove!=null && priTrecsToremove.size()>0){
			
			primaryProrationVOs.removeAll(priTrecsToremove);
			
			for(ProrationDetailsVO newProrationVO :newProrationVOs){
				
				priProrationVO =  new ProrationDetailsVO();						
				
				priProrationVO.setOperationFlag("N");						
				priProrationVO.setSectorFrom(tOrgin);
				priProrationVO.setSectorTo(tDestn);
				priProrationVO.setProrationFactor(profactor);
				priProrationVO.setPayableFlag("T");
				priProrationVO.setProrationPercentage(100);
				priProrationVO.setNumberOfPieces(newProrationVO.getNumberOfPieces());
				priProrationVO.setWeight(newProrationVO.getWeight());
				priProrationVO.setProrationType(newProrationVO.getProrationType());
				priProrationVO.setCarrierCode(newProrationVO.getCarrierCode());
				priProrationVO.setProrationAmtInUsd(totalTrecInUsd);
				priProrationVO.setProrationAmtInBaseCurr(totalTrecInBase);
				priProrationVO.setProrationAmtInSdr(totalTrecInSdr);
				priProrationVO.setProratedAmtInCtrCur(totalTrecInCtr);
				priProrationVO.setSectorStatus(newProrationVO.getSectorStatus());
				priProrationVO.setGpaarlBillingFlag("A");
				primaryProrationVOs.add(priProrationVO);
			}					
		}
		//---------------------END 	 multi sector flight
		
		
		
        manualProrationSession.setPrimaryProrationVOs(primaryProrationVOs);
        manualProrationSession.setSecondaryProrationVOs(secondaryProrationVOs);
        
        
        Collection<ProrationDetailsVO> oldreceiveableProrations = new ArrayList<ProrationDetailsVO>();
        
        for(ProrationDetailsVO prorationDataVO : prorationDetailsVOs){
        	  if("G".equals(prorationDataVO.getGpaarlBillingFlag())){	
  				if("R".equals(prorationDataVO.getPayableFlag())){
  					oldreceiveableProrations.add(prorationDataVO);
  				}	  				
  			}
        }
              
        prorationDetailsVOs.clear();
        
        if(oldreceiveableProrations.size()>0){
        	prorationDetailsVOs.addAll(oldreceiveableProrations);
        }
        
        for(ProrationDetailsVO primaryProrationVO : primaryProrationVOs){
        	if(primaryProrationVO.getPayableFlag()!=null && !("T".equals(primaryProrationVO.getPayableFlag()))){
        		prorationDetailsVOs.add(primaryProrationVO);
        	}
        }
        for(ProrationDetailsVO secondaryProrationVO : secondaryProrationVOs){	        	
        		prorationDetailsVOs.add(secondaryProrationVO);	        	
        }
        
        manualProrationSession.setProrationDetailVOs(prorationDetailsVOs);
		



	}

}

