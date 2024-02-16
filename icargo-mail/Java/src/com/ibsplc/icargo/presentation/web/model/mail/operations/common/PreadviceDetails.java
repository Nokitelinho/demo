package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.unit.Measure;

public class PreadviceDetails {
	  private String originExchangeOffice;
	  private String destinationExchangeOffice;
	  private int totalbags;
	  private Measure totalWeight;
	  private String uldNumbr;
	  private String mailCategory;
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}
	public int getTotalbags() {
		return totalbags;
	}
	public void setTotalbags(int totalbags) {
		this.totalbags = totalbags;
	}
	public Measure getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getUldNumbr() {
		return uldNumbr;
	}
	public void setUldNumbr(String uldNumbr) {
		this.uldNumbr = uldNumbr;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	  

}
