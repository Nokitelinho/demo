/**
 * GenerateManifestCommand.java Created on March 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign.report;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class GenerateManifestCommand extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String REPORT_ID = "RPRMTK074";
	private static final String ACTION = "generateManifest";	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String PRODUCT_NAME = "mail";
	private static final String SUBPRODUCT_NAME = "operations";
	private static final String RESOURCE_BUNDLE = "nationalassignMailbagResources";
	private static final String OUTBOUND = "O";
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	/**
	 * @param  invocationcontext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		log.entering("GenerateManifestCommand", "execute");
		AssignMailBagSession assignMailBagSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationcontext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		assignMailBagForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		FlightValidationVO flightValidationVO = assignMailBagSession.getFlightValidationVO();	
		Map<String, Collection<OneTimeVO>> oneTimMap = assignMailBagSession.getOneTimeVOs();
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setPol(logonAttributes.getAirportCode());		
		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());		 
		operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
		operationalFlightVO.setFlightDate(flightValidationVO.getStd());
		operationalFlightVO.setDirection(OUTBOUND);
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setPou(flightValidationVO.getLegDestination());
		operationalFlightVO.setTailNumber(flightValidationVO.getTailNumber()); //added by a-5133 as part of CR ICRD-22342
		getReportSpec().addExtraInfo(oneTimMap.get(CATEGORY));  
/**
 * Added by A-4816 for ICRD-19139 : 
 */
		getReportSpec().addExtraInfo(flightValidationVO.getFlightRoute());
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(PRODUCT_NAME);
		getReportSpec().setSubProductCode(SUBPRODUCT_NAME);
		getReportSpec().setHttpServerBase(invocationcontext.httpServerBase);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE);
		getReportSpec().addFilterValue(operationalFlightVO);		
		getReportSpec().addParameter(operationalFlightVO);
		getReportSpec().setPreview(true);
		getReportSpec().setAction(ACTION);
		generateReport();
		invocationcontext.target = getTargetPage();

	}

}
