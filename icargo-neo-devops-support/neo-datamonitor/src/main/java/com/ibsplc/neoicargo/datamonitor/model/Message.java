package com.ibsplc.neoicargo.datamonitor.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

	private String status;
	private List<Alert> alerts;


	
}