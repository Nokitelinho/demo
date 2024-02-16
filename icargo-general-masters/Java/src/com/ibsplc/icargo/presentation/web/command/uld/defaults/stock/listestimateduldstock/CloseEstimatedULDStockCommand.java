package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listestimateduldstock;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.ListEstimatedULDStockSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.ListEstimatedULDStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class CloseEstimatedULDStockCommand extends BaseCommand{

	private static final String CLOSE_SUCCESS ="close_success";
	private static final String SCREEN_ID = 
				"uld.defaults.stock.listestimateduldstock";
	private static final String MODULE_NAME = "uld.defaults";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
       ListEstimatedULDStockForm listEstimatedULDStockForm = 
			(ListEstimatedULDStockForm)invocationContext.screenModel;
        ListEstimatedULDStockSessionImpl listEstimatedULDStockSessionImpl = 
		(ListEstimatedULDStockSessionImpl)getScreenSession(MODULE_NAME,SCREEN_ID);
		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("CloseEstimatedUldStockCommand","execute");
		listEstimatedULDStockForm.setAirport("");
		listEstimatedULDStockForm.setUldType("");
		listEstimatedULDStockForm.setSelectAll(false);
		listEstimatedULDStockSessionImpl.setEstimatedULDStockVOs(null);
		listEstimatedULDStockSessionImpl.removeAllAttributes();
		invocationContext.target = CLOSE_SUCCESS;
		log.exiting("CloseEstimatedUldStockCommand","execute");
		
		
		
	}

}
