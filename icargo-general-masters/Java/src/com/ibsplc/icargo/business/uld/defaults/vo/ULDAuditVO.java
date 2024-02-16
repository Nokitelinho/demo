/**
 * 
 */
package com.ibsplc.icargo.business.uld.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1883
 *
 */
public class ULDAuditVO extends AuditVO implements Serializable{

	private String uldNumber;
	/**
	 * @param argA
	 * @param argB
	 * @param argC
	 */
	public ULDAuditVO(String argA, String argB, String argC) {
		super(argA, argB, argC);
		// To be reviewed Auto-generated constructor stub
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
