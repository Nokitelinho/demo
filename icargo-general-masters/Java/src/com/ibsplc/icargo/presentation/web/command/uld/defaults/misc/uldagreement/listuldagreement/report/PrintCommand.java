/*
 * PrintCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.listuldagreement.report;


import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;


/**
 * 
 * @author A-1936
 * 
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST018";

	private Log log = LogFactory.getLogger("List ULD Agreement");

	private static final String BLANK = "";

	private static final String SELECT = "ALL";
	
	private static final String PARTY_ALL="ALL";

	private static final String TRANSACTION_TYPE = "uld.defaults.TxnType";

	private static final String AGREEMENT_STATUS = "uld.defaults.agreementstatus";

	private static final String PARTY_TYPE = "uld.defaults.PartyType";

	 private static final String RESOURCE_BUNDLE_KEY = "listuldagreement";
		
	private static final String ACTION = "printlistuldagreement";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		//Commented by Manaf for INT ULD510
		//Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();

		ListULDAgreementForm listULDAgreementForm = (ListULDAgreementForm) invocationContext.screenModel;
		LocalDate agrDate = new LocalDate(logonAttributesVO.getAirportCode(),
				Location.ARP, false);
		LocalDate agrToDate = new LocalDate(logonAttributesVO.getAirportCode(),
				Location.ARP, false);
		LocalDate agrFrmDate = new LocalDate(
				logonAttributesVO.getAirportCode(), Location.ARP, false);

		ULDAgreementFilterVO uldAgreementFilterVO = new ULDAgreementFilterVO();

		uldAgreementFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		String agrNum = upper(listULDAgreementForm.getAgreementNumber());
		if(agrNum != null && agrNum.trim().length()>0){
			uldAgreementFilterVO.setAgreementNumber(agrNum);
		}else{	/*modified for ICRD-328247 wrt ICRD-276383*/
		if (listULDAgreementForm.getPartyType() != null
				&& !BLANK.equals(listULDAgreementForm.getPartyType())
				&& !SELECT.equals(listULDAgreementForm.getPartyType())) {
			uldAgreementFilterVO.setPartyType(upper(listULDAgreementForm
					.getPartyType()));
		}
		uldAgreementFilterVO.setPartyCode(upper(listULDAgreementForm
				.getPartyCode()));
			if (listULDAgreementForm.getFromPartyType() != null
					&& !BLANK.equals(listULDAgreementForm.getFromPartyType())
					&& !SELECT.equals(listULDAgreementForm.getFromPartyType())) {
				uldAgreementFilterVO.setFromPartyType(upper(listULDAgreementForm
						.getFromPartyType()));
			}
			uldAgreementFilterVO.setFromPartyCode(upper(listULDAgreementForm
					.getFromPartyCode()));
		if (listULDAgreementForm.getAgreementListDate() != null
				&& !BLANK.equals(listULDAgreementForm.getAgreementListDate())) {
			uldAgreementFilterVO.setAgreementDate(agrDate
					.setDate(listULDAgreementForm.getAgreementListDate()));
		}

		if (listULDAgreementForm.getTransactionType() != null
				&& !BLANK.equals(listULDAgreementForm.getTransactionType())
				&& !SELECT.equals(listULDAgreementForm.getTransactionType())) {
			uldAgreementFilterVO.setTxnType(upper(listULDAgreementForm
					.getTransactionType()));
		}
			if (listULDAgreementForm.getAgreementStatus() != null
					&& !BLANK.equals(listULDAgreementForm.getAgreementStatus())
					&& !SELECT.equals(listULDAgreementForm.getAgreementStatus())) {
				uldAgreementFilterVO.setAgreementStatus(upper(listULDAgreementForm
						.getAgreementStatus()));
			}
		if (listULDAgreementForm.getAgreementFromDate() != null
				&& !BLANK.equals(listULDAgreementForm.getAgreementFromDate())) {
			uldAgreementFilterVO.setAgreementFromDate(agrFrmDate
					.setDate(listULDAgreementForm.getAgreementFromDate()));
		}
		if (listULDAgreementForm.getAgreementToDate() != null
				&& !BLANK.equals(listULDAgreementForm.getAgreementToDate())) {
			uldAgreementFilterVO.setAgreementToDate(agrToDate
					.setDate(listULDAgreementForm.getAgreementToDate()));
		}
		}
		uldAgreementFilterVO.setPageNumber(Integer
				.parseInt(listULDAgreementForm.getDisplayPageNum()));
		log.log(Log.FINE, "\n\n\n----------Filter Vo sent to server----->",
				uldAgreementFilterVO);
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listULDAgreementForm.getProduct());
		reportSpec.setSubProductCode(listULDAgreementForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldAgreementFilterVO);
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

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(TRANSACTION_TYPE);
		oneTimeList.add(AGREEMENT_STATUS);
		oneTimeList.add(PARTY_TYPE);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			error = handleDelegateException(exception);
		}
		return hashMap;
	}

	private String upper(String input) {

		if (input != null) {
			return input.trim().toUpperCase();
		} else {
			return "";
		}
	}
}
