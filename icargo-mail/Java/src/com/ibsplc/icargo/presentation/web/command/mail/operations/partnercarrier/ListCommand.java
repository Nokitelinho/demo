/*
 * ListCommand.java Created on August 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.partnercarrier;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PartnerCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ListCommand extends BaseCommand {

	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	private Log log = LogFactory.getLogger("MailTracking,PartnerCarrier");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.partnercarriers";
	
	private static final String ARP_EMPTY = 
		"mailtracking.defaults.partnercarrier.msg.err.arpEmpty";
	private static final String NO_MATCH = "mailtracking.defaults.noresultsfound";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	PartnerCarriersForm partnerCarriersForm =
							(PartnerCarriersForm)invocationContext.screenModel;
		PartnerCarrierSession partnerCarrierSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	   
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    ArrayList<PartnerCarrierVO> partnerCarrierVOs = null;
	    
	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    
	    if(("").equals(partnerCarriersForm.getAirport())) {
	    	log.log(Log.FINE, "\n\n ARP_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(ARP_EMPTY);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	partnerCarrierSession.setPartnerCarrierVOs(null);
	    	partnerCarriersForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
	    }
	    
	    
	    	String airport = partnerCarriersForm.getAirport().toUpperCase().trim();
	    	try{
	    		new AreaDelegate().validateAirportCode(companyCode,airport);
	    	}catch(BusinessDelegateException businessDelegateException){
	    		errors = handleDelegateException(businessDelegateException);
	    		invocationContext.addAllError(errors);
	    		partnerCarrierSession.setPartnerCarrierVOs(null);
		    	partnerCarriersForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	   
	    	 partnerCarriersForm.setDisableSave("N");
	    try {
	    	partnerCarrierVOs = (ArrayList<PartnerCarrierVO>)delegate.findAllPartnerCarriers(companyCode,
	    									logonAttributes.getOwnAirlineCode(),
	    																airport);
	    	log.log(Log.FINE, "\n\n partnerCarrierVOs=========>",
					partnerCarrierVOs);
			if(partnerCarrierVOs == null || partnerCarrierVOs.size()==0) {
	    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
				ErrorVO error = new ErrorVO(NO_MATCH);
				errors.add(error);
				invocationContext.addAllError(errors);
				partnerCarrierSession.setPartnerCarrierVOs(null);
		    	partnerCarriersForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	    }catch(BusinessDelegateException businessDelegateException) {
	    	
	    	errors = handleDelegateException(businessDelegateException);
	    }
		partnerCarrierSession.setAirport(airport);
	    partnerCarrierSession.setPartnerCarrierVOs(partnerCarrierVOs);
	    partnerCarriersForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;

	}

}
