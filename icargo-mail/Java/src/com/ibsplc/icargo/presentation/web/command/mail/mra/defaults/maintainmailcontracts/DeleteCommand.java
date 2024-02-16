/*
 * DeleteCommand.java Created on Apr 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailcontracts;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailContractsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class DeleteCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MaintainMAilConttracts ScreenloadCommand");

	private static final String CLASS_NAME = "Delete Command";
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
	MaintainMailContractsSession session=(MaintainMailContractsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
	MaintainMailContractsForm form=(MaintainMailContractsForm)invocationContext.screenModel;
	ArrayList<MailContractDetailsVO> contractDetails=null;
	String[] index=form.getCheck();
	//log.log(log.INFO,"selected values"+index);
	if(index!=null && index.length>0){
		if(session.getMailContractDetails()!=null && session.getMailContractDetails().size()>0){
			contractDetails=new ArrayList<MailContractDetailsVO>(session.getMailContractDetails());
				for(int i=index.length-1;i>-1;i--){
					log.log(Log.INFO, "selected values", index, i);
					if(index[i]!=null && index[i].trim().length()>0){
				if(OPERATION_FLAG_INSERT.equals(contractDetails.get(Integer.parseInt(index[i])).getOperationFlag())){
					contractDetails.remove(Integer.parseInt(index[i]));
				}
				else{
					contractDetails.get(Integer.parseInt(index[i])).setOperationFlag(OPERATION_FLAG_DELETE);
				}
				}
			}
		}
		session.setMailContractDetails(contractDetails);
	}
log.log(Log.INFO, "vos from session", session.getMailContractDetails());
	invocationContext.target = ACTION_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
	 }
}
