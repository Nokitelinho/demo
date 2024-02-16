/**
 *PopulateGpaNameCommand.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class PopulateGpaNameCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "PopulateGpaNameCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.invoicesettlement";

	private static final String SCREEN_SUCCESS = "populate_success";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		try {
			MailTrackingMRADelegate delegate
			= new MailTrackingMRADelegate();
			int displayPage=1;
            int defaultpagesize =10;
			String paCode = form.getGpaCodeFilter().toUpperCase();
			String companyCode = logonAttributes.getCompanyCode();
			String paName =null;	

			Page<PostalAdministrationVO> page=delegate.findPALov(
					companyCode,paCode,paName,displayPage,defaultpagesize);
			log.log(Log.INFO, "page", page);
			for(PostalAdministrationVO postalAdministrationVO:page){		
				paName=postalAdministrationVO.getPaName();

			}
			form.setGpaNameFilter(paName);
			log.log(Log.INFO, "", paName);
		}
		catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		invocationContext.target =SCREEN_SUCCESS;

	}
}
