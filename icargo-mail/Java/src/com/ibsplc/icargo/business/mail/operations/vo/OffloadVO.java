/*
 * OffloadVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-3109
 *
 */
public class OffloadVO extends AbstractVO {

    private String companyCode;
    private String pol;
    private int carrierId;
    private String flightNumber;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private String carrierCode ;
    private LocalDate flightDate ;
    private String userCode;


    /*
     * U , D or M
     */
    private String offloadType;

    private Collection<ContainerVO> offloadContainers;
    private Page<DespatchDetailsVO> offloadDSNs;
    private Page<MailbagVO> offloadMailbags;

    private Page<ContainerVO> offloadContainerDetails;

    private boolean isDepartureOverride;

    private boolean fltClosureChkNotReq;//Added by A-5219 for ICRD-253863 to bypass flight closure check when flow comes from cargo ops for offload
	
    private boolean isRemove;
    /**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}
	/**
	 * @param carrierId The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	/**
	 * @return Returns the offloadContainers.
	 */
	public Collection<ContainerVO> getOffloadContainers() {
		return offloadContainers;
	}
	/**
	 * @param offloadContainers
	 * The offloadContainers to set.
	 */
	public void setOffloadContainers(
			Collection<ContainerVO> offloadContainers) {
		this.offloadContainers = offloadContainers;
	}


	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return pol;
	}
	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}
	/**
	 * @return Returns the offloadType.
	 */
	public String getOffloadType() {
		return offloadType;
	}
	/**
	 * @param offloadType The offloadType to set.
	 */
	public void setOffloadType(String offloadType) {
		this.offloadType = offloadType;
	}
	/**
	 * @return Returns the userCode.
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode The userCode to set.
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
    /**
     * @return Returns the isDepartureOverride.
     */
    public boolean isDepartureOverride() {
        return isDepartureOverride;
    }
    /**
     * @param isDepartureOverride The isDepartureOverride to set.
     */
    public void setDepartureOverride(boolean isDepartureOverride) {
        this.isDepartureOverride = isDepartureOverride;
    }

	/**
	 * @return Returns the offloadDSNs.
	 */
	public Page<DespatchDetailsVO> getOffloadDSNs() {
		return offloadDSNs;
	}
	/**
	 * @param offloadDSNs The offloadDSNs to set.
	 */
	public void setOffloadDSNs(Page<DespatchDetailsVO> offloadDSNs) {
		this.offloadDSNs = offloadDSNs;
	}

	/**
	 * @return Returns the offloadMailbags.
	 */
	public Page<MailbagVO> getOffloadMailbags() {
		return offloadMailbags;
	}
	/**
	 * @param offloadMailbags The offloadMailbags to set.
	 */
	public void setOffloadMailbags(Page<MailbagVO> offloadMailbags) {
		this.offloadMailbags = offloadMailbags;
	}
	/**
	 *
	 * @return
	 */
	public boolean isFltClosureChkNotReq() {
		return fltClosureChkNotReq;
	}
	/**
	 *
	 * @param fltClosureChkNotReq
	 */
	public void setFltClosureChkNotReq(boolean fltClosureChkNotReq) {
		this.fltClosureChkNotReq = fltClosureChkNotReq;
	}
	 public Page<ContainerVO> getOffloadContainerDetails() {
			return offloadContainerDetails;
		}
		public void setOffloadContainerDetails(Page<ContainerVO> offloadContainerDetails) {
			this.offloadContainerDetails = offloadContainerDetails;
	}
		public boolean isRemove() {
			return isRemove;
		}
		public void setRemove(boolean isRemove) {
			this.isRemove = isRemove;
		}
}
