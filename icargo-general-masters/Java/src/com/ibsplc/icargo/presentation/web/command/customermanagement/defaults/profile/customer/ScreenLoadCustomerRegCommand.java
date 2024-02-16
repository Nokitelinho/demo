/*
 * ScreenLoadCustomerRegCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;


import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.capacity.booking.MaintainBookingSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.capacity.booking.permanent.MaintainPermanentBookingSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 
/**
 * @author A-1496
 *
 */
public class ScreenLoadCustomerRegCommand  extends BaseCommand {

    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String MODULENAME = "customermanagement.defaults";
    private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
    private static final String CUSTOMER_STATUS = "customer.RegisterCustomer.status";
    private static final String ONETIME_GROUPTYPE = "customermanagement.defaults.grouptype";
	private static final String ONETIME_CATEGORY = "shared.generalmastergrouping.groupcategory";
    /**
     * Default Notification Mode
     * */
    private static final String DEF_NOT_MODE = "shared.customer.notificationmode";
    private static final String ADMIN_PRIV = "customermanagement.defaults.adminprivilege";
    private static final String ADMIN_PRIV_VALUE = "Y";
    private static final String SYSPAR_CMPCOD = "customermanagement.defaults.companycode";
    private static final String SYSPAR_BILLINGCOD = "system.defaults.isCRApresent";
    // Added by A-5198 for ICRD-56507
    private static final String SYSPAR_CUSTOMERID_GENERATIONREQUIRED = "customermanagement.defaults.customerIDGenerationRequired";
    /**
     * Forwarder Type Flag
     */
    
    private static final String  FRW_TYP = "shared.customer.forwardertype";
    private static final String BLL_PRD ="customer.RegisterCustomer.billingPeriod";
    //Added by A-7555 for ICRD-319714
    private static final String DUEDATEBASIS_ONETIME = "cra.agentbilling.invoice.duedatebasis";
    // Added By A-5183 for ICRD 18283
    private static final String AGENTINFO_ONETIME = "shared.agent.forwardertype";   
    private static final String CASS_ONETIME = "cra.agentbilling.cass.cassidentifier";  
    private static final String DATEOFEXCHANGE_ONETIME = "cra.defaults.dateofexchange";
    private static final String BILLEDTO_ONETIME = "shared.agent.billedto";
    private static final String AIRCRAFTTYPE_ONETIME = "shared.aircraft.flighttypes";
    private static final String CUSTOMER_TYPE_ONETIME = "shared.customer.customertype";
    private static final String CONTROL_CUSTOMER_TYPE_ONETIME = "shared.customer.controllingcustomertype";
    private static final String ONETIME_BANKCODETYPE = "shared.agent.bankdetails.codes";
    //Added by A-5807 for ICRD-73246
    private static final String ONETIME_PAYMENTTYPE="shared.defaults.paymenttype"; 
    /* Added by A-5253 as part of BUG ICRD-37922 */
    private static final String DEF_NOT_MODE_TELEX = "Telex";
    //Adde by A-5220 for ICRD-55852
    private static final String ONETIME_CONTACTTYPE = "customermanagement.defaults.customercontact.contacttype";
    //Added for ICRD-82767 by a-4812
	private static final String BILLINGPERIOD_ONETIME = "cra.defaults.billingperiod";
    //added by a-6162 for icrd 38408
    private static final String INVOICE_TYPE_ONETIME = "shared.agent.invoicetype";
    //Added for ICRD-58628 by A-5163 starts
    private static final String CERTIFICATE_TYEP_ONETIME = "system.defaults.certificatetype";
    //Added for ICRD-58628 by A-5163 ends
    
