/*
 * RefreshSearchContainerCommand.java 
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * Author(s)			: A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;
import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.SEARCH_MODE_ALL;
import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.SEARCH_MODE_DESTN;
import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.SEARCH_MODE_FLIGHT;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3251
 *
 */
public class RefreshSearchContainerCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "refresh_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	  
	   private static final String CONST_SEARCH_ALL = "ALL";
	   private static final String CONST_SEARCH_DEST = "DESTN";
	   private static final String CONST_SEARCH_FLIGHT = "FLT";
	    
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("RefreshSearchContainerCommand","execute");
	    	  
	    	SearchContainerForm searchContainerForm = 
	    		(SearchContainerForm)invocationContext.screenModel;
	    	SearchContainerSession searchContainerSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);	    	
			SearchContainerFilterVO searchContainerFilterVO = new SearchContainerFilterVO();
			
			
			searchContainerFilterVO = searchContainerSession.getSearchContainerFilterVO(); 
			searchContainerForm.setContainerNo(searchContainerFilterVO.getContainerNumber());
			searchContainerForm.setFromDate(searchContainerFilterVO.getStrFromDate());
			searchContainerForm.setToDate(searchContainerFilterVO.getStrToDate());
			searchContainerForm.setDeparturePort(searchContainerFilterVO.getDeparturePort());
			searchContainerForm.setAssignedBy(searchContainerFilterVO.getAssignedUser());			
			searchContainerForm.setTransferable(searchContainerFilterVO.getTransferStatus());
			searchContainerForm.setReList("Y");
			
			String assignedTo = searchContainerFilterVO.getSearchMode();
			
			  if (CONST_SEARCH_ALL.equals(assignedTo)) {
				  searchContainerForm.setAssignedTo(SEARCH_MODE_ALL);		  
				  String oprType = searchContainerFilterVO.getOperationType();
				  if (oprType != null && oprType.trim().length() > 0) {
					  searchContainerForm.setOperationType(oprType);
					}
					
			  }else if (CONST_SEARCH_DEST.equals(assignedTo)) {
				  searchContainerForm.setAssignedTo(SEARCH_MODE_DESTN);
				  searchContainerForm.setCarrier(searchContainerFilterVO.getCarrierCode());
				  searchContainerForm.setDestination(searchContainerFilterVO.getFinalDestination());				  
			  }else if (CONST_SEARCH_FLIGHT.equals(assignedTo)) {
				  searchContainerForm.setAssignedTo(SEARCH_MODE_FLIGHT);		  
				  String oprType = searchContainerFilterVO.getOperationType();
				  if (oprType != null 
							&& oprType.trim().length() > 0) {
					  searchContainerForm.setOperationType(oprType);
					  searchContainerForm.setFlightCarrierCode(searchContainerFilterVO.getFlightCarrierCode());
					  searchContainerForm.setFlightDate(searchContainerFilterVO.getStrFlightDate());
					  searchContainerForm.setFlightNumber(searchContainerFilterVO.getFlightNumber());					  
					}
			  }  
			 
			searchContainerForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);				
	    	invocationContext.target = TARGET;	       	
	    	log.exiting("RefreshSearchContainerCommand","execute");
	    	
	    }
	    
}