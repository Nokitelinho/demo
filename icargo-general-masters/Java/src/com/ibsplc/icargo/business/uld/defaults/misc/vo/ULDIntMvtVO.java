/*
 * ULDIntMvtVO.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2412
 *
 */
public class ULDIntMvtVO extends AbstractVO{
	
	public static final String INT_MVT_SEQNUM = "INTSEQNUM";
	public static final String DUMMY = "D";
	public static final String ACTUAL = "A";
	public static final String MODULE ="uld";
	public static final String SUBMODULE ="defaults";
	public static final String ENTITY ="uld.defaults.misc.ULDIntMvt";
	 private String uldNumber;
	 private String companyCode;
	 private String intSequenceNumber;
	 
	 //Added by Sreekumar S on 29Mar08
	 private String airport;
	 private String lastUpdatedUser;
	 private LocalDate lastUpdatedTime;
	 private Collection<ULDIntMvtDetailVO> uLDIntMvtDetailVOs;
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
	 * @return the intSequenceNumber
	 */
	public String getIntSequenceNumber() {
		return intSequenceNumber;
	}
	/**
	 * @param intSequenceNumber the intSequenceNumber to set
	 */
	public void setIntSequenceNumber(String intSequenceNumber) {
		this.intSequenceNumber = intSequenceNumber;
	}
	/**
	 * @return the uldNumber
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * 
	 * @return
	 */
	public Collection<ULDIntMvtDetailVO> getULDIntMvtDetailVOs() {
		return uLDIntMvtDetailVOs;
	}
	/**
	 * 
	 * @param intMvtDetailVOs
	 */
	public void setULDIntMvtDetailVOs(Collection<ULDIntMvtDetailVO> intMvtDetailVOs) {
		uLDIntMvtDetailVOs = intMvtDetailVOs;
	}
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	public String getAirport() {
		return airport;
	}
	public void setAirport(String airport) {
		this.airport = airport;
	}
}
