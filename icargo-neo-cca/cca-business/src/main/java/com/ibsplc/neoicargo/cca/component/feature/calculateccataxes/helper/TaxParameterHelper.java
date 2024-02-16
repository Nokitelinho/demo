package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants;
import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.cca.vo.TaxFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_N;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_CLASS;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxParameterHelper {

    private final CustomerComponent customerComponent;
    private final ObjectMapper objectMapper;

    public void populateParameters(CcaAwbVO ccaAwbVO, TaxFilterVO taxFilterVO) {
        log.info("TaxParameterHelper -> Entry");

        var parameterMap = new HashMap<String, Collection<String>>();

        enrichAwbParameters(ccaAwbVO, parameterMap);
        enrichAwbDetailParameters(ccaAwbVO, parameterMap);

        var customerDetails = getCustomerModel(ccaAwbVO.getAgentCode());
        if (customerDetails != null) {
            enrichCustomerParameters(customerDetails, parameterMap);
            taxFilterVO.setCountryCode(customerDetails.getCountryCode());
        }

        if (ccaAwbVO.getAwbRates() != null) {
            enrichRateClassParameter(ccaAwbVO.getAwbRates(), parameterMap);
        }

        taxFilterVO.setParameterMap(parameterMap);
        log.info("TaxParameterHelper -> Exit");
    }

    private void enrichAwbParameters(CcaAwbVO ccaAwbVO, HashMap<String, Collection<String>> parameterMap) {
        putNotNullParameter(parameterMap, TaxFilterConstant.UNIQUEREFERENCE_ONE, TaxFilterConstant.NOVALUE);
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_PAYTYP, ccaAwbVO.getPayType());
        var chargeableWeight = ccaAwbVO.getChargeableWeight() == null
                ? Double.toString(0.0)
                : ccaAwbVO.getChargeableWeight().getDisplayValue().toString();
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_CHGWGT, chargeableWeight);
        var bilimpct = isExport(ccaAwbVO)
                ? TaxFilterConstant.BILIMPCT_EXPORT
                : TaxFilterConstant.BILIMPCT_IMPORT;
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_BILIMPCT, bilimpct);
        var commissionApplied = isSpotCommissionIncluded(ccaAwbVO)
                ? FLAG_Y
                : FLAG_N;
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_COMMISIONAPPLIED, commissionApplied);
    }

    private void putNotNullParameter(HashMap<String, Collection<String>> parameterMap, String key, String value) {
        if (value != null) {
            parameterMap.put(key, List.of(value));
        }
    }

    private boolean isExport(CcaAwbVO ccaAwbVO) {
        return ccaAwbVO.getExportBillingStatus() != null
                && ccaAwbVO.getExportBillingStatus().equals(CcaTaxCommissionConstants.BLG_STA_EXP);
    }

    private boolean isSpotCommissionIncluded(CcaAwbVO ccaAwbVO) {
        return ccaAwbVO.getSpotRateStatus() != null
                && (CcaTaxCommissionConstants.SPOT_RATE_APPROVED_STATUS.equals(ccaAwbVO.getSpotRateStatus())
                || (CcaTaxCommissionConstants.SPOT_RATE_VERIFIED_STATUS.equals(ccaAwbVO.getSpotRateStatus())));
    }

    private void enrichAwbDetailParameters(CcaAwbVO ccaAwbVO, HashMap<String, Collection<String>> parameterMap) {
        if (ccaAwbVO.getRatingDetails() != null) {
            parameterMap.put(
                    TaxFilterConstant.PARAMETERCODE_SCCCOD,
                    ccaAwbVO.getRatingDetails()
                            .stream()
                            .map(CcaRatingDetailVO::getSccCode)
                            .collect(Collectors.toList())
            );
        }
    }

    private CustomerModel getCustomerModel(String agentCode) {
        try {
            return customerComponent.getCustomerDetails(agentCode).get(0);
        } catch (BusinessException | RuntimeException e) {
            log.warn("Could not find Country for Agent code [{}] ", agentCode, e);
            return null;
        }
    }

    private void enrichCustomerParameters(@NotNull CustomerModel customer,
                                          HashMap<String, Collection<String>> parameterMap) {
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_AGTCOD, customer.getAgentCode());
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_STNCOD, customer.getStationCode());
        putNotNullParameter(
                parameterMap,
                TaxFilterConstant.PARAMETERCODE_CTLCUSCOD,
                customer.getControllingLocationCustomerCode()
        );
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_OWNSAL, customer.getOwnSales());
        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_AGTCAT, customer.getCustomerType());
    }

    private void enrichRateClassParameter(Collection<CcaRateDetailsVO> awbRates,
                                          HashMap<String, Collection<String>> parameterMap) {
        awbRates.stream()
                .filter(rat -> Objects.equals(CcaConstants.IATA_TYPE, rat.getRateType()))
                .map(CcaRateDetailsVO::getRateDetails)
                .filter(Objects::nonNull)
                .map(this::getIataRateClass)
                .filter(Objects::nonNull)
                .map(this::getMinRateAppliedFlag)
                .findFirst()
                .ifPresent(minRateApplied ->
                        putNotNullParameter(parameterMap, TaxFilterConstant.PARAMETERCODE_MINRATFLG, minRateApplied));
    }

    @Nullable
    private String getIataRateClass(Object rateDetails) {
        try {
            return objectMapper.convertValue(rateDetails, JsonNode.class)
                    .get(RATE_CLASS)
                    .asText();
        } catch (RuntimeException e) {
            log.warn("Cannot read IATA Rate Class from [{}] ", rateDetails, e);
            return null;
        }
    }

    private String getMinRateAppliedFlag(String iataRateClass) {
        return TaxFilterConstant.MINRATFLG_VALUE.equals(iataRateClass)
                ? FLAG_Y
                : FLAG_N;
    }

}
