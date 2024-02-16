package com.ibsplc.neoicargo.awb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class AwbUserNotificationModel {
    @JsonProperty("tracking_awb_serial_number")
    private Long trackingAwbSerialNumber;

    private Map<String, Boolean> notifications;

    private List<String> emails;
}
