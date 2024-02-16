/*
 * SaveConfigureResditCommand.java Created on July 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ResditConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditTransactionDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConfigureResditSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConfigureResditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2001
 *
 */
public class SaveConfigureResditCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.configureresdit";
   private static final String TARGET_SUCCESS = "save_success";
   private static final String TARGET_FAILURE = "save_failure";
   private static final String ONETIME_MILESTONE = "mailtracking.defaults.resdit.transaction";  
   private static final String NULL_STRING = "";
   private static final String COMMA = ",";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveConfigureResditCommand","execute");    	  
    	ConfigureResditForm configureResditForm = 
    		(ConfigureResditForm)invocationContext.screenModel;
    	ConfigureResditSession configureResditSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	ResditConfigurationVO resditConfigurationVO = 
    		configureResditSession.getResditConfigurationVO();
    	resditConfigurationVO.setCompanyCode(logonAttributes.getCompanyCode());
    	updateResditConfigVO(configureResditForm, resditConfigurationVO, logonAttributes);
    	
    	log.log(Log.FINE, "Going to save resditConfigurationVO :",
				resditConfigurationVO);
		try {
    		new MailTrackingDefaultsDelegate().saveResditConfiguration(
    				resditConfigurationVO);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
    	clearDetails(configureResditForm, configureResditSession);    	 	
    	configureResditForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = TARGET_SUCCESS;
    	log.exiting("SaveConfigureResditCommand","execute");
    	
    }
	private void updateResditConfigVO(ConfigureResditForm configureResditForm, 
			ResditConfigurationVO resditConfigurationVO,
			LogonAttributes logonAttributes) {
		ArrayList<ResditTransactionDetailVO> resditTransactionDetailVOs =
    		(ArrayList<ResditTransactionDetailVO>) resditConfigurationVO.getResditTransactionDetails();
    	String[] assigned = configureResditForm.getAssigned().split(COMMA);   	
    	String[] reserved = configureResditForm.getReserved().split(COMMA); 
    	String[] uplifted = configureResditForm.getUplifted().split(COMMA); 
    	String[] handedover = configureResditForm.getHandedover().split(COMMA);
    	String[] handedoverRecieved = configureResditForm.getHandedoverRecieved().split(COMMA);
    	String[] departed = configureResditForm.getDeparted().split(COMMA);
    	String[] delivered = configureResditForm.getDelivered().split(COMMA);
    	String[] readyForDelivery = configureResditForm.getReadyForDelivery().split(COMMA);
    	String[] transportCompleted = configureResditForm.getTransportCompleted().split(COMMA);
    	String[] mailArrived = configureResditForm.getMailArrived().split(COMMA);
    	String[] returned = configureResditForm.getReturned().split(COMMA);
    	for(int i = 0; i < assigned.length ; i++ ) {
    		ResditTransactionDetailVO resditTransactionDetailVO 
    					= resditTransactionDetailVOs.get(i);
    		resditTransactionDetailVO.setAssignedResditFlag(assigned[i]);
    		resditTransactionDetailVO.setReceivedResditFlag(reserved[i]);
    		resditTransactionDetailVO.setUpliftedResditFlag(uplifted[i]);
    		resditTransactionDetailVO.setHandedOverResditFlag(handedover[i]);
    		resditTransactionDetailVO.setHandedOverReceivedResditFlag(handedoverRecieved[i]);
    		resditTransactionDetailVO.setLoadedResditFlag(departed[i]);
    		resditTransactionDetailVO.setDeliveredResditFlag(delivered[i]);
    		resditTransactionDetailVO.setReturnedResditFlag(returned[i]);
    		resditTransactionDetailVO.setReadyForDeliveryFlag(readyForDelivery[i]);
    		resditTransactionDetailVO.setTransportationCompletedResditFlag(transportCompleted[i]);
    		resditTransactionDetailVO.setArrivedResditFlag(mailArrived[i]);
    		resditTransactionDetailVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
    	}
	}
	private void clearDetails(ConfigureResditForm configureResditForm,
			ConfigureResditSession configureResditSession) {
		configureResditForm.setAirline(NULL_STRING);
    	HashMap<String, Collection<OneTimeVO>> oneTimeValues = 
    		configureResditSession.getOneTimeVOs();
    	
    	
    	Collection<OneTimeVO> milestoneOntimes = new ArrayList<OneTimeVO>();
    	
    	ArrayList<OneTimeVO> milestoneArrayList = (ArrayList<OneTimeVO>)oneTimeValues.get(ONETIME_MILESTONE);
    	milestoneOntimes.add(milestoneArrayList.get(0));
    	milestoneOntimes.add(milestoneArrayList.get(2));
    	milestoneOntimes.add(milestoneArrayList.get(3));
    	milestoneOntimes.add(milestoneArrayList.get(1));
    	milestoneOntimes.add(milestoneArrayList.get(5));
    	milestoneOntimes.add(milestoneArrayList.get(4));
    	
    	ResditConfigurationVO resditConfigurationVO = new ResditConfigurationVO(); 
    	Collection<ResditTransactionDetailVO> resditTransactionDetailVOs =
    		new ArrayList<ResditTransactionDetailVO>();
    	for(OneTimeVO milestoneOntime : milestoneOntimes) {
    		ResditTransactionDetailVO resditTransactionDetailVO =
    			new ResditTransactionDetailVO();
    		resditTransactionDetailVO.setTransaction(
    				milestoneOntime.getFieldValue());
    		resditTransactionDetailVO.setReceivedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setAssignedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setHandedOverResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setUpliftedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setLoadedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setHandedOverReceivedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setDeliveredResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setReturnedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setReadyForDeliveryFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setTransportationCompletedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVO.setArrivedResditFlag(AbstractVO.FLAG_NO);
    		resditTransactionDetailVOs.add(resditTransactionDetailVO);
    	}
    	resditConfigurationVO.setResditTransactionDetails(resditTransactionDetailVOs);
    	configureResditSession.setResditConfigurationVO(resditConfigurationVO);
		
	}
       
}
