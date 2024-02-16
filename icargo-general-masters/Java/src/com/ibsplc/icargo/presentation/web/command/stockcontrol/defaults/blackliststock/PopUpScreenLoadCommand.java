package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.blackliststock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.BlackListStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.BlackListStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import java.util.HashMap;
import java.util.Collection;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1952
 *
 */
public class PopUpScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

log.entering("PopUpScreenLoadCommand","execute");
 BlackListStockForm frm = (BlackListStockForm) invocationContext.screenModel;
 BlackListStockSession blacklistsession = getScreenSession( "stockcontrol.defaults",
 							"stockcontrol.defaults.blackliststock");
 MonitorStockSession session =
	  		getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
 //blacklistsession.removeAllAttributes();

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
HashMap<String,Collection<String>> documentList = null;
try{
	documentList =new HashMap<String,Collection<String>>(new  
			DocumentTypeDelegate().findAllDocuments(getApplicationSession().getLogonVO().getCompanyCode()));
	documentList.put("",null);
}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
}

	blacklistsession.setDynamicDocType(documentList);
	frm.setDocType(selectedVO.getDocumentType());
	frm.setSubType(selectedVO.getDocumentSubType());

	blacklistsession.setMode("popup");
	 log.exiting("PopUpScreenLoadCommand","execute");
	invocationContext.target = "screenload_success";

}

}
