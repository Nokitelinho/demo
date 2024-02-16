package com.ibsplc.icargo.business.addons.mail.operations.proxy;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("addonsmail")
@SubModule("operations")
public class AddonsMailOperationProxy extends ProductProxy {

	public boolean saveContainerTransfer(ContainerDetailsVO containerDetailsVO, MailFlightSummaryVO mailFlightSummaryVO)
			throws SystemException {
		try {
			return despatchRequest("saveContainerTransfer", containerDetailsVO, mailFlightSummaryVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, proxyException);
		}

	}
}
