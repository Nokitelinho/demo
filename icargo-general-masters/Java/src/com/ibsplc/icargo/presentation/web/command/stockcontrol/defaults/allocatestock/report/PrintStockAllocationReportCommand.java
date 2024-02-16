/*
 * PrintStockAllocationReportCommand.java Created on Apr 5, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock.report;

import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PrintStockAllocationReportCommand.
 */
public class PrintStockAllocationReportCommand extends AbstractPrintCommand {
	

	/** The Constant BUNDLE. */
	private static final String BUNDLE = "allocatestockresources";	
	
	/** The Constant AWBSTOCK_REPORT_ID. */
	private static final String STOCK_ALLOC_REPORT_ID = "RPRSTK006";
	
	/** The Constant GENERATE_RPT. */
	private static final String GENERATE_RPT = "generatereport";
	
	/** The Constant ACTION. */
	private static final String ACTION = "generateStockAllocationReport";
	
	/** The log. */
	private Log log = LogFactory.getLogger("STOCK CONTROL");	
	
	/** The Constant REPORT_TITLE_KEY. */
	private static final String REPORT_TITLE_KEY="stockcontrol.defaults.allocatestock.stockallocation.report.title";

	private static final String DOCTYP_AWB="AWB";

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {			
		log.entering("PrintStockAllocationReportCommand", "--->execute<---- From allocate Stock");
		AllocateStockSession allocateStockSession= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
		AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
		//Added by A-5237 for ICRD-96246 STARTS
		if(invocationContext.hasErrors()){
			invocationContext.target ="reject_failure";
			return;
		}
		//Added by A-5237 for ICRD-96246 Ends 		
		
		if("ReportGenerateModeOn".equals(allocateStockForm.getReportGenerateMode()))  {
				log.log(Log.FINE,"1st entry in PrintStockAllocationReportCommand", "************ From allocateStock");
				allocateStockForm.setReportGenerateMode(GENERATE_RPT);
				invocationContext.target = "screenload_success";
				log.exiting("PrintStockAllocationReportCommand", "execute---->From allocate Stock");
			  }
		else {
			log.log(Log.FINE,"2nd entry in PrintStockAllocationReportCommand", "************ From allocateStock");
			getReportSpec().setAction(ACTION);
			getReportSpec().setReportId(STOCK_ALLOC_REPORT_ID);
			getReportSpec().setResourceBundle(BUNDLE);	
			getReportSpec().setReportKey(REPORT_TITLE_KEY); 
			getReportSpec().setProductCode(allocateStockForm.getProduct());
			getReportSpec().setSubProductCode(allocateStockForm.getSubProduct());		
			getReportSpec().setPreview(true);	
			StockAllocationVO stockAllocationVO = null;
			if(allocateStockSession.getStockAllocationVO()!= null) {
				stockAllocationVO = allocateStockSession.getStockAllocationVO();
			}
			if(stockAllocationVO != null) {				
				getReportSpec().addExtraInfo(stockAllocationVO.getExecutionDate().toDisplayFormat("dd-MM-yyyy"));
				getReportSpec().addExtraInfo(stockAllocationVO.getStockControlFor());
				getReportSpec().addExtraInfo(stockAllocationVO.getStockHolderCode());			
			}	
			if(allocateStockSession.getRangeVO()!= null) {
				Collection<RangeVO> ranges = allocateStockSession.getRangeVO();		
				if(ranges != null && ranges.size() > 0) {
					if(DOCTYP_AWB.equals(stockAllocationVO.getDocumentType())){
					for(RangeVO range : ranges){
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
					getReportSpec().setData(ranges);
				}
			}
			allocateStockForm.setReportGenerateMode("");			
			generateReport();	
			invocationContext.target = getTargetPage();
			
		}
		
	}
}
