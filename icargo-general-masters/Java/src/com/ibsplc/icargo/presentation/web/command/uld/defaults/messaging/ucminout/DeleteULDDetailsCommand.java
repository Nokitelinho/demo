/*
 * DeleteULDDetailsCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import java.util.ArrayList;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class DeleteULDDetailsCommand extends AddULDDetailsCommand {
	private static final String DELETE_SUCCESS = "delete_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("Delete Command", "UCM IN OUT");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		ULDFlightMessageReconcileVO reconcileVO = session
				.getMessageReconcileVO();
		ArrayList<ULDFlightMessageReconcileDetailsVO> reconcileDetails = (ArrayList<ULDFlightMessageReconcileDetailsVO>)reconcileVO
				.getReconcileDetailsVOs();

		if (reconcileDetails != null && reconcileDetails.size() > 0) {
			updateULDDetails(reconcileDetails, form,reconcileVO);
		}
		ArrayList<ULDFlightMessageReconcileDetailsVO> reconcileDetailsToRemove = new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		String[] checked = form.getSelectedRows();
		int index = 0;
		for (ULDFlightMessageReconcileDetailsVO detailsVO : reconcileDetails) {
			for (int i = 0; i < checked.length; i++) {
				if (index == Integer.parseInt(checked[i])) {
					if (ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_INSERT
							.equals(detailsVO.getOperationFlag())) {
						reconcileDetailsToRemove.add(detailsVO);

					} else {
						detailsVO
								.setOperationFlag(ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_DELETE);
					}
				}

			}
			index++;

		}

		if (reconcileDetailsToRemove != null
				&& reconcileDetailsToRemove.size() > 0) {
			reconcileDetails.removeAll(reconcileDetailsToRemove);
		}

		invocationContext.target = DELETE_SUCCESS;
	}

}
