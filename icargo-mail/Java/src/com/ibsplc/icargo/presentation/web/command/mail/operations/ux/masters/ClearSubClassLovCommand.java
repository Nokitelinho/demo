package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailSubClassUxLovForm;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters.ClearSubClassLovCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	05-Jul-2018	:	Draft
 */
public class ClearSubClassLovCommand extends BaseCommand{
	private static final String SUCCESS="clearsubclasLov_Success";
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
		MailSubClassUxLovForm subClasLovForm = 
				(MailSubClassUxLovForm)invocationContext.screenModel;
		subClasLovForm.setCode("");
		subClasLovForm.setDescription("");
		subClasLovForm.setDisplayPage("1");
		subClasLovForm.setSelectedValues("");
		if(subClasLovForm.getSubClassLovPage()!=null){
			subClasLovForm.setSubClassLovPage(null);
		}
		invocationContext.target = SUCCESS;
	}
}