/*
 * ListActionHistoryCommand.java Created on May, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
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
 * This command class is invoked on the 
 * 	list button the ULDMovementHistory screen
 * 
 * @author A-3093
 */
public class ListActionHistoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD_MOVEMENT_HISTORY");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.misc.listuldmovement";

	private static final String LIST_SUCCESS = "list_success";

	/** 
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ListULDMovementHistoryCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		ListULDMovementForm listULDMovementForm = (ListULDMovementForm) invocationContext.screenModel;
		ListULDMovementSession listUldMovementSession = getScreenSession(
				MODULE, SCREENID);
		
		ULDMovementFilterVO uldMovementFilterVO = listUldMovementSession
				.getUldMovementFilterVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		uldMovementFilterVO.setCompanyCode(companyCode);
		uldMovementFilterVO.setUldNumber(uldMovementFilterVO.getUldNumber().toUpperCase());
		

		listUldMovementSession.setUldMovementFilterVO(uldMovementFilterVO);
		
		
		Page<AuditDetailsVO> auditDetailsVO = new Page<AuditDetailsVO>(
				new ArrayList<AuditDetailsVO>(), 0, 0, 0, 0, 0, false);

		String displayPage = null;

		displayPage = listULDMovementForm.getDisplayPage();

		if ("1".equals(listULDMovementForm.getDisplayPageFlag())) {
			displayPage = "1";
			listULDMovementForm.setDisplayPageFlag("");
		}
		log.log(Log.INFO, "form.getDisplayPage()--------- ",
				listULDMovementForm.getDisplayPage());
		int pageNumber = Integer.parseInt(displayPage);
		
		uldMovementFilterVO.setPageNumber(pageNumber);
		try {

			auditDetailsVO = new ULDDefaultsDelegate().findULDActionHistory(uldMovementFilterVO);

			log.log(Log.INFO, "repairDetailVOs returned ---->", auditDetailsVO);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			
		}
		
		listUldMovementSession.setAuditDetailsVO(auditDetailsVO);
		log.log(Log.INFO, "session returned ---->", listUldMovementSession.getAuditDetailsVO());
		invocationContext.target = LIST_SUCCESS;
	}
	

}
