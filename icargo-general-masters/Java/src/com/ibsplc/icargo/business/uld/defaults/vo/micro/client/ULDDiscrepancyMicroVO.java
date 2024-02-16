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

public class ULDDiscrepancyMicroVO extends Exportable {


	private String companyCode;
	private String uldNumber;
	private String discrepencyCode;
	private String discrepencyDate;
	private String reportingStation;
	private String remarks;
	private String airportCode;
	private String operationFlag;
	private String lastUpdatedUser;
	private String lastUpdatedTime;
	private String uldStatus;
	private String transactionStatus; 
	private String scmSequenceNumber; 
	private String facilityDescription; 
	private String facilityType; 
	private String location; 
	private String closeStatus; 
	private String sequenceNumber; 
	private String agentCode;
 	private static final String[] ATTRIBUTE_NAMES ;

	static {
		 ATTRIBUTE_NAMES = new String[19] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "discrepencyCode";
		 ATTRIBUTE_NAMES[3] = "discrepencyDate";
		 ATTRIBUTE_NAMES[4] = "reportingStation";
		 ATTRIBUTE_NAMES[5] = "remarks";
		 ATTRIBUTE_NAMES[6] = "airportCode";
		 ATTRIBUTE_NAMES[7] = "operationFlag";
		 ATTRIBUTE_NAMES[8] = "lastUpdatedUser";
		 ATTRIBUTE_NAMES[9] = "lastUpdatedTime";
		 ATTRIBUTE_NAMES[10] = "uldStatus";
		 ATTRIBUTE_NAMES[11] = "transactionStatus";
		 ATTRIBUTE_NAMES[12] = "scmSequenceNumber";
		 ATTRIBUTE_NAMES[13] = "facilityDescription";
		 ATTRIBUTE_NAMES[14] = "facilityType";
		 ATTRIBUTE_NAMES[15] = "location";
		 ATTRIBUTE_NAMES[16] = "closeStatus";
		 ATTRIBUTE_NAMES[17] = "sequenceNumber";
		 ATTRIBUTE_NAMES[18] = "agentCode";
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
		  ret = (String) discrepencyCode;
		  break;
		 }
		 case 3: 
				 {
		  ret = (String) discrepencyDate;
		  break;
		 }
		 case 4: 
				 {
		  ret = (String) reportingStation;
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) remarks;
		  break;
		 }
		 case 6: 
				 {
		  ret = (String) airportCode;
		  break;
		 }
		 case 7: 
				 {
		  ret = (String) operationFlag;
		  break;
		 }
		 case 8: 
				 {
		  ret = (String) lastUpdatedUser;
		  break;
		 }
		 case 9: 
				 {
		  ret = (String) lastUpdatedTime;
		  break;
		 }
		 case 10: 
				 {
		  ret = (String) uldStatus;
		  break;
		 }
		 case 11: 
				 {
		  ret = (String) transactionStatus;
		  break;
		 }
		 case 12: 
				 {
		  ret = (String) scmSequenceNumber;
		  break;
		 }
		 case 13: 
				 {
		  ret = (String) facilityDescription;
		  break;
		 }
		 case 14: 
				 {
		  ret = (String) facilityType;
		  break;
		 }
		 case 15: 
				 {
		  ret = (String) location;
		  break;
		 }
		 case 16: 
				 {
		  ret = (String) closeStatus;
		  break;
		 }
		 case 17: 
				 {
		  ret = (String) sequenceNumber;
		  break;
		 }
		 case 18: 
				 {
		  ret = (String) agentCode;
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
			  discrepencyCode = (String) value;
			  break;
			 }
			 case 3: 
					 {
			  discrepencyDate = (String) value;
			  break;
			 }
			 case 4: 
					 {
			  reportingStation = (String) value;
			  break;
			 }
			 case 5: 
					 {
			  remarks = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  airportCode = (String) value;
			  break;
			 }
			 case 7: 
					 {
			  operationFlag = (String) value;
			  break;
			 }
			 case 8: 
					 {
			  lastUpdatedUser = (String) value;
			  break;
			 }
			 case 9: 
					 {
			  lastUpdatedTime = (String) value;
			  break;
			 }
			 case 10: 
					 {
			  uldStatus = (String) value;
			  break;
			 }
			 case 11: 
					 {
			  transactionStatus = (String) value;
			  break;
			 }
			 case 12: 
					 {
			  scmSequenceNumber = (String) value;
			  break;
			 }
			 case 13: 
					 {
			  facilityDescription = (String) value;
			  break;
			 }
			 case 14: 
					 {
			  facilityType = (String) value;
			  break;
			 }
			 case 15: 
					 {
			  location = (String) value;
			  break;
			 }
			 case 16: 
					 {
			  closeStatus = (String) value;
			  break;
			 }
			 case 17: 
					 {
			  sequenceNumber = (String) value;
			  break;
			 }
			 case 18: 
					 {
			  agentCode = (String) value;
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
			 ret = new AttributeInfo(ATTRIBUTE_NAMES[2],AttributeInfo.STRING_TYPE);
			 break;
		 	}	
		 case 3:
		 	{
			 ret = new AttributeInfo(ATTRIBUTE_NAMES[3],AttributeInfo.STRING_TYPE);
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

	public String getDiscrepencyCode(){
			 return discrepencyCode;
	  }

	public void setDiscrepencyCode(String discrepencyCode ){
			 this.discrepencyCode = discrepencyCode;
	  }

	public String getDiscrepencyDate(){
			 return discrepencyDate;
	  }

	public void setDiscrepencyDate(String discrepencyDate ){
			 this.discrepencyDate = discrepencyDate;
	  }

	public String getReportingStation(){
			 return reportingStation;
	  }

	public void setReportingStation(String reportingStation ){
			 this.reportingStation = reportingStation;
	  }

	public String getRemarks(){
			 return remarks;
	  }

	public void setRemarks(String remarks ){
			 this.remarks = remarks;
	  }

	public String getAirportCode(){
			 return airportCode;
	  }

	public void setAirportCode(String airportCode ){
			 this.airportCode = airportCode;
	  }

	public String getOperationFlag(){
			 return operationFlag;
	  }

	public void setOperationFlag(String operationFlag ){
			 this.operationFlag = operationFlag;
	  }

	public String getLastUpdatedUser(){
			 return lastUpdatedUser;
	  }

	public void setLastUpdatedUser(String lastUpdatedUser ){
			 this.lastUpdatedUser = lastUpdatedUser;
	  }

	public String getLastUpdatedTime(){
			 return lastUpdatedTime;
	  }

	public void setLastUpdatedTime(String lastUpdatedTime ){
			 this.lastUpdatedTime = lastUpdatedTime;
	  }

	public String getUldStatus(){
			 return uldStatus;
	  }

	public void setUldStatus(String uldStatus ){
			 this.uldStatus = uldStatus;
	  }

	public String getTransactionStatus(){
			 return transactionStatus;
	  }

	public void setTransactionStatus(String transactionStatus ){
			 this.transactionStatus = transactionStatus;
	  }

	public String getScmSequenceNumber(){
			 return scmSequenceNumber;
	  }

	public void setScmSequenceNumber(String scmSequenceNumber ){
			 this.scmSequenceNumber = scmSequenceNumber;
	  }

	public String getFacilityDescription(){
			 return facilityDescription;
	  }

	public void setFacilityDescription(String facilityDescription ){
			 this.facilityDescription = facilityDescription;
	  }

	public String getFacilityType(){
			 return facilityType;
	  }

	public void setFacilityType(String facilityType ){
			 this.facilityType = facilityType;
	  }

	public String getLocation(){
			 return location;
	  }

	public void setLocation(String location ){
			 this.location = location;
	  }

	public String getCloseStatus(){
			 return closeStatus;
	  }

	public void setCloseStatus(String closeStatus ){
			 this.closeStatus = closeStatus;
	  }

	public String getSequenceNumber(){
			 return sequenceNumber;
	  }

	public void setSequenceNumber(String sequenceNumber ){
			 this.sequenceNumber = sequenceNumber;
	  }

	public String getAgentCode(){
			 return agentCode;
	  }

	public void setAgentCode(String agentCode ){
			 this.agentCode = agentCode;
	  }


}
