package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CcaCassValidationDataRequest implements Serializable {

    private static final long serialVersionUID = 1489002936093291466L;

    @JsonProperty("inbound_customer_code")
    private String inboundCustomerCode;

    @JsonProperty("outbound_customer_code")
    private String outboundCustomerCode;

    private String origin;

    private String destination;

    @JsonProperty("agent_code")
    private String agentCode;

    @JsonProperty("awb_freight_payment_type")
    private String payType;

}
