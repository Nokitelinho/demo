/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockholder;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockHolderForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */
public class FilterDetailScreenCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		log.entering("FilterDetailScreenCommand","execute");
		ListStockHolderForm form = (ListStockHolderForm)invocationContext.screenModel;
		ListStockHolderSession session = 
				getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.liststockholder");
		
		StockHolderFilterVO stockFilterVo= new StockHolderFilterVO();
		stockFilterVo.setStockHolderCode(form.getStockHolderCode());
		stockFilterVo.setStockHolderType(form.getStockHolderType());
		stockFilterVo.setDocumentType(form.getDocType());
		stockFilterVo.setDocumentSubType(form.getDocSubType());
		session.setStockHolderFilterDetails(stockFilterVo);
		log.log(Log.FINE, "stockFilterVo in session", session.getStockHolderFilterDetails());
		log.exiting("FilterDetailScreenCommand","execute");
		invocationContext.target = "filterdetails_success";
	}

}
