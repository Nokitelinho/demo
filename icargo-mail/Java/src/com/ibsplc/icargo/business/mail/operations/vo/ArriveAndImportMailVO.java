package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-8952
 * 
 */

public class ArriveAndImportMailVO extends AbstractVO {

	private String companyCode;
	private int offset;
	private boolean isArrivalAndDeliveryMarkedTogether;
	
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
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the isArrivalAndDeliveryMarkedTogether
	 */
	public boolean isArrivalAndDeliveryMarkedTogether() {
		return isArrivalAndDeliveryMarkedTogether;
	}
	/**
	 * @param isArrivalAndDeliveryMarkedTogether the isArrivalAndDeliveryMarkedTogether to set
	 */
	public void setArrivalAndDeliveryMarkedTogether(boolean isArrivalAndDeliveryMarkedTogether) {
		this.isArrivalAndDeliveryMarkedTogether = isArrivalAndDeliveryMarkedTogether;
	}
	
}
