/*
 * ListCustRegCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.cra.defaults.vo.CCCollectorVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1496
 *
 */
public class ListCustRegCommand  extends BaseCommand {

    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE= "list_failure";
    private static final String MODULENAME = "customermanagement.defaults";
    private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
    private static final String BLANK = "";
    private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
    private static final String SYSPAR_CMPCOD = "customermanagement.defaults.companycode";
  //Added for ICRD-33334 by A-5163 Starts
    private static final String INVOICE_CLUBBING_ON = "on";
  //Added for ICRD-33334 by A-5163 Ends
    
    private static final String CUSTOMERID_GENERATION_MESSAGE = "customermanagement.defaults.error.customeridGenerationmessage";
    private static final String CUSTOMERCODE_DOESNOT_EXIST = "customermanagement.defaults.error.nocustomercode";
    //Added by A-5220 for ICRD-55852
    private static final String DEF_NOT_MODE_TELEX = "Telex";
    private static final String CUSTOMER_STATUS = "customer.RegisterCustomer.status";
    private static final String DEF_NOT_MODE = "shared.customer.notificationmode";
    private static final String ONETIME_CONTACTTYPE = "customermanagement.defaults.customercontact.contacttype";
    private static final String  FRW_TYP = "shared.customer.forwardertype";
    private static final String BLL_PRD ="customer.RegisterCustomer.billingPeriod";
    private static final String AGENTINFO_ONETIME = "shared.agent.forwardertype";   
    private static final String CASS_ONETIME = "cra.agentbilling.cass.cassidentifier";  
    private static final String DATEOFEXCHANGE_ONETIME = "cra.defaults.dateofexchange";
    private static final String BILLEDTO_ONETIME = "shared.agent.billedto";
    private static final String AIRCRAFTTYPE_ONETIME = "shared.aircraft.flighttypes";
    private static final String CUSTOMER_TYPE_ONETIME = "shared.customer.customertype";
    private static final String ONETIME_BANKCODETYPE = "shared.agent.bankdetails.codes";
    //Added by A-5807 for ICRD-73246
    private static final String ONETIME_PAYMENTTYPE="shared.defaults.paymenttype"; 
    //Added for ICRD-82767 by a-4812
	private static final String BILLINGPERIOD_ONETIME = "cra.defaults.billingperiod";
	//Added by A-7555 for ICRD-319714
	private static final String DUEDATEBASIS_ONETIME = "cra.agentbilling.invoice.duedatebasis";
    //added by a-6162 for icrd 38408
    private static final String INVOICE_TYPE_ONETIME = "shared.agent.invoicetype";
    //added by a-6314 for ICRD-130084
    private static final String EMAIL= "E";
  //Added by A-7137 as part of CR ICRD-152555 starts
    private static final String  ONETIME_CUSTOMERPREFERENCES = "customermanagement.defaults.customerpreferences";
    private static final String ONETIME_LANGUAGE = "customermanagement.defaults.customercontact.language";
    private static final String ONETIME_FORMAT = "customermanagement.defaults.customercontact.format";//ICRD-162691
    private static final String ONETIME_CONTACT_MODE = "customermanagement.defaults.customercontact.contactmode";
    //Added by A-7137 as part of CR ICRD-152555 ends
	//Added by A-8130 for CR ICRD-257611
    private static final String CUSTOMERCODE_VIEW_AUTHORISATION = "customermanagement.defaults.error.viewauthorisation";
	private static final String TRANSACTION_CODE_LSTCUS = "LSTCUS";
	private static final String LEVEL_TYPE_CUSGRP = "CUSGRP";
	private static final String LEVEL_TYPE_CUS = "CUS";
	private static final String LEVEL_TYPE_STATION ="STN";
	private static final String  ONETIME_CUST_AGENT_TYPE= "customermanagement.defaults.agenttype";
	private static final String ONETIME_GROUPTYPE = "customermanagement.defaults.grouptype";
	private static final String ONETIME_CATEGORY = "shared.generalmastergrouping.groupcategory";
	//ENDS
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	MaintainRegCustomerForm form = (MaintainRegCustomerForm)invocationContext.screenModel;
    	MaintainCustomerRegistrationSession session = getScreenSession(MODULENAME,SCREENID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();//A-8370 for ICRD-346987
		LogonAttributes logonAttributes = applicationSession.getLogonVO();//A-8370 for ICRD-346987
    	ErrorVO error = null;
    	Collection<ErrorVO>  errors = new ArrayList<ErrorVO>();
    	CustomerVO customerVO = null;
    	//Added by A-5220 for ICRD-55852 starts
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	//CRQ ID: ICRD-162691 - A-5127 modiied - start
    	Map<String,Collection<OneTimeVO>> oneTimeValues = null;
    	if((session.getOneTimeValues() == null) || 
    			(session.getOneTimeValues()!= null && session.getOneTimeValues().isEmpty())){
    		oneTimeValues = fetchScreenLoadDetails(companyCode);
    	}else{
    		oneTimeValues = session.getOneTimeValues();
    	}
    	//Added by A-8125 for ICRD-264991 starts
    	if("LNKNAV".equals(form.getStatusFlag())){
    		oneTimeValues = fetchScreenLoadDetails(companyCode);
    	}
    	//Added by A-8125 for ICRD-264991 ends
    	//CRQ ID: ICRD-162691 - A-5127 modiied - start
    	Collection<OneTimeVO> statusValues = oneTimeValues.get(CUSTOMER_STATUS);
    	Collection<OneTimeVO> notifyModes = oneTimeValues.get(DEF_NOT_MODE);
    	/* Added by A-5253 as part of BUG ICRD-37922 Start */
    	OneTimeVO notifyMod = null;
    	//Added by A-7373 for CR ICRD-262726
    	boolean isValidType = false;
    	// ENDS
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
    	//Collection<OneTimeVO> creditPeriods = oneTimeValues.get(CREDIT_PERIOD);
    	//session.setCreditPeriod((ArrayList<OneTimeVO>)creditPeriods);
    	session.setCustomerStatus((ArrayList<OneTimeVO>)statusValues);
    	session.setDefaultNotifyModes((ArrayList<OneTimeVO>)notifyModes);
    	session.setForwarderTypes((ArrayList<OneTimeVO>)forwarderTypes);      
    	session.setBillingPeriods((ArrayList<OneTimeVO>)billingPeriods);
    	
    	// Added By A-5183    	
    	session.setOneTimeValues((HashMap<String, Collection<OneTimeVO>>)oneTimeValues);
    	//Added by A-5220 for ICRD-55852 ends
    	if(session.getPageURL()==null)
    	{
    	//Added by A-8227 for ICRD-236527 starts
    	if(null==form.getPageURL()){
    	form.setPageURL("");
    	}
    	//Added by A-8227 for ICRD-236527 ends

    	CustomerFilterVO filterVO = new CustomerFilterVO();
    	/** Added by A-8971 for JTO JAL**/
    	if("dprdohistory".equals(session.getSourcePage())){ 
			form.setCustomerCode(session.getCustomerVO().getCustomerCode()); 
			form.setCustName(session.getCustomerVO().getCustomerName());  
		} 
    	errors =validateForm(form, session);
    	if(errors != null && errors.size()>0){
    		invocationContext.addAllError(errors);
    		invocationContext.target = LIST_FAILURE;
    		return;
    	}
    	
    	filterVO.setCustomerCode(form.getCustomerCode().toUpperCase());
    	filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());

    	CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();

    	try{
    		//Modified by A-8130 for CR ICRD-257611
    		isValidType = hasTransactionPrivilege(filterVO);
    		if(isValidType){
    		customerVO = delegate.listCustomer(filterVO);
    		}
    	}catch(BusinessDelegateException ex){
    		handleDelegateException(ex);
    	}
    	}
    	else
    	{
    		form.setPageURL(session.getPageURL());
    		customerVO=session.getCustomerVO();
    		customerVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
    		session.setPageURL(null);
    	}
    	log.log(Log.FINE, "customerVO********-----", customerVO);
    	
