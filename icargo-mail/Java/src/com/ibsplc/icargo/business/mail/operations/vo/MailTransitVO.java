package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailTransitVO extends AbstractVO{
	
	private String carrierCode;
	private String mailBagDestination;
	private String totalNoImportBags;
	private String totalWeightImportBags;
	private String countOfExportNonAssigned;
	private String totalWeightOfExportNotAssigned;
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
	
	
	
	
}
