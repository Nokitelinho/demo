package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * LovVo class 
 * @author A-1885
 *
 */
public class LovVO extends AbstractVO implements Serializable{
	
	private static final long serialVersionUID = -6463178633394333174L;
	private String code;
	private String description;
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
