/*
 * SaveCustomerCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CCCollectorVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerBillingDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscellaneousVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerPreferenceVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.IACDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.ScreeningFacilityVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.scc.SCCDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.capacity.booking.MaintainBookingSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.capacity.booking.permanent.MaintainPermanentBookingSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1496
 *
 */
public class SaveCustomerCommand extends BaseCommand {

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";

	/** The Constant NO_OPERATION. */
	private static final String NO_OPERATION = "NOOP";

	private static final String BLANK = "";

	private static final String BOTH =  "B";
	
	private static final String CANNOT_UPDATE_RECORD="customermanagement.defaults.maintainregcustomer.cannotupdateelapsedrecord";
	private static final String CANNOT_DELETE_RECORD="customermanagement.defaults.maintainregcustomer.cannotdeleteelapsedrecord";
	private static final String OVERLAPPDATE="customermanagement.defaults.maintainregcustomer.overlappeddate";
	private static final String ERR_DATE_OUTSIDE_AGENT_VALIDITY = 
		"customermanagement.defaults.maintainregcustomer.datesoutsideagentvalidity";
	private static final String DATELESS_THAN_CURRENTDATE="customermanagement.defaults.maintainregcustomer.datelessthancurrentdate";
	private static final String FROMDATE_TODATE_MANDATORY="customermanagement.defaults.maintainregcustomer.fromdateandtodateismandatory";
	private static final String VALUE_MANDATORY ="customermanagement.defaults.customerregn.msg.err.valuemandatory";

	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");

	private static final String CUSTOMER_ALREADY_EXISTS = "shared.customer.customeralreadyexists";

	private static final String NOOPERATION_FLAG="NOOP";
	
	private static final String LOCK_ACTION = "MODIFYCUS";
	private static final String LOCK_REMARKS = "CUSTOMER LOCKING";
	private static final String LOCK_DESCRIPTION = "Customer Modifications";
	private static final String DELIMETER_COMMA = ",";
	private static final String CHECKSTATUS = "on";
	private static final String CUSTOMER_TYPE_ONETIME = "shared.customer.customertype";
	private static final String CONTROL_CUSTOMER_TYPE_ONETIME = "shared.customer.controllingcustomertype";
	private static final String CUSTOMER_ALREADY_EXISTS_FOR_AIRPORTDETAILS= "cra.defaults.ccCollectorAlreadyExistingForAirport";
	//Added by A-5165 for ICRD-39214
	private static final String SYSPAR_CRA_PRESENT = "system.defaults.isCRApresent";
	
	/** The Constant SOURCE_NAVIGATION. */
	private static final String SOURCE_SAVE = "SAVE";
	
	private static final String ONETIME_BANKCODETYPE = "shared.agent.bankdetails.codes";
	
