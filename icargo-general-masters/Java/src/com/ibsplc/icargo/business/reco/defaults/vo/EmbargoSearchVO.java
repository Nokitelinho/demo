/*
 * EmbargoSearchVO.java Created on May 20, 2014
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
public class EmbargoSearchVO extends AbstractVO {
    
	

    private List<EmbargoDetailsVO> embargoDetails;
    
    private List<EmbargoLeftPanelParameterVO> embargoLeftPanelParameterVOs;

	public List<EmbargoDetailsVO> getEmbargoDetails() {
		return embargoDetails;
	}

	public void setEmbargoDetails(List<EmbargoDetailsVO> embargoDetails) {
		this.embargoDetails = embargoDetails;
	}

	public List<EmbargoLeftPanelParameterVO> getEmbargoLeftPanelParameterVOs() {
		return embargoLeftPanelParameterVOs;
	}

	public void setEmbargoLeftPanelParameterVOs(
			List<EmbargoLeftPanelParameterVO> embargoLeftPanelParameterVOs) {
		this.embargoLeftPanelParameterVOs = embargoLeftPanelParameterVOs;
	}  
    
}
