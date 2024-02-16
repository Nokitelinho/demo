package com.ibsplc.icargo.presentation.web.command.products.defaults.sendemail;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SendEmailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class ScreenloadCommand extends BaseCommand{
	
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		SendEmailForm sendEmailForm = (SendEmailForm)invocationContext.screenModel;
		invocationContext.target = "screenload_success";
	}
	
}
