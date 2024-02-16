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

public class RecordULDMovementMicroVO extends Exportable {


	private String companyCode;
	private String uldNumber;
	private Vector ULDMovementMicroVOs;
 	private static final String[] ATTRIBUTE_NAMES ;

	static {
		 ATTRIBUTE_NAMES = new String[3] ;
		 ATTRIBUTE_NAMES[0] = "companyCode";
		 ATTRIBUTE_NAMES[1] = "uldNumber";
		 ATTRIBUTE_NAMES[2] = "ULDMovementMicroVOs";
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
		  ret = (Vector) ULDMovementMicroVOs;
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
			  ULDMovementMicroVOs = (Vector) value;
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
				ret = new AttributeInfo(ATTRIBUTE_NAMES[2], true, ULDMovementMicroVO.class);
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

	public Vector getULDMovementMicroVOs(){
			 return ULDMovementMicroVOs;
	  }

	public void setULDMovementMicroVOs(Vector ULDMovementMicroVOs ){
			 this.ULDMovementMicroVOs = ULDMovementMicroVOs;
	  }


}
