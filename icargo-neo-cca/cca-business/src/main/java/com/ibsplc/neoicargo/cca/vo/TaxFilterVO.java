package com.ibsplc.neoicargo.cca.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TaxFilterVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("company_code")
    private String companyCode;

    @JsonProperty("shipment_prefix")
    private String shipmentPrefix;

    @JsonProperty("master_document_number")
    private String masterDocumentNumber;

    @JsonProperty("cargo_type")
    private String cargoType;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("date_of_journey")
    private String dateOfJourney;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("configuration_type")
    private String configurationType;

    @JsonProperty("tax_configuration_details")
    private Map<String, Collection<String>> taxConfigurationDetails;

    @JsonProperty("parameter_map")
    private Map<String, Collection<String>> parameterMap;

    @JsonProperty("charge_details_map")
    private Map<String, PricingChargeDetailsVO> chargeDetailsMap;

}
