package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.OfficeOfExchangeUxLovForm;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.masters.ClearOELovCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	05-Jul-2018	:	Draft
 */
public class ClearOELovCommand extends BaseCommand{
	private static final String SUCCESS="clearoeLov_Success";
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-8164 on 05-Jul-2018
	 * 	Used for 	:	 ICRD-263285
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		OfficeOfExchangeUxLovForm oeLovForm = 
				(OfficeOfExchangeUxLovForm)invocationContext.screenModel;
		oeLovForm.setCode("");
		oeLovForm.setDescription("");
		oeLovForm.setAirportCode("");
		oeLovForm.setPoaCode("");
		oeLovForm.setDisplayPage("1");
		oeLovForm.setSelectedValues("");
		if(oeLovForm.getOeLovPage()!=null){
			oeLovForm.setOeLovPage(null);
		}
		invocationContext.target = SUCCESS;
	}
}