/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class LoyaltyAttributeVO extends AbstractVO{
	/**
	 * Weight
	 */
	public static final String WEIGHT = "WGT";
	/**
	 * Volume
	 */
	public static final String VOLUME = "VOL";
	/**
	 * Distance
	 */
	public static final String DISTANCE = "DIS";
	/**
	 * Yield (same constant value for Revenue also) 
	 */
	public static final String YIELD = "REV";
	
	/**
	 * Attribute Weight
	 */
	public static final String WEIGHT_ATTRIBUTE = "Weight";
	/**
	 * Attribute Volume
	 */
	public static final String VOLUME_ATTRIBUTE = "Volume";
	/**
	 * Attribute Distance
	 */
	public static final String DISTANCE_ATTRIBUTE = "Distance";
	/**
	 * Attribute Revenue
	 */
	public static final String REVENUE_ATTRIBUTE = "Revenue";
	/**
	 * Attribute Yield
	 */
	public static final String YIELD_ATTRIBUTE = "Yield";

	private String attribute;
	private String unit;
	private String unitDescription;
	private double value;
	/**
	 * @return Returns the attribute.
	 */
	public String getAttribute() {
		return attribute;
	}
	/**
	 * @param attribute The attribute to set.
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
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
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return Returns the unitDescription.
	 */
	public String getUnitDescription() {
		return unitDescription;
	}
	/**
	 * @param unitDescription The unitDescription to set.
	 */
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}
}
