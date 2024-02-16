package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;

public class Preadvice {
	  private String companyCode;
	  private int carrierId;
	  private String flightNumber;
	  private long flightSequenceNumber;
	  private int legSerialNumber;
	  private LocalDate flightDate;
	  private String carrierCode;
	  private int totalBags;
	  private Measure totalWeight;
	  private List<PreadviceDetails> preadviceDetails;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getTotalBags() {
		return totalBags;
	}
	public void setTotalBags(int totalBags) {
		this.totalBags = totalBags;
	}
	public Measure getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	public List<PreadviceDetails> getPreadviceDetails() {
		return preadviceDetails;
	}
	public void setPreadviceDetails(List<PreadviceDetails> preadviceDetails) {
		this.preadviceDetails = preadviceDetails;
	}
	  

}
