/*
 * ListCommand.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockagentmapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

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
public class ListCommand extends BaseCommand {
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
		log.entering("ListCommand","execute");
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
    	LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	String lastUpdatedUser = logonAttributes.getUserId();
		MaintainStockAgentMappingForm form=(MaintainStockAgentMappingForm)invocationContext.screenModel;
		MaintainStockAgentMappingSession session=getScreenSession(MODULE_CONSTANT,SCREEN_ID);
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		/*
		 * Checking whether and errors due to unsaved data is present
		 */
		if(!form.getIsErrorPresent()) {
			/*
			 * Setting the filter parameters from the screen
			 */
		StockAgentFilterVO agentVO=new StockAgentFilterVO();
		agentVO.setAgentCode(form.getAgent().toUpperCase().trim());
		agentVO.setCompanyCode(companyCode);
		agentVO.setStockHolderCode(form.getStockHolder().toUpperCase().trim());
		agentVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		StockControlDefaultsDelegate delegate=new StockControlDefaultsDelegate();
		Page<StockAgentVO> pages=null;
		try{
	    pages=delegate.findStockAgentMappings(agentVO);
	    storeOriginal(session,pages,companyCode);	//For Storing The Original Copy of the Page obtained from the server
	    session.setStockHolderAgentMapping(pages);
		}
		catch(BusinessDelegateException businessDelegateException) {
		log.log(Log.SEVERE,"Business Delegate Exception ");	
			}
		if(pages==null || pages.size()==0){
			ErrorVO errorVO=new ErrorVO("stockholder.defaults.MaintainStockAgentMapping.error.NoData");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		}else {
			log.log(Log.INFO,"Error Due to unsaved Data ...Listing Unsucessful");
			ErrorVO error=new ErrorVO("stockholder.defaults.MaintainStockAgentMapping.error.UnSavedData");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
		}
		if(errors!=null && errors.size()>0) {
			invocationContext.addAllError(errors);		
		}
		
		invocationContext.target="screenload_success";
		log.exiting("ListCommand","execute");
		
	}
	/*
	 * Method for storing the original copy of the collection
	 * 	obtained from the database
	 */
	private void storeOriginal(MaintainStockAgentMappingSession session,Page<StockAgentVO> pages,
				String companyCode) {
		Map<String,StockAgentVO> map=new HashMap<String,StockAgentVO>();
		
		if(pages!=null) {		
		for(StockAgentVO agentVO:pages) {
			/*
			 * Creating the unique key and setting it into the VO 
			 * for uniquely identifying an object in the collection
			 */
			StringBuffer idTemp=new StringBuffer().append(agentVO.getCompanyCode())
								.append(agentVO.getAgentCode()).append(agentVO.getStockHolderCode());
			String id=new String(idTemp); 
			agentVO.setId(id);	
			agentVO.setCompanyCode(companyCode);
			StockAgentVO stockAgentVO=new StockAgentVO();
			stockAgentVO.setCompanyCode(companyCode);
			stockAgentVO.setAgentCode(agentVO.getAgentCode());
			stockAgentVO.setStockHolderCode(agentVO.getStockHolderCode());
			stockAgentVO.setId(agentVO.getId());
			stockAgentVO.setLastUpdateTime(agentVO.getLastUpdateTime());
			map.put(stockAgentVO.getId(),stockAgentVO);			
			
		}
		session.setStockHolderAgentMappingOriginal(map); // Adding the map into the session 
		}
		
	}

}
