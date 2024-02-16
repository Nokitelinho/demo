/*
 * MarkIntactCommand.java Created on Jun 30 2016
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
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
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
public class MarkIntactCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "mail.operations";	    
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	private static final String TARGET = "success";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("MarkIntactCommand","execute");

		MailArrivalForm mailArrivalForm =(MailArrivalForm)invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		//ApplicationSessionImpl applicationSession = getApplicationSession();
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<MailbagVO> mailbagVOs=new ArrayList<MailbagVO>();
		Collection<DespatchDetailsVO> despatchDetailsVOs=new ArrayList<DespatchDetailsVO>();
		Collection<String> doe=new ArrayList<String>();	
		ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

		mailbagVOs = containerDetailsVO.getMailDetails();
		despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();

//		/**RULE-1
//		 * @author A-3227
//		 * Validating Whether the The Container Is ACCEPTED Or NOT,By Checking the number of Manifested mailBags
//		 * and INTACT is marked against ACCEPTED Container
//		 */
//		if(!(containerDetailsVO.getTotalBags()>0 && containerDetailsVO.getTotalWeight()>0)){
//			errors.add(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.intactonlyforaccepted"));
//		}
		
		/**@author A-3227 Reno K Abraham
		 * To Check Whether a container is already marked as intact
		 */
		if(MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getIntact())){
			errors.add(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.intactalreadymarkedforuld"));
			invocationContext.addAllError(errors);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
		}

		/**RULE-2
		 * @author A-3227 Reno K Abraham
		 * Validating Whether the The Container Is ULD Or BULK
		 * and INTACT is marked against ULD
		 */
		if(!MailConstantsVO.ULD_TYPE.equals(containerDetailsVO.getContainerType())){
			errors.add(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.intactonlyforuld"));
			invocationContext.addAllError(errors);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
		}
		
		/**
		 * @author A-3227 Reno K Abraham
		 * This validates whether any mailbags or despatches present in container.
		 */
		if(mailbagVOs==null && despatchDetailsVOs==null){
			errors.add(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.intactmarkingonemptycontainer"));
			invocationContext.addAllError(errors);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
		}
		
		/**RULE-3
		 * A container should be marked INTACT only when it is a "ULD",
		 * and all the mailbags/despatches inside it, should be delivered to "SINGLE PA" 
		 * @author A-3227 Reno K Abraham
		 */
		boolean similar=true;
		
		//this collects the distinct DOE's from Mailbags
		if(mailbagVOs!=null && mailbagVOs.size()>0){
			for(MailbagVO mailbagVO : mailbagVOs){
				if(!doe.contains(mailbagVO.getDoe())){
					doe.add(mailbagVO.getDoe());
				}
			}
		}
		// this collects the distinct DOE's from Despatches
		if(despatchDetailsVOs!=null && despatchDetailsVOs.size()>0){
			for(DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs){
				if(!doe.contains(despatchDetailsVO.getDestinationOfficeOfExchange())){
					doe.add(despatchDetailsVO.getDestinationOfficeOfExchange());
				}
			}
		}
		
		try {
			//checking whether the PA for the DOE's send is same or not
			if(doe !=null && doe.size()>1){
				String companyCode=containerDetailsVO.getCompanyCode();
				similar = mailTrackingDefaultsDelegate
						.validatePACodeForMailBags(companyCode,doe);
			}
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if(similar){
			/*
			 * This Condition Says, all the mailbags inside this container 
			 * are bound to delivered to a same PA
			 */
			containerDetailsVO.setIntact(MailConstantsVO.FLAG_YES);
		}else{
			errors.add(new ErrorVO("mailtracking.defaults.mailarrival.msg.err.mailfordiffpa"));
		}
		
		if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		invocationContext.target = TARGET;
    		return;
    	  }
		
		
		mailArrivalSession.setContainerDetailsVO(containerDetailsVO);
    	invocationContext.target = TARGET;
       	log.exiting("BulkContainerScreenLoadCommand","execute");
	}

}
