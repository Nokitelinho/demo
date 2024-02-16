package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.ibsplc.neoicargo.cca.modal.CcaChargeDetailData.toIndentedString;

@Getter
@Setter
public class CcaRateDetailData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    @JsonProperty("company_code")
    private String companyCode;

    @JsonProperty("rate_type")
    private String rateType;

    @JsonProperty("commodity_code")
    private String commodityCode;

    @JsonProperty("chargeable_weight")
    private double chargeableWeight;

    private double displayChargeableWeight;

    private double charge;

    @JsonProperty("pricing_details")
    private Object rateDetails;

    private double rate;

    @JsonProperty("link_flag")
    private String linkFlag;

    @JsonProperty("net_charge")
    private double netCharge;

    @JsonProperty("discount_percentage")
    private double discountPercentage;

    @JsonProperty("all_in_attribute")
    private String allInAttribute;

    @JsonProperty("discrepancy_flag")
    private String discrepancyFlag;

    private String key;

    @JsonProperty("minimum_charge_applied_flag")
    private boolean minimumChargeAppliedFlag;

    @JsonProperty("rated_scc_code")
    private String ratedSccCode;

    @Override
    public String toString() {
        return "class AWBRateDetailData {\n" +
                "    company_code: " + toIndentedString(companyCode) + "\n" +
                "    rate_type: " + toIndentedString(rateType) + "\n" +
                "    commodity_code: " + toIndentedString(commodityCode) + "\n" +
                "    chargeable_weight: " + toIndentedString(chargeableWeight) + "\n" +
                "    display_chargeable_weight: " + toIndentedString(displayChargeableWeight) + "\n" +
                "    charge: " + toIndentedString(charge) + "\n" +
                "    rate: " + toIndentedString(rate) + "\n" +
                "    pricing_details: " + toIndentedString(rateDetails) + "\n" +
                "    net_charge: " + toIndentedString(netCharge) + "\n" +
                "    discrepancy_flag: " + toIndentedString(discrepancyFlag) + "\n" +
                "}";
    }

}
