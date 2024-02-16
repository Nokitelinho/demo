/*
 * SaveCommand.java created on Mar 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 *
 */
public class SaveCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String SAVE_SUCCESS = "save_success";

	private static final String OPFLAG_UPD = "U";
	
	private static final String DUP_BILLINGLINE = 
		"mailtracking.mra.defaults.duplicatebillingline";
	
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
		
		ListBillingMatrixSession session=getScreenSession(MODULE_NAME,SCREENID);
	
		Collection<BillingMatrixVO> vosToSave = new ArrayList<BillingMatrixVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.log(Log.INFO, " collection....", session.getBillingMatrixVOs());
		if(session.getBillingMatrixVOs() != null
				&& session.getBillingMatrixVOs().size() > 0){
			log.log(Log.INFO, "size of collection....", session.getBillingMatrixVOs().size());
			for(BillingMatrixVO vo : session.getBillingMatrixVOs()){
				if(OPFLAG_UPD.equals(vo.getOperationFlag())){
					vosToSave.add(vo);
				}
			}
			
		}
		log.log(Log.INFO, "***************", vosToSave);
		try{
			if(vosToSave.size() > 0){
				log.log(1,"Calling delegate save status...");
				new MailTrackingMRADelegate().saveBillingLineStatus(vosToSave,null);
			}
		}
		catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			errors.add(new ErrorVO(DUP_BILLINGLINE));
		}
		
		if(errors.size() == 0){
			invocationContext.target = SAVE_SUCCESS;
			log.log(1,"No errors...............");
			session.removeBillingMatrixVOs();
		}
		else{
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			log.log(1," Errors present...............");
		}
		log.exiting(CLASS_NAME,"execute");
		
	}
}
