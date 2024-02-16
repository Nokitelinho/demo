package com.ibsplc.neoicargo.devops.maven.plugin.icgrefactor.business.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Statement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PKInvocInfo {

	private String fieldName;
	
	private Statement instanceCreation;
	
	private List<String> setMethods = new ArrayList<>();
}
