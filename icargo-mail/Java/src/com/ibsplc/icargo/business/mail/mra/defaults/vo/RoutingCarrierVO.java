package com.ibsplc.icargo.business.mail.mra.defaults.vo;

/**
 * RoutingCarrierVO
 * 
 * @author A-4452
 * 
 */


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class RoutingCarrierVO extends AbstractVO {

	
	private String companyCode;
	private String originCity;
	private String destCity;
	private String carrier;
	private String ownSectorFrm;
	private String ownSectorTo;
	private String oalSectorFrm;
	private String oalSectorTo;
	private String operationFlag;
	private int sequenceNumber;
	private LocalDate validFrom;
	private LocalDate validTo;
	private int carrierIdr;
	
	/**
	 * @return the carrierIdr
	 */
	public int getCarrierIdr() {
		return carrierIdr;
	}
	/**
	 * @param carrierIdr the carrierIdr to set
	 */
	public void setCarrierIdr(int carrierIdr) {
		this.carrierIdr = carrierIdr;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getOwnSectorFrm() {
		return ownSectorFrm;
	}
	public void setOwnSectorFrm(String ownSectorFrm) {
		this.ownSectorFrm = ownSectorFrm;
	}
	public String getOwnSectorTo() {
		return ownSectorTo;
	}
	public void setOwnSectorTo(String ownSectorTo) {
		this.ownSectorTo = ownSectorTo;
	}
	public String getOalSectorFrm() {
		return oalSectorFrm;
	}
	public void setOalSectorFrm(String oalSectorFrm) {
		this.oalSectorFrm = oalSectorFrm;
	}
	public String getOalSectorTo() {
		return oalSectorTo;
	}
	public void setOalSectorTo(String oalSectorTo) {
		this.oalSectorTo = oalSectorTo;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public LocalDate getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}
	public LocalDate getValidTo() {
		return validTo;
	}
	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}
	
	
	
	
}
