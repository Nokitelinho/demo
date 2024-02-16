/*
 * NavigateToCN38Command.java Created on Aug 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class NavigateToCN38Command extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private static final String CLASS_NAME = "NavigateToCN38Command";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	private static final String VIEW_MODULE_NAME = "mailtracking.defaults";	
	private static final String VIEW_SCREEN_ID = "mailtracking.defaults.consignment";	
	private static final String NAVIGATE_SUCCESS = "navigate_success";
	
	

	/**
	 * Method to implement the navigate operation
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListInterlineBillingEntriesForm  listInterlineBillingEntriesForm = (ListInterlineBillingEntriesForm)invocationContext.screenModel;

    	ListInterlineBillingEntriesSession listInterlineBillingEntriesSession =
    		(ListInterlineBillingEntriesSession)getScreenSession(MODULE_NAME, SCREENID);
    	ConsignmentSession consignmentSession = 
    		getScreenSession(VIEW_MODULE_NAME,VIEW_SCREEN_ID);
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		String select= listInterlineBillingEntriesForm.getSelect();
		log.log(Log.FINE, "Select : ", listInterlineBillingEntriesForm.getSelect());
		listInterlineBillingEntriesSession.setFromScreen("fromInterLineBilling");
		Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs = listInterlineBillingEntriesSession
		.getDocumentBillingDetailVOs();
    	ArrayList<DocumentBillingDetailsVO> documentBillingDetailsVOArraylist = new ArrayList<DocumentBillingDetailsVO>(
    	documentBillingDetailsVOs);
    	DocumentBillingDetailsVO documentBillingDetailsVO;
    		
    	documentBillingDetailsVO= documentBillingDetailsVOArraylist.get(Integer.parseInt(select));
    	log.log(Log.FINE, "Inside NavigateCommand ... >>",
				documentBillingDetailsVO);
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
    	consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	if(documentBillingDetailsVO.getCsgDocumentNumber()!=null){
		consignmentFilterVO.setConsignmentNumber(String.valueOf(documentBillingDetailsVO.getCsgDocumentNumber()));
    	}
    	if(documentBillingDetailsVO.getPoaCode()!=null){
		consignmentFilterVO.setPaCode(documentBillingDetailsVO.getPoaCode().toUpperCase());
    	}
		log.log(Log.FINE, "consignmentFilterVO ... >>", consignmentFilterVO);
		consignmentSession.setConsignmentFilterVO(consignmentFilterVO);
		
		/*
		    * Getting OneTime values
		    */
	       Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		   if(oneTimes!=null){
			   Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
			   Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
			   Collection<OneTimeVO> rsnVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
			   Collection<OneTimeVO> mailClassVOs = oneTimes.get("mailtracking.defaults.mailclass");
			   Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
			   
			   consignmentSession.setOneTimeCat(catVOs);
			   consignmentSession.setOneTimeRSN(rsnVOs);
			   consignmentSession.setOneTimeHNI(hniVOs);
			   consignmentSession.setOneTimeMailClass(mailClassVOs);
			   consignmentSession.setOneTimeType(typeVOs);
			}	
		   
		   invocationContext.target = NAVIGATE_SUCCESS;
	    }
	    
	    /**
		 * This method will be invoked at the time of screen load
		 * @param companyCode
		 * @return Map<String, Collection<OneTimeVO>>
		 */
		public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
			Map<String, Collection<OneTimeVO>> oneTimes = null;
			Collection<ErrorVO> errors = null;
			try{
				Collection<String> fieldValues = new ArrayList<String>();
				fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
				fieldValues.add("mailtracking.defaults.mailcategory");
				fieldValues.add("mailtracking.defaults.highestnumbermail");
				fieldValues.add("mailtracking.defaults.mailclass");
				fieldValues.add("mailtracking.defaults.consignmentdocument.type");
				oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
			}catch(BusinessDelegateException businessDelegateException){
				errors = handleDelegateException(businessDelegateException);
			}
			return oneTimes;
		}
}
