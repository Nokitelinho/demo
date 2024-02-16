/*
 * GenerateReportCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailstatus.report;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailStatusForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class GenerateReportCommand extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");	

	/**
	 * TARGET
	 */
	private static final String REPORT_ID = "RPTOPS028";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateMailStatusReport";
	private static final String BUNDLE = "mailStatusResources";
	private static final String REPORT_ID_MAIL_ACCEPTED_NOT_UPLIFTED = "04MTK014";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("GenerateReportMailStatusCommand","execute");    	  
		MailStatusForm mailStatusForm = (MailStatusForm)invocationContext.screenModel;  	
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailStatusFilterVO mailStatusFilterVO = new MailStatusFilterVO();
		mailStatusFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailStatusFilterVO.setCarrierCode(mailStatusForm.getCarrierCode());
		mailStatusFilterVO.setCurrentStatus(mailStatusForm.getCurrentStatus());
		mailStatusFilterVO.setFlightCarrierCode(mailStatusForm.getFlightCarrierCode());
		mailStatusFilterVO.setFlightNumber(mailStatusForm.getFlightNumber());
		mailStatusFilterVO.setPacode(mailStatusForm.getPaCode());
		mailStatusFilterVO.setPol(mailStatusForm.getPol());
		mailStatusFilterVO.setPou(mailStatusForm.getPou());
		if(mailStatusForm.getCarrierIdr()!=null && mailStatusForm.getCarrierIdr().length()>0){
			mailStatusFilterVO.setCarrierid(Integer.parseInt(mailStatusForm.getCarrierIdr()));
		}
		if(mailStatusForm.getFlightCarrierIdr()!=null&& mailStatusForm.getFlightCarrierIdr().length()>0){
			mailStatusFilterVO.setFlightCarrierid(Integer.parseInt(mailStatusForm.getFlightCarrierIdr()));
		}else{
			int i=0;
			mailStatusFilterVO.setFlightCarrierid(i);
		}
		mailStatusFilterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailStatusForm.getFromDate()));
		mailStatusFilterVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailStatusForm.getToDate()));
		     
		ReportSpec reportSpec = getReportSpec();				
		if(MailConstantsVO.MAIL_ACCEPTED_NOT_UPLIFTED.equals(mailStatusForm.getCurrentStatus())){
			reportSpec.setReportId(REPORT_ID_MAIL_ACCEPTED_NOT_UPLIFTED);			
		} else{
		reportSpec.setReportId(REPORT_ID);
		}
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(mailStatusFilterVO);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setAction(ACTION);
		
		generateReport();
		invocationContext.target = getTargetPage();
	}

}
