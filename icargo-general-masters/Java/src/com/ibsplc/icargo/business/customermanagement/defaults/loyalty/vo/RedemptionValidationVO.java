/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-1883
 *
 */
public class RedemptionValidationVO extends AbstractVO {

	private String companyCode;
	private String customerCode;
	private LocalDate currentDate;
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
	 * @return Returns the currentDate.
	 */
	public LocalDate getCurrentDate() {
		return currentDate;
	}
	/**
	 * @param currentDate The currentDate to set.
	 */
	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}
	/**
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	
}
