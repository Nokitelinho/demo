/*
 * ClearCommand.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockagentmapping;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class ClearCommand extends BaseCommand {
	
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
		log.entering("ClearCommand","execute");
		MaintainStockAgentMappingForm form=(MaintainStockAgentMappingForm)invocationContext.screenModel;
		MaintainStockAgentMappingSession session=getScreenSession(MODULE_CONSTANT,SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	String lastUpdatedUser = logonAttributes.getUserId();
		/*
		 * Clearing the textfields and setting the default page parameters
		 */
		form.setAgent("");
		form.setStockHolder("");
		form.setDisplayPage("1");
		form.setLastPageNum("0");
		form.setCurrentPage("1");
		form.setIsErrorPresent(false);
		/*
		 * Clearing the table 
		 */
		List<StockAgentVO> stockAgentLists=new ArrayList<StockAgentVO>();
		StockAgentVO stockAgentVO=new StockAgentVO();
		stockAgentVO.setAgentCode("");
		stockAgentVO.setStockHolderCode("");
		stockAgentVO.setOperationFlag(StockAgentVO.OPERATION_FLAG_INSERT);
		stockAgentVO.setCompanyCode(companyCode);
		stockAgentVO.setLastUpdateUser(lastUpdatedUser);
		/*
		 * Making changes in the original collection 
		 * and setting it into session
		 */
		Map<String,StockAgentVO> map=new HashMap<String,StockAgentVO>();
		map.put(String.valueOf(stockAgentVO.hashCode()),stockAgentVO);
		
		stockAgentLists.add(stockAgentVO);
		Page<StockAgentVO> pages=new Page<StockAgentVO>(stockAgentLists,1,25,stockAgentLists.size(),0,0,false);
		
		session.setStockHolderAgentMappingOriginal(map);
		session.setStockHolderAgentMapping(pages);
		
		invocationContext.target="screenload_success";
		log.exiting("ClearCommand","execute");
				
	}
	
}
