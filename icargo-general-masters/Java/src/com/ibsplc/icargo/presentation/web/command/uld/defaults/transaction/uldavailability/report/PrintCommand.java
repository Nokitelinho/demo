/*
 * PrintCommand.java Created on Apr 01, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.uldavailability.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDAvailabilityForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3278
 * 
 */
public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST212";

	private Log log = LogFactory.getLogger("List ULD Transactions");

	private static final String BLANK = "";

	private static final String SELECT = "ALL";

	private static final String RESOURCE_BUNDLE_KEY = "uldAvailabilityResources";

	private static final String ACTION = "printuldtransactionReport";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
	private static final String AIRLINE = "A";

	private static final String AGENT = "G";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		
		ULDAvailabilityForm uldAvailabilityForm = (ULDAvailabilityForm) invocationContext.screenModel;
		
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();

		transactionFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		transactionFilterVO.setTransactionStationCode(upper(uldAvailabilityForm
				.getStationCode()));
		if (uldAvailabilityForm.getUldTypeCode() != null
				&& !BLANK.equals(uldAvailabilityForm.getUldTypeCode())) {
			transactionFilterVO.setUldTypeCode(uldAvailabilityForm.getUldTypeCode()
					.toUpperCase());
		} else {
			transactionFilterVO.setUldTypeCode("");
		}
		if (uldAvailabilityForm.getPartyType() != null
				&& !BLANK.equals(uldAvailabilityForm.getPartyType())
				&& !SELECT.equals(uldAvailabilityForm.getPartyType())) {
			transactionFilterVO.setPartyType(upper(uldAvailabilityForm
					.getPartyType()));
		} else {
			transactionFilterVO.setPartyType("");
		}
		if (uldAvailabilityForm.getPartyCode() != null
				&& !BLANK.equals(uldAvailabilityForm.getPartyCode())){
			transactionFilterVO.setToPartyCode(upper(uldAvailabilityForm
				.getPartyCode()));		
		} else {
			transactionFilterVO.setToPartyCode("");
		}
		//added by a-3045 for BUG ULD315 starts
		if("L".equals(uldAvailabilityForm.getPartyType())){
			
			transactionFilterVO.setPartyType("");
		}
		Collection<ErrorVO> errors = null;
		if (uldAvailabilityForm.getStationCode() != null
				&& uldAvailabilityForm.getStationCode().trim().length() > 0) {
			errors = validateAirportCodes(uldAvailabilityForm.getStationCode().toUpperCase(),
					logonAttributesVO);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "exception");
				invocationContext.addAllError(errors);
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}
		}
		if (uldAvailabilityForm.getUldTypeCode() != null
					&& uldAvailabilityForm.getUldTypeCode().trim().length() > 0) {
			errors = validateUldType(uldAvailabilityForm.getUldTypeCode().toUpperCase(),
					logonAttributesVO);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "exception");
				invocationContext.addAllError(errors);
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}
		}		
		AirlineDelegate airlineDelegate = new AirlineDelegate();	
		AirlineValidationVO validationVO = null;
		AgentDelegate agentDelegate = new AgentDelegate();		
		AgentVO agentVO = null;
		String partyCode = "";
		String companyCode = logonAttributesVO.getCompanyCode();
		if (AIRLINE.equals(uldAvailabilityForm.getPartyType())) {
			if (uldAvailabilityForm.getPartyCode() != null
					&& uldAvailabilityForm.getPartyCode().trim().length() > 0) {
				partyCode = uldAvailabilityForm.getPartyCode().toUpperCase();

				try {
					validationVO = airlineDelegate.validateAlphaCode(
							companyCode, partyCode);
				} catch (BusinessDelegateException exception) {
					errors = handleDelegateException(exception);
					invocationContext.addAllError(errors);
					invocationContext.target = PRINT_UNSUCCESSFUL;
					return;
				}
			}
		}
		if (AGENT.equals(uldAvailabilityForm.getPartyType())) {
			if (uldAvailabilityForm.getPartyCode() != null
					&& uldAvailabilityForm.getPartyCode().trim().length() > 0) {
				partyCode = uldAvailabilityForm.getPartyCode().toUpperCase();

				Collection<ErrorVO> error = new ArrayList<ErrorVO>();
				try {
					agentVO = agentDelegate.findAgentDetails(companyCode,
							partyCode);
				} catch (BusinessDelegateException exception) {
					log.log(Log.FINE, "*****in the exception");					
					error = handleDelegateException(exception);
				}
				if (agentVO == null) {
					errors = new ArrayList<ErrorVO>();
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.uldavailability.invalidagentcode");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = PRINT_UNSUCCESSFUL;
					return;
				}
			}
		}		
		log.log(Log.FINE, "\n\n\n----------Filter Vo sent to server----->",
				transactionFilterVO);
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(uldAvailabilityForm.getProduct());
		reportSpec.setSubProductCode(uldAvailabilityForm.getSubProduct());
		if (("GENERATE").equals(uldAvailabilityForm.getIsPreview())){
		reportSpec.setPreview(true);
		}
		else if (("PRINT").equals(uldAvailabilityForm.getIsPreview())){
			reportSpec.setPreview(false);	
		}
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(transactionFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);

		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {

			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("MRA_GPABILLING", "PrintCommand exit");
		invocationContext.target = getTargetPage();
	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */

	private String upper(String input) {

		if (input != null) {
			return input.trim().toUpperCase();
		} else {
			return "";
		}

	}
	
	//added by a-3045 for BUG ULD315 starts	
	/**
	 * @param station
	 * @param logonAttributes
	 * @return errors
	 */
	public Collection<ErrorVO> validateAirportCodes(String station,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> errors = null;

		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(logonAttributes.getCompanyCode(),station);

		} catch (BusinessDelegateException e) {			
			errors = handleDelegateException(e);
		}
		return errors;
	}

	/**
	 * 
	 * @param uldType
	 * @param logonAttributes
	 * @return errors
	 */
	public Collection<ErrorVO> validateUldType(String uldType,
			LogonAttributes logonAttributes) {
		Collection<ErrorVO> errors = null;
		ULDDelegate delegate = new ULDDelegate();
		Collection<String> uldTypes = new ArrayList<String>();
		uldTypes.add(uldType);
		try {
			delegate.validateULDTypeCodes(logonAttributes.getCompanyCode(),
					uldTypes);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
	}
	//added by a-3045 for BUG ULD315 ends
}
