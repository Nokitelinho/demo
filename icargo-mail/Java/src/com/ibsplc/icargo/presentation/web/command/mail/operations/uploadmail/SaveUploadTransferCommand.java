/*
 * SaveUploadTransferCommand.java Created on Oct 10, 2008
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
public class SaveUploadTransferCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	private static final String SAVED ="SAVED";
	private static final String TARGET = "success";
	
		
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
			ScannedDetailsVO returnscanneddetailsVO = CompareScannedDateCommand.checkscannedDetailsVO(scannedDetailsVO,"TRA");
			uploadMailSession.setScannedDetailsVO(returnscanneddetailsVO);
		}
		
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		Collection<ScannedMailDetailsVO> scannedTransferVOs = scannedDetailsVO.getTransferMails();
		Collection<ScannedMailDetailsVO> scannedTransferAssignVOs = new ArrayList<ScannedMailDetailsVO>();
		Collection<ScannedMailDetailsVO> scannedTransferFinalVOs = new ArrayList<ScannedMailDetailsVO>();
		
	   Collection<OneTimeVO> catVOs = uploadMailSession.getOneTimeCat();
       Collection<OneTimeVO> hniVOs = uploadMailSession.getOneTimeHNI();
       Collection<OneTimeVO> riVOs = uploadMailSession.getOneTimeRI();
       
       Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		
       /**
		 * Validation 1:-
		 * validate mailbags  - with onetime
		 */
        int onetimeerror = 0;
		if(scannedTransferVOs != null && scannedTransferVOs.size() > 0){
			for(ScannedMailDetailsVO intialscannedMailDetailsVO:scannedTransferVOs){
			Collection<MailbagVO> mailbagReassignmailsVOs = intialscannedMailDetailsVO.getMailDetails();
			for(MailbagVO mailbagVO:mailbagReassignmailsVOs){
				if(!"EXPERR".equalsIgnoreCase(mailbagVO.getErrorType())){
				String error = "";
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
		
		Collection<ScannedMailDetailsVO> scannedTransferSecondVOs = new ArrayList<ScannedMailDetailsVO>();
		String catErr = "Mail category code is invalid";
		String hniErr = "HNI is invalid";
		String riErr = "RI is invalid";
		String errDesc = "";
		
			if(scannedTransferVOs != null && scannedTransferVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedTransferVOs){
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
						scannedTransferFinalVOs.add(scannedVO);
					}
					
					if(mailbagCorrectVOs.size() > 0){
						scannedVO.setMailDetails(mailbagCorrectVOs);
						scannedTransferSecondVOs.add(scannedVO);
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
			if(scannedTransferSecondVOs != null && scannedTransferSecondVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedTransferSecondVOs){
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

			Collection<ScannedMailDetailsVO> scannedTransferThirdVOs = new ArrayList<ScannedMailDetailsVO>();
			if(scannedTransferSecondVOs != null && scannedTransferSecondVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedTransferSecondVOs){
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
						scannedTransferFinalVOs.add(scannedVO);
					}
					
					if(mailbagCorrectVOs.size() > 0){
						scannedVO.setMailDetails(mailbagCorrectVOs);
						scannedTransferThirdVOs.add(scannedVO);
					}
				}
			}
			

			/**
			 * Checking for duplicate mailbags acrosss container
			 */
			count = 0;
			Collection<MailbagVO> newMailbags = new ArrayList<MailbagVO>();
			if(scannedTransferThirdVOs != null && scannedTransferThirdVOs.size() > 0){
				for(ScannedMailDetailsVO scannedOuter:scannedTransferThirdVOs){
					newMailbags = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbagsOuter = scannedOuter.getMailDetails();
					for(MailbagVO mailbagOuter:mailbagsOuter){
						String error = "";
						for(ScannedMailDetailsVO scannedInner:scannedTransferThirdVOs){
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

			Collection<ScannedMailDetailsVO> scannedTransferFourthVOs = new ArrayList<ScannedMailDetailsVO>();
			if(scannedTransferThirdVOs != null && scannedTransferThirdVOs.size() > 0){
				for(ScannedMailDetailsVO scannedVO:scannedTransferThirdVOs){
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
						scannedTransferFinalVOs.add(scannedVO);
					}
					
					if(mailbagCorrectVOs.size() > 0){
						scannedVO.setMailDetails(mailbagCorrectVOs);
						scannedTransferFourthVOs.add(scannedVO);
					}
				}
			}
			

		
		Collection<ScannedMailDetailsVO> scannedTransfermailsAfterVOs = new ArrayList<ScannedMailDetailsVO>();
		if(scannedTransferFourthVOs != null && scannedTransferFourthVOs.size() > 0){
			try {
				scannedTransfermailsAfterVOs = new MailTrackingDefaultsDelegate().transferScannedMailbags(scannedTransferFourthVOs);
	        }catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 	    }
		}
		
		log.log(Log.FINE, "selected mail ===>", scannedTransfermailsAfterVOs);
			log.log(Log.INFO, "scannedOutboundFinalVOs >>> ",
					scannedTransfermailsAfterVOs);
			log.log(Log.INFO, "scannedVOs >>> ", scannedTransferFinalVOs);
			boolean flag = false;
	        Collection<ScannedMailDetailsVO> finalscannedOutboundFinalVOs = new ArrayList<ScannedMailDetailsVO>();
	        if(scannedTransfermailsAfterVOs != null && scannedTransfermailsAfterVOs.size() > 0){
				for(ScannedMailDetailsVO scannedOuter:scannedTransferFinalVOs){
					if(scannedTransferFinalVOs != null && scannedTransferFinalVOs.size() > 0){
						for(ScannedMailDetailsVO scannedInner:scannedTransferFinalVOs){
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
				if(scannedTransferFinalVOs != null && scannedTransferFinalVOs.isEmpty()){
					finalscannedOutboundFinalVOs.addAll(scannedTransfermailsAfterVOs);
				}else{
					finalscannedOutboundFinalVOs.addAll(scannedTransferFinalVOs);
				}
				
	        }else{
	        	finalscannedOutboundFinalVOs.addAll(scannedTransferFinalVOs);
	        }
	        
		scannedDetailsVO.setTransferMails(finalscannedOutboundFinalVOs);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		uploadMailForm.setDisableStat(SAVED);
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		  
	
	}
}

	
	
	


