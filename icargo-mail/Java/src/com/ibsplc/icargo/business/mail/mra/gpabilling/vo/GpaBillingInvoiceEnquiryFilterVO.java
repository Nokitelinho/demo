/*
 * GpaBillingInvoiceEnquiryFilterVO.java Created on July 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3434
 *
 */
public class GpaBillingInvoiceEnquiryFilterVO extends AbstractVO {

private String companyCode;  
private String gpaCode; 
private String invoiceNumber;
private String gpaName;
/**
 * @return Returns the gpaName.
 */
public String getGpaName() {
	return gpaName;
}
/**
 * @param gpaName The gpaName to set.
 */
public void setGpaName(String gpaName) {
	this.gpaName = gpaName;
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
 * @return Returns the gpaCode.
 */
public String getGpaCode() {
	return gpaCode;
}
/**
 * @param gpaCode The gpaCode to set.
 */
public void setGpaCode(String gpaCode) {
	this.gpaCode = gpaCode;
}

/**
 * @return Returns the invoiceNumber.
 */
public String getInvoiceNumber() {
	return invoiceNumber;
}
/**
 * @param invoiceNumber The invoiceNumber to set.
 */
public void setInvoiceNumber(String invoiceNumber) {
	this.invoiceNumber = invoiceNumber;
}

}
