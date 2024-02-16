package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class CloseMaintainSubProductsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CloseMaintainSubProductsCommand");

	/**
	 * The Module Name
	 */
	private static final String SCREENID = "products.defaults.maintainsubproducts";

	private static final String MODULE = "product.defaults";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSEMAINTAINSUBPRODUCTS_SUCCESS = "close_maintainsubproducts_success";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("CloseMaintainSubProductsCommand", "CloseCommand");
		MaintainSubProductForm form =
			(MaintainSubProductForm) invocationContext.screenModel;
		MaintainSubProductSessionInterface session = getScreenSession(MODULE, SCREENID);
		log.log(Log.FINE, "form.getFromListSubproduct()", form.getFromListSubproduct());
		if (("listsubproductmode").equals(form.getFromListSubproduct())) {
			form.setFromListSubproduct("");
			invocationContext.target = CLOSEMAINTAINSUBPRODUCTS_SUCCESS;
			return;
		}


	}
}
