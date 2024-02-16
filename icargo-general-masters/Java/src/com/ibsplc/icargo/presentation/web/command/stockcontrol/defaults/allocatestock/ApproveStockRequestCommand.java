/*
 * ApproveCommand.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestApproveVO;
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
public class ApproveStockRequestCommand extends BaseCommand {

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
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;

			Page<StockRequestVO> pageStockRequestVO=session.getPageStockRequestVO();
				String[] chk=allocateStockForm.getCheckBox();
				Collection<StockRequestVO> stockRequestVo=new ArrayList<StockRequestVO>();
				String docType = null;
				String docSubType = null;
				for(int i=0;i<chk.length;i++){
					for(StockRequestVO stockRequestVO:pageStockRequestVO){
						docType = stockRequestVO.getDocumentType();
						docSubType = stockRequestVO.getDocumentSubType();
						if(stockRequestVO!=null){
						if(stockRequestVO.getRequestRefNumber().equals(chk[i])){
								stockRequestVO.setCompanyCode(logonAttributes.getCompanyCode());
								stockRequestVO.setApprovalRemarks(allocateStockForm.getAppremarks());
								stockRequestVo.add(stockRequestVO);

						}

					}
				}
			}

			handleUpdate(session,allocateStockForm);
			errors = validateForm(stockRequestVo);
			 if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = "reject_failure";
					return;
				}

			try{
				StockRequestApproveVO stockRequestApproveVO=new StockRequestApproveVO();
				stockRequestApproveVO.setStockRequests(stockRequestVo);
				stockRequestApproveVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
				stockRequestApproveVO.setDocumentSubType(docSubType);
				stockRequestApproveVO.setDocumentType(docType);
				stockRequestApproveVO.setApproverCode(session.getData());
				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
				stockControlDefaultsDelegate.approveStockRequests(stockRequestApproveVO);

			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
//printStackTrrace()();

   }
			if (errors != null && errors.size() > 0) {

				invocationContext.addAllError(errors);
				invocationContext.target = "reject_failure";
			}
			else{
                   invocationContext.target ="list_success";
			}
}
private Collection<ErrorVO> validateForm(Collection<StockRequestVO> stockRequestVoList){


		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		for(StockRequestVO stockRequestvo:stockRequestVoList){
			long allocatedStock=stockRequestvo.getAllocatedStock();
			long approvedStock=stockRequestvo.getApprovedStock();
			if(approvedStock<allocatedStock){
				error = new ErrorVO("stockcontrol.defaults.approvedstknotlessthanallocatedstk");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				break;
			}
			if(stockRequestvo.getApprovedStock() == 0 && NEW.equals(stockRequestvo.getStatus())){
				stockRequestvo.setApprovedStock(stockRequestvo.getRequestedStock());
			}
		}

		return errors;
	}
private void handleUpdate(AllocateStockSession session,AllocateStockForm allocateStockForm){
	Page<StockRequestVO> StockRequestVOs=session.getPageStockRequestVO();
	String[] approvedStock=allocateStockForm.getApprovedStock();
	String[] allocatedStock=allocateStockForm.getAllocatedStock();
	int i=-1;
	for(StockRequestVO stockReqvo:StockRequestVOs){
		i++;
		if(!"".equals(approvedStock[i])){
			stockReqvo.setApprovedStock(new Integer(approvedStock[i]).longValue());

		}
		if(!"".equals(allocatedStock[i])){
			stockReqvo.setAllocatedStock(new Integer(allocatedStock[i]).longValue());

		}
	}
	session.setPageStockRequestVO(StockRequestVOs);

}
    }
