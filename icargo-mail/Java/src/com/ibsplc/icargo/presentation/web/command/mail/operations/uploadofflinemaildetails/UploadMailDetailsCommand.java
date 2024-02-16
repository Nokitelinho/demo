/*
 * UploadMailDetailsCommand.java Created on Oct, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadofflinemaildetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.upload.parser.mail.operations.MailUploadParser;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadOfflineMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6385
 * Added for ICRD-84459
 */
public class UploadMailDetailsCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAIL OPEARTIONS");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.offlinemailupload";

	private static final String UNSAVED = "U";
	private static final String EOL = "\\*#";
	private static final String DATA_DELIMITER = ";";
	private static final String EOL_DELIMITER = "*#";
	private static final String HEAD_DELIM = "\\*";
	
	private static final String TARGET_SUCCESS = "upload_details_success";	

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("UploadOfflineMailDetails:UploadMailDetailsCommand","execute");

		UploadOfflineMailDetailsSession uploadOfflineMailDetailsSession = 
			(UploadOfflineMailDetailsSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		UploadOfflineMailDetailsForm uploadOfflineMailDetailsForm = 
			(UploadOfflineMailDetailsForm) invocationContext.screenModel;
		MailUploadParser mailUploadParser = new MailUploadParser();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = null;

		/**
		 * Retreiving MailBatchVOs from Terminal
		 */
		String mailData = getApplicationSession().getUploadScannedData();
		
		log.log(Log.INFO, "UploadOfflineMailDetails---mailData--->"+mailData);
		
		if(mailData != null && mailData.trim().length() > 0){
		HashMap<String,String> map = getUploadFileData(mailData);
		String headercontent = map.get("HEAD");
		String mailcontent = map.get("MAILBAGDATA");
		
		
		//Set scannedport from logon attributes. This is setting as part of bug ICRD-102549
		uploadOfflineMailDetailsForm.setScanningPort(logonAttributes.getAirportCode());
		// Get scanning port from header
		/*if(headercontent != null && headercontent.trim().length() > 0){
		String[] headdata = headercontent.split(DATA_DELIMITER);
			if(headdata.length >= 5){
			String scanningPort = headdata[5];
			uploadOfflineMailDetailsForm.setScanningPort(scanningPort);
			}
		}*/
		// Creating VOs from string
 	    String[] mailbagdata = mailcontent.split(EOL); 
		  
		  MailUploadVO mailUploadVO = null;
		  Collection<MailUploadVO> mailBatchVOs = new ArrayList<MailUploadVO>();
		  if (mailbagdata != null) {
	        int length = mailbagdata.length;
	        log.log(Log.INFO, "Length:"+length);
	        if (length > 0) {
	        	for (String mail : mailbagdata) {
	        	  mailUploadVO = mailUploadParser.splitData(mail+EOL_DELIMITER);
	        	  if(mailUploadVO != null){
	        	  mailBatchVOs.add(mailUploadVO);
	          }
	        }
	      }
	      }
		log.log(Log.INFO, "UploadOfflineMailDetails---mailBatchVOs size--->"+mailBatchVOs.size());		
	    if (mailBatchVOs.size() > 0) {
	    		
			HashMap<String, Collection<MailUploadVO>> mailMap = mailUploadParser.groupMailBags(mailBatchVOs);
				
			log.log(Log.INFO, "UploadOfflineMailDetails---mailMap--->"+mailMap);
			log.log(Log.INFO, "UploadOfflineMailDetails---calling constructScannedMailCollection--->");
			scannedMailDetailsVOs = mailUploadParser.constructScannedMailCollection(mailMap,UNSAVED);
				
			uploadOfflineMailDetailsSession.setMailMap(mailMap);
			uploadOfflineMailDetailsSession.setScannedMailDetailsVOs(scannedMailDetailsVOs);
					
		}
		}// null check mailData
	    
		invocationContext.target = TARGET_SUCCESS;
		log.exiting("UploadOfflineMailDetails:UploadMailDetailsCommand","execute");
	}
	/**
	 * Method to extract header and mailbag data 
	 * @param uploadData
	 * @return
	 */
	public HashMap<String,String> getUploadFileData(String uploadData){
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		String[] mailbagdata = uploadData.split(HEAD_DELIM);
		String headerData = "";
		String mailData = "";
		  if (mailbagdata != null) {
	        int length = mailbagdata.length;
	        log.log(Log.INFO, "Length:"+length);
	                
	        if (length > 0) { 
	        	if(mailbagdata[0] != null){
	        	headerData = mailbagdata[0];
	        	}
	        	log.log(Log.INFO, "headerData:"+headerData);
	        	mailData = uploadData.substring(uploadData.indexOf("*")+1);
	        	log.log(Log.INFO, "mailData:"+mailData);
	        }
		  }		    
	    map.put("HEAD", headerData);
	    map.put("MAILBAGDATA", mailData);
	    return map;
	} //end of getUploadFileData

}
