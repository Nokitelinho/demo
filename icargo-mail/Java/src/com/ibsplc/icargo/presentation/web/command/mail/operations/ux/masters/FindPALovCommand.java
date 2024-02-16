package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.PostalAdministrationUxLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

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
	
		PostalAdministrationUxLovForm paLovForm= 
					(PostalAdministrationUxLovForm)invocationContext.screenModel;
		try {
			
			MailTrackingDefaultsDelegate delegate
										= new MailTrackingDefaultsDelegate();
			int displayPage=Integer.parseInt(paLovForm.getDisplayPage());
			int defaultSize=Integer.parseInt(paLovForm.getDefaultPageSize());//Added by A-8164
			
			String paCode = paLovForm.getCode().toUpperCase();
			String paName = paLovForm.getDescription();
	
			if(!(("Y").equals(paLovForm.getMultiselect()))){
				paLovForm.setSelectedValues("");
			}
	
			Page<PostalAdministrationVO> page=delegate.findPALov(
										companyCode,paCode,paName,displayPage,defaultSize);
			paLovForm.setPaLovPage(page);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SUCCESS;
	}
}
