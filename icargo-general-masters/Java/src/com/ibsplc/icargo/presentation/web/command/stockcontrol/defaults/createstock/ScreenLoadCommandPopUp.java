package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.createstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import java.util.Collection;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1952
 *
 */
public class ScreenLoadCommandPopUp extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
/**
 * The execute method in BaseCommand
 * @author A-1952
 * @param invocationContext
 * @throws CommandInvocationException
 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException { 
 log.entering("ScreenLoadCommandPopUp","execute");
 CreateStockForm frm = (CreateStockForm) invocationContext.screenModel;
 CreateStockSession createstocksession = getScreenSession( "stockcontrol.defaults",
 							"stockcontrol.defaults.createstock");
 MonitorStockSession session =
	  		getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
 createstocksession.removeAllAttributes();

 Collection<MonitorStockVO> collmonitorVO = session.getCollectionMonitorStockVO();
 String chk=frm.getStockHolder();
 MonitorStockVO selectedVO = new MonitorStockVO();

 if(collmonitorVO!=null){
		for(MonitorStockVO monitorStockVO:collmonitorVO){
			if(monitorStockVO.getStockHolderCode().equals(chk)){
				selectedVO = monitorStockVO;
				break;

			}
			else{
				Collection<MonitorStockVO> childVO = monitorStockVO.getMonitorStock();
				for(MonitorStockVO stkVO:childVO){
				if(stkVO.getStockHolderCode().equals(chk)){
					selectedVO = stkVO;
					break;

					}
				}
			}
		}
  }

 boolean isManual = false;
 frm.setStockHolder(selectedVO.getStockHolderCode());
 frm.setSubType(selectedVO.getDocumentSubType());
 frm.setDocType(session.getDocumentType());
 log.log(Log.FINE, "\n\n..........create......session.getManual()..........> ",
		session.getManual());
if("Y".equals(session.getManual())){
	log.log(Log.FINE,"\n\n........create.............Manal Flag true...............> ");
	isManual = true; 
 }
 frm.setManual(isManual);
 createstocksession.setMode("popup");
 invocationContext.target = "screenloadpopup_success";

}


}
