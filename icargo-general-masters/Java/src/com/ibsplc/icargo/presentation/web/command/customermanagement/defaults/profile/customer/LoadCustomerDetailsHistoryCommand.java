/*
 * LoadCustomerDetailsHistory.java Created on August 22, 2014
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
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.ibsplc.icargo.business.cra.defaults.vo.CCCollectorVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerPreferenceVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * The Class LoadCustomerDetailsHistoryCommand.
 *
 * @author 		:	A-5163
 * Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.LoadCustomerDetailsHistoryCommand.java
 * Version		:	Name	:	Date			:	Added for 	: 	Status
 * -----------------------------------------------------------------------
 * 0.1			:	A-5163	:	August 22, 2014	:	ICRD-67442	:	Draft
 */
public class LoadCustomerDetailsHistoryCommand extends BaseCommand {
	
	 /** The Constant LIST_SUCCESS. */
 	private static final String LIST_SUCCESS = "list_success";
	 
 	/** The Constant MODULENAME. */
 	private static final String MODULENAME = "customermanagement.defaults";
	 
 	/** The Constant SCREENID. */
 	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	 
  	/** The Constant LINKNAVIGATION. */
	private static final String LINKNAVIGATION = "LNKNAV";
	
	/** The Constant SYSPAR_CMPCOD. */
	private static final String SYSPAR_CMPCOD = "customermanagement.defaults.companycode";
	
	/** The Constant CONST_VALUE_ON. */
	private static final String CONST_VALUE_ON = "on";
	
	/** The Constant CONST_VALUE_OFF. */
	private static final String CONST_VALUE_OFF = "off";
	
	/** The Constant CONST_VALUE_YES. */
	private static final String CONST_VALUE_YES = "Y";
	
	/** The Constant CONST_VALUE_NO. */
	private static final String CONST_VALUE_NO = "N";
	
	/** The Constant CONST_VALUE_UPDATE. */
	private static final String CONST_VALUE_UPDATE = "UPDATE";
	
	/** The Constant CONST_VALUE_BLANK. */
	private static final String CONST_VALUE_BLANK = ""; 
	
	/** The Constant CONST_VALUE_AWB. */
	private static final String CONST_VALUE_AWB = "awb";
	
	/** The Constant CONST_VALUE_INVOICE. */
	private static final String CONST_VALUE_INVOICE = "invoice";
	
	/** The Constant SYSPAR_FLTTYP. */
	private static final String SYSPAR_FLTTYP = "shared.aircraft.flighttypes";
	
	/** The Constant OPR_FLG_UPDATE. */
	private static final String OPR_FLG_UPDATE = "U";
	
	/** The Constant CONST_VALUE_CUSTOMER. */
	private static final String CONST_VALUE_CUSTOMER = "CU";
	
	/** The Constant CONST_VALUE_AGENT. */
	private static final String CONST_VALUE_AGENT = "AG";
	
	/** The Constant CONST_VALUE_CHARGE_COLLECT. */
	private static final String CONST_VALUE_CHARGE_COLLECT = "CC";	
	
	/** The Constant BANK_NAME_CODE. */
	private static final String BANK_NAME_CODE = "BANK_NAME_CODE";
	
	/** The Constant BANK_BRANCH. */
	private static final String BANK_BRANCH = "BANK_BRANCH";
	
	/** The Constant BANK_CITY. */
	private static final String BANK_CITY = "BANK_CITY";
	
	/** The Constant BANK_STATE. */
	private static final String BANK_STATE = "BANK_STATE";
	
	/** The Constant BANK_COUNTRY. */
	private static final String BANK_COUNTRY = "BANK_COUNTRY";
	
	/** The Constant BANK_PIN. */
	private static final String BANK_PIN = "BANK_PIN";
	
	/** The Constant CONST_VALUE_COMMA. */
	private static final String CONST_VALUE_COMMA = ",";
	
	/** The Constant CONST_VALUE_HYPHEN. */
	private static final String CONST_VALUE_HYPHEN = "-";
	
	/** The Constant SOURCE_LIST. */
	private static final String SOURCE_LIST = "LIST";
	
