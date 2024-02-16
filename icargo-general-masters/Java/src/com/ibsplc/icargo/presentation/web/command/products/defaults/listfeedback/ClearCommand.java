package com.ibsplc.icargo.presentation.web.command.products.defaults.listfeedback;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListFeedbackForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class ClearCommand extends BaseCommand{
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
		ListFeedbackForm listFeedbackForm =
			(ListFeedbackForm)invocationContext.screenModel;
		listFeedbackForm.setProductName("");
		listFeedbackForm.setFromDate("");
		listFeedbackForm.setToDate("");
		listFeedbackForm.setProductCode("");
		listFeedbackForm.setPageProductFeedback(null);
		invocationContext.target="clear_success";
	}

}
