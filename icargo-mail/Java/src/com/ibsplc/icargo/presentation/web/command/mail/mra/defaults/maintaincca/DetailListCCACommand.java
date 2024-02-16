/*
 * DetailListCCACommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected CCAs
 * 
 * @author A-3429
 */
public class DetailListCCACommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "DetailListCCACommand";

	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * Screen ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listcca";

	/**
	 * target action
	 */
	private static final String DETAIL_SUCCESS = "detail_success";

	/**
	 * LIST_FAILURE
	 */
	private static final String LIST_FAILURE = "list_failure";

	/**
	 * For Error Tags
	 */
	private static final String ERROR_MANDATORY = "mailtracking.mra.defaults.maintaincca.anyfiltercriteria";

	/**
	 * target action
	 */
	private static final String SCREEN_STATUS = "maintaincca";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		ListCCASession listccaSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		int count = Integer.parseInt(maintainCCAForm.getCount());
		Page<CCAdetailsVO> ccaDetailVOs = listccaSession.getCCADetailsVOs();
		CCAdetailsVO ccaDetailVO = ccaDetailVOs.get(count);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO displayErrorVO = null;

		MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();

		maintainCCAFilterVO.setCompanyCode(companyCode);
		if (ccaDetailVO.getCcaRefNumber() != null) {
			maintainCCAFilterVO.setCcaReferenceNumber(ccaDetailVO
					.getCcaRefNumber());
			maintainCCAForm.setCcaNum(ccaDetailVO.getCcaRefNumber());
		}
		if (ccaDetailVO.getDsnNo() != null) {
			maintainCCAFilterVO.setDsnNumber(ccaDetailVO.getDsnNo());
			maintainCCAForm.setDsnNumber(ccaDetailVO.getDsnNo());
		}
		if (ccaDetailVO.getDsDate() != null
				&& ccaDetailVO.getDsnDate().trim().length() > 0) {
			maintainCCAFilterVO.setDsnDate(ccaDetailVO.getDsDate());
			maintainCCAForm.setDsnDate(ccaDetailVO.getDsDate()
					.toDisplayDateOnlyFormat());
		}
		maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
		log.log(Log.FINE, "maintainCCAFilterVO----->", maintainCCAFilterVO);
		maintainCCAForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		// Mandatory check
		if ((maintainCCAFilterVO.getCcaReferenceNumber() == null || maintainCCAFilterVO
				.getCcaReferenceNumber().trim().length() == 0)
				&& (maintainCCAFilterVO.getDsnNumber() == null || maintainCCAFilterVO
						.getDsnNumber().trim().length() == 0)
				&& (maintainCCAFilterVO.getDsnDate() == null)) {

			displayErrorVO = new ErrorVO(ERROR_MANDATORY);
			displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(displayErrorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}

		if (maintainCCAFilterVO.getCcaReferenceNumber() != null
				&& maintainCCAFilterVO.getCcaReferenceNumber().trim().length() > 0) {
			updateSession(maintainCCASession, maintainCCAFilterVO,
					maintainCCAForm);

		}
		listccaSession.setListStatus(SCREEN_STATUS);
		invocationContext.target = DETAIL_SUCCESS;

	}

	/**
	 * @author A-3429
	 * @param maintainCCASession
	 * @param maintainCCAFilterVO
	 * @param maintainCCAForm
	 */

	public void updateSession(MaintainCCASession maintainCCASession,
			MaintainCCAFilterVO maintainCCAFilterVO,
			MRAMaintainCCAForm maintainCCAForm) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<CCAdetailsVO> ccaDetailsVO = null;
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();

		try {
			ccaDetailsVO = mailTrackingMRADelegate
					.findCCAdetails(maintainCCAFilterVO);

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if (ccaDetailsVO != null) {
			/*maintainCCAForm.setIssueDate(ccaDetailsVO.getIssueDate());
			maintainCCAForm.setBilfrmdate(ccaDetailsVO.getBillingPeriodFrom());
			maintainCCAForm.setBiltodate(ccaDetailsVO.getBillingPeriodTo());
			maintainCCAForm.setOrigin(ccaDetailsVO.getOrigin());
			maintainCCAForm.setDestination(ccaDetailsVO.getDestination());
			maintainCCAForm.setCategory(ccaDetailsVO.getCategory());
			maintainCCAForm.setSubclass(ccaDetailsVO.getSubClass());
			maintainCCAForm.setCcaNum(ccaDetailsVO.getCcaRefNumber());
			maintainCCASession.setCCAdetailsVO(ccaDetailsVO);
			log.log(Log.FINE, "ccaDetailsVOs----->" + ccaDetailsVO);*/

		}
		return;
	}

}
