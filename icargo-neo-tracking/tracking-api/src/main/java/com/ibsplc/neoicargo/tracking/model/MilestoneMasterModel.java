package com.ibsplc.neoicargo.tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MilestoneMasterModel {
	
	
	@JsonProperty("milestone_code")
    private String milestoneCode;
	@JsonProperty("milestone_description")
    private String milestoneDescription;
	@JsonProperty("milestone_type")
    private String milestoneType;
    @JsonProperty("milestone_shipment_type")
    private String milestoneShipmentType;
    @JsonProperty("activity_view_flag")
    private boolean activityViewFlag;
    @JsonProperty("email_notification_flag")
    private boolean emailNotificationFlag;

}
