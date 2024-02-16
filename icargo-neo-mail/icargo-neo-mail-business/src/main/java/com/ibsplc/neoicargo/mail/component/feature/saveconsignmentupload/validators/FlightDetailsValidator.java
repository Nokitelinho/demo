package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload.validators;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.masters.airline.AirlineWebAPI;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("flightDetailsValidator")
public class FlightDetailsValidator extends Validator<ConsignmentDocumentVO> {

	@Autowired
	private AirlineWebAPI airlineWebAPI;
	@Autowired
	private FlightOperationsProxy flightOperationsProxy;
	
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
			throws SystemException, BusinessException {
		String mailOrigin;
		String mailDestination;
		Collection<FlightValidationVO> flightValidationVOs = null;
		AirlineValidationVO airlineValidationVO = null;
		ArrayList<ErrorVO> errorList = new ArrayList<>();
		ArrayList<String> carriers = new ArrayList<>();
		carriers.add(routingInConsignmentVO.getOnwardCarrierCode());
			List<AirlineModel> airlineModel = airlineWebAPI.validateAlphaCode(carriers);
			//TODO: NEO : Exception handling to be verified as part of NEO coding
		/*airlineValidationVO = sharedAirlineProxy.getInstance().get(SharedAirlineProxy.class).validateAlphaCode(
				routingInConsignmentVO.getCompanyCode(), routingInConsignmentVO.getOnwardCarrierCode());*/

		if (Objects.nonNull(airlineModel)) {
			FlightFilterVO flightFilterVO = constructFlightFilterVO(consignmentDocumentVO, routingInConsignmentVO,
					airlineValidationVO);
			if (Objects.nonNull(flightFilterVO.getFlightDate())) {
				flightValidationVOs = flightOperationsProxy.validateFlightForAirport(flightFilterVO);
				if (Objects.nonNull(flightValidationVOs)&&!flightValidationVOs.isEmpty()) {
					flightMap.put(getKey(routingInConsignmentVO), flightValidationVOs);
					addtoFeatureContext(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP,
							flightMap);
				}
			}
			if (flightCount == 0) {
				mailOrigin = routingInConsignmentVO.getPol();
				addtoFeatureContext(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_ORIGIN, mailOrigin);
			}
			if (++flightCount == consignmentDocumentVO.getRoutingInConsignmentVOs().size()) {
				mailDestination = routingInConsignmentVO.getPou();
				addtoFeatureContext(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_DESTINATION,
						mailDestination);
			}
		}
		validateAndRaiseBusinessException(consignmentDocumentVO, flightValidationVOs);
		return flightCount;
	}

	private void validateAndRaiseBusinessException(ConsignmentDocumentVO consignmentDocumentVO,
			Collection<FlightValidationVO> flightValidationVOs) throws MailOperationsBusinessException {
		if (Objects.isNull(flightValidationVOs)||flightValidationVOs.isEmpty()) {
			MailOperationsBusinessException mailOperationsBusinessException = new MailOperationsBusinessException();
			Collection<ErrorVO> errors = new ArrayList<>();
			String[] errorData = new String[] { String.valueOf(consignmentDocumentVO.getLineCount()),
					consignmentDocumentVO.getConsignmentNumber() };
			ErrorVO error = new ErrorVO(SaveConsignmentUploadFeatureConstants.ERROR_INVALID_FLIGHT_DETAILS,errorData);
			//TODO:Neo to validate whether error detail is needed
			//error.setErrorDetail(SaveConsignmentUploadFeatureConstants.ERROR_INVALID_FLIGHT_DETAILS);
			errors.add(error);
			mailOperationsBusinessException.addErrors(errors);
			throw mailOperationsBusinessException;
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
