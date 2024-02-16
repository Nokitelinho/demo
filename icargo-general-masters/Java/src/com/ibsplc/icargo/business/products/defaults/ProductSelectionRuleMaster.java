/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductSelectionRuleMaster.java
 *
 *	Created by	:	Prashant Behera
 *	Created on	:	Jun 29, 2022
 *
 *  Copyright 2022 Copyright  IBS Software  (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright  IBS Software  (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParametersVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.LogonAttributesVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductSelectionRuleMaster.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Prashant Behera	:	Jun 29, 2022	:	Draft
 */

@Entity
@Table(name="PRDSELRULMST")
public class ProductSelectionRuleMaster {
	
	
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
	
	ProductSelectionRuleMaster productSelectionRule; 
	
	/**
	 * 	Getter for serialNumber 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	@Id
	@Column(name="SERNUM")
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
	@Column(name="CMPCOD")
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
	@Column(name="SRCCOD")
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
	@Column(name="COMCOD")
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
	@Column(name="SCCCOD")
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
	@Column(name="SCCGRPCOD")
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
	@Column(name="INTDOMFLG")
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
	@Column(name="AGTCOD")
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
	@Column(name="AGTGRPCOD")
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
	@Column(name="ORGCNTCOD")
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
	@Column(name="DSTCNTCOD")
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
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	@Column(name="PRDCOD")
	public String getProductCode() {
		return productCode;
	}

	/**
	 *  @param productCode the productCode to set
	 * 	Setter for productCode 
	 *	Added by : Prashant Behera on Jun 29, 2022
	 * 	Used for :
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 
	 *	Constructor	: 	
	 *	Created by	:	Prashant Behera
	 *	Created on	:	Jun 29, 2022
	 */
	public ProductSelectionRuleMaster() {
		
	}
	
	/**
	 * 
	 *	Constructor	: 	@param productSelectionRuleMasterVO
	 *	Created by	:	Prashant Behera
	 *	Created on	:	Jun 29, 2022
	 * @throws SystemException 
	 */
	public ProductSelectionRuleMaster(ProductSelectionRuleMasterVO productSelectionRuleMasterVO ) throws SystemException {
		populateAttributes(productSelectionRuleMasterVO);
		persistEntity();
	}

	private void persistEntity() throws SystemException {
		productSelectionRule = this;
		try {
			PersistenceController.getEntityManager().persist(productSelectionRule);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(),e);
		}
	}

	private void populateAttributes(ProductSelectionRuleMasterVO productSelectionRuleMasterVO) {
		setSerialNumber(productSelectionRuleMasterVO.getSerialNumber());
		setCompanyCode(productSelectionRuleMasterVO.getCompanyCode());
		setCommodityCode(productSelectionRuleMasterVO.getCommodityCode());
		setSourceCode(productSelectionRuleMasterVO.getSourceCode());
		setSccCode(productSelectionRuleMasterVO.getSccCode());
		setSccGroupCode(productSelectionRuleMasterVO.getSccGroupCode());
		setAgentCode(productSelectionRuleMasterVO.getAgentCode());
		setAgentGroupCode(productSelectionRuleMasterVO.getAgentGroupCode());
		setInternationalDomesticFlag(productSelectionRuleMasterVO.getInternationalDomesticFlag());
		setOriginCountryCode(productSelectionRuleMasterVO.getOriginCountryCode());
		setDestinationCountryCode(productSelectionRuleMasterVO.getDestinationCountryCode());
		setProductCode(productSelectionRuleMasterVO.getProductCode());
	}
	
	/**
	 * 
	 * 	Method		:	ProductSelectionRuleMaster.clearProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void clearProductSelectionRuleMaster() throws SystemException{
		LogonAttributesVO logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		constructDAO().clearProductSelectionRuleMaster(logonAttributes.getCompanyCode());
	}
	
	/**
	 * 
	 * 	Method		:	ProductSelectionRuleMaster.listProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 * @param companyCode 
	 */
	public static Collection<ProductSelectionRuleMasterVO> listProductSelectionRuleMaster(String companyCode) throws SystemException{
		return constructDAO().listProductSelectionRuleMaster(companyCode);
	}
	/**
	 * 
	 * 	Method		:	ProductSelectionRuleMaster.constructDAO
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ProductDefaultsSqlDAO
	 */
	private static ProductDefaultsDAO constructDAO() throws SystemException {
		ProductDefaultsDAO productDefaultsDAO = null;
		try {
			EntityManager entityManager = PersistenceController.getEntityManager();
			productDefaultsDAO = (ProductDefaultsDAO)ProductDefaultsDAO.class
					.cast(entityManager.getQueryDAO("products.defaults"));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(),persistenceException);
		}
		return productDefaultsDAO;
	}
	
	/**
	 * 
	 * 	Method		:	ProductSelectionRuleMaster.findProductsForBookingFromProductSelectionRule
	 *	Added by 	:	A-8146 on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param filterConditions
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 * @param generalParametersVOs 
	 */
	public static Collection<ProductSelectionRuleMasterVO> findProductsForBookingFromProductSelectionRule(
			String companyCode, Map<String, String> filterConditions, Collection<GeneralParametersVO> generalParametersVOs) throws SystemException {
		return constructDAO().findProductsForBookingFromProductSelectionRule(companyCode,filterConditions,generalParametersVOs);
	}
}
