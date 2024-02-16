/*
 * ListAgentDetailsCommand.java Created on March 31, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customerregistration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * To list the agent details
 *
 * @author A-5791
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			March 31, 2014				   A-5791			First Draft
 */
public class ListAgentDetailsCommand extends BaseCommand{

	 
	 private static final String ACTION_SUCCESS = "action_success";
	 private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT DEFAULTS");
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		
		log.entering("ListAgentDetailsCommand", "execute");
		MaintainRegCustomerForm form =
	            (MaintainRegCustomerForm)invocationContext.screenModel;
		
		 AgentDelegate agentDelegate = new AgentDelegate();
		 AgentVO agentVO = null;
		 String agentCode = form.getCustomerAgentCode().toUpperCase();
		 Map<String, AgentVO> agentValidationMap = null;
		 Collection<String> agentCodes = new ArrayList<String>();
	        agentCodes.add(agentCode);
		 String stationCodeFOrLocalDate = form.getStation();
		 
		 LocalDate ldate=new LocalDate(
				  stationCodeFOrLocalDate, Location.ARP, false);
		 try {
			agentValidationMap = agentDelegate.validateAgentsWithDate(getApplicationSession().getLogonVO().getCompanyCode(),agentCodes,
			 		ldate);
		} catch (BusinessDelegateException e) {
			 invocationContext.addAllError(handleDelegateException(e));
			// TODO Auto-generated catch block
			
		}
		if(agentValidationMap != null && agentValidationMap.size() >0) {
 			agentVO = agentValidationMap.get(agentCode);
			log.log(Log.FINE, "\n\nAgent VO frm validateAgentwithdate----->",
					agentVO);
 		}
		if(agentVO != null) {
            form.setCustomerAgentName(agentVO.getAgentName());
            
		}
		 log.exiting("ListAgentDetailsCommand", "execute");

	        invocationContext.target = ACTION_SUCCESS;   
		}
	}
	


