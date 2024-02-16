package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.enricher;


import com.ibsplc.neoicargo.businessrules.client.category.Action;
import com.ibsplc.neoicargo.businessrules.client.category.OutputParameter;
import com.ibsplc.neoicargo.businessrules.client.util.RuleClient;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants.DISCOUNT_FORMULA_NAME;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscountCalculationEnricher extends Enricher<CCAMasterVO> {

    private final RuleClient client;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("DiscountCalculationEnricher -> Invoked");

        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        var discountRuleActions = client.fireRule(DISCOUNT_FORMULA_NAME, ccaAwbVO, false);
        setDiscountFromRule(ccaAwbVO, discountRuleActions);

        log.info("DiscountCalculationEnricher -> Exit");
    }

    private void setDiscountFromRule(CcaAwbVO ccaAwbVO, Collection<Action> discountRuleActions) {
        if (!isNullOrEmpty(discountRuleActions)) {
            var discountRuleAction = discountRuleActions.iterator().next();
            ccaAwbVO.setDiscountAmount(getDiscountAmount(discountRuleAction));
        } else {
            log.warn("Rule action for discount formula is empty");
            ccaAwbVO.setDiscountAmount(0.0);
        }
    }

    private double getDiscountAmount(@NotNull Action taxAction) {
        return taxAction.getParams() == null
                ? 0.0
                : taxAction.getParams()
                .stream()
                .map(OutputParameter::getValue)
                .filter(NumberUtils::isParsable)
                .map(Double::parseDouble)
                .findFirst()
                .orElse(0.0);
    }

}