/*
 * ViewCN66DetailsCommand.java Created on Mar 14,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listCN51;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListCN51ScreenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListCN51ScreenForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2049
 *
 */
public class ViewCN66DetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MailTracking:Mra:Defaults");

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listCN51s";

	private static final String MODULE_NAME_CN66 = "mailtracking.mra.airlinebilling.defaults";

	private static final String SCREEN_ID_CN66 = "mailtracking.mra.airlinebilling.defaults.capturecn66";

	private static final String VIEW_CN51_SUCCESS = "view_success";

	private static final String VIEW_CN51_FAILURE = "view_failure";
	
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.billingtype";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String KEY_DESPATCH_STATUS_ONETIME = "mailtracking.mra.despatchstatus";
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mailtracking.defaults.DsnLevelImportToMRA";


	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invContext)
			throws CommandInvocationException {
		log.entering("ViewCN66DetailsCommand","execute");
		ListCN51ScreenForm cn51ScreenForm
								= (ListCN51ScreenForm)invContext.screenModel;
		ListCN51ScreenSession cn51ScreenSession
								= (ListCN51ScreenSession)getScreenSession(MODULE_NAME,SCREEN_ID);

		CaptureCN66Session cn66ScreenSession
								= (CaptureCN66Session)getScreenSession(MODULE_NAME_CN66,SCREEN_ID_CN66);

		// set the filterVO to session for displaying the details back
		// after returning to the same screen
		updateFilterVOInSession(cn51ScreenSession,cn51ScreenForm);

		Page<AirlineCN51SummaryVO> listedVOs = null;
		listedVOs =(Page<AirlineCN51SummaryVO>)
								(cn51ScreenSession.getAirlineCN51SummaryVOs());

		String[] selectedRowIds = cn51ScreenForm.getTableRowId();

		if(selectedRowIds != null && selectedRowIds.length > 0 ){
			log.log(Log.INFO, "#### No of Rows Selected ",
					selectedRowIds.length);
			log.log(Log.INFO, "#### selected Row Index ", selectedRowIds);
		}else{
			log.log(Log.INFO,"#### Now Rows Selected ");
			invContext.target = VIEW_CN51_FAILURE;
			log.exiting("ViewCN51DetailsCommand","execute");
			return;
		}

		AirlineCN51SummaryVO selectedVO
						= listedVOs.get( Integer.parseInt(selectedRowIds[0]) );
		
		cn66ScreenSession.setCn66FilterVO(constructCN66FilterVO(selectedVO));		
		cn66ScreenSession.setParentId(SCREEN_ID);
		
		
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add(KEY_BILLING_TYPE_ONETIME);
		oneTimeActiveStatusList.add(KEY_CATEGORY_ONETIME);
		oneTimeActiveStatusList.add(KEY_DESPATCH_STATUS_ONETIME);
		setOneTimesForDisplay(cn66ScreenSession,
							  cn51ScreenSession.getAirlineCN51FilterVO().getCompanyCode(),
							  oneTimeActiveStatusList);

		invContext.target = VIEW_CN51_SUCCESS;
		log.exiting("ViewCN66DetailsCommand","execute");
	}


	private void updateFilterVOInSession(ListCN51ScreenSession cn51ScreenSession,
			 ListCN51ScreenForm cn51ScreenForm ) {
		AirlineCN51FilterVO formFilterVO = new AirlineCN51FilterVO();
		if(cn51ScreenForm.getAirlineCode() != null &&
		cn51ScreenForm.getAirlineCode().length() > 0 ){
		formFilterVO.setAirlineCode(cn51ScreenForm.getAirlineCode().toUpperCase().trim());
		}
		formFilterVO.setBilledDateFrom
		(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
		.setDate(cn51ScreenForm.getBlgFromDateStr()));
		formFilterVO.setBilledDateTo
		(new LocalDate(LocalDate.NO_STATION,Location.NONE,false)
		.setDate(cn51ScreenForm.getBlgToDateStr()));
		LogonAttributes logonAttributes = this.getApplicationSession().getLogonVO();
		formFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		formFilterVO.setInterlineBillingType(cn51ScreenForm.getInterlineBlgType());

		cn51ScreenSession.setAirlineCN51FilterVO(formFilterVO);

	}
	
	/**
	 * 
	 * @param selectedVO
	 * @return
	 */
	private AirlineCN66DetailsFilterVO constructCN66FilterVO(AirlineCN51SummaryVO selectedVO) {
		log.entering("ViewCN51DetailsCommand","constructCN66FilterVO");
		AirlineCN66DetailsFilterVO cn66FilterVO 
								= new AirlineCN66DetailsFilterVO();
		cn66FilterVO.setCompanyCode(selectedVO.getCompanycode());
		cn66FilterVO.setInvoiceRefNumber(selectedVO.getInvoicenumber());
		cn66FilterVO.setClearancePeriod(selectedVO.getClearanceperiod());		
		cn66FilterVO.setInterlineBillingType(selectedVO.getInterlinebillingtype());
		cn66FilterVO.setAirlineId(selectedVO.getAirlineidr());
		cn66FilterVO.setAirlineCode(selectedVO.getAirlinecode());
		log.exiting("ViewCN51DetailsCommand","constructCN66FilterVO");
		return cn66FilterVO;
	}
	
	private void setOneTimesForDisplay(CaptureCN66Session cn66ScreenSession,
									   String companyCode , 
									   Collection<String> fieldTypes) {
		log.entering("ViewCN51DetailsCommand","setOneTimesForDisplay");
		Map<String,Collection<OneTimeVO>> oneTimeMap = null;
		Map<String, String> systemParameterValues = null;
		try {
			oneTimeMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldTypes);
			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
			log.log(Log.SEVERE," ####### Error in finding OneTimes ");
			oneTimeMap = null;
		} 		
		cn66ScreenSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeMap);
		cn66ScreenSession.setSystemparametres((HashMap<String, String>)systemParameterValues);
		log.entering("ViewCN51DetailsCommand","setOneTimesForDisplay");
	}
	 private Collection<String> getSystemParameterTypes(){
	    	log.entering("RefreshCommand", "getSystemParameterTypes");
	    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
	    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);
	    	log.exiting("ScreenLoadCommand", "getSystemParameterTypes");
	    	return systemparameterTypes;
	      }

}