    //Added by A-7137 as part of CR ICRD-152555 starts
    private static final String  ONETIME_CUSTOMERPREFERENCES = "customermanagement.defaults.customerpreferences";
    private static final String ONETIME_LANGUAGE = "customermanagement.defaults.customercontact.language";
    private static final String ONETIME_FORMAT = "customermanagement.defaults.customercontact.format";//ICRD-162691
    private static final String ONETIME_CONTACT_MODE = "customermanagement.defaults.customercontact.contactmode";
    //Added by E-1289 for ICRD-309437
    private static final String ONETIME_NOTIFICATIONFILTER = "customermanagement.defaults.customercontact.notificationfilters";
    //Added by A-7137 as part of CR ICRD-152555 ends
    private static final String TRANSACTIONCODE_GETCUSCNT = "GETCUSCNT";//ICRD-162691
    //Added for ICRD-229500 by a-3429
	private static final String STATECODE_ONETIME = "tariff.tax.statecode";
	private static final String STOCKAUTOMATIONREQUIRED = "stockcontrol.defaults.stockautomationrequired";
	 private static final String  ONETIME_CUST_AGENT_TYPE= "customermanagement.defaults.agenttype";
	/*Added by A-7567 as part of ICRD-305684*/
	private static final String APPLICABLETO_ONETIME = "shared.customer.controlinglocationbillingapplicableto";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("ScreenLoadCustomerRegCommand");
    	MaintainRegCustomerForm form = (MaintainRegCustomerForm)invocationContext.screenModel;
    	MaintainCustomerRegistrationSession session = getScreenSession(MODULENAME,SCREENID);
    	/*Added by A-2390 for register customer link from reservation screen starts*/
    	form.setFromReservation(false);
    	if("maintainReservation".equals(form.getScreenStatus())){
    		//Modified by A-5247 for BUG_ICRD-81514 Starts
    		MaintainBookingSession maintainBookingSession = getScreenSession("capacity.booking", "capacity.booking.maintainreservation");
    		//Modified by A-5247 for BUG_ICRD-81514 Ends
			form.setCustomerCode(maintainBookingSession.getRegisterCustomerCode()); 
			form.setCustName(maintainBookingSession.getRegisterCustomerName()); 
		}
    	
    	if("maintaindprdohistory".equals(form.getScreenStatus())){
    		
			form.setCustomerCode(session.getCustomerVO().getCustomerCode()); 
			form.setCustName(session.getCustomerVO().getCustomerName()); 
		} 
    	/*ends*/
    	/* Added by A-3254 for rejister customer link from permanentBooking screen status*/
    	MaintainPermanentBookingSessionInterface maintainPermanentBookingSessionInterface = getScreenSession(
				"capacity.booking.permanent",
				"capacity.booking.permanent.maintainpermanentbooking");
    	form.setFromPermanentBooking(false);
    	if("maintainPermanentBooking".equals(form.getScreenStatus())){    
			form.setCustomerCode(maintainPermanentBookingSessionInterface.getRegisterCustomerCode()); 
			form.setCustName(maintainPermanentBookingSessionInterface.getRegisterCustomerName()); 
		} 
    	/*ends*/
    	IsAutomaticStockAllocationRequired(form);//added by A-8130
    	
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	ApplicationSessionImpl applicationSession = getApplicationSession(); 
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	
    	Collection<CurrencyVO> currencyCodes = findCurrencyCodes(companyCode);
    	session.setCurrency((ArrayList<CurrencyVO>) currencyCodes);
    	Map<String,Collection<OneTimeVO>> oneTimeValues = fetchScreenLoadDetails(companyCode);
    	Collection<OneTimeVO> statusValues = oneTimeValues.get(CUSTOMER_STATUS);
    	Collection<OneTimeVO> notifyModes = oneTimeValues.get(DEF_NOT_MODE);
    	/* Added by A-5253 as part of BUG ICRD-37922 Start */
    	OneTimeVO notifyMod = null;
    	
    	Iterator<OneTimeVO> notifyModesItr = notifyModes.iterator();
    	
    	while(notifyModesItr.hasNext()) {
    		notifyMod = (OneTimeVO) notifyModesItr.next();
    		if(notifyMod.getFieldDescription().equals(DEF_NOT_MODE_TELEX)) {
    			notifyModesItr.remove();
    			break;
    		}
    	}
    	/* Added by A-5253 as part of BUG ICRD-37922 End */
    	
