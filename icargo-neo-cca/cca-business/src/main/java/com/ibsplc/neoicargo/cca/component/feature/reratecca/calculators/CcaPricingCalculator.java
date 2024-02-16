package com.ibsplc.neoicargo.cca.component.feature.reratecca.calculators;

import com.ibsplc.neoicargo.booking.BookingWebAPI;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.cca.vo.CcaRoutingVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import com.ibsplc.neoicargo.pricing.PricingWebAPI;
import com.ibsplc.neoicargo.pricing.modal.CalculateAWBChargesRequest;
import com.ibsplc.neoicargo.pricing.modal.CalculateAWBChargesResponse;
import com.ibsplc.neoicargo.pricing.modal.EvaluationParameter;
import com.ibsplc.neoicargo.pricing.modal.Flight;
import com.ibsplc.neoicargo.pricing.utils.PricingConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.DUE_AGENT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.DUE_CARRIER;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.NEO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_TYPE_RECOMMENDED_IATA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_TYPE_RECOMMENDED_MKT;

@Component
@RegisterJAXRSClient(clazz = BookingWebAPI.class, targetService = "neo-booking-business")
@RegisterJAXRSClient(clazz = PricingWebAPI.class, targetService = "pricing-service")
@AllArgsConstructor
@Slf4j
public class CcaPricingCalculator {

    private static final String SERVICE_CODE = "AWBEXC";

    private final CcaAwbDetailMapper ccaAwbDetailMapper;
    private final PricingWebAPI pricingWebAPI;
    private final PricingConverter pricingConverter;
    private final ContextUtil contextUtil;
    private final BookingWebAPI bookingWebAPI;

    public Collection<CalculateAWBChargesResponse> findAwbRatesFromPricing(
            CcaAwbVO shipmentDetailVO) throws BusinessException {
        final var bookingRoute = getCcaRoutingVOS(shipmentDetailVO);
        final var evaluationParameters = constructEvaluationParameters(shipmentDetailVO, bookingRoute);
        final var pricingRequest = constructRequestForFreight(shipmentDetailVO, bookingRoute, evaluationParameters);
        pricingRequest.add(constructRequestForOtherCharge(shipmentDetailVO, evaluationParameters, pricingRequest));
        return getCalculateAWBChargesResponses(shipmentDetailVO, pricingRequest);
    }

    public Collection<CalculateAWBChargesResponse> findFreightRatesFromPricing(
            CcaAwbVO shipmentDetailVO) throws BusinessException {
        final var bookingRoute = getCcaRoutingVOS(shipmentDetailVO);
        final var evaluationParameters = constructEvaluationParameters(shipmentDetailVO, bookingRoute);
        final var pricingRequest = constructRequestForFreight(shipmentDetailVO, bookingRoute, evaluationParameters);
        return getCalculateAWBChargesResponses(shipmentDetailVO, pricingRequest);
    }

    public Collection<CalculateAWBChargesResponse> findOtherChargeFromPricing(
            CcaAwbVO shipmentDetailVO) throws BusinessException {
        final var bookingRoute = getCcaRoutingVOS(shipmentDetailVO);
        final var evaluationParameters = constructEvaluationParameters(shipmentDetailVO, bookingRoute);
        final var pricingRequest = constructRequestForFreight(shipmentDetailVO, bookingRoute, evaluationParameters);
        final var otherChargeRequest =
                constructRequestForOtherCharge(shipmentDetailVO, evaluationParameters, pricingRequest);

        if (shipmentDetailVO.getAwbRates() != null) {
            processAwbRates(shipmentDetailVO, otherChargeRequest);
        }
        pricingRequest.add(otherChargeRequest);
        constructEvaluationParametersForOtherCharge(shipmentDetailVO, evaluationParameters);
        return getCalculateAWBChargesResponses(shipmentDetailVO, pricingRequest);
    }

    private void constructEvaluationParametersForOtherCharge(CcaAwbVO shipmentDetailVO,
                                                             List<EvaluationParameter> evaluationParameters) {
        final var evaluationParameterForCommodity = new ArrayList<EvaluationParameter>();
        final var ratingDetails = shipmentDetailVO.getRatingDetails();
        if (ratingDetails != null) {
            for (final var ratingDetailVO : ratingDetails) {
                evaluationParameterForCommodity.addAll(pricingConverter.getEvaluationParameters(
                        CcaRatingDetailVO.class, ratingDetailVO, shipmentDetailVO.getShippingDate()));
            }
            if (!evaluationParameterForCommodity.isEmpty()) {
                addEvaluationParameter("COM", evaluationParameters, evaluationParameterForCommodity);
                addEvaluationParameter("COMGRP", evaluationParameters, evaluationParameterForCommodity);
            }
        }
    }

