/*
 * OKCommand.java Created on Mar 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.copybillingline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyBlgLineSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyBlgLineForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class OKCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS COPYBLGLINE");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyblgline";

	private static final String OK_SUCCESS = "ok_success";

	private static final String OK_FAILURE = "ok_failure";

	private static final String OPEN = "open";

	private static final String STATUS_NEW = "N";

	private static final String CAPTURE = "capture";

	private static final String SCREEN_ID_MAINTAINBLGMTX = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String BLGMTX_MANDATORY = "mailtracking.mra.defaults.copybillingline.err.blgmtxidmadatory";

	private static final String DATES_MANDOTORY = "mailtracking.mra.defaults.copybillingline.err.datesmandatory";

	private static final String FROMDATE_GREATER = "mailtracking.mra.defaults.copybillingline.err.fromdateisgreater";

	private static final String BLANK = "";

	private static final String FROMDATE_DATE_BEFORE_CUREENTDATE = "mailtracking.mra.defaults.fromdatebeforecurrentdate";
	private static final String BLGMTXS_ALREADY_EXISTS="mailtracking.mra.defaults.billingmatrixalreadyexists";
	private static final String BLGLINE_STATUS_EXPIRED="E";
	private static final String BLGLINE_STATUS_NEW="N";

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CopyBlgLineForm copyBlgLineForm = (CopyBlgLineForm) invocationContext.screenModel;
		CopyBlgLineSession copyBlgLineSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		MaintainBillingMatrixSession maintainBillingMatrixSession = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREEN_ID_MAINTAINBLGMTX);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ArrayList<BillingLineVO> selectedBlgLineVOs = (ArrayList<BillingLineVO>) copyBlgLineSession
				.getSelectedBlgLines();
		Page<BillingLineVO> pageBillingLines = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		BillingMatrixVO billingMatrixVO = null;
		log.log(Log.INFO, "\n\nScreen Mode Flag-->", copyBlgLineForm.getScreenMode());
		log.log(Log.INFO, "\n\nBilling Matrix Id-->", copyBlgLineForm.getBlgMatrixId());
		log.log(Log.INFO, "\n\nValid From-->", copyBlgLineForm.getValidFrom());
		log.log(Log.INFO, "\n\nValid To-->", copyBlgLineForm.getValidTo());
		BillingLineVO selectedBillingLineVO = selectedBlgLineVOs.get(0);
		if (selectedBillingLineVO != null) {
			if (copyBlgLineForm.getBlgMatrixId().equalsIgnoreCase(
					selectedBillingLineVO.getBillingMatrixId())) {

			//copyBlgLineForm.setCloseFlag("Y");
				//log.log(Log.INFO, "CloseFlag"+copyBlgLineForm.getCloseFlag());
				ErrorVO error = new ErrorVO(BLGMTXS_ALREADY_EXISTS);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(error);
				log
						.log(Log.INFO,
								"The selected Billing Matrix and new Billing Matrix are same");
				/*if(STATUS_CANCELLED.equalsIgnoreCase(selectedBillingLineVO.getBillingLineStatus())){
					ErrorVO err = new ErrorVO(BLGMTXS_CANCELLED_ERR);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(err);
				}
				if(STATUS_ACTIVE.equalsIgnoreCase(selectedBillingLineVO.getBillingLineStatus())){
					ErrorVO err = new ErrorVO(BLGMTXS_SAME_ERR);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(err);

				}*/
				invocationContext.target = OK_FAILURE;
				return;
			}
		}
		if (CAPTURE.equals(copyBlgLineForm.getScreenMode())) {
			log.log(Log.INFO, "Inside Cpature mode");
			billingMatrixVO = new BillingMatrixVO();
			if (BLANK.equals(copyBlgLineForm.getBlgMatrixId())) {
				ErrorVO errVO = new ErrorVO(BLGMTX_MANDATORY);
				errors.add(errVO);
			}
			if (BLANK.equals(copyBlgLineForm.getValidFrom())
					|| BLANK.equals(copyBlgLineForm.getValidTo())) {
				log.log(Log.INFO, "DATES mandatory");
				ErrorVO err = new ErrorVO(DATES_MANDOTORY);
				errors.add(err);
			} else {
				LocalDate frmDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false).setDate(copyBlgLineForm
						.getValidFrom());
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false).setDate(copyBlgLineForm
						.getValidTo());
				if (frmDate.isGreaterThan(toDate)) {
					log.log(Log.INFO, "From date is greater than TO date");
					ErrorVO err = new ErrorVO(FROMDATE_GREATER);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
				}
				//Commented for ICRD-135181
				/*if (frmDate.before(currentDate)) {
					log.log(Log.INFO, "From date is before current date");
					ErrorVO err = new ErrorVO(FROMDATE_DATE_BEFORE_CUREENTDATE);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
				}*/
				if (errors != null && errors.size() > 0) {
					log.log(Log.FINE, "INSIDE ERRORS PART");
					invocationContext.addAllError(errors);
					invocationContext.target = OK_FAILURE;
					return;
				}
				billingMatrixVO.setBillingMatrixStatus(STATUS_NEW);
				billingMatrixVO
						.setCompanyCode(logonAttributes.getCompanyCode());
				billingMatrixVO.setValidityStartDate(frmDate);
				billingMatrixVO.setValidityEndDate(toDate);
				billingMatrixVO.setBillingMatrixId(copyBlgLineForm
						.getBlgMatrixId().toUpperCase());
				billingMatrixVO
						.setOperationFlag(BillingMatrixVO.OPERATION_FLAG_INSERT);
				for(BillingLineVO billingLineVO: selectedBlgLineVOs){
					//if(BLGLINE_STATUS_EXPIRED.equals(billingLineVO.getBillingLineStatus())){
						billingLineVO.setValidityStartDate(frmDate);
						billingLineVO.setValidityEndDate(toDate);
						billingLineVO.setBillingLineStatus(BLGLINE_STATUS_NEW);
					//}
				}
				pageBillingLines = new Page<BillingLineVO>(selectedBlgLineVOs,
						1, 25, selectedBlgLineVOs.size(), 0, selectedBlgLineVOs
								.size() - 1, false);
				
				billingMatrixVO.setBillingLineVOs(pageBillingLines);
				
				maintainBillingMatrixSession
						.setBillingMatrixVO(billingMatrixVO);
				maintainBillingMatrixSession
						.setBillingLineDetails(pageBillingLines);
				
				Collection<BillingLineVO> billingLineVOs = maintainBillingMatrixSession
						.getBillingLineDetails();
				ArrayList<BillingLineVO> billingLineVOArraylist = new ArrayList<BillingLineVO>(
						billingLineVOs);
				
				BillingLineVO billingLineVO;
				
				billingLineVO= billingLineVOArraylist.get(selectedBlgLineVOs.size()-1);
				
				billingLineVO.setBillingLineStatus(STATUS_NEW);
				copyBlgLineForm.setScreenFlag(OPEN);
			}
		} else {
			log.log(Log.INFO, "Inside billing matrix details present");
			billingMatrixVO = copyBlgLineSession.getBillingMatrixVO();
			for(BillingLineVO billingLineVO: selectedBlgLineVOs){
				//if(BLGLINE_STATUS_EXPIRED.equals(billingLineVO.getBillingLineStatus())){
					billingLineVO.setValidityStartDate(billingMatrixVO.getValidityStartDate());
					billingLineVO.setValidityEndDate(billingMatrixVO.getValidityEndDate());
					billingLineVO.setBillingLineStatus(BLGLINE_STATUS_NEW);
				//}
			}
			pageBillingLines = new Page<BillingLineVO>(selectedBlgLineVOs, 1,
					25, selectedBlgLineVOs.size(), 0,
					selectedBlgLineVOs.size() - 1, false);
			billingMatrixVO
					.setOperationFlag(BillingMatrixVO.OPERATION_FLAG_UPDATE);
			maintainBillingMatrixSession.setBillingMatrixVO(billingMatrixVO);
			maintainBillingMatrixSession
					.setBillingLineDetails(pageBillingLines);
			copyBlgLineForm.setScreenFlag(OPEN);
		}
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target = OK_FAILURE;
			return;
		}
		invocationContext.target = OK_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
