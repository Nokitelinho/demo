/*
 * FindSubClassLovCommand.java Created on June 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class FindSubClassLovCommand extends BaseCommand {

	private static final String SUCCESS="findsubclasLov_Success";
	
	private Log log = LogFactory.getLogger("FindSubClassLovCommand");


	/**
	* Method to execute the command
	* @param invocationContext
	* @exception  CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n FindSubClassLovCommand----------> \n\n");
	
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
	
		MailSubClassLovForm subClasLovForm = 
							(MailSubClassLovForm)invocationContext.screenModel;
		try {
			
			MailTrackingDefaultsDelegate delegate
										= new MailTrackingDefaultsDelegate();
			int displayPage=Integer.parseInt(subClasLovForm.getDisplayPage());
			
			String code = subClasLovForm.getCode().toUpperCase();
			String description = subClasLovForm.getDescription();
	
			if(!(("Y").equals(subClasLovForm.getMultiselect()))){
				subClasLovForm.setSelectedValues("");
			}
	
			Page<MailSubClassVO> page=delegate.findMailSubClassCodeLov(
									companyCode,code,description,displayPage,0);
			subClasLovForm.setSubClassLovPage(page);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SUCCESS;
	}

}
