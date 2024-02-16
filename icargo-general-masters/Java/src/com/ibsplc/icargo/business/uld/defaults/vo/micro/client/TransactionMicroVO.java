/*
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd
 * Use is subject to license terms.
 *
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.client;


import java.util.Vector;
import com.ibsplc.icargo.framework.micro.serialization.AttributeInfo;
import com.ibsplc.icargo.framework.micro.serialization.Exportable;
/**
 * 
 * @author 
 *
 */

public class TransactionMicroVO extends Exportable {


	private String companyCode;
	private String transactionType;
	private String transactionNature;
	private String transactionStation;
	private String transactionDate;
	private String strTransactionDate;
	private String transactionTime;
	private String transactionRemark;
	private String partyType;
	private String fromPartyCode;
	private String fromPartyName;
	private String toPartyCode;
	private String toPartyName;
	private int currOwnerCode;
	private String operationalFlag;
	private int operationalAirlineIdentifier;
	private String transactionStatus;
	private Vector ULDTransactionDetailsMicroVOs;
	private Vector accessoryTransactionMicroVOs;
 	private static final String[] ATTRIBUTE_NAMES ;

	static {
		 ATTRIBUTE_NAMES = new String[19] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "transactionType";
		 ATTRIBUTE_NAMES[2] = "transactionNature";
		 ATTRIBUTE_NAMES[3] = "transactionStation";
		 ATTRIBUTE_NAMES[4] = "transactionDate";
		 ATTRIBUTE_NAMES[5] = "strTransactionDate";
		 ATTRIBUTE_NAMES[6] = "transactionTime";
		 ATTRIBUTE_NAMES[7] = "transactionRemark";
		 ATTRIBUTE_NAMES[8] = "partyType";
		 ATTRIBUTE_NAMES[9] = "fromPartyCode";
		 ATTRIBUTE_NAMES[10] = "fromPartyName";
		 ATTRIBUTE_NAMES[11] = "toPartyCode";
		 ATTRIBUTE_NAMES[12] = "toPartyName";
		 ATTRIBUTE_NAMES[13] = "currOwnerCode";
		 ATTRIBUTE_NAMES[14] = "operationalFlag";
		 ATTRIBUTE_NAMES[15] = "operationalAirlineIdentifier";
		 ATTRIBUTE_NAMES[16] = "transactionStatus";
		 ATTRIBUTE_NAMES[17] = "ULDTransactionDetailsMicroVOs";
		 ATTRIBUTE_NAMES[18] = "accessoryTransactionMicroVOs";
	}

