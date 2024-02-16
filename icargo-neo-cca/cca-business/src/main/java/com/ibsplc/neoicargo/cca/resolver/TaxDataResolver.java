package com.ibsplc.neoicargo.cca.resolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibsplc.neoicargo.businessrules.client.fact.DataResolver;
import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CGST;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CUSTOMER_CODE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.IATA_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.IGST;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.MKT_TYPE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_CLASS;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_CLASS_IATA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.RATE_CLASS_MARKET;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_COMMISSION;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_DISCOUNT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_FREIGHTCHARGE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_OCDA;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SERVICETAX_OCDC;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.SGST;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.TDS_DUEAGENT;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.TDS_DUECARRIER;
import static com.ibsplc.neoicargo.cca.constants.TaxFilterConstant.CONFIGURATIONTYPE_TAX;

@Slf4j
@Component("taxDataResolver")
@AllArgsConstructor
public class TaxDataResolver extends DataResolver<CcaAwbVO> {

    private final ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    @Override
    public ObjectNode find(CcaAwbVO shipmentDetailVO) {
        log.info("TaxDataResolver Invoked");
        var objectNode = objectMapper.createObjectNode();
        var customerCode = (StringUtils.equalsAny(shipmentDetailVO.getPayType(), "PP", "PC"))
                ? shipmentDetailVO.getOutboundCustomerCode()
                : shipmentDetailVO.getInboundCustomerCode();
        objectNode.put(CUSTOMER_CODE, Objects.nonNull(customerCode) ? customerCode : "");
        if (Objects.nonNull(shipmentDetailVO.getAwbTaxes()) && !shipmentDetailVO.getAwbTaxes().isEmpty()) {
            updateConfigurationTypeTax(shipmentDetailVO, objectNode);
            updateConfigurationTdsType(shipmentDetailVO, objectNode);
        } else {
            populateEmptyObjectNodesForTax(objectNode);
        }
        populateRateClass(objectNode, shipmentDetailVO);
        return objectNode;
    }

    private void updateConfigurationTdsType(CcaAwbVO shipmentDetailVO, ObjectNode objectNode) {
        shipmentDetailVO.getAwbTaxes().stream()
                .filter(t -> TaxFilterConstant.CONFIGURATIONTYPE_TDS.equals(t.getConfigurationType()))
                .map(CcaTaxDetailsVO::getTaxDetails)
                .findFirst()
                .ifPresentOrElse(tdsObj -> {
                    final JsonNode json;
                    try {
                        json = objectMapper.convertValue(tdsObj, JsonNode.class);
                        objectNode.put(TDS_DUECARRIER, jsonPropertyToDouble(json, TDS_DUECARRIER));
                        objectNode.put(TDS_DUEAGENT, jsonPropertyToDouble(json, TDS_DUEAGENT));
                    } catch (RuntimeException e) {
                        log.warn("Cannot read tax details of TDS configuration type: [{}]. Cause: ", tdsObj, e);
                        populateEmptyObjectNodesForTax(objectNode);
                    }
                }, () -> {
                    objectNode.put(TDS_DUECARRIER, 0.0);
                    objectNode.put(TDS_DUEAGENT, 0.0);
                });
    }

