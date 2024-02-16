package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;


/**
 *
 * @author A-1865
 *
 */
public class CreateRequestCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CREATE_STOCK_SUCCESS = "createStock_success";
	
	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1865
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("\n\nCreateRequestCommand", "execute");
		MonitorStockForm form = (MonitorStockForm) invocationContext.screenModel;
		MonitorStockSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
		StockFilterVO stockFilterVO = new 	StockFilterVO();
		stockFilterVO = setStockFilterDetails(form);
		session.setStockFilterDetails(stockFilterVO);
		log.exiting("CreateRequestCommand", "execute");
		invocationContext.target = CREATE_STOCK_SUCCESS;
	}
	private StockFilterVO setStockFilterDetails(MonitorStockForm form){
		StockFilterVO stockFilterVO = new 	StockFilterVO();
		stockFilterVO.setStockHolderType(form.getStockHolderType());
		stockFilterVO.setStockHolderCode(form.getStockHolderCode());
		stockFilterVO.setDocumentType(form.getDocType());
		stockFilterVO.setDocumentSubType(form.getSubType());
		return stockFilterVO;
	}
}