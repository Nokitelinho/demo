/*
 * AddRowCommand.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockagentmapping;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockAgentMappingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockAgentMappingForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2394
 *
 */

public class AddRowCommand extends BaseCommand{
	
	private static final String SCREEN_ID="stockcontrol.defaults.maintainstockagentmapping";
	private static final String MODULE_CONSTANT="stockcontrol.defaults";
	private Log log=LogFactory.getLogger("STOCKCONTROL_DEFAULTS");
	/**
	    * @param invocationContext
	    * @throws CommandInvocationException
	    * @return void
	    */
	public void execute(InvocationContext invocationContext) 
		throws CommandInvocationException {
		log.entering("AddRowCommand","execute");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	String lastUpdatedUser = logonAttributes.getUserId();
		MaintainStockAgentMappingForm form=(MaintainStockAgentMappingForm)invocationContext.screenModel;
		MaintainStockAgentMappingSession session=getScreenSession(MODULE_CONSTANT,SCREEN_ID);
		Page<StockAgentVO> pages=session.getStockHolderAgentMapping(); 
		List<StockAgentVO> lists=new ArrayList<StockAgentVO>();
		String agents[]=form.getAgents();
		String stockHolders[]=form.getStockHolders();
		/*
		 * Upadating the Collection in Session with that of form values 
		 */
		if(pages!=null && pages.size()>0) {
			log.log(Log.FINE,"Pages is not null ");
			int count=0;
			for(StockAgentVO tempAgentVO: pages) {
				if(!tempAgentVO.getOperationFlag().equalsIgnoreCase(StockAgentVO.OPERATION_FLAG_DELETE)){
				if(!tempAgentVO.getAgentCode().equals(agents[count].trim()) || 
						!tempAgentVO.getStockHolderCode().equals(stockHolders[count].trim())) {
					log.log(Log.FINE, "Change---> ", count);
					tempAgentVO.setAgentCode(agents[count].trim().toUpperCase());
					tempAgentVO.setStockHolderCode(stockHolders[count].trim().toUpperCase());
					tempAgentVO.setCompanyCode(companyCode);
					tempAgentVO.setLastUpdateUser(lastUpdatedUser);
					tempAgentVO.setOperationFlag(pages.get(count).getOperationFlag());
						// Checking and setting the operational flag
						if(!tempAgentVO.getOperationFlag().equalsIgnoreCase(StockAgentVO.OPERATION_FLAG_INSERT)){
						tempAgentVO.setOperationFlag(StockAgentVO.OPERATION_FLAG_UPDATE);
					
						}
				}
						
			count+=1;
			}
			lists.add(tempAgentVO);			
		}
		}
		
			StockAgentVO agentVO=new StockAgentVO();
			/*
			 * Adding an extra row 
			 */
			agentVO.setAgentCode("");
			agentVO.setStockHolderCode("");
			agentVO.setOperationFlag(StockAgentVO.OPERATION_FLAG_INSERT);
			agentVO.setCompanyCode(companyCode);
			agentVO.setLastUpdateUser(lastUpdatedUser);
			lists.add(agentVO);	
			pages=new Page<StockAgentVO>(lists,1,25,lists.size(),0,0,false); // setting the size of the pages
			session.setStockHolderAgentMapping(pages);
		
		
		invocationContext.target="screenload_success";
		log.exiting("AddRowCommand","execute");
		
	}
}
