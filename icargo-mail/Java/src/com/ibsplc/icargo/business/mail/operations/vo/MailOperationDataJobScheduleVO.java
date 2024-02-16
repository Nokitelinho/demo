/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailOperationDataJobScheduleVO.java
 * <p>
 * Created by	:	204082
 * Created on	:	25-Jul-2023
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

public class MailOperationDataJobScheduleVO extends JobScheduleVO {
	
	private static final String NO_OF_DAYS = "MAL_OPS_NOOFDAYS";
	private static final String TRIGGER_POINTS = "MAL_OPS_TRIGGERPOINTS";
	private static final String TOLERANCE_RATE = "MAL_OPS_TOLERANCE";
	private static final String POACOD = "MAL_OPS_POACOD";
	private static final String CARRIER_CODE = "MAL_OPS_CARRIERCODE";
	private static final String ORG_OF_MAL_BAGS = "MAL_OPS_ORGOFMALBAGS";

	private Integer noOfDaysToConsider;
	private String triggerPoints;
	private Integer tolerance;
	private String postalAuthorityCode;
	private String carrierCode;
	private String mailbagOrigin;
	
	 private static final Map<String, Integer> map;

	    static {
	        map = new HashMap<>();
	        map.put(NO_OF_DAYS, 1);
	        map.put(TRIGGER_POINTS, 2);
	        map.put(TOLERANCE_RATE, 3);
	        map.put(POACOD, 4);
	        map.put(CARRIER_CODE, 5);
	        map.put(ORG_OF_MAL_BAGS, 6);
	    }
	    
	    
	public Integer getNoOfDaysToConsider() {
		return noOfDaysToConsider;
	}
	public void setNoOfDaysToConsider(Integer noOfDaysToConsider) {
		this.noOfDaysToConsider = noOfDaysToConsider;
	}
	public String getTriggerPoints() {
		return triggerPoints;
	}
	public void setTriggerPoints(String triggerPoints) {
		this.triggerPoints = triggerPoints;
	}
	public Integer getTolerance() {
		return tolerance;
	}
	public void setTolerance(Integer tolerance) {
		this.tolerance = tolerance;
	}
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getMailbagOrigin() {
		return mailbagOrigin;
	}
	public void setMailbagOrigin(String mailbagOrigin) {
		this.mailbagOrigin = mailbagOrigin;
	}
	
	 @Override
     public int getPropertyCount() {
        return map.size();
     }
	 
	 @Override
     public int getIndex(String key) {
        return map.get(key);
     }
	 
	 @Override
	    public String getValue(int index) {
	        switch (index) {
	            case 1:
	                return String.valueOf(this.noOfDaysToConsider);

	            case 2:
	                return this.triggerPoints;

	            case 3:
	                return String.valueOf(this.tolerance);

	            case 4:
	                return this.postalAuthorityCode;
	            case 5:
	                return this.carrierCode;
	            case 6:
	                return this.mailbagOrigin;

	            default:
	                return null;
	        }
	    }

	 @Override
	    public void setValue(int index, String key) {
	        switch (index) {
	            case 1:
	            	 setNoOfDaysToConsider(Integer.parseInt(key));
	                break;

	            case 2:
	                setTriggerPoint(key);
	                break;

	            case 3:
	                setTolerance(Integer.parseInt(key));
	                break;

	            case 4:
	                setPostalAuthorityCode(key);
	                break;
	            case 5:
	                setCarrierCode(key);
	                break;
	            case 6:
	                setMailbagOrigin(key);
	                break;

	            default:
	                break;
	        }
	    }
	 
	
	

}
