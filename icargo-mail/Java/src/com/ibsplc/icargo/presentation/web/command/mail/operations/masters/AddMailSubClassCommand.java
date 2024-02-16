/*
 * AddMailSubClassCommand.java Created on July 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailSubClassMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class AddMailSubClassCommand extends BaseCommand {

	private static final String SUCCESS = "add_success";
	private static final String FAILURE = "add_failure";
	
	private Log log = LogFactory.getLogger("AddMailSubClassCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
									"mailtracking.defaults.masters.subclass";
	
	private static final String CODE_EMPTY = 
		"mailtracking.defaults.mailsubclassmaster.msg.err.codeEmpty";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the add command----------> \n\n");
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
    	
    	MailSubClassForm mailSubClassForm =
							(MailSubClassForm)invocationContext.screenModel;
    	MailSubClassMasterSession subClassSession = 
    									getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	
    	ArrayList<MailSubClassVO> mailSubClassVOs = 
    			(ArrayList<MailSubClassVO>)subClassSession.getMailSubClassVOs();
    	
    	if (mailSubClassVOs == null) {
    		mailSubClassVOs = new ArrayList<MailSubClassVO>();
		}else {
			mailSubClassVOs = updateMailSubClassVOs
								(mailSubClassVOs,mailSubClassForm,companyCode);
		}
    	
    	    	
    	String[] opFlag = mailSubClassForm.getOperationFlag();
		String[] rowIds = mailSubClassForm.getRowId();
		int index=0;
		if(rowIds != null)	{
			if(rowIds.length == 1){
				index = Integer.parseInt(rowIds[0])+1;
			}else {
				index = opFlag.length;
			}
			
		}else {
			if(opFlag!=null){
				index = opFlag.length;
			}else{
				index=0;
			}
		}
		
		if(mailSubClassVOs != null && mailSubClassVOs.size()>0){
			for(MailSubClassVO mailSubClassVO:mailSubClassVOs){
				if(("").equals(mailSubClassVO.getCode())) {
					ErrorVO error = new ErrorVO(CODE_EMPTY);
					errors.add(error);
					break;
				}
			}
		}
			
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
	    	return;
		}
		
		MailSubClassVO newMailSubClassVO = new MailSubClassVO();
		
		newMailSubClassVO.setCode("");
		newMailSubClassVO.setDescription("");
		newMailSubClassVO.setSubClassGroup("");
		newMailSubClassVO.setCompanyCode(companyCode);
		newMailSubClassVO.setOperationFlag
										(MailSubClassVO.OPERATION_FLAG_INSERT);
		mailSubClassVOs.add(index,newMailSubClassVO);
		
		subClassSession.setMailSubClassVOs(mailSubClassVOs);
		
		
		mailSubClassForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SUCCESS;

	}
	
	/**
     * Method to update the mailSubClassVOs in session
     * @param mailSubClassVOs
     * @param mailSubClassForm
     * @param companyCode
     * @return
     */
    private ArrayList<MailSubClassVO> updateMailSubClassVOs(
    		ArrayList<MailSubClassVO> mailSubClassVOs,
    		MailSubClassForm mailSubClassForm,String companyCode) {
    	
    	log.entering("AddCommand","updateMailSubClassVOs");
		
    	if(mailSubClassVOs != null){
    		int index = 0;
    		for(MailSubClassVO mailSubClassVO:mailSubClassVOs) {
    			mailSubClassVO.setCode(mailSubClassForm.getCode()
    											[index].toUpperCase().trim());
    			mailSubClassVO.setSubClassGroup(mailSubClassForm.getSubClassGroup()
						[index].toUpperCase().trim());
				mailSubClassVO.setDescription(mailSubClassForm.
													getDescription()[index]);
				mailSubClassVO.setCompanyCode(companyCode);
				mailSubClassVO.setOperationFlag(mailSubClassForm.
													getOperationFlag()[index]);
    	
    			index++;
    		}
    	}
    	log
				.log(Log.FINE, "Updated mailSubClassVOs---------> ",
						mailSubClassVOs);
		log.exiting("AddCommand","updateMailSubClassVOs");
    	
    	return mailSubClassVOs;    	
    }

}
