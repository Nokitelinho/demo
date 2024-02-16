/*
 * ClearULDAgreementCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
/**
 * 
 * @author A-2046
 *
 */
public class ClearULDAgreementCommand extends BaseCommand {
	private static final String BLANK_STRING = "";
	
	private static final String MODULE_NAME = "uld.defaults";

    private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";
    
    private static  final String SCREENLOAD_SUCCESS = "screenload_success";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	    public void execute(InvocationContext invocationContext)throws 
	    								CommandInvocationException{
		MaintainULDAgreementForm form = (MaintainULDAgreementForm)
    	invocationContext.screenModel;
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		form.setAgreementDate(BLANK_STRING);
		form.setAgreementNumber(BLANK_STRING);
		form.setFreeLoanPeriod(0);
		form.setDemurrageRate(0.0);
		form.setTaxes(0.0);
		form.setRemarks(BLANK_STRING);
		form.setTransactionType(BLANK_STRING);
		form.setFromDate(BLANK_STRING);
		form.setPartyCode(BLANK_STRING);
		form.setPartyName(BLANK_STRING);
		form.setFromPartyCode(BLANK_STRING);
		form.setFromPartyName(BLANK_STRING);
		form.setFromPartyType("A");
		form.setToDate(BLANK_STRING);
		form.setPartyType("A");
		form.setAgreementDate(BLANK_STRING);
		form.setOnload(BLANK_STRING);
		form.setDemurrageFrequency(BLANK_STRING);
		form.setAgreementStatus(BLANK_STRING);
		form.setUldTypeFilter(BLANK_STRING);
		form.setLastPageNumStr("0");
		form.setDisplayPageNumStr("1");
		
		AreaDelegate areaDelegate = new AreaDelegate();
    	String defCur = "";
		try {
			defCur = areaDelegate.defaultCurrencyForStation(logonAttributes
					.getCompanyCode(), logonAttributes.getStationCode());
		} catch (BusinessDelegateException e) {
			e.getMessage();
		}
		form.setCurrencyCode(defCur);
		ULDAgreementVO agreementVO = new ULDAgreementVO();
		agreementVO.setOperationFlag(OPERATION_FLAG_INSERT);
		session.setUldAgreementDetails(agreementVO);
		session.setAgreementNumbers(null);
		session.setUldAgreementVOs(null);
		session.setUldAgreementPageDetails(null);
		session.setTotalRecordsCount(0);
		
		invocationContext.target=SCREENLOAD_SUCCESS;
		
		
	}

}
