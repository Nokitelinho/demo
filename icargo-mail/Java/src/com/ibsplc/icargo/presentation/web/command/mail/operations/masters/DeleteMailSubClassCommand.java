/*
 * DeleteMailSubClassCommand.java Created on June 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class DeleteMailSubClassCommand extends BaseCommand {
	
	private static final String SUCCESS = "delete_success";
	
	private Log log = LogFactory.getLogger("DeleteMailSubClassCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
									"mailtracking.defaults.masters.subclass";
	
	private static final String OPERATION_FLAG_INS_DEL ="I_D";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the delete command----------> \n\n");
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
    	
    	MailSubClassForm mailSubClassForm =
							(MailSubClassForm)invocationContext.screenModel;
    	MailSubClassMasterSession subClassSession = 
    									getScreenSession(MODULE_NAME,SCREEN_ID);
    	Collection<MailSubClassVO> mailSubClassVOs = 
    									subClassSession.getMailSubClassVOs();
    	
    	if (mailSubClassVOs == null) {
    		mailSubClassVOs = new ArrayList<MailSubClassVO>();
		}else {
			mailSubClassVOs = updateMailSubClassVOs
								(mailSubClassVOs,mailSubClassForm,companyCode);
		}
    	
    	ArrayList<MailSubClassVO> newMailSubClassVOs = 
    											new ArrayList<MailSubClassVO>();
    	
		String[] rowIds = mailSubClassForm.getRowId();
		
		int num = 0;
    	
		for(MailSubClassVO mailSubClassVO:mailSubClassVOs) {
    		for(int i = 0; i < rowIds.length; i++){
    			if(num == Integer.parseInt(rowIds[i])){
    				if (OPERATION_FLAG_INSERT.equals
    									(mailSubClassVO.getOperationFlag())) {
    					mailSubClassVO.setOperationFlag(OPERATION_FLAG_INS_DEL);
					}
					else {
						mailSubClassVO.setOperationFlag(OPERATION_FLAG_DELETE);
					}	
    			}
    		}
    		if (!OPERATION_FLAG_INS_DEL.equals
    									(mailSubClassVO.getOperationFlag())) {
    			newMailSubClassVOs.add(mailSubClassVO);
			}
    		num++;
    	}
		
		subClassSession.setMailSubClassVOs(newMailSubClassVOs);
		
		
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
    private Collection<MailSubClassVO> updateMailSubClassVOs(
    		Collection<MailSubClassVO> mailSubClassVOs,
    		MailSubClassForm mailSubClassForm,String companyCode) {
    	
    	log.entering("SaveCommand","updateMailSubClassVOs");
		
    	if(mailSubClassVOs != null){
    		int index = 0;
    		for(MailSubClassVO mailSubClassVO:mailSubClassVOs) {
    			mailSubClassVO.setCode(mailSubClassForm.getCode()
    											[index].toUpperCase().trim());
    			mailSubClassVO.setDescription(mailSubClassForm.
						getDescription()[index]);
				mailSubClassVO.setDescription
									(mailSubClassForm.getDescription()[index]);
				mailSubClassVO.setCompanyCode(companyCode);
				mailSubClassVO.setOperationFlag
								(mailSubClassForm.getOperationFlag()[index]);
    	
    			index++;
    		}
    	}
    	log
				.log(Log.FINE, "Updated mailSubClassVOs---------> ",
						mailSubClassVOs);
		log.exiting("SaveCommand","updateMailSubClassVOs");
    	
    	return mailSubClassVOs;    	
    }

}
