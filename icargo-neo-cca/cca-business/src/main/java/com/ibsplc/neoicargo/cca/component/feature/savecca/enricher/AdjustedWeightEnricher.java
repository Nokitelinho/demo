package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.util.CcaParameterUtil;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;
import static com.ibsplc.neoicargo.masters.ParameterType.SYSTEM_PARAMETER;

@Slf4j
@Component
@AllArgsConstructor
public class AdjustedWeightEnricher extends Enricher<CCAMasterVO> {

    private static final String SYS_PAR_SCC_ADJ_WGT = "tariff.freight.sccschargedonadjustedweight";

    private final CcaParameterUtil awbParameterUtil;
    private final QuantityMapper quantityMapper;

    /*
     * This method first takes a collection of SCC codes - handling code of ShipmentDetailVO
     * Then takes the SCC Codes from System Parameter and then uses Collection.disjoint which returns
     * true if the two specified collections have no elements in common.If there contains a common element
     * then we set the adjusted weight as chargeable weight
     * **/
    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("adjustedWeightEnricher Invoked");
        final var sccFromSystemParameter =
                Optional.ofNullable(awbParameterUtil.getSystemParameter(SYS_PAR_SCC_ADJ_WGT, SYSTEM_PARAMETER))
                .map(this::parse)
                .orElse(new HashSet<>());
        final var revisedShipmentDetailVO = ccaMasterVO.getRevisedShipmentVO();
        final var sccCodes = Optional.ofNullable(revisedShipmentDetailVO.getHandlingCode())
                .map(handlingCode -> parse(revisedShipmentDetailVO.getHandlingCode()))
                .orElse(new HashSet<>());
        if (!Collections.disjoint(sccFromSystemParameter, sccCodes)) {
            log.info("SCC of AWB equals to System parameter");
            final var ratingDetails = revisedShipmentDetailVO.getRatingDetails();
            if (!CcaUtil.isNullOrEmpty(ratingDetails)) {
                for (var ccaRatingDetail : ratingDetails) {
                    ccaRatingDetail.setChargeableWeight(ccaRatingDetail.getAdjustedWeight());
                    if (isGreaterThan(ccaRatingDetail.getWeightOfShipment(), ccaRatingDetail.getChargeableWeight())) {
                        throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_017.getErrorCode(),
                                CcaErrors.NEO_CCA_017.getErrorMessage(), ErrorType.ERROR));
                    }
                }
                updateTotals(revisedShipmentDetailVO, ratingDetails, ccaMasterVO.getUnitOfMeasure());
            }
        }
    }

    private Set<String> parse(String handlingCode) {
        return Arrays.stream(handlingCode.split(",")).collect(Collectors.toSet());
    }

    private void updateTotals(CcaAwbVO ccaAwbVO, Collection<CcaRatingDetailVO> detailVOList, Units units) {
        final var totalAdjustedWeight = detailVOList.stream()
                .map(CcaRatingDetailVO::getAdjustedWeight)
                .map(Quantity::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final var totalChargeableWeight = detailVOList.stream()
                .map(CcaRatingDetailVO::getChargeableWeight)
                .map(Quantity::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        ccaAwbVO.setAdjustedWeight(quantityMapper.getQuantityWeight(totalAdjustedWeight, units));
        ccaAwbVO.setChargeableWeight(quantityMapper.getQuantityWeight(totalChargeableWeight, units));
    }

    private boolean isGreaterThan(Quantity<Weight> first, Quantity<Weight> second) {
        final var firstWeightDouble = Objects.isNull(first) ? 0.0
                : first.getValue().doubleValue();
        final var secondWeightDouble = Objects.isNull(second) ? 0.0
                : second.getValue().doubleValue();
        return firstWeightDouble > secondWeightDouble;
    }
}
