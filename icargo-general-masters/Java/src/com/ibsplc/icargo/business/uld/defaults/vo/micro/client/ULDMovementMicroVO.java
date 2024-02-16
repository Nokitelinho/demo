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

public class ULDMovementMicroVO extends Exportable {

 
	private String carrierCode; 
	private String companyCode; 
	private String uldNumber; 
	private int flightCarrierIdentifier; 
	private String flightNumber; 
	private String flightDate; 
	private String flightDateString; 
	private String pointOfLading; 
	private String pointOfUnLading; 
	private String content; 
	private boolean dummyMovement; 
	private String remark; 
	private long movementSequenceNumber; 
	private boolean updateCurrentStation; 
	private String currentStation; 
	private String lastUpdatedTime; 
	private String lastUpdatedUser; 
	private boolean discrepancyToBeSolved;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[18] ;
		 ATTRIBUTE_NAMES[0] = "carrierCode";
		 ATTRIBUTE_NAMES[1] = "companyCode";
		 ATTRIBUTE_NAMES[2] = "uldNumber";
		 ATTRIBUTE_NAMES[3] = "flightCarrierIdentifier";
		 ATTRIBUTE_NAMES[4] = "flightNumber";
		 ATTRIBUTE_NAMES[5] = "flightDate";
		 ATTRIBUTE_NAMES[6] = "flightDateString";
		 ATTRIBUTE_NAMES[7] = "pointOfLading";
		 ATTRIBUTE_NAMES[8] = "pointOfUnLading";
		 ATTRIBUTE_NAMES[9] = "content";
		 ATTRIBUTE_NAMES[10] = "dummyMovement";
		 ATTRIBUTE_NAMES[11] = "remark";
		 ATTRIBUTE_NAMES[12] = "movementSequenceNumber";
		 ATTRIBUTE_NAMES[13] = "updateCurrentStation";
		 ATTRIBUTE_NAMES[14] = "currentStation";
		 ATTRIBUTE_NAMES[15] = "lastUpdatedTime";
		 ATTRIBUTE_NAMES[16] = "lastUpdatedUser";
		 ATTRIBUTE_NAMES[17] = "discrepancyToBeSolved";
	}

	public Object getAttribute(int pos) {
		Object ret = null;
		switch (pos) {
		 case 0: 
				{
		  ret = carrierCode;
		  break;
		 }
		 case 1: 
				{
		  ret = companyCode;
		  break;
		 }
		 case 2: 
				{
		  ret = uldNumber;
		  break;
		 }
		 case 3: 
				{
		  ret = new Integer(flightCarrierIdentifier);
		  break;
		 }
		 case 4: 
				{
		  ret = flightNumber;
		  break;
		 }
		 case 5: 
				{
		  ret = flightDate;
		  break;
		 }
		 case 6: 
				{
		  ret = flightDateString;
		  break;
		 }
		 case 7: 
				{
		  ret = pointOfLading;
		  break;
		 }
		 case 8: 
				{
		  ret = pointOfUnLading;
		  break;
		 }
		 case 9: 
				{
		  ret = content;
		  break;
		 }
		 case 10: 
				{
		  ret = new Boolean(dummyMovement);
		  break;
		 }
		 case 11: 
				{
		  ret = remark;
		  break;
		 }
		 case 12: 
				{
		  ret = new Long(movementSequenceNumber);
		  break;
		 }
		 case 13: 
				{
		  ret = new Boolean(updateCurrentStation);
		  break;
		 }
		 case 14: 
				{
		  ret = currentStation;
		  break;
		 }
		 case 15: 
				{
		  ret = lastUpdatedTime;
		  break;
		 }
		 case 16: 
				{
		  ret = lastUpdatedUser;
		  break;
		 }
		 case 17: 
				{
		  ret = new Boolean(discrepancyToBeSolved);
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
 					carrierCode = (String) value;
 					break;
 				}
			 case 1: 
 				{
 					companyCode = (String) value;
 					break;
 				}
 				
			 case 2: 
 				{
					uldNumber = (String) value;
					break;
				}
					
			 case 3: 
 				{
					flightCarrierIdentifier = ((Integer) value).intValue();
					break;
				}
					
			 case 4: 
 				{
					flightNumber = (String) value;
					break;
				}
				
			 case 5: 
 				{
 					flightDate = (String) value;
					break;
				}
					
			 case 6: 
 				{
					flightDateString = (String) value;
					break;
				}
			 case 7: 
 				{
					pointOfLading = (String) value;
					break;
				}
				
			 case 8: 
 				{
					pointOfUnLading = (String) value;
					break;
 				}
			 case 9: 
 				{
					content = (String) value;
					break;
				}
				 case 10: 
 				{
					dummyMovement = ((Boolean) value).booleanValue();
					break;
				}
				
			 case 11: 
 				{
					remark = (String) value;
					break;
				}
				
			 case 12: 
 				{
					movementSequenceNumber = ((Long) value).longValue();
					break;
				}
			 case 13: 
 				{
					updateCurrentStation = ((Boolean) value).booleanValue();
					break;
				}
			case 14: 
 				{
					currentStation = (String) value;
					break;
				}
			 case 15: 
 				{
					lastUpdatedTime = (String) value;
					break;
				}
			case 16: 
 				{
					lastUpdatedUser = (String) value;
					break;
				}
			case 17: 
 				{
					discrepancyToBeSolved = ((Boolean) value).booleanValue();
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
				ret = new AttributeInfo(ATTRIBUTE_NAMES[10], AttributeInfo.BOOLEAN_TYPE);
				break;
			}
		case 11:
			 {
				ret = new AttributeInfo(ATTRIBUTE_NAMES[11], AttributeInfo.STRING_TYPE);
				break;
			}
		 case 12:
			{
				ret = new AttributeInfo(ATTRIBUTE_NAMES[12], AttributeInfo.LONG_TYPE);
				break;
			}
		 case 13:
			 {
				ret = new AttributeInfo(ATTRIBUTE_NAMES[13], AttributeInfo.BOOLEAN_TYPE);
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
				ret = new AttributeInfo(ATTRIBUTE_NAMES[17], AttributeInfo.BOOLEAN_TYPE);
				break;
			}
		 default: {
				ret = null;
			}

		}
	  return ret;
	}

	public String getCarrierCode(){
			 return carrierCode;
	  }

	public void setCarrierCode(String carrierCode ){
			 this.carrierCode = carrierCode;
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

	public int getFlightCarrierIdentifier(){
			 return flightCarrierIdentifier;
	  }

	public void setFlightCarrierIdentifier(int flightCarrierIdentifier ){
			 this.flightCarrierIdentifier = flightCarrierIdentifier;
	  }

	public String getFlightNumber(){
			 return flightNumber;
	  }

	public void setFlightNumber(String flightNumber ){
			 this.flightNumber = flightNumber;
	  }

	public String getFlightDate(){
			 return flightDate;
	  }

	public void setFlightDate(String flightDate ){
			 this.flightDate = flightDate;
	  }

	public String getFlightDateString(){
			 return flightDateString;
	  }

	public void setFlightDateString(String flightDateString ){
			 this.flightDateString = flightDateString;
	  }

	public String getPointOfLading(){
			 return pointOfLading;
	  }

	public void setPointOfLading(String pointOfLading ){
			 this.pointOfLading = pointOfLading;
	  }

	public String getPointOfUnLading(){
			 return pointOfUnLading;
	  }

	public void setPointOfUnLading(String pointOfUnLading ){
			 this.pointOfUnLading = pointOfUnLading;
	  }

	public String getContent(){
			 return content;
	  }

	public void setContent(String content ){
			 this.content = content;
	  }

	public boolean getDummyMovement(){
			 return dummyMovement;
	  }

	public void setDummyMovement(boolean dummyMovement ){
			 this.dummyMovement = dummyMovement;
	  }

	public String getRemark(){
			 return remark;
	  }

	public void setRemark(String remark ){
			 this.remark = remark;
	  }

	public long getMovementSequenceNumber(){
			 return movementSequenceNumber;
	  }

	public void setMovementSequenceNumber(long movementSequenceNumber ){
			 this.movementSequenceNumber = movementSequenceNumber;
	  }

	public boolean getUpdateCurrentStation(){
			 return updateCurrentStation;
	  }

	public void setUpdateCurrentStation(boolean updateCurrentStation ){
			 this.updateCurrentStation = updateCurrentStation;
	  }

	public String getCurrentStation(){
			 return currentStation;
	  }

	public void setCurrentStation(String currentStation ){
			 this.currentStation = currentStation;
	  }

	public String getLastUpdatedTime(){
			 return lastUpdatedTime;
	  }

	public void setLastUpdatedTime(String lastUpdatedTime ){
			 this.lastUpdatedTime = lastUpdatedTime;
	  }

	public String getLastUpdatedUser(){
			 return lastUpdatedUser;
	  }

	public void setLastUpdatedUser(String lastUpdatedUser ){
			 this.lastUpdatedUser = lastUpdatedUser;
	  }

	public boolean getDiscrepancyToBeSolved(){
			 return discrepancyToBeSolved;
	  }

	public void setDiscrepancyToBeSolved(boolean discrepancyToBeSolved ){
			 this.discrepancyToBeSolved = discrepancyToBeSolved;
	  }


}
