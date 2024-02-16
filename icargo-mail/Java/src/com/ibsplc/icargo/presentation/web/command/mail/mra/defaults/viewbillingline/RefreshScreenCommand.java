/*
 * RefreshScreenCommand.java.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 *
 */
public class RefreshScreenCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String SCREEN_SUCCESS = "refresh_success";

	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		
		//ViewBillingLineForm form=(ViewBillingLineForm)invocationContext.screenModel;
		ViewBillingLineSession session=getScreenSession(MODULE_NAME,SCREENID);
		//form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		if(invocationContext.getErrors() != null){
			log.log(Log.INFO, "Errors are->>>", invocationContext.getErrors());
			invocationContext.addAllError(invocationContext.getErrors());
		}
		Page<BillingLineVO> vos = session.getBillingLineDetails();
		if(vos!= null )
		{
			for(BillingLineVO  vo : vos){
				log.log(1,"**********************************");
				log.log(Log.INFO, "VO is---->", vo);
				log.log(1,"**********************************");
			}
		}	
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
		
	}
	

}
