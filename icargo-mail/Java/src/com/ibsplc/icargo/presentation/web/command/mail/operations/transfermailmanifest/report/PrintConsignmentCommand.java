package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermailmanifest.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class PrintConsignmentCommand extends AbstractPrintCommand{
	
	private static final String REPORT_ID = "RPTOPR046";
	//Added as part of CRQ ICRD-103713 by A-5526 starts
	private static final String REPORT_ID_CN37 = "RPTMTK094";
	private static final String REPORT_ID_CN38 = "RPTMTK095";
	private static final String REPORT_ID_CN41 = "RPTMTK096";
	private static final String REPORT_ID_CN46 = "RPTMTK097";
	private static final String REPORT_ID_CN47 = "RPTMTK098";
	private static final String MAL_SUMMARY_ACTION="generateConsignmentReports";
	private static final String DSN_SUMMARY_ACTION="generateConsignmentSummaryReports";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.transfermailmanifest";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String FAILURE="print_failiure";
	private static final String SUCCESS=  "print_success";
	private static final String CN38_SUMMARY = "CNSummary38";
	private static final String CN41_SUMMARY = "CNSummary41";
	private static final String CN47_SUMMARY = "CNSummary47";
	private static final String CN37_SUMMARY = "CNSummary37";
	private static final String CN46_SUMMARY = "CNSummary46";
	private static final String REPORT_ID_CNSUM38 = "RPTMTK103";
	private static final String REPORT_ID_CNSUM41 = "RPTMTK105";
	private static final String REPORT_ID_CNSUM47 = "RPTMTK106";
	private static final String REPORT_ID_CNSUM37 = "RPTMTK104";
	private static final String REPORT_ID_CNSUM46 = "RPTMTK102";
	private static final String MAILBAGLEVEL_SUMMARY = "MALSUM";
	private static final String REPORT_ID_MANIFEST_CN46 ="RPRMTK304";
	private static final String REPORT_ID_MANIFEST_CN46_SUMMARY ="RPRMTK305";
	
    
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	@Override
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		TransferMailManifestForm transferMailManifestForm = (TransferMailManifestForm) invocationContext.screenModel;

		TransferMailManifestSession transferMailManifestSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		Collection<TransferManifestVO> transferMftVOs = new ArrayList<>();
		Collection<TransferManifestVO> transferManifestVOs = transferMailManifestSession.getTransferManifestVOs();
		Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		for (TransferManifestVO trfManifestVO : transferManifestVOs) {
			transferMftVOs.add(trfManifestVO);
		}  
		String select = transferMailManifestForm.getSelectMail();
		TransferManifestVO transferManifestVO = ((ArrayList<TransferManifestVO>) (transferMftVOs))
			.get(Integer.parseInt(select));
			if(MailConstantsVO.CONSIGNMENT_TYPE_CN46.equals(transferMailManifestForm.getCnPrintType())||
					CN46_SUMMARY.equals(transferMailManifestForm.getCnPrintType())	){
				try {
					consignmentDocumentVOs = new MailTrackingDefaultsDelegate().findCN46TransferManifestDetails(transferManifestVO);
				} catch (BusinessDelegateException e) {
					log.log(Log.FINE,e);
					this.errors = e.getMessageVO().getErrors();
					invocationContext.target = FAILURE;
					return;
				}
		if(consignmentDocumentVOs==null ||  consignmentDocumentVOs.isEmpty()) {
		return;
	}
			}else {
		try {
			consignmentDocumentVOs = new MailTrackingDefaultsDelegate().findTransferManifestConsignmentDetails(transferManifestVO);
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,e);
			this.errors = e.getMessageVO().getErrors();
			invocationContext.target = FAILURE;
			return;
		}
		if(consignmentDocumentVOs==null ||  consignmentDocumentVOs.isEmpty()) {
			errors = new ArrayList<>();
			errors.add(new ErrorVO("mail.operations.transfermailmanifest.report.consignmentnotavailable"));
			invocationContext.addAllError(getErrors());
			invocationContext.target = FAILURE;
			return;
		}
		}
		Collection<ConsignmentFilterVO> consignmentFilterVOs = new ArrayList<>();
		for(ConsignmentDocumentVO consignmentDocumentVO: consignmentDocumentVOs) {
			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO(); 
			consignmentFilterVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
			if(MailConstantsVO.CONSIGNMENT_TYPE_CN46.equals(transferMailManifestForm.getCnPrintType())||
					CN46_SUMMARY.equals(transferMailManifestForm.getCnPrintType())	){
				consignmentFilterVO.setTransferManifestId(transferManifestVO.getTransferManifestId());
			}
			consignmentFilterVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber()); 
			consignmentFilterVO.setPaCode(consignmentDocumentVO.getPaCode());
			consignmentFilterVO.setBulkDownload(true);
			consignmentFilterVOs.add(consignmentFilterVO);
		}
		
		
		if(MAILBAGLEVEL_SUMMARY.equals(transferMailManifestForm.getCnReportType())){
			
			getReportSpec().setAction(MAL_SUMMARY_ACTION);
			
		if (MailConstantsVO.CONSIGNMENT_TYPE_CN41.equals(transferMailManifestForm.getCnPrintType())) {
			getReportSpec().setReportId(REPORT_ID_CN41);
		} else if (MailConstantsVO.CONSIGNMENT_TYPE_CN37.equals(transferMailManifestForm.getCnPrintType())) {
			getReportSpec().setReportId(REPORT_ID_CN37);
		} else if (MailConstantsVO.CONSIGNMENT_TYPE_CN38.equals(transferMailManifestForm.getCnPrintType())) {
			getReportSpec().setReportId(REPORT_ID_CN38);
		} else if(MailConstantsVO.CONSIGNMENT_TYPE_CN46.equals(transferMailManifestForm.getCnPrintType())) {
			getReportSpec().setReportId(REPORT_ID_MANIFEST_CN46);
		} else if (MailConstantsVO.CONSIGNMENT_TYPE_CN47.equals(transferMailManifestForm.getCnPrintType())) {
			getReportSpec().setReportId(REPORT_ID_CN47);
		} else {
			getReportSpec().setReportId(REPORT_ID);
		}

		}else{
			
			getReportSpec().setAction(DSN_SUMMARY_ACTION);
			
			if (CN41_SUMMARY.equals(transferMailManifestForm.getCnPrintType())) {
				getReportSpec().setReportId(REPORT_ID_CNSUM41);
			}else if (CN37_SUMMARY.equals(transferMailManifestForm.getCnPrintType())) {
				getReportSpec().setReportId(REPORT_ID_CNSUM37);
			}else if (CN38_SUMMARY.equals(transferMailManifestForm.getCnPrintType())) {
				getReportSpec().setReportId(REPORT_ID_CNSUM38);
			}else if (CN46_SUMMARY.equals(transferMailManifestForm.getCnPrintType())) {
				getReportSpec().setReportId(REPORT_ID_MANIFEST_CN46_SUMMARY);
			}else if (CN47_SUMMARY.equals(transferMailManifestForm.getCnPrintType())) {
				getReportSpec().setReportId(REPORT_ID_CNSUM47);
			}	
		}
		
		getReportSpec().setPreview(false);
		getReportSpec().setProductCode(PRODUCTCODE);
		getReportSpec().setSubProductCode(SUBPRODUCTCODE);
		getReportSpec().setResourceBundle("consignmentResources");
		getReportSpec().addFilterValue(consignmentFilterVOs);
		getReportDataAndDownLoad(getReportSpec());
		if (getErrors() != null && !getErrors().isEmpty()) {
			invocationContext.addAllError(getErrors());
			log.log(Log.FINE, "Enter error", getErrors().size());
			invocationContext.target = FAILURE;
			return;
		}
		invocationContext.target = SUCCESS;

	}
	
	private void getReportDataAndDownLoad(ReportSpec reportSpec) {

		String fileName = "ConsignmentReports.pdf";
		Map<String, Object> data = null;
		try {
			if(MAL_SUMMARY_ACTION.equals(reportSpec.getAction())){
				if(REPORT_ID_MANIFEST_CN46.equals(reportSpec.getReportId())){
					data = new MailTrackingDefaultsDelegate().generateCN46ConsignmentReport(reportSpec);
				}else {
			data = new MailTrackingDefaultsDelegate().generateConsignmentReports(reportSpec);
				}
			}else{
				if(REPORT_ID_MANIFEST_CN46_SUMMARY.equals(reportSpec.getReportId())){
					data = new MailTrackingDefaultsDelegate().generateCN46ConsignmentSummaryReport(reportSpec);
				}else {
				data = new MailTrackingDefaultsDelegate().generateConsignmentSummaryReports(reportSpec);
				}
			}
		} catch (BusinessDelegateException e) {
			this.errors = e.getMessageVO().getErrors();
			return;
		}

		if (data != null) {
			writeToResponse(System.currentTimeMillis(), data, fileName);
		}

	}
	
	/**
	 * Writes data in  application/pdf
	 * @param start
	 * @param data
	 */
	private void writeToResponse(long start, Map<String, Object> data, String fileName) {
		OutputStream responseOutputStream = null;
		HttpServletResponse httpServletResponse = getHttpServletResponse();
		try {
			httpServletResponse.setContentType("application/pdf");
			httpServletResponse.setHeader("Expires", "0");
			httpServletResponse.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName);
			httpServletResponse.setHeader("Pragma", "public");
			responseOutputStream = httpServletResponse.getOutputStream();
			byte[] reportData = (byte[]) data.get(ReportConstants.REPORT_DATA);
			if (reportData.length > 0) {
				responseOutputStream.write(reportData);
			}

		} catch (IOException e) {
			log.log(3, "Error ocuured");
		} finally {
			try {
				if (responseOutputStream != null) {
					responseOutputStream.close();
				}
			} catch (IOException e) {
				log.log(3, "Error occurred");
			}

			log.log(3, new StringBuilder().append("Time taken to Generate pdf  ")
					.append(System.currentTimeMillis() - start).append("ms").toString());
		}
	}

}
