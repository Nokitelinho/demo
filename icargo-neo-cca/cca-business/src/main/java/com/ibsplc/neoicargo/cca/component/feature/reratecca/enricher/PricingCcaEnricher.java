package com.ibsplc.neoicargo.cca.component.feature.reratecca.enricher;

import com.ibsplc.neoicargo.cca.component.feature.reratecca.calculators.CcaPricingCalculator;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.pricing.modal.CalculateAWBChargesResponse;
import com.ibsplc.neoicargo.pricing.modal.OtherChargeDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.*;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Slf4j
@Component
@AllArgsConstructor
public class PricingCcaEnricher extends Enricher<CCAMasterVO> {

    private static final String FIND_RATE_FOR_FREIGHT = "FRT";
    private static final String FIND_RATE_FOR_OTHER_CHARGE = "OTH";
    private static final String FIND_RATE_FOR_AWB = "AWB";

    private final CcaPricingCalculator pricingCalculator;
    private final CcaAwbDetailMapper ccaAwbDetailMapper;
    private final CcaAwbMapper awbMapper;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) {
        log.info("CCA PricingEnricher Invoked");

        final var shipmentDetailVO = ccaMasterVO.getRevisedShipmentVO();
        shipmentDetailVO.setUnitOfMeasure(ccaMasterVO.getUnitOfMeasure());

        if (isNullOrEmpty(shipmentDetailVO.getServiceCargoClass())) {
            var ratingParameter = FeatureContextUtilThreadArray
                    .getInstance()
                    .getFeatureContext()
                    .getContextMap()
                    .get(PRICING_PARAMETER);

            Collection<CalculateAWBChargesResponse> pricingResponse;
            switch ((String) ratingParameter) {
                case FIND_RATE_FOR_FREIGHT:
                    try {
                        pricingResponse = pricingCalculator.findFreightRatesFromPricing(shipmentDetailVO);
                        populateRatesIntoCcaAwbVO(pricingResponse, shipmentDetailVO);
                    } catch (BusinessException exception) {
                        log.error("Pricing BusinessException ", exception);
                    }
                    break;
                case FIND_RATE_FOR_OTHER_CHARGE:
                    try {
                        pricingResponse = pricingCalculator.findOtherChargeFromPricing(shipmentDetailVO);
                        populateOtherChargesIntoCcaAwbVO(shipmentDetailVO, pricingResponse);
                    } catch (BusinessException exception) {
                        log.error("Pricing BusinessException", exception);
                    }
                    break;
                case FIND_RATE_FOR_AWB:
                    try {
                        pricingResponse = pricingCalculator.findAwbRatesFromPricing(shipmentDetailVO);
                        populateRatesIntoCcaAwbVO(pricingResponse, shipmentDetailVO);
                        populateOtherChargesIntoCcaAwbVO(shipmentDetailVO, pricingResponse);
                    } catch (BusinessException exception) {
                        log.error("Pricing BusinessException ", exception);
                    }
                    break;
                default:
                    log.info("Unrecognized Rating Parameter code!");
            }
        }
    }

    private void populateRatesIntoCcaAwbVO(Collection<CalculateAWBChargesResponse> pricingResponse,
                                           CcaAwbVO shipmentDetailVO) {
        BinaryOperator<String> buildRateKey = (String commodityCode, String rateType) -> commodityCode + "_" + rateType;

        final var updatedAwbRates = new ArrayList<CcaRateDetailsVO>();
        final var oldAwbRates = shipmentDetailVO.getAwbRates();

        var ratesFromResp = isNullOrEmpty(pricingResponse)
                ? new HashMap<String, CcaRateDetailsVO>()
                : pricingResponse.stream()
                .flatMap(resp -> {
                    var rates = new LinkedList<CcaRateDetailsVO>();
                    if (resp.getIataCharge() != null) {
                        final var awbRateDetailsVO = awbMapper.constructRatesFromPricing(
                                resp.getIataCharge(), shipmentDetailVO.getUnitOfMeasure());
                        awbRateDetailsVO.setRateType(IATA_TYPE);
                        awbRateDetailsVO.setCommodityCode(resp.getId());
                        rates.add(awbRateDetailsVO);
                        final var awbRateDetailsVORec = awbMapper.constructRatesFromPricing(
                                resp.getIataCharge(), shipmentDetailVO.getUnitOfMeasure());
                        awbRateDetailsVORec.setRateType(RATE_TYPE_RECOMMENDED_IATA);
                        awbRateDetailsVORec.setCommodityCode(resp.getId());
                        rates.add(awbRateDetailsVORec);
                    }
                    if (resp.getMarketCharge() != null) {
                        final var awbRateDetailsVO = awbMapper.constructRatesFromPricing(
                                resp.getMarketCharge(), shipmentDetailVO.getUnitOfMeasure());
                        awbRateDetailsVO.setRateType(MKT_TYPE);
                        awbRateDetailsVO.setCommodityCode(resp.getId());
                        rates.add(awbRateDetailsVO);
                        final var awbRateDetailsVORec = awbMapper.constructRatesFromPricing(
                                resp.getMarketCharge(), shipmentDetailVO.getUnitOfMeasure());
                        awbRateDetailsVORec.setRateType(RATE_TYPE_RECOMMENDED_MKT);
                        awbRateDetailsVORec.setCommodityCode(resp.getId());
                        rates.add(awbRateDetailsVORec);
                    }
                    return rates.stream();
                })
                .collect(
                        Collectors.toMap(
                                rate -> buildRateKey.apply(rate.getCommodityCode(), rate.getRateType()),
                                Function.identity()
                        )
                );

        var ratesFromReq = isNullOrEmpty(oldAwbRates)
                ? new HashMap<String, CcaRateDetailsVO>()
                : oldAwbRates.stream()
                .filter(rat -> Objects.equals(MKT_TYPE, rat.getRateType())
                        || Objects.equals(IATA_TYPE, rat.getRateType())
                )
                .collect(
                        Collectors.toMap(
                                rate -> buildRateKey.apply(rate.getCommodityCode(), rate.getRateType()),
                                Function.identity()
                        )
                );

        if (ratesFromResp.isEmpty()) {
            if (!ratesFromReq.isEmpty()) {
                //resp is empty, request is not, return rates from request as is
                updatedAwbRates.addAll(ratesFromReq.values());
            }
            //else response and request are empty, leave empty rates list
        } else {
            //response is not empty
            if (!ratesFromReq.isEmpty()) {
                //process all from request
                ratesFromReq.forEach((rateKey, rate) ->
                        updatedAwbRates.add(ratesFromResp.getOrDefault(rateKey, rate))
                );
            }
            //process all unprocessed from response
            ratesFromResp.entrySet()
                    .stream()
                    .filter(keyAndRateFromResp -> !ratesFromReq.containsKey(keyAndRateFromResp.getKey()))
                    .forEach(keyAndRateFromResp ->
                            updatedAwbRates.add(keyAndRateFromResp.getValue())
                    );
        }

        shipmentDetailVO.setAwbRates(updatedAwbRates);
    }

    private void populateOtherChargesIntoCcaAwbVO(CcaAwbVO shipmentDetailVO,
                                                  Collection<CalculateAWBChargesResponse> pricingResponse) {
        final var chargeHeadsFromVO = initChargeHeadsFromVO(shipmentDetailVO);
        final var awbChargeDetailsVOs = new ArrayList<CcaChargeDetailsVO>();
        if (!isNullOrEmpty(pricingResponse)) {
            pricingResponse.forEach(response -> {
                final var responseOtherCharge = response.getOtherCharge();
                if (responseOtherCharge != null) {
                    responseOtherCharge.stream()
                            .findFirst()
                            .ifPresent(details -> {
                                if (responseOtherCharge.size() == 1 && !"EBL_TRF_003".equals(details.getErrorCode())) {
                                    updateChargeToZero(shipmentDetailVO, response, awbChargeDetailsVOs);
                                } else {
                                    responseOtherCharge.forEach(otherCharge -> {
                                        if (!chargeHeadsFromVO.isEmpty()
                                                && chargeHeadsFromVO.contains(otherCharge.getChargeHeadCode())) {
                                            updateCharge(otherCharge, shipmentDetailVO, awbChargeDetailsVOs);
                                        } else {
                                            awbChargeDetailsVOs.add(addChargeHead(otherCharge, shipmentDetailVO));
                                        }
                                    });
                                    updateChargeToZero(shipmentDetailVO, response, awbChargeDetailsVOs);
                                    shipmentDetailVO.setAwbCharges(awbChargeDetailsVOs);
                                }
                            });
                }
            });
        }
    }

    private List<String> initChargeHeadsFromVO(CcaAwbVO shipmentDetailVO) {
        final var awbCharges = shipmentDetailVO.getAwbCharges();
        if (awbCharges != null) {
            return awbCharges.stream()
                    .map(CcaChargeDetailsVO::getChargeHeadCode)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private void updateCharge(OtherChargeDetail otherCharge, CcaAwbVO shipmentDetailVO,
                              Collection<CcaChargeDetailsVO> awbChargeDetailsVOs) {
        boolean dueCarrier = false;
        boolean dueAgent = false;
        if (DUE_CARRIER.equals(otherCharge.getChargeType())) {
            dueCarrier = true;
        } else if (DUE_AGENT.equals(otherCharge.getChargeType())) {
            dueAgent = true;
        }
        if (shipmentDetailVO.getAwbCharges() != null) {
            final var existingChargeHeads = shipmentDetailVO.getAwbCharges().stream()
                    .map(CcaChargeDetailsVO::businessKey)
                    .collect(Collectors.toSet());
            for (final var awbCharge : shipmentDetailVO.getAwbCharges()) {
                if (Objects.equals(awbCharge.getChargeHeadCode(), otherCharge.getChargeHeadCode())) {
                    if (Objects.equals(awbCharge.isDueAgent(), dueAgent)
                            && Objects.equals(awbCharge.isDueCarrier(), dueCarrier)) {
                        awbCharge.setCharge(Money.of(otherCharge.getCharge(), shipmentDetailVO.getCurrency()));
                        awbCharge.setPaymentType(shipmentDetailVO.getAwbOtherChargePaymentType());
                    } else {
                        awbCharge.setCharge(Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()));
                        CcaChargeDetailsVO dueCarrierAgentChargeHead = addChargeHead(otherCharge, shipmentDetailVO);
                        if (!existingChargeHeads.contains(dueCarrierAgentChargeHead.businessKey())) {
                            awbChargeDetailsVOs.add(dueCarrierAgentChargeHead);
                        }
                    }
                    awbChargeDetailsVOs.add(awbCharge);
                }
            }
        }
    }

    private void updateChargeToZero(CcaAwbVO shipmentDetailVO, CalculateAWBChargesResponse othChg,
                                    Collection<CcaChargeDetailsVO> awbChargeDetailsVOs) {
        final var chargeHeadsFromResponse = othChg.getOtherCharge().stream()
                .map(OtherChargeDetail::getChargeHeadCode)
                .collect(Collectors.toList());
        final var awbCharges = shipmentDetailVO.getAwbCharges();
        if (awbCharges != null) {
            awbCharges.forEach(charges -> {
                if (!chargeHeadsFromResponse.contains(charges.getChargeHeadCode())) {
                    charges.setCharge(Money.of(BigDecimal.ZERO, shipmentDetailVO.getCurrency()));
                    charges.setPaymentType(shipmentDetailVO.getAwbOtherChargePaymentType());
                    awbChargeDetailsVOs.add(charges);
                }
            });
        }
    }

    private CcaChargeDetailsVO addChargeHead(OtherChargeDetail otherCharge, CcaAwbVO shipmentDetailVO) {
        final var awbChargeDetailsVO = ccaAwbDetailMapper.constructChargeDetailsFromPricing(
                otherCharge, shipmentDetailVO.getUnitOfMeasure());
        awbChargeDetailsVO.setPaymentType(shipmentDetailVO.getAwbOtherChargePaymentType());
        return awbChargeDetailsVO;
    }

}
