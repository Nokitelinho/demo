package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import com.ibsplc.icargo.business.mail.operations.vo.MailBoxIdLovVO;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailboxIdLovForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author a-5931
 *
 */
public class ListMailBoxIdLovCommand extends BaseCommand{
private static final String SUCCESS="findMBILov_Success";
	
private Log log = LogFactory.getLogger("FindMBILovCommand");


	/**
	* Method to execute the command
	* @param invocationContext
	* @exception  CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n FindMBILovCommand----------> \n\n");
	
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
	
		MailboxIdLovForm mailboxIdLovForm= 
					(MailboxIdLovForm)invocationContext.screenModel;
		try {
			
			MailTrackingDefaultsDelegate delegate
										= new MailTrackingDefaultsDelegate();
			int displayPage=Integer.parseInt(mailboxIdLovForm.getDisplayPage());
			int defaultSize=Integer.parseInt(mailboxIdLovForm.getDefaultPageSize());
			
			String mailboxCode = mailboxIdLovForm.getCode().toUpperCase();
			String mailboxDesc = mailboxIdLovForm.getDescription();
	
			if(!(MailBoxIdLovVO.FLAG_YES.equals(mailboxIdLovForm.getMultiselect()))){
				mailboxIdLovForm.setSelectedValues("");
			}
	
			Page<MailBoxIdLovVO> page=delegate.findMailBoxIdLov(
										companyCode,mailboxCode,mailboxDesc,displayPage,defaultSize);
			mailboxIdLovForm.setMailboxidLovPage(page);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SUCCESS;
	}

}
