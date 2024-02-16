/*
 * SaveUploadReturnCommand.java Created on Oct 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class SaveUploadReturnCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
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
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		if(uploadMailForm.getEnablemode()){
			ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
			ScannedDetailsVO returnscanneddetailsVO = CompareScannedDateCommand.checkscannedDetailsVO(scannedDetailsVO,"RET");
			uploadMailSession.setScannedDetailsVO(returnscanneddetailsVO);
		}
		
		
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		Collection<ScannedMailDetailsVO> scannedReturnVOs = scannedDetailsVO.getReturnedMails();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		
		if(scannedReturnVOs != null && scannedReturnVOs.size() > 0){
		
		scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedReturnVOs).get(0);
		Collection<MailbagVO> mailbagReturnVOs = scannedMailDetailsVO.getMailDetails();
		Collection<MailbagVO> mailbagErrorVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagCorrectFirstVOs = new ArrayList<MailbagVO>();
		
				
	   /*
        * Getting OneTime values
        */
       Collection<OneTimeVO> catVOs = uploadMailSession.getOneTimeCat();
       Collection<OneTimeVO> hniVOs = uploadMailSession.getOneTimeHNI();
       Collection<OneTimeVO> riVOs = uploadMailSession.getOneTimeRI();
		
	   /**
		 * Validation 3
		 * validate mailbags - with onetime
		 */
		int onetimeerror = 0;
				for(MailbagVO mailbagVO:mailbagReturnVOs){
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
	   
	   
				Collection<MailbagVO> mailbagCorrectSecondVOs = new ArrayList<MailbagVO>();
				String catErr = "Mail category code is invalid";
				String hniErr = "HNI is invalid";
				String riErr = "RI is invalid";
				String errDesc = "";
		        for(MailbagVO mailbagVO:mailbagReturnVOs){
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
						mailbagCorrectSecondVOs.add(mailbagVO);
					}
				}
				
		
		
		
		int count = 0;
		Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
	
				for(MailbagVO mailbagOuter:mailbagCorrectSecondVOs){
					String error = "";
					for(MailbagVO mailbagInner:mailbagCorrectSecondVOs){
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
				
		
		Collection<MailbagVO> mailbagCorrectThirdVOs = new ArrayList<MailbagVO>();
		     for(MailbagVO mailbagVO:mailbags){
					if(mailbagVO.getErrorType() != null){
						if("DS".equals(mailbagVO.getErrorType())){
							errDesc = "Duplicate Mailbags present";
						}
						mailbagVO.setErrorDescription(errDesc);
						mailbagErrorVOs.add(mailbagVO);
					}else{
						mailbagCorrectThirdVOs.add(mailbagVO);
					}
				}
				
		
		log.log(Log.FINE, "mailbagCorrectThirdVOs ...in command",
				mailbagCorrectThirdVOs);
		log.log(Log.FINE, "mailbagErrorVOs ...in command", mailbagErrorVOs);
		log.log(Log.FINE, "logonAttributes.getOwnAirlineCode() ...in command",
				logonAttributes.getOwnAirlineCode());
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		Collection<OneTimeVO> dmgVOs = new ArrayList<OneTimeVO>();
		   if(oneTimes!=null){
			   dmgVOs = oneTimes.get("mailtracking.defaults.return.reasoncode");
		   }	
		
		if(mailbagCorrectThirdVOs != null && mailbagCorrectThirdVOs.size() > 0){
			//added by anitha
			String[] pacodes=uploadMailForm.getPaCode();
			int index=0;
			for(MailbagVO mailbagVO:mailbagCorrectThirdVOs){
				log.log(Log.FINE, "pacodes[index]", pacodes, index);
				mailbagVO.setPaCode(pacodes[index]);
				Collection<DamagedMailbagVO> damagedVOs = mailbagVO.getDamagedMailbags();
				if(damagedVOs != null && damagedVOs.size() > 0){
					for(DamagedMailbagVO damagedMailbagVO:damagedVOs){
						if(dmgVOs != null && dmgVOs.size() > 0){
						   for(OneTimeVO dmgVO:dmgVOs){
							   if(dmgVO.getFieldValue().equals(damagedMailbagVO.getDamageCode())){
								   damagedMailbagVO.setDamageDescription(dmgVO.getFieldDescription());
							   }
						   }
						}
						damagedMailbagVO.setPaCode(pacodes[index]);						
						damagedMailbagVO.setOperationType(mailbagVO.getOperationalStatus());
						damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
					}
				}
				index++;
			}
	   }
		
		
		
		
		log.log(Log.INFO, "mailbagCorrectThirdVOs....to server.\n",
				mailbagCorrectThirdVOs);
		Collection<ScannedMailDetailsVO> scannedRetAfterVOs = new ArrayList<ScannedMailDetailsVO>();
		if(mailbagCorrectThirdVOs != null && mailbagCorrectThirdVOs.size() > 0){
			try {
				scannedRetAfterVOs = new MailTrackingDefaultsDelegate().returnScannedMailbags(logonAttributes.getAirportCode(), mailbagCorrectThirdVOs);
	        }catch (BusinessDelegateException businessDelegateException) {
	 			errors = handleDelegateException(businessDelegateException);
	 	    }
		}
		
		log.log(Log.INFO, "mailbagErrorVOs....in command.\n", mailbagErrorVOs);
		log.log(Log.INFO, "scannedRetAfterVOs....from server.\n",
				scannedRetAfterVOs);
		Collection<MailbagVO> mailbagsColl = new ArrayList<MailbagVO>();
		if(scannedRetAfterVOs != null && scannedRetAfterVOs.size() > 0){
			for(ScannedMailDetailsVO scannedOuter:scannedRetAfterVOs){
				if(scannedOuter.getMailDetails() != null && scannedOuter.getMailDetails().size() > 0){
					mailbagsColl.addAll(scannedOuter.getMailDetails());
				}
			}
		}
		  
		if(mailbagsColl != null && mailbagsColl.size() > 0){
		      mailbagErrorVOs.addAll(mailbagsColl);
		}
		  
		  scannedMailDetailsVO.setMailDetails(mailbagErrorVOs);
		  Collection<ScannedMailDetailsVO> scannedRetVOs = new ArrayList<ScannedMailDetailsVO>();
		  scannedRetVOs.add(scannedMailDetailsVO);
		  scannedDetailsVO.setReturnedMails(scannedRetVOs);
		  
		}
	    
		
		/**
		 * For Summary Details
		 */
		ScannedDetailsVO summaryVO = uploadMailSession.getScannedSummaryVO();
		Collection<ScannedMailDetailsVO> scannedSummaryVOs = summaryVO.getReturnedMails();
		Collection<ScannedMailDetailsVO> scannedRetVOs = scannedDetailsVO.getReturnedMails();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		if(scannedSummaryVOs != null && scannedSummaryVOs.size() > 0){
			for(ScannedMailDetailsVO scannedSummaryVO:scannedSummaryVOs){
				int unsaved = 0;
				if(scannedRetVOs != null && scannedRetVOs.size() > 0){
					for(ScannedMailDetailsVO scannedMailVO:scannedRetVOs){
						mailbagVOs = new ArrayList<MailbagVO>();
						mailbagVOs = scannedMailVO.getMailDetails();
						if(mailbagVOs != null && mailbagVOs.size() > 0){
							unsaved = mailbagVOs.size();
						}
						scannedSummaryVO.setUnsavedBags(unsaved);
						scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
					}
				}else{
					scannedSummaryVO.setUnsavedBags(unsaved);
					scannedSummaryVO.setSavedBags(scannedSummaryVO.getScannedBags() - scannedSummaryVO.getUnsavedBags());
				}
			}
		}
		
		summaryVO.setReturnedMails(scannedSummaryVOs);
		
		uploadMailSession.setScannedSummaryVO(summaryVO);
		
		log.log(Log.INFO, "scannedDetailsVO....after server.\n",
				scannedDetailsVO);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		uploadMailForm.setDisableStat("SAVED");
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		
	}
	
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.return.reasoncode");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

}
