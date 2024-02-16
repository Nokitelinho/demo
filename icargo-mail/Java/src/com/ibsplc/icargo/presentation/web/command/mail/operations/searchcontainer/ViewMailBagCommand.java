/*
 * ViewMailBagCommand.java Created on Oct 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchcontainer;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchContainerForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3429
 *
 */
public class ViewMailBagCommand extends BaseCommand {

	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID_VIEWMAILBAG = "mailtracking.defaults.mailBagEnquiry";	
	   private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";	
	   
	   private static final String TARGET_SUCCESS = "viewmails_success";
	
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ViewMailBagCommand","execute");
	    	SearchContainerForm searchContainerForm = 
	    					(SearchContainerForm)invocationContext.screenModel;
	    	MailBagEnquirySession mailBagEnquirySession = 
	    								getScreenSession(MODULE_NAME,SCREEN_ID_VIEWMAILBAG);
	    	SearchContainerSession searchContainerSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	Page<ContainerVO> containerVOs = searchContainerSession.getListContainerVOs();
	    	
	    	String companyCode = null;
	    	
	    	if (containerVOs != null && containerVOs.isEmpty() == false) {
	    		companyCode = containerVOs.get(0).getCompanyCode();
	    	}
	    	
	    	String[] selectedRow = searchContainerForm.getSelectContainer();
	    	int count = 0; 
	    	if (companyCode != null) {
	    		count = Integer.parseInt(selectedRow[0].substring(companyCode.length(), selectedRow[0].length()));
	    	}
	    	
	    	ContainerVO containerVO = containerVOs.get(count-1);
	    	MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
	    	mailbagEnquiryFilterVO.setCompanyCode(containerVO.getCompanyCode());
	    	//Added By A-5945 for ICRD-135997 starts
	    	if(MailConstantsVO.BULK_TYPE.equals(containerVO.getType())&& MailConstantsVO.OPERATION_INBOUND.equals(searchContainerForm.getOperationType())&&
	    			containerVO.getFlightSequenceNumber()>0 ){
	    		mailbagEnquiryFilterVO.setContainerNumber(new StringBuilder().append("BULK").append("-").append(containerVO.getFinalDestination()).toString());
	    	}else{//Added by A05945 for ICRD-135997 ends
	    	mailbagEnquiryFilterVO.setContainerNumber(containerVO.getContainerNumber());
	    	}
	    	mailbagEnquiryFilterVO.setPageNumber(0);
	    	if(containerVO.getFlightDate()!= null){
	    		mailbagEnquiryFilterVO.setCarrierCode(containerVO.getCarrierCode());
	    		mailbagEnquiryFilterVO.setFlightNumber(containerVO.getFlightNumber());
	    		mailbagEnquiryFilterVO.setFlightDate(containerVO.getFlightDate());
	    	}else{
	    		  mailbagEnquiryFilterVO.setCarrierCode(containerVO.getCarrierCode());
	    		  mailbagEnquiryFilterVO.setFlightNumber(containerVO.getFlightNumber());
	    		if(ContainerVO.FLAG_YES.equals(containerVO.getArrivedStatus())){
		    		mailbagEnquiryFilterVO.setScanPort(containerVO.getPou());
		    	}else{
		    		mailbagEnquiryFilterVO.setScanPort(containerVO.getAssignedPort());
		    	}	
	    	}
	    	    	
	    	mailBagEnquirySession.setMailbagEnquiryFilterVO(mailbagEnquiryFilterVO);
	    	invocationContext.target = TARGET_SUCCESS;
	       	
	    	log.exiting("ViewMailBagCommand","execute");

	}

}
