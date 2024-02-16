/*
 * ClearCommand.java Created on July 01, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ClearCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "clear_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListCommand","execute");

    	DsnEnquiryForm dsnEnquiryForm =
    		(DsnEnquiryForm)invocationContext.screenModel;
    	DsnEnquirySession dsnEnquirySession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		dsnEnquiryForm.setCategory("");
		dsnEnquiryForm.setConsignmentNo("");
		dsnEnquiryForm.setContainerType("");
		dsnEnquiryForm.setDestnCity("");
		dsnEnquiryForm.setDsn("");
		dsnEnquiryForm.setFlightDate("");
		dsnEnquiryForm.setFlightNumber("");
		dsnEnquiryForm.setMailClass("");
		dsnEnquiryForm.setOriginCity("");
		dsnEnquiryForm.setPlt(false);
		dsnEnquiryForm.setUldNo("");
		dsnEnquiryForm.setStatus("");
		dsnEnquiryForm.setDisplayPage("");
		dsnEnquiryForm.setLastPageNum("");
		dsnEnquiryForm.setPostalAuthorityCode("");
		dsnEnquiryForm.setCapNotAcp(false);
		dsnEnquiryForm.setTransit("");

		dsnEnquiryForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
		dsnEnquiryForm.setFromDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dsnEnquiryForm.setToDate(currentdate.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		dsnEnquiryForm.setPort(logonAttributes.getAirportCode());
		dsnEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);

		dsnEnquirySession.setDespatchDetailsVOs(null);
		dsnEnquirySession.setDsnEnquiryFilterVO(null);
		dsnEnquirySession.setSelectedDespatchDetailsVOs(null);

    	invocationContext.target = TARGET_SUCCESS;

    	log.exiting("ClearCommand","execute");

    }

}
