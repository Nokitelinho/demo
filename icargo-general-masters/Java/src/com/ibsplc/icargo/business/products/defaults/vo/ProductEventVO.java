/*
 * ProductEventVO.java Created on Jul 4, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 */
public class ProductEventVO extends AbstractVO implements Serializable {

    private static final long serialVersionUID = 2733904975964306776L;

	private String eventCode;

    /**
     * Minimum timegap before the occurance of the milestone
     */
    private double minimumTime;

    /**
     * Maximum timegap before the occurance of the milestone
     */
    private double maximumTime;
    /**
     * Maximum timegap before the occurance of the milestone converted to String
     */
    private String minimumTimeStr;
    /**
     * Maximum timegap before the occurance of the milestone converted to String
     */
    private String maximumTimeStr;
    /**
     * Duration of the milestone
     */
    private int duration;

    /**
     * Indicates whether the milestone
     * external to the customer
     */
    private boolean isExternal;
	
	  /**
     * Indicates whether the milestone is internal to the airline 
     */
    private boolean isInternal;

    /**
     * Identifies whether the milestone is associated to Import or Export
     */
    private String isExport;

    /**
     * offset time from minimum time in minutes before which alert
     * has to be sent
     */
    private double alertTime;

    /**
     * offset time from maximum time in minutes after which alert
     * has to be sent
     */
    private double chaserTime;

    /**
     * Possible values are 'I-Insert','U-Update' and 'D-Delete'
     */
    private String operationFlag;

    /**Possible values are 'S' or'M'
    */
    private String eventType;
    
    private double chaserFrequency;
    
    private int maxNoOfChasers;
    
    private boolean isTransit;

    /** getter method for eventType
    */
    public String getEventType(){
		return eventType;
	}

	/*setter method for eventType
	*/
	public void setEventType(String eventType){
		this.eventType=eventType;
	}

    /**
     * @return Returns the alertTime.
     */
    public double getAlertTime() {
        return alertTime;
    }
    /**
     * @param alertTime The alertTime to set.
     */
    public void setAlertTime(double alertTime) {
        this.alertTime = alertTime;
    }
    /**
     * @return Returns the chaserTime.
     */
    public double getChaserTime() {
        return chaserTime;
    }
    /**
     * @param chaserTime The chaserTime to set.
     */
    public void setChaserTime(double chaserTime) {
        this.chaserTime = chaserTime;
    }

    /**
     * @return Returns the duration.
     */
    public int getDuration() {
        return duration;
    }
    /**
     * @param duration The duration to set.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    /**
     * @return Returns the eventCode.
     */
    public String getEventCode() {
        return eventCode;
    }
    /**
     * @param eventCode The eventCode to set.
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
    /**
     * @return Returns the isExport.
     */
    public String getIsExport() {
        return isExport;
    }
    /**
     * @param isExport The isExport to set.
     */
    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }
   
    /**
     * @return Returns the maximumTime.
     */
    public double getMaximumTime() {
        return maximumTime;
    }
    /**
     * @param maximumTime The maximumTime to set.
     */
    public void setMaximumTime(double maximumTime) {
        this.maximumTime = maximumTime;
    }
    /**
     * @return Returns the minimumTime.
     */
    public double getMinimumTime() {
        return minimumTime;
    }
    /**
     * @param minimumTime The minimumTime to set.
     */
    public void setMinimumTime(double minimumTime) {
        this.minimumTime = minimumTime;
    }

    /**
     * @return Returns the operationFlag.
     */
    public String getOperationFlag() {
        return operationFlag;
    }
    /**
     * @param operationFlag The operationFlag to set.
     */
    public void setOperationFlag(String operationFlag) {
        this.operationFlag = operationFlag;
    }

	public boolean isExternal() {
		return isExternal;
	}
	

	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}
	

	public boolean isInternal() {
		return isInternal;
	}
	

	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}

	public double getChaserFrequency() {
		return chaserFrequency;
	}

	public void setChaserFrequency(double chaserFrequency) {
		this.chaserFrequency = chaserFrequency;
	}

	public int getMaxNoOfChasers() {
		return maxNoOfChasers;
	}

	public void setMaxNoOfChasers(int maxNoOfChasers) {
		this.maxNoOfChasers = maxNoOfChasers;
	}

	/**
	 * @return Returns the maximumTimeStr.
	 */
	public String getMaximumTimeStr() {
		return maximumTimeStr;
	}

	/**
	 * @param maximumTimeStr The maximumTimeStr to set.
	 */
	public void setMaximumTimeStr(String maximumTimeStr) {
		this.maximumTimeStr = maximumTimeStr;
	}

	/**
	 * @return Returns the minimumTimeStr.
	 */
	public String getMinimumTimeStr() {
		return minimumTimeStr;
	}

	/**
	 * @param minimumTimeStr The minimumTimeStr to set.
	 */
	public void setMinimumTimeStr(String minimumTimeStr) {
		this.minimumTimeStr = minimumTimeStr;
	}

	/**
	 * @return Returns the isTransit.
	 */
	public boolean isTransit() {
		return isTransit;
	}

	/**
	 * @param isTransit The isTransit to set.
	 */
	public void setTransit(boolean isTransit) {
		this.isTransit = isTransit;
	}
	
}
