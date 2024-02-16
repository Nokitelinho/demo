/*
 * CloseEmbargoCommand.java Created on May 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.searchembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos.SearchEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/** * Command class for Closing Search Embargo page
 *
 * @author A-5867
 *
 */
public class CloseEmbargoCommand extends BaseCommand {

	private static final String SUCCESS = "success";
	
	private static final String FAILURE = "failure";
	
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");
	
	private static final String MODULE_NAME = "reco";
	private static final String SCREENID ="reco.defaults.searchembargo";
/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearEmbargoCommand","Execute");
		SearchEmbargoForm searchEmbargoForm = (SearchEmbargoForm)invocationContext.screenModel;
		SearchEmbargoSession searchEmbargoSession =getScreenSession(MODULE_NAME,SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		clearFormValues(searchEmbargoForm);
		searchEmbargoSession.removeEmbargoSearchVO();		
		List<RegulatoryMessageVO> regulatoryMessages = null;
		RegulatoryMessageFilterVO filter= new RegulatoryMessageFilterVO();
		filter.setCompanyCode(logonAttributes.getCompanyCode());
		filter.setRolGroup(logonAttributes.getRoleGroupCode());
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);	
		filter.setCurrentDate(currentDate);
		LocalDate utcDate = null;
		try {
			EmbargoRulesDelegate embargoRulesDelegate = new EmbargoRulesDelegate();
			regulatoryMessages = embargoRulesDelegate.findAllRegulatoryMessages(filter);
			if(null !=regulatoryMessages && regulatoryMessages.size()>0){
				for(RegulatoryMessageVO regulatoryMessage:regulatoryMessages){
					if(null !=regulatoryMessage.getUpdatedTransactionTime() ){				
						utcDate = new LocalDate(regulatoryMessage.getUpdatedTransactionTime(),logonAttributes.getStationCode(),Location.STN);
						regulatoryMessage.setUpdatedTransactionTimeView(utcDate);
					}
				}
			}
			searchEmbargoSession.setRegulatoryComposeMessages(regulatoryMessages);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		if(errors != null && errors.size () > 0){
			   invocationContext.addAllError(errors);
			   invocationContext.target = FAILURE;
			   return;
		}
		invocationContext.target =SUCCESS;
		log.exiting("ClearEmbargoCommand","Execute");
	}
	
private void clearFormValues(SearchEmbargoForm searchEmbargoForm) {
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes = applicationSession.getLogonVO();
	searchEmbargoForm.setGeographicLevelType(null);
	searchEmbargoForm.setGeographicLevel(null);
	searchEmbargoForm.setDayOfOperationApplicableOn(null);
	searchEmbargoForm.setOriginType(null);
	searchEmbargoForm.setOrigin(null);
	searchEmbargoForm.setApplicableTransaction(null);
	searchEmbargoForm.setCategory(null);
	searchEmbargoForm.setRuleType(null);
	searchEmbargoForm.setDestination(null);
	searchEmbargoForm.setDestinationType(null);
	searchEmbargoForm.setEmbargoDate(null);
	LocalDate fromLocalDate = null;
	fromLocalDate = new LocalDate(logonAttributes.getStationCode(), Location.STN, true);
	searchEmbargoForm.setFromDate(
			fromLocalDate.toDisplayDateOnlyFormat());
	searchEmbargoForm.setLevelCode(null);
	searchEmbargoForm.setParameterCode(null);
	searchEmbargoForm.setParameterValue(null);
	searchEmbargoForm.setToDate(null);
	searchEmbargoForm.setViaPoint(null);
	searchEmbargoForm.setViaPointType(null);
}
	
	
}
