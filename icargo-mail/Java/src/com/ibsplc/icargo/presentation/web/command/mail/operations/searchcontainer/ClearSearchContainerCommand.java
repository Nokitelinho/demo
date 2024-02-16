/*
 * ClearSearchContainerCommand.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class ClearSearchContainerCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "clear_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String OUTBOUND = "O";
   private static final String CONST_SEARCH_ALL = "All";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearSearchContainerCommand","execute");
    	  
    	SearchContainerForm searchContainerForm = 
    		(SearchContainerForm)invocationContext.screenModel;
    	SearchContainerSession searchContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Page<ContainerVO> containerVOs = null; 
		SearchContainerFilterVO searchContainerFilterVO = 
			                          new SearchContainerFilterVO();
		searchContainerFilterVO.setOperationType(OUTBOUND);
		searchContainerFilterVO.setStrFromDate((new LocalDate(logonAttributes.getAirportCode(),
    			Location.ARP,true)).toDisplayDateOnlyFormat());
		searchContainerFilterVO.setStrToDate((new LocalDate(logonAttributes.getAirportCode(),
    			Location.ARP,true)).toDisplayDateOnlyFormat());
		searchContainerSession.setSearchContainerFilterVO(searchContainerFilterVO);
		searchContainerSession.setListContainerVOs(containerVOs);
		searchContainerForm.setDeparturePort(logonAttributes.getAirportCode());
    	searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	searchContainerForm.setAssignedto(CONST_FLIGHT);
    	searchContainerForm.setAssignedTo(CONST_SEARCH_ALL);
    	searchContainerForm.setOperationTypeAll(OUTBOUND);
    	searchContainerForm.setDeparturePort(logonAttributes.getAirportCode());
    	searchContainerForm.setStatus("");
    	searchContainerForm.setOperationType(OUTBOUND);
    	searchContainerForm.setContainerNo("");
    //	Added by A-5945 for ICRD-100649 starts 
    	searchContainerFilterVO.setShowEmptyContainer("Y");     
        //	Added by A-5945 for ICRD-100649 ends   
    	searchContainerSession.setParentScreen(null);
    	invocationContext.target = TARGET;
       	
    	log.exiting("ClearSearchContainerCommand","execute");
    	
    }
       
}
