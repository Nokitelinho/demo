package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper;

import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.PricingChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.PricingChargeHeadDetailsVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxBasisHelper {

    private final ContextUtil contextUtil;

    public Map<String, PricingChargeDetailsVO> getChargeDetailsMap(CcaAwbVO ccaAwbVO) {
        log.info("Construct charges details map for CCA Awb [{}]",
                ccaAwbVO.getShipmentPrefix() + "-" + ccaAwbVO.getMasterDocumentNumber() + "-" + ccaAwbVO.getRecordType()
        );

        var chargeDetails = new ArrayList<>(constructChargeDetails(ccaAwbVO));

        if (!isNullOrEmpty(ccaAwbVO.getAwbCharges())) {
            chargeDetails.addAll(constructChargeDetailsForAwbCharges(ccaAwbVO.getAwbCharges()));
        }

        // Adding Commission
        if (isDifferentWithCallerShipmentPrefix(ccaAwbVO.getShipmentPrefix())) {
            var commissionChargeDetail = new PricingChargeDetailsVO();
            commissionChargeDetail.setBasis(TaxFilterConstant.CONFIGURATIONTYPE_COMMISION);
            commissionChargeDetail.setBasisTotalAmount(ccaAwbVO.getCommissionAmount());
            chargeDetails.add(commissionChargeDetail);
        }

        return chargeDetails.stream()
                .collect(Collectors.toMap(PricingChargeDetailsVO::getBasis, chargeDetail -> chargeDetail));
    }

    private boolean isDifferentWithCallerShipmentPrefix(String shipmentPrefix) {
        var ownAirlineIdentifier = contextUtil.callerLoginProfile().getOwnAirlineIdentifier();
        var callerShipmentPrefix = Integer.toString(ownAirlineIdentifier).substring(1);
        return !callerShipmentPrefix.equals(shipmentPrefix);
    }

    private List<PricingChargeDetailsVO> constructChargeDetails(CcaAwbVO ccaAwbVO) {
        var iataCharge = ccaAwbVO.getWeightCharge() + ccaAwbVO.getValuationCharge();
        var iataChargeDetail = getPricingChargeDetailsVO(ccaAwbVO, iataCharge, TaxFilterConstant.BASISCODE_IATACHG);
        var marketCharge = ccaAwbVO.getNetCharge() + ccaAwbVO.getValuationCharge();
        var marketChargeDetail = getPricingChargeDetailsVO(ccaAwbVO, marketCharge, TaxFilterConstant.BASISCODE_MKTCHG);

        var discTotalAmount = iataCharge - marketCharge;
        var discChargeDetail = getPricingChargeDetailsVO(ccaAwbVO, discTotalAmount, TaxFilterConstant.BASISCODE_DISC);

        return List.of(iataChargeDetail, marketChargeDetail, discChargeDetail);
    }

    private PricingChargeDetailsVO getPricingChargeDetailsVO(CcaAwbVO ccaAwbVO, Double totalAmount, String basisCode) {
        var discChargeDetail = new PricingChargeDetailsVO();
        discChargeDetail.setBasis(basisCode);
        discChargeDetail.setBasisTotalAmount(totalAmount);
        discChargeDetail.setPaymentType(ccaAwbVO.getPayType());
        return discChargeDetail;
    }

    private List<PricingChargeDetailsVO> constructChargeDetailsForAwbCharges(Collection<CcaChargeDetailsVO> charges) {

        Map<Boolean, List<PricingChargeHeadDetailsVO>> chargesGroupedByAgentTrue = charges.stream()
                .collect(Collectors.groupingBy(
                        CcaChargeDetailsVO::isDueAgent,
                        Collectors.mapping(this::getPricingChargeHeadDetailsVO, Collectors.toList()))
                );

        var agentChargeDetail = getAgentPricingChargeDetailsVO(charges, chargesGroupedByAgentTrue);
        var carrierChargeDetail = getCarrierPricingChargeDetailsVO(charges, chargesGroupedByAgentTrue);

        return List.of(agentChargeDetail, carrierChargeDetail);
    }

    private boolean hasNoAgentCharges(Map<Boolean, List<PricingChargeHeadDetailsVO>> chargesGroupedByAgentTrue) {
        return chargesGroupedByAgentTrue.get(true) == null;
    }

    private boolean hasNoCarrierCharges(Map<Boolean, List<PricingChargeHeadDetailsVO>> chargesGroupedByCarrierFalse) {
        return chargesGroupedByCarrierFalse.get(false) == null;
    }

    private PricingChargeDetailsVO getAgentPricingChargeDetailsVO(
            Collection<CcaChargeDetailsVO> awbCharges,
            Map<Boolean, List<PricingChargeHeadDetailsVO>> chargesGroupedByAgentTrue) {
        var ocdaChargeDetail = new PricingChargeDetailsVO();
        ocdaChargeDetail.setBasis(TaxFilterConstant.BASISCODE_OCDA);
        ocdaChargeDetail.setChargeHeadDetails(chargesGroupedByAgentTrue.get(true));

        if (hasNoAgentCharges(chargesGroupedByAgentTrue)) {
            ocdaChargeDetail.setBasisTotalAmount(0.0);
        } else {
            awbCharges.stream()
                    .filter(Objects::nonNull)
                    .filter(CcaChargeDetailsVO::isDueAgent)
                    .findFirst()
                    .map(CcaChargeDetailsVO::getPaymentType)
                    .ifPresent(ocdaChargeDetail::setPaymentType);

            var ocdaTotalAmount = chargesGroupedByAgentTrue.get(true)
                    .stream()
                    .mapToDouble(PricingChargeHeadDetailsVO::getChargeHeadAmount)
                    .sum();
            ocdaChargeDetail.setBasisTotalAmount(ocdaTotalAmount);
        }
        return ocdaChargeDetail;
    }

    private PricingChargeDetailsVO getCarrierPricingChargeDetailsVO(
            Collection<CcaChargeDetailsVO> awbCharges,
            Map<Boolean, List<PricingChargeHeadDetailsVO>> chargesGroupedByAgentTrue) {
        var ocdcChargeDetail = new PricingChargeDetailsVO();
        ocdcChargeDetail.setBasis(TaxFilterConstant.BASISCODE_OCDC);
        ocdcChargeDetail.setChargeHeadDetails(chargesGroupedByAgentTrue.get(false));

        if (hasNoCarrierCharges(chargesGroupedByAgentTrue)) {
            ocdcChargeDetail.setBasisTotalAmount(0.0);
        } else {
            awbCharges.stream()
                    .filter(Objects::nonNull)
                    .filter(CcaChargeDetailsVO::isDueCarrier)
                    .findFirst()
                    .map(CcaChargeDetailsVO::getPaymentType)
                    .ifPresent(ocdcChargeDetail::setPaymentType);

            var carrierTotalAmount = chargesGroupedByAgentTrue.get(false)
                    .stream()
                    .mapToDouble(PricingChargeHeadDetailsVO::getChargeHeadAmount)
                    .sum();
            ocdcChargeDetail.setBasisTotalAmount(carrierTotalAmount);
        }
        return ocdcChargeDetail;
    }

    private PricingChargeHeadDetailsVO getPricingChargeHeadDetailsVO(CcaChargeDetailsVO awbCharge) {
        var chargeHeadDetailsVO = new PricingChargeHeadDetailsVO();
        chargeHeadDetailsVO.setChargeHeadCode(awbCharge.getChargeHeadCode());
        chargeHeadDetailsVO.setChargeHeadAmount(CcaUtil.getRoundedMoneyAmount(awbCharge::getCharge));
        return chargeHeadDetailsVO;
    }

}
