/*
 * ListLOVCommand.java Created on Nov 17, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteLOVVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteLOVForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class ListLOVCommand.
 *
 * @author a-5219
 */
public class ListLOVCommand extends BaseCommand {


	/** The Constant SCREENLOAD_SUCCESS. */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	/** The Constant CLASS_NAME. */
	private static final String CLASS_NAME = "ListLOVCommand";

	/** The Constant MODULE_NAME. */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/** The Constant SCREENID. */
	private static final String SCREENID = "mailtracking.mra.defaults.billingsitelov";
	
	/** The log. */
	private Log log = LogFactory.getLogger(	"BillingSiteLOV ListCommand");
	
	/**
	 * Execute.
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
     	log.entering(CLASS_NAME,"execute");
     	log.entering(MODULE_NAME,"execute");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		log.entering(SCREENID,"execute");
		BillingSiteLOVForm form =
			(BillingSiteLOVForm)invocationContext.screenModel;

    	BillingSiteLOVFilterVO bsLovFilterVo=new BillingSiteLOVFilterVO();

    	bsLovFilterVo.setCompanyCode(logonAttributes.getCompanyCode());
    	bsLovFilterVo.setBillingSiteCode(form.getCode());
    	bsLovFilterVo.setBillingSite(form.getDescription());
    	int pageNumber = 1;
    	if(form.getDisplayPage()!=null){
    		if(!("").equals(form.getDisplayPage())){
    			pageNumber = Integer.parseInt(form.getDisplayPage());
    		}
    	}
    	bsLovFilterVo.setPageNumber(pageNumber);
    	log.log(Log.INFO, "Filter VO CC BSC to Server:--->>", bsLovFilterVo.getCompanyCode());
		Page<BillingSiteLOVVO> bsLovVOS=null;
    	log.log(Log.INFO, "Filter VO to Server:--->>", bsLovFilterVo);
    	MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	try{
  		bsLovVOS = mailTrackingMRADelegate.listParameterLov(bsLovFilterVo);

    		log.log(Log.INFO, "**************", bsLovVOS);
    	} catch(BusinessDelegateException businessDelegateException) {
    		errors=handleDelegateException(businessDelegateException);
    	}
    	form.setBillingSiteLov(bsLovVOS);
    	form.setMultiselect("N");
    	invocationContext.addAllError(errors);
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting("ListParameterLOVCommand","execute");
    }

}