    	//Added for the CR TIACT 492
    	Collection<OneTimeVO> forwarderTypes = oneTimeValues.get(FRW_TYP);
    	Collection<OneTimeVO> billingPeriods = oneTimeValues.get(BLL_PRD);
    	//Added by A-7555 for ICRD-319714
    	Collection<OneTimeVO> dueDateBasis = oneTimeValues.get(DUEDATEBASIS_ONETIME);
    	//Collection<OneTimeVO> creditPeriods = oneTimeValues.get(CREDIT_PERIOD);
    	//session.setCreditPeriod((ArrayList<OneTimeVO>)creditPeriods);
    	session.setCustomerStatus((ArrayList<OneTimeVO>)statusValues);
    	session.setDefaultNotifyModes((ArrayList<OneTimeVO>)notifyModes);
    	session.setForwarderTypes((ArrayList<OneTimeVO>)forwarderTypes);      
    	session.setBillingPeriods((ArrayList<OneTimeVO>)billingPeriods);
    	
    	//Added for ICRD-58628 by A-5163 starts
    	Collection<OneTimeVO> certificateTypes = oneTimeValues.get(CERTIFICATE_TYEP_ONETIME);
    	session.setCertificateTypes((ArrayList<OneTimeVO>)certificateTypes);
    	//Added for ICRD-58628 by A-5163 ends
    	
    	//CRQ ID: ICRD-162691 - A-5127 added - start
 		TransactionPrivilegeNewVO transactionPrivilegeNewVO = fetchTransactionPrivilegeNewVOs(TRANSACTIONCODE_GETCUSCNT);
 		Collection<OneTimeVO> contactTypes = oneTimeValues.get(ONETIME_CONTACTTYPE);
 		Collection<OneTimeVO> contactTypesUpdated = null;
 		
 		if(transactionPrivilegeNewVO!=null){   //if check added BUG_ICRD-186308_ManjulaTK_29Dec2016
 		if((contactTypes != null && !contactTypes.isEmpty() && transactionPrivilegeNewVO != null )){
 			for(OneTimeVO contactType : contactTypes){					
 				if(transactionPrivilegeNewVO.getTypeValue() != null && transactionPrivilegeNewVO.getTypeValue().contains(",")){
 					String[] typeValues = transactionPrivilegeNewVO.getTypeValue().split(",");
 					for(String typeValue: typeValues){
 						if(typeValue.equalsIgnoreCase(contactType.getFieldDescription())){
 							if(contactTypesUpdated == null){
 								contactTypesUpdated =  new ArrayList<OneTimeVO>();
 							}
 							contactTypesUpdated.add(contactType);
 						}
 					}
 				}else{
 					if(transactionPrivilegeNewVO.getTypeValue()!= null &&
 							transactionPrivilegeNewVO.getTypeValue().equalsIgnoreCase(contactType.getFieldDescription())){
 						if(contactTypesUpdated == null){
 							contactTypesUpdated =  new ArrayList<OneTimeVO>();
 						}
 						contactTypesUpdated.add(contactType);
 					}
 				}
 			}
 			if(contactTypesUpdated != null && !contactTypesUpdated.isEmpty()){
 				oneTimeValues.get(ONETIME_CONTACTTYPE).clear();
 				oneTimeValues.get(ONETIME_CONTACTTYPE).addAll(contactTypesUpdated);
 				
		 		}
 			}
 		}
 		//CRQ ID: ICRD-162691 - A-5127 added - end
    	// Added By A-5183   
 		
    	session.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
    	
    	CustomerVO customerVO = new CustomerVO();
    	customerVO.setOperationFlag(OPERATION_FLAG_INSERT);
    	session.setCustomerVO(customerVO);
    	session.setPageURL(null);
    	form.setPageURL(""); 
    	form.setStation(logonAttributes.getStationCode());
    	boolean hasPrivilege=false;
		try{
 			hasPrivilege = SecurityAgent.getInstance().checkBusinessPrivilege(ADMIN_PRIV);
 				
 		}catch(SystemException systemException){
//printStackTrrace()();
 	    }
 		if(hasPrivilege){
 			form.setAdminPrivilege(ADMIN_PRIV_VALUE);
 		}
    	//Added now for null row in screenload
 		Map<String,String> paremeterMap = fetchSystemParameterDetails();
 		String cmpCode = paremeterMap.get(SYSPAR_CMPCOD);
 		form.setCompanyCode(cmpCode);
 		
