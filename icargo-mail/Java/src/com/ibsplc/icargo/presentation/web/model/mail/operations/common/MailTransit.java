package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.io.Serializable;

public class MailTransit implements Serializable{
	
	private String carrierCode;
	private String mailBagDestination;
	private String totalNoImportBags;
	private String totalWeightImportBags;
	private String countOfExportNonAssigned;
	private String totalWeightOfExportNotAssigned;
	private String usedCapacityofMailbag;
	private Double allotedULDPostnLDC=0.0;
	private Double allotedULDPostnLDP=0.0;
	private Double allotedULDPostnMDP=0.0;
	private Double allotedWeight=0.0;
	private Double availableFreeSaleCapacity=0.0;
	private Double freeSaleULDPostnLDP=0.0;
	private Double freeSaleULDPostnLDC=0.0;
	private Double freeSaleULDPostnMDP=0.0;
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getMailBagDestination() {
		return mailBagDestination;
	}
	public void setMailBagDestination(String mailBagDestination) {
		this.mailBagDestination = mailBagDestination;
	}
	public String getTotalNoImportBags() {
		return totalNoImportBags;
	}
	public void setTotalNoImportBags(String totalNoImportBags) {
		this.totalNoImportBags = totalNoImportBags;
	}
	public String getTotalWeightImportBags() {
		return totalWeightImportBags;
	}
	public void setTotalWeightImportBags(String totalWeightImportBags) {
		this.totalWeightImportBags = totalWeightImportBags;
	}
	public String getCountOfExportNonAssigned() {
		return countOfExportNonAssigned;
	}
	public void setCountOfExportNonAssigned(String countOfExportNonAssigned) {
		this.countOfExportNonAssigned = countOfExportNonAssigned;
	}
	public String getTotalWeightOfExportNotAssigned() {
		return totalWeightOfExportNotAssigned;
	}
	public void setTotalWeightOfExportNotAssigned(String totalWeightOfExportNotAssigned) {
		this.totalWeightOfExportNotAssigned = totalWeightOfExportNotAssigned;
	}
	public String getUsedCapacityofMailbag() {
		return usedCapacityofMailbag;
	}
	public void setUsedCapacityofMailbag(String usedCapacityofMailbag) {
		this.usedCapacityofMailbag = usedCapacityofMailbag;
	}
	public Double getAllotedULDPostnLDC() {
		return allotedULDPostnLDC;
	}
	public void setAllotedULDPostnLDC(Double allotedULDPostnLDC) {
		this.allotedULDPostnLDC = allotedULDPostnLDC;
	}
	public Double getAllotedULDPostnLDP() {
		return allotedULDPostnLDP;
	}
	public void setAllotedULDPostnLDP(Double allotedULDPostnLDP) {
		this.allotedULDPostnLDP = allotedULDPostnLDP;
	}
	public Double getAllotedULDPostnMDP() {
		return allotedULDPostnMDP;
	}
	public void setAllotedULDPostnMDP(Double allotedULDPostnMDP) {
		this.allotedULDPostnMDP = allotedULDPostnMDP;
	}
	public Double getAllotedWeight() {
		return allotedWeight;
	}
	public void setAllotedWeight(Double allotedWeight) {
		this.allotedWeight = allotedWeight;
	}
	public Double getAvailableFreeSaleCapacity() {
		return availableFreeSaleCapacity;
	}
	public void setAvailableFreeSaleCapacity(Double availableFreeSaleCapacity) {
		this.availableFreeSaleCapacity = availableFreeSaleCapacity;
	}
	public Double getFreeSaleULDPostnLDP() {
		return freeSaleULDPostnLDP;
	}
	public void setFreeSaleULDPostnLDP(Double freeSaleULDPostnLDP) {
		this.freeSaleULDPostnLDP = freeSaleULDPostnLDP;
	}
	public Double getFreeSaleULDPostnLDC() {
		return freeSaleULDPostnLDC;
	}
	public void setFreeSaleULDPostnLDC(Double freeSaleULDPostnLDC) {
		this.freeSaleULDPostnLDC = freeSaleULDPostnLDC;
	}
	public Double getFreeSaleULDPostnMDP() {
		return freeSaleULDPostnMDP;
	}
	public void setFreeSaleULDPostnMDP(Double freeSaleULDPostnMDP) {
		this.freeSaleULDPostnMDP = freeSaleULDPostnMDP;
	}

}
