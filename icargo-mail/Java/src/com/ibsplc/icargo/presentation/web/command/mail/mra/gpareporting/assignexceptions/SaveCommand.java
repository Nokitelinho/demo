/*
 * SaveCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.assignexceptions;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.admin.user.vo.ValidUsersVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.AssignExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.AssignExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 *  
 * @author A-2245
 * 
 /*
 * 
 * Revision History
 * 
 * Version      Date          	 	Author          Description
 * 
 *  0.1         Feb 21, 2007   		A-2245			Initial draft
 *  
 *  
 *  
 */
public class SaveCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	/*
	 * String constants for CLASS_NAME
	 */
	private static final String CLASS_NAME = "SaveCommand";
	/*
	 * String constants for MODULE_NAME,SCREENID
	 */
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String SCREENID = "mailtracking.mra.gpareporting.assignexceptions";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "save_success";
	private static final String ACTION_FAILURE = "save_failure";
	/*
	 * error codes
	 */
	private static final String INFO_DATASAVED = "mailtracking.mra.gpareporting.assignexceptions.msg.info.datasaved";
	private static final String ERR_INVALIDASSIGNEE = "mailtracking.mra.gpareporting.assignexceptions.msg.err.invalidassignee";
	private static final String INFO_NODATAFORSAVE="mailtracking.mra.gpareporting.assignexceptions.msg.err.nodataforsave";
	/*
	 * EMPTY_STRING
	 */
	private static final String EMPTY_STRING = "";
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		/*
		 * assignExceptionsSession defined
		 */
		AssignExceptionsSession assignExceptionsSession = getScreenSession(MODULE_NAME,SCREENID);
		/*
		 * assignExceptionsForm defined
		 */
		AssignExceptionsForm assignExceptionsForm = 
							(AssignExceptionsForm)invocationContext.screenModel;
		updateGPADetailsSession(assignExceptionsSession, assignExceptionsForm);
		Collection<GPAReportingClaimDetailsVO> saveGPAReportingVOs = 
									new ArrayList<GPAReportingClaimDetailsVO>();

		/*
		 * Getting updatedGPAReportingVOs for saving
		 */
		if(assignExceptionsSession.getGpaReportingDetailVOs()!=null && 
				assignExceptionsSession.getGpaReportingDetailVOs().size()>0){
			Collection<String> assigneeCodes = new ArrayList<String>();
			for(GPAReportingClaimDetailsVO gpaReportingDetailsVO :
								assignExceptionsSession.getGpaReportingDetailVOs()){
				if(GPAReportingClaimDetailsVO.OPERATION_FLAG_UPDATE.equals(
						gpaReportingDetailsVO.getOperationFlag())){
					/*
					 * Does not allow duplicates
					 */
					if(!assigneeCodes.contains(gpaReportingDetailsVO.getAssignedUser())) {
						assigneeCodes.add(gpaReportingDetailsVO.getAssignedUser());
					}
					saveGPAReportingVOs.add(gpaReportingDetailsVO);
				}
			}
			log.log(Log.INFO, "for assignee validations (assigneeCodes)>>",
					assigneeCodes);
			if(assigneeCodes!=null && assigneeCodes.size()>0){
				Collection<ValidUsersVO> usersVOs = null;
				try{
					usersVOs =new MailTrackingMRADelegate().validateUsers(assigneeCodes, 
							getApplicationSession().getLogonVO().getCompanyCode());
				}catch(BusinessDelegateException businessDelegateException){
					handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
					log.log(Log.INFO,"Invalid User Exception...Caught!");
				}
				/*
				 * (Non-Javadoc)
				 * Getting Invalid Users/Assignees for error message
				 * *************************************************
				 * usersVOs are valid users/assignees
				 * Invalid assignees are selected from assigneeCodes 
				 */
				if(usersVOs==null || usersVOs.size()==0 || 
						usersVOs.size() != assigneeCodes.size()){
					StringBuilder invalidUsers = new StringBuilder();
					boolean isUserValid=false;
					for(String assigneeCode: assigneeCodes){
						if(usersVOs!=null && usersVOs.size()>0){
							/*
							 * Reinitialising isUserValid flag 
							 */
							isUserValid=false;
							for(ValidUsersVO usersVO: usersVOs){
								/*
								 * Getting Assignee Codes that are invalid from
								 * string collection assigneeCodes
								 */
								if(assigneeCode.equals(usersVO.getUserCode())){
									isUserValid = true;
									break;
								}
							}
							if(!isUserValid){
								if(invalidUsers.length()==0){
									invalidUsers.append(assigneeCode);
								}else{
									invalidUsers.append(", ").append(assigneeCode);
								}
							}
						}else{
							/*
							 * no valid usersVOs 
							 */
							if(invalidUsers.length()==0) {
								invalidUsers.append(assigneeCode);
							} else {
								invalidUsers.append(", ").append(assigneeCode);
							}
						}
					}
					log.log(Log.INFO, "invalidUsers===>>", invalidUsers);
					invocationContext.addError(new ErrorVO(
							ERR_INVALIDASSIGNEE, new Object[]{invalidUsers}));
					invocationContext.target=ACTION_FAILURE;
					return;
				}
			}
		}
		
		/*
		 * Defaulting empty Assigned dates for save
		 */
		if(saveGPAReportingVOs!=null && saveGPAReportingVOs.size()>0){
			for(GPAReportingClaimDetailsVO gpaReportingDetailsVO :saveGPAReportingVOs){
					/*
					 * Defaulting empty Assignee Dates with Current date 
					 */
					if(gpaReportingDetailsVO.getAssignedDate()==null){
						log
								.log(
										Log.INFO,
										"gpaReportingDetailsVO.getAssignedDate(empty date)>>>",
										gpaReportingDetailsVO.getAssignedDate());
						gpaReportingDetailsVO.setAssignedDate(
								new LocalDate(LocalDate.NO_STATION,Location.NONE, false));
					}
					log
							.log(
									Log.INFO,
									"gpaReportingDetailsVO.getAssignedDate(empty date defaulting)>>>",
									gpaReportingDetailsVO.getAssignedDate());
			}

			log.log(Log.INFO, "saveGPAReportingVOs------>>>>",
					saveGPAReportingVOs);
			/*
			 * Delegate call for save
			 */
			if(saveGPAReportingVOs!=null && saveGPAReportingVOs.size()>0){
				try{
					new MailTrackingMRADelegate().assignClaims(saveGPAReportingVOs);
				}catch(BusinessDelegateException businessDelegateException){
					handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
					log.log(Log.WARNING,"save exception caught!!!!!!");
//					assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
					invocationContext.target = ACTION_FAILURE;		
					return;
				}
				invocationContext.addError(new ErrorVO(INFO_DATASAVED));
			}
		} else{
			invocationContext.addError(new ErrorVO(INFO_NODATAFORSAVE));
			invocationContext.target = ACTION_FAILURE;
			return;
		}
