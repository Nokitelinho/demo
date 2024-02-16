/*
 * ScreenLoadMUCActionsCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-3045
 *
 */
public class ScreenLoadMUCActionsCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MUC Action");
		
		private static final String MODULE = "uld.defaults";

		private static final String SCREENID =
			"uld.defaults.messaging.muctracking";
		private static final String SCREENLOAD_SUCCESS = "actionsScreenload_success";
		private static final String SCREENLOAD_FAILURE = "actionsScreenload_failure";
	    
	    /**
		 * @param invocationContext
		 * @throws CommandInvocationException
		 * @return 
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
			log.entering("ScreenLoadMUCActionsCommand", "execute");
	    	/*
			 * Obtain the logonAttributes
			 */
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();			
			MUCTrackingForm mucTrackingForm = (MUCTrackingForm) invocationContext.screenModel;
			MUCTrackingSession mucTrackingSession = getScreenSession(MODULE,
					SCREENID);
			Collection<ErrorVO> errors = null;
			ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();		
			TransactionListVO transactionListVO = mucTrackingSession
														.getListDisplayColl();
			Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionListVO
					.getUldTransactionsDetails();
			log.log(Log.FINE, "mucTrackingForm.getUldNum()------------>>>>>>",
					mucTrackingForm.getUldNum());
			ULDTransactionDetailsVO selectedULDTransactionDetailsVO = null;
			for (ULDTransactionDetailsVO uldTransactionDetailsVO : uldTransactionDetailsVOs) {	
				if(uldTransactionDetailsVO.getUldNumber().equals(mucTrackingForm.getUldNum())){
					selectedULDTransactionDetailsVO = uldTransactionDetailsVO;	
				}				
			}
			Collection<ULDConfigAuditVO> uldConfigAuditVOs = null;
			if(selectedULDTransactionDetailsVO != null){
				try {
					/*String mucReferenceNumber = selectedULDTransactionDetailsVO.getMucReferenceNumber() != null 
												? selectedULDTransactionDetailsVO.getMucReferenceNumber().toUpperCase() : "";
					String mucDate = null;
					if(selectedULDTransactionDetailsVO.getMucDate() != null) {
						mucDate = TimeConvertor.toStringFormat(
								selectedULDTransactionDetailsVO.getMucDate().toCalendar(),"dd-MMM-yyyy");
					}
					String controlReceiptNumber = selectedULDTransactionDetailsVO.getControlReceiptNumber() != null 
					? selectedULDTransactionDetailsVO.getControlReceiptNumber().toUpperCase() : "";
					log.log(Log.FINE,"mucRefNum------------>>>>>>"+mucReferenceNumber);							
					log.log(Log.FINE,"mucDate------------>>>>>>"+mucDate);*/				
					uldConfigAuditVOs = new ULDDefaultsDelegate().findMUCAuditDetails(selectedULDTransactionDetailsVO);
					log.log(Log.FINE, "uldConfigAuditVOs--->",
							uldConfigAuditVOs);					
				}
				catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}				
			}
			mucTrackingSession.setConfigAuditColl(uldConfigAuditVOs);
			invocationContext.target = SCREENLOAD_SUCCESS;
	    }
}
