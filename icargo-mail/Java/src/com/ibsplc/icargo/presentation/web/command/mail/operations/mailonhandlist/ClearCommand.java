package com.ibsplc.icargo.presentation.web.command.mail.operations.mailonhandlist;


import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MainOnHandListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailOnHandListForm;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
/*
 * ClearCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearCommand extends BaseCommand{

	
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "clear_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailonhandlist";	
	
	
	
	@Override
	public void execute(InvocationContext invocationcontext)
			throws CommandInvocationException {
		// TODO Auto-generated method stub
		log.entering("ClearSearchContainerCommands","execute");
  	  
		MailOnHandListForm mailOnHandListForm = 
    		(MailOnHandListForm)invocationcontext.screenModel;
		MainOnHandListSession mailOnHandListSession = 	getScreenSession(MODULE_NAME,SCREEN_ID);   	

		Page<MailOnHandDetailsVO> MailOnHandDetailsVOS = null; 
		SearchContainerFilterVO searchContainerFilterVO=new SearchContainerFilterVO();
		
		mailOnHandListSession.setMailOnHandDetailsVO(MailOnHandDetailsVOS);
		searchContainerFilterVO.setDeparturePort("");
		mailOnHandListForm.setAssignedto("");		
		
		mailOnHandListSession.setSearchContainerFilterVO(searchContainerFilterVO);
		mailOnHandListSession.setMailOnHandDetailsVO(MailOnHandDetailsVOS);
		invocationcontext.target = TARGET;
       	
    	log.exiting("ClearSearchContainerCommand","execute");
	}

}
