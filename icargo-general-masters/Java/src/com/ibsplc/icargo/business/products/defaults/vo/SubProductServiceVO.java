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
public class SubProductServiceVO extends AbstractVO implements Serializable{
	 
	private static final long serialVersionUID = 3258624841431913052L;

	/**
     * Possible values are 'I-Insert','U-Update' and 'D-Delete'
     */
    private String operationFlag;

    private String serviceCode;

    private String serviceDescription;

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the serviceCode.
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param serviceCode The serviceCode to set.
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return Returns the serviceDescription.
	 */
	public String getServiceDescription() {
		return serviceDescription;
	}

	/**
	 * @param serviceDescription The serviceDescription to set.
	 */
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}


}
