package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class RdtMasterFilterVO extends AbstractVO {

	private String gpaCode;
	private String airportCodes; 
	private String companyCode;
	private String mailType;
	private String mailClass;
	private String originAirportCode;
	
	public String getMailClass() {
		return mailClass;
	}
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the airportCodes
	 */
	public String getAirportCodes() {
		return airportCodes;
	}
	/**
	 * @param airportCodes the airportCodes to set
	 */
	public void setAirportCodes(String airportCodes) {
		this.airportCodes = airportCodes;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for mailType 
	 *	Added by : A-6991 on 19-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public String getMailType() {
		return mailType;
	}
	/**
	 *  @param mailType the mailType to set
	 * 	Setter for mailType 
	 *	Added by : A-6991 on 19-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}  
	public String getOriginAirportCode() {
		return originAirportCode;
	}
	public void setOriginAirportCode(String originAirportCode) {
		this.originAirportCode = originAirportCode;
	}
}
