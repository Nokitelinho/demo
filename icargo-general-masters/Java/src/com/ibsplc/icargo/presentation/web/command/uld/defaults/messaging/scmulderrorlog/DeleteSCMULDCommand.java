/* DeleteSCMULDCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmulderrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class DeleteSCMULDCommand extends BaseCommand {
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private static final String DELETE_SUCCESS = "delete_success";

	private Log log = LogFactory.getLogger("SCM_ULD_RECONCILE");
	

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String compCode = logonAttributes.getCompanyCode();

		SCMULDErrorLogForm scmUldReconcileForm = (SCMULDErrorLogForm) invocationContext.screenModel;
		SCMULDErrorLogSession scmUldSession = (SCMULDErrorLogSession) getScreenSession(
				MODULE, SCREENID);
		Page<ULDSCMReconcileDetailsVO> reconcileDetailsVOs = scmUldSession
				.getSCMReconcileDetailVOs();
		String[] selectedUlds = scmUldReconcileForm.getSelectedUlds();
		if (selectedUlds != null) {
			int index = 0;
			for (ULDSCMReconcileDetailsVO detailsVO : reconcileDetailsVOs) {
				for (int i = 0; i < selectedUlds.length; i++) {
					if (index == Integer.parseInt(selectedUlds[i])) {
						detailsVO
								.setOperationFlag(ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_DELETE);

					}

				}
				index++;

			}
		}
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ULDSCMReconcileDetailsVO> reconcileVOsForDelete = new ArrayList<ULDSCMReconcileDetailsVO>();
		for (ULDSCMReconcileDetailsVO reconcileDetailsVO : reconcileDetailsVOs) {
			if (ULDFlightMessageReconcileDetailsVO.OPERATION_FLAG_DELETE
					.equals(reconcileDetailsVO.getOperationFlag())) {
				reconcileVOsForDelete.add(reconcileDetailsVO);
			}
		}
		log.log(Log.FINE, "\n\n\nReconcile vos for deletion------------>",
				reconcileVOsForDelete);
		reconcileDetailsVOs.removeAll(reconcileVOsForDelete);
		scmUldSession.setSCMReconcileDetailVOs(reconcileDetailsVOs);
		log.log(Log.FINE, "VOS for session-------------------->",
				reconcileDetailsVOs);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			delegate.deleteULDsInSCM(reconcileVOsForDelete);
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			errors = handleDelegateException(ex);
		}

		invocationContext.target = DELETE_SUCCESS;

	}
}
