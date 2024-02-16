/*
 * ControlDocumentVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class ControlDocumentVO extends AbstractVO {
	
    private Collection<CN38ReportVO> cn38ReportVos;
    private Collection<CN41ReportVO> cn41ReportVos;
    private Collection<AV7ReportVO> av7ReportVos;
    
    private Map<String, Collection<String>> documentReportsMap;
	/**
	 * @return Returns the av7ReportVos.
	 */
	public Collection<AV7ReportVO> getAv7ReportVos() {
		return av7ReportVos;
	}
	/**
	 * @param av7ReportVos The av7ReportVos to set.
	 */
	public void setAv7ReportVos(Collection<AV7ReportVO> av7ReportVos) {
		this.av7ReportVos = av7ReportVos;
	}
	/**
	 * @return Returns the cn38ReportVos.
	 */
	public Collection<CN38ReportVO> getCn38ReportVos() {
		return cn38ReportVos;
	}
	/**
	 * @param cn38ReportVos The cn38ReportVos to set.
	 */
	public void setCn38ReportVos(Collection<CN38ReportVO> cn38ReportVos) {
		this.cn38ReportVos = cn38ReportVos;
	}
	/**
	 * @return Returns the cn41ReportVos.
	 */
	public Collection<CN41ReportVO> getCn41ReportVos() {
		return cn41ReportVos;
	}
	/**
	 * @param cn41ReportVos The cn41ReportVos to set.
	 */
	public void setCn41ReportVos(Collection<CN41ReportVO> cn41ReportVos) {
		this.cn41ReportVos = cn41ReportVos;
	}
	/**
	 * @return Returns the documentReportsMap.
	 */
	public Map<String, Collection<String>> getDocumentReportsMap() {
		return documentReportsMap;
	}
	/**
	 * @param documentReportsMap The documentReportsMap to set.
	 */
	public void setDocumentReportsMap(
			Map<String, Collection<String>> documentReportsMap) {
		this.documentReportsMap = documentReportsMap;
	}
    
}
