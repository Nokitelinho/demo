/**
 *	Java file	: 	com.ibsplc.icargo.business.operations.flthandling.feature.savebreakdown.enrichers.AttachStorageUnitDetailsEnricher.java
 *
 *	Created by	:	A-8330
 *	Created on	:	12-Feb-2020
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.enrichers;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.RatingDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.RoutingVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerPreferenceVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.DependsOn;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.*;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.OperationsShipmentUtil;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

/**
 * Java file :
 * com.ibsplc.icargo.business.operations.flthandling.feature.savebreakdown.enrichers.AttachStorageUnitDetailsEnricher.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-8330 :
 * 12-Feb-2020 : Draft
 */
@Component(AutoAttachAWBDetailsFeatureConstants.SHIPMENT_DETAIL_ENRICHER)
public class ShipmentDetailVOEnricher extends Enricher<MailManifestDetailsVO> {

	private static final String MODULENAME = "mail.operations";
	private static final String SPACE = " ";
	@Autowired
	private ContextUtil contextUtil;

	@Autowired
	private MailController mailController;

	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;

	@Autowired
	private FlightOperationsProxy flightOperationsProxy;


	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;

	@Autowired
	private OperationsShipmentUtil operationsShipmentUtil;

	@Autowired
	private MeasureMapper measureMapper;

	@Autowired
	private Quantities quantities;

	@Autowired
	private ProductDefaultsProxy productDefaultsProxy;



	@Override
	public void enrich(MailManifestDetailsVO mailManifestDetailsVO)  {
		
		
		Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestDetailsVO.getContainerDetailsVOs();
		OperationalFlightVO operationalFlightVO = mailManifestDetailsVO.getOperationalFlightVO();
		if(Objects.isNull(operationalFlightVO)){
			return;
		}
		String companyCode = operationalFlightVO.getCompanyCode();
		DocumentFilterVO documentFilterVO = mailManifestDetailsVO.getDocumentFilterVO();
		AirlineValidationVO airlineValidationVO = mailManifestDetailsVO.getAirlineValidationVO();
		Collection<MailbagVO> mailbagVOs = mailManifestDetailsVO.getMailbagVOs();
		DocumentValidationVO documentValidationVO = mailManifestDetailsVO.getDocumentValidationVO();
		boolean checkAWBAttach = mailManifestDetailsVO.isCheckAWBAttached();
		ShipmentValidationVO shipmentValidationVO = null;
		ShipmentDetailVO shipmentDetailVO = null;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			String shipper = mailbagVOs.iterator().next().getPaCode();
			String consignee = (String) getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_PACODE);
			String agentName = null;
			String iataCode = null;
			
			String agentCode = (String) getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
			String shipmentPrefix = airlineValidationVO.getNumericCode();
			String product = null;
			try {
				product = findSystemParameterValue(MailConstantsVO.MAIL_AWB_PRODUCT);
			} catch (SystemException e) {
				e.printStackTrace();
			}
			Collection<ProductValidationVO> productVOs = null;
			ProductVO productVO = new ProductVO();
			ProductValidationVO productValidationVO = null;

			productVOs = neoMastersServiceUtils.findProductsByName(companyCode, product);

