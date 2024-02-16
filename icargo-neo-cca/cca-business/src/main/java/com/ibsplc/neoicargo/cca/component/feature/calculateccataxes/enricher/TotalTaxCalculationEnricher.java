package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.enricher;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ibsplc.neoicargo.businessrules.client.category.Action;
import com.ibsplc.neoicargo.businessrules.client.category.OutputParameter;
import com.ibsplc.neoicargo.businessrules.client.category.RuleCategory;
import com.ibsplc.neoicargo.businessrules.client.util.RuleClient;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import com.ibsplc.neoicargo.framework.core.context.tenant.config.HierarchicalResourceLoader;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RULE_DIRECTORY;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RULE_FILE_EXTENSION;
import static com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants.TOTAL_TAX_FORMULA_NAME;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_TAX;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class TotalTaxCalculationEnricher extends Enricher<CCAMasterVO> {

    private final RuleClient client;
    private final ObjectMapper mapper;
    private final HierarchicalResourceLoader hierarchicalResourceLoader;

    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("TotalTaxCalculationEnricher -> Invoked");
        log.info("Total tax auto calculate: [{}]", ccaMasterVO.getAutoCalculateTax());

        if (FLAG_Y.equals(ccaMasterVO.getAutoCalculateTax())) {
            var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
            var totalTaxRuleActions = client.fireRule(TOTAL_TAX_FORMULA_NAME, ccaAwbVO, false);
            setTotalTaxFromRule(ccaAwbVO, totalTaxRuleActions);
        }

        log.info("TotalTaxCalculationEnricher -> Exit");
    }

    private void setTotalTaxFromRule(CcaAwbVO ccaAwbVO, Collection<Action> totalTaxRuleActions) {
        if (!isNullOrEmpty(totalTaxRuleActions)) {
            var taxAction = totalTaxRuleActions.iterator().next();
            ccaAwbVO.setDisplayTaxDetails(getTaxDetails(taxAction, ccaAwbVO));
            ccaAwbVO.setTaxAmount(getTotalTaxAmount(taxAction));
        } else {
            log.warn("Rule action for total tax formula is empty");
            ccaAwbVO.setTaxAmount(0.0);
        }
    }

    private double getTotalTaxAmount(@NotNull Action taxAction) {
        return taxAction.getParams()
                .stream()
                .map(OutputParameter::getValue)
                .map(Double::parseDouble)
                .findFirst()
                .orElse(0.0);
    }

    public Object getTaxDetails(Action taxAction, CcaAwbVO ccaAwbVO) {

        var jsonData = mapper.createObjectNode();

        if (taxAction.getParams().size() <= 1) {
            log.warn("Tax expression is absent in Rule action");
        } else {
            var totalTaxRuleParameters = loadTotalTaxRuleParameters();
            TreeMap<String, Double> taxValuesFromConfigurationTax =
                    getTaxDataFromConfigurationTax(ccaAwbVO.getAwbTaxes());

            for (var taxOperand : getTaxOperandsFromFormula(taxAction)) {
                var taxTypeFromFormula = taxOperand.trim();
                if (taxValuesFromConfigurationTax.containsKey(taxTypeFromFormula)) {
                    var taxValue = taxValuesFromConfigurationTax.getOrDefault(taxTypeFromFormula, 0.0);
                    var taxDescription = totalTaxRuleParameters.get(taxTypeFromFormula);

                    if (taxDescription != null) {
                        jsonData.put(taxDescription, taxValue);
                    }
                } else {
                    log.warn("Tax [{}] is absent in CCA Awb taxes", taxTypeFromFormula);
                }
            }
        }

        return jsonData;
    }

    private List<String> getTaxOperandsFromFormula(Action taxAction) {
        return taxAction.getParams()
                .stream()
                .skip(1)
                .findFirst()
                .map(OutputParameter::getValue)
                .map(taxExpression -> taxExpression.replaceAll("[()]", ""))
                .map(taxExpression -> taxExpression.split("([-+*%/])"))
                .map(Arrays::asList)
                .orElse(new ArrayList<>());
    }

    private TreeMap<String, Double> getTaxDataFromConfigurationTax(Collection<CcaTaxDetailsVO> ccaTaxDetailsVOS) {
        return ccaTaxDetailsVOS.stream()
                .filter(ccaTaxDetailsVO -> CONFIGURATIONTYPE_TAX.equals(ccaTaxDetailsVO.getConfigurationType()))
                .map(CcaTaxDetailsVO::getTaxDetails)
                .map(this::convertToTreeMap)
                .findFirst()
                .orElseGet(TreeMap::new);
    }

    @SuppressWarnings("unchecked")
    private TreeMap<String, Double> convertToTreeMap(Object taxData) {
        try {
            return mapper.convertValue(taxData, TreeMap.class);
        } catch (RuntimeException e) {
            log.warn("Cannot read values from CCA Awb taxes, [{}]", taxData, e);
            return new TreeMap<>();
        }
    }

    private Map<String, String> loadTotalTaxRuleParameters() {
        try {
            var objectMapper = new ObjectMapper(new YAMLFactory())
                    .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            var ruleResources = hierarchicalResourceLoader.discoverDeploymentResources(
                    RULE_DIRECTORY, List.of(TOTAL_TAX_FORMULA_NAME + RULE_FILE_EXTENSION));

            if (!CcaUtil.isNullOrEmpty(ruleResources)) {
                var ruleInputStream = ruleResources.iterator().next().getInputStream();
                var category = objectMapper.readValue(ruleInputStream, RuleCategory.class);

                return category.getOutput()
                        .stream()
                        .limit(1)
                        .map(Action::getParams)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toMap(OutputParameter::getName, OutputParameter::getDescription));
            }
        } catch (IOException | RuntimeException e) {
            log.warn("Cannot read parameters of the Total Tax formula", e);
        }

        return Map.of();
    }
}