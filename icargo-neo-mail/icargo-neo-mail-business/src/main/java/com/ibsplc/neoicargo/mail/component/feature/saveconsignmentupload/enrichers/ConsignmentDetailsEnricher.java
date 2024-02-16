package com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload.enrichers;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.orchestration.DependsOn;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@DependsOn(SaveConsignmentUploadFeatureConstants.PA_CODE_VALIDATOR)
@Component("consignmentDetailsEnricher")
@Slf4j
public class ConsignmentDetailsEnricher extends Enricher<ConsignmentDocumentVO> {

	private static final Log LOGGER = LogFactory.getLogger(SaveConsignmentUploadFeatureConstants.MODULE_SUBMODULE);
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private Quantities quantities;

	@Override

	public void enrich(ConsignmentDocumentVO consignmentDocumentVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "enrich");
		setPACode(consignmentDocumentVO);
		String uspsDomesticPA=null;
		try{
			uspsDomesticPA =parameterService.getParameter(MailConstantsVO.USPS_DOMESTIC_PA, ParameterType.SYSTEM_PARAMETER);
		}catch(BusinessException businessException){
			log.error(businessException.toString());
		}
		String mailOrigin = (String) getContextObject(
				SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_ORIGIN);
		String mailDestination = (String) getContextObject(
				SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_DESTINATION);
		Map<String, Collection<FlightValidationVO>> flightMap = (Map<String, Collection<FlightValidationVO>>) getContextObject(
				SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_FLIGHT_VALIDATION_MAP);
		enrichRoutingInConsignment(consignmentDocumentVO, flightMap);
		enrichMailInConsignment(consignmentDocumentVO, uspsDomesticPA, mailOrigin, mailDestination);
		if (!("MALCSGUPL").equals(consignmentDocumentVO.getSource())) {
			consignmentDocumentVO.setAirportCode(mailOrigin);
		}
		consignmentDocumentVO.setOperation(isNotNullAndEmpty(consignmentDocumentVO.getOperation())
				&& consignmentDocumentVO.getOperation().startsWith(MailConstantsVO.OPERATION_INBOUND)
						? MailConstantsVO.OPERATION_INBOUND : MailConstantsVO.OPERATION_OUTBOUND);
		LOGGER.exiting(this.getClass().getSimpleName(), "enrich");
	}

	private void enrichMailInConsignment(ConsignmentDocumentVO consignmentDocumentVO, String uspsDomesticPA,
										 String mailOrigin, String mailDestination) {
		if (Objects.nonNull(consignmentDocumentVO.getMailInConsignmentVOs())
				&& !consignmentDocumentVO.getMailInConsignmentVOs().isEmpty()) {
			for (MailInConsignmentVO mailInConsignmentVO : consignmentDocumentVO.getMailInConsignmentVOs()) {
				mailInConsignmentVO.setPaCode(consignmentDocumentVO.getPaCode());
				mailInConsignmentVO.setMailOrigin(mailOrigin);
				mailInConsignmentVO.setMailDestination(mailDestination);
				mailInConsignmentVO.setStatedBags(1);
				if (isNotNullAndEmpty(mailInConsignmentVO.getPaCode())) {
					if (mailInConsignmentVO.getPaCode().equals(uspsDomesticPA)) {
						enrichUSPSDomesticMailInConsignmentDetails(mailOrigin, mailDestination, mailInConsignmentVO);
					} else {
						enrichInternationalMailInConsignmentDetails(mailInConsignmentVO);
					}
				}
			}
		}
	}

	private void enrichUSPSDomesticMailInConsignmentDetails(String mailOrigin, String mailDestination,
			MailInConsignmentVO mailInConsignmentVO) {
		double weight;
		if (isNotNullAndEmpty(mailInConsignmentVO.getMailId())
				&& mailInConsignmentVO.getMailId().length() == 12) {
			weight = Double.parseDouble(mailInConsignmentVO.getMailId().substring(10, 12));
			mailInConsignmentVO.setStatedWeight(quantities.getQuantity(UnitConstants.MAIL_WGT, BigDecimal.ZERO, BigDecimal.valueOf(weight), UnitConstants.WEIGHT_UNIT_POUND));
			mailInConsignmentVO.setOriginExchangeOffice(mailOrigin);
			mailInConsignmentVO.setDestinationExchangeOffice(mailDestination);
			mailInConsignmentVO.setMailCategoryCode(MailConstantsVO.MAIL_CATEGORY_SAL);
			mailInConsignmentVO.setMailClass(mailInConsignmentVO.getMailId().substring(3, 4));
			mailInConsignmentVO.setMailSubclass(MailConstantsVO.MAIL_CATEGORY_SAL + "X");
			mailInConsignmentVO.setDsn(mailInConsignmentVO.getMailId().substring(4, 8));
			mailInConsignmentVO.setReceptacleSerialNumber("000");
			mailInConsignmentVO.setHighestNumberedReceptacle("9");
			mailInConsignmentVO.setRegisteredOrInsuredIndicator("9");
		}
	}

	private void enrichInternationalMailInConsignmentDetails(MailInConsignmentVO mailInConsignmentVO) {
		double weight;
		if (isNotNullAndEmpty(mailInConsignmentVO.getMailId())
				&& mailInConsignmentVO.getMailId().length() == 29) {
			weight = Double.parseDouble(mailInConsignmentVO.getMailId().substring(25, 29))/10;

			mailInConsignmentVO.setStatedWeight(quantities.getQuantity(UnitConstants.MAIL_WGT, BigDecimal.ZERO, BigDecimal.valueOf(weight), "K"));
			mailInConsignmentVO
					.setOriginExchangeOffice(mailInConsignmentVO.getMailId().substring(0, 6));
			mailInConsignmentVO
					.setDestinationExchangeOffice(mailInConsignmentVO.getMailId().substring(6, 12));
			mailInConsignmentVO.setMailCategoryCode(mailInConsignmentVO.getMailId().substring(12, 13));
			mailInConsignmentVO.setMailClass(mailInConsignmentVO.getMailId().substring(13, 14));
			mailInConsignmentVO.setMailSubclass(mailInConsignmentVO.getMailId().substring(13, 15));
			mailInConsignmentVO
					.setYear(Integer.parseInt(mailInConsignmentVO.getMailId().substring(15, 16)));
			mailInConsignmentVO.setDsn(mailInConsignmentVO.getMailId().substring(16, 20));
			mailInConsignmentVO
					.setReceptacleSerialNumber(mailInConsignmentVO.getMailId().substring(20, 23));
			mailInConsignmentVO
					.setHighestNumberedReceptacle(mailInConsignmentVO.getMailId().substring(23, 24));
			mailInConsignmentVO
					.setRegisteredOrInsuredIndicator(mailInConsignmentVO.getMailId().substring(24, 25));
		}
	}

	private void enrichRoutingInConsignment(ConsignmentDocumentVO consignmentDocumentVO,
			Map<String, Collection<FlightValidationVO>> flightMap) {
		Collection<FlightValidationVO> flightValidationVOs;
		if (Objects.nonNull(consignmentDocumentVO.getRoutingInConsignmentVOs())
				&& !consignmentDocumentVO.getRoutingInConsignmentVOs().isEmpty()) {
			for (RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getRoutingInConsignmentVOs()) {
				if (Objects.nonNull(flightMap) && !flightMap.isEmpty()) {
					flightValidationVOs = flightMap.get(getKey(routingInConsignmentVO));
					routingInConsignmentVO
							.setOnwardFlightNumber(flightValidationVOs.iterator().next().getFlightNumber());
					routingInConsignmentVO
							.setOnwardCarrierSeqNum(flightValidationVOs.iterator().next().getFlightSequenceNumber());
					routingInConsignmentVO
							.setOnwardCarrierId(flightValidationVOs.iterator().next().getFlightCarrierId());
					routingInConsignmentVO
							.setLegSerialNumber(flightValidationVOs.iterator().next().getLegSerialNumber());
					routingInConsignmentVO.setScheduledArrivalDate(flightValidationVOs.iterator().next().getSta().toZonedDateTime());
					routingInConsignmentVO.setPaCode(consignmentDocumentVO.getPaCode());
					routingInConsignmentVO.setOnwardCarrierCode(flightValidationVOs.iterator().next().getCarrierCode());
					routingInConsignmentVO.setFlightCarrierCode(flightValidationVOs.iterator().next().getCarrierCode());
				}
			}
		}
	}

	private void setPACode(ConsignmentDocumentVO consignmentDocumentVO) {
		String paCode = (String) getContextObject(SaveConsignmentUploadFeatureConstants.SAVE_CONSIGNMENT_UPLOAD_PACODE);
		consignmentDocumentVO.setPaCode(paCode);
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
