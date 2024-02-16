package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailboxIdLovForm;
/**
 * @author a-5931
 *
 */
public class ClearMailboxIdLovCommand extends BaseCommand{
	
	

		private static final String SUCCESS="clearMailBoxIdLov_Success";
		
		
		
		/**
		 * Method to execute the command
		 * @param invocationContext
		 * @exception  CommandInvocationException
		 */	
		public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			
			MailboxIdLovForm mbIdLovForm = 
						(MailboxIdLovForm)invocationContext.screenModel;
			mbIdLovForm.setCode("");
			mbIdLovForm.setDescription("");
			mbIdLovForm.setDisplayPage("1");
			mbIdLovForm.setSelectedValues("");
			if(mbIdLovForm.getMailboxidLovPage()!=null){
				mbIdLovForm.setMailboxidLovPage(null);
			}
			invocationContext.target = SUCCESS;
		}



		

	}