 		String BillingCode = paremeterMap.get(SYSPAR_BILLINGCOD);
 		session.setBillingCode(BillingCode);
 		
 		// Added by A-5198 for ICRD-56507
 		String customerIDGenerationRequired = paremeterMap.get(SYSPAR_CUSTOMERID_GENERATIONREQUIRED);
 		session.setCustomerIDGenerationRequired(customerIDGenerationRequired);
 		//Added as part of CR ICRD-253447 by A-8154
		form.setScreenFlag("Screenload");
 		
    	/*Collection<CustomerContactVO> keyContactDetailVOs = new ArrayList<CustomerContactVO>();
    	
    	CustomerContactVO newContactVO = new CustomerContactVO();
		newContactVO.setOperationFlag(OPERATION_FLAG_INSERT);
		newContactVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());		
		newContactVO.setCustomerName("");
		newContactVO.setContactCustomerCode("");
		newContactVO.setCustomerDesignation("");
		newContactVO.setEmailAddress("");
		newContactVO.setFax("");
		newContactVO.setMobile("");
		newContactVO.setRemarks("");
		newContactVO.setTelephone("");
		newContactVO.setSiteAddress("");
		keyContactDetailVOs.add(newContactVO);
		customerVO.setCustomerContactVOs(keyContactDetailVOs);
		
		
		Collection<CustomerAgentVO> keyAgentDetailVOs = new ArrayList<CustomerAgentVO>();
    	CustomerAgentVO newAgentVO = new CustomerAgentVO();
		newAgentVO.setOperationFlag(OPERATION_FLAG_INSERT);
		newAgentVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		newAgentVO.setStationCode("");
		newAgentVO.setAgentCode("");
		newAgentVO.setRemarks("");
		keyAgentDetailVOs.add(newAgentVO);
		customerVO.setCustomerAgentVOs(keyAgentDetailVOs);
		
		session.setCustomerVO(customerVO);*/
    	invocationContext.target = SCREENLOAD_SUCCESS;

    }
    
    /**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Collection<CurrencyVO> findCurrencyCodes(String companyCode){
		CurrencyDelegate currencyDelegate = new CurrencyDelegate();
		Collection<CurrencyVO> currencies = new ArrayList<CurrencyVO>();
		try{
			currencies = currencyDelegate.findAllCurrencyCodes(companyCode);
		}catch(BusinessDelegateException exception){
//printStackTrrace()();
			handleDelegateException(exception);
		}
		return currencies;
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String,Collection<OneTimeVO>> fetchScreenLoadDetails(String companyCode){
		Map<String,Collection<OneTimeVO>> hashMap = new 
		HashMap<String,Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		Collection<String> sysParList = new ArrayList<String>();
		oneTimeList.add(CUSTOMER_STATUS);
		oneTimeList.add(DEF_NOT_MODE);
		oneTimeList.add(FRW_TYP);
		oneTimeList.add(BLL_PRD);
		// Added by A-7555 for ICRD-319714
		oneTimeList.add(DUEDATEBASIS_ONETIME);
		// Added By A-5183 For ICRD-18283
		oneTimeList.add(AGENTINFO_ONETIME);
		oneTimeList.add(CASS_ONETIME);
		oneTimeList.add(DATEOFEXCHANGE_ONETIME);
		oneTimeList.add(BILLEDTO_ONETIME);
		oneTimeList.add(AIRCRAFTTYPE_ONETIME);
		oneTimeList.add(CUSTOMER_TYPE_ONETIME);
		oneTimeList.add(CONTROL_CUSTOMER_TYPE_ONETIME);
		oneTimeList.add(ONETIME_BANKCODETYPE);
		//Added by A-5220 for ICRD-55852
		oneTimeList.add(ONETIME_CONTACTTYPE);
		//Added by A-5807 for ICRD-73246
		oneTimeList.add(ONETIME_PAYMENTTYPE);
		oneTimeList.add(BILLINGPERIOD_ONETIME);
		//added by a-6162 for icrd 38408
		oneTimeList.add(INVOICE_TYPE_ONETIME);
		//Added for ICRD-58628 by A-5163 starts
		oneTimeList.add(CERTIFICATE_TYEP_ONETIME);
		//Added for ICRD-58628 by A-5163 ends
		//Added by A-7137 as part of CR ICRD-152555 starts
		oneTimeList.add(ONETIME_CUSTOMERPREFERENCES);
		oneTimeList.add(ONETIME_CUST_AGENT_TYPE);
		oneTimeList.add(ONETIME_LANGUAGE);
		oneTimeList.add(ONETIME_FORMAT);//ICRD-162691
		oneTimeList.add(ONETIME_CONTACT_MODE);
		//Added by A-7137 as part of CR ICRD-152555 ends
		oneTimeList.add(STATECODE_ONETIME);
		/*Added by A-7567 as part of ICRD-305684*/
		oneTimeList.add(APPLICABLETO_ONETIME);	
