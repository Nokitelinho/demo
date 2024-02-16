/*
 * RefreshScreenCommand.java.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;


import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm;
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

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";

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
		
		ListBillingMatrixForm listBillingMatrixForm=(ListBillingMatrixForm)invocationContext.screenModel;
		ListBillingMatrixSession session=getScreenSession(MODULE_NAME,SCREENID);
		listBillingMatrixForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		if(invocationContext.getErrors() != null){
			log.log(Log.INFO, "Errors are->>>", invocationContext.getErrors());
			invocationContext.addAllError(invocationContext.getErrors());
		}
		Collection<BillingMatrixVO> vos = session.getBillingMatrixVOs();
		if(vos!= null ) {
			for(BillingMatrixVO  vo : vos){
				log.log(Log.INFO, "VO is---->", vo);
			}
		}
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
		
	}
	

}
