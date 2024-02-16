/* DeleteULDMovementHistoryCommand.java Created on Aug 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2412
 *
 */
public class DeleteULDMovementHistoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD_MOVEMENT_HISTORY");

	private static final String MODULE = "uld.defaults";

	private static final String DELETE_SUCCESS = "delete_success";

	private static final String DELETE_FAILURE = "delete_failure";

	private static final String SCREENID = "uld.defaults.misc.listuldmovement";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DeleteULDMovementHistoryCommand", "execute");				
		ListULDMovementForm listULDMovementForm = (ListULDMovementForm) invocationContext.screenModel;
		ListULDMovementSession listUldMovementSession = getScreenSession(
				MODULE, SCREENID);
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();

		String[] checked = listULDMovementForm.getSelectedRows();
		int size = checked.length;

		Page<ULDMovementDetailVO> movementVOs = listUldMovementSession
				.getUldMovementDetails();

		Collection<ULDMovementDetailVO> uldMovementDetails = new ArrayList<ULDMovementDetailVO>();

		log.log(Log.FINE, "checkedValues", checked);
		log.log(Log.FINE, "checkedValues", checked.length);
		for (int j = 0; j < size; j++) {
			String[] token = checked[j].split("-");
			log.log(Log.FINE, "token[0]-------->", token);
			String selected = token[0];
			int x = Integer.parseInt(selected);
			movementVOs.get(x).setUldNumber(listULDMovementForm.getUldNumber());
			uldMovementDetails.add(movementVOs.get(x));
			log.log(Log.FINE, "selected carrier code-------->", movementVOs.get(x).getCarrierCode());
			log.log(Log.FINE, "selected mov seq num", movementVOs.get(x).getMovementSequenceNumber());
		}

		log.log(Log.FINE, "uldMovementDetails for deletion are ",
				uldMovementDetails);
		ULDDefaultsDelegate uLDDefaultsDelegate = new ULDDefaultsDelegate();
		try {
			uLDDefaultsDelegate.deleteULDMovements(uldMovementDetails);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(error);
			invocationContext.target = DELETE_FAILURE;
			return;
		}
		if (error != null && error.size() > 0) {
			invocationContext.addAllError(error);
		}

		clearForm(listULDMovementForm);
		listUldMovementSession.setUldMovementDetails(null);
		listUldMovementSession.setUldMovementFilterVO(null);
		invocationContext.addError(new ErrorVO(
				"uld.defaults.misc.listuldmovement.savedsuccessfully"));
		invocationContext.target = DELETE_SUCCESS;
	}

	private void clearForm(ListULDMovementForm listULDMovementForm) {
		listULDMovementForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		listULDMovementForm.setCurrentStn("");
		listULDMovementForm.setCurrentStatus("");
		listULDMovementForm.setFromDate("");
		listULDMovementForm.setOwnerCode("");
		listULDMovementForm.setOwnerStation("");
		listULDMovementForm.setToDate("");
		listULDMovementForm.setUldNumber("");
		listULDMovementForm.setSelectAll(false);
		listULDMovementForm.setListStatus("");
	}

}
