package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.common;

import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.framework.web.json.PageResult;

public class BillingInvoiceDetails {
	private PageResult<InvoiceDetails> invoiceDetails;
	private Collection<ReceivablesAgeing> receivablesAgeing;
	private ReceivablesCreditInfo receivablesCreditInfo;
	public ReceivablesCreditInfo getReceivablesCreditInfo() {
		return receivablesCreditInfo;
	}

	public Collection<ReceivablesAgeing> getReceivablesAgeing() {
		return receivablesAgeing;
	}

	public void setReceivablesAgeing(Collection<ReceivablesAgeing> receivablesAgeing) {
		this.receivablesAgeing = receivablesAgeing;
	}

	public void setReceivablesCreditInfo(ReceivablesCreditInfo receivablesCreditInfo) {
		this.receivablesCreditInfo = receivablesCreditInfo;
	}

	private Map<String, String> statusCount;
	private Collection<StatusView> statusView;

	public Map<String, String> getStatusCount() {
		return statusCount;
	}

	public void setStatusCount(Map<String, String> statusCount) {
		this.statusCount = statusCount;
	}

	public PageResult<InvoiceDetails> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(PageResult<InvoiceDetails> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public Collection<StatusView> getStatusView() {
		return statusView;
	}

	public void setStatusView(Collection<StatusView> statusView) {
		this.statusView = statusView;
	}

	

}
