/*
 * SaveCommand.java Created on May 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.composemessages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.composemessages.RegulatoryComposeMessageSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.RegulatoryComposeMessageForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/** * Command class for saving Compose messages
*
* @author A-5867
*
*/
public class SaveCommand extends BaseCommand{

	/** Logger for Regulatory Compose Messages Log. */
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");

	/** The Module Name. */
	private static final String MODULE = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.maintainregulatorycompliance";
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS = "success";
	
	/** The Constant FAILURE. */
	private static final String FAILURE = "failure";
	
	

	/**
	 * execute method.
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
	public void execute(InvocationContext invocationContext)
				throws CommandInvocationException {
			log.entering("SaveCommand","execute ");    
			RegulatoryComposeMessageForm composeMessageForm = (RegulatoryComposeMessageForm) invocationContext.screenModel;
			RegulatoryComposeMessageSession session = (RegulatoryComposeMessageSession) getScreenSession(MODULE, SCREENID);
			Collection<RegulatoryMessageVO> regulatoryMessageList = new ArrayList<RegulatoryMessageVO>();
			RegulatoryMessageVO regulatoryMessage= null;
	    	ApplicationSession appSession = (ApplicationSession)getApplicationSession();
			LogonAttributes logonVO = appSession.getLogonVO();
			Collection<UserRoleGroupDetailsVO> userRoleGroupDetails=null;
			EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
			boolean isRoleGroupEmpty = false;
			boolean isMessageEmpty = false;
			boolean isMessageLong = false;
			boolean isStartDateEmpty = false;
			boolean isEndDateEmpty = false;
			boolean isValidSave = true;
			String companyCode = logonVO.getCompanyCode();
			String stationCode = logonVO.getStationCode();
	    	
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	session.setRegulatoryMessageErrorList(null);
	    	session.setRegulatoryMessages(null);
	    	String[] serialNumbers = composeMessageForm.getSerialNumbers();
	    	String[] roleGroups = composeMessageForm.getRoleGroups();
	    	String[] messages = composeMessageForm.getMessages();
	    	String[] startDates = composeMessageForm.getStartDates();
	    	String[] endDates = composeMessageForm.getEndDates();
	    	String[] hiddenOpFlag =  composeMessageForm.getHiddenOpFlag();
	    	Collection<String> roleGroupCollection= new ArrayList<String>();
	    	Map<String,String> roleGroupMap=new HashMap<String, String>();
    		if(null !=roleGroups && roleGroups.length>0){
    			for(String roleDroup:roleGroups){
    				if(null !=roleDroup && roleDroup.length()>0){
    					for(String role:roleDroup.split(",")){
    						if(null !=role && role.length()>0){
    							roleGroupMap.put(role.toUpperCase(), role.toUpperCase());
    						}   						
    					} 					
    				}
    			}
    		}
    		if(roleGroupMap.size()>0 ){
    			for(String role:roleGroupMap.keySet()){
    				roleGroupCollection.add(role);
    			}
    			
    		}
	    	
	    	LocalDate date = null;		
	    	for (int index = 0; index < messages.length - 1; index++) {
				if (!("NOOP".equals(hiddenOpFlag[index]) )) {
					if(RegulatoryMessageVO.OPERATION_FLAG_INSERT.equals(hiddenOpFlag[index])|| 
							RegulatoryMessageVO.OPERATION_FLAG_UPDATE.equals(hiddenOpFlag[index])){
						regulatoryMessage = new RegulatoryMessageVO();
						regulatoryMessage.setCompanyCode(companyCode);
						LocalDate updatedTransactionTime =new LocalDate(stationCode,Location.STN, true);
						regulatoryMessage.setUpdatedTransactionTime(updatedTransactionTime.toGMTDate());
						if(null !=roleGroups && null !=roleGroups[index] && roleGroups[index].length()>0){
							regulatoryMessage.setRolGroup(roleGroups[index].toUpperCase());
						}else{
							isRoleGroupEmpty=true;
							regulatoryMessage.setRolGroup(null);
						}
						if(null !=messages && null !=messages[index] && messages[index].length()>0
								&& messages[index].length()<=3000){
							regulatoryMessage.setMessage(messages[index]);
						}else if(null !=messages && null !=messages[index] && messages[index].length()>0){
							regulatoryMessage.setMessage(messages[index]);
							isMessageLong=true;
						}else{
							isMessageEmpty=true;
							regulatoryMessage.setMessage(null);
						}
						if(null !=hiddenOpFlag && null !=hiddenOpFlag[index] && hiddenOpFlag[index].length()>0){
							regulatoryMessage.setOperationFlag(hiddenOpFlag[index]);
						}
						if(null !=serialNumbers && null !=serialNumbers[index] && serialNumbers[index].length()>0){
							regulatoryMessage.setSerialNumber(new Integer(serialNumbers[index]).intValue());
						}
						if(null !=startDates && null !=startDates[index] && startDates[index].length()>0){
							date = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);	
							date.setDate(startDates[index]);
							regulatoryMessage.setStartDate(date);
						}else{
							isStartDateEmpty=true;
							regulatoryMessage.setStartDate(null);
						}
						if(null !=endDates && null !=endDates[index] && endDates[index].length()>0){
							date = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
							date.setDate(endDates[index]);
							regulatoryMessage.setEndDate(date);
						}else{
							isEndDateEmpty=true;
							regulatoryMessage.setEndDate(null);
						}
						regulatoryMessage.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
						regulatoryMessage.setLastUpdateUser(logonVO.getUserId());
						regulatoryMessageList.add(regulatoryMessage);
						
					}else if(RegulatoryMessageVO.OPERATION_FLAG_DELETE.equals(hiddenOpFlag[index])){
						regulatoryMessage = new RegulatoryMessageVO();
						regulatoryMessage.setCompanyCode(companyCode);
						regulatoryMessage.setOperationFlag(RegulatoryMessageVO.OPERATION_FLAG_DELETE);
						if(null !=serialNumbers && null !=serialNumbers[index] && serialNumbers[index].length()>0){
							regulatoryMessage.setSerialNumber(new Integer(serialNumbers[index]).intValue());
							regulatoryMessageList.add(regulatoryMessage);
						}
						
					}else if("N".equals(hiddenOpFlag[index])){
						regulatoryMessage = new RegulatoryMessageVO();
						regulatoryMessage.setCompanyCode(companyCode);
						regulatoryMessage.setOperationFlag("N");
						regulatoryMessage.setRolGroup(roleGroups[index].toUpperCase());
						regulatoryMessage.setMessage(messages[index]);
						
						date = new LocalDate(stationCode,Location.STN, true);	
						date.setDate(startDates[index]);
						regulatoryMessage.setStartDate(date);
						date = new LocalDate(stationCode,Location.STN, true);	
						date.setDate(endDates[index]);
						regulatoryMessage.setEndDate(date);
						
						if(null !=serialNumbers && null !=serialNumbers[index] && serialNumbers[index].length()>0){
							regulatoryMessage.setSerialNumber(new Integer(serialNumbers[index]).intValue());						
						}
						regulatoryMessageList.add(regulatoryMessage);
						
					}
				}
	    	}
	    	if(regulatoryMessageList.size()<1){
	    		ErrorVO error = new ErrorVO("reco.composemessage.nodatatosave");
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				invocationContext.addError(error);
				invocationContext.target = FAILURE;
				return;
	    	}else if(isRoleGroupEmpty ||isMessageEmpty || isStartDateEmpty || isEndDateEmpty || isMessageLong){
	    		session.setRegulatoryMessageErrorList((ArrayList<RegulatoryMessageVO>)regulatoryMessageList);
	    		isValidSave=false;
	    	}
	    	if(isRoleGroupEmpty){
				ErrorVO error = new ErrorVO("reco.composemessage.rolgroupempty");
				invocationContext.addError(error);
	    	}
	    	if(isMessageEmpty){
				ErrorVO error = new ErrorVO("reco.composemessage.messageempty");
				invocationContext.addError(error);
	    	}
	    	if(isStartDateEmpty){
				ErrorVO error = new ErrorVO("reco.composemessage.startdateempty");
				invocationContext.addError(error);
	    	}
	    	if(isEndDateEmpty){
				ErrorVO error = new ErrorVO("reco.composemessage.enddateempty");
				invocationContext.addError(error);
	    	}
	    	if(isMessageLong){
				ErrorVO error = new ErrorVO("reco.composemessage.longmessage");
				invocationContext.addError(error);
	    	}
	    	try {
	    		if(null !=roleGroupCollection && roleGroupCollection.size()>0){    			
	    			userRoleGroupDetails=delegate.validateRoleGroup(roleGroupCollection, companyCode);
	    		}	
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if(errors != null && errors.size () > 0){
				   invocationContext.addAllError(errors);
				   invocationContext.target = FAILURE;
				   return;
			}
			if(!isRoleGroupEmpty && (null == userRoleGroupDetails || roleGroupCollection.size()!=userRoleGroupDetails.size())){
				session.setRegulatoryMessageErrorList((ArrayList<RegulatoryMessageVO>)regulatoryMessageList);
				ErrorVO error = new ErrorVO("reco.composemessage.invalidrolegroup");
				invocationContext.addError(error);
				isValidSave=false;
			}
	    	if (isValidSave) {
				try {					
					delegate.saveRegulatoryMessages(regulatoryMessageList);
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
				}
				if(errors != null && errors.size () > 0){
					   invocationContext.addAllError(errors);
					   invocationContext.target = FAILURE;
					   return;
				}else{
					composeMessageForm.setRoleGroup(null);
			    	composeMessageForm.setMessages(null);
			    	composeMessageForm.setStartDate(null);
			    	composeMessageForm.setStartDates(null);
			    	composeMessageForm.setEndDate(null);
			    	composeMessageForm.setEndDates(null);
			    	composeMessageForm.setRoleGroups(null);
			    	composeMessageForm.setSerialNumbers(null);
			    	
			    	session.removeRegulatoryMessages();
					session.removeRegulatoryMessageErrorList();
					ErrorVO error = new ErrorVO("reco.composemessage.savesuccess");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);
				}
				invocationContext.target = SUCCESS;
			}else{
				invocationContext.target = FAILURE;
			}
	    	
	    	}

}
