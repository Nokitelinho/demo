/*
 * ScreenLoadChgStatusCommand.java created on Mar 1, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class ScreenSaveChgStatusCommand extends BaseCommand {

	private static final Log LOG = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";

	private static final String SCREEN_SUCCESS = "popup_success";
	
	private static final String SCREEN_ERROR = "popup_error";
	
	private static final String VALUE_EMPTY = "--Select--";

	private static final String VALUE_EMPTY_ERROR = "mailtracking.mra.defaults.listbillingmatrix.err.statusempty";
	
	private static final String STATUS_TO_NEW = "mailtracking.mra.defaults.listbillingmatrix.err.statustonew";
	
	private static final String EXPIRED_TO_ACTIVE = "mailtracking.mra.defaults.listbillingmatrix.err.expiredtoactive";

	private static final String CHANGE_TO_BE_SAME_STATUS = "mailtracking.mra.defaults.listbillingmatrix.err.statustobesameerror";
	
	private static final String SCREENID_BILLINGLINE = "mailtracking.mra.defaults.viewbillingline";

	private static final String INFO_STATUS = "mailtracking.mra.defaults.listbillingmatrix.info.statuscheck";
	
	private static final String STATUS_TO_EXPIRED = "mailtracking.mra.defaults.listbillingmatrix.err.statustoExpired";

	private static final String STA_NEW = "N";
	
	private static final String STA_ACT = "A";
	
	private static final String STATUS_ACTIVE="Active";

	private static final String STA_INA = "I";
	
	private static final String STATUS_INACTIVE="Inactive";

	private static final String STA_CAN = "C";
	
	private static final String STATUS_CANCELLED="Cancelled";
	
	private static final String STA_EXP="E";
	
	private static final String STATUS_EXPIRED="Expired";
	
	private static final String CANCELLED_STATUS_CHANGE_ERROR="mailtracking.mra.defaults.listbillingmatrix.cancelstatuschange";
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo
	 * .framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		LOG.entering(CLASS_NAME, "execute");

		ListBillingMatrixForm listBillingMatrixForm = (ListBillingMatrixForm) invocationContext.screenModel;
		LOG.log(Log.INFO, "New getChangedStatus value---->>", listBillingMatrixForm.getStatus());
		String changedStatus = listBillingMatrixForm.getStatus();
		
		////Error Popup -Save Empty Status
		if (changedStatus.equalsIgnoreCase(VALUE_EMPTY)) {
			invocationContext.addError(new ErrorVO(VALUE_EMPTY_ERROR));
			invocationContext.target = SCREEN_ERROR;
			return;
		}
		ListBillingMatrixSession listBillingMatrixSession = getScreenSession(MODULE_NAME, SCREENID);
		ViewBillingLineSession billingLineSession = getScreenSession(MODULE_NAME, SCREENID_BILLINGLINE);

		BillingLineFilterVO billingLineFilterVO = billingLineSession.getBillingLineFilterVO();
		BillingMatrixFilterVO billingMatrix = listBillingMatrixSession.getBillingMatrixFilterVO();
		String selectedStatus = billingLineFilterVO.getBillingLineStatus();
		billingMatrix.getStatus();
		
		if(selectedStatus.equalsIgnoreCase(STA_CAN)){
			invocationContext.addError(new ErrorVO(CANCELLED_STATUS_CHANGE_ERROR));
			invocationContext.target = SCREEN_ERROR;
			return;
		}
		
		////Error Popup User shall not be able to change a billing matrix in Active, inactive/cancelled status to New status
		// “Billing matrix ID cannot be updated to New status” shall be shown
		if (changedStatus.equalsIgnoreCase(STA_NEW) && (selectedStatus.equalsIgnoreCase(STA_ACT)
				|| selectedStatus.equalsIgnoreCase(STA_INA) || selectedStatus.equalsIgnoreCase(STA_CAN)||selectedStatus.equalsIgnoreCase(STA_EXP))) {
			invocationContext.addError(new ErrorVO(STATUS_TO_NEW));
			invocationContext.target = SCREEN_ERROR;
			return;
		}
		
		if(selectedStatus.equalsIgnoreCase(STA_EXP) && changedStatus.equalsIgnoreCase(STA_ACT)){
			invocationContext.addError(new ErrorVO(EXPIRED_TO_ACTIVE));
			invocationContext.target = SCREEN_ERROR;
			return;
		}
		
		if (changedStatus.equalsIgnoreCase(STA_EXP)) {
			invocationContext.addError(new ErrorVO(STATUS_TO_EXPIRED));
			invocationContext.target = SCREEN_ERROR;
			return;
		}
		
		//Error Popup if trying to change Billing Matrix  with same status
		if(changedStatus.equalsIgnoreCase(selectedStatus)){
			Object[] statustobeChange= { statusConvertion(changedStatus) };
			invocationContext.addError(new ErrorVO(CHANGE_TO_BE_SAME_STATUS,statustobeChange));
			invocationContext.target = SCREEN_ERROR;
			return;
		}
		LOG.log(Log.INFO, "New selectedStatus value---->>", billingLineFilterVO.getBillingLineStatus());
		listBillingMatrixForm.setChangedStatus("Changed");
		
		//Showing Information popup after select Status
		if (listBillingMatrixForm.getChangedStatus().equalsIgnoreCase("Changed")) {
			ErrorVO displayMessageErrorVO = null;
			displayMessageErrorVO = new ErrorVO(INFO_STATUS);
			displayMessageErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			invocationContext.addError(displayMessageErrorVO);
			invocationContext.target =SCREEN_SUCCESS;
		}
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType("BM");
		invoiceTransactionLogVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks(billingLineFilterVO.getBillingMatrixId());
		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setTransactionTimeUTC(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setClearancePeriod("test");
		invoiceTransactionLogVO.setUser(logonAttributes.getUserId());

		try {
			invoiceTransactionLogVO=new MailTrackingMRADelegate()
					.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
		} catch (BusinessDelegateException ex) {
			LOG.log(Log.INFO, "Exception",ex);
		}
		changedStatus = new StringBuilder(changedStatus+"-"+invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();
		try {
			 
			new MailTrackingMRADelegate().changeBillingMatrixStatusUpdate(billingMatrix, changedStatus);
		} catch (BusinessDelegateException e) {

			LOG.log(Log.INFO, "Exception",e);
		}

	}
	
	private String statusConvertion(String status) {
		if(status.equalsIgnoreCase(STA_ACT)){
			return STATUS_ACTIVE;
		}
		else if (status.equalsIgnoreCase(STA_CAN)){
		return STATUS_CANCELLED;
		}
		else if (status.equalsIgnoreCase(STA_INA)){
			return STATUS_INACTIVE;
			}
		else if (status.equalsIgnoreCase(STA_EXP)){
			return STATUS_EXPIRED;
			}
		else{
			return status;
		}
	}
		

}
