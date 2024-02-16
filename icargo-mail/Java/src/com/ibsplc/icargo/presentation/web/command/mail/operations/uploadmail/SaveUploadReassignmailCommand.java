/*
 * SaveUploadReassignmailCommand.java Created on Sept 30, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2107
 *
 */
public class SaveUploadReassignmailCommand extends BaseCommand {
	
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
				
		log.entering("UploadCommand","execute");
		UploadMailForm uploadMailForm 
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if(uploadMailForm.getEnablemode()){
			ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
			ScannedDetailsVO returnscanneddetailsVO = CompareScannedDateCommand.checkscannedDetailsVO(scannedDetailsVO,"RSGM");
			uploadMailSession.setScannedDetailsVO(returnscanneddetailsVO);
		}
		
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		Collection<ScannedMailDetailsVO> scannedReassignmailVOs = scannedDetailsVO.getReassignMails();
		Collection<ScannedMailDetailsVO> scannedReassignmailAssignVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedReassignmailFinalVOs = new ArrayList<ScannedMailDetailsVO>();
		
	   Collection<OneTimeVO> catVOs = uploadMailSession.getOneTimeCat();
       Collection<OneTimeVO> hniVOs = uploadMailSession.getOneTimeHNI();
       Collection<OneTimeVO> riVOs = uploadMailSession.getOneTimeRI();
       
       Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		
       /**
		 * Validation 1:-
		 * validate mailbags  - with onetime
		 */
        int onetimeerror = 0;
		if(scannedReassignmailVOs != null && scannedReassignmailVOs.size() > 0){
			for(ScannedMailDetailsVO intialscannedMailDetailsVO:scannedReassignmailVOs){
			Collection<MailbagVO> mailbagReassignmailsVOs = intialscannedMailDetailsVO.getMailDetails();
			for(MailbagVO mailbagVO:mailbagReassignmailsVOs){
				String error = "";
				if(!"EXPERR".equalsIgnoreCase(mailbagVO.getErrorType())){
				for(OneTimeVO catVO:catVOs){
					if(mailbagVO.getMailCategoryCode().equals(catVO.getFieldValue())){
						onetimeerror = 1;
					}
				}
				if(onetimeerror != 1){
					error = "C";
				}
				onetimeerror = 0;
				for(OneTimeVO hniVO:hniVOs){
					if(mailbagVO.getHighestNumberedReceptacle().equals(hniVO.getFieldValue())){
						onetimeerror = 1;
					}
				}
				if(onetimeerror != 1){
					error = new StringBuilder(error).append("H").toString();
				}
				onetimeerror = 0;
				for(OneTimeVO riVO:riVOs){
					if(mailbagVO.getRegisteredOrInsuredIndicator().equals(riVO.getFieldValue())){
						onetimeerror = 1;
					}
				}
				if(onetimeerror != 1){
					error = new StringBuilder(error).append("R").toString();
				}
				if("".equals(error)){
					mailbagVO.setErrorType(null);
				}else{
					mailbagVO.setErrorType(error);
				}
				onetimeerror = 0;
			   }
			}
			}
		  }
		
		Collection<ScannedMailDetailsVO> scannedReassignSecondVOs = new ArrayList<ScannedMailDetailsVO>();
		String catErr = "Mail category code is invalid";
		String hniErr = "HNI is invalid";
		String riErr = "RI is invalid";
		String errDesc = "";
		