    private void updateConfigurationTypeTax(CcaAwbVO shipmentDetailVO, ObjectNode objectNode) {
        shipmentDetailVO.getAwbTaxes().stream()
                .filter(t -> CONFIGURATIONTYPE_TAX.equals(t.getConfigurationType()))
                .map(CcaTaxDetailsVO::getTaxDetails)
                .findFirst()
                .ifPresentOrElse(taxObj -> {
                    final JsonNode json;
                    try {
                        json = objectMapper.convertValue(taxObj, JsonNode.class);
                        objectNode.put(SERVICETAX_FREIGHTCHARGE, jsonPropertyToDouble(json, SERVICETAX_FREIGHTCHARGE));
                        objectNode.put(SERVICETAX_OCDC, jsonPropertyToDouble(json, SERVICETAX_OCDC));
                        objectNode.put(SERVICETAX_OCDA, jsonPropertyToDouble(json, SERVICETAX_OCDA));
                        objectNode.put(SERVICETAX_COMMISSION, jsonPropertyToDouble(json, SERVICETAX_COMMISSION));
                        objectNode.put(SERVICETAX_DISCOUNT, jsonPropertyToDouble(json, SERVICETAX_DISCOUNT));
                        objectNode.put(SGST, jsonPropertyToDouble(json, SGST));
                        objectNode.put(CGST, jsonPropertyToDouble(json, CGST));
                        objectNode.put(IGST, jsonPropertyToDouble(json, IGST));
                    } catch (RuntimeException e) {
                        log.warn("Cannot read tax details of TAX configuration type: [{}]. Cause: ", taxObj, e);
                        populateEmptyObjectNodesForTax(objectNode);
                    }
                }, () -> {
                    objectNode.put(SERVICETAX_FREIGHTCHARGE, 0.0);
                    objectNode.put(SERVICETAX_OCDC, 0.0);
                    objectNode.put(SERVICETAX_OCDA, 0.0);
                    objectNode.put(SERVICETAX_COMMISSION, 0.0);
                    objectNode.put(SERVICETAX_DISCOUNT, 0.0);
                    objectNode.put(SGST, 0.0);
                    objectNode.put(CGST, 0.0);
                    objectNode.put(IGST, 0.0);
                });
    }

    private void populateEmptyObjectNodesForTax(ObjectNode objectNode) {
        objectNode.put(SERVICETAX_FREIGHTCHARGE, 0.0);
        objectNode.put(SERVICETAX_OCDC, 0.0);
        objectNode.put(SERVICETAX_OCDA, 0.0);
        objectNode.put(SERVICETAX_COMMISSION, 0.0);
        objectNode.put(SERVICETAX_DISCOUNT, 0.0);
        objectNode.put(SGST, 0.0);
        objectNode.put(CGST, 0.0);
        objectNode.put(IGST, 0.0);
        objectNode.put(TDS_DUECARRIER, 0.0);
        objectNode.put(TDS_DUEAGENT, 0.0);

    }

    private double jsonPropertyToDouble(JsonNode json, String propertyName) {
        return Objects.nonNull(json.get(propertyName))
                ? json.get(propertyName).asDouble()
                : 0.0;
    }

    public void populateRateClass(ObjectNode objectNode, CcaAwbVO shipmentDetailVO) {
        var iataRateClass = new StringBuilder();
        var marketRateClass = new StringBuilder();
        if (Objects.nonNull(shipmentDetailVO.getAwbRates()) && !shipmentDetailVO.getAwbRates().isEmpty()) {
            shipmentDetailVO.getAwbRates().forEach(r -> {
                if (IATA_TYPE.equals(r.getRateType())) {
                    populateRateClassForEachRateDetail(iataRateClass, r);
                } else if (MKT_TYPE.equals(r.getRateType())) {
                    populateRateClassForEachRateDetail(marketRateClass, r);
                }
            });
        }
        objectNode.put(RATE_CLASS_IATA, iataRateClass.toString());
        objectNode.put(RATE_CLASS_MARKET, marketRateClass.toString());
    }

    private void populateRateClassForEachRateDetail(StringBuilder rateClass, CcaRateDetailsVO ccaRateDetailsVO) {
        var rateDetail = ccaRateDetailsVO.getRateDetails();
        if (Objects.nonNull(rateDetail)) {
            try {
                var json = objectMapper.convertValue(rateDetail, JsonNode.class);
                if (Objects.nonNull(json.get(RATE_CLASS))) {
                    if (rateClass.toString().isEmpty()) {
                        rateClass.append(json.get(RATE_CLASS).asText());
                    } else {
                        rateClass.append(",").append(json.get(RATE_CLASS).asText());
                    }
                }
            } catch (RuntimeException e) {
                log.warn("Cannot read rate class from [{}] in TaxDataResolver. Cause: ", rateDetail, e);
            }

        }
    }
}
