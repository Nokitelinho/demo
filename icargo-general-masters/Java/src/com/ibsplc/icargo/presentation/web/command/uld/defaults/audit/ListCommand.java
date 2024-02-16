package com.ibsplc.icargo.presentation.web.command.uld.defaults.audit;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ULDAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD AUDIT");
	private static final String ACTION_SUCCESS="action_success";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListCommand", "execute");
		
		ULDAuditForm uldForm = (ULDAuditForm) invocationContext.screenModel;
		AuditEnquirySession auditEnquirySession = (AuditEnquirySession) getScreenSession(
				"shared.audit", "shared.audit.auditenquiry");
		auditEnquirySession.setAuditDetailsVOs(null);
	        
		LogonAttributes logon=getApplicationSession().getLogonVO();
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (uldForm.getTxnFromDate() == null
				|| uldForm.getTxnFromDate().trim().length() == 0) {
			ErrorVO error = new ErrorVO(
					"shared.audit.auditenquiry.msg.err.txnfrmdateerror");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (uldForm.getTxnToDate() == null
				|| uldForm.getTxnToDate().trim().length() == 0) {
			ErrorVO error = new ErrorVO(
					"shared.audit.auditenquiry.msg.err.txntodateerror");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (uldForm.getUldNumber() == null
				|| uldForm.getUldNumber().trim().length() == 0) {
			ErrorVO error = new ErrorVO(
					"uld.defaults.uldaudit.msg.err.uldlength");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_SUCCESS;
			return; 
		}
		try {
			new ULDDefaultsDelegate().validateULDFormat(logon.getCompanyCode(), uldForm.getUldNumber());
		} catch (BusinessDelegateException businessDelegateException) {
			 errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_SUCCESS;
			return;
		}
		RelocateULDVO filterVO=new RelocateULDVO();
		filterVO.setCompanyCode(logon.getCompanyCode());
		filterVO.setUldNumber(uldForm.getUldNumber().toUpperCase());
		LocalDate fromDate=new LocalDate(logon.getAirportCode(), Location.ARP, false);
		LocalDate toDate=new LocalDate(logon.getAirportCode(), Location.ARP, false);
		filterVO.setTxnFromDate(fromDate.setDate(uldForm.getTxnFromDate()));
		filterVO.setTxnToDate(toDate.setDate(uldForm.getTxnToDate()));
		Collection<AuditDetailsVO> auditDetailsVOs=null;
		try {
			auditDetailsVOs=new ULDDefaultsDelegate().findULDAuditEnquiryDetails(filterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			 errors = handleDelegateException(businessDelegateException);
		}
		if(auditDetailsVOs==null||auditDetailsVOs.size()==0){
			ErrorVO error = new ErrorVO(
			"uld.defaults.uldaudit.msg.err.uldtranscation");
	error.setErrorDisplayType(ErrorDisplayType.ERROR);
	errors.add(error);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = ACTION_SUCCESS;
			return;
		}
		auditEnquirySession.setAuditDetailsVOs(auditDetailsVOs);
		log.exiting("ListCommand", "execute");
		invocationContext.target= ACTION_SUCCESS;

	}

}
