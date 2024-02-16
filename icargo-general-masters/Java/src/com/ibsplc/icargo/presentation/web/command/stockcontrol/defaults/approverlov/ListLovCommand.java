/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.approverlov;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ApproverLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1870
 *
 */
public class ListLovCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadCommand","lovcommand");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		String companyCode = logonAttributes.getCompanyCode(); 
		ApproverLovForm approverLovForm = (ApproverLovForm) invocationContext.screenModel;
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
		StockHolderLovFilterVO  stockHolderLovFilterVO=new StockHolderLovFilterVO();
		StockHolderPriorityVO stockHolderPriorityVO=new StockHolderPriorityVO();
		StockHolderPriorityVO stockHolderPriorityVo=new StockHolderPriorityVO();
		String[] chkBoxVal=approverLovForm.getApproverChkBox();
		int displayPage=Integer.parseInt(approverLovForm.getDisplayPage());
		session.setPageStockLovVO(null);

		ArrayList<StockHolderPriorityVO> stockHolderpriorityVos=(ArrayList<StockHolderPriorityVO>)session.getPrioritizedStockHolders();		
		if(chkBoxVal!=null){
		int id=Integer.parseInt(chkBoxVal[0]);
		if(id==1){
			log.log(Log.FINE,
					"_____________approverLovForm.getDescription()-------->",
					approverLovForm.getDescription());
			stockHolderPriorityVO = stockHolderpriorityVos.get(id-1);
			stockHolderLovFilterVO.setStockHolderType(stockHolderPriorityVO.getStockHolderType());
			stockHolderLovFilterVO.setStockHolderName(approverLovForm.getDescription());
			stockHolderLovFilterVO.setDocumentType(session.getDocType());
			stockHolderLovFilterVO.setDocumentSubType(session.getDocSubType());
			stockHolderLovFilterVO.setCompanyCode(companyCode);
			stockHolderLovFilterVO.setStockHolderCode(upper(approverLovForm.getCode()));
			//stockHolderLovFilterVO.setDescription(approverLovForm.getDescription());


		}else{
			log
					.log(
							Log.FINE,
							"________________approverLovForm.getDescription()--------->",
							approverLovForm.getDescription());
			stockHolderPriorityVO=stockHolderpriorityVos.get(id-1);
			stockHolderPriorityVo=stockHolderpriorityVos.get(id-2);
			stockHolderLovFilterVO.setApproverCode(upper(stockHolderPriorityVo.getStockHolderCode()));
			stockHolderLovFilterVO.setStockHolderName(approverLovForm.getDescription());
			stockHolderLovFilterVO.setStockHolderType(stockHolderPriorityVO.getStockHolderType());
			stockHolderLovFilterVO.setDocumentType(session.getDocType());
			stockHolderLovFilterVO.setDocumentSubType(session.getDocSubType());
			stockHolderLovFilterVO.setCompanyCode(companyCode);
			stockHolderLovFilterVO.setStockHolderCode(upper(approverLovForm.getCode()));
			//stockHolderLovFilterVO.setDescription(approverLovForm.getDescription());

			}
		}else{
			log.log(Log.FINE,
					"______________approverLovForm.getDescription()--------->",
					approverLovForm.getDescription());
			stockHolderLovFilterVO.setStockHolderCode(upper(approverLovForm.getCode()));
			stockHolderLovFilterVO.setStockHolderName(approverLovForm.getDescription());
			//stockHolderLovFilterVO.setDescription(approverLovForm.getDescription());
			stockHolderLovFilterVO.setDocumentType(session.getDocType());
			stockHolderLovFilterVO.setDocumentSubType(session.getDocSubType());
			stockHolderLovFilterVO.setStockHolderType(approverLovForm.getStockHolderType());
			stockHolderLovFilterVO.setCompanyCode(companyCode);

		}
		try{
			log
					.log(
							Log.FINE,
							"\n\n\n________ Modified_______________stockHolderLovFilterVO from client--------->",
							stockHolderLovFilterVO);
			Page<StockHolderLovVO> pageLovVO=new StockControlDefaultsDelegate().
			findStockHolderLov(stockHolderLovFilterVO,displayPage);
			if (pageLovVO == null || pageLovVO.size() == 0) {
				log.log(Log.FINE,"____________________________pagelov null--------->");
				ErrorVO error = null;
				error = new ErrorVO("ERR_1026");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				session.setPageStockLovVO(null);
				invocationContext.addAllError(errors);
				invocationContext.target = "list_failure";
				return;

			}
			log.log(Log.FINE,"____________________________pagelov not null--------->");
			for(StockHolderLovVO vo:pageLovVO){
				log
						.log(
								Log.FINE,
								"___________________________getDescription------------>",
								vo.getDescription());
				log
						.log(
								Log.FINE,
								"____________________________getStockHolderCode--------->",
								vo.getStockHolderCode());

			}
			session.setPageStockLovVO(pageLovVO);

		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}

		invocationContext.target="list_success";
	}
	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}

}