//Added by E-1289 for ICRD-309437
		oneTimeList.add(ONETIME_NOTIFICATIONFILTER);
		oneTimeList.add(ONETIME_GROUPTYPE);
		oneTimeList.add(ONETIME_CATEGORY);
		
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try{
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
		}catch(BusinessDelegateException exception){
//printStackTrrace()();
			handleDelegateException(exception);
		}
		return hashMap;
	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String,String> fetchSystemParameterDetails(){
		Map<String,String> sysParMap = new HashMap<String,String>();
		Collection<String> sysParList = new ArrayList<String>();
		sysParList.add(SYSPAR_CMPCOD);
		sysParList.add(SYSPAR_BILLINGCOD);
		sysParList.add(SYSPAR_CUSTOMERID_GENERATIONREQUIRED);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try{
			sysParMap = sharedDefaultsDelegate.findSystemParameterByCodes(sysParList);
		}catch(BusinessDelegateException exception){
//printStackTrrace()();
			handleDelegateException(exception);
		}
		return sysParMap;
	}
	
	/**
	 * Used for: ICRD-162691
	 * This method is used to get customer preferences having privilege that are set in
	 * ADM015 - GETCUSCNT(Transaction Code-OneTime
	 * @param transactionCode
	 * @return
	 */
	private TransactionPrivilegeNewVO fetchTransactionPrivilegeNewVOs(String transactionCode){		
		List<TransactionPrivilegeNewVO> privilegeNewVOs = null;		
		try {
			privilegeNewVOs =  (ArrayList<TransactionPrivilegeNewVO>)TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {			
			e.getMessage();
		}		
		if(privilegeNewVOs != null && !privilegeNewVOs.isEmpty())
			{
			return privilegeNewVOs.get(0);
			}

		return null;
	}
	
	private boolean IsAutomaticStockAllocationRequired(MaintainRegCustomerForm form){
		Collection<String> systemCodes = new ArrayList<String>();
		systemCodes.add(STOCKAUTOMATIONREQUIRED);
		boolean stockAutomationRequired=false;
		Map<String, String> systemParametersMap = null;
		try {
			systemParametersMap = new SharedDefaultsDelegate().findSystemParameterByCodes(systemCodes);
		} catch (BusinessDelegateException e) {
		}
		if(systemParametersMap!=null && systemParametersMap.size() > 0){
			if(CustomerVO.FLAG_YES.equals(systemParametersMap.get(STOCKAUTOMATIONREQUIRED) )){  
				/*customerVO.setStockAutomationRequired(true);*/
				stockAutomationRequired=true;
				form.setStockAutomationRequired("Y");
			} else{
				/*customerVO.setStockAutomationRequired(false);*/
				stockAutomationRequired=false;
				form.setStockAutomationRequired("N");
			}
		}
		return stockAutomationRequired;
	}
}
