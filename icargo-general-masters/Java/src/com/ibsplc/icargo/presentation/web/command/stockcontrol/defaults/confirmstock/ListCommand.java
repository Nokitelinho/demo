/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.confirmstock;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ConfirmStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-4443
 *
 */
public class ListCommand  extends BaseCommand {
	/**
	 * log
	 */
	private Log log = LogFactory.getLogger("STOCKCONTROL DEFAULTS");
	private static final String BUTTON_ACTIION_LIST="L";
	private static final String BUTTON_ACTIION_CLOSE="C";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {

		 log.log(Log.FINE,"INSIDE LIST STOCK *****************************************");
		 ConfirmStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.confirmstock");
		ConfirmStockForm confirmStockForm=(ConfirmStockForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		/*if("".equals(confirmStockForm.getStockHolderCode()) &&
				(confirmStockForm.getStockHolderCode().trim().length()==0 )){
			ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.stockholdercodemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = "list_failure";
			return;	
		}*/
		if (BUTTON_ACTIION_LIST.equals(confirmStockForm.getBtn())) {   
			errors = validateForm(confirmStockForm);
			if (errors != null && errors.size() > 0) {

				invocationContext.addAllError(errors);
				invocationContext.target = "list_failure";
				return;
			}
		}    
		 StockRequestFilterVO stockRequestFilterVO = new StockRequestFilterVO();  
		 if(session.getFilterDetails()!=null && !BUTTON_ACTIION_LIST.equals(confirmStockForm.getBtn())){
			 stockRequestFilterVO = session.getFilterDetails();             
		 }
		 else{
			 stockRequestFilterVO=getSearchDetails(confirmStockForm); 
			 session.setFilterDetails(stockRequestFilterVO);                        
		 }
		 //This condition will be executed when Close btn is clicked from 
		 //Blacklist screen
		 if (BUTTON_ACTIION_CLOSE.equals(confirmStockForm.getBtn())) {                                               
                          
			confirmStockForm.setStockHolderCode(stockRequestFilterVO
					.getStockHolderCode());   
			confirmStockForm.setDocType(stockRequestFilterVO.getDocumentType());
			confirmStockForm.setDocSubType(stockRequestFilterVO
					.getDocumentSubType());
			confirmStockForm.setOperation(stockRequestFilterVO.getOperation());
			if (stockRequestFilterVO.getFromDate() != null) {
				confirmStockForm.setFromDate(stockRequestFilterVO.getFromDate()
						.toDisplayDateOnlyFormat());
			}
			if (stockRequestFilterVO.getToDate() != null) {
				confirmStockForm.setToDate(stockRequestFilterVO.getToDate()
						.toDisplayDateOnlyFormat());
			}
			confirmStockForm.setStatus(stockRequestFilterVO.getStatus());
			confirmStockForm.setStockHolderType(stockRequestFilterVO
					.getStockHolderType());
		}
		
		Collection<TransitStockVO> transitStockVOs = null;
		StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
		try {
			transitStockVOs = stockControlDefaultsDelegate.findTransitStocks(stockRequestFilterVO);
		} catch (BusinessDelegateException e) {
			
			log.log(Log.SEVERE,"BusinessDelegateException");
		}
		if(transitStockVOs!=null && transitStockVOs.size()>0 ){
			session.setTransitStockVOs(transitStockVOs);
		}else{
			ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.norecordsfound");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			session.setTransitStockVOs(null);
			invocationContext.target = "list_failure";
			return;	
		}
		if(errors!=null && errors.size()>0)
		{
			invocationContext.target = "list_Failure";
			return;
		}
		invocationContext.target ="list_success";
	}



/**
* creating the searching vo
* @param ConfirmStockForm
* @return StockRequestFilterVO
*/
private StockRequestFilterVO getSearchDetails(ConfirmStockForm confirmStockForm)
{
	StockRequestFilterVO stockRequestFilterVO=new StockRequestFilterVO();

	
		stockRequestFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		stockRequestFilterVO.setDocumentType(confirmStockForm.getDocType());
		stockRequestFilterVO.setDocumentSubType(confirmStockForm.getDocSubType());
		stockRequestFilterVO.setStatus(confirmStockForm.getStatus());
		stockRequestFilterVO.setStockHolderType(confirmStockForm.getStockHolderType());
		stockRequestFilterVO.setStockHolderCode(upper(confirmStockForm.getStockHolderCode()));
		stockRequestFilterVO.setAirlineIdentifier(""+getAirlineIdentifier(confirmStockForm.getAwbPrefix()));
		stockRequestFilterVO.setOperation(confirmStockForm.getOperation());
		LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		if(confirmStockForm.getFromDate()!= null &&
				confirmStockForm.getFromDate().trim().length()!=0){
			stockRequestFilterVO.setFromDate(from.setDate(confirmStockForm.getFromDate()));
		}

		if(confirmStockForm.getToDate()!= null &&
				confirmStockForm.getToDate().trim().length()!=0 ){
			LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			stockRequestFilterVO.setToDate(to.setDate( confirmStockForm.getToDate()));
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

private Collection<ErrorVO> validateForm(ConfirmStockForm confirmStockForm){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if("".equals(confirmStockForm.getStockHolderCode()) &&
				(confirmStockForm.getStockHolderCode().trim().length()==0 )){
				error = new ErrorVO("stockcontrol.defaults.stockholdercodemandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		if("".equals(confirmStockForm.getDocType()) &&
				(confirmStockForm.getDocType().trim().length()==0 )){
				error = new ErrorVO("stockcontrol.defaults.doctypemandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}
	
		return errors;
	}
}
