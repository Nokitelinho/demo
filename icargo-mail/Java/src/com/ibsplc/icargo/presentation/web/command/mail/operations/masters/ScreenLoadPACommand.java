/*
 * ScreenLoadPACommand.java Created on June 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PostalAdministrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ScreenLoadPACommand extends BaseCommand {

	private static final String SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger("ScreenLoadPACommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
						"mailtracking.defaults.masters.postaladministration";
	
	private static final String MAIL_CATAGORY = "mailtracking.defaults.mailcategory";
	private static final String MAIL_BASETYPE="mailtracking.defaults.basistype";
	private static final String MAIL_BILLINGSOURCE="mailtracking.defaults.billingsource";
	private static final String MAIL_RESDITVERSION="mailtracking.defaults.postaladministration.resditversion";
	private static final String MAIL_BILLINGFREQUENCY="mailtracking.defaults.billingfrequency";
	private static final String MAIL_PARCODE="mailtracking.defaults.parcod";
	private static final String MAIL_LATVAL_LEVEL="mailtracking.defaults.latvalidationlevel";
	private static final String MAIL_PARCODE_HANMALTYP="mailtracking.defaults.parcod.handlemaltyp";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the screen load---------->\n\n");

    	PostalAdministrationForm paMasterForm =
						(PostalAdministrationForm)invocationContext.screenModel;
    	PostalAdministrationSession paSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
    	
	    SharedDefaultsDelegate sharedDefaultsDelegate =
												new SharedDefaultsDelegate();
	    Map<String,Collection<OneTimeVO>> hashMap = null;

		Collection<String> oneTimeList = new ArrayList<String>();
		
		oneTimeList.add(MAIL_CATAGORY);
		oneTimeList.add(MAIL_BASETYPE);
		oneTimeList.add(MAIL_BILLINGFREQUENCY);
		oneTimeList.add(MAIL_BILLINGSOURCE);
		oneTimeList.add(MAIL_RESDITVERSION);
		oneTimeList.add(MAIL_PARCODE);
		oneTimeList.add(MAIL_LATVAL_LEVEL);
		oneTimeList.add(MAIL_PARCODE_HANMALTYP);
		
		
		try {
			
			hashMap = sharedDefaultsDelegate.findOneTimeValues
								 					(companyCode,oneTimeList);
			log.log(Log.FINEST, "\n\n hash map******************", hashMap);
		
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.SEVERE, "\n\n message fetch exception..........;;;;");
		}
		
		Collection<OneTimeVO> oneTimeMailCatagory =
							(Collection<OneTimeVO>)hashMap.get(MAIL_CATAGORY);
		
		paSession.setOneTimeCategory(oneTimeMailCatagory);
		paSession.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>) hashMap);

    	paMasterForm.setPaCode("");
    	paMasterForm.setPaName("");
    	paMasterForm.setCountryCode("");
    	paMasterForm.setAddress("");
    	
    	paMasterForm.setAccNum("");
    	paMasterForm.setResidtversion("");
    	paMasterForm.setVatNumber("");
    	
    	//Added by A-7540 
    	paMasterForm.setLatValLevel("");
    	
    	
    	paSession.setPaVO(null);
    	paSession.setPostalAdministrationDetailsVOs(null);
    	
  //  	paMasterForm.setOpFlag(OPERATION_FLAG_INSERT);
    	
    	paMasterForm.setScreenStatusFlag
     					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	
    	log.log(Log.FINE, "\n\n out of the screen load---------->\n\n");

    	invocationContext.target = SUCCESS;

	}
}
