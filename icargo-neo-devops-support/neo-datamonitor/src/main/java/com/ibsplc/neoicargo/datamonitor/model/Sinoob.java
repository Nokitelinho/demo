package com.ibsplc.neoicargo.datamonitor.model;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Component
@ConfigurationProperties("service")
public class Sinoob {

	public Map<String, String> mappings;
	
	
	
}
