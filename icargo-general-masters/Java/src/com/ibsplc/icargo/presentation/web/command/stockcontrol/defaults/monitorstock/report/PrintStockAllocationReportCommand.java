/*
 * PrintStockAllocationReportCommand.java Created on Apr 5, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock.report;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReturnStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.TransferStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PrintStockAllocationReportCommand.
 */
public class PrintStockAllocationReportCommand extends AbstractPrintCommand {	
	
	/** The Constant GENERATE_RPT. */
	private static final String GENERATE_RPT = "generatereport";
	
	/** The Constant BUNDLE. */
	private static final String BUNDLE = "monitorstockresources";	
	
	/** The Constant AWBSTOCK_REPORT_ID. */
	private static final String STOCK_ALLOC_REPORT_ID = "RPRSTK006";
	
	/** The Constant ACTION. */
	private static final String ACTION = "generateStockAllocationReport";
	
	/** The log. */
	private Log log = LogFactory.getLogger("STOCK CONTROL");
	
	/** The Constant REPORT_TITLE_KEY. */
	private static final String REPORT_TITLE_KEY="stockcontrol.defaults.monitorstock.stockallocation.report.title";
	
	private static final String DOCTYP_AWB="AWB";
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("PrintStockAllocationMonitorReportCommand", "-->execute<-----");
		MonitorStockForm monitorStockForm = (MonitorStockForm) invocationContext.screenModel;	
		ReturnStockSession returnStockSession = getScreenSession("stockcontrol.defaults","stockcontrol.defaults.returnstockrange");	
		TransferStockSession transferStockSession = getScreenSession("stockcontrol.defaults","stockcontrol.defaults.transferstockrange");
		MonitorStockSession monitorSession = getScreenSession("stockcontrol.defaults","stockcontrol.defaults.monitorstock");
		getReportSpec().setAction(ACTION);
		getReportSpec().setReportId(STOCK_ALLOC_REPORT_ID);
		getReportSpec().setResourceBundle(BUNDLE);
		getReportSpec().setReportKey(REPORT_TITLE_KEY);  
		getReportSpec().setProductCode(monitorStockForm.getProduct());
		getReportSpec().setSubProductCode(monitorStockForm.getSubProduct());
		StockAllocationVO stockAllocationVO = null;
		getReportSpec().setPreview(true);	
		if(returnStockSession.getStockAllocationVO()!= null) {
			stockAllocationVO = returnStockSession.getStockAllocationVO();
			returnStockSession.setStockAllocationVO(null);
		} else if(transferStockSession.getStockAllocationVO() != null) {
			stockAllocationVO = transferStockSession.getStockAllocationVO();
			transferStockSession.setStockAllocationVO(null);
			
		}
		if(stockAllocationVO != null) {
			if(stockAllocationVO.getRanges() != null && stockAllocationVO.getRanges().size() > 0) { 
				if(DOCTYP_AWB.equals(stockAllocationVO.getDocumentType())){
				for(RangeVO range : stockAllocationVO.getRanges()){
					int checkDigit=7;
					StringBuilder startRange= new StringBuilder();
					StringBuilder endRange= new StringBuilder();
					int appendStartRange = Integer.parseInt(range.getStartRange())%checkDigit ;
					int appendEndRange = Integer.parseInt(range.getEndRange())%checkDigit ;
					startRange.append(range.getStartRange()).append(appendStartRange);
					endRange.append(range.getEndRange()).append(appendEndRange);
					range.setStartRange(startRange.toString());
					range.setEndRange(endRange.toString());
					}
				}
			}
			getReportSpec().setData(stockAllocationVO.getRanges());
			getReportSpec().addExtraInfo(stockAllocationVO.getExecutionDate().toDisplayFormat("dd-MM-yyyy"));
			getReportSpec().addExtraInfo(stockAllocationVO.getStockControlFor());
			getReportSpec().addExtraInfo(stockAllocationVO.getStockHolderCode());			
		}				
		if(GENERATE_RPT.equals(monitorStockForm.getReportGenerateMode())) {	
			monitorSession.setReportGenerateMode("");
			monitorStockForm.setReportGenerateMode("");
			generateReport();	
			invocationContext.target = getTargetPage();
			log.exiting("PrintStockAllocationMonitorReportCommand", "execute");
		}
		  

	}
}