    	//Added for ICRD-67442 by A-5163 starts
    	if(customerVO!=null)
    	{
		if (customerVO.getCustomerDetailsHistoryVersions() != null
				&& customerVO.getCustomerDetailsHistoryVersions().size()!=0) {
			int totalRecords = customerVO.getCustomerDetailsHistoryVersions().size();
			form.setIsHistoryPresent("Y");
			form.setIsHistoryPopulated("N");
			customerVO.setIsHistoryPresent("Y");
			customerVO.setIsHistoryPopulated("N");
			form.setDisplayPopupPage(String.valueOf(totalRecords));
			form.setTotalViewRecords(String.valueOf(totalRecords));
			int count = 0;
			String [] versionNumbers = new String[totalRecords];			
			for(String version : customerVO.getCustomerDetailsHistoryVersions()){			
				versionNumbers[count++] = String.valueOf(version);				
			}
			form.setVersionNumbers(versionNumbers);
			form.setNavVersionNumbers(versionNumbers);
			form.setCusVersionNumber(versionNumbers[versionNumbers.length-1]);
			form.setIsLatestVersion("Y");
		}
		session.removeValidationErrors();
		form.setIsLatestVersion("Y");                                                    
		//Added for ICRD-67442 by A-5163 ends
		
		//Added by A-5163 for the ICRD-78230 starts
		if(customerVO.getGibCustomerFlag()!=null && "Y".equals(customerVO.getGibCustomerFlag()))
    	{
    		form.setGibCustomerFlag("on");
    	}else{
    		form.setGibCustomerFlag("off");
    	}	
		if(customerVO.getPublicSectorFlag()!=null && "Y".equals(customerVO.getPublicSectorFlag()))
    	{
    		form.setPublicSectorFlag("on");
    	}else{
    		form.setPublicSectorFlag("off");
    	}	
		if(customerVO.getGibRegistrationDate() != null){
			form.setGibRegistrationDate(customerVO.getGibRegistrationDate().toDisplayDateOnlyFormat());
		}		
		//Added by A-5163 for the ICRD-78230 ends
		
		//Added by A-4772 for ICRD-34844 starts here    	    	
    	if(customerVO.getCustomerCode()!= null && customerVO.getCustomerCode().length()>0){
    		form.setCustomerTypecheckFlag("Y");
    	}else{
    		form.setCustomerTypecheckFlag("N");
    	}
    	//Added by A-4772 for ICRD-34844 ends here
    	if(customerVO.getCompanyCode() != null && customerVO.getCompanyCode().trim().length()>0){
    		form.setScreenStatus("UPDATE");
    		List<CustomerContactVO> contactVOs = new ArrayList<>();
    	if(customerVO.getCustomerContactVOs() != null &&
    			customerVO.getCustomerContactVOs().size()>0){
					for (CustomerContactVO contactVO : customerVO.getCustomerContactVOs()) {
						contactVOs.add(contactVO);
					}
    	Collections.sort(contactVOs,new DesigComparator());
    	customerVO.setCustomerContactVOs(contactVOs);
    	}
    		customerVO.setCustomerAgentVOs(getCustomerAgents(customerVO.getCustomerAgentVOs()));
    	}
    	session.setCustomerVO(customerVO);           
    	
    	//Added By A-6055 for ICRD-88346
    	ArrayList<CustomerMiscDetailsVO> initialCustomerMiscValues=null;
    	if(customerVO.getCustomerMiscValDetailsVO()!=null && customerVO.getCustomerMiscValDetailsVO().size()>0){
			initialCustomerMiscValues=new ArrayList<CustomerMiscDetailsVO>();
			for(CustomerMiscDetailsVO customerMiscDetailsVO:customerVO.getCustomerMiscValDetailsVO()){
				initialCustomerMiscValues.add(customerMiscDetailsVO);
			}
		}
    	session.setCustomerMiscDetailsVOs(initialCustomerMiscValues);
    	//ICRD-88346 ends
      
        
    	form.setBillPeriod(customerVO.getBillPeriod());
    	form.setCustName(customerVO.getCustomerName());
    	form.setStation(customerVO.getStationCode());
    	form.setAddress1(customerVO.getAddress1());
    	form.setAddress2(customerVO.getAddress2());
    	form.setArea(customerVO.getArea());
    	form.setCity(customerVO.getCity());
    	form.setCustomerGroup(customerVO.getCustomerGroup());
    	form.setCountry(customerVO.getCountry());
    	form.setZipCode(customerVO.getZipCode());
    	form.setTelephone(customerVO.getTelephone());
    	form.setMobile(customerVO.getMobile());
    	form.setFax(customerVO.getFax());
    	form.setEoriNo(customerVO.getEoriNo());
    	form.setSita(customerVO.getSita());
    	form.setEmail(customerVO.getEmail());
    	form.setZone(customerVO.getZone());
    	form.setState(customerVO.getState());
    	form.setCreditLimit(String.valueOf(customerVO.getCreditLimit()));
    	form.setCreditPeriod(String.valueOf(customerVO.getCreditPeriod()));
    	form.setCreditLimitCurrencyCode(customerVO.getCreditCurrencyCode());
    	form.setCreditAccountNo(customerVO.getCreditAccountNo());
    	form.setCreditOutStanding(String.valueOf(customerVO.getCreditOutstanding()));
    	form.setCreditOutStandingCurrencyCode(customerVO.getCreditOutstdCurrencyCode());	
        form.setEmail2(customerVO.getEmail2());
    	form.setCustomerCompanyCode(customerVO.getCustomerCompanyCode());
    	form.setBranch(customerVO.getBranch());
    	form.setBillingCode(customerVO.getBillingCode());
    	/*Added by 201930 for IASCB-131790*/
    	form.setCassCountry(customerVO.getCassCountryCode());
    	form.setNaacsbbAgentCode(customerVO.getNaacsbbAgentCode());
    	form.setNaccsInvoiceCode(customerVO.getNaccsInvoiceCode());
    	form.setSpotForwarder(customerVO.getSpotForwarder());
    	form.setDefaultHawbLength(customerVO.getDefaultHawbLength());
    	form.setBillingPeriod(customerVO.getBillingPeriod());
		//Added by A-5165	for ICRD-35135
    	form.setBillingDueDays(String.valueOf(customerVO.getBillingDueDays()));
    	//Added by A-7555 for ICRD-319714
    	form.setDueDateBasis(customerVO.getDueDateBasis());
    	form.setHandledCustomerImport(customerVO.getHandledCustomerImport());
    	form.setHandledCustomerExport(customerVO.getHandledCustomerExport());
    	form.setHandledCustomerForwarder(customerVO.getHandledCustomerForwarder());
    	form.setForwarderType(customerVO.getForwarderType());
    	form.setConsolidator(customerVO.getConsolidator());    	
    	form.setPoa(customerVO.getPoa());
    	form.setNaccsDeclarationCode(customerVO.getNaccsDeclarationCode());
    	form.setNaccsAircargoAgentCode(customerVO.getNaccsAircargoAgentCode());
    	form.setBranchName(customerVO.getBranchName());
    	form.setScc(customerVO.getScc());
    	form.setCustomerShortCode(customerVO.getCustomerShortCode());//Added by A-5214 as part from the ICRD-21666
    	if(customerVO.getInvoiceSendingMode()!=null &&customerVO.getInvoiceSendingMode().length()>0 ){
    		//form.setInvoiceType(customerVO.getInvoiceSendingMode().split(","));
    		form.setInvoiceType(customerVO.getInvoiceSendingMode()); //Modified by A-9563 for ICRD-353652 
    	}
    	if(customerVO.getCustomerPOAValidity()!=null){
    		form.setCustomerPOAValidity(customerVO.getCustomerPOAValidity().toDisplayDateOnlyFormat());
    	}
    	//added by a-3045 for CR HA94 on 27Mar09 starts
    	form.setAccountNumber(customerVO.getAccountNumber());
    	//added by a-3045 for CR HA94 on 27Mar09 ends
    	//added by a-3045 for CR HA16 on 29May09 starts
    	if(customerVO.getEstablishedDate() != null){
    		form.setEstablishedDate(customerVO.getEstablishedDate().toDisplayDateOnlyFormat());
    	}
    	Map<String,String> paremeterMap = fetchSystemParameterDetails();
 		String cmpCode = paremeterMap.get(SYSPAR_CMPCOD);
 		form.setCompanyCode(cmpCode);
		form.setRemarks(customerVO.getRemarks());
		//added by A-3278 for JetBlue31-1 on 23Apr10
		form.setBillingPeriod(customerVO.getBillingPeriod());
    	form.setBillingPeriodDescription(customerVO.getBillingPeriod());   
    	//JetBlue31-1 on 23Apr10 ends
		if(customerVO.getCustomerBillingDetailsVO() != null){
			form.setBillingCityCode(customerVO.getCustomerBillingDetailsVO().getCityCode());
			form.setBillingCountry(customerVO.getCustomerBillingDetailsVO().getCountry());
			form.setBillingEmail(customerVO.getCustomerBillingDetailsVO().getEmail());
			//Added by A-7905 as part of ICRD-228463 starts
			form.setBillingEmailOne(customerVO.getCustomerBillingDetailsVO().getEmailOne());
			form.setBillingEmailTwo(customerVO.getCustomerBillingDetailsVO().getEmailTwo());
			//Added by A-7905 as part of ICRD-228463 ends
			form.setBillingFax(customerVO.getCustomerBillingDetailsVO().getFax());
			form.setBillingLocation(customerVO.getCustomerBillingDetailsVO().getLocation());
			form.setBillingState(customerVO.getCustomerBillingDetailsVO().getState());
			form.setBillingStreet(customerVO.getCustomerBillingDetailsVO().getStreet());
			form.setBillingTelephone(customerVO.getCustomerBillingDetailsVO().getTelephone());
			form.setBillingZipcode(customerVO.getCustomerBillingDetailsVO().getZipcode());
		}
		//Added by A-5169 for ICRD-31552 on 29-APR-2013 starts
		if(customerVO.getCustomerMiscDetails() !=null){
			form.setCustomsLocationNo(customerVO.getCustomerMiscDetails().getCustomsLocationNumber());
		}
		//Added by A-5169 for ICRD-31552 on 29-APR-2013 ends
		if(customerVO.getIacDetailsVO() != null){
			form.setIacNumber(customerVO.getIacDetailsVO().getIacNumber());
			if(customerVO.getIacDetailsVO().getIacExpiryDate() != null){
				form.setIacExpiryDate(customerVO.getIacDetailsVO().getIacExpiryDate().toDisplayDateOnlyFormat());
			}			
			form.setApiacsspNumber(customerVO.getIacDetailsVO().getApiacsspNumber());
			if(customerVO.getIacDetailsVO().getApiacsspExpiryDate() != null){
				form.setApiacsspExpiryDate(customerVO.getIacDetailsVO().getApiacsspExpiryDate().toDisplayDateOnlyFormat());
			}			
		}	
    	//added by a-3045 for CR HA16 on 29May09 starts
    	if(customerVO.getGlobalCustomerFlag()!=null && "Y".equals(customerVO.getGlobalCustomerFlag()))
    	{
    		form.setGlobalCustomer("on");
    	}else{
    		form.setGlobalCustomer("off");
    	}
    	// Added BY A-8374 For ICRD-247693
    	if(customerVO.isClearingAgentFlag())
    	{
    		form.setClearingAgentFlag(true);
    	}else{
    		form.setClearingAgentFlag(false);
    	}
    	
    	// Added By A-5183 For ICRD-18283 Starts   	
    	
    	if(customerVO.getValidFrom() != null)
		{
		String validFromDate = TimeConvertor.toStringFormat(((LocalDate)customerVO.getValidFrom()).toCalendar(),
				TimeConvertor.CALENDAR_DATE_FORMAT);
		form.setFromDate(validFromDate);
		}else{			
			form.setFromDate("");
		}
		if(customerVO.getValidTo() != null){
		
		String validToDate = TimeConvertor.toStringFormat(((LocalDate)customerVO.getValidTo()).toCalendar(),
				TimeConvertor.CALENDAR_DATE_FORMAT);    			
		form.setToDate(validToDate);    			
		}else{			
			form.setFromDate("");
		}
		
    	form.setSalesId(customerVO.getSalesId()); 
    	if(customerVO.isControllingLocation()){
    		form.setControllingLocation("on");
    	}	
    	if(customerVO.isSellingLocation()){
    		form.setSellingLocation("on");
    	}
    	form.setIataCode(customerVO.getContyrollingAgentCode());
		form.setClName(customerVO.getControllingAgentName());
		form.setBillingTo(customerVO.getBillingIndicator());
		/*Added by A-7567 as part of ICRD-305684*/
		form.setCntLocBillingApplicableTo(customerVO.getCntLocBillingApplicableTo());
		
			//Added for ICRD-33334 by A-5163 Starts
			if (customerVO.getInvoiceClubbingIndicator() != null
					&& CustomerVO.FLAG_YES.equals(customerVO
							.getInvoiceClubbingIndicator())) {
				form.setInvoiceClubbingIndicator(INVOICE_CLUBBING_ON);
			} 
			//Added for ICRD-33334 by A-5163 Ends
		
		if(customerVO.isEnduranceFlag()){    			 	
			form.setEnduranceFlag("awb");
			form.setEndurancePercentage(Double.valueOf(customerVO.getEndurancePercentage()).toString());
			form.setEnduranceValue(Double.valueOf(customerVO.getEnduranceValue()).toString());
			form.setEnduranceMaxValue(Double.valueOf(customerVO.getEnduranceMaxValue()).toString());
			
		}else{    				
			form.setEnduranceFlag("invoice");
			form.setEndurancePercentage(Double.valueOf(customerVO.getEndurancePercentage()).toString());
			form.setEnduranceValue(Double.valueOf(customerVO.getEnduranceValue()).toString());
			form.setEnduranceMaxValue(Double.valueOf(customerVO.getEnduranceMaxValue()).toString());
		}
		
		form.setVatRegNumber(customerVO.getVatRegNumber()); 
		form.setSettlementCurrency(customerVO.getSettlementCurrencyCodes());
		form.setBillingremark(customerVO.getRemark()); 
		//CRQ ID:117235 - A-5127 added
		form.setCassImportIdentifier(customerVO.getCassImportIdentifier());
		form.setRecipientCode(customerVO.getRecipientCode());
		//End - CRQ ID:117235 - A-5127 added
		if(customerVO.canExcludeRounding()){//Added for ICRD-57704 BY A-5117
			form.setExcludeRounding("on");
		}else{
			form.setExcludeRounding("off");
		}
    	    	
    	if("CU".equals(customerVO.getCustomerType())){
    		form.setCustomerType(customerVO.getCustomerType());    		
    	}    	
    	if("AG".equals(customerVO.getCustomerType())){
    		form.setCustomerType(customerVO.getCustomerType()); 
    		
    		for(AgentVO agentVO : customerVO.getAgentVOs()){
    			 
    			form.setIataAgentCode(agentVO.getIataAgentCode());
    			form.setAgentInformation(agentVO.getAgentType());    			
    			form.setCassIdentifier(agentVO.getCassIdentifier());
    			//form.setSalesId(agentVO.getSalesId()); 
    			form.setHoldingCompany(agentVO.getHoldingCompany());   	
    			form.setNormalComm(String.valueOf(agentVO.getNormCommPrc()));
    			form.setNormalCommFixed(String.valueOf(agentVO.getFixedValue()));
    			if(agentVO.getOwnSalesFlag()!=null && 
    					AbstractVO.FLAG_YES.equals(agentVO.getOwnSalesFlag())){
    				form.setOwnSalesFlag("on");
    			}		
    			if(agentVO.isSalesReporting()){
    				form.setSalesReportAgent("on");
    			}		
    			/*if(agentVO.isControllingLocation()){
    				form.setControllingLocation("on");
    			}	
    			if(agentVO.isSellingLocation()){
    				form.setSellingLocation("on");
    			}*/
    			if(agentVO.isInvoiceGenerationFlag()){
    				form.setProformaInv("on");
    			} 
    			form.setBillingremark(agentVO.getRemarks());
    			

    		}    		
    	}    	
    	if("CC".equals(customerVO.getCustomerType())){
    		form.setCustomerType(customerVO.getCustomerType());
    		StringBuilder aircraftTypeHandled=new StringBuilder();
    		for(CCCollectorVO cCCollectorVO : customerVO.getCcCollectorVOs())
    		{   			
    			form.setAirportCode(cCCollectorVO.getAirportCode());
    			//form.setVatRegNumber(cCCollectorVO.getVatRegNumber());
    			aircraftTypeHandled.append(cCCollectorVO.getAircraftTypeHandled());
    			form.setDateOfExchange(cCCollectorVO.getDateOfExchange());    			
    			form.setCassBillingIndicator(cCCollectorVO.isCassBillingIndicator());
    			form.setCassCode(cCCollectorVO.getCassCode());    			
    			form.setBillingThroughInterline(cCCollectorVO.isBillingThroughInterline());    			
    			form.setAirlineCode(cCCollectorVO.getAirlineCode());    			
    			form.setBillingremark(cCCollectorVO.getRemarks()); 
    			//Added by A-7656 for ICRD-242148
    			form.setCcFeeDueGHA(cCCollectorVO.isCCFeeDueGHA());
    		}
    		
				String[] aircraftType = aircraftTypeHandled.toString().split(",");
				aircraftTypeHandled = new StringBuilder();
				Collection<OneTimeVO> oneTimeValue = session.getOneTimeValues().get(
						"shared.aircraft.flighttypes");
				for (OneTimeVO oneTimeVO : oneTimeValue) {
					aircraftTypeHandled.append(oneTimeVO.getFieldValue());
					boolean flag = false;
					for (String aircraftValue : aircraftType) {
						if (aircraftValue.equalsIgnoreCase(oneTimeVO.getFieldValue())) {
							aircraftTypeHandled.append("-");
							aircraftTypeHandled.append("Y");
							flag = true;
							break;
						}
					} 
					if (!flag) {
						aircraftTypeHandled.append("-");
						aircraftTypeHandled.append("N");
					}
					aircraftTypeHandled.append(","); 
				}
				form.setAircraftTypeHandledList(aircraftTypeHandled.toString()); 
    	}    	
    	if(CustomerVO.CUSTOMER_TYPE_ML.equals(customerVO.getCustomerType())) {
    		form.setHoldingCompany(customerVO.getHoldingCompany());
    	}
    	//Added by A-5165 for ICRD-64121, to update bank details in screen
    	if(customerVO.getCustomerMiscValDetailsVO()!=null)
		{
			populateCustomerMiscBankDetails(form,customerVO);
		}
    	if(customerVO.getLastUpdatedTime()!=null){//A-8370 for ICRD-346987 
    	GMTDate newConvertedDate= customerVO.getLastUpdatedTime().toGMTDate();
    	//Modified by Prashant Behera for ICOJTO-1761
    	LocalDate newDate=new LocalDate(newConvertedDate,(customerVO.getLastUpdatedStation()!=null?
    			customerVO.getLastUpdatedStation():logonAttributes.getStationCode()),Location.STN);
    	customerVO.setLastUpdatedTime(newDate); //A-8370 for ICRD-346987
    	}
    	//Added by A-5165 for ICRD-64121, to update bank details in screen ends
    	// Added By A-5183 For ICRD-18283 Ends
    	//Added by A-5807 for ICRD-73246
    	if(customerVO.getRestrictedFOPs()!=null){
    		String[] restrictedFoPs=new String[1];
    		restrictedFoPs[0]=customerVO.getRestrictedFOPs();
    		form.setRestrictedFOPs(restrictedFoPs);
    	}
    	invocationContext.target = LIST_SUCCESS;
    	return;
    	}
		//Added by A-8130 for the CR ICRD-257611
    	else if(!isValidType){
    		form.setCustomerCode(BLANK);
    		error= new ErrorVO(CUSTOMERCODE_VIEW_AUTHORISATION);
        	errors.add(error);
        	invocationContext.addAllError(errors);
        	invocationContext.target = LIST_FAILURE;
    		return;
    	}
    	else{
    		form.setIsLatestVersion("Y");
    		//Added by a-6314 for ICRD-130084
    		if(oneTimeValues!=null
    				&& oneTimeValues.size()>0
    				&& oneTimeValues.get(INVOICE_TYPE_ONETIME)!=null
    				&& oneTimeValues.get(INVOICE_TYPE_ONETIME).size()>0){
    		//Commented by A-5493 for ICRD-197882
    		//form.setInvoiceType(new String[]{EMAIL});
    		}
    		
    		
    		/**
    		 * Added by A-5198 for ICRD-56507
    		 * If syspar 'customermanagement.defaults.customerIDGenerationRequired' is "Y",
    		 * session.getCustomerIDGenerationRequired() will be "Y". 
    		 * Ideally it will be "Y" for TK user only. In such case, CUSTOMERCODE_DOESNOT_EXIST error will be thrown
    		 */
    		if(AbstractVO.FLAG_NO.equalsIgnoreCase(session.getCustomerIDGenerationRequired())){
    			//form.setCustomerCode(BLANK);
    			error= new ErrorVO(CUSTOMERCODE_DOESNOT_EXIST);
        		errors.add(error);
        		invocationContext.addAllError(errors);
        		form.setIsLatestVersion("Y");          
    		}else{
    		//form.setCustomerCode(BLANK);
    		form.setListStatus("Y");
    		/*error= new ErrorVO("customermanagement.defaults.customerregn.msg.err.norecords");
    		errors.add(error);
    		invocationContext.addAllError(errors);*/
    		}
    		invocationContext.target = LIST_FAILURE;
    		return;
    	}

    }

	
	/**
	 * 	Method		:	ListCustRegCommand.getCustomerAgents
	 *	Added on 	:	07-Apr-2022
	 * 	Used for 	:	getting sorted customer agent collection
	 *	Parameters	:	@param customerAgentVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CustomerAgentVO>
	 */
	private Collection<CustomerAgentVO> getCustomerAgents(Collection<CustomerAgentVO> customerAgentVOs) {
		ArrayList<CustomerAgentVO> agentVOs = null;
		if (Objects.nonNull(customerAgentVOs) && !customerAgentVOs.isEmpty()) {
			agentVOs = (ArrayList<CustomerAgentVO>) customerAgentVOs;
			Collections.sort(agentVOs, new AgentComparator());
		}
		return agentVOs;
	}
