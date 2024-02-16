package com.ibsplc.icargo.business.xaddons.ey.uld.defaults;

import com.ibsplc.icargo.business.msgbroker.message.vo.xaddons.ey.uld.defaults.CPMBulkFlightDetailsVO;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.ey.uld.defaults.EYCPMMessageDetailPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7765	:	22-Jul-2020	    :	Draft
 */

@Embeddable
public class EYCPMMessageDetailPK implements Serializable {

	private String companyCode;
	private String compartmentSection;
	private String content;
	private int flightCarrierId;
	private String flightNumber;
	private int flightSequenceNumber;
	private int legSerialNumber;
	private String stationCode;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompartmentSection() {
		return compartmentSection;
	}

	public void setCompartmentSection(String compartmentSection) {
		this.compartmentSection = compartmentSection;
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public int getFlightCarrierId() {
		return flightCarrierId;
	}

	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public EYCPMMessageDetailPK() {
	}

	public EYCPMMessageDetailPK(CPMBulkFlightDetailsVO cpmBulkFlightDetailsVO) {
		this.companyCode = cpmBulkFlightDetailsVO.getCompanyCode();
		this.stationCode = cpmBulkFlightDetailsVO.getStationCode();
		this.legSerialNumber = cpmBulkFlightDetailsVO.getLegSerialNumber();
		this.flightSequenceNumber = cpmBulkFlightDetailsVO.getFlightSequenceNumber();
		this.flightNumber = cpmBulkFlightDetailsVO.getFlightNumber();
		this.flightCarrierId = cpmBulkFlightDetailsVO.getFlightCarrierID();
		this.compartmentSection = cpmBulkFlightDetailsVO.getCompartmentSection();
		this.content = cpmBulkFlightDetailsVO.getBulkContent();
	}
}
