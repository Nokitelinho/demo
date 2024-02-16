package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailSubClassUxLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters.FindSubClassLovCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	05-Jul-2018	:	Draft
 */
public class FindSubClassLovCommand extends BaseCommand {
	
	private static final String SUCCESS="findsubclasLov_Success";
	private Log log = LogFactory.getLogger("FindSubClassLovCommand");
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-8164 on 05-Jul-2018
	 * 	Used for 	:	 ICRD-263285
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n FindSubClassLovCommand----------> \n\n");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		
		MailSubClassUxLovForm subClasLovForm = 
				(MailSubClassUxLovForm)invocationContext.screenModel;
		try {
			
			MailTrackingDefaultsDelegate delegate
										= new MailTrackingDefaultsDelegate();
			int displayPage=Integer.parseInt(subClasLovForm.getDisplayPage());
			int defaultSize=Integer.parseInt(subClasLovForm.getDefaultPageSize());
			
			String code = subClasLovForm.getCode().toUpperCase();
			String description = subClasLovForm.getDescription();
	
			if(!(("Y").equals(subClasLovForm.getMultiselect()))){
				subClasLovForm.setSelectedValues("");
			}
	
			Page<MailSubClassVO> page=delegate.findMailSubClassCodeLov(
									companyCode,code,description,displayPage,defaultSize);
			subClasLovForm.setSubClassLovPage(page);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SUCCESS;
	}

}
