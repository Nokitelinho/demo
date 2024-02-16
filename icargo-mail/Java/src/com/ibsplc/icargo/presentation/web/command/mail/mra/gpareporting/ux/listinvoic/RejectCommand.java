package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.listinvoic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicLockVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class RejectCommand  extends BaseCommand {

	private Log log = LogFactory.getLogger("Mail Mra Reject List Invoic  ");

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.listinvoic";
	private static final String TARGET = "reject_success";
	private static final String TARGET_FAIL = "reject_failure";
	private static final String INVOIC_REJECTION_INITIATED = "Invoic Rejection from List Invoic Screen initiated";
	private static final String INVOIC_REJECTION = "MRAINVREJ";
	private static final String SYS_PARAM_INCPRCLEVEL = "mail.mra.gpareporting.invoicprocessinglevel";

	@Override
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("RejectCommand", "execute");
		ListInvoicForm listInvoicForm = (ListInvoicForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		String rejectrow = null;
		boolean isLocked = false;
		ListInvoicSession listinvoicsession =getScreenSession(MODULE_NAME,SCREENID);
		Collection<ErrorVO> errors = new ArrayList<>();
		   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		   InvoicFilterVO invoicFilterVO=new InvoicFilterVO();
		if (listInvoicForm.getSelectedValues() != null && listInvoicForm.getSelectedValues().length() > 0) {
			rejectrow = listInvoicForm.getSelectedValues();
		} else {
			rejectrow = listinvoicsession.getSelectedRecords();
		}
		    String companyCode = logonAttributes.getCompanyCode();
		int processCount = 0;
		int mailbagCount = 0;
		    listInvoicForm.setCompanycode(companyCode);
		    invoicFilterVO.setCmpcod(companyCode);
			if(listInvoicForm.getFromDate()!=null && listInvoicForm.getFromDate().length()>0){
				invoicFilterVO.setFromDate(listInvoicForm.getFromDate());
			}

			if(listInvoicForm.getToDate()!=null && listInvoicForm.getToDate().length()>0){
				invoicFilterVO.setToDate(listInvoicForm.getToDate());
			}
		if (listInvoicForm.getPaCode() != null && listInvoicForm.getPaCode().trim().length() > 0) {
				invoicFilterVO.setGpaCode(listInvoicForm.getPaCode());
			}
			if(listInvoicForm.getStatus()!=null && listInvoicForm.getStatus().length()>0){
				invoicFilterVO.setInvoicfilterStatus(listInvoicForm.getStatus());
			}
			if(listInvoicForm.getFileName() != null && listInvoicForm.getFileName().trim().length()>0){
				invoicFilterVO.setFileName(listInvoicForm.getFileName());
			}
			if(listInvoicForm.getInvoicRefId() != null && listInvoicForm.getInvoicRefId().trim().length()>0){
				invoicFilterVO.setInvoicRefId(listInvoicForm.getInvoicRefId());
			}

		    Page <InvoicVO> invoicVOs=listinvoicsession.getListinvoicvos();
		InvoicVO invoic = invoicVOs.get(Integer.parseInt(rejectrow));
		InvoicVO rejectInvoic = new InvoicVO();
		try {
			BeanHelper.copyProperties(rejectInvoic, invoic);
		} catch (SystemException systemException) {
			systemException.getMessage();
		}
		rejectInvoic.setRemarks(listInvoicForm.getRemarks());
		    MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
			if(rejectrow!=null && rejectrow.length()>0){
				rejectrecords.add(rejectInvoic);
			}
		listinvoicsession.setSelectedRecords(rejectrow);
		if (!listInvoicForm.isRejectFlag()) {

			ErrorVO displayErrorVO=null;
            if("NW".equals(rejectInvoic.getInvoicStatusCode())) {
           	  displayErrorVO = new ErrorVO("mailtracking.mra.defaults.listinvoic.confirmrejectcriteriafornew");
			displayErrorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
			errors.add(displayErrorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAIL;
			return;
            }
            else {
            displayErrorVO = new ErrorVO("mailtracking.mra.defaults.listinvoic.confirmrejectcriteria");
            displayErrorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
			  errors.add(displayErrorVO);
			  invocationContext.addAllError(errors);
			  invocationContext.target = TARGET_FAIL;
			  return;
            }
		} else if ("NW".equals(invoic.getInvoicStatusCode())) {
			try {
				delegate.updateInvoicReject(rejectrecords);
				listinvoicsession.setFilterParamValues(invoicFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessage();
					invocationContext.target = TARGET_FAIL;
				}
			listinvoicsession.setRejectInvoic("NW");

		  }else{
			  try {
					mailbagCount = delegate.checkForRejectionMailbags(companyCode, invoic);
				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessage();
					handleDelegateException(businessDelegateException);
				}
				if (mailbagCount >= 1) {
					ErrorVO error = null;
					error = new ErrorVO("mailtracking.mra.gpareporting.listinvoic.mailbagsprsent");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAIL;
					return;
				}

				try {
					processCount = delegate.checkForProcessCount(companyCode, rejectInvoic);
				} catch (BusinessDelegateException businessDelegateException) {
					businessDelegateException.getMessage();
					handleDelegateException(businessDelegateException);
				}
				if (processCount >= 1) {
					ErrorVO error = null;
					if ("I".equals(levelOfInvoicProcessing())) {
						error = new ErrorVO("mailtracking.mra.gpareporting.listinvoic.processcountexceeded");
					} else {
						error = new ErrorVO("mailtracking.mra.gpareporting.listinvoic.fileprocesscountexceeded");
					}
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					invocationContext.addAllError(errors);
					invocationContext.target = TARGET_FAIL;
					return;
				}
				Collection<LockVO> acquiredLocks = new ArrayList<>();
		        try {
		          acquiredLocks = delegate.generateINVOICProcessingLock(logonAttributes.getCompanyCode());
		          this.log.log(3, " Lock VOs acquiredLocks-=-=->" + acquiredLocks);
		        }catch (Exception exception){
		          isLocked = true;
		          errors = new ArrayList<>();
		          errors.add(new ErrorVO("mailtracking.mra.gpareporting.objectalreadylocked"));
		        }
		        if (errors != null && !errors.isEmpty()) {
		          invocationContext.target = TARGET_FAIL;
		          invocationContext.addAllError(errors);
		          return;
		        }
		        if (!isLocked){
					try {
						InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
						invoiceTransactionLogVO.setCompanyCode(companyCode);
						invoiceTransactionLogVO.setInvoiceType(INVOIC_REJECTION);
						invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				   		invoiceTransactionLogVO.setPeriodFrom( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				   		invoiceTransactionLogVO.setPeriodTo( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MailConstantsVO.INITIATED_STATUS);
				   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
						invoiceTransactionLogVO.setRemarks(INVOIC_REJECTION_INITIATED);
						invoiceTransactionLogVO.setSubSystem(MailConstantsVO.MAIL_SUBSYSTEM);
						invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
						try{
							invoiceTransactionLogVO = delegate.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
						}catch (BusinessDelegateException businessDelegateException) {
							businessDelegateException.getMessage();
							handleDelegateException(businessDelegateException);
						}
						rejectInvoic.setTxnCode(invoiceTransactionLogVO.getTransactionCode());
						rejectInvoic.setTxnSerialNum(invoiceTransactionLogVO.getSerialNumber());
						Collection<LockVO> savelocks = prepareLocksForSave(rejectInvoic);
						delegate.rejectProcessedInvoic(savelocks,true,rejectrecords);
						listinvoicsession.setFilterParamValues(invoicFilterVO);
					} catch (BusinessDelegateException businessDelegateException) {
						businessDelegateException.getMessage();
						invocationContext.target = TARGET_FAIL;
					}
					listInvoicForm.setRejectFlag(false);
		        }
		 }
		invocationContext.target = TARGET;
	}
	private String levelOfInvoicProcessing() {
		String level = "F";
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(SYS_PARAM_INCPRCLEVEL);
		Map<String, String> systemParameters = null;
		try {
			systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			level = "F";
		}
		if (systemParameters != null && systemParameters.size() > 0
				&& "I".equals(systemParameters.get(SYS_PARAM_INCPRCLEVEL))) {
			level = "I";
		}
		return level;
	}
	private Collection<LockVO> prepareLocksForSave(InvoicVO invoicVO){
	    LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection <LockVO>locks = new ArrayList<LockVO>();
	    InvoicLockVO lock = new InvoicLockVO();
	    lock.setForceLockEntity(InvoicLockVO.ACTION_INVOICLOCK);
	    lock.setAction(InvoicLockVO.ACTION_INVOICLOCK);
	    lock.setClientType(ClientType.WEB);
	    lock.setCompanyCode(logonAttributes.getCompanyCode());
	    lock.setDescription("GPA INVOIC LOCK ON" + invoicVO.getInvoicRefId());
	    lock.setRemarks("REMARKS " + invoicVO.getInvoicRefId());
	    lock.setScreenId("mail.mra.gpareporting.ux.listinvoic");
	    lock.setStationCode(logonAttributes.getStationCode());
	    locks.add(lock);
	    return locks;
	}

}