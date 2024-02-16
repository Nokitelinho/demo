package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author 
 *
 */

public class RecordULDMovementMicroVO extends AbstractVO{

	private String companyCode;
    private String uldNumber;
    private ULDMovementMicroVO[] ULDMovementMicroVOs;
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
	 * @return Returns the uLDMovementMicroVOs.
	 */
	public ULDMovementMicroVO[] getULDMovementMicroVOs() {
		return ULDMovementMicroVOs;
	}
	/**
	 * @param movementMicroVOs The uLDMovementMicroVOs to set.
	 */
	public void setULDMovementMicroVOs(ULDMovementMicroVO[] ULDMovementMicroVOs) {
		this.ULDMovementMicroVOs = ULDMovementMicroVOs;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

}
