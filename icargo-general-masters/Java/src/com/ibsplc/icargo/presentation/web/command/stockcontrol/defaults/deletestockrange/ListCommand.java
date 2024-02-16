/*
 * ListCommand.java Created on July 12, 2017
 *
 * Copyright  IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.deletestockrange;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.DeleteStockRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DeleteStockRangeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand extends BaseCommand {
	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		DeleteStockRangeForm form = (DeleteStockRangeForm) invocationContext.screenModel;
		DeleteStockRangeSession sessionDeleteStockRange = (DeleteStockRangeSession) getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.deletestockrange");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		String companyCode = logonAttributesVO.getCompanyCode();
		Collection<ErrorVO> errorVos = validateForm(form,
				sessionDeleteStockRange);
		if (errorVos != null && errorVos.size() > 0) {
			invocationContext.addAllError(errorVos);
			invocationContext.target = FAILURE;
			return;
		}
		StockFilterVO stockFilterVO = sessionDeleteStockRange
				.getStockFilterVO();
		RangeVO rangeVO = new RangeVO();
		rangeVO.setStartRange(form.getDeleteFrom());
		rangeVO.setEndRange(form.getDeleteTo());
		if (form.getNoOfDocs() != null
				&& form.getNoOfDocs().trim().length() > 0) {
			rangeVO.setNumberOfDocuments(Long.valueOf(form.getNoOfDocs()));
		}
		/*
		 * Call for finding the stock ranges available for a stock holder
		 */
		RangeFilterVO rangeFilterVO = new RangeFilterVO();
		Collection<RangeVO> rangeVOs = null;
		rangeFilterVO.setCompanyCode(companyCode);
		if (stockFilterVO != null) {
			rangeFilterVO
					.setStockHolderCode(stockFilterVO.getStockHolderCode());
			rangeFilterVO.setDocumentType(stockFilterVO.getDocumentType());
			rangeFilterVO
					.setDocumentSubType(stockFilterVO.getDocumentSubType());
		}
		rangeFilterVO.setStartRange(rangeVO.getStartRange());
		rangeFilterVO.setEndRange(rangeVO.getEndRange());
		rangeFilterVO.setNumberOfDocuments(form.getNoOfDocs());
		try {
			StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
			log.log(Log.FINE, "...rangeFilterVO.........", rangeFilterVO);
			rangeVOs = stockControlDefaultsDelegate.findRanges(rangeFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			errorVos = handleDelegateException(businessDelegateException);
		}
		if (rangeVOs != null && rangeVOs.size() > 0) {
			sessionDeleteStockRange.setCollectionRangeVO(rangeVOs);
		} else {
			ErrorVO error = new ErrorVO("stockcontrol.defaults.invalidrange");		          
	        error.setErrorDisplayType(ErrorDisplayType.ERROR);
	        errorVos.add(error);
	        sessionDeleteStockRange.setCollectionRangeVO(null);
		}
		if (errorVos != null && errorVos.size() > 0) {
			invocationContext.addAllError(errorVos);
			invocationContext.target = FAILURE;
			return;
		}
		invocationContext.target = SUCCESS;
		return;
	}

	private Collection<ErrorVO> validateForm(DeleteStockRangeForm form,
			DeleteStockRangeSession session) {
		Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		int noOfDocs = 0;
		if (form.getNoOfDocs() != null
				&& form.getNoOfDocs().trim().length() > 0) {
			noOfDocs = Integer.parseInt(form.getNoOfDocs());
		}
		if (form.getDeleteFrom() != null
				&& form.getDeleteFrom().trim().length() == 0) {
			error = new ErrorVO(
					"stockcontrol.defaults.deletestock.err.plzenterrangefrom");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errorVos.add(error);
		}
		if (form.getNoOfDocs() != null
				&& form.getNoOfDocs().trim().length() == 0 || noOfDocs == 0) {
			error = new ErrorVO(
					"stockcontrol.defaults.deletestock.err.plzenternoofdocs");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errorVos.add(error);
		}
		return errorVos;
	}
}
