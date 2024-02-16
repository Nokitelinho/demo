/*
 * SaveUploadCommand.java Created on Oct 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadofflinemaildetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadOfflineMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6385
 * Added for ICRD-84459
 */
public class SaveUploadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.offlinemailupload";
	/*
	 * INVOCATION TARGETS
	 */
	private static final String UPLOAD_SAVE_SUCCESS = "upload_save_success";
	
	/*
	 * Screen Specific Constants
	 */
	private static final String SAVED = "S";
		
	public void execute(InvocationContext invocationContext) 
	throws CommandInvocationException {	
		
		log.entering("UploadOfflineMailDetails:SaveUploadCommand","execute");
	
		UploadOfflineMailDetailsForm uploadOfflineMailDetailsForm = 
			(UploadOfflineMailDetailsForm)invocationContext.screenModel;
	
		UploadOfflineMailDetailsSession uploadOfflineMailDetailsSession = 
			(UploadOfflineMailDetailsSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		
		String[] selectedMails = uploadOfflineMailDetailsForm.getSelectedMails();
		String scanningPort = uploadOfflineMailDetailsForm.getScanningPort();
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = uploadOfflineMailDetailsSession.getScannedMailDetailsVOs();
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
				
		//get the mail records selected for saving
		if(selectedMails != null && selectedMails.length > 0 && scannedMailDetailsVOs != null){
			
			ArrayList<MailUploadVO> selectedMailListFinal = new ArrayList<MailUploadVO>();
			for(int index=0; index<selectedMails.length; index++){
				String selectedMailKey = selectedMails[index];
				log.log(Log.INFO,"SaveUploadCommand---selectedMailKey--->"+selectedMailKey);
				for(ScannedMailDetailsVO scanvo:scannedMailDetailsVOs){
					if(selectedMailKey != null && selectedMailKey.equalsIgnoreCase(scanvo.getRemarks())){
						scanvo.setStatus(SAVED);
						
						// get full collection of selected mailbags
						ArrayList<MailUploadVO> selectedMailList = (ArrayList)scanvo.getOfflineMailDetails();
						selectedMailListFinal.addAll(selectedMailList);
					}
				}
			}
			// sort the mailbags based on scanned date and time and save 
			log.log(Log.INFO,"SaveUploadCommand---selectedMailListFinal Size--->"+selectedMailListFinal.size());
			log.log(Log.INFO,"selectedMailListFinal BEFORE--->"+selectedMailListFinal);
			Collections.sort(selectedMailListFinal, new ScannedDateComparator());
			log.log(Log.INFO,"selectedMailListFinal AFTER--->"+selectedMailListFinal);
			
			if(selectedMailListFinal != null && selectedMailListFinal.size() > 0){
				log.log(Log.INFO,"SaveUploadCommand---selectedMailList Size--->"+selectedMailListFinal.size());
				for(MailUploadVO mailvo : selectedMailListFinal){
								
							log.log(Log.INFO,"SaveUploadCommand---Saving Mail Tag START--->"+mailvo.getMailTag());
							String mailKey = "";
							try {
				    		  if(mailvo != null && scanningPort != null){
				    			  mailvo.setCompanyCode(logonAttributes.getCompanyCode());  
				    			  mailKey = mailvo.getMailKeyforDisplay();
				    			  ArrayList<MailUploadVO> mailVoToSave = new ArrayList<MailUploadVO>();
				    			  mailVoToSave.add(mailvo);
				    			  scannedMailDetailsVO = mailTrackingDefaultsDelegate.saveMailUploadDetails(mailVoToSave, scanningPort);
				    			  log.log(Log.INFO,"SaveUploadCommand---scannedMailDetailsVO returned--->"+scannedMailDetailsVO);
				    			  if(scannedMailDetailsVO != null){
				    				  String error = scannedMailDetailsVO.getErrorDescription();
				    				  Collection<ErrorVO> containerErrors = scannedMailDetailsVO.getContainerSpecificErrors();
				    				  log.log(Log.INFO,"SaveUploadCommand---ErrorDescription-->"+error);
				    				  log.log(Log.INFO,"SaveUploadCommand---containerErrors-->"+containerErrors);
				    				  if((error != null && error.trim().length() > 0) || 
				    						  (containerErrors != null && containerErrors.size()>0)){				    					  				    					  
				    					  //scanvo.setHasErrors(true); 
				    					  setErrorFlag(mailKey, scannedMailDetailsVOs);
				    				  }
				    			  }
				    		  }
							
							} catch (BusinessDelegateException businessDelegateException) {
								log.log(Log.INFO,"SaveUploadCommand---ERROR OCCURED !!!!--BS->"+businessDelegateException);
								//scanvo.setHasErrors(true);
								setErrorFlag(mailKey, scannedMailDetailsVOs);
							} catch (Exception exception) {
								log.log(Log.INFO,"SaveUploadCommand---ERROR OCCURED !!!!-E-->"+exception);
								//exception.printStackTrace();
								//scanvo.setHasErrors(true);
								setErrorFlag(mailKey, scannedMailDetailsVOs);
							}
							log.log(Log.INFO,"SaveUploadCommand---Saving Mail Tag END--->"+mailvo.getMailTag());
							
							}//for
						}//if
			
			log.log(Log.INFO,"SaveUploadCommand---scannedMailDetailsVOs---->"+scannedMailDetailsVOs);
			uploadOfflineMailDetailsSession.setScannedMailDetailsVOs(scannedMailDetailsVOs);			
			
		}// end of if
		
		invocationContext.target=UPLOAD_SAVE_SUCCESS;
		
		log.exiting("UploadOfflineMailDetails:SaveUploadCommand","execute");
	}
	/**
	 * Method to set error flag for ScannedMailDetailsVO if any exception occurs while saving a mailvo
	 * Error flag helps to show display as red for errors while saving.
	 * @param mailKey
	 * @param scannedMailDetailsVOs
	 */
	private void setErrorFlag(String mailKey, Collection<ScannedMailDetailsVO> scannedMailDetailsVOs){
		if(scannedMailDetailsVOs != null && scannedMailDetailsVOs.size() > 0 && mailKey != null){
			for(ScannedMailDetailsVO scanvo:scannedMailDetailsVOs){
				if(mailKey.equals(scanvo.getRemarks())){
					scanvo.setHasErrors(true);
					break;
				}
			}
		}
	}
	/**
	 * Comparator class to sort the selected mailbags in the order of scanned date & time
	 * @author A-6385
	 *
	 */
	public static class ScannedDateComparator implements Comparator {
		public int compare(Object arg0, Object arg1) {
			MailUploadVO firstmailbag = (MailUploadVO) arg0;
			MailUploadVO secondmailbag = (MailUploadVO) arg1;				
				return firstmailbag.getScannedDate().compareTo(secondmailbag.getScannedDate());
		}
	}// end of ScannedDateComparator
}
