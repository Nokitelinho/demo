package com.ibsplc.icargo.business.mail.operations.proxy.webservices.lh;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.services.jaxws.proxy.WebServiceProxy;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class MailStockRetrievalWSProxy extends WebServiceProxy {

	

	private static final String CLASS = "MailStockRetrivalWSProxy";

	private static final String SERVICE_ID = "AwbAgentStockValidation-service";

	private static final String OPERATION_ID = "requestAWBForBooking";
	private static final String MODULE_SUBMODULE = "MAIL OPERATIONS RETRIEVEStOCK";
	private static final Log LOGGER = LogFactory.getLogger(MODULE_SUBMODULE);

	public DocumentFilterVO stockRetrievalForPAWB(
			DocumentFilterVO documentFilterVO) throws WebServiceException,
			SystemException {
		LOGGER.entering(CLASS, OPERATION_ID);
		
		DocumentFilterVO shpVldVO = despatchRequest(SERVICE_ID,
				OPERATION_ID, new Object[] { documentFilterVO });
		
		LOGGER.exiting(CLASS, OPERATION_ID);
		return shpVldVO;
	}
	
}
