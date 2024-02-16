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
public class CloseCommand extends BaseCommand{
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String RETURN_TO_MAINTAINPRODUCT_SUCCESS = "returnToMaintainProduct_success";
	private static final String RETURN_TO_LISTPRODUCT_SUCCESS = "returnToListProduct_success";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
		if("fromMaintainProduct".equals(session.getButtonStatusFlag())){
			invocationContext.target = RETURN_TO_MAINTAINPRODUCT_SUCCESS; 
		}
		else if("fromListProduct".equals(session.getButtonStatusFlag())){
			invocationContext.target = RETURN_TO_LISTPRODUCT_SUCCESS; 
		}
	}

}
