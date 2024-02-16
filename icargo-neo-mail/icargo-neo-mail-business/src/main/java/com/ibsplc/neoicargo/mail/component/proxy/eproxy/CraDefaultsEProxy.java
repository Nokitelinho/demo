package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;

import java.util.Collection;

@EProductProxy(module = "cra", submodule = "defaults", name = "craDefaultsEProxy")
public interface CraDefaultsEProxy {
	public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
			InvoiceTransactionLogVO invoiceTransactionLogVO );
	public void updateTransactionandRemarks(
			InvoiceTransactionLogVO invoiceTransactionLogVO );

}
