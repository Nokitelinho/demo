/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO.java
 *
 *	Created by	:	Prashant Behera
 *	Created on	:	Jun 29, 2022
 *
 *  Copyright 2022 Copyright  IBS Software  (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright  IBS Software  (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Prashant Behera	:	Jun 29, 2022	:	Draft
 */
public class ProductSelectionRuleMasterVO extends AbstractVO implements Serializable{
	
	/** The serial number. */
	private int serialNumber;
	
	/** The company code. */
	private String companyCode;
	
	/** The source code. */
	private String sourceCode;
	
	/** The commodity code. */
	private String commodityCode;
	
	/** The scc code. */
	private String sccCode;
	
	/** The scc group code. */
	private String sccGroupCode;
	
	/** The international domestic flag. */
	private String internationalDomesticFlag;
	
	/** The agent code. */
	private String agentCode;
	
	/** The agent group code. */
	private String agentGroupCode;
	
	/** The origin country code. */
	private String originCountryCode;
	
	/** The destination country code. */
	private String destinationCountryCode;
	
	/** The product code. */
	private String productCode;

	/**
	 * 	Getter for serialNumber 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 *  @param serialNumber the serialNumber to set
	 * 	Setter for serialNumber 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 	Getter for companyCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 *  @param companyCode the companyCode to set
	 * 	Setter for companyCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 	Getter for sourceCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getSourceCode() {
		return sourceCode;
	}

	/**
	 *  @param sourceCode the sourceCode to set
	 * 	Setter for sourceCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	/**
	 * 	Getter for commodityCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getCommodityCode() {
		return commodityCode;
	}

	/**
	 *  @param commodityCode the commodityCode to set
	 * 	Setter for commodityCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	/**
	 * 	Getter for sccCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getSccCode() {
		return sccCode;
	}

	/**
	 *  @param sccCode the sccCode to set
	 * 	Setter for sccCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setSccCode(String sccCode) {
		this.sccCode = sccCode;
	}

	/**
	 * 	Getter for sccGroupCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getSccGroupCode() {
		return sccGroupCode;
	}

	/**
	 *  @param sccGroupCode the sccGroupCode to set
	 * 	Setter for sccGroupCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setSccGroupCode(String sccGroupCode) {
		this.sccGroupCode = sccGroupCode;
	}

	/**
	 * 	Getter for internationalDomesticFlag 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getInternationalDomesticFlag() {
		return internationalDomesticFlag;
	}

	/**
	 *  @param internationalDomesticFlag the internationalDomesticFlag to set
	 * 	Setter for internationalDomesticFlag 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setInternationalDomesticFlag(String internationalDomesticFlag) {
		this.internationalDomesticFlag = internationalDomesticFlag;
	}

	/**
	 * 	Getter for agentCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 *  @param agentCode the agentCode to set
	 * 	Setter for agentCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 	Getter for agentGroupCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getAgentGroupCode() {
		return agentGroupCode;
	}

	/**
	 *  @param agentGroupCode the agentGroupCode to set
	 * 	Setter for agentGroupCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setAgentGroupCode(String agentGroupCode) {
		this.agentGroupCode = agentGroupCode;
	}

	/**
	 * 	Getter for originCountryCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getOriginCountryCode() {
		return originCountryCode;
	}

	/**
	 *  @param originCountryCode the originCountryCode to set
	 * 	Setter for originCountryCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setOriginCountryCode(String originCountryCode) {
		this.originCountryCode = originCountryCode;
	}

	/**
	 * 	Getter for destinationCountryCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public String getDestinationCountryCode() {
		return destinationCountryCode;
	}

	/**
	 *  @param destinationCountryCode the destinationCountryCode to set
	 * 	Setter for destinationCountryCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setDestinationCountryCode(String destinationCountryCode) {
		this.destinationCountryCode = destinationCountryCode;
	}

	/**
	 * 	Getter for productCode 
	 *	Added by : A-8146 on Jun 29, 2022
	 * 	Used for :
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 *  @param productCode the productCode to set
	 * 	Setter for productCode 
	 *	Added by : A-8146 on Jun 29, 2022
	 * 	Used for :
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	

}
