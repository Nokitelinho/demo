/*
 * ListCommand.java Created on Mar 30, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.monitormailsla;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailActualDetailVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MonitorMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MonitorMailSLAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2122
 * 
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.monitormailsla";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		MonitorMailSLAForm monitorMailSLAForm = (MonitorMailSLAForm) invocationContext.screenModel;
		MonitorMailSLASession monitorMailSLASession = (MonitorMailSLASession) getScreenSession(
				MODULE_NAME, SCREEN_ID);

		if (("fromMaintainSLA").equals(monitorMailSLAForm.getCloseFlag())) {

			populateFilterToForm(monitorMailSLAForm, monitorMailSLASession);

		}

		updateFilterDetails(monitorMailSLAForm, monitorMailSLASession);

		log.log(Log.FINE, "Filter VO", monitorMailSLASession.getFilterVO());
		Page<MailActualDetailVO> mailSLADetails = null;

		Collection<ErrorVO> errors = null;

		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();

		try {
			mailSLADetails = mailTrackingDefaultsDelegate
					.findMailActivityDetails(monitorMailSLASession.getFilterVO());

		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "inside try...caught businessDelegateException");
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		if (mailSLADetails == null || mailSLADetails.size() == 0) {
			log.log(Log.FINE, "!!!inside resultList== null");
			monitorMailSLAForm.setDisplayPage("1");
			monitorMailSLAForm.setLastPageNum("0");
			monitorMailSLASession.setMailSlaDetails(null);
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.monitormailsla.msg.err.nomailsladetailsexists");
			errorVO.setErrorDisplayType(ERROR);
			errors = new ArrayList<ErrorVO>();
			errors.add(errorVO);

		}

		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "!!!inside errors!= null");
			invocationContext.addAllError(errors);
			monitorMailSLAForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
		} else {
			log.log(Log.FINE, "!!!inside resultList!= null");

			monitorMailSLASession.setMailSlaDetails(mailSLADetails);
			monitorMailSLAForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;

			log.exiting("ListCommand", "execute");
		}

		invocationContext.target = LIST_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

	/**
	 * Method to populate filter vo
	 * 
	 * @param monitorMailSLAForm
	 * @param monitorMailSLASession
	 */
	private void updateFilterDetails(MonitorMailSLAForm monitorMailSLAForm,
			MonitorMailSLASession monitorMailSLASession) {
		// TODO Auto-generated method stub
		MailActualDetailFilterVO mailActualDetailFilterVO = new MailActualDetailFilterVO();
		mailActualDetailFilterVO.setCompanyCode(getApplicationSession()
				.getLogonVO().getCompanyCode());
		mailActualDetailFilterVO.setAirlineCode(monitorMailSLAForm
				.getAirlineCode());
		mailActualDetailFilterVO.setPostalAuthorityCode(monitorMailSLAForm
				.getPaCode());
		mailActualDetailFilterVO.setFlightCarrierCode(monitorMailSLAForm
				.getCarrierCode());
		mailActualDetailFilterVO.setFlightNumber(monitorMailSLAForm
				.getFlightNo());
		mailActualDetailFilterVO.setMailCategory(monitorMailSLAForm
				.getCategory());
		mailActualDetailFilterVO.setActivity(monitorMailSLAForm.getActivity());
		mailActualDetailFilterVO
				.setSlaStatus(monitorMailSLAForm.getSlaStatus());
		String displayPage = monitorMailSLAForm.getDisplayPage();
		int pageNumber = Integer.parseInt(displayPage);
		mailActualDetailFilterVO.setPageNumber(pageNumber);
		monitorMailSLASession.setFilterVO(mailActualDetailFilterVO);

	}

	/**
	 * Method to populate filter fields to form variables
	 * 
	 * @param monitorMailSLAForm
	 * @param monitorMailSLASession
	 */
	private void populateFilterToForm(MonitorMailSLAForm monitorMailSLAForm,
			MonitorMailSLASession monitorMailSLASession) {
		// TODO Auto-generated method stub
		MailActualDetailFilterVO mailActualDetailFilterVO = monitorMailSLASession
				.getFilterVO();
		if (mailActualDetailFilterVO.getAirlineCode() != null
				&& mailActualDetailFilterVO.getAirlineCode().trim().length() > 0) {
			monitorMailSLAForm.setAirlineCode(mailActualDetailFilterVO
					.getAirlineCode());
		}
		if (mailActualDetailFilterVO.getPostalAuthorityCode() != null
				&& mailActualDetailFilterVO.getPostalAuthorityCode().trim()
						.length() > 0) {
			monitorMailSLAForm.setPaCode(mailActualDetailFilterVO
					.getPostalAuthorityCode());
		}
		if (mailActualDetailFilterVO.getFlightCarrierCode() != null
				&& mailActualDetailFilterVO.getFlightCarrierCode().trim()
						.length() > 0) {
			monitorMailSLAForm.setCarrierCode(mailActualDetailFilterVO
					.getFlightCarrierCode());
		}
		if (mailActualDetailFilterVO.getFlightNumber() != null
				&& mailActualDetailFilterVO.getFlightNumber().trim().length() > 0) {
			monitorMailSLAForm.setFlightNo(mailActualDetailFilterVO
					.getFlightNumber());
		}
		if (mailActualDetailFilterVO.getMailCategory() != null
				&& mailActualDetailFilterVO.getMailCategory().trim().length() > 0) {
			monitorMailSLAForm.setCategory(mailActualDetailFilterVO
					.getMailCategory());
		}

		if (mailActualDetailFilterVO.getActivity() != null
				&& mailActualDetailFilterVO.getActivity().trim().length() > 0) {
			monitorMailSLAForm.setActivity(mailActualDetailFilterVO
					.getActivity());
		}

		if (mailActualDetailFilterVO.getSlaStatus() != null
				&& mailActualDetailFilterVO.getSlaStatus().trim().length() > 0) {
			monitorMailSLAForm.setSlaStatus(mailActualDetailFilterVO
					.getSlaStatus());
		}

	}

}
