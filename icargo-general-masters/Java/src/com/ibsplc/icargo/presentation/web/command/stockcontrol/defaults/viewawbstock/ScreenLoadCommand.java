/*
 * ScreenLoadCommand.java Created on Jan 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.viewawbstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.DisplayAwbStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRangeHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewAwbStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3184
 *
 */

public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("STOCK CONTROLLER");
	
	private static final String TX_COD_MONITOR_STOCK = "MONITOR_STOCK";

	/**
	 * The execute method in BaseCommand
	 * @author A-3184
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadCommand","execute");
		ViewAwbStockForm viewAwbStockForm=(ViewAwbStockForm)invocationContext.screenModel;
		Collection<StockRangeHistoryVO> stockRangeHistoryVOs=new ArrayList<StockRangeHistoryVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		StockRangeFilterVO stockRangeFilterVO= new StockRangeFilterVO();
		DisplayAwbStockSession session = (DisplayAwbStockSession)
											getScreenSession("stockcontrol.defaults","stockcontrol.defaults.viewawbstock");
		ListStockRangeHistorySession awbsession =
			getScreenSession("stockcontrol.defaults","stockcontrol.defaults.listawbstockhistory");

		//Map<String, Collection<OneTimeVO>> map = handleScreenLoadDetails(getApplicationSession().getLogonVO().getCompanyCode());
		session.removeAllAttributes();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();

		String companyCode=logonAttributesVO.getCompanyCode();
		stockRangeFilterVO.setCompanyCode(companyCode);
		/*Mod by A-2394 */
		if(viewAwbStockForm.getAwp()!=null && viewAwbStockForm.getAwp().length()>0){
			AirlineValidationVO validationVO = null;
			try {
				validationVO = new AirlineDelegate().validateNumericCode(logonAttributesVO.getCompanyCode(), viewAwbStockForm.getAwp());
				stockRangeFilterVO.setAirlineIdentifier(validationVO.getAirlineIdentifier());
			}catch (BusinessDelegateException e) {
//printStackTrrace()();
				errors = handleDelegateException(e);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_success";
				return;
			}
		} else {
			stockRangeFilterVO.setAirlineIdentifier(logonAttributesVO.getOwnAirlineIdentifier());
		}

		stockRangeFilterVO.setHistory(false);
		stockRangeFilterVO.setAwb(viewAwbStockForm.getAwb());

		TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TX_COD_MONITOR_STOCK);
		if(privilegeNewVO!=null){
			stockRangeFilterVO.setPrivilegeLevelType(privilegeNewVO.getLevelType());
			stockRangeFilterVO.setPrivilegeLevelValue(privilegeNewVO.getTypeValue());
			stockRangeFilterVO.setPrivilegeRule(privilegeNewVO.getPrivilegeCode());
		}
		log.log(Log.INFO, "$$$$ Value to delegate  : ", stockRangeFilterVO);
		try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();

			StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
			stockDepleteFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			stockDepleteFilterVO.setAirlineId(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
			//Commented by A-5153 for CRQ_ICRD-38007
			//stockControlDefaultsDelegate.autoDepleteStock(stockDepleteFilterVO);

			stockRangeHistoryVOs  = stockControlDefaultsDelegate.findStockRangeHistory(stockRangeFilterVO);
			if(stockRangeHistoryVOs==null || stockRangeHistoryVOs.size()==0 )
			{
			log
					.log(
							Log.FINE,
							"\n\n.....................stockRangeHistoryVOs...............> ",
							stockRangeHistoryVOs);
			ErrorVO error = null;
			//Object[] obj = { "norecords" };
			error = new ErrorVO("stockcontrol.defaults.norecordsmonitornotauthorized");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			return;
			}
			for(StockRangeHistoryVO historyVo:stockRangeHistoryVOs){
				viewAwbStockForm.setAwb(historyVo.getAwbRange());
			}
		/*if(map != null){

			awbsession.setOneTimeStockStatus(map.get("stockcontrol.defaults.stockstatus"));
			awbsession.setOneTimeStockUtilizationStatus(map.get("stockcontrol.defaults.stockutilizationstatus"));
*/
		Collection<OneTimeVO> oneTimeVOs =new ArrayList<OneTimeVO>();

		if (awbsession.getOneTimeStockStatus()!=null){
			oneTimeVOs.addAll(awbsession.getOneTimeStockStatus());

			 }
		if (awbsession.getOneTimeStockUtilizationStatus()!=null){
			oneTimeVOs.addAll(awbsession.getOneTimeStockUtilizationStatus());
		}
		if(stockRangeHistoryVOs!=null){
			for(StockRangeHistoryVO srhVO : stockRangeHistoryVOs){
				if(oneTimeVOs!=null){
					srhVO.setStatus(findOneTimeDescription(oneTimeVOs,srhVO.getStatus()));

					}

				}
		}
		log.log(Log.FINE, "stockrangehistroyVOs --------------------",
				stockRangeHistoryVOs);
		session.setStockRangeHistoryVOs(stockRangeHistoryVOs);
		}
		catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			log.log(Log.FINE,"\n\n<-------------INSIDE EXCEPTION-------------------->");
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			return;

		}
		log.exiting("ScreenLoadCommand","execute");
		invocationContext.target = "screenload_success";

	}

	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		for (OneTimeVO oneTimeVO:oneTimeVOs){
			if(status.equalsIgnoreCase(oneTimeVO.getFieldValue())){
				return oneTimeVO.getFieldDescription();
			}
		}
		return null;
	}
	
	/**
	 * getPrivilegeVO()
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(
			String transactionCode) {
		log.entering("ListMonitorStockCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList=null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) 
			TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {			
			log.log(Log.SEVERE,e.getMessage());
		}
		log.exiting("ListMonitorStockCommand", "getPrivilegeVO");
		if(privilegeList!=null && !privilegeList.isEmpty()){
			return privilegeList.get(0);
		}
		return null;
	}

}
