/**
 * 
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class StationAvailabilityFilterVO extends AbstractVO implements Serializable {

	private String companyCode;
	private String productCode;
	private String commodity;
	private String origin;
	private String destination;
	/**
	 * @return Returns the commodity.
	 */
	public String getCommodity() {
		return commodity;
	}
	/**
	 * @param commodity The commodity to set.
	 */
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return Returns the productCode.
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode The productCode to set.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
