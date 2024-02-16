/* CloseCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmulderrorlog;

//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class CloseCommand extends BaseCommand {

	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of ucm error log
	 */
	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private static final String SCREENID_SCMERR = "uld.defaults.scmreconcile";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String SCMRECONCILESUCCESS = "scmreconcile_success";
	
	private static final String FROMSCMRECONCILE = "fromScmReconcile";
	
	private static final String FROMMAINTAINULD = "frommaintainuld";

	private Log log = LogFactory.getLogger("SCMULDERRORLOG");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		log.log(Log.FINE, "Entering CloseCommand");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String compCode = logonAttributes.getCompanyCode();
		SCMULDErrorLogSession session = getScreenSession(MODULE, SCREENID);
		SCMReconcileSession scmReconcileSession = getScreenSession(MODULE,
				SCREENID_SCMERR);
		SCMULDErrorLogForm scmUldReconcileForm = (SCMULDErrorLogForm) invocationContext.screenModel;
		log.log(Log.FINE, "\n\n\nPage URL------------------->", session.getPageUrl());
		//Author a-5125 for the ICRD-22255 26dec2012
		if ((session.getPageUrl() != null
				&& "fromScmReconcile".equals(session.getPageUrl()))||"fromScmReconcile".equals(scmUldReconcileForm.getPageUrl())) {
			session.removeAllAttributes();
			scmUldReconcileForm.setPageUrl("");
			scmReconcileSession.setPageUrl("fromScmUldErrorLog");
			invocationContext.target = SCMRECONCILESUCCESS;
			return;
			//Added by Tarun BUG_1775_08Jan08
		}else if("fromScmReconcile".equals(session.getParentPageUrl()) 
						&& ("fromrecorduld").equals(session.getPageUrl())){
			session.removeAllAttributes();
			scmUldReconcileForm.setPageUrl(""); 
			scmReconcileSession.setPageUrl("fromScmUldErrorLog");
			invocationContext.target = SCMRECONCILESUCCESS;
			return;	
		}else if(FROMSCMRECONCILE.equals(session.getParentPageUrl()) &&
				(FROMMAINTAINULD.equals(session.getPageUrl()))){
			
			session.removeAllAttributes();
			scmUldReconcileForm.setPageUrl("");
			scmReconcileSession.setPageUrl("fromScmUldErrorLog");
			invocationContext.target = SCMRECONCILESUCCESS;
			return;
			
		}
		//Added by Tarun BUG_1775_08Jan08 ends
		session.removeAllAttributes();
		invocationContext.target = CLOSE_SUCCESS;

	}

}
