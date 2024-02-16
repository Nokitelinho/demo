/*
 * AddDespatchesCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AddDespatchesCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddAcceptMailCommand","execute");
    	  
//    	MailAcceptanceForm mailAcceptanceForm = 
  //  		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
    	ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
    	    	
	    Collection<DespatchDetailsVO> despatchDtlsVOs =  containerDetailsVO.getDesptachDetailsVOs();
	    
//		Check for same OOE and DOE for despatches
		int sameOE = 0;
		Collection<DespatchDetailsVO> despatchVOs = containerDetailsVO.getDesptachDetailsVOs();
		if(despatchVOs != null && despatchVOs.size() > 0){
	  		for(DespatchDetailsVO despatchVO:despatchVOs){
  				String ooe = despatchVO.getOriginOfficeOfExchange();	 
		    	String doe = despatchVO.getDestinationOfficeOfExchange();
		    	if(ooe.trim().length() == 6){
			      if(doe.trim().length() == 6){
			        if (ooe.equals(doe)) {  
		        	   sameOE = 1;
		        	   String pk = new StringBuilder(despatchVO.getOriginOfficeOfExchange())
        							.append(despatchVO.getDestinationOfficeOfExchange())
        							//.append(despatchVO.getMailClass())
        							//added by anitha for change in pk
        							.append(despatchVO.getMailCategoryCode())
        							.append(despatchVO.getMailSubclass())
        							.append(despatchVO.getYear())
        							.append(despatchVO.getDsn()).toString();
		        			 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",new Object[]{pk}));
		        	}
			      }
			    }
	  		}
	  	}
		if(sameOE != 1){
			
			Collection<DespatchDetailsVO> newDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();
	    	DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
	    	despatchDetailsVO.setOperationalFlag("I");
	    	despatchDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	despatchDetailsVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
	    	String year = (new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true)).toDisplayDateOnlyFormat();
	    	despatchDetailsVO.setYear(Integer.parseInt(year.substring(year.length()-1,year.length())));
	    	despatchDetailsVO.setAcceptedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	    	despatchDetailsVO.setConsignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	    	if(despatchDtlsVOs != null && despatchDtlsVOs.size() > 0){
	    		despatchDtlsVOs.add(despatchDetailsVO);
	    		newDespatchDetailsVOs.addAll(despatchDtlsVOs);
	    	}else{
	    		newDespatchDetailsVOs.add(despatchDetailsVO);
	    	}
	    	containerDetailsVO.setDesptachDetailsVOs(newDespatchDetailsVOs);
	    	mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
		}
    	invocationContext.target = TARGET;
       	log.exiting("AddAcceptMailCommand","execute");
    	
    }
       
}
