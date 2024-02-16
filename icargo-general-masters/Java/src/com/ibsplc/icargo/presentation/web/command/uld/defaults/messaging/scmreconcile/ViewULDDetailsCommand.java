/* ViewULDDetailsCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmreconcile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMReconcileForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * \
 *
 * @author A-2046
 *
 */
public class ViewULDDetailsCommand extends BaseCommand {

	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmreconcile";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.scmulderrorlog";

	private static final String LIST_SUCCESS = "list_success";

	private Log log = LogFactory.getLogger("SCM RECONCILE");

	private static final String ERROR_DESC = "uld.defaults.scmulderror";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
//		Commented by Manaf for INT ULD510
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String compCode = logonAttributes.getCompanyCode();

		SCMReconcileForm scmReconcileForm = (SCMReconcileForm) invocationContext.screenModel;
		SCMReconcileSession scmReconcileSession = getScreenSession(MODULE,
				SCREENID);
		SCMULDErrorLogSession uldErrorLogSession=getScreenSession(MODULE,SCREENID_ULDERRORLOG);

		log.log(Log.FINE, "SELECTED ROW INDEX------>", scmReconcileForm.getRowIndex());
		Page<ULDSCMReconcileVO> scmReconcileVOs = scmReconcileSession
				.getSCMReconcileVOs();
		String[] selectedRows=scmReconcileForm.getSelectedSCMErrorLog();
		log.log(Log.FINE, "Selcted Index------------------->", selectedRows);
		ULDSCMReconcileVO selectedReconcileVO = scmReconcileVOs.get(Integer
				.parseInt(selectedRows[0]));
		log.log(Log.FINE, "Selected SCM Reconcile VO-------------->",
				selectedReconcileVO);
		SCMMessageFilterVO scmFilterVO = new SCMMessageFilterVO();
		scmFilterVO.setAirportCode(selectedReconcileVO.getAirportCode());
		scmFilterVO.setCompanyCode(selectedReconcileVO.getCompanyCode());
		scmFilterVO.setStockControlDate(selectedReconcileVO.getStockCheckDate());
		scmFilterVO.setSequenceNumber(selectedReconcileVO.getSequenceNumber());
		scmFilterVO.setAirlineCode(scmReconcileSession.getMessageFilterVO().getAirlineCode());
		scmFilterVO.setFlightCarrierIdentifier(selectedReconcileVO.getAirlineIdentifier());
		scmFilterVO.setPageNumber(1);
		uldErrorLogSession.setSCMULDFilterVO(scmFilterVO);
		uldErrorLogSession.setPageUrl("fromScmReconcile");

		//Added by Tarun for INT_ULD502
		Collection<OneTimeVO> errorDescriptions = getOneTimeVOs(logonAttributes.getCompanyCode());
		uldErrorLogSession.setErrorDescriptions(errorDescriptions);

		log.log(Log.FINE, "PageUrl-------------->", uldErrorLogSession.getPageUrl());
		invocationContext.target = LIST_SUCCESS;

	}

	//	Added by Tarun for INT_ULD502
	public Collection<OneTimeVO> getOneTimeVOs(String companyCode){
		ArrayList<String> parameterList = new ArrayList<String>();
		Map<String,Collection<OneTimeVO>> hashMap = new HashMap<String,Collection<OneTimeVO>>();
		parameterList.add(ERROR_DESC);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode, parameterList);
		} catch (BusinessDelegateException e) {
			e.getMessage();
		}
		return hashMap.get(ERROR_DESC);
	}


}
