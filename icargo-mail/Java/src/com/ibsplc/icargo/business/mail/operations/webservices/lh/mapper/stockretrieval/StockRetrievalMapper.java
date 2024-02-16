package com.ibsplc.icargo.business.mail.operations.webservices.lh.mapper.stockretrieval;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.services.jaxws.proxy.mapper.TypeMapper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.lhsystems.xsd.esb.AWBStockService.AwbForBookingRequest;
import com.lhsystems.xsd.esb.AWBStockService.AwmStockResponse;
import com.lhsystems.xsd.esb.AWBStockService.ProcessOriginator;
import com.lhsystems.xsd.esb.AWBStockService.ServiceHeaderType;
import com.lhsystems.xsd.esb.AWBStockService.serviceheader.ServiceInfo;

public class StockRetrievalMapper implements TypeMapper {
	private static final String CLASS = "StockRetrievalMapper";
	private static final String MODULE_SUBMODULE = "MAIL OPERATIONS RETRIEVEStOCK";
	private static final Log LOGGER = LogFactory.getLogger(MODULE_SUBMODULE);

	/**
	 * Added for ICRD-137307
	 */
	@Override
	public Object[] mapParameters(Object[] parameters) throws SystemException {
		LOGGER.entering("mapParameters", CLASS);
		AwbForBookingRequest requestType = new AwbForBookingRequest();
		populateRequest(requestType, parameters);
		LOGGER.exiting("mapParameters", CLASS);
		return new Object[] { requestType };
		
	}

	@Override
	public <T> T mapResult(T result) throws SystemException {
		LOGGER.entering("mapResult", CLASS);
		DocumentFilterVO documentFilterVO = null;
		AwmStockResponse response = (AwmStockResponse) result;
		if (response != null && response.getAwbResponse()!=null) {
			documentFilterVO = new DocumentFilterVO();
			documentFilterVO.setShipmentPrefix(response.getAwbResponse().getAwbPrefix());
			documentFilterVO.setDocumentNumber(response.getAwbResponse().getAwbNumber());
		}
		LOGGER.exiting("mapResult", CLASS);
		return (T) documentFilterVO;
	}
	
	private void populateRequest(AwbForBookingRequest requestType,
			Object[] parameters) {
		LOGGER.entering("populateRequest",CLASS);
		updateMessageHeader(requestType);
		updateRequestFilterData(requestType, parameters);
		LOGGER.exiting("populateRequest", CLASS);
	}
	
	private void updateMessageHeader(AwbForBookingRequest requestType) {
		LOGGER.entering("updateMessageHeader", CLASS);
		StockMessageHeader msgHeader = new StockMessageHeader();
		ServiceHeaderType header = msgHeader.messageHeader();
		ProcessOriginator originator = new ProcessOriginator();
		originator.setApplicationName("iCargo");
		originator.setProcessName("Booking Request");
		header.setProcessOriginator(originator);
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setServiceName("AWBStockService");
		serviceInfo.setServiceOperation(
				"requestAWBForBooking");
		serviceInfo.setServiceVersion("1_0");
	    requestType.setInterfaceHeader(header);
	    LOGGER.exiting("updateMessageHeader",CLASS);
		
	}
	
	private void updateRequestFilterData(
			AwbForBookingRequest requestType,
			Object[] parameters) {
		LOGGER.entering("updateRequestFilterData", CLASS);
		DocumentFilterVO documentFilterVO = (DocumentFilterVO) parameters[0];
		requestType.setCdbNumber(documentFilterVO.getStockOwner());
		requestType.setAwbPrefix(documentFilterVO.getShipmentPrefix());
		requestType.setOrigin(documentFilterVO.getAwbOrigin());
		requestType.setDestination(documentFilterVO.getAwbDestination());
		LocalDate date = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				true);
		String msgID = "FWB";
		msgID = msgID.concat(date.toDisplayFormat());
		requestType.setConversationId(msgID);
		LOGGER.exiting("updateRequestFilterData", CLASS);
	}

	
}
