/*
 * DeleteRowCommand.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockagentmapping;

import java.util.ArrayList;


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
public class DeleteRowCommand extends BaseCommand{
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
		log.entering("DeleteRowCommand","execute");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	String lastUpdatedUser = logonAttributes.getUserId();
		MaintainStockAgentMappingForm form=(MaintainStockAgentMappingForm)invocationContext.screenModel;
		MaintainStockAgentMappingSession session=getScreenSession(MODULE_CONSTANT,SCREEN_ID);
		String checkBoxes[]=form.getCheckbox();
		String agents[]=form.getAgents();
		String stockHolders[]=form.getStockHolders();
	    ArrayList<StockAgentVO> lists=new ArrayList<StockAgentVO>();
		Page<StockAgentVO> pages=session.getStockHolderAgentMapping();
		ArrayList<StockAgentVO> newLists=null;

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
			/*
			 *Creating a new ArrayList for storing the rows to be deleted
			 */
			newLists= new ArrayList<StockAgentVO>();

		if(checkBoxes!=null && checkBoxes.length>0) {
			for(int i=0;i<checkBoxes.length;i++) {
				StockAgentVO agentVO=lists.get(Integer.parseInt(checkBoxes[i]));
				if(StockAgentVO.OPERATION_FLAG_INSERT.equals(agentVO.getOperationFlag())) {

					newLists.add(agentVO);
					newLists.trimToSize();

				}else {
					agentVO.setOperationFlag(StockAgentVO.OPERATION_FLAG_DELETE);
					lists.remove(agentVO);
					lists.add(Integer.parseInt(checkBoxes[i]),agentVO);
				}
			}
		}
		/*
		 * Removing from the main collection
		 */
	lists.removeAll(newLists);
	lists.trimToSize();
	Page<StockAgentVO> newPages=new Page<StockAgentVO>(lists,1,25,lists.size(),0,0,false);
	session.setStockHolderAgentMapping(newPages);
	invocationContext.target="screenload_success";
	log.exiting("DeleteRowCommand","execute");

	}
}
