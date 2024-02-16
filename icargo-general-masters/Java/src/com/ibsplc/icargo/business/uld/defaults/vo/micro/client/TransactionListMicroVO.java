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

public class TransactionListMicroVO extends Exportable {

 
	private String companyCode; 
	private String uldNumber; 
	private String transactionType; 
	private String operationalFlag; 
	private String returnStationCode; 
	private String currency; 
	private double demurrageAmount; 
	private String damageStatus; 
	private String transactionStatus; 
	private int operationalAirlineIdentifier; 
	private int transactionRefNumber; 
	private String transactionDate; 
	private String returnDate; 
	private String partyType; 
	private String uldType; 
	private String transactionNature; 
	private String fromPartyCode; 
	private String toPartyCode; 
	private int fromPartyIdentifier; 
	private int toPartyIdentifier; 
	private String transactionStationCode; 
	private String transationPeriod; 
	private String transactionRemark; 
	private String capturedRefNumber; 
	private String invoiceStatus; 
	private double waived; 
	private double taxes; 
	private String returnedBy; 
	private double otherCharges; 
	private double total; 
	private String invoiceRefNumber; 
	private String paymentStatus; 
	private String returnRemark; 
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
	private String txStationCode; 
	private String lastUpdateUser; 
	private String lastUpdateTime;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[48] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "transactionType";
		 ATTRIBUTE_NAMES[3] = "operationalFlag";
		 ATTRIBUTE_NAMES[4] = "returnStationCode";
		 ATTRIBUTE_NAMES[5] = "currency";
		 ATTRIBUTE_NAMES[6] = "demurrageAmount";
		 ATTRIBUTE_NAMES[7] = "damageStatus";
		 ATTRIBUTE_NAMES[8] = "transactionStatus";
		 ATTRIBUTE_NAMES[9] = "operationalAirlineIdentifier";
		 ATTRIBUTE_NAMES[10] = "transactionRefNumber";
		 ATTRIBUTE_NAMES[11] = "transactionDate";
		 ATTRIBUTE_NAMES[12] = "returnDate";
		 ATTRIBUTE_NAMES[13] = "partyType";
		 ATTRIBUTE_NAMES[14] = "uldType";
		 ATTRIBUTE_NAMES[15] = "transactionNature";
		 ATTRIBUTE_NAMES[16] = "fromPartyCode";
		 ATTRIBUTE_NAMES[17] = "toPartyCode";
		 ATTRIBUTE_NAMES[18] = "fromPartyIdentifier";
		 ATTRIBUTE_NAMES[19] = "toPartyIdentifier";
		 ATTRIBUTE_NAMES[20] = "transactionStationCode";
		 ATTRIBUTE_NAMES[21] = "transationPeriod";
		 ATTRIBUTE_NAMES[22] = "transactionRemark";
		 ATTRIBUTE_NAMES[23] = "capturedRefNumber";
		 ATTRIBUTE_NAMES[24] = "invoiceStatus";
		 ATTRIBUTE_NAMES[25] = "waived";
		 ATTRIBUTE_NAMES[26] = "taxes";
		 ATTRIBUTE_NAMES[27] = "returnedBy";
		 ATTRIBUTE_NAMES[28] = "otherCharges";
		 ATTRIBUTE_NAMES[29] = "total";
		 ATTRIBUTE_NAMES[30] = "invoiceRefNumber";
		 ATTRIBUTE_NAMES[31] = "paymentStatus";
		 ATTRIBUTE_NAMES[32] = "returnRemark";
		 ATTRIBUTE_NAMES[33] = "controlReceiptNumber";
		 ATTRIBUTE_NAMES[34] = "numberMonths";
		 ATTRIBUTE_NAMES[35] = "selectNumber";
		 ATTRIBUTE_NAMES[36] = "repairSeqNumber";
		 ATTRIBUTE_NAMES[37] = "strTxnDate";
		 ATTRIBUTE_NAMES[38] = "strRetDate";
		 ATTRIBUTE_NAMES[39] = "returnPartyCode";
		 ATTRIBUTE_NAMES[40] = "returnPartyIdentifier";
		 ATTRIBUTE_NAMES[41] = "agreementNumber";
		 ATTRIBUTE_NAMES[42] = "toPartyName";
		 ATTRIBUTE_NAMES[43] = "fromPartyName";
		 ATTRIBUTE_NAMES[44] = "currOwnerCode";
		 ATTRIBUTE_NAMES[45] = "txStationCode";
		 ATTRIBUTE_NAMES[46] = "lastUpdateUser";
		 ATTRIBUTE_NAMES[47] = "lastUpdateTime";
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
		  ret = (String) transactionType;
		  break;
		 }
		 case 3: 
				 {
		  ret = (String) operationalFlag;
		  break;
		 }
		 case 4: 
				 {
		  ret = (String) returnStationCode;
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) currency;
		  break;
		 }
		 case 6: 
				 {
		  ret = new Double(demurrageAmount);
		  break;
		 }
		 case 7: 
				 {
		  ret = (String) damageStatus;
		  break;
		 }
		 case 8: 
				 {
		  ret = (String) transactionStatus;
		  break;
		 }
		 case 9: 
				 {
		  ret = new Integer(operationalAirlineIdentifier);
		  break;
		 }
		 case 10: 
				 {
		  ret = new Integer(transactionRefNumber);
		  break;
		 }
		 case 11: 
				 {
		  ret = (String) transactionDate;
		  break;
		 }
		 case 12: 
				 {
		  ret = (String) returnDate;
		  break;
		 }
		 case 13: 
				 {
		  ret = (String) partyType;
		  break;
		 }
		 case 14: 
				 {
		  ret = (String) uldType;
		  break;
		 }
		 case 15: 
				 {
		  ret = (String) transactionNature;
		  break;
		 }
		 case 16: 
				 {
		  ret = (String) fromPartyCode;
		  break;
		 }
		 case 17: 
				 {
		  ret = (String) toPartyCode;
		  break;
		 }
		 case 18: 
				 {
		  ret = new Integer(fromPartyIdentifier);
		  break;
		 }
		 case 19: 
				 {
		  ret = new Integer(toPartyIdentifier);
		  break;
		 }
		 case 20: 
				 {
		  ret = (String) transactionStationCode;
		  break;
		 }
		 case 21: 
				 {
		  ret = (String) transationPeriod;
		  break;
		 }
		 case 22: 
				 {
		  ret = (String) transactionRemark;
		  break;
		 }
		 case 23: 
				 {
		  ret = (String) capturedRefNumber;
		  break;
		 }
		 case 24: 
				 {
		  ret = (String) invoiceStatus;
		  break;
		 }
		 case 25: 
				 {
		  ret = new Double(waived);
		  break;
		 }
		 case 26: 
				 {
		  ret = new Double(taxes);
		  break;
		 }
		 case 27: 
				 {
		  ret = (String) returnedBy;
		  break;
		 }
		 case 28: 
				 {
		  ret = new Double(otherCharges);
		  break;
		 }
		 case 29: 
				 {
		  ret = new Double(total);
		  break;
		 }
		 case 30: 
				 {
		  ret = (String) invoiceRefNumber;
		  break;
		 }
		 case 31: 
				 {
		  ret = (String) paymentStatus;
		  break;
		 }
		 case 32: 
				 {
		  ret = (String) returnRemark;
		  break;
		 }
		 case 33: 
				 {
		  ret = (String) controlReceiptNumber;
		  break;
		 }
		 case 34: 
				 {
		  ret = new Integer(numberMonths);
		  break;
		 }
		 case 35: 
				 {
		  ret = new Integer(selectNumber);
		  break;
		 }
		 case 36: 
				 {
		  ret = new Integer(repairSeqNumber);
		  break;
		 }
		 case 37: 
				 {
		  ret = (String) strTxnDate;
		  break;
		 }
		 case 38: 
				 {
		  ret = (String) strRetDate;
		  break;
		 }
		 case 39: 
				 {
		  ret = (String) returnPartyCode;
		  break;
		 }
		 case 40: 
				 {
		  ret = new Integer(returnPartyIdentifier);
		  break;
		 }
		 case 41: 
				 {
		  ret = (String) agreementNumber;
		  break;
		 }
		 case 42: 
				 {
		  ret = (String) toPartyName;
		  break;
		 }
		 case 43: 
				 {
		  ret = (String) fromPartyName;
		  break;
		 }
		 case 44: 
				 {
		  ret = new Integer(currOwnerCode);
		  break;
		 }
		 case 45: 
				 {
		  ret = (String) txStationCode;
		  break;
		 }
		 case 46: 
				 {
		  ret = (String) lastUpdateUser;
		  break;
		 }
		 case 47: 
				 {
		  ret = (String) lastUpdateTime;
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
			  transactionType = (String) value;
			  break;
			 }
			 case 3: 
					 {
			  operationalFlag = (String) value;
			  break;
			 }
			 case 4: 
					 {
			  returnStationCode = (String) value;
			  break;
			 }
			 case 5: 
					 {
			  currency = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  demurrageAmount = ((Double) value).doubleValue();
			  break;
			 }
			 case 7: 
					 {
			  damageStatus = (String) value;
			  break;
			 }
			 case 8: 
					 {
			  transactionStatus = (String) value;
			  break;
			 }
			 case 9: 
					 {
			  operationalAirlineIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 10: 
					 {
			  transactionRefNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 11: 
					 {
			  transactionDate = (String) value;
			  break;
			 }
			 case 12: 
					 {
			  returnDate = (String) value;
			  break;
			 }
			 case 13: 
					 {
			  partyType = (String) value;
			  break;
			 }
			 case 14: 
					 {
			  uldType = (String) value;
			  break;
			 }
			 case 15: 
					 {
			  transactionNature = (String) value;
			  break;
			 }
			 case 16: 
					 {
			  fromPartyCode = (String) value;
			  break;
			 }
			 case 17: 
					 {
			  toPartyCode = (String) value;
			  break;
			 }
			 case 18: 
					 {
			  fromPartyIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 19: 
					 {
			  toPartyIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 20: 
					 {
			  transactionStationCode = (String) value;
			  break;
			 }
			 case 21: 
					 {
			  transationPeriod = (String) value;
			  break;
			 }
			 case 22: 
					 {
			  transactionRemark = (String) value;
			  break;
			 }
			 case 23: 
					 {
			  capturedRefNumber = (String) value;
			  break;
			 }
			 case 24: 
					 {
			  invoiceStatus = (String) value;
			  break;
			 }
			 case 25: 
					 {
			  waived = ((Double) value).doubleValue();
			  break;
			 }
			 case 26: 
					 {
			  taxes = ((Double) value).doubleValue();
			  break;
			 }
			 case 27: 
					 {
			  returnedBy = (String) value;
			  break;
			 }
			 case 28: 
					 {
			  otherCharges = ((Double) value).doubleValue();
			  break;
			 }
			 case 29: 
					 {
			  total = ((Double) value).doubleValue();
			  break;
			 }
			 case 30: 
					 {
			  invoiceRefNumber = (String) value;
			  break;
			 }
			 case 31: 
					 {
			  paymentStatus = (String) value;
			  break;
			 }
			 case 32: 
					 {
			  returnRemark = (String) value;
			  break;
			 }
			 case 33: 
					 {
			  controlReceiptNumber = (String) value;
			  break;
			 }
			 case 34: 
					 {
			  numberMonths = ((Integer) value).intValue();
			  break;
			 }
			 case 35: 
					 {
			  selectNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 36: 
					 {
			  repairSeqNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 37: 
					 {
			  strTxnDate = (String) value;
			  break;
			 }
			 case 38: 
					 {
			  strRetDate = (String) value;
			  break;
			 }
			 case 39: 
					 {
			  returnPartyCode = (String) value;
			  break;
			 }
			 case 40: 
					 {
			  returnPartyIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 41: 
					 {
			  agreementNumber = (String) value;
			  break;
			 }
			 case 42: 
					 {
			  toPartyName = (String) value;
			  break;
			 }
			 case 43: 
					 {
			  fromPartyName = (String) value;
			  break;
			 }
			 case 44: 
					 {
			  currOwnerCode = ((Integer) value).intValue();
			  break;
			 }
			 case 45: 
					 {
			  txStationCode = (String) value;
			  break;
			 }
			 case 46: 
					 {
			  lastUpdateUser = (String) value;
			  break;
			 }
			 case 47: 
					 {
			  lastUpdateTime = (String) value;
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[2], AttributeInfo.STRING_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[6], AttributeInfo.DOUBLE_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[18], AttributeInfo.INTEGER_TYPE);
						break;
					}
		 case 19:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[19], AttributeInfo.INTEGER_TYPE);
						break;
					}
		 case 20:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[20], AttributeInfo.STRING_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[23], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 24:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[24], AttributeInfo.STRING_TYPE);
						break;
					}
		case 25:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[25], AttributeInfo.DOUBLE_TYPE);
						break;
					}
			
		 case 26:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[26], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 27:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[27], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 28:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[28], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 29:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[29], AttributeInfo.DOUBLE_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[33], AttributeInfo.STRING_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[36], AttributeInfo.INTEGER_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[39], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 40:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[40], AttributeInfo.INTEGER_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[43], AttributeInfo.STRING_TYPE);
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

	public String getTransactionType(){
			 return transactionType;
	  }

	public void setTransactionType(String transactionType ){
			 this.transactionType = transactionType;
	  }

	public String getOperationalFlag(){
			 return operationalFlag;
	  }

	public void setOperationalFlag(String operationalFlag ){
			 this.operationalFlag = operationalFlag;
	  }

	public String getReturnStationCode(){
			 return returnStationCode;
	  }

	public void setReturnStationCode(String returnStationCode ){
			 this.returnStationCode = returnStationCode;
	  }

	public String getCurrency(){
			 return currency;
	  }

	public void setCurrency(String currency ){
			 this.currency = currency;
	  }

	public double getDemurrageAmount(){
			 return demurrageAmount;
	  }

	public void setDemurrageAmount(double demurrageAmount ){
			 this.demurrageAmount = demurrageAmount;
	  }

	public String getDamageStatus(){
			 return damageStatus;
	  }

	public void setDamageStatus(String damageStatus ){
			 this.damageStatus = damageStatus;
	  }

	public String getTransactionStatus(){
			 return transactionStatus;
	  }

	public void setTransactionStatus(String transactionStatus ){
			 this.transactionStatus = transactionStatus;
	  }

	public int getOperationalAirlineIdentifier(){
			 return operationalAirlineIdentifier;
	  }

	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier ){
			 this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	  }

	public int getTransactionRefNumber(){
			 return transactionRefNumber;
	  }

	public void setTransactionRefNumber(int transactionRefNumber ){
			 this.transactionRefNumber = transactionRefNumber;
	  }

	public String getTransactionDate(){
			 return transactionDate;
	  }

	public void setTransactionDate(String transactionDate ){
			 this.transactionDate = transactionDate;
	  }

	public String getReturnDate(){
			 return returnDate;
	  }

	public void setReturnDate(String returnDate ){
			 this.returnDate = returnDate;
	  }

	public String getPartyType(){
			 return partyType;
	  }

	public void setPartyType(String partyType ){
			 this.partyType = partyType;
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

	public String getTxStationCode(){
			 return txStationCode;
	  }

	public void setTxStationCode(String txStationCode ){
			 this.txStationCode = txStationCode;
	  }

	public String getLastUpdateUser(){
			 return lastUpdateUser;
	  }

	public void setLastUpdateUser(String lastUpdateUser ){
			 this.lastUpdateUser = lastUpdateUser;
	  }

	public String getLastUpdateTime(){
			 return lastUpdateTime;
	  }

	public void setLastUpdateTime(String lastUpdateTime ){
			 this.lastUpdateTime = lastUpdateTime;
	  }


}
