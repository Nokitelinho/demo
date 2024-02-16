/*
 * WorkflowBuilder.java Created on Oct 16, 2012
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.stockcontrol.defaults.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.WorkflowDefaultsProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterInProcessVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.WorkflowVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-4820
 */


public class WorkflowBuilder extends AbstractActionBuilder{
	private Log log = LogFactory.getLogger("STOCKCONTROL DEFAULTS");    
	private static final String STOCK_REQUEST_WORKFLOW = "stockcontrol.defaults.stockrequestCreationWorkflowName";
	
	/**
	 * 
	 * @param blacklistStockVO
	 * @throws SystemException
	 */    
	
	public void notificationForBlacklistStockAndRevokeBlacklistStock(
			BlacklistStockVO blacklistStockVO) throws SystemException {  
		log.entering("StockController", "notificationForBlacklistStockAndRevokeBlacklistStock");
		SharedDefaultsProxy proxy = new SharedDefaultsProxy();
		Collection<String> syspar = new ArrayList<String>();
		if(blacklistStockVO.isBlacklistStock()){
			syspar.add("stockcontrol.defaults.blacklistWorkflowName");
		}
		if(blacklistStockVO.isRevokeBlacklist()){
			syspar.add("stockcontrol.defaults.blacklistRevokeWorkflowName");
		}
		String workflowname = null;
		 if(syspar.size()>0){
		try{
			Map<String,String> sysmap= proxy.findSystemParameterByCodes(syspar);
			if(blacklistStockVO.isBlacklistStock()){
				workflowname = sysmap.get("stockcontrol.defaults.blacklistWorkflowName");
			}
			if(blacklistStockVO.isRevokeBlacklist()){
				workflowname = sysmap.get("stockcontrol.defaults.blacklistRevokeWorkflowName");
			}
		}
		catch(ProxyException proxyException){
			for(ErrorVO ex : proxyException.getErrors()){
				throw new SystemException(ex.getErrorCode());
			}
		}
		if(workflowname!=null && workflowname.trim().length()>0){
			Collection<WorkflowVO> workflowVOs = new ArrayList<WorkflowVO>();
			WorkflowVO workflowVO = new WorkflowVO();
			workflowVO.setCompanyCode(blacklistStockVO.getCompanyCode());
			workflowVO.setStationCode(blacklistStockVO.getStationCode());
			workflowVO.setWorkflowName(workflowname);
			workflowVO.setAssignedTime(blacklistStockVO.getLastUpdateTime());
			workflowVO.setAssigner(blacklistStockVO.getLastUpdateUser());
			workflowVO.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
			workflowVO.setParametersInProcess(new ArrayList<ParameterInProcessVO>());

			ParameterInProcessVO param = new ParameterInProcessVO();
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_DOCTYP);
			param.setParamaterValue(blacklistStockVO.getDocumentType());
			workflowVO.getParametersInProcess().add(param);

			param = new ParameterInProcessVO();
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_DOCSUBTYP);
			param.setParamaterValue(blacklistStockVO.getDocumentSubType());
			workflowVO.getParametersInProcess().add(param);

			param = new ParameterInProcessVO();
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_RNGFRM);
			param.setParamaterValue(blacklistStockVO.getRangeFrom());
			workflowVO.getParametersInProcess().add(param);

