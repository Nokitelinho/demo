package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CcaChargeDetailData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    private double charge;

    @JsonProperty("due_carrier_flag")
    private Boolean dueCarrier;

    @JsonProperty("due_agent_flag")
    private Boolean dueAgent;

    @JsonProperty("charge_head")
    private String chargeHead;

    @JsonProperty("charge_head_code")
    private String chargeHeadCode;

    private String key;

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        sb.append("class AWBRateDetailData {\n");

        sb.append("    due_carrier_flag: ").append(toIndentedString(dueCarrier)).append("\n");
        sb.append("    due_agent_flag: ").append(toIndentedString(dueAgent)).append("\n");
        sb.append("    charge_head: ").append(toIndentedString(chargeHead)).append("\n");
        sb.append("    charge_head_code: ").append(toIndentedString(chargeHeadCode)).append("\n");
        sb.append("    charge: ").append(toIndentedString(charge)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    static String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }

}
