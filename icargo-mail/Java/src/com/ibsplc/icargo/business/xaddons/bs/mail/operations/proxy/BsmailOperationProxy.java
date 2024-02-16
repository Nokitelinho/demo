package com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Module("bsmail")
@SubModule("operations")
public class BsmailOperationProxy extends ProductProxy {

	private Log log = LogFactory.getLogger("BSMAIL_OPERATIONS");

	private static final String MODULE = "BsmailOperationProxy";

	public boolean saveContainerTransfer(ContainerDetailsVO containerDetailsVO,
			MailFlightSummaryVO mailFlightSummaryVO) throws SystemException, ProxyException{
		return  despatchRequest("saveContainerTransfer",containerDetailsVO,mailFlightSummaryVO);
	}

	public boolean saveContainerforReassigns(
			ContainerDetailsVO containerDetailsVO,
			MailFlightSummaryVO mailFlightSummaryVO) throws SystemException, ProxyException{
		return  despatchRequest("saveContainerforReassigns",containerDetailsVO,mailFlightSummaryVO);
	} 
	

}