			if(scannedReassignmailVOs != null && scannedReassignmailVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedReassignmailVOs){
					mailbagVOs = scannedVO.getMailDetails();
					Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbagCorrectVOs = new ArrayList<MailbagVO>();
					for(MailbagVO mailbagVO:mailbagVOs){
						if(mailbagVO.getErrorType() != null){
							if("C".equals(mailbagVO.getErrorType())){
								errDesc = catErr;
							}
							if("CH".equals(mailbagVO.getErrorType())){
								errDesc = new StringBuilder(catErr).append(",").append(hniErr).toString();
							}
							if("CHR".equals(mailbagVO.getErrorType())){
								errDesc = new StringBuilder(catErr).append(",").append(hniErr).append(",").append(riErr).toString();
							}
							if("H".equals(mailbagVO.getErrorType())){
								errDesc = hniErr;
							}
							if("HR".equals(mailbagVO.getErrorType())){
								errDesc = new StringBuilder(hniErr).append(",").append(riErr).toString();
							}
							if("R".equals(mailbagVO.getErrorType())){
								errDesc = riErr;
							}
							if("EXPERR".equals(mailbagVO.getErrorType())){
								errDesc = mailbagVO.getErrorDescription();
							}
							mailbagVO.setErrorDescription(errDesc);
							mailbagErrorVOs.add(mailbagVO);
						}else{
							mailbagCorrectVOs.add(mailbagVO);
						}
					}
					
					if(mailbagErrorVOs.size() > 0){
						scannedVO.setMailDetails(mailbagErrorVOs);
						scannedReassignmailFinalVOs.add(scannedVO);
					}
					
					if(mailbagCorrectVOs.size() > 0){
						scannedVO.setMailDetails(mailbagCorrectVOs);
						scannedReassignSecondVOs.add(scannedVO);
					}
				}
			}
			
			
			/**
			 * Validation 2:-
			 * validate duplicate mailbags in clientend
			 */
		
			/**
			 * Checking for duplicate mailbags in same container
			 */
			int count = 0;
			Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
			if(scannedReassignSecondVOs != null && scannedReassignSecondVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedReassignSecondVOs){
					mailbags = new ArrayList<MailbagVO>();
					mailbagVOs = scannedVO.getMailDetails();
					for(MailbagVO mailbagOuter:mailbagVOs){
						String error = "";
						for(MailbagVO mailbagInner:mailbagVOs){
							if(mailbagInner.getMailbagId().equals(mailbagOuter.getMailbagId())){
								count++;
							}
						}
						if(count == 2){
							error = "DS";
						}
						if("".equals(error)){
							mailbagOuter.setErrorType(null);
						}else{
							mailbagOuter.setErrorType(error);
						}
						mailbags.add(mailbagOuter);
						count = 0;
					}
					scannedVO.setMailDetails(mailbags);
				}
			}

			Collection<ScannedMailDetailsVO> scannedReassignThirdVOs = new ArrayList<ScannedMailDetailsVO>();
			if(scannedReassignSecondVOs != null && scannedReassignSecondVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedReassignSecondVOs){
					Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbagCorrectVOs = new ArrayList<MailbagVO>();
					mailbagVOs = scannedVO.getMailDetails();
					for(MailbagVO mailbagVO:mailbagVOs){
						if(mailbagVO.getErrorType() != null){
							if("DS".equals(mailbagVO.getErrorType())){
								errDesc = "Duplicate Mailbags in Same container";
							}
							mailbagVO.setErrorDescription(errDesc);
							mailbagErrorVOs.add(mailbagVO);
						}else{
							mailbagCorrectVOs.add(mailbagVO);
						}
					}
					if(mailbagErrorVOs.size() > 0){
						scannedVO.setMailDetails(mailbagErrorVOs);
						scannedReassignmailFinalVOs.add(scannedVO);
					}
					
					if(mailbagCorrectVOs.size() > 0){
						scannedVO.setMailDetails(mailbagCorrectVOs);
						scannedReassignThirdVOs.add(scannedVO);
					}
				}
			}
			

			/**
			 * Checking for duplicate mailbags acrosss container
			 */
			count = 0;
			Collection<MailbagVO> newMailbags = new ArrayList<MailbagVO>();
			if(scannedReassignThirdVOs != null && scannedReassignThirdVOs.size() > 0){
				for(ScannedMailDetailsVO scannedOuter:scannedReassignThirdVOs){
					newMailbags = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbagsOuter = scannedOuter.getMailDetails();
					for(MailbagVO mailbagOuter:mailbagsOuter){
						String error = "";
						for(ScannedMailDetailsVO scannedInner:scannedReassignThirdVOs){
							Collection<MailbagVO> mailbagsInner = scannedInner.getMailDetails();
							for(MailbagVO mailbagInner:mailbagsInner){
								if(mailbagInner.getMailbagId().equals(mailbagOuter.getMailbagId())){
									count++;
								}
							}
						}
						if(count == 2){
							error = "DA";
						}
						if("".equals(error)){
							mailbagOuter.setErrorType(null);
						}else{
							mailbagOuter.setErrorType(error);
						}
						newMailbags.add(mailbagOuter);
						count = 0;
					}
					scannedOuter.setMailDetails(newMailbags);
				}
			}

			Collection<ScannedMailDetailsVO> scannedReassignFourthVOs = new ArrayList<ScannedMailDetailsVO>();
			if(scannedReassignThirdVOs != null && scannedReassignThirdVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedReassignThirdVOs){
					Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbagCorrectVOs = new ArrayList<MailbagVO>();
					mailbagVOs = scannedVO.getMailDetails();
					for(MailbagVO mailbagVO:mailbagVOs){
						if(mailbagVO.getErrorType() != null){
							if("DA".equals(mailbagVO.getErrorType())){
								errDesc = "Duplicate Mailbags Across container";
							}
							mailbagVO.setErrorDescription(errDesc);
							mailbagErrorVOs.add(mailbagVO);
						}else{
							mailbagCorrectVOs.add(mailbagVO);
						}
					}
					
					if(mailbagErrorVOs.size() > 0){
						scannedVO.setMailDetails(mailbagErrorVOs);
						scannedReassignmailFinalVOs.add(scannedVO);
					}
					
					if(mailbagCorrectVOs.size() > 0){
						scannedVO.setMailDetails(mailbagCorrectVOs);
						scannedReassignFourthVOs.add(scannedVO);
					}
				}
			}
			

		
		log.log(Log.INFO, "scannedReassignFourthVOs....to server.\n",
				scannedReassignFourthVOs);
		Collection<ScannedMailDetailsVO> scannedReassignmailsAfterVOs = new ArrayList<ScannedMailDetailsVO>();
		if(scannedReassignFourthVOs != null && scannedReassignFourthVOs.size() > 0){
			try {
				scannedReassignmailsAfterVOs = new MailTrackingDefaultsDelegate().reassignScannedMailbags(scannedReassignFourthVOs);
	        }catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 	    }
		}
		
		 /**
		   * merging scanned VOs from server with available scanned error VOs
		   */
		Collection<ScannedMailDetailsVO> finalscannedOutboundFinalVOs = new ArrayList<ScannedMailDetailsVO>();
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
					 finalscannedOutboundFinalVOs.add(scannedOuter);
			     }
				}
			}
			if(scannedReassignmailsAfterVOs != null && scannedReassignmailsAfterVOs.isEmpty()){
				finalscannedOutboundFinalVOs.addAll(scannedReassignFourthVOs);
			}else{
				finalscannedOutboundFinalVOs.addAll(scannedReassignmailsAfterVOs);
			}
			
        }else{
        	finalscannedOutboundFinalVOs.addAll(scannedReassignmailsAfterVOs);
        }
		
		scannedDetailsVO.setReassignMails(finalscannedOutboundFinalVOs);
		log.log(Log.INFO, "scannedDetailsVO....after server.\n",
				scannedDetailsVO);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		uploadMailForm.setDisableStat(SAVED);
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		  
	
	}
}

	
	
	


