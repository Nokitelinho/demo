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

public class ULDAirportLocationMicroVO extends Exportable {

 
	private String companyCode; 
	private String airportCode; 
	private String facilityType; 
	private String sequenceNumber; 
	private String facilityCode; 
	private String description; 
	private String operationFlag; 
	private String defaultFlag;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[8] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "airportCode";
		 ATTRIBUTE_NAMES[2] = "facilityType";
		 ATTRIBUTE_NAMES[3] = "sequenceNumber";
		 ATTRIBUTE_NAMES[4] = "facilityCode";
		 ATTRIBUTE_NAMES[5] = "description";
		 ATTRIBUTE_NAMES[6] = "operationFlag";
		 ATTRIBUTE_NAMES[7] = "defaultFlag";
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
		  ret = (String) airportCode;
		  break;
		 }
		 case 2: 
				 {
		  ret = (String) facilityType;
		  break;
		 }
		 case 3: 
				 {
		  ret = (String) sequenceNumber;
		  break;
		 }
		 case 4: 
				 {
		  ret = (String) facilityCode;
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) description;
		  break;
		 }
		 case 6: 
				 {
		  ret = (String) operationFlag;
		  break;
		 }
		 case 7: 
				 {
		  ret = (String) defaultFlag;
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
			  airportCode = (String) value;
			  break;
			 }
			 case 2: 
					 {
			  facilityType = (String) value;
			  break;
			 }
			 case 3: 
					 {
			  sequenceNumber = (String) value;
			  break;
			 }
			 case 4: 
					 {
			  facilityCode = (String) value;
			  break;
			 }
			 case 5: 
					 {
			  description = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  operationFlag = (String) value;
			  break;
			 }
			 case 7: 
					 {
			  defaultFlag = (String) value;
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
				ret = new AttributeInfo(ATTRIBUTE_NAMES[6], AttributeInfo.STRING_TYPE);
				break;
			}
			
		 case 7:
			{
				ret = new AttributeInfo(ATTRIBUTE_NAMES[7], AttributeInfo.STRING_TYPE);
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

	public String getAirportCode(){
			 return airportCode;
	  }

	public void setAirportCode(String airportCode ){
			 this.airportCode = airportCode;
	  }

	public String getFacilityType(){
			 return facilityType;
	  }

	public void setFacilityType(String facilityType ){
			 this.facilityType = facilityType;
	  }

	public String getSequenceNumber(){
			 return sequenceNumber;
	  }

	public void setSequenceNumber(String sequenceNumber ){
			 this.sequenceNumber = sequenceNumber;
	  }

	public String getFacilityCode(){
			 return facilityCode;
	  }

	public void setFacilityCode(String facilityCode ){
			 this.facilityCode = facilityCode;
	  }

	public String getDescription(){
			 return description;
	  }

	public void setDescription(String description ){
			 this.description = description;
	  }

	public String getOperationFlag(){
			 return operationFlag;
	  }

	public void setOperationFlag(String operationFlag ){
			 this.operationFlag = operationFlag;
	  }

	public String getDefaultFlag(){
			 return defaultFlag;
	  }

	public void setDefaultFlag(String defaultFlag ){
			 this.defaultFlag = defaultFlag;
	  }


}