			param = new ParameterInProcessVO();
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_RNGTO);
			param.setParamaterValue(blacklistStockVO.getRangeTo());
			workflowVO.getParametersInProcess().add(param);
			
			if(blacklistStockVO.isBlacklistStock()){
				param = new ParameterInProcessVO();
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STKHLD);
				param.setParamaterValue(blacklistStockVO.getDocumentSubType());
				workflowVO.getParametersInProcess().add(param);
				
				param = new ParameterInProcessVO();
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_BLKLSTDAT);
				param.setParamaterValue(blacklistStockVO.getBlacklistDate().toString());
				workflowVO.getParametersInProcess().add(param);
			}
			
			if(blacklistStockVO.isRevokeBlacklist()){
				param = new ParameterInProcessVO();
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_RVKDAT);
				param.setParamaterValue(blacklistStockVO.getBlacklistDate().toString());
				workflowVO.getParametersInProcess().add(param);
			}  
			try {
				WorkflowDefaultsProxy workflowDefaultsProxy = new WorkflowDefaultsProxy();
				workflowVOs.add(workflowVO);
				log.log(Log.FINE, "WORKFLOW ------------>>>", workflowVOs);
				workflowDefaultsProxy.startWorkflow(workflowVOs);
			} catch (ProxyException proxyException) {
				for (ErrorVO errorVo : proxyException.getErrors()) {
					throw new SystemException(errorVo.getErrorCode());
				}
			}
		}
		}
		log.exiting("StockController", "notificationForBlacklistStockAndRevokeBlacklistStock");
	}
	
	/**
	 * 
	 * 	Method		:	WorkflowBuilder.generateNotificationForStockRequest
	 *	Added by 	:	A-7364 on 08-Dec-2017 as part of ICRD-227512
	 * 	Used for 	:
	 *	Parameters	:	@param stockRequestVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void generateNotificationForStockRequest(
			StockRequestVO stockRequestVO) throws SystemException {  
		log.entering("WorkflowBuilder", "generateNotificationForStockRequest");
		Collection<String> systemCodes = new ArrayList<String>();
		systemCodes.add(STOCK_REQUEST_WORKFLOW);
		String workflowName = null;
		Map<String, String> systemParameters = null;
		try {
			systemParameters = new SharedDefaultsProxy().findSystemParameterByCodes(systemCodes);
			if (systemParameters != null && systemParameters.size() > 0) {
				workflowName = systemParameters.get(STOCK_REQUEST_WORKFLOW);
			}
		}catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getErrors());
		}
		if(workflowName!=null && workflowName.trim().length()>0){
			Collection<WorkflowVO> workFlows = new ArrayList<WorkflowVO>();
			WorkflowVO workflowVO = new WorkflowVO();
			workflowVO.setCompanyCode(stockRequestVO.getCompanyCode());
			//workflowVO.setStationCode(stockRequestVO.getStationCode());
			workflowVO.setWorkflowName(workflowName);
			workflowVO.setAssignedTime(stockRequestVO.getLastUpdateDate());
			workflowVO.setAssigner(stockRequestVO.getLastUpdateUser());
			workflowVO.setLastUpdateUser(stockRequestVO.getLastUpdateUser());
			workflowVO.setParametersInProcess(new ArrayList<ParameterInProcessVO>());
			ParameterInProcessVO paramaterInProcess = null;
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_DOCTYP);
			paramaterInProcess.setParamaterValue(stockRequestVO.getDocumentType());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_DOCSUBTYP);
			paramaterInProcess.setParamaterValue(stockRequestVO.getDocumentSubType());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STKHLD);
			paramaterInProcess.setParamaterValue(stockRequestVO.getStockHolderCode());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STOCKHOLDERTYPE);
			paramaterInProcess.setParamaterValue(stockRequestVO.getStockHolderType());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_REQDAT);
			paramaterInProcess.setParamaterValue(stockRequestVO.getRequestDate().toDisplayDateOnlyFormat());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_REQREFNUM);
			paramaterInProcess.setParamaterValue(stockRequestVO.getRequestRefNumber());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STKREQSTA);
			paramaterInProcess.setParamaterValue(stockRequestVO.getStatus());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			/*paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STKHLDAPR);
			paramaterInProcess.setParamaterValue(stockHolderApprover);
			workflowVO.getParametersInProcess().add(paramaterInProcess);*/
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_REQDOC);
			paramaterInProcess.setParamaterValue(String.valueOf(stockRequestVO.getRequestedStock()));
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			//Added by A-7534 for ICRD-227512
			paramaterInProcess = new ParameterInProcessVO();
			paramaterInProcess.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_CMPCOD);
			paramaterInProcess.setParamaterValue(stockRequestVO.getCompanyCode());
			workflowVO.getParametersInProcess().add(paramaterInProcess);
			WorkflowDefaultsProxy workflowDefaultsProxy = new WorkflowDefaultsProxy();
			workFlows.add(workflowVO);
			try{
				workflowDefaultsProxy.startWorkflow(workFlows);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage(),
						proxyException);
			}
		}
		log.exiting("WorkflowBuilder", "generateNotificationForStockRequest");
	}

}
