package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import java.util.Collection;


/**
 *
 * @author A-1952
 *
 */
public class AllocateCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String ALLOCATE ="Y";
	
	private static final String ALLOCATENEW ="N";

	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("AllocateCommand", "execute");
		
		MonitorStockForm formMonitor=(MonitorStockForm)invocationContext.screenModel;
		String chk = formMonitor.getCode();
		log.log(Log.FINE, "/n/n<--------chk------------------->", chk);
		MonitorStockSession session = getScreenSession("stockcontrol.defaults",
		"stockcontrol.defaults.monitorstock");
		Collection<MonitorStockVO> collmonitorVO = session
			.getCollectionMonitorStockVO();
		
		StockFilterVO stockFilterVO = new 	StockFilterVO();
		stockFilterVO = setStockFilterDetails(formMonitor);
		session.setStockFilterDetails(stockFilterVO);
		
		MonitorStockVO selectedVO = new MonitorStockVO();
		if (collmonitorVO != null) {
			for (MonitorStockVO monitorStockVO : collmonitorVO) {
				if (monitorStockVO.getStockHolderCode().equals(chk)) {
					selectedVO = monitorStockVO;
					break;
		
				} else {
					Collection<MonitorStockVO> childVO = monitorStockVO
							.getMonitorStock();
		
					for (MonitorStockVO stkVO : childVO) {
						if (stkVO.getStockHolderCode().equals(chk)) {
							selectedVO = stkVO;
							break;
		
						}
					}
				}
			}
		}
		String approver = "";
		try{
			approver = new StockControlDefaultsDelegate().findApproverCode(
				getApplicationSession().getLogonVO().getCompanyCode(),
				selectedVO.getStockHolderCode(),
				session.getDocumentType(),
				selectedVO.getDocumentSubType());
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}
		session.setApproverCode(approver);
		log
				.log(
						Log.FINE,
						"/n/n/n/n/n<------------selectedVO in allocate command------------------->",
						selectedVO);
		log
				.log(
						Log.FINE,
						"/n/n/n/n/n<------------session.getApproverCode()------------------>",
						session.getApproverCode());
		log
				.log(
						Log.FINE,
						"/n/n/n/n/n<------------session.getOneTimeRequestedBy()------------------>",
						session.getOneTimeRequestedBy());
		log
				.log(
						Log.FINE,
						"/n/n/n/n/n<------------session.getPrioritizedStockHolders()------------------>",
						session.getPrioritizedStockHolders());
		StockRequestFilterVO stockRequestFilterVO=getSearchDetails(selectedVO,session);
		stockRequestFilterVO.setAllocateCall(true);
		//int displayPage=Integer.parseInt("1");
		formMonitor.setFlag(ALLOCATENEW);
		log
				.log(
						Log.FINE,
						"<----flag--allocate-->>>Y---allocate new--->>>N---->>>>>>>>>>>>",
						formMonitor.getFlag());
		log.exiting("AllocateCommand", "execute");
		invocationContext.target = "screenload_success";
	}
	/**
	 * Function to set form details into session
	 * @param form
	 * @return
	 */
	private StockFilterVO setStockFilterDetails(MonitorStockForm form){
		StockFilterVO stockFilterVO = new 	StockFilterVO();
		stockFilterVO.setStockHolderType(form.getStockHolderType());
		stockFilterVO.setStockHolderCode(form.getStockHolderCode());
		stockFilterVO.setDocumentType(form.getDocType());
		stockFilterVO.setDocumentSubType(form.getSubType());
		return stockFilterVO;
	}
	/**
	* creating the searching vo
	* @param allocateStockForm
	* @return StockRequestFilterVO
	*/
	private StockRequestFilterVO getSearchDetails(
				MonitorStockVO selectedVO,MonitorStockSession session)
	{
		StockRequestFilterVO stockRequestFilterVO=new StockRequestFilterVO();
					stockRequestFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			stockRequestFilterVO.setDocumentType(session.getDocumentType());
			stockRequestFilterVO.setDocumentSubType(selectedVO.getDocumentSubType());
			if("Y".equals(session.getManual())){
				stockRequestFilterVO.setManual(true);
			}else{
				stockRequestFilterVO.setManual(false);
			}
			//stockRequestFilterVO.setStatus(allocateStockForm.getStatus());
			//stockRequestFilterVO.setStockHolderType(stockHolderType);
			stockRequestFilterVO.setStatus(StockRequestFilterVO.FLAG_ALL);
			stockRequestFilterVO.setStockHolderCode(selectedVO.getStockHolderCode());
			stockRequestFilterVO.setApprover(session.getApproverCode());
			

			log
					.log(
							Log.FINE,
							"<--------------------------stockRequestFilterVO in allocate command------------------->",
							stockRequestFilterVO);
		return stockRequestFilterVO;
	}
	
}
