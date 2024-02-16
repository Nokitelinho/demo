/*
 * DocumentValidationVO.java Created on Jul 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
/**
 *
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1954
 *
 */
public class DocumentValidationVO extends AbstractVO {

	/**
	 *
	 */
	public static final String DOC_TYP_AWB = "AWB";

	/**
	 *
	 */
	public static final String DOC_TYP_COURIER = "COU";

	/**
	 *
	 */
	public static final String DOC_TYP_EBT = "EBT";

	/*
	 *
	 * Status to set, which identifies whether the document
	 * is in-stock or reserved
	 */
	public static final String STATUS_RESERVED = "RESERVED";

	public static final String STATUS_INSTOCK = "INSTOCK";
	
	
	public static final String DOC_TYP_INV = "INVOICE";
	
	public static final String STOCKHOLDER_CODE = "HQ";


	private String companyCode;

	protected String documentType;

	private String documentSubType;

	protected String documentNumber;

	protected String stockHolderCode;
	
	private String stockHolderType;

	private String status;

	private Collection<ProductStockVO> productStockVOs;


	/**
	 * @return Returns the productStockVOs.
	 */
	public Collection<ProductStockVO> getProductStockVOs() {
		return productStockVOs;
	}

	/**
	 * @param productStockVOs The productStockVOs to set.
	 */
	public void setProductStockVOs(Collection<ProductStockVO> productStockVOs) {
		this.productStockVOs = productStockVOs;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the documentNumber.
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber
	 *            The documentNumber to set.
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @param documentSubType
	 *            The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType
	 *            The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode
	 *            The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}

	/**
	 * @param stockHolderType The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	
	
}
