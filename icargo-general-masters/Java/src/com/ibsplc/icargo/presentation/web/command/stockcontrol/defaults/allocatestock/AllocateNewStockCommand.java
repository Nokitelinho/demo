/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1865
 *
 */
public class AllocateNewStockCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("STOCKCONTROL DEFAULTS");
	//private static final String COMPANY_CODE = "AV";
	private static final String ALLOCATENEWASTOCK_SUCCESS = "allocateNewStock_success";
	/**
	 * The execute method in BaseCommand
	 * @author A-1865
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		AllocateStockSession session = getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
		AllocateStockForm allocateStockForm = (AllocateStockForm)invocationContext.screenModel;
		StockRequestFilterVO stockRequestFilterVO = new StockRequestFilterVO();
		
		session.setButtonStatusFlag(allocateStockForm.getButtonStatusFlag());
		log.log(Log.INFO, "\n\n\nStatus flag:::::::::>>", session.getButtonStatusFlag());
		stockRequestFilterVO = getFormDetails(allocateStockForm);
		session.setFilterDetails(stockRequestFilterVO);
		
		invocationContext.target = ALLOCATENEWASTOCK_SUCCESS;
	}
	
	private StockRequestFilterVO getFormDetails(AllocateStockForm allocateStockForm){
		StockRequestFilterVO stockRequestFilterVO = new StockRequestFilterVO();
		
		stockRequestFilterVO.setDocumentType(allocateStockForm.getDocType());
		stockRequestFilterVO.setDocumentSubType(allocateStockForm.getDocSubType());
		stockRequestFilterVO.setStockControlFor(allocateStockForm.getStockControlFor());
		stockRequestFilterVO.setStatus(allocateStockForm.getStatus());
		stockRequestFilterVO.setStockHolderCode(allocateStockForm.getStockHolderCode());
		stockRequestFilterVO.setStockHolderType(allocateStockForm.getStockHolderType());
		
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);		
		if(allocateStockForm.getFromDate() != null && allocateStockForm.getFromDate().trim().length() != 0){
			stockRequestFilterVO.setFromDate(fromDate.setDate(allocateStockForm.getFromDate()));	
		}
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);	
		if(allocateStockForm.getToDate() != null && allocateStockForm.getToDate().trim().length() != 0){
			stockRequestFilterVO.setToDate(toDate.setDate(allocateStockForm.getToDate()));
		}
		
		
		return stockRequestFilterVO;
		
	}
}
