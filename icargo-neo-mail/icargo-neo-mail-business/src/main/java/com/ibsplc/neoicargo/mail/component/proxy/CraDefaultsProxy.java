package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.CraDefaultsEProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CraDefaultsProxy {

	@Autowired
	private CraDefaultsEProxy craDefaultsEProxy;

	public InvoiceTransactionLogVO initiateTransactionLogForInvoiceGeneration(
			InvoiceTransactionLogVO invoiceTransactionLogVO ){

		try {
			return craDefaultsEProxy.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
		}catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}
	}

	public void updateTransactionandRemarks(
			InvoiceTransactionLogVO invoiceTransactionLogVO ){
		try {
			 craDefaultsEProxy.updateTransactionandRemarks(invoiceTransactionLogVO);
		}catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}
	}

}
