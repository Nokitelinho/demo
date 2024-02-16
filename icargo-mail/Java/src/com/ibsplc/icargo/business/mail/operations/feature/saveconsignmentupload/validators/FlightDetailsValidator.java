package com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.validators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.SharedProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.Validator;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveConsignmentUploadFeatureConstants.FLIGHT_DETAILS_VALIDATOR)
public class FlightDetailsValidator extends Validator<ConsignmentDocumentVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveConsignmentUploadFeatureConstants.MODULE_SUBMODULE);
	@Override
	public void validate(ConsignmentDocumentVO consignmentDocumentVO) throws BusinessException, SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "validate");
		int flightCount = 0;
		Map<String, Collection<FlightValidationVO>> flightMap = new HashMap<>();
		if (!("MALCSGUPL").equals(consignmentDocumentVO.getSource())) {
			for (RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getRoutingInConsignmentVOs()) {
				flightCount = validateFlight(consignmentDocumentVO, flightCount, flightMap, routingInConsignmentVO);
			}
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "validate");
	}

	private int validateFlight(ConsignmentDocumentVO consignmentDocumentVO, int flightCount,
			Map<String, Collection<FlightValidationVO>> flightMap, RoutingInConsignmentVO routingInConsignmentVO)
			throws SystemException, SharedProxyException, ProxyException, MailTrackingBusinessException {
		String mailOrigin;
		String mailDestination;
		Collection<FlightValidationVO> flightValidationVOs = null;
		AirlineValidationVO airlineValidationVO = null;
		airlineValidationVO = Proxy.getInstance().get(SharedAirlineProxy.class).validateAlphaCode(
				routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getOnwardCarrierCode());
		if (Objects.nonNull(airlineValidationVO)) {
			FlightFilterVO flightFilterVO = constructFlightFilterVO(consignmentDocumentVO, routingInConsignmentVO,
					airlineValidationVO);
			if (Objects.nonNull(flightFilterVO.getFlightDate())) {
				flightValidationVOs = Proxy.getInstance().get(FlightOperationsProxy.class).validateFlight(flightFilterVO);
				if (Objects.nonNull(flightValidationVOs)&&!flightValidationVOs.isEmpty()) {
					flightMap.put(getKey(routingInConsignmentVO), flightValidationVOs);
					addToContext(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP,
							flightMap);
				}
			}
			if (flightCount == 0) {
				mailOrigin = routingInConsignmentVO.getPol();
				addToContext(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_ORIGIN, mailOrigin);
			}
			if (++flightCount == consignmentDocumentVO.getRoutingInConsignmentVOs().size()) {
				mailDestination = routingInConsignmentVO.getPou();
				addToContext(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_DESTINATION,
						mailDestination);
			}
		}
		validateAndRaiseBusinessException(consignmentDocumentVO, flightValidationVOs);
		return flightCount;
	}

	private void validateAndRaiseBusinessException(ConsignmentDocumentVO consignmentDocumentVO,
			Collection<FlightValidationVO> flightValidationVOs) throws MailTrackingBusinessException {
		if (Objects.isNull(flightValidationVOs)||flightValidationVOs.isEmpty()) {
			MailTrackingBusinessException mailTrackingBusinessException = new MailTrackingBusinessException();
			Collection<ErrorVO> errors = new ArrayList<>();
			String[] errorData = new String[] { String.valueOf(consignmentDocumentVO.getLineCount()),
					consignmentDocumentVO.getConsignmentNumber() };
			ErrorVO error = new ErrorVO(SaveConsignmentUploadFeatureConstants.ERROR_INVALID_FLIGHT_DETAILS,errorData);
			error.setErrorDescription(SaveConsignmentUploadFeatureConstants.ERROR_INVALID_FLIGHT_DETAILS);
			errors.add(error);
			mailTrackingBusinessException.addErrors(errors);
			throw mailTrackingBusinessException;
		}
	}

	private FlightFilterVO constructFlightFilterVO(ConsignmentDocumentVO consignmentDocumentVO,
			RoutingInConsignmentVO routingInConsignmentVO, AirlineValidationVO airlineValidationVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(routingInConsignmentVO.getCompanyCode());
		flightFilterVO.setFlightNumber(formatFlightNumber(routingInConsignmentVO.getOnwardFlightNumber()));
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
		flightFilterVO.setFlightDate(routingInConsignmentVO.getOnwardFlightDate());
		flightFilterVO.setStation(routingInConsignmentVO.getPol());
		flightFilterVO.setDirection(isNotNullAndEmpty(consignmentDocumentVO.getOperation())
				&& consignmentDocumentVO.getOperation().contains(MailConstantsVO.OPERATION_INBOUND)
						? MailConstantsVO.OPERATION_INBOUND : MailConstantsVO.OPERATION_OUTBOUND);
		return flightFilterVO;
	}

	private String formatFlightNumber(String flightNumber) {
		StringBuilder sb = new StringBuilder();
		int lastCharInAscii = flightNumber.charAt(flightNumber.length() - 1);
		int flightNumberLength = MailConstantsVO.FLIGHT_NUMBER_LENGTH;
		if (lastCharInAscii < 65 || lastCharInAscii > 90) {
			flightNumberLength--;
		}
		int iterIdx = flightNumberLength - flightNumber.length();
		while (iterIdx > 0) {
			flightNumber = sb.append(MailConstantsVO.PAD_DIGIT).append(flightNumber).toString();
			iterIdx--;
		}
		return flightNumber;
	}

	private String getKey(RoutingInConsignmentVO routingInConsignmentVO) {
		String key = null;
		key = new StringBuilder().append(routingInConsignmentVO.getCompanyCode())
				.append(routingInConsignmentVO.getConsignmentNumber()).append(routingInConsignmentVO.getPol())
				.append(routingInConsignmentVO.getPou()).append(routingInConsignmentVO.getOnwardFlightNumber())
				.toString();
		return key;
	}

	private static boolean isNotNullAndEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}

}
