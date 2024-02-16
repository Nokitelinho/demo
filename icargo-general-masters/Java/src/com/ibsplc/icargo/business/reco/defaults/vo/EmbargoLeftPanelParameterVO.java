/*
 * EmbargoLeftPanelParameterVO.java Created on May 20, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.List;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5867
 *
 */
public class EmbargoLeftPanelParameterVO extends AbstractVO {
    
	

    private List<String> subModules;
    private String fieldValue;
    private String fieldDescription;
    private List<EmbargoParameterVO> parameters;
    
	
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldDescription() {
		return fieldDescription;
	}
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}
	public void setSubModules(List<String> subModules) {
		this.subModules = subModules;
	}
	public List<String> getSubModules() {
		return subModules;
	}
	public void setParameters(List<EmbargoParameterVO> parameters) {
		this.parameters = parameters;
	}
	public List<EmbargoParameterVO> getParameters() {
		return parameters;
	}
    
}
