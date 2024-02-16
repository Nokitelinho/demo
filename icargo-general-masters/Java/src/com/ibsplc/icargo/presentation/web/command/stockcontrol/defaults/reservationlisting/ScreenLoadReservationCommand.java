/*
 * ScreenLoadReservationCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reservationlisting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ScreenLoadReservationCommand extends BaseCommand {

	   private static final String MODULE_NAME = "stockcontrol.defaults";

		private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";

		private static final String SCREENLOAD_SUCCESS= "ScreenLoadReservation_Success";

		private Log log = LogFactory.getLogger("ScreenLoadReservationCommand");

		private static final String SYS_DOC_UNIT="stock.reserveawb.defaultdocumentType";

		private static final String SYS_EXPIRY_UNIT="stock.defaults.reservation.allowabledaysforamending";

 
		/**
		 * @param invocationContext
		 * @throws CommandInvocationException
		 * @return
		 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ReservationListingForm form = (ReservationListingForm)invocationContext.screenModel;
		ReservationListingSession reservationListingSession
		           = getScreenSession(MODULE_NAME, SCREEN_ID);
        ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
		reservationListingSession.removeReservationVO();

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map  map = null;
		Collection<String> sysParamList = new ArrayList<String>();
		sysParamList.add(SYS_DOC_UNIT);
		sysParamList.add(SYS_EXPIRY_UNIT);

		try {
			log.log(Log.FINEST,"***********************************inside try");
			map = sharedDefaultsDelegate.findSystemParameterByCodes(sysParamList);
			log.log(Log.FINEST,
					"Loading Units (map)*****************************", map);

		}catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			handleDelegateException(businessDelegateException);
			log.log(Log.SEVERE, "status fetch exception");
		}
		String doccode= (String) map.get(SYS_DOC_UNIT);
		String expiry=(String) map.get(SYS_EXPIRY_UNIT);
		log.log(Log.FINE, "doccode****************************************>",
				doccode);
		log.log(Log.FINE,
				"expirydate****************************************>", expiry);
		reservationListingSession.setExpiryDate(expiry);


		DocumentTypeDelegate delegate = new DocumentTypeDelegate();
		Collection<DocumentVO> doctype=new ArrayList<DocumentVO>();
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();

		 try {
			 documentFilterVO.setCompanyCode(companyCode);
			 documentFilterVO.setDocumentCode(doccode);
			     doctype = delegate.findDocumentDetails(documentFilterVO);
		}catch(BusinessDelegateException e) {
			handleDelegateException(e);

		}
		log.log(Log.FINE, "doctype****************************************>",
				doctype);
		reservationListingSession.setDocumentType(doctype);
		//form.setListScreen("false");
		invocationContext.target=SCREENLOAD_SUCCESS;
	}
    }


