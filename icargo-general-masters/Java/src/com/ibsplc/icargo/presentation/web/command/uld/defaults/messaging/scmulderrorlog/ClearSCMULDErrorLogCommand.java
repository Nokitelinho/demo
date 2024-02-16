/* ClearSCMULDErrorLogCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmulderrorlog;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * 
 * @author A-1496
 * 
 */
public class ClearSCMULDErrorLogCommand extends BaseCommand {
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String BLANK = "";

	private static final String GHA = "GHA";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		SCMULDErrorLogForm scmUldReconcileForm = (SCMULDErrorLogForm) invocationContext.screenModel;
		SCMULDErrorLogSession scmUldSession = (SCMULDErrorLogSession) getScreenSession(
				MODULE, SCREENID);
		scmUldReconcileForm.setScmUldAirport(BLANK);
		scmUldReconcileForm.setStockCheckdate(BLANK);
		scmUldReconcileForm.setDisplayPage("1");
		scmUldReconcileForm.setLastPageNum("0");
		scmUldReconcileForm.setUldNumber(BLANK);
		scmUldReconcileForm.setScmSeqNo(BLANK);
		scmUldReconcileForm.setCurrentPageNumber("1");
		scmUldReconcileForm.setScmStockCheckTime(BLANK);
		scmUldSession.setSCMReconcileDetailVOs(null);
		scmUldSession.setSCMULDFilterVO(null);
		scmUldSession.setPageUrl(null);
		
		SCMMessageFilterVO filterVO = new SCMMessageFilterVO();    	
    	filterVO.setAirportCode(logonAttributes.getAirportCode());
    	
    	/*   changed by A-5142 for ICRD-16062 */	
    	if(logonAttributes.isGHAUser()){
    		scmUldReconcileForm.setScmULDDisable(GHA);  
    	}else{
    		scmUldReconcileForm.setScmULDDisable(BLANK);  
    	}
    	
    	scmUldSession.setSCMULDFilterVO(filterVO);
    	scmUldReconcileForm.setListStatus(BLANK);
	   	// Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471)
      	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
      	
      	/*   changed by A-5142  */
			if(logonAttributes.isAirlineUser()){
				scmUldReconcileForm.setAirline(logonAttributes.getOwnAirlineCode()); 
		}else{
			scmUldReconcileForm.setAirline(BLANK);
			}
	
		//Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471) ends
		invocationContext.addAllError(error);
		invocationContext.target = CLEAR_SUCCESS;
	}
}