			if (productVOs != null && !productVOs.isEmpty()) {

				productValidationVO = productVOs.iterator().next();

				productVO = neoMastersServiceUtils.findProductDetails(companyCode,
						productValidationVO.getProductCode());
			}
			Collection<CustomerLovVO> customerLovVOS = null;
			CustomerFilterVO customerFilterVO = new CustomerFilterVO();
			customerFilterVO.setCompanyCode(companyCode);
			customerFilterVO.setCustomerCode(agentCode);
			customerFilterVO.setPageNumber(1);
			customerLovVOS = neoMastersServiceUtils.findCustomers(customerFilterVO);
			if (customerLovVOS != null && !customerLovVOS.isEmpty()) {
				agentName = customerLovVOS.iterator().next().getCustomerName();
				iataCode = customerLovVOS.iterator().next().getIataCode();
			}
			if (documentValidationVO != null && !checkAWBAttach) {

				try {
					shipmentDetailVO = createShipmentDetailVO(documentFilterVO);
				} catch (SystemException e) {
					e.printStackTrace();
				}
				try {
					populateShipmentShipperConsignee(shipmentDetailVO, shipper, consignee, mailbagVOs);
				} catch (SystemException e) {
					e.printStackTrace();
				}
				try {
					populateShipmentRoutingDetails(shipmentDetailVO, mailbagVOs, containerDetailsVOs.iterator().next(),mailManifestDetailsVO);
				} catch (SystemException e) {
					e.printStackTrace();
				}
				shipmentDetailVO.setAgentCode(documentFilterVO.getStockOwner());
				shipmentDetailVO.setAgentName(agentName);
				shipmentDetailVO.setIataCode(iataCode);
				shipmentDetailVO.setShipmentPrefix(shipmentPrefix);
				shipmentDetailVO.setOwnerCode(operationalFlightVO.getCarrierCode());
				try {
					collectPieceWieghtdetails(mailbagVOs, shipmentDetailVO);
				} catch (SystemException e) {
					e.printStackTrace();
				}
				shipmentDetailVO.setServiceCargoClass(AutoAttachAWBDetailsFeatureConstants.MAIL_SERVICE_CARGO_CLASS);
				shipmentDetailVO.setDocumentSubType(productVO.getDocumentSubType());
				shipmentDetailVO.setScc(AutoAttachAWBDetailsFeatureConstants.MAIL_AWB_SOURCE);
				shipmentDetailVO.setProduct(product);
				shipmentDetailVO.setParameterWarningToBeDiscarded(true);
				shipmentDetailVO.setOrigin(mailbagVOs.iterator().next().getOrigin());
				shipmentDetailVO.setDestination(mailbagVOs.iterator().next().getDestination());
				//TODO: Neo to fix below
				if (containerDetailsVOs.stream().anyMatch(container -> MailConstantsVO.MAILOUTBOUND_SCREEN.equals(container.getFromScreen()))){
					mailController.validateAndPopulateShipmentCustomsInformationVOsForACS(shipmentDetailVO);
				}
				mailManifestDetailsVO.setShipmentDetailVO(shipmentDetailVO);
				shipmentValidationVO = operationsShipmentUtil.saveShipmentDetails(shipmentDetailVO);
				mailManifestDetailsVO.setShipmentValidationVO(shipmentValidationVO);

			}

		}
		
	}

	private ShipmentDetailVO createShipmentDetailVO(DocumentFilterVO documentFilterVO) throws SystemException {
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode(documentFilterVO.getCompanyCode());
		shipmentDetailVO.setOwnerId(documentFilterVO.getAirlineIdentifier());
		shipmentDetailVO.setMasterDocumentNumber(documentFilterVO.getDocumentNumber());
		shipmentDetailVO.setOperationFlag(ShipmentDetailVO.OPERATION_FLAG_INSERT);
		shipmentDetailVO.setStatedWeightCode(MailConstantsVO.WEIGHT_CODE);
		shipmentDetailVO.setDateOfJourney(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		shipmentDetailVO.setScc(findSystemParameterValue(MailConstantsVO.ATTACH_AWB_SCC_CODE));
		shipmentDetailVO.setSourceIndicator(MailConstantsVO.MAIL_AWB_SOURCE);
		shipmentDetailVO.setOverrideCertificateValidations("N");
		shipmentDetailVO.setSciValidationToBeSkipped(true);
		return shipmentDetailVO;
	}

	private String findSystemParameterValue(String syspar) throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils
						.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			return sysparValue;
		}
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	private void collectPieceWieghtdetails(Collection<MailbagVO> mailbagVOs, ShipmentDetailVO shipmentDetailVO)
			throws SystemException {
		int statedPieces = 0;
		double statedWieght = 0.0D;
		double statedVolume = 0.0D;
		double density = 1.0D;
		String commodityCode = null;
		String companyCode = null;
		Collection<String> systemParameters = new ArrayList<>();

		Collection<RatingDetailVO> ratingDetails = new ArrayList<>(); // Added
																		// by
																		// A-8164
																		// for
																		// ICRD-259778
		RatingDetailVO ratingDetailVO = new RatingDetailVO();

		systemParameters.add(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER);
		Map<String, String> systemParamterMap = findSystemParameterValuesForBooking(systemParameters);

		if (systemParamterMap != null && systemParamterMap.size() > 0) {
			commodityCode = systemParamterMap.get(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER);
		}
		companyCode = shipmentDetailVO.getCompanyCode();
		CommodityValidationVO commodityValidationVO = validateCommodity(companyCode, commodityCode);
		statedPieces = mailbagVOs.size();
		for (MailbagVO mailbagVO : mailbagVOs) {
			//TODO: Neo to correct unit
			if (mailbagVO.getWeight() != null) {
				statedWieght = statedWieght + mailbagVO.getWeight().getValue().doubleValue();// Modified
																						// for
																						// IASCB-47326
			}
			if (mailbagVO.getVolume() != null) {
				statedVolume = statedVolume + mailbagVO.getVolume().getValue().doubleValue();
			}

		}
		
		if(statedVolume<=0.0D ){
		double vol;
		if (commodityValidationVO != null) {
			density = commodityValidationVO.getDensityFactor();
		}
		vol = statedWieght / density;
		if (MailConstantsVO.MINIMUM_VOLUME > vol) {
			statedVolume = MailConstantsVO.MINIMUM_VOLUME;
		} else {
			statedVolume = vol;
		}
		}
		ratingDetailVO.setOperationFlag(RatingDetailVO.OPERATION_FLAG_UPDATE); // Added
		ratingDetailVO.setCompanyCode(companyCode);	  																// by
																				// A-8164
																				// for
																				// ICRD-265352
		ratingDetailVO.setRateLineSerialNumber(1);
		//TODO: Neo to correct unit
		Measure statedWeightInMeasure = measureMapper.toMeasure(quantities
				.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(statedWieght)));
		Measure statedVolumeInMeasure = measureMapper.toMeasure(quantities
				.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(statedVolume)));
		ratingDetailVO.setGrossWeight(statedWeightInMeasure);

		// Added by A-8164 for ICRD-259778
		ratingDetailVO.setGrossVolume(statedVolumeInMeasure);
		if (commodityValidationVO != null) {
			ratingDetailVO.setCommodityName(commodityValidationVO.getCommodityCode());
			ratingDetailVO.setDescription(commodityValidationVO.getCommodityDesc());
		}
		ratingDetailVO.setPieces(statedPieces);
		ratingDetails.add(ratingDetailVO);
		shipmentDetailVO.setRatingDetails(ratingDetails);
		shipmentDetailVO.setStatedPieces(statedPieces);

		shipmentDetailVO.setStatedWeight(statedWeightInMeasure);
		shipmentDetailVO.setStatedVolume(statedVolumeInMeasure);
		shipmentDetailVO.setTotalVolume(statedVolumeInMeasure);

		shipmentDetailVO.setTotalAcceptedPieces(statedPieces);
		shipmentDetailVO.setTotalAcceptedWeight(statedWeightInMeasure);
		shipmentDetailVO.setGrossStatedVolume(statedVolumeInMeasure);
		shipmentDetailVO.setGrossDisplayedVolume(statedVolumeInMeasure);
		if (commodityValidationVO != null) {
			shipmentDetailVO.setShipmentDescription(commodityValidationVO.getCommodityDesc());
		}

	}

	private void populateShipmentShipperConsignee(ShipmentDetailVO shipmentDetailVO, String shipper, String consignee,
			Collection<MailbagVO> mailbagVOs) throws SystemException {
		Collection<CustomerVO> shipperCustomerDetails = null;
		Collection<CustomerVO> consigneeCustomerDetails = null;
		Collection<String> customerPreferences = new ArrayList<>();
		customerPreferences.add(MailConstantsVO.CUSTOMS_POSTAL_AUTHORITY_CODE_PRFCOD);
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(shipmentDetailVO.getCompanyCode());
		customerFilterVO.setCustomerType(MailConstantsVO.CUSTOMER_TYPE_GPA);
		customerFilterVO.setStationCode(mailbagVOs.iterator().next().getOrigin());
		customerFilterVO.setPageNumber(1);
		customerFilterVO.setCustomerPreferenceCodes(customerPreferences);
		//TODO : Neo to fix below
			shipperCustomerDetails = neoMastersServiceUtils.getAllCustomerDetails(customerFilterVO);
		customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(shipmentDetailVO.getCompanyCode());
		customerFilterVO.setCustomerType(MailConstantsVO.CUSTOMER_TYPE_GPA);
		customerFilterVO.setStationCode(mailbagVOs.iterator().next().getDestination());
		customerFilterVO.setPageNumber(1);
		customerFilterVO.setCustomerPreferenceCodes(customerPreferences);
		//TODO: Neo to fix below
			consigneeCustomerDetails = neoMastersServiceUtils.getAllCustomerDetails(customerFilterVO);

		Collection<CustomerVO> shipperDetailsForCustoms = getCustomerForCustoms(shipper, shipperCustomerDetails);
		if (!shipperDetailsForCustoms.isEmpty()) {
			shipmentDetailVO.setShipperCode(shipperDetailsForCustoms.iterator().next().getCustomerCode());
			shipmentDetailVO.setShipperName(shipperDetailsForCustoms.iterator().next().getCustomerName());
			shipmentDetailVO.setShipperAddress1(shipperDetailsForCustoms.iterator().next().getAddress1());
			shipmentDetailVO.setShipperCity(shipperDetailsForCustoms.iterator().next().getCity());
			shipmentDetailVO.setShipperCountry(shipperDetailsForCustoms.iterator().next().getCountry());
			shipmentDetailVO.setShipperAccountNumber(shipperDetailsForCustoms.iterator().next().getAccountNumber());
			shipmentDetailVO.setConsigneeTelephoneNumber(shipperDetailsForCustoms.iterator().next().getTelephone());
			shipmentDetailVO.setShipperEmailId(shipperDetailsForCustoms.iterator().next().getEmail());
			shipmentDetailVO.setShipperState(shipperDetailsForCustoms.iterator().next().getState());
			shipmentDetailVO.setShipperPostalCode(shipperDetailsForCustoms.iterator().next().getZipCode());
			shipmentDetailVO.setOrigin(mailbagVOs.iterator().next().getMailOrigin());
			shipmentDetailVO.setDestination(mailbagVOs.iterator().next().getMailDestination());
		}
		Collection<CustomerVO> consigneeDetailsForCustoms = getCustomerForCustoms(consignee, consigneeCustomerDetails);
		if (!consigneeDetailsForCustoms.isEmpty()) {
			shipmentDetailVO.setConsigneeCode(consigneeDetailsForCustoms.iterator().next().getCustomerCode());
			shipmentDetailVO.setConsigneeName(consigneeDetailsForCustoms.iterator().next().getCustomerName());
			shipmentDetailVO.setConsigneeAddress1(consigneeDetailsForCustoms.iterator().next().getAddress1());
			shipmentDetailVO.setConsigneeCity(consigneeDetailsForCustoms.iterator().next().getCity());
			shipmentDetailVO.setConsigneeCountry(consigneeDetailsForCustoms.iterator().next().getCountry());
			shipmentDetailVO.setConsigneeAccountNumber(consigneeDetailsForCustoms.iterator().next().getAccountNumber());
			shipmentDetailVO.setConsigneeTelephoneNumber(consigneeDetailsForCustoms.iterator().next().getTelephone());
			shipmentDetailVO.setConsigneeEmailId(consigneeDetailsForCustoms.iterator().next().getEmail());
			shipmentDetailVO.setConsigneeState(consigneeDetailsForCustoms.iterator().next().getState());
			shipmentDetailVO.setConsigneePostalCode(consigneeDetailsForCustoms.iterator().next().getZipCode());
		}
		shipmentDetailVO.setAWBDataCaptureDone(true);
	}

	private Collection<CustomerVO> getCustomerForCustoms(String paCode, Collection<CustomerVO> customerDetails) {
		Collection<CustomerVO> customerForCustoms = new ArrayList<>();
		if (customerDetails != null && !customerDetails.isEmpty()) {
			for (CustomerVO customer : customerDetails) {
				if (customer.getCustomerPreferences() != null && !customer.getCustomerPreferences().isEmpty()) {
					for (CustomerPreferenceVO customerPreferenceVO : customer.getCustomerPreferences()) {
						customerForCustoms = populatecustomerforcustomCOllection(customerPreferenceVO,
								customerForCustoms, customer, paCode);
					}
				}
			}
		}
		return customerForCustoms;
	}

	private Collection<CustomerVO> populatecustomerforcustomCOllection(CustomerPreferenceVO customerPreferenceVO,
			Collection<CustomerVO> customerForCustoms, CustomerVO customer, String paCode) {
		if (isNotNullAndEmpty(paCode) && customerPreferenceVO.getPreferenceValue().contains(paCode)) {
			if (customerForCustoms.isEmpty()) {
				customerForCustoms = new ArrayList<>();
			}
			customerForCustoms.add(customer);
			return customerForCustoms;
		}
		return customerForCustoms;
	}

	private static boolean isNotNullAndEmpty(String s) {
		return s != null && !"".equals(s.trim());
	}

	private void populateShipmentRoutingDetails(ShipmentDetailVO shipmentDetailVO, Collection<MailbagVO> mailbagVOs,
			ContainerDetailsVO containerDetailsVO,MailManifestDetailsVO mailManifestDetailsVO) throws SystemException {
		Collection<RoutingVO> routingVOs = new ArrayList<>();
		for (MailbagVO mailbagVO : mailbagVOs) {
			if (isNotNullAndEmpty(mailbagVO.getConsignmentNumber()) && isNotNullAndEmpty(mailbagVO.getPaCode())) {

				populateRoutingInfofromConsignmentinfo(mailbagVO, routingVOs, shipmentDetailVO);

			} else {
				/**
				 * If consignment info not present then use the current flight
				 * route If flight destination is different from mailbag
				 * destination then additional routing info needs to be added
				 * till mailbag destination, to complete routing
				 */
				Collection<FlightValidationVO> flightValidationVOs = null;
				FlightFilterVO flightFilterVO = constructFlightFilterVO(containerDetailsVO);
					flightValidationVOs = flightOperationsProxy
							.validateFlightForAirport(flightFilterVO);
				if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
					populateRoutinginfofromflightroute(flightValidationVOs, routingVOs, mailbagVO, shipmentDetailVO);
					mailManifestDetailsVO.setFlightValidationVO(flightValidationVOs.iterator().next());
									}
			}
			
			break;
		}
	}

	private ShipmentDetailVO populateRoutinginfofromflightroute(Collection<FlightValidationVO> flightValidationVOs,
			Collection<RoutingVO> routingVOs, MailbagVO mailbagVO, ShipmentDetailVO shipmentDetailVO)
			throws SystemException {
		int sequenceNumber = 1;
		String flightLegDestination = flightValidationVOs.iterator().next().getLegDestination();
		RoutingVO routingVO = new RoutingVO();
		routingVO.setCompanyCode(flightValidationVOs.iterator().next().getCompanyCode());
		routingVO.setCarrierCode(flightValidationVOs.iterator().next().getCarrierCode());
		routingVO.setCarrierId(flightValidationVOs.iterator().next().getFlightCarrierId());
		routingVO.setOrigin(flightValidationVOs.iterator().next().getLegOrigin());
		routingVO.setDestination(flightLegDestination);
		routingVO.setAirportCode(flightLegDestination);
		routingVO.setFlightNumber(flightValidationVOs.iterator().next().getFlightNumber());
		routingVO.setFlightDate(flightValidationVOs.iterator().next().getFlightDate());
		routingVO.setRoutingSequenceNumber(sequenceNumber);
		routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
		routingVOs.add(routingVO);
		sequenceNumber++;
		shipmentDetailVO.setRoutingDetails(routingVOs);
		if (isNotNullAndEmpty(mailbagVO.getDestination()) && !mailbagVO.getDestination().equals(flightLegDestination)) {
			LoginProfile logonAttributes = contextUtil.callerLoginProfile();
			routingVO = new RoutingVO();
			routingVO.setCompanyCode(logonAttributes.getCompanyCode());
			routingVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
			routingVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
			routingVO.setOrigin(flightLegDestination);
			routingVO.setDestination(mailbagVO.getDestination());
			routingVO.setAirportCode(mailbagVO.getDestination());
			routingVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
			routingVO.setRoutingSequenceNumber(sequenceNumber);
			routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
			shipmentDetailVO.getRoutingDetails().add(routingVO);

		}
		return shipmentDetailVO;
	}

	private ShipmentDetailVO populateRoutingInfofromConsignmentinfo(MailbagVO mailbagVO,
			Collection<RoutingVO> routingVOs, ShipmentDetailVO shipmentDetailVO) throws SystemException {
		Collection<ConsignmentRoutingVO> consignmentRoutingVOs = null;
		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		carditEnquiryFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		carditEnquiryFilterVO.setConsignmentDocument(mailbagVO.getConsignmentNumber());
		carditEnquiryFilterVO.setPaoCode(mailbagVO.getPaCode());
		//TODO: Neo to fix below
		try {
			consignmentRoutingVOs = constructDAO().findConsignmentRoutingDetails(carditEnquiryFilterVO);
		} catch (SystemException | PersistenceException exp) {
			throw new SystemException(exp.getMessage());
		}

		if (consignmentRoutingVOs != null && !consignmentRoutingVOs.isEmpty()) {
			String consignmentDestination = null;
			int sequenceNumber = 1;
			for (ConsignmentRoutingVO consignmentRoutingVO : consignmentRoutingVOs) {
				RoutingVO routingVO = new RoutingVO();
				routingVO.setCompanyCode(consignmentRoutingVO.getCompanyCode());
				routingVO.setCarrierCode(consignmentRoutingVO.getFlightCarrierCode());
				routingVO.setCarrierId(consignmentRoutingVO.getFlightCarrierId());
				routingVO.setOrigin(consignmentRoutingVO.getPol());
				routingVO.setDestination(consignmentRoutingVO.getPou());
				routingVO.setAirportCode(consignmentRoutingVO.getPou());
				routingVO.setFlightNumber(consignmentRoutingVO.getFlightNumber());
				routingVO.setFlightDate(LocalDateMapper.toLocalDate(consignmentRoutingVO.getFlightDate()));
				routingVO.setRoutingSequenceNumber(sequenceNumber);
				routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
				routingVOs.add(routingVO);
				sequenceNumber++;
				consignmentDestination = consignmentRoutingVO.getPou();
			}
			shipmentDetailVO.setRoutingDetails(routingVOs);
			shipmentDetailVO.setHandlingInfo(mailbagVO.getConsignmentNumber() + SPACE + mailbagVO.getPaCode());
			/**
			 * If consignment destination is not the mailbag destination then
			 * additional routing info needs to be added till mailbag
			 * destination, to complete routing
			 */
			if (isNotNullAndEmpty(mailbagVO.getDestination())
					&& !mailbagVO.getDestination().equals(consignmentDestination)) {
				LoginProfile logonAttributes = contextUtil.callerLoginProfile();
				RoutingVO routingVO = new RoutingVO();
				routingVO.setCompanyCode(logonAttributes.getCompanyCode());
				routingVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
				routingVO.setCarrierId(logonAttributes.getOwnAirlineIdentifier());
				routingVO.setOrigin(consignmentDestination);
				routingVO.setDestination(mailbagVO.getDestination());
				routingVO.setAirportCode(mailbagVO.getDestination());
				routingVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
				routingVO.setRoutingSequenceNumber(sequenceNumber);
				routingVO.setOperationFlag(OPERATION_FLAG_INSERT);
				shipmentDetailVO.getRoutingDetails().add(routingVO);
			}
		}
		return shipmentDetailVO;

	}

	private FlightFilterVO constructFlightFilterVO(ContainerDetailsVO containerDetailsVO) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(containerDetailsVO.getCarrierId());
		flightFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		flightFilterVO.setStation(containerDetailsVO.getPol());
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		return flightFilterVO;
	}

	private static MailOperationsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	private Map<String, String> findSystemParameterValuesForBooking(Collection<String> systemParameterCodes)
			throws SystemException {
		try {
			return neoMastersServiceUtils.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessException e) {
			return new HashMap<>();
		}
	}

	public CommodityValidationVO validateCommodity(String companyCode, String commodityCode) throws SystemException {
		CommodityValidationVO commodityValidationVo = null;
		Collection<String> commodityColl = new ArrayList<>();
		Map<String, CommodityValidationVO> commodityMap = null;
		/*
		 * Since for all the Mails the commodity Code Is MAL OR a value which
		 * will be always unique..
		 */
		commodityColl.add(commodityCode);

		try {
			commodityMap = neoMastersServiceUtils.validateCommodityCodes(companyCode, commodityColl);
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		if (commodityMap != null && commodityMap.size() > 0) {
			commodityValidationVo = commodityMap.get(commodityCode);
		}
		return commodityValidationVo;
	}
}
