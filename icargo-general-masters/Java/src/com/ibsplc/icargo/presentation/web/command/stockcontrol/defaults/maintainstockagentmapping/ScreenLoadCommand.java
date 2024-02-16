/*
 * ScreenLoadCommand.java Created on Oct 14, 2006
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
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2394
 * 
 */
public class ScreenLoadCommand extends BaseCommand {

private Log log=LogFactory.getLogger("STOCKCONTROL_DEFAULTS");	
private static final String SCREEN_ID="stockcontrol.defaults.maintainstockagentmapping";
private static final String MODULE_CONSTANT="stockcontrol.defaults";
/**
 * @param invocationContext
 * @throws CommandInvocationException
 * @return void
 */
public void execute(InvocationContext invocationContext)
throws CommandInvocationException {
	log.entering("ScreenLoadCommand","execute");
	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
	String companyCode = logonAttributes.getCompanyCode();
	String lastUpdatedUser = logonAttributes.getUserId();
	MaintainStockAgentMappingSession session=getScreenSession(MODULE_CONSTANT,SCREEN_ID);		
	List<StockAgentVO> stockAgentLists=new ArrayList<StockAgentVO>();
	/*
	 * Adding an empty row into the session
	 */
	StockAgentVO stockAgentVO=new StockAgentVO();
	stockAgentVO.setAgentCode("");
	stockAgentVO.setStockHolderCode("");
	stockAgentVO.setLastUpdateUser(lastUpdatedUser);
	stockAgentVO.setCompanyCode(companyCode);
	stockAgentVO.setOperationFlag(stockAgentVO.OPERATION_FLAG_INSERT);
	stockAgentLists.add(stockAgentVO);
	Page<StockAgentVO> pages=new Page<StockAgentVO>(stockAgentLists,1,25,stockAgentLists.size(),0,0,false);
	session.setStockHolderAgentMapping(pages);
			
	invocationContext.target="screenload_success";
	log.exiting("ScreenLoadCommand","execute");
}
}
