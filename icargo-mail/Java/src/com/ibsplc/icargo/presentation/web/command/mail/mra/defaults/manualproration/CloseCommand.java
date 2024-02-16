/*
 * CloseCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.manualproration;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ManualProrationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

	/**
	 * @author A-3229
	 *
	 *
	 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.manualproration";

	private static final String CLASS_NAME = "CloseCommand";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSE_TO_VIEW_PRORATION = "close_to_view_proration";
	private static final String SCREENID_VIEW_PRORATION ="mailtracking.mra.defaults.viewproration";


	private static final String VIEWPRORATION ="viewproration";

	private static final String CLOSE_TOPRORATIONEXCEPTION_SUCCESS = "closeprorationexception_success";

	/**
	 * Method  implementing closing of screen
	 * @author a-3229
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");

		ManualProrationSession session = (ManualProrationSession)getScreenSession(MODULE, SCREENID);
		MRAViewProrationSession mraViewProrationSession = getScreenSession(MODULE,SCREENID_VIEW_PRORATION);


		ManualProrationForm manualProrationForm = (ManualProrationForm) invocationContext.screenModel;			
		log.log(Log.INFO, "FromScreen...", manualProrationForm.getFromScreen());
		if(manualProrationForm.getFromScreen()!=null && 
				VIEWPRORATION.equals(manualProrationForm.getFromScreen())){	
			mraViewProrationSession.setProrationFilterVO(session.getProrationFilterVO());		
			log
					.log(
							Log.INFO,
							"manualproration..close...mraViewProrationSession.getProrationFilterVO()",
							mraViewProrationSession.getProrationFilterVO());
			invocationContext.target = CLOSE_TO_VIEW_PRORATION;
		}else if(manualProrationForm.getFromScreen()!=null && 
				"fromlistproexceptions".equals(manualProrationForm.getFromScreen())){
			invocationContext.target = CLOSE_TOPRORATIONEXCEPTION_SUCCESS;
		}else{			
			invocationContext.target = CLOSE_SUCCESS;
		}
		session.removeAllAttributes();
	}


}





