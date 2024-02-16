/*
 * AcquitUldCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class AcquitUldCommand extends BaseCommand{
	
	  private Log log = LogFactory.getLogger("MAILOPERATIONS");
	  
	  private static final String TARGET = "acquit_success";
	  
	  private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	   private static final String SAVE_SUCCESS = 
			"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
	  /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException { 
	    	log.entering("ArriveMailCommand","execute");
	    	
	    	MailArrivalForm mailArrivalForm = 
	    		(MailArrivalForm)invocationContext.screenModel;
	    	MailArrivalSession mailArrivalSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    
			
		    
			MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
			OperationalFlightVO operationalFlightVO=mailArrivalSession.getOperationalFlightVO();
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			ArrayList<ContainerDetailsVO> containerDetailsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
			ArrayList<ContainerDetailsVO> containerDetailsVOsforAcquital=new ArrayList<ContainerDetailsVO>();
			String[] selectedContainers = mailArrivalForm.getSelectContainer();
			
			for(String selectedIndex:selectedContainers){
				containerDetailsVOsforAcquital.add(containerDetailsVOs.get(Integer.parseInt(selectedIndex)));
			}
			for(ContainerDetailsVO continerVO:containerDetailsVOsforAcquital){
				continerVO.setCarrierCode(operationalFlightVO.getCarrierCode());
				continerVO.setFlightDate(operationalFlightVO.getFlightDate());
			}
			if(containerDetailsVOsforAcquital.size()!=0){
				try {
					new MailTrackingDefaultsDelegate().autoAcquitContainers(containerDetailsVOsforAcquital);
				} catch (BusinessDelegateException e) {
				
					e.getMessage();
				}
			}
			//Added as part of bug ICRD-101744 by A-5526 starts 
			MailArrivalVO mailArriveVO = new MailArrivalVO(); 
			//Added for ICRD-134007 starts
			LocalDate arrivalDate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
	    	arrivalDate.setDate(mailArrivalForm.getArrivalDate());
	    	mailArriveVO.setFlightCarrierCode(mailArrivalForm.getFlightCarrierCode());
	    	mailArriveVO.setFlightNumber(mailArrivalForm.getFlightNumber());
	    	mailArriveVO.setArrivalDate(arrivalDate);
	    	//Added for ICRD-134007 ends
	    	mailArrivalSession.setMailArrivalVO(mailArriveVO);
	    	mailArrivalForm.setSaveSuccessFlag(FLAG_YES);//Added for ICRD-134007
	    	//mailArrivalForm.setListFlag("FAILURE");//Commented for ICRD-134007
	    	mailArrivalForm.setOperationalStatus("");
	    	FlightValidationVO flightValidationVO = new FlightValidationVO();
	    	mailArrivalSession.setFlightValidationVO(flightValidationVO);
	    	mailArrivalForm.setInitialFocus(FLAG_YES);      
	    	mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	mailArrivalForm.setArrivalPort(logonAttributes.getAirportCode());
	    	//invocationContext.addError(new ErrorVO(SAVE_SUCCESS));//Commented for ICRD-134007
	    	//Added as part of bug ICRD-101744 by A-5526 ends 
	    	invocationContext.target=TARGET;
	    	
	    }
	    	
	    	

}
