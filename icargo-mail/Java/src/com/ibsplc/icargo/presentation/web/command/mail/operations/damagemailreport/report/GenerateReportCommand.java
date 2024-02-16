/*
 * GenerateReportCommand.java Created on JUL 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.damagemailreport.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DamageMailFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamageMailReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class GenerateReportCommand   extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTOPS012";
	private Log log = LogFactory.getLogger("DamageMailReport");	
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateFindDamageMailReport";
	private static final String BUNDLE = "damageMailReportResources";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("GenerateReportComand", "starts");
		DamageMailReportForm damageMailReportForm =(DamageMailReportForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();	
		
		DamageMailFilterVO damageMailReportFilterVO = new DamageMailFilterVO();
		damageMailReportFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(damageMailReportForm.getAirlineId()!=null && damageMailReportForm.getAirlineId().length() >0 ){
			damageMailReportFilterVO.setAirlineId(Integer.parseInt(damageMailReportForm.getAirlineId()));			
		}
		damageMailReportFilterVO.setAirport(damageMailReportForm.getAirport());
		damageMailReportFilterVO.setDamageCode(damageMailReportForm.getDamageCode());
		damageMailReportFilterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(damageMailReportForm.getFromDate()));
		damageMailReportFilterVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(damageMailReportForm.getToDate()));
		//added by A-5844 for ICRD-67196 starts
		damageMailReportFilterVO.setFlightCarrierCode(damageMailReportForm.getFlightCarrierCode());
		damageMailReportFilterVO.setFlightNumber(damageMailReportForm.getFlightNumber());
		if(damageMailReportForm.getFlightDate()!=null && damageMailReportForm.getFlightDate().trim().length()>0){
			damageMailReportFilterVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(damageMailReportForm.getFlightDate()));
		}		
		damageMailReportFilterVO.setFlightOrigin(damageMailReportForm.getFlightOrigin());
		damageMailReportFilterVO.setFlightDestination(damageMailReportForm.getFlightDestination());
		damageMailReportFilterVO.setGpaCode(damageMailReportForm.getGpaCode());
		damageMailReportFilterVO.setOriginOE(damageMailReportForm.getOriginOE());
		damageMailReportFilterVO.setDestinationOE(damageMailReportForm.getDestinationOE());
		damageMailReportFilterVO.setSubClassGroup(damageMailReportForm.getSubClassGroup());
		damageMailReportFilterVO.setSubClassCode(damageMailReportForm.getSubClassCode());
		//added by A-5844 for ICRD-67196 ends
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		Collection<OneTimeVO> damageCodes=new ArrayList<OneTimeVO>();
		if(oneTimes!=null){
			damageCodes = oneTimes.get("mailtracking.defaults.return.reasoncode");
		}

		ReportSpec reportSpec = getReportSpec();				
		if(damageMailReportForm.getReportFlag()!=null && "Y".equals(damageMailReportForm.getReportFlag()))
			{
			reportSpec.setReportId("RPRMTK093");
			}
		else
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(damageMailReportFilterVO);
		reportSpec.addExtraInfo(damageCodes);
		reportSpec.setAction(ACTION);

		generateReport();
		invocationContext.target = getTargetPage();
		
		

	}

	/**
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode){
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.return.reasoncode");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}


}
