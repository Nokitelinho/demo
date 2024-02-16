/*
 * DeleteAgreementDetailsCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.maintainuldagreement;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AddULDAgreementForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class DeleteAgreementDetailsCommand extends
		SelectNextAgreementDetailCommand {
	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.maintainuldagreement";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ULD_AGREEMENT");
		MaintainULDAgreementSession session = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		AddULDAgreementForm form = (AddULDAgreementForm) invocationContext.screenModel;
		// ULDAgreementVO agreementVO = session.getUldAgreementDetails();
		ArrayList<ULDAgreementDetailsVO> detailVOs = session
				.getUldAgreementVOs();
		if (detailVOs != null && detailVOs.size() > 0) {
			int discIndex = Integer.parseInt(form.getDisplayPage()) - 1;
			detailVOs.remove(discIndex);
			int size = detailVOs.size();
			ULDAgreementDetailsVO detailsVO = new ULDAgreementDetailsVO();
			if (size <= 0) {
				form.setFlag("NORECORDS");
				invocationContext.addError(new ErrorVO(
						"uld.defaults.nomorerecords"));
			}
			int displayPage = Integer.parseInt(form.getDisplayPage());
			int totalRecords = detailVOs != null && detailVOs.size() > 0 ? detailVOs
					.size()
					: 0;

			if (displayPage != 1) {
				displayPage = Integer.parseInt(form.getDisplayPage()) - 1;
			}
			form.setTotalRecords(String.valueOf(totalRecords));
			form.setLastPageNumber(String.valueOf(totalRecords));
			form.setDisplayPage(String.valueOf(displayPage));
			form.setCurrentPage(form.getDisplayPage());
			int selectedIndex = Integer.parseInt(form.getCurrentPage()) - 1;
			log.log(Log.FINE, "\n\nselectedIndex ---> " + selectedIndex);
			if (detailVOs != null && detailVOs.size() > 0) {

				detailsVO = detailVOs.get(selectedIndex);
			}
			log.log(Log.FINE, "\n\nuldagreementVOs after delete ---> "
					+ detailVOs);
			session.setUldAgreementVOs(detailVOs);
			popuplateNewDetailsVO(detailsVO, form);
		} else {
			form.setFlag("NORECORDS");
			invocationContext
					.addError(new ErrorVO("uld.defaults.nomorerecords"));
		}

		invocationContext.target = SCREENLOAD_SUCCESS;
	}
}
