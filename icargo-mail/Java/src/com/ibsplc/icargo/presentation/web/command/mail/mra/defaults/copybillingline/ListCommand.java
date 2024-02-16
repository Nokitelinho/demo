/*
 * ListCommand.java Created on Mar 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.copybillingline;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyBlgLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyBlgLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-2391
 * 
 */
public class ListCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS COPYBLGLINE");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyblgline";

	private static final String BLGMTX_MANDATORY = "mailtracking.mra.defaults.copybillingline.err.blgmtxidmadatory";

	private static final String LIST_FAILURE = "list_failure";

	private static final String LIST_SUCCESS = "list_success";

	private static final String CAPTURE = "capture";

	private static final String BLGMATRIX_NOTEXISTS = "mailtracking.mra.defaults.copybillingline.msg.wrn.blgmatrixnotexists";

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
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		CopyBlgLineSession copyBlgLineSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		copyBlgLineForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		BillingMatrixVO billingMatrixVO = null;
		copyBlgLineForm.setScreenMode("");
		if (copyBlgLineForm.getBlgMatrixId() == null
				&& copyBlgLineForm.getBlgMatrixId().trim().length() == 0) {
			ErrorVO errVO = new ErrorVO(BLGMTX_MANDATORY);
			errVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(errVO);
			invocationContext.target = LIST_FAILURE;
			return;
		} else {
			String blgMatrixId = copyBlgLineForm.getBlgMatrixId().toUpperCase();
			try {
				copyBlgLineSession.removeBillingMatrixVO();
				BillingMatrixFilterVO filtervo = new BillingMatrixFilterVO();
				filtervo.setCompanyCode(logonAttributes.getCompanyCode());
				filtervo.setBillingMatrixId(copyBlgLineForm.getBlgMatrixId());
				billingMatrixVO = delegate.findBillingMatrixDetails(filtervo);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
			}
			if (billingMatrixVO == null) {
				copyBlgLineForm.setScreenMode(CAPTURE);
				ErrorVO errVO = new ErrorVO(BLGMATRIX_NOTEXISTS);
				errVO.setErrorDisplayType(ErrorDisplayType.WARNING);
				invocationContext.addError(errVO);
				copyBlgLineForm.setBlgMatrixId(blgMatrixId);
				copyBlgLineForm.setValidFrom("");
				copyBlgLineForm.setValidTo("");
			} else {
				log.log(Log.INFO, "\n\nRate Card VO-->", billingMatrixVO);
				copyBlgLineForm.setScreenMode("list");
				copyBlgLineForm.setBlgMatrixId(billingMatrixVO
						.getBillingMatrixId());
				String validFrm = "";
				String validTo = "";
				if (billingMatrixVO.getValidityStartDate() != null) {
					validFrm = TimeConvertor.toStringFormat(billingMatrixVO
							.getValidityStartDate(), logonAttributes
							.getDateFormat());
				}
				if (billingMatrixVO.getValidityEndDate() != null) {
					validTo = TimeConvertor.toStringFormat(billingMatrixVO
							.getValidityEndDate(), logonAttributes
							.getDateFormat());
				}
				log.log(Log.INFO, "valid frm-->", validFrm);
				copyBlgLineForm.setValidFrom(validFrm);
				copyBlgLineForm.setValidTo(validTo);
				billingMatrixVO
						.setOperationFlag(RateCardVO.OPERATION_FLAG_UPDATE);
				copyBlgLineSession.setBillingMatrixVO(billingMatrixVO);
			}
		}
		invocationContext.target = LIST_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
