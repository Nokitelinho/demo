package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;

@Slf4j
@Component
@RequiredArgsConstructor
public class GrossWeightEnricher extends Enricher<CCAMasterVO> {

    private final QuantityMapper quantityMapper;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("grossWeightEnricher Invoked");
        final var revisedShipmentDetailVO = ccaMasterVO.getRevisedShipmentVO();
        final var ratingDetails = revisedShipmentDetailVO.getRatingDetails();
        if (!CcaUtil.isNullOrEmpty(ratingDetails)) {
            for (CcaRatingDetailVO ccaRatingDetailVO : ratingDetails) {
                if (!CcaUtil.isNullOrEmpty(ccaRatingDetailVO.getUlds())) {
                    ccaRatingDetailVO.setChargeableWeight(ccaRatingDetailVO.getWeightOfShipment());
                }
                if (isGreaterThan(ccaRatingDetailVO.getWeightOfShipment(), ccaRatingDetailVO.getChargeableWeight())) {
                    throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_017.getErrorCode(),
                            CcaErrors.NEO_CCA_017.getErrorMessage(), ErrorType.ERROR));
                }
            }
            updateTotals(revisedShipmentDetailVO, ratingDetails, ccaMasterVO.getUnitOfMeasure());
        }
    }

    private void updateTotals(CcaAwbVO ccaAwbVO, Collection<CcaRatingDetailVO> detailVOList, Units units) {
        final var totalGrossWeight = detailVOList.stream()
                .map(CcaRatingDetailVO::getWeightOfShipment)
                .map(Quantity::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final var totalChargeableWeight = detailVOList.stream()
                .map(CcaRatingDetailVO::getChargeableWeight)
                .map(Quantity::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        ccaAwbVO.setWeight(quantityMapper.getQuantityWeight(totalGrossWeight, units));
        ccaAwbVO.setChargeableWeight(quantityMapper.getQuantityWeight(totalChargeableWeight, units));
    }

    private boolean isGreaterThan(Quantity<Weight> first, Quantity<Weight> second) {
        final var firstInDouble = Objects.isNull(first) ? 0.0
                : first.getValue().doubleValue();
        final var secondInDouble = Objects.isNull(second) ? 0.0
                : second.getValue().doubleValue();
        return firstInDouble > secondInDouble;
    }
}