	public Object getAttribute(int pos) {
		Object ret = null;
		switch (pos) {
		 case 0: 
				 {
		  ret = (String) companyCode;
				 break;
		 }
		 case 1: {
          ret = (String)transactionType;
				 break;	
         }
		 case 2: 
				 {
		  ret = (String) transactionNature;
				 break;
		 }
		 case 3: {
          ret = (String)transactionStation;
				 break;	
         }
		 case 4: 
				 {
		  ret = (String) transactionDate;
				 break;
		 }
		 case 5: {
          ret = (String)strTransactionDate;
				 break;	
         }
		 case 6: 
				 {
		  ret = (String) transactionTime;
				 break;
		 }
		 case 7: {
          ret = (String)transactionRemark;
				 break;	 
         }
		 case 8: 
				 {
		  ret = (String) partyType;
				 break;
		 }
		 case 9: {
          ret = (String)fromPartyCode;
				 break;	  
         }
		 case 10: 
				 {
		  ret = (String) fromPartyName;
				 break;
		 }
		 case 11: {
          ret = (String)toPartyCode;
				 break;	
         }
		 case 12: 
				 {
		  ret = (String) toPartyName;
				 break;
		 }
		 case 13: {
          ret = new Integer(currOwnerCode);
				 break;	 
         }
		 case 14: 
				 {
		  ret = (String) operationalFlag;
				 break;
		 }
		 case 15:{
          ret = new Integer(operationalAirlineIdentifier);
				 break;		
         }
		 case 16: 
				 {
		  ret = (String) transactionStatus;
				 break;
		 }
		 case 17: {
          ret = (Vector)ULDTransactionDetailsMicroVOs;
				 break;	  
         }
		 case 18: 
				 {
		  ret = (Vector) accessoryTransactionMicroVOs;
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
			 case 1: {
              transactionType = (String) value;
					 break;	 
             }
			 case 2: 
					 {
			  transactionNature = (String) value;
					 break;
			 }
			 case 3:{
              transactionStation = (String) value;
					 break;	 
             }
			 case 4: 
					 {
			  transactionDate = (String) value;
					 break;
			 }
			 case 5: {
              strTransactionDate = (String) value;
					 break;	
             }
			 case 6: 
					 {
			  transactionTime = (String) value;
					 break;
			 }
			 case 7: {
              transactionRemark = (String) value;
					 break;	   
             }
			 case 8: 
					 {
			  partyType = (String) value;
					 break;
			 }
			 case 9: {
              fromPartyCode = (String) value;
					 break;	 
             }
			 case 10: 
					 {
			  fromPartyName = (String) value;
					 break;
			 }
			 case 11: {
              toPartyCode = (String) value;
					 break;	
             }
			 case 12: 
					 {
			  toPartyName = (String) value;
					 break;
			 }
			 case 13: {
              currOwnerCode = ((Integer) value).intValue();
					 break;	  
             }
			 case 14: 
					 {
			  operationalFlag = (String) value;
					 break;
			 }
			 case 15: {
              operationalAirlineIdentifier = ((Integer) value).intValue();
					 break;	  
             }
			 case 16: 
					 {
			  transactionStatus = (String) value;
					 break;
			 }
			 case 17:{
              ULDTransactionDetailsMicroVOs = (Vector) value;
					 break;	
             }
			 case 18: 
					 {
			  accessoryTransactionMicroVOs = (Vector) value;
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
			 ret = new AttributeInfo(ATTRIBUTE_NAMES[0],AttributeInfo.STRING_TYPE);
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
					ret = new AttributeInfo(ATTRIBUTE_NAMES[13], AttributeInfo.INTEGER_TYPE);
				break;
				}
		 case 14:
			 
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[14], AttributeInfo.STRING_TYPE);
				break;
				}
		 case 15:
			
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[15], AttributeInfo.INTEGER_TYPE);
				break;
				}
		 case 16:
			
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[16], AttributeInfo.STRING_TYPE);
				break;
				}
		 case 17:
			
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[17], true, ULDTransactionDetailsMicroVO.class);
				break;
				}
		 case 18:
			
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[18], true, AccessoryTransactionMicroVO.class);
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

	public String getTransactionStation(){
			 return transactionStation;
	  }

	public void setTransactionStation(String transactionStation ){
			 this.transactionStation = transactionStation;
	  }

	public String getTransactionDate(){
			 return transactionDate;
	  }

	public void setTransactionDate(String transactionDate ){
			 this.transactionDate = transactionDate;
	  }

	public String getStrTransactionDate(){
			 return strTransactionDate;
	  }

	public void setStrTransactionDate(String strTransactionDate ){
			 this.strTransactionDate = strTransactionDate;
	  }

	public String getTransactionTime(){
			 return transactionTime;
	  }

	public void setTransactionTime(String transactionTime ){
			 this.transactionTime = transactionTime;
	  }

	public String getTransactionRemark(){
			 return transactionRemark;
	  }

	public void setTransactionRemark(String transactionRemark ){
			 this.transactionRemark = transactionRemark;
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

	public String getFromPartyName(){
			 return fromPartyName;
	  }

	public void setFromPartyName(String fromPartyName ){
			 this.fromPartyName = fromPartyName;
	  }

	public String getToPartyCode(){
			 return toPartyCode;
	  }

	public void setToPartyCode(String toPartyCode ){
			 this.toPartyCode = toPartyCode;
	  }

	public String getToPartyName(){
			 return toPartyName;
	  }

	public void setToPartyName(String toPartyName ){
			 this.toPartyName = toPartyName;
	  }

	public int getCurrOwnerCode(){
			 return currOwnerCode;
	  }

	public void setCurrOwnerCode(int currOwnerCode ){
			 this.currOwnerCode = currOwnerCode;
	  }

	public String getOperationalFlag(){
			 return operationalFlag;
	  }

	public void setOperationalFlag(String operationalFlag ){
			 this.operationalFlag = operationalFlag;
	  }

	public int getOperationalAirlineIdentifier(){
			 return operationalAirlineIdentifier;
	  }

	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier ){
			 this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	  }

	public String getTransactionStatus(){
			 return transactionStatus;
	  }

	public void setTransactionStatus(String transactionStatus ){
			 this.transactionStatus = transactionStatus;
	  }

	public Vector getULDTransactionDetailsMicroVOs(){
			 return ULDTransactionDetailsMicroVOs;
	  }

	public void setULDTransactionDetailsMicroVOs(Vector ULDTransactionDetailsMicroVOs ){
			 this.ULDTransactionDetailsMicroVOs = ULDTransactionDetailsMicroVOs;
	  }

	public Vector getAccessoryTransactionMicroVOs(){
			 return accessoryTransactionMicroVOs;
	  }

	public void setAccessoryTransactionMicroVOs(Vector accessoryTransactionMicroVOs ){
			 this.accessoryTransactionMicroVOs = accessoryTransactionMicroVOs;
	  }


}