	// Added for ICRD-58628 by A-5163 starts
	private static final String CERTIFICATE_TYPE_INDIRECT_AIR_CARRIER = "IAC";
	private static final String CERTIFICATE_TYPE_DECLARED_KNOWN = "SDK";
	private static final String CERTIFICATE_TYPE_KNOWN_SHIPPER = "Known Shipper";
	private static final String INDIRECT_AIR_CARRIER = "Indirect Air Carrier";
	private static final String DECLARED_KNOWN = "Declared Known";
	private static final String CANNOT_EXIST_TOGETHER_DUPLICATE = "customermanagement.defaults.customerregistration.certifications.duplicatescannotexists";
	private static final String CANNOT_EXIST_TOGETHER = "customermanagement.defaults.customerregistration.certifications.cannotexisttogether";
	private static final String DUPLICATE_CERTIFICATE_DETAILS = "customermanagement.defaults.customerregistration.certifications.duplicatecertificates";
	private static final String ERR_CODE_INVALID_DATERANGE = "customermanagement.defaults.customerregistration.certifications.todateearlierthanfromdate";
	// Added for ICRD-58628 by A-5163 ends
	//Added for ICRD-112552 by A-5163 starts
	private static final String DUPLICATE_CERTIFICATE_NUMBERS = "customermanagement.defaults.customerregistration.certifications.duplicatecertificatenums";
	//Added for ICRD-112552 by A-5163 ends
	 //Added for ICRD-229500 by a-3429
	private static final String STATECODE_ONETIME = "tariff.tax.statecode";
    //Added as part of ICRD-212854
	private static final String INVALID_ACCT_RESPONSIBLE = "customermanagement.defaults.customerregistration.preferences.invalidaccountingresponsible";
	private static final String WRONG_PGNHLE_FORMAT = "customermanagement.defaults.customerregistration.preferences.invalidpigeonHoleformat";   
    private static final String ACCOUNTING_RESP_CODE = "ACCTRESP";
    private static final String DELIVERY_SLOT_TIME_CODE = "DLVSLTTIM";
    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3])[0-5][0-9]";
	 private static final String PGNHLE_CODE = "PGNHLE";
	// Added by A-8823 for IASCB-4841 beg
	private static final String ERR_DUPLICATE_CARRIER_EXISTS_IN_AGENT_DETAILS = "customermanagement.defaults.customerregistration.agent.duplicatecarriercannotexists";
	private static final String ERR_INVALID_CARRIER_EXISTS_IN_AGENT_DETAILS = "customermanagement.defaults.customerregistration.agent.invalidcarriercannotexists";
	// Added by A-8823 for IASCB-4841 end
	
	private static final String INVALID_DELIVERY_TIME_FORMAT = "customermanagement.defaults.customerregistration.certifications.invaliddeliveryslotformat";
	
    /* Added as part of IASCB-105699*/
    private static final String INVALID_ACCT_SUPERVISOR = "customermanagement.defaults.customerregistration.preferences.invalidaccountingsupervisor";
    private static final String ACCOUNTING_SUPR_CODE = "ACCTSUPR";


    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);
		MaintainBookingSession maintainBookingSession = getScreenSession("capacity.booking", "capacity.booking.maintainreservation");
		MaintainPermanentBookingSessionInterface maintainPermanentBookingSessionInterface = getScreenSession(
				"capacity.booking.permanent",
				"capacity.booking.permanent.maintainpermanentbooking");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		CustomerVO customerVO =null;
		String companyCode = logonAttributes.getCompanyCode();
		Collection<CustomerMiscDetailsVO> initialCustomerMiscValues=null;
		
		//setting the main operationflag
		if(session.getCustomerVO()!=null && session.getCustomerVO().getCustomerCode()!= null){
			
			customerVO = session.getCustomerVO();
			/* Commented by A-6055 for ICRD-88346
			 * if(customerVO.getCustomerMiscValDetailsVO()!=null && customerVO.getCustomerMiscValDetailsVO().size()>0){
				initialCustomerMiscValues=new ArrayList<CustomerMiscDetailsVO>();
				for(CustomerMiscDetailsVO customerMiscDetailsVO:customerVO.getCustomerMiscValDetailsVO()){
					initialCustomerMiscValues.add(customerMiscDetailsVO);
				}
			}*/
			//Added by A-6055 for ICRD-88346
			if(session.getCustomerMiscDetailsVOs()!= null && session.getCustomerMiscDetailsVOs().size() > 0){
				initialCustomerMiscValues = (ArrayList<CustomerMiscDetailsVO>) session.getCustomerMiscDetailsVOs();
			}
			//ICRD-88346 ends
			customerVO.setCcCollectorVOs(null);
			log.log(Log.FINE, "\n opstatusx", customerVO.getOperationFlag());
			if(!OPERATION_FLAG_INSERT.equals(customerVO.getOperationFlag())){
				customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
			}
			
		}else{
			customerVO = new CustomerVO();
			customerVO.setOperationFlag(OPERATION_FLAG_INSERT);
			
		}
		
		// Added by A-5198 for ICRD-56507 
		if(session.getCustomerIDGenerationRequired() != null
				&& !session.getCustomerIDGenerationRequired().isEmpty()){
			customerVO.setCustomerIDGenerationRequired(session.getCustomerIDGenerationRequired());
		}
		// Added BY A-5183 For ICRD-18283 Starts		
		
		if(form.getToDate()!= null && form.getToDate().trim().length()> 0) {
	   		LocalDate toDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
	   		toDate.setDate(form.getToDate());
	   		customerVO.setValidTo(toDate);
	   	}
		
		if(form.getFromDate()!= null &&	form.getFromDate().trim().length()> 0) {
	   		LocalDate frmDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
	   		frmDate.setDate(form.getFromDate());
	   		customerVO.setValidFrom(frmDate);
	   	}			
		
		customerVO.setSalesId(form.getSalesId());
		
		if(CHECKSTATUS.equals(form.getControllingLocation())){
			customerVO.setControllingLocation(true);
		}
		else{
			customerVO.setControllingLocation(false);
		}
		if(CHECKSTATUS.equals(form.getSellingLocation())){
			customerVO.setSellingLocation(true);
		}
		else{
			customerVO.setSellingLocation(false);
		}
		
		if(!(BLANK.equals(form.getIataCode()))){
			customerVO.setContyrollingAgentCode(form.getIataCode().toUpperCase());
		}
		else{
			customerVO.setContyrollingAgentCode(null);
		}
		if(Objects.nonNull(form.getClName()) && !(BLANK.equals(form.getClName())) ){
			customerVO.setControllingAgentName(form.getClName().toUpperCase());
		}
		else{
			customerVO.setControllingAgentName(null);
		}
		customerVO.setBillingIndicator(form.getBillingTo());
		/*Added by A-7567 as part of ICRD-305684*/
		customerVO.setCntLocBillingApplicableTo(form.getCntLocBillingApplicableTo());
		customerVO.setRemark(form.getBillingremark());
			
		if (form.getVatRegNumber() != null) {
			customerVO.setVatRegNumber(form.getVatRegNumber());
		}
		
		if("awb".equals(form.getEnduranceFlag())){ 			
			//Modified by A-9066 as part of IASCB-75503 starts 
			customerVO.setEnduranceFlag(true);
			 if((!BLANK.equals(form.getEndurancePercentage())) && form.getEndurancePercentage()!= null){
	   				customerVO.setEndurancePercentage(Double.parseDouble(form.getEndurancePercentage()));
	   		   }else{
	   			customerVO.setEndurancePercentage(0.0);
	   		   }
	   		   if(form.getEnduranceValue()!=null && !BLANK.equals(form.getEnduranceValue())){
	   				customerVO.setEnduranceValue(Double.parseDouble(form.getEnduranceValue()));
		   	   }else{
		   		customerVO.setEnduranceValue(0.0);
		   	   }
	   			if(form.getEnduranceMaxValue()!=null && !BLANK.equals(form.getEnduranceMaxValue())){
	   				customerVO.setEnduranceMaxValue(Double.parseDouble(form.getEnduranceMaxValue()));
	   			}else{
	   				customerVO.setEnduranceMaxValue(0.0);
	   			}
	   			
	   	}else if("invoice".equals(form.getEnduranceFlag())){	   		
	   		customerVO.setEnduranceFlag(false);
	   	 if((!BLANK.equals(form.getEndurancePercentage())) && form.getEndurancePercentage()!= null){
	   				customerVO.setEndurancePercentage(Double.parseDouble(form.getEndurancePercentage()));
   		   }else{
   			customerVO.setEndurancePercentage(0.0);
   		   }
   		   if(form.getEnduranceValue()!=null && !BLANK.equals(form.getEnduranceValue())){
	   				customerVO.setEnduranceValue(Double.parseDouble(form.getEnduranceValue()));
	   	   }else{
	   		customerVO.setEnduranceValue(0.0);
	   	   }
	   			if(form.getEnduranceMaxValue()!=null && !BLANK.equals(form.getEnduranceMaxValue())){
	   				customerVO.setEnduranceMaxValue(Double.parseDouble(form.getEnduranceMaxValue()));
	   			}else{
	   				customerVO.setEnduranceMaxValue(0.0);
	   			}
		}	
		//Modified by A-9066 as part of IASCB-75503 ends
		String currencyCodes = form.getSettlementCurrency().toUpperCase();				
		customerVO.setSettlementCurrencyCodes(currencyCodes);
		
		if(customerVO.getCustomerType()!= null){			
			if(!customerVO.getCustomerType().equals(form.getCustomerType())){				
				if("A".equals(customerVO.getStatus())){					
					error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.CustomerTypeCantchange");
					errors.add(error);
				}				
			}				
		}
		String billingNeededParameter = session.getBillingCode(); 
		if (CustomerVO.CUSTOMER_TYPE_CU.equals(form.getCustomerType())
				|| (!CustomerVO.CUSTOMER_TYPE_AG.equals(form.getCustomerType()) &&
						!CustomerVO.CUSTOMER_TYPE_CC.equals(form.getCustomerType()))){   
						
			if(customerVO.getCustomerType()!= null){ 	
				
				if(CustomerVO.CUSTOMER_TYPE_AG.equals(customerVO.getCustomerType())){	
					for(AgentVO agentVO : customerVO.getAgentVOs()){				
						agentVO.setOperationFlag(AgentVO.OPERATION_FLAG_DELETE);
				   }					
				}	
				if(CustomerVO.CUSTOMER_TYPE_CC.equals(customerVO.getCustomerType())){
					if(customerVO.getCcCollectorVOs() != null && customerVO.getCcCollectorVOs().size()>0){
					for(CCCollectorVO ccCollectorVO : customerVO.getCcCollectorVOs()){				
						ccCollectorVO.setOperationFlag(CCCollectorVO.OPERATION_FLAG_DELETE);
				   }
				}					   
			}
		}
		}
		if (CustomerVO.CUSTOMER_TYPE_AG.equals(form.getCustomerType())){ 	
			//customerVO.setCustomerType("AG");
			Collection<AgentVO> agentVOs = new ArrayList<AgentVO>();		 
			
			AgentVO agentVo = populateAgentDetails(form,companyCode,customerVO); 
			
			if(form.getToDate()!= null && form.getToDate().trim().length()> 0) {
		   		LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
		   		toDate.setDate(form.getToDate());
		   		agentVo.setValidTo(toDate);
		   	}			
			if(form.getFromDate()!= null &&	form.getFromDate().trim().length()> 0) {
		   		LocalDate frmDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
		   		frmDate.setDate(form.getFromDate());
		   		agentVo.setValidFrom(frmDate);
		   	}			
			agentVOs.add(agentVo);
			customerVO.setAgentVOs(agentVOs);			
			
		}
		
		if (CustomerVO.CUSTOMER_TYPE_CC.equals(form.getCustomerType())){  
			
			//customerVO.setCustomerType("CC");
			Collection<CCCollectorVO> cCCollectorVOs = new ArrayList<CCCollectorVO>();
			CCCollectorVO cCCollectorVO = populateCCCollectorDetails(form,companyCode,customerVO);			
			cCCollectorVO.setLastUpdatedUser(logonAttributes.getUserId());
			
			// Used for CCCollector find method [findCCCollectorForAirport(CCCollectorVO cCCollectorVO)]
			/**Commented by A-4772 for ICRD-34645 starts here 
			 * as AirportCode is already in populateCCCollectorDetails method
			 * from form
			 * 
			 */
			
			//cCCollectorVO.setAirportCode(logonAttributes.getAirportCode());				
			if(form.getToDate()!= null && form.getToDate().trim().length()> 0) {
		   		LocalDate toDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
		   		toDate.setDate(form.getToDate());
		   		cCCollectorVO.setValidTo(toDate);
		   	}
			
			if(form.getFromDate()!= null &&	form.getFromDate().trim().length()> 0) {
		   		LocalDate frmDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
		   		frmDate.setDate(form.getFromDate());
		   		cCCollectorVO.setValidFrom(frmDate);
		   	}
			
			poulateAircraftTypeHandledForForm(form,session);
			cCCollectorVOs.add(cCCollectorVO);			
			customerVO.setCcCollectorVOs(cCCollectorVOs);	
			
		}
		if("ML".equals(form.getCustomerType())) {
			customerVO.setHoldingCompany(form.getHoldingCompany());
		}
		// Added BY A-8374 For ICRD-247693
		if (form.isClearingAgentFlag()) {
			customerVO.setClearingAgentFlag(true);
		} else {
			customerVO.setClearingAgentFlag(false);
		}
		// Added BY A-5183 For ICRD-18283 Ends
		if(form.getInternalAccountHolder()!=null && form.getInternalAccountHolder().trim().length()>0){
		 
			customerVO.setInternalAccountHolder(form.getInternalAccountHolder().trim());
		}else{
			customerVO.setInternalAccountHolder(null);
		}
		//Added by A-5163 for the ICRD-78230 starts
		if (form.getGibCustomerFlag()!=null && ("on".equals(form.getGibCustomerFlag()))) {
			customerVO.setGibCustomerFlag("Y");
		} else {
			customerVO.setGibCustomerFlag("N");
		}
		if (form.getPublicSectorFlag()!=null && ("on".equals(form.getPublicSectorFlag()))) {
			customerVO.setPublicSectorFlag("Y");
		} else {
			customerVO.setPublicSectorFlag("N");
		}
		if(form.getGibRegistrationDate() != null && form.getGibRegistrationDate().trim().length() > 0){
			LocalDate gibregistrationDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			customerVO.setGibRegistrationDate(gibregistrationDate.setDate(form.getGibRegistrationDate()));
		}else{
			customerVO.setGibRegistrationDate(null);
		}	
		//Added by A-5163 for the ICRD-78230 ends
		if("true".equals(form.getExcludeRounding())){//Added for ICRD-57704 BY A-5117
			customerVO.setCanExcludeRounding(true);
		}else{
			customerVO.setCanExcludeRounding(false);
		}
		customerVO.setAddress1(form.getAddress1());
		customerVO.setAddress2(form.getAddress2());
		customerVO.setArea(form.getArea());//toUpperCase removed as part of ICRD-314270 by A-7364
			//customerVO.setZone(form.getZone().toUpperCase());
		customerVO.setState(form.getState());//toUpperCase removed as part of ICRD-314270 by A-7364
		customerVO.setCity(form.getCity());//toUpperCase removed as part of ICRD-314270 by A-7364
		customerVO.setCompanyCode(logonAttributes.getCompanyCode());
		customerVO.setLastUpdatedUser(logonAttributes.getUserId());
		customerVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));//A-8370 for ICRD-347284
		customerVO.setCountry(form.getCountry().toUpperCase());
		customerVO.setCustomerCode(form.getCustomerCode().toUpperCase());
		//Added for bug 103842 by A-3767 on 06Jan11
		customerVO.setCustomerGroup(form.getCustomerGroup().toUpperCase());
		//Changed by a-3045 for bug 63025 on 16Sep09
		customerVO.setCustomerName(form.getCustName().toUpperCase());
		customerVO.setEmail(form.getEmail());
		customerVO.setFax(form.getFax());
		customerVO.setMobile(form.getMobile());
		customerVO.setZipCode(form.getZipCode().toUpperCase());
		customerVO.setTelephone(form.getTelephone());
		customerVO.setSita(form.getSita());
		customerVO.setStationCode(form.getStation().toUpperCase());
		customerVO.setStatus(form.getStatus());
		customerVO.setDefaultNotifyMode(form.getDefaultNotifyMode());
		customerVO.setAirlineIdentifier(getApplicationSession().getLogonVO()
				.getOwnAirlineIdentifier());
		customerVO.setEoriNo(form.getEoriNo());
		//added by a-3045 for CR HA94 on 27Mar09 starts
		customerVO.setKnownShipper(form.getKnownShipper());
		customerVO.setAccountNumber(form.getAccountNumber());
		customerVO.setScc(form.getScc());
		customerVO.setCustomerShortCode(form.getCustomerShortCode());//Added by A-5214 as part from the ICRD-21666
		//Added by A-5807 for ICRD-73246
		String[] restrictedFOPs=form.getRestrictedFOPs(); 
		customerVO.setRestrictedFOPs(restrictedFOPs[0]);
		//CRQ ID:117235 - A-5127 added
		customerVO.setRecipientCode(form.getRecipientCode());
		customerVO.setCassImportIdentifier(form.getCassImportIdentifier());
		//End - CRQ ID:117235 - A-5127 added
		// Added by A-3207 for ICRD-95548 starts
		CustomerFilterVO customerfiltervo = new CustomerFilterVO();
		CustomerVO customerValidationVO = null;
		customerfiltervo.setCustomerCode(form.getIataCode().toUpperCase());
		customerfiltervo.setCompanyCode(logonAttributes.getCompanyCode());	
		
		try {
			customerValidationVO = new CustomerDelegate().validateCustomer(customerfiltervo);
		} catch (BusinessDelegateException businessDelegateException) {				
			errors.add(new ErrorVO("customermanagement.defaults.msg.err.invalidControllingLocation"));
		}
		/**
		 * Change  by A-5222 for bug ICRD-100597 on 20FEB2015
		 * ClName and ControllingAgentName picking from customerValidationVO
		 * */
		if(customerValidationVO!=null){
			form.setClName(customerValidationVO.getCustomerName());
			customerVO.setControllingAgentName(customerValidationVO.getCustomerName());
		}
		// Added by A-3207 for ICRD-95548 ends
		//commented by A-9563 for ICRD-353652
		String invoiceSendinMode="";
		/*
		 * for(String s :form.getInvoiceType()){ if(!"".equals(invoiceSendinMode)){
		 * invoiceSendinMode =invoiceSendinMode.concat(","); }
		 * 
		 * invoiceSendinMode = invoiceSendinMode.concat(s);
		 * 
		 * }
		 */
		invoiceSendinMode=form.getInvoiceType(); //A-9563
		
		//added by a-6162 ICRD - 38408
		customerVO.setInvoiceSendingMode(invoiceSendinMode);
		//Added by A-5165 for ICRD-64121 
		//Modified by a-6314 as part of ICRD-106395 starts
		//Modified by A-9563 for ICRD-353652
		String str=form.getInvoiceType();
		if(str !=null && str.contains("E")){
			if(form.getBillingEmail().length()== 0 && BLANK.equals(form.getBillingEmail())){
				error = new ErrorVO("customermanagement.defaults.msg.err.configurebillingemail");
				errors.add(error);
			}
		}
		//Modified by A-9563 for ICRD-353652
		//Modified by a-6314 as part of ICRD-106395 ends
		
		//if("Y".equals(form.getRestrictionFlag())){ Commented as part of ICRD-208909
			Collection<CustomerMiscDetailsVO> cusmiscvos = new ArrayList<CustomerMiscDetailsVO>();
			cusmiscvos=populateCustomerMiscDetailsVO(cusmiscvos,form,customerVO,session);
			customerVO.setCustomerMiscValDetailsVO(cusmiscvos);	
		//}
		
		customerVO.setPoa("on".equals(form.getPoa())?"Y":"N");
		
		if(form.getEstablishedDate() != null && form.getEstablishedDate().trim().length() > 0){
			LocalDate estDate =
				new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			customerVO.setEstablishedDate(estDate.setDate(
					form.getEstablishedDate()));
		}else{
			customerVO.setEstablishedDate(null);
		}
		customerVO.setRemarks(form.getRemarks());
		//added by A-3278 for JetBlue31-1 on 23Apr10
		
		//Modified by A-5237 for ICRD-82767
		if(form.getBillingPeriod()!=null && !(BLANK.equals(form.getBillingPeriod()))){
			Map<String,Collection<OneTimeVO>> hashMap =session.getOneTimeValues();
			Collection<OneTimeVO>billingPeriodOneTime=hashMap.get("cra.defaults.billingperiod");
			customerVO.setBillingPeriod(form.getBillingPeriod());
			if(billingPeriodOneTime!=null && !billingPeriodOneTime.isEmpty()){
				for (OneTimeVO oneTimeVo:billingPeriodOneTime){
					if(form.getBillingPeriod().equals(oneTimeVo.getFieldValue())){
						customerVO.setBillingPeriodDescription(oneTimeVo.getFieldDescription());
						break;
					}
				}
			}
		}
		else{
			customerVO.setBillingPeriod(null);
		}
		//Added by A-5165	for ICRD-35135
		if(!(BLANK.equals(form.getBillingDueDays()))){
			customerVO.setBillingDueDays(Integer.parseInt(form.getBillingDueDays()));
		}
		else{
			customerVO.setBillingDueDays(0);
		}
		//Added by A-5165	for ICRD-35135 ends
		//Added by A-7555 for ICRD-319714 starts
		if(!(BLANK.equals(form.getDueDateBasis()))){
			customerVO.setDueDateBasis(form.getDueDateBasis());
		}else{
			customerVO.setDueDateBasis(null);
		}
		//JB31-1 ends
		//Added by A-5169 for ICRD-31552 on 25-APR-2013 starts
		if(form.getCustomsLocationNo()!= null){
		CustomerMiscellaneousVO customermiscellaneousvo = new CustomerMiscellaneousVO();
		customermiscellaneousvo.setCompanyCode(companyCode);
		customermiscellaneousvo.setCustomerCode(form.getCustomerCode());
		customermiscellaneousvo.setCustomsLocationNumber(form.getCustomsLocationNo());
		//Added for ICRD-234812
		customermiscellaneousvo.setLavNumber(form.getAccountNumber());
		customerVO.setCustomerMiscDetails(customermiscellaneousvo);
		}
		//Added by A-5169 for ICRD-31552 on 25-APR-2013 ends
			
		CustomerBillingDetailsVO customerBillingDetailsVO = new CustomerBillingDetailsVO();
		customerBillingDetailsVO.setCityCode(form.getBillingCityCode());
		customerBillingDetailsVO.setCountry(form.getBillingCountry());
		customerBillingDetailsVO.setEmail(form.getBillingEmail());
		//Added by A-7905 as part of ICRD-228463 starts
		customerBillingDetailsVO.setEmailOne(form.getBillingEmailOne());
		customerBillingDetailsVO.setEmailTwo(form.getBillingEmailTwo());
		//Added by A-7905 as part of ICRD-228463 ends
		customerBillingDetailsVO.setFax(form.getBillingFax());
		customerBillingDetailsVO.setLocation(form.getBillingLocation());
		customerBillingDetailsVO.setState(form.getBillingState());
		customerBillingDetailsVO.setStreet(form.getBillingStreet());
		customerBillingDetailsVO.setTelephone(form.getBillingTelephone());
		customerBillingDetailsVO.setZipcode(form.getBillingZipcode());
		customerVO.setCustomerBillingDetailsVO(customerBillingDetailsVO);
		if(form.getCustomerPOAValidity()!= null && form.getCustomerPOAValidity().trim().length() > 0){
			LocalDate validDate =
				new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
			customerVO.setCustomerPOAValidity(validDate.setDate(
					form.getCustomerPOAValidity()));
		}else{
			customerVO.setCustomerPOAValidity(null);
		}
		//Removing CCSf details from customer as a part of ICRD-251782
		//String[] ccsfNumber = form.getCcsfNumber();
		///String[] ccsfCityName = form.getCcsfCityName();
		//String[] ccsfExpiryDate = form.getCcsfExpiryDate();
		//String[] hiddenOpFlagForCCSF = form.getHiddenOpFlagForCCSF();
		Collection<ScreeningFacilityVO> screeningFacilityVOs = new ArrayList<ScreeningFacilityVO>();
		//added by a-3045 for CR HA16 on 02Jun09 starts
		
		//Added for ICRD-33334 by A-5163 Starts
		customerVO.setInvoiceClubbingIndicator(CHECKSTATUS.equals(form.getInvoiceClubbingIndicator())?"Y":"N");
		//Added for ICRD-33334 by A-5163 Ends
		
		  /** CR TIACT 492 starts ***/
		
	customerVO.setStopCredit(form.getStopCredit());
	customerVO.setInvoiceToCustomer(form.getInvoiceToCustomer());
	//Added by A-5493 for ICRD-17199 to set the vendor flag
	customerVO.setVendorFlag(form.getVendorFlag());
	//Added by A-5493 for ICRD-17199 ends
	customerVO.setNaccsDeclarationCode(form.getNaccsDeclarationCode());
	customerVO.setNaccsAircargoAgentCode(form.getNaccsAircargoAgentCode());
	
	customerVO.setBranchName(form.getBranchName());
	customerVO.setBillingPeriodDescription(form.getBillingPeriodDescription());
		customerVO.setEmail2(form.getEmail2());
		customerVO.setCustomerCompanyCode(form.getCustomerCompanyCode());
		customerVO.setBranch(form.getBranch());
		customerVO.setBillingCode(form.getBillingCode());
		customerVO.setNaacsbbAgentCode(form.getNaacsbbAgentCode());
		customerVO.setNaccsInvoiceCode(form.getNaccsInvoiceCode());
		customerVO.setBillPeriod(form.getBillPeriod());
		/*Added by 201930 for IASCB-131790 start*/
		if (form.getCassCountry() != null && form.getCassCountry().trim().length() > 0) {
			customerVO.setCassCountryCode(form.getCassCountry());
		}
		else {
			customerVO.setCassCountryCode(null);
		}
		/*Added by 201930 for IASCB-131790 end*/
		if("on" .equals(form.getSpotForwarder()) ){
			customerVO.setSpotForwarder("Y");
	    }else{
	    	customerVO.setSpotForwarder("N");
	    }
		customerVO.setDefaultHawbLength(form.getDefaultHawbLength());
		customerVO.setBillingPeriod(form.getBillingPeriod());
		
		if("on" .equals(form.getHandledCustomerImport())){
			customerVO.setHandledCustomerImport("Y");
	    }else{
	    	customerVO.setHandledCustomerImport("N");
	    }
		
		if("on" .equals( form.getHandledCustomerExport())){
			customerVO.setHandledCustomerExport("Y");
		}else{
			customerVO.setHandledCustomerExport("N");
		}
		if("on" .equals(form.getHandledCustomerForwarder())){
			customerVO.setHandledCustomerForwarder("Y");
		}else{
			customerVO.setHandledCustomerForwarder("N");
		}
		
		customerVO.setForwarderType(form.getForwarderType());
		
		if("on" .equals(form.getConsolidator())){
			customerVO.setConsolidator("Y");
		}else{
			customerVO.setConsolidator("N");
		}
		/** CR TIACT 492-I starts ***/
		
		/** Code added for ICRD-58628 by A-5163 starts **/
		String[] hiddenOpFlagsForCertificates = form.getHiddenOpFlagForCertificate();
		if (hiddenOpFlagsForCertificates != null && hiddenOpFlagsForCertificates.length > 0) {
			Collection<CustomerCertificateVO> customerCertificateDetails = new ArrayList<CustomerCertificateVO>();
			String[] customerCertificateTypes 	= form.getCustomerCertificateType();
			String[] customerCertificateNumbers = form.getCustomerCertificateNumber();
			String[] certificateValidFromDates 	= form.getCertificateValidFrom();
			String[] certificateValidToDates 	= form.getCertificateValidTo();
			String[] certificateSequenceNumbers	= form.getCertificateSequenceNumber();
			for (int i = 0; i < hiddenOpFlagsForCertificates.length; i++) {
				if (!NO_OPERATION.equals(hiddenOpFlagsForCertificates[i])) {
					CustomerCertificateVO certificateVO = new CustomerCertificateVO();
					certificateVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
					certificateVO.setCustomerCode(form.getCustomerCode().toUpperCase());
					if (certificateSequenceNumbers != null && certificateSequenceNumbers.length - 1 >= i) {
						certificateVO.setSequenceNumber(Integer.parseInt(certificateSequenceNumbers[i]));
					}
					certificateVO.setCertificateType(customerCertificateTypes[i]);
					certificateVO.setCertificateNumber(customerCertificateNumbers[i]);
					certificateVO.setOperationFlag(hiddenOpFlagsForCertificates[i]);
					if (certificateValidFromDates[i] != null && !certificateValidFromDates[i].isEmpty()) {
						LocalDate validFrom = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
						validFrom.setDate(certificateValidFromDates[i]);
						certificateVO.setValidityStartDate(validFrom);
					}
					if (certificateValidToDates[i] != null && !certificateValidToDates[i].isEmpty()) {
						LocalDate validTo = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
						validTo.setDate(certificateValidToDates[i]);
						certificateVO.setValidityEndDate(validTo);
					}
					customerCertificateDetails.add(certificateVO);
				}
			}
			customerVO.setCustomerCertificateDetails(customerCertificateDetails);
		}
		/** Code added for ICRD-58628 by A-5163 ends **/
		
		/*** code for customercontact starts ***/
		Collection<CustomerContactVO> keyContactDetailVOs = new ArrayList<CustomerContactVO>();
		String[] contactCode = form.getContactCode();
		String[] name = form.getContactName();
		String[] designation = form.getContactDesignation();
		String[] telephone = form.getContactTelephone();
		String[] mobile = form.getContactMobile();
		String[] fax = form.getContactFax();
		String[] sita = form.getContactSita();
		String[] email = form.getContactEmail();
		String[] remarks = form.getContactRemarks();
		String[] status = form.getCheckedStatus().split(",");
		String[] primaryContactStatus = form.getPrimaryContact().split(",");
		String[] hiddenFlagsForCustomer = form.getHiddenOpFlagForCustomer();
		
		
		/*** code for agent starts ***/
		String[] agentcode = form.getAgentCode();
		String[] agentName = form.getAgentName();
		String[] agentStation = form.getAgentStation();
		String[] agentRemarks = form.getAgentRemarks();
		String[] checkedExport = form.getCheckedExport().split(",");
		String[] checkedImport = form.getCheckedImport().split(",");
		String[] checkedSales = form.getCheckedSales().split(",");
		String[] hiddenFlagsForAgent = form.getHiddenOpFlagForAgent();
		String[] agentScc = form.getAgentScc();
		String[] agentCarrier = form.getAgentCarrier(); // Added by A-8823 for IASCB-4841
		//ADDED BY A-8531 FOR IASCB-11603
		String[] agentType=form.getAgentType();
		String[]origin=form.getOrigin();
		String[]agnetValidFrom=form.getAgnetValidFrom();
		String[]agnetValidTo=form.getAgnetValidTo();
		
		
		//ADDED BY A-8531 FOR IASCB-11603 ends		
		
		//Added by A-5220 for ICRD-55852
		String[] contactTypes = form.getContactTypes();
		int size = hiddenFlagsForCustomer.length;
		
		for(int x=0;x<hiddenFlagsForAgent.length;x++){
			log.log(Log.FINE, "\n.........agent++++", hiddenFlagsForAgent, x);
		}
		for(int x=0;x<hiddenFlagsForCustomer.length;x++){
			log.log(Log.FINE, "\n.........cust++++", hiddenFlagsForCustomer, x);
		}
		
		//Added by A-7137 as part of CR ICRD-152555 starts
		String preferenceCodes[] = form.getPreferenceCode();
		String preferenceValues[] = form.getPreferenceValue();
		//Added by A-7137 as part of CR ICRD-152555 ends
		
		String customerCode = BLANK;
		
		//insertion starts
		//customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
		
		if(CustomerVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(customerVO.getOperationFlag())) {
			//added by a-3045 for CR HA16 on 02Jun09 starts
			//for ccsf details starts
			IACDetailsVO iacDetailsVO = new IACDetailsVO();
			iacDetailsVO.setIacNumber(form.getIacNumber());
			iacDetailsVO.setApiacsspNumber(form.getApiacsspNumber());
			if(form.getIacExpiryDate() != null && form.getIacExpiryDate().trim().length() > 0){
				LocalDate iacExpDate =
					new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				iacDetailsVO.setIacExpiryDate(iacExpDate.setDate(form.getIacExpiryDate()));
			}else{
				iacDetailsVO.setIacExpiryDate(null);
			}
			if(form.getApiacsspExpiryDate() != null && form.getApiacsspExpiryDate().trim().length() > 0){
				LocalDate apExpDate =
					new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				iacDetailsVO.setApiacsspExpiryDate(apExpDate.setDate(form.getApiacsspExpiryDate()));
			}else{
				iacDetailsVO.setApiacsspExpiryDate(null);
			}
			customerVO.setIacDetailsVO(iacDetailsVO);
			/*for(int i=0;i<(hiddenOpFlagForCCSF.length - 1);i++){
				if(ScreeningFacilityVO.OPERATION_FLAG_INSERT.equals(hiddenOpFlagForCCSF[i])){
					ScreeningFacilityVO screeningFacilityVO = new ScreeningFacilityVO();
					screeningFacilityVO.setOperationFlag(OPERATION_FLAG_INSERT);
					screeningFacilityVO.setCompanyCode(getApplicationSession().getLogonVO()
							.getCompanyCode());
					screeningFacilityVO.setAuthNumber(ccsfNumber[i]);
					screeningFacilityVO.setFacilityCityName(ccsfCityName[i]);
					if(ccsfExpiryDate[i] != null && ccsfExpiryDate[i].trim().length() > 0){
						LocalDate ccsfExpDate =
							new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
						screeningFacilityVO.setExpiryDate(ccsfExpDate.setDate(ccsfExpiryDate[i]));
					}else{
						screeningFacilityVO.setExpiryDate(null);
					}
					screeningFacilityVOs.add(screeningFacilityVO);
				}
			}*/
			iacDetailsVO.setScreeningFacilityVOs(screeningFacilityVOs);
			customerVO.setIacDetailsVO(iacDetailsVO);
			//added by a-3045 for CR HA16 on 02Jun09 ends		
			//for customer reg starts


			int index = 0;	
			if(("EBK".equals(contactTypes[index]) || "QAE".equals(contactTypes[index]) || "EFRT".equals(contactTypes[index]) || "BTP".equals(contactTypes[index])) &&
					(CustomerContactVO.OPERATION_FLAG_INSERT.equals(hiddenFlagsForCustomer[index])) &&
					(session.getCustomerVO()!= null && (session.getCustomerVO().getCustomerContactVOs() != null &&
							!session.getCustomerVO().getCustomerContactVOs().isEmpty()))){
					
				for(CustomerContactVO customerContactVO:session.getCustomerVO().getCustomerContactVOs()){
					CustomerContactVO newContactVO = new CustomerContactVO();
					newContactVO.setOperationFlag(OPERATION_FLAG_INSERT);
					newContactVO.setCompanyCode(getApplicationSession().getLogonVO()
							.getCompanyCode());
					if ("A".equals(status[index])) {
						newContactVO.setActiveStatus("A");
					} else {
						newContactVO.setActiveStatus("I");
					}
					if ("Y".equals(primaryContactStatus[index])) {
						newContactVO.setPrimaryUserFlag("Y");
					} else {
						newContactVO.setPrimaryUserFlag("N");
					}
					newContactVO.setCustomerName(name[index]);
					newContactVO.setContactCustomerCode(contactCode[index]);
					newContactVO.setCustomerDesignation(designation[index]);
					newContactVO.setEmailAddress(email[index]);
					newContactVO.setFax(fax[index]);
					newContactVO.setMobile(mobile[index]);
					newContactVO.setRemarks(remarks[index]);
					newContactVO.setTelephone(telephone[index]);
					newContactVO.setSiteAddress(sita[index]);
					//Added by A-5220 for ICRD-55852
					newContactVO.setContactType(contactTypes[index]);
					newContactVO.setNotificationFormat(customerContactVO.getNotificationFormat());
					newContactVO.setNotificationLanguageCode(customerContactVO.getNotificationLanguageCode());
					if("EFRT".equals(contactTypes[index]) ||"BTP".equals(contactTypes[index])) {
						newContactVO.setAdditionalContacts(customerContactVO.getAdditionalContacts());
					}
					index++;
					keyContactDetailVOs.add(newContactVO);
				}
			}else{
				for(int i=0;i<size;i++){
					if(CustomerContactVO.OPERATION_FLAG_INSERT.equals(hiddenFlagsForCustomer[i])){
						CustomerContactVO newContactVO = new CustomerContactVO();
						newContactVO.setOperationFlag(OPERATION_FLAG_INSERT);
						newContactVO.setCompanyCode(getApplicationSession().getLogonVO()
								.getCompanyCode());
						if ("A".equals(status[i])) {
							newContactVO.setActiveStatus("A");
						} else {
							newContactVO.setActiveStatus("I");
						}
						if ("Y".equals(primaryContactStatus[i])) {
							newContactVO.setPrimaryUserFlag("Y");
						} else {
							newContactVO.setPrimaryUserFlag("N");
						}
						newContactVO.setCustomerName(name[i]);
						newContactVO.setContactCustomerCode(contactCode[i]);
						newContactVO.setCustomerDesignation(designation[i]);
						newContactVO.setEmailAddress(email[i]);
						newContactVO.setFax(fax[i]);
						newContactVO.setMobile(mobile[i]);
						newContactVO.setRemarks(remarks[i]);
						newContactVO.setTelephone(telephone[i]);
						newContactVO.setSiteAddress(sita[i]);
						//Added by A-5220 for ICRD-55852
						newContactVO.setContactType(contactTypes[i]);

						keyContactDetailVOs.add(newContactVO);

					}
				}

			}
			
			customerVO.setCustomerContactVOs(keyContactDetailVOs);
			
			log
					.log(Log.FINE, "................??.", keyContactDetailVOs.size());
			log.log(Log.FINE, "................???.", customerVO);
			//for agent reg starts
			Collection<CustomerAgentVO> agentVOsForInsert = new ArrayList<CustomerAgentVO>();
			for(int i=0;i<(hiddenFlagsForAgent.length)-1;i++){
				if(CustomerContactVO.OPERATION_FLAG_INSERT.equals(hiddenFlagsForAgent[i])){
					CustomerAgentVO newAgentVO = new CustomerAgentVO();
					newAgentVO.setOperationFlag(OPERATION_FLAG_INSERT);
					newAgentVO.setCompanyCode(getApplicationSession().getLogonVO()
							.getCompanyCode());
					newAgentVO.setStationCode(agentStation[i]);
					newAgentVO.setAgentCode(agentcode[i] );
					newAgentVO.setRemarks(agentRemarks[i]);
					newAgentVO.setScc(agentScc[i]);
					/** Mandaory Field **/
					//added by a-8531 for IASCB-11603 starts
					if(agentType!=null)
					{
					newAgentVO.setAgentType(agentType[i]);
					}
					newAgentVO.setOrigin(origin[i]);
					
					LocalDate validTo =null;
					LocalDate validFrom=null;
					if (agnetValidFrom[i] != null
							&& !agnetValidFrom[i].isEmpty()) {
						validFrom = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						validFrom.setDate(agnetValidFrom[i]);
						newAgentVO.setValidityStartDate(validFrom);
					}else{
						//Throw error
						errors.add(new ErrorVO("customermanagement.defaults.customerregistration.preferences.validdatemandatory"));
						//session.setCustomerVO(customerVO);
						form.setSourcePage("SAVE");
						session.setSourcePage("SAVE");
						session.setValidationErrors((ArrayList<ErrorVO>)errors);
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
					if (agnetValidTo[i] != null
							&& !agnetValidTo[i].isEmpty()) {
						validTo = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						validTo.setDate(agnetValidTo[i]);
						newAgentVO.setValidityEndDate(validTo);
					}else{
						//throw error :Shemeel
						errors.add(new ErrorVO("customermanagement.defaults.customerregistration.preferences.validdatemandatory"));
						//session.setCustomerVO(customerVO);
						form.setSourcePage("SAVE");
						session.setSourcePage("SAVE");
						session.setValidationErrors((ArrayList<ErrorVO>)errors);
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
					/** Mandaory Field **/
					if ("Y".equals(checkedExport[i])) {
						newAgentVO.setExportFlag("Y");
					} else {
						newAgentVO.setExportFlag("N");
					}
					if ("Y".equals(checkedImport[i])) {
						newAgentVO.setImportFlag("Y");
					} else {
						newAgentVO.setImportFlag("N");
					}
					if ("Y".equals(checkedSales[i])) {
						newAgentVO.setSalesFlag("Y");
					} else {
						newAgentVO.setSalesFlag("N");
					}
					newAgentVO.setCarrier(agentCarrier[i]); // Added by A-8823 for IASCB-4841
					
					agentVOsForInsert.add(newAgentVO);
				}
				
				customerVO.setCustomerAgentVOs(agentVOsForInsert);
			}
			
			if ("on".equals(form.getGlobalCustomer())) {
				customerVO.setGlobalCustomerFlag("Y");

			} else {
				customerVO.setGlobalCustomerFlag("N");
			}
			if (customerVO.getGlobalCustomerFlag() != null
					&& "Y".equals(customerVO.getGlobalCustomerFlag())) {
				form.setGlobalCustomer("on");
			}
			
			/**
			 * Deleting the server contents 
			 * 
			 */
			if(customerVO.getCustomerPreferences() != null && customerVO.getCustomerPreferences().size()>0){
				for(CustomerPreferenceVO customerPreferenceVO:customerVO.getCustomerPreferences()){
					customerPreferenceVO.setOperationFlag(CustomerPreferenceVO.OPERATION_FLAG_DELETE);
				}			
			}
			//Added by A-7137 as part of CR ICRD-152555 starts
			log.log(Log.FINE, "Creating Customer Preferences");
			CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();			
			if (preferenceCodes != null) {
				if (customerVO.getCustomerPreferences() == null) {
					customerVO.setCustomerPreferences(new ArrayList<>());
				}
				for (int i = 0; i < preferenceCodes.length; i++) {
				if(preferenceValues[i]!=null && preferenceValues[i].trim().length()>0){
					/**
					 * Added as part of IASCB-105699
					 * Validating the 'accounting supervisor' customer preference. If the value entered is not available
					 *  in the User maintenance screen then system shall throw an error.
					 */
					/**
					 * Added as part of ICRD-212854.
					 * Validating the 'accounting responsible' customer preference. If the value entered is not available
					 *  in the User maintenance screen then system shall throw an error.
					 */
					if(ACCOUNTING_RESP_CODE.equals(preferenceCodes[i]) || ACCOUNTING_SUPR_CODE.equals(preferenceCodes[i]) ){
						UserVO userVO = null;
						try {
							userVO = delegate.findUserDetails(companyCode,preferenceValues[i]);
						} catch (BusinessDelegateException e) {
							errors = handleDelegateException(e);
						}
						if(userVO == null || userVO.getUserCode() == null){
								if (ACCOUNTING_RESP_CODE.equals(preferenceCodes[i])) {
									errors.add(new ErrorVO(INVALID_ACCT_RESPONSIBLE));
								} else if (ACCOUNTING_SUPR_CODE.equals(preferenceCodes[i])) {
									errors.add(new ErrorVO(INVALID_ACCT_SUPERVISOR));
								}
							session.setCustomerVO(customerVO);
							form.setSourcePage("SAVE");
							session.setSourcePage("SAVE");
							session.setValidationErrors((ArrayList<ErrorVO>)errors);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;
							return;
						}
					}
					
				CustomerPreferenceVO customerPreferenceVO = new CustomerPreferenceVO();
				customerPreferenceVO.setCompanyCode(companyCode);
				customerPreferenceVO.setCustomerCode(customerVO.getCustomerCode());
				customerPreferenceVO.setPreferenceCode(preferenceCodes[i]);
				customerPreferenceVO.setPreferenceValue(preferenceValues[i]);
					customerPreferenceVO.setOperationFlag(CustomerPreferenceVO.OPERATION_FLAG_INSERT);
					customerPreferenceVO.setLastUpdatedUser(logonAttributes.getUserId());
					customerVO.getCustomerPreferences().add(customerPreferenceVO);

				}
			}
			
			
				}
			log.log(Log.FINE, "After Creating Customer Preferences");
			//Added by A-7137 as part of CR ICRD-152555 ends			
			
			Collection<CustomerAgentVO> agentVOs = customerVO.getCustomerAgentVOs();
			Collection<CustomerContactVO> contactVOs = customerVO.getCustomerContactVOs();
			log.log(Log.FINE, "CustomerContactVO------>", customerVO);
			log.log(Log.FINE, "CustomerContactVOss------>", customerVO.getCustomerContactVOs().size());
			Collection<CustomerContactVO> keyContactDetailVOsForCheck = customerVO
			.getCustomerContactVOs();
				
			log.log(Log.FINE,"validating contactVO");
			//Modified by a-6314 for ICRD-140348
			if (keyContactDetailVOsForCheck != null && keyContactDetailVOsForCheck.size() > 0) {
				errors .addAll(updateKeyContactDetails(
						(ArrayList<CustomerContactVO>) keyContactDetailVOsForCheck, form,customerVO));
				
				//validating contact so that min one should be added
				if(contactVOs != null && contactVOs.size()>0){
					int i=0;
					for(CustomerContactVO contactVO : contactVOs){
						if(!OPERATION_FLAG_DELETE.equals(contactVO .getOperationFlag())){
							i++;
						}
					}
					/*if(i==0){
						error = new ErrorVO(
						"customermanagement.defaults.msg.err.addatleastonekeycontact");
						errors.add(error);
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}*/
				}
				// Commented the primary user validation as part of ICRD-175746, Modified by A-7137
				//checking whether primary contact is selected
				/*if (contactVOs != null && contactVOs.size() == 1) {
					for (CustomerContactVO contactVO : contactVOs) {
						if (!"Y".equals(contactVO.getPrimaryUserFlag())) {
							error = new ErrorVO(
									"customermanagement.defaults.msg.err.selectprimarycontact");
							errors.add(error);
							log.log(Log.FINE, "\n\n here ****");
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;
							session.setSourcePage("SAVE");
							form.setSourcePage("SAVE");
							session.setValidationErrors((ArrayList<ErrorVO>)errors);
							return;

						}
					}

				}*/
				
		}/*else{
			error = new ErrorVO(
			"customermanagement.defaults.msg.err.addatleastonekeycontact");
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
		}*/
				
		   log.log(Log.FINE,"validating agentvO");	
			if(agentVOs!=null && agentVOs.size()>0){
				errors = validateAgentVOs(agentVOs,logonAttributes.getCompanyCode(),form);
			}
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			log.log(Log.FINE,"validating form");
				errors = validateForm(form, logonAttributes,billingNeededParameter);
				
			if (errors != null && errors.size() > 0) {
					//setting to session to display VO entered by the end user
					session.setCustomerVO(customerVO);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					form.setSourcePage("SAVE");
					session.setSourcePage("SAVE");
					session.setValidationErrors((ArrayList<ErrorVO>)errors);
					return;
			}
			/*
			 * Modified by A-5165 for ICRD-64121
			 */
			// Added by A-5198 for ICRD-63059
			errors = validateCustomerMiscDetailsVos( initialCustomerMiscValues, customerVO,form);
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				session.setSourcePage("SAVE");
				form.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				return;
			}
			log.log(Log.FINE,"validating area fields");
				errors = validateAreaFields(form, customerVO);
				
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				customerVO.setOperationFlag(OPERATION_FLAG_INSERT);
				session.setCustomerVO(customerVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				return;
			}
			
			if (agentVOs != null) {
				for (CustomerAgentVO agentVO : agentVOs) {
					agentVO.setCustomerCode(customerVO.getCustomerCode()
							.toUpperCase());
	
				}
			}
			if(form.getScc() != null && form.getScc().trim().length()>0){
				 errors=validateSccCodes(companyCode,form.getScc());
					if(errors!=null && errors.size() > 0) {
				       	invocationContext.addAllError(errors);

					}
			}
			// Commeted as part of ICRD-3462 by A-3767 on 02Aug11
			/*if (contactVOs != null) { 
				for (CustomerContactVO contactVO : contactVOs) {
					contactVO.setCustomerCode(customerVO.getCustomerCode()
							.toUpperCase());
					contactVO.setSiteAddress(form.getBillingStreet());
					contactVO.setCompanyCode(logonAttributes.getCompanyCode());
					contactVO.setContactCustomerCode(form.getCustomerCode());
					contactVO.setEmailAddress(form.getBillingEmail());
					contactVO.setFax(form.getBillingFax());
					contactVO.setTelephone(form.getBillingTelephone());
					//contactVO.setCustomerDesignation(form.getContactDesignation());
					contactVO.setRemarks(form.getRemarks());
					contactVO.setSiteAddress(form.getBillingStreet());
					contactVO.setCustomerName(form.getCustName());
					
				}	
				
			}*/
			//added by a-3045 for bug 62984 on 15Sep09 starts
			//errors = validateDuplicateTSANumbers(customerVO);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE,"errors exists in validateDuplicateTSANumbers");
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors(new ArrayList<ErrorVO>(errors));
				return;
			}
			//added by a-3045 for bug 62984 on 15Sep09 ends
			/**
			 * Method call commented by A-5163 ends
			 */
			
			/** Code added for ICRD-58628 by A-5163 starts **/
			if (customerVO.getCustomerCertificateDetails() != null
					&& !customerVO.getCustomerCertificateDetails().isEmpty()) {
				errors = validateCustomerCertificates(customerVO.getCustomerCertificateDetails(), logonAttributes.getCompanyCode(), form);
			}
			if (errors != null && errors.size() > 0) {
				session.setCustomerVO(customerVO);
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			/** Code added for ICRD-58628 by A-5163 ends **/
			
			//validation ends
			customerVO.setCustomerType(form.getCustomerType());
			
			
			log.log(Log.FINE, "//before reg", customerVO);
			Collection<LockVO> locks = prepareLockForSave();
			try {
				customerCode = delegate.registerCustomer(customerVO,locks);
			} catch (BusinessDelegateException ex) {
//printStackTrrace()();
				errors = handleDelegateException(ex);
			}
			log.log(Log.FINE, "After Cust Reg,,..");
			
		}else{
			log.log(Log.FINE,"\n inside update loop from session");
		if(session.getCustomerVO()!=null){
			log.log(Log.FINE, "\n inside update loop from session", session.getCustomerVO());
			customerVO = session.getCustomerVO();
		}
		Collection<CustomerContactVO> keyContactDetailVOsForUpdate = new ArrayList<CustomerContactVO>();
		Collection<CustomerContactVO> keyContactDetailVOsFromSession = customerVO
		.getCustomerContactVOs();	 
		
				
			customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
			//customer starts
			//customer rows updating 
			
		if(keyContactDetailVOsFromSession!=null && keyContactDetailVOsFromSession.size()>0){
				log.log(Log.FINE,"Updating customer details");
				int count =0;
				for(CustomerContactVO vo:keyContactDetailVOsFromSession){
					if(CustomerContactVO.OPERATION_FLAG_UPDATE.equals(hiddenFlagsForCustomer[count])
							||CustomerContactVO.OPERATION_FLAG_INSERT.equals(hiddenFlagsForCustomer[count])){
						if(CustomerContactVO.OPERATION_FLAG_INSERT.equals(hiddenFlagsForCustomer[count])){
							vo.setOperationFlag(CustomerContactVO.OPERATION_FLAG_INSERT);
							hiddenFlagsForCustomer[count] = CustomerContactVO.OPERATION_FLAG_UPDATE;
						}else{
							vo.setOperationFlag(CustomerContactVO.OPERATION_FLAG_UPDATE);
						}						
						vo.setCompanyCode(getApplicationSession().getLogonVO()
								.getCompanyCode());
						vo.setCustomerName(name[count]);
						vo.setContactCustomerCode(contactCode[count]);
						vo.setCustomerDesignation(designation[count]);
						vo.setEmailAddress(email[count]);
						vo.setFax(fax[count]);
						vo.setMobile(mobile[count]);
						vo.setRemarks(remarks[count]);
						vo.setTelephone(telephone[count]);
						vo.setSiteAddress(sita[count]);
						if ("A".equals(status[count])) {
							vo.setActiveStatus("A");
						} else {
							vo.setActiveStatus("I");
						}
						if ("Y".equals(primaryContactStatus[count])) {
							vo.setPrimaryUserFlag("Y");
						} else {
							vo.setPrimaryUserFlag("N");
						}
						//Added by A-5220  
						vo.setContactType(contactTypes[count]);
						/* Added by A-7137
						 * This check is to avoid insertion of unwanted Notification Prefernces and Additional Contacts of the customer 
						contact after changing the contact type other than ETR or ETRTOA*/
						if(contactTypes[count]!=null && (!"ETR".equals(contactTypes[count])&&
								!"ETRTOA".equals(contactTypes[count]) 
								&& !"EBK".equals(contactTypes[count]) && !"QAE".equals(contactTypes[count]) 
								&& !"EFRT".equals(contactTypes[count])
								&& !"BTP".equals(contactTypes[count]))){//ICRD-162691 - updated condition
							vo.setNotificationPreferences(null);
							vo.setAdditionalContacts(null);
							vo.setNotificationLanguageCode(null);
						}
						keyContactDetailVOsForUpdate.add(vo);
					}else if(CustomerContactVO.OPERATION_FLAG_DELETE.equals(hiddenFlagsForCustomer[count])){
						vo.setOperationFlag(CustomerContactVO.OPERATION_FLAG_DELETE);
						vo.setCompanyCode(getApplicationSession().getLogonVO()
								.getCompanyCode());
						vo.setCustomerName(name[count]);
						vo.setContactCustomerCode(contactCode[count]);
						vo.setCustomerDesignation(designation[count]);
						vo.setEmailAddress(email[count]);
						vo.setFax(fax[count]);
						vo.setMobile(mobile[count]);
						vo.setRemarks(remarks[count]);
						vo.setTelephone(telephone[count]);
						vo.setSiteAddress(sita[count]);
						if ("A".equals(status[count])) {
							vo.setActiveStatus("A");
						} else {
							vo.setActiveStatus("I");
						}
						if ("Y".equals(primaryContactStatus[count])) {
							vo.setPrimaryUserFlag("Y");
						} else {
							vo.setPrimaryUserFlag("N");
						}
						//Added by A-5220  
						vo.setContactType(contactTypes[count]);
						keyContactDetailVOsForUpdate.add(vo);
						
    				}else if("N".equals(hiddenFlagsForCustomer[count])) {
    					vo.setOperationFlag(CustomerContactVO.OPERATION_FLAG_UPDATE);
    					vo.setCompanyCode(getApplicationSession().getLogonVO()
								.getCompanyCode());
						vo.setCustomerName(name[count]);
						vo.setContactCustomerCode(contactCode[count]);
						vo.setCustomerDesignation(designation[count]);
						vo.setEmailAddress(email[count]);
						vo.setFax(fax[count]);
						vo.setMobile(mobile[count]);
						vo.setRemarks(remarks[count]);
						vo.setTelephone(telephone[count]);
						vo.setSiteAddress(sita[count]);
						if ("A".equals(status[count])) {
							vo.setActiveStatus("A");
						} else {
							vo.setActiveStatus("I");
						}
						if ("Y".equals(primaryContactStatus[count])) {
							vo.setPrimaryUserFlag("Y");
						} else {
							vo.setPrimaryUserFlag("N");
						}
						//Added by A-5220  
						vo.setContactType(contactTypes[count]);
    					keyContactDetailVOsForUpdate.add(vo);	   					
    				}
			count++;
		}
				
		}
			//customer rows updating ends
			//adding new rows for customer
				
			for(int i=0;i<size;i++){
				if(CustomerContactVO.OPERATION_FLAG_INSERT.equals(hiddenFlagsForCustomer[i])){
					log.log(Log.FINE, "inserting new rows for customer");
					CustomerContactVO newContactVO = new CustomerContactVO();
					newContactVO.setOperationFlag(OPERATION_FLAG_INSERT);
					newContactVO.setCompanyCode(getApplicationSession().getLogonVO()
							.getCompanyCode());
					if ("A".equals(status[i])) {
						newContactVO.setActiveStatus("A");
					} else {
						newContactVO.setActiveStatus("I");
					}
					if ("Y".equals(primaryContactStatus[i])) {
						newContactVO.setPrimaryUserFlag("Y");
					} else {
						newContactVO.setPrimaryUserFlag("N");
					}
					// newContactVO.setActiveStatus(BLANK);
					newContactVO.setCustomerName(name[i]);
					newContactVO.setContactCustomerCode(contactCode[i]);
					newContactVO.setCustomerDesignation(designation[i]);
					newContactVO.setEmailAddress(email[i]);
					newContactVO.setFax(fax[i]);
					newContactVO.setMobile(mobile[i]);
					newContactVO.setRemarks(remarks[i]);
					newContactVO.setTelephone(telephone[i]);
					newContactVO.setSiteAddress(sita[i]);
					//Added by A-5220 for ICRD-55852
					newContactVO.setContactType(contactTypes[i]);
					keyContactDetailVOsForUpdate.add(newContactVO);
					//customerVO.setCustomerContactVOs(keyContactDetailVOsForUpdate);
					
				}
			}
			
			//adding new rows for customer ends
			customerVO.setCustomerContactVOs(keyContactDetailVOsForUpdate);
			
			
			
			
			//customer ends
			
			//agent starts
			//updating values for agent
			Collection<CustomerAgentVO> agentVOsForUpdate = customerVO.getCustomerAgentVOs();
			Collection<CustomerAgentVO> newagentVOs =new ArrayList<CustomerAgentVO>();
			if(agentVOsForUpdate!=null && agentVOsForUpdate.size()>0){
				log.log(Log.FINE, "updating agent ");
				int count =0;
				for(CustomerAgentVO agentVO:agentVOsForUpdate){
					log.log(Log.FINE, "\n agnt op flag=", hiddenFlagsForAgent,
							count);
						if(CustomerAgentVO.OPERATION_FLAG_UPDATE.equals(hiddenFlagsForAgent[count])){
							agentVO.setCompanyCode(getApplicationSession().getLogonVO()
									.getCompanyCode());
							agentVO.setStationCode(agentStation[count]);
							agentVO.setAgentCode(agentcode[count] );
							agentVO.setRemarks(agentRemarks[count]);
							agentVO=agentcheckbox(form,agentVO,count);
							agentVO.setOperationFlag(CustomerAgentVO.OPERATION_FLAG_UPDATE);
							agentVO.setScc(agentScc[count]);
						agentVO.setCarrier(agentCarrier[count]); // Added by A-8823 for IASCB-4841
						if(agentType!=null)
						{
						agentVO.setAgentType(agentType[count]);
						}
						agentVO.setOrigin(origin[count]);
						
						LocalDate validTo =null;
						LocalDate validFrom=null;
						if (agnetValidFrom[count] != null
								&& !agnetValidFrom[count].isEmpty()) {
							validFrom = new LocalDate(LocalDate.NO_STATION,
									Location.NONE, false);
							validFrom.setDate(agnetValidFrom[count]);
							agentVO.setValidityStartDate(validFrom);
						}else{
								agentVO.setValidityStartDate(null);
						}
						if (agnetValidTo[count] != null
								&& !agnetValidTo[count].isEmpty()) {
							validTo = new LocalDate(LocalDate.NO_STATION,
									Location.NONE, false);
							validTo.setDate(agnetValidTo[count]);
							agentVO.setValidityEndDate(validTo);

						}else{
								agentVO.setValidityEndDate(null);
						}
						newagentVOs.add(agentVO);
						
						}else if(CustomerAgentVO.OPERATION_FLAG_DELETE.equals(hiddenFlagsForAgent[count])){
							agentVO.setCompanyCode(getApplicationSession().getLogonVO()
									.getCompanyCode());
							agentVO.setStationCode(agentStation[count]);
							agentVO.setAgentCode(agentcode[count] );
							agentVO.setRemarks(agentRemarks[count]);
							agentVO=agentcheckbox(form,agentVO,count);
							agentVO.setOperationFlag(CustomerAgentVO.OPERATION_FLAG_DELETE);
						agentVO.setCarrier(agentCarrier[count]); // Added by A-8823 for IASCB-4841
						if(agentType!=null)
						{
						agentVO.setAgentType(agentType[count]);
						}
						agentVO.setOrigin(origin[count]);
						
						LocalDate validTo =null;
						LocalDate validFrom=null;
						if (agnetValidFrom[count] != null
								&& !agnetValidFrom[count].isEmpty()) {
							validFrom = new LocalDate(LocalDate.NO_STATION,
									Location.NONE, false);
							validFrom.setDate(agnetValidFrom[count]);
							agentVO.setValidityStartDate(validFrom);

						}
						if (agnetValidTo[count] != null
								&& !agnetValidTo[count].isEmpty()) {
							validTo = new LocalDate(LocalDate.NO_STATION,
									Location.NONE, false);
							validTo.setDate(agnetValidTo[count]);
								agentVO.setValidityEndDate(validTo);
						}
							newagentVOs.add(agentVO);
							agentVO.setScc(agentScc[count]);
						}else if("N".equals(hiddenFlagsForAgent[count])){
							agentVO.setCompanyCode(getApplicationSession().getLogonVO()
									.getCompanyCode());
							agentVO.setStationCode(agentStation[count]);
							agentVO.setAgentCode(agentcode[count] );
							agentVO.setRemarks(agentRemarks[count]);
							agentVO=agentcheckbox(form,agentVO,count);
							agentVO.setScc(agentScc[count]);
							agentVO.setOperationFlag(CustomerAgentVO.OPERATION_FLAG_UPDATE);
						agentVO.setCarrier(agentCarrier[count]); // Added by A-8823 for IASCB-4841
							newagentVOs.add(agentVO);
						
						}
						
					count++;
				}
				
				customerVO.setCustomerAgentVOs(newagentVOs);
			}
			
			//inserting new rows for agent
			Collection<CustomerAgentVO> agentVOsForUpdateNew = new ArrayList<CustomerAgentVO>();
			for(int i=0;i<(hiddenFlagsForAgent.length)-1;i++){
				if(CustomerContactVO.OPERATION_FLAG_INSERT.equals(hiddenFlagsForAgent[i])){
					CustomerAgentVO newAgentVO = new CustomerAgentVO();
					newAgentVO.setOperationFlag(OPERATION_FLAG_INSERT);
					newAgentVO.setCompanyCode(getApplicationSession().getLogonVO()
							.getCompanyCode());
					newAgentVO.setStationCode(agentStation[i]);
					newAgentVO.setAgentCode(agentcode[i] );
					newAgentVO.setAgentName(agentName[i]);
					newAgentVO.setRemarks(agentRemarks[i]);
					newAgentVO=agentcheckbox(form,newAgentVO,i);
					newAgentVO.setScc(agentScc[i]);
					newAgentVO.setCarrier(agentCarrier[i]); // Added by A-8823 for IASCB-4841
					if(agentType!=null)
					{
					newAgentVO.setAgentType(agentType[i]);
					}
					newAgentVO.setOrigin(origin[i]);
					
					LocalDate validTo =null;
					LocalDate validFrom=null;
					if (agnetValidFrom[i] != null
							&& !agnetValidFrom[i].isEmpty()) {
						validFrom = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						validFrom.setDate(agnetValidFrom[i]);
						newAgentVO.setValidityStartDate(validFrom);

					}
					if (agnetValidTo[i] != null
							&& !agnetValidTo[i].isEmpty()) {
						validTo = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						validTo.setDate(agnetValidTo[i]);
						newAgentVO.setValidityEndDate(validTo);

					}
					//checking whether already agent is added 
					if(newagentVOs!=null && newagentVOs.size()>0){
						newagentVOs.add(newAgentVO);
					}else{
						agentVOsForUpdateNew.add(newAgentVO);
					}
					
				}
			}//added
				if(newagentVOs!=null && newagentVOs.size()>0){
					customerVO.setCustomerAgentVOs(newagentVOs);
				}
				else if(agentVOsForUpdateNew!=null && agentVOsForUpdateNew.size()>0){
					customerVO.setCustomerAgentVOs(agentVOsForUpdateNew);
				}
			//ICOJTO-5411, ICRD-356649 --> A-8953
			if(Objects.nonNull(customerVO) 
					&& Objects.nonNull(customerVO.getCustomerAgentVOs()) && !customerVO.getCustomerAgentVOs().isEmpty()) {
				boolean errorDataExist = customerVO.getCustomerAgentVOs().stream().anyMatch(customerAgentVO -> 
					!CustomerAgentVO.OPERATION_FLAG_DELETE.equals(customerAgentVO.getOperationFlag()) 
					&& (customerAgentVO.getValidityStartDate() == null || customerAgentVO.getValidityEndDate() == null));
				if(errorDataExist) {
					errors.add(new ErrorVO("customermanagement.defaults.customerregistration.preferences.validdatemandatory"));
					form.setSourcePage("SAVE");
					session.setSourcePage("SAVE");
					session.setValidationErrors((ArrayList<ErrorVO>)errors);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
				}
			}
		//}//removed
			
			//inserting new rows for agent ends
			//agent ends
			//added by a-3045 for CR HA16 on 04Jun09 starts
			//ccsf starts
			//updating values for ccsf			
			IACDetailsVO tempIACDtlVO = customerVO.getIacDetailsVO();
			Collection<ScreeningFacilityVO> ccsfVOsForUpdate = null;
			Collection<ScreeningFacilityVO> newccsfVOs =new ArrayList<ScreeningFacilityVO>();
			if(tempIACDtlVO != null){
				ccsfVOsForUpdate = tempIACDtlVO.getScreeningFacilityVOs();
			}else{
				tempIACDtlVO = new IACDetailsVO();
			}
			tempIACDtlVO.setIacNumber(form.getIacNumber());
			tempIACDtlVO.setApiacsspNumber(form.getApiacsspNumber());
			if(form.getIacExpiryDate() != null && form.getIacExpiryDate().trim().length() > 0){
				LocalDate iacExpDate =
					new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				tempIACDtlVO.setIacExpiryDate(iacExpDate.setDate(form.getIacExpiryDate()));
			}else{
				tempIACDtlVO.setIacExpiryDate(null);
			}
			if(form.getApiacsspExpiryDate() != null && form.getApiacsspExpiryDate().trim().length() > 0){
				LocalDate apExpDate =
					new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
				tempIACDtlVO.setApiacsspExpiryDate(apExpDate.setDate(form.getApiacsspExpiryDate()));
			}else{
				tempIACDtlVO.setApiacsspExpiryDate(null);
			}
			customerVO.setIacDetailsVO(tempIACDtlVO);		
				/*if(ccsfVOsForUpdate!=null && ccsfVOsForUpdate.size()>0){
				log.log(Log.FINE, "updating agent ");
				int count =0;
				for(ScreeningFacilityVO ccsfVO:ccsfVOsForUpdate){
					log.log(Log.FINE, "\n ccsf op flag=", hiddenOpFlagForCCSF,
							count);
						if(ScreeningFacilityVO.OPERATION_FLAG_UPDATE.equals(hiddenOpFlagForCCSF[count])){
							ccsfVO.setCompanyCode(getApplicationSession().getLogonVO()
									.getCompanyCode());
							ccsfVO.setAuthNumber(ccsfNumber[count]);
							ccsfVO.setFacilityCityName(ccsfCityName[count]);
							if(ccsfExpiryDate[count] != null && ccsfExpiryDate[count].trim().length() > 0){
								LocalDate ccsfExpDate =
									new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
								ccsfVO.setExpiryDate(ccsfExpDate.setDate(ccsfExpiryDate[count]));
							}else{
								ccsfVO.setExpiryDate(null);
							}								
							ccsfVO.setOperationFlag(ScreeningFacilityVO.OPERATION_FLAG_UPDATE);
							newccsfVOs.add(ccsfVO);
						}else if(CustomerAgentVO.OPERATION_FLAG_DELETE.equals(hiddenOpFlagForCCSF[count])){
							ccsfVO.setCompanyCode(getApplicationSession().getLogonVO()
									.getCompanyCode());
							ccsfVO.setAuthNumber(ccsfNumber[count]);
							ccsfVO.setFacilityCityName(ccsfCityName[count]);
							if(ccsfExpiryDate[count] != null && ccsfExpiryDate[count].trim().length() > 0){
								LocalDate ccsfExpDate =
									new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
								ccsfVO.setExpiryDate(ccsfExpDate.setDate(ccsfExpiryDate[count]));
							}else{
								ccsfVO.setExpiryDate(null);
							}
							ccsfVO.setOperationFlag(ScreeningFacilityVO.OPERATION_FLAG_DELETE);
							newccsfVOs.add(ccsfVO);
						}else if("N".equals(hiddenOpFlagForCCSF[count])){
							ccsfVO.setCompanyCode(getApplicationSession().getLogonVO()
									.getCompanyCode());
							ccsfVO.setAuthNumber(ccsfNumber[count]);
							ccsfVO.setFacilityCityName(ccsfCityName[count]);
							if(ccsfExpiryDate[count] != null && ccsfExpiryDate[count].trim().length() > 0){
								LocalDate ccsfExpDate =
									new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
								ccsfVO.setExpiryDate(ccsfExpDate.setDate(ccsfExpiryDate[count]));
							}else{
								ccsfVO.setExpiryDate(null);
							}	
							ccsfVO.setOperationFlag(ScreeningFacilityVO.OPERATION_FLAG_UPDATE);
							newccsfVOs.add(ccsfVO);
						
						}						
					count++;
				}	
				tempIACDtlVO.setScreeningFacilityVOs(newccsfVOs);
				customerVO.setIacDetailsVO(tempIACDtlVO);
			}	*/		
			//inserting new rows for ccsf					
			/*Collection<ScreeningFacilityVO> ccsfVOsForUpdateNew = new ArrayList<ScreeningFacilityVO>();
			/*for(int i=0;i<(hiddenOpFlagForCCSF.length)-1;i++){
				if(ScreeningFacilityVO.OPERATION_FLAG_INSERT.equals(hiddenOpFlagForCCSF[i])){
					ScreeningFacilityVO ccsfDetVO = new ScreeningFacilityVO();
					ccsfDetVO.setOperationFlag(OPERATION_FLAG_INSERT);
					ccsfDetVO.setCompanyCode(getApplicationSession().getLogonVO()
							.getCompanyCode());
					ccsfDetVO.setAuthNumber(ccsfNumber[i]);
					ccsfDetVO.setFacilityCityName(ccsfCityName[i]);
					if(ccsfExpiryDate[i] != null && ccsfExpiryDate[i].trim().length() > 0){
						LocalDate ccsfExpDate =
							new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,false);
						ccsfDetVO.setExpiryDate(ccsfExpDate.setDate(ccsfExpiryDate[i]));
					}else{
						ccsfDetVO.setExpiryDate(null);
					}	
					if(newccsfVOs!=null && newccsfVOs.size()>0){
						newccsfVOs.add(ccsfDetVO);
					}else{
						ccsfVOsForUpdateNew.add(ccsfDetVO);
					}
				}
				if(newccsfVOs!=null && newccsfVOs.size()>0){
					tempIACDtlVO.setScreeningFacilityVOs(newccsfVOs);
					customerVO.setIacDetailsVO(tempIACDtlVO);
				}else if(ccsfVOsForUpdateNew!=null && ccsfVOsForUpdateNew.size()>0){
					tempIACDtlVO.setScreeningFacilityVOs(ccsfVOsForUpdateNew);
					customerVO.setIacDetailsVO(tempIACDtlVO);
				}
			}*/			
			//inserting new rows for ccsf ends
			//ccsf ends			
			//added by a-3045 for CR HA16 on 04Jun09 ends
			if (form.getGlobalCustomer()!=null && ("on".equals(form.getGlobalCustomer()))) {
				customerVO.setGlobalCustomerFlag("Y");

			} else {
				customerVO.setGlobalCustomerFlag("N");
			}
			if (customerVO.getGlobalCustomerFlag() != null
					&& "Y".equals(customerVO.getGlobalCustomerFlag())) {
				form.setGlobalCustomer("on");
			}
			
			Collection<CustomerAgentVO> agentVOs = customerVO.getCustomerAgentVOs();
			//	Collection<CustomerContactVO> contactVOs = null;
			/** Removed the NULL chekc for customer VO as part of FINDBUg code quality by A-6770**/
			boolean duplicateErrorThrown = false; 
				if (CollectionUtils.isNotEmpty(customerVO.getCustomerAgentVOs())) {
					StringBuilder agentSccCodes = null;
				for (CustomerAgentVO customerAgentVO : customerVO.getCustomerAgentVOs()) {
					if (customerAgentVO.getScc() != null && customerAgentVO.getScc().trim().length() > 0) {
							if (agentSccCodes == null) {
							if (customerAgentVO.getScc().endsWith(DELIMETER_COMMA)) {
								agentSccCodes = new StringBuilder(customerAgentVO.getScc());
								} else {
								agentSccCodes = new StringBuilder(customerAgentVO.getScc()).append(DELIMETER_COMMA);
								}
							} else {
							if (customerAgentVO.getScc().endsWith(DELIMETER_COMMA)) {
								agentSccCodes = new StringBuilder(agentSccCodes).append(DELIMETER_COMMA)
										.append(customerAgentVO.getScc());
								} else {
								agentSccCodes = new StringBuilder(agentSccCodes).append(DELIMETER_COMMA)
										.append(customerAgentVO.getScc()).append(DELIMETER_COMMA);
							}
						}
					}
				}
				log.log(Log.FINE, "agentSccCodes ---------------------->", agentSccCodes);
					if (agentSccCodes != null) {
						String agentSccCodesForValidation = agentSccCodes.substring(0, agentSccCodes.length() - 1);
						errors = validateSccCodes(companyCode, agentSccCodesForValidation);
					}

					// Added by A-8823 for IASCB-4841 beg
					if(!CollectionUtils.isEmpty(errors)) {
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						form.setSourcePage("SAVE");
						session.setSourcePage("SAVE");
						session.setValidationErrors(new ArrayList<ErrorVO>(errors));
					    return;
					}
					String carrierValidationKey = "";
					Map<String, CustomerAgentVO> carrierValidationMap = new LinkedHashMap<>();
				Collection<CustomerAgentVO> agentVos = customerVO.getCustomerAgentVOs().stream().filter(
						agentVO -> !(Objects.equals(agentVO.getOperationFlag(), AbstractVO.OPERATION_FLAG_DELETE)))
						.collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(agentVos)) {
					for (CustomerAgentVO customerAgentVO : agentVos) {
						if (!isNullorEmpty(customerAgentVO.getCarrier()) || !isNullorEmpty(customerAgentVO.getScc())
								|| !isNullorEmpty(customerAgentVO.getOrigin())) {
							String[] carriers = customerAgentVO.getCarrier().isEmpty() ? null:customerAgentVO.getCarrier().split(",");
							String[] origins = customerAgentVO.getOrigin().isEmpty() ? null:customerAgentVO.getOrigin().split(",");
							String[] sccs = customerAgentVO.getScc().isEmpty() ? null:customerAgentVO.getScc().split(",");
							if (Objects.nonNull(carriers)) {
								errors = checkDuplicateBasisValuesOfCarrier(
										Stream.of(carriers).collect(Collectors.toList()));
							if(!CollectionUtils.isEmpty(errors)) {
								invocationContext.addAllError(errors);
								invocationContext.target = SAVE_FAILURE;
								form.setSourcePage("SAVE");
								session.setSourcePage("SAVE");
								session.setValidationErrors((ArrayList<ErrorVO>) errors);
								return;
							}
							AirlineDelegate airlineDelegate = new AirlineDelegate();
							for (String carrierCode : carriers) {
								try {
									airlineDelegate.validateAlphaCode(companyCode, carrierCode);
								} catch (BusinessDelegateException businessDelegateException) {
									errors.add(new ErrorVO(ERR_INVALID_CARRIER_EXISTS_IN_AGENT_DETAILS));
									invocationContext.addAllError(errors);
									invocationContext.target = SAVE_FAILURE;
									form.setSourcePage("SAVE");
									session.setSourcePage("SAVE");
									session.setValidationErrors((ArrayList<ErrorVO>) errors);
									return;
								}
							}
							for (String carrierCode : carriers) {

							carrierValidationKey = customerAgentVO.getStationCode() + "||" + carrierCode + "||"
									+ customerAgentVO.getScc() + "||" + customerAgentVO.getAgentType();
									
								if (carrierValidationMap.containsKey(carrierValidationKey)) {
										CustomerAgentVO customerAgentVO1 = carrierValidationMap
												.get(carrierValidationKey);
								if ((customerAgentVO.getValidityStartDate()
										.isGreaterThan(customerAgentVO1.getValidityStartDate())
										&& customerAgentVO.getValidityStartDate()
												.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| (customerAgentVO.getValidityEndDate()
												.isGreaterThan(customerAgentVO1.getValidityStartDate())
												&& customerAgentVO.getValidityEndDate()
														.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| (customerAgentVO1.getValidityStartDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityStartDate()
														.isLesserThan(customerAgentVO.getValidityEndDate()))
										|| (customerAgentVO1.getValidityEndDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityEndDate()
																.isLesserThan(customerAgentVO.getValidityEndDate()))) {
											duplicateErrorThrown = true;
									errors.add(new ErrorVO(ERR_DUPLICATE_CARRIER_EXISTS_IN_AGENT_DETAILS));
											break;
								}
								} else {
								carrierValidationMap.put(carrierValidationKey, customerAgentVO);
							}
						}
							}
							if (duplicateErrorThrown) {
								break;
							}
							if (Objects.nonNull(origins)) {
								for (String origincode : origins) {
									carrierValidationKey = customerAgentVO.getStationCode() + "||" + origincode + "||"
											+ customerAgentVO.getScc() + "||" + customerAgentVO.getAgentType() + "||"
											+ customerAgentVO.getCarrier();
							if (carrierValidationMap.containsKey(carrierValidationKey)) {
										CustomerAgentVO customerAgentVO1 = carrierValidationMap
												.get(carrierValidationKey);
								if ((customerAgentVO.getValidityStartDate()
										.isGreaterThan(customerAgentVO1.getValidityStartDate())
										&& customerAgentVO.getValidityStartDate()
												.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| (customerAgentVO.getValidityEndDate()
												.isGreaterThan(customerAgentVO1.getValidityStartDate())
												&& customerAgentVO.getValidityEndDate()
														.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| (customerAgentVO1.getValidityStartDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityStartDate()
														.isLesserThan(customerAgentVO.getValidityEndDate()))
										|| (customerAgentVO1.getValidityEndDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityEndDate()
																.isLesserThan(customerAgentVO.getValidityEndDate()))) {
									errors.add(new ErrorVO(ERR_DUPLICATE_CARRIER_EXISTS_IN_AGENT_DETAILS));
											duplicateErrorThrown = true;
											break;
										}
									} else {
									carrierValidationMap.put(carrierValidationKey, customerAgentVO);
								}
						}
							}
							if (duplicateErrorThrown) {
								break;
							}
							if (Objects.nonNull(sccs)) {
								for (String scccode : sccs) {
									carrierValidationKey = customerAgentVO.getStationCode() + "||" + scccode + "||"
											+ customerAgentVO.getAgentType() +"||"+ customerAgentVO.getCarrier();
							if (carrierValidationMap.containsKey(carrierValidationKey)) {
										CustomerAgentVO customerAgentVO1 = carrierValidationMap
												.get(carrierValidationKey);
								if ((customerAgentVO.getValidityStartDate()
										.isGreaterThan(customerAgentVO1.getValidityStartDate())
										&& customerAgentVO.getValidityStartDate()
												.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| (customerAgentVO.getValidityEndDate()
												.isGreaterThan(customerAgentVO1.getValidityStartDate())
												&& customerAgentVO.getValidityEndDate()
														.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| (customerAgentVO1.getValidityStartDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityStartDate()
														.isLesserThan(customerAgentVO.getValidityEndDate()))
										|| (customerAgentVO1.getValidityEndDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityEndDate()
																.isLesserThan(customerAgentVO.getValidityEndDate()))) {

								errors.add(new ErrorVO(ERR_DUPLICATE_CARRIER_EXISTS_IN_AGENT_DETAILS));
											duplicateErrorThrown = true;
											break;
								}
							} else {
								carrierValidationMap.put(carrierValidationKey, customerAgentVO);
									}
								}
							}
							if (duplicateErrorThrown) {
								break;
							}
						} else {
							carrierValidationKey = customerAgentVO.getStationCode() + "||"
									+ customerAgentVO.getAgentType() + "||"+ customerAgentVO.getAgentCode();
							if (carrierValidationMap.containsKey(carrierValidationKey)) {
								CustomerAgentVO customerAgentVO1 = carrierValidationMap.get(carrierValidationKey);
								if ((customerAgentVO.getValidityStartDate()
										.isGreaterThan(customerAgentVO1.getValidityStartDate())
										&& customerAgentVO.getValidityStartDate()
												.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| (customerAgentVO.getValidityEndDate()
												.isGreaterThan(customerAgentVO1.getValidityStartDate())
												&& customerAgentVO.getValidityEndDate()
														.isLesserThan(customerAgentVO1.getValidityEndDate()))
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityStartDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityStartDate())
										|| customerAgentVO.getValidityEndDate()
												.equals(customerAgentVO1.getValidityEndDate())
										|| (customerAgentVO1.getValidityStartDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityStartDate()
														.isLesserThan(customerAgentVO.getValidityEndDate()))
										|| (customerAgentVO1.getValidityEndDate()
												.isGreaterThan(customerAgentVO.getValidityStartDate())
												&& customerAgentVO1.getValidityEndDate()
														.isLesserThan(customerAgentVO.getValidityEndDate()))) {
									errors.add(new ErrorVO(ERR_DUPLICATE_CARRIER_EXISTS_IN_AGENT_DETAILS));
									duplicateErrorThrown = true;
									break;
								}
							} else {
								carrierValidationMap.put(carrierValidationKey, customerAgentVO);
							}
							if (duplicateErrorThrown) {
								break;
							}
							}
						}
					}
					// Added by A-8823 for IASCB-4841 end
				}
			
			if (CollectionUtils.isNotEmpty(errors)
					&& (duplicateErrorThrown && !validateOps(customerVO.getCustomerAgentVOs()))) {
				if (errors.size() > 1) {
					errors.removeAll(
							errors.stream()
									.filter(errorVO -> Objects.equals(errorVO.getErrorCode(),
											ERR_DUPLICATE_CARRIER_EXISTS_IN_AGENT_DETAILS))
									.collect(Collectors.toList()));
				} else {
					errors = null;
				}
			}
			if (CollectionUtils.isNotEmpty(errors)) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				return;
			}
			//validating both customer and agent
			log.log(Log.FINE, "\n validating customer and agent");
			Collection<CustomerContactVO> keyContactDetailVOsForCheck = customerVO
					.getCustomerContactVOs();
				
			if (keyContactDetailVOsForCheck != null && keyContactDetailVOsForCheck.size() > 0) {
					errors = updateKeyContactDetails(
							(ArrayList<CustomerContactVO>) keyContactDetailVOsForCheck, form,customerVO);
					
					//validating contact so that min one should be added
					if(keyContactDetailVOsForCheck != null && keyContactDetailVOsForCheck.size()>0){
						int i=0;
						for(CustomerContactVO contactVO : keyContactDetailVOsForCheck){
							if(!OPERATION_FLAG_DELETE.equals(contactVO .getOperationFlag())){
								i++;
							}
						}
						/*if(i==0){
							error = new ErrorVO(
							"customermanagement.defaults.msg.err.addatleastonekeycontact");
							errors.add(error);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;
							return;
						}*/
					}
					// Commented the primary user validation as part of ICRD-175746, Modified by A-7137
					//checking whether primary contact is selected
					/*log.log(Log.FINE, "\n checking primary key selected");
					if (keyContactDetailVOsForCheck != null && keyContactDetailVOsForCheck.size() > 0) {
						int pkcount=0;
						for (CustomerContactVO contactVO : keyContactDetailVOsForCheck) {
							if ("Y".equals(contactVO.getPrimaryUserFlag())) {
								pkcount++;
							}
						}
						if(pkcount==0){
							error = new ErrorVO(
							"customermanagement.defaults.msg.err.selectprimarycontact");
							errors.add(error);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;
							form.setSourcePage("SAVE");
							session.setSourcePage("SAVE");
							session.setValidationErrors((ArrayList<ErrorVO>)errors);
							return;

						}

					}*/
					
			}/*else{
				error = new ErrorVO(
				"customermanagement.defaults.msg.err.addatleastonekeycontact");
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}*/
			
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				return;
			}	
				
			if(agentVOs!=null && agentVOs.size()>0){
				errors = validateAgentVOs(agentVOs,logonAttributes.getCompanyCode(),form);
			}
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			log.log(Log.FINE,"validating form");
				errors = validateForm(form, logonAttributes,billingNeededParameter);
			
			if (errors != null && errors.size() > 0) {
					//setting to session to display VO entered by the end user
					session.setCustomerVO(customerVO);
					form.setSourcePage("SAVE");
					session.setSourcePage("SAVE");
					session.setValidationErrors((ArrayList<ErrorVO>)errors);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_FAILURE;
					return;
			}
			/*
			 * Modified by A-5165 for ICRD-64121
			 */
			// Added by A-5198 for ICRD-63059
			errors = validateCustomerMiscDetailsVos(initialCustomerMiscValues, customerVO,form);
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				session.setSourcePage("SAVE");
				form.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				return;
			}
			log.log(Log.FINE,"validating area fields");
				errors = validateAreaFields(form, customerVO);
			
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			
			if (agentVOs != null) {
				for (CustomerAgentVO agentVO : agentVOs) {
					agentVO.setCustomerCode(customerVO.getCustomerCode()
							.toUpperCase());
	
				}
			}
			
			if (keyContactDetailVOsForCheck != null) {
				for (CustomerContactVO contactVO : keyContactDetailVOsForCheck) {
					contactVO.setCustomerCode(customerVO.getCustomerCode()
							.toUpperCase());
					contactVO.setCompanyCode(logonAttributes.getCompanyCode());
				}
			}
			log.log(Log.FINE, " \n checking error*size");
			if(invocationContext.getErrors() != null &&
					invocationContext.getErrors().size() > 0){
				invocationContext.target = SAVE_FAILURE;
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				return;
			}
			//added by a-3045 for CR HA115 on 31Jul09 starts
			/*errors = validateDuplicateTSANumbers(customerVO);
			if (errors != null && errors.size() > 0) {
				//setting to session to display VO entered by the end user
				session.setCustomerVO(customerVO);
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors(new ArrayList<ErrorVO>(errors));
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}*/
			//added by a-3045 for CR HA115 on 31Jul09 starts
			/**
			 * Method call commented by A-5163 ends
			 */
            /** Code added for ICRD-58628 by A-5163 starts **/
			if (customerVO.getCustomerCertificateDetails() != null
					&& !customerVO.getCustomerCertificateDetails().isEmpty()) {
				errors = validateCustomerCertificates(customerVO.getCustomerCertificateDetails(), logonAttributes.getCompanyCode(), form);
			}
			if (errors != null && errors.size() > 0) {
				session.setCustomerVO(customerVO);
				form.setSourcePage("SAVE");
				session.setSourcePage("SAVE");
				session.setValidationErrors((ArrayList<ErrorVO>)errors);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
			/** Code added for ICRD-58628 by A-5163 ends **/
			
			log.log(Log.FINE, "\n affter validation", customerVO);
			customerVO.setCustomerType(form.getCustomerType());
			
			//Added by A-7137 as part of CR ICRD-152555 starts
			log.log(Log.FINE, " Updating Customer Preferences");
			CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
			List<CustomerPreferenceVO> customerPreferences = new ArrayList<CustomerPreferenceVO>();
			if(preferenceCodes != null){
			if(preferenceCodes != null && preferenceCodes.length > 0){
			for (int i = 0; i < preferenceCodes.length; i++) {
				if(preferenceValues!=null && preferenceValues.length>0 &&  preferenceValues[i]!=null && preferenceValues[i].trim().length()>0){
					/**
					 * Added as part of ICRD-212854.
					 * Validating the 'accounting responsible' customer preference. If the value entered is not available
					 *  in the User maintenance screen then system shall throw an error.
					 */
					if(ACCOUNTING_RESP_CODE.equals(preferenceCodes[i])||ACCOUNTING_SUPR_CODE.equals(preferenceCodes[i])){
						UserVO userVO = null;
						try {
							userVO = delegate.findUserDetails(companyCode,preferenceValues[i]);
						} catch (BusinessDelegateException e) {
							errors = handleDelegateException(e);
						}
						if(userVO == null || userVO.getUserCode() == null){
							if (ACCOUNTING_RESP_CODE.equals(preferenceCodes[i])) {
								errors.add(new ErrorVO(INVALID_ACCT_RESPONSIBLE));
							} else if (ACCOUNTING_SUPR_CODE.equals(preferenceCodes[i])) {
								errors.add(new ErrorVO(INVALID_ACCT_SUPERVISOR));
							}
							session.setCustomerVO(customerVO);
							form.setSourcePage("SAVE");
							session.setSourcePage("SAVE");
							session.setValidationErrors((ArrayList<ErrorVO>)errors);
							invocationContext.addAllError(errors);
							invocationContext.target = SAVE_FAILURE;
							return;
						}
					}
							

if((PGNHLE_CODE.equals(preferenceCodes[i]))&&preferenceValues[i].length()>50)
					{
				    		    errors.add(new ErrorVO(WRONG_PGNHLE_FORMAT));
								session.setCustomerVO(customerVO);
								form.setSourcePage("SAVE");
								session.setSourcePage("SAVE");
								session.setValidationErrors((ArrayList<ErrorVO>)errors);
								invocationContext.addAllError(errors);
								invocationContext.target = SAVE_FAILURE;
								return;
					}
					// Delivery slot time validation
					if(Objects.equals(preferenceCodes[i],DELIVERY_SLOT_TIME_CODE)) {
						String[] deliverySlotTimes = preferenceValues[i].split(",");
						for(int count=0;count<deliverySlotTimes.length;count++) {
							String s1 = deliverySlotTimes[count];
							String[] singleDeliverySlotTime = s1.split("-");
							for(int j =0;j<singleDeliverySlotTime.length;j++) {
								Boolean validTime = validateDeliverySlot(singleDeliverySlotTime[j]);
								if(!validTime) {
									errors.add(new ErrorVO(INVALID_DELIVERY_TIME_FORMAT));
									session.setCustomerVO(customerVO);
									form.setSourcePage("SAVE");
									session.setSourcePage("SAVE");
									session.setValidationErrors((ArrayList<ErrorVO>)errors);
									invocationContext.addAllError(errors);
									invocationContext.target = SAVE_FAILURE;
									return;
								}
							}
						
						}
					}
					CustomerPreferenceVO customerPreferenceVO = new CustomerPreferenceVO();
					customerPreferenceVO.setCompanyCode(companyCode);
					customerPreferenceVO.setCustomerCode(customerVO.getCustomerCode());
					customerPreferenceVO.setPreferenceCode(preferenceCodes[i]);
					customerPreferenceVO.setPreferenceValue(preferenceValues[i]);
					customerPreferenceVO.setOperationFlag(CustomerPreferenceVO.OPERATION_FLAG_INSERT);
					customerPreferenceVO.setLastUpdatedUser(logonAttributes.getUserId());
					customerPreferences.add(customerPreferenceVO);
				}
			}
			}
			}
			
			if(customerVO.getCustomerPreferences() != null && customerVO.getCustomerPreferences().size()>0){
				for(CustomerPreferenceVO customerPreferenceVO:customerVO.getCustomerPreferences()){
					customerPreferenceVO.setOperationFlag(CustomerPreferenceVO.OPERATION_FLAG_DELETE);
				}			
			}
			
			if(customerVO.getCustomerPreferences() == null){				
				customerVO.setCustomerPreferences(new ArrayList<CustomerPreferenceVO>());				
			}
			
			if(customerPreferences != null && customerPreferences.size()>0){
				customerVO.getCustomerPreferences().addAll(customerPreferences);
			}
			log.log(Log.FINE, "Updating Creating Customer Preferences");
			//Added by A-7137 as part of CR ICRD-152555 ends
			
			
			Collection<LockVO> locks = prepareLockForSave();
		try {
			customerCode = delegate.registerCustomer(customerVO,locks);
		} catch (BusinessDelegateException ex) {

			errors = handleDelegateException(ex);
			log.log(Log.FINE, "errors from server ---------------------->",
					errors);
		}
			log.log(Log.FINE, "After Cust Reg Update,,..");
			
		}
				
	  //String[] contactCode = form.getContactCode();		
		
		log.log(Log.FINE, "\n errors from server ----->", errors);
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		if (errors != null && errors.size() > 0) {
			for (ErrorVO errorVO : errors) {
				log.log(Log.FINE, "ErrorVO from exception is", errorVO.getErrorCode().toString());
				if ((CUSTOMER_ALREADY_EXISTS.equals(errorVO.getErrorCode().toString()))) {
					log.log(Log.FINE, "ErrorVO from exception is", errorVO.getErrorCode().toString());
					errorVO.setErrorData(new Object[] { form.getCustomerCode()
							.toUpperCase() });
					errorVOs.add(errorVO);

				}
				//Added by A-4772 for ICRD-34645  starts here
				if((CUSTOMER_ALREADY_EXISTS_FOR_AIRPORTDETAILS.equals(errorVO.getErrorCode().toString()))){
					log.log(Log.FINE, "ErrorVO from exception is", errorVO.getErrorCode().toString());
					errorVOs.add(errorVO);
				}
				//Added by A-4772 for ICRD-34645  ends here
			}
			if(errorVOs!=null && errorVOs.size()>0){
				invocationContext.addAllError(errorVOs);
				session.setValidationErrors((ArrayList<ErrorVO>)errorVOs);
			}else{
				invocationContext.addAllError(errors);
				//A-5273 Commented for ICRD-59741 
				//session.setValidationErrors((ArrayList<ErrorVO>)errors);
			}
			form.setSourcePage("SAVE");
			session.setSourcePage("SAVE");
			session.setCustomerVO(customerVO);
			invocationContext.target = SAVE_FAILURE;
			return;
		}
		errors = new ArrayList<ErrorVO>();
		if (customerCode != null && !BLANK.equals(customerCode)) {
			Object[] code = new Object[1];
			code[0] = customerCode;
			log.log(Log.FINE, "code---", code);
			if(!"maintainReservation".equals(form.getScreenStatus())&& !"maintainPermanentBooking".equals(form.getScreenStatus())){//if condition added by A-2390 to check whether the call is from reservation screen
				error = new ErrorVO(
						"customermanagement.defaults.customerregn.msg.info.customerCode",
						code);
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				log.log(Log.FINE, "error-----", error);
				errors.add(error);
			/*Added by A-2390 for register customer link from reservation screen starts*/
				form.setFromReservation(false);
				form.setCustomerCode(BLANK);				
			}else{
				log.log(Log.FINE, "Screen Status...", form.getScreenStatus());
				if("maintainReservation".equals(form.getScreenStatus())){         
				maintainBookingSession.setRegisterCustomerCode(form.getCustomerCode());
				maintainBookingSession.setRegisterCustomerName(form.getCustName());
				form.setFromReservation(true);
			}
				if("maintainPermanentBooking".equals(form.getScreenStatus())){
				maintainPermanentBookingSessionInterface.setRegisterCustomerCode(form.getCustomerCode());
				maintainPermanentBookingSessionInterface.setRegisterCustomerName(form.getCustName());
				form.setFromPermanentBooking(true);
				}
			}
			/*changes by A-2390 ends*/
			CustomerVO newCustomerVO = new CustomerVO();
			newCustomerVO.setOperationFlag(OPERATION_FLAG_INSERT);
			form.setCustomerPOAValidity(BLANK);
			form.setCustName(BLANK);
			//form.setCustomerCode(BLANK);
			form.setAddress1(BLANK);
			form.setAddress2(BLANK);
			form.setCity(BLANK);
			form.setCountry(BLANK);
			form.setArea(BLANK);
			form.setZipCode(BLANK);
			form.setEmail(BLANK);
			form.setMobile(BLANK);
			form.setTelephone(BLANK);
			form.setZone(BLANK);
			form.setState(BLANK);
			form.setSita(BLANK);
			form.setFax(BLANK);
			 form.setEoriNo(BLANK);
			//Added by A-5163 for ICRD-78230 starts
		    form.setGibCustomerFlag(BLANK);
			form.setGibRegistrationDate(BLANK);
			form.setPublicSectorFlag(BLANK);
			//Added by A-5163 for ICRD-78230 ends
			form.setEmail2(BLANK);
			form.setCustomerCompanyCode(BLANK);
			form.setBranch(BLANK);
			form.setBillingCode(BLANK);
			form.setNaacsbbAgentCode(BLANK);
			form.setNaccsInvoiceCode(BLANK);
			form.setSpotForwarder(BLANK);
			form.setDefaultHawbLength(BLANK);
			form.setBillingPeriod("M");
			//Added by A-5165	for ICRD-35135
			form.setBillingDueDays(BLANK);
			form.setDueDateBasis(BLANK);
			form.setHandledCustomerImport("N");
			form.setHandledCustomerExport("N");
			form.setHandledCustomerForwarder("N");
			form.setForwarderType("FW");
			form.setConsolidator(BLANK);
			form.setPoa(BLANK);
			form.setNaccsDeclarationCode(BLANK);
			form.setNaccsAircargoAgentCode(BLANK);
			form.setBranchName(BLANK);
			form.setCustomerPOAValidity("31-Dec-2100");
			//changed by a-3045 for bug 32199 starts
			form.setStation(logonAttributes.getStationCode());
			//changed by a-3045 for bug 32199 ends
			form.setCustomerGroup(BLANK);
			form.setGlobalCustomer("off");
			form.setClearingAgentFlag(false);// Added BY A-8374 For ICRD-247693
			customerVO = new CustomerVO();
			customerVO.setOperationFlag(OPERATION_FLAG_INSERT);
			session.setCustomerVO(customerVO);
			invocationContext.addAllError(errors);
			session.setPageURL(null);
			form.setScreenStatus("");
			//added by a-3045 for CR HA94 on 27Mar09 starts
			form.setKnownShipper(BLANK);
			form.setAccountNumber(BLANK);
			//added by a-3045 for CR HA94 on 27Mar09 ends
			//added by a-3045 for CR HA113 on 03Jul09 starts
			form.setStopCredit(BLANK);
			form.setInvoiceToCustomer(BLANK);
			//Added by A-5493 for ICRD-17199 to reset the vendor flag
			form.setVendorFlag(BLANK);
			//Added by A-5493 for ICRD-17199 ends
			//added by a-3045 for CR HA113 on 03Jul09 ends
			//added by a-3045 for CR HA16 on 27May09 starts
			form.setAccountNumber(BLANK);
			form.setEstablishedDate(BLANK);
			form.setRemarks(BLANK);
			form.setBillingCityCode(BLANK);
			form.setBillingCountry(BLANK);
			form.setBillingEmail(BLANK);
			form.setBillingFax(BLANK);
			form.setBillingLocation(BLANK);
			form.setBillingState(BLANK);
			form.setBillingStreet(BLANK);
			form.setBillingTelephone(BLANK);
			form.setBillingZipcode(BLANK);
			form.setIacNumber(BLANK);
			form.setIacExpiryDate(BLANK);
			form.setScc(BLANK);
			form.setApiacsspNumber(BLANK);
			form.setApiacsspExpiryDate(BLANK);
			form.setCustomerShortCode(BLANK);
			//added by a-3045 for CR HA16 on 27May09 starts
			//added by A-3278 for JetBlue31-1 on 23Apr10
			form.setBillingPeriod(BLANK);
			form.setDueDateBasis(BLANK);
			form.setBillingPeriodDescription(BLANK);
			//Added by A-5169 for ICRD-31552 on 30-APR-2013 
			form.setCustomsLocationNo(BLANK); 
			//JB31-1 ends
			form.setCustomerCertificateType(null);// Added by A-8832 as part of IASCB-4139
			// Added By A-5183 FOR ICRD-18283 Starts			
			form.setAgent(BLANK);
			form.setCustomer(BLANK);
			form.setCccollector(BLANK);
			form.setVatRegNumber(BLANK);			
			form.setAirportCode(BLANK);
			form.setVatRegNumber(BLANK);
			form.setAircraftTypeHandled(BLANK);
			form.setDateOfExchange(BLANK);    			
			form.setCassBillingIndicator(false);
			form.setCassCode(BLANK);    			
			form.setBillingThroughInterline(false);    			
			form.setAirlineCode(BLANK);    			
			//form.setBillingCurrency(BLANK);
			//form.setBillingPeriodDescription(BLANK);
			form.setIataAgentCode(BLANK);
			form.setAgentInformation(BLANK);    			
			form.setCassIdentifier(BLANK);
			//CRQ ID:117235 - A-5127 added
			form.setRecipientCode(BLANK);
			form.setCassImportIdentifier(BLANK);
			//End - CRQ ID:117235 - A-5127 added
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
			/*Added by A-7567 as part of ICRD-305684*/
			form.setCntLocBillingApplicableTo(BLANK);
			form.setEnduranceFlag("");
			form.setEndurancePercentage(BLANK);
			form.setEnduranceValue(BLANK);
			form.setEnduranceMaxValue(String.valueOf(BLANK));
			form.setSettlementCurrency(BLANK);
			form.setRemarks(BLANK);
			form.setFromDate(BLANK);
			form.setToDate(BLANK);
			form.setCustomerType(BLANK);
			form.setBillingremark(BLANK); 
			
			form.setBankbranch(BLANK);
			form.setBankcityname(BLANK);
			form.setBankcode(BLANK);
			form.setBankcountryname(BLANK);
			form.setBankpincode(BLANK);
			form.setBankstatename(BLANK);
			form.setResave(BLANK);
			//form.setValidateflag(BLANK);
			form.setMiscDetailCode(null);
			form.setMiscDetailOpFlag(null);
			form.setMiscDetailRemarks(null);
			form.setMiscDetailValidFrom(null);
			form.setMiscDetailValidTo(null);
			form.setMiscDetailValue(null);
			form.setMiscSeqNum(null);
			form.setMisopcheck(null);
			// Added By A-5183 FOR ICRD-18283 Ends
			
			//Added for ICRD-33334 by A-5163 Starts
			form.setInvoiceClubbingIndicator(null);
			//Added for ICRD-33334 by A-5163 Ends
			//added by a-6314 for ICRD-130084
			form.setInvoiceType(null);
			form.setInternalAccountHolder(BLANK);
			/*Added by 201930 for IASCB-131790*/
			form.setCassCountry(null);
			invocationContext.target = SAVE_SUCCESS;
		} else {

			/*if (FROMLIST.equals(form.getFromCustListing())) {
				session.setCustomerVO(null);
				invocationContext.target = LIST_SUCCESS;
				return;
			}*/
			CustomerVO newCustomerVO = new CustomerVO();
			newCustomerVO.setOperationFlag(OPERATION_FLAG_INSERT);
			form.setCustomerPOAValidity(BLANK);
			form.setCustName(BLANK);
			form.setCustomerCode(BLANK);
			form.setAddress1(BLANK);
			form.setAddress2(BLANK);
			form.setCity(BLANK);
			form.setCountry(BLANK);
			form.setArea(BLANK);
			form.setZipCode(BLANK);
			form.setEmail(BLANK);
			form.setMobile(BLANK);
			form.setTelephone(BLANK);
			form.setZone(BLANK);
			form.setState(BLANK);
			form.setSita(BLANK);
			form.setFax(BLANK);
			//Added by A-5200 for ICRD-78230 starts
		    form.setGibCustomerFlag(BLANK);
			form.setGibRegistrationDate(BLANK);
			form.setPublicSectorFlag(BLANK);
			//Added by A-5200 for ICRD-78230 ends
			//added for tiact492
			form.setEoriNo(BLANK);
			form.setEmail2(BLANK);
			form.setCustomerCompanyCode(BLANK);
			form.setBranch(BLANK);
			form.setBillingCode(BLANK);
			form.setNaacsbbAgentCode(BLANK);
			form.setNaccsInvoiceCode(BLANK);
			form.setSpotForwarder(BLANK);
			form.setDefaultHawbLength(BLANK);
			form.setBillingPeriod("M");
			form.setDueDateBasis(BLANK);
			form.setHandledCustomerImport("N");
			form.setHandledCustomerExport("N");
			form.setHandledCustomerForwarder("N");
			form.setForwarderType("FW");
			form.setScc(BLANK);
			form.setConsolidator(BLANK);
			form.setNaccsDeclarationCode(BLANK);
			form.setPoa(BLANK);
			form.setNaccsAircargoAgentCode(BLANK);
			form.setBranchName(BLANK);
			form.setCustomerShortCode(BLANK);
		
			//form.setCustomerPOAValidity("2100/12/31");
			//changed by a-3045 for bug 32199 starts
			form.setStation(logonAttributes.getStationCode());
			//changed by a-3045 for bug 32199 ends
			form.setCustomerGroup(BLANK);
			form.setGlobalCustomer("off");
			form.setClearingAgentFlag(false);// Added BY A-8374 For ICRD-247693
			customerVO = new CustomerVO();
			customerVO.setOperationFlag(OPERATION_FLAG_INSERT);
			session.setCustomerVO(customerVO);
			form.setFromReservation(false); //Added by A-2390 for register customer link from reservation screen 
			form.setFromPermanentBooking(false);
			form.setScreenStatus("");
			session.setPageURL(null);
			//added by a-3045 for CR HA94 on 27Mar09 starts
			form.setKnownShipper(BLANK);
			form.setAccountNumber(BLANK);
			//added by a-3045 for CR HA94 on 27Mar09 ends
			//added by a-3045 for CR HA113 on 03Jul09 starts
			form.setStopCredit(BLANK);
			form.setInvoiceToCustomer(BLANK);
			//Added by A-5493 for ICRD-17199 to reset the vendor flag
			form.setVendorFlag(BLANK);
			//Added by A-5493 for ICRD-17199 ends
			//added by a-3045 for CR HA113 on 03Jul09 ends
			//added by a-3045 for CR HA16 on 27May09 starts
			form.setAccountNumber(BLANK);
			form.setEstablishedDate(BLANK);
			form.setRemarks(BLANK);
			form.setBillingCityCode(BLANK);
			form.setBillingCountry(BLANK);
			form.setBillingEmail(BLANK);
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
			//added by a-3045 for CR HA16 on 27May09 starts
			//added by A-3278 for JetBlue31-1 on 23Apr10
			form.setBillingPeriod(BLANK);
			form.setDueDateBasis(BLANK);
			form.setBillingPeriodDescription(BLANK);
			//JB31-1 ends
			
			// Added By A-5183 FOR ICRD-18283 Starts
			
			form.setAgent(BLANK);
			form.setCustomer(BLANK);
			form.setCccollector(BLANK);
			form.setVatRegNumber(BLANK);			
			form.setAirportCode(BLANK);
			form.setVatRegNumber(BLANK);
			form.setAircraftTypeHandled(BLANK);
			form.setAircraftTypeHandledList(null);
			form.setDateOfExchange(BLANK);    			
			form.setCassBillingIndicator(false);
			form.setCassCode(BLANK);    			
			form.setBillingThroughInterline(false);    			
			form.setAirlineCode(BLANK);    			
			//form.setBillingCurrency(BLANK);
			//form.setBillingPeriodDescription(BLANK);
			form.setIataAgentCode(BLANK);
			form.setAgentInformation(BLANK);    			
			form.setCassIdentifier(BLANK);
			//CRQ ID:117235 - A-5127 added
			form.setRecipientCode(BLANK);
			form.setCassImportIdentifier(BLANK);
			//End - CRQ ID:117235 - A-5127 added
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
			/*Added by A-7567 as part of ICRD-305684*/
			form.setCntLocBillingApplicableTo(BLANK);
			form.setEnduranceFlag("");
			form.setEndurancePercentage(BLANK);
			form.setEnduranceValue(BLANK);
			form.setEnduranceMaxValue(String.valueOf(BLANK));
			form.setSettlementCurrency(BLANK);
			form.setRemarks(BLANK);
			form.setFromDate(BLANK);
			form.setToDate(BLANK);
			form.setCustomerType(BLANK);
			form.setBillingremark(BLANK);
			//Added by A-5169 for ICRD-31552 on 30-APR-2013 
			form.setCustomsLocationNo(BLANK); 			
			
			form.setBankbranch(BLANK);
			form.setBankcityname(BLANK);
			form.setBankcode(BLANK);
			form.setBankcountryname(BLANK);
			form.setBankpincode(BLANK);
			form.setBankstatename(BLANK);
			form.setResave(BLANK);
			//form.setValidateflag(BLANK);
			form.setMiscDetailCode(null);
			form.setMiscDetailOpFlag(null);
			form.setMiscDetailRemarks(null);
			form.setMiscDetailValidFrom(null);
			form.setMiscDetailValidTo(null);
			form.setMiscDetailValue(null);
			form.setMiscSeqNum(null);
			form.setMisopcheck(null);
			// Added By A-5183 FOR ICRD-18283 Ends
			//Added by A-5807 for ICRD-73246
			form.setRestrictedFOPs(null);
			//Added for ICRD-33334 by A-5163 Starts
			form.setInvoiceClubbingIndicator(null);
			//Added for ICRD-33334 by A-5163 Ends
			
			//Added for ICRD-67442 by A-5163 starts
			session.setCustomerDetailVOsInMap(null);
			form.setVersionNumbers(null);
			form.setNavVersionNumbers(null);
			form.setCusVersionNumber(BLANK);
			form.setIsHistoryPresent(BLANK);
			form.setIsHistoryPopulated(BLANK);
			form.setDisplayPopupPage(BLANK);
			form.setTotalViewRecords(BLANK);
			form.setIsLatestVersion(BLANK);
	    	//Added for ICRD-67442 by A-5163 ends
			form.setExcludeRounding(BLANK);
			form.setInternalAccountHolder(BLANK);
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.info.updated");
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			log.log(Log.FINE, "error-----", error);
			errors.add(error);
			invocationContext.addAllError(errors);
			//added by a-6314 for ICRD-130084
			form.setInvoiceType(null);
			form.setCustomerCertificateType(null);// Added by A-8832 as part of IASCB-4139
			/*Added by 201930 for IASCB-131790*/
			form.setCassCountry(null);
			invocationContext.target = SAVE_SUCCESS;
		}
		
		if (session.getValidationErrors() != null
				&& !session.getValidationErrors().isEmpty()) {
			if(SOURCE_SAVE.equals(session.getSourcePage())){
				session.removeValidationErrors();
				session.setSourcePage(null);
				form.setSourcePage(null);
			}	
		}
		
	}

	private boolean validateOps(Collection<CustomerAgentVO> customerAgentVOs) {
		Collection<CustomerAgentVO> agentVOs=  null;
		if (CollectionUtils.isNotEmpty(customerAgentVOs)) {
			agentVOs = customerAgentVOs.stream()
					.filter(agentVO -> !(Objects.equals(agentVO.getOperationFlag(), AbstractVO.OPERATION_FLAG_DELETE)))
					.collect(Collectors.toList());
			int index1 = 0;
			for (CustomerAgentVO agentVO : agentVOs) {
				int index2 = 0;
				for (CustomerAgentVO cusAgentVO : agentVOs) {
					if (!(Objects.equals(index1, index2))) {
							if ((Objects.equals(agentVO.getExportFlag(), AbstractVO.FLAG_YES)
									&& Objects.equals(agentVO.getExportFlag(), cusAgentVO.getExportFlag()))
									|| (Objects.equals(agentVO.getImportFlag(), AbstractVO.FLAG_YES)
											&& Objects.equals(agentVO.getImportFlag(), cusAgentVO.getImportFlag()))
									|| (Objects.equals(agentVO.getSalesFlag(), AbstractVO.FLAG_YES)
											&& Objects.equals(agentVO.getSalesFlag(), cusAgentVO.getSalesFlag()))){
								return true;
							}
					}
					index2++;
				}
				index1++;
			}
		}
		return false;
	}
	private Boolean validateDeliverySlot(String time) {
		Matcher matcher = null;
		Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
		matcher = pattern.matcher(time);
		 return matcher.matches();
	
		
	}

	/**
	 * 
	 * 	Method		:	SaveCustomerCommand.validateCustomerCertificates
	 *	Added by 	:	A-5163 on January 5, 2015
	 * 	Used for 	:	Added for ICRD-58628.
	 *	Parameters	:	@param certificateDetails
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param form
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateCustomerCertificates(Collection<CustomerCertificateVO> certificateDetails,
			String companyCode, MaintainRegCustomerForm form) {
		log.entering("SaveCustomerCommand", "validateCustomerCertificates");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();		
		List<String> certificateTypesKnown = new ArrayList<String>(
				Arrays.asList(new String[] { "KCC", "SRJ", "SDQ", "SAA", "SUA", "KSH" }));		
		List<String > certificateTypes = new ArrayList<String>(certificateDetails.size());		
		//Added for ICRD-112552 by A-5163 starts
		List<String> certificateNumbers = new ArrayList<String>(certificateDetails.size());
		//Added for ICRD-112552 by A-5163 ends
		for (CustomerCertificateVO customerCertificateVO : certificateDetails) {			
			if (customerCertificateVO != null
					&& customerCertificateVO.getCertificateType() != null
					&& !CustomerMiscDetailsVO.OPERATION_FLAG_DELETE.equals(customerCertificateVO.getOperationFlag())) {
				certificateTypes.add(customerCertificateVO.getCertificateType());
				//Added for ICRD-112552 by A-5163 starts
				if (customerCertificateVO.getCertificateNumber() != null
						&& customerCertificateVO.getCertificateNumber().trim().length() > 0) {
				if (certificateNumbers.contains(customerCertificateVO.getCertificateNumber())){
					errors.add(new ErrorVO(DUPLICATE_CERTIFICATE_NUMBERS));
				} else {
					certificateNumbers.add(customerCertificateVO.getCertificateNumber());
				}
				}/*else if(!certificateNumInvalid){//Added by A-4772 for ICRD-160726 starts here
					//added by A-6344 for ICRD-128802 starts
					errors.add(new ErrorVO(INVALID_CERTIFICATE_NUMBERS));
					certificateNumInvalid=true;
					//added by A-6344 for ICRD-128802 ends
				}	*/	//Added by A-4772 for ICRD-160726 ends here
				
				//Added for ICRD-112552 by A-5163 ends
				
			}
		}
		if (!certificateTypes.isEmpty()) {
			if (certificateTypes.size() > 1) {
			Set<String> uniqueCertificates = new HashSet<String>();
			uniqueCertificates.addAll(certificateTypes);
			if (uniqueCertificates.size() != certificateTypes.size()) {				
				errors.add(new ErrorVO(DUPLICATE_CERTIFICATE_DETAILS));
			}
			boolean knownShipperPresent = false;
			for (CustomerCertificateVO certificateVO : certificateDetails) {
				if (certificateTypesKnown.contains(certificateVO.getCertificateType())) {
					if (knownShipperPresent) {						
						errors.add(new ErrorVO(CANNOT_EXIST_TOGETHER_DUPLICATE, 
								new Object[] {CERTIFICATE_TYPE_KNOWN_SHIPPER}));						
					}
					knownShipperPresent = true;
				}
			}
			if (certificateTypes.contains(CERTIFICATE_TYPE_INDIRECT_AIR_CARRIER)) {
				if (knownShipperPresent) {					
					errors.add(new ErrorVO(CANNOT_EXIST_TOGETHER, 
							new Object[] {INDIRECT_AIR_CARRIER, CERTIFICATE_TYPE_KNOWN_SHIPPER}));
				} else if (certificateTypes.contains(CERTIFICATE_TYPE_DECLARED_KNOWN)) {					
					errors.add(new ErrorVO(CANNOT_EXIST_TOGETHER, 
							new Object[] {INDIRECT_AIR_CARRIER, DECLARED_KNOWN}));
				}
			}
			if (certificateTypes.contains(CERTIFICATE_TYPE_DECLARED_KNOWN)	&& knownShipperPresent) {				
				errors.add(new ErrorVO(CANNOT_EXIST_TOGETHER, 
						new Object[] {DECLARED_KNOWN, CERTIFICATE_TYPE_KNOWN_SHIPPER}));
				}
			}
			for (CustomerCertificateVO certificateVO : certificateDetails) {
				if (certificateVO.getValidityEndDate() != null
						&& !certificateVO.getValidityEndDate().toString().isEmpty()
						&& certificateVO.getValidityStartDate() != null
						&& !certificateVO.getValidityStartDate().toString().isEmpty()) {
					if (certificateVO.getValidityStartDate().isGreaterThan(certificateVO.getValidityEndDate())) {
						errors.add(new ErrorVO(ERR_CODE_INVALID_DATERANGE));
						break;
					}				
				}
			}
		}
		log.exiting("SaveCustomerCommand", "validateCustomerCertificates");
		return errors;
	}

	/**
	 * @param station
	 * @param logonAttributes
	 * @return errors
	 */
	/*
	 * public Collection<ErrorVO> validateAirportCodes( String station,
	 * LogonAttributes logonAttributes){
	 *
	 *
	 * Collection<ErrorVO> errors = null;
	 *
	 * try { AirportDelegate delegate = new AirportDelegate();
	 * delegate.validateAirportCode( logonAttributes.getCompanyCode(),station); }
//printStackTrrace()(); errors =
	 * handleDelegateException(e); } return errors; }
	 */
	/**
	 * @param form
	 * @param companyCode
	 * @return Collection<ErrorVO>
	 */

	private Collection<ErrorVO> validateStationCode(
			MaintainRegCustomerForm form, String companyCode, CustomerVO customerVO) {
		log.entering("SaveCustomerCommand", "validateStationCode");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		AreaValidationVO areaValidationVO = null;
		try {
			AreaDelegate delegate = new AreaDelegate();

			areaValidationVO = delegate.validateLevel(logonAttributes
					.getCompanyCode().toUpperCase(), "STN", form.getStation()
					.toUpperCase());
			/**
			 * Added by A-5198 for ICRD-56507
			 * Setting cityCode to CustomerVo for generating Customer Code
			 */
			if(areaValidationVO != null && areaValidationVO.getCityCode() != null){
				customerVO.setCityCode(areaValidationVO.getCityCode());
			}
		} catch (BusinessDelegateException e) {

			errors = handleDelegateException(e);
		}
		log.exiting("SaveCustomerCommand", "validateStationCode");
		return errors;
	}

	/**
	 * method for validating country
	 *
	 * @param form
	 * @param companyCode
	 * @return
	 */
	private Collection<ErrorVO> validateCountry(String country,
			String companyCode) {
		log.entering("SaveCustomerCommand", "validateCountry");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		AreaValidationVO areaValidationVO = null;
		try {
			AreaDelegate delegate = new AreaDelegate();

			areaValidationVO = delegate.validateLevel(logonAttributes
					.getCompanyCode().toUpperCase(), "CNT", country
					.toUpperCase());
		} catch (BusinessDelegateException e) {

			errors = handleDelegateException(e);
		}
		log.exiting("SaveCustomerCommand", "validateCountry");
		return errors;
	}
