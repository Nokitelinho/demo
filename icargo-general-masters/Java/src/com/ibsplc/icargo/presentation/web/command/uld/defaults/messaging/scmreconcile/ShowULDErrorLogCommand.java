/* ShowULDErrorLogCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmreconcile;

import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMReconcileForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * \
 * 
 * @author A-2046
 * 
 */
public class ShowULDErrorLogCommand extends BaseCommand {

	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmreconcile";
	
	private static final String SCREENID_ULDERRORLOG = "uld.defaults.scmulderrorlog";

	private static final String LIST_SUCCESS = "list_success";

	private Log log = LogFactory.getLogger("SCM RECONCILE");
	
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

		SCMReconcileForm scmReconcileForm = (SCMReconcileForm) invocationContext.screenModel;
		
		SCMReconcileSession scmReconcileSession = getScreenSession(MODULE,
				SCREENID);
		SCMULDErrorLogSession uldErrorLogSession=getScreenSession(MODULE,SCREENID_ULDERRORLOG);
		
		log.log(Log.FINE, "SELECTED ROW INDEX------>", scmReconcileForm.getRowIndex());
		Page<ULDSCMReconcileVO> scmReconcileVOs = scmReconcileSession
				.getSCMReconcileVOs();
		ULDSCMReconcileVO selectedReconcileVO = scmReconcileVOs.get(Integer
				.parseInt(scmReconcileForm.getRowIndex()));
		SCMMessageFilterVO scmFilterVO = new SCMMessageFilterVO();
		scmFilterVO.setAirportCode(selectedReconcileVO.getAirportCode());
		scmFilterVO.setCompanyCode(selectedReconcileVO.getCompanyCode());
		scmFilterVO.setStockControlDate(selectedReconcileVO.getStockCheckDate());
		scmFilterVO.setSequenceNumber(selectedReconcileVO.getSequenceNumber());
		scmFilterVO.setFlightCarrierIdentifier(selectedReconcileVO.getAirlineIdentifier());
		scmFilterVO.setAirlineCode(scmReconcileSession.getMessageFilterVO().getAirlineCode());
		//scmFilterVO.setPageNumber(1);   - Commented by Sreekumar S
		scmReconcileForm.setRowIndex("");
		uldErrorLogSession.setSCMULDFilterVO(scmFilterVO);
		uldErrorLogSession.setPageUrl("fromScmReconcile");
		invocationContext.target = LIST_SUCCESS;

	}

}
