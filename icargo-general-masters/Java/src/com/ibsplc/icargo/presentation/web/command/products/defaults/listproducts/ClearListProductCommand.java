package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
/**
 * ClearListProductCommand is for clearing the list product screen
 * @author A-1870
 *
 */
public class ClearListProductCommand extends BaseCommand{
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		listProductForm.setProductName("");
		listProductForm.setFromDate("");
		listProductForm.setProductScc("");
		listProductForm.setToDate("");
		listProductForm.setStatus("ALL");
		listProductForm.setTransportMode("ALL");
		listProductForm.setPriority("ALL");
		listProductForm.setRateDefined(false);
		listProductForm.setProductCategory(null);//Added for ICRD-166985 by A-5117
		session.setPageProductVO(null);
		invocationContext.target ="clear_success";
	}
	

}
