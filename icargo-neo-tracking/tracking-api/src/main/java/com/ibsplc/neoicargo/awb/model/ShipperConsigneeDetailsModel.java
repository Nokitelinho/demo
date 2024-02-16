package com.ibsplc.neoicargo.awb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipperConsigneeDetailsModel {

    @JsonProperty("shipper_details")
    private ContactDetailsModel shipperDetails;
    @JsonProperty("consignee_details")
    private ContactDetailsModel consigneeDetails;

}
