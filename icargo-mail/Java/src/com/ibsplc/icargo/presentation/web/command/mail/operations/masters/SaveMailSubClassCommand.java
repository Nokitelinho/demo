/*
 * SaveMailSubClassCommand.java Created on June 08, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailSubClassMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class SaveMailSubClassCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("SaveMailSubClassCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID ="mailtracking.defaults.masters.subclass";

	private static final String CODE_EMPTY = 
			"mailtracking.defaults.mailsubclassmaster.msg.err.codeEmpty";
	private static final String DUPLICATE_CODE = 
			"mailtracking.defaults.mailsubclassmaster.msg.err.dupeCode";
	private static final String SAVE_SUCCESS = 
			"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the save command----------> \n\n");
    	
    	MailSubClassForm mailSubClassForm =
						(MailSubClassForm)invocationContext.screenModel;
    	MailSubClassMasterSession subClassSession = 
    					getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
    	Collection<MailSubClassVO> mailSubClassVOs = subClassSession.getMailSubClassVOs();
    	
    	String[] oprflags = mailSubClassForm.getOperationFlag();
    	for(int i=0; i<oprflags.length;i++){
    		log.log(Log.FINE, "**oprflags", oprflags, i);
    	}
    	
    	if (mailSubClassVOs == null) {
    		mailSubClassVOs = new ArrayList<MailSubClassVO>();
		}
			mailSubClassVOs = updateMailSubClassVOs
								(mailSubClassVOs,mailSubClassForm,logonAttributes);
		    	
		if(mailSubClassVOs != null && mailSubClassVOs.size()>0){
			for(MailSubClassVO mailSubClassVO:mailSubClassVOs){
				if(("").equals(mailSubClassVO.getCode())) {
					invocationContext.addError(new ErrorVO(CODE_EMPTY));
					invocationContext.target = FAILURE;
			    	return;
				}
			}
		}
    	
		errors = checkDuplicate(mailSubClassVOs);
		
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			subClassSession.setMailSubClassVOs(mailSubClassVOs);
			invocationContext.target = FAILURE;
	    	return;
		}
		
		//mailSubClassVOs = selectVO(mailSubClassVOs);
		
		log.log(Log.FINE, "\n\n mailSubClassVOs----------> ", mailSubClassVOs);
		try {
			new MailTrackingDefaultsDelegate().saveMailSubClassCodes(mailSubClassVOs);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			subClassSession.setMailSubClassVOs(mailSubClassVOs);
			invocationContext.target = FAILURE;
	    	return;
		}
		
		invocationContext.addError(new ErrorVO(SAVE_SUCCESS));
		mailSubClassForm.setSubClassFilter("");		
		subClassSession.setMailSubClassVOs(null);
		mailSubClassForm.setScreenStatusFlag
				(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;	

	}
	
	private Collection<ErrorVO> checkDuplicate
								(Collection<MailSubClassVO> mailSubClassVOs){

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		log.log(Log.FINE,"\n\n **in check duplicate***\n");

		if(mailSubClassVOs != null && mailSubClassVOs.size()>0){

			int num = 0;
			String flag = "Y";
			HashSet<String> obj = new HashSet<String>();
			
			for(MailSubClassVO mailSubClassVO:mailSubClassVOs) {
				num = 0;
				String code = "";
				if(!(OPERATION_FLAG_DELETE).equals
										(mailSubClassVO.getOperationFlag())){
					
					String subClassCode = 
										mailSubClassVO.getCode().toUpperCase();
	//				log.log(Log.FINE,"\n\n **subClassCode***"+subClassCode);
	
					for(MailSubClassVO chkVO:mailSubClassVOs) {
						if(!(OPERATION_FLAG_DELETE).equals
												(chkVO.getOperationFlag())) {
							String checkCode = chkVO.getCode().toUpperCase();

	//						log.log(Log.FINE,"\n\n **checkCode***"+checkCode);
							if(subClassCode.equals(checkCode)){
								num++;
							}
	
						}
	
					}
				}

				if(num > 1){
					log.log(Log.FINE, "**duplicate present*** num =", num);
					obj.add(code);
					flag = "N";
				}
			}

			String destnObj=obj.toString();
			Object[] destinationObject={destnObj};

			if(("N").equals(flag)) {	
				error = new ErrorVO(DUPLICATE_CODE,destinationObject);
				errors.add(error);
			}

		}
		
		return errors;
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
    		MailSubClassForm mailSubClassForm ,LogonAttributes logonAttributes) {
    	
    	log.entering("SaveCommand","updateMailSubClassVOs");
    	
    	String[] oprflags = mailSubClassForm.getOperationFlag();
    	
    	int size = 0;
    	if(mailSubClassVOs != null && mailSubClassVOs.size() > 0){
			   size = mailSubClassVOs.size();
    	}
    	Collection<MailSubClassVO> newSubClassVOs = new ArrayList<MailSubClassVO>();
		for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					MailSubClassVO mailSubClassVO = new MailSubClassVO();
	    			mailSubClassVO.setCompanyCode(logonAttributes.getCompanyCode());
	    			mailSubClassVO.setCode(mailSubClassForm.getCode()[index].toUpperCase().trim());
					mailSubClassVO.setDescription(mailSubClassForm.getDescription()[index]);
					mailSubClassVO.setSubClassGroup(mailSubClassForm.getSubClassGroup()[index]);
					mailSubClassVO.setOperationFlag(mailSubClassForm.getOperationFlag()[index]);
					mailSubClassVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					newSubClassVOs.add(mailSubClassVO);
				}
			}else{
				int count = 0;
				if(mailSubClassVOs != null && mailSubClassVOs.size() > 0){
				   for(MailSubClassVO mailSubClassVO:mailSubClassVOs){
					   if(count == index){
						   if(!"NOOP".equals(oprflags[index])){
							   mailSubClassVO.setCode(mailSubClassForm.getCode()[index].toUpperCase().trim());
							   mailSubClassVO.setDescription(mailSubClassForm.getDescription()[index]);
							   mailSubClassVO.setSubClassGroup(mailSubClassForm.getSubClassGroup()[index]);
							   if("N".equals(oprflags[index])){
								   mailSubClassVO.setOperationFlag(null);
							   }else{
								   mailSubClassVO.setOperationFlag(oprflags[index]);
							   }
							   mailSubClassVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
							   newSubClassVOs.add(mailSubClassVO);
						   }
					   }
					   count++;
				   }
				}
			}
		}
    	
    	log.exiting("SaveCommand","updateMailSubClassVOs");
    	
    	return newSubClassVOs;    	
    }
    
	/**
     * Method to select VOs which are to be sent to server 
     * for insertion modification or deletion
     * @param mailSubClassVOs
     * @return
     */
    
    private Collection<MailSubClassVO> selectVO(
    						Collection<MailSubClassVO> mailSubClassVOs){
    	
    	log.entering("SaveCommand","selectVO");
    	Collection<MailSubClassVO> selectedVOs =
    								new ArrayList<MailSubClassVO>();
    	
    	for(MailSubClassVO mailSubClassVO:mailSubClassVOs) {
    		if(!("NA").equals(mailSubClassVO.getOperationFlag())){
    			selectedVOs.add(mailSubClassVO);
    		}
    	}
    	
    	log.log(Log.FINE, "\n\n**selectedVOs---------->", selectedVOs);
		log.exiting("SaveCommand","selectVO");
    	
    	return selectedVOs;
    }

}
