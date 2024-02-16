/*
 * PrintCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment.report;


import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTOPR046";
	//Added as part of CRQ ICRD-103713 by A-5526 starts
	private static final String REPORT_ID_CN37 = "RPTMTK094";
	private static final String REPORT_ID_CN38 = "RPTMTK095";
	private static final String REPORT_ID_CN41 = "RPTMTK096";
	private static final String REPORT_ID_CN46 = "RPTMTK097";
	private static final String REPORT_ID_CN47 = "RPTMTK098";
	private static final String ACTION="generateConsignmentReports";
	//Added as part of CRQ ICRD-103713 by A-5526 ends
	//Added as part of ICRD-212235 starts
	private static final String REPORT_ID_AV7 = "RPTMTK052";
	private static final String ACTION_AV7 = "generateAV7Report";
	//Added as part of ICRD-212235 ends
	//Modified as part of CRQ ICRD-103713 by A-5526 
	private static final String ACTION_TK="generateConsignmentReport";
	private Log log = LogFactory.getLogger("List Consignment");
	//private static final String PRINT_FAILURE = "print_failure";
	//private static final String MODULENAME = "mailtracking.defaults";
	//private static final String SCREENID = "mailtracking.defaults.consignment";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	//private static final String BLANK = "";
                
	/**    
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(ActionContext actionContext) throws CommandInvocationException {
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
		//	ConsignmentSession consignmentSession =
	    //		getScreenSession(MODULENAME,SCREENID);
			//String companyCode = logonAttributes.getCompanyCode().toUpperCase();
			MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();

		//	ErrorVO error = null;
		//	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentFilterVO.setConsignmentNumber(maintainConsignmentModel.getConsignment().getConsignmentNumber());
			consignmentFilterVO.setPaCode(maintainConsignmentModel.getConsignment().getPaCode().toUpperCase());
		//Added by A-6991 for ICRD-196641
			consignmentFilterVO.setSubType(maintainConsignmentModel.getConsignment().getSubType());
			consignmentFilterVO.setConType(maintainConsignmentModel.getConsignment().getType());//Added for ICRD_212235 starts
		//	ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		/*	try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate().
				        findConsignmentDocumentDetails(consignmentFilterVO);

			} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
				handleDelegateException(businessDelegateException);
			}


			if(consignmentDocumentVO == null) {
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
				invocationContext.addError(errorVO);
				invocationContext.target = "normal-report-error-jsp";
					return;
				}

				log.log(Log.FINE,"\n\n\n----------consignmentDocumentVO----->"+consignmentDocumentVO);
				log.log(Log.FINE,"\n\n\n----------consignmentFilterVO----->"+consignmentFilterVO);
				ArrayList<MailInConsignmentVO> consignmentVOs=new ArrayList<MailInConsignmentVO>();
				if(consignmentDocumentVO.getMailInConsignmentVOs()!=null && consignmentDocumentVO.getMailInConsignmentVOs().size()>0){
				for(MailInConsignmentVO mailConsignmentVO:consignmentDocumentVO.getMailInConsignmentVOs()){
					if(mailConsignmentVO.getReceptacleSerialNumber()!=null && !("".equals(mailConsignmentVO.getReceptacleSerialNumber()))){
						consignmentVOs.add(mailConsignmentVO);
					}

				}
				if(consignmentVOs.size()==0){
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.noresultsfound");
					invocationContext.addError(errorVO);
					invocationContext.target = "normal-report-error-jsp";
						return;
					}*/
/*			if(consignmentForm.getReportFlag()!=null && "Y".equals(consignmentForm.getReportFlag()))
				getReportSpec().setReportId("RPRMTK092");*/
			//Added as part of CRQ ICRD-103713 by A-5526 starts
			if(MailConstantsVO.CONSIGNMENT_TYPE_CN41.equals(maintainConsignmentModel.getConsignment().getType())){   
				getReportSpec().setReportId(REPORT_ID_CN41);
			}else if(MailConstantsVO.CONSIGNMENT_TYPE_CN37.equals(maintainConsignmentModel.getConsignment().getType())){
				getReportSpec().setReportId(REPORT_ID_CN37);          
			}else if(MailConstantsVO.CONSIGNMENT_TYPE_CN38.equals(maintainConsignmentModel.getConsignment().getType())){
				getReportSpec().setReportId(REPORT_ID_CN38);
			}else if(MailConstantsVO.CONSIGNMENT_TYPE_CN46.equals(maintainConsignmentModel.getConsignment().getType())){   
				getReportSpec().setReportId(REPORT_ID_CN46);
			}else if(MailConstantsVO.CONSIGNMENT_TYPE_CN47.equals(maintainConsignmentModel.getConsignment().getType())){     
				getReportSpec().setReportId(REPORT_ID_CN47);  
			}
			//Added for ICRD_212235 starts
			else if(MailConstantsVO.CONSIGNMENT_TYPE_AV7.equals(maintainConsignmentModel.getConsignment().getType())){     
				getReportSpec().setReportId(REPORT_ID_AV7);  
			}//Added for ICRD_212235 ends
			else{
			getReportSpec().setReportId(REPORT_ID);
			}
			//Added as part of CRQ ICRD-103713 by A-5526 ends
		/*	ReportMetaData parameterMetaData = new ReportMetaData();
			parameterMetaData.setFieldNames(new String[] { "consignmentNumber",
			"paCode" });
			getReportSpec().addParameterMetaData(parameterMetaData);
			getReportSpec().addParameter(consignmentFilterVO);
			ReportMetaData reportMetaData = new ReportMetaData();
			reportMetaData.setColumnNames(new String[] { "ORGEXGOFC",
			"DSTEXGOFC", "MALCTGCOD","MALSUBCLS", "YER","DSN",
			"RSN", "HSN","REGIND", "WGT","ULDNUM"});
			reportMetaData.setFieldNames(new String[] { "originExchangeOffice",
			"destinationExchangeOffice", "mailCategoryCode","mailSubclass","year","dsn",
			"receptacleSerialNumber","highestNumberedReceptacle",
			"registeredOrInsuredIndicator","statedWeight","uldNumber"});
			getReportSpec().setReportMetaData(reportMetaData);
			getReportSpec().setData(consignmentVOs);*/
			getReportSpec().setPreview(true);
			getReportSpec().setProductCode(PRODUCTCODE);
			getReportSpec().setSubProductCode(SUBPRODUCTCODE);
			getReportSpec().setResourceBundle("consignmentResources");
			///if(MailConstantsVO.CONSIGNMENT_TYPE_CN41.equals(consignmentForm.getType())){  
			getReportSpec().setAction(ACTION);
				if(maintainConsignmentModel.getConsignment().getReportType()!=null && "Y".equals(maintainConsignmentModel.getConsignment().getReportType())){
					getReportSpec().setReportId("RPRMTK092");
					getReportSpec().setAction(ACTION_TK);  
				}
			///}else{
				//getReportSpec().setAction(ACTION);   
			//}
			//Added for ICRD_212235 starts
			if("AV7".equals(consignmentFilterVO.getConType())){  
				getReportSpec().setAction(ACTION_AV7);
			}
			//Added for ICRD_212235 ends
			getReportSpec().addFilterValue(consignmentFilterVO);
			//log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
				//log.log(Log.FINE,"\n\n\n----------reportMetaData----->"+reportMetaData);
			/*try {*/
				generateReport(actionContext);
			/*} catch (Exception e) {
//printStackTrrace()();
			}*/

			/*log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
			actionContext.target = getTargetPage();
			log.log(Log.FINE, "\n\n\n----------report----->",
					actionContext.target);*/
				}



	}







