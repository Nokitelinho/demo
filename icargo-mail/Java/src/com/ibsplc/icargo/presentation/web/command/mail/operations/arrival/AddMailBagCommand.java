/*
 * AddMailBagCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AddMailBagCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddMailBagCommand","execute");
    	    	
    	MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
    	Collection<MailbagVO> mailbgVOs =  containerDetailsVO.getMailDetails();
    	if(mailbgVOs != null && mailbgVOs.size() > 0) {
			  for(MailbagVO newMailbagVO:mailbgVOs) {
			    String mailId = new StringBuilder()
			            .append(newMailbagVO.getOoe())
			            .append(newMailbagVO.getDoe())
						.append(newMailbagVO.getMailCategoryCode())
						.append(newMailbagVO.getMailSubclass())
						.append(newMailbagVO.getYear())
						.append(newMailbagVO.getDespatchSerialNumber())
						.append(newMailbagVO.getReceptacleSerialNumber())
						.append(newMailbagVO.getHighestNumberedReceptacle())
						.append(newMailbagVO.getRegisteredOrInsuredIndicator())
						.append(newMailbagVO.getStrWeight()).toString();
			    newMailbagVO.setMailbagId(mailId);
			    newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			  }
			}
  	    containerDetailsVO.setMailDetails(mailbgVOs);
  	  mailArrivalSession.setContainerDetailsVO(containerDetailsVO);
    	
//   	 checking duplicate mailbags across containers
	  	int duplicateAcross = 0;
	  	String container = "";
	  	String mailId = "";
	  	Collection<MailbagVO> firstMailbagVOs =  containerDetailsVO.getMailDetails();
	  	MailArrivalVO mailArrivalVO =  mailArrivalSession.getMailArrivalVO();
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalVO.getContainerDetails();
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
    			for(MailbagVO fstMailbagVO:firstMailbagVOs){
    				for(ContainerDetailsVO popupVO:containerDetailsVOs){
    					if(!fstMailbagVO.getContainerNumber().equals(popupVO.getContainerNumber())){
    						Collection<MailbagVO> secMailVOs = popupVO.getMailDetails();
    						if(secMailVOs != null && secMailVOs.size() > 0){
    							for(MailbagVO secMailbagVO:secMailVOs){
    								if(fstMailbagVO.getMailbagId().equals(secMailbagVO.getMailbagId())){
    									duplicateAcross = 1;
    									container = popupVO.getContainerNumber();
    									mailId = fstMailbagVO.getMailbagId();
    								}
    							}
    						}
    					}
    				}
    			}
			}
		}
    	if(duplicateAcross == 1){
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.duplicatemailbagacross",new Object[]{container,mailId}));
	  		invocationContext.target = TARGET;
	  		log.exiting("AddMailBagsCommand - duplicate mailbags across containers","execute");
	  		return;	
	  	}
    	
    	
    	Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
    	
//	  	Check for same OOE and DOE for mail bags
		int sameOE = 0;
		if(mailbagVOs != null && mailbagVOs.size() > 0){
	  		for(MailbagVO mailbagVO:mailbagVOs){
  				String ooe = mailbagVO.getOoe();	 
		    	String doe = mailbagVO.getDoe();
		    	if(ooe.trim().length() == 6){
			       if(doe.trim().length() == 6){
			    	  if (ooe.equals(doe)) {  
		        		 sameOE = 1;
		        		 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{mailbagVO.getMailbagId()}));
			    	  }
			       }
			    }
	  		}
	  	}
		if(sameOE != 1){
			Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
	    	MailbagVO nwMailVO = null;
	    	
	    	/**
	    	 * To get the last vo values.New row to be defaulted with this VO values.
	    	 */
	    	if(mailbagVOs != null && mailbagVOs.size() > 0){
	    		int size = mailbagVOs.size();
	    		int count = 1;
	    		try{
		    		for(MailbagVO mailbagVO:mailbagVOs){
			  			if(size == count){	
			  				nwMailVO = new MailbagVO();
					    	BeanHelper.copyProperties(nwMailVO,mailbagVO);				    	
			  			}
			  			count++;
			  		}
	    		}catch(SystemException systemException){
		    		systemException.getMessage();
		    	}
		  	}
	    	
	    	MailbagVO newMailbagVO = new MailbagVO();
	    	
	    	newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
	    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
	    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
	    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
	    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
	    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
	    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
	    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
	    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
	    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
	    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
	    	newMailbagVO.setArrivedFlag("Y");
	    	newMailbagVO.setAcceptanceFlag("N");
	    	newMailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());
	    	String year = (new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true)).toDisplayDateOnlyFormat();
	    	newMailbagVO.setYear(Integer.parseInt(year.substring(year.length()-1,year.length())));
	    	newMailbagVO.setPou(containerDetailsVO.getPou());
	    	newMailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	    	newMailbagVO.setOperationalFlag("I");
	    	
	    	if(nwMailVO != null){
		    	newMailbagVO.setOoe(nwMailVO.getOoe());
		        newMailbagVO.setDoe(nwMailVO.getDoe());
				newMailbagVO.setMailCategoryCode(nwMailVO.getMailCategoryCode());
				newMailbagVO.setMailSubclass(nwMailVO.getMailSubclass());
				newMailbagVO.setYear(nwMailVO.getYear());
				newMailbagVO.setDespatchSerialNumber(nwMailVO.getDespatchSerialNumber());
				newMailbagVO.setHighestNumberedReceptacle(nwMailVO.getHighestNumberedReceptacle());
				newMailbagVO.setRegisteredOrInsuredIndicator(nwMailVO.getRegisteredOrInsuredIndicator());
				newMailbagVO.setStrWeight(null);//modified by A-7371
				//newMailbagVO.setStrWeight(nwMailVO.getStrWeight());
				newMailbagVO.setWeight(nwMailVO.getWeight());
	    	}
	    		    	
	    	if(mailbagVOs != null && mailbagVOs.size() > 0){
	    		mailbagVOs.add(newMailbagVO);
	    		newMailbagVOs.addAll(mailbagVOs);
	    	}else{
	    		newMailbagVOs.add(newMailbagVO);
	    	}
	    	containerDetailsVO.setMailDetails(newMailbagVOs);
	    	mailArrivalSession.setContainerDetailsVO(containerDetailsVO);
		}
    	invocationContext.target = TARGET;
       	log.exiting("AddMailBagCommand","execute");
    	
    }

}
