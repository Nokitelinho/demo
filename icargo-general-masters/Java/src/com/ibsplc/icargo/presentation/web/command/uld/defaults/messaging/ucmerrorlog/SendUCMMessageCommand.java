/*
 * SendUCMMessageCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * SendUCMMessageCommand screen
 * 
 * @author A-1862
 */

public class SendUCMMessageCommand extends BaseCommand {
    
	/**
	 * Logger for UCM Error Log
	 */
	private Log log = LogFactory.getLogger("UCM Error Log");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID =
		"uld.defaults.ucmerrorlog";
	
	
	private static final String SEND_SUCCESS = "send_success";
	
	private static final String MSG_MODULE_NAME = "msgbroker.message";
	private static final String MSG_SCREEN_ID = "msgbroker.message.listmessages";
    

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String  compCode = logonAttributes.getCompanyCode();
		UCMErrorLogSession ucmErrorLogSession = 
			(UCMErrorLogSession)getScreenSession(MODULE,SCREENID);
		UCMErrorLogForm ucmErrorLogForm = 
			(UCMErrorLogForm) invocationContext.screenModel;
		
		String[] checked = ucmErrorLogForm.getSelectedUCMErrorLog();
	   	log.log(Log.FINE, "checked[0]------------>", checked);
		Page<ULDFlightMessageReconcileVO> uldFlightMessageReconcileVOs=ucmErrorLogSession.getULDFlightMessageReconcileVOs();
		ULDFlightMessageReconcileVO uldFlightMessageReconcileVO=uldFlightMessageReconcileVOs.get(Integer.parseInt(checked[0]));
		log.log(Log.FINE, "uldFlightMessageReconcileVO-------------------->",
				uldFlightMessageReconcileVO);
		
		/*Added for ICRD-200282 startssa*/
		Collection<MessageDespatchDetailsVO> detailsVOs = null;
		ListMessageSession listMessageSession = (ListMessageSession) getScreenSession(
				MSG_MODULE_NAME, MSG_SCREEN_ID);
		detailsVOs = listMessageSession.getDespatchDetails();
		if (listMessageSession.getAutoForwardDetails() != null) {
			if (detailsVOs == null) {
				detailsVOs = new ArrayList<MessageDespatchDetailsVO>();
			}
			detailsVOs
					.addAll(populateMessageDespatchDetailsVOs(listMessageSession
							.getAutoForwardDetails()));
		}

		uldFlightMessageReconcileVO.setMsgDsptcDetailsVOs(detailsVOs);
		/*Added for ICRD-200282 ends*/
		log.log(Log.INFO, "filter VO in session", ucmErrorLogSession.getFlightFilterMessageVOSession());
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			new ULDDefaultsDelegate().sendUCM(uldFlightMessageReconcileVO);
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		ucmErrorLogForm.setFlightValidationStatus("Y");
		invocationContext.target = SEND_SUCCESS;
        
    }
 
    private Collection<MessageDespatchDetailsVO> populateMessageDespatchDetailsVOs(Collection<AutoForwardDetailsVO> autoForwardDetailsVOs){
		Collection<MessageDespatchDetailsVO> messageDespatchDetailsVOs = new ArrayList<MessageDespatchDetailsVO>();
		Collection<String> airports;
		MessageDespatchDetailsVO messageDespatchDetailsVO = null;
		if(autoForwardDetailsVOs != null && autoForwardDetailsVOs.size()>0){
			for(AutoForwardDetailsVO autoFwdDetls:autoForwardDetailsVOs){
				airports = new ArrayList<String>();
				messageDespatchDetailsVO = new MessageDespatchDetailsVO();
				messageDespatchDetailsVO.setParty(autoFwdDetls.getParticipantName());
				messageDespatchDetailsVO.setPartyType(autoFwdDetls.getParticipantType());
				messageDespatchDetailsVO.setInterfaceSystem(autoFwdDetls.getInterfaceSystem());
				messageDespatchDetailsVO.setCountry(autoFwdDetls.getCountryCode());
				messageDespatchDetailsVO.setExcludeMsgCfg(autoFwdDetls.isExcludeMsgCfg());
				if(autoFwdDetls.getAirportCode().trim().length()>0){
					airports.add(autoFwdDetls.getAirportCode());
					messageDespatchDetailsVO.setPous(airports);
				}
				messageDespatchDetailsVOs.add(messageDespatchDetailsVO);
			}
		}
		return messageDespatchDetailsVOs;
    }
}
