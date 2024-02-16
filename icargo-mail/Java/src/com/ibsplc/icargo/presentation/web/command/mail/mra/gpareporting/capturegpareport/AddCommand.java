/*
 * AddCommand.java Created on Feb 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Feb 22, 2007 a-2257 Created
 */
public class AddCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "AddCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";

	/**
	 * constant for invoking popup screen
	 */
	private static final String POPUP = "POPUP";

	private static final String ADD = "ADD";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";

	/**
	 * 
	 * TODO Purpose Mar 11, 2007, a-2257
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("AddCommand", "execute");

		CaptureGPAReportSession session = (CaptureGPAReportSession) getScreenSession(
				MODULE_NAME, SCREENID);
		CaptureGPAReportForm form = (CaptureGPAReportForm) invocationContext.screenModel;

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs = new ArrayList<GPAReportingDetailsVO>();

		GPAReportingFilterVO gpaReportingFilterVO = session
				.getGPAReportingFilterVO();

		GPAReportingDetailsVO gpaReportingDetailsVO = new GPAReportingDetailsVO();

		gpaReportingDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		// private String companyCode;

		gpaReportingDetailsVO.setPoaCode(gpaReportingFilterVO.getPoaCode());
		gpaReportingDetailsVO.setCountryCode(gpaReportingFilterVO.getCountry());
		gpaReportingDetailsVO.setBillingBasis("");
		gpaReportingDetailsVO.setReportingFrom(gpaReportingFilterVO
				.getReportingPeriodFrom());
		gpaReportingDetailsVO.setReportingTo(gpaReportingFilterVO
				.getReportingPeriodTo());
		gpaReportingDetailsVO.setSequenceNumber(0);
		gpaReportingDetailsVO.setDsnDate(null);
		gpaReportingDetailsVO.setBasistype(form.getBasistype());
		gpaReportingDetailsVO.setOriginOfficeExchange("");
		gpaReportingDetailsVO.setDestinationOfficeExchange("");
		gpaReportingDetailsVO.setMailCategory("");
		gpaReportingDetailsVO.setActualMailSubClass("");
		gpaReportingDetailsVO.setYear("");
		gpaReportingDetailsVO.setDsnNumber("");
		if (form.getBasistype() != null
				&& form.getBasistype().trim().length() > 0) {
			if ("M".equals(form.getBasistype())) {
				gpaReportingDetailsVO.setNoOfMailBags(1);
			} else {

				gpaReportingDetailsVO.setNoOfMailBags(0);
			}
		}

		gpaReportingDetailsVO.setWeight(0);
		gpaReportingDetailsVO.setRate(0);
		gpaReportingDetailsVO.setAmount(null);
		gpaReportingDetailsVO.setTax(0);
		gpaReportingDetailsVO.setTotal(null);
		gpaReportingDetailsVO.setReportingStatus("R");

		gpaReportingDetailsVO
				.setOperationFlag(GPAReportingDetailsVO.OPERATION_FLAG_INSERT);

		// form.setPopUpStatusFlag(POPUPFLAG);

		gpaReportingDetailsVOs.add(gpaReportingDetailsVO);

		session.setModifiedGPAReportingDetailsVOs(gpaReportingDetailsVOs);

		session
				.setSelectedGPAReportingDetailsVO(((ArrayList<GPAReportingDetailsVO>) session
						.getModifiedGPAReportingDetailsVOs()).get(0));

		log.log(Log.INFO, "session.getSelectedVO()-->", session.getSelectedGPAReportingDetailsVO());
		form.setScreenFlag(POPUP);
		form.setPopUpStatusFlag(ADD);
		log.log(Log.INFO, "form.getScreenFlag()-->", form.getScreenFlag());
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
