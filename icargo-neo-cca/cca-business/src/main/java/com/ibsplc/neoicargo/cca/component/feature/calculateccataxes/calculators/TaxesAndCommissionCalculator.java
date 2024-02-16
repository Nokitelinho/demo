package com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.calculators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxData;
import com.ibsplc.icargo.ebl.nbridge.types.tax.TaxResponse;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper.TaxBasisHelper;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper.TaxConfigurationHelper;
import com.ibsplc.neoicargo.cca.component.feature.calculateccataxes.helper.TaxParameterHelper;
import com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants;
import com.ibsplc.neoicargo.cca.mapper.CcaTaxMapper;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import com.ibsplc.neoicargo.cca.vo.TaxFilterVO;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.COMMA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.OTHER_CHARGE_DUE_AGENT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.OTHER_CHARGE_DUE_CARRIER;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SLASH;
import static com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants.TDSTYPE_EXEMPTED;
import static com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants.TDSTYPE_NORMAL;
import static com.ibsplc.neoicargo.cca.constants.CcaTaxCommissionConstants.TDS_ATTRIBUTE;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_CGST;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_CGSTCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_CGSTDIS;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_CGSTFRT;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_CGSTOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_CGSTOCDC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_GSTCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_GSTDIS;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_GSTFRT;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_GSTOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_GSTOCDC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_IGST;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_IGSTCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_IGSTDIS;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_IGSTFRT;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_IGSTOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_IGSTOCDC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_SGST;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_SGSTCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_SGSTDIS;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_SGSTFRT;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_SGSTOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_SGSTOCDC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_ST;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STDISC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STEXPCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STEXPDISC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STEXPOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STFC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STOCDA_DA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STOCDA_DC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STOCDC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STRECCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STRECDISC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_STRECOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_TDSDUEAGT;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_TDSDUECAR;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_UGSTCOM;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_UGSTDIS;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_UGSTFRT;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_UGSTOCDA;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONCODE_UGSTOCDC;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_COMMISION;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_TAX;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_TDS;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_TDS_EXEMPTION;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaxesAndCommissionCalculator {

    @Value("${EBL_URL_NBRIDGE}")
    private String eblUrl;

    private final Map<String, String> configurationCodes = constructConfigurationCodes();

    private final ServiceProxy<AbstractVO> serviceProxy;
    private final ContextUtil contextUtil;
    private final CcaTaxMapper ccaTaxMapper;
    private final ObjectMapper mapper;
    private final TaxBasisHelper taxBasisHelper;
    private final TaxConfigurationHelper taxConfigurationHelper;
    private final TaxParameterHelper taxParameterHelper;

    public Collection<CcaTaxDetailsVO> computeTax(CcaAwbVO ccaAwbVO) {
        log.info("TaxCalculator -> Invoked");

        var taxFilterVO = ccaTaxMapper.fromCcaAwbVOToTaxFilterVO(ccaAwbVO, contextUtil);
        populateTaxMap(ccaAwbVO, taxFilterVO);
        var taxResponse = getTaxResponse(taxFilterVO);

        if (taxResponse != null) {
            var taxKey = getMasterDocumentTaxKey(taxFilterVO);
            return constructTaxVOs(taxResponse.getTaxMap().get(taxKey), ccaAwbVO);
        }
        return new ArrayList<>();
    }

    private void populateTaxMap(CcaAwbVO ccaAwbVO, TaxFilterVO taxFilterVO) {
        taxFilterVO.setChargeDetailsMap(taxBasisHelper.getChargeDetailsMap(ccaAwbVO));
        taxConfigurationHelper.populateConfigurationDetails(taxFilterVO);
        taxParameterHelper.populateParameters(ccaAwbVO, taxFilterVO);
    }

    private String getTaxUrl() {
        return eblUrl
                .concat("tariff")
                .concat(SLASH)
                .concat("computeTax");
    }

    @Nullable
    private TaxResponse getTaxResponse(TaxFilterVO taxFilterVO) {
        try {
            return serviceProxy.dispatch(getTaxUrl(), HttpMethod.POST, taxFilterVO, TaxResponse.class);
        } catch (RuntimeException e) {
            log.warn("Cannot receive taxes from tariff service", e);
            return null;
        }
    }

    private String getMasterDocumentTaxKey(TaxFilterVO filter) {
        return String.join("-",
                filter.getCompanyCode(), filter.getShipmentPrefix(), filter.getMasterDocumentNumber());
    }

    private Collection<CcaTaxDetailsVO> constructTaxVOs(Map<String, Collection<TaxData>> taxMap, CcaAwbVO ccaAwbVO) {
        log.info("Create charges details map for CCA Awb [{}]",
                ccaAwbVO.getShipmentPrefix() + "-" + ccaAwbVO.getMasterDocumentNumber() + "-" + ccaAwbVO.getRecordType()
        );

        var ccaTaxDetailsVOs = new ArrayList<CcaTaxDetailsVO>();
        if (taxMap != null) {
            for (var taxEntry : taxMap.entrySet()) {
                var taxType = taxEntry.getKey();
                var taxData = taxEntry.getValue();
                if (CONFIGURATIONTYPE_TAX.equals(taxType)) {
                    ccaTaxDetailsVOs.add(
                            constructTaxDetail(CONFIGURATIONTYPE_TAX, constructTaxDetails(taxData, false))
                    );
                } else if (CONFIGURATIONTYPE_TDS.equals(taxType)) {
                    ccaTaxDetailsVOs.add(constructTaxDetail(CONFIGURATIONTYPE_TDS, constructTaxDetails(taxData, true)));
                } else if (CONFIGURATIONTYPE_TDS_EXEMPTION.equals(taxType)) {
                    ccaTaxDetailsVOs.add(
                            constructTaxDetail(CONFIGURATIONTYPE_TDS_EXEMPTION, constructTDSExemptionDetails(taxData))
                    );
                } else if (CONFIGURATIONTYPE_COMMISION.equals(taxType)) {
                    ccaTaxDetailsVOs.add(
                            constructTaxDetail(
                                    CONFIGURATIONTYPE_COMMISION,
                                    constructCommissionDetails(taxData, ccaAwbVO)
                            )
                    );
                }
            }
        }
        return ccaTaxDetailsVOs;
    }

    private boolean setCommissionOnSpotRateFlag(CcaAwbVO ccaAwbVO) {
        return ccaAwbVO.getSpotRateId() != null && ccaAwbVO.getSpotRateStatus() != null
                && (ccaAwbVO.getSpotRateStatus().equals(CcaTaxCommissionConstants.SPOT_RATE_APPROVED_STATUS)
                || ccaAwbVO.getSpotRateStatus().equals(CcaTaxCommissionConstants.SPOT_RATE_VERIFIED_STATUS));
    }

    private CcaTaxDetailsVO constructTaxDetail(String key, Object taxDetails) {
        var taxDetail = new CcaTaxDetailsVO();
        taxDetail.setConfigurationType(key);
        taxDetail.setTaxDetails(taxDetails);
        return taxDetail;

    }

    private ObjectNode constructTaxDetails(Collection<TaxData> taxDataCollection, boolean isTds) {
        var jsonData = mapper.createObjectNode();
        var tdsRemark = new StringBuilder();
        taxDataCollection.forEach(taxData -> {
            var configCode = configurationCodes.get(taxData.getConfigurationCode());
            var ppTaxFieldName = String.join("_", configCode, "PP");
            jsonData.put(ppTaxFieldName, taxData.getPpTaxAmount());
            var taxCCFieldName = String.join("_", configCode, "CC");
            jsonData.put(taxCCFieldName, taxData.getCcTaxAmount());
            var totalTaxFieldName = configCode != null
                    ? configCode : "";
            jsonData.put(totalTaxFieldName, taxData.getTotalTaxAmount());

            if (isPercentageCommissionPresent(taxData)) {
                var percentageField =
                        String.join("_", configCode, "TaxPercentage");
                jsonData.put(percentageField, taxData.getTaxConfigurationData().getTaxDetails().getPercentageValue());
            }

            if (isDueFlagPresent(taxData)) {
                var dueFlagFieldName = String.join("_", configCode, "DueFlag");
                jsonData.put(dueFlagFieldName, taxData.getTaxConfigurationData().getTaxCodeType());
                if (isTds) {
                    constructTDSRemarks(taxData, tdsRemark);
                }
            }
        });
        if (isTds) {
            jsonData.put(TDS_ATTRIBUTE, tdsRemark.toString());
        }
        return jsonData;
    }

    private boolean isDueFlagPresent(TaxData taxData) {
        return taxData.getTaxConfigurationData() != null && taxData.getTaxConfigurationData().getTaxCodeType() != null;
    }

    private boolean isPercentageCommissionPresent(TaxData taxData) {
        return taxData.getTaxConfigurationData() != null
                && taxData.getTaxConfigurationData().getTaxDetails() != null
                && !CcaUtil.isNullOrEmpty(taxData.getTaxConfigurationData().getTaxDetails().getPercentageValue());
    }

    private void constructTDSRemarks(TaxData taxData, StringBuilder tdsRemark) {
        if (CONFIGURATIONTYPE_TDS_EXEMPTION
                .equals(taxData.getTaxConfigurationData().getConfigurationType())) {
            tdsRemark.append(TDSTYPE_EXEMPTED).append(COMMA).append(OTHER_CHARGE_DUE_CARRIER).append(COMMA)
                    .append(taxData.getTaxConfigurationData().getTaxDetails().getPercentageValue()).append(COMMA);
        } else {
            tdsRemark.append(TDSTYPE_NORMAL).append(COMMA).append(OTHER_CHARGE_DUE_CARRIER).append(COMMA)
                    .append(taxData.getTaxConfigurationData().getTaxDetails().getPercentageValue()).append(COMMA);
        }
        tdsRemark.append(taxData.getTaxableAmount()).append(COMMA);
    }

    private ObjectNode constructTDSExemptionDetails(Collection<TaxData> taxDataCollection) {
        var jsonData = mapper.createObjectNode();
        var tdsRemark = new StringBuilder();
        taxDataCollection.forEach(taxData -> {
            if (taxData.getTaxableAmount() > 0) {
                tdsRemark.append(TDSTYPE_EXEMPTED).append(COMMA);
                if (CONFIGURATIONCODE_TDSDUEAGT.equals(taxData.getConfigurationCode())) {
                    tdsRemark
                            .append(OTHER_CHARGE_DUE_AGENT)
                            .append(COMMA);
                }
                if (CONFIGURATIONCODE_TDSDUECAR.equals(taxData.getConfigurationCode())) {
                    tdsRemark
                            .append(OTHER_CHARGE_DUE_CARRIER)
                            .append(COMMA);
                }
                if (isPercentageCommissionPresent(taxData)) {
                    tdsRemark
                            .append(taxData.getTaxConfigurationData().getTaxDetails().getPercentageValue())
                            .append(COMMA);
                }
            }
            tdsRemark
                    .append(taxData.getTaxableAmount())
                    .append(COMMA);
        });
        jsonData.put(TDS_ATTRIBUTE, tdsRemark.toString());
        return jsonData;
    }

    private ObjectNode constructCommissionDetails(Collection<TaxData> taxDatas, CcaAwbVO ccaAwbVO) {
        var applySpotRateOnCommission = setCommissionOnSpotRateFlag(ccaAwbVO);
        var jsonData = mapper.createObjectNode();
        taxDatas.forEach(taxData -> {
            log.info("Commission -> ");
            log.info("Apply On Spot Rate -> " + applySpotRateOnCommission);
            log.info("Commission Amount -> " + taxData.getPpTaxAmount());
            log.info("Commission Amount -> " + taxData.getCcTaxAmount());
            jsonData.put("commissionDueAgentPP", applySpotRateOnCommission ? 0 : taxData.getPpTaxAmount());
            jsonData.put("commissionDueAgentCC", applySpotRateOnCommission ? 0 : taxData.getCcTaxAmount());
            if (isPercentageCommissionPresent(taxData)) {
                var percentageValue = taxData.getTaxConfigurationData().getTaxDetails().getPercentageValue();
                jsonData.put("commissionPercentage", percentageValue);
                ccaAwbVO.setCommissionPercentage(Double.parseDouble(percentageValue));
                double ppCommissionAmount = taxData.getPpTaxAmount() == null ? 0.0 : taxData.getPpTaxAmount();
                double ccCommissionAmount = taxData.getCcTaxAmount() == null ? 0.0 : taxData.getCcTaxAmount();
                ccaAwbVO.setCommissionAmount(applySpotRateOnCommission ? 0 : ppCommissionAmount + ccCommissionAmount);
            }
        });
        return jsonData;
    }

    private Map<String, String> constructConfigurationCodes() {
        var config = new HashMap<String, String>();
        config.put(CONFIGURATIONCODE_STOCDC, "serviceTaxOCDC");
        config.put(CONFIGURATIONCODE_STOCDA, "serviceTaxOCDA");
        config.put(CONFIGURATIONCODE_STOCDA_DA, "serviceTaxOCDADueAgent");
        config.put(CONFIGURATIONCODE_STOCDA_DC, "serviceTaxOCDADueCarrier");
        config.put(CONFIGURATIONCODE_STEXPOCDA, "serviceTaxExpenseOCDA");
        config.put(CONFIGURATIONCODE_STRECDISC, "serviceTaxRecoveryDiscount");
        config.put(CONFIGURATIONCODE_STEXPDISC, "serviceTaxExpenseDiscount");
        config.put(CONFIGURATIONCODE_STCOM, "serviceTaxCommission");
        config.put(CONFIGURATIONCODE_STDISC, "serviceTaxDiscount");
        config.put(CONFIGURATIONCODE_ST, "serviceTaxNet");
        config.put(CONFIGURATIONCODE_STEXPCOM, "serviceTaxExpenseCommission");
        config.put(CONFIGURATIONCODE_STFC, "serviceTaxFreightCharge");
        config.put(CONFIGURATIONCODE_STRECOCDA, "serviceTaxRecoveryOCDA");
        config.put(CONFIGURATIONCODE_STRECCOM, "serviceTaxRecoveryCommission");
        config.put(CONFIGURATIONCODE_TDSDUEAGT, "tdsDueAgent");
        config.put(CONFIGURATIONCODE_TDSDUECAR, "tdsDueCarrier");

        /*
          GST Configuration Codes
         */

        config.put(CONFIGURATIONCODE_SGSTFRT, "sgstFreightCharge");
        config.put(CONFIGURATIONCODE_SGSTOCDC, "sgstDueCarrierOCDC");
        config.put(CONFIGURATIONCODE_SGSTOCDA, "sgstDueAgentOCDA");
        config.put(CONFIGURATIONCODE_SGSTDIS, "sgstDiscount");
        config.put(CONFIGURATIONCODE_SGSTCOM, "sgstCommission");
        config.put(CONFIGURATIONCODE_CGSTFRT, "cgstFreightCharge");
        config.put(CONFIGURATIONCODE_CGSTOCDC, "cgstDueCarrierOCDC");
        config.put(CONFIGURATIONCODE_CGSTOCDA, "cgstDueAgentOCDA");
        config.put(CONFIGURATIONCODE_CGSTDIS, "cgstDiscount");
        config.put(CONFIGURATIONCODE_CGSTCOM, "cgstCommission");
        config.put(CONFIGURATIONCODE_UGSTFRT, "ugstFreightCharge");
        config.put(CONFIGURATIONCODE_UGSTOCDC, "ugstDueCarrierOCDC");
        config.put(CONFIGURATIONCODE_UGSTOCDA, "ugstDueAgentOCDA");
        config.put(CONFIGURATIONCODE_UGSTDIS, "ugstDiscount");
        config.put(CONFIGURATIONCODE_UGSTCOM, "ugstCommission");
        config.put(CONFIGURATIONCODE_IGSTFRT, "igstFreightCharge");
        config.put(CONFIGURATIONCODE_IGSTOCDC, "igstDueCarrierOCDC");
        config.put(CONFIGURATIONCODE_IGSTOCDA, "igstDueAgentOCDA");
        config.put(CONFIGURATIONCODE_IGSTDIS, "igstDiscount");
        config.put(CONFIGURATIONCODE_IGSTCOM, "igstCommission");
        config.put(CONFIGURATIONCODE_GSTFRT, "gstFreightCharge");
        config.put(CONFIGURATIONCODE_GSTOCDC, "gstDueCarrierOCDC");
        config.put(CONFIGURATIONCODE_GSTOCDA, "gstDueAgentOCDA");
        config.put(CONFIGURATIONCODE_GSTCOM, "gstCommission");
        config.put(CONFIGURATIONCODE_GSTDIS, "gstDiscount");
        config.put(CONFIGURATIONCODE_SGST, "sgst");
        config.put(CONFIGURATIONCODE_CGST, "cgst");
        config.put(CONFIGURATIONCODE_IGST, "igst");

        return Collections.unmodifiableMap(config);
    }

}
