/*
 * SelectNextAgreementDetailCommand.java.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AddULDAgreementForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class SelectNextAgreementDetailCommand extends
		AddAgreementDetailsCommand {

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENLOAD_FAILURE = "screenload_failure";

	private static final String BLANK = "";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ULD_AGREEMENT");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		AddULDAgreementForm form = (AddULDAgreementForm) invocationContext.screenModel;

		/*
		 * validate
		 */
		Collection<ErrorVO> errors = null;
		errors = validateCreatedVO(form);

		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt(form.getDisplayPage());
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->" + displayPageNum);
			form.setDisplayPage(form.getCurrentPage());
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		ArrayList<ULDAgreementDetailsVO> detailVOs = session
				.getUldAgreementVOs();
		/*
		 * The currently displayed VO
		 * 
		 */
		ULDAgreementDetailsVO currentULDAgreementVO = detailVOs.get(Integer
				.parseInt(form.getCurrentPage()) - 1);

		ArrayList<ULDAgreementDetailsVO> validationVOs = new ArrayList<ULDAgreementDetailsVO>();
		validationVOs = (ArrayList<ULDAgreementDetailsVO>) detailVOs.clone();
		validationVOs.remove(currentULDAgreementVO);

		ErrorVO error = validateDetailsDate(validationVOs, form);
		if (error != null) {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt(form.getDisplayPage());
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->" + displayPageNum);
			form.setDisplayPage(form.getCurrentPage());
			invocationContext.addError(error);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		errors = validateAirportCodes(form.getStation().toUpperCase(),
				logonAttributes);
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt(form.getDisplayPage());
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->" + displayPageNum);
			form.setDisplayPage(form.getCurrentPage());
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		errors = validateUldType(form.getUldType().toUpperCase(),
				logonAttributes);
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "exception");
			int displayPageNum = Integer.parseInt(form.getDisplayPage());
			log.log(Log.FINE, "\n\n\n**DisplayPage val-->" + displayPageNum);
			form.setDisplayPage(form.getCurrentPage());
			invocationContext.addAllError(errors);
			invocationContext.target = SCREENLOAD_FAILURE;
			return;
		}
		if (setTransactionFlag(currentULDAgreementVO, form)) {
			log.log(Log.FINE, "\n\n\n\nsetting the transaction flag");
			session.getUldAgreementDetails().setValidateFlag(true);
		}

		/*
		 * update the currently selected VO with the values from the form
		 */
		updateULDAgreementDetailsVO(currentULDAgreementVO, form,
				applicationSession);
		/*
		 * validate with existing collection
		 */
		// To be reviewed:
		/*
		 * Obtain the VO that is to be displayed
		 */
		ULDAgreementDetailsVO uldAgreementDetailsVO = detailVOs.get(Integer
				.parseInt(form.getDisplayPage()) - 1);
		/*
		 * populate the form with the new VO
		 */
		populateNewDetails(uldAgreementDetailsVO, form);
		/*
		 * update the form variables for navigation
		 */
		form.setCurrentPage(form.getDisplayPage());
		form.setTotalRecords(String.valueOf(detailVOs.size()));
		form.setLastPageNumber(String.valueOf(detailVOs.size()));

		invocationContext.target = SCREENLOAD_SUCCESS;

	}

	/**
	 * 
	 * @param detailsVO
	 * @param form
	 */
	public void popuplateNewDetailsVO(ULDAgreementDetailsVO detailsVO,
			AddULDAgreementForm form) {
		form.setStation(detailsVO.getStation());
		form.setUldType(detailsVO.getUldTypeCode());
		form.setCurrencyCode(detailsVO.getCurrency());
		form.setDemurrageFrequency(detailsVO.getDemurrageFrequency());
		form.setDemurrageRate(detailsVO.getDemurrageRate());
		form.setTaxes(detailsVO.getTax());
		if (detailsVO.getAgreementFromDate() != null) {
			form.setValidFrom(detailsVO.getAgreementFromDate()
					.toDisplayDateOnlyFormat());
		} else {
			form.setValidFrom(BLANK);
		}
		if (detailsVO.getAgreementToDate() != null) {
			form.setValidTo(detailsVO.getAgreementToDate()
					.toDisplayDateOnlyFormat());
		} else {
			form.setValidTo(BLANK);
		}
		form.setRemarks(detailsVO.getRemark());

	}

	/**
	 * 
	 * @param detailsVO
	 * @param form
	 * @return boolean
	 */
	public boolean setTransactionFlag(ULDAgreementDetailsVO detailsVO,
			AddULDAgreementForm form) {
		if (!AbstractVO.OPERATION_FLAG_INSERT.equals(detailsVO
				.getOperationFlag())) {
			if (hasValueChanged(detailsVO.getCurrency(), form.getCurrencyCode())
					|| hasValueChanged(detailsVO.getDemurrageRate(), form
							.getDemurrageRate())
					|| hasValueChanged(detailsVO.getDemurrageFrequency(), form
							.getDemurrageFrequency())
					|| hasValueChanged(detailsVO.getFreeLoanPeriod(), form
							.getFreeLoanPeriod())
					|| hasValueChanged(detailsVO.getTax(), form.getTaxes())
					|| hasValueChanged(detailsVO.getAgreementFromDate()
							.toDisplayDateOnlyFormat().toUpperCase(), form.getValidFrom())) {
				return true;
			}
			if (detailsVO.getAgreementToDate() != null) {
				if (hasValueChanged(detailsVO.getAgreementToDate()
						.toDisplayDateOnlyFormat().toUpperCase(), form.getValidTo())) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	public boolean hasValueChanged(double originalValue, double formValue) {
		return originalValue != formValue;
	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	public boolean hasValueChanged(int originalValue, int formValue) {
		return originalValue != formValue;
	}

	/**
	 * 
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	public boolean hasValueChanged(String originalValue, String formValue) {
		return !originalValue.equals(formValue);
	}

}
