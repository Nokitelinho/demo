/*
 * ListULDAgreementCommand.java.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 * 
 */
public class ListULDAgreementCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";

	private static final String BLANK = "";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ULD_MANAGEMENT");
		log.entering("LIST COMMAND", "-------uldmnagement");
		MaintainULDAgreementForm form = (MaintainULDAgreementForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//Added by A-8445
		ULDAgreementFilterVO filterVO = new ULDAgreementFilterVO();
		Page<ULDAgreementDetailsVO> pageULDAgreementDetailsVO = null;
		ULDAgreementVO agreementVO = null;
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		if (form.getAgreementNumber() == null || form.getAgreementNumber().trim().length() == 0) {
			ErrorVO errorVO = new ErrorVO("uld.defaults.enteragreementnumber");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			//Added by A-8445
			filterVO.setCompanyCode(logonAttributes.getCompanyCode());
			filterVO.setAgreementNumber(form.getAgreementNumber().toUpperCase());
			if(form.getUldTypeFilter()!=null){
				filterVO.setUldTypeCodeFilter(form.getUldTypeFilter());
			}
			if(MaintainULDAgreementForm.PAGINATION_MODE_FROM_LIST.equals(form.getNavigationMode())
					||form.getNavigationMode() == null){
				
				filterVO.setTotalRecordsCount(-1);
				filterVO.setPageNumber(1); 
				
			}else if(MaintainULDAgreementForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(form.getNavigationMode()))
			{				
				filterVO.setTotalRecordsCount(session.getTotalRecordsCount());
				if(form.getUldTypeFilter()!=null && session.getTotalRecordsCount()<25){
					filterVO.setPageNumber(1);
				} else {
					filterVO.setPageNumber(Integer.parseInt(form.getDisplayPageNumStr()));
				}
			}
			//Commented by A-8445
			agreementVO = delegate.findULDAgreementDetails(companyCode, form
					.getAgreementNumber().toUpperCase());
			
			pageULDAgreementDetailsVO = delegate.findULDAgreementDetailsPagination(companyCode, form
					.getAgreementNumber().toUpperCase(),filterVO);	
			
			if(agreementVO==null)
			{	
				ErrorVO errorVO = new ErrorVO("uld.defaults.entervalidagreementnumber");
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_FAILURE;
				return;
			}
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			error = handleDelegateException(exception);
		}
		log.log(Log.FINE, "agreementVo" + agreementVO);
		if (agreementVO == null) {
			form.setAgreementDate(BLANK);
			form.setPartyCode(BLANK);
			form.setPartyName(BLANK);
			form.setFromPartyCode(BLANK);
			form.setFromPartyName(BLANK);
			form.setFreeLoanPeriod(0);
			form.setDemurrageRate(0.0);
			form.setTaxes(0.0);
			form.setFromDate(BLANK);
			form.setToDate(BLANK);
			form.setRemarks(BLANK);
			form.setListStatus("Y");
			form.setLastPageNumStr("0");
			form.setDisplayPageNumStr("1");
			form.setUldTypeFilter(BLANK);
			
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		if (agreementVO != null) {
			form.setListStatus("N");
		}
		session.setUldAgreementDetails(agreementVO);
		session.setUldAgreementPageDetails(pageULDAgreementDetailsVO);
		if(pageULDAgreementDetailsVO!=null){
			session.setTotalRecordsCount(pageULDAgreementDetailsVO.getTotalRecordCount());
		}
		form.setOnload("valid");
		form.setHasListed("Y");
		form.setAgreementNumber(agreementVO.getAgreementNumber());
		form.setAgreementStatus(agreementVO.getAgreementStatus());
		form.setPartyCode(agreementVO.getPartyCode());
		form.setPartyType(agreementVO.getPartyType());
		form.setCurrencyCode(agreementVO.getCurrency());
		form.setDemurrageFrequency(agreementVO.getDemurrageFrequency());
		form.setDemurrageRate(agreementVO.getDemurrageRate());
		form.setFreeLoanPeriod(agreementVO.getFreeLoanPeriod());
		form.setPartyName(agreementVO.getPartyName());
		//added as part of ICRD-232684 by A-4393 ends 
		form.setFromPartyCode(agreementVO.getFromPartyCode());
		form.setFromPartyType(agreementVO.getFromPartyType());
		form.setFromPartyName(agreementVO.getFromPartyName());
		//added as part of ICRD-232684 by A-4393 ends 
		form.setRemarks(agreementVO.getRemark());
		if (agreementVO.getAgreementDate() != null) {
			form.setAgreementDate(agreementVO.getAgreementDate()
					.toDisplayDateOnlyFormat());
		}
		if (agreementVO.getAgreementFromDate() != null) {
			form.setFromDate(agreementVO.getAgreementFromDate()
					.toDisplayDateOnlyFormat());
		}
		if (agreementVO.getAgreementToDate() != null) {
			form.setToDate(agreementVO.getAgreementToDate()
					.toDisplayDateOnlyFormat());
		}
		form.setTaxes(agreementVO.getTax());
		form.setTransactionType(agreementVO.getTxnType());
		log.log(Log.INFO, "form.getCloseStatus()-------------------"+form.getCloseStatus());
		
		log.log(Log.INFO, "form.getIsDataSaved()-------------------"+form.getIsDataSaved());
		if(ULDAgreementVO.FLAG_YES.equals(form.getCloseStatus()) && ULDAgreementVO.FLAG_YES.equals(form.getIsDataSaved())){
			ErrorVO saveSuccess = new ErrorVO("uld.defaults.maintainuldagreement.savedsuccessfully");
			saveSuccess.setErrorDisplayType(ErrorDisplayType.STATUS);
		    errors = new ArrayList<ErrorVO>();
		    errors.add(saveSuccess);
		    invocationContext.addAllError(errors);			
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

}
