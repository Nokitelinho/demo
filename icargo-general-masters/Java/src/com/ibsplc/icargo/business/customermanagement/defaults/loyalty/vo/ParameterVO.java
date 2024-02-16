package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/***
 * 
 * @author a-1496
 *
 */
public class ParameterVO extends AbstractVO {

	/**
	 * Parameter Distance
	 */
	public static final String DISTANCE = "DIS";
	/**
	 * Parameter Revenue
	 */
	public static final String REVENUE = "REV";
	/**
	 * Parameter Special Cargo Codes
	 */
	public static final String SCC = "SCC";
	/**
	 * Parameter Carrier
	 */
	public static final String CAR = "CAR";
	/**
	 * Parameter Agent
	 */
	public static final String AGT = "AGT";

	private String parameter;
	private Collection<String> parameterValues;
	//Applicable for Distance/Revenue
	private String unit;


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
	 * @return Returns the parameterValues.
	 */
	public Collection<String> getParameterValues() {
		return parameterValues;
	}
	/**
	 * @param parameterValues The parameterValues to set.
	 */
	public void setParameterValues(Collection<String> parameterValues) {
		this.parameterValues = parameterValues;
	}
	/**
	 * @return Returns the unit.
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit The unit to set.
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