/**
 *
	 * 	Method		:	SaveCustomerCommand.validateCustomerMiscDetailsVos
	 *	Added by 	:	A-5198 on 17-Feb-2014 for ICRD-63059
	 * 	Used for 	:	
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@param customerVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 *
	 *	Modified by A-5165 for ICRD64121, Renamed the method
	 *	Changed Bank details from Agent to Customer
	 * @param session 
	 * @param form 
	 */
	public Collection<ErrorVO> validateCustomerMiscDetailsVos(Collection<CustomerMiscDetailsVO> initialCustomerMiscValues, CustomerVO customerVO, MaintainRegCustomerForm form){
		log.entering("SaveCustomerCommand", "validateCustomerMiscDetailsVos");
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);
		Collection<OneTimeVO> stateCodes=session.getOneTimeValues().get(STATECODE_ONETIME);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = false;
		if(customerVO.getCustomerMiscValDetailsVO() != null) {
			for(CustomerMiscDetailsVO cusMiscDetailsVO : customerVO.getCustomerMiscValDetailsVO()) {			
				if(cusMiscDetailsVO.getMiscValidFrom() != null && cusMiscDetailsVO.getMiscValidTo() != null
						&& !CustomerMiscDetailsVO.OPERATION_FLAG_DELETE.equals(cusMiscDetailsVO.getOperationFlag())) {

					//Validation for from date> to date
					if(cusMiscDetailsVO.getMiscValidFrom().isGreaterThan(cusMiscDetailsVO.getMiscValidTo())) {
						error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.fromDateGreaterThanToDate",
								new Object[] {cusMiscDetailsVO.getMiscValidFrom().toDisplayDateOnlyFormat(),
								cusMiscDetailsVO.getMiscValidTo().toDisplayDateOnlyFormat()});
						errors.add(error); 
					}


				}
				if("STECOD".equalsIgnoreCase(cusMiscDetailsVO.getMiscCode())){
					isValid = false;
					for(OneTimeVO oneTimeVO:stateCodes){
						if(oneTimeVO.getFieldValue().equals(cusMiscDetailsVO.getMiscValue())){
							isValid = true;
							break;
						}
					}
						if(!isValid){
							error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.invalidstatecode",
									new Object[] {cusMiscDetailsVO.getMiscValue()});
							errors.add(error); 
						}
				}
			}
		}
		/*
		 * Following validations are done on the 
		 */

		/*
		 * If its and exisiting customer update , 
		 * taking customerMiscDetails from session for validation between form and initial Vo values from session	 	
		 */
		if(OPERATION_FLAG_UPDATE.equals(customerVO.getOperationFlag())){

			Collection<ErrorVO> errorVOs=vaildateUpdateMiscDetail(initialCustomerMiscValues,form);
			if(errorVOs!=null && errorVOs.size()>0){					
				errors.addAll(errorVOs);					
			}
		}	
		/*
		 * This method validate the overlapping of date ranges for newly inserted row and updated rows.
		 * This method also check the deletion validation of each records. * 
		 */
		Collection<ErrorVO> errorVOs=vaildateMiscDetailDateOverlap(form);
		if(errorVOs!=null && errorVOs.size()>0){					
			errors.addAll(errorVOs);					
		}
		log.exiting("SaveCustomerCommand", "validateCustomerMiscDetailsVos");
		return errors;
	}
	
