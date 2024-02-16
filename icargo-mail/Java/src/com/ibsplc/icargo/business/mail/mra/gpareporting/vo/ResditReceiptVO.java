/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO.java
 *
 *	Created by	:	A-7540
 *	Created on	:	08-May-2019
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;


import java.util.Date;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-7540
 *
 */
public class ResditReceiptVO extends AbstractVO{

  private String mailbagId;
  private String carrierCode ;
  private String scanTimeUTC ;
  private String scannedModeCode;
  private String scannedport ;
  private String validForPayIndicator;
  private String exceptionCode;
/**
 * @return the mailbagId
 */
public String getMailbagId() {
	return mailbagId;
}
/**
 * @param mailbagId the mailbagId to set
 */
public void setMailbagId(String mailbagId) {
	this.mailbagId = mailbagId;
}
/**
 * @return the carrierCode
 */
public String getCarrierCode() {
	return carrierCode;
}
/**
 * @param carrierCode the carrierCode to set
 */
public void setCarrierCode(String carrierCode) {
	this.carrierCode = carrierCode;
}

/**
 * @return the scanTimeUTC
 */
public String getScanTimeUTC() {
	return scanTimeUTC;
}
/**
 * @param scanTimeUTC the scanTimeUTC to set
 */
public void setScanTimeUTC(String scanTimeUTC) {
	this.scanTimeUTC = scanTimeUTC;
}
/**
 * @return the scannedport
 */
public String getScannedport() {
	return scannedport;
}
/**
 * @param scannedport the scannedport to set
 */
public void setScannedport(String scannedport) {
	this.scannedport = scannedport;
}
/**
 * @return the validForPayIndicator
 */
public String getValidForPayIndicator() {
	return validForPayIndicator;
}
/**
 * @param validForPayIndicator the validForPayIndicator to set
 */
public void setValidForPayIndicator(String validForPayIndicator) {
	this.validForPayIndicator = validForPayIndicator;
}
/**
 * @return the exceptionCode
 */
public String getExceptionCode() {
	return exceptionCode;
}
/**
 * @param exceptionCode the exceptionCode to set
 */
public void setExceptionCode(String exceptionCode) {
	this.exceptionCode = exceptionCode;
}
/**
 * @return the scannedModeCode
 */
public String getScannedModeCode() {
	return scannedModeCode;
}
/**
 * @param scannedModeCode the scannedModeCode to set
 */
public void setScannedModeCode(String scannedModeCode) {
	this.scannedModeCode = scannedModeCode;
}
public void setEpochConvertedDate(Date date) {
	// TODO Auto-generated method stub
	
}



}
