/*
 * ArrivalPrintCommand.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival.report;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class ArrivalPrintCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTOPR081";
	private Log log = LogFactory.getLogger("Mailbag Arrival");	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateImportManifestReport";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		MailArrivalForm mailArrivalForm = 
    		(MailArrivalForm)invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
			
		LogonAttributes logonAttributes  =  getApplicationSession().getLogonVO();	
		
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		/*
		MailArrivalFilterVO filterVO = new MailArrivalFilterVO();		
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setPou(logonAttributes.getAirportCode());
		filterVO.setCarrierCode(mailArrivalForm.getFlightCarrierCode());
		filterVO.setFlightNumber(mailArrivalForm.getFlightNumber());
		filterVO.setMailStatus(mailArrivalForm.getPrintType());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		filterVO.setFlightDate(date.setDate(mailArrivalForm.getArrivalDate()));
		filterVO.setCarrierId(mailArrivalForm.getCarrierId());
		filterVO.setFlightSequenceNumber(mailArrivalForm.getFlightSequenceNumber());
		filterVO.setLegSerialNumber(mailArrivalForm.getLegSerialNumber());*/
		
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setPou(logonAttributes.getAirportCode());
		operationalFlightVO.setCarrierCode(mailArrivalForm.getFlightCarrierCode());
		operationalFlightVO.setFlightNumber(mailArrivalForm.getFlightNumber());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		operationalFlightVO.setFlightDate(date.setDate(mailArrivalForm.getArrivalDate()));
		operationalFlightVO.setCarrierId(mailArrivalForm.getCarrierId());
		operationalFlightVO.setFlightSequenceNumber(mailArrivalForm.getFlightSequenceNumber());
		operationalFlightVO.setLegSerialNumber(mailArrivalForm.getLegSerialNumber());
		operationalFlightVO.setFlightRoute(mailArrivalSession.getFlightValidationVO().getFlightRoute());
		operationalFlightVO.setPol(mailArrivalForm.getArrivalPort());
		log.log(Log.FINE, "****filterVO*****", operationalFlightVO);
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(operationalFlightVO);
		reportSpec.setResourceBundle("mailArrivalResources");
		reportSpec.setAction(ACTION);
			
			generateReport();
			invocationContext.target = getTargetPage();
		}

	}

