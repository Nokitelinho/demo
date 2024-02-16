/*
 * CancelStockHolderCommand.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockholder;




import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;

import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockHolderForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */
public class CancelStockHolderCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String SEGMENT_SEPERATOR = "-";
	
	private static final String BLANKSPACE = "";
	
	/**Overriding the execute method
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
							throws CommandInvocationException {
		log.entering("CancelStockHolderCommand","execute");
		ListStockHolderForm form = (ListStockHolderForm)invocationContext.screenModel;
		String[] checkedStockHolder = form.getCheckStockHolder();
		String stkHolderCode = BLANKSPACE;
		log.log(Log.FINE, "====code=============", checkedStockHolder);
		String[] checkValues;
		if(checkedStockHolder[0] != null && checkedStockHolder[0].trim().length()>0){
			checkValues = checkedStockHolder[0].split(SEGMENT_SEPERATOR);
			stkHolderCode = checkValues[0];
		}
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 StockHolderDetailsVO detailsVO  = new StockHolderDetailsVO();
		 detailsVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		 if(checkedStockHolder!=null){
			 detailsVO.setStockHolderCode(stkHolderCode);
		 }
		 try{
			 form.setStatusFlag("cancel");
			 new StockControlDefaultsDelegate().deleteStockHolder(detailsVO);
		 }catch(BusinessDelegateException businessDelegateException){
		 	errors = handleDelegateException(businessDelegateException);
		 }
		 
		 if(errors!=null && errors.size()>0){
			 
			invocationContext.addAllError(errors);
		}
		 
		invocationContext.target = "screenload_success";
		log.exiting("CancelStockHolderCommand","execute");
	}
	
	
}
