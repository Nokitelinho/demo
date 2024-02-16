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

public class ULDMicroVO extends Exportable {

 
	public static final String MODULE = "uld"; 
	public static final String SUBMODULE = "defaults"; 
	public static final String ENTITY = "uld.defaults.ULD"; 
	private String companyCode; 
	private String uldNumber; 
	private String currentStation; 
	private String location; 
	private String operationalFlag; 
	private String remarks; 
	private String locationType; 
	private String lastUpdateUser; 
	private String lastUpdateTime;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[9] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "currentStation";
		 ATTRIBUTE_NAMES[3] = "location";
		 ATTRIBUTE_NAMES[4] = "operationalFlag";
		 ATTRIBUTE_NAMES[5] = "remarks";
		 ATTRIBUTE_NAMES[6] = "locationType";
		 ATTRIBUTE_NAMES[7] = "lastUpdateUser";
		 ATTRIBUTE_NAMES[8] = "lastUpdateTime";
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
		  ret = (String) currentStation;
		  break;
		 }
		 case 3: 
				 {
		  ret = (String) location;
		  break;
		 }
		 case 4: 
				 {
		  ret = (String) operationalFlag;
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) remarks;
		  break;
		 }
		 case 6: 
				 {
		  ret = (String) locationType;
		  break;
		 }
		 case 7: 
				 {
		  ret = (String) lastUpdateUser;
		  break;
		 }
		 case 8: 
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
			  currentStation = (String) value;
			  break;
			 }
			 case 3: 
					 {
			  location = (String) value;
			  break;
			 }
			 case 4: 
					 {
			  operationalFlag = (String) value;
			  break;
			 }
			 case 5: 
					 {
			  remarks = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  locationType = (String) value;
			  break;
			 }
			 case 7: 
					 {
			  lastUpdateUser = (String) value;
			  break;
			 }
			 case 8: 
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

	public String getCurrentStation(){
			 return currentStation;
	  }

	public void setCurrentStation(String currentStation ){
			 this.currentStation = currentStation;
	  }

	public String getLocation(){
			 return location;
	  }

	public void setLocation(String location ){
			 this.location = location;
	  }

	public String getOperationalFlag(){
			 return operationalFlag;
	  }

	public void setOperationalFlag(String operationalFlag ){
			 this.operationalFlag = operationalFlag;
	  }

	public String getRemarks(){
			 return remarks;
	  }

	public void setRemarks(String remarks ){
			 this.remarks = remarks;
	  }

	public String getLocationType(){
			 return locationType;
	  }

	public void setLocationType(String locationType ){
			 this.locationType = locationType;
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
