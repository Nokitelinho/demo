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
public class ReloadScreenCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		log.entering("ReloadScreenCommand","execute");
		ListStockHolderForm form = (ListStockHolderForm)invocationContext.screenModel;
		ListStockHolderSession session = 
				getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.liststockholder");
		invocationContext.target = "reloadListStock_success";
		StockHolderFilterVO stockFilterVo= session.getStockHolderFilterDetails();
		if(stockFilterVo!=null){
			form.setStockHolderCode(stockFilterVo.getStockHolderCode());
			form.setStockHolderType(stockFilterVo.getStockHolderType());
			form.setDocType(stockFilterVo.getDocumentType());
			form.setDocSubType(stockFilterVo.getDocumentSubType());
		}
		
		log.exiting("ReloadScreenCommand","execute");
	}
	
	
}
