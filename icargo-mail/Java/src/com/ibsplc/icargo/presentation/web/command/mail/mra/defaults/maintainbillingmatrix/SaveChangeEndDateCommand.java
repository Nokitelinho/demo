/*
 * InactivateCommand.java Created on Jul 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyBlgLineSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author 204569
 * 
 */
public class SaveChangeEndDateCommand extends BaseCommand {

	private static final Log LOG = LogFactory.getLogger("MRA:DEFAULTS");

	private static final String CLASS_NAME = "SaveChangeEndDateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String SCREEN_SUCCESS = "screenload_success";
	private static final String SCREEN_ERROR = "screenload_error";
	private static final String PAST_DATE_ERROR = "mailtracking.mra.defaults.maintainbillingmatrix.pastdate";
	private static final String VALID_ENDDATE_HIGHER = "mailtracking.mra.defaults.maintainbillingmatrix.validenddategreater";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyblgline";
	private static final String DUP_BILLINGLINE = "mailtracking.mra.defaults.duplicatebillinglineChaneDate";
	private static final String DUPLICATE_EXISTS = "duplicate_exists";
	private static final String PARSE_PATTERN = "dd-MMM-yyyy";
	private static final String RATE_LINE_BACK_DATE_REQUIRED = "mailtracking.mra.ratelinebackdaterequired";
	private static final String EMPTY_DATE_ERROR = "mailtracking.mra.defaults.maintainbillingmatrix.emptyDate";
	private static final String   UNSAVED_BLG_LINE= "mailtracking.mra.defaults.maintainbillingmatrix.savebillinglineforchangeDate";

	/**
	 * *
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		LOG.entering(CLASS_NAME, "execute");
		String systemParamcode="Y";
		BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(MODULE_NAME, SCREENID);
		CopyBlgLineSession copyBlgLineSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		Collection<BillingLineVO> selectedBlgLineVOs = (ArrayList<BillingLineVO>) copyBlgLineSession
				.getSelectedBlgLines();
		Collection<BillingLineVO> billingLineVos = new ArrayList<>();
		billingLineVos.add(session.getBillingLineDetails().get(0));
		String changeEndDate = form.getValidFrom();
		if (!changeEndDate.isEmpty()) {

			LOG.log(Log.INFO, "ChangeEnddate getSelectedIndexes", changeEndDate, form.getSelectedIndex());
			Collection<String> systemParameterCodes = new ArrayList<>();
			systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
		
				Map<String, String> systemParamcodeCheck = findSystemParameterByCodes(systemParameterCodes);
				if(systemParamcodeCheck!=null){
				systemParamcode=systemParamcodeCheck.get(RATE_LINE_BACK_DATE_REQUIRED);
			}

				LOG.log(Log.INFO, "systemParamcode", systemParamcode);
			String sysCurrentDate = new SimpleDateFormat(PARSE_PATTERN).format(new Date());
			LocalDate endDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(changeEndDate);
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(sysCurrentDate);
			if (systemParamcode.equalsIgnoreCase("N") && endDate.before(currentDate)) {
				LOG.log(Log.INFO, "Formatted Date is befor system date sysDate:>" + currentDate
						+ "  formattedchangeEndDate:>" + endDate);
				invocationContext.addError(new ErrorVO(PAST_DATE_ERROR));
				invocationContext.target = SCREEN_ERROR;
				return;
			} else {
				for (BillingLineVO billingLineVo : selectedBlgLineVOs) {
					LocalDate startDate = billingLineVo.getValidityStartDate();
					if (endDate.before(startDate)) {
						LOG.log(Log.INFO, "Valid end date should be greater than valid from date");
						invocationContext.addError(new ErrorVO(VALID_ENDDATE_HIGHER));
						invocationContext.target = SCREEN_ERROR;
						return;
					}
					if("I".equals(billingLineVo.getOperationFlag()) || "U".equals(billingLineVo.getOperationFlag())){
						invocationContext.addError(new ErrorVO(UNSAVED_BLG_LINE));
						invocationContext.target = SCREEN_ERROR;
						return;
					}
				}
			}
			try {
				new MailTrackingMRADelegate().changeEnddate(selectedBlgLineVOs, changeEndDate);
				form.setFormStatusFlag("SAVE");
				invocationContext.target = SCREEN_SUCCESS;
			} 
			catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> errors ;
				String message = businessDelegateException.getMessageVO().getDetailsMessage();
				if(!message.isEmpty()){
				Object[] splited = message.split(":");
				LOG.log(Log.INFO, "before handleDelegateException");
				errors = handleDelegateException(businessDelegateException);
				LOG.log(Log.INFO, "after handleDelegateException");
				if (errors != null && errors.size() > 0) {
					Object[] splitedBill = { splited[2] };
					for (ErrorVO error : errors) {
						error.getErrorDescription();
						LOG.log(Log.INFO, "errorcode", error.getErrorCode());
						String[] errorCodeSplit = error.getErrorCode().split("_");
						if (("mailtracking.mra.defaults.duplicatebillingline").equals(errorCodeSplit[0])) {

							invocationContext.addError(new ErrorVO(DUP_BILLINGLINE, splitedBill));
						}
						invocationContext.target = DUPLICATE_EXISTS;
						invocationContext.target = SCREEN_ERROR;
					}}
				
					
				}
			
				invocationContext.target = SCREEN_ERROR;
			}

		}
		else{
			invocationContext.addError(new ErrorVO(EMPTY_DATE_ERROR));
			invocationContext.target = SCREEN_ERROR;
			return;
		}

		invocationContext.target = SCREEN_ERROR;

	}

	private Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes) {
		LOG.entering("UploadMailDetailsCommand", "findSystemParameterByCodes");
		Map<String, String> results = null;
		try {
			results = new SharedDefaultsDelegate().findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		LOG.exiting("UploadMailDetailsCommand", "findSystemParameterByCodes");
		return results;

	}
}
