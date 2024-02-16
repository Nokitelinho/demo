package com.ibsplc.neoicargo.datamonitor.notify;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.datamonitor.model.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebhookNotificationListener implements NotificationListener {

	private String url;

	public WebhookNotificationListener(String u) {
		this.url = u;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void sendNotification(Message message) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			ObjectMapper objectMapper = new ObjectMapper();
			String messageJson;
			messageJson = objectMapper.writeValueAsString(message);
			HttpEntity<String> entity = new HttpEntity<>(messageJson, headers);
			restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (JsonProcessingException e) {
			log.error("Exception while sending notification", e);
		}
	}

}
