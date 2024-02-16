/*
 * PopulateGpaNameCommand.java created on APR 23,2009
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.fuelsurcharge;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.FuelSurchargeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class PopulateGpaNameCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "PopulateGpaNameCommand";

	private static final String SCREEN_SUCCESS = "populate_success";
	
	private static final String SCREEN_FAILURE = "populate_failure";
	
	private static final String GPACOD_INVALID = "mailtracking.mra.defaults.fuelsurcharge.gpacodeinvalid";
	
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
	
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		
		FuelSurchargeForm form = (FuelSurchargeForm) invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailTrackingMRADelegate mailtrackingMRADelegate = new MailTrackingMRADelegate();
		PostalAdministrationVO returnvo = null;
		try {
			returnvo = mailtrackingMRADelegate.findPostalAdminDetails(logonAttributes.getCompanyCode(), form.getGpaCode().toUpperCase());
		} catch (BusinessDelegateException e) {
			log.log(Log.SEVERE, "\n\n\nPA Name Cannot be found!!!!! Check PA Code -->>\n\n\n");
		}
		 if(returnvo!=null){
			 log.log(Log.INFO, "returnvo FROM POPULATE COMMAND ", returnvo);
			form.setGpaName(returnvo.getPaName());
			 form.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			 invocationContext.target=SCREEN_SUCCESS;
		 }
		 else{
			 invocationContext.target=SCREEN_FAILURE;
				ErrorVO err = new ErrorVO(GPACOD_INVALID);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
				invocationContext.addAllError(errors);
				form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				invocationContext.target=SCREEN_FAILURE;
		 }
		
		log.exiting(CLASS_NAME,"execute");
	}
	
		
		
	

	

}
