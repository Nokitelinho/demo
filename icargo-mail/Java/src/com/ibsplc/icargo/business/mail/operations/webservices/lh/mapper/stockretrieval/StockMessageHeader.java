package com.ibsplc.icargo.business.mail.operations.webservices.lh.mapper.stockretrieval;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.lhsystems.xsd.esb.AWBStockService.ServiceHeaderType;

public class StockMessageHeader {
	private static final String CLASS = "StockMessageHeader";
	private static final String MODULE_SUBMODULE = "MAIL OPERATIONS";
	private static final Log LOGGER = LogFactory.getLogger(MODULE_SUBMODULE);

	public ServiceHeaderType messageHeader() {
		LOGGER.entering(CLASS, CLASS);
		ServiceHeaderType header = new ServiceHeaderType();
		LocalDate date = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				true);
		String msgID = "FWB";
		msgID = msgID.concat(date.toDisplayFormat());
		header.setConversationID(msgID);
		header.setMessageID(msgID);
		header.setMessageType("request");
		header.setOriginalMessageDateTimeUTC(date.toDisplayFormat());
		header.setCurrentMessageDateTimeUTC(date.toDisplayFormat());
		header.setHostname("ESB");
		LOGGER.exiting(CLASS, CLASS);
		return header;
		
		
	}

}
