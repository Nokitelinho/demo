package com.ibsplc.icargo.presentation.web.command.products.defaults.listsubproducts;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm;
/**
 * ClearSubProductCommand is for clearing the list
 * @author A-1870
 *
 */
public class ClearSubProductCommand extends BaseCommand{
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
		session.setPageSubProductVO(null);
		listSubProductForm.setStatus("ALL");
		listSubProductForm.setTransportMode("ALL");
		listSubProductForm.setPriority("ALL");
		listSubProductForm.setProductScc("");
		 invocationContext.target = "clear_success"; 
	}

}
