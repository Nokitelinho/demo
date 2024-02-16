/*
 * ListRangesCommand.java Created on Sep 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1366
 *
 */
public class ListRangesCommand extends BaseCommand {
	private static final String NEW = "New";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AllocateStockSession session = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.allocatestock");
		AllocateStockForm allocateStockForm = (AllocateStockForm) invocationContext.screenModel;
		session.setCheck(null);
		errors = validateForm(allocateStockForm, session);

		handleUpdate(session, allocateStockForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "list_failure";
			return;
		}
		Page<StockRequestVO> pageStockRequestVO = session
				.getPageStockRequestVO();
		RangeFilterVO rangeFilterVO = new RangeFilterVO();
		String[] chk = allocateStockForm.getCheckBox();
		session.setMode("Y");
        DocumentTypeDelegate documentTypeDelegate=new DocumentTypeDelegate();
       	for (StockRequestVO stockRequestVO : pageStockRequestVO) {
			if (chk[0].equals(stockRequestVO.getRequestRefNumber())) {
				session.setStockRequestVO(stockRequestVO);
				session.setCheck(chk[0]);
				rangeFilterVO.setCompanyCode(stockRequestVO.getCompanyCode());
				rangeFilterVO.setDocumentType(stockRequestVO.getDocumentType());
				rangeFilterVO.setDocumentSubType(stockRequestVO
						.getDocumentSubType());
				rangeFilterVO.setStockHolderCode(allocateStockForm
						.getStockControlFor());
				long allocatedStock = stockRequestVO.getAllocatedStock();
				long approvedStock = stockRequestVO.getApprovedStock();
				long toBeAllocated = approvedStock - allocatedStock;
				rangeFilterVO.setNumberOfDocuments(String
						.valueOf(toBeAllocated));
				rangeFilterVO.setAirlineIdentifier(getAirlineIdentifier(allocateStockForm.getAwbPrefix()));
				
				rangeFilterVO.setStartRange(allocateStockForm.getStartRange());
				//Modified by A-5807 for ICRD-76030
				 DocumentVO documentVO=new DocumentVO();
			        documentVO.setDocumentType(stockRequestVO.getDocumentType());
			        documentVO.setCompanyCode(stockRequestVO.getCompanyCode());
			        documentVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
			        SharedRangeVO sharedRangeVO=new SharedRangeVO();
			        sharedRangeVO.setFromrange(allocateStockForm.getStartRange());
			        sharedRangeVO.setToRange(allocateStockForm.getStartRange());
			        Collection<SharedRangeVO> sharedRangeVOs=new ArrayList<SharedRangeVO>();
			        sharedRangeVOs.add(sharedRangeVO);
			        documentVO.setRange(sharedRangeVOs);
			        try {
						documentTypeDelegate.validateRange(documentVO);
					} catch (BusinessDelegateException e) {
						ErrorVO error = null;
						error = new ErrorVO("shared.document.invalidalphanumericrange");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						invocationContext.addAllError(errors);
						invocationContext.target = "list_failure";
						return;
					}
				log.log(Log.FINE, "Manual is ------->", stockRequestVO.isManual());
				rangeFilterVO.setManual(stockRequestVO.isManual());
			}
		}

		try {
			StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
			Collection<RangeVO> rangeVO = stockControlDefaultsDelegate
					.findRanges(rangeFilterVO);
			if (rangeVO == null || rangeVO.size() == 0) {

				ErrorVO error = null;
				error = new ErrorVO("stockcontrol.defaults.rangesnotfound");
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				session.setRangeVO(null);
				invocationContext.addAllError(errors);
				invocationContext.target = "list_failure";
				return;

			}
			session.setRangeVO(rangeVO);
		} catch (BusinessDelegateException businessDelegateException) {
			//printStackTrrace()();
			log.log(Log.SEVERE, "BusinessDelegateException caught");
		}
		invocationContext.target = "list_success";
	}

	private int getAirlineIdentifier(String awbPrefix) {		
		int ownAirlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();	
		if(awbPrefix!=null && awbPrefix.trim().length()>0){
			String[] tokens=awbPrefix.split("-");
			return (tokens!=null && tokens.length>2)?Integer.parseInt(tokens[2]):ownAirlineIdentifier;
		}
		return ownAirlineIdentifier;
	}

	private void handleUpdate(AllocateStockSession session,
			AllocateStockForm allocateStockForm) {
		Page<StockRequestVO> StockRequestVOs = session.getPageStockRequestVO();
		String[] approvedStock = allocateStockForm.getApprovedStock();
		String[] allocatedStock = allocateStockForm.getAllocatedStock();
		int i = -1;
		for (StockRequestVO stockReqvo : StockRequestVOs) {
			i++;
			if (!"".equals(approvedStock[i])) {
				stockReqvo.setApprovedStock(new Integer(approvedStock[i])
						.longValue());

			}
			if (!"".equals(allocatedStock[i])) {
				stockReqvo.setAllocatedStock(new Integer(allocatedStock[i])
						.longValue());

			}

		}
		session.setPageStockRequestVO(StockRequestVOs);

	}

	private Collection<ErrorVO> validateForm(
			AllocateStockForm allocateStockForm, AllocateStockSession session) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		Page<StockRequestVO> pageStockRequestVO = session
				.getPageStockRequestVO();
		String[] chk = allocateStockForm.getCheckBox();

		for (StockRequestVO StockRequestvo : pageStockRequestVO) {
			if (chk[0].equals(StockRequestvo.getRequestRefNumber())) {
				if((StockRequestvo.getApprovedStock()-StockRequestvo.getAllocatedStock())<=0){
			 		error = new ErrorVO("stockcontrol.defaults.approvedstknotlessthanallocatedstk");
			 		error.setErrorDisplayType(ErrorDisplayType.ERROR);
			 		errors.add(error);
			 		break;
				}
				if (NEW.equals(StockRequestvo.getStatus())) {
					error = new ErrorVO("stockcontrol.defaults.stknotapproved");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					break;
				}

			}
		}
		return errors;
	}
	
	
}
