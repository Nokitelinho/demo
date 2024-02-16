/*
 * PrintCommand.java Created on Jan 9, 2006
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1863
 *
 */
public class PrintCommand extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("ReserveAWB PrintCommand");

	private static final String REPORT_ID = "RPRSTK003";

	
	private static final String ACTION_SL = "generateStockListReport";


	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		StockListForm form = (StockListForm) invocationContext.screenModel;
		StockFilterVO filterVO = new StockFilterVO();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<DocumentVO> doctype = new ArrayList<DocumentVO>();

		getReportSpec().setReportId(REPORT_ID);
		filterVO = getFilterForPrint(form, logonAttributes, doctype);
		log.log(Log.FINE, "\n\n\n----------FILTER VO----", filterVO);
		getReportSpec().addFilterValue(filterVO);
		getReportSpec().setResourceBundle(form.getBundle());
		getReportSpec().setProductCode("stockcontrol");
		getReportSpec().setSubProductCode("defaults");
		getReportSpec().setAction(ACTION_SL);
		
			getReportSpec().setPreview(true);
		
		generateReport();
		log.log(Log.FINE, "\n\n\n----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();

	}

	private StockFilterVO getFilterForPrint(StockListForm form,
			LogonAttributes logonAttributes, Collection<DocumentVO> doctype) {
		StockFilterVO stockFilterVO = new StockFilterVO();
		if (form.getDocumentType() != null
				&& form.getDocumentType().trim().length() > 0
				&& !("ALL").equals(form.getDocumentType())) {
			stockFilterVO.setDocumentType(form.getDocumentType().trim());
			log.log(Log.FINE, "\n\n\n----------DOCTYPE---->", stockFilterVO.getDocumentType());
		} else {
			stockFilterVO.setDocumentType(null);
		}
		
			
			
		if (form.getDocSubType() != null
				&& form.getDocSubType().trim().length() > 0
				&& !("ALL").equals(form.getDocSubType())) {
			stockFilterVO.setDocumentSubType(form.getDocSubType());
			log.log(Log.FINE, "\n\n\n----------SUBTYPE---->", stockFilterVO.getDocumentSubType());
		} else {
			stockFilterVO.setDocumentSubType(null);
		}
		
		if(form.getAirline()!=null && form.getAirline().trim().length()>0) {
			stockFilterVO.setAirlineCode(form.getAirline());
		} else {
			stockFilterVO.setAirlineCode(null);
		}
		
		stockFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		stockFilterVO.setAirportCode(logonAttributes.getAirportCode());
		stockFilterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		
		
		return stockFilterVO;
	}


}
