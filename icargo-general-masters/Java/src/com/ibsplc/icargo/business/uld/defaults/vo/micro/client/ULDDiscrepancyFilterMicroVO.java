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

public class ULDDiscrepancyFilterMicroVO extends Exportable {

 
	private String uldNumber; 
	private String reportingStation; 
	private String ownerStation; 
	private int uldOwnerIdentifier; 
	private String companyCode; 
	private String airlineCode; 
	private int pageNumber; 
	private String agentCode; 
	private String agentLocation;
	private String facilityType;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[10] ;
		 ATTRIBUTE_NAMES[0] = "uldNumber";
		 ATTRIBUTE_NAMES[1] = "reportingStation";
		 ATTRIBUTE_NAMES[2] = "ownerStation";
		 ATTRIBUTE_NAMES[3] = "uldOwnerIdentifier";
		 ATTRIBUTE_NAMES[4] = "companyCode";
		 ATTRIBUTE_NAMES[5] = "airlineCode";
		 ATTRIBUTE_NAMES[6] = "pageNumber";
		 ATTRIBUTE_NAMES[7] = "agentCode";
		 ATTRIBUTE_NAMES[8] = "agentLocation";
		 ATTRIBUTE_NAMES[9] = "facilityType";
	}

	public Object getAttribute(int pos) {
		Object ret = null;
		switch (pos) {
		 case 0: 
				 {
		  ret = (String) uldNumber;
				 break;
		 }
		 case 1: {
          ret = (String)reportingStation;
				 break;		
         }
		 case 2: 
				 {
		  ret = (String) ownerStation;
				 break;
		 }
		 case 3:{
          ret = new Integer(uldOwnerIdentifier);
				 break;	 
         }
		 case 4: 
				 {
		  ret = (String) companyCode;
				 break;
		 }
		 case 5: {
          ret = (String)airlineCode;
				 break;	 
         }
		 case 6: 
				 {
		  ret = new Integer(pageNumber);
				 break;
		 }
		 case 7: {
          ret = (String)agentCode;
				 break;	
         }
		 case 8: 
				 {
		  ret = (String) agentLocation;
				 break;
		 }
		 case 9:{
          ret = (String)facilityType;
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
			  uldNumber = (String) value;
					 break;
			 }
			 case 1: {
              reportingStation = (String) value;
					 break;	  
             }
			 case 2: 
					 {
			  ownerStation = (String) value;
					 break;
			 }
			 case 3: {
              uldOwnerIdentifier = ((Integer) value).intValue();
					 break;	
             }
			 case 4: 
					 {
			  companyCode = (String) value;
					 break;
			 }
			 case 5: {
              airlineCode = (String) value;
					 break;	 
             }
			 case 6: 
					 {
			  pageNumber = ((Integer) value).intValue();
					 break;
			 }
			 case 7: {
              agentCode = (String) value;
					 break;	
             }
			 case 8: 
					 {
			  agentLocation = (String) value;
					 break;
			 }
			 case 9: {
              facilityType = (String) value;
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
					ret = new AttributeInfo(ATTRIBUTE_NAMES[3], AttributeInfo.INTEGER_TYPE);
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
		default: {
			ret = null;
		}

		}
	  return ret;
	}

	public String getUldNumber(){
			 return uldNumber;
	  }

	public void setUldNumber(String uldNumber ){
			 this.uldNumber = uldNumber;
	  }

	public String getReportingStation(){
			 return reportingStation;
	  }

	public void setReportingStation(String reportingStation ){
			 this.reportingStation = reportingStation;
	  }

	public String getOwnerStation(){
			 return ownerStation;
	  }

	public void setOwnerStation(String ownerStation ){
			 this.ownerStation = ownerStation;
	  }

	public int getUldOwnerIdentifier(){
			 return uldOwnerIdentifier;
	  }

	public void setUldOwnerIdentifier(int uldOwnerIdentifier ){
			 this.uldOwnerIdentifier = uldOwnerIdentifier;
	  }

	public String getCompanyCode(){
			 return companyCode;
	  }

	public void setCompanyCode(String companyCode ){
			 this.companyCode = companyCode;
	  }

	public String getAirlineCode(){
			 return airlineCode;
	  }

	public void setAirlineCode(String airlineCode ){
			 this.airlineCode = airlineCode;
	  }

	public int getPageNumber(){
			 return pageNumber;
	  }

	public void setPageNumber(int pageNumber ){
			 this.pageNumber = pageNumber;
	  }

	public String getAgentCode(){
			 return agentCode;
	  }

	public void setAgentCode(String agentCode ){
			 this.agentCode = agentCode;
	  }

	public String getAgentLocation(){
			 return agentLocation;
	  }

	public void setAgentLocation(String agentLocation ){
			 this.agentLocation = agentLocation;
	  }

	public String getFacilityType(){
			 return facilityType;
	  }

	public void setFacilityType(String facilityType ){
			 this.facilityType = facilityType;
	  }


}
