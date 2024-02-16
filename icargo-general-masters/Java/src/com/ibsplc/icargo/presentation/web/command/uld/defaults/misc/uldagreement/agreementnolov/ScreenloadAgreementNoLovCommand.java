/*
 * ScreenloadAgreementNoLovCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.agreementnolov;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AgreementNoLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class ScreenloadAgreementNoLovCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("ScreenLoad AgreementLOV");
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
		AgreementNoLovForm agreementNoLovForm = (AgreementNoLovForm)invocationContext.screenModel;
		//added by a-3045 for CR QF1154 starts
		ULDAgreementFilterVO uldAgreementFilterVO = new ULDAgreementFilterVO();
		uldAgreementFilterVO.setCompanyCode(compCode);
		if(agreementNoLovForm.getAgreementNo() != null 
				&& agreementNoLovForm.getAgreementNo().trim().length() > 0){
			uldAgreementFilterVO.setAgreementNumber(agreementNoLovForm.getAgreementNo());
		}
		if(agreementNoLovForm.getPartyCode() != null 
				&& agreementNoLovForm.getPartyCode().trim().length() > 0){
			uldAgreementFilterVO.setPartyCode(agreementNoLovForm.getPartyCode());
		}		
		if(agreementNoLovForm.getPartyName() != null 
				&& agreementNoLovForm.getPartyName().trim().length() > 0){
			uldAgreementFilterVO.setPartyName(agreementNoLovForm.getPartyName());
		}	
		uldAgreementFilterVO.setPageNumber(Integer.parseInt(agreementNoLovForm.getDisplayPage()));	
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
			Page<ULDAgreementVO> page =new ULDDefaultsDelegate().
								populateULDAgreementLOV(uldAgreementFilterVO);	
			//added by a-3045 for CR QF1154 ends
			agreementNoLovForm.setPageAgreementLov(page);
			log.log(Log.FINE, "displayPage ---> ", uldAgreementFilterVO.getPageNumber());
			log.log(Log.FINE, "page ---> ", page);

		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		invocationContext.target="screenload_success";
	}

}
