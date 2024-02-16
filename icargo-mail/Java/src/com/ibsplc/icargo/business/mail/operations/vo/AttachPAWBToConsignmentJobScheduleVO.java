/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.AttachPAWBToConsignmentJobScheduleVO.java
 * <p>
 * Created by	:	A-9998
 * 
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

import java.util.HashMap;
import java.util.Map;

public class AttachPAWBToConsignmentJobScheduleVO extends JobScheduleVO {

	private static final long serialVersionUID = 1L;
    private static final String NUMBER_OF_DAYS = "MAL_DEF_NUMOFDAYS";
    private Integer noOfDays;

    private static final Map<String, Integer> map;

    static {
        map = new HashMap<>();
        map.put(NUMBER_OF_DAYS, 1);
    }

    public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	/**
     * @return int
     */
    @Override
    public int getPropertyCount() {
        return map.size();
    }

    /**
     * @param key
     * @return int
     */
    @Override
    public int getIndex(String key) {
        return map.get(key);
    }

    /**
     * @param index
     * @return String
     */
    @Override
    public String getValue(int index) {
    	if(index == 1){
    		 return String.valueOf(this.noOfDays);
    	}
    	
    	else{
    		return null;
    	}
    }

    /**
     * @param index
     * @param key
     */
    @Override
    public void setValue(int index, String key) {
    	
    	if(index == 1){
    		setNoOfDays(Integer.parseInt(key));
         
    	}
       
    }
}
