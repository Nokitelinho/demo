/*
 * CloseCommand.java Created on Jul 09, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;

/**
 * @author A-3817
 *
 */
public class CloseCommand extends BaseCommand{

	private static final String SCREEN_ID_ARRIVAL = "mailtracking.defaults.mailarrival";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID_MANIFEST = "mailtracking.defaults.mailmanifest";
	private static final String CLOSE_HOME = "close_home";
	private static final String CLOSE_ARRIVAL_LIST = "close_arrival_list";
	private static final String CLOSE_ARRIVAL_SCREENLOAD = "close_arrival_screenload";
	private static final String CLOSE_MANIFEST_LIST = "close_manifest_list";
	private static final String CLOSE_MANIFEST_SCREENLOAD = "close_manifest__screenload";
	private static final String SCREENID = "mailtracking.defaults.searchflight";
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		SearchFlightForm form=(SearchFlightForm)invocationContext.screenModel;
		/*MailArrivalSession mailArrivalSession=getScreenSession(MODULE_NAME, SCREEN_ID_ARRIVAL);
		MailManifestSession  mailManifestSession=getScreenSession(MODULE_NAME, SCREEN_ID_MANIFEST);*/
		SearchFlightSession session = (SearchFlightSession) getScreenSession(
				MODULE_NAME, SCREENID);
		/**
		 * for navigating to the home screen
		 */
		if("ON".equals(form.getFromParentScreen())){
			removeSession(session);
			invocationContext.target=CLOSE_HOME;
			return;
		}
		/**
		 * for navigating to mail Arrival Screen
		 */
		if(MailConstantsVO.OPERATION_INBOUND.equals(form.getFromScreen())){
			if("LIST".equals(session.getScreenFlag())){
				removeSession(session);
				invocationContext.target=CLOSE_ARRIVAL_LIST;
			}
			else{
				removeSession(session);
				invocationContext.target=CLOSE_ARRIVAL_SCREENLOAD;
			}
		}
		if(MailConstantsVO.OPERATION_OUTBOUND.equals(form.getFromScreen())){
			if("LIST".equals(session.getScreenFlag())){
				removeSession(session);
				invocationContext.target=CLOSE_MANIFEST_LIST;
			}
			else{
				removeSession(session);
				invocationContext.target=CLOSE_MANIFEST_SCREENLOAD;
			}
		}
		
		
	}
	
	private void removeSession(SearchFlightSession session){
		session.removeAllAttributes();
	}
}
