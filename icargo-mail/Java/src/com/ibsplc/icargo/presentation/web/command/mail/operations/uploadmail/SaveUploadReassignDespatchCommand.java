/*
 * SaveUploadReassignDespatchCommand.java Created on Sept 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class SaveUploadReassignDespatchCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	private static final String SAVED ="SAVED";
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("SaveUploadReassignDespatchCommand","execute");
		UploadMailForm uploadMailForm 
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		Collection<ScannedMailDetailsVO> scannedReassignDespatchVOs = scannedDetailsVO.getReassignDespatch();		
		Collection<ScannedMailDetailsVO> scannedReassignmailFinalVOs = new ArrayList<ScannedMailDetailsVO>();
		
	   Collection<OneTimeVO> catVOs = uploadMailSession.getOneTimeCat();
       
       Collection<DespatchDetailsVO> despatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
		
       /**
		 * Validation 1:-
		 * validate mailbags  - with onetime
		 */
       Collection<ScannedMailDetailsVO> scannedReassignSecondVOs = new ArrayList<ScannedMailDetailsVO>();
        boolean isCorrect = false;
        String catErr = "Mail category code is invalid";	
		if(scannedReassignDespatchVOs != null && scannedReassignDespatchVOs.size() > 0){
			for(ScannedMailDetailsVO intialscannedMailDetailsVO:scannedReassignDespatchVOs){
			Collection<DespatchDetailsVO> reassignDespatchVOs = intialscannedMailDetailsVO.getDespatchDetails();
			Collection<DespatchDetailsVO> despatchErrorVOs = new ArrayList<DespatchDetailsVO>();
			Collection<DespatchDetailsVO> despatchCorrectVOs = new ArrayList<DespatchDetailsVO>();
			for(DespatchDetailsVO despatchDetailsVO:reassignDespatchVOs){
				String error = "";
				for(OneTimeVO catVO:catVOs){
					if(despatchDetailsVO.getMailCategoryCode().equals(catVO.getFieldValue())){
						isCorrect = true;
						break;
					}
				}
				if(isCorrect){
					despatchCorrectVOs.add(despatchDetailsVO);
				}else{
					despatchDetailsVO.setErrorDescription(catErr);
					despatchErrorVOs.add(despatchDetailsVO);
				}
			  }
			if(despatchErrorVOs.size() > 0){
				intialscannedMailDetailsVO.setDespatchDetails(despatchErrorVOs);
				scannedReassignmailFinalVOs.add(intialscannedMailDetailsVO);
			}
			
			if(despatchCorrectVOs.size() > 0){
				intialscannedMailDetailsVO.setDespatchDetails(despatchCorrectVOs);
				scannedReassignSecondVOs.add(intialscannedMailDetailsVO);
			}
			
			}
			
			
			
		  }
		
			
			
			
			/**
			 * Validation 2:-
			 * validate duplicate despatches in clientend
			 */
		
			/**
			 * Checking for duplicate despatches in same container
			 */
			int count = 0;
			String errDesc ="";
			Collection<DespatchDetailsVO> despatches =  new ArrayList<DespatchDetailsVO>();
			if(scannedReassignSecondVOs != null && scannedReassignSecondVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO: scannedReassignSecondVOs){
					despatches = new ArrayList<DespatchDetailsVO>();
					despatchDetailsVOs = scannedVO.getDespatchDetails();
					for(DespatchDetailsVO despatchOuter : despatchDetailsVOs){
						
						String error = "";
						
						String firstPK = new StringBuilder()
			            .append(despatchOuter.getOriginOfficeOfExchange())
			            .append(despatchOuter.getDestinationOfficeOfExchange())
						.append(despatchOuter.getMailCategoryCode())
						.append(despatchOuter.getMailClass())
						.append(despatchOuter.getYear())
						.append(despatchOuter.getDsn())
						.append(despatchOuter.getStatedBags())
						.append(despatchOuter.getStatedWeight())
						.append(despatchOuter.getUldNumber()).toString();
						
						for(DespatchDetailsVO despatchInner: despatchDetailsVOs){
							
							String secondPK = new StringBuilder()
				            .append(despatchInner.getOriginOfficeOfExchange())
				            .append(despatchInner.getDestinationOfficeOfExchange())
							.append(despatchInner.getMailCategoryCode())
							.append(despatchInner.getMailClass())
							.append(despatchInner.getYear())
							.append(despatchInner.getDsn())
							.append(despatchInner.getStatedBags())
							.append(despatchInner.getStatedWeight())
							.append(despatchInner.getUldNumber()).toString();
							
							if(firstPK.equals(secondPK)){
								count++;
							}
						}
						if(count == 2){
							error = "DS";
						}
						if("".equals(error)){
							despatchOuter.setErrorType(null);
						}else{
							despatchOuter.setErrorType(error);
						}
						despatches.add(despatchOuter);
						count = 0;
					}
					scannedVO.setDespatchDetails(despatches);
				}
			}

			Collection<ScannedMailDetailsVO> scannedReassignThirdVOs = new ArrayList<ScannedMailDetailsVO>();
			if(scannedReassignSecondVOs != null && scannedReassignSecondVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO : scannedReassignSecondVOs){
					Collection<DespatchDetailsVO> despatchErrorVOs = new ArrayList<DespatchDetailsVO>();
					Collection<DespatchDetailsVO> despatchCorrectVOs = new ArrayList<DespatchDetailsVO>();
					despatchDetailsVOs = scannedVO.getDespatchDetails();
					for(DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs){
						if(despatchDetailsVO.getErrorType() != null){
							if("DS".equals(despatchDetailsVO.getErrorType())){
								errDesc = "Duplicate Despatches in Same container";
							}
							despatchDetailsVO.setErrorDescription(errDesc);
							despatchErrorVOs.add(despatchDetailsVO);
						}else{
							despatchCorrectVOs.add(despatchDetailsVO);
						}
					}
					if(despatchErrorVOs.size() > 0){
						scannedVO.setDespatchDetails(despatchErrorVOs);
						scannedReassignmailFinalVOs.add(scannedVO);
					}
					
					if(despatchCorrectVOs.size() > 0){
						scannedVO.setDespatchDetails(despatchCorrectVOs);
						scannedReassignThirdVOs.add(scannedVO);
					}
				}
			}
			

			/**
			 * Checking for duplicate mailbags acrosss container
			 */
			count = 0;
			Collection<DespatchDetailsVO> newDespatches = new ArrayList<DespatchDetailsVO>();
			if(scannedReassignThirdVOs != null && scannedReassignThirdVOs.size() > 0){
				for(ScannedMailDetailsVO scannedOuter : scannedReassignThirdVOs){
					
					newDespatches = new ArrayList<DespatchDetailsVO>();
					Collection<DespatchDetailsVO> despatchesOuter = scannedOuter.getDespatchDetails();
					
					for(DespatchDetailsVO despatchOuter: despatchesOuter){
						String error = "";
						
						
						String firstPK = new StringBuilder()
			            .append(despatchOuter.getOriginOfficeOfExchange())
			            .append(despatchOuter.getDestinationOfficeOfExchange())
						.append(despatchOuter.getMailCategoryCode())
						.append(despatchOuter.getMailClass())
						.append(despatchOuter.getYear())
						.append(despatchOuter.getDsn())
						.append(despatchOuter.getStatedBags())
						.append(despatchOuter.getStatedWeight())
						.append(despatchOuter.getUldNumber()).toString();
						
						for(ScannedMailDetailsVO scannedInner:scannedReassignThirdVOs){
							Collection<DespatchDetailsVO> despatchesInner = scannedInner.getDespatchDetails();
							for(DespatchDetailsVO despatchInner: despatchesInner){
								
								String secondPK = new StringBuilder()
					            .append(despatchInner.getOriginOfficeOfExchange())
					            .append(despatchInner.getDestinationOfficeOfExchange())
								.append(despatchInner.getMailCategoryCode())
								.append(despatchInner.getMailClass())
								.append(despatchInner.getYear())
								.append(despatchInner.getDsn())
								.append(despatchInner.getStatedBags())
								.append(despatchInner.getStatedWeight())
								.append(despatchInner.getUldNumber()).toString();
								
								
								if(firstPK.equals(secondPK)){
									count++;
								}
								
							}
						}
						if(count == 2){
							error = "DA";
						}
						if("".equals(error)){
							despatchOuter.setErrorType(null);
						}else{
							despatchOuter.setErrorType(error);
						}
						newDespatches.add(despatchOuter);
						count = 0;
					}
					scannedOuter.setDespatchDetails(newDespatches);
				}
			}

			Collection<ScannedMailDetailsVO> scannedReassignFourthVOs = new ArrayList<ScannedMailDetailsVO>();
			if(scannedReassignThirdVOs != null && scannedReassignThirdVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO : scannedReassignThirdVOs){
					Collection<DespatchDetailsVO> despatchErrorVOs = new ArrayList<DespatchDetailsVO>();
					Collection<DespatchDetailsVO> despatchCorrectVOs = new ArrayList<DespatchDetailsVO>();
					despatchDetailsVOs = scannedVO.getDespatchDetails();
					for(DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs){
						if(despatchDetailsVO.getErrorType() != null){
							if("DA".equals(despatchDetailsVO.getErrorType())){
								errDesc = "Duplicate Despatches Across container";
							}
							despatchDetailsVO.setErrorDescription(errDesc);
							despatchErrorVOs.add(despatchDetailsVO);
						}else{
							despatchCorrectVOs.add(despatchDetailsVO);
						}
					}
					
					if(despatchErrorVOs.size() > 0){
						scannedVO.setDespatchDetails(despatchErrorVOs);
						scannedReassignmailFinalVOs.add(scannedVO);
					}
					
					if(despatchCorrectVOs.size() > 0){
						scannedVO.setDespatchDetails(despatchCorrectVOs);
						scannedReassignFourthVOs.add(scannedVO);
					}
				}
			}
			

		setULDtype(scannedReassignFourthVOs);
		log.log(Log.INFO, "scannedReassignFourthVOs....after server.\n",
				scannedReassignFourthVOs);
		Collection<ScannedMailDetailsVO> scannedReassignmailsAfterVOs = new ArrayList<ScannedMailDetailsVO>();
		if(scannedReassignFourthVOs != null && scannedReassignFourthVOs.size() > 0){
			try {
				scannedReassignmailsAfterVOs = new MailTrackingDefaultsDelegate().reassignScannedDespatches(scannedReassignFourthVOs);
	        }catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 	    }
		}
		
		 /**
		   * merging scanned VOs from server with available scanned error VOs
		   */
		Collection<ScannedMailDetailsVO> finalscannedFinalVOs = new ArrayList<ScannedMailDetailsVO>();
		boolean flag = false;
		if(scannedReassignFourthVOs != null && scannedReassignFourthVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedReassignFourthVOs){
				if(scannedReassignmailsAfterVOs != null && scannedReassignmailsAfterVOs.size() > 0){
					for(ScannedMailDetailsVO scannedInner:scannedReassignmailsAfterVOs){
						 flag = false;
						 if(scannedOuter.getCompanyCode().equals(scannedInner.getCompanyCode())
										&& scannedOuter.getCarrierId() == scannedInner.getCarrierId()
										&& scannedOuter.getFlightNumber().equals(scannedInner.getFlightNumber())
										&& scannedOuter.getFlightSequenceNumber() == scannedInner.getFlightSequenceNumber()
										&& scannedOuter.getContainerNumber().equals(scannedInner.getContainerNumber())){
								 		log.log(Log.INFO,"same vos.....");
								 		flag = true;
						            	break;
						 }
					}
				 if(!flag){
					 finalscannedFinalVOs.add(scannedOuter);
			     }
				}
			}
			if(scannedReassignmailsAfterVOs != null && scannedReassignmailsAfterVOs.isEmpty()){
				finalscannedFinalVOs.addAll(scannedReassignFourthVOs);
			}else{
				finalscannedFinalVOs.addAll(scannedReassignmailsAfterVOs);
			}
			
        }else{
        	finalscannedFinalVOs.addAll(scannedReassignmailsAfterVOs);
        }
		
		scannedDetailsVO.setReassignDespatch (finalscannedFinalVOs);
		
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		uploadMailForm.setDisableStat(SAVED);
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		  
	
	}
	
	
	private void setULDtype(Collection<ScannedMailDetailsVO> scannedReassignVOs){
		for(ScannedMailDetailsVO intialscannedMailDetailsVO:scannedReassignVOs){
			Collection<DespatchDetailsVO> despatchVOs = intialscannedMailDetailsVO.getDespatchDetails();
			for(DespatchDetailsVO despatchDetailsVO:despatchVOs){
				boolean  isULDType = false;
				if(despatchDetailsVO.getContainerNumber().length()>= 3) {
					String containerPart = despatchDetailsVO.getContainerNumber().substring(0,3);
					log.log(Log.FINE, "$$$$$$containerPart------->>",
							containerPart);
					Collection<String> containerParts = new ArrayList<String>();
					containerParts.add(containerPart);
					try {
						new ULDDelegate().validateULDTypeCodes(
								intialscannedMailDetailsVO.getCompanyCode(),containerParts);
						isULDType = true;
					}catch (BusinessDelegateException businessDelegateException) {
						isULDType = false;
					}
				}else{
					despatchDetailsVO.setContainerType("B");
				}
				log.log(Log.FINE, "isULDType------->>", isULDType);
				if(isULDType){	    	
					try {
						new ULDDelegate().validateULD(intialscannedMailDetailsVO.getCompanyCode(),despatchDetailsVO.getContainerNumber());
						log.log(Log.FINE, "isULDType------->>", isULDType);
						despatchDetailsVO.setContainerType("U");
					}catch (BusinessDelegateException businessDelegateException) {
						despatchDetailsVO.setContainerType("B");
					}
				}	
				
			}
		}
	}
}

	
	
	


