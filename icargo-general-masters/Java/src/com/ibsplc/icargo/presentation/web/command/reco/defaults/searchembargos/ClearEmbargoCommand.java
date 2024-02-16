/*
 * ClearEmbargoCommand.java Created on May 14, 2014
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
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLeftPanelParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos.SearchEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/** * Command class for Clear Embargo search details
 *
 * @author A-5867
 *
 */
public class ClearEmbargoCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";
	
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");
	
	private static final String MODULE_NAME = "reco";
	private static final String SCREENID ="reco.defaults.searchembargo";
	private static final String LEFT_PANEL_PARAMETERS = "reco.defaults.leftpanelparameters";
/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearEmbargoCommand","Execute");
		SearchEmbargoForm searchEmbargoForm = (SearchEmbargoForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		SearchEmbargoSession searchEmbargoSession =getScreenSession(MODULE_NAME,SCREENID);
		clearFormValues(searchEmbargoForm);
		if(null ==searchEmbargoForm.getCloseBtnFlag()){
			EmbargoSearchVO embargoSearchVO = new EmbargoSearchVO();
			List<EmbargoLeftPanelParameterVO> leftPanelParameterVOs = new ArrayList<EmbargoLeftPanelParameterVO>();
			Map hashMap = null;
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			Collection<String> oneTimeList = new ArrayList<String>();
			oneTimeList.add(LEFT_PANEL_PARAMETERS);
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
	
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			Collection<OneTimeVO> leftPanelParameters= (Collection<OneTimeVO>)hashMap.get(LEFT_PANEL_PARAMETERS);
		     if(null !=leftPanelParameters ){
		    	 EmbargoLeftPanelParameterVO panelParameterVO= null;
				for(OneTimeVO  oneTimeVO:leftPanelParameters){
					panelParameterVO= new EmbargoLeftPanelParameterVO();
					panelParameterVO.setFieldValue(oneTimeVO.getFieldValue());
					panelParameterVO.setFieldDescription(oneTimeVO.getFieldDescription());
					leftPanelParameterVOs.add(panelParameterVO);
				}
			embargoSearchVO.setEmbargoLeftPanelParameterVOs(leftPanelParameterVOs);	
			}
			searchEmbargoSession.setEmbargoSearchVO(embargoSearchVO);
			searchEmbargoSession.removeRegulatoryComplianceRules();
		}
		invocationContext.target =CLEAR_SUCCESS;
		log.exiting("ClearEmbargoCommand","Execute");
	}
	
private void clearFormValues(SearchEmbargoForm searchEmbargoForm) {
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes = applicationSession.getLogonVO();
	if(null !=searchEmbargoForm.getSimpleSearch()){
		searchEmbargoForm.setGeographicLevelType(null);
		searchEmbargoForm.setGeographicLevel(null);
		searchEmbargoForm.setDayOfOperationApplicableOn(null);
	}
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