/**
 * @author A-5165
 * Used for ICRD-64121
 * This method is for validating the bank and other detils based on date before saving the details
 * This method validate the overlapping of date ranges for newly inserted row and updated rows.
 * This method also check the deletion validation of each records. *  	
 * @param form
 * @return
 */
	private Collection<ErrorVO> vaildateMiscDetailDateOverlap(
			MaintainRegCustomerForm form) {

	log.entering("SaveCustomerCommand", "vaildateMiscDetailDateOverlap");
	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes = applicationSession.getLogonVO();
	LocalDate currentDate = new LocalDate(logonAttributes.getStationCode(),
			Location.STN, false);
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	// Added under BUG_ICRD-82963_AiynaSuresh_31Aug2014 starts
	LocalDate frmDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
	frmDate.setDate(form.getFromDate());
	LocalDate toDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
	toDate.setDate(form.getToDate());
	StringBuilder datesOutsideAgentValidity = null;
	StringBuilder overlappingDates = null;
	// Added under BUG_ICRD-82963_AiynaSuresh_31Aug2014 ends
	Boolean errorFound=false;
	for (int i = 0; i < (form.getMiscDetailOpFlag().length) - 1; i++) {
		/*if (errors != null && errors.size() > 0) {
			break;
		}*/		
		// outerloop
		if (form.getMiscDetailOpFlag()[i] != null
				&& !"NOOP".equals(form.getMiscDetailOpFlag()[i])) {
			/*String fromdate = form.getMiscDetailValidFrom()[i];
			String todate = form.getMiscDetailValidTo()[i];*/
			
			if(("GSTRN").equals(form.getMiscDetailCode()[i]))
			{
				if(form.getMiscDetailValidFrom()[i]==null || "".equals(form.getMiscDetailValidFrom()[i]) || form.getMiscDetailValidTo()[i]==null || "".equals(form.getMiscDetailValidTo()[i])){
					errors.add(new ErrorVO(
							FROMDATE_TODATE_MANDATORY));
					break;
					}
				
			}
			else if(("TRNNO").equals(form.getMiscDetailCode()[i]) && !errorFound){
				
				if(form.getMiscDetailValue()[i] == null || form.getMiscDetailValue()[i].trim().length() == 0 ) {
					//isValidFromDate = false;
					//error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.valuemandatory");
					errors.add(new ErrorVO(
							VALUE_MANDATORY));
					 errorFound=true;
				}
				if(form.getMiscDetailValidFrom()[i] == null || form.getMiscDetailValidFrom()[i].trim().length() == 0 ||  form.getMiscDetailValidTo()[i] == null || form.getMiscDetailValidTo()[i].trim().length() == 0) {
					//isValidToDate = false;
					//error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.datemandatory");
					errors.add(new ErrorVO(
							FROMDATE_TODATE_MANDATORY));
					 errorFound=true;
				} 
			}
			else{
			LocalDate validfromfirst = new LocalDate(
					logonAttributes.getStationCode(), Location.STN, false);
			LocalDate validtofirst = new LocalDate(
					logonAttributes.getStationCode(), Location.STN, false);
		if(form.getMiscDetailValidFrom()[i]!=null && form.getMiscDetailValidFrom()[i].trim().length()>0){
			validfromfirst.setDate(form.getMiscDetailValidFrom()[i]);
		}
		if(form.getMiscDetailValidTo()[i]!=null && form.getMiscDetailValidTo()[i].trim().length()>0){
			validtofirst.setDate(form.getMiscDetailValidTo()[i]);
		}
			
			
			// validation for deletion of rows
			if (AbstractVO.OPERATION_FLAG_DELETE.equals(form.getMiscDetailOpFlag()[i])) {
				if (validtofirst.isLesserThan(currentDate)
						|| validfromfirst.isLesserThan(currentDate)
						|| validfromfirst.equals(currentDate)
						|| validtofirst.equals(currentDate)) {
					errors.add(new ErrorVO(
							CANNOT_DELETE_RECORD));
					break;
				}
			}
			// validation for newly inserted row
			if (AbstractVO.OPERATION_FLAG_INSERT.equals(form.getMiscDetailOpFlag()[i])) {
				if (validtofirst.isLesserThan(currentDate)
						|| validfromfirst.isLesserThan(currentDate)) {
					errors.add(new ErrorVO(
							DATELESS_THAN_CURRENTDATE));
					break;
				}
			}
			// Added under BUG_ICRD-82963_AiynaSuresh_31Aug2014 starts
			if(validfromfirst.isLesserThan(frmDate) || 
					validtofirst.isGreaterThan(toDate)){
				if(datesOutsideAgentValidity == null){
					datesOutsideAgentValidity = new StringBuilder().append(i+1);
				}else{
					datesOutsideAgentValidity.append(",").append(i+1);
				}			
			}
			// Added under BUG_ICRD-82963_AiynaSuresh_31Aug2014 ends
		/*	if (validfromfirst.isGreaterThan(validtofirst)) {
				errors.add(new ErrorVO(
						"shared.agent.maintainagentmaster.fromdategreaterthantodate"));
				break;
			}*/
			// innerloop
			for (int j = i + 1; j < (form.getMiscDetailOpFlag().length) - 1; j++) {
				// errors = vaildateDatePresent(fromdates, todates);
				if (errors != null && errors.size() > 0) {
					break;
				}
				if (form.getMiscDetailOpFlag()[j] != null
						&& !"NOOP".equals(form.getMiscDetailOpFlag()[j])) {
					if(("GSTRN").equals(form.getMiscDetailCode()[i]))
					{
						if(form.getMiscDetailValidFrom()[i]==null || "".equals(form.getMiscDetailValidFrom()[i]) || form.getMiscDetailValidTo()[i]==null || "".equals(form.getMiscDetailValidTo()[i])){
							errors.add(new ErrorVO(
									FROMDATE_TODATE_MANDATORY));
							break;
							}
						
					}
					else{
					
					LocalDate validfromsecond = new LocalDate(
							logonAttributes.getStationCode(), Location.STN,
							false);
					LocalDate validtosecond = new LocalDate(
							logonAttributes.getStationCode(), Location.STN,
							false);
					if((form.getMiscDetailValidTo()[i]!=null && form.getMiscDetailValidTo()[i].trim().length()>0) &&
							(!(form.getMiscDetailValidTo()[j]==null || "".equals(form.getMiscDetailValidTo()[j]) ))){
						
					validtosecond.setDate(form.getMiscDetailValidTo()[j]);
						
					}
					if(form.getMiscDetailValidFrom()[i]!=null && form.getMiscDetailValidFrom()[i].trim().length()>0 &&
							(!(form.getMiscDetailValidFrom()[j]==null || "".equals(form.getMiscDetailValidFrom()[j]) ))){
					
					validfromsecond
							.setDate(form.getMiscDetailValidFrom()[j]);
						
					}
					// validation for deletion of rows
					if (AbstractVO.OPERATION_FLAG_DELETE.equals(form.getMiscDetailOpFlag()[j])) {
						if (validtosecond.isLesserThan(currentDate)
								|| validfromsecond
										.isLesserThan(currentDate)
								|| validfromsecond.equals(currentDate)
								|| validtosecond.equals(currentDate)) {
							errors.add(new ErrorVO(
									CANNOT_DELETE_RECORD));
							break;
						}
					}
					// validation for newly inserted row
					if (AbstractVO.OPERATION_FLAG_INSERT.equals(form.getMiscDetailOpFlag()[j])) {
						if (validtosecond.isLesserThan(currentDate)
								|| validfromsecond
										.isLesserThan(currentDate)) {
							errors.add(new ErrorVO(
									DATELESS_THAN_CURRENTDATE));
							break;
						}
					}
					/*if (validfromsecond.isGreaterThan(validtosecond)) {
						errors.add(new ErrorVO(
								"shared.agent.maintainagentmaster.fromdategreaterthantodate"));
						break;
					}*/

					// validation of records having the same miscdetailscode.
					
					if ((form.getMiscDetailCode()[i].equals(form
							.getMiscDetailCode()[j]))
							&& (!AbstractVO.OPERATION_FLAG_DELETE.equals(form.getMiscDetailOpFlag()[i]))
							&& (!AbstractVO.OPERATION_FLAG_DELETE.equals(form.getMiscDetailOpFlag()[j]))) {
						if (validtofirst.isGreaterThan(validtosecond)
								&& validfromfirst
										.isGreaterThan(validfromsecond)) {
							
								if ((validfromsecond
										.isGreaterThan(validtofirst))
										&& (validtosecond
												.isGreaterThan(validfromfirst))) {
									if(overlappingDates == null){
										overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
									}else{
										overlappingDates.append(",").append(i+1).append("-").append(j+1);
									}
									
								}
							
						}
						if (validtofirst.isLesserThan(validtosecond)
								&& validfromfirst
										.isGreaterThan(validfromsecond)) {
							if(overlappingDates == null){
								overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
							}else{
								overlappingDates.append(",").append(i+1).append("-").append(j+1);
							}
						}
						if (validtofirst.isLesserThan(validtosecond)
								&& validfromfirst
										.isLesserThan(validfromsecond)) {
							if (validfromfirst.isGreaterThan(validtosecond)) {
								if(overlappingDates == null){
									overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
								}else{
									overlappingDates.append(",").append(i+1).append("-").append(j+1);
								}
							}
							if (validfromsecond.isLesserThan(validtofirst)) {
								if(overlappingDates == null){
									overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
								}else{
									overlappingDates.append(",").append(i+1).append("-").append(j+1);
								}
							}
						}
						if ((validfromfirst.isGreaterThan(validfromsecond))
								&& (validtofirst
										.isGreaterThan(validtosecond))) {
							if (validtosecond.isGreaterThan(validfromfirst)) {
								if(overlappingDates == null){
									overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
								}else{
									overlappingDates.append(",").append(i+1).append("-").append(j+1);
								}
							}
						}
						if (validtofirst.isGreaterThan(validtosecond)
								&& validfromfirst
										.isLesserThan(validfromsecond)) {
							if(overlappingDates == null){
								overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
							}else{
								overlappingDates.append(",").append(i+1).append("-").append(j+1);
							}
						}

						if (validtofirst.equals(validtosecond)
								&& validfromfirst.equals(validfromsecond)) {
							if(overlappingDates == null){
								overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
							}else{
								overlappingDates.append(",").append(i+1).append("-").append(j+1);
							}
						}

						if (validtofirst.equals(validtosecond)
								|| validfromfirst.equals(validfromsecond)) {
							if(overlappingDates == null){
								overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
							}else{
								overlappingDates.append(",").append(i+1).append("-").append(j+1);
							}
						}

						if (validtofirst.equals(validfromsecond)
								|| validtofirst.equals(validtosecond)) {
							if(overlappingDates == null){
								overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
							}else{
								overlappingDates.append(",").append(i+1).append("-").append(j+1);
							}
						}

						if (validfromfirst.equals(validfromsecond)
								|| validfromfirst.equals(validtosecond)) {
							if(overlappingDates == null){
								overlappingDates = new StringBuilder().append(i+1).append("-").append(j+1);
							}else{
								overlappingDates.append(",").append(i+1).append("-").append(j+1);
							}
						}
					}
				}
			}
		}
	}
	}
}
	// Added under BUG_ICRD-82963_AiynaSuresh_31Aug2014 starts
	if(datesOutsideAgentValidity != null){
							errors.add(new ErrorVO(
				ERR_DATE_OUTSIDE_AGENT_VALIDITY, new Object[]{datesOutsideAgentValidity}));
	}
	if(overlappingDates != null){
		errors.add(new ErrorVO(OVERLAPPDATE,new Object[]{overlappingDates.toString()}));
	}
	// Added under BUG_ICRD-82963_AiynaSuresh_31Aug2014 ends
	log.exiting("SaveCustomerCommand", "vaildateMiscDetailDateOverlap");
	return errors;

	}
