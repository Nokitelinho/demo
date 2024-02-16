/*
 * OffloadListULDCommand.java Created on July 22, 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3278
 *
 */
public class OffloadListULDCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Delete Uld");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.listuld";

	private static final String OFFLOAD_SUCCESS = "offload_success";

	private static final String OFFLOAD_FAILURE = "offload_failure";
	
	private static final String NOT_OCCUPIED = "N";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		invocationContext.target = OFFLOAD_SUCCESS;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		ListULDSession listULDSession = (ListULDSession) getScreenSession(
				MODULE, SCREENID);

		Page<ULDListVO> uldListVO = listULDSession.getListDisplayPage();
		Collection<ULDVO> uldVOs = new ArrayList<ULDVO>();
		if (listULDForm.getSelectedRows() != null
				&& listULDForm.getSelectedRows().length > 0) {
			String selectedRows[] = listULDForm.getSelectedRows();
			String flightInfo = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				ULDVO uldVO = new ULDVO();
				uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_UPDATE);
				uldVO.setCompanyCode(logonAttributes.getCompanyCode());
				uldVO.setUldNumber(uldListVO.get(
						Integer.parseInt(selectedRows[i])).getUldNumber()
						.toUpperCase());
				uldVO.setTransitStatus(NOT_OCCUPIED);

				// Added for Bug 104214
				flightInfo = uldListVO.get(
						Integer.parseInt(selectedRows[i])).getFlightInfo();
				if (flightInfo != null){
					uldVO.setFlightInfo(flightInfo.toUpperCase());
				}

				uldVO.setLastUpdateUser(logonAttributes.getUserId());
				uldVO.setLastUpdateTime(uldListVO.get(
						Integer.parseInt(selectedRows[i])).getLastUpdateTime());

				uldVOs.add(uldVO);
				log.log(Log.FINE, "Offload<-->uldVO------>>>", uldVO);
			}

			try {
				new ULDDefaultsDelegate().offloadULDs(uldVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> offloadErrors = handleDelegateException(businessDelegateException);
				if (offloadErrors != null && offloadErrors.size() > 0) {
					errors.addAll(offloadErrors);
				}
			}

			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = OFFLOAD_FAILURE;
				return;
			}

			listULDForm
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = OFFLOAD_SUCCESS;

		}

	}
}
