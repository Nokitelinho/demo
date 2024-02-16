/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1870
 *
 */
public class ReloadAllocateStock extends BaseCommand {
	private Log log = LogFactory.getLogger("STOCKCONTROL DEFAULTS");
	private static final String RELOADSTOCK_SUCCESS = "reload_success";
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {	
		AllocateStockSession session = getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
		AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
		setFormDetails(allocateStockForm,session);
		invocationContext.target = RELOADSTOCK_SUCCESS;
	}

	private void setFormDetails(AllocateStockForm allocateStockForm,AllocateStockSession session){
		StockRequestFilterVO stockRequestFilterVO = session.getFilterDetails();
		if(stockRequestFilterVO != null){
			
			allocateStockForm.setDocType(stockRequestFilterVO.getDocumentType());
			allocateStockForm.setDocSubType(stockRequestFilterVO.getDocumentSubType());
			allocateStockForm.setStockControlFor(stockRequestFilterVO.getApprover());
			allocateStockForm.setStatus(stockRequestFilterVO.getStatus());
			if(stockRequestFilterVO.getFromDate() != null){
				allocateStockForm.setFromDate(TimeConvertor.toStringFormat(stockRequestFilterVO.getFromDate(),TimeConvertor.CALENDAR_DATE_FORMAT));	
			}
			if(stockRequestFilterVO.getToDate() != null){
				allocateStockForm.setToDate(TimeConvertor.toStringFormat(stockRequestFilterVO.getToDate(),TimeConvertor.CALENDAR_DATE_FORMAT));	
			}
			allocateStockForm.setStockHolderType(stockRequestFilterVO.getStockHolderType());
			allocateStockForm.setStockHolderCode(stockRequestFilterVO.getStockHolderCode());
			
			
		}
	}
}
