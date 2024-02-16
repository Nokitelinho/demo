/*
 * PrintDocumentScreenloadCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailflightsummary;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ControlDocumentVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailFlightSummarySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailFlightSummaryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class PrintDocumentScreenloadCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";

   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailflightsummary";	

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("PrintDocumentScreenloadCommand","execute");

    	MailFlightSummaryForm mailFlightSummaryForm = 
    		(MailFlightSummaryForm)invocationContext.screenModel;
    	MailFlightSummarySession mailFlightSummarySession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	OperationalFlightVO operationalFlightVO = mailFlightSummarySession.getOperationalFlightVO();
    	log.log(Log.FINE, "operationalFlightVO---------->>",
				operationalFlightVO);
		ControlDocumentVO controlDocumentVO = new ControlDocumentVO();
    	try {			
    		controlDocumentVO = new MailTrackingDefaultsDelegate().findControlDocumentsForPrint(operationalFlightVO);
		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}	
		
		mailFlightSummaryForm.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		mailFlightSummaryForm.setCarrierID(operationalFlightVO.getCarrierId());
		mailFlightSummaryForm.setFlightNumber(operationalFlightVO.getFlightNumber());
		mailFlightSummaryForm.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
		mailFlightSummaryForm.setFlightDate(operationalFlightVO.getFlightDate().toDisplayFormat());
		
		Map<String,Collection<String>> documentReportsMap = controlDocumentVO.getDocumentReportsMap();
		if(documentReportsMap != null ){
			for(String key:documentReportsMap.keySet()){
				mailFlightSummaryForm.setPrintDocType(key);
				break;
			}
		}
		
		mailFlightSummarySession.setDocumentMap(controlDocumentVO.getDocumentReportsMap());
		log.log(Log.FINE,
				"controlDocumentVO.getDocumentReportsMap()---------->>",
				controlDocumentVO.getDocumentReportsMap());
		mailFlightSummaryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		
    	invocationContext.target = TARGET;

    	log.exiting("PrintDocumentScreenloadCommand","execute");

    }

}
