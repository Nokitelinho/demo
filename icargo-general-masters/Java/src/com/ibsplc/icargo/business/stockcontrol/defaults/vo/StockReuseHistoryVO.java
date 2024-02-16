package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class StockReuseHistoryVO extends AbstractVO {

	/**
	 * CMPCOD
	 * SERNUM
       MSTDOCNUM
       DOCOWRIDR
	   DUPNUM
       SEQNUM
       CNTCOD
       CAPDAT

	  */
	 

	/**
	 * @param companyCode
	 * @param serialNumber
	 * @param masterDocumentNumber
	 * @param documentOwnerId
	 * @param duplicateNumber
	 * @param sequenceNumber
	 * @param countryCode
	 * @param captureDate
	 */
	
	private String companyCode;
	private int serialNumber;
	private String masterDocumentNumber;
	private int documentOwnerId;
	private int duplicateNumber;
	private int  sequenceNumber;
	private String countryCode;
	private LocalDate captureDate;
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	public int getDocumentOwnerId() {
		return documentOwnerId;
	}
	public void setDocumentOwnerId(int documentOwnerId) {
		this.documentOwnerId = documentOwnerId;
	}
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public LocalDate getCaptureDate() {
		return captureDate;
	}
	public void setCaptureDate(LocalDate captureDate) {
		this.captureDate = captureDate;
	}
}
