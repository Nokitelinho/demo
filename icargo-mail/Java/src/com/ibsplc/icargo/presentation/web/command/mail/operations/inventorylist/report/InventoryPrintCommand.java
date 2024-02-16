/*
 * InventoryPrintCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.inventorylist.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.InventoryListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class InventoryPrintCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTOPR057";
	private Log log = LogFactory.getLogger("Mailbag Inventory");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.inventorylist";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateInventoryList";
	private static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		InventoryListForm inventoryListForm = 
    		(InventoryListForm)invocationContext.screenModel;
    	//InventoryListSession inventoryListSession = getScreenSession(MODULE_NAME,SCREEN_ID);
			
		LogonAttributes logonAttributes  =  getApplicationSession().getLogonVO();	
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		Map hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(MAIL_CATEGORY);
		try {
		
		hashMap = new SharedDefaultsDelegate().findOneTimeValues
								(logonAttributes.getCompanyCode(),oneTimeList);
		log.log(Log.FINEST, "\n\n hash map******************", hashMap);
		
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			log.log(Log.SEVERE, "\n\n message fetch exception..........;;;;");
		}
		
		     
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(logonAttributes.getCompanyCode());
		reportSpec.addFilterValue(inventoryListForm.getCarrierID());
		reportSpec.addFilterValue(inventoryListForm.getDepPort());
		reportSpec.addExtraInfo(hashMap);	
		reportSpec.setResourceBundle("inventorylistResources");
		reportSpec.setAction(ACTION);
			
			generateReport();
			invocationContext.target = getTargetPage();
		}

	}

