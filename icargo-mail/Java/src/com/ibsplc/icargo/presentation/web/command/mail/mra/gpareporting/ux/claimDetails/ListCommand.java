/*
 * ListCommand.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.claimDetails;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ClaimDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand  extends BaseCommand {
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.claimDetails";
	private static final String TARGET = "list_success";
	private Log log = LogFactory.getLogger("Mail MRA of claimDetails Screen ");
	private static final String NO_RESULT = "mail.mra.gpareporting.ux.claimDetails.msg.err.noresultsfound";
	private static final String LIST_FAILURE="list_failure";

	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE,
				"\n\n in the list command of claimDetails Screen----------> \n\n");
		ClaimDetailsForm claimDetailsForm= (ClaimDetailsForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ClaimDetailsSession claimDetailsSession =getScreenSession(MODULE_NAME,SCREENID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<ClaimDetailsVO> claimDetailsVOs = null;
		InvoicFilterVO invoicFilterVO=null;
		/*if(claimDetailsSession.getFilterParamValues() != null){
			invoicFilterVO = claimDetailsSession.getFilterParamValues();
		}else{*/          //Commented as part of ICRD-343865
			invoicFilterVO = new InvoicFilterVO();
		//}

		invoicFilterVO.setCmpcod(companyCode);
		if(claimDetailsForm.getFromDate()!=null && claimDetailsForm.getFromDate().length()>0){
			invoicFilterVO.setFromDate(claimDetailsForm.getFromDate());
		}

		if(claimDetailsForm.getToDate()!=null && claimDetailsForm.getToDate().length()>0){
			invoicFilterVO.setToDate(claimDetailsForm.getToDate());
		}
		if (claimDetailsForm.getPaCode() != null
				&& claimDetailsForm.getPaCode().trim().length() > 0) {
			invoicFilterVO.setGpaCode(claimDetailsForm.getPaCode());
		}else{
			invoicFilterVO.setGpaCode("");
		}
		if (claimDetailsForm.getMailId() != null
				&& claimDetailsForm.getMailId().trim().length() > 0) {
			invoicFilterVO.setMailbagId(claimDetailsForm.getMailId());
		}else{
			invoicFilterVO.setMailbagId("");
		}
		/*if (claimDetailsForm.getStatus() != null
				&& claimDetailsForm.getStatus().trim().length() > 0) {
			invoicFilterVO.setInvoicfilterStatus(claimDetailsForm.getStatus());
		}*/                                  // Commented as part of ICRD-343866
		if (claimDetailsForm.getClaimtype() != null
				&& claimDetailsForm.getClaimtype().trim().length() > 0) {
			invoicFilterVO.setClaimType(claimDetailsForm.getClaimtype());
		}
		if(claimDetailsForm.getClaimFileName()!=null && claimDetailsForm.getClaimFileName().trim().length()>0) {
			invoicFilterVO.setClaimFileName(claimDetailsForm.getClaimFileName());
		}
		
		claimDetailsSession.setFilterParamValues(invoicFilterVO);
		
		int displaypage=1;
		if(invoicFilterVO.getTotalRecords() == 0){
			invoicFilterVO.setTotalRecords(-1);
		}
		if(claimDetailsForm.getDefaultPageSize()!=null && claimDetailsForm.getDefaultPageSize().trim().length()>0){
			invoicFilterVO.setDefaultPageSize(Integer.parseInt(claimDetailsForm.getDefaultPageSize()));
		}
		if(claimDetailsForm.getDisplayPage()!=null&& claimDetailsForm.getDisplayPage().trim().length()>0){
			displaypage=Integer.parseInt(claimDetailsForm.getDisplayPage());
		}

		claimDetailsVOs=listClaimDetails(invoicFilterVO,displaypage);
		if (claimDetailsVOs == null
				|| claimDetailsVOs.size() == 0) {
			log.log(Log.SEVERE,
					"\n\n *******no record found********** \n\n");
			ErrorVO error = new ErrorVO(NO_RESULT);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);

			invocationContext.target = LIST_FAILURE;
			return;
		}else{
		
			
			claimDetailsForm.setActionFlag("SHOWLIST");
			claimDetailsSession.setTotalRecords(claimDetailsVOs.size());
			claimDetailsSession.setListclaimdtlsvos(claimDetailsVOs);
			}

		
	}

	private Page<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO,int pageNumber) {
		Page<ClaimDetailsVO> listClaimDetailsVOs = null;
		log.log(Log.INFO,
				"\n\n ******* Inside listClaimDetails********** \n\n");
		try {

			listClaimDetailsVOs = new MailTrackingMRADelegate().listClaimDetails(invoicFilterVO, pageNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return listClaimDetailsVOs;
	}


}