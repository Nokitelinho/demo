/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class ParameterDescriptionVO extends AbstractVO {

	private String companyCode;
	private String parameter;
	private String defaultUnit;
	private String checkNumberField;
	private String parameterDesc;
	/**
	 * @return Returns the checkNumberField.
	 */
	public String getCheckNumberField() {
		return checkNumberField;
	}
	/**
	 * @param checkNumberField The checkNumberField to set.
	 */
	public void setCheckNumberField(String checkNumberField) {
		this.checkNumberField = checkNumberField;
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
	 * @return Returns the defaultUnit.
	 */
	public String getDefaultUnit() {
		return defaultUnit;
	}
	/**
	 * @param defaultUnit The defaultUnit to set.
	 */
	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
	/**
	 * @return Returns the parameter.
	 */
	public String getParameter() {
		return parameter;
	}
	/**
	 * @param parameter The parameter to set.
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	/**
	 * @return Returns the parameterDesc.
	 */
	public String getParameterDesc() {
		return parameterDesc;
	}
	/**
	 * @param parameterDesc The parameterDesc to set.
	 */
	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}
	
}
