/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockrequest;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm;
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
		ListStockRequestForm form = (ListStockRequestForm)invocationContext.screenModel;
		ListStockRequestSession session =
    		getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.liststockrequest");
		
		StockRequestFilterVO stockFilterVo= new StockRequestFilterVO();
		stockFilterVo.setDocumentType(form.getDocType());
		stockFilterVo.setManual(form.isManual());
		stockFilterVo.setRequestRefNumber(form.getReqRefNo());
		stockFilterVo.setStatus(form.getStatus());
		stockFilterVo.setDocumentSubType(form.getSubType());
		stockFilterVo.setStockHolderCode(form.getCode());
		stockFilterVo.setStockHolderType(form.getStockHolderType());
		
		LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		if(form.getFromDate()!= null &&
				form.getFromDate().trim().length()!=0){

			stockFilterVo.setFromDate(from.setDate(form.getFromDate()));
		}

		if(form.getToDate()!= null &&
				form.getToDate().trim().length()!=0 ){

			stockFilterVo.setToDate(to.setDate(form.getToDate()));
		}
		session.setStockRequestFilterDetails(stockFilterVo);
		log.log(Log.FINE, "stockFilterVo in session", session.getStockRequestFilterDetails());
		log.exiting("FilterDetailScreenCommand","execute");
		invocationContext.target = "filterdetails_success";
	}

}
