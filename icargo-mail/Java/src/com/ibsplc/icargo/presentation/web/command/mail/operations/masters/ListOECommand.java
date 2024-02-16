/*
 * ListOECommand.java Created on June 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OfficeOfExchangeMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ListOECommand extends BaseCommand {

	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	private Log log = LogFactory.getLogger("ListOECommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
							"mailtracking.defaults.masters.officeofexchange";
	
	private static final String NO_MATCH = 
							"mailtracking.defaults.oemaster.msg.err.noMatch";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	OfficeOfExchangeMasterForm oeMasterForm =
					(OfficeOfExchangeMasterForm)invocationContext.screenModel;
    	OfficeOfExchangeMasterSession oeSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
	    
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    Page<OfficeOfExchangeVO> oeVOs = null;
	    
	    MailTrackingDefaultsDelegate delegate = 
	    								new MailTrackingDefaultsDelegate();

	    String oe = "";
	    
	    if(!("").equals(oeMasterForm.getOfficeOfExchange())) {
	    	oe = oeMasterForm.getOfficeOfExchange().toUpperCase();
	    }
	    
	    try {
	    	oeVOs = delegate.findOfficeOfExchange
	    					(companyCode,oe,Integer.parseInt
	    									(oeMasterForm.getDisplayPage()));
	    	log.log(Log.FINE, "\n\n oeVOs=========>", oeVOs);
			if(oeVOs == null || oeVOs.size()==0) {
	    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
				ErrorVO error = new ErrorVO(NO_MATCH);
				errors.add(error);
				invocationContext.addAllError(errors);
				oeSession.setOfficeOfExchangeVOs(null);
		    	oeMasterForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	    }catch(BusinessDelegateException businessDelegateException) {
	    	handleDelegateException(businessDelegateException);
	    }
	    
	    oeSession.setOfficeOfExchangeVOs(oeVOs);
	    oeMasterForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;

	}


}
