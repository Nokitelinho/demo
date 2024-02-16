/*
 * PrintCommand.java Created on June 11,2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3184
 * 
 */
public class PrintCommand extends AbstractPrintCommand {

	private static final String AWBSTOCK_REPORT_ID = "RPRSTK004";

	private Log log = LogFactory.getLogger("StockControl");
	
	/**
	 * ACTION
	 */
	private static final String ACTION = "generateAWBStockReport";
	/**
	 * 
	 */
	private static final String BUNDLE = "monitorstockresources";
	/*
	 * The Module and ScreenID mappings
	 */
	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String ALLOCATENEWSTOCK_SCREENID = "stockcontrol.defaults.monitorstock";

	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-3184
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("PrintCommand", "execute"); 
				
		MonitorStockForm monitorStockForm = (MonitorStockForm) invocationContext.screenModel;		

		getReportSpec().setAction(ACTION);
					
		getReportSpec().setReportId(AWBSTOCK_REPORT_ID);
		getReportSpec().setResourceBundle(BUNDLE);
		getReportSpec().setProductCode(monitorStockForm.getProduct());
		getReportSpec().setSubProductCode(monitorStockForm.getSubProduct());		
		getReportSpec().setPreview(true); 
		getReportSpec().addFilterValue(upper(monitorStockForm.getStockHolderCode()));			
		log.log(Log.FINE, "----------REPORT_ID----->", AWBSTOCK_REPORT_ID);
		generateReport();
		ErrorVO error = null;
		if(getErrors() != null && getErrors().size()>0){
			for(ErrorVO err : getErrors()){
				if("stockcontrol.defaults.invalidstockholderformonitor".equalsIgnoreCase(err.getErrorCode())){
					 error = new ErrorVO("stockcontrol.defaults.invalidstockholderformonitor");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					 invocationContext.addError(error);
				}else if("stockcontrol.defaults.stocknotfound".equalsIgnoreCase(err.getErrorCode())){
					 error = new ErrorVO("stockcontrol.defaults.stocknotfound");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					 invocationContext.addError(error);
				}
				else{
					invocationContext.addError(err);
				}
			}
			log.log(Log.FINE, "inside errors");
			invocationContext.target="normal-report-error-jsp";
			return;
		}
		log.log(Log.FINE, "-------After generating report----->");
		invocationContext.target = getTargetPage();
		
		log.exiting("PrintCommand", "execute");

	}
	
	/**
	* method to convert to upper case
	* @param input
	*
	*/

 	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}

}
