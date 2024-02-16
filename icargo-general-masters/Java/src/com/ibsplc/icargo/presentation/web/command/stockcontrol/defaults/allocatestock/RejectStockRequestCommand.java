/*
 * RejectStockRequestCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-1366
 *
 */
public class RejectStockRequestCommand extends BaseCommand {
	private static final String NEW = "New";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	    ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	        LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		    AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
			Page<StockRequestVO> pageStockRequestVO=session.getPageStockRequestVO();
			String[] chk=allocateStockForm.getCheckBox();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			errors = validateForm(allocateStockForm,session);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = "reject_failure";
				return;
			}
			Collection<StockRequestVO> stockRequestVo=new ArrayList<StockRequestVO>();
			for(int i=0;i<chk.length;i++){
				for(StockRequestVO stockRequestVO:pageStockRequestVO){
					if(stockRequestVO!=null){

						if(stockRequestVO.getRequestRefNumber().equals(chk[i])){
							stockRequestVO.setCompanyCode(logonAttributes.getCompanyCode());
							stockRequestVO.setApprovalRemarks(allocateStockForm.getAppremarks());
							stockRequestVo.add(stockRequestVO);

						}
					}
				}
			}

			try{
				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
				stockControlDefaultsDelegate.rejectStockRequests(stockRequestVo);

			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
   }
                   invocationContext.target ="list_success";
}
private Collection<ErrorVO> validateForm(AllocateStockForm allocateStockForm, AllocateStockSession session){


		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		Page<StockRequestVO> pageStockRequestVO=session.getPageStockRequestVO();
		String[] chk=allocateStockForm.getCheckBox();

			for(StockRequestVO StockRequestvo:pageStockRequestVO){
				if(chk[0].equals(StockRequestvo.getRequestRefNumber())){
					 if(!(NEW.equals(StockRequestvo.getStatus()))){
							error = new ErrorVO("stockcontrol.defaults.onlystkreqnewstatuscanberejected");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);


					}
					break;
				}

			}



		return errors;
	}
}
