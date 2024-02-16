/*
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd
 * Use is subject to license terms.
 *
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.client;

import com.ibsplc.icargo.framework.micro.serialization.AttributeInfo;
import com.ibsplc.icargo.framework.micro.serialization.Exportable;
/**
 * 
 * @author 
 *
 */

public class ULDTransactionDetailsMicroVO extends Exportable {

 
	private String companyCode; 
	private String uldNumber; 
	private int transactionRefNumber; 
	private String transactionType; 
	private String uldType; 
	private String transactionNature; 
	private String partyType; 
	private String fromPartyCode; 
	private String toPartyCode; 
	private int fromPartyIdentifier; 
	private int toPartyIdentifier; 
	private String transactionStationCode; 
	private String transationPeriod; 
	private String transactionDate; 
	private String transactionStatus; 
	private String transactionRemark; 
	private String capturedRefNumber; 
	private String damageStatus; 
	private String returnDate; 
	private String returnStationCode; 
	private double demurrageAmount; 
	private String currency; 
	private String invoiceStatus; 
	private double waived; 
	private double taxes; 
	private String returnedBy; 
	private double otherCharges; 
	private double total; 
	private String invoiceRefNumber; 
	private String paymentStatus; 
	private String returnRemark; 
	private String operationalFlag; 
	private String controlReceiptNumber; 
	private int numberMonths; 
	private int selectNumber; 
	private int repairSeqNumber; 
	private String strTxnDate; 
	private String strRetDate; 
	private String returnPartyCode; 
	private int returnPartyIdentifier; 
	private String agreementNumber; 
	private String toPartyName; 
	private String fromPartyName; 
	private int currOwnerCode; 
	private int operationalAirlineIdentifier; 
	private String txStationCode; 
	private String uldConditionCode; 
	private String lastUpdateUser;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[48] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "transactionRefNumber";
		 ATTRIBUTE_NAMES[3] = "transactionType";
		 ATTRIBUTE_NAMES[4] = "uldType";
		 ATTRIBUTE_NAMES[5] = "transactionNature";
		 ATTRIBUTE_NAMES[6] = "partyType";
		 ATTRIBUTE_NAMES[7] = "fromPartyCode";
		 ATTRIBUTE_NAMES[8] = "toPartyCode";
		 ATTRIBUTE_NAMES[9] = "fromPartyIdentifier";
		 ATTRIBUTE_NAMES[10] = "toPartyIdentifier";
		 ATTRIBUTE_NAMES[11] = "transactionStationCode";
		 ATTRIBUTE_NAMES[12] = "transationPeriod";
		 ATTRIBUTE_NAMES[13] = "transactionDate";
		 ATTRIBUTE_NAMES[14] = "transactionStatus";
		 ATTRIBUTE_NAMES[15] = "transactionRemark";
		 ATTRIBUTE_NAMES[16] = "capturedRefNumber";
		 ATTRIBUTE_NAMES[17] = "damageStatus";
		 ATTRIBUTE_NAMES[18] = "returnDate";
		 ATTRIBUTE_NAMES[19] = "returnStationCode";
		 ATTRIBUTE_NAMES[20] = "demurrageAmount";
		 ATTRIBUTE_NAMES[21] = "currency";
		 ATTRIBUTE_NAMES[22] = "invoiceStatus";
		 ATTRIBUTE_NAMES[23] = "waived";
		 ATTRIBUTE_NAMES[24] = "taxes";
		 ATTRIBUTE_NAMES[25] = "returnedBy";
		 ATTRIBUTE_NAMES[26] = "otherCharges";
		 ATTRIBUTE_NAMES[27] = "total";
		 ATTRIBUTE_NAMES[28] = "invoiceRefNumber";
		 ATTRIBUTE_NAMES[29] = "paymentStatus";
		 ATTRIBUTE_NAMES[30] = "returnRemark";
		 ATTRIBUTE_NAMES[31] = "operationalFlag";
		 ATTRIBUTE_NAMES[32] = "controlReceiptNumber";
		 ATTRIBUTE_NAMES[33] = "numberMonths";
		 ATTRIBUTE_NAMES[34] = "selectNumber";
		 ATTRIBUTE_NAMES[35] = "repairSeqNumber";
		 ATTRIBUTE_NAMES[36] = "strTxnDate";
		 ATTRIBUTE_NAMES[37] = "strRetDate";
		 ATTRIBUTE_NAMES[38] = "returnPartyCode";
		 ATTRIBUTE_NAMES[39] = "returnPartyIdentifier";
		 ATTRIBUTE_NAMES[40] = "agreementNumber";
		 ATTRIBUTE_NAMES[41] = "toPartyName";
		 ATTRIBUTE_NAMES[42] = "fromPartyName";
		 ATTRIBUTE_NAMES[43] = "currOwnerCode";
		 ATTRIBUTE_NAMES[44] = "operationalAirlineIdentifier";
		 ATTRIBUTE_NAMES[45] = "txStationCode";
		 ATTRIBUTE_NAMES[46] = "uldConditionCode";
		 ATTRIBUTE_NAMES[47] = "lastUpdateUser";
	}

	public Object getAttribute(int pos) {
		Object ret = null;
		switch (pos) {
		 case 0: 
				 {
		  ret = (String) companyCode;
		  break;
		 }
		 case 1: 
				 {
		  ret = (String) uldNumber;
		  break;
		 }
		 case 2: 
				 {
		  ret = new Integer(transactionRefNumber);
		  break;
		 }
		 case 3: 
				 {
		  ret = (String) transactionType;
		  break;
		 }
		 case 4: 
				 {
		  ret = (String) uldType;
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) transactionNature;
		  break;
		 }
		 case 6: 
				 {
		  ret = (String) partyType;
		  break;
		 }
		 case 7: 
				 {
		  ret = (String) fromPartyCode;
		  break;
		 }
		 case 8: 
				 {
		  ret = (String) toPartyCode;
		  break;
		 }
		 case 9: 
				 {
		  ret = new Integer(fromPartyIdentifier);
		  break;
		 }
		 case 10: 
				 {
		  ret = new Integer(toPartyIdentifier);
		  break;
		 }
		 case 11: 
				 {
		  ret = (String) transactionStationCode;
		  break;
		 }
		 case 12: 
				 {
		  ret = (String) transationPeriod;
		  break;
		 }
		 case 13: 
				 {
		  ret = (String) transactionDate;
		  break;
		 }
		 case 14: 
				 {
		  ret = (String) transactionStatus;
		  break;
		 }
		 case 15: 
				 {
		  ret = (String) transactionRemark;
		  break;
		 }
		 case 16: 
				 {
		  ret = (String) capturedRefNumber;
		  break;
		 }
		 case 17: 
				 {
		  ret = (String) damageStatus;
		  break;
		 }
		 case 18: 
				 {
		  ret = (String) returnDate;
		  break;
		 }
		 case 19: 
				 {
		  ret = (String) returnStationCode;
		  break;
		 }
		 case 20: 
				 {
		  ret = new Double(demurrageAmount);
		  break;
		 }
		 case 21: 
				 {
		  ret = (String) currency;
		  break;
		 }
		 case 22: 
				 {
		  ret = (String) invoiceStatus;
		  break;
		 }
		 case 23: 
				 {
		  ret = new Double(waived);
		  break;
		 }
		 case 24: 
				 {
		  ret = new Double(taxes);
		  break;
		 }
		 case 25: 
				 {
		  ret = (String) returnedBy;
		  break;
		 }
		 case 26: 
				 {
		  ret = new Double(otherCharges);
		  break;
		 }
		 case 27: 
				 {
		  ret = new Double(total);
		  break;
		 }
		 case 28: 
				 {
		  ret = (String) invoiceRefNumber;
		  break;
		 }
		 case 29: 
				 {
		  ret = (String) paymentStatus;
		  break;
		 }
		 case 30: 
				 {
		  ret = (String) returnRemark;
		  break;
		 }
		 case 31: 
				 {
		  ret = (String) operationalFlag;
		  break;
		 }
		 case 32: 
				 {
		  ret = (String) controlReceiptNumber;
		  break;
		 }
		 case 33: 
				 {
		  ret = new Integer(numberMonths);
		  break;
		 }
		 case 34: 
				 {
		  ret = new Integer(selectNumber);
		  break;
		 }
		 case 35: 
				 {
		  ret = new Integer(repairSeqNumber);
		  break;
		 }
		 case 36: 
				 {
		  ret = (String) strTxnDate;
		  break;
		 }
		 case 37: 
				 {
		  ret = (String) strRetDate;
		  break;
		 }
		 case 38: 
				 {
		  ret = (String) returnPartyCode;
		  break;
		 }
		 case 39: 
				 {
		  ret = new Integer(returnPartyIdentifier);
		  break;
		 }
		 case 40: 
				 {
		  ret = (String) agreementNumber;
		  break;
		 }
		 case 41: 
				 {
		  ret = (String) toPartyName;
		  break;
		 }
		 case 42: 
				 {
		  ret = (String) fromPartyName;
		  break;
		 }
		 case 43: 
				 {
		  ret = new Integer(currOwnerCode);
		  break;
		 }
		 case 44: 
				 {
		  ret = new Integer(operationalAirlineIdentifier);
		  break;
		 }
		 case 45: 
				 {
		  ret = (String) txStationCode;
		  break;
		 }
		 case 46: 
				 {
		  ret = (String) uldConditionCode;
		  break;
		 }
		 case 47: 
				 {
		  ret = (String) lastUpdateUser;
		  break;
		 }
		default: {
			ret = null;
		}
		}
	  return ret;
	}

	public void setAttribute(int pos, Object value) {
		if (value != null) {
			switch (pos) {
			 case 0: 
					 {
			  companyCode = (String) value;
			  break;
			 }
			 case 1: 
					 {
			  uldNumber = (String) value;
			  break;
			 }
			 case 2: 
					 {
			  transactionRefNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 3: 
					 {
			  transactionType = (String) value;
			  break;
			 }
			 case 4: 
					 {
			  uldType = (String) value;
			  break;
			 }
			 case 5: 
					 {
			  transactionNature = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  partyType = (String) value;
			  break;
			 }
			 case 7: 
					 {
			  fromPartyCode = (String) value;
			  break;
			 }
			 case 8: 
					 {
			  toPartyCode = (String) value;
			  break;
			 }
			 case 9: 
					 {
			  fromPartyIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 10: 
					 {
			  toPartyIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 11: 
					 {
			  transactionStationCode = (String) value;
			  break;
			 }
			 case 12: 
					 {
			  transationPeriod = (String) value;
			  break;
			 }
			 case 13: 
					 {
			  transactionDate = (String) value;
			  break;
			 }
			 case 14: 
					 {
			  transactionStatus = (String) value;
			  break;
			 }
			 case 15: 
					 {
			  transactionRemark = (String) value;
			  break;
			 }
			 case 16: 
					 {
			  capturedRefNumber = (String) value;
			  break;
			 }
			 case 17: 
					 {
			  damageStatus = (String) value;
			  break;
			 }
			 case 18: 
					 {
			  returnDate = (String) value;
			  break;
			 }
			 case 19: 
					 {
			  returnStationCode = (String) value;
			  break;
			 }
			 case 20: 
					 {
			  demurrageAmount = ((Double) value).doubleValue();
			  break;
			 }
			 case 21: 
					 {
			  currency = (String) value;
			  break;
			 }
			 case 22: 
					 {
			  invoiceStatus = (String) value;
			  break;
			 }
			 case 23: 
					 {
			  waived = ((Double) value).doubleValue();
			  break;
			 }
			 case 24: 
					 {
			  taxes = ((Double) value).doubleValue();
			  break;
			 }
			 case 25: 
					 {
			  returnedBy = (String) value;
			  break;
			 }
			 case 26: 
					 {
			  otherCharges = ((Double) value).doubleValue();
			  break;
			 }
			 case 27: 
					 {
			  total = ((Double) value).doubleValue();
			  break;
			 }
			 case 28: 
					 {
			  invoiceRefNumber = (String) value;
			  break;
			 }
			 case 29: 
					 {
			  paymentStatus = (String) value;
			  break;
			 }
			 case 30: 
					 {
			  returnRemark = (String) value;
			  break;
			 }
			 case 31: 
					 {
			  operationalFlag = (String) value;
			  break;
			 }
			 case 32: 
					 {
			  controlReceiptNumber = (String) value;
			  break;
			 }
			 case 33: 
					 {
			  numberMonths = ((Integer) value).intValue();
			  break;
			 }
			 case 34: 
					 {
			  selectNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 35: 
					 {
			  repairSeqNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 36: 
					 {
			  strTxnDate = (String) value;
			  break;
			 }
			 case 37: 
					 {
			  strRetDate = (String) value;
			  break;
			 }
			 case 38: 
					 {
			  returnPartyCode = (String) value;
			  break;
			 }
			 case 39: 
					 {
			  returnPartyIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 40: 
					 {
			  agreementNumber = (String) value;
			  break;
			 }
			 case 41: 
					 {
			  toPartyName = (String) value;
			  break;
			 }
			 case 42: 
					 {
			  fromPartyName = (String) value;
			  break;
			 }
			 case 43: 
					 {
			  currOwnerCode = ((Integer) value).intValue();
			  break;
			 }
			 case 44: 
					 {
			  operationalAirlineIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 45: 
					 {
			  txStationCode = (String) value;
			  break;
			 }
			 case 46: 
					 {
			  uldConditionCode = (String) value;
			  break;
			 }
			 case 47: 
					 {
			  lastUpdateUser = (String) value;
			  break;
			 }
			default: 
			}
		}
	}

	public int getAttributeCount() {
		return ATTRIBUTE_NAMES.length;
	}

	public AttributeInfo getAttributeInfo(int pos) {
		AttributeInfo ret = null;
		switch (pos) {
		 case 0:
			 {
			  ret = new AttributeInfo(ATTRIBUTE_NAMES[0], AttributeInfo.STRING_TYPE);
			  break;
			 }
		 case 1:
			 {
			 
			   ret = new AttributeInfo(ATTRIBUTE_NAMES[1], AttributeInfo.STRING_TYPE);
			   break;
			  
			 }
		 case 2:
			
				
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[2], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 3:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[3], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 4:
			 
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[4], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 5:
			 
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[5], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 6:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[6], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 7:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[7], AttributeInfo.STRING_TYPE);
						break;
					
				}
		 case 8:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[8], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 9:
			 
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[9], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 10:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[10], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 11:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[11], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 12:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[12], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 13:
		
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[13], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 14:
			 
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[14], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 15:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[15], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 16:
			
				
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[16], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 17:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[17], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 18:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[18], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 19:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[19], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 20:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[20], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 21:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[21], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 22:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[22], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 23:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[23], AttributeInfo.DOUBLE_TYPE);
						break;
					
				}
		 case 24:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[24], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 25:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[25], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 26:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[26], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 27:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[27], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 28:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[28], AttributeInfo.STRING_TYPE);
						break;
					}
			
		 case 29:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[29], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 30:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[30], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 31:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[31], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 32:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[32], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 33:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[33], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 34:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[34], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 35:
		
		 {
						ret = new AttributeInfo(ATTRIBUTE_NAMES[35], AttributeInfo.INTEGER_TYPE);
						break;
					}
			
		 case 36:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[36], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 37:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[37], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 38:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[38], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 39:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[39], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 40:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[40], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 41:
			 
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[41], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 42:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[42], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 43:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[43], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 44:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[44], AttributeInfo.INTEGER_TYPE);
						break;
					}
				
		 case 45:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[45], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 46:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[46], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 47:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[47], AttributeInfo.STRING_TYPE);
						break;
					}
				
		default: {
			ret = null;
		}

		}
	  return ret;
}

	public String getCompanyCode(){
			 return companyCode;
	  }

	public void setCompanyCode(String companyCode ){
			 this.companyCode = companyCode;
	  }

	public String getUldNumber(){
			 return uldNumber;
	  }

	public void setUldNumber(String uldNumber ){
			 this.uldNumber = uldNumber;
	  }

	public int getTransactionRefNumber(){
			 return transactionRefNumber;
	  }

	public void setTransactionRefNumber(int transactionRefNumber ){
			 this.transactionRefNumber = transactionRefNumber;
	  }

	public String getTransactionType(){
			 return transactionType;
	  }

	public void setTransactionType(String transactionType ){
			 this.transactionType = transactionType;
	  }

	public String getUldType(){
			 return uldType;
	  }

	public void setUldType(String uldType ){
			 this.uldType = uldType;
	  }

	public String getTransactionNature(){
			 return transactionNature;
	  }

	public void setTransactionNature(String transactionNature ){
			 this.transactionNature = transactionNature;
	  }

	public String getPartyType(){
			 return partyType;
	  }

	public void setPartyType(String partyType ){
			 this.partyType = partyType;
	  }

	public String getFromPartyCode(){
			 return fromPartyCode;
	  }

	public void setFromPartyCode(String fromPartyCode ){
			 this.fromPartyCode = fromPartyCode;
	  }

	public String getToPartyCode(){
			 return toPartyCode;
	  }

	public void setToPartyCode(String toPartyCode ){
			 this.toPartyCode = toPartyCode;
	  }

	public int getFromPartyIdentifier(){
			 return fromPartyIdentifier;
	  }

	public void setFromPartyIdentifier(int fromPartyIdentifier ){
			 this.fromPartyIdentifier = fromPartyIdentifier;
	  }

	public int getToPartyIdentifier(){
			 return toPartyIdentifier;
	  }

	public void setToPartyIdentifier(int toPartyIdentifier ){
			 this.toPartyIdentifier = toPartyIdentifier;
	  }

	public String getTransactionStationCode(){
			 return transactionStationCode;
	  }

	public void setTransactionStationCode(String transactionStationCode ){
			 this.transactionStationCode = transactionStationCode;
	  }

	public String getTransationPeriod(){
			 return transationPeriod;
	  }

	public void setTransationPeriod(String transationPeriod ){
			 this.transationPeriod = transationPeriod;
	  }

	public String getTransactionDate(){
			 return transactionDate;
	  }

	public void setTransactionDate(String transactionDate ){
			 this.transactionDate = transactionDate;
	  }

	public String getTransactionStatus(){
			 return transactionStatus;
	  }

	public void setTransactionStatus(String transactionStatus ){
			 this.transactionStatus = transactionStatus;
	  }

	public String getTransactionRemark(){
			 return transactionRemark;
	  }

	public void setTransactionRemark(String transactionRemark ){
			 this.transactionRemark = transactionRemark;
	  }

	public String getCapturedRefNumber(){
			 return capturedRefNumber;
	  }

	public void setCapturedRefNumber(String capturedRefNumber ){
			 this.capturedRefNumber = capturedRefNumber;
	  }

	public String getDamageStatus(){
			 return damageStatus;
	  }

	public void setDamageStatus(String damageStatus ){
			 this.damageStatus = damageStatus;
	  }

	public String getReturnDate(){
			 return returnDate;
	  }

	public void setReturnDate(String returnDate ){
			 this.returnDate = returnDate;
	  }

	public String getReturnStationCode(){
			 return returnStationCode;
	  }

	public void setReturnStationCode(String returnStationCode ){
			 this.returnStationCode = returnStationCode;
	  }

	public double getDemurrageAmount(){
			 return demurrageAmount;
	  }

	public void setDemurrageAmount(double demurrageAmount ){
			 this.demurrageAmount = demurrageAmount;
	  }

	public String getCurrency(){
			 return currency;
	  }

	public void setCurrency(String currency ){
			 this.currency = currency;
	  }

	public String getInvoiceStatus(){
			 return invoiceStatus;
	  }

	public void setInvoiceStatus(String invoiceStatus ){
			 this.invoiceStatus = invoiceStatus;
	  }

	public double getWaived(){
			 return waived;
	  }

	public void setWaived(double waived ){
			 this.waived = waived;
	  }

	public double getTaxes(){
			 return taxes;
	  }

	public void setTaxes(double taxes ){
			 this.taxes = taxes;
	  }

	public String getReturnedBy(){
			 return returnedBy;
	  }

	public void setReturnedBy(String returnedBy ){
			 this.returnedBy = returnedBy;
	  }

	public double getOtherCharges(){
			 return otherCharges;
	  }

	public void setOtherCharges(double otherCharges ){
			 this.otherCharges = otherCharges;
	  }

	public double getTotal(){
			 return total;
	  }

	public void setTotal(double total ){
			 this.total = total;
	  }

	public String getInvoiceRefNumber(){
			 return invoiceRefNumber;
	  }

	public void setInvoiceRefNumber(String invoiceRefNumber ){
			 this.invoiceRefNumber = invoiceRefNumber;
	  }

	public String getPaymentStatus(){
			 return paymentStatus;
	  }

	public void setPaymentStatus(String paymentStatus ){
			 this.paymentStatus = paymentStatus;
	  }

	public String getReturnRemark(){
			 return returnRemark;
	  }

	public void setReturnRemark(String returnRemark ){
			 this.returnRemark = returnRemark;
	  }

	public String getOperationalFlag(){
			 return operationalFlag;
	  }

	public void setOperationalFlag(String operationalFlag ){
			 this.operationalFlag = operationalFlag;
	  }

	public String getControlReceiptNumber(){
			 return controlReceiptNumber;
	  }

	public void setControlReceiptNumber(String controlReceiptNumber ){
			 this.controlReceiptNumber = controlReceiptNumber;
	  }

	public int getNumberMonths(){
			 return numberMonths;
	  }

	public void setNumberMonths(int numberMonths ){
			 this.numberMonths = numberMonths;
	  }

	public int getSelectNumber(){
			 return selectNumber;
	  }

	public void setSelectNumber(int selectNumber ){
			 this.selectNumber = selectNumber;
	  }

	public int getRepairSeqNumber(){
			 return repairSeqNumber;
	  }

	public void setRepairSeqNumber(int repairSeqNumber ){
			 this.repairSeqNumber = repairSeqNumber;
	  }

	public String getStrTxnDate(){
			 return strTxnDate;
	  }

	public void setStrTxnDate(String strTxnDate ){
			 this.strTxnDate = strTxnDate;
	  }

	public String getStrRetDate(){
			 return strRetDate;
	  }

	public void setStrRetDate(String strRetDate ){
			 this.strRetDate = strRetDate;
	  }

	public String getReturnPartyCode(){
			 return returnPartyCode;
	  }

	public void setReturnPartyCode(String returnPartyCode ){
			 this.returnPartyCode = returnPartyCode;
	  }

	public int getReturnPartyIdentifier(){
			 return returnPartyIdentifier;
	  }

	public void setReturnPartyIdentifier(int returnPartyIdentifier ){
			 this.returnPartyIdentifier = returnPartyIdentifier;
	  }

	public String getAgreementNumber(){
			 return agreementNumber;
	  }

	public void setAgreementNumber(String agreementNumber ){
			 this.agreementNumber = agreementNumber;
	  }

	public String getToPartyName(){
			 return toPartyName;
	  }

	public void setToPartyName(String toPartyName ){
			 this.toPartyName = toPartyName;
	  }

	public String getFromPartyName(){
			 return fromPartyName;
	  }

	public void setFromPartyName(String fromPartyName ){
			 this.fromPartyName = fromPartyName;
	  }

	public int getCurrOwnerCode(){
			 return currOwnerCode;
	  }

	public void setCurrOwnerCode(int currOwnerCode ){
			 this.currOwnerCode = currOwnerCode;
	  }

	public int getOperationalAirlineIdentifier(){
			 return operationalAirlineIdentifier;
	  }

	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier ){
			 this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	  }

	public String getTxStationCode(){
			 return txStationCode;
	  }

	public void setTxStationCode(String txStationCode ){
			 this.txStationCode = txStationCode;
	  }

	public String getUldConditionCode(){
			 return uldConditionCode;
	  }

	public void setUldConditionCode(String uldConditionCode ){
			 this.uldConditionCode = uldConditionCode;
	  }

	public String getLastUpdateUser(){
			 return lastUpdateUser;
	  }

	public void setLastUpdateUser(String lastUpdateUser ){
			 this.lastUpdateUser = lastUpdateUser;
	  }


}
