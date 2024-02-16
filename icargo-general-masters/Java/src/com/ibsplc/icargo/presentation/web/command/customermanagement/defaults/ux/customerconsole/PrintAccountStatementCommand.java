/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.PrintAccountStatementCommand.java
 *
 *	Created by	:	A-8169
 *	Created on	:	Nov 13, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.file.FileDownloadCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.CustomerConsoleModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.customerconsole.PrintAccountStatementCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8169	:	Nov 13, 2018	:	Draft
 *      1.0     :   A-5491  :   Jun 22, 2020    : Changed to FileDownloadCommand as part of implementing account statement download option to PDF/Excel
 */
public class PrintAccountStatementCommand extends FileDownloadCommand {

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT DEFAULTS");
	private static final String CLASS_NAME = "PrintAccountStatementCommand"; 
	@Override
	public void constructStreamInfo(ActionContext actionContext)
			throws BusinessDelegateException, CommandInvocationException {
		log.entering(CLASS_NAME, "constructStreamInfo");
		CustomerConsoleModel customerConsoleModel = (CustomerConsoleModel) actionContext.getScreenModel();
		LogonAttributes logonAttribute = getLogonAttribute();
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCustomerCode(customerConsoleModel.getCustomerCode().toUpperCase().trim());
		customerFilterVO.setCompanyCode(logonAttribute.getCompanyCode());
		customerFilterVO.setExportMode(customerConsoleModel.getExportMode());
		CustomerMgmntDefaultsDelegate customerMgmntDefaultsDelegate =new CustomerMgmntDefaultsDelegate();
		byte byteArray[]=null;
		byteArray=customerMgmntDefaultsDelegate.printAccountStatement(customerFilterVO).getBytes();
		ByteArrayOutputStream outputStream = null;
		if(byteArray!=null){
			 String fileName="STATEMENT OF ACCOUNT";
			 String contentType ="";
			if("P".equals(customerConsoleModel.getExportMode())){
				fileName=fileName.concat(".pdf");
				contentType="application/pdf";
				
			addStreamInfo(contentType, byteArray, "attachment", fileName); 
			}else if("E".equals(customerConsoleModel.getExportMode())){
				fileName=fileName.concat(".xlsx");
				contentType="application/xlsx"; 
	            try {
	            	outputStream=new ByteArrayOutputStream();
					outputStream.write(byteArray);
					outputStream.close();
					addStreamInfo(contentType, outputStream.toByteArray(), "attachment", fileName); 
				} catch (IOException e) {
					log.log(3, new Object[] { "STATEMENT OF ACCOUNT.xlsx", e.getMessage() });
				}
			} 
			
		}
	}
}