/**
 *
 * @param form
 * @return
 */
public Collection<ErrorVO> validateForm(MaintainRegCustomerForm form, MaintainCustomerRegistrationSession session){
	ErrorVO error = null;
	Collection<ErrorVO>  errors = new ArrayList<ErrorVO>();
	if(form.getCustomerCode()==null || BLANK.equals(form.getCustomerCode())){
		// Added by A-5198 for ICRD-56507
		if(AbstractVO.FLAG_YES.equalsIgnoreCase(session.getCustomerIDGenerationRequired())){
			error=new ErrorVO(CUSTOMERID_GENERATION_MESSAGE);
			error.setErrorDisplayType(ErrorDisplayType.WARNING);
			errors.add(error);
		}else{
		error = new ErrorVO("customermanagement.defaults.custregn.msg.err.custcodemandatory");
		errors.add(error);
	}
	}
return errors;
}

/**
 *
 * @author A-2046
 *
 */
 class DesigComparator implements Comparator<CustomerContactVO>{
/**
 * 
 * @param firstVO
 * @param secondVO
 * @return
 */
	 public int compare(CustomerContactVO firstVO,CustomerContactVO secondVO){
	  int returnValue = 0;

	  int firstSeq = firstVO.getSequenceNumber();
	  int secondSeq =secondVO.getSequenceNumber();
	  if ( firstSeq < secondSeq){
	   returnValue = -1 ;
	  }else if(firstSeq == secondSeq){
	   returnValue = 0 ;
	  }else if (firstSeq > secondSeq ){
	   returnValue = 1 ;
	  }
	  return returnValue;
	 }

	}
