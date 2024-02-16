/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment.report.PrintCNSummaryCommand.java
 *
 *	Created by	:	A-9084
 *	Created on	:	12-Nov-2020
 *
 *  Copyright 2020 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment.report;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment.report.PrintCNSummaryCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-9084	:	12-Nov-2020	:	Draft
 */
public class PrintCNSummaryCommand extends AbstractPrintCommand{
	
	private static final String REPORT_ID = "RPTMTK102";
	private static final String ACTION="generateConsignmentSummaryReports";
	private Log log = LogFactory.getLogger("Print ScreeningDetails");
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String REPORT_TITLE_46 = "Consignment CN 46 Summary";
	private static final String REPORT_TITLE_CN38 = "CN 38 Summary level print";
	private static final String REPORT_ID_CN38 = "RPTMTK103";
	private static final String REPORT_ID_CN41 = "RPTMTK105";
	private static final String REPORT_ID_CN47 = "RPTMTK106";
	private static final String REPORT_ID_CN37 = "RPTMTK104";
	private static final String CN38_SUMMARY = "CNSummary38";
	private static final String CN41_SUMMARY = "CNSummary41";
	private static final String CN47_SUMMARY = "CNSummary47";
	private static final String CN37_SUMMARY = "CNSummary37";
	private static final String BUNDLE = "consignmentResources";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.command.AbstractCommand#execute(com.ibsplc.icargo.framework.web.spring.controller.ActionContext)
	 *	Added by 			: A-9084 on 12-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("PrintCNSummaryCommand", "execute");
		MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		consignmentFilterVO.setConsignmentNumber(maintainConsignmentModel.getConsignment().getConsignmentNumber());
		consignmentFilterVO.setPaCode(maintainConsignmentModel.getConsignment().getPaCode().toUpperCase());
		consignmentFilterVO.setConType(maintainConsignmentModel.getConsignment().getType());
		getReportSpec().setResourceBundle(BUNDLE);
		if("CNSummary46".equals(consignmentFilterVO.getConType())){
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setReportTitle(REPORT_TITLE_46);
		}else if (CN38_SUMMARY.equals(consignmentFilterVO.getConType())){
			getReportSpec().setReportId(REPORT_ID_CN38);
			getReportSpec().setReportTitle(REPORT_TITLE_CN38);
		}else if(CN41_SUMMARY.equals(consignmentFilterVO.getConType())){
			getReportSpec().setReportId(REPORT_ID_CN41);
		}else if(CN47_SUMMARY.equals(consignmentFilterVO.getConType())){
			getReportSpec().setReportId(REPORT_ID_CN47);
		}else if(CN37_SUMMARY.equals(consignmentFilterVO.getConType())){
			getReportSpec().setReportId(REPORT_ID_CN37);
		}
		
		getReportSpec().setPreview(true);
		getReportSpec().setProductCode(PRODUCTCODE);
		getReportSpec().setSubProductCode(SUBPRODUCTCODE);
		getReportSpec().setAction(ACTION);
		
		getReportSpec().addFilterValue(consignmentFilterVO);
		generateReport(actionContext);
	}
	

}
