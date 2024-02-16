/*
 * ViewContainerDtlsCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailonhandlist;

import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MainOnHandListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailOnHandListForm;

import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class ViewContainerDtlsCommand extends BaseCommand{
	private static final String SCREEN_ID_SC = "mailtracking.defaults.searchContainer";	
	
	private static final String SCREEN_ID_MHL = "mailtracking.defaults.mailonhandlist";
	private static final String MODULE_NAME = "mail.operations";
	private static final String ACTION_SUCCESS = "success";
	
	 private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		
		log.entering("ViewContainerDtlsCommand  ","execute"); 
		
	    MailOnHandListForm mailHandlistform = 
    		(MailOnHandListForm)invocationcontext.screenModel;
		
		SearchContainerSession searchContainerSession = 
  		getScreenSession(MODULE_NAME,SCREEN_ID_SC);
    
		MainOnHandListSession mailonlistsession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID_MHL);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		SearchContainerFilterVO searchContainerFilterVO =
			new SearchContainerFilterVO();
		searchContainerFilterVO.setCompanyCode(companyCode);
		String[] selectedRow = mailHandlistform.getSelectmaillist();
    	int count = Integer.parseInt(selectedRow[0]);
		Page<MailOnHandDetailsVO>  MailOnHandDetailsVOs =mailonlistsession.getMailOnHandDetailsVO();
		MailOnHandDetailsVO MailOnHandDetailsvo=MailOnHandDetailsVOs.get(count-1);			
			searchContainerFilterVO.setStrFromDate(mailHandlistform.getFromDate());
			searchContainerFilterVO.setStrToDate(mailHandlistform.getToDate());
			searchContainerFilterVO.setFinalDestination(MailOnHandDetailsvo.getDestination());
			searchContainerFilterVO.setSubclassGroup(MailOnHandDetailsvo.getSubclassGroup());
			searchContainerFilterVO.setDeparturePort(MailOnHandDetailsvo.getCurrentAirport());
			searchContainerFilterVO.setSearchMode(mailHandlistform.getAssignedto());
			searchContainerFilterVO.setNotClosedFlag("Y");
			// Added by A-5945 for ICRD-96261  starts
			searchContainerFilterVO.setMailAcceptedFlag("N");
			searchContainerFilterVO.setShowEmptyContainer("N");
			searchContainerFilterVO.setOperationType("O");
			searchContainerSession.setSearchContainerFilterVO(searchContainerFilterVO);
		
		
		invocationcontext.target = ACTION_SUCCESS;
	}
	
	



}