/**
*
* @author A-2046
*
*/
 class AgentComparator implements Comparator<CustomerAgentVO>{
/**
 * 
 * @param firstVO
 * @param secondVO
 * @return
 */
	 public int compare(CustomerAgentVO firstVO,CustomerAgentVO secondVO){
	  int returnValue = 0;

	  int firstSeq = firstVO.getSequenceNumber();
	  int secondSeq =secondVO.getSequenceNumber();
	  if ( firstSeq < secondSeq){
	   returnValue = -1 ;
	  }else if(firstSeq == secondSeq){
	   returnValue = 0 ;
	  }else if (firstSeq > secondSeq ){
	   returnValue = 1 ;
	  }
	  return returnValue;
	 }

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
	 * @author A-5165
	 * @param form
	 * @param customerVO
	 * @return 
	 * This method is to populate the CustomerMiscDetails from CustomerMiscDetailsVO to form for filter fields
	 * The details under the table are directly displaying from the CustomerMiscDetailsVO
	 * 
	 * Bank and other details in Maintain customer are to saved in CustomerMiscDetails 
	 */
	 public void populateCustomerMiscBankDetails(MaintainRegCustomerForm form,CustomerVO customerVO)
	{    
		Collection<CustomerMiscDetailsVO> preMiscVOs = customerVO.getCustomerMiscValDetailsVO();		
		Collection<CustomerMiscDetailsVO>  newMiscVOs = new ArrayList<CustomerMiscDetailsVO>();
		
		  for(CustomerMiscDetailsVO miscdetailsvo:preMiscVOs )
		  {
			if ("BANK_NAME_CODE".equals(miscdetailsvo.getMiscCode())) {
				form.setBankcode(miscdetailsvo.getMiscValue());
			} else if ("BANK_BRANCH".equals(miscdetailsvo.getMiscCode())) {
				form.setBankbranch(miscdetailsvo.getMiscValue());
			} else if ("BANK_CITY".equals(miscdetailsvo.getMiscCode())) {
				form.setBankcityname(miscdetailsvo.getMiscValue());
			} else if ("BANK_STATE".equals(miscdetailsvo.getMiscCode())) {
				form.setBankstatename(miscdetailsvo.getMiscValue());
			} else if ("BANK_COUNTRY".equals(miscdetailsvo.getMiscCode())) {
				form.setBankcountryname(miscdetailsvo.getMiscValue());
			} else if ("BANK_PIN".equals(miscdetailsvo.getMiscCode())) {
				form.setBankpincode(miscdetailsvo.getMiscValue());
			}  else {
				miscdetailsvo.setOperationFlag("U");
				newMiscVOs.add(miscdetailsvo);
			 }	
		 }
		 customerVO.setCustomerMiscValDetailsVO(newMiscVOs);		
	}
	/**
	 *  
	 * 	Method		:	ListCustRegCommand.fetchScreenLoadDetails
	 *	Added by 	:	a-5220 on 25-Apr-2014 for ICRD-55852
	 * 	Used for 	:	getting screenload details
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */
	 private Map<String,Collection<OneTimeVO>> fetchScreenLoadDetails(String companyCode){
		 log.entering("ListCustRegCommand", "fetchScreenLoadDetails");
			Map<String,Collection<OneTimeVO>> hashMap = new 
			HashMap<String,Collection<OneTimeVO>>();
			Collection<String> oneTimeList = new ArrayList<String>();
			Collection<String> sysParList = new ArrayList<String>();
			oneTimeList.add(CUSTOMER_STATUS);
			oneTimeList.add(DEF_NOT_MODE);
			oneTimeList.add(FRW_TYP);
			oneTimeList.add(BLL_PRD);
			// Added By A-5183 For ICRD-18283
			oneTimeList.add(AGENTINFO_ONETIME);
			oneTimeList.add(CASS_ONETIME);
			oneTimeList.add(DATEOFEXCHANGE_ONETIME);
			oneTimeList.add(BILLEDTO_ONETIME);
			oneTimeList.add(AIRCRAFTTYPE_ONETIME);
			oneTimeList.add(CUSTOMER_TYPE_ONETIME);
			oneTimeList.add(ONETIME_BANKCODETYPE);
			//Added by A-5220 for ICRD-55852
			oneTimeList.add(ONETIME_CONTACTTYPE);
			//Added by A-5807 for ICRD-73246
			oneTimeList.add(ONETIME_PAYMENTTYPE);
			oneTimeList.add(BILLINGPERIOD_ONETIME);
			oneTimeList.add(DUEDATEBASIS_ONETIME);
			oneTimeList.add(INVOICE_TYPE_ONETIME);
			//Added by A-7137 as part of CR ICRD-152555 starts
			oneTimeList.add(ONETIME_CUSTOMERPREFERENCES);
			oneTimeList.add(ONETIME_LANGUAGE);
			oneTimeList.add(ONETIME_FORMAT);//ICRD-162691
			oneTimeList.add(ONETIME_CONTACT_MODE);
			oneTimeList.add("shared.customer.controlinglocationbillingapplicableto");
			oneTimeList.add(ONETIME_CUST_AGENT_TYPE);
			oneTimeList.add(ONETIME_GROUPTYPE);
			oneTimeList.add(ONETIME_CATEGORY);
			//Added by A-7137 as part of CR ICRD-152555 ends
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			try{
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
			}catch(BusinessDelegateException exception){
	//printStackTrrace()();
				handleDelegateException(exception);
			}
			log.exiting("ListCustRegCommand", "fetchScreenLoadDetails");
			return hashMap;
	}
	 
	 /**
		 * 
		 * 	Method		:	ListCustRegCommand.hasTransactionPrivilage
		 *	Added by 	:	A-8130 on 31-05-2018
		 * 	Used for 	:   ICRD-257611
		 *	Parameters	:	@param filterVO
		 *	Return type	: 	boolean
		 */
		public boolean hasTransactionPrivilege(CustomerFilterVO filterVO)  {
			Log log = LogFactory.getLogger(" hasTransactionPrivilage----->"+filterVO.getCustomerCode());
			boolean isValidType=false;
			String privilegedCountry="";
			Collection<TransactionPrivilegeNewVO> privilegesList = null ;
			CustomerDelegate delegate = new CustomerDelegate();
			CustomerVO customerVO = null;
			try {
			customerVO = delegate.validateCustomer(filterVO);
			}
			catch (BusinessDelegateException ex) {
				handleDelegateException(ex);
		     }
			try {
				privilegesList =TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(TRANSACTION_CODE_LSTCUS);	 
			    } catch (SystemException e) {
				log.log(Log.FINE,"System Exception occurred while finding transaction privilage for customer"+e.getMessage());
			     }
			if(privilegesList!=null 
		    && !privilegesList.isEmpty()
		    && customerVO.getCustomerCode() != null && customerVO != null){
				for (TransactionPrivilegeNewVO transactionPrivilegeNewVO : privilegesList) {
					if(transactionPrivilegeNewVO != null 
							&& (LEVEL_TYPE_CUSGRP.equals(transactionPrivilegeNewVO.getLevelType()) 
									|| LEVEL_TYPE_CUS.equals(transactionPrivilegeNewVO.getLevelType()))){
						if(transactionPrivilegeNewVO.getTypeValue() != null 
								&& transactionPrivilegeNewVO.getTypeValue().length() > 0)
						{
						String typeValue=transactionPrivilegeNewVO.getTypeValue();
						String[] typeArray = typeValue.split(","); 
						if (typeArray != null && typeArray.length > 0) {
							for (String type: typeArray) {  
								if(type.equalsIgnoreCase(filterVO.getCustomerCode())) {
									isValidType=true;
								}
							}
						}
					 }
				}
				else if((("CNT".equals(transactionPrivilegeNewVO.getLevelType())) && (transactionPrivilegeNewVO.getTypeValue() != null))){
				  String[] countries = transactionPrivilegeNewVO.getTypeValue().split(",");
		            for (String country : countries) {
		              privilegedCountry = privilegedCountry.concat(country).concat(",");
		            }
		            filterVO.setCountryCodes(privilegedCountry);
		            filterVO.setCountryPrivilege(transactionPrivilegeNewVO.getLevelType());
		            isValidType=true;
				}else if (transactionPrivilegeNewVO != null && (LEVEL_TYPE_STATION.equals(transactionPrivilegeNewVO.getLevelType())) && (transactionPrivilegeNewVO.getTypeValue() != null)){
					filterVO.setStationForPrivilege(transactionPrivilegeNewVO.getTypeValue());
		            isValidType=true;
				}
				 else {
						isValidType=true;
					  }
				}
			} 
			else {
				isValidType=true;
			}
			return isValidType;
		}

	 
}
