package com.ibsplc.icargo.business.mail.mra.defaults.vo;

/**
 * RoutingCarrierFilterVO
 * 
 * @author A-4452
 * 
 */
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class RoutingCarrierFilterVO extends AbstractVO {
	private String companyCode;
	private String originCity;
	private String destCity;
	private String carrier;
	private LocalDate validFromDate;
	private LocalDate validFromTo;
	private String ownSectorFrm;
	private String ownSectorTo;
	private String oalSectorFrm;
	private String oalSectorTo;
	private int sequenceNumber;
	
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
	public LocalDate getValidFromDate() {
		return validFromDate;
	}
	public void setValidFromDate(LocalDate validFromDate) {
		this.validFromDate = validFromDate;
	}
	public LocalDate getValidFromTo() {
		return validFromTo;
	}
	public void setValidFromTo(LocalDate validFromTo) {
		this.validFromTo = validFromTo;
	}
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	
	
}
