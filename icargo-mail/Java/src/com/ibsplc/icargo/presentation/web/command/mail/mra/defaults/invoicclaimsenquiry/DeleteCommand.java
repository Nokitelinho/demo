/*
 * DeleteCommand.java Created on Mar 12, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicclaimsenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicClaimsEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicClaimsEnquiryForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2259
 *
 */
public class DeleteCommand extends BaseCommand{

	private static final String CLASS_NAME = "DeleteCommand";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String DELETE_SUCCESS = "delete_success";

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.invoicclaimsenquiry";



	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		InvoicClaimsEnquirySession session = (InvoicClaimsEnquirySession)getScreenSession(MODULE,SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		InvoicClaimsEnquiryForm form = (InvoicClaimsEnquiryForm)invocationContext.screenModel;
		Page<MailInvoicClaimsEnquiryVO> mailInvoicClaimsEnquiryVOs = null;
		MailInvoicClaimsEnquiryVO mailInvoicClaimsEnquiryVO=new MailInvoicClaimsEnquiryVO();
		Collection<MailInvoicClaimsEnquiryVO> mailInvoicClaimsEnquiryVOsForDel=new ArrayList<MailInvoicClaimsEnquiryVO>();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		mailInvoicClaimsEnquiryVOs=session.getMailInvoicClaimsEnquiryVOs();
		//LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
		/*String[] row = form.getSelectedElements();
		int index=0;
		if(row!= null && row.length >0){
		for(String rowId:row) {
			index = Integer.parseInt(rowId);

			mailInvoicClaimsEnquiryVOs=session.getMailInvoicClaimsEnquiryVOs();
			mailInvoicClaimsEnquiryVO = mailInvoicClaimsEnquiryVOs.get(index);

		//	mailInvoicClaimsEnquiryVO.setLastUpdatedTime(date);
			mailInvoicClaimsEnquiryVOsForDel.add(mailInvoicClaimsEnquiryVO);
		}
		}
		try {
			log.log(Log.FINE,
			"Inside try : Calling findInvoicEnquiryDetails");
			 delegate.deleteInvoicClaimsEnquiryDetails(mailInvoicClaimsEnquiryVOsForDel);
			log.log(Log.FINE, "mailInvoicClaimsEnquiryVOs from Server:--> "
					+ mailInvoicClaimsEnquiryVOs);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught Exception");
		}*/
		invocationContext.target = DELETE_SUCCESS;
	}

}
