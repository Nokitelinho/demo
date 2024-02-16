/*
 * ScreenloadLocationLovCommand.java Created on Mar 17,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadLocationLovCommand is for screenload action of productLov
 * @author a-2667
 *
 */
public class ScreenloadLocationLovCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("ScreenLoad AgreementLOV");
	
	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.listulddiscrepancies";
	
	private static final String BLANK="";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ULD.DEFAULTS", "ScreenloadLocationLovCommand ---------");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
		ListULDDiscrepanciesForm listULDDiscrepanciesForm = (ListULDDiscrepanciesForm)invocationContext.screenModel;
		
		ListUldDiscrepancySession listUldDiscrepancySession = 
			(ListUldDiscrepancySession)getScreenSession(MODULE,SCREENID);
		if(BLANK.equals(listUldDiscrepancySession.getCloseFlag())){
			listUldDiscrepancySession.removeAllAttributes();
		}
		
		String airportCode = logonAttributes.getAirportCode();
		int displayPage=Integer.parseInt(listULDDiscrepanciesForm.getDisplayPage());
		log.log(Log.FINE,
				"listULDDiscrepanciesForm.getReportingStationChild()asdasd",
				listULDDiscrepanciesForm.getReportingStationChild());
		if(listULDDiscrepanciesForm.getReportingStationChild() != null && 
				listULDDiscrepanciesForm.getReportingStationChild().trim().length() > 0){
			airportCode = listULDDiscrepanciesForm.getReportingStationChild().toUpperCase();
			log.log(Log.FINE,
					"listULDDiscrepanciesForm.getReportingStationChild()",
					listULDDiscrepanciesForm.getReportingStationChild());
		}
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try{
			String comboValue = listULDDiscrepanciesForm.getDefaultComboValue() != null
						? listULDDiscrepanciesForm.getDefaultComboValue().toUpperCase() : "";
			log.log(Log.FINE, "compCode----->>", compCode);
			log.log(Log.FINE, "displayPage----->>", displayPage);
			log.log(Log.FINE, "comboValue----->>", comboValue);
			log.log(Log.FINE, "airportCode----->>", airportCode);
			Page<ULDDiscrepancyVO> page =new ULDDefaultsDelegate().
					populateLocationLov(compCode,displayPage,comboValue,airportCode);
			/*if(page != null){
				listULDDiscrepanciesForm.setPageLocationLov(page);
			}else{
				Page<ULDDiscrepancyVO> nullPage = null;
				listULDDiscrepanciesForm.setPageLocationLov(nullPage);
			}*/
			if(page != null && page.size()>0){
				listUldDiscrepancySession.setFacilityTypes(page);
			}
			else{
				Page<ULDDiscrepancyVO> nullPage = null;
				listUldDiscrepancySession.setFacilityTypes(nullPage);
			}
			log.log(Log.FINE, "displayPage ---> ", displayPage);
			log.log(Log.FINE, "page ---> ", page);

		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.exiting("ULD.DEFAULTS", "ScreenloadLocationLovCommand");
		invocationContext.target="screenload_success";
	}

}
