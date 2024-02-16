package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.missingstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MissingStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MissingStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
*
* @author A-4443
*
*/
public class MissingStockOKCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("MissingStockOKCommand","execute");
		ConfirmStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.confirmstock");
		MissingStockForm missingStockForm = (MissingStockForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<TransitStockVO> transitStockVOsFromSession = new ArrayList<TransitStockVO>();
		TransitStockVO transitStockVO = new TransitStockVO();
		transitStockVOsFromSession = session.getSelectedTransitStockVOs();
		transitStockVO = (TransitStockVO)((ArrayList)transitStockVOsFromSession).get(0);
		transitStockVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		
		ArrayList<MissingStockVO> missingStockVOs = new ArrayList<MissingStockVO>();
		Long startRange = Long.parseLong(missingStockForm.getStartRange());
		Long endRange = Long.parseLong(missingStockForm.getEndRange());
		
		
		for(int i=0; i<(missingStockForm.getRangeFrom().length);i++){
			if(!MissingStockVO.OPERATION_FLAG_NOOPERATION.equals(missingStockForm
					.getHiddenOpFlag()[i])){
		MissingStockVO missingStockVO = new MissingStockVO();
		missingStockVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		missingStockVO.setMissingStartRange(missingStockForm.getRangeFrom()[i]);
		missingStockVO.setMissingEndRange(missingStockForm.getRangeTo()[i]);
		missingStockVO.setStockHolderCode(missingStockForm.getStockHolderCode());
		missingStockVO.setMissingRemarks(missingStockForm.getRemarks()[i]);
		missingStockVO.setOperationFlag(missingStockForm.getHiddenOpFlag()[i]);
		
		if(!"".equals(missingStockForm.getRangeFrom()[i]) && missingStockForm.getRangeFrom()[i].trim().length()>0){
		missingStockVO.setAsciiMissingStartRange(Long.parseLong(missingStockForm.getRangeFrom()[i]));
		}else{
			ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.startrangemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = "action_failure";
			return;	
		}
		if(!"".equals(missingStockForm.getRangeTo()[i]) && missingStockForm.getRangeTo()[i].trim().length()>0){
		missingStockVO.setAsciiMissingEndRange(Long.parseLong(missingStockForm.getRangeTo()[i]));
		}else{
			ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.endrangemandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = "action_failure";
			return;	
		}
		
		
		missingStockVOs.add(missingStockVO);
		
		
		if (startRange <= Long.parseLong(missingStockForm
						.getRangeFrom()[i])) {              
					if (!(Long.parseLong(missingStockForm.getRangeFrom()[i]) <= endRange)) {

						ErrorVO error = new ErrorVO(
								"stockcontrol.defaults.rangeFromshouldbewithinactualstartandendranges");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
						session.setMissingStockVOs(missingStockVOs);
						invocationContext.target = "action_failure";
						return;
					}
				}
		else{
			ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.rangeFromshouldbewithinactualstartandendranges");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			session.setMissingStockVOs(missingStockVOs);
			invocationContext.target = "action_failure";
			return;	
		}
		
		
			if (Long.parseLong(missingStockForm.getRangeTo()[i]) <= endRange) {
					if (!(startRange <= Long.parseLong(missingStockForm
							.getRangeTo()[i]))) {

						ErrorVO error = new ErrorVO(
								"stockcontrol.defaults.rangeToshouldbewithinactualstartandendranges");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
						session.setMissingStockVOs(missingStockVOs);
						invocationContext.target = "action_failure";
						return;
					}
				}
		else{
			ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.rangeToshouldbewithinactualstartandendranges");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			session.setMissingStockVOs(missingStockVOs);
			invocationContext.target = "action_failure";
			return;	
		}
			
			if(Long.parseLong(missingStockForm.getRangeTo()[i])<(Long.parseLong(missingStockForm.getRangeFrom()[i]))){
				ErrorVO error = new ErrorVO(
				"stockcontrol.defaults.endrangeshouldbegreaterthanstartrange");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(error);
				session.setMissingStockVOs(missingStockVOs);
			invocationContext.target = "action_failure";
			return;	
		}
		}
		}
		/*************************************************/
		/**
		 * Eg 25-100 is the range selected to mark some as missing
		 * validate following conditions
		 * 28-50,49-60 marked as missing
		 * 28-50,26-29 marked as missing
		 */
		int indx=0;
		for(MissingStockVO missingVO:missingStockVOs){  
			
			int innerIdx=0;
			for(MissingStockVO innerMissingVO:missingStockVOs){
				if(indx!=innerIdx){
					if(missingVO.getAsciiMissingStartRange()<=innerMissingVO.getAsciiMissingStartRange()
							&& missingVO.getAsciiMissingEndRange()>=innerMissingVO.getAsciiMissingStartRange()){
						ErrorVO error = new ErrorVO(
						"stockcontrol.defaults.avoidoverlappedranges");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
						session.setMissingStockVOs(missingStockVOs);
						invocationContext.target = "action_failure";
						return;	
					}
				}
				innerIdx++;
			}
			indx++;
		}

		/*********************************************************/
		transitStockVO.setMissingRanges(missingStockVOs);
		transitStockVO.setStockHolderCode(missingStockForm.getStockHolderCode());
		//transitStockVO.setDocumentType(documentType)
		StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
		Collection<TransitStockVO> transitStockVOs = null;
		try {
			transitStockVOs = stockControlDefaultsDelegate.updateMissingStock(transitStockVO);
		} catch (BusinessDelegateException e) {
			
			log.log(Log.FINE,"BusinessDelegateException");
		}
		if(transitStockVOs!=null && transitStockVOs.size()>0 ){
			session.setTransitStockVOs(transitStockVOs);
		}else{
			session.setTransitStockVOs(null);
		}
		/*else{
			ErrorVO error = new ErrorVO(
			"stockcontrol.defaults.norecordsfound");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			//session.setTransitStockVOs(null);
			invocationContext.target = "action_failure";
			return;	
		}*/
		/*if(errors!=null && errors.size()>0)
		{
			invocationContext.target = "action_failure";
			return;
		}*/
		log.log(Log.FINE,"Before setOkSuccess");
		missingStockForm.setOkSuccess("Y");
		session.setMissingStockVOs(null);
		invocationContext.target = "action_success";
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

}
