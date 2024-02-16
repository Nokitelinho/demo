/*
 * ClearListUldDiscrepancyCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 */
public class ClearListUldDiscrepancyCommand extends BaseCommand {

	private static final String SCREENID = "uld.defaults.listulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private Log log = LogFactory.getLogger("ClearListUldDiscrepancyCommand");

	private static final String CLEAR_SUCCESS = "clear_success";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearListUldDiscrepancyCommand", "ENTRY");
		ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		
		ListULDDiscrepanciesForm form =
			(ListULDDiscrepanciesForm) invocationContext.screenModel;
		ListUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		form.setUldNo("");
		form.setAirlineCode("");
		form.setReportingStation("");
		form.setOwnerStation("");
		form.resetForm();
		session.setUldNumber("");
		session.setMode("");
		session.removeULDDiscrepancyFilterVODetails();
		session.removeULDDiscrepancyVODetails();
		ULDDiscrepancyFilterVO filterVO = new ULDDiscrepancyFilterVO();
    	
    	
    	
	   	// Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471)
      	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
      	//removed by nisha on 29Apr08
       	//Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471) ends
		//Added by Tarun for BUG_4647_03Dec08
    	
    		if(logonAttributes.isAirlineUser()){
        		filterVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
        		form.setDiscDisableStat("airline");
        		form.setAirlineCode(logonAttributes.getOwnAirlineCode());
        	}
        	else{
        		filterVO.setReportingStation(logonAttributes.getAirportCode());
        		form.setDiscDisableStat("GHA");
        	}
    	
    	//Added by Tarun for BUG_4647_03Dec08 ends
		session.setULDDiscrepancyFilterVODetails(filterVO);
		log.exiting("ClearListUldDiscrepancyCommand", "EXIT");
		invocationContext.addAllError(error);
		invocationContext.target = CLEAR_SUCCESS;
	}
}