
 /*
 * MailBagAuditHistoryPK.java Created on Jun 27 2016 by A-5991
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */package com.ibsplc.icargo.business.mail.operations;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyTable;
import com.ibsplc.xibase.server.framework.persistence.keygen.TableKeyGenerator;


@KeyTable(table ="MALAUDHISKEY", keyColumn ="KEYTYP", valueColumn ="MAXSEQNUM")

@TableKeyGenerator(name ="ID_GEN", table ="MALAUDHISKEY", key ="HIS_SEQ_NUM")
@Embeddable
public class MailBagAuditHistoryPK  implements Serializable{

    /**
     * The companyCode
     */

   private String companyCode;
   /**
    * The Dsn
    */
//private String mailbagId;
/*   private String dsn;
   *//**
    * The originExchangeOffice
    *//*

   private String originExchangeOffice;
   *//**
    * The destinationExchangeOffice
    *//*

   private String destinationExchangeOffice;
   *//**
    * The mailSubClass
    *//*

   private String mailSubclass;

   *//**
    * The mailCategory
    *//*

   private String mailCategoryCode;

   private int year;*/

   private long serialNumber;
   private long historySequenceNumber;
   private long mailSequenceNumber;
/**
 * @return the companyCode
 */
   @KeyCondition(column="CMPCOD")
public String getCompanyCode() {
	return companyCode;
}
/**
 * @param companyCode the companyCode to set
 */
public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}
/**
 * @return the mailbagId
 *//*
@KeyCondition(column="MALIDR")
public String getMailbagId() {
	return mailbagId;
}

public void setMailbagId(String mailbagId) {
	this.mailbagId = mailbagId;
}*/
/*//**
 * @return the dsn
 *//*
@KeyCondition(column="DSN")
public String getDsn() {
	return dsn;
}
*//**
 * @param dsn the dsn to set
 *//*
public void setDsn(String dsn) {
	this.dsn = dsn;
}
*//**
 * @return the originExchangeOffice
 *//*
@KeyCondition(column="ORGEXGOFC")
public String getOriginExchangeOffice() {
	return originExchangeOffice;
}
*//**
 * @param originExchangeOffice the originExchangeOffice to set
 *//*
public void setOriginExchangeOffice(String originExchangeOffice) {
	this.originExchangeOffice = originExchangeOffice;
}
*//**
 * @return the destinationExchangeOffice
 *//*
@KeyCondition(column="DSTEXGOFC")
public String getDestinationExchangeOffice() {
	return destinationExchangeOffice;
}
*//**
 * @param destinationExchangeOffice the destinationExchangeOffice to set
 *//*

public void setDestinationExchangeOffice(String destinationExchangeOffice) {
	this.destinationExchangeOffice = destinationExchangeOffice;
}
*//**
 * @return the mailSubclass
 *//*
@KeyCondition(column="MALSUBCLS")
public String getMailSubclass() {
	return mailSubclass;
}
*//**
 * @param mailSubclass the mailSubclass to set
 *//*
public void setMailSubclass(String mailSubclass) {
	this.mailSubclass = mailSubclass;
}
*//**
 * @return the mailCategoryCode
 *//*
@KeyCondition(column="MALCTGCOD")
public String getMailCategoryCode() {
	return mailCategoryCode;
}
*//**
 * @param mailCategoryCode the mailCategoryCode to set
 *//*
public void setMailCategoryCode(String mailCategoryCode) {
	this.mailCategoryCode = mailCategoryCode;
}
*//**
 * @return the year
 *//*
@KeyCondition(column="YER")
public int getYear() {
	return year;
}
*//**
 * @param year the year to set
 *//*
public void setYear(int year) {
	this.year = year;
}*/
/**
 * @return the serialNumber
 */
@KeyCondition(column="SERNUM")
public long getSerialNumber() {
	return serialNumber;
}
/**
 * @param serialNumber the serialNumber to set
 */
public void setSerialNumber(long serialNumber) {
	this.serialNumber = serialNumber;
}
/**
 * @return the historySequenceNumber
 */
@Key(generator="ID_GEN",startAt="1")
public long getHistorySequenceNumber() {
	return historySequenceNumber;
}
/**
 * @param historySequenceNumber the historySequenceNumber to set
 */
public void setHistorySequenceNumber(long historySequenceNumber) {
	this.historySequenceNumber = historySequenceNumber;
}
/**
 * This method tests for equality of one instance of this class with
 * the other.
 * @param other - another object to test for equality
 * @return boolean - returns true if equal
 */
public boolean equals(Object other) {
	return (other != null) && ((hashCode() == other.hashCode()));
}

/**
 * This method generates the hashcode of an instance
 * @return int - returns the hashcode of the instance
 */
public int hashCode() {
	return new StringBuilder(companyCode)
	//.append(mailbagId)
/*	.append(dsn)
	.append(originExchangeOffice)
	.append(destinationExchangeOffice)
	.append(mailSubclass)
	.append(mailCategoryCode)
	.append(year)*/
	.append(serialNumber)
	.append(mailSequenceNumber)
	.append(historySequenceNumber)
	.toString().hashCode();
}

public MailBagAuditHistoryPK(MailBagAuditHistoryVO mailBagAuditHistoryVO){
	this.setCompanyCode(mailBagAuditHistoryVO.getCompanyCode());
	//this.setMailbagId(mailBagAuditHistoryVO.getMailbagId());
	this.setSerialNumber(mailBagAuditHistoryVO.getSerialNumber());
	this.setMailSequenceNumber(mailBagAuditHistoryVO.getMailSequenceNumber());
	/*this.setDestinationExchangeOffice(mailBagAuditHistoryVO.getDestinationExchangeOffice());
	this.setDsn(mailBagAuditHistoryVO.getDsn());
	this.setMailCategoryCode(mailBagAuditHistoryVO.getMailCategoryCode());
	this.setMailSubclass(mailBagAuditHistoryVO.getMailSubclass());
	this.setOriginExchangeOffice(mailBagAuditHistoryVO.getOriginExchangeOffice());
	this.setYear(mailBagAuditHistoryVO.getYear());
	*/
}
/**
 * @param mailSequenceNumber the mailSequenceNumber to set
 */
public void setMailSequenceNumber(long mailSequenceNumber) {
	this.mailSequenceNumber = mailSequenceNumber;
}
/**
 * @return the mailSequenceNumber
 */
@KeyCondition(column="MALSEQNUM")
public long getMailSequenceNumber() {
	return mailSequenceNumber;
}
}
