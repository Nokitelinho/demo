/*
 * ScreenloadCommand.java Created on Nov 22, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest.ScreenloadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8527	:	22-Nov-2018	:	Draft
 */
public class ScreenloadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Force Majeure ScreenLoad Command ");
	private static final String TARGET = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";

	private static final String FLTTYPE_ONETIME = "mailtracking.defaults.forcemajeurerequest.source";
	private static final String SCANTYPE_ONETIME = "mailtracking.defaults.forcemajeurerequest.scantype";
	

	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenloadCommand", "execute");
		
		ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ForceMajeureRequestSession forceMajeureRequestSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		
		   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		    String companyCode = logonAttributes.getCompanyCode();
		    String stationCode = logonAttributes.getStationCode();
		    String userId = logonAttributes.getUserId();
		    String airportCode = logonAttributes.getAirportCode();
		    this.log.log(3, new Object[] { "companyCode =  ", companyCode });
		    this.log.log(3, new Object[] { "stationCode =  ", stationCode });
		    this.log.log(3, new Object[] { "userId =  ", userId });
		    Map <String, Collection<OneTimeVO>>oneTimeSource = getOneTimeValues(companyCode);
		    
		    String displayPage = forceMajeureRequestForm.getDisplayPage();
			String defaultSize = forceMajeureRequestForm.getDefaultPageSize();
					
			forceMajeureRequestForm.setScanType("ALL");      
		    forceMajeureRequestForm.setOrigin_airport("");
		    forceMajeureRequestForm.setDestination("");
		    forceMajeureRequestForm.setViaPoint("");
		    forceMajeureRequestForm.setAffectedAirport("");
		    forceMajeureRequestForm.setPacode("");
		    forceMajeureRequestForm.setFlightNumber("");
		    forceMajeureRequestForm.setFlightDateStr("");
		    forceMajeureRequestForm.setFrmDate("");
		    forceMajeureRequestForm.setToDate("");
		    forceMajeureRequestForm.setTotalRecords("-1");
		    forceMajeureRequestForm.setSource("O");
		    forceMajeureRequestForm.setDefaultPageSize(defaultSize);
		    Collection<OneTimeVO> sourceOneTime = (Collection<OneTimeVO>) oneTimeSource
					.get(FLTTYPE_ONETIME);
		    Collection<OneTimeVO> scanTypeOneTime = (Collection<OneTimeVO>) oneTimeSource
					.get(SCANTYPE_ONETIME);
			if (sourceOneTime != null) {
				log.log(Log.INFO, "Sizeee----", sourceOneTime.size());
				for (OneTimeVO list : sourceOneTime) {
					log.log(Log.INFO, "LIST----------", list.getFieldDescription());
				}
			}
		    forceMajeureRequestSession.setSource((ArrayList<OneTimeVO>)sourceOneTime);
		    forceMajeureRequestSession.setScanType((ArrayList<OneTimeVO>)scanTypeOneTime);              
		    forceMajeureRequestSession.setTotalRecords(Integer.parseInt(forceMajeureRequestForm.getTotalRecords()));
		    forceMajeureRequestSession.setFilterParamValues(null);
		    forceMajeureRequestSession.setListforcemajeurevos(null);

		    
		log.exiting("ScreenLoadCommand","exiting");

	}

	private Map<String, Collection<OneTimeVO>> getOneTimeValues(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimeValues = new HashMap<String, Collection<OneTimeVO>>();
	    Collection <String> oneTimeList = new ArrayList<String>();
	    oneTimeList.add(FLTTYPE_ONETIME); 
	    oneTimeList.add(SCANTYPE_ONETIME);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		return  oneTimeValues;
	}

}