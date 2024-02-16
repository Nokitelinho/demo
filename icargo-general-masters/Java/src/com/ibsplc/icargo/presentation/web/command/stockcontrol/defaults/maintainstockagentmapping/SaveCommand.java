/*
 * SaveCommand.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockagentmapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockAgentMappingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockAgentMappingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2394
 * 
 */
public class SaveCommand extends BaseCommand{
	private static final String SCREEN_ID="stockcontrol.defaults.maintainstockagentmapping";
	private static final String MODULE_CONSTANT="stockcontrol.defaults";
	public static final String INVALID_STOCK_HOLDER = "stockcontrol.defaults.maintainstockagentmapping.error.invalidstockholder";
	public static final String INVALID_AGENT="stockcontrol.defaults.maintainstockagentmapping.error.invalidagent";
	private Log log=LogFactory.getLogger("STOCKCONTROL_DEFAULTS");
	  
	/** @author A-2394
	    * @param invocationContext
	    * @throws CommandInvocationException
	    * @return void
	    */
	public void execute(InvocationContext invocationContext) 
		throws CommandInvocationException {
		log.entering("SaveCommand","execute");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	String lastUpdatedUser = logonAttributes.getUserId();
		MaintainStockAgentMappingForm form=(MaintainStockAgentMappingForm)invocationContext.screenModel;
		MaintainStockAgentMappingSession session=getScreenSession(MODULE_CONSTANT,SCREEN_ID);
		boolean hasValidatedStockHolders=true;
		boolean hasValidatedAgents=true;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
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
		List<StockAgentVO> newList=new ArrayList<StockAgentVO>();
		newList=validateList(session,lists); //Calling the method for validating the collection
		StockControlDefaultsDelegate stockHolderDelegate=new StockControlDefaultsDelegate();
		AgentDelegate agentDelegate=null;
		/* Code Added for validating StockHolders and Agents */
		Collection<String> stockHolderCodes=new ArrayList<String>();
		Collection<String> agentCodes=new ArrayList<String>();
		log.log(Log.INFO, "VOs--->\n", newList);
		for(StockAgentVO tempAgentVO:newList){
			if(StockAgentVO.OPERATION_FLAG_UPDATE.equals(tempAgentVO.getOperationFlag()) ||
					StockAgentVO.OPERATION_FLAG_INSERT.equals(tempAgentVO.getOperationFlag())){
				if(!stockHolderCodes.contains(tempAgentVO.getStockHolderCode())){
					stockHolderCodes.add(tempAgentVO.getStockHolderCode());	
				}
				if(!agentCodes.contains(tempAgentVO.getAgentCode())){
					agentCodes.add(tempAgentVO.getAgentCode());	
				}
				 
			}
		}
		//Added by A-1927 starts
		boolean isEmpty = false;
		if(newList == null || newList.size()==0){
			isEmpty = true;
		}else if(newList.size()==1){
			for(StockAgentVO tempAgentVO:newList){
				if(tempAgentVO.getStockHolderCode() == null || tempAgentVO.getStockHolderCode().trim().length()==0){
					isEmpty = true;
				}else if(tempAgentVO.getAgentCode() == null || tempAgentVO.getAgentCode().trim().length()==0){
					isEmpty = true;
				}
			}
		}
		if(isEmpty){
			errorVO=new ErrorVO("stockholder.defaults.maintainstockagentmapping.err.plzenterstockagentdetails");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target="screenload_success";
			return;
		}
		//Added by A-1927 ends
		if(stockHolderCodes.size()>0){
		try{
			stockHolderDelegate.validateStockHolders(companyCode,stockHolderCodes);
		}catch(BusinessDelegateException businessException){
			errorVO=new ErrorVO(INVALID_STOCK_HOLDER);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			hasValidatedStockHolders=false;
		}
		try{
			agentDelegate=new AgentDelegate();
			agentDelegate.validateAgents(companyCode,agentCodes);
		}catch(BusinessDelegateException businessException){
			errorVO=new ErrorVO(INVALID_AGENT);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			hasValidatedAgents=false;
		}
		}
		if(hasValidatedAgents && hasValidatedStockHolders){
		try{	
			stockHolderDelegate.saveStockAgentMappings(newList);
			session.removeAllAttributes();
			form.setStockHolder("");
			form.setAgent("");
		errorVO=new ErrorVO("stockholder.defaults.MaintainStockAgentMapping.info.SaveSuccessful");
		errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
		errors.add(errorVO);
		}
		catch(BusinessDelegateException businessDelegateException) {			
			errorVO=new ErrorVO("stockholder.defaults.MaintainStockAgentMapping.error.DuplicateData");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		}
		
		if(errors.size()>0) {
			invocationContext.addAllError(errors);
		}
		
		form.setIsErrorPresent(false);	
		invocationContext.target="screenload_success";
		log.exiting("SaveCommand","execute");
	}	
	
/*
 * This method checks whether there is any change in the PK
 * if change is present then two VOs are sent to server for
 * deleting the previous entry and inserting the new entry
 */
	private List<StockAgentVO> validateList(MaintainStockAgentMappingSession session,List<StockAgentVO> lists) {
	
		Map<String,StockAgentVO> map=session.getStockHolderAgentMappingOriginal();
		List<StockAgentVO> newList=new ArrayList<StockAgentVO>();
		String key=null;
		
		for(StockAgentVO agentVO:lists) {
			if(StockAgentVO.OPERATION_FLAG_INSERT.equals(agentVO.getOperationFlag()) || 
					StockAgentVO.OPERATION_FLAG_DELETE.equals(agentVO.getOperationFlag())) {
				
				newList.add(agentVO);
			} 
			else if(StockAgentVO.OPERATION_FLAG_UPDATE.equals(agentVO.getOperationFlag()))
			{
			key=agentVO.getId();  // getting the key set in the VO , key set in the ListCommand
			StockAgentVO tempVO=new StockAgentVO(); 
			tempVO=map.get(key);
			if(tempVO!=null && tempVO.getAgentCode().equals(agentVO.getAgentCode())) {
				newList.add(agentVO);	
			}
			else if(tempVO!=null && !tempVO.getAgentCode().equals(agentVO.getAgentCode())) {
	
				tempVO.setOperationFlag(StockAgentVO.OPERATION_FLAG_DELETE);
				agentVO.setOperationFlag(StockAgentVO.OPERATION_FLAG_INSERT);
				tempVO.setLastUpdateUser(agentVO.getLastUpdateUser());
				newList.add(tempVO);
				newList.add(agentVO);
			}
			}
		}
		/*
		for(StockAgentVO agentVO:newList) {
			System.out.println("*********************************************");
			System.out.println("Agent Code : "+agentVO.getAgentCode());
			System.out.println("Stock Holder Code : "+agentVO.getStockHolderCode());
			System.out.println("Operational Flag : "+agentVO.getOperationFlag());
			System.out.println("*********************************************");
			
		} */
		return newList;
	}
}
