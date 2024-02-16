/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailMasterDataJobScheduleVO.java
 * <p>
 * Created by	:	204082
 * Created on	:	26-Sep-2022
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

public class MailMasterDataJobScheduleVO extends JobScheduleVO {

    private static final String COMPANY_CODE = "MAL_OPS_CMPCOD";
    private static final String MASTER_TYPE = "MAL_OPS_MSTTYP";

    private static final String NUMBER_OF_DAYS_TO_CONSIDER = "MAL_OPS_NUMOFDAYS";

    private static final String LAST_SCAN_TIME = "MAL_OPS_LSTSCNTIM";
    private static final String RECORD_SIZE = "MAL_OPS_RECORDSIZE";

    private String companyCode;
    private String masterType;
    private Integer noOfDaysToConsider;
    private Integer lastScanTime;
    private Integer recordSize;
    private static final Map<String, Integer> map;

    static {
        map = new HashMap<>();
        map.put(COMPANY_CODE, 1);
        map.put(MASTER_TYPE, 2);
        map.put(NUMBER_OF_DAYS_TO_CONSIDER, 3);
        map.put(LAST_SCAN_TIME, 4);
        map.put(RECORD_SIZE, 5);
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getMasterType() {
        return masterType;
    }

    public void setMasterType(String masterType) {
        this.masterType = masterType;
    }

    public Integer getNoOfDaysToConsider() {
        return noOfDaysToConsider;
    }

    public void setNoOfDaysToConsider(Integer noOfDaysToConsider) {
        this.noOfDaysToConsider = noOfDaysToConsider;
    }

    public Integer getLastScanTime() {
        return lastScanTime;
    }

    public void setLastScanTime(Integer lastScanTime) {
        this.lastScanTime = lastScanTime;
    }

    public Integer getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
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
        switch (index) {
            case 1:
                return this.companyCode;

            case 2:
                return this.masterType;

            case 3:
                return String.valueOf(this.noOfDaysToConsider);

            case 4:
                return String.valueOf(this.lastScanTime);

            case 5:
                return String.valueOf(this.recordSize);
            default:
                return null;
        }
    }

    /**
     * @param index
     * @param key
     */
    @Override
    public void setValue(int index, String key) {
        switch (index) {
            case 1:
                setCompanyCode(key);
                break;

            case 2:
                setMasterType(key);
                break;

            case 3:
                setNoOfDaysToConsider(Integer.parseInt(key));
                break;

            case 4:
                setLastScanTime(Integer.parseInt(key));
                break;
            case 5:
            	setRecordSize(Integer.parseInt(key));
                break;

            default:
                break;
        }
    }
}
