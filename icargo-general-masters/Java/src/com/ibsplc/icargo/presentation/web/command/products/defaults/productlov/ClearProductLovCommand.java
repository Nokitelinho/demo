package com.ibsplc.icargo.presentation.web.command.products.defaults.productlov;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductLovForm;
/**
 * ClearProductLovCommand is for clearing the productlov screen
 * @author a-1870
 *
 */
public class ClearProductLovCommand extends BaseCommand{
	/**
	* @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ProductLovForm productLovForm = (ProductLovForm)invocationContext.screenModel;
		productLovForm.setProductName("");
		productLovForm.setStartDate("");
		productLovForm.setEndDate("");
		productLovForm.setSccCode("");//Added by A-8146 for ICRD-254587
		productLovForm.setPageProductLov(null);
		invocationContext.target="clear_success";
	}
		

}
