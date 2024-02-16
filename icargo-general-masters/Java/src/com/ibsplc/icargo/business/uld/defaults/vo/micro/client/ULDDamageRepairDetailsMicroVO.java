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

public class ULDDamageRepairDetailsMicroVO extends Exportable {

 
	private String companyCode; 
	private String uldNumber; 
	private String damageStatus; 
	private String overallStatus; 
	private String supervisor; 
	private String investigationReport; 
	private String damgePicture; 
	private String lastUpdatedTime; 
	private String lastUpdatedUser; 
	private String currentStation; 
	private Vector uldDamageMicroVOs;
 	private static final String[] ATTRIBUTE_NAMES ; 

	static {
		 ATTRIBUTE_NAMES = new String[11] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "damageStatus";
		 ATTRIBUTE_NAMES[3] = "overallStatus";
		 ATTRIBUTE_NAMES[4] = "supervisor";
		 ATTRIBUTE_NAMES[5] = "investigationReport";
		 ATTRIBUTE_NAMES[6] = "damgePicture";
		 ATTRIBUTE_NAMES[7] = "lastUpdatedTime";
		 ATTRIBUTE_NAMES[8] = "lastUpdatedUser";
		 ATTRIBUTE_NAMES[9] = "currentStation";
		 ATTRIBUTE_NAMES[10] = "uldDamageMicroVOs";
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
		  ret = (String) damageStatus;
		  break;
		 }
		 case 3: 
				 {
		  ret = (String) overallStatus;
		  break;
		 }
		 case 4: 
				 {
		  ret = (String) supervisor;
		  break;
		 }
		 case 5: 
				 {
		  ret = (String) investigationReport;
		  break;
		 }
		 case 6: 
				 {
		  ret = (String) damgePicture;
		  break;
		 }
		 case 7: 
				 {
		  ret = (String) lastUpdatedTime;
		  break;
		 }
		 case 8: 
				 {
		  ret = (String) lastUpdatedUser;
		  break;
		 }
		 case 9: 
				 {
		  ret = (String) currentStation;
		  break;
		 }
		 case 10: 
				 {
		  ret = (Vector) uldDamageMicroVOs;
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
			  damageStatus = (String) value;
			  break;
			 }
			 case 3: 
					 {
			  overallStatus = (String) value;
			  break;
			 }
			 case 4: 
					 {
			  supervisor = (String) value;
			  break;
			 }
			 case 5: 
					 {
			  investigationReport = (String) value;
			  break;
			 }
			 case 6: 
					 {
			  damgePicture = (String) value;
			  break;
			 }
			 case 7: 
					 {
			  lastUpdatedTime = (String) value;
			  break;
			 }
			 case 8: 
					 {
			  lastUpdatedUser = (String) value;
			  break;
			 }
			 case 9: 
					 {
			  currentStation = (String) value;
			  break;
			 }
			 case 10: 
					 {
			  uldDamageMicroVOs = (Vector) value;
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
				ret = new AttributeInfo(ATTRIBUTE_NAMES[10], ULDDamageMicroVO.class);
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

	public String getDamageStatus(){
			 return damageStatus;
	  }

	public void setDamageStatus(String damageStatus ){
			 this.damageStatus = damageStatus;
	  }

	public String getOverallStatus(){
			 return overallStatus;
	  }

	public void setOverallStatus(String overallStatus ){
			 this.overallStatus = overallStatus;
	  }

	public String getSupervisor(){
			 return supervisor;
	  }

	public void setSupervisor(String supervisor ){
			 this.supervisor = supervisor;
	  }

	public String getInvestigationReport(){
			 return investigationReport;
	  }

	public void setInvestigationReport(String investigationReport ){
			 this.investigationReport = investigationReport;
	  }

	public String getDamgePicture(){
			 return damgePicture;
	  }

	public void setDamgePicture(String damgePicture ){
			 this.damgePicture = damgePicture;
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

	public String getCurrentStation(){
			 return currentStation;
	  }

	public void setCurrentStation(String currentStation ){
			 this.currentStation = currentStation;
	  }

	public Vector getUldDamageMicroVOs(){
			 return uldDamageMicroVOs;
	  }

	public void setUldDamageMicroVOs(Vector uldDamageMicroVOs ){
			 this.uldDamageMicroVOs =uldDamageMicroVOs;
	  }


}
