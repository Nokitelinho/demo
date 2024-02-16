/*
 * CarditEnquiryOkCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-5991
 *
 */
public class CarditEnquiryOkCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "save_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
	   private static final String SCREEN_ID_AXP = "mailtracking.defaults.mailacceptance";	
	   
		 /**
		 * This method overrides the execute method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("CarditEnquiryOkCommand","execute");
	    	  
	    	SearchConsignmentForm carditEnquiryForm = 
	    		(SearchConsignmentForm)invocationContext.screenModel;
	    	SearchConsignmentSession carditEnquirySession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailAcceptanceSession mailAcceptanceSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID_AXP);
	    	
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			
			ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
			
		
			Collection<MailbagVO> mailbagVOs = carditEnquirySession.getMailbagVOsCollection();
			Collection<MailbagVO> newMailbagVOs = containerDetailsVO.getMailDetails();
			Collection<DespatchDetailsVO> newDespatchVOs = containerDetailsVO.getDesptachDetailsVOs();
			String[] primaryKey = carditEnquiryForm.getSelectMail();
	     	   int cnt=0;
			   int count = 0;
		       int primaryKeyLen = primaryKey.length;
		       double vol = 0.0;
		       
		       if (mailbagVOs != null && mailbagVOs.size() != 0) {
		          	for (MailbagVO mailbagVO : mailbagVOs) {
		          		if ((cnt < primaryKeyLen) &&( String.valueOf(count)).
		          				          equalsIgnoreCase(primaryKey[cnt].trim())) {	          			
							
		          			if(mailbagVO.getReceptacleSerialNumber()!=null && mailbagVO.getReceptacleSerialNumber().length()>0){
		          				
		          				mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			          			mailbagVO.setScannedPort(logonAttributes.getAirportCode());
			          			mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
			          			mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			          			mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			          			mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
			          			mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			          			mailbagVO.setDamageFlag("N");
			          			mailbagVO.setArrivedFlag("N");
			          			mailbagVO.setDeliveredFlag("N");
			          			mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			          			mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			          			mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
			          			mailbagVO.setContainerType(containerDetailsVO.getContainerType());
			          			mailbagVO.setPou(containerDetailsVO.getPou());
			          			mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
			          			mailbagVO.setOperationalFlag("I");	          			
								//mailbagVO.setStrWeight(dateFormat(mailbagVO.getWeight()));
			          				// Previously there was a chance that Mailbagid was coming as null need to test 		          			
			          			//added by A-7371
			          					mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailbagVO.getMailbagId().substring(25,29))/10));//added by A-7550
			          					if(mailbagVO.getStrWeight()!=null){
			          					vol =mailbagVO.getStrWeight().getRoundedSystemValue()/Double.parseDouble(carditEnquiryForm.getDensity());
			          					}
			          					vol = Double.parseDouble(TextFormatter.formatDouble(vol , 4));
			          					//mailbagVO.setVolume(vol);
			          					mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,vol));		//Added by A-7550					

								/*
		    					 * Added By RENO K ABRAHAM : ANZ BUG : 37646
		    					 * As a part of performance Upgrade
		    					 * START.
		    					 */
		           		       	mailbagVO.setDisplayLabel("Y");
		           		       	//END
			          					
			          			if (newMailbagVOs != null && newMailbagVOs.size() != 0) {
									boolean isDuplicate = false;
			        	          	for (MailbagVO alreadyMailbagVO : newMailbagVOs) {
			        	          		if(alreadyMailbagVO.getMailbagId()!=null 
			        	          				&& alreadyMailbagVO.getMailbagId().equals(mailbagVO.getMailbagId())){
			        	          			isDuplicate = true;
			        	          		}
			        	          	}
			        	          	if(!isDuplicate){
			        	          		newMailbagVOs.add(mailbagVO);
			        	          	}
			          			}else{
						    		newMailbagVOs = new ArrayList<MailbagVO>();
						    		newMailbagVOs.add(mailbagVO);
						    	}
		          			}
		          			cnt++;
		          		}
		          		count++;
		          	}
		          }
			
				
			
			containerDetailsVO.setMailDetails(newMailbagVOs);
			containerDetailsVO.setDesptachDetailsVOs(newDespatchVOs);
			mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
			carditEnquiryForm.setLookupClose("CLOSE");
	    	invocationContext.target = TARGET;
	    	log.exiting("CarditEnquiryOkCommand","execute");
	    	
	    }
	   
	
}