/**
 * @author A-5165
 * Used for ICRD-64121, 
 * for validating update on Expired records;
 * @param initialCustomerMiscValues
 * @param form
 * @return 
 */
private Collection<ErrorVO> vaildateUpdateMiscDetail(
			Collection<CustomerMiscDetailsVO> initialCustomerMiscValues,
			MaintainRegCustomerForm form) {

	log.entering("SaveCustomerCommand", "vaildateUpdateMiscDetail");
	Collection<ErrorVO> errors = null;

	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
			Location.NONE, false);
	LocalDate validfrom = new LocalDate(LocalDate.NO_STATION,
			Location.NONE, false);
	LocalDate validto = new LocalDate(LocalDate.NO_STATION, Location.NONE,
			false);

	LocalDate miscValidFrom;
	LocalDate miscValidTo;
	String miscValue;
	//Validating update on Expired data
		if (initialCustomerMiscValues != null
				&& initialCustomerMiscValues.size()>0) {

			errors = new ArrayList<ErrorVO>();

			for (CustomerMiscDetailsVO cusMiscDetailsVO : initialCustomerMiscValues) {
				for (int i = 0; i < (form.getMiscDetailOpFlag().length) - 1; i++) {
					if ("U".equals(form.getMiscDetailOpFlag()[i])) {
						int sequencenumber = Integer.parseInt(form
								.getMiscSeqNum()[i]);
						if(("GSTRN").equals(form.getMiscDetailCode()[i]))
						{
							if(form.getMiscDetailValidFrom()[i]==null || "".equals(form.getMiscDetailValidFrom()[i]) || form.getMiscDetailValidTo()[i]==null || "".equals(form.getMiscDetailValidTo()[i])){
								errors.add(new ErrorVO(
										FROMDATE_TODATE_MANDATORY));
								break;
							}
							
						}
						else{
							if(form.getMiscDetailValidFrom()[i]!=null && form.getMiscDetailValidFrom()[i].trim().length()>0){
						validfrom.setDate(form.getMiscDetailValidFrom()[i]);
							}
							if(form.getMiscDetailValidTo()[i]!=null && form.getMiscDetailValidTo()[i].trim().length()>0){
						validto.setDate(form.getMiscDetailValidTo()[i]);
							}
						if (cusMiscDetailsVO.getSequenceNumber() == sequencenumber) {
							if (cusMiscDetailsVO.getMiscCode().equals(
									form.getMiscDetailCode()[i])) {

								/*
								 * validation for updation of expired record
								 * System shall restrict modification of expired
								 * set up.
								 */

								miscValidFrom = cusMiscDetailsVO
										.getMiscValidFrom();
								miscValidTo = cusMiscDetailsVO.getMiscValidTo();
								miscValue = cusMiscDetailsVO.getMiscValue();
								if ((miscValidFrom != null && miscValidFrom
										.isLesserThan(currentDate))
										&& (miscValidTo != null && miscValidTo
												.isLesserThan(currentDate))) {
									if (!miscValidFrom.equals(validfrom)
											|| !miscValidTo.equals(validto)
											|| !cusMiscDetailsVO
													.getMiscCode()
													.equals(form
															.getMiscDetailCode()[i])
											|| (miscValue != null && !miscValue
													.equals(form
															.getMiscDetailValue()[i]))) {
										errors.add(new ErrorVO(
												CANNOT_UPDATE_RECORD));
										break;
									}

									// if remarks is already present
									// modified after code review
									if (cusMiscDetailsVO.getMiscRemarks() != null
											&& cusMiscDetailsVO
													.getMiscRemarks().trim()
													.length() > 0) {
										if (form.getMiscDetailRemarks()[i] != null
												&& form.getMiscDetailRemarks()[i]
														.trim().length() > 0
												&& !cusMiscDetailsVO
														.getMiscRemarks()
														.equals(form
																.getMiscDetailRemarks()[i])) {
											errors.add(new ErrorVO(
													CANNOT_UPDATE_RECORD));
											break;
										}
										if (form.getMiscDetailRemarks()[i] == null
												|| form.getMiscDetailRemarks()[i]
														.trim().length() <= 0) {
											errors.add(new ErrorVO(
													CANNOT_UPDATE_RECORD));
											break;
										}
									}
									// if remarks is already absent
									if (cusMiscDetailsVO.getMiscRemarks() == null) {
										if (form.getMiscDetailRemarks()[i] != null
												&& form.getMiscDetailRemarks()[i]
														.trim().length() > 0) {
											errors.add(new ErrorVO(
													CANNOT_UPDATE_RECORD));
											break;
										}
									}
								}
								// validation for update of still valid records
								
								if (miscValidFrom!=null && miscValidTo!=null 
										&& (miscValidFrom.isLesserThan(currentDate) || miscValidFrom.equals(currentDate))
										&& (miscValidTo.isGreaterThan(currentDate) || miscValidTo.equals(currentDate))) {
								/*	if ( !miscValidFrom.equals(validfrom) ||
											!cusMiscDetailsVO.getMiscCode().equals(form.getMiscDetailCode()[i]) || 
											(miscValue!=null && !miscValue.equals(form.getMiscDetailValue()[i]))) {
										errors.add(new ErrorVO(
												"shared.agent.maintainagentmaster.cannotupdatestillvalidrecord"));
										break;
									}*/
									if (validto.isLesserThan(currentDate)) {
										errors.add(new ErrorVO(
												DATELESS_THAN_CURRENTDATE));
										break;
									}
								}
								// validation for update of future records
								if ((miscValidFrom!=null && (miscValidFrom.isGreaterThan(currentDate))) &&
										 (miscValidTo!=null && (miscValidTo.isGreaterThan(currentDate)))) {
									if (validfrom.isLesserThan(currentDate) || 
											validto.isLesserThan(currentDate)) {
										errors.add(new ErrorVO(
												DATELESS_THAN_CURRENTDATE));
										break;
									}
									
								}
							}
						}
					}
				}
			}
		}
	}
		log.exiting("SaveCustomerCommand", "vaildateUpdateMiscDetail");
	return errors;
}

