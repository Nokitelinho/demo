/*
 * TransferOkCommand.java Created on Jun 30 2016
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class TransferOkCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "transferok_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("TransferOkCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
    	OperationalFlightVO operationalFlightVO= mailArrivalSession.getOperationalFlightVO();
    	MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
    	
    	/**
    	 * To validate carrier
    	 */
    	AirlineValidationVO airlineValidationVO = null;
    	String carrier = mailArrivalForm.getCarrier().trim().toUpperCase();        	
    	if (carrier != null && !"".equals(carrier)) {        		
    		try {        			
    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
    					logonAttributes.getCompanyCode(),carrier);

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {            			
    			Object[] obj = {carrier};
    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
    			invocationContext.target = TARGET;
    			return;
    		}
    	}
    	operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
    	String selectedContainer = mailArrivalForm.getSelectToTransfer();
    	log.log(Log.FINE, "selectedContainer", selectedContainer);
		String[] primaryKey = selectedContainer.split(",");
    	Collection<ContainerDetailsVO> selectedContainers = new ArrayList<ContainerDetailsVO>();
    	Collection<ContainerDetailsVO> containerDtlsVOs = mailArrivalVO.getContainerDetails();
    	for (int i=0;i<primaryKey.length;i++){
    		 selectedContainers.add((ContainerDetailsVO) new ArrayList<ContainerDetailsVO>(containerDtlsVOs).get(Integer.parseInt(primaryKey[i])));
    	}
    	
    	Collection<MailbagVO> newMailbagVOs =  new ArrayList<MailbagVO>();
    	Collection<MailbagVO> tempMailbagVOs =  new ArrayList<MailbagVO>();
    	if(selectedContainers != null && selectedContainers.size() > 0) {
		  for(ContainerDetailsVO containerDetailsVO:selectedContainers) {
			  tempMailbagVOs =  new ArrayList<MailbagVO>();
			  tempMailbagVOs = containerDetailsVO.getMailDetails();
			  if(tempMailbagVOs != null && tempMailbagVOs.size() > 0) {
				  newMailbagVOs.addAll(tempMailbagVOs);
			  }
		  }
		}
    	
//    	try {
//			log.log(Log.FINE,"newMailbagVOs ------------> " + newMailbagVOs);
//			new MailTrackingDefaultsDelegate().transferToCarrier(newMailbagVOs, operationalFlightVO);
//		}catch (BusinessDelegateException businessDelegateException) {
//			errors = handleDelegateException(businessDelegateException);
//		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
		}
    	 
    	log.log(Log.FINE, "selected Containers : ", selectedContainers);
		mailArrivalForm.setTransferFlag("CLOSEPOPUP");
    	invocationContext.target = TARGET;
    	
       	log.exiting("TransferOkCommand","execute");
    	
    }
    
}
