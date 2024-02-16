/*
 * ScreenLoadCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;



/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 07, 2018	     A-2257		First draft
 */

public class ScreenLoadCommand extends AbstractCommand {
	  
	   private static final String MAILSTATUS = "mailtracking.defaults.mailstatus";
	   private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	   private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
	   private static final String OPERATIONTYPE = "mailtracking.defaults.operationtype";
	   private static final String DAMAGE_CODE = "mailtracking.defaults.return.reasoncode";
	   private static final String SERVICE_LEVELS = "mail.operations.mailservicelevels";
	   private static final String DEST_FOR_CDT_MISSING_DOM_MAL="mail.operation.destinationforcarditmissingdomesticmailbag";
	   
	private Log log = LogFactory.getLogger("Mail Operations Mailbag Enquiry ");
	

/**
 * 
 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ScreenLoadCommand", "execute");
		LogonAttributes logonAttributes = (LogonAttributes)getLogonAttribute();
		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate(); 
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), getOneTimeParameterTypes());			
		} catch (BusinessDelegateException e) {
			actionContext.addAllError(handleDelegateException(e));
		}
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(DEST_FOR_CDT_MISSING_DOM_MAL);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
			.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
		sysparValue = systemParameterMap.get(DEST_FOR_CDT_MISSING_DOM_MAL);
		}
		if(sysparValue!=null&&sysparValue.trim().length()>0&&!"NA".equals(sysparValue)){
		mailbagEnquiryModel.setDummyAirportForDomMail(sysparValue);
		}
		
		MailbagFilter mailbagFilter = new MailbagFilter();
	
		mailbagEnquiryModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
		mailbagEnquiryModel.setCarrierCode(logonAttributes.getOwnAirlineCode());
		
		
		mailbagFilter.setAirportCode(logonAttributes.getAirportCode());
		
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);	
	
		mailbagFilter.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailbagFilter.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		mailbagEnquiryModel.setMailbagFilter(mailbagFilter);
		AirportValidationVO airportValidationVO = new AirportValidationVO();
			airportValidationVO = new AreaDelegate().validateAirportCode(
    				logonAttributes.getCompanyCode(),
    				logonAttributes.getAirportCode());
			String countryCode = airportValidationVO.getCountryCode();
		Collection<PostalAdministrationVO> postalAdministrationVOs = new MailTrackingDefaultsDelegate().findLocalPAs(
				logonAttributes.getCompanyCode(),
				countryCode);
		MailOperationsModelConverter mailOperationsModelConverter = new MailOperationsModelConverter();
		mailbagEnquiryModel.setPostalAdministrations(mailOperationsModelConverter.convertPostalAdministartionVos(postalAdministrationVOs));
		ResponseVO responseVO = new ResponseVO(); 
		
		List<MailbagEnquiryModel> results = new ArrayList<MailbagEnquiryModel>();
		results.add(mailbagEnquiryModel); 
		responseVO.setResults(results);	
		actionContext.setResponseVO(responseVO);
		log.exiting("ScreenLoadCommand", "execute");
	}
	
	/**
	 * The method which returns the collection of
	 * onetime parameter types
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
   	
    	Collection<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(MAILSTATUS);
    	parameterTypes.add(CATEGORY); 
    	parameterTypes.add(CONTAINERTYPE);
    	parameterTypes.add(OPERATIONTYPE);
    	parameterTypes.add(DAMAGE_CODE);
    	parameterTypes.add(SERVICE_LEVELS);
    	return parameterTypes;    	
    }

}
