/*
 * FindPALovCommand.java Created on June 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class FindPALovCommand extends BaseCommand {

	private static final String SUCCESS="findPALov_Success";
	
	private Log log = LogFactory.getLogger("FindPALovCommand");


	/**
	* Method to execute the command
	* @param invocationContext
	* @exception  CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n FindPALovCommand----------> \n\n");
	
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
	
		PostalAdministrationLovForm paLovForm= 
					(PostalAdministrationLovForm)invocationContext.screenModel;
		try {
			
			MailTrackingDefaultsDelegate delegate
										= new MailTrackingDefaultsDelegate();
			int displayPage=Integer.parseInt(paLovForm.getDisplayPage());
			
			String paCode = paLovForm.getCode().toUpperCase();
			String paName = paLovForm.getDescription();
	
			if(!(("Y").equals(paLovForm.getMultiselect()))){
				paLovForm.setSelectedValues("");
			}
	
			Page<PostalAdministrationVO> page=delegate.findPALov(
										companyCode,paCode,paName,displayPage,0);
			paLovForm.setPaLovPage(page);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SUCCESS;
	}
}
