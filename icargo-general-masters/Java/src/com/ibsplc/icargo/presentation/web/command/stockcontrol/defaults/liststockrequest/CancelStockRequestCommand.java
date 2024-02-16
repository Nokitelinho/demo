/*
 * CancelStockRequestCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.liststockrequest;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListStockRequestForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRequestSession;

/**
 * @author A-1952
 *
 */
public class CancelStockRequestCommand extends BaseCommand {

      private static final String NEW="NEW";
      private static final String ALLOCATED="ALLOCATED";
      private static final String APPROVED="APPROVED";
      private static final String CANCELLED="CANCELLED";
      private static final String COMPLETED="COMPLETED";
      private static final String REJECTED="REJECTED";

   /**
    * @param invocationContext
    * @throws CommandInvocationException
    * @return void
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = true;
		ListStockRequestForm listStockRequestForm = (ListStockRequestForm) invocationContext.screenModel;
    	ListStockRequestSession session = getScreenSession("stockcontrol.defaults", "stockcontrol.defaults.liststockrequest");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
    	Page<StockRequestVO> pageStockRequestVO = session.getPageStockRequestVO();
    	StockRequestVO cancelVO = new StockRequestVO();
    	String[] chk=listStockRequestForm.getCheckbox();
    	String referenceNumber = null;

    	if(chk!=null){
			referenceNumber = chk[0];
		}else{
			referenceNumber = session.getRefNo();
		}

			if(pageStockRequestVO!=null){
				for(StockRequestVO stockRequestVO:pageStockRequestVO){
				if(stockRequestVO!=null){
					if(stockRequestVO.getRequestRefNumber().equals(referenceNumber)){
						session.setRefNo(referenceNumber);
						cancelVO = stockRequestVO;
						cancelVO.setLastUpdateUser(logonAttributes.getUserId());
					}
				}
		    }
	      }


			if(ALLOCATED.equals(cancelVO.getStatus().toUpperCase())){
				isValid = false;
				Object[] obj = { "ALLOCATED" };
				error = new ErrorVO("stockcontrol.defaults.statusofselectedreqisnot", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (APPROVED.equals(cancelVO.getStatus().toUpperCase())) {
				isValid = false;
				Object[] obj = { "APPROVED" };
				error = new ErrorVO("stockcontrol.defaults.statusofselereqisnot", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (CANCELLED.equals(cancelVO.getStatus().toUpperCase())) {
				isValid = false;
				Object[] obj = { "CANCELLED" };
				error = new ErrorVO("stockcontrol.defaults.statusofrequestisnotnew", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (COMPLETED.equals(cancelVO.getStatus().toUpperCase())) {
				isValid = false;
				Object[] obj = { "COMPLETED" };
				error = new ErrorVO("stockcontrol.defaults.statusrequestnotnew", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if (REJECTED.equals(cancelVO.getStatus().toUpperCase())) {
				    isValid = false;
					if ("N".equals(listStockRequestForm.getCanCancel())) {
					Object[] obj = { "REJECTED" };
					error = new ErrorVO("stockcontrol.defaults.statusofrequestisrejected", obj);
					error.setErrorDisplayType(ErrorDisplayType.WARNING);
					errors.add(error);
				}else{
					try{
						listStockRequestForm.setCanCancel("N");
						StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
						stockControlDefaultsDelegate.cancelStockRequest(cancelVO);
					 }catch(BusinessDelegateException businessDelegateException){
						 errors=handleDelegateException(businessDelegateException);
					}
				}

		   }
			if (NEW.equals(cancelVO.getStatus().toUpperCase())) {

				 try{
						StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
						stockControlDefaultsDelegate.cancelStockRequest(cancelVO);
					 }catch(BusinessDelegateException businessDelegateException){
						 errors=handleDelegateException(businessDelegateException);
        	     }
			}

		if (errors != null && errors.size() > 0) {

				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
			}else{
				invocationContext.target = "screenload_success";
		}

	}

}

