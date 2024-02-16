/*
 * ViewStockRequestCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockrequest;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1927
 *
 */

public class ViewStockRequestCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String TX_COD_STOCK_REQUEST = "STOCK_REQUEST";
	
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ViewStockRequestCommand","execute");
		MaintainStockRequestForm maintainStockRequestForm = (MaintainStockRequestForm) invocationContext.screenModel;
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISKC002
		 */
		MaintainStockRequestSession session = (MaintainStockRequestSession)getScreenSession("stockcontrol.defaults","stockcontrol.defaults.maintainstockrequest");
		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	    Collection<ErrorVO> errors = null;
	    ErrorVO error = null;
		StockRequestVO stockReqVO=null;
		String companyCode=logonAttributes.getCompanyCode();
		
		StockRequestFilterVO stockRequestFilterVO = new StockRequestFilterVO();
		stockRequestFilterVO.setCompanyCode(companyCode);
		stockRequestFilterVO.setRequestRefNumber(maintainStockRequestForm.getReqRefNo().toUpperCase());
		TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TX_COD_STOCK_REQUEST);
		if(privilegeNewVO!=null){
			stockRequestFilterVO.setPrivilegeLevelType(privilegeNewVO.getLevelType());
			stockRequestFilterVO.setPrivilegeLevelValue(privilegeNewVO.getTypeValue());
			stockRequestFilterVO.setPrivilegeRule(privilegeNewVO.getPrivilegeCode());
		}
		//Modified by A-5153 for CRQ_ICRD-107872
		//stockReqVO=findStockRequestDetails(companyCode,maintainStockRequestForm.getReqRefNo().toUpperCase());
		stockReqVO=findStockRequestDetails(stockRequestFilterVO);
		
		//Added by A-5117 for ICRD-4259
		if (stockReqVO.getRequestRefNumber() == null) {
			session.setStockRequestVO(null);
			StockRequestFilterVO stockRequestFilterVO1 = new StockRequestFilterVO();
			stockRequestFilterVO1.setCompanyCode(companyCode);
			stockRequestFilterVO1.setRequestRefNumber(maintainStockRequestForm.getReqRefNo().toUpperCase());
			StockRequestVO requestVO = findStockRequestDetails(stockRequestFilterVO1);
			
			if(requestVO.getRequestRefNumber() != null){
				error = new ErrorVO(
				"stockcontrol.defaults.stocklistnotauthorized");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
			}else{
				error = new ErrorVO("stockcontrol.defaults.maintainstockrequest.norecordsexists");
				error.setErrorDisplayType(ErrorDisplayType.WARNING);
			}
			invocationContext.addError(error);
			invocationContext.target = "screenload_failure";
			return;

		}
		session.setStockRequestVO(stockReqVO);
		Collection<OneTimeVO> statusColl=session.getOneTimeStatus();
		for(OneTimeVO status:statusColl){
				if(status.getFieldValue().equals(stockReqVO.getStatus())) {
					maintainStockRequestForm.setStatus(status.getFieldDescription());
				}
		}
		String dateString = stockReqVO
				.getRequestDate().toDisplayFormat(CALENDAR_DATE_FORMAT);
		maintainStockRequestForm.setReqRefNo(stockReqVO.getRequestRefNumber());
		maintainStockRequestForm.setDateOfReq(dateString);
		maintainStockRequestForm.setReqStock(String.valueOf(stockReqVO.getRequestedStock()));
		maintainStockRequestForm.setAllocatedStock(String.valueOf(stockReqVO.getAllocatedStock()));
		maintainStockRequestForm.setRemarks(stockReqVO.getRemarks());
		maintainStockRequestForm.setAppRejRemarks(stockReqVO.getApprovalRemarks());
		maintainStockRequestForm.setDocType(stockReqVO.getDocumentType());
		maintainStockRequestForm.setSubType(stockReqVO.getDocumentSubType());
		maintainStockRequestForm.setManual(stockReqVO.isManual());
		maintainStockRequestForm.setCode(stockReqVO.getStockHolderCode());
		maintainStockRequestForm.setStockHolderType(stockReqVO.getStockHolderType());
		String type = stockReqVO.getStockHolderType();
		/*Collection<StockHolderPriorityVO> stockHolderPriorityVOs = session.getPrioritizedStockHolders();
	    for(StockHolderPriorityVO stockHolderPriority:stockHolderPriorityVOs){
			 if(stockHolderPriority.getStockHolderType().equals(type)){
				 stockHolderPriority.setStockHolderCode(stockReqVO.getStockHolderCode());
			 }


		 }

		 session.setPrioritizedStockHolders(stockHolderPriorityVOs);*/
		maintainStockRequestForm.setMode("U");
		
		
		log.entering("ViewStockRequestCommand","execute");
		invocationContext.target = "screenload_success";
	}

	/**
	 * The findStockRequestDetails method 
	 * @author A-1927
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 */
	public StockRequestVO findStockRequestDetails(StockRequestFilterVO stockRequestFilterVO){
		StockRequestVO stockRequestVO=new StockRequestVO();
		try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			log.log(Log.FINE,"...........Inside Controller.......");
			stockRequestVO = stockControlDefaultsDelegate.findStockRequestDetails(stockRequestFilterVO);
		}
		catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE, "BusinessDelegateException caught from findStockRequestDetails");
		}
		return stockRequestVO;
}

	
	/**
	 * getPrivilegeVO()
	 * 
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(String transactionCode) {
		log.entering("ViewStockRequestCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList = null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) TransactionPrivilegeHelper
					.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {
			log.log(Log.SEVERE, e.getMessage());
		}
		log.exiting("ViewStockRequestCommand", "getPrivilegeVO");
		if (privilegeList != null && !privilegeList.isEmpty()) {
			return privilegeList.get(0);
		}
		return null;
	}


}

