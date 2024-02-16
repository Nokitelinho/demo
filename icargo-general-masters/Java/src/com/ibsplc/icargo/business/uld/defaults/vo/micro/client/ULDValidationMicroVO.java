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

public class ULDValidationMicroVO extends Exportable {

 
	private String companyCode; 
	private String uldNumber; 
	private String uldContour; 
	private double tareWeight; 
	private double displayTareWeight; 
	private String displayTareWeightUnit; 
	private double structuralWeight; 
	private double displayStructuralWeight; 
	private String displayStructuralWeightUnit; 
	private double baseLength; 
	private double displayBaseLength; 
	private double baseWidth; 
	private double displayBaseWidth; 
	private double baseHeight; 
	private double displayBaseHeight; 
	private String displayDimensionUnit; 
	private String uldType; 
	private String operationalAirlineIdentifier; 
	private int ownerAirlineIdentifier; 
	private String ownerAirlineCode; 
	private String currentStation; 
	private String ownerStation; 
	private String overallStatus; 
	private String cleanlinessStatus; 
	private String damageStatus; 
	private String location; 
	private String vendor; 
	private String manufacturer; 
	private String uldSerialNumber; 
	private String purchaseDate; 
	private String purchaseInvoiceNumber; 
	private double uldPrice; 
	private String uldPriceUnit; 
	private double iataReplacementCost; 
	private String iataReplacementCostUnit; 
	private double currentValue; 
	private String currentValueUnit; 
	private int loanReferenceNumber; 
	private int borrowReferenceNumber; 
	private String lastUpdateTime; 
	private String lastUpdateUser;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[41] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "uldContour";
		 ATTRIBUTE_NAMES[3] = "tareWeight";
		 ATTRIBUTE_NAMES[4] = "displayTareWeight";
		 ATTRIBUTE_NAMES[5] = "displayTareWeightUnit";
		 ATTRIBUTE_NAMES[6] = "structuralWeight";
		 ATTRIBUTE_NAMES[7] = "displayStructuralWeight";
		 ATTRIBUTE_NAMES[8] = "displayStructuralWeightUnit";
		 ATTRIBUTE_NAMES[9] = "baseLength";
		 ATTRIBUTE_NAMES[10] = "displayBaseLength";
		 ATTRIBUTE_NAMES[11] = "baseWidth";
		 ATTRIBUTE_NAMES[12] = "displayBaseWidth";
		 ATTRIBUTE_NAMES[13] = "baseHeight";
		 ATTRIBUTE_NAMES[14] = "displayBaseHeight";
		 ATTRIBUTE_NAMES[15] = "displayDimensionUnit";
		 ATTRIBUTE_NAMES[16] = "uldType";
		 ATTRIBUTE_NAMES[17] = "operationalAirlineIdentifier";
		 ATTRIBUTE_NAMES[18] = "ownerAirlineIdentifier";
		 ATTRIBUTE_NAMES[19] = "ownerAirlineCode";
		 ATTRIBUTE_NAMES[20] = "currentStation";
		 ATTRIBUTE_NAMES[21] = "ownerStation";
		 ATTRIBUTE_NAMES[22] = "overallStatus";
		 ATTRIBUTE_NAMES[23] = "cleanlinessStatus";
		 ATTRIBUTE_NAMES[24] = "damageStatus";
		 ATTRIBUTE_NAMES[25] = "location";
		 ATTRIBUTE_NAMES[26] = "vendor";
		 ATTRIBUTE_NAMES[27] = "manufacturer";
		 ATTRIBUTE_NAMES[28] = "uldSerialNumber";
		 ATTRIBUTE_NAMES[29] = "purchaseDate";
		 ATTRIBUTE_NAMES[30] = "purchaseInvoiceNumber";
		 ATTRIBUTE_NAMES[31] = "uldPrice";
		 ATTRIBUTE_NAMES[32] = "uldPriceUnit";
		 ATTRIBUTE_NAMES[33] = "iataReplacementCost";
		 ATTRIBUTE_NAMES[34] = "iataReplacementCostUnit";
		 ATTRIBUTE_NAMES[35] = "currentValue";
		 ATTRIBUTE_NAMES[36] = "currentValueUnit";
		 ATTRIBUTE_NAMES[37] = "loanReferenceNumber";
		 ATTRIBUTE_NAMES[38] = "borrowReferenceNumber";
		 ATTRIBUTE_NAMES[39] = "lastUpdateTime";
		 ATTRIBUTE_NAMES[40] = "lastUpdateUser";
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
		  ret = (String) uldContour;
		  break;
		 }
		 case 3: 
				 {
		  ret = new Double(tareWeight);
		  break;
		 }
		 case 4: 
				 {
		  ret = new Double(displayTareWeight);
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) displayTareWeightUnit;
		  break;
		 }
		 case 6: 
				 {
		  ret = new Double(structuralWeight);
		  break;
		 }
		 case 7: 
				 {
		  ret = new Double(displayStructuralWeight);
		  break;
		 }
		 case 8: 
				 {
		  ret = (String) displayStructuralWeightUnit;
		  break;
		 }
		 case 9: 
				 {
		  ret = new Double(baseLength);
		  break;
		 }
		 case 10: 
				 {
		  ret = new Double(displayBaseLength);
		  break;
		 }
		 case 11: 
				 {
		  ret = new Double(baseWidth);
		  break;
		 }
		 case 12: 
				 {
		  ret = new Double(displayBaseWidth);
		  break;
		 }
		 case 13: 
				 {
		  ret = new Double(baseHeight);
		  break;
		 }
		 case 14: 
				 {
		  ret = new Double(displayBaseHeight);
		  break;
		 }
		 case 15: 
				 {
		  ret = (String) displayDimensionUnit;
		  break;
		 }
		 case 16: 
				 {
		  ret = (String) uldType;
		  break;
		 }
		 case 17: 
				 {
		  ret = (String) operationalAirlineIdentifier;
		  break;
		 }
		 case 18: 
				 {
		  ret = new Integer(ownerAirlineIdentifier);
		  break;
		 }
		 case 19: 
				 {
		  ret = (String) ownerAirlineCode;
		  break;
		 }
		 case 20: 
				 {
		  ret = (String) currentStation;
		  break;
		 }
		 case 21: 
				 {
		  ret = (String) ownerStation;
		  break;
		 }
		 case 22: 
				 {
		  ret = (String) overallStatus;
		  break;
		 }
		 case 23: 
				 {
		  ret = (String) cleanlinessStatus;
		  break;
		 }
		 case 24: 
				 {
		  ret = (String) damageStatus;
		  break;
		 }
		 case 25: 
				 {
		  ret = (String) location;
		  break;
		 }
		 case 26: 
				 {
		  ret = (String) vendor;
		  break;
		 }
		 case 27: 
				 {
		  ret = (String) manufacturer;
		  break;
		 }
		 case 28: 
				 {
		  ret = (String) uldSerialNumber;
		  break;
		 }
		 case 29: 
				 {
		  ret = (String) purchaseDate;
		  break;
		 }
		 case 30: 
				 {
		  ret = (String) purchaseInvoiceNumber;
		  break;
		 }
		 case 31: 
				 {
		  ret = new Double(uldPrice);
		  break;
		 }
		 case 32: 
				 {
		  ret = (String) uldPriceUnit;
		  break;
		 }
		 case 33: 
				 {
		  ret = new Double(iataReplacementCost);
		  break;
		 }
		 case 34: 
				 {
		  ret = (String) iataReplacementCostUnit;
		  break;
		 }
		 case 35: 
				 {
		  ret = new Double(currentValue);
		  break;
		 }
		 case 36: 
				 {
		  ret = (String) currentValueUnit;
		  break;
		 }
		 case 37: 
				 {
		  ret = new Integer(loanReferenceNumber);
		  break;
		 }
		 case 38: 
				 {
		  ret = new Integer(borrowReferenceNumber);
		  break;
		 }
		 case 39: 
				 {
		  ret = (String) lastUpdateTime;
		  break;
		 }
		 case 40: 
				 {
		  ret = (String) lastUpdateUser;
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
			  uldContour = (String) value;
			  break;
			 }
			 case 3: 
					 {
			  tareWeight = ((Double) value).doubleValue();
			  break;
			 }
			 case 4: 
					 {
			  displayTareWeight = ((Double) value).doubleValue();
			  break;
			 }
			 case 5: 
					 {
			  displayTareWeightUnit = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  structuralWeight = ((Double) value).doubleValue();
			  break;
			 }
			 case 7: 
					 {
			  displayStructuralWeight = ((Double) value).doubleValue();
			  break;
			 }
			 case 8: 
					 {
			  displayStructuralWeightUnit = (String) value;
			  break;
			 }
			 case 9: 
					 {
			  baseLength = ((Double) value).doubleValue();
			  break;
			 }
			 case 10: 
					 {
			  displayBaseLength = ((Double) value).doubleValue();
			  break;
			 }
			 case 11: 
					 {
			  baseWidth = ((Double) value).doubleValue();
			  break;
			 }
			 case 12: 
					 {
			  displayBaseWidth = ((Double) value).doubleValue();
			  break;
			 }
			 case 13: 
					 {
			  baseHeight = ((Double) value).doubleValue();
			  break;
			 }
			 case 14: 
					 {
			  displayBaseHeight = ((Double) value).doubleValue();
			  break;
			 }
			 case 15: 
					 {
			  displayDimensionUnit = (String) value;
			  break;
			 }
			 case 16: 
					 {
			  uldType = (String) value;
			  break;
			 }
			 case 17: 
					 {
			  operationalAirlineIdentifier = (String) value;
			  break;
			 }
			 case 18: 
					 {
			  ownerAirlineIdentifier = ((Integer) value).intValue();
			  break;
			 }
			 case 19: 
					 {
			  ownerAirlineCode = (String) value;
			  break;
			 }
			 case 20: 
					 {
			  currentStation = (String) value;
			  break;
			 }
			 case 21: 
					 {
			  ownerStation = (String) value;
			  break;
			 }
			 case 22: 
					 {
			  overallStatus = (String) value;
			  break;
			 }
			 case 23: 
					 {
			  cleanlinessStatus = (String) value;
			  break;
			 }
			 case 24: 
					 {
			  damageStatus = (String) value;
			  break;
			 }
			 case 25: 
					 {
			  location = (String) value;
			  break;
			 }
			 case 26: 
					 {
			  vendor = (String) value;
			  break;
			 }
			 case 27: 
					 {
			  manufacturer = (String) value;
			  break;
			 }
			 case 28: 
					 {
			  uldSerialNumber = (String) value;
			  break;
			 }
			 case 29: 
					 {
			  purchaseDate = (String) value;
			  break;
			 }
			 case 30: 
					 {
			  purchaseInvoiceNumber = (String) value;
			  break;
			 }
			 case 31: 
					 {
			  uldPrice = ((Double) value).doubleValue();
			  break;
			 }
			 case 32: 
					 {
			  uldPriceUnit = (String) value;
			  break;
			 }
			 case 33: 
					 {
			  iataReplacementCost = ((Double) value).doubleValue();
			  break;
			 }
			 case 34: 
					 {
			  iataReplacementCostUnit = (String) value;
			  break;
			 }
			 case 35: 
					 {
			  currentValue = ((Double) value).doubleValue();
			  break;
			 }
			 case 36: 
					 {
			  currentValueUnit = (String) value;
			  break;
			 }
			 case 37: 
					 {
			  loanReferenceNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 38: 
					 {
			  borrowReferenceNumber = ((Integer) value).intValue();
			  break;
			 }
			 case 39: 
					 {
			  lastUpdateTime = (String) value;
			  break;
			 }
			 case 40: 
					 {
			  lastUpdateUser = (String) value;
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[3], AttributeInfo.DOUBLE_TYPE);
						break;
					}
			
		 case 4:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[4], AttributeInfo.DOUBLE_TYPE);
						break;
					}
			
		 case 5:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[5], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 6:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[6], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 7:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[7], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 8:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[8], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 9:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[9], AttributeInfo.DOUBLE_TYPE);
						break;
					}
			
		 case 10:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[10], AttributeInfo.DOUBLE_TYPE);
						break;
					}
				
		 case 11:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[11], AttributeInfo.DOUBLE_TYPE);
						break;
					}
		 case 12:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[12], AttributeInfo.DOUBLE_TYPE);
						break;
					}
			
		 case 13:
			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[13], AttributeInfo.DOUBLE_TYPE);
						break;
					}
		 case 14:
				{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[14], AttributeInfo.DOUBLE_TYPE);
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
						ret = new AttributeInfo(ATTRIBUTE_NAMES[18], AttributeInfo.INTEGER_TYPE);
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
		 case 21:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[21], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 22:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[22], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 23:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[23], AttributeInfo.STRING_TYPE);
						break;
		 			}
		 case 24:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[24], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 25:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[25], AttributeInfo.STRING_TYPE);
						break;
		 			}
		case 26:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[26], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 27:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[27], AttributeInfo.STRING_TYPE);
						break;
					}
		 case 28:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[28], AttributeInfo.STRING_TYPE);
						break;
					}
			
		 case 29:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[29], AttributeInfo.STRING_TYPE);
						break;
					}
		
		 case 30:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[30], AttributeInfo.STRING_TYPE);
						break;
					}
			
		 case 31:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[31], AttributeInfo.DOUBLE_TYPE);
						break;
					}
		
		 case 32:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[32], AttributeInfo.STRING_TYPE);
						break;
					}
			
		 case 33:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[33], AttributeInfo.DOUBLE_TYPE);
						break;
					}
		 case 34:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[34], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 35:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[35], AttributeInfo.DOUBLE_TYPE);
						break;
					}
			
		 case 36:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[36], AttributeInfo.STRING_TYPE);
						break;
					}
			
		 case 37:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[37], AttributeInfo.INTEGER_TYPE);
						break;
					}
			
		 case 38:
					{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[38], AttributeInfo.INTEGER_TYPE);
						break;
					}
			
		 case 39:
			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[39], AttributeInfo.STRING_TYPE);
						break;
					}
				
		 case 40:
		 			{
						ret = new AttributeInfo(ATTRIBUTE_NAMES[40], AttributeInfo.STRING_TYPE);
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

	public double getDisplayTareWeight(){
			 return displayTareWeight;
	  }

	public void setDisplayTareWeight(double displayTareWeight ){
			 this.displayTareWeight = displayTareWeight;
	  }

	public String getDisplayTareWeightUnit(){
			 return displayTareWeightUnit;
	  }

	public void setDisplayTareWeightUnit(String displayTareWeightUnit ){
			 this.displayTareWeightUnit = displayTareWeightUnit;
	  }

	public double getStructuralWeight(){
			 return structuralWeight;
	  }

	public void setStructuralWeight(double structuralWeight ){
			 this.structuralWeight = structuralWeight;
	  }

	public double getDisplayStructuralWeight(){
			 return displayStructuralWeight;
	  }

	public void setDisplayStructuralWeight(double displayStructuralWeight ){
			 this.displayStructuralWeight = displayStructuralWeight;
	  }

	public String getDisplayStructuralWeightUnit(){
			 return displayStructuralWeightUnit;
	  }

	public void setDisplayStructuralWeightUnit(String displayStructuralWeightUnit ){
			 this.displayStructuralWeightUnit = displayStructuralWeightUnit;
	  }

	public double getBaseLength(){
			 return baseLength;
	  }

	public void setBaseLength(double baseLength ){
			 this.baseLength = baseLength;
	  }

	public double getDisplayBaseLength(){
			 return displayBaseLength;
	  }

	public void setDisplayBaseLength(double displayBaseLength ){
			 this.displayBaseLength = displayBaseLength;
	  }

	public double getBaseWidth(){
			 return baseWidth;
	  }

	public void setBaseWidth(double baseWidth ){
			 this.baseWidth = baseWidth;
	  }

	public double getDisplayBaseWidth(){
			 return displayBaseWidth;
	  }

	public void setDisplayBaseWidth(double displayBaseWidth ){
			 this.displayBaseWidth = displayBaseWidth;
	  }

	public double getBaseHeight(){
			 return baseHeight;
	  }

	public void setBaseHeight(double baseHeight ){
			 this.baseHeight = baseHeight;
	  }

	public double getDisplayBaseHeight(){
			 return displayBaseHeight;
	  }

	public void setDisplayBaseHeight(double displayBaseHeight ){
			 this.displayBaseHeight = displayBaseHeight;
	  }

	public String getDisplayDimensionUnit(){
			 return displayDimensionUnit;
	  }

	public void setDisplayDimensionUnit(String displayDimensionUnit ){
			 this.displayDimensionUnit = displayDimensionUnit;
	  }

	public String getUldType(){
			 return uldType;
	  }

	public void setUldType(String uldType ){
			 this.uldType = uldType;
	  }

	public String getOperationalAirlineIdentifier(){
			 return operationalAirlineIdentifier;
	  }

	public void setOperationalAirlineIdentifier(String operationalAirlineIdentifier ){
			 this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	  }

	public int getOwnerAirlineIdentifier(){
			 return ownerAirlineIdentifier;
	  }

	public void setOwnerAirlineIdentifier(int ownerAirlineIdentifier ){
			 this.ownerAirlineIdentifier = ownerAirlineIdentifier;
	  }

	public String getOwnerAirlineCode(){
			 return ownerAirlineCode;
	  }

	public void setOwnerAirlineCode(String ownerAirlineCode ){
			 this.ownerAirlineCode = ownerAirlineCode;
	  }

	public String getCurrentStation(){
			 return currentStation;
	  }

	public void setCurrentStation(String currentStation ){
			 this.currentStation = currentStation;
	  }

	public String getOwnerStation(){
			 return ownerStation;
	  }

	public void setOwnerStation(String ownerStation ){
			 this.ownerStation = ownerStation;
	  }

	public String getOverallStatus(){
			 return overallStatus;
	  }

	public void setOverallStatus(String overallStatus ){
			 this.overallStatus = overallStatus;
	  }

	public String getCleanlinessStatus(){
			 return cleanlinessStatus;
	  }

	public void setCleanlinessStatus(String cleanlinessStatus ){
			 this.cleanlinessStatus = cleanlinessStatus;
	  }

	public String getDamageStatus(){
			 return damageStatus;
	  }

	public void setDamageStatus(String damageStatus ){
			 this.damageStatus = damageStatus;
	  }

	public String getLocation(){
			 return location;
	  }

	public void setLocation(String location ){
			 this.location = location;
	  }

	public String getVendor(){
			 return vendor;
	  }

	public void setVendor(String vendor ){
			 this.vendor = vendor;
	  }

	public String getManufacturer(){
			 return manufacturer;
	  }

	public void setManufacturer(String manufacturer ){
			 this.manufacturer = manufacturer;
	  }

	public String getUldSerialNumber(){
			 return uldSerialNumber;
	  }

	public void setUldSerialNumber(String uldSerialNumber ){
			 this.uldSerialNumber = uldSerialNumber;
	  }

	public String getPurchaseDate(){
			 return purchaseDate;
	  }

	public void setPurchaseDate(String purchaseDate ){
			 this.purchaseDate = purchaseDate;
	  }

	public String getPurchaseInvoiceNumber(){
			 return purchaseInvoiceNumber;
	  }

	public void setPurchaseInvoiceNumber(String purchaseInvoiceNumber ){
			 this.purchaseInvoiceNumber = purchaseInvoiceNumber;
	  }

	public double getUldPrice(){
			 return uldPrice;
	  }

	public void setUldPrice(double uldPrice ){
			 this.uldPrice = uldPrice;
	  }

	public String getUldPriceUnit(){
			 return uldPriceUnit;
	  }

	public void setUldPriceUnit(String uldPriceUnit ){
			 this.uldPriceUnit = uldPriceUnit;
	  }

	public double getIataReplacementCost(){
			 return iataReplacementCost;
	  }

	public void setIataReplacementCost(double iataReplacementCost ){
			 this.iataReplacementCost = iataReplacementCost;
	  }

	public String getIataReplacementCostUnit(){
			 return iataReplacementCostUnit;
	  }

	public void setIataReplacementCostUnit(String iataReplacementCostUnit ){
			 this.iataReplacementCostUnit = iataReplacementCostUnit;
	  }

	public double getCurrentValue(){
			 return currentValue;
	  }

	public void setCurrentValue(double currentValue ){
			 this.currentValue = currentValue;
	  }

	public String getCurrentValueUnit(){
			 return currentValueUnit;
	  }

	public void setCurrentValueUnit(String currentValueUnit ){
			 this.currentValueUnit = currentValueUnit;
	  }

	public int getLoanReferenceNumber(){
			 return loanReferenceNumber;
	  }

	public void setLoanReferenceNumber(int loanReferenceNumber ){
			 this.loanReferenceNumber = loanReferenceNumber;
	  }

	public int getBorrowReferenceNumber(){
			 return borrowReferenceNumber;
	  }

	public void setBorrowReferenceNumber(int borrowReferenceNumber ){
			 this.borrowReferenceNumber = borrowReferenceNumber;
	  }

	public String getLastUpdateTime(){
			 return lastUpdateTime;
	  }

	public void setLastUpdateTime(String lastUpdateTime ){
			 this.lastUpdateTime = lastUpdateTime;
	  }

	public String getLastUpdateUser(){
			 return lastUpdateUser;
	  }

	public void setLastUpdateUser(String lastUpdateUser ){
			 this.lastUpdateUser = lastUpdateUser;
	  }


}
