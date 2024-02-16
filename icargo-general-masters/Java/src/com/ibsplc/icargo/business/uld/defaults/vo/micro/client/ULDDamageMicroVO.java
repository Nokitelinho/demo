/*
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd
 * Use is subject to license terms.
 *
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.client;

import  com.ibsplc.icargo.framework.micro.serialization.AttributeInfo;
import  com.ibsplc.icargo.framework.micro.serialization.Exportable;
/**
 * 
 * @author
 *
 */

public class ULDDamageMicroVO extends Exportable {

 
	public static final String MODULE = "uld"; 
	public static final String SUBMODULE = "defaults"; 
	public static final String ENTITY = "uld.defaults.misc.ULDDamage"; 
	private String damageCode; 
	private String position; 
	private String severity; 
	private String reportedStation; 
	private String repairedBy; 
	private String repairDate; 
	private String remarks; 
	private String damageReferenceNumber; 
	private String sequenceNumber; 
	private String operationFlag; 
	private String lastUpdateUser; 
	private boolean closed; 
	private String reportedDate;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[13] ;
		 ATTRIBUTE_NAMES[0] = "damageCode";
		 ATTRIBUTE_NAMES[1] = "position";
		 ATTRIBUTE_NAMES[2] = "severity";
		 ATTRIBUTE_NAMES[3] = "reportedStation";
		 ATTRIBUTE_NAMES[4] = "repairedBy";
		 ATTRIBUTE_NAMES[5] = "repairDate";
		 ATTRIBUTE_NAMES[6] = "remarks";
		 ATTRIBUTE_NAMES[7] = "damageReferenceNumber";
		 ATTRIBUTE_NAMES[8] = "sequenceNumber";
		 ATTRIBUTE_NAMES[9] = "operationFlag";
		 ATTRIBUTE_NAMES[10] = "lastUpdateUser";
		 ATTRIBUTE_NAMES[11] = "closed";
		 ATTRIBUTE_NAMES[12] = "reportedDate";
	}

	public Object getAttribute(int pos) {
		Object ret = null;
		switch (pos) {
		 case 0: 
				 {
		  ret = (String) damageCode;
				 break;
		 }
		 case 1: {
          ret = (String)position;
				 break;
         }
		 case 2: 
				 {
		  ret = (String) severity;
				 break;
		 }
		 case 3:{
          ret = (String)reportedStation;
				 break;	
         }
		 case 4: 
				 {
		  ret = (String) repairedBy;
				 break;
		 }
		 case 5: {
          ret = (String)repairDate;
				 break;	
         }
		 case 6: 
				 {
		  ret = (String) remarks;
				 break;
		 }
		 case 7: {
          ret = (String)damageReferenceNumber;
				 break;	
         }
		 case 8: 
				 {
		  ret = (String) sequenceNumber;
				 break;
		 }
		 case 9:{
          ret = (String)operationFlag;
				 break;	  
         }
		 case 10: 
				 {
		  ret = (String) lastUpdateUser;
				 break;
		 }
		 case 11: {
          ret = new Boolean(closed);
				 break;	  
         }
		 case 12: 
				 {
		  ret = (String) reportedDate;
				 break;
		 }
		default: {
			ret = null;
		}
		}
	  return ret;
	}

	public int getAttributeCount() {
		return ATTRIBUTE_NAMES.length;
	}

	public void setAttribute(int pos, Object value) {
		if (value != null) {
			switch (pos) {
			 case 0: 
					 {
			  damageCode = (String) value;
					 break;
			 }
			 case 1: {
              position = (String) value;
					 break;	
             }
			 case 2: 
					 {
			  severity = (String) value;
					 break;
			 }
			 case 3: {
              reportedStation = (String) value;
					 break;	
             }
			 case 4: 
					 {
			  repairedBy = (String) value;
					 break;
			 }
			 case 5: {
              repairDate = (String) value;
					 break;	
             }
			 case 6: 
					 {
			  remarks = (String) value;
					 break;
			 }
			 case 7:{
              damageReferenceNumber = (String) value;
					 break;	  
             }
			 case 8: 
					 {
			  sequenceNumber = (String) value;
					 break;
			 }
			 case 9:{
              operationFlag = (String) value;
					 break;	 
             }
			 case 10: 
					 {
			  lastUpdateUser = (String) value;
					 break;
			 }
			 case 11:{
               closed = ((Boolean) value).booleanValue();
					 break;	 
             }
			 case 12: 
					 {
			  reportedDate = (String) value;
					 break;
			 }
			default: 
			}
		}
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
					ret = new AttributeInfo(ATTRIBUTE_NAMES[11], AttributeInfo.BOOLEAN_TYPE);
				break;
				}
		 case 12:
			 
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[12], AttributeInfo.STRING_TYPE);
				break;
				}
		default: {
			ret = null;
		}

		}
	  return ret;
	}

	public String getDamageCode(){
			 return damageCode;
	  }

	public void setDamageCode(String damageCode ){
			 this.damageCode = damageCode;
	  }

	public String getPosition(){
			 return position;
	  }

	public void setPosition(String position ){
			 this.position = position;
	  }

	public String getSeverity(){
			 return severity;
	  }

	public void setSeverity(String severity ){
			 this.severity = severity;
	  }

	public String getReportedStation(){
			 return reportedStation;
	  }

	public void setReportedStation(String reportedStation ){
			 this.reportedStation = reportedStation;
	  }

	public String getRepairedBy(){
			 return repairedBy;
	  }

	public void setRepairedBy(String repairedBy ){
			 this.repairedBy = repairedBy;
	  }

	public String getRepairDate(){
			 return repairDate;
	  }

	public void setRepairDate(String repairDate ){
			 this.repairDate = repairDate;
	  }

	public String getRemarks(){
			 return remarks;
	  }

	public void setRemarks(String remarks ){
			 this.remarks = remarks;
	  }

	public String getDamageReferenceNumber(){
			 return damageReferenceNumber;
	  }

	public void setDamageReferenceNumber(String damageReferenceNumber ){
			 this.damageReferenceNumber = damageReferenceNumber;
	  }

	public String getSequenceNumber(){
			 return sequenceNumber;
	  }

	public void setSequenceNumber(String sequenceNumber ){
			 this.sequenceNumber = sequenceNumber;
	  }

	public String getOperationFlag(){
			 return operationFlag;
	  }

	public void setOperationFlag(String operationFlag ){
			 this.operationFlag = operationFlag;
	  }

	public String getLastUpdateUser(){
			 return lastUpdateUser;
	  }

	public void setLastUpdateUser(String lastUpdateUser ){
			 this.lastUpdateUser = lastUpdateUser;
	  }

	public boolean getClosed(){
			 return closed;
	  }

	public void setClosed(boolean closed ){
			 this.closed = closed;
	  }

	public String getReportedDate(){
			 return reportedDate;
	  }

	public void setReportedDate(String reportedDate ){
			 this.reportedDate = reportedDate;
	  }


}
