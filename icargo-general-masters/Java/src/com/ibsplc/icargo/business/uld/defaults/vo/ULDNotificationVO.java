/*
 * ULDNotificationVO.java Created on Jun 19, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;


import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * This class is used to represent the details for 
 * creating for ULD-QMS notifications
 * 
 * @author A-3791
 *
 */
public class ULDNotificationVO extends AbstractVO{
    
	private String companyCode; 
	private String airportCode;
    private String sourceForAlert;
    
    private Collection<EstimatedULDStockVO> estimatedULDStockVOS;
    private Collection<FlightDetailsVO> flightDetailsVOs;
    private EstimatedULDStockFilterVO estimatedULDStockFilterVO;
    
    
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the airportCode
	 */
	public String getAirportCode() {
		return airportCode;
	}
	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	/**
	 * @return the sourceForAlert
	 */
	public String getSourceForAlert() {
		return sourceForAlert;
	}
	/**
	 * @param sourceForAlert the sourceForAlert to set
	 */
	public void setSourceForAlert(String sourceForAlert) {
		this.sourceForAlert = sourceForAlert;
	}
	/**
	 * @return the estimatedULDStockVOS
	 */
	public Collection<EstimatedULDStockVO> getEstimatedULDStockVOS() {
		return estimatedULDStockVOS;
	}
	/**
	 * @param estimatedULDStockVOS the estimatedULDStockVOS to set
	 */
	public void setEstimatedULDStockVOS(Collection<EstimatedULDStockVO> estimatedULDStockVOS) {
		this.estimatedULDStockVOS = estimatedULDStockVOS;
	}
	/**
	 * @return the flightDetailsVOs
	 */
	public Collection<FlightDetailsVO> getFlightDetailsVOs() {
		return flightDetailsVOs;
	}
	/**
	 * @param flightDetailsVO the flightDetailsVO to set
	 */
	public void setFlightDetailsVOs(Collection<FlightDetailsVO> flightDetailsVOs) {
		this.flightDetailsVOs = flightDetailsVOs;
	}
	/**
	 * @return the estimatedULDStockFilterVO
	 */
	public EstimatedULDStockFilterVO getEstimatedULDStockFilterVO() {
		return estimatedULDStockFilterVO;
	}
	/**
	 * @param estimatedULDStockFilterVO the estimatedULDStockFilterVO to set
	 */
	public void setEstimatedULDStockFilterVO(EstimatedULDStockFilterVO estimatedULDStockFilterVO) {
		this.estimatedULDStockFilterVO = estimatedULDStockFilterVO;
	}
    
}
