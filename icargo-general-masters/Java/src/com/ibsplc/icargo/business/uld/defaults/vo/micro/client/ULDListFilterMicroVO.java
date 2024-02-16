/*
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd
 * Use is subject to license terms.
 *
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.client;

import  com.ibsplc.icargo.framework.micro.serialization.AttributeInfo ;
import  com.ibsplc.icargo.framework.micro.serialization.Exportable ;
/**
 * 
 * @author 
 *
 */

public class ULDListFilterMicroVO extends Exportable {


	private String companyCode;
	private String uldNumber;
	private String uldTypeCode;
	private String airlineCode;
	private String overallStatus;
	private int airlineidentifier;
	private int displayPage;
 	private static final String[] ATTRIBUTE_NAMES ;

	static {
		 ATTRIBUTE_NAMES = new String[7] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "uldTypeCode";
		 ATTRIBUTE_NAMES[3] = "airlineCode";
		 ATTRIBUTE_NAMES[4] = "overallStatus";
		 ATTRIBUTE_NAMES[5] = "airlineidentifier";
		 ATTRIBUTE_NAMES[6] = "displayPage";
	}

	public Object getAttribute(int pos) {
		Object ret = null;
		switch (pos) {
		 case 0: 
				{
		  ret = companyCode;
				break;
		 }
		 case 1: {
          ret = uldNumber;
				break;	
         }
		 case 2: 
				{
		  ret = uldTypeCode;
				break;
		 }
		 case 3:{
          ret = airlineCode;
				break;   
         }
		 case 4: 
				{
		  ret = overallStatus;
				break;
		 }
		 case 5:{
          ret = new Integer(airlineidentifier);
				break;   
         }
		 case 6: 
				{
		  ret = new Integer(displayPage);
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
						uldTypeCode = (String) value;
					break;
					}
			 case 3:
 				
					{
						airlineCode = (String) value;
					break;
					}
			 case 4:
 				
					{
						overallStatus = (String) value;
					break;
					}
			 case 5:
 				
					{
						airlineidentifier = ((Integer) value).intValue();
					break;
					}
			 case 6:
 				
					{
						displayPage = ((Integer) value).intValue();
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
					ret = new AttributeInfo(ATTRIBUTE_NAMES[5], AttributeInfo.INTEGER_TYPE);
				break;
				}
		 case 6:
			 
				{
					ret = new AttributeInfo(ATTRIBUTE_NAMES[6], AttributeInfo.INTEGER_TYPE);
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

	public String getUldTypeCode(){
			 return uldTypeCode;
	  }

	public void setUldTypeCode(String uldTypeCode ){
			 this.uldTypeCode = uldTypeCode;
	  }

	public String getAirlineCode(){
			 return airlineCode;
	  }

	public void setAirlineCode(String airlineCode ){
			 this.airlineCode = airlineCode;
	  }

	public String getOverallStatus(){
			 return overallStatus;
	  }

	public void setOverallStatus(String overallStatus ){
			 this.overallStatus = overallStatus;
	  }

	public int getAirlineidentifier(){
			 return airlineidentifier;
	  }

	public void setAirlineidentifier(int airlineidentifier ){
			 this.airlineidentifier = airlineidentifier;
	  }

	public int getDisplayPage(){
			 return displayPage;
	  }

	public void setDisplayPage(int displayPage ){
			 this.displayPage = displayPage;
	  }


}
