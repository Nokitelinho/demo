/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-1870
 *
 */
public class ListCommand  extends BaseCommand {
	/**
	 * log
	 */
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {

		 log.log(Log.FINE,"INSIDE LIST STOCK *****************************************");
		 AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
		AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 //Added by A-7364 as part of ICRD-227512 starts
        MessageInboxSession messageInboxSession = 
				(MessageInboxSession)getScreenSession("workflow.defaults", "workflow.defaults.messageinbox");
			if(messageInboxSession.getMessageDetails() != null){
				invocationContext.target = "msg_proccessed";
				return;
			}
        //Added by A-7364 as part of ICRD-227512 ends
		
		StockRequestFilterVO stockRequestFilterVO=getSearchDetails(allocateStockForm); 
		//Commented as part of ICRD-46860
		//session.setRangeVO(null);
		session.setData(allocateStockForm.getStockControlFor());
		stockRequestFilterVO.setAllocateCall(true);
		int displayPage=Integer.parseInt(allocateStockForm.getDisplayPage());

		try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
		     Page<StockRequestVO> pageStock=stockControlDefaultsDelegate.findStockRequests(stockRequestFilterVO,displayPage);
		     Collection<OneTimeVO> oneTimeVOs =session.getStatus();
		     if(pageStock!=null){
					for(StockRequestVO stockRequestVo : pageStock){
						if(oneTimeVOs!=null){
							stockRequestVo.setStatus(findOneTimeDescription(oneTimeVOs,stockRequestVo.getStatus()));

						}

					}
				}
		     session.setPageStockRequestVO(pageStock);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
		}	
		session.setRangeVO(null); //Added as a part of ICRD-253951
		allocateStockForm.setReportGenerateMode("ReportGenerateModeOn");
		invocationContext.target ="list_success";
	}



/**
* creating the searching vo
* @param allocateStockForm
* @return StockRequestFilterVO
*/
private StockRequestFilterVO getSearchDetails(AllocateStockForm allocateStockForm)
{
	StockRequestFilterVO stockRequestFilterVO=new StockRequestFilterVO();

	
		stockRequestFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		stockRequestFilterVO.setDocumentType(allocateStockForm.getDocType());
		stockRequestFilterVO.setDocumentSubType(allocateStockForm.getDocSubType());
		stockRequestFilterVO.setManual(allocateStockForm.isManual());
		stockRequestFilterVO.setStatus(allocateStockForm.getStatus());
		stockRequestFilterVO.setStockHolderType(allocateStockForm.getStockHolderType());
		stockRequestFilterVO.setStockHolderCode(upper(allocateStockForm.getStockHolderCode()));
		stockRequestFilterVO.setApprover(allocateStockForm.getStockControlFor());
		stockRequestFilterVO.setAirlineIdentifier(""+getAirlineIdentifier(allocateStockForm.getAwbPrefix()));

		LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		if(allocateStockForm.getFromDate()!= null &&
				allocateStockForm.getFromDate().trim().length()!=0){
			stockRequestFilterVO.setFromDate(from.setDate(allocateStockForm.getFromDate()));
		}

		if(allocateStockForm.getToDate()!= null &&
				allocateStockForm.getToDate().trim().length()!=0 ){
			LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			stockRequestFilterVO.setToDate(to.setDate( allocateStockForm.getToDate()));
		}



	return stockRequestFilterVO;
}

private int getAirlineIdentifier(String awbPrefix) {
	if(awbPrefix!=null&&awbPrefix.trim().length()>0){
		String[] tokens=awbPrefix.split("-"); 
		if(tokens.length>2){
			return Integer.parseInt(tokens[2]);
		}
	}
	return getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
}

/**
* This method will the dstatus escription
* corresponding to the value from onetime
* @param oneTimeVOs
* @param status
* @return String
*/
private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
	for (OneTimeVO oneTimeVO:oneTimeVOs){
		if(status.equals(oneTimeVO.getFieldValue())){
			return oneTimeVO.getFieldDescription();
		}
	}
	return null;
}
private String upper(String input){

	if(input!=null){
		return input.trim().toUpperCase();
	}else{
		return "";
	}
}
}
