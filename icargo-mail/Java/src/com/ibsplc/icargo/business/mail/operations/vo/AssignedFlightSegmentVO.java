/*
 * AssignedFlightSegmentVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-5991
 * 
 */
public class AssignedFlightSegmentVO extends AbstractVO {

	private String companyCode;

	private int carrierId;

	private String flightNumber;

	private long flightSequenceNumber;

	private int segmentSerialNumber;

	private String pou;

	private String pol;

	private Collection<ULDForSegmentVO> containersForSegment;

	private Collection<DSNForSegmentVO> dsnsForSegment;
	

	private String mraStatus;

	/**
	 * @return Returns the mraStatus.
	 */
	public String getMraStatus() {
		return mraStatus;
	}

	/**
	 * @param mraStatus The mraStatus to set.
	 */
	public void setMraStatus(String mraStatus) {
		this.mraStatus = mraStatus;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
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
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the containersForSegment.
	 */
	public Collection<ULDForSegmentVO> getContainersForSegment() {
		return containersForSegment;
	}

	/**
	 * @param containersForSegment
	 *            The containersForSegment to set.
	 */
	public void setContainersForSegment(
			Collection<ULDForSegmentVO> containersForSegment) {
		this.containersForSegment = containersForSegment;
	}

	/**
	 * @return Returns the dsnsForSegment.
	 */
	public Collection<DSNForSegmentVO> getDsnsForSegment() {
		return dsnsForSegment;
	}

	/**
	 * @param dsnsForSegment
	 *            The dsnsForSegment to set.
	 */
	public void setDsnsForSegment(Collection<DSNForSegmentVO> dsnsForSegment) {
		this.dsnsForSegment = dsnsForSegment;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
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
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

}
