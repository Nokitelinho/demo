/*
 * SaveCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2401
 *
 */
public class SaveCommand extends BaseCommand{
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");
	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.flown";
	private static final String SCREEN_ID = "mailtracking.mra.flown.assignexceptions";
	private static final String SAVE_SUCCESS = "save_success";
	private static final String STATUS = "toList";
	private static final String ALL_INVALID_USERS = 
					"mailtracking.mra.flown.assignexceptios.msg.err.allinvalidusers";
	
	private static final String INVALID_USERS = 
		"mailtracking.mra.flown.assignexceptios.msg.err.invalidusers";

	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		AssignExceptionsForm assignExceptionsForm = (
				AssignExceptionsForm)invocationContext.screenModel;
		AssignExceptionSession assignExceptionSession = 
			(AssignExceptionSession)
				getScreenSession(MODULE_NAME, SCREEN_ID);
		Collection<ErrorVO> errorvos = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSessionImpl=getApplicationSession();
		LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
		String companyCode=logonAttributes.getCompanyCode();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		Collection<ErrorVO> errors = null;
		String [] usersFromScreen = assignExceptionsForm.getAsigneeCodes();
		Collection<ValidUsersVO> validUsers = null;
		Collection<String> users = new ArrayList<String>();
		log.log(Log.INFO, "usersFromScreen.length", usersFromScreen.length);
		for(int i=0;i<usersFromScreen.length;i++){
			if(usersFromScreen[i]!=null && usersFromScreen[i].trim().length()>0) {
				users.add(usersFromScreen[i]);
			}
		}
		try{
			validUsers = mailTrackingMRADelegate.validateUsers(users,companyCode);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);			
		}
		log.log(Log.INFO, "+++++++++++++++VALID USERS ARE ", validUsers);
		ArrayList<ValidUsersVO> validusersList = null;
		if(validUsers == null || validUsers.size() == 0){
			log.log(Log.INFO,"+++++++++++++++ALL USERS INVALID");
			if(users.size()>0){
			
			ErrorVO errorVO = new ErrorVO(ALL_INVALID_USERS);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			
			
			errorvos.add(errorVO);
			}
			//validusersList = new ArrayList<ValidUsersVO>();
		}
		else{
			StringBuffer errorString = new StringBuffer();
			validusersList = new ArrayList<ValidUsersVO>(validUsers);
		log.log(Log.INFO, "valid users--->", validusersList);
			//if some users are invalid find the invalid users
			Collection<String> invalidUsers = new ArrayList<String>();
			String invalidUser =null ;
			boolean isValid =false;
			ValidUsersVO validUserVO = null;
			boolean isFirstInvalidUser = true;//to add comma b/w invalid users in the error string
			for(int i=0;i<usersFromScreen.length;i++){
				log.log(Log.INFO, " +++++++++++inside first for loop", i);
				isValid = false;
				invalidUser = usersFromScreen[i];
				int size = validUsers.size();
				//log.log(Log.INFO," +++++++++++before second for loop");
				
				for(int j=0 ; j<size ; j++){
					log.log(Log.INFO," +++++++++++inside second for loop");
					validUserVO = validusersList.get(j);
					if(invalidUser!=null && invalidUser.trim().length()>0){
					if(invalidUser.equals(validUserVO.getUserCode())){
						isValid = true;
						log.log(Log.INFO, invalidUser,
								"**************** is valid");
					
					}
				}else{
					isValid = true;
				}
				}
				if(!isValid){
					invalidUsers.add(invalidUser);
					if(isFirstInvalidUser){
						errorString.append("    ");
						isFirstInvalidUser = false;						
					}
					else{
						errorString.append(" , ");
					}
					errorString.append(invalidUser);
				}
			}
			if(invalidUsers != null && invalidUsers.size() > 0){
				log.log(Log.INFO, "+++++++++++++++INVALID USERS ARE ",
						errorString.toString());
				Object [] errorDisplay ={errorString.toString()};
				ErrorVO errorVO = new ErrorVO(INVALID_USERS,errorDisplay);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);			
				errorvos.add(errorVO);
				//validusersList = new ArrayList<ValidUsersVO>();
			
			}
		
			
				
			
		}
		if(errorvos != null && errorvos.size() > 0){
			invocationContext.addAllError(errorvos);
			invocationContext.target = SAVE_SUCCESS;
			return;
		}
		log.log(Log.INFO, "VOs to Server", assignExceptionSession.getExceptions());
				try {
					mailTrackingMRADelegate.assignFlownMailExceptions(assignExceptionSession.getExceptions());
				}catch(BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);			
				}
				assignExceptionSession.setExceptions(null);
				assignExceptionsForm.setStatusFlag(STATUS);	
		if(errors != null && errors.size() > 0){
			invocationContext.addAllError(errors);
		}
		
		invocationContext.target = SAVE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
