package com.ibsplc.neoicargo.mail.component.proxy.webservices.lh;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailStockRetrievalWSProxy {
	private static final String CLASS = "MailStockRetrivalWSProxy";
	private static final String SERVICE_ID = "AwbAgentStockValidation-service";
	private static final String OPERATION_ID = "requestAWBForBooking";
	private static final String MODULE_SUBMODULE = "MAIL OPERATIONS RETRIEVEStOCK";

	public DocumentFilterVO stockRetrievalForPAWB(DocumentFilterVO documentFilterVO)  {

		//TODO: Neo to correct
		//DocumentFilterVO shpVldVO = despatchRequest(SERVICE_ID, OPERATION_ID, new Object[] { documentFilterVO });
		//log.debug(CLASS + " : " + OPERATION_ID + " Exiting");
		return null;
	}
}