//		assignExceptionsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = ACTION_SUCCESS;
		assignExceptionsSession.removeGpaReportingFilterVO();
		assignExceptionsSession.removeGpaReportingDetailVOs();
		clearFormFields(assignExceptionsForm);
		log.exiting(CLASS_NAME, "execute");
	}	
	/**
	 * method to updateGPADetails to Session
	 * @param assignExceptionsSession
	 * @param assignExceptionsForm
	 */
	private void updateGPADetailsSession(AssignExceptionsSession assignExceptionsSession, 
									AssignExceptionsForm assignExceptionsForm){
		log.entering(CLASS_NAME, "updateGPADetailsSession");
		if(assignExceptionsSession.getGpaReportingDetailVOs()!=null &&
				assignExceptionsSession.getGpaReportingDetailVOs().size()>0){
			String[] opFlag = assignExceptionsForm.getOperationFlag();
			String[] assignedUser = assignExceptionsForm.getAssignedUser();
			String[] assignedDate = assignExceptionsForm.getAssignedDate();
			
			int index=0;
			for(GPAReportingClaimDetailsVO gpaReportingDetailsVO :
							assignExceptionsSession.getGpaReportingDetailVOs()){
				if(GPAReportingClaimDetailsVO.OPERATION_FLAG_UPDATE.equals(opFlag[index])){
					log.log(Log.INFO, "opFlag[index]>>>>>", opFlag, index);
					gpaReportingDetailsVO.setOperationFlag(
							GPAReportingClaimDetailsVO.OPERATION_FLAG_UPDATE);
					log.log(Log.INFO, "assignedUser[index]>>>>>", assignedUser,
							index);
					gpaReportingDetailsVO.setAssignedUser(assignedUser[index].trim().toUpperCase());
					log.log(Log.INFO, "assignedDate[index]>>>>>", assignedDate,
							index);
					if(assignedDate[index].trim().length()>0){
						gpaReportingDetailsVO.setAssignedDate(
								new LocalDate(LocalDate.NO_STATION,Location.NONE, false)
								.setDate(assignedDate[index].trim()));
					}else{
						gpaReportingDetailsVO.setAssignedDate(null);
					}
				}
				index++;
			}
		}
		log.exiting(CLASS_NAME, "updateGPADetailsSession");
	}
	/**
	 * method clear FormFields
	 * @param assignExceptionsForm
	 */
	private void clearFormFields(AssignExceptionsForm assignExceptionsForm){
		log.entering(CLASS_NAME,"clearFormFields");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		assignExceptionsForm.setGpaCode(EMPTY_STRING);
		assignExceptionsForm.setGpaName(EMPTY_STRING);
		assignExceptionsForm.setCountryCode(EMPTY_STRING);
		assignExceptionsForm.setFromDate(EMPTY_STRING);
		assignExceptionsForm.setToDate(EMPTY_STRING);
		assignExceptionsForm.setExceptionCode(EMPTY_STRING);
		assignExceptionsForm.setAssignee(EMPTY_STRING);
		/*
		 * localdate defined
		 */
		LocalDate localDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		String localDateString = TimeConvertor.toStringFormat(localDate,logonAttributes.getDateFormat());
		assignExceptionsForm.setFromDate(localDateString);
		assignExceptionsForm.setToDate(localDateString);
		/*hidden fields*/
		assignExceptionsForm.setSaveFlag(EMPTY_STRING);
		assignExceptionsForm.setDisplayPage("1");
		assignExceptionsForm.setLastPageNum("0");
		log.exiting(CLASS_NAME,"clearFormFields");
	}
}
