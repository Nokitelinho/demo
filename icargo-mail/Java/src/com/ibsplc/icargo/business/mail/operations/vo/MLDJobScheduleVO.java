/*
 * MLDJobScheduleVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * 
 * @author A-3109
 * 
 */
public class MLDJobScheduleVO extends JobScheduleVO {

  private static final String RECORD_SIZE = "MTK_RECORD_SIZE";
  private static final String COMPANY_CODE = "MTK_DEF_CMPCOD";
	private String companyCode;
	private int recordSize;






	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String paramString) {
		this.companyCode = paramString;
	}

	public int getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}
	private static HashMap<String,Integer> map;
	static {
        map = new HashMap<String,Integer>();
        map.put(COMPANY_CODE,1);
        map.put(RECORD_SIZE,2);
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
		switch(index){
			case 1: {
				return companyCode;
			}
			case 2: {
				return String.valueOf(recordSize);
			}
			default: {
				return null;
			}
		}
	}
	@Override
	public void setValue(int index, String val) {
		switch(index){
			case 1 : 
				setCompanyCode(val);
				break;
			case 2 :
				setRecordSize(Integer.parseInt(val));
				break;
			default :{
				break;
			}
		}
	}
}