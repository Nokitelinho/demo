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

public class AccessoryTransactionMicroVO extends Exportable {

 
	private String companyCode; 
	private String accessoryCode; 
	private int transactionRefNumber; 
	private String transactionType; 
	private String transactionNature; 
	private String partyType; 
	private int currOwnerCode; 
	private String transactionStationCode; 
	private String transationPeriod; 
	private String transactionDate; 
	private String transactionRemark; 
	private int quantity; 
	private int operationalAirlineIdentifier; 
	private String operationalFlag; 
	private String lastUpdateUser; 
	private String returnStation; 
	private String strTxnDate; 
	private String toPartyCode; 
	private String fromPartyCode; 
	private String toPartyName; 
	private String fromPartyName;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[21] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "accessoryCode";
		 ATTRIBUTE_NAMES[2] = "transactionRefNumber";
		 ATTRIBUTE_NAMES[3] = "transactionType";
		 ATTRIBUTE_NAMES[4] = "transactionNature";
		 ATTRIBUTE_NAMES[5] = "partyType";
		 ATTRIBUTE_NAMES[6] = "currOwnerCode";
		 ATTRIBUTE_NAMES[7] = "transactionStationCode";
		 ATTRIBUTE_NAMES[8] = "transationPeriod";
		 ATTRIBUTE_NAMES[9] = "transactionDate";
		 ATTRIBUTE_NAMES[10] = "transactionRemark";
		 ATTRIBUTE_NAMES[11] = "quantity";
		 ATTRIBUTE_NAMES[12] = "operationalAirlineIdentifier";
		 ATTRIBUTE_NAMES[13] = "operationalFlag";
		 ATTRIBUTE_NAMES[14] = "lastUpdateUser";
		 ATTRIBUTE_NAMES[15] = "returnStation";
		 ATTRIBUTE_NAMES[16] = "strTxnDate";
		 ATTRIBUTE_NAMES[17] = "toPartyCode";
		 ATTRIBUTE_NAMES[18] = "fromPartyCode";
		 ATTRIBUTE_NAMES[19] = "toPartyName";
		 ATTRIBUTE_NAMES[20] = "fromPartyName";
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
		  ret = (String) accessoryCode;
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
		  ret = (String) transactionNature;
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) partyType;
		  break;
		 }
		 case 6: 
				 {
		  ret = new Integer(currOwnerCode);
		  break;
		 }
		 case 7: 
				 {
		  ret = (String) transactionStationCode;
		  break;
		 }
		 case 8: 
				 {
		  ret = (String) transationPeriod;
		  break;
		 }
		 case 9: 
				 {
		  ret = (String) transactionDate;
		  break;
		 }
		 case 10: 
				 {
		  ret = (String) transactionRemark;
		  break;
		 }
		 case 11: 
				 {
		  ret = new Integer(quantity);
		  break;
		 }
		 case 12: 
				 {
		  ret = new Integer(operationalAirlineIdentifier);
		  break;
		 }
		 case 13: 
				 {
		  ret = (String) operationalFlag;
		  break;
		 }
		 case 14: 
				 {
		  ret = (String) lastUpdateUser;
		  break;
		 }
		 case 15: 
				 {
		  ret = (String) returnStation;
		  break;
		 }
		 case 16: 
				 {
		  ret = (String) strTxnDate;
		  break;
		 }
		 case 17: 
				 {
		  ret = (String) toPartyCode;
		  break;
		 }
		 case 18: 
				 {
		  ret = (String) fromPartyCode;
		  break;
		 }
		 case 19: 
				 {
		  ret = (String) toPartyName;
		  break;
		 }
		 case 20: 
				 {
		  ret = (String) fromPartyName;
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
			  accessoryCode = (String) value;
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
			  transactionNature = (String) value;
			  break;
			 }
			 case 5: 
					 {
			  partyType = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  currOwnerCode = ((Integer) value).intValue();
			  break;
			 }
			 case 7: 
					 {
			  transactionStationCode = (String) value;
			  break;
			 }
			 case 8: 
					 {
			  transationPeriod = (String) value;
			  break;
			 }
			 case 9: 
					 {
			  transactionDate = (String) value;
			  break;
			 }
			 case 10: 
					 {
			  transactionRemark = (String) value;
			  break;
			 }
			 case 11: 
					 {
			  quantity = ((Integer) value).intValue();
			  break;
			 }
			 case 12: 
					 {
			  operationalAirlineIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 13: 
					 {
			  operationalFlag = (String) value;
			  break;
			 }
			 case 14: 
					 {
			  lastUpdateUser = (String) value;
			  break;
			 }
			 case 15: 
					 {
			  returnStation = (String) value;
			  break;
			 }
			 case 16: 
					 {
			  strTxnDate = (String) value;
			  break;
			 }
			 case 17: 
					 {
			  toPartyCode = (String) value;
			  break;
			 }
			 case 18: 
					 {
			  fromPartyCode = (String) value;
			  break;
			 }
			 case 19: 
					 {
			  toPartyName = (String) value;
			  break;
			 }
			 case 20: 
					 {
			  fromPartyName = (String) value;
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[6], AttributeInfo.INTEGER_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[9], AttributeInfo.STRING_TYPE);
						break;
					
				}
		 case 10:
		 {
						ret = new AttributeInfo(ATTRIBUTE_NAMES[10], AttributeInfo.STRING_TYPE);
						break;
					
				}
		 case 11:
			
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[11], AttributeInfo.INTEGER_TYPE);
						break;
					
				}
		 case 12:
			 
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[12], AttributeInfo.INTEGER_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[20], AttributeInfo.STRING_TYPE);
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

	public String getAccessoryCode(){
			 return accessoryCode;
	  }

	public void setAccessoryCode(String accessoryCode ){
			 this.accessoryCode = accessoryCode;
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

	public int getCurrOwnerCode(){
			 return currOwnerCode;
	  }

	public void setCurrOwnerCode(int currOwnerCode ){
			 this.currOwnerCode = currOwnerCode;
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

	public String getTransactionRemark(){
			 return transactionRemark;
	  }

	public void setTransactionRemark(String transactionRemark ){
			 this.transactionRemark = transactionRemark;
	  }

	public int getQuantity(){
			 return quantity;
	  }

	public void setQuantity(int quantity ){
			 this.quantity = quantity;
	  }

	public int getOperationalAirlineIdentifier(){
			 return operationalAirlineIdentifier;
	  }

	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier ){
			 this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	  }

	public String getOperationalFlag(){
			 return operationalFlag;
	  }

	public void setOperationalFlag(String operationalFlag ){
			 this.operationalFlag = operationalFlag;
	  }

	public String getLastUpdateUser(){
			 return lastUpdateUser;
	  }

	public void setLastUpdateUser(String lastUpdateUser ){
			 this.lastUpdateUser = lastUpdateUser;
	  }

	public String getReturnStation(){
			 return returnStation;
	  }

	public void setReturnStation(String returnStation ){
			 this.returnStation = returnStation;
	  }

	public String getStrTxnDate(){
			 return strTxnDate;
	  }

	public void setStrTxnDate(String strTxnDate ){
			 this.strTxnDate = strTxnDate;
	  }

	public String getToPartyCode(){
			 return toPartyCode;
	  }

	public void setToPartyCode(String toPartyCode ){
			 this.toPartyCode = toPartyCode;
	  }

	public String getFromPartyCode(){
			 return fromPartyCode;
	  }

	public void setFromPartyCode(String fromPartyCode ){
			 this.fromPartyCode = fromPartyCode;
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


}
