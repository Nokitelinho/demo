/*
 * SaveCommand.java Created on Mar 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 * 
 */
public class SaveCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String CLASS_NAME = "SaveCommand";
	
	//private static final String OPFLAG_UPD = "U";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String SAVE_SUCCESS = "save_success";
	
	private static final String DUP_BILLINGLINE = 
		"mailtracking.mra.defaults.duplicatebillingline";
	
	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
	//	ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;

		ViewBillingLineSession session = (ViewBillingLineSession) getScreenSession(
				MODULE, SCREENID);
		Collection<BillingLineVO> vosToSave = new ArrayList<BillingLineVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
//		error = validateForm(form);
		
		Page<BillingLineVO> billingLineDetails = session.getBillingLineDetails();
		if(billingLineDetails != null &&
				billingLineDetails.size() > 0){
			log
					.log(Log.INFO, "session page size...", billingLineDetails.size());
			for(BillingLineVO vo :billingLineDetails){
				log.log(Log.INFO, "vo-------->>", vo);
				if(AbstractVO.OPERATION_FLAG_UPDATE.equals(vo.getOperationFlag())){
					vosToSave.add(vo);
				}
			}
		}
		log.log(Log.INFO, "vos to save...", vosToSave);
		if(vosToSave.size() > 0){
			try{
				new MailTrackingMRADelegate().saveBillingLineStatus(null,vosToSave);
			}catch(BusinessDelegateException businessDelegateException){
				businessDelegateException.getMessage();
				errors.add(new ErrorVO(DUP_BILLINGLINE));
				
			}
			
		}
		if(errors.size() == 0){
			invocationContext.target = SAVE_SUCCESS;
			log.log(1,"No errors...............");
		}
		else{
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			log.log(1," Errors present...............");
		}
		log.exiting(CLASS_NAME,"execute");
		}
	}
