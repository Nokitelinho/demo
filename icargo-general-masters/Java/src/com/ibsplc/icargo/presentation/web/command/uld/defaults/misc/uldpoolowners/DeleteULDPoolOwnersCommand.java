/*
 * DeleteULDPoolOwnersCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldpoolowners;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDPoolOwnersSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDPoolOwnersForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3429
 * 
 */

public class DeleteULDPoolOwnersCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD Management");

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.uldpoolowners";

	/**
	 * The execute method in BaseCommand
	 * 
	 * @author
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("DeleteRowCommand", "execute");

		ArrayList<ULDPoolOwnerVO> list = null;

		ULDPoolOwnersForm form = (ULDPoolOwnersForm) invocationContext.screenModel;
		ULDPoolOwnersSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		String[] selectedRows = form.getSelectedRows();
		ArrayList<ULDPoolOwnerVO> removedRows = new ArrayList<ULDPoolOwnerVO>();
		if (session.getUldPoolOwnerVO() != null) {
			list = new ArrayList<ULDPoolOwnerVO>(session.getUldPoolOwnerVO());
			// listSession=new
			// ArrayList<ULDPoolOwnerVO>(session.getUldPoolOwnerVO());
			if (list != null && selectedRows != null) {
				for (int i = 0; i < selectedRows.length; i++) {
					log.log(Log.INFO, "Selected Rowsssssss", selectedRows, i);
					ULDPoolOwnerVO vo = list.get(Integer
							.parseInt(selectedRows[i]));

					if (("I").equalsIgnoreCase(vo.getOperationFlag())) {
						log.log(Log.INFO, "Poll Colectionnnn", list);
						log.log(Log.INFO, "inside remove", vo);
						removedRows.add(vo);

					} else {
						vo.setOperationFlag("D");
					}
				}
			}
			if (removedRows != null && removedRows.size() > 0) {
				log.log(Log.INFO, "deleted rowssss", removedRows);
				list.removeAll(removedRows);
			}
			session.setUldPoolOwnerVO(list);
			log.log(Log.INFO, "vos after removal in session", list);
		}
		log.exiting("DeleteRowCommand", "execute");
		invocationContext.target = "save_success";
	}
}
