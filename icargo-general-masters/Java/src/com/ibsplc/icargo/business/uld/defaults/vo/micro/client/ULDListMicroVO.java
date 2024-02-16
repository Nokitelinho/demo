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

public class ULDListMicroVO extends Exportable {

 
	private String companyCode; 
	private String uldNumber; 
	private String uldGroupCode; 
	private String uldTypeCode; 
	private String manufacturer; 
	private String partyLoaned; 
	private String partyBorrowed; 
	private String overallStatus; 
	private String damageStatus; 
	private String cleanlinessStatus; 
	private String location; 
	private String ownerStation; 
	private String currentStation; 
	private String facilityType; 
	private String transitStatus; 
	private String uldContour; 
	private double tareWeight; 
	private double maxGrossWt; 
	private String lastUpdateUser; 
	private String lastUpdateTime;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[20] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "uldGroupCode";
		 ATTRIBUTE_NAMES[3] = "uldTypeCode";
		 ATTRIBUTE_NAMES[4] = "manufacturer";
		 ATTRIBUTE_NAMES[5] = "partyLoaned";
		 ATTRIBUTE_NAMES[6] = "partyBorrowed";
		 ATTRIBUTE_NAMES[7] = "overallStatus";
		 ATTRIBUTE_NAMES[8] = "damageStatus";
		 ATTRIBUTE_NAMES[9] = "cleanlinessStatus";
		 ATTRIBUTE_NAMES[10] = "location";
		 ATTRIBUTE_NAMES[11] = "ownerStation";
		 ATTRIBUTE_NAMES[12] = "currentStation";
		 ATTRIBUTE_NAMES[13] = "facilityType";
		 ATTRIBUTE_NAMES[14] = "transitStatus";
		 ATTRIBUTE_NAMES[15] = "uldContour";
		 ATTRIBUTE_NAMES[16] = "tareWeight";
		 ATTRIBUTE_NAMES[17] = "maxGrossWt";
		 ATTRIBUTE_NAMES[18] = "lastUpdateUser";
		 ATTRIBUTE_NAMES[19] = "lastUpdateTime";
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
          ret = (String)uldNumber;
				 break;	 
         }
		 case 2: 
				 {
		  ret = (String) uldGroupCode;
				 break;
		 }
		 case 3: {
          ret = (String)uldTypeCode;
				 break;	   
         }
		 case 4: 
				 {
		  ret = (String) manufacturer;
				 break;
		 }
		 case 5: {
          ret = (String)partyLoaned;
				 break;	
         }
		 case 6: 
				 {
		  ret = (String) partyBorrowed;
				 break;
		 }
		 case 7: {
          ret = (String)overallStatus;
				 break;	
         }
		 case 8: 
				 {
		  ret = (String) damageStatus;
				 break;
		 }
		 case 9: {
          ret = (String)cleanlinessStatus;
				 break;	
         }
		 case 10: 
				 {
		  ret = (String) location;
				 break;
		 }
		 case 11: {
          ret = (String)ownerStation;
				 break;	 
         }
		 case 12: 
				 {
		  ret = (String) currentStation;
				 break;
		 }
		 case 13: {
          ret = (String)facilityType;
				 break;	 
         }
		 case 14: 
				 {
		  ret = (String) transitStatus;
				 break;
		 }
		 case 15:{
          ret = (String)uldContour;
				 break;	
         }
		 case 16: 
				 {
		  ret = new Double(tareWeight);
				 break;
		 }
		 case 17:{
          ret = new Double(maxGrossWt);
				 break;	 
         }
		 case 18: 
				 {
		  ret = (String) lastUpdateUser;
				 break;
		 }
		 case 19: {
          ret = (String)lastUpdateTime;
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
              uldNumber = (String) value;
					 break;
             }
			 case 2: 
					 {
			  uldGroupCode = (String) value;
					 break;
			 }
			 case 3: {
              uldTypeCode = (String) value;
					 break;	 
             }
			 case 4: 
					 {
			  manufacturer = (String) value;
					 break;
			 }
			 case 5: {
              partyLoaned = (String) value;
					 break;	
             }
			 case 6: 
					 {
			  partyBorrowed = (String) value;
					 break;
			 }
			 case 7: {
              overallStatus = (String) value;
					 break;	 
             }
			 case 8: 
					 {
			  damageStatus = (String) value;
					 break;
			 }
			 case 9: {
              cleanlinessStatus = (String) value;
					 break;	
             }
			 case 10: 
					 {
			  location = (String) value;
					 break;
			 }
			 case 11: {
              ownerStation = (String) value;
					 break;	 
             }
			 case 12: 
					 {
			  currentStation = (String) value;
					 break;
			 }
			 case 13: {
              facilityType = (String) value;
					 break;	  
             }
			 case 14: 
					 {
			  transitStatus = (String) value;
					 break;
			 }
			 case 15:{
               uldContour = (String) value;
					 break;	 
             }
			 case 16: 
					 {
			  tareWeight = ((Double) value).doubleValue();
					 break;
			 }
			 case 17:{
               maxGrossWt = ((Double) value).doubleValue();
					 break;	 
             }
			 case 18: 
					 {
			  lastUpdateUser = (String) value;
					 break;
			 }
			 case 19: {
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
					ret = new AttributeInfo(ATTRIBUTE_NAMES[16], AttributeInfo.DOUBLE_TYPE);
				break;
				}
		 case 17:
			
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[17], AttributeInfo.DOUBLE_TYPE);
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

	public String getUldGroupCode(){
			 return uldGroupCode;
	  }

	public void setUldGroupCode(String uldGroupCode ){
			 this.uldGroupCode = uldGroupCode;
	  }

	public String getUldTypeCode(){
			 return uldTypeCode;
	  }

	public void setUldTypeCode(String uldTypeCode ){
			 this.uldTypeCode = uldTypeCode;
	  }

	public String getManufacturer(){
			 return manufacturer;
	  }

	public void setManufacturer(String manufacturer ){
			 this.manufacturer = manufacturer;
	  }

	public String getPartyLoaned(){
			 return partyLoaned;
	  }

	public void setPartyLoaned(String partyLoaned ){
			 this.partyLoaned = partyLoaned;
	  }

	public String getPartyBorrowed(){
			 return partyBorrowed;
	  }

	public void setPartyBorrowed(String partyBorrowed ){
			 this.partyBorrowed = partyBorrowed;
	  }

	public String getOverallStatus(){
			 return overallStatus;
	  }

	public void setOverallStatus(String overallStatus ){
			 this.overallStatus = overallStatus;
	  }

	public String getDamageStatus(){
			 return damageStatus;
	  }

	public void setDamageStatus(String damageStatus ){
			 this.damageStatus = damageStatus;
	  }

	public String getCleanlinessStatus(){
			 return cleanlinessStatus;
	  }

	public void setCleanlinessStatus(String cleanlinessStatus ){
			 this.cleanlinessStatus = cleanlinessStatus;
	  }

	public String getLocation(){
			 return location;
	  }

	public void setLocation(String location ){
			 this.location = location;
	  }

	public String getOwnerStation(){
			 return ownerStation;
	  }

	public void setOwnerStation(String ownerStation ){
			 this.ownerStation = ownerStation;
	  }

	public String getCurrentStation(){
			 return currentStation;
	  }

	public void setCurrentStation(String currentStation ){
			 this.currentStation = currentStation;
	  }

	public String getFacilityType(){
			 return facilityType;
	  }

	public void setFacilityType(String facilityType ){
			 this.facilityType = facilityType;
	  }

	public String getTransitStatus(){
			 return transitStatus;
	  }

	public void setTransitStatus(String transitStatus ){
			 this.transitStatus = transitStatus;
	  }

	public String getUldContour(){
			 return uldContour;
	  }

	public void setUldContour(String uldContour ){
			 this.uldContour = uldContour;
	  }

	public double getTareWeight(){
			 return tareWeight;
	  }

	public void setTareWeight(double tareWeight ){
			 this.tareWeight = tareWeight;
	  }

	public double getMaxGrossWt(){
			 return maxGrossWt;
	  }

	public void setMaxGrossWt(double maxGrossWt ){
			 this.maxGrossWt = maxGrossWt;
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
