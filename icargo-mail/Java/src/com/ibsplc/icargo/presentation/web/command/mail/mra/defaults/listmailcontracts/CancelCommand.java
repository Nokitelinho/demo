/*
 * CancelCommand.java created on APR 2, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listmailcontracts;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailContractsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1946
 *
 */
public class CancelCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CancelCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listmailcontracts";

	private static final String SCREEN_SUCCESS = "cancel_success";
	
	private static final String SCREEN_FAILURE = "cancel_failure";
	
	private static final String CANCEL_STATUS = "C";
	
//	private static final String ACIVE_STATUS="A";
	
	private static final String DRAFT_STATUS="D";
	
	private static final String INVALID_STATUS="mailtracking.mra.defaults.listmailcontracts.draftstatus";

	
	
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
		ListMailContractsSession  session=	(ListMailContractsSession)getScreenSession(
				MODULE_NAME, SCREENID);
		ListMailContractsForm form=(ListMailContractsForm)invocationContext.screenModel;
		String selectedRow[]=form.getRowId();
		Collection<ErrorVO> errors=null;
		/**
		 * Getting log on attribute here
		 */
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		int index=0;
		/**
		 * Getting log on attribute here
		 */		
		ArrayList<MailContractVO> mailContractVOs =(ArrayList<MailContractVO>)session.getMailContractVOs();
		Collection<MailContractVO> tempMailContractVOs =new ArrayList<MailContractVO>();
		for(String s:selectedRow){
			log.log(Log.FINE, "The selecte row ", s);
			index=Integer.parseInt(s);
			tempMailContractVOs.add(mailContractVOs.get(index));
		}
		/**
		 * Status Of Contract must be Active For Cancel
		 * We cant Cancel Draft Status
		 */
		if(tempMailContractVOs.size()>0){			
			for(MailContractVO vo:tempMailContractVOs){
				log.log(Log.FINE, "The MailContractVO ", vo);
				if(vo.getAgreementStatus()!=null){
					if(DRAFT_STATUS.equals(vo.getAgreementStatus())){
						Object errorObject []={vo.getContractReferenceNumber()};
						ErrorVO error=new ErrorVO(INVALID_STATUS,errorObject);
						errors=new ArrayList<ErrorVO>();
						errors.add(error);
						break;
					}					
				}
			}
			if(errors!=null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target=SCREEN_FAILURE;
				return;	
			}
			/**
			 * Here set the Stsus to Cancel
			 */
			for(MailContractVO vo:tempMailContractVOs){		
				log.log(Log.FINE,"ENTER INTO FOR LOOP");
				vo.setCompanyCode(logonAttributes.getCompanyCode());
				vo.setAgreementStatus(CANCEL_STATUS);	
			}
		}
		try{
			new MailTrackingMRADelegate().changeMailContractStatus(tempMailContractVOs);
		}
		catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
		}
		
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	
}
