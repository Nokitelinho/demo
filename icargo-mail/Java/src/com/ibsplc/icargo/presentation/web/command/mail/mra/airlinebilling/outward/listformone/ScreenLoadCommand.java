/*
 * ScreenLoadCommand.java Created on June 13, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.listformone;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ListFormOneSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListForm1 ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE = "mra.airlinebilling";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.listform1";
	private static final String VIEW_SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
		/*
		 * Target mappings for succes and failure
		 * 
		 */
	
	private static final String ACTION_SUCCESS = "screenload_success";
		//private static final String ACTION_FAILURE = "screenload_failure";
	private static final String BLANK = "";	
		/**
		 * @param invocationContext
		 * @exception CommandInvocationException
		 */
	public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {

	log.entering(CLASS_NAME, "execute");
	    	
	ListMailFormOneForm listFormOneForm=(ListMailFormOneForm)invocationContext.screenModel;
	  String fromScreen = listFormOneForm.getFromScreen() == null? 
			  BLANK : listFormOneForm.getFromScreen();
	String viewScreen = "viewscreen";

	if(viewScreen.equals(fromScreen)){

	ViewFormOneSession viewFormOneSession = (ViewFormOneSession) getScreenSession(
	MODULE, VIEW_SCREENID);
	populateFormFields(viewFormOneSession, listFormOneForm);

	}
	else{

	ListFormOneSession  listFormOneSession =
	(ListFormOneSession)getScreenSession(MODULE, SCREENID);
	listFormOneSession.removeFormOneVOs();
	}
		listFormOneForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);	
	   invocationContext.target = ACTION_SUCCESS;
	   log.exiting(CLASS_NAME, "execute");
	 }

	    /**
		 * 
		 * @param session
		 * @param form
		 */
		private void populateFormFields(ViewFormOneSession session,ListMailFormOneForm form){
			
			InterlineFilterVO interlineFilterVo = session.getInterlineFilterVO();
			
			if(interlineFilterVo != null){
				form.setClearancePeriod(interlineFilterVo.getClearancePeriod());
			}
		}
	}