	/** The Constant SOURCE_NAVIGATION. */
	private static final String SOURCE_SAVE = "SAVE";
	
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * Added by 			: 	A-5163 on August 22, 2014
	 * Used for 	:
	 * Parameters	:	@param 	invocationContext
	 * Parameters	:	@throws CommandInvocationException
	 *
	 * @param 	invocationContext the invocation context
	 * @throws 	CommandInvocationException the command invocation exception
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		MaintainRegCustomerForm cusRegForm = (MaintainRegCustomerForm)invocationContext.screenModel;
    	MaintainCustomerRegistrationSession cusRegSession  = getScreenSession(MODULENAME, SCREENID);
    	HashMap<Integer, CustomerVO> customerDetailVoMap   = new HashMap<Integer, CustomerVO>();
    	CustomerVO customerVO = null;
    	
    	if(cusRegSession.getCustomerDetailVOsInMap() != null){
    		customerDetailVoMap = cusRegSession.getCustomerDetailVOsInMap();
    		SortedSet<Integer> mapKey = new TreeSet<Integer>(cusRegSession.getCustomerDetailVOsInMap().keySet());
    		HashMap<Integer, Integer> customerVersionMap 	 = new HashMap<Integer, Integer>();
    		HashMap<Integer, Integer> customerDisplayPageMap = new HashMap<Integer, Integer>();
    		int count = 1;
    		String [] navigationVersionNumbers = new String [mapKey.size()];
			for(Integer version : mapKey) {
				customerVersionMap.put(version, count);
				customerDisplayPageMap.put(count,version);
				navigationVersionNumbers[count-1] = Integer.toString(version);
				count++;
			}
			if (cusRegSession.getSourcePage() != null
					&& SOURCE_LIST.equals(cusRegSession.getSourcePage())) {
	    		cusRegForm.setDisplayPage(Integer.toString(count - 1));
	    		cusRegSession.setSourcePage(null);
			}
			int displayPageNumber = Integer.parseInt(cusRegForm.getDisplayPage());
			if (LINKNAVIGATION.equals(cusRegForm.getStatusFlag())) {				
				customerVO = customerDetailVoMap.get(customerDisplayPageMap.get(displayPageNumber));
				String customerAuditVersion = customerVO.getCustomerAuditVersion();
				customerVO.setIsHistoryPopulated(CONST_VALUE_YES);
				cusRegForm.setIsHistoryPopulated(CONST_VALUE_YES);
				cusRegSession.setCustomerVO(customerVO);
				cusRegForm.setDisplayPopupPage(String.valueOf(customerVersionMap.get(Integer.parseInt(customerAuditVersion))));
				cusRegForm.setCusVersionNumber(customerAuditVersion);
				if(customerVersionMap.get(Integer.parseInt(customerAuditVersion)) == count-1){
					cusRegForm.setIsLatestVersion(CONST_VALUE_YES);
				}else{
					cusRegForm.setIsLatestVersion(CONST_VALUE_NO);
				}
			}			
			cusRegForm.setTotalViewRecords(String.valueOf(cusRegSession.getCustomerDetailVOsInMap().size()));
			if(customerVO != null){
				populateRegCustomerForm(cusRegForm,customerVO,cusRegSession);
			}
			if (cusRegForm.getVersionNumbers() == null
					|| cusRegForm.getVersionNumbers().length != count-1) {
				cusRegForm.setVersionNumbers(navigationVersionNumbers);
				cusRegForm.setNavVersionNumbers(navigationVersionNumbers);				
			}
    	}else {
    		Collection<CustomerVO> navigationCustomerDetailVOs = null;
    		String [] navigationVersionNumbers = null;
    		int count = 0;
        	CustomerFilterVO filterVO = new CustomerFilterVO();
        	
        	LocalDate lastUpdatedTime =  null;
        	if(cusRegSession.getCustomerVO() != null){
        		lastUpdatedTime  = 	cusRegSession.getCustomerVO().getLastUpdatedTime();
        	}
        	filterVO.setCustomerCode(cusRegForm.getCustomerCode().toUpperCase());
        	filterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
        	
    		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
    		try {
    			navigationCustomerDetailVOs = delegate.loadCustomerDetailsHistory(filterVO);
    		} catch (BusinessDelegateException ex) {
    			handleDelegateException(ex);
    		}
    		
    		if(navigationCustomerDetailVOs != null){
    			navigationVersionNumbers = new String[navigationCustomerDetailVOs.size()];
    			for(CustomerVO customerDetailVO : navigationCustomerDetailVOs){
    				navigationVersionNumbers[count++] = String.valueOf(customerDetailVO.getCustomerAuditVersion());
    				if(customerDetailVO.getLastUpdatedTime()==null && lastUpdatedTime != null){
    					customerDetailVO.setLastUpdatedTime(lastUpdatedTime);
    				}
    				customerDetailVoMap.put(Integer.parseInt(customerDetailVO.getCustomerAuditVersion()), customerDetailVO);
    			}
    			cusRegSession.setCustomerDetailVOsInMap(customerDetailVoMap);
    			//customerVO = customerDetailVoMap.get(Integer.parseInt(navigationVersionNumbers[navigationCustomerDetailVOs.size()-1]));    			   			
    			if(cusRegSession.getCustomerVO() != null){    				
    				cusRegSession.getCustomerVO().setIsHistoryPopulated(CONST_VALUE_YES);
    				//populateSequenceNumber(cusRegSession.getCustomerVO(),customerVO);
    	    	}
    			//cusRegSession.setCustomerVO(customerVO);
    	    	if(cusRegForm.getVersionNumbers() == null){
    	    		cusRegForm.setIsHistoryPresent(CONST_VALUE_YES);
    	    		cusRegForm.setIsHistoryPopulated(CONST_VALUE_YES);
    	    		cusRegForm.setVersionNumbers(navigationVersionNumbers);
    	    		cusRegForm.setNavVersionNumbers(navigationVersionNumbers);
    	    		cusRegForm.setCusVersionNumber(navigationVersionNumbers[navigationVersionNumbers.length-1]);
    	    	}
    	    	if (cusRegSession.getSourcePage() != null
    					&& SOURCE_LIST.equals(cusRegSession.getSourcePage())) {
    	    		cusRegForm.setDisplayPage(Integer.toString(count));
    	    		cusRegSession.setSourcePage(null);
    			}
    		}
    	}
    	if (cusRegSession.getValidationErrors() != null
				&& !cusRegSession.getValidationErrors().isEmpty()) {
			if(SOURCE_SAVE.equals(cusRegSession.getSourcePage())){
				cusRegSession.removeValidationErrors();
				cusRegSession.setSourcePage(null);
				cusRegForm.setSourcePage(null);
			}	
		}
    	invocationContext.target = LIST_SUCCESS;
    	return;
	}

	/**
	 * 
	 * 	Method		:	LoadCustomerDetailsHistoryCommand.populateRegCustomerForm
	 *	Added by 	:	A-5163 on September 2, 2014
	 * 	Used for 	:	ICRD-67442
	 *	Parameters	:	@param cusRegForm
	 *	Parameters	:	@param customerVO
	 *	Parameters	:	@param cusRegSession 
	 *	Return type	: 	void
	 */
	private void populateRegCustomerForm(MaintainRegCustomerForm cusRegForm, CustomerVO customerVO, MaintainCustomerRegistrationSession cusRegSession) {		  	
		if (customerVO.getCustomerCode() != null && customerVO.getCustomerCode().length() > 0) {
			cusRegForm.setCustomerTypecheckFlag(CONST_VALUE_YES);
		} else {
			cusRegForm.setCustomerTypecheckFlag(CONST_VALUE_NO);
		}
		if (customerVO.getCompanyCode() != null && customerVO.getCompanyCode().trim().length() > 0) {
			cusRegForm.setScreenStatus(CONST_VALUE_UPDATE);
			if (customerVO.getCustomerContactVOs() != null && customerVO.getCustomerContactVOs().size() > 0) {
				ArrayList<CustomerContactVO> contactVOs = new ArrayList<CustomerContactVO>(customerVO.getCustomerContactVOs().size());
				for(CustomerContactVO contactVO : customerVO.getCustomerContactVOs()){
					contactVOs.add(contactVO);
				}
				Collections.sort(contactVOs, new DesigComparator());
				customerVO.setCustomerContactVOs(contactVOs);
			}
			if (customerVO.getCustomerAgentVOs() != null && customerVO.getCustomerAgentVOs().size() > 0) {
				ArrayList<CustomerAgentVO> agentVOs = new ArrayList<CustomerAgentVO> (customerVO.getCustomerAgentVOs().size());
				for(CustomerAgentVO agentVO : customerVO.getCustomerAgentVOs()){
					agentVOs.add(agentVO);
				}
				Collections.sort(agentVOs, new AgentComparator());
				customerVO.setCustomerAgentVOs(agentVOs);
			}
			Map<String, String> paremeterMap = fetchSystemParameterDetails();
			String cmpCode = paremeterMap.get(SYSPAR_CMPCOD);
			cusRegForm.setCompanyCode(cmpCode);
			cusRegForm.setCustomerCode(customerVO.getCustomerCode());
			cusRegForm.setCustName(customerVO.getCustomerName());
			cusRegForm.setStation(customerVO.getStationCode());
			cusRegForm.setCustomerShortCode(customerVO.getCustomerShortCode());
			if (customerVO.getEstablishedDate() != null) {
				cusRegForm.setEstablishedDate(customerVO.getEstablishedDate().toDisplayDateOnlyFormat());
			}
			cusRegForm.setAccountNumber(customerVO.getAccountNumber());
			cusRegForm.setVatRegNumber(customerVO.getVatRegNumber());
			cusRegForm.setCustomerGroup(customerVO.getCustomerGroup());
			if (customerVO.getGlobalCustomerFlag() != null && CONST_VALUE_YES.equals(customerVO.getGlobalCustomerFlag())) {
				cusRegForm.setGlobalCustomer(CONST_VALUE_ON);
			} else {
				cusRegForm.setGlobalCustomer(CONST_VALUE_OFF);
			}
			// Added BY A-8374 For ICRD-247693
			if (customerVO.isClearingAgentFlag()) {
				cusRegForm.setClearingAgentFlag(true);
			} else {
				cusRegForm.setClearingAgentFlag(false);
			}
			if (customerVO.getValidFrom() != null) {
				String validFromDate = TimeConvertor.toStringFormat(((LocalDate) customerVO.getValidFrom()).toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT);
				cusRegForm.setFromDate(validFromDate);
			} else {
				cusRegForm.setFromDate(CONST_VALUE_BLANK);
			}
			if (customerVO.getValidTo() != null) {
				String validToDate = TimeConvertor.toStringFormat(((LocalDate) customerVO.getValidTo()).toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT);
				cusRegForm.setToDate(validToDate);
			} else {
				cusRegForm.setFromDate(CONST_VALUE_BLANK);
			}
			cusRegForm.setRemarks(customerVO.getRemarks());
			cusRegForm.setAddress1(customerVO.getAddress1());
			cusRegForm.setAddress2(customerVO.getAddress2());
			cusRegForm.setCity(customerVO.getCity());		
			cusRegForm.setArea(customerVO.getArea());		
			cusRegForm.setState(customerVO.getState());		
			cusRegForm.setCountry(customerVO.getCountry());
			cusRegForm.setZipCode(customerVO.getZipCode());
			cusRegForm.setTelephone(customerVO.getTelephone());
			cusRegForm.setMobile(customerVO.getMobile());
			cusRegForm.setFax(customerVO.getFax());
			cusRegForm.setSita(customerVO.getSita());
			cusRegForm.setEmail(customerVO.getEmail());
			cusRegForm.setEoriNo(customerVO.getEoriNo());
			if (customerVO.getCustomerMiscDetails() != null) {
				cusRegForm.setCustomsLocationNo(customerVO.getCustomerMiscDetails().getCustomsLocationNumber());
			}
			if (customerVO.getCustomerBillingDetailsVO() != null) {
				cusRegForm.setBillingStreet(customerVO.getCustomerBillingDetailsVO().getStreet());
				cusRegForm.setBillingLocation(customerVO.getCustomerBillingDetailsVO().getLocation());
				cusRegForm.setBillingCityCode(customerVO.getCustomerBillingDetailsVO().getCityCode());
				cusRegForm.setBillingState(customerVO.getCustomerBillingDetailsVO().getState());	
				cusRegForm.setBillingCountry(customerVO.getCustomerBillingDetailsVO().getCountry());
				cusRegForm.setBillingZipcode(customerVO.getCustomerBillingDetailsVO().getZipcode());
				cusRegForm.setBillingTelephone(customerVO.getCustomerBillingDetailsVO().getTelephone());
				cusRegForm.setBillingFax(customerVO.getCustomerBillingDetailsVO().getFax());				
				cusRegForm.setBillingEmail(customerVO.getCustomerBillingDetailsVO().getEmail());				
				//added by A-7905 as part of ICRD-228463 starts
				cusRegForm.setBillingEmailOne(customerVO.getCustomerBillingDetailsVO().getEmailOne());
				cusRegForm.setBillingEmailTwo(customerVO.getCustomerBillingDetailsVO().getEmailTwo());
				//added by A-7905 as part of ICRD-228463 ends
			}
			
			cusRegForm.setBillPeriod(customerVO.getBillPeriod());				
			cusRegForm.setZone(customerVO.getZone());			
			cusRegForm.setCreditLimit(String.valueOf(customerVO.getCreditLimit()));
			cusRegForm.setCreditPeriod(String.valueOf(customerVO.getCreditPeriod()));
			cusRegForm.setCreditLimitCurrencyCode(customerVO.getCreditCurrencyCode());
			cusRegForm.setCreditAccountNo(customerVO.getCreditAccountNo());
			cusRegForm.setCreditOutStanding(String.valueOf(customerVO.getCreditOutstanding()));
			cusRegForm.setCreditOutStandingCurrencyCode(customerVO.getCreditOutstdCurrencyCode());
			cusRegForm.setEmail2(customerVO.getEmail2());
			cusRegForm.setCustomerCompanyCode(customerVO.getCustomerCompanyCode());
			cusRegForm.setBranch(customerVO.getBranch());
			cusRegForm.setBillingCode(customerVO.getBillingCode());
			/*Added by 201930 for IASCB-131790*/
			cusRegForm.setCassCountry(customerVO.getCassCountryCode());
			cusRegForm.setNaacsbbAgentCode(customerVO.getNaacsbbAgentCode());
			cusRegForm.setNaccsInvoiceCode(customerVO.getNaccsInvoiceCode());
			cusRegForm.setSpotForwarder(customerVO.getSpotForwarder());
			cusRegForm.setDefaultHawbLength(customerVO.getDefaultHawbLength());
			cusRegForm.setBillingPeriod(customerVO.getBillingPeriod());
			//Added by A-7555 for ICRD-319714
			cusRegForm.setDueDateBasis(customerVO.getDueDateBasis());
			cusRegForm.setHandledCustomerImport(customerVO.getHandledCustomerImport());
			cusRegForm.setHandledCustomerExport(customerVO.getHandledCustomerExport());
			cusRegForm.setHandledCustomerForwarder(customerVO.getHandledCustomerForwarder());
			cusRegForm.setForwarderType(customerVO.getForwarderType());
			cusRegForm.setConsolidator(customerVO.getConsolidator());
			cusRegForm.setPoa(customerVO.getPoa());
			cusRegForm.setNaccsDeclarationCode(customerVO.getNaccsDeclarationCode());
			cusRegForm.setNaccsAircargoAgentCode(customerVO.getNaccsAircargoAgentCode());
			cusRegForm.setBranchName(customerVO.getBranchName());
			cusRegForm.setScc(customerVO.getScc());
			if (customerVO.getCustomerPOAValidity() != null) {
				cusRegForm.setCustomerPOAValidity(customerVO.getCustomerPOAValidity().toDisplayDateOnlyFormat());
			}
			cusRegForm.setBillingPeriod(customerVO.getBillingPeriod());
			cusRegForm.setBillingPeriodDescription(customerVO.getBillingPeriodDescription());
			if (customerVO.getIacDetailsVO() != null) {
				cusRegForm.setIacNumber(customerVO.getIacDetailsVO().getIacNumber());
				if (customerVO.getIacDetailsVO().getIacExpiryDate() != null) {
					cusRegForm.setIacExpiryDate(customerVO.getIacDetailsVO().getIacExpiryDate().toDisplayDateOnlyFormat());
				}
				cusRegForm.setApiacsspNumber(customerVO.getIacDetailsVO().getApiacsspNumber());
				if (customerVO.getIacDetailsVO().getApiacsspExpiryDate() != null) {
					cusRegForm.setApiacsspExpiryDate(customerVO.getIacDetailsVO().getApiacsspExpiryDate().toDisplayDateOnlyFormat());
				}
			}
			cusRegForm.setSalesId(customerVO.getSalesId());
			if (customerVO.isControllingLocation()) {
				cusRegForm.setControllingLocation(CONST_VALUE_ON);
			}
			if (customerVO.isSellingLocation()) {
				cusRegForm.setSellingLocation(CONST_VALUE_ON);
			}
			cusRegForm.setIataCode(customerVO.getContyrollingAgentCode());
			cusRegForm.setClName(customerVO.getControllingAgentName());
			cusRegForm.setBillingTo(customerVO.getBillingIndicator());
			/*Added by A-7567 as part of ICRD-305684*/
			cusRegForm.setCntLocBillingApplicableTo(customerVO.getCntLocBillingApplicableTo());
			if (customerVO.getInvoiceClubbingIndicator() != null && CustomerVO.FLAG_YES.equals(customerVO.getInvoiceClubbingIndicator())) {
				cusRegForm.setInvoiceClubbingIndicator(CONST_VALUE_ON);
			}
			if (customerVO.isEnduranceFlag()) {
				cusRegForm.setEnduranceFlag(CONST_VALUE_AWB);
				cusRegForm.setEndurancePercentage(Double.valueOf(customerVO.getEndurancePercentage()).toString());
				cusRegForm.setEnduranceValue(Double.valueOf(customerVO.getEnduranceValue()).toString());
				cusRegForm.setEnduranceMaxValue(Double.valueOf(customerVO.getEnduranceMaxValue()).toString());

			} else {
				cusRegForm.setEnduranceFlag(CONST_VALUE_INVOICE);
				cusRegForm.setEndurancePercentage(Double.valueOf(customerVO.getEndurancePercentage()).toString());
				cusRegForm.setEnduranceValue(Double.valueOf(customerVO.getEnduranceValue()).toString());
				cusRegForm.setEnduranceMaxValue(Double.valueOf(customerVO.getEnduranceMaxValue()).toString());
			}
			cusRegForm.setSettlementCurrency(customerVO.getSettlementCurrencyCodes());
			cusRegForm.setBillingremark(customerVO.getRemark());
			//CRQ ID:117235 - A-5127 added
			cusRegForm.setRecipientCode(customerVO.getRecipientCode());
			cusRegForm.setCassImportIdentifier(customerVO.getCassImportIdentifier());
			//End - CRQ ID:117235 - A-5127 added
			if (CONST_VALUE_CUSTOMER.equals(customerVO.getCustomerType())) {
				cusRegForm.setCustomerType(customerVO.getCustomerType());
			}
			if (CONST_VALUE_AGENT.equals(customerVO.getCustomerType())) {
				cusRegForm.setCustomerType(customerVO.getCustomerType());
				for (AgentVO agentVO : customerVO.getAgentVOs()) {
					cusRegForm.setIataAgentCode(agentVO.getIataAgentCode());
					cusRegForm.setAgentInformation(agentVO.getAgentType());
					cusRegForm.setCassIdentifier(agentVO.getCassIdentifier());
					cusRegForm.setHoldingCompany(agentVO.getHoldingCompany());
					cusRegForm.setNormalComm(String.valueOf(agentVO.getNormCommPrc()));
					cusRegForm.setNormalCommFixed(String.valueOf(agentVO.getFixedValue()));
					if (agentVO.getOwnSalesFlag() != null && CONST_VALUE_YES.equals(agentVO.getOwnSalesFlag())) {
						cusRegForm.setOwnSalesFlag(CONST_VALUE_ON);
					}
					if (agentVO.isSalesReporting()) {
						cusRegForm.setSalesReportAgent(CONST_VALUE_ON);
					}
					if (agentVO.isInvoiceGenerationFlag()) {
						cusRegForm.setProformaInv(CONST_VALUE_ON);
					}
					cusRegForm.setBillingremark(agentVO.getRemarks());
				}
			}
			if (CONST_VALUE_CHARGE_COLLECT.equals(customerVO.getCustomerType())) {
				cusRegForm.setCustomerType(customerVO.getCustomerType());
				StringBuilder aircraftTypeHandled = new StringBuilder();
				for (CCCollectorVO cCCollectorVO : customerVO.getCcCollectorVOs()) {
					cusRegForm.setAirportCode(cCCollectorVO.getAirportCode());
					aircraftTypeHandled.append(cCCollectorVO.getAircraftTypeHandled());
					cusRegForm.setDateOfExchange(cCCollectorVO.getDateOfExchange());
					cusRegForm.setCassBillingIndicator(cCCollectorVO.isCassBillingIndicator());
					cusRegForm.setCassCode(cCCollectorVO.getCassCode());
					cusRegForm.setBillingThroughInterline(cCCollectorVO.isBillingThroughInterline());
					cusRegForm.setAirlineCode(cCCollectorVO.getAirlineCode());
					cusRegForm.setBillingremark(cCCollectorVO.getRemarks());
					//Added by A-7656 for ICRD-242148
					cusRegForm.setCcFeeDueGHA(cCCollectorVO.isCCFeeDueGHA());
				}
				String[] aircraftType = aircraftTypeHandled.toString().split(CONST_VALUE_COMMA);
				aircraftTypeHandled = new StringBuilder();
				Collection<OneTimeVO> oneTimeValue = cusRegSession.getOneTimeValues().get(SYSPAR_FLTTYP);
				for (OneTimeVO oneTimeVO : oneTimeValue) {
					aircraftTypeHandled.append(oneTimeVO.getFieldValue());
					boolean flag = false;
					for (String aircraftValue : aircraftType) {
						if (aircraftValue.equalsIgnoreCase(oneTimeVO.getFieldValue())) {
							aircraftTypeHandled.append(CONST_VALUE_HYPHEN);
							aircraftTypeHandled.append(CONST_VALUE_YES);
							flag = true;
							break;
						}
					}
					if (!flag) {
						aircraftTypeHandled.append(CONST_VALUE_HYPHEN);
						aircraftTypeHandled.append(CONST_VALUE_NO);
					}
					aircraftTypeHandled.append(CONST_VALUE_COMMA);
				}
				cusRegForm.setAircraftTypeHandledList(aircraftTypeHandled.toString());
			}
			/* Changed as part of ICRD-178015 on 26OCT2016 */
			if (customerVO.getGibCustomerFlag() != null
					&& "Y".equals(customerVO.getGibCustomerFlag())) {
				cusRegForm.setGibCustomerFlag("on");
			} else {
				cusRegForm.setGibCustomerFlag("off");
			}
			if (customerVO.getPublicSectorFlag() != null
					&& "Y".equals(customerVO.getPublicSectorFlag())) {
				cusRegForm.setPublicSectorFlag("on");
			} else {
				cusRegForm.setPublicSectorFlag("off");
			}
			if (customerVO.getGibRegistrationDate() != null) {
				cusRegForm.setGibRegistrationDate(customerVO
						.getGibRegistrationDate().toDisplayDateOnlyFormat());
			} else {
				cusRegForm.setGibRegistrationDate("");
			}
			/* Changed as part of ICRD-178015 on 26OCT2016 ends */
		}
	}
	
	/**
	 * 
	 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.LoadCustomerDetailsHistoryCommand.java
	 *	Version		:	Name	:	Date					:	Status
	 * ------------------------------------------------------------------
	 *		0.1		:	A-5163	:	September 2, 2014		:	Draft
	 */
	class DesigComparator implements Comparator<CustomerContactVO> {
		public int compare(CustomerContactVO firstVO, CustomerContactVO secondVO) {
			int returnValue = 0;
			int firstSeq = firstVO.getSequenceNumber();
			int secondSeq = secondVO.getSequenceNumber();
			if (firstSeq < secondSeq) {
				returnValue = -1;
			} else if (firstSeq == secondSeq) {
				returnValue = 0;
			} else if (firstSeq > secondSeq) {
				returnValue = 1;
			}
			return returnValue;
		}
	}
	
	/**
	 * 
	 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer.LoadCustomerDetailsHistoryCommand.java
	 *	Version		:	Name	:	Date				:	Status
	 * -----------------------------------------------------------
	 *		0.1		:	A-5163	:	September 2, 2014	:	Draft
	 */
	class AgentComparator implements Comparator<CustomerAgentVO> {
		public int compare(CustomerAgentVO firstVO, CustomerAgentVO secondVO) {
			int returnValue = 0;
			int firstSeq = firstVO.getSequenceNumber();
			int secondSeq = secondVO.getSequenceNumber();
			if (firstSeq < secondSeq) {
				returnValue = -1;
			} else if (firstSeq == secondSeq) {
				returnValue = 0;
			} else if (firstSeq > secondSeq) {
				returnValue = 1;
			}
			return returnValue;
		}
	}

	/**
	 * 
	 * 	Method		:	LoadCustomerDetailsHistoryCommand.fetchSystemParameterDetails
	 *	Added by 	:	A-5163 on September 2, 2014
	 * 	Used for 	:	ICRD-67442
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,String>
	 */
	private Map<String, String> fetchSystemParameterDetails() {
		Map<String, String> sysParMap = new HashMap<String, String>();
		Collection<String> sysParList = new ArrayList<String>();
		sysParList.add(SYSPAR_CMPCOD);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			sysParMap = sharedDefaultsDelegate.findSystemParameterByCodes(sysParList);
		} catch (BusinessDelegateException exception) {
			handleDelegateException(exception);
		}
		return sysParMap;
	}
	
	/**
	 * 
	 * 	Method		:	LoadCustomerDetailsHistoryCommand.populateSequenceNumber
	 *	Added by 	:	A-5163 on October 16, 2014
	 * 	Used for 	:	populateSequenceNumber. 
	 * 				:	To correct sequence number mismatch between history version and master data.
	 *	Parameters	:	@param customerVO
	 *	Parameters	:	@param customerVO2 
	 *	Return type	: 	void
	 */
	private void populateSequenceNumber(CustomerVO customerVOInSession, CustomerVO customerVO) {
		Collection<CustomerAgentVO> customerAgentVOs = null;
		Collection<CustomerContactVO> customerContactVOs = null;
		Collection<CustomerCertificateVO> customerCertificateDetails = null;
		Collection<CustomerPreferenceVO> customerPreferenceDetails = null;
		if (customerVOInSession.getCustomerAgentVOs() != null
				&& !customerVOInSession.getCustomerAgentVOs().isEmpty()) {
			customerAgentVOs = customerVOInSession.getCustomerAgentVOs();
		}
		if (customerVOInSession.getCustomerContactVOs() != null
				&& !customerVOInSession.getCustomerContactVOs().isEmpty()) {			
			customerContactVOs =  customerVOInSession.getCustomerContactVOs();			
		}
		if (customerVOInSession.getCustomerCertificateDetails() != null
				&& !customerVOInSession.getCustomerCertificateDetails().isEmpty()) {			
			customerCertificateDetails = customerVOInSession.getCustomerCertificateDetails();				
		}
		if (customerVOInSession.getCustomerPreferences() != null
				&& !customerVOInSession.getCustomerPreferences().isEmpty()) {			
			customerPreferenceDetails = customerVOInSession.getCustomerPreferences();				
		}
		
		if (customerVO.getCustomerAgentVOs() != null
				&& !customerVO.getCustomerAgentVOs().isEmpty()
				&& customerAgentVOs != null
				&& !customerAgentVOs.isEmpty()
				&& customerAgentVOs.size() == customerVO.getCustomerAgentVOs().size()) {
			customerVO.setCustomerAgentVOs(customerAgentVOs);
		}
		if (customerContactVOs != null
				&& !customerContactVOs.isEmpty()
				&& customerVO.getCustomerContactVOs() != null
				&& !customerVO.getCustomerContactVOs().isEmpty()
				&& customerContactVOs.size() == customerVO.getCustomerContactVOs().size()) {
		 customerVO.setCustomerContactVOs(new ArrayList<CustomerContactVO>(customerContactVOs));
		}
		if (customerPreferenceDetails != null
				&& !customerPreferenceDetails.isEmpty()
				&& customerVO.getCustomerPreferences() != null
				&& !customerVO.getCustomerPreferences().isEmpty()
				&& customerPreferenceDetails.size() == customerVO.getCustomerPreferences().size()) {				
				customerVO.setCustomerPreferences(customerPreferenceDetails);
			}
		
		if (customerCertificateDetails != null
				&& !customerCertificateDetails.isEmpty()
				&& customerVO.getCustomerCertificateDetails() != null
				&& !customerVO.getCustomerCertificateDetails().isEmpty()
				&& customerCertificateDetails.size() == customerVO.getCustomerCertificateDetails().size()) {				
				customerVO.setCustomerCertificateDetails(customerCertificateDetails);
			}
	}
	
}
