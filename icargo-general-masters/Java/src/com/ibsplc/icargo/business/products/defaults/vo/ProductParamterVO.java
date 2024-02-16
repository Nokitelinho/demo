/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO.java
 *
 *	Created by	:	A-7740
 *	Created on	:	03-Oct-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;
import java.io.Serializable;
import java.util.Collection;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7740	:	03-Oct-2018	:	Draft
 */
public class ProductParamterVO  extends AbstractVO implements Serializable {
	private String companyCode;
	private String productCode;
	private String parameterCode;
	private String parameterDescription;
	private String possibleValues;
	private String parameterValue;
	private String operationalFlag;
	private String displayType;
	private Collection<String> lov;
	/**
	 * 	Getter for companyCode 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * 	Getter for productCode 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 *  @param productCode the productCode to set
	 * 	Setter for productCode 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * 	Getter for parameterCode 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public String getParameterCode() {
		return parameterCode;
	}
	/**
	 *  @param parameterCode the parameterCode to set
	 * 	Setter for parameterCode 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	/**
	 * 	Getter for parameterDescription 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public String getParameterDescription() {
		return parameterDescription;
	}
	/**
	 *  @param parameterDescription the parameterDescription to set
	 * 	Setter for parameterDescription 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}
	/**
	 * 	Getter for possibleValues 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public String getPossibleValues() {
		return possibleValues;
	}
	/**
	 *  @param possibleValues the possibleValues to set
	 * 	Setter for possibleValues 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public void setPossibleValues(String possibleValues) {
		this.possibleValues = possibleValues;
	}
	/**
	 * 	Getter for parameterValue 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 *  @param parameterValue the parameterValue to set
	 * 	Setter for parameterValue 
	 *	Added by : A-7740 on 03-Oct-2018
	 * 	Used for :
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	/**
	 * 	Getter for operationalFlag 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public String getOperationalFlag() {
		return operationalFlag;
	}
	/**
	 *  @param operationalFlag the operationalFlag to set
	 * 	Setter for operationalFlag 
	 *	Added by : A-7740 on 04-Oct-2018
	 * 	Used for :
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}
	/**
	 * 	Getter for displayType 
	 *	Added by : A-7740 on 08-Oct-2018
	 * 	Used for :
	 */
	public String getDisplayType() {
		return displayType;
	}
	/**
	 *  @param displayType the displayType to set
	 * 	Setter for displayType 
	 *	Added by : A-7740 on 08-Oct-2018
	 * 	Used for :
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	/**
	 * 	Getter for lov 
	 *	Added by : A-7740 on 08-Oct-2018
	 * 	Used for :
	 */
	public Collection<String> getLov() {
		return lov;
	}
	/**
	 *  @param lov the lov to set
	 * 	Setter for lov 
	 *	Added by : A-7740 on 08-Oct-2018
	 * 	Used for :
	 */
	public void setLov(Collection<String> lov) {
		this.lov = lov;
	}	
}