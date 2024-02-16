/*
 * ClearCustomerRegistrationCommand.java Created on Dec 19, 2005
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

import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;

/**
 * 
 * @author A-2046
 * 
 */
public class ClearCustomerRegistrationCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";

	private static final String BLANK = "";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);

		session.setPageURL(null);
		session.setCustomerVO(null);
		form.setCustName(BLANK);
		form.setCustomerCode(BLANK);
		form.setAddress1(BLANK);
		form.setCustomerShortCode(BLANK);//Added by A-5214 as part from the ICRD-21666
		form.setStatus(BLANK);//Added by A-5214 as part from the ICRD-21666
		form.setAddress2(BLANK);
		form.setCity(BLANK);
		form.setCountry(BLANK);
		form.setArea(BLANK);
		form.setZipCode(BLANK);
		form.setEmail(BLANK);
		form.setMobile(BLANK);
		form.setTelephone(BLANK);
		form.setCreditAccountNo(BLANK);
		form.setCreditLimit(BLANK);
		form.setCreditLimitCurrencyCode(BLANK);
		form.setCreditOutStanding(BLANK);
		form.setCreditOutStandingCurrencyCode(BLANK);
		form.setZone(BLANK);
		form.setState(BLANK);
		form.setSita(BLANK);
		form.setFax(BLANK);
		form.setStation(BLANK);
		form.setCreditPeriod(BLANK);
		form.setCustomerGroup(BLANK);
		form.setEoriNo(BLANK);
		if(!("maintainReservation").equals(form.getScreenStatus())){
			form.setScreenStatus(""); 
		}
		form.setGlobalCustomer("");
		form.setFromCustListing(BLANK);
		form.setDisplayPage("1");
		form.setLastPageNumber("0");
		//added by a-3045 for CR HA16 on 27May09 starts
		form.setAccountNumber(BLANK);
		form.setEstablishedDate(BLANK);
		form.setRemarks(BLANK);
		form.setBillingCityCode(BLANK);
		form.setBillingCountry(BLANK);
		form.setBillingEmail(BLANK);
		form.setBillingEmailOne(BLANK);
		form.setBillingEmailTwo(BLANK);
		form.setBillingFax(BLANK);
		form.setBillingLocation(BLANK);
		form.setBillingState(BLANK);
		form.setBillingStreet(BLANK);
		form.setBillingTelephone(BLANK);
		form.setBillingZipcode(BLANK);
		form.setIacNumber(BLANK);
		form.setIacExpiryDate(BLANK);
		form.setApiacsspNumber(BLANK);
		form.setApiacsspExpiryDate(BLANK);
		//A-3278 for JetBlue31-1 on 23Apr10
		form.setBillingPeriod(BLANK);
		//Added by A-5165	for ICRD-35135
		form.setBillingDueDays(BLANK);
		//Added by A-7555 for ICRD-319714
		form.setDueDateBasis(BLANK);
		form.setBillingPeriodDescription(BLANK);
		//form.setCompanyCode(BLANK);
		form.setNaccsInvoiceCode(BLANK);
		form.setNaccsDeclarationCode(BLANK);
		form.setBranch(BLANK);
		form.setNaccsAircargoAgentCode(BLANK);
		form.setBillingCode(BLANK);
		form.setDefaultHawbLength(BLANK);
		form.setBranchName(BLANK);
		form.setNaacsbbAgentCode(BLANK);
		//Added by A-5169 for ICRD-31552 on 29-APR-2013 
		form.setCustomsLocationNo(BLANK); 
		//JetBlue31-1 ends
		//added by a-3045 for CR HA16 on 27May09 starts
		form.setForwarderType(BLANK);
		
		session.setCustomerCodesFromListing(null);
		CustomerVO customerVO = new CustomerVO();
		customerVO.setOperationFlag(OPERATION_FLAG_INSERT);
		session.setCustomerVO(customerVO);
		
		//Added now for null row in screenload
    	
		//Added for ICRD-67442 by A-5163 starts
		session.setCustomerDetailVOsInMap(null);
		session.removeValidationErrors();
		session.setSourcePage(null);
		form.setSourcePage(null);
		form.setVersionNumbers(null);
		form.setNavVersionNumbers(null);
		form.setCusVersionNumber(BLANK);
		form.setIsHistoryPresent(BLANK);
		form.setIsHistoryPopulated(BLANK);
		form.setDisplayPopupPage(BLANK);
		form.setTotalViewRecords(BLANK);
		form.setIsLatestVersion(BLANK);
		form.setClearingAgentFlag(false);// Added BY A-8374 For ICRD-247693
    	//Added for ICRD-67442 by A-5163 ends
    	
    	Collection<CustomerContactVO> keyContactDetailVOs = new ArrayList<CustomerContactVO>();
    	
    	/*CustomerContactVO newContactVO = new CustomerContactVO();
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
		customerVO.setCustomerContactVOs(keyContactDetailVOs);*/
		session.setCustomerVO(null);
		session.removeAdditionalContacts();
		session.removeNotificationPreferences();
		
		//Added by 203132 for IASCB-177945 starts
		form.setContactTypes(null);
		form.setContactType(null);
		//Added by 203132 for IASCB-177945 ends
		
		// Added BY A-5183 For ICRD-18283 Starts
		
		form.setAirportCode(BLANK);
		form.setVatRegNumber(BLANK);
		form.setAircraftTypeHandled(BLANK);
		form.setDateOfExchange(BLANK);    			
		form.setCassBillingIndicator(false);
		form.setCassCode(BLANK);    			
		form.setBillingThroughInterline(false);    			
		form.setAirlineCode(BLANK);    			
		//form.setBillingCurrency(BLANK);
		form.setBillingPeriodDescription(BLANK);
		form.setIataAgentCode(BLANK);
		form.setAgentInformation(BLANK);    			
		form.setCassIdentifier(BLANK);
		form.setSalesId(BLANK);
		form.setHoldingCompany(BLANK);   	
		form.setNormalComm(String.valueOf(BLANK));
		form.setNormalCommFixed(String.valueOf(BLANK));
		form.setOwnSalesFlag("");
		form.setSalesReportAgent("");
		form.setControllingLocation("");
		form.setSellingLocation("");
		form.setProformaInv("");
		form.setIataCode(BLANK);
		form.setClName(BLANK);
		form.setBillingTo(BLANK);
		//Added for ICRD-33334 by A-5163 Starts
		form.setInvoiceClubbingIndicator(null);
		//Added for ICRD-33334 by A-5163 Ends
		form.setEnduranceFlag(""); 
		form.setEndurancePercentage(BLANK);
		form.setEnduranceValue(BLANK);
		form.setEnduranceMaxValue(BLANK);
		form.setSettlementCurrency(BLANK);
		form.setRemarks(BLANK);   
		form.setAgent("");
		form.setCccollector("");
		form.setCustomer("");
		form.setFromDate("");
		form.setToDate("");
		form.setCustomerType(BLANK);
		form.setInternalAccountHolder(BLANK);//Added by A-7534 for ICRD-228903
		form.setBillingremark(BLANK);
		
		form.setBankbranch(BLANK);
		form.setBankcityname(BLANK);
		form.setBankcode(BLANK);
		form.setBankcountryname(BLANK);
		form.setBankpincode(BLANK);
		form.setBankstatename(BLANK);
		form.setResave(BLANK);
		//form.setValidateflag(BLANK);
		//CRQ ID:117235 - A-5127 added
		form.setRecipientCode(BLANK);
		form.setCassImportIdentifier(BLANK);
	    //End - CRQ ID:117235 - A-5127 added
		form.setMiscDetailCode(null);
		form.setMiscDetailOpFlag(null);
		form.setMiscDetailRemarks(null);
		form.setMiscDetailValidFrom(null);
		form.setMiscDetailValidTo(null);
		form.setMiscDetailValue(null);
		form.setMiscSeqNum(null);
		form.setMisopcheck(null);
		form.setAircraftTypeHandledList(null);
		// Added BY A-5183 For ICRD-18283 Ends
		//Added by A-5807 for ICRD-73246
		form.setRestrictedFOPs(null);
		//Added by A-5198 for ICRD-70917
		form.setCustomerTypecheckFlag(BLANK);
		form.setExcludeRounding("");//Added for ICRD-57704 BY A-5117
		//added by a-6314 for ICRD-112496
		form.setInvoiceType(null);
		//added by A-7656 for ICRD-242148
		form.setCcFeeDueGHA(false);
		/*Added by A-7567 as part of ICRD-305684*/
		form.setCntLocBillingApplicableTo(BLANK);
		form.setCustomerCertificateType(null);// Added by A-8832 as part of IASCB-3936
		form.setPreferenceValue(null);
		/*Added by 201930 for IASCB-131790*/
		form.setCassCountry(null);
		invocationContext.target = CLEAR_SUCCESS;

	}
}
