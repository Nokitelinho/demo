/**
 * 
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class ProductFeedbackFilterVO extends AbstractVO implements Serializable {

	private String companyCode;
	
	private String productCode;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private int totalRecords;//Added by A-5201 as part for the ICRD-22065 
	
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
	 * @return Returns the endDate.
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

	/**
	 * @return Returns the startDate.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	//Added by A-5201 as part from the ICRD-22065 starts
	/**
	 * @param setTotalRecords to set total number of records
	*/
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
 
    /**
	 * @return the total number of records
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	//Added by A-5201 as part from the ICRD-22065 end
	
}
