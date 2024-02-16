
/*
 * SaveCommand.java Created on June 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.changestatus;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */

public class SaveCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
	private static final String KEY_STATUS_SAME="mailtracking.mra.gpabilling.enteredsamestatus";
	private static final String KEY_STATUS_SHOULDBE_WD="mailtracking.mra.gpabilling.statusshouldbewd";
	private static final String KEY_STATUSDB_NOTCHANGE="mailtracking.mra.gpabilling.statuscantchangefordb";
	private static final String KEY_STATUS_SHOULDBE_PO="mailtracking.mra.gpabilling.statusshouldbepo";
	private static final String KEY_STATUS_SHOULDBE_PB="mailtracking.mra.gpabilling.statusshouldbepb";


	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		GPABillingInvoiceEnquirySession session = (GPABillingInvoiceEnquirySession) getScreenSession(
 				MODULE_NAME, SCREEN_ID);
		GPABillingInvoiceEnquiryForm  form=( GPABillingInvoiceEnquiryForm )invocationContext.screenModel;

		log.entering("SaveCommand", "execute");
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate ();

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String counter = form.getCounter();
		Collection<CN66DetailsVO> cN66DetailsVOs = session
				.getCN66VOs();
		ArrayList<CN66DetailsVO> cN66DetailsVOArraylist = new ArrayList<CN66DetailsVO>(
				cN66DetailsVOs);
		CN66DetailsVO cN66DetailsVO;
		log.log(Log.FINE, "inside *****<<<<counter>>>>----------  ", counter);
		cN66DetailsVO= cN66DetailsVOArraylist.get(Integer.parseInt(counter));


		log.log(Log.FINE, "cN66DetailsVO... >>", cN66DetailsVO);
		CN51SummaryVO cN51SummaryVO=session.getCN51SummaryVO();
 		log.log(Log.FINE, "InvoiceStatus-----  ", cN51SummaryVO.getInvoiceStatus());
			log.log(Log.FINE, "Status-----  ", form.getStatus());
			if ("DB".equals(cN66DetailsVO.getBillingStatus())){
				if(!("WD".equals(form.getStatus()))){
					errors.add(new ErrorVO(KEY_STATUS_SHOULDBE_WD));
		    		invocationContext.addAllError(errors);

					invocationContext.target = SAVE_FAILURE;
					return;
				}
			}
			 if ("DD".equals(cN66DetailsVO.getBillingStatus())){
				errors.add(new ErrorVO(KEY_STATUSDB_NOTCHANGE));
	    		invocationContext.addAllError(errors);

				invocationContext.target = SAVE_FAILURE;
				return;
			}
			

			 if ("PB".equals(cN66DetailsVO.getBillingStatus())){
				if(!("PO".equals(form.getStatus()))){
					errors.add(new ErrorVO(KEY_STATUS_SHOULDBE_PO));
		    		invocationContext.addAllError(errors);

					invocationContext.target = SAVE_FAILURE;
					return;
				}
			 }
			if ("PO".equals(cN66DetailsVO.getBillingStatus())){
					if(!("PB".equals(form.getStatus()))){
						errors.add(new ErrorVO(KEY_STATUS_SHOULDBE_PB));
			    		invocationContext.addAllError(errors);

						invocationContext.target = SAVE_FAILURE;
						return;
				}
			}
			 

		cN66DetailsVO.setBillingStatus(form.getStatus());

		cN66DetailsVO.setRemarks(form.getRemarks());
		try {

			// calling the Delegate

				mailTrackingMRADelegate.saveBillingStatus(cN66DetailsVO);


		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);

		}
		 if(errors!=null && errors.size()>0){
			 invocationContext.addAllError(errors);
			 invocationContext.target = SAVE_FAILURE;
			 return;
		  }

		invocationContext.target = SAVE_SUCCESS;
		form.setClosePopup("Y");
		log.exiting("SaveCommand", "execute");

	}



}
