package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author A-1952
 *
 */
public class ScreenLoadAllocateCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String CLOSE_STATUS  = "back_to_allocate_stock";
	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadAllocateCommand", "execute");
		MonitorStockForm frm = (MonitorStockForm) invocationContext.screenModel;
		AllocateStockSession allocateSession=
						(AllocateStockSession)getScreenSession( "stockcontrol.defaults",
									"stockcontrol.defaults.allocatestock");
		 //session.removeAllAttributes();

		Page<StockRequestVO> pageStockRequestVO=allocateSession.getPageStockRequestVO();
		log.log(Log.FINE, "<-------------------------->", frm.getReferenceNo());
		for(StockRequestVO stockRequestVO:pageStockRequestVO){
			if(stockRequestVO!=null){
				if(stockRequestVO.getRequestRefNumber().equals(frm.getReferenceNo())){
					log.log(Log.FINE,
							"<------------Reference Number-------------->",
							stockRequestVO.getRequestRefNumber());
					frm.setDocType(stockRequestVO.getDocumentType());
					frm.setSubType(stockRequestVO.getDocumentSubType());
					frm.setStockHolderType(stockRequestVO.getStockHolderType());
					frm.setManual(stockRequestVO.isManual());
					frm.setStockHolderCode(stockRequestVO.getStockHolderCode());
					break;

				}
			}
		}
		
		MonitorStockSession session =
			 getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
		 session.removeAllAttributes();
		 Map<String, Collection<OneTimeVO>> map =
                         handleScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
        if(map != null){

			session.setOneTimeRequestedBy(map.get("stockcontrol.defaults.stockholdertypes"));

        }
		HashMap<String,Collection<String>> documentList = null;
		try{
		 documentList =new HashMap<String,Collection<String>>(new 
				 DocumentTypeDelegate().findAllDocuments(getApplicationSession().getLogonVO().getCompanyCode()));
		documentList.put("",null);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}	
		 session.setDynamicDocType(documentList);
		 session.setCloseStatus(CLOSE_STATUS);
	
		invocationContext.target = "screenload_success";
		log.exiting("ScreenLoadAllocateCommand", "execute");
	}
	
	 /**
	 * This method will be invoked at the time of screen load
	 *
	 * @param companyCode
	 * @return oneTimes
	 */
	public Map<String, Collection<OneTimeVO>> handleScreenLoadDetails(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add("stockcontrol.defaults.stockholdertypes");

			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldTypes);


		Collection<OneTimeVO> stockHolder = oneTimes.get("stockcontrol.defaults.stockholdertypes");
		MonitorStockSession session =
						getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
		Collection<StockHolderPriorityVO> stockHolderpriorityVos =
								 new StockControlDefaultsDelegate().findStockHolderTypes(companyCode);
		populatePriorityStockHolders(stockHolderpriorityVos,stockHolder,session);
		log
				.log(
						Log.FINE,
						"\n\n.....................stockHolderpriorityVos...............> ",
						stockHolderpriorityVos);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "BusinessDelegateException caught from findStockHolderTypes");
		}
		return oneTimes;
	}
	/**
	 * This method will be invoked at the time of screen load
	 *
	 * @param stockHolderpriorityVos
	 * @param stockHolder
	 * @param session
	 * @return
	 */
	private void populatePriorityStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos,
							Collection<OneTimeVO> stockHolder,MonitorStockSession session){
		if(stockHolderpriorityVos!=null){
			for(StockHolderPriorityVO priorityVO : stockHolderpriorityVos){
				for(OneTimeVO onetime : stockHolder){
					if(onetime.getFieldValue().equals(priorityVO.getStockHolderType())){
						priorityVO.setStockHolderDescription(onetime.getFieldDescription());
					}
				}
			}
				session.setPrioritizedStockHolders(stockHolderpriorityVos);

			}
}

}
