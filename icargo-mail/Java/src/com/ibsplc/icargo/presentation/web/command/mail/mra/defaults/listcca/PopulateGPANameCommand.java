/*
 * PopulateGPANameCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
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

	private Log log = LogFactory.getLogger("ListCCA ScreenloadCommand");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "PopulateGPANameCommand";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listcca";

	/**
	 * Target action
	 */
	private static final String POPULATE_SUCCESS = "populate_success";
	private static final String POPULATE_FAILURE = "populate_failure";

	/**
	 * String
	 */
	private static final String BLANK = "";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");		
		ListCCAForm listCCAForm = (ListCCAForm) invocationContext.screenModel;
		ListCCASession session = (ListCCASession) getScreenSession(MODULE_NAME,SCREEN_ID);
		MailTrackingMRADelegate mailtrackingMRADelegate = new MailTrackingMRADelegate();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		PostalAdministrationVO postalAdministrationVO = null;
		
		if(listCCAForm.getGpaCode()!= null && listCCAForm.getGpaCode().trim().length() > 0){
		
			try {
				postalAdministrationVO = mailtrackingMRADelegate.findPostalAdminDetails(logonAttributes.getCompanyCode(), listCCAForm.getGpaCode().toUpperCase());
				if(postalAdministrationVO==null){
					listCCAForm.setGpaName("");
					errors = new ArrayList<ErrorVO>();
					ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.msg.err.populategpaname");				
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = POPULATE_FAILURE;
					return;
				}
				
				if(postalAdministrationVO!=null){				
				listCCAForm.setGpaName(postalAdministrationVO.getPaName());
				}
				
				
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "\n\n\nPA Name Cannot be found!!!!! Check PA Code -->>\n\n\n");
			}
		}else{
			listCCAForm.setGpaName("");
		}
		
		

		invocationContext.target = POPULATE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}