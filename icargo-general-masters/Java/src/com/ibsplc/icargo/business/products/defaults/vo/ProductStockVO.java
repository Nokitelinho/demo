/*
 * ProductFilterVO.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;
import java.util.Collection;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * * @author A-1358
 *
 */
public class ProductStockVO extends AbstractVO implements Serializable{

private String companyCode;

private String productCode;

private String productName;

private String description;

private String detailedDescription;

private LocalDate startDate;

private LocalDate endDate;

private Collection <String> transportMode;
private Collection <String> scc;
private Collection <String> priority;

private String status;

private String documentType;

private String documentSubType;

private String transportModeCode;

private String sccCode;

private String priorityCode;

public String getCompanyCode() {
	return companyCode;
}

public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getDetailedDescription() {
	return detailedDescription;
}

public void setDetailedDescription(String detailedDescription) {
	this.detailedDescription = detailedDescription;
}

public String getDocumentSubType() {
	return documentSubType;
}

public void setDocumentSubType(String documentSubType) {
	this.documentSubType = documentSubType;
}

public String getDocumentType() {
	return documentType;
}

public void setDocumentType(String documentType) {
	this.documentType = documentType;
}

public LocalDate getEndDate() {
	return endDate;
}

public void setEndDate(LocalDate endDate) {
	this.endDate = endDate;
}

public Collection<String> getPriority() {
	return priority;
}

public void setPriority(Collection<String> priority) {
	this.priority = priority;
}

public String getProductCode() {
	return productCode;
}

public void setProductCode(String productCode) {
	this.productCode = productCode;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}

public Collection<String> getScc() {
	return scc;
}

public void setScc(Collection<String> scc) {
	this.scc = scc;
}

public LocalDate getStartDate() {
	return startDate;
}

public void setStartDate(LocalDate startDate) {
	this.startDate = startDate;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Collection<String> getTransportMode() {
	return transportMode;
}

public void setTransportMode(Collection<String> transportMode) {
	this.transportMode = transportMode;
}

public String getPriorityCode() {
	return priorityCode;
}

public void setPriorityCode(String priorityCode) {
	this.priorityCode = priorityCode;
}

public String getSccCode() {
	return sccCode;
}

public void setSccCode(String sccCode) {
	this.sccCode = sccCode;
}

public String getTransportModeCode() {
	return transportModeCode;
}

public void setTransportModeCode(String transportModeCode) {
	this.transportModeCode = transportModeCode;
}

}
