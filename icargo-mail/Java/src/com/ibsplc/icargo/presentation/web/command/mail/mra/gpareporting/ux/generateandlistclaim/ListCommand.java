/*
 * ListCommand.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.generateandlistclaim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.GenerateandListClaimSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.GenerateandListClaimForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand extends BaseCommand {

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.generateandlistclaim";
	private static final String TARGET = "list_success";
	private Log log = LogFactory.getLogger("Mail MRA of List invoic Screen ");
	private static final String NO_RESULT = "mail.mra.gpareporting.ux.generateandlistclaim.msg.err.noresultsfound";
	private static final String LIST_FAILURE = "list_failure";
	private static final String ERROR_INVALID_DATE_RANGE = "mail.mra.gpareporting.ux.generateandlistclaim.msg.err.invalidDateRange";
	private static final String ERROR_INVALID_PACODE = "mail.mra.gpareporting.ux.generateandlistclaim.msg.err.invalidPacode";
	private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE,
				"\n\n in the list command of Generate Claim Screen----------> \n\n");
		GenerateandListClaimForm generateandListClaimForm = (GenerateandListClaimForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		GenerateandListClaimSession listclaimsession = getScreenSession(
				MODULE_NAME, SCREENID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<ClaimDetailsVO> claimDetailsVOs = null;
		InvoicFilterVO invoicFilterVO = null;
		if (listclaimsession.getFilterParamValues() != null) {
			invoicFilterVO = listclaimsession.getFilterParamValues();
		} else {
			invoicFilterVO = new InvoicFilterVO();
		}

		invoicFilterVO.setCmpcod(companyCode);
		if (generateandListClaimForm.getFromDate() != null
				&& generateandListClaimForm.getFromDate().length() > 0) {
			invoicFilterVO.setFromDate(generateandListClaimForm.getFromDate());
		}

		if (generateandListClaimForm.getToDate() != null
				&& generateandListClaimForm.getToDate().length() > 0) {
			invoicFilterVO.setToDate(generateandListClaimForm.getToDate());
		}
		if (generateandListClaimForm.getPaCode() != null
				&& generateandListClaimForm.getPaCode().trim().length() > 0) {
			invoicFilterVO.setGpaCode(generateandListClaimForm.getPaCode());
		}

		errors = validateuspsPacode(invoicFilterVO, errors);

		errors = validatefrmtoDateRange(invoicFilterVO, errors);
		if (errors != null && errors.size() > 0) {

			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		listclaimsession.setFilterParamValues(invoicFilterVO);

		int displaypage = 1;
		if (invoicFilterVO.getTotalRecords() == 0) {
			invoicFilterVO.setTotalRecords(-1);
		}
		if (generateandListClaimForm.getDefaultPageSize() != null
				&& generateandListClaimForm.getDefaultPageSize().trim()
						.length() > 0) {
			invoicFilterVO.setDefaultPageSize(Integer
					.parseInt(generateandListClaimForm.getDefaultPageSize()));
		}
		if (generateandListClaimForm.getDisplayPage() != null
				&& generateandListClaimForm.getDisplayPage().trim().length() > 0) {
			displaypage = Integer.parseInt(generateandListClaimForm
					.getDisplayPage());
		}

		claimDetailsVOs = listGenerateClaimDetails(invoicFilterVO, displaypage);
		if (claimDetailsVOs == null || claimDetailsVOs.size() == 0) {
			log.log(Log.SEVERE, "\n\n *******no record found********** \n\n");
			ErrorVO error = new ErrorVO(NO_RESULT);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		} else {
			generateandListClaimForm.setActionFlag("SHOWLIST");
			listclaimsession.setTotalRecords(claimDetailsVOs.size());
			listclaimsession.setListclaimbulkvos(claimDetailsVOs);
		}

	}

	private Page<ClaimDetailsVO> listGenerateClaimDetails(
			InvoicFilterVO invoicFilterVO, int pageNumber) {
		Page<ClaimDetailsVO> listClaimDetailsVOs = null;
		log.log(Log.INFO,
				"\n\n ******* Inside listGenerateClaimDetails********** \n\n");
		try {

			listClaimDetailsVOs = new MailTrackingMRADelegate()
					.listGenerateClaimDetails(invoicFilterVO, pageNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return listClaimDetailsVOs;
	}

	private Collection<ErrorVO> validatefrmtoDateRange(
			InvoicFilterVO invoicFilterVO, Collection<ErrorVO> errors) {
		Collection<USPSPostalCalendarVO> validdateRange = new ArrayList<USPSPostalCalendarVO>();
		try {
			validdateRange = new MailTrackingMRADelegate()
					.validateFrmToDateRange(invoicFilterVO);
			if (validdateRange != null && validdateRange.size() > 0) {
				log.log(Log.INFO,
						"\n\n ******* ValidatefrmtoDateRange is True********** \n\n");
			} else {
				ErrorVO error = new ErrorVO(ERROR_INVALID_DATE_RANGE);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return errors;

	}

	private Collection<ErrorVO> validateuspsPacode(InvoicFilterVO invoicFilterVO, Collection<ErrorVO> errors){
		String paCode_int = "";
		String paCode_dom = "";
		Collection<String> systemParameters = new ArrayList<String>();
		Map<String, String> systemParameterMap=new HashMap<String, String>();
		systemParameters.add(USPS_INTERNATIONAL_PA);
		systemParameters.add(USPS_DOMESTIC_PA);
		try {
		systemParameterMap= new MailTrackingMRADelegate().validateuspsPacode(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			paCode_int = systemParameterMap.get(USPS_INTERNATIONAL_PA);
			paCode_dom= systemParameterMap.get(USPS_DOMESTIC_PA);
		}
		
		if (paCode_int.equals(invoicFilterVO.getGpaCode())||paCode_dom.equals(invoicFilterVO.getGpaCode())) {
			log.log(Log.INFO,
					"\n\n ******* Valid International PACODE ********** \n\n");
		} else {
			ErrorVO error = new ErrorVO(ERROR_INVALID_PACODE);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return errors;
	}



}