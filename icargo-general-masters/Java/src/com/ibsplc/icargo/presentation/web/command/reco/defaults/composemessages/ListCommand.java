/*
 * ListCommand.java Created on May 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.composemessages;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.composemessages.RegulatoryComposeMessageSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.RegulatoryComposeMessageForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/** * Command class for listing  Compose messages
*
* @author A-5867
*
*/
public class ListCommand extends BaseCommand{
	
	/** Logger for Regulatory Compose Messages Log. */
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");

	/** The Module Name. */
	private static final String MODULE = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.maintainregulatorycompliance";
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS = "success";
	
	/** The Constant SUCCESS. */
	private static final String FAILURE = "failure";
 
    /**
     * execute method.
     *
     * @param invocationContext the invocation context
     * @throws CommandInvocationException the command invocation exception
     */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ListCommand","execute "); 
    	RegulatoryComposeMessageForm composeMessageForm = (RegulatoryComposeMessageForm) invocationContext.screenModel;
		RegulatoryComposeMessageSession session = (RegulatoryComposeMessageSession) getScreenSession(MODULE, SCREENID);
    	ApplicationSession appSession = (ApplicationSession)getApplicationSession();
		LogonAttributes logonVO = appSession.getLogonVO();
		Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
		Collection<String> roleGroupCollection= new ArrayList<String>();
		Collection<UserRoleGroupDetailsVO> userRoleGroupDetails=null;
	  	composeMessageForm.setMessages(null);
    	composeMessageForm.setStartDates(null);
    	composeMessageForm.setEndDates(null);
    	composeMessageForm.setRoleGroups(null);
    	composeMessageForm.setSerialNumbers(null);
		Page<RegulatoryMessageVO> regulatoryMessages = null;
		RegulatoryMessageFilterVO filter= new RegulatoryMessageFilterVO();
		session.removeRegulatoryMessages();
		session.removeRegulatoryMessageErrorList();
		String companyCode = logonVO.getCompanyCode();
		filter.setCompanyCode(companyCode);
		if((null ==composeMessageForm.getRoleGroup() || composeMessageForm.getRoleGroup().length()<1) &&
				(null ==composeMessageForm.getStartDate() || composeMessageForm.getStartDate().length()<1) &&
				(null ==composeMessageForm.getEndDate() || composeMessageForm.getEndDate().length()<1)){
			ErrorVO error = new ErrorVO("reco.composemessage.rolgroupanddateempty");
			errors.add(error);	
		}else if(null !=composeMessageForm.getRoleGroup() && composeMessageForm.getRoleGroup().length()>0){
			filter.setRolGroup(composeMessageForm.getRoleGroup().toUpperCase());	
			roleGroupCollection.add(composeMessageForm.getRoleGroup().toUpperCase());
			EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
			try {
	    		userRoleGroupDetails=delegate.validateRoleGroup(roleGroupCollection, companyCode);
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if(errors != null && errors.size () > 0){
				   invocationContext.addAllError(errors);
				   invocationContext.target = FAILURE;
				   return;
			}
			if(null == userRoleGroupDetails){
				ErrorVO error = new ErrorVO("reco.composemessage.invalidrolegroup");
				invocationContext.addError(error);
				invocationContext.target = FAILURE;
				return;
			}
			if(null !=composeMessageForm.getStartDate() && composeMessageForm.getStartDate().length()>0 &&
					null !=composeMessageForm.getEndDate() && composeMessageForm.getEndDate().length()>0){
				filter.setStartDate(composeMessageForm.getStartDate());
				filter.setEndDate(composeMessageForm.getEndDate());
			}
			
		}else if(null !=composeMessageForm.getStartDate() && composeMessageForm.getStartDate().length()>0 &&
				null !=composeMessageForm.getEndDate() && composeMessageForm.getEndDate().length()>0){
			filter.setStartDate(composeMessageForm.getStartDate());
			filter.setEndDate(composeMessageForm.getEndDate());
		}else if(null !=composeMessageForm.getStartDate() && composeMessageForm.getStartDate().length()>0 &&
				(null ==composeMessageForm.getEndDate() ||composeMessageForm.getEndDate().length()<1)){
			ErrorVO error = new ErrorVO("reco.composemessage.enddateempty");
			errors.add(error);
		}else if(null !=composeMessageForm.getEndDate() && composeMessageForm.getEndDate().length()>0 &&
				(null ==composeMessageForm.getStartDate() ||composeMessageForm.getStartDate().length()<1)){
			ErrorVO error = new ErrorVO("reco.composemessage.startdateempty");
			errors.add(error);
		}else if(null ==composeMessageForm.getRoleGroup() || composeMessageForm.getRoleGroup().length()<1){
			ErrorVO error = new ErrorVO("reco.composemessage.rolgroupempty");
			errors.add(error);
		}
		if(errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		String displayPage = composeMessageForm.getDisplayPage();
		if(null !=displayPage && displayPage.length()>0){
			filter.setPageNumber(Integer.parseInt(displayPage));
		}
		filter.setAbsoluteIndex(0);
		
		if(RegulatoryComposeMessageForm.PAGINATION_MODE_FROM_FILTER.equals(composeMessageForm.getNavigationMode())) {
			filter.setTotalRecordCount(-1);
		}else if(RegulatoryComposeMessageForm.PAGINATION_MODE_FROM_NAVIGATION.equals(composeMessageForm.getNavigationMode())) {
			filter.setTotalRecordCount(session.getTotalRecords());
		}else {
			filter.setTotalRecordCount(-1);
		}
		try {
			EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
			regulatoryMessages = delegate.findRegulatoryMessages(filter);
			if(null !=regulatoryMessages){
				session.setTotalRecords(regulatoryMessages.getTotalRecordCount());
				session.setRegulatoryMessages(regulatoryMessages);
			}else{
				ErrorVO error = new ErrorVO("embargo.nulllist");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);	
				session.removeRegulatoryMessages();
			}
			
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		if(errors != null && errors.size () > 0){
			   invocationContext.addAllError(errors);
			   invocationContext.target = FAILURE;
			   return;
		}
    	invocationContext.target = SUCCESS;
    	log.exiting("ListCommand","execute");
    	}	
}
