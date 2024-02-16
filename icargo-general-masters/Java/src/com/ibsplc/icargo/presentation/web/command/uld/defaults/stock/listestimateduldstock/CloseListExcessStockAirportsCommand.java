package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.listestimateduldstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListEstimatedULDStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListExcessStockAirportsSession;



public class CloseListExcessStockAirportsCommand extends BaseCommand{

	private static final String CLOSE_SUCCESS ="close_success";
	private static final String SCREEN_ID = 
				"uld.defaults.stock.findairportswithexcessstock";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String	Excess_Stock_SCREEN_ID="uld.defaults.stock.listestimateduldstock";
	private static final String LIST_STATUS ="noListForm";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
	
		 
		 ListEstimatedULDStockSession listEstimatedULDStockSession = getScreenSession(MODULE_NAME, Excess_Stock_SCREEN_ID);
		 ListExcessStockAirportsSession listExcessStockAirportsSession =   getScreenSession(MODULE_NAME,SCREEN_ID);
		 listExcessStockAirportsSession.removeAllAttributes();
		 listEstimatedULDStockSession.setListStatus(LIST_STATUS);
		 listExcessStockAirportsSession.setIndexMap(null);
		 listExcessStockAirportsSession.setFltIndexMap(null);
		 invocationContext.target=CLOSE_SUCCESS;
	}
	
	

}
