package com.ibsplc.neoicargo.datamonitor.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.datamonitor.notify.WebhookNotificationListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("datamonitor")
public class DataMonitorConfigList {
	
	private Map<String, DataMonitorConfig> monitorMap;
	private List<String> webhookUrls;
}
