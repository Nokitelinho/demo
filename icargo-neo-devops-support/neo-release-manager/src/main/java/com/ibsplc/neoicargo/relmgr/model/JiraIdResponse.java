package com.ibsplc.neoicargo.relmgr.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class JiraIdResponse implements Serializable{

	
	Map<String,String> jiraIdTask;
	
}
