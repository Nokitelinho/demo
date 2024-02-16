/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockrequest;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

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
		ListStockRequestForm form = (ListStockRequestForm)invocationContext.screenModel;
		ListStockRequestSession session =
    		getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.liststockrequest");
		invocationContext.target = "reloadListStock_success";
		ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributes = applicationSession.getLogonVO();
		StockRequestFilterVO stockFilterVo= session.getStockRequestFilterDetails();
		
	   

	   	
		if(stockFilterVo!=null){
			form.setDocType(stockFilterVo.getDocumentType());
			form.setManual(stockFilterVo.isManual());
			form.setReqRefNo(stockFilterVo.getRequestRefNumber());
			form.setStatus(stockFilterVo.getStatus());
			form.setSubType(stockFilterVo.getDocumentSubType());
			form.setCode(stockFilterVo.getStockHolderCode());
			form.setStockHolderType(stockFilterVo.getStockHolderType());
			if(stockFilterVo.getFromDate()!=null){
				form.setFromDate(TimeConvertor.toStringFormat(stockFilterVo
						.getFromDate().toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT));
				}
			if(stockFilterVo.getToDate()!=null){
				form.setToDate(TimeConvertor.toStringFormat(stockFilterVo
						.getToDate().toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT));
					}

			
			
			                
			
			
		}
		
		log.exiting("ReloadScreenCommand","execute");
	}
	
	
}
