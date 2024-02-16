package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CCADetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerBillingInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerReceivablesAgeingVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerReceivablesCreditInfoVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerReceivablesVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerStatusViewVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.PaymentDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CCCollectorVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerMiscDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class CustomerConsoleModelConverter {
	private static final String FIELD_TYPE_CUSTOMER_STATUS = "customer.RegisterCustomer.status";
	private static final String SHARED_AGENT_BANK_DETAILS_CODE_INSTRUCTIONS = "INSTRUCTIONS";
	private static final String FINALIZED_STATUS = "F";
	private static final String ALL = "ALL";
	private static final String FINALIZED = "FINALIZED";
	private static final String DIFFERENCE = "DIFFERENCE";

	private static Map<String, String> statusCount;

	public static Map<String, String> getStatusCount() {
		return statusCount;
	}

	public static CustomerDetails constructCustomerDetails(CustomerVO customerVO,
			Map<String, Collection<OneTimeVO>> oneTimeValues, UserVO userVO) {
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setCustomerName(customerVO.getCustomerName());
		customerDetails.setEmail(customerVO.getEmail());
		customerDetails.setFax(customerVO.getFax());
		customerDetails.setPhoneNumber(customerVO.getTelephone());

		customerDetails.setAccountNumber(customerVO.getAccountNumber());
		customerDetails.setBillingCurrency(customerVO.getBillingCode());
		if (null != oneTimeValues)
			for (OneTimeVO oneTimeVO : oneTimeValues.get(FIELD_TYPE_CUSTOMER_STATUS)) {
				if (oneTimeVO.getFieldValue().equals(customerVO.getStatus())) {
					customerDetails.setStatus(oneTimeVO.getFieldDescription());
					break;
				}

			}

		customerDetails.setValidFrom(customerVO.getValidFrom());
		customerDetails.setValidTo(customerVO.getValidTo());
		customerDetails.setCustomerCode(customerVO.getCustomerCode());
		//Commented by A-8169 for ICRD-304523 starts
		//customerDetails.setIataCode(customerVO.getContyrollingAgentCode());
		//Commented by A-8169 for ICRD-304523 ends

		if (null != customerVO.getCustomerBillingDetailsVO()) {
			customerDetails.setCity(customerVO.getCustomerBillingDetailsVO().getCityCode());
			customerDetails.setCountry(customerVO.getCustomerBillingDetailsVO().getCountry());
			customerDetails.setState(customerVO.getCustomerBillingDetailsVO().getState());
			customerDetails.setZipCode(customerVO.getCustomerBillingDetailsVO().getZipcode());
			customerDetails.setStreet(customerVO.getCustomerBillingDetailsVO().getStreet());
		}

		if (null != customerVO.getCustomerPreferences()) {
			if (null != userVO) {
				String firstName = userVO.getUserFirstName();
				String middleName = userVO.getUserMiddleName();
				String lastName = userVO.getUserLastName();
				String accountSpecialist = "";
				if (null != firstName) {
					accountSpecialist = firstName;
				}
				if (null != middleName) {
					accountSpecialist = accountSpecialist.concat(" ").concat(middleName);

				}
				if (null != lastName) {
					accountSpecialist = accountSpecialist.concat(" ").concat(lastName);

				}
				customerDetails.setAccountSpecialist(accountSpecialist);
			}

		}
		if (null != customerVO.getCustomerMiscValDetailsVO()) {
			Collection<CustomerMiscDetailsVO> customerMiscVOs = customerVO.getCustomerMiscValDetailsVO();
			for (CustomerMiscDetailsVO customerMiscVO : customerMiscVOs) {
				if (customerMiscVO.getMiscCode().equalsIgnoreCase(SHARED_AGENT_BANK_DETAILS_CODE_INSTRUCTIONS)) {
					LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
					if ((customerMiscVO.getMiscValidFrom().isLesserThan(currentDate)
							&& customerMiscVO.getMiscValidTo().isGreaterThan(currentDate))
							|| customerMiscVO.getMiscValidFrom().equals(currentDate)
							|| customerMiscVO.getMiscValidTo().equals(currentDate)) {
						customerDetails.setInstruction(customerMiscVO.getMiscValue());
						break;

					}

				}

			}
		}
		
		if(customerVO!=null&&customerVO.getCustomerContactVOs()!=null){
			for(CustomerContactVO contactVO:customerVO.getCustomerContactVOs()){
				if(contactVO!=null && "RVN".equals(contactVO.getContactType())){
					customerDetails.setContactcustomerName(contactVO.getContactCustomerCode()+" "+contactVO.getCustomerName());
					customerDetails.setContactcustomerEmail(contactVO.getEmailAddress());
					customerDetails.setContactcustomerFax(contactVO.getFax());
					customerDetails.setContactcustomerPhone(contactVO.getTelephone());
					customerDetails.setContactcustomerTitle(contactVO.getCustomerDesignation());
				}
				
			}
		}
		//Added by A-8169 for ICRD-304523 starts
		if (customerVO.getCustomerType() != null) {
			if ("AG".equals(customerVO.getCustomerType())) {
				if (customerVO.getAgentVOs() != null
						&& customerVO.getAgentVOs().size() > 0) {
					List<AgentVO> agentVOs = (List<AgentVO>) customerVO
							.getAgentVOs();
					AgentVO agentVO = new AgentVO();
					agentVO = agentVOs.get(0);
					customerDetails.setIataCode(agentVO.getIataAgentCode());
				}
			}else if("CC".equals(customerVO.getCustomerType())){
				if (customerVO.getCcCollectorVOs() != null
						&& customerVO.getCcCollectorVOs().size() > 0) {
					List<CCCollectorVO> ccCollectorVOs = (List<CCCollectorVO>) customerVO
							.getCcCollectorVOs();
					CCCollectorVO ccCollectorVO = new CCCollectorVO();
					ccCollectorVO = ccCollectorVOs.get(0);
					customerDetails.setIataCode(ccCollectorVO.getCassCode());
				}
			}
		}
		//Added by A-8169 for ICRD-304523 ends

		return customerDetails;

	}

	public static BillingInvoiceDetails constructBillingInvoiceDetails(
			CustomerBillingInvoiceDetailsVO billingInvoiceDetailsVO) {
		
		BillingInvoiceDetails billingInvoiceDetails=new BillingInvoiceDetails();
		if (null!=billingInvoiceDetailsVO.getInvoiceDetailsPage())	
			{
			billingInvoiceDetails.setInvoiceDetails(constructInvoiceDetails(billingInvoiceDetailsVO.getInvoiceDetailsPage()));
			billingInvoiceDetails.setStatusCount(statusCount);
			}
		if (null!=billingInvoiceDetailsVO.getReceivablesCreditInfo()&&null!=billingInvoiceDetailsVO.getReceivables())	{	
			billingInvoiceDetails.setReceivablesCreditInfo(constructReceivablesCreditInfo(billingInvoiceDetailsVO.getReceivablesCreditInfo(),billingInvoiceDetailsVO.getReceivables()));
		}
		if (null!=billingInvoiceDetailsVO.getStatusView())	{	
			billingInvoiceDetails.setStatusView(constructStatusView(billingInvoiceDetailsVO.getStatusView()));
		}
		if (null!=billingInvoiceDetailsVO.getReceivablesAgeing())	{	
			billingInvoiceDetails.setReceivablesAgeing(constructReceivablesAgeing(billingInvoiceDetailsVO.getReceivablesAgeing()));
		}
		return billingInvoiceDetails;

	}
	
	private static Collection<ReceivablesAgeing> constructReceivablesAgeing(
			Collection<CustomerReceivablesAgeingVO> receivablesAgeingVOs) {
		Collection<ReceivablesAgeing> receivablesAgeings = new ArrayList<ReceivablesAgeing>();
		ReceivablesAgeing receivablesAgeing1 = new ReceivablesAgeing();
		receivablesAgeing1.setName("0-15");
		receivablesAgeings.add(receivablesAgeing1);
		ReceivablesAgeing receivablesAgeing2 = new ReceivablesAgeing();
		receivablesAgeing2.setName("16-30");
		receivablesAgeings.add(receivablesAgeing2);
		ReceivablesAgeing receivablesAgeing3 = new ReceivablesAgeing();
		receivablesAgeing3.setName("31-45");
		receivablesAgeings.add(receivablesAgeing3);
		ReceivablesAgeing receivablesAgeing4 = new ReceivablesAgeing();
		receivablesAgeing4.setName("46-60");
		receivablesAgeings.add(receivablesAgeing4);
		ReceivablesAgeing receivablesAgeing5 = new ReceivablesAgeing();
		receivablesAgeing5.setName("61-90");
		receivablesAgeings.add(receivablesAgeing5);
		ReceivablesAgeing receivablesAgeing6 = new ReceivablesAgeing();
		receivablesAgeing6.setName("91-120");
		receivablesAgeings.add(receivablesAgeing6);
		ReceivablesAgeing receivablesAgeing7 = new ReceivablesAgeing();
		receivablesAgeing7.setName(">120");
		receivablesAgeings.add(receivablesAgeing7);
		for (ReceivablesAgeing receivablesAgeing : receivablesAgeings) {
			for (CustomerReceivablesAgeingVO receivablesAgeingVO : receivablesAgeingVOs) {
				if (receivablesAgeing.getName().equals(
						receivablesAgeingVO.getAgeing())) {
					receivablesAgeing.setValue(receivablesAgeingVO
							.getAwbValue());
					receivablesAgeing.setAwbCount(receivablesAgeingVO
							.getAwbCount());
				}
			}
		}
		return receivablesAgeings;
	}

	public static PageResult<InvoiceDetails> constructInvoiceDetails(
			Page<CustomerInvoiceDetailsVO> invoiceDetailsVOs) {
		
		
		List<InvoiceDetails> invoiceDetails = new ArrayList<InvoiceDetails>();
		int totalCount = 0;
		int finalizedCount = 0;
		for (CustomerInvoiceDetailsVO invoiceDetailsVO : invoiceDetailsVOs) {
			InvoiceDetails invoiceDetail = new InvoiceDetails();
			invoiceDetail.setAdjBilledAmount(invoiceDetailsVO.getAdjBilledAmount());
			invoiceDetail.setAge(invoiceDetailsVO.getAge());
			invoiceDetail.setInvoiceDate(invoiceDetailsVO.getInvoiceDate());
			invoiceDetail.setInvoiceNumber(invoiceDetailsVO.getInvoiceNumber());
			invoiceDetail.setLastActedDate(invoiceDetailsVO.getLastActedDate());
			invoiceDetail.setNetBilledAmount(invoiceDetailsVO.getNetBilledAmount());
			invoiceDetail.setOrgBilledAmount(invoiceDetailsVO.getOrgBilledAmount());
			invoiceDetail.setOutstandingAmount(invoiceDetailsVO.getOutstandingAmount());
			invoiceDetail.setPaidAmount(invoiceDetailsVO.getPaidAmount());
			invoiceDetail.setPeriodFromDate(invoiceDetailsVO.getPeriodFromDate());
			invoiceDetail.setPeriodToDate(invoiceDetailsVO.getPeriodToDate());
			invoiceDetail.setStatus(invoiceDetailsVO.getStatus());
			invoiceDetail.setBillingType(invoiceDetailsVO.getBillingType());
			invoiceDetail.setAdjustmentFlag(invoiceDetailsVO.getAdjustmentFlag());
			invoiceDetail.setCountryCode(invoiceDetailsVO.getCountryCode());
			//Added by A-8169 for ICRD-308533 starts
			invoiceDetail.setCassFlag(invoiceDetailsVO.getCassFlag());
			//Added by A-8169 for ICRD-308533 ends
			if (invoiceDetailsVO.getStatus().equals(FINALIZED_STATUS)) {
				finalizedCount++;
			}
			totalCount++;
			invoiceDetails.add(invoiceDetail);
		}
		statusCount = new HashMap<String, String>();
		statusCount.put(ALL, String.valueOf(totalCount));
		statusCount.put(FINALIZED, String.valueOf(finalizedCount));
		statusCount.put(DIFFERENCE, String.valueOf(totalCount - finalizedCount));
		PageResult<InvoiceDetails> invoiceDetailsPage=new PageResult<InvoiceDetails>(invoiceDetailsVOs,invoiceDetails);
		return invoiceDetailsPage;
	}
	public static ReceivablesCreditInfo constructReceivablesCreditInfo(
			CustomerReceivablesCreditInfoVO receivablesCreditInfoVO, CustomerReceivablesVO receivablesVO) {

		ReceivablesCreditInfo receivablesCreditInfo = new ReceivablesCreditInfo();
		receivablesCreditInfo.setAvailableCreditLimit(receivablesCreditInfoVO.getAvailableCreditLimit());
		receivablesCreditInfo.setCollectionAgency(receivablesCreditInfoVO.getCollectionAgency());
		receivablesCreditInfo.setCustomerType(receivablesCreditInfoVO.getCustomerType());
		receivablesCreditInfo.setDaysToClose(receivablesCreditInfoVO.getDaysToClose());
		receivablesCreditInfo.setRestrictedFOP(receivablesCreditInfoVO.getRestrictedFOP());
		receivablesCreditInfo.setVatRegNumber(receivablesCreditInfoVO.getVatRegNumber());
		receivablesCreditInfo.setCreditLimit(receivablesCreditInfoVO.getCreditLimit());
		receivablesCreditInfo.setOutstandingReceivableCount(receivablesVO.getOutstandingReceivableCount());
		receivablesCreditInfo.setOutstandingReceivableValue(receivablesVO.getOutstandingReceivableValue());
		receivablesCreditInfo.setUnbilledReceivableCount(receivablesVO.getUnbilledReceivableCount());
		receivablesCreditInfo.setUnbilledReceivableValue(receivablesVO.getUnbilledReceivableValue());
		return receivablesCreditInfo;
	}
	public static Collection<StatusView> constructStatusView(
			Collection<CustomerStatusViewVO> statusViewVOs) {
		
		
		Collection<StatusView> statusViews = new ArrayList<StatusView>();
		//int awbCount = 0;
		//double awbAmount = 0.0;
		/*for (CustomerStatusViewVO customerStatusViewVO : statusViewVOs) {
			StatusView statusView = new StatusView();
			if (customerStatusViewVO.getAwbType().equals("UNPAID")) {
				statusView.setAwbType("Prime Billing");
			} else if (customerStatusViewVO.getAwbType().equals("SHORTPAID")) {
				statusView.setAwbType("Short Paid");
			} else if (customerStatusViewVO.getAwbType().equals("OVERPAID")) {
				statusView.setAwbType("Over Paid");
			} else {
			statusView.setAwbType(customerStatusViewVO.getAwbType());
			}
			statusView.setAwbCount(customerStatusViewVO.getAwbCount());
			statusView.setAwbAmount(customerStatusViewVO.getAwbAmount());
			if (statusView.getAwbType().equals("Unapplied")) {
				awbCount = awbCount - statusView.getAwbCount();
				awbAmount = awbAmount - statusView.getAwbAmount();
			} else {
				awbCount = awbCount + statusView.getAwbCount();
				awbAmount = awbAmount + statusView.getAwbAmount();
			}
			statusViews.add(statusView);
		}*/
		
		for (CustomerStatusViewVO customerStatusViewVO : statusViewVOs) {
			if(customerStatusViewVO.getAwbType()!=null){
			if (customerStatusViewVO.getAwbType().equals("UNPAID")) {
				customerStatusViewVO.setAwbType("Prime Billed");
			} else if (customerStatusViewVO.getAwbType().equals("SHORTPAID")) {
				customerStatusViewVO.setAwbType("Short Paid");
			} else if (customerStatusViewVO.getAwbType().equals("OVERPAID")) {
				customerStatusViewVO.setAwbType("Over Paid");
			}
			}
		}
		
		StatusView statusView1 = new StatusView();
		statusView1.setAwbType("Prime Billed");
		statusViews.add(statusView1);
		StatusView statusView2 = new StatusView();
		statusView2.setAwbType("Short Paid");
		statusViews.add(statusView2);
		StatusView statusView3 = new StatusView();
		statusView3.setAwbType("Over Paid");
		statusViews.add(statusView3);
		StatusView statusView4 = new StatusView();
		statusView4.setAwbType("Rebill");
		statusViews.add(statusView4);
		StatusView statusView5 = new StatusView();
		statusView5.setAwbType("Unapplied");
		statusViews.add(statusView5);
		
		int awbCount = 0;
		double awbAmount = 0.0;
		
		for (StatusView statusView : statusViews) {
			for (CustomerStatusViewVO customerStatusViewVO : statusViewVOs) {
				if(customerStatusViewVO.getAwbType()!=null){
				if (statusView.getAwbType().equals(
						customerStatusViewVO.getAwbType())) {
					statusView.setAwbCount(customerStatusViewVO
							.getAwbCount());
					statusView.setAwbAmount(customerStatusViewVO
							.getAwbAmount());
				}
			}
			}
			if (statusView.getAwbType().equals("Unapplied")) {
				awbCount = awbCount - statusView.getAwbCount();
				awbAmount = awbAmount - statusView.getAwbAmount();
			} else {
				awbCount = awbCount + statusView.getAwbCount();
				if(statusView.getAwbType().equalsIgnoreCase("Over Paid")){
					awbAmount = awbAmount - statusView.getAwbAmount();
				}else{
				awbAmount = awbAmount + statusView.getAwbAmount();
				}
			}
		}
		
		StatusView statusViewTot = new StatusView();
		statusViewTot.setAwbType("TOTAL");
		statusViewTot.setAwbCount(awbCount);
		statusViewTot.setAwbAmount(awbAmount);
		statusViews.add(statusViewTot);
		return statusViews;
	}
	public static List<CCADetails> constructCCADetails(
			List<CCADetailsVO> ccaDetailsVOs) {
		
		
		List<CCADetails> ccaDetails = new ArrayList<CCADetails>();
		for (CCADetailsVO ccaDetailsVO : ccaDetailsVOs) {
			CCADetails ccaDetail = new CCADetails();
			ccaDetail.setAwb(ccaDetailsVO.getAwb());
			ccaDetail.setAwbDate(ccaDetailsVO.getAwbDate());
			ccaDetail.setBillingStatus(ccaDetailsVO.getBillingStatus());
			ccaDetail.setCcaAmount(ccaDetailsVO.getCcaAmount());
			ccaDetail.setCcaDate(ccaDetailsVO.getCcaDate());
			ccaDetail.setCcaNumber(ccaDetailsVO.getCcaNumber());
			ccaDetail.setCcaReason(ccaDetailsVO.getCcaReason());
			ccaDetail.setCcaStatus(ccaDetailsVO.getCcaStatus());
			ccaDetail.setCcaType(ccaDetailsVO.getCcaType());			
			ccaDetails.add(ccaDetail);
		}
		
		return ccaDetails;
	}
	public static Collection<PaymentDetails> constructPaymentDetails(
			Collection<PaymentDetailsVO> paymentDetailsVOs) {
		
		
		Collection<PaymentDetails> paymentDetails = new ArrayList<PaymentDetails>();
		for (PaymentDetailsVO paymentDetailsVO : paymentDetailsVOs) {
			PaymentDetails paymentDetail = new PaymentDetails();
			paymentDetail.setAwb(paymentDetailsVO.getAwb());
			paymentDetail.setBillingIndicator(paymentDetailsVO.getBillingIndicator());
			paymentDetail.setCaseClosed(paymentDetailsVO.getCaseClosed());
			paymentDetail.setCcaNumber(paymentDetailsVO.getCcaNumber());
			paymentDetail.setDueAmount(paymentDetailsVO.getDueAmount());
			paymentDetail.setInternalCCAAmount(paymentDetailsVO.getInternalCCAAmount());
			paymentDetail.setPaymentStatus(paymentDetailsVO.getPaymentStatus());
			paymentDetail.setTotalBilledAmount(paymentDetailsVO.getTotalBilledAmount());
			paymentDetail.setTotalSettledAmount(paymentDetailsVO.getTotalSettledAmount());
			paymentDetail.setRemarks(paymentDetailsVO.getRemarks());
			paymentDetails.add(paymentDetail);
		}
		
		return paymentDetails;
	}

}
