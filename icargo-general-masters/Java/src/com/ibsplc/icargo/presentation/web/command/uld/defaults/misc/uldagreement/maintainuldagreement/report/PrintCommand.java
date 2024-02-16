/*
 * PrintCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement.report;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class PrintCommand extends AbstractPrintCommand {
	/*
	 * report id
	 */

	private static final String BLANK = "";

	private static final String REPORT_ID = "RPTOPR042";

	private static final String PREVIEW = "true";

	private static final String PRODUCTCODE = "uld";

	private static final String SUBPRODUCTCODE = "defaults";
	
	 private static final String RESOURCE_BUNDLE_KEY = "maintainuldagreement";
		
		private static final String ACTION = "printmaintainuldagreement";
		private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("Print COMMAND", "-------Maintain ULD Agreement");
		MaintainULDAgreementForm form = (MaintainULDAgreementForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//ULDAgreementVO agreementVO = null;
		log.log(Log.INFO, "agreementNumber--------->>>>>"
				+ form.getAgreementNumber());
		String agreementNo="";
		if (form.getAgreementNumber() != null
				&& form.getAgreementNumber().trim().length() > 0) {
			agreementNo= form.getAgreementNumber().toUpperCase();
		}else{
			ErrorVO errorVO = new ErrorVO("uld.defaults.enteragreementnumber");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		/*ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		if (form.getAgreementNumber() != null
				&& form.getAgreementNumber().trim().length() > 0) {
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			try {
				agreementVO = delegate.findULDAgreementDetails(companyCode,
						form.getAgreementNumber().toUpperCase());
			} catch (BusinessDelegateException exception) {
//printStackTrrace()();
				error = handleDelegateException(exception);
			}
		}else{
			ErrorVO errorVO = new ErrorVO("uld.defaults.enteragreementnumber");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = "normal-report-error-jsp";
			return;
		}
		log.log(Log.FINE, "agreementVo" + agreementVO);
		if (agreementVO != null) {
			getReportSpec().setReportId(REPORT_ID);
			ReportMetaData parameterMetaData = new ReportMetaData();
			parameterMetaData.setFieldNames(new String[] { "agreementNumber",
					"partyType", "partyCode", "partyName", "txnType",
					"agreementStatus", "agreementDate", "freeLoanPeriod",
					"demurrageRate", "agreementFromDate", "agreementToDate",
					"demurrageFrequency", "tax", "remark" });
			getReportSpec().addParameterMetaData(parameterMetaData);
			getReportSpec().addParameter(agreementVO);
			Collection<ULDAgreementDetailsVO> detailsVO = agreementVO
					.getUldAgreementDetailVOs();
			if (detailsVO != null && detailsVO.size() > 0) {
				getReportSpec().setData(detailsVO);
				
			}
			
			ReportMetaData reportMetaData = new ReportMetaData();
			reportMetaData.setColumnNames(new String[] { "AGRMNTNUM",
					"PTYTYP", "PTYCOD", "AGRMNTDAT", "TXNTYP",
					"AGRMNTFRMDAT", "AGRMNTTODAT", "AGRMNTSTA", "CURRENCY",
					"REMARKS" });
			reportMetaData.setFieldNames(new String[] { "uldTypeCode",
					"station", "agreementFromDate", "agreementToDate",
					"freeLoanPeriod", "demurrageFrequency",
					"demurrageRate", "tax", "currency", "remark" });
			getReportSpec().setReportMetaData(reportMetaData);
			
			if (PREVIEW.equals(form.getPreview())) {
				getReportSpec().setPreview(true);
			} else {
				getReportSpec().setPreview(false);
			}
			getReportSpec().setProductCode(PRODUCTCODE);
			getReportSpec().setSubProductCode(SUBPRODUCTCODE);
			getReportSpec().setResourceBundle("maintainuldagreement");
			
			generateReport();
			invocationContext.target = getTargetPage();
		} else {
			ErrorVO error = new ErrorVO("uld.defaults.invalidagreement");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "normal-report-error-jsp";
			return;
		}
*/
		
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(companyCode);
		reportSpec.addFilterValue(agreementNo);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		
		generateReport();
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();
		
	}
}
