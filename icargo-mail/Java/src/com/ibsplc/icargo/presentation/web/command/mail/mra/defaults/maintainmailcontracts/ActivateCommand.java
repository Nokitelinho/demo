/*
 * ActivateCommand.java Created on Apr 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailcontracts;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailContractsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ActivateCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MaintainMAilConttracts ScreenloadCommand");

	private static final String CLASS_NAME = "ActivateCommand";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailcontracts";
	private static final String DETAILS_FAILURE="screenload_failure";
	private static final String CONTRACT_NUM_MANDATORY="mailtracking.mra.defaults.msg.err.contractnummandatory";
	private static final String ACTIVE_STATUS="A";
	private static final String SCREEN_ACTIVE="active";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
	log.entering(CLASS_NAME, "execute");
	MaintainMailContractsForm form=(MaintainMailContractsForm)invocationContext.screenModel;
	MaintainMailContractsSession session=(MaintainMailContractsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
	//String contractRefNumber=form.getContractRefNumber();
	//String companyCode=getApplicationSession().getLogonVO().getCompanyCode();
	Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
	//ErrorVO errorVO=null;
	Collection<MailContractVO> vos=new ArrayList<MailContractVO>();
	//MailContractVO mailContractVO=new MailContractVO();
	form.setAgreementStatus(ACTIVE_STATUS);
	form.setScreenStatus(SCREEN_ACTIVE);
	if(session.getMailContractVO()!=null){
		session.getMailContractVO().setAgreementStatus(ACTIVE_STATUS);
		vos.add(session.getMailContractVO());
	}
	/*if(contractRefNumber!=null && contractRefNumber.trim().length()>0){
		contractRefNumber=form.getContractRefNumber().trim().toUpperCase();
	}else{
		errorVO=new ErrorVO(CONTRACT_NUM_MANDATORY);
		errors.add(errorVO);
	}
	if(errors!=null && errors.size()>0){
		  invocationContext.addAllError(errors);
		  invocationContext.target = DETAILS_FAILURE;
		  return;
	  }*/
	try{
		new MailTrackingMRADelegate().changeMailContractStatus(vos);
	}catch(BusinessDelegateException businessDelegateException){
		errors=handleDelegateException(businessDelegateException);
	}
	if(errors!=null && errors.size()>0){
		  invocationContext.addAllError(errors);
		  invocationContext.target = DETAILS_FAILURE;
		  return;
	  }
	invocationContext.target = ACTION_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
	 }
}