    private void findAllInAttributes(CcaRateDetailsVO rates, Set<String> allinAttributes) {
        if ("ALL".equalsIgnoreCase(rates.getAllInAttribute())) {
            allinAttributes.add(DUE_AGENT);
            allinAttributes.add(DUE_CARRIER);
        } else {
            allinAttributes.add(rates.getAllInAttribute());
        }
    }

    private CalculateAWBChargesRequest constructRequestForOtherCharge(CcaAwbVO shipmentDetailVO,
                                                                      List<EvaluationParameter> evaluationParameters,
                                                                      List<CalculateAWBChargesRequest> pricingRequest) {
        final var otherChargeRequest = new CalculateAWBChargesRequest();
        otherChargeRequest.setServiceCode(SERVICE_CODE);
        otherChargeRequest.setPieces(shipmentDetailVO.getPieces());
        otherChargeRequest.setChargeableWeight(shipmentDetailVO.getChargeableWeight() != null
                ? shipmentDetailVO.getChargeableWeight().getDisplayValue() : BigDecimal.ZERO);
        otherChargeRequest.setRatingModes(Collections.singletonList("OTH"));
        otherChargeRequest.setId("OTH");
        otherChargeRequest.setParentId(pricingRequest.stream()
                .map(CalculateAWBChargesRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        constructEvaluationParametersForOtherCharge(shipmentDetailVO, evaluationParameters);
        otherChargeRequest.setEvalParameters(evaluationParameters);
        otherChargeRequest.setUnits(getUnits(shipmentDetailVO));
        return otherChargeRequest;
    }

    private List<CalculateAWBChargesRequest> constructRequestForFreight(CcaAwbVO shipmentDetailVO,
                                                                        List<CcaRoutingVO> bookingRoute,
                                                                        List<EvaluationParameter> evaluationParams) {
        final var calculateAWBChargesRequest = new ArrayList<CalculateAWBChargesRequest>();
        if (shipmentDetailVO.getRatingDetails() != null) {
            final var flights = ccaAwbDetailMapper.fromRoutingDetailsToFlight(bookingRoute);
            for (final var ratingDetailVO : shipmentDetailVO.getRatingDetails()) {
                final var evaluationParameterForCommodity = new ArrayList<>(pricingConverter.getEvaluationParameters(
                        CcaRatingDetailVO.class, ratingDetailVO, shipmentDetailVO.getShippingDate()));
                final var evaluationParameterForRequest = new ArrayList<EvaluationParameter>();
                evaluationParameterForRequest.addAll(evaluationParameterForCommodity);
                evaluationParameterForRequest.addAll(evaluationParams);
                final var awbChargesRequest = getCalculateAWBChargesRequest(
                        shipmentDetailVO, flights, ratingDetailVO, evaluationParameterForRequest);
                calculateAWBChargesRequest.add(awbChargesRequest);
            }
        }
        return calculateAWBChargesRequest;
    }

    private List<EvaluationParameter> constructEvaluationParameters(CcaAwbVO shipmentDetailVO,
                                                                    List<CcaRoutingVO> bookingRoute) {
        final var routes = new HashSet<String>();
        final var flightNumbers = new HashSet<String>();
        final var carrierCode = new HashSet<String>();
        final var ratedFlightType = new HashSet<String>();

        var stationCode = shipmentDetailVO.getAgentStation();
        if (CcaUtil.isNullOrEmpty(stationCode)) {
            stationCode = contextUtil.callerLoginProfile().getStationCode();
        }
        shipmentDetailVO.setStationCode(stationCode);
        shipmentDetailVO.setDayOfWeek(DayOfWeek.from(shipmentDetailVO.getShippingDate()).getValue());

        final var evaluationParameter = new ArrayList<>(pricingConverter.getEvaluationParameters(
                CcaAwbVO.class, shipmentDetailVO, shipmentDetailVO.getShippingDate()));

        if (bookingRoute != null && !bookingRoute.isEmpty()) {
            bookingRoute.forEach(bkgRoute -> {
                if (bkgRoute.getFlightCarrierCode() != null && bkgRoute.getFlightNumber() != null) {
                    flightNumbers.add(String.join("-", bkgRoute.getFlightCarrierCode(), bkgRoute.getFlightNumber()));
                    carrierCode.add(bkgRoute.getFlightCarrierCode());
                }
                ratedFlightType.add(bkgRoute.getFlightType());
                routes.add(String.join("-", bkgRoute.getSegOrgCod(), bkgRoute.getSegDstCod()));
            });
            enrichEvaluationParameterList(routes, flightNumbers, carrierCode, ratedFlightType, evaluationParameter);
        }
        return evaluationParameter;
    }

    private void enrichEvaluationParameterList(Set<String> routes, Set<String> flightNumbers,
                                               Set<String> carrierCode, Set<String> ratedFlightType,
                                               List<EvaluationParameter> evaluationParameter) {
        final var parameterMap = new HashMap<String, Set<String>>();
        parameterMap.put("CARCOD", carrierCode);
        parameterMap.put("FLT", flightNumbers);
        parameterMap.put("RTDFLTTYP", ratedFlightType);
        parameterMap.put("ROUTE", routes);
        for (final var map : parameterMap.entrySet()) {
            evaluationParameter.add(constructEvaluationParameter(map.getKey(), map.getValue()));
        }
    }

    private EvaluationParameter constructEvaluationParameter(String key, Set<String> value) {
        final var evaluationParameter = new EvaluationParameter();
        evaluationParameter.setParameterName(key);
        evaluationParameter.setParameterValue(new ArrayList<>(value));
        return evaluationParameter;
    }

    private List<CcaRoutingVO> getCcaRoutingVOS(CcaAwbVO shipmentDetailVO) {
        final var bookingData = bookingWebAPI.findBooking(shipmentDetailVO.getShipmentPrefix()
                .concat("-").concat(shipmentDetailVO.getMasterDocumentNumber()));
        if (bookingData == null) {
            return new ArrayList<>();
        }
        return ccaAwbDetailMapper.constructBookingRoutes(bookingData.getFlights());
    }

    private Collection<CalculateAWBChargesResponse> getCalculateAWBChargesResponses(
            CcaAwbVO shipmentDetailVO, List<CalculateAWBChargesRequest> pricingRequest) throws BusinessException {
        if (!NEO.equals(shipmentDetailVO.getTriggerPoint())) {
            return pricingWebAPI.calculateAWBCharges(shipmentDetailVO.getOrigin(),
                    shipmentDetailVO.getDestination(), shipmentDetailVO.getShippingDate().toString(), pricingRequest);
        }
        return Collections.emptyList();
    }

    private void processAwbRates(CcaAwbVO shipmentDetailVO, CalculateAWBChargesRequest otherChargeRequest) {
        final var allinAttributes = new HashSet<String>();
        shipmentDetailVO.getAwbRates()
                .stream()
                .filter(f -> f.getAllInAttribute() != null)
                .forEach(rates -> findAllInAttributes(rates, allinAttributes));
        otherChargeRequest.setAllInAttributes(allinAttributes);

        shipmentDetailVO.getAwbRates()
                .stream()
                .filter(rat -> Objects.equals(RATE_TYPE_RECOMMENDED_IATA, rat.getRateType())
                        || Objects.equals(RATE_TYPE_RECOMMENDED_MKT, rat.getRateType()))
                .findFirst()
                .ifPresent(ccaRateDetailsVO ->
                        otherChargeRequest.setMinimumChargeApplied(ccaRateDetailsVO.isMinimumChargeAppliedFlag()));
    }

    @NotNull
    private Units getUnits(CcaAwbVO shipmentDetailVO) {
        final var units = shipmentDetailVO.getUnitOfMeasure();
        units.setCurrencyCode(shipmentDetailVO.getCurrency());
        return units;
    }

    private void addEvaluationParameter(String key, List<EvaluationParameter> evaluationParameters,
                                        List<EvaluationParameter> evaluationParameterForCommodity) {
        final var parameterValue = evaluationParameterForCommodity.stream()
                .filter(f -> Objects.equals(f.getParameterName(), key))
                .map(EvaluationParameter::getParameterValue)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
        final var evaluationParameter = constructEvaluationParameter(key, parameterValue);
        evaluationParameters.add(evaluationParameter);
    }

    @NotNull
    private CalculateAWBChargesRequest getCalculateAWBChargesRequest(CcaAwbVO shipmentDetailVO, List<Flight> flights,
                                                                     CcaRatingDetailVO ratingDetailVO,
                                                                     List<EvaluationParameter> evaluationParams) {
        final var chargeableWeight = ratingDetailVO.getChargeableWeight() != null ?
                ratingDetailVO.getChargeableWeight().getDisplayValue() : BigDecimal.ZERO;

        final var awbChargesRequest = new CalculateAWBChargesRequest();
        awbChargesRequest.setEvalParameters(evaluationParams);
        awbChargesRequest.setPieces(ratingDetailVO.getNumberOfPieces());
        awbChargesRequest.setChargeableWeight(chargeableWeight);
        awbChargesRequest.setRatingModes(Collections.singletonList("FRT"));
        awbChargesRequest.setId(ratingDetailVO.getCommodityCode());
        awbChargesRequest.setUnits(getUnits(shipmentDetailVO));
        if (!CcaUtil.isNullOrEmpty(flights)) {
            awbChargesRequest.setFlights(new ArrayList<>(flights));
        }
        return awbChargesRequest;
    }

}
