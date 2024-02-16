/*
 * AddCommand.java Created on Apr 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailcontracts;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailContractsSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class AddCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MaintainMAilConttracts ScreenloadCommand");

	private static final String CLASS_NAME = "Add Command";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailcontracts";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String BLANK="";
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
	log.entering(CLASS_NAME, "execute");
	//MaintainMailContractsForm form=(MaintainMailContractsForm)invocationContext.screenModel;
	MaintainMailContractsSession session=(MaintainMailContractsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
	ArrayList<MailContractDetailsVO> mailDetailsVO=null;
	
	
	if(session.getMailContractDetails()!=null && session.getMailContractDetails().size()>0){
		mailDetailsVO=new ArrayList<MailContractDetailsVO>(session.getMailContractDetails());
	}else{
		mailDetailsVO=new ArrayList<MailContractDetailsVO>();
	}
	
	MailContractDetailsVO vo=new MailContractDetailsVO();
	vo.setOriginCode(BLANK);
	vo.setDestinationCode(BLANK);
	vo.setAcceptanceToDeparture(BLANK);
	vo.setArrivalToDelivery(BLANK);
	vo.setOperationFlag(OPERATION_FLAG_INSERT);
	mailDetailsVO.add(vo);
	session.setMailContractDetails(mailDetailsVO);
	
	invocationContext.target = ACTION_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
	 }
}