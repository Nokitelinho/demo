/*
 * SaveCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 * 
 */

public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SaveCommand");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	private static final String ACTION_SUCCESS = "save_success";

	private static final String ACTION_FAILURE = "save_failure";

	private static final String NO_DATA_FOR_SAVE = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.nodateforsave";
	private static final String CANNOTBE_REVIEWED = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.cannotbereviewed";
	private static final String COMMA = ",";
	
	

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListInterlineBillingEntriesSession session = (ListInterlineBillingEntriesSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ListInterlineBillingEntriesForm form = (ListInterlineBillingEntriesForm) invocationContext.screenModel;
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate ();
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if(session.getDocumentBillingDetailVOs()!=null){
    		ArrayList<DocumentBillingDetailsVO> documentBillingDetailVOs=new ArrayList<DocumentBillingDetailsVO>
    		(session.getDocumentBillingDetailVOs());
    		Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs=new ArrayList<DocumentBillingDetailsVO>();
    		String[] selectedrows= form. getSelectedrows().split(COMMA);	
    		
    	for(String selectedrow:selectedrows){
    			documentBillingDetailsVOs.add(documentBillingDetailVOs.get(Integer.parseInt(selectedrow)));
    			}
		
    		
		if(errors != null && errors.size() > 0){
			
			invocationContext.addAllError(errors);
			invocationContext.target=ACTION_FAILURE;
			return;
		}
		for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){
		if(form.getReview()!=null){
			if(documentBillingDetailsVO.getReviewCheck()!=null&& 
			documentBillingDetailsVO.getReviewCheck().trim().length()>0)	{
				documentBillingDetailsVO.setReviewCheck(form.getReview());
			}
		log.log(Log.FINE, "Review : ", documentBillingDetailsVO.getReviewCheck());
		}
		}
		log.log(Log.FINE, "documentBillingDetailsVOs... : ",
				documentBillingDetailsVOs);
			try {
				mailTrackingMRADelegate.changeReview(documentBillingDetailsVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
