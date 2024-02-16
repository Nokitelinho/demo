package com.ibsplc.icargo.presentation.web.model.mail.mra.gpareporting;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.InvoicDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.InvoicFilter;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class InvoicEnquiryModel extends AbstractScreenModel{
	
	private static final String PRODUCT = "mail";
	private static final String SUBPRODUCT = "mra";
	private static final String SCREENID = " mail.mra.gpareporting.ux.invoicenquiry";	
	
	
	
	private InvoicFilter invoicFilter;
	
	private PageResult<InvoicDetails> invoicDetailsPageResult;
	
	private InvoicDetails selectedInvoicDetail;
	
	private Collection<InvoicDetails> selectedInvoicDetails;
	
	private Map<String, Collection<OneTime>> oneTimeValues;  //Added by A-7929

	private Collection<String> airportCodes;//added by a-8464
	
	private Collection<String> mailSubClassCodes;//added by a-7929
	
	private String toProcessStatus;
	
	private String groupRemarks;
	
	private String raiseClaimFlag;  //Added by A-7929
	
	private String currencyCode; //Added by A-7929
	
	
	
	public String getCurrencyCode() {
		return currencyCode;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	
	public String getScreenId() {
		return SCREENID;
	}

	
	public String getProduct() {
		return PRODUCT;
	}

	
	public String getSubProduct() {
		return SUBPRODUCT;
	}


	public InvoicFilter getInvoicFilter() {
		return invoicFilter;
	}


	public void setInvoicFilter(InvoicFilter invoicFilter) {
		this.invoicFilter = invoicFilter;
	}


	public PageResult<InvoicDetails> getInvoicDetails() {
		return invoicDetailsPageResult;
	}


	public void setInvoicDetails(PageResult<InvoicDetails> invoicDetails) {
		this.invoicDetailsPageResult = invoicDetails;
	}


	public InvoicDetails getSelectedInvoicDetail() {
		return selectedInvoicDetail;
	}


	public void setSelectedInvoicDetail(InvoicDetails selectedInvoicDetail) {
		this.selectedInvoicDetail = selectedInvoicDetail;
	}


	public Collection<InvoicDetails> getSelectedInvoicDetails() {
		return selectedInvoicDetails;
	}


	public void setSelectedInvoicDetails(Collection<InvoicDetails> selectedInvoicDetails) {
		this.selectedInvoicDetails = selectedInvoicDetails;
	}


	public String getToProcessStatus() {
		return toProcessStatus;
	}


	public void setToProcessStatus(String processStatus) {
		this.toProcessStatus = processStatus;
	}


	public String getGroupRemarks() {
		return groupRemarks;
	}


	public void setGroupRemarks(String groupRemarks) {
		this.groupRemarks = groupRemarks;
	}

	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}


	public Collection<String> getAirportCodes() {
		return airportCodes;
	}


	public void setAirportCodes(Collection<String> airportCodes) {
		this.airportCodes = airportCodes;
	}


	public String getRaiseClaimFlag() {
		return raiseClaimFlag;
	}


	public void setRaiseClaimFlag(String raiseClaimFlag) {
		this.raiseClaimFlag = raiseClaimFlag;
	}


	public Collection<String> getMailSubClassCodes() {
		return mailSubClassCodes;
	}


	public void setMailSubClassCodes(Collection<String> mailSubClassCodes) {
		this.mailSubClassCodes = mailSubClassCodes;
	}


	

}






