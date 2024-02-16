/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening.PrintScreeningDetailsCommand.java;
 *
 *	Created by	:	A-9084
 *	Created on	:	Nov 10, 2020
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class PrintScreeningDetailsCommand extends AbstractPrintCommand{

	private static final String REPORT_ID = "RPTOPR1027";
	private static final String ACTION="generateConsignmentSecurityReport";
	private Log log = LogFactory.getLogger("Print ScreeningDetails");
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String REPORT_TITLE = "Consignment Security Report";
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
	ConsignmentScreeningModel consignmentScreeningModel = (ConsignmentScreeningModel)actionContext.getScreenModel();
	ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
	String consignmentNumber = consignmentScreeningModel.getConDocNo();
	String paCode= consignmentScreeningModel.getPaCode();
	consignmentScreeningVO.setConsignmentNumber(consignmentNumber);
	consignmentScreeningVO.setPaCode(paCode);
	getReportSpec().setReportId(REPORT_ID);
	getReportSpec().setPreview(true);
	getReportSpec().setProductCode(PRODUCTCODE);
	getReportSpec().setSubProductCode(SUBPRODUCTCODE);
	getReportSpec().setResourceBundle("consignmentsecuritydeclarationResourceBundle");
	getReportSpec().setAction(ACTION);
	getReportSpec().setReportTitle(REPORT_TITLE);
	
	getReportSpec().addFilterValue(consignmentScreeningVO);
	generateReport(actionContext);
		
	}

}
