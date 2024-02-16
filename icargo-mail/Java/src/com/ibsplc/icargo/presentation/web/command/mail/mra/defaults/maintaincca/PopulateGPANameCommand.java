/*
 * PopulateGPANameCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class PopulateGPANameCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "--PopulateGPANameCommand--";

	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * target action
	 */
	private static final String CLEAR_SUCCESS = "clear_success";

	/**
	 * for clearing Fields
	 * 
	 */
	private static final String BLANK="";
	/**
	 * DSN POPUP SCREENID
	 */
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String POPULATE_SUCCESS = "populate_success";
	private static final String POPULATE_FAILURE = "populate_failure";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;		
		MailTrackingMRADelegate mailtrackingMRADelegate = new MailTrackingMRADelegate();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		PostalAdministrationVO postalAdministrationVO = null;
		
		if(maintainCCAForm.getRevGpaCode()!= null && maintainCCAForm.getRevGpaCode().trim().length() > 0){
		
			try {
				postalAdministrationVO = mailtrackingMRADelegate.findPostalAdminDetails(logonAttributes.getCompanyCode(), maintainCCAForm.getRevGpaCode().toUpperCase());
				if(postalAdministrationVO==null){
					maintainCCAForm.setRevGpaName("");
					errors = new ArrayList<ErrorVO>();
					ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.msg.err.populategpaname");				
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = POPULATE_FAILURE;
					return;
				}
				
				if(postalAdministrationVO!=null){				
					maintainCCAForm.setRevGpaName(postalAdministrationVO.getPaName());
				}
				
				
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "\n\n\nPA Name Cannot be found!!!!! Check PA Code -->>\n\n\n");
			}
		}else{
			maintainCCAForm.setRevGpaName("");
		}
		
		

		invocationContext.target = POPULATE_SUCCESS;		
		log.exiting(CLASS_NAME,"execute");
		
	}
}
