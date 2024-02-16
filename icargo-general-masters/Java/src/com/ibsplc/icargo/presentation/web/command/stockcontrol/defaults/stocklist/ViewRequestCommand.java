/*
 * ViewRequestCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stocklist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ViewStockRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewStockRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ViewRequestCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ViewStockRequestForm form = (ViewStockRequestForm) invocationContext.screenModel;
		ViewStockRequestSession session = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.cto.viewstockrequest");
		StockListSession stockListSession = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.cto.stocklist");
		Collection<DocumentVO> colVo = stockListSession.getDocumentVO();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		StockControlDefaultsDelegate delegate = new StockControlDefaultsDelegate();
		String rowValue = session.getRowValues();
		log.log(Log.FINE, "rowidddddssss", rowValue);
		String[] row = rowValue.split("-");
		Page<StockVO> page = stockListSession.getStockVOs();
		StockFilterVO filtervo = new StockFilterVO();
		StockVO stockDetailsVO = new StockVO();
		log.log(Log.FINE, "INSIDE view requestcommand", rowValue);
		for (StockVO stockvo : page) {
			log.log(Log.FINE, "INSIDE view StockVO", rowValue);
			if ((row[0].equals(stockvo.getAirline()))
					&& row[1].equals(stockvo.getDocumentType())
					&& row[2].equals(stockvo.getDocumentSubType())) {
				log.log(Log.FINE, "row[0].equals(stockvo.getAirline())",
						rowValue);
				filtervo = getRequestDetails(stockvo, logonAttributes, colVo);
				stockDetailsVO = stockvo;
			}
		}
		Collection<StockRequestForOALVO> requestVOs = new ArrayList<StockRequestForOALVO>();
		try {
			requestVOs = delegate.findStockRequestForOAL(filtervo);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);

		}
		for (DocumentVO docvo : colVo) {
			if (stockDetailsVO.getDocumentSubType().trim().equals(
					docvo.getDocumentSubTypeDes().trim())) {
				form.setDocSubType(docvo.getDocumentSubTypeDes());
			}
		}
		log.log(Log.FINE, "BusinessDelegateException", stockDetailsVO);
		log.log(Log.FINE, "BusinessDelegateExceptioneeeee", requestVOs);
		session.setRequestValues(requestVOs);
		form.setAirline(stockDetailsVO.getAirline());
		form.setDocumentType(stockDetailsVO.getDocumentType());
		invocationContext.target = "screenload_success";
	}

	private StockFilterVO getRequestDetails(StockVO stockvo,
			LogonAttributes logonAttributes, Collection<DocumentVO> docvoCol) {

		StockFilterVO vo = new StockFilterVO();

		vo.setAirlineIdentifier(stockvo.getAirlineId());
		vo.setCompanyCode(logonAttributes.getCompanyCode());
		vo.setAirportCode(logonAttributes.getAirportCode());
		vo.setDocumentType(stockvo.getDocumentType());
		//commented by A-7364 as part of ICRD-320783
		/*for (DocumentVO docVO : docvoCol) {
			if (stockvo.getDocumentSubType().trim().equals(
					docVO.getDocumentSubTypeDes().trim())) {
				vo.setDocumentSubType(docVO.getDocumentSubType());
			}
		}*/
		//Added by A-7364 as part of ICRD-320783
		vo.setDocumentSubType(stockvo.getDocumentSubType());
		return vo;
	}

}