/**
 *
 * @param form
 * @param logonAttributes
 * @return
 */
	public Collection<ErrorVO> validateForm(MaintainRegCustomerForm form,
			LogonAttributes logonAttributes,String billingNeededParameter) {
		MaintainCustomerRegistrationSession session = getScreenSession(
				MODULENAME, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		
		//Added for ICRD-39214	by A-5165
		Collection<String> sysParList = new ArrayList<String>();
		sysParList.add(SYSPAR_CRA_PRESENT);
		Map<String,String> paremeterMap=fetchSystemParameterDetail(sysParList);
		String isCraPresent=paremeterMap.get(SYSPAR_CRA_PRESENT);
		
		if(CustomerVO.FLAG_YES.equals(isCraPresent)){			
			if (BLANK.equals(form.getBillingCode())
					|| (form.getBillingCode() != null && form.getBillingCode().trim()
							.length() == 0)) {
				error = new ErrorVO(
				"customermanagement.defaults.customerregn.msg.err.enterbillingcode");
				errors.add(error);
			}			
			
			if (BLANK.equals(form.getBillingPeriod())
					|| (form.getBillingPeriod() != null && form.getBillingPeriod().trim()
							.length() == 0)) {
				if(!BOTH.equals(form.getCassIdentifier())){
					error = new ErrorVO(
					"customermanagement.defaults.customerregn.msg.err.enterbillingperiod");
					errors.add(error);
				}
			}					
		}
		/*As per XSD, normal comm and tolerance % should be between 0 and 100. If value higher than 100 is 
		given the master data publish shows encoding failed
		Added by A-7896 as part of ICRD-297925*/
		if(form.getNormalComm() != null &&  form.getNormalComm().trim().length()>0){
		 if(Double.parseDouble(form.getNormalComm())>100){
			error = new ErrorVO("customermanagement.defaults.msg.err.invalidNormalCommPrc");
			errors.add(error);
		 }
		}
		if(form.getEndurancePercentage() != null &&  form.getEndurancePercentage().trim().length()>0){
		if(Double.parseDouble(form.getEndurancePercentage())>100){
			error = new ErrorVO("customermanagement.defaults.msg.err.invalidTolerancePrc");
			errors.add(error);
		 }
		}
		//Added for ICRD-39214 ends
		
		if (BLANK.equals(form.getStation())
				|| (form.getStation() != null && form.getStation().trim()
						.length() == 0)) {
			error = new ErrorVO(
					"customermanagement.defaults.customerregn.msg.err.enterstation");
			errors.add(error);
		}
		if (BLANK.equals(form.getCustName())
				|| (form.getCustName() != null && form.getCustName().trim()
						.length() == 0)) {
			error = new ErrorVO(
					"customermanagement.defaults.customerregn.msg.err.entercustname");
			errors.add(error);
		}
		/*if (BLANK.equals(form.getAddress1())
				|| (form.getAddress1() != null && form.getAddress1().trim()
						.length() == 0)) {
			error = new ErrorVO(
					"customermanagement.defaults.customerregn.msg.err.enteradr1");
			errors.add(error);
		}*/
		
		if(form.getInvoiceToCustomer()!=null 
				&& form.getInvoiceToCustomer().trim().length()>0
				&& CustomerVO.FLAG_YES.equalsIgnoreCase(form.getInvoiceToCustomer())){
			
			if(form.getBillingCode()==null || form.getBillingCode().trim().length()==0){
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterbillcode");
				errors.add(error);
			}else{
				try{
					new CurrencyDelegate().validateCurrency(
							logonAttributes.getCompanyCode(),form.getBillingCode().toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					errors.addAll(handleDelegateException(businessDelegateException));
				}
			}
			if(form.getBillingPeriod()==null || form.getBillingPeriod().trim().length()==0){
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterbillprd");
				errors.add(error);
			}
		}
		
		// Added By A-5198 For ICRD-63059
		validateDateRanges(errors, logonAttributes, form.getFromDate(), form.getToDate());
		
		
		if(form.getCustomerType()==null || form.getCustomerType().trim().length()==0){
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.entercusttype");
			errors.add(error);
		}
		//Added by a-6314 as part of ICRD-112496 - to set E as default Invoice Type while creating a new customer -starts
//		int errorCount=0;
//		for(String count  : form.getInvoiceType()){
//			if(count== EMAIL ||  count== PAPER)
//			{
//				errorCount++;
//			}
//		}
//		if(errorCount==0){
//			form.setInvoiceType(new String[]{EMAIL});								
//		}
		//Added by a-6314 as part of ICRD-112496 ends
	
		
		// modified by A-4781 for ICRD-32918
		if(form.getCity()==null || form.getCity().trim().length()==0){
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterCity");
			errors.add(error);
		}
		
		if(form.getCountry()==null || form.getCountry().trim().length()==0){
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterCountry");
			errors.add(error);
		}
				
		// NEED TO CHANGE 
		/*if(form.getBillingCode()==null || form.getBillingCode().trim().length()==0){
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterbillcode");
			errors.add(error);
		}else{
			try{
				new CurrencyDelegate().validateCurrency(
						logonAttributes.getCompanyCode(),form.getBillingCode().toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				errors.addAll(handleDelegateException(businessDelegateException));
			}
		}
		
		if(form.getBillingPeriod()==null || form.getBillingPeriod().trim().length()==0){
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterbillprd");
			errors.add(error);
		}*/
		
		// mandatory fields ends
		
		// mandatory fields if CCCOL		
		
		if(CustomerVO.CUSTOMER_TYPE_CC.equals(form.getCustomerType())){ 
			
			if(form.getAirportCode()==null || form.getAirportCode().trim().length()==0){
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterAirportCode");
				errors.add(error);
			}
			
			if(form.getAircraftTypeHandled()==null || form.getAircraftTypeHandled().trim().length()==0){
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.enterAircraftTypeHandled");
				errors.add(error);
			}
			
			if(form.getDateOfExchange()==null || form.getDateOfExchange().trim().length()==0){
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.DateOfExchange");
				errors.add(error);
			}	
			
		}
		/*Commented as part of the bug ICRD-114124 ; 
		 * If for a agent  controlling loc checkbox is given, Error message 'Please enter Controlling Loc details' on 
		 * saving Controlling Loc in Customer Master.*/
		//Commented by A-8374 for ICRD-355362 starts
		/*if (CustomerVO.CUSTOMER_TYPE_AG.equals(form.getCustomerType())) {
			if (CHECKSTATUS.equals(form.getSellingLocation())) {//removed the add controlling location details check on selecting selling location by A-8940 for ICRD-114124 
				// removed the controlling location name check by A-5290 for ICRD-98089
				if (BLANK.equals(form.getIataCode())) {
					error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.ControllingLocationforCL");
					errors.add(error);
				}
			}
		}*/
		//Commented by A-8374 for ICRD-355362 ends
		//added a-5133 as part of bug ICRD-43339 starts ends	
		// Mandatory fields for Agent & the Business Validations
						
		if(CustomerVO.CUSTOMER_TYPE_AG.equals(form.getCustomerType())){    
			
			if(BLANK.equals(form.getIataAgentCode())){
					
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.IataAgentCode");
				errors.add(error);
				
			}
			else  if(form.getIataAgentCode().length()!=11 &&  !(BLANK.equals(form.getCassIdentifier()))){
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.IataAgentCodeshouldbe11incass");
				errors.add(error);
			}
			//added by A-6850 for ICRD-139076
			if(form.getCassImportIdentifier()!=null && form.getCassImportIdentifier().length()>0){//modified by a-5505 for the bug ICRD-141041
			if(form.getRecipientCode() == null || (BLANK.equals(form.getRecipientCode().trim()))){
				
				error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.RecipientCode");
				errors.add(error);
				
				}
			}
			//added by A-6850 for ICRD-139076 ends
			if("Y".equals(billingNeededParameter)){				
			
				if(CHECKSTATUS.equals(form.getProformaInv())){
								
					if(BOTH.equals(form.getCassIdentifier())){
						
						error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.ExportCASS");
						errors.add(error);					
					}				
					if(CHECKSTATUS.equals(form.getSalesReportAgent())){
						
						error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.SalesReportingAgent");
						errors.add(error);	
					}										
					
				}		
			}
		}
				
				
			
		if(form.getAirportCode().trim().length()!=0){
			String airport=form.getAirportCode().trim().toUpperCase();
			Collection<String> airports = new ArrayList<String>();
			airports.add(airport);
			try{
				new AreaDelegate().validateAirportCodes(logonAttributes.getCompanyCode(),airports);
			}catch(BusinessDelegateException businessDelegateException){
				
				errors.add(new ErrorVO("customermanagement.defaults.msg.err.invalidairportcode"));
				
			}
		}
		if(form.getAirlineCode().trim().length()!=0){			
			try {
				 new AirlineDelegate().validateAlphaCode(logonAttributes.getCompanyCode(), form.getAirlineCode().toUpperCase());
			} catch (BusinessDelegateException e) {				
				errors.add(new ErrorVO("customermanagement.defaults.msg.err.invalidairlinecode"));	
			}
		}
		
		//Currency Validation 
		if(form.getSettlementCurrency().trim().length()!=0){
			String currency=form.getSettlementCurrency().trim().toUpperCase();			
			try{
				new CurrencyDelegate().validateCurrency(logonAttributes.getCompanyCode(),currency);
			}catch(BusinessDelegateException businessDelegateException){
				errors.add(new ErrorVO("customermanagement.defaults.msg.err.invalidsettlementcurrency"));
				
			}
		}
		
		if(!BLANK.equals(form.getBillingCode())){				
			try{
				new CurrencyDelegate().validateCurrency(
						logonAttributes.getCompanyCode(),form.getBillingCode().toUpperCase());
			} catch (BusinessDelegateException businessDelegateException) {
				errors.addAll(handleDelegateException(businessDelegateException)); 
			}
		}	
			
			
		if("Y".equals(billingNeededParameter)){		
		
			if((CHECKSTATUS.equals(form.getSellingLocation())&& !(BLANK.equals(form.getIataCode())))||
					(CHECKSTATUS.equals(form.getControllingLocation())&& !(BLANK.equals(form.getIataCode())))	){			 
				CustomerFilterVO customerfiltervo = new CustomerFilterVO();
				CustomerVO customerVO = null;
				customerfiltervo.setCustomerCode(form.getIataCode().toUpperCase());
				customerfiltervo.setCompanyCode(logonAttributes.getCompanyCode());	
				
				try {
					customerVO = new CustomerDelegate().validateCustomer(customerfiltervo);
				} catch (BusinessDelegateException businessDelegateException) {				
					errors.add(new ErrorVO("customermanagement.defaults.msg.err.invalidControllingLocation"));
				}
				if(customerVO!=null){
				//Modified if condition For ICRD-71348 by A-5791
				if(form.getCustomerType()!=null 
						&& form.getCustomerType().equals(customerVO.getCustomerType())){	
						
						if(!customerVO.isControllingLocation()){
							Object errorObject[] = {form.getIataCode()};						
							errors.add(new ErrorVO("customermanagement.defaults.msg.err.notaControlingLocationCustomer",errorObject));
				    	}					
					}else{
						// Added for ICRD-35173 by A-5235
						HashMap<String, Collection<OneTimeVO>> custType=session.getOneTimeValues();
						Collection<OneTimeVO> statusValues = custType.get(CUSTOMER_TYPE_ONETIME);
						String customerType=null;
						for(OneTimeVO vo:statusValues){
							if(vo.getFieldValue().equals(form.getCustomerType())){
								customerType= vo.getFieldDescription();
							}
						}
						//added by a-6792 for ICRD-323236
						HashMap<String, Collection<OneTimeVO>> controlCustType=session.getOneTimeValues();
						Collection<OneTimeVO> controlValues = controlCustType.get(CONTROL_CUSTOMER_TYPE_ONETIME);
						String controlCustomerType=null;
						List<String> validCustomerType= new ArrayList<String> ();
						if(controlValues!=null){
							for(OneTimeVO vo:controlValues){
								validCustomerType.add(vo.getFieldValue()) ;
								if(vo.getFieldValue().equals(customerVO.getCustomerType())){
									controlCustomerType= vo.getFieldDescription();
								}
							}	
								if(controlCustomerType==null){
									Object errorObject[] = {validCustomerType};
									errors.add(new ErrorVO("customermanagement.defaults.msg.err.invalidControlingLocationType",errorObject));
								}
								
						}else{
							Object errorObject[] = {customerType};
							errors.add(new ErrorVO("customermanagement.defaults.msg.err.invalidControlingLocationType",errorObject));
						}
											
					}
						
					}
				// Added by A-3207 for ICRD-95548
				else{
			 			ErrorVO errorVO = new ErrorVO("customermanagement.defaults.msg.err.invalidControllingLocation");
			 			errors.add(errorVO);
				}
			}				
		
		}
		// Added By A-5183 For ICRD-18283
		
		/*Added by A-7567 as part of ICRD-305684*/
		if(form.getBillingTo().equals("C") && (BLANK.equals(form.getCntLocBillingApplicableTo()) || form.getCntLocBillingApplicableTo() == null)){
			ErrorVO errorVO = new ErrorVO("customermanagement.defaults.msg.err.invalidApplicableTo");
 			errors.add(errorVO);
		}
		
		return errors;
	}
	
	/**
	 * 
	 * 	Method		:	SaveCustomerCommand.validateDateRanges
	 *	Added by 	:	a-5198 on 18-Feb-2014 for ICRD-63059
	 * 	Used for 	:
	 *	Parameters	:	@param errors
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@param frmDate
	 *	Parameters	:	@param tDate 
	 *	Return type	: 	void
	 */
	private void validateDateRanges(Collection<ErrorVO> errors, LogonAttributes logonAttributes, String frmDate, String tDate) {
		log.entering("SaveCustomerCommand", "validateDateRanges");
		ErrorVO error = null;
		boolean isValidFromDate = true;
		boolean isValidToDate = true;
		if(frmDate == null || frmDate.trim().length() == 0) {
			isValidFromDate = false;
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.validfromDate");
			errors.add(error);
		}
		if(tDate == null || tDate.trim().length() == 0) {
			isValidToDate = false;
			error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.validtoDate");
			errors.add(error);
		}
		if(isValidFromDate && isValidToDate) {
			LocalDate fromDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
	   		fromDate.setDate(frmDate);
	   		LocalDate toDate = new LocalDate( logonAttributes.getStationCode(),Location.STN,false);
		   	toDate.setDate(tDate);
		   	
		   	if(fromDate.isGreaterThan(toDate)) {
		   		error = new ErrorVO("customermanagement.defaults.customerregn.msg.err.fromDateGreaterThanToDate",
		   				new Object[] {fromDate.toDisplayDateOnlyFormat(),
		   							  toDate.toDisplayDateOnlyFormat()});
		   		errors.add(error);
		   	}
		}
		log.exiting("SaveCustomerCommand", "validateDateRanges");
	}
	
	
	
	/**
	 * @author A-5165
	 * Used for ICRD-39214
	 * @param sysParList2
	 * @return
	 */
	private Map<String, String> fetchSystemParameterDetail(
			Collection<String> sysParList) {
		Map<String,String> sysParMap = new HashMap<String,String>();
		try{
			sysParMap = new SharedDefaultsDelegate().findSystemParameterByCodes(sysParList);
		}catch(BusinessDelegateException exception){
			handleDelegateException(exception);
		}
		return sysParMap;		
	}

	/**
	 *
	 * @param originalValue
	 * @param formValue
	 * @return
	 */
	private boolean hasValueChanged(String originalValue, String formValue) {
		if (originalValue != null) {
			if (originalValue.equals(formValue)) {
				return false;
			}

		}
		return true;
	}

/**
 *
 * @param contactVOs
 * @param form
 * @param customerVO
 * @return
 */
	public Collection<ErrorVO> updateKeyContactDetails(
			ArrayList<CustomerContactVO> contactVOs,
			MaintainRegCustomerForm form, CustomerVO customerVO) {

		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] code = form.getContactCode();
		String[] name = form.getContactName();
		String[] type = form.getContactTypes();
		String[] designation = form.getContactDesignation();
		String[] telephone = form.getContactTelephone();
		String[] mobile = form.getContactMobile();
		String[] fax = form.getContactFax();
		String[] sita = form.getContactSita();
		String[] email = form.getContactEmail();
		String[] remarks = form.getContactRemarks();
		String[] status = form.getCheckedStatus().split(",");
		String[] primaryContactStatus = form.getPrimaryContact().split(",");
		String[] hiddenOpContactFlag = form.getHiddenOpFlagForCustomer();
		/** Added the null check as aprt of FINDBUG code quality a-6770 */
		if (code != null) {
		for(String p:hiddenOpContactFlag){
			log.log(Log.FINE, "\n hiddenOpContactFlag*****", p);
		}
		for(String p:code){
			log.log(Log.FINE, "\n code*****", p);
		}
		
		for(String p:remarks){
			log.log(Log.FINE, "\n remarks*****", p);
		}
		
		log.log(Log.FINE, "**contactVOs Before*****", contactVOs);
		log.log(Log.FINE, "**customerVO Before *****", customerVO);
		int index = 0;
		for(int j=0;j<(hiddenOpContactFlag.length)-1;j++){
		for (CustomerContactVO contactVO : contactVOs) {
			if(index<((hiddenOpContactFlag.length)-1)){
			int noOfOccurance = 0;
			log.log(Log.FINE, "\n code length------->", code.length);
			log.log(Log.FINE, "\n index------->", index);
			log.log(Log.FINE, "\n OPFLAAG------->", contactVO.getOperationFlag());
			if (!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag())) {
				if (!OPERATION_FLAG_INSERT.equals(contactVO.getOperationFlag())) {
					if (hasValueChanged(contactVO.getContactCustomerCode(),
							code[index])
							|| hasValueChanged(contactVO
									.getCustomerDesignation(),
									designation[index])
							|| hasValueChanged(contactVO.getTelephone(),
									telephone[index])
							|| hasValueChanged(contactVO.getMobile(),
									mobile[index])
							|| hasValueChanged(contactVO.getFax(), fax[index])
							|| hasValueChanged(contactVO.getSiteAddress(),
									sita[index])
							|| hasValueChanged(contactVO.getEmailAddress(),
									email[index])
							|| hasValueChanged(contactVO.getRemarks(),
									remarks[index])
							|| hasValueChanged(contactVO.getCustomerName(),
									name[index])
							|| hasValueChanged(contactVO.getActiveStatus(),
									status[j])
							|| hasValueChanged(contactVO.getPrimaryUserFlag(),
									primaryContactStatus[j])) {
						if (contactVO.getOperationFlag() != null) {
							//contactVO.setOperationFlag(OPERATION_FLAG_UPDATE);
							if (!OPERATION_FLAG_INSERT.equals(customerVO
									.getOperationFlag())) {
								customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
							}
						}
					}

				}
			}
			/*if (!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag()) ) {
				if (code[index] != null) {
					contactVO.setContactCustomerCode(code[index].toUpperCase());
				}
				if (designation[index] != null) {
					contactVO.setCustomerDesignation(designation[index]
							.toUpperCase());
				}
				contactVO.setEmailAddress(email[index]);
				contactVO.setFax(fax[index]);
				contactVO.setMobile(mobile[index]);
				contactVO.setRemarks(remarks[index]);
				contactVO.setTelephone(telephone[index]);
				contactVO.setSiteAddress(sita[index]);
				if (name[index] != null) {
					contactVO.setCustomerName(name[index].toUpperCase());
				}
				
				if ("A".equals(status[j])) {
					contactVO.setActiveStatus("A");
				} else {
					contactVO.setActiveStatus("I");
				}
				if ("Y".equals(primaryContactStatus[j])) {
					contactVO.setPrimaryUserFlag("Y");
				} else {
					contactVO.setPrimaryUserFlag("N");
				}
				checkIndex++;
			}*/
		
			if (name != null) {
				log.log(Log.FINE, "\n\ncode length------>", code.length);
				for (int i = 0; i < (code.length)-1; i++) {
					log.log(Log.FINE, "\n\n hiddenOpContactFlag------>",
							hiddenOpContactFlag, i);
					//Modified by A-7137 as part of CR ICRD-152555 starts
						/*
						 * As part of checkstyle code violation, made the string literals to come in LHS
						 * Added by A-6770
						 */
					if (!OPERATION_FLAG_DELETE.equals(contactVO.getOperationFlag())&& 
							!OPERATION_FLAG_DELETE.equals(hiddenOpContactFlag[i]) &&
								!NOOPERATION_FLAG.equals(hiddenOpContactFlag[i]) &&
								!"".equals(name[i].trim()) && !"".equals(contactVO.getCustomerName())){
					//Added by A-7396 for ICRD-274900
						if (name[i].equalsIgnoreCase(contactVO
								.getCustomerName()) && code[i].equalsIgnoreCase(contactVO.getContactCustomerCode())) {
							if(type[i].equals(contactVO.getContactType())){
								noOfOccurance++;
							}
						}
					}
					//Modified by A-7137 as part of CR ICRD-152555 ends
				}
			}
			log.log(Log.FINE, "\n no of occurance--- ->", noOfOccurance);
			if (noOfOccurance > 1) {
				error = new ErrorVO(
						"customermanagement.defaults.customerregn.msg.err.dupcode");
				errors.add(error);
				return errors;

			}
			index++;
				}
			}
			}
		}
		log.log(Log.FINE, "**contactVOs After*****", contactVOs);
		log.log(Log.FINE, "**customerVO After *****", customerVO);
		return errors;
	}

	/**
	 *
	 * @param clearingAgentVOs
	 * @param form
	 * @param customerVO
	 */
	public Collection<ErrorVO> updateClearingAgentDetails(
			Collection<CustomerAgentVO> clearingAgentVOs,
			MaintainRegCustomerForm form, CustomerVO customerVO) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		String[] code = form.getAgentCode();
		String[] agetnStation = form.getAgentStation();
		String[] remarks = form.getAgentRemarks();
		String[] checkedExport = form.getCheckedExport().split(",");
		log.log(Log.FINE, "checkedExport.length------>", checkedExport.length);
		String[] checkedImport = form.getCheckedImport().split(",");
		String[] checkedSales = form.getCheckedSales().split(",");
		String[] opAgentFlag = form.getOperationAgentFlag();
		String[] agentScc = form.getAgentScc();
		String[] agentCarrier = form.getAgentCarrier(); // Added by A-8823 for IASCB-4841
		int index = 0;
		int checkIndex = 0;
		for (CustomerAgentVO agentVO : clearingAgentVOs) {
			int noOfOccurance = 0;
			/*
			 * if (code != null) { for (String codeValue : code) { if
			 * (codeValue.equals(agentVO.getAgentCode())) { noOfOccurance++; } } }
			 * if (noOfOccurance > 1) { error = new ErrorVO(
			 * "customermanagement.defaults.customerregn.msg.err.agentdupcode");
			 * errors.add(error); }
			 */

			if (!OPERATION_FLAG_INSERT.equals(agentVO.getOperationFlag())
					&& !OPERATION_FLAG_DELETE
							.equals(agentVO.getOperationFlag())) {
				log.log(Log.FINE, "dxport value--**--------->", checkedExport,
						checkIndex);
				log.log(Log.FINE, "dxport value---**-------->", checkedImport,
						checkIndex);
				log.log(Log.FINE, "dxport value----**------->", checkedSales,
						checkIndex);
				log.log(Log.FINE, "agentVO-----**------>", agentVO);
				if (hasValueChanged(agentVO.getAgentCode(), code[index])
						|| hasValueChanged(agentVO.getRemarks(), remarks[index])
						|| hasValueChanged(agentVO.getStationCode(),
								agetnStation[index])
						|| hasValueChanged(agentVO.getExportFlag(),
								checkedExport[index])
						|| hasValueChanged(agentVO.getImportFlag(),
								checkedImport[index])
						|| hasValueChanged(agentVO.getSalesFlag(),
								checkedSales[index]))

				{
					if (agentVO.getOperationFlag() == null) {
						agentVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						if (!OPERATION_FLAG_INSERT.equals(customerVO
								.getOperationFlag())) {
							customerVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						}
					}

				}
			}
			if (!OPERATION_FLAG_DELETE.equals(agentVO.getOperationFlag())) {
				if (code[index] != null && code[index].trim().length() > 0) {
					agentVO.setAgentCode(code[index].toUpperCase());
				}
				agentVO.setRemarks(remarks[index]);
				agentVO.setStationCode(agetnStation[index].toUpperCase());

				if ("Y".equals(checkedExport[checkIndex])) {

					agentVO.setExportFlag("Y");

				} else {
					agentVO.setExportFlag("N");
				}
				if ("Y".equals(checkedImport[checkIndex])) {

					agentVO.setImportFlag("Y");

				} else {
					agentVO.setImportFlag("N");
				}
				if ("Y".equals(checkedSales[checkIndex])) {

					agentVO.setSalesFlag("Y");

				} else {
					agentVO.setSalesFlag("N");
				}
				agentVO.setScc(agentScc[index]);
				agentVO.setCarrier(agentCarrier[index]); // Added by A-8823 for IASCB-4841
				log.log(Log.FINE, "dxport value----------->", checkedExport,
						checkIndex);
				log.log(Log.FINE, "dxport value----------->", checkedImport,
						checkIndex);
				log.log(Log.FINE, "dxport value----------->", checkedSales,
						checkIndex);
				checkIndex++;
			}

			if (code != null) {
				for (int i = 0; i < (code.length)-1; i++) {
					if (!OPERATION_FLAG_DELETE.equals(agentVO
							.getOperationFlag())
							&& !OPERATION_FLAG_DELETE.equals(opAgentFlag[i])) {
						if (code[i].equalsIgnoreCase(agentVO.getAgentCode())) {
							noOfOccurance++;
						}
					}
				}
			}
			if (noOfOccurance > 1) {
				error = new ErrorVO(
						"customermanagement.defaults.customerregn.msg.err.agentdupcode");
				errors.add(error);
				return errors;
			}

			index++;

		}
		return errors;

	}

	 private Collection<ErrorVO> validateSccCodes(String company,String formscc) {
			log.entering("SaveTempCustCommand", "validateCommodity");

			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			Collection<String> sccCodes = null;
			
			String[] codes = null;
			if (formscc != null && formscc.trim().length() > 0) {
				if(formscc.contains(DELIMETER_COMMA)){
					codes=formscc.split(DELIMETER_COMMA);
					sccCodes = convertToCollection(codes);
				}else{
					sccCodes = new ArrayList<String>();
					sccCodes.add(formscc.toUpperCase());
				}
				if(formscc.contains(DELIMETER_COMMA)){
				//	errorVOs=checkDuplicateBasisValuesOfScc(sccCodes);
				}
				if(errorVOs!=null && errorVOs.size() > 0) {
					return errorVOs;
		    	}
			}
			Set<String> nonDuplicateSCC = new HashSet<>();
			nonDuplicateSCC.addAll(sccCodes);
			try { 
				new SCCDelegate().validateSCCCodes(company,nonDuplicateSCC); //modified by A-5116
										
			} catch (BusinessDelegateException businessDelegateException) {
				errorVOs = handleDelegateException(businessDelegateException);
			}
			return errorVOs;

		}
	 private Collection<ErrorVO> checkDuplicateBasisValuesOfScc(Collection<String> basisValueCol){
	    	log.log(Log.FINE, " \n 77777");
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			if(basisValueCol != null && basisValueCol.size() > 0){
				Collection<String> tmpStrings = new ArrayList<String>();
				log.log(Log.FINE, " \n 8888");
				for(String str : basisValueCol){
					if(str != null && str.trim().length() > 0 && errors.size() == 0){
						if(tmpStrings.contains(str)){
							errors.add(new ErrorVO("customermanagement.defaults.duplicatesccvaluesexists"));
							break;
						}else{
							tmpStrings.add(str);
						}
					}
				}
			}
			return errors;
		}
	 private Collection<String> convertToCollection(String[] codes) {
	    	Collection<String> codesCol = null;
			if (codes != null && codes.length > 0) {
				codesCol = new ArrayList<String>();
				for (String code : codes) {
					if (code != null && code.trim().length() > 0) {
						codesCol.add(code.toUpperCase().trim());
					}
				}
			}
			return codesCol;
		}

	/**
	 *
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateAreaFields(MaintainRegCustomerForm form, CustomerVO customerVO) {
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		if (form.getStation() != null && form.getStation().trim().length() > 0) {
			errors = validateStationCode(form, logonAttributes.getCompanyCode(), customerVO);
		}
		if (errors != null && errors.size() > 0) {
			errorVOs.addAll(errors);
		}
		log.log(Log.FINE, " \n ibtarun");
		String[] destinationCodes = form.getAgentStation();
		if(destinationCodes!=null && destinationCodes.length != 0){//Modified By A-5374
		for(int i=0;i<destinationCodes.length-1;i++){
				if(destinationCodes[i] != null && destinationCodes[i].length() != 0){
				errors = validateStationCodeForDestination(destinationCodes[i], logonAttributes.getCompanyCode());
				}
		}
		if (errors != null && errors.size() > 0) {
			errorVOs.addAll(errors);
		}
		}
		
		//commented by a-3045 for bug 65138 on 29Sep09 
		/*errors = new ArrayList<ErrorVO>();
		if (form.getCity() != null && form.getCity().trim().length() > 0) {
			errors = validateCity(form.getCity(), logonAttributes.getCompanyCode());
		}
		if (errors != null && errors.size() > 0) {

			errorVOs.addAll(errors);
		}*/
		//commented by A-7604 for bug ICRD-233132 on 22SEP2018 
		/* for s7 specific validateCustomerGroup  method is moved to validateCustomerGroupCommand class	
		errors = new ArrayList<ErrorVO>();
		if(form.getCustomerGroup() != null && form.getCustomerGroup().trim().length()>0){
			errors = validateCustomerGroup(form);
		}
		if (errors != null && errors.size() > 0) {

			errorVOs.addAll(errors);
			
		}*/
		errors = new ArrayList<ErrorVO>();
		if (form.getCountry() != null && form.getCountry().trim().length() > 0) {
			errors = validateCountry(form.getCountry(), logonAttributes.getCompanyCode());
		}
		if (errors != null && errors.size() > 0) {
			errorVOs.addAll(errors);
			return errorVOs;
		}
		errors = new ArrayList<ErrorVO>();
		if(form.getScc() != null && form.getScc().trim().length()>0){
			 errors=validateSccCodes(logonAttributes.getCompanyCode(),form.getScc());
		}
		//added by a-3045 for CR HA16 on 04Jun09 starts
		
		errors = new ArrayList<ErrorVO>();
		if (form.getBillingCountry() != null && form.getBillingCountry().trim().length() > 0) {
			errors = validateCountry(form.getBillingCountry(), logonAttributes.getCompanyCode());
		}
		if (errors != null && errors.size() > 0 ) {
			errorVOs.addAll(errors);
		}
		
		//commented by a-3045 for bug 65138 on 29Sep09 
		/*errors = new ArrayList<ErrorVO>();
		if (form.getBillingCityCode() != null && form.getBillingCityCode().trim().length() > 0) {
			errors = validateCity(form.getBillingCityCode(), logonAttributes.getCompanyCode());
		}
		if (errors != null && errors.size() > 0) {

			errorVOs.addAll(errors);
		}*/
		//added by a-3045 for CR HA16 on 04Jun09 ends
		
		/*Added by 201930 for IASCB-131790 start*/
		if (form.getCassCountry() != null && form.getCassCountry().trim().length() > 0) {
			errors = validateCountry(form.getCassCountry(), logonAttributes.getCompanyCode());
		}
		if (errors != null && errors.size() > 0 ) {
			errorVOs.addAll(errors);
		}
		/*Added by 201930 for IASCB-131790 end*/
		return errorVOs;

	}

	private Collection<ErrorVO> validateStationCodeForDestination(
				String destinationCode, String companyCode) {

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();

			AreaValidationVO areaValidationVO = null;
			try {
				AreaDelegate delegate = new AreaDelegate();

				areaValidationVO = delegate.validateLevel(logonAttributes
						.getCompanyCode().toUpperCase(), "STN", destinationCode
						.toUpperCase());
			} catch (BusinessDelegateException e) {

				errors = handleDelegateException(e);
			}

			return errors;
	}

	


		/**
		 * 
		 * @param agentVOs
		 * @param companyCode
		 * @param form
		 * @return ErrorVO
		 */
		private Collection<ErrorVO> validateAgentVOs(Collection<CustomerAgentVO> agentVOs,String companyCode,MaintainRegCustomerForm form) {
			log.entering("SaveCustomerCommand", "validateAgentVOs");
			LocalDate toDateForm = null;
			//String[] agentcode = form.getAgentCode();
			//String[] agentStation = form.getAgentStation();
			//String[] opflag=form.getHiddenOpFlagForAgent();
			boolean isAgentInValid = false;
			//int noOfOccurance = 0;
			ErrorVO error = null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			AgentVO agentVO = null;
			try {
				if(agentVOs!=null && agentVOs.size()>0){
				 for(CustomerAgentVO customerAgentVO:agentVOs){
					 if(!isAgentInValid){
						 		log.log(Log.FINE, "\n agentCode ---> ",
										customerAgentVO.getAgentCode());
							String agentCode = customerAgentVO.getAgentCode();
							agentVO = new AgentDelegate().findAgentDetails(companyCode,
													agentCode);
							if(agentVO == null ){
								isAgentInValid= true;
							}
							LocalDate sysDate1  = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
							if(!CustomerAgentVO.OPERATION_FLAG_DELETE.equals(customerAgentVO.getOperationFlag())){
								if(Objects.nonNull(customerAgentVO.getValidityStartDate())&& Objects.nonNull(customerAgentVO.getValidityEndDate())&&
										customerAgentVO.getValidityStartDate().isGreaterThan(customerAgentVO.getValidityEndDate())){
									error=new ErrorVO("customermanagement.defaults.msg.err.invalidStartandFromDate");
									errors.add(error);
								}
								
							/**
							 * IASCB-42832 - CMT008-Error message is displayed while saving the customer details
							 * Added null check to Agent
							 */
							if (form.getToDate() != null) {
								toDateForm = new LocalDate(LocalDate.NO_STATION, Location.NONE, false)
										.setDate(form.getToDate());

							}
							if (Objects.nonNull(agentVO) && agentVO.getValidTo() != null && agentVO.getValidTo()
									.isLesserThan(sysDate1) && toDateForm!=null && toDateForm.isLesserThan(sysDate1)) {
								error=new ErrorVO("customermanagement.defaults.msg.err.invalidAgentCode");
								errors.add(error);
							}
							}
					 }

				 }
				 StringBuilder ctoClearingAgents = new StringBuilder();
				 if(agentVOs!=null && agentVOs.size()>0){
				 						for(CustomerAgentVO customerAgentVO: agentVOs){
				 							if(!CustomerAgentVO.OPERATION_FLAG_DELETE.equals(customerAgentVO.getOperationFlag())
				 									&& !"NOOP".equals(customerAgentVO.getOperationFlag())){
				 								if(customerAgentVO.getAgentCode()!=null
				 										&& customerAgentVO.getAgentCode().length()>0
				 										&& !( "Y".equals(customerAgentVO.getImportFlag())
				 											||"Y".equals(customerAgentVO.getExportFlag()) // added as part of ICRD-17496 
				 												|| "Y".equals(customerAgentVO.getSalesFlag()))){
				 									/** Need to add errors if import OR sales flag is not checked */
				 									ctoClearingAgents.append(customerAgentVO.getAgentCode())
				 													 .append(" ")
				 													 .append(",");
				 								}
				 							}
				 						}
				 					}
				 					if(ctoClearingAgents!=null
				 							&& ctoClearingAgents.length()>0){
				 						Object[] clearingAgentPar = new Object[]{ctoClearingAgents.toString()};
				 						error = new ErrorVO("customermanagement.defaults.msg.err.clearingagentimportorsalesflagmissing",clearingAgentPar);
				 						errors.add(error);
				 }
			 if(agentVO == null){
							 error = new ErrorVO(
				 						"customermanagement.defaults.regcustomer.invalidagentcode");
					errors.add(error);

					}else{/*
						//validating duplicate agentcode
						 for(CustomerAgentVO customerAgentVO:agentVOs){
							 if (agentcode != null && agentStation!=null) {
									for (int i = 0; i < (agentcode.length)-1; i++) {
										log.log(Log.FINE, "\n\n hiddenOpContactFlag------>" + opflag[i]);
										if (!OPERATION_FLAG_DELETE.equals(customerAgentVO.getOperationFlag())&& 
												!OPERATION_FLAG_DELETE.equals(opflag[i]) &&
													!NOOPERATION_FLAG.equals(opflag[i])){
											log.log(log.FINE,"\n....11....+++******"+agentcode[i]+customerAgentVO.getAgentCode());
											log.log(log.FINE,"\n....22....+++******"+agentStation[i]+customerAgentVO.getStationCode());
											if ((agentcode[i].equalsIgnoreCase(
													customerAgentVO	.getAgentCode()) && 
														(agentStation[i].equalsIgnoreCase(
															customerAgentVO.getStationCode())))) {
													noOfOccurance++;
													log.log(log.FINE, "\ninside occurence"+noOfOccurance);
											}
										}
									}
								}
							 
						 }
						 log.log(log.FINE, "\n  value occurence"+noOfOccurance);
						 if (noOfOccurance > 1) {
								error = new ErrorVO(
										"customermanagement.defaults.customerregn.msg.err.agentdupcode");
								errors.add(error);
								return errors;
							}
					*/}
				}

			} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
				handleDelegateException(businessDelegateException);
			}

			log.log(Log.FINE, "agentVO ---> ", agentVO);
			log.exiting("SaveCustomerCommand", "validateAgentCode");
			return errors;
	}
		
		private  CustomerAgentVO agentcheckbox(MaintainRegCustomerForm form,CustomerAgentVO newAgentVO,int i) {
			
			String[] checkedExport = form.getCheckedExport().split(",");
			String[] checkedImport = form.getCheckedImport().split(",");
			String[] checkedSales = form.getCheckedSales().split(",");
			
			if ("Y".equals(checkedExport[i])) {
				newAgentVO.setExportFlag("Y");
			} else {
				newAgentVO.setExportFlag("N");
			}
			if ("Y".equals(checkedImport[i])) {
				newAgentVO.setImportFlag("Y");
			} else {
				newAgentVO.setImportFlag("N");
			}
			if ("Y".equals(checkedSales[i])) {
				newAgentVO.setSalesFlag("Y");
			} else {
				newAgentVO.setSalesFlag("N");
			}
			
			return newAgentVO;
	}
	
	/**
	 * 
	 * @param agentVOs
	 * @param companyCode
	 * @param form
	 * @return ErrorVO
	 * Modified the logic by A-5163 for ICRD-115865.
	 * It is not mandatory that all the Certificates should have certificate number.
	 * So validateDuplicateTSANumbers can be called only when there is at least one Certificate with Certificate
	 * number is present.
	 */
	private Collection<ErrorVO> validateDuplicateTSANumbers(CustomerVO customerVO){
		log.entering("SaveCustomerCommand", "validateDuplicateTSANumbers");		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		CustomerDelegate delegate = new CustomerDelegate();
		if(customerVO.getCustomerCertificateDetails() != null
			   && customerVO.getCustomerCertificateDetails().size() > 0){
			for (CustomerCertificateVO certificateVO : customerVO.getCustomerCertificateDetails()) {
				if (certificateVO.getCertificateNumber() != null
						&& certificateVO.getCertificateNumber().trim().length() > 0) {
			try {
				delegate.validateDuplicateTSANumbers(customerVO);
			}catch(BusinessDelegateException businessDelegateException){
				errors = businessDelegateException.getMessageVO().getErrors();
					}
					break;
				}
			}
		}
		log.exiting("SaveCustomerCommand", "validateDuplicateTSANumbers");		
		return errors;
	}
	
	private Collection<LockVO> prepareLockForSave(){
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<LockVO>();
		LockVO txnLockVO = new TransactionLockVO(LOCK_ACTION);
		txnLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		txnLockVO.setAction(LOCK_ACTION);
		txnLockVO.setScreenId(SCREENID);
		txnLockVO.setStationCode(logonAttributes.getStationCode());
		txnLockVO.setDescription(LOCK_DESCRIPTION);
		txnLockVO.setRemarks(LOCK_REMARKS);
		locks.add(txnLockVO);
		return locks;
	}

	// Added by A-5183 For ICRD-18283 Starts
	
	/**
	 *  ICRD-18283
	 * 	Method		:	populateAgentDetails
	 *	Added by 	:	A-5183 on 22-02-2013
	 * 	Used for 	:	Set Agent Details from form
	 *	Parameters	:	@param MaintainRegCustomerForm
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param CustomerVO
	 *	Parameters	:	@return AgentVO
	 */
		
	private AgentVO populateAgentDetails(MaintainRegCustomerForm form, String companyCode,CustomerVO customerVO){
		
		AgentVO agentVO = new AgentVO();		
		agentVO.setCompanyCode(companyCode);
		agentVO.setAgentCode(form.getCustomerCode().toUpperCase());
		agentVO.setCustomerCode(form.getCustomerCode().toUpperCase());
		agentVO.setIataAgentCode(form.getIataAgentCode());
		agentVO.setAgentType(form.getAgentInformation());
		agentVO.setAgentName(form.getCustName().toUpperCase());
		agentVO.setCassIdentifier(form.getCassIdentifier());
		agentVO.setStation(form.getStation()); //added by a-5133 as part of bug ICRD-34868
		//agentVO.setSalesId(form.getSalesId());
		agentVO.setHoldingCompany(form.getHoldingCompany());	
		//added by a-5133 as part of BUG ICRD-43388 starts
		if(form.getBillingCode() != null && form.getBillingCode().trim().length() > 0) {
		agentVO.setBillingCurrencyCode(form.getBillingCode());
		}
		//added by a-5133 as part of BUG ICRD-43388 ends
		if(customerVO.getCustomerType()!= null){ 			
			if(!CustomerVO.CUSTOMER_TYPE_AG.equals(customerVO.getCustomerType())){
				agentVO.setOperationFlag(AgentVO.OPERATION_FLAG_INSERT);
				
				if(CustomerVO.CUSTOMER_TYPE_CC.equals(customerVO.getCustomerType())){
					
					for(CCCollectorVO ccCollectorVO : customerVO.getCcCollectorVOs()){				
						ccCollectorVO.setOperationFlag(CCCollectorVO.OPERATION_FLAG_DELETE);
					}					
				}	
			}			
		}
		if(form.getNormalComm() != null &&  form.getNormalComm().trim().length()>0){
			agentVO.setNormCommPrc(Double.parseDouble(form.getNormalComm()));
		}else{
			agentVO.setNormCommPrc(0.0);
		}
		if(form.getNormalCommFixed() != null &&  form.getNormalCommFixed().trim().length()>0){
			agentVO.setFixedValue(Double.parseDouble(form.getNormalCommFixed()));
		} else {
			agentVO.setFixedValue(0.0);
		}	
		if (CHECKSTATUS.equals(form.getOwnSalesFlag())) {
			agentVO.setOwnSalesFlag("Y");
		}else{
			agentVO.setOwnSalesFlag("N");
		}	
		if(CHECKSTATUS.equals(form.getSalesReportAgent())){
			agentVO.setSalesReporting(true);
		}		
		if(CHECKSTATUS.equals(form.getControllingLocation())){
			agentVO.setControllingLocation(true);
		}
		else{
			agentVO.setControllingLocation(false);
		}
		if(CHECKSTATUS.equals(form.getSellingLocation())){
			agentVO.setSellingLocation(true);
		}
		else{
			agentVO.setSellingLocation(false);
		}
		if(CHECKSTATUS.equals(form.getProformaInv())){
			agentVO.setInvoiceGenerationFlag(true);
		}
		else{
			agentVO.setInvoiceGenerationFlag(false);
		}
		
		agentVO.setRemarks(form.getBillingremark());		
		agentVO.setAgentCountry(form.getCountry());
		agentVO.setAgentCity(form.getCity());	
		/*Modified by A-5491 as part of ICRD-61752 on 23JAN2014
		 *EnduranceAwbFlag is set to Y for AWB and N for Invoice 
		 *or else the value of ENDAWBFLG of SHRAGTMST table will always be N */
		if(customerVO.isEnduranceFlag()){
			agentVO.setEnduranceAwbFlag(true);
		}
		else{
			agentVO.setEnduranceAwbFlag(false);
		}
	    //Added by A-9066 for IASCB-75503
   		   if((!BLANK.equals(form.getEndurancePercentage())) && form.getEndurancePercentage()!= null){
   				agentVO.setEndurancePercentage(Double.parseDouble(form.getEndurancePercentage()));
   		   }else{
   			agentVO.setEndurancePercentage(0.0);
   		   }
   		   if(form.getEnduranceValue()!=null && !BLANK.equals(form.getEnduranceValue())){
	   				agentVO.setEnduranceValue(Double.parseDouble(form.getEnduranceValue()));
	   	   }else{
	   			agentVO.setEnduranceValue(0.0);
	   	   }
   		   if(form.getEnduranceMaxValue()!=null && !BLANK.equals(form.getEnduranceMaxValue())){
   				agentVO.setEnduranceMaxValue(Double.parseDouble(form.getEnduranceMaxValue()));
   		   }else{
   	   			agentVO.setEnduranceMaxValue(0.0);
    	   }
			
		return agentVO;
	}
		
	/**
	 *  ICRD-18283
	 * 	Method		:	populateCCCollectorDetails
	 *	Added by 	:	A-5183 on 22-02-2013
	 * 	Used for 	:	Set CCCollector Details from form
	 *	Parameters	:	@param MaintainRegCustomerForm
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param CustomerVO
	 *	Parameters	:	@return CCCollectorVO
	 */
	
	private CCCollectorVO populateCCCollectorDetails(MaintainRegCustomerForm form,String companyCode,CustomerVO customerVO) {

		CCCollectorVO cCCollectorVO = new CCCollectorVO();	
		/**Populating the country code by A-4113 for ICRD-37418
		*/
		cCCollectorVO.setCountryCode(form.getCountry()); 
		cCCollectorVO.setCcCollectorCode(form.getCustomerCode().toUpperCase());	
		/**Populating the country code by A-4113 for ICRD-37418
		 */
		cCCollectorVO.setCountryCode(form.getCountry());
		cCCollectorVO.setCustomerCode(form.getCustomerCode().toUpperCase());
		cCCollectorVO.setCcCollectorName(form.getCustName().toUpperCase()); //modified by a-5133 as part of bug ICRD-38449
		
		/** populating the city code and billing currency value by a-5133 as part of bug ICRD-41748 starts */
		if (form.getCity() != null && form.getCity().trim().length() > 0) {
		cCCollectorVO.setCityCode(form.getCity().toUpperCase());  
		}
		if(form.getBillingCode() != null && form.getBillingCode().trim().length() > 0) {
		cCCollectorVO.setBillingCurrency(form.getBillingCode().toUpperCase());
		}
		
		/** populating the city code and billing currency value by a-5133 as part of bug ICRD-41748 ends */
		cCCollectorVO.setCompanyCode(companyCode); 
		//Added by A-4772 for ICRD-34645 starts here
		cCCollectorVO.setLastUpdatedTime(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, true));
		//Added by A-4772 for ICRD-34645 ends here
		if(customerVO.getCustomerType()!= null){ 
			//when existing type  is changed to cc collector 
			if(!CustomerVO.CUSTOMER_TYPE_CC.equals(customerVO.getCustomerType())){
				 cCCollectorVO.setOperationFlag(CCCollectorVO.OPERATION_FLAG_INSERT);
				 
				if(CustomerVO.CUSTOMER_TYPE_AG.equals(customerVO.getCustomerType())){
					
					for(AgentVO agentVO : customerVO.getAgentVOs()){				
						agentVO.setOperationFlag(AgentVO.OPERATION_FLAG_DELETE);
				   }	
									
				}	   
			}	
		}		
		if (form.getAirportCode() != null && form.getAirportCode().trim().length() > 0) {
			cCCollectorVO.setAirportCode(form.getAirportCode().toUpperCase());
		}
		if (form.getAircraftTypeHandled() != null) {
			cCCollectorVO.setAircraftTypeHandled(form.getAircraftTypeHandled());
		}

		if (form.getDateOfExchange() != null) {
			cCCollectorVO.setDateOfExchange(form.getDateOfExchange());
		} else {
			cCCollectorVO.setDateOfExchange(null);
		}
		cCCollectorVO.setCassBillingIndicator(form.isCassBillingIndicator());
		
		if(form.isCassBillingIndicator()){
			
			if(form.getCassCode()!= null ) {
		   		cCCollectorVO.setCassCode(form.getCassCode());
		   	}else {
		   		cCCollectorVO.setCassCode(null);
		   	}		
			
		}				
		cCCollectorVO.setBillingThroughInterline(form.isBillingThroughInterline());
		
		if(form.isBillingThroughInterline()){
			
			if(form.getAirlineCode()!= null && form.getAirlineCode().trim().length()> 0) {			
		   		cCCollectorVO.setAirlineCode(form.getAirlineCode().toUpperCase());
		   	}
		}
		cCCollectorVO.setRemarks(form.getBillingremark());			
		
		//Added by A-6055 for ICRD-86574
		cCCollectorVO.setAccountNumber(form.getAccountNumber());
		//End ICRD-86574
		//Added by A-7656 for ICRD-242148
		cCCollectorVO.setCCFeeDueGHA(form.isCcFeeDueGHA());
		return cCCollectorVO;
	}	
	
	/** Added by A-5219
	 * For Check Boxes 
	 * @param form
	 * @param session
	 */
	private void poulateAircraftTypeHandledForForm(MaintainRegCustomerForm form, MaintainCustomerRegistrationSession session) {
		if (form.getAircraftTypeHandled() != null) {
    		StringBuilder aircraftTypeHandled=new StringBuilder();
			String[] aircraftType = form.getAircraftTypeHandled().split(",");
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
	}
	
	/**
     * @author A-5165
     * @param Collection<CustomerMiscDetailsVO>
     * @param form
     * @param customerVO
	 * @param session 
     * @return
     * This method is to populate the  CustomerMiscDetailsVO from 
     *  The filter fields are populated based on miscCodes
     *  The table fields are populated based on MiscDetailOpFlag.
     *
     *  Used for populating banks and other details of a customer
     *  ICRD-64121
     *
     */
	public Collection<CustomerMiscDetailsVO> populateCustomerMiscDetailsVO(
			Collection<CustomerMiscDetailsVO> customerMiscVos,
			MaintainRegCustomerForm form, CustomerVO customerVO, MaintainCustomerRegistrationSession session){		
		String[] inputcode = form.getMiscDetailCode();
		String[] inputvalue = form.getMiscDetailValue();
		//Added by A-5165 for ICRD-35135
		Collection<OneTimeVO> oneTimeValue = session.getOneTimeValues().get(ONETIME_BANKCODETYPE);
		
		String[] miscCodes = { "BANK_NAME_CODE", "BANK_BRANCH", "BANK_CITY",
				"BANK_STATE", "BANK_COUNTRY", "BANK_PIN" };		
		
		
	//	String[] inputDescription = form.getMiscDetailDescription();
		CustomerMiscDetailsVO miscdetailsvo = null;
		// Added for bug icrd-18422 by a-4810
		if (form.getMiscDetailCode() != null
				&& form.getMiscDetailCode().length > 1) {
			for (int i = 0; i < form.getMiscDetailCode().length; i++) {
				if (!"NOOP".equals(form.getMiscDetailOpFlag()[i])) {
					miscdetailsvo = new CustomerMiscDetailsVO();
					miscdetailsvo.setCompanyCode(customerVO.getCompanyCode());
					miscdetailsvo.setCustomerCode(customerVO.getCustomerCode());
					miscdetailsvo.setMiscCode(inputcode[i]);
					miscdetailsvo.setMiscValue(inputvalue[i]);
					//Added by A-5165 for ICRD-35135
					
					for (OneTimeVO oneTimeVO : oneTimeValue) {
						if(miscdetailsvo.getMiscCode().equalsIgnoreCase(oneTimeVO.getFieldValue())){
							miscdetailsvo.setMiscDescription(oneTimeVO.getFieldDescription());
							break;
						}
					}
					
					
				
					miscdetailsvo
							.setOperationFlag(form.getMiscDetailOpFlag()[i]);
					miscdetailsvo.setSequenceNumber(Integer.parseInt(form
							.getMiscSeqNum()[i]));

					if (!"".equals(form.getMiscDetailValidTo()[i])) {
						LocalDate validto = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						validto.setDate(form.getMiscDetailValidTo()[i]);
						miscdetailsvo.setMiscValidTo(validto);
					}
					if (!"".equals(form.getMiscDetailValidFrom()[i])) {
						LocalDate validfrom = new LocalDate(
								LocalDate.NO_STATION, Location.NONE, false);
						validfrom.setDate(form.getMiscDetailValidFrom()[i]);
						miscdetailsvo.setMiscValidFrom(validfrom);
					}

					miscdetailsvo.setCustomerCode(customerVO.getCustomerCode());
					miscdetailsvo.setCompanyCode(customerVO.getCompanyCode());
					miscdetailsvo
							.setMiscRemarks(form.getMiscDetailRemarks()[i]);

					customerMiscVos.add(miscdetailsvo);
				}
			}
		}

		for (String miscCode : miscCodes) {

			miscdetailsvo = new CustomerMiscDetailsVO();
			miscdetailsvo.setOperationFlag("U");
			miscdetailsvo.setSequenceNumber(1);
			miscdetailsvo.setCustomerCode(customerVO.getCustomerCode());
			miscdetailsvo.setCompanyCode(customerVO.getCompanyCode());

			if ("BANK_NAME_CODE".equals(miscCode)) {

				if (form.getBankcode() != null
						&& form.getBankcode().trim().length() > 0) {
					miscdetailsvo.setMiscValue(form.getBankcode());
					miscdetailsvo.setMiscCode(miscCode);
					customerMiscVos.add(miscdetailsvo);
				}
			}

			if ("BANK_BRANCH".equals(miscCode)) {
				if (form.getBankbranch() != null
						&& form.getBankbranch().trim().length() > 0) {

					miscdetailsvo.setMiscCode(miscCode);
					miscdetailsvo.setMiscValue(form.getBankbranch());
					customerMiscVos.add(miscdetailsvo);
				}
			}

			if ("BANK_CITY".equals(miscCode)) {
				if (form.getBankcityname() != null
						&& form.getBankcityname().trim().length() > 0) {

					miscdetailsvo.setMiscCode(miscCode);
					miscdetailsvo.setMiscValue(form.getBankcityname());
					customerMiscVos.add(miscdetailsvo);
				}
			}

			if ("BANK_STATE".equals(miscCode)) {
				if (form.getBankstatename() != null
						&& form.getBankstatename().trim().length() > 0) {

					miscdetailsvo.setMiscCode(miscCode);
					miscdetailsvo.setMiscValue(form.getBankstatename());
					customerMiscVos.add(miscdetailsvo);
				}
			}

			if ("BANK_COUNTRY".equals(miscCode)) {
				if (form.getBankcountryname() != null
						&& form.getBankcountryname().trim().length() > 0) {

					miscdetailsvo.setMiscCode(miscCode);
					miscdetailsvo.setMiscValue(form.getBankcountryname());
					customerMiscVos.add(miscdetailsvo);
				}
			}

			if ("BANK_PIN".equals(miscCode)) {
				if (form.getBankpincode() != null
						&& form.getBankpincode().trim().length() > 0) {

					miscdetailsvo.setMiscCode(miscCode);
					miscdetailsvo.setMiscValue(form.getBankpincode());
					customerMiscVos.add(miscdetailsvo);
				}
			}
		}		
		return customerMiscVos;
	}
	// Added by A-5183 For ICRD-18283 Ends

	// Added by A-8823 for IASCB-4841 beg
	private Collection<ErrorVO> checkDuplicateBasisValuesOfCarrier(Collection<String> basisValueCol) {
		Collection<ErrorVO> errors = new ArrayList<>();
		if (basisValueCol != null && !basisValueCol.isEmpty()) {
			Collection<String> tmpStrings = new ArrayList<>();
			for (String str : basisValueCol) {
				if (str != null && str.trim().length() > 0) {
					if (tmpStrings.contains(str)) {
						errors.add(new ErrorVO(ERR_DUPLICATE_CARRIER_EXISTS_IN_AGENT_DETAILS));
						break;
					} else {
						tmpStrings.add(str);
					}
				}
			}
		}
		return errors;
	}
	// Added by A-8823 for IASCB-4841 ends
	private boolean isNullorEmpty(String val) {
		return (val == null || val.trim().length() <= 0);
	}
}